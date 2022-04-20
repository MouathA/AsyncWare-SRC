package net.minecraft.client.audio;

import net.minecraft.client.settings.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import org.apache.commons.lang3.*;
import net.minecraft.client.resources.*;
import java.util.*;
import net.minecraft.entity.player.*;
import org.apache.logging.log4j.*;
import com.google.gson.*;
import java.lang.reflect.*;
import org.apache.commons.io.*;
import java.io.*;

public class SoundHandler implements ITickable, IResourceManagerReloadListener
{
    private final SoundRegistry sndRegistry;
    private static final Gson GSON;
    private final SoundManager sndManager;
    private final IResourceManager mcResourceManager;
    private static final ParameterizedType TYPE;
    public static final SoundPoolEntry missing_sound;
    private static final Logger logger;
    
    @Override
    public void update() {
        this.sndManager.updateAllSounds();
    }
    
    public SoundHandler(final IResourceManager mcResourceManager, final GameSettings gameSettings) {
        this.sndRegistry = new SoundRegistry();
        this.mcResourceManager = mcResourceManager;
        this.sndManager = new SoundManager(this, gameSettings);
    }
    
    public SoundEventAccessorComposite getRandomSoundFromCategories(final SoundCategory... array) {
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<ResourceLocation> iterator = this.sndRegistry.getKeys().iterator();
        while (iterator.hasNext()) {
            final SoundEventAccessorComposite soundEventAccessorComposite = (SoundEventAccessorComposite)this.sndRegistry.getObject(iterator.next());
            if (ArrayUtils.contains((Object[])array, (Object)soundEventAccessorComposite.getSoundCategory())) {
                arrayList.add(soundEventAccessorComposite);
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return (SoundEventAccessorComposite)arrayList.get(new Random().nextInt(arrayList.size()));
    }
    
    public void stopSound(final ISound sound) {
        this.sndManager.stopSound(sound);
    }
    
    public void resumeSounds() {
        this.sndManager.resumeAllSounds();
    }
    
    public void unloadSounds() {
        this.sndManager.unloadSoundSystem();
    }
    
    @Override
    public void onResourceManagerReload(final IResourceManager resourceManager) {
        this.sndManager.reloadSoundSystem();
        this.sndRegistry.clearMap();
        for (final String s : resourceManager.getResourceDomains()) {
            final Iterator iterator2 = resourceManager.getAllResources(new ResourceLocation(s, "sounds.json")).iterator();
            while (iterator2.hasNext()) {
                for (final Map.Entry<String, V> entry : this.getSoundMap(iterator2.next().getInputStream()).entrySet()) {
                    this.loadSoundResource(new ResourceLocation(s, entry.getKey()), (SoundList)entry.getValue());
                }
            }
        }
    }
    
    public boolean isSoundPlaying(final ISound sound) {
        return this.sndManager.isSoundPlaying(sound);
    }
    
    public void setListener(final EntityPlayer entityPlayer, final float n) {
        this.sndManager.setListener(entityPlayer, n);
    }
    
    static {
        logger = LogManager.getLogger();
        GSON = new GsonBuilder().registerTypeAdapter((Type)SoundList.class, (Object)new SoundListSerializer()).create();
        TYPE = new ParameterizedType() {
            @Override
            public Type getOwnerType() {
                return null;
            }
            
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[] { String.class, SoundList.class };
            }
            
            @Override
            public Type getRawType() {
                return Map.class;
            }
        };
        missing_sound = new SoundPoolEntry(new ResourceLocation("meta:missing_sound"), 0.0, 0.0, false);
    }
    
    private void loadSoundResource(final ResourceLocation resourceLocation, final SoundList list) {
        final boolean b = !this.sndRegistry.containsKey(resourceLocation);
        SoundEventAccessorComposite soundEventAccessorComposite;
        if (!b && !list.canReplaceExisting()) {
            soundEventAccessorComposite = (SoundEventAccessorComposite)this.sndRegistry.getObject(resourceLocation);
        }
        else {
            if (!b) {
                SoundHandler.logger.debug("Replaced sound event location {}", new Object[] { resourceLocation });
            }
            soundEventAccessorComposite = new SoundEventAccessorComposite(resourceLocation, 1.0, 1.0, list.getSoundCategory());
            this.sndRegistry.registerSound(soundEventAccessorComposite);
        }
        for (final SoundList.SoundEntry soundEntry : list.getSoundList()) {
            final String soundEntryName = soundEntry.getSoundEntryName();
            final ResourceLocation resourceLocation2 = new ResourceLocation(soundEntryName);
            final String s = soundEntryName.contains(":") ? resourceLocation2.getResourceDomain() : resourceLocation.getResourceDomain();
            ISoundEventAccessor soundEventAccessor = null;
            switch (SoundHandler$3.$SwitchMap$net$minecraft$client$audio$SoundList$SoundEntry$Type[soundEntry.getSoundEntryType().ordinal()]) {
                case 1: {
                    final ResourceLocation resourceLocation3 = new ResourceLocation(s, "sounds/" + resourceLocation2.getResourcePath() + ".ogg");
                    IOUtils.closeQuietly(this.mcResourceManager.getResource(resourceLocation3).getInputStream());
                    soundEventAccessor = new SoundEventAccessor(new SoundPoolEntry(resourceLocation3, soundEntry.getSoundEntryPitch(), soundEntry.getSoundEntryVolume(), soundEntry.isStreaming()), soundEntry.getSoundEntryWeight());
                    break;
                }
                case 2: {
                    soundEventAccessor = new ISoundEventAccessor(this, s, soundEntry) {
                        final SoundHandler this$0;
                        final ResourceLocation field_148726_a = new ResourceLocation(this.val$s1, this.val$soundlist$soundentry.getSoundEntryName());
                        final SoundList.SoundEntry val$soundlist$soundentry;
                        final String val$s1;
                        
                        @Override
                        public SoundPoolEntry cloneEntry() {
                            final SoundEventAccessorComposite soundEventAccessorComposite = (SoundEventAccessorComposite)SoundHandler.access$000(this.this$0).getObject(this.field_148726_a);
                            return (soundEventAccessorComposite == null) ? SoundHandler.missing_sound : soundEventAccessorComposite.cloneEntry();
                        }
                        
                        @Override
                        public int getWeight() {
                            final SoundEventAccessorComposite soundEventAccessorComposite = (SoundEventAccessorComposite)SoundHandler.access$000(this.this$0).getObject(this.field_148726_a);
                            return (soundEventAccessorComposite == null) ? 0 : soundEventAccessorComposite.getWeight();
                        }
                        
                        @Override
                        public Object cloneEntry() {
                            return this.cloneEntry();
                        }
                    };
                    break;
                }
                default: {
                    throw new IllegalStateException("IN YOU FACE");
                }
            }
            soundEventAccessorComposite.addSoundToEventPool(soundEventAccessor);
        }
    }
    
    public void playSound(final ISound sound) {
        this.sndManager.playSound(sound);
    }
    
    public void setSoundLevel(final SoundCategory soundCategory, final float n) {
        if (soundCategory == SoundCategory.MASTER && n <= 0.0f) {
            this.stopSounds();
        }
        this.sndManager.setSoundCategoryVolume(soundCategory, n);
    }
    
    public void playDelayedSound(final ISound sound, final int n) {
        this.sndManager.playDelayedSound(sound, n);
    }
    
    protected Map getSoundMap(final InputStream inputStream) {
        final Map map = (Map)SoundHandler.GSON.fromJson((Reader)new InputStreamReader(inputStream), (Type)SoundHandler.TYPE);
        IOUtils.closeQuietly(inputStream);
        return map;
    }
    
    public void stopSounds() {
        this.sndManager.stopAllSounds();
    }
    
    static SoundRegistry access$000(final SoundHandler soundHandler) {
        return soundHandler.sndRegistry;
    }
    
    public SoundEventAccessorComposite getSound(final ResourceLocation resourceLocation) {
        return (SoundEventAccessorComposite)this.sndRegistry.getObject(resourceLocation);
    }
    
    public void pauseSounds() {
        this.sndManager.pauseAllSounds();
    }
}
