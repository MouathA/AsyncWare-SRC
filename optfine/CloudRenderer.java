package optfine;

import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;

public class CloudRenderer
{
    private int cloudTickCounterUpdate;
    int cloudTickCounter;
    private double cloudPlayerZ;
    private Minecraft mc;
    float partialTicks;
    private int glListClouds;
    private boolean updated;
    private double cloudPlayerY;
    private double cloudPlayerX;
    private boolean renderFancy;
    
    public void startUpdateGlList() {
        GL11.glNewList(this.glListClouds, 4864);
    }
    
    public void prepareToRender(final boolean renderFancy, final int cloudTickCounter, final float partialTicks) {
        if (this.renderFancy != renderFancy) {
            this.updated = false;
        }
        this.renderFancy = renderFancy;
        this.cloudTickCounter = cloudTickCounter;
        this.partialTicks = partialTicks;
    }
    
    public boolean shouldUpdateGlList() {
        if (!this.updated) {
            return true;
        }
        if (this.cloudTickCounter >= this.cloudTickCounterUpdate + 20) {
            return true;
        }
        final Entity renderViewEntity = this.mc.getRenderViewEntity();
        return renderViewEntity.prevPosY + renderViewEntity.getEyeHeight() < 128.0 + this.mc.gameSettings.ofCloudsHeight * 128.0f != this.cloudPlayerY + renderViewEntity.getEyeHeight() < 128.0 + this.mc.gameSettings.ofCloudsHeight * 128.0f;
    }
    
    public void endUpdateGlList() {
        GL11.glEndList();
        this.cloudTickCounterUpdate = this.cloudTickCounter;
        this.cloudPlayerX = this.mc.getRenderViewEntity().prevPosX;
        this.cloudPlayerY = this.mc.getRenderViewEntity().prevPosY;
        this.cloudPlayerZ = this.mc.getRenderViewEntity().prevPosZ;
        this.updated = true;
    }
    
    public void renderGlList() {
        final Entity renderViewEntity = this.mc.getRenderViewEntity();
        final double n = renderViewEntity.prevPosX + (renderViewEntity.posX - renderViewEntity.prevPosX) * this.partialTicks;
        final double n2 = renderViewEntity.prevPosY + (renderViewEntity.posY - renderViewEntity.prevPosY) * this.partialTicks;
        final double n3 = renderViewEntity.prevPosZ + (renderViewEntity.posZ - renderViewEntity.prevPosZ) * this.partialTicks;
        final float n4 = (float)(n - this.cloudPlayerX + (this.cloudTickCounter - this.cloudTickCounterUpdate + this.partialTicks) * 0.03);
        final float n5 = (float)(n2 - this.cloudPlayerY);
        final float n6 = (float)(n3 - this.cloudPlayerZ);
        if (this.renderFancy) {
            GlStateManager.translate(-n4 / 12.0f, -n5, -n6 / 12.0f);
        }
        else {
            GlStateManager.translate(-n4, -n5, -n6);
        }
        GlStateManager.callList(this.glListClouds);
    }
    
    public CloudRenderer(final Minecraft mc) {
        this.updated = false;
        this.renderFancy = false;
        this.glListClouds = -1;
        this.cloudTickCounterUpdate = 0;
        this.cloudPlayerX = 0.0;
        this.cloudPlayerY = 0.0;
        this.cloudPlayerZ = 0.0;
        this.mc = mc;
        this.glListClouds = GLAllocation.generateDisplayLists(1);
    }
    
    public void reset() {
        this.updated = false;
    }
}
