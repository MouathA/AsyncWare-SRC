package com.nquantum.util.render.comp;

import org.lwjgl.opengl.*;

public class drawOutlinedBox
{
    public drawOutlinedBox(final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final float n7) {
        GL11.glLineWidth(n7);
        GL11.glBegin(1);
        GL11.glVertex3d(n, n2, n3);
        GL11.glVertex3d(n, n5, n3);
        GL11.glVertex3d(n4, n2, n3);
        GL11.glVertex3d(n4, n5, n3);
        GL11.glVertex3d(n4, n2, n6);
        GL11.glVertex3d(n4, n5, n6);
        GL11.glVertex3d(n, n2, n6);
        GL11.glVertex3d(n, n5, n6);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(n4, n5, n3);
        GL11.glVertex3d(n4, n2, n3);
        GL11.glVertex3d(n, n5, n3);
        GL11.glVertex3d(n, n2, n3);
        GL11.glVertex3d(n, n5, n6);
        GL11.glVertex3d(n, n2, n6);
        GL11.glVertex3d(n4, n5, n6);
        GL11.glVertex3d(n4, n2, n6);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(n, n5, n3);
        GL11.glVertex3d(n4, n5, n3);
        GL11.glVertex3d(n4, n5, n6);
        GL11.glVertex3d(n, n5, n6);
        GL11.glVertex3d(n, n5, n3);
        GL11.glVertex3d(n, n5, n6);
        GL11.glVertex3d(n4, n5, n6);
        GL11.glVertex3d(n4, n5, n3);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(n, n2, n3);
        GL11.glVertex3d(n4, n2, n3);
        GL11.glVertex3d(n4, n2, n6);
        GL11.glVertex3d(n, n2, n6);
        GL11.glVertex3d(n, n2, n3);
        GL11.glVertex3d(n, n2, n6);
        GL11.glVertex3d(n4, n2, n6);
        GL11.glVertex3d(n4, n2, n3);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(n, n2, n3);
        GL11.glVertex3d(n, n5, n3);
        GL11.glVertex3d(n, n2, n6);
        GL11.glVertex3d(n, n5, n6);
        GL11.glVertex3d(n4, n2, n6);
        GL11.glVertex3d(n4, n5, n6);
        GL11.glVertex3d(n4, n2, n3);
        GL11.glVertex3d(n4, n5, n3);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(n, n5, n6);
        GL11.glVertex3d(n, n2, n6);
        GL11.glVertex3d(n, n5, n3);
        GL11.glVertex3d(n, n2, n3);
        GL11.glVertex3d(n4, n5, n3);
        GL11.glVertex3d(n4, n2, n3);
        GL11.glVertex3d(n4, n5, n6);
        GL11.glVertex3d(n4, n2, n6);
        GL11.glEnd();
    }
}
