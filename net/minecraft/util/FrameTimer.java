package net.minecraft.util;

public class FrameTimer
{
    private int field_181753_b;
    private int field_181754_c;
    private int field_181755_d;
    private final long[] field_181752_a;
    
    public void func_181747_a(final long n) {
        this.field_181752_a[this.field_181755_d] = n;
        ++this.field_181755_d;
        if (this.field_181755_d == 240) {
            this.field_181755_d = 0;
        }
        if (this.field_181754_c < 240) {
            this.field_181753_b = 0;
            ++this.field_181754_c;
        }
        else {
            this.field_181753_b = this.func_181751_b(this.field_181755_d + 1);
        }
    }
    
    public long[] func_181746_c() {
        return this.field_181752_a;
    }
    
    public int func_181751_b(final int n) {
        return n % 240;
    }
    
    public int func_181748_a(final long n, final int n2) {
        return (int)(n / 1.6666666E7 * n2);
    }
    
    public int func_181750_b() {
        return this.field_181755_d;
    }
    
    public int func_181749_a() {
        return this.field_181753_b;
    }
    
    public FrameTimer() {
        this.field_181752_a = new long[240];
    }
}
