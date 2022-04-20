package net.minecraft.client.gui;

import org.apache.logging.log4j.*;
import net.minecraft.util.*;
import java.net.*;
import org.lwjgl.*;
import com.google.common.collect.*;
import java.io.*;
import java.util.*;
import net.minecraft.client.resources.*;

public class GuiScreenResourcePacks extends GuiScreen
{
    private static final Logger logger;
    private List selectedResourcePacks;
    private GuiResourcePackSelected selectedResourcePacksList;
    private GuiResourcePackAvailable availableResourcePacksList;
    private final GuiScreen parentScreen;
    private List availableResourcePacks;
    private boolean changed;
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawBackground(0);
        this.availableResourcePacksList.drawScreen(n, n2, n3);
        this.selectedResourcePacksList.drawScreen(n, n2, n3);
        this.drawCenteredString(this.fontRendererObj, I18n.format("resourcePack.title", new Object[0]), GuiScreenResourcePacks.width / 2, 16, 16777215);
        this.drawCenteredString(this.fontRendererObj, I18n.format("resourcePack.folderInfo", new Object[0]), GuiScreenResourcePacks.width / 2 - 77, GuiScreenResourcePacks.height - 26, 8421504);
        super.drawScreen(n, n2, n3);
    }
    
    static {
        logger = LogManager.getLogger();
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        this.availableResourcePacksList.mouseClicked(n, n2, n3);
        this.selectedResourcePacksList.mouseClicked(n, n2, n3);
    }
    
    @Override
    protected void mouseReleased(final int n, final int n2, final int n3) {
        super.mouseReleased(n, n2, n3);
    }
    
    public void markChanged() {
        this.changed = true;
    }
    
    public List getAvailableResourcePacks() {
        return this.availableResourcePacks;
    }
    
    public boolean hasResourcePackEntry(final ResourcePackListEntry resourcePackListEntry) {
        return this.selectedResourcePacks.contains(resourcePackListEntry);
    }
    
    public GuiScreenResourcePacks(final GuiScreen parentScreen) {
        this.changed = false;
        this.parentScreen = parentScreen;
    }
    
    public List getSelectedResourcePacks() {
        return this.selectedResourcePacks;
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == 2) {
                final File dirResourcepacks = this.mc.getResourcePackRepository().getDirResourcepacks();
                final String absolutePath = dirResourcepacks.getAbsolutePath();
                if (Util.getOSType() == Util.EnumOS.OSX) {
                    GuiScreenResourcePacks.logger.info(absolutePath);
                    Runtime.getRuntime().exec(new String[] { "/usr/bin/open", absolutePath });
                    return;
                }
                if (Util.getOSType() == Util.EnumOS.WINDOWS) {
                    Runtime.getRuntime().exec(String.format("cmd.exe /C start \"Open file\" \"%s\"", absolutePath));
                    return;
                }
                final Class<?> forName = Class.forName("java.awt.Desktop");
                forName.getMethod("browse", URI.class).invoke(forName.getMethod("getDesktop", (Class<?>[])new Class[0]).invoke(null, new Object[0]), dirResourcepacks.toURI());
                GuiScreenResourcePacks.logger.info("Opening via system class!");
                Sys.openURL("file://" + absolutePath);
            }
            else if (guiButton.id == 1) {
                if (this.changed) {
                    final ArrayList arrayList = Lists.newArrayList();
                    for (final ResourcePackListEntry resourcePackListEntry : this.selectedResourcePacks) {
                        if (resourcePackListEntry instanceof ResourcePackListEntryFound) {
                            arrayList.add(((ResourcePackListEntryFound)resourcePackListEntry).func_148318_i());
                        }
                    }
                    Collections.reverse(arrayList);
                    this.mc.getResourcePackRepository().setRepositories(arrayList);
                    this.mc.gameSettings.resourcePacks.clear();
                    this.mc.gameSettings.field_183018_l.clear();
                    for (final ResourcePackRepository.Entry entry : arrayList) {
                        this.mc.gameSettings.resourcePacks.add(entry.getResourcePackName());
                        if (entry.func_183027_f() != 1) {
                            this.mc.gameSettings.field_183018_l.add(entry.getResourcePackName());
                        }
                    }
                    this.mc.gameSettings.saveOptions();
                    this.mc.refreshResources();
                }
                this.mc.displayGuiScreen(this.parentScreen);
            }
        }
    }
    
    public List getListContaining(final ResourcePackListEntry resourcePackListEntry) {
        return this.hasResourcePackEntry(resourcePackListEntry) ? this.selectedResourcePacks : this.availableResourcePacks;
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.selectedResourcePacksList.handleMouseInput();
        this.availableResourcePacksList.handleMouseInput();
    }
    
    @Override
    public void initGui() {
        this.buttonList.add(new GuiOptionButton(2, GuiScreenResourcePacks.width / 2 - 154, GuiScreenResourcePacks.height - 48, I18n.format("resourcePack.openFolder", new Object[0])));
        this.buttonList.add(new GuiOptionButton(1, GuiScreenResourcePacks.width / 2 + 4, GuiScreenResourcePacks.height - 48, I18n.format("gui.done", new Object[0])));
        if (!this.changed) {
            this.availableResourcePacks = Lists.newArrayList();
            this.selectedResourcePacks = Lists.newArrayList();
            final ResourcePackRepository resourcePackRepository = this.mc.getResourcePackRepository();
            resourcePackRepository.updateRepositoryEntriesAll();
            final ArrayList arrayList = Lists.newArrayList((Iterable)resourcePackRepository.getRepositoryEntriesAll());
            arrayList.removeAll(resourcePackRepository.getRepositoryEntries());
            final Iterator<ResourcePackRepository.Entry> iterator = arrayList.iterator();
            while (iterator.hasNext()) {
                this.availableResourcePacks.add(new ResourcePackListEntryFound(this, iterator.next()));
            }
            final Iterator iterator2 = Lists.reverse(resourcePackRepository.getRepositoryEntries()).iterator();
            while (iterator2.hasNext()) {
                this.selectedResourcePacks.add(new ResourcePackListEntryFound(this, iterator2.next()));
            }
            this.selectedResourcePacks.add(new ResourcePackListEntryDefault(this));
        }
        (this.availableResourcePacksList = new GuiResourcePackAvailable(this.mc, 200, GuiScreenResourcePacks.height, this.availableResourcePacks)).setSlotXBoundsFromLeft(GuiScreenResourcePacks.width / 2 - 4 - 200);
        this.availableResourcePacksList.registerScrollButtons(7, 8);
        (this.selectedResourcePacksList = new GuiResourcePackSelected(this.mc, 200, GuiScreenResourcePacks.height, this.selectedResourcePacks)).setSlotXBoundsFromLeft(GuiScreenResourcePacks.width / 2 + 4);
        this.selectedResourcePacksList.registerScrollButtons(7, 8);
    }
}
