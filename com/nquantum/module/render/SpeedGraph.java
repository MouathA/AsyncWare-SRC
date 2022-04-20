package com.nquantum.module.render;

import com.nquantum.util.*;
import com.nquantum.event.*;
import com.nquantum.module.*;
import com.nquantum.event.impl.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import java.util.*;

public class SpeedGraph extends Module
{
    int maxHeight;
    private ArrayList speeds;
    int ticks;
    
    @Punjabi
    @Override
    public void onUpdate(final EventUpdate eventUpdate) {
        this.setDisplayName("Motion Graph");
        if (!this.mc.thePlayer.isEntityAlive()) {
            return;
        }
        final double n = MovementUtil.getSpeed() * 20.0f;
        if (this.speeds.size() > this.ticks) {
            this.speeds.remove(0);
        }
        this.speeds.add(n);
    }
    
    public SpeedGraph() {
        super("SpeedGraph", 0, Category.RENDER);
        this.speeds = new ArrayList((Collection<? extends E>)Collections.nCopies(50, 0.0));
        this.maxHeight = 30;
        this.ticks = 5;
    }
    
    @Punjabi
    private void onRender2D(final Event2D event2D) {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glBegin(7);
        GlStateManager.color(0.015686275f, 0.015686275f, 0.015686275f, 0.0f);
        GL11.glVertex2f(1.0f, (float)(160 - this.maxHeight - 2));
        GL11.glVertex2f(1.0f, (float)160);
        GL11.glVertex2f((float)(2 + this.speeds.size()), (float)160);
        GL11.glVertex2f((float)(2 + this.speeds.size()), (float)(160 - this.maxHeight - 2));
        GL11.glEnd();
        GL11.glEnable(2848);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glBegin(3);
        float n = 160;
        final Iterator<Double> iterator = (Iterator<Double>)this.speeds.iterator();
        while (iterator.hasNext()) {
            final float clamp_float = MathHelper.clamp_float((float)(160 - iterator.next() * 2.0), (float)(160 - this.maxHeight), 160.0f);
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
    
    public float getSpeed() {
        return (float)Math.sqrt(this.mc.thePlayer.motionX * this.mc.thePlayer.motionX + this.mc.thePlayer.motionZ * this.mc.thePlayer.motionZ);
    }
}
