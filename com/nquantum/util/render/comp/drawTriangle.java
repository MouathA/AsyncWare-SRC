package com.nquantum.util.render.comp;

import java.awt.*;
import org.lwjgl.opengl.*;

public class drawTriangle
{
    public static void drawTri(final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final Color color) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4d((double)(color.getRed() / 255.0f), (double)(color.getGreen() / 255.0f), (double)(color.getBlue() / 255.0f), (double)(color.getAlpha() / 255.0f));
        GL11.glLineWidth((float)n7);
        GL11.glBegin(3);
        GL11.glVertex2d(n, n2);
        GL11.glVertex2d(n3, n4);
        GL11.glVertex2d(n5, n6);
        GL11.glEnd();
    }
}
