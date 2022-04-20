package dupa;

public class Animate
{
    private Easing ease;
    private float value;
    private float time;
    private boolean reversed;
    private float min;
    private float speed;
    private float max;
    
    public float getMin() {
        return this.min;
    }
    
    public Animate update() {
        if (this.reversed) {
            if (this.time > this.min) {
                this.time -= Delta.DELTATIME * 0.001f * this.speed;
            }
        }
        else if (this.time < this.max) {
            this.time += Delta.DELTATIME * 0.001f * this.speed;
        }
        this.time = this.clamp(this.time, this.min, this.max);
        this.value = this.getEase().ease(this.time, this.min, this.max, this.max);
        return this;
    }
    
    public Animate setMin(final float min) {
        this.min = min;
        return this;
    }
    
    private float clamp(final float n, final float n2, final float n3) {
        return (n < n2) ? n2 : ((n > n3) ? n3 : n);
    }
    
    public Animate setSpeed(final float speed) {
        this.speed = speed;
        return this;
    }
    
    public boolean isReversed() {
        return this.reversed;
    }
    
    public Animate setValue(final float value) {
        this.value = value;
        return this;
    }
    
    public float getMax() {
        return this.max;
    }
    
    public Animate setEase(final Easing ease) {
        this.ease = ease;
        return this;
    }
    
    public Animate setReversed(final boolean reversed) {
        this.reversed = reversed;
        return this;
    }
    
    public float getValue() {
        return this.value;
    }
    
    public Animate() {
        this.ease = Easing.LINEAR;
        this.value = 0.0f;
        this.min = 0.0f;
        this.max = 1.0f;
        this.speed = 50.0f;
        this.reversed = false;
    }
    
    public void reset() {
        this.time = this.min;
    }
    
    public Animate setMax(final float max) {
        this.max = max;
        return this;
    }
    
    public Easing getEase() {
        return this.ease;
    }
    
    public float getSpeed() {
        return this.speed;
    }
}
