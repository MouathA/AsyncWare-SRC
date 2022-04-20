package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import net.minecraft.tileentity.*;
import java.util.*;

public class TileEntityRendererDispatcher
{
    public static double staticPlayerZ;
    public float entityPitch;
    public TextureManager renderEngine;
    public double entityX;
    private Map mapSpecialRenderers;
    public World worldObj;
    public double entityY;
    public static TileEntityRendererDispatcher instance;
    public static double staticPlayerY;
    public Entity entity;
    public static double staticPlayerX;
    public float entityYaw;
    private FontRenderer fontRenderer;
    public double entityZ;
    
    public void renderTileEntityAt(final TileEntity tileEntity, final double n, final double n2, final double n3, final float n4, final int n5) {
        final TileEntitySpecialRenderer specialRenderer = this.getSpecialRenderer(tileEntity);
        if (specialRenderer != null) {
            specialRenderer.renderTileEntityAt(tileEntity, n, n2, n3, n4, n5);
        }
    }
    
    public TileEntitySpecialRenderer getSpecialRenderer(final TileEntity tileEntity) {
        return (tileEntity == null) ? null : this.getSpecialRendererByClass(tileEntity.getClass());
    }
    
    public TileEntitySpecialRenderer getSpecialRendererByClass(final Class clazz) {
        TileEntitySpecialRenderer specialRendererByClass = this.mapSpecialRenderers.get(clazz);
        if (specialRendererByClass == null && clazz != TileEntity.class) {
            specialRendererByClass = this.getSpecialRendererByClass(clazz.getSuperclass());
            this.mapSpecialRenderers.put(clazz, specialRendererByClass);
        }
        return specialRendererByClass;
    }
    
    public void renderTileEntity(final TileEntity tileEntity, final float n, final int n2) {
        if (tileEntity.getDistanceSq(this.entityX, this.entityY, this.entityZ) < tileEntity.getMaxRenderDistanceSquared()) {
            final int combinedLight = this.worldObj.getCombinedLight(tileEntity.getPos(), 0);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, combinedLight % 65536 / 1.0f, combinedLight / 65536 / 1.0f);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            final BlockPos pos = tileEntity.getPos();
            this.renderTileEntityAt(tileEntity, pos.getX() - TileEntityRendererDispatcher.staticPlayerX, pos.getY() - TileEntityRendererDispatcher.staticPlayerY, pos.getZ() - TileEntityRendererDispatcher.staticPlayerZ, n, n2);
        }
    }
    
    static {
        TileEntityRendererDispatcher.instance = new TileEntityRendererDispatcher();
    }
    
    public void cacheActiveRenderInfo(final World world, final TextureManager renderEngine, final FontRenderer fontRenderer, final Entity entity, final float n) {
        if (this.worldObj != world) {
            this.setWorld(world);
        }
        this.renderEngine = renderEngine;
        this.entity = entity;
        this.fontRenderer = fontRenderer;
        this.entityYaw = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * n;
        this.entityPitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * n;
        this.entityX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * n;
        this.entityY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * n;
        this.entityZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * n;
    }
    
    private TileEntityRendererDispatcher() {
        (this.mapSpecialRenderers = Maps.newHashMap()).put(TileEntitySign.class, new TileEntitySignRenderer());
        this.mapSpecialRenderers.put(TileEntityMobSpawner.class, new TileEntityMobSpawnerRenderer());
        this.mapSpecialRenderers.put(TileEntityPiston.class, new TileEntityPistonRenderer());
        this.mapSpecialRenderers.put(TileEntityChest.class, new TileEntityChestRenderer());
        this.mapSpecialRenderers.put(TileEntityEnderChest.class, new TileEntityEnderChestRenderer());
        this.mapSpecialRenderers.put(TileEntityEnchantmentTable.class, new TileEntityEnchantmentTableRenderer());
        this.mapSpecialRenderers.put(TileEntityEndPortal.class, new TileEntityEndPortalRenderer());
        this.mapSpecialRenderers.put(TileEntityBeacon.class, new TileEntityBeaconRenderer());
        this.mapSpecialRenderers.put(TileEntitySkull.class, new TileEntitySkullRenderer());
        this.mapSpecialRenderers.put(TileEntityBanner.class, new TileEntityBannerRenderer());
        final Iterator<TileEntitySpecialRenderer> iterator = this.mapSpecialRenderers.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().setRendererDispatcher(this);
        }
    }
    
    public void setWorld(final World worldObj) {
        this.worldObj = worldObj;
    }
    
    public FontRenderer getFontRenderer() {
        return this.fontRenderer;
    }
    
    public void renderTileEntityAt(final TileEntity tileEntity, final double n, final double n2, final double n3, final float n4) {
        this.renderTileEntityAt(tileEntity, n, n2, n3, n4, -1);
    }
}
