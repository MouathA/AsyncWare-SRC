package com.nquantum.util;

import org.lwjgl.opengl.*;

public class drawLine
{
    public drawLine(final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final float n7) {
        GL11.glPushMatrix();
        GL11.glColor4f(30.0f, 30.0f, 230.0f, 100.0f);
        GL11.glLineWidth(n7);
        GL11.glBegin(6913);
        GL11.glColor3f(255.0f, 50.0f, 50.0f);
        GL11.glVertex3d(n, n2, n3);
        GL11.glVertex3d(n4, n5, n6);
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glEnable(2896);
        GL11.glEnable(2929);
        GL11.glDisable(32925);
        GL11.glDisable(32926);
        GL11.glPopMatrix();
    }
}
