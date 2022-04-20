package com.nquantum.util.render.comp;

import org.lwjgl.opengl.*;

public class fillRectangle
{
    public fillRectangle(final double n, final double n2, final double n3, final double n4) {
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glBegin(7);
        GL11.glVertex2d(n, n2 + n4);
        GL11.glVertex2d(n + n3, n2 + n4);
        GL11.glVertex2d(n + n3, n2);
        GL11.glVertex2d(n, n2);
        GL11.glEnd();
    }
}
