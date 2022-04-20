package net.minecraft.client.audio;

import net.minecraft.client.settings.*;
import net.minecraft.entity.player.*;
import io.netty.util.internal.*;
import java.util.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import paulscode.sound.libraries.*;
import paulscode.sound.codecs.*;
import org.apache.logging.log4j.*;
import java.net.*;
import java.io.*;
import net.minecraft.client.*;
import paulscode.sound.*;

public class SoundManager
{
    private final Map delayedSounds;
    private Map playingSoundPoolEntries;
    private final List tickableSounds;
    private final Map playingSoundsStopTime;
    private boolean loaded;
    private final Multimap categorySounds;
    private final Map invPlayingSounds;
    private static final Logger logger;
    private final GameSettings options;
    private final SoundHandler sndHandler;
    private int playTime;
    private static final Marker LOG_MARKER;
    private final Map playingSounds;
    private SoundSystemStarterThread sndSystem;
    
    public void setListener(final EntityPlayer entityPlayer, final float n) {
        if (this.loaded && entityPlayer != null) {
            final float n2 = entityPlayer.prevRotationPitch + (entityPlayer.rotationPitch - entityPlayer.prevRotationPitch) * n;
            final float n3 = entityPlayer.prevRotationYaw + (entityPlayer.rotationYaw - entityPlayer.prevRotationYaw) * n;
            final double n4 = entityPlayer.prevPosX + (entityPlayer.posX - entityPlayer.prevPosX) * n;
            final double n5 = entityPlayer.prevPosY + (entityPlayer.posY - entityPlayer.prevPosY) * n + entityPlayer.getEyeHeight();
            final double n6 = entityPlayer.prevPosZ + (entityPlayer.posZ - entityPlayer.prevPosZ) * n;
            final float cos = MathHelper.cos((n3 + 90.0f) * 0.017453292f);
            final float sin = MathHelper.sin((n3 + 90.0f) * 0.017453292f);
            final float cos2 = MathHelper.cos(-n2 * 0.017453292f);
            final float sin2 = MathHelper.sin(-n2 * 0.017453292f);
            final float cos3 = MathHelper.cos((-n2 + 90.0f) * 0.017453292f);
            final float sin3 = MathHelper.sin((-n2 + 90.0f) * 0.017453292f);
            final float n7 = cos * cos2;
            final float n8 = sin * cos2;
            final float n9 = cos * cos3;
            final float n10 = sin * cos3;
            this.sndSystem.setListenerPosition((float)n4, (float)n5, (float)n6);
            this.sndSystem.setListenerOrientation(n7, sin2, n8, n9, sin3, n10);
        }
    }
    
    public void setSoundCategoryVolume(final SoundCategory soundCategory, final float masterVolume) {
        if (this.loaded) {
            if (soundCategory == SoundCategory.MASTER) {
                this.sndSystem.setMasterVolume(masterVolume);
            }
            else {
                for (final String s : this.categorySounds.get((Object)soundCategory)) {
                    final ISound sound = this.playingSounds.get(s);
                    final float normalizedVolume = this.getNormalizedVolume(sound, this.playingSoundPoolEntries.get(sound), soundCategory);
                    if (normalizedVolume <= 0.0f) {
                        this.stopSound(sound);
                    }
                    else {
                        this.sndSystem.setVolume(s, normalizedVolume);
                    }
                }
            }
        }
    }
    
    public void updateAllSounds() {
        ++this.playTime;
        for (final ITickableSound tickableSound : this.tickableSounds) {
            tickableSound.update();
            if (tickableSound.isDonePlaying()) {
                this.stopSound(tickableSound);
            }
            else {
                final String s = this.invPlayingSounds.get(tickableSound);
                this.sndSystem.setVolume(s, this.getNormalizedVolume(tickableSound, this.playingSoundPoolEntries.get(tickableSound), this.sndHandler.getSound(tickableSound.getSoundLocation()).getSoundCategory()));
                this.sndSystem.setPitch(s, this.getNormalizedPitch(tickableSound, this.playingSoundPoolEntries.get(tickableSound)));
                this.sndSystem.setPosition(s, tickableSound.getXPosF(), tickableSound.getYPosF(), tickableSound.getZPosF());
            }
        }
        final Iterator<Map.Entry<String, V>> iterator2 = this.playingSounds.entrySet().iterator();
        while (iterator2.hasNext()) {
            final Map.Entry<String, V> entry = iterator2.next();
            final String s2 = entry.getKey();
            final ISound sound = (ISound)entry.getValue();
            if (!this.sndSystem.playing(s2) && (int)this.playingSoundsStopTime.get(s2) <= this.playTime) {
                final int repeatDelay = sound.getRepeatDelay();
                if (sound.canRepeat() && repeatDelay > 0) {
                    this.delayedSounds.put(sound, this.playTime + repeatDelay);
                }
                iterator2.remove();
                SoundManager.logger.debug(SoundManager.LOG_MARKER, "Removed channel {} because it's not playing anymore", new Object[] { s2 });
                this.sndSystem.removeSource(s2);
                this.playingSoundsStopTime.remove(s2);
                this.playingSoundPoolEntries.remove(sound);
                this.categorySounds.remove((Object)this.sndHandler.getSound(sound.getSoundLocation()).getSoundCategory(), (Object)s2);
                if (!(sound instanceof ITickableSound)) {
                    continue;
                }
                this.tickableSounds.remove(sound);
            }
        }
        final Iterator<Map.Entry<K, Integer>> iterator3 = this.delayedSounds.entrySet().iterator();
        while (iterator3.hasNext()) {
            final Map.Entry<K, Integer> entry2 = iterator3.next();
            if (this.playTime >= entry2.getValue()) {
                final ISound sound2 = (ISound)entry2.getKey();
                if (sound2 instanceof ITickableSound) {
                    ((ITickableSound)sound2).update();
                }
                this.playSound(sound2);
                iterator3.remove();
            }
        }
    }
    
    static Logger access$000() {
        return SoundManager.logger;
    }
    
    private float getNormalizedPitch(final ISound sound, final SoundPoolEntry soundPoolEntry) {
        return (float)MathHelper.clamp_double(sound.getPitch() * soundPoolEntry.getPitch(), 0.5, 2.0);
    }
    
    private synchronized void loadSoundSystem() {
        if (!this.loaded) {
            new Thread(new Runnable(this) {
                final SoundManager this$0;
                
                @Override
                public void run() {
                    SoundSystemConfig.setLogger((SoundSystemLogger)new SoundSystemLogger(this) {
                        final SoundManager$1 this$1;
                        
                        public void message(final String s, final int n) {
                            if (!s.isEmpty()) {
                                SoundManager.access$000().info(s);
                            }
                        }
                        
                        public void importantMessage(final String s, final int n) {
                            if (!s.isEmpty()) {
                                SoundManager.access$000().warn(s);
                            }
                        }
                        
                        public void errorMessage(final String s, final String s2, final int n) {
                            if (!s2.isEmpty()) {
                                SoundManager.access$000().error("Error in class '" + s + "'");
                                SoundManager.access$000().error(s2);
                            }
                        }
                    });
                    SoundManager.access$102(this.this$0, this.this$0.new SoundSystemStarterThread(null));
                    SoundManager.access$302(this.this$0, true);
                    SoundManager.access$100(this.this$0).setMasterVolume(SoundManager.access$400(this.this$0).getSoundLevel(SoundCategory.MASTER));
                    SoundManager.access$000().info(SoundManager.access$500(), "Sound engine started");
                }
            }, "Sound Library Loader").start();
        }
    }
    
    public void stopSound(final ISound sound) {
        if (this.loaded) {
            final String s = this.invPlayingSounds.get(sound);
            if (s != null) {
                this.sndSystem.stop(s);
            }
        }
    }
    
    public void playSound(final ISound sound) {
        if (this.loaded) {
            if (this.sndSystem.getMasterVolume() <= 0.0f) {
                SoundManager.logger.debug(SoundManager.LOG_MARKER, "Skipped playing soundEvent: {}, master volume was zero", new Object[] { sound.getSoundLocation() });
            }
            else {
                final SoundEventAccessorComposite sound2 = this.sndHandler.getSound(sound.getSoundLocation());
                if (sound2 == null) {
                    SoundManager.logger.warn(SoundManager.LOG_MARKER, "Unable to play unknown soundEvent: {}", new Object[] { sound.getSoundLocation() });
                }
                else {
                    final SoundPoolEntry cloneEntry = sound2.cloneEntry();
                    if (cloneEntry == SoundHandler.missing_sound) {
                        SoundManager.logger.warn(SoundManager.LOG_MARKER, "Unable to play empty soundEvent: {}", new Object[] { sound2.getSoundEventLocation() });
                    }
                    else {
                        final float volume = sound.getVolume();
                        float n = 16.0f;
                        if (volume > 1.0f) {
                            n *= volume;
                        }
                        final SoundCategory soundCategory = sound2.getSoundCategory();
                        final float normalizedVolume = this.getNormalizedVolume(sound, cloneEntry, soundCategory);
                        final double n2 = this.getNormalizedPitch(sound, cloneEntry);
                        final ResourceLocation soundPoolEntryLocation = cloneEntry.getSoundPoolEntryLocation();
                        if (normalizedVolume == 0.0f) {
                            SoundManager.logger.debug(SoundManager.LOG_MARKER, "Skipped playing sound {}, volume was zero.", new Object[] { soundPoolEntryLocation });
                        }
                        else {
                            final boolean b = sound.canRepeat() && sound.getRepeatDelay() == 0;
                            final String string = MathHelper.getRandomUuid((Random)ThreadLocalRandom.current()).toString();
                            if (cloneEntry.isStreamingSound()) {
                                this.sndSystem.newStreamingSource(false, string, getURLForSoundResource(soundPoolEntryLocation), soundPoolEntryLocation.toString(), b, sound.getXPosF(), sound.getYPosF(), sound.getZPosF(), sound.getAttenuationType().getTypeInt(), n);
                            }
                            else {
                                this.sndSystem.newSource(false, string, getURLForSoundResource(soundPoolEntryLocation), soundPoolEntryLocation.toString(), b, sound.getXPosF(), sound.getYPosF(), sound.getZPosF(), sound.getAttenuationType().getTypeInt(), n);
                            }
                            SoundManager.logger.debug(SoundManager.LOG_MARKER, "Playing sound {} for event {} as channel {}", new Object[] { cloneEntry.getSoundPoolEntryLocation(), sound2.getSoundEventLocation(), string });
                            this.sndSystem.setPitch(string, (float)n2);
                            this.sndSystem.setVolume(string, normalizedVolume);
                            this.sndSystem.play(string);
                            this.playingSoundsStopTime.put(string, this.playTime + 20);
                            this.playingSounds.put(string, sound);
                            this.playingSoundPoolEntries.put(sound, cloneEntry);
                            if (soundCategory != SoundCategory.MASTER) {
                                this.categorySounds.put((Object)soundCategory, (Object)string);
                            }
                            if (sound instanceof ITickableSound) {
                                this.tickableSounds.add(sound);
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void stopAllSounds() {
        if (this.loaded) {
            final Iterator<String> iterator = this.playingSounds.keySet().iterator();
            while (iterator.hasNext()) {
                this.sndSystem.stop((String)iterator.next());
            }
            this.playingSounds.clear();
            this.delayedSounds.clear();
            this.tickableSounds.clear();
            this.categorySounds.clear();
            this.playingSoundPoolEntries.clear();
            this.playingSoundsStopTime.clear();
        }
    }
    
    private float getNormalizedVolume(final ISound sound, final SoundPoolEntry soundPoolEntry, final SoundCategory soundCategory) {
        return (float)MathHelper.clamp_double(sound.getVolume() * soundPoolEntry.getVolume(), 0.0, 1.0) * this.getSoundCategoryVolume(soundCategory);
    }
    
    public void pauseAllSounds() {
        for (final String s : this.playingSounds.keySet()) {
            SoundManager.logger.debug(SoundManager.LOG_MARKER, "Pausing channel {}", new Object[] { s });
            this.sndSystem.pause(s);
        }
    }
    
    public void reloadSoundSystem() {
        this.unloadSoundSystem();
        this.loadSoundSystem();
    }
    
    static SoundSystemStarterThread access$100(final SoundManager soundManager) {
        return soundManager.sndSystem;
    }
    
    public void playDelayedSound(final ISound sound, final int n) {
        this.delayedSounds.put(sound, this.playTime + n);
    }
    
    public SoundManager(final SoundHandler sndHandler, final GameSettings options) {
        this.playTime = 0;
        this.playingSounds = (Map)HashBiMap.create();
        this.invPlayingSounds = (Map)((BiMap)this.playingSounds).inverse();
        this.playingSoundPoolEntries = Maps.newHashMap();
        this.categorySounds = (Multimap)HashMultimap.create();
        this.tickableSounds = Lists.newArrayList();
        this.delayedSounds = Maps.newHashMap();
        this.playingSoundsStopTime = Maps.newHashMap();
        this.sndHandler = sndHandler;
        this.options = options;
        SoundSystemConfig.addLibrary((Class)LibraryLWJGLOpenAL.class);
        SoundSystemConfig.setCodec("ogg", (Class)CodecJOrbis.class);
    }
    
    private float getSoundCategoryVolume(final SoundCategory soundCategory) {
        return (soundCategory != null && soundCategory != SoundCategory.MASTER) ? this.options.getSoundLevel(soundCategory) : 1.0f;
    }
    
    static GameSettings access$400(final SoundManager soundManager) {
        return soundManager.options;
    }
    
    public void unloadSoundSystem() {
        if (this.loaded) {
            this.stopAllSounds();
            this.sndSystem.cleanup();
            this.loaded = false;
        }
    }
    
    static boolean access$302(final SoundManager soundManager, final boolean loaded) {
        return soundManager.loaded = loaded;
    }
    
    public void resumeAllSounds() {
        for (final String s : this.playingSounds.keySet()) {
            SoundManager.logger.debug(SoundManager.LOG_MARKER, "Resuming channel {}", new Object[] { s });
            this.sndSystem.play(s);
        }
    }
    
    static {
        LOG_MARKER = MarkerManager.getMarker("SOUNDS");
        logger = LogManager.getLogger();
    }
    
    static SoundSystemStarterThread access$102(final SoundManager soundManager, final SoundSystemStarterThread sndSystem) {
        return soundManager.sndSystem = sndSystem;
    }
    
    private static URL getURLForSoundResource(final ResourceLocation resourceLocation) {
        return new URL(null, String.format("%s:%s:%s", "mcsounddomain", resourceLocation.getResourceDomain(), resourceLocation.getResourcePath()), new URLStreamHandler(resourceLocation) {
            final ResourceLocation val$p_148612_0_;
            
            @Override
            protected URLConnection openConnection(final URL url) {
                return new URLConnection(this, url) {
                    final SoundManager$2 this$0;
                    
                    @Override
                    public void connect() throws IOException {
                    }
                    
                    @Override
                    public InputStream getInputStream() throws IOException {
                        return Minecraft.getMinecraft().getResourceManager().getResource(this.this$0.val$p_148612_0_).getInputStream();
                    }
                };
            }
        });
    }
    
    static Marker access$500() {
        return SoundManager.LOG_MARKER;
    }
    
    public boolean isSoundPlaying(final ISound sound) {
        if (!this.loaded) {
            return false;
        }
        final String s = this.invPlayingSounds.get(sound);
        return s != null && (this.sndSystem.playing(s) || (this.playingSoundsStopTime.containsKey(s) && (int)this.playingSoundsStopTime.get(s) <= this.playTime));
    }
    
    class SoundSystemStarterThread extends SoundSystem
    {
        final SoundManager this$0;
        
        SoundSystemStarterThread(final SoundManager soundManager, final SoundManager$1 runnable) {
            this(soundManager);
        }
        
        private SoundSystemStarterThread(final SoundManager this$0) {
            this.this$0 = this$0;
        }
        
        public boolean playing(final String s) {
            // monitorenter(thread_SYNC = SoundSystemConfig.THREAD_SYNC)
            if (this.soundLibrary == null) {
                // monitorexit(thread_SYNC)
                return false;
            }
            final Source source = this.soundLibrary.getSources().get(s);
            // monitorexit(thread_SYNC)
            return source != null && (source.playing() || source.paused() || source.preLoad);
        }
    }
}
