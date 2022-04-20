package com.nquantum.util.render.comp;

import org.lwjgl.opengl.*;

public class drawBox
{
    public drawBox(final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        GL11.glBegin(7);
        GL11.glVertex3d(n, n2, n3);
        GL11.glVertex3d(n, n5, n3);
        GL11.glVertex3d(n4, n2, n3);
        GL11.glVertex3d(n4, n5, n3);
        GL11.glVertex3d(n4, n2, n6);
        GL11.glVertex3d(n4, n5, n6);
        GL11.glVertex3d(n, n2, n6);
        GL11.glVertex3d(n, n5, n6);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(n4, n5, n3);
        GL11.glVertex3d(n4, n2, n3);
        GL11.glVertex3d(n, n5, n3);
        GL11.glVertex3d(n, n2, n3);
        GL11.glVertex3d(n, n5, n6);
        GL11.glVertex3d(n, n2, n6);
        GL11.glVertex3d(n4, n5, n6);
        GL11.glVertex3d(n4, n2, n6);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(n, n5, n3);
        GL11.glVertex3d(n4, n5, n3);
        GL11.glVertex3d(n4, n5, n6);
        GL11.glVertex3d(n, n5, n6);
        GL11.glVertex3d(n, n5, n3);
        GL11.glVertex3d(n, n5, n6);
        GL11.glVertex3d(n4, n5, n6);
        GL11.glVertex3d(n4, n5, n3);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(n, n2, n3);
        GL11.glVertex3d(n4, n2, n3);
        GL11.glVertex3d(n4, n2, n6);
        GL11.glVertex3d(n, n2, n6);
        GL11.glVertex3d(n, n2, n3);
        GL11.glVertex3d(n, n2, n6);
        GL11.glVertex3d(n4, n2, n6);
        GL11.glVertex3d(n4, n2, n3);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(n, n2, n3);
        GL11.glVertex3d(n, n5, n3);
        GL11.glVertex3d(n, n2, n6);
        GL11.glVertex3d(n, n5, n6);
        GL11.glVertex3d(n4, n2, n6);
        GL11.glVertex3d(n4, n5, n6);
        GL11.glVertex3d(n4, n2, n3);
        GL11.glVertex3d(n4, n5, n3);
        GL11.glEnd();
        GL11.glBegin(7);
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
    
    public drawBox(final double n, final double n2, final double n3, final float n4) {
        GL11.glPushMatrix();
        GL11.glTranslated(0.0, 0.0, (double)(-n4));
        drawRect(-n4, 1.0f, n4, 0.0f);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslated(0.0, 0.0, (double)n4);
        drawRect(-n4, 1.0f, n4, 0.0f);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslated((double)n4, 0.0, 0.0);
        GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
        drawRect(-n4, 1.0f, n4, 0.0f);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslated((double)(-n4), 0.0, 0.0);
        GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
        drawRect(-n4, 1.0f, n4, 0.0f);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslated(0.0, 0.0, (double)(-n4));
        GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
        drawRect(-n4, n4 * 2.0f, n4, 0.0f);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslated(0.0, 1.0, (double)(-n4));
        GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
        drawRect(-n4, n4 * 2.0f, n4, 0.0f);
        GL11.glPopMatrix();
    }
    
    public static void drawRect(final float n, final float n2, final float n3, final float n4) {
        GL11.glBegin(7);
        GL11.glVertex2f(n, n2);
        GL11.glVertex2f(n3, n2);
        GL11.glVertex2f(n3, n4);
        GL11.glVertex2f(n, n4);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex2f(n, n4);
        GL11.glVertex2f(n3, n4);
        GL11.glVertex2f(n3, n2);
        GL11.glVertex2f(n, n2);
        GL11.glEnd();
    }
}
