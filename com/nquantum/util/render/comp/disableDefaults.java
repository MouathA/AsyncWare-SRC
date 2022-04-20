package com.nquantum.util.render.comp;

import org.lwjgl.opengl.*;

public class disableDefaults
{
    public disableDefaults() {
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glEnable(2929);
        GL11.glDisable(32925);
        GL11.glDisable(32926);
        GL11.glDepthMask(true);
        GL11.glDisable(32926);
        GL11.glDisable(32925);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
    }
}
