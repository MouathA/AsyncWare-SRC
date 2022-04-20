package net.minecraft.client.resources.data;

public class AnimationFrame
{
    private final int frameTime;
    private final int frameIndex;
    
    public int getFrameTime() {
        return this.frameTime;
    }
    
    public AnimationFrame(final int frameIndex, final int frameTime) {
        this.frameIndex = frameIndex;
        this.frameTime = frameTime;
    }
    
    public int getFrameIndex() {
        return this.frameIndex;
    }
    
    public boolean hasNoTime() {
        return this.frameTime == -1;
    }
    
    public AnimationFrame(final int n) {
        this(n, -1);
    }
}
