package net.minecraft.util;

public class MouseFilter
{
    private float field_76334_b;
    private float field_76336_a;
    private float field_76335_c;
    
    public void reset() {
        this.field_76336_a = 0.0f;
        this.field_76334_b = 0.0f;
        this.field_76335_c = 0.0f;
    }
    
    public float smooth(float field_76335_c, final float n) {
        this.field_76336_a += field_76335_c;
        field_76335_c = (this.field_76336_a - this.field_76334_b) * n;
        this.field_76335_c += (field_76335_c - this.field_76335_c) * 0.5f;
        if ((field_76335_c > 0.0f && field_76335_c > this.field_76335_c) || (field_76335_c < 0.0f && field_76335_c < this.field_76335_c)) {
            field_76335_c = this.field_76335_c;
        }
        this.field_76334_b += field_76335_c;
        return field_76335_c;
    }
}
