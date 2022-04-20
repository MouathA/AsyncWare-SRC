package net.minecraft.client.resources.data;

import com.google.common.collect.*;
import java.util.*;

public class AnimationMetadataSection implements IMetadataSection
{
    private final int frameWidth;
    private final boolean interpolate;
    private final List animationFrames;
    private final int frameHeight;
    private final int frameTime;
    
    public int getFrameWidth() {
        return this.frameWidth;
    }
    
    public int getFrameIndex(final int n) {
        return this.animationFrames.get(n).getFrameIndex();
    }
    
    public AnimationMetadataSection(final List animationFrames, final int frameWidth, final int frameHeight, final int frameTime, final boolean interpolate) {
        this.animationFrames = animationFrames;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.frameTime = frameTime;
        this.interpolate = interpolate;
    }
    
    public Set getFrameIndexSet() {
        final HashSet hashSet = Sets.newHashSet();
        final Iterator<AnimationFrame> iterator = this.animationFrames.iterator();
        while (iterator.hasNext()) {
            hashSet.add(iterator.next().getFrameIndex());
        }
        return hashSet;
    }
    
    public boolean isInterpolate() {
        return this.interpolate;
    }
    
    public int getFrameTime() {
        return this.frameTime;
    }
    
    public int getFrameTimeSingle(final int n) {
        final AnimationFrame animationFrame = this.getAnimationFrame(n);
        return animationFrame.hasNoTime() ? this.frameTime : animationFrame.getFrameTime();
    }
    
    private AnimationFrame getAnimationFrame(final int n) {
        return this.animationFrames.get(n);
    }
    
    public boolean frameHasTime(final int n) {
        return !this.animationFrames.get(n).hasNoTime();
    }
    
    public int getFrameCount() {
        return this.animationFrames.size();
    }
    
    public int getFrameHeight() {
        return this.frameHeight;
    }
}
