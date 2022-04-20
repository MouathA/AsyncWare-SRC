package net.minecraft.client.audio;

import java.util.*;
import com.google.common.collect.*;

public class SoundList
{
    private final List soundList;
    private boolean replaceExisting;
    private SoundCategory category;
    
    public SoundCategory getSoundCategory() {
        return this.category;
    }
    
    public void setReplaceExisting(final boolean replaceExisting) {
        this.replaceExisting = replaceExisting;
    }
    
    public List getSoundList() {
        return this.soundList;
    }
    
    public boolean canReplaceExisting() {
        return this.replaceExisting;
    }
    
    public void setSoundCategory(final SoundCategory category) {
        this.category = category;
    }
    
    public SoundList() {
        this.soundList = Lists.newArrayList();
    }
    
    public static class SoundEntry
    {
        private float volume;
        private int weight;
        private Type type;
        private boolean streaming;
        private float pitch;
        private String name;
        
        public Type getSoundEntryType() {
            return this.type;
        }
        
        public void setSoundEntryName(final String name) {
            this.name = name;
        }
        
        public void setStreaming(final boolean streaming) {
            this.streaming = streaming;
        }
        
        public void setSoundEntryPitch(final float pitch) {
            this.pitch = pitch;
        }
        
        public float getSoundEntryVolume() {
            return this.volume;
        }
        
        public boolean isStreaming() {
            return this.streaming;
        }
        
        public void setSoundEntryWeight(final int weight) {
            this.weight = weight;
        }
        
        public void setSoundEntryType(final Type type) {
            this.type = type;
        }
        
        public int getSoundEntryWeight() {
            return this.weight;
        }
        
        public float getSoundEntryPitch() {
            return this.pitch;
        }
        
        public SoundEntry() {
            this.volume = 1.0f;
            this.pitch = 1.0f;
            this.weight = 1;
            this.type = Type.FILE;
            this.streaming = false;
        }
        
        public void setSoundEntryVolume(final float volume) {
            this.volume = volume;
        }
        
        public String getSoundEntryName() {
            return this.name;
        }
        
        public enum Type
        {
            private static final Type[] $VALUES;
            private final String field_148583_c;
            
            FILE("FILE", 0, "file"), 
            SOUND_EVENT("SOUND_EVENT", 1, "event");
            
            private Type(final String s, final int n, final String field_148583_c) {
                this.field_148583_c = field_148583_c;
            }
            
            static {
                $VALUES = new Type[] { Type.FILE, Type.SOUND_EVENT };
            }
            
            public static Type getType(final String s) {
                final Type[] values = values();
                while (0 < values.length) {
                    final Type type = values[0];
                    if (type.field_148583_c.equals(s)) {
                        return type;
                    }
                    int n = 0;
                    ++n;
                }
                return null;
            }
        }
    }
}
