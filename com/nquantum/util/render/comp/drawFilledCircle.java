package com.nquantum.util.render.comp;

import org.lwjgl.opengl.*;

public class drawFilledCircle
{
    double pi;
    
    public drawFilledCircle(final double n, final double n2, final double n3, final int n4) {
        this.pi = 3.141592653589793;
        final float n5 = (n4 >> 24 & 0xFF) / 255.0f;
        final float n6 = (n4 >> 16 & 0xFF) / 255.0f;
        final float n7 = (n4 >> 8 & 0xFF) / 255.0f;
        final float n8 = (n4 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(n6, n7, n8, n5);
        GL11.glBegin(6);
        while (true) {
            GL11.glVertex2d(n + Math.sin(0 * this.pi / 180.0) * n3, n2 + Math.cos(0 * this.pi / 180.0) * n3);
            int n9 = 0;
            ++n9;
        }
    }
}
