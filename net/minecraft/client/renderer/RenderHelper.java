package net.minecraft.client.renderer;

import net.minecraft.util.*;
import java.nio.*;
import org.lwjgl.opengl.*;

public class RenderHelper
{
    private static final Vec3 LIGHT1_POS;
    private static final Vec3 LIGHT0_POS;
    private static FloatBuffer colorBuffer;
    
    static {
        RenderHelper.colorBuffer = GLAllocation.createDirectFloatBuffer(16);
        LIGHT0_POS = new Vec3(0.20000000298023224, 1.0, -0.699999988079071).normalize();
        LIGHT1_POS = new Vec3(-0.20000000298023224, 1.0, 0.699999988079071).normalize();
    }
    
    public static void disableStandardItemLighting() {
        GlStateManager.disableLight(0);
        GlStateManager.disableLight(1);
    }
    
    private static FloatBuffer setColorBuffer(final double n, final double n2, final double n3, final double n4) {
        return setColorBuffer((float)n, (float)n2, (float)n3, (float)n4);
    }
    
    private static FloatBuffer setColorBuffer(final float n, final float n2, final float n3, final float n4) {
        RenderHelper.colorBuffer.clear();
        RenderHelper.colorBuffer.put(n).put(n2).put(n3).put(n4);
        RenderHelper.colorBuffer.flip();
        return RenderHelper.colorBuffer;
    }
    
    public static void enableGUIStandardItemLighting() {
        GlStateManager.rotate(-30.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(165.0f, 1.0f, 0.0f, 0.0f);
    }
    
    public static void enableStandardItemLighting() {
        GlStateManager.enableLight(0);
        GlStateManager.enableLight(1);
        GlStateManager.colorMaterial(1032, 5634);
        final float n = 0.4f;
        final float n2 = 0.6f;
        final float n3 = 0.0f;
        GL11.glLight(16384, 4611, setColorBuffer(RenderHelper.LIGHT0_POS.xCoord, RenderHelper.LIGHT0_POS.yCoord, RenderHelper.LIGHT0_POS.zCoord, 0.0));
        GL11.glLight(16384, 4609, setColorBuffer(n2, n2, n2, 1.0f));
        GL11.glLight(16384, 4608, setColorBuffer(0.0f, 0.0f, 0.0f, 1.0f));
        GL11.glLight(16384, 4610, setColorBuffer(n3, n3, n3, 1.0f));
        GL11.glLight(16385, 4611, setColorBuffer(RenderHelper.LIGHT1_POS.xCoord, RenderHelper.LIGHT1_POS.yCoord, RenderHelper.LIGHT1_POS.zCoord, 0.0));
        GL11.glLight(16385, 4609, setColorBuffer(n2, n2, n2, 1.0f));
        GL11.glLight(16385, 4608, setColorBuffer(0.0f, 0.0f, 0.0f, 1.0f));
        GL11.glLight(16385, 4610, setColorBuffer(n3, n3, n3, 1.0f));
        GlStateManager.shadeModel(7424);
        GL11.glLightModel(2899, setColorBuffer(n, n, n, 1.0f));
    }
}
