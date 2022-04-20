package net.minecraft.client.audio;

import java.util.*;
import net.minecraft.client.*;
import net.minecraft.util.*;

public class MusicTicker implements ITickable
{
    private final Random rand;
    private int timeUntilNextMusic;
    private ISound currentMusic;
    private final Minecraft mc;
    
    public void func_181558_a(final MusicType musicType) {
        this.currentMusic = PositionedSoundRecord.create(musicType.getMusicLocation());
        this.mc.getSoundHandler().playSound(this.currentMusic);
        this.timeUntilNextMusic = Integer.MAX_VALUE;
    }
    
    public MusicTicker(final Minecraft mc) {
        this.rand = new Random();
        this.timeUntilNextMusic = 100;
        this.mc = mc;
    }
    
    public void func_181557_a() {
        if (this.currentMusic != null) {
            this.mc.getSoundHandler().stopSound(this.currentMusic);
            this.currentMusic = null;
            this.timeUntilNextMusic = 0;
        }
    }
    
    @Override
    public void update() {
        final MusicType ambientMusicType = this.mc.getAmbientMusicType();
        if (this.currentMusic != null) {
            if (!ambientMusicType.getMusicLocation().equals(this.currentMusic.getSoundLocation())) {
                this.mc.getSoundHandler().stopSound(this.currentMusic);
                this.timeUntilNextMusic = MathHelper.getRandomIntegerInRange(this.rand, 0, ambientMusicType.getMinDelay() / 2);
            }
            if (!this.mc.getSoundHandler().isSoundPlaying(this.currentMusic)) {
                this.currentMusic = null;
                this.timeUntilNextMusic = Math.min(MathHelper.getRandomIntegerInRange(this.rand, ambientMusicType.getMinDelay(), ambientMusicType.getMaxDelay()), this.timeUntilNextMusic);
            }
        }
        if (this.currentMusic == null && this.timeUntilNextMusic-- <= 0) {
            this.func_181558_a(ambientMusicType);
        }
    }
    
    public enum MusicType
    {
        END_BOSS("END_BOSS", 5, new ResourceLocation("minecraft:music.game.end.dragon"), 0, 0);
        
        private final ResourceLocation musicLocation;
        private static final MusicType[] $VALUES;
        
        CREATIVE("CREATIVE", 2, new ResourceLocation("minecraft:music.game.creative"), 1200, 3600), 
        MENU("MENU", 0, new ResourceLocation("minecraft:music.menu"), 20, 600), 
        GAME("GAME", 1, new ResourceLocation("minecraft:music.game"), 12000, 24000), 
        NETHER("NETHER", 4, new ResourceLocation("minecraft:music.game.nether"), 1200, 3600);
        
        private final int minDelay;
        
        CREDITS("CREDITS", 3, new ResourceLocation("minecraft:music.game.end.credits"), Integer.MAX_VALUE, Integer.MAX_VALUE);
        
        private final int maxDelay;
        
        END("END", 6, new ResourceLocation("minecraft:music.game.end"), 6000, 24000);
        
        static {
            $VALUES = new MusicType[] { MusicType.MENU, MusicType.GAME, MusicType.CREATIVE, MusicType.CREDITS, MusicType.NETHER, MusicType.END_BOSS, MusicType.END };
        }
        
        public ResourceLocation getMusicLocation() {
            return this.musicLocation;
        }
        
        private MusicType(final String s, final int n, final ResourceLocation musicLocation, final int minDelay, final int maxDelay) {
            this.musicLocation = musicLocation;
            this.minDelay = minDelay;
            this.maxDelay = maxDelay;
        }
        
        public int getMinDelay() {
            return this.minDelay;
        }
        
        public int getMaxDelay() {
            return this.maxDelay;
        }
    }
}
