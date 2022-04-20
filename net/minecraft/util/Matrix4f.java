package net.minecraft.util;

public class Matrix4f extends org.lwjgl.util.vector.Matrix4f
{
    public Matrix4f() {
        final float n = 0.0f;
        this.m33 = n;
        this.m32 = n;
        this.m31 = n;
        this.m30 = n;
        this.m23 = n;
        this.m22 = n;
        this.m21 = n;
        this.m20 = n;
        this.m13 = n;
        this.m12 = n;
        this.m11 = n;
        this.m10 = n;
        this.m03 = n;
        this.m02 = n;
        this.m01 = n;
        this.m00 = n;
    }
    
    public Matrix4f(final float[] array) {
        this.m00 = array[0];
        this.m01 = array[1];
        this.m02 = array[2];
        this.m03 = array[3];
        this.m10 = array[4];
        this.m11 = array[5];
        this.m12 = array[6];
        this.m13 = array[7];
        this.m20 = array[8];
        this.m21 = array[9];
        this.m22 = array[10];
        this.m23 = array[11];
        this.m30 = array[12];
        this.m31 = array[13];
        this.m32 = array[14];
        this.m33 = array[15];
    }
}
