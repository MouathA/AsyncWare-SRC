package net.minecraft.client.renderer.tileentity;

import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import com.mojang.authlib.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.*;
import com.mojang.authlib.minecraft.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.client.model.*;

public class TileEntitySkullRenderer extends TileEntitySpecialRenderer
{
    private static final ResourceLocation ZOMBIE_TEXTURES;
    private static final ResourceLocation SKELETON_TEXTURES;
    private static final ResourceLocation CREEPER_TEXTURES;
    private final ModelSkeletonHead humanoidHead;
    public static TileEntitySkullRenderer instance;
    private static final ResourceLocation WITHER_SKELETON_TEXTURES;
    private final ModelSkeletonHead skeletonHead;
    
    @Override
    public void renderTileEntityAt(final TileEntity tileEntity, final double n, final double n2, final double n3, final float n4, final int n5) {
        this.renderTileEntityAt((TileEntitySkull)tileEntity, n, n2, n3, n4, n5);
    }
    
    @Override
    public void setRendererDispatcher(final TileEntityRendererDispatcher rendererDispatcher) {
        super.setRendererDispatcher(rendererDispatcher);
        TileEntitySkullRenderer.instance = this;
    }
    
    public void renderSkull(final float n, final float n2, final float n3, final EnumFacing enumFacing, float n4, final int n5, final GameProfile gameProfile, final int n6) {
        ModelSkeletonHead modelSkeletonHead = this.skeletonHead;
        if (n6 >= 0) {
            this.bindTexture(TileEntitySkullRenderer.DESTROY_STAGES[n6]);
            GlStateManager.matrixMode(5890);
            GlStateManager.scale(4.0f, 2.0f, 1.0f);
            GlStateManager.translate(0.0625f, 0.0625f, 0.0625f);
            GlStateManager.matrixMode(5888);
        }
        else {
            switch (n5) {
                default: {
                    this.bindTexture(TileEntitySkullRenderer.SKELETON_TEXTURES);
                    break;
                }
                case 1: {
                    this.bindTexture(TileEntitySkullRenderer.WITHER_SKELETON_TEXTURES);
                    break;
                }
                case 2: {
                    this.bindTexture(TileEntitySkullRenderer.ZOMBIE_TEXTURES);
                    modelSkeletonHead = this.humanoidHead;
                    break;
                }
                case 3: {
                    modelSkeletonHead = this.humanoidHead;
                    ResourceLocation resourceLocation = DefaultPlayerSkin.getDefaultSkinLegacy();
                    if (gameProfile != null) {
                        final Minecraft minecraft = Minecraft.getMinecraft();
                        final Map loadSkinFromCache = minecraft.getSkinManager().loadSkinFromCache(gameProfile);
                        if (loadSkinFromCache.containsKey(MinecraftProfileTexture.Type.SKIN)) {
                            resourceLocation = minecraft.getSkinManager().loadSkin(loadSkinFromCache.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN);
                        }
                        else {
                            resourceLocation = DefaultPlayerSkin.getDefaultSkin(EntityPlayer.getUUID(gameProfile));
                        }
                    }
                    this.bindTexture(resourceLocation);
                    break;
                }
                case 4: {
                    this.bindTexture(TileEntitySkullRenderer.CREEPER_TEXTURES);
                    break;
                }
            }
        }
        if (enumFacing != EnumFacing.UP) {
            switch (TileEntitySkullRenderer$1.$SwitchMap$net$minecraft$util$EnumFacing[enumFacing.ordinal()]) {
                case 1: {
                    GlStateManager.translate(n + 0.5f, n2 + 0.25f, n3 + 0.74f);
                    break;
                }
                case 2: {
                    GlStateManager.translate(n + 0.5f, n2 + 0.25f, n3 + 0.26f);
                    n4 = 180.0f;
                    break;
                }
                case 3: {
                    GlStateManager.translate(n + 0.74f, n2 + 0.25f, n3 + 0.5f);
                    n4 = 270.0f;
                    break;
                }
                default: {
                    GlStateManager.translate(n + 0.26f, n2 + 0.25f, n3 + 0.5f);
                    n4 = 90.0f;
                    break;
                }
            }
        }
        else {
            GlStateManager.translate(n + 0.5f, n2, n3 + 0.5f);
        }
        final float n7 = 0.0625f;
        GlStateManager.scale(-1.0f, -1.0f, 1.0f);
        modelSkeletonHead.render(null, 0.0f, 0.0f, 0.0f, n4, 0.0f, n7);
        if (n6 >= 0) {
            GlStateManager.matrixMode(5890);
            GlStateManager.matrixMode(5888);
        }
    }
    
    static {
        SKELETON_TEXTURES = new ResourceLocation("textures/entity/skeleton/skeleton.png");
        WITHER_SKELETON_TEXTURES = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
        ZOMBIE_TEXTURES = new ResourceLocation("textures/entity/zombie/zombie.png");
        CREEPER_TEXTURES = new ResourceLocation("textures/entity/creeper/creeper.png");
    }
    
    public TileEntitySkullRenderer() {
        this.skeletonHead = new ModelSkeletonHead(0, 0, 64, 32);
        this.humanoidHead = new ModelHumanoidHead();
    }
    
    public void renderTileEntityAt(final TileEntitySkull tileEntitySkull, final double n, final double n2, final double n3, final float n4, final int n5) {
        this.renderSkull((float)n, (float)n2, (float)n3, EnumFacing.getFront(tileEntitySkull.getBlockMetadata() & 0x7), tileEntitySkull.getSkullRotation() * 360 / 16.0f, tileEntitySkull.getSkullType(), tileEntitySkull.getPlayerProfile(), n5);
    }
}
