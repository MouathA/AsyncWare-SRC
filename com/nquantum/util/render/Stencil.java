package com.nquantum.util.render;

import net.minecraft.client.*;
import net.minecraft.client.shader.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;

public class Stencil
{
    static Minecraft mc;
    
    public static void checkSetupFBO() {
        final Framebuffer framebuffer = Stencil.mc.getFramebuffer();
        if (framebuffer != null && framebuffer.depthBuffer > -1) {
            setupFBO(framebuffer);
            framebuffer.depthBuffer = -1;
        }
    }
    
    static {
        Stencil.mc = Minecraft.getMinecraft();
    }
    
    public static void write(final boolean b) {
        GL11.glClearStencil(0);
        GL11.glClear(1024);
        GL11.glEnable(2960);
        GL11.glStencilFunc(519, 1, 65535);
        GL11.glStencilOp(7680, 7680, 7681);
        if (!b) {
            GlStateManager.colorMask(false, false, false, false);
        }
    }
    
    public static void dispose() {
        GL11.glDisable(2960);
    }
    
    public static void setupFBO(final Framebuffer framebuffer) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(framebuffer.depthBuffer);
        final int glGenRenderbuffersEXT = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT(36161, glGenRenderbuffersEXT);
        EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, glGenRenderbuffersEXT);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, glGenRenderbuffersEXT);
    }
    
    public static void erase(final boolean b) {
        GL11.glStencilFunc(b ? 514 : 517, 1, 65535);
        GL11.glStencilOp(7680, 7680, 7681);
        GlStateManager.colorMask(true, true, true, true);
        GL11.glAlphaFunc(516, 0.0f);
    }
}
