package net.minecraft.client.resources;

import java.util.concurrent.locks.*;
import com.google.common.hash.*;
import com.google.common.io.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import java.util.concurrent.*;
import com.google.common.util.concurrent.*;
import org.apache.commons.io.filefilter.*;
import com.google.common.collect.*;
import org.apache.commons.io.comparator.*;
import net.minecraft.client.settings.*;
import java.util.*;
import org.apache.logging.log4j.*;
import java.awt.image.*;
import net.minecraft.client.resources.data.*;
import org.apache.commons.io.*;
import java.io.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;

public class ResourcePackRepository
{
    private final ReentrantLock lock;
    public final IMetadataSerializer rprMetadataSerializer;
    private ListenableFuture field_177322_i;
    private static final FileFilter resourcePackFilter;
    private final File dirResourcepacks;
    private IResourcePack resourcePackInstance;
    public final IResourcePack rprDefaultResourcePack;
    private final File dirServerResourcepacks;
    private static final Logger logger;
    private List repositoryEntries;
    private List repositoryEntriesAll;
    
    public IResourcePack getResourcePackInstance() {
        return this.resourcePackInstance;
    }
    
    public ListenableFuture downloadResourcePack(final String s, final String s2) {
        String s3;
        if (s2.matches("^[a-f0-9]{40}$")) {
            s3 = s2;
        }
        else {
            s3 = "legacy";
        }
        final File resourcePackInstance = new File(this.dirServerResourcepacks, s3);
        this.lock.lock();
        this.func_148529_f();
        if (resourcePackInstance.exists() && s2.length() == 40) {
            final String string = Hashing.sha1().hashBytes(Files.toByteArray(resourcePackInstance)).toString();
            if (string.equals(s2)) {
                final ListenableFuture setResourcePackInstance = this.setResourcePackInstance(resourcePackInstance);
                this.lock.unlock();
                return setResourcePackInstance;
            }
            ResourcePackRepository.logger.warn("File " + resourcePackInstance + " had wrong hash (expected " + s2 + ", found " + string + "). Deleting it.");
            FileUtils.deleteQuietly(resourcePackInstance);
        }
        this.func_183028_i();
        final GuiScreenWorking guiScreenWorking = new GuiScreenWorking();
        final Map sessionInfo = Minecraft.getSessionInfo();
        final Minecraft minecraft = Minecraft.getMinecraft();
        Futures.getUnchecked((Future)minecraft.addScheduledTask(new Runnable(this, minecraft, guiScreenWorking) {
            final GuiScreenWorking val$guiscreenworking;
            final Minecraft val$minecraft;
            final ResourcePackRepository this$0;
            
            @Override
            public void run() {
                this.val$minecraft.displayGuiScreen(this.val$guiscreenworking);
            }
        }));
        Futures.addCallback(this.field_177322_i = HttpUtil.downloadResourcePack(resourcePackInstance, s, sessionInfo, 52428800, guiScreenWorking, minecraft.getProxy()), (FutureCallback)new FutureCallback(this, resourcePackInstance, SettableFuture.create()) {
            final ResourcePackRepository this$0;
            final File val$file1;
            final SettableFuture val$settablefuture;
            
            public void onSuccess(final Object o) {
                this.this$0.setResourcePackInstance(this.val$file1);
                this.val$settablefuture.set((Object)null);
            }
            
            public void onFailure(final Throwable exception) {
                this.val$settablefuture.setException(exception);
            }
        });
        final ListenableFuture field_177322_i = this.field_177322_i;
        this.lock.unlock();
        return field_177322_i;
    }
    
    public List getRepositoryEntriesAll() {
        return (List)ImmutableList.copyOf((Collection)this.repositoryEntriesAll);
    }
    
    public ListenableFuture setResourcePackInstance(final File file) {
        this.resourcePackInstance = new FileResourcePack(file);
        return Minecraft.getMinecraft().scheduleResourcesRefresh();
    }
    
    private void fixDirResourcepacks() {
        if (this.dirResourcepacks.exists()) {
            if (!this.dirResourcepacks.isDirectory() && (!this.dirResourcepacks.delete() || !this.dirResourcepacks.mkdirs())) {
                ResourcePackRepository.logger.warn("Unable to recreate resourcepack folder, it exists but is not a directory: " + this.dirResourcepacks);
            }
        }
        else if (!this.dirResourcepacks.mkdirs()) {
            ResourcePackRepository.logger.warn("Unable to create resourcepack folder: " + this.dirResourcepacks);
        }
    }
    
    public void func_148529_f() {
        this.lock.lock();
        if (this.field_177322_i != null) {
            this.field_177322_i.cancel(true);
        }
        this.field_177322_i = null;
        if (this.resourcePackInstance != null) {
            this.resourcePackInstance = null;
            Minecraft.getMinecraft().scheduleResourcesRefresh();
        }
        this.lock.unlock();
    }
    
    private void func_183028_i() {
        final ArrayList arrayList = Lists.newArrayList((Iterable)FileUtils.listFiles(this.dirServerResourcepacks, TrueFileFilter.TRUE, (IOFileFilter)null));
        Collections.sort((List<Object>)arrayList, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
        for (final File file : arrayList) {
            final int n = 0;
            int n2 = 0;
            ++n2;
            if (n >= 10) {
                ResourcePackRepository.logger.info("Deleting old server resource pack " + file.getName());
                FileUtils.deleteQuietly(file);
            }
        }
    }
    
    public List getRepositoryEntries() {
        return (List)ImmutableList.copyOf((Collection)this.repositoryEntries);
    }
    
    public ResourcePackRepository(final File dirResourcepacks, final File dirServerResourcepacks, final IResourcePack rprDefaultResourcePack, final IMetadataSerializer rprMetadataSerializer, final GameSettings gameSettings) {
        this.lock = new ReentrantLock();
        this.repositoryEntriesAll = Lists.newArrayList();
        this.repositoryEntries = Lists.newArrayList();
        this.dirResourcepacks = dirResourcepacks;
        this.dirServerResourcepacks = dirServerResourcepacks;
        this.rprDefaultResourcePack = rprDefaultResourcePack;
        this.rprMetadataSerializer = rprMetadataSerializer;
        this.fixDirResourcepacks();
        this.updateRepositoryEntriesAll();
        final Iterator iterator = gameSettings.resourcePacks.iterator();
        while (iterator.hasNext()) {
            final String s = iterator.next();
            for (final Entry entry : this.repositoryEntriesAll) {
                if (entry.getResourcePackName().equals(s)) {
                    if (entry.func_183027_f() == 1 || gameSettings.field_183018_l.contains(entry.getResourcePackName())) {
                        this.repositoryEntries.add(entry);
                        break;
                    }
                    iterator.remove();
                    ResourcePackRepository.logger.warn("Removed selected resource pack {} because it's no longer compatible", new Object[] { entry.getResourcePackName() });
                }
            }
        }
    }
    
    public void updateRepositoryEntriesAll() {
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<File> iterator = this.getResourcePackFiles().iterator();
        while (iterator.hasNext()) {
            final Entry entry = new Entry(iterator.next(), null);
            if (!this.repositoryEntriesAll.contains(entry)) {
                entry.updateResourcePack();
                arrayList.add(entry);
            }
            else {
                final int index = this.repositoryEntriesAll.indexOf(entry);
                if (index <= -1 || index >= this.repositoryEntriesAll.size()) {
                    continue;
                }
                arrayList.add(this.repositoryEntriesAll.get(index));
            }
        }
        this.repositoryEntriesAll.removeAll(arrayList);
        final Iterator<Entry> iterator2 = this.repositoryEntriesAll.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().closeResourcePack();
        }
        this.repositoryEntriesAll = arrayList;
    }
    
    private List getResourcePackFiles() {
        return this.dirResourcepacks.isDirectory() ? Arrays.asList(this.dirResourcepacks.listFiles(ResourcePackRepository.resourcePackFilter)) : Collections.emptyList();
    }
    
    public File getDirResourcepacks() {
        return this.dirResourcepacks;
    }
    
    public void setRepositories(final List list) {
        this.repositoryEntries.clear();
        this.repositoryEntries.addAll(list);
    }
    
    static {
        logger = LogManager.getLogger();
        resourcePackFilter = new FileFilter() {
            @Override
            public boolean accept(final File file) {
                final boolean b = file.isFile() && file.getName().endsWith(".zip");
                final boolean b2 = file.isDirectory() && new File(file, "pack.mcmeta").isFile();
                return b || b2;
            }
        };
    }
    
    public class Entry
    {
        private BufferedImage texturePackIcon;
        private ResourceLocation locationTexturePackIcon;
        final ResourcePackRepository this$0;
        private PackMetadataSection rePackMetadataSection;
        private IResourcePack reResourcePack;
        private final File resourcePackFile;
        
        public void closeResourcePack() {
            if (this.reResourcePack instanceof Closeable) {
                IOUtils.closeQuietly((Closeable)this.reResourcePack);
            }
        }
        
        Entry(final ResourcePackRepository resourcePackRepository, final File file, final ResourcePackRepository$1 fileFilter) {
            this(resourcePackRepository, file);
        }
        
        public void updateResourcePack() throws IOException {
            this.reResourcePack = (this.resourcePackFile.isDirectory() ? new FolderResourcePack(this.resourcePackFile) : new FileResourcePack(this.resourcePackFile));
            this.rePackMetadataSection = (PackMetadataSection)this.reResourcePack.getPackMetadata(this.this$0.rprMetadataSerializer, "pack");
            this.texturePackIcon = this.reResourcePack.getPackImage();
            if (this.texturePackIcon == null) {
                this.texturePackIcon = this.this$0.rprDefaultResourcePack.getPackImage();
            }
            this.closeResourcePack();
        }
        
        @Override
        public String toString() {
            return String.format("%s:%s:%d", this.resourcePackFile.getName(), this.resourcePackFile.isDirectory() ? "folder" : "zip", this.resourcePackFile.lastModified());
        }
        
        public void bindTexturePackIcon(final TextureManager textureManager) {
            if (this.locationTexturePackIcon == null) {
                this.locationTexturePackIcon = textureManager.getDynamicTextureLocation("texturepackicon", new DynamicTexture(this.texturePackIcon));
            }
            textureManager.bindTexture(this.locationTexturePackIcon);
        }
        
        @Override
        public boolean equals(final Object o) {
            return this == o || (o instanceof Entry && this.toString().equals(o.toString()));
        }
        
        public String getTexturePackDescription() {
            return (this.rePackMetadataSection == null) ? (EnumChatFormatting.RED + "Invalid pack.mcmeta (or missing 'pack' section)") : this.rePackMetadataSection.getPackDescription().getFormattedText();
        }
        
        public IResourcePack getResourcePack() {
            return this.reResourcePack;
        }
        
        @Override
        public int hashCode() {
            return this.toString().hashCode();
        }
        
        private Entry(final ResourcePackRepository this$0, final File resourcePackFile) {
            this.this$0 = this$0;
            this.resourcePackFile = resourcePackFile;
        }
        
        public int func_183027_f() {
            return this.rePackMetadataSection.getPackFormat();
        }
        
        public String getResourcePackName() {
            return this.reResourcePack.getPackName();
        }
    }
}
