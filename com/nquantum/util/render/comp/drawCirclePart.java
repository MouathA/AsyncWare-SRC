package com.nquantum.util.render.comp;

import org.lwjgl.opengl.*;
import net.minecraft.util.*;

public class drawCirclePart
{
    public drawCirclePart(final double n, final double n2, final float n3, final float n4, final float n5, final int n6) {
        GL11.glBegin(6);
        GL11.glVertex2d(n, n2);
        final float n7 = (n4 - n3) / n6;
        while (0 <= n6) {
            final float n8 = n3 + 0 * n7;
            GL11.glVertex2d(n + MathHelper.sin(n8) * n5, n2 + MathHelper.cos(n8) * n5);
            int n9 = 0;
            ++n9;
        }
        GL11.glEnd();
    }
}
