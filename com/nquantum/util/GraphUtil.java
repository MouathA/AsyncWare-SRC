package com.nquantum.util;

import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import java.util.*;

public class GraphUtil
{
    private static ArrayList speeds;
    
    public static void speedGraph() {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glBegin(7);
        GlStateManager.color(0.015686275f, 0.02745098f, 0.1254902f, 0.6f);
        GL11.glVertex2f(1.0f, (float)153);
        GL11.glVertex2f(1.0f, (float)160);
        GL11.glVertex2f((float)(2 + GraphUtil.speeds.size()), (float)160);
        GL11.glVertex2f((float)(2 + GraphUtil.speeds.size()), (float)153);
        GL11.glEnd();
        GL11.glEnable(2848);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glBegin(3);
        float n = 160;
        final Iterator<Double> iterator = (Iterator<Double>)GraphUtil.speeds.iterator();
        while (iterator.hasNext()) {
            final float clamp_float = MathHelper.clamp_float((float)(160 - iterator.next() * 2.0), 155, 160.0f);
            GL11.glVertex2f((float)2, n);
            GL11.glVertex2f((float)2, clamp_float);
            n = clamp_float;
            int n2 = 0;
            ++n2;
        }
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }
    
    static {
        GraphUtil.speeds = new ArrayList((Collection<? extends E>)Collections.nCopies(50, 0.0));
    }
}
