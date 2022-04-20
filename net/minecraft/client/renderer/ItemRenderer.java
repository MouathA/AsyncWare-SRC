package net.minecraft.client.renderer;

import com.nquantum.util.time.*;
import net.minecraft.item.*;
import net.minecraft.client.*;
import net.minecraft.client.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.world.storage.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.block.material.*;

public class ItemRenderer
{
    private static final ResourceLocation RES_UNDERWATER_OVERLAY;
    Timer rotateTimer;
    float delay;
    private final RenderManager renderManager;
    private ItemStack itemToRender;
    private static final ResourceLocation RES_MAP_BACKGROUND;
    private final RenderItem itemRenderer;
    private int equippedItemSlot;
    private float equippedProgress;
    private float prevEquippedProgress;
    private final Minecraft mc;
    
    public ItemRenderer(final Minecraft mc) {
        this.equippedItemSlot = -1;
        this.mc = mc;
        this.delay = 0.0f;
        this.rotateTimer = new Timer();
        this.renderManager = mc.getRenderManager();
        this.itemRenderer = mc.getRenderItem();
    }
    
    public void updateEquippedItem() {
        this.prevEquippedProgress = this.equippedProgress;
        final EntityPlayerSP thePlayer = this.mc.thePlayer;
        final ItemStack currentItem = thePlayer.inventory.getCurrentItem();
        if (this.itemToRender != null && currentItem != null) {
            if (!this.itemToRender.getIsItemStackEqual(currentItem)) {}
        }
        else if (this.itemToRender != null || currentItem == null) {}
        final float n = 0.4f;
        this.equippedProgress += MathHelper.clamp_float(0.0f - this.equippedProgress, -n, n);
        if (this.equippedProgress < 0.1f) {
            this.itemToRender = currentItem;
            this.equippedItemSlot = thePlayer.inventory.currentItem;
        }
    }
    
    private void renderItemMap(final AbstractClientPlayer abstractClientPlayer, final float n, final float n2, final float n3) {
        GlStateManager.translate(-0.4f * MathHelper.sin(MathHelper.sqrt_float(n3) * 3.1415927f), 0.2f * MathHelper.sin(MathHelper.sqrt_float(n3) * 3.1415927f * 2.0f), -0.2f * MathHelper.sin(n3 * 3.1415927f));
        final float func_178100_c = this.func_178100_c(n);
        GlStateManager.translate(0.0f, 0.04f, -0.72f);
        GlStateManager.translate(0.0f, n2 * -1.2f, 0.0f);
        GlStateManager.translate(0.0f, func_178100_c * -0.5f, 0.0f);
        GlStateManager.rotate(90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(func_178100_c * -85.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(0.0f, 1.0f, 0.0f, 0.0f);
        this.renderPlayerArms(abstractClientPlayer);
        final float sin = MathHelper.sin(n3 * n3 * 3.1415927f);
        final float sin2 = MathHelper.sin(MathHelper.sqrt_float(n3) * 3.1415927f);
        GlStateManager.rotate(sin * -20.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(sin2 * -20.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(sin2 * -80.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(0.38f, 0.38f, 0.38f);
        GlStateManager.rotate(90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(0.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.translate(-1.0f, -1.0f, 0.0f);
        GlStateManager.scale(0.015625f, 0.015625f, 0.015625f);
        this.mc.getTextureManager().bindTexture(ItemRenderer.RES_MAP_BACKGROUND);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        GL11.glNormal3f(0.0f, 0.0f, -1.0f);
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(-7.0, 135.0, 0.0).tex(0.0, 1.0).endVertex();
        worldRenderer.pos(135.0, 135.0, 0.0).tex(1.0, 1.0).endVertex();
        worldRenderer.pos(135.0, -7.0, 0.0).tex(1.0, 0.0).endVertex();
        worldRenderer.pos(-7.0, -7.0, 0.0).tex(0.0, 0.0).endVertex();
        instance.draw();
        final MapData mapData = Items.filled_map.getMapData(this.itemToRender, this.mc.theWorld);
        if (mapData != null) {
            this.mc.entityRenderer.getMapItemRenderer().renderMap(mapData, false);
        }
    }
    
    private void func_178096_b(final float n, final float n2) {
        GlStateManager.translate(0.56f, -0.52f, -0.71999997f);
        GlStateManager.translate(0.0f, n * -0.6f, 0.0f);
        GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);
        final float sin = MathHelper.sin(n2 * n2 * 3.1415927f);
        final float sin2 = MathHelper.sin(MathHelper.sqrt_float(n2) * 3.1415927f);
        GlStateManager.rotate(sin * -20.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(sin2 * -20.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(sin2 * -80.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(0.3f, 0.3f, 0.3f);
    }
    
    private void func_178109_a(final AbstractClientPlayer abstractClientPlayer) {
        final int combinedLight = this.mc.theWorld.getCombinedLight(new BlockPos(abstractClientPlayer.posX, abstractClientPlayer.posY + abstractClientPlayer.getEyeHeight(), abstractClientPlayer.posZ), 0);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)(combinedLight & 0xFFFF), (float)(combinedLight >> 16));
    }
    
    private void transformFirstPersonItem(final float n, final float n2) {
        GlStateManager.translate(0.56f, -0.52f, -0.71999997f);
        GlStateManager.translate(0.0f, n * -0.6f, 0.0f);
        GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);
        final float sin = MathHelper.sin(n2 * n2 * 3.1415927f);
        final float sin2 = MathHelper.sin(MathHelper.sqrt_float(n2) * 3.1415927f);
        GlStateManager.rotate(sin * -20.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(sin2 * -20.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(sin2 * -80.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(0.4f, 0.4f, 0.4f);
    }
    
    public void resetEquippedProgress() {
        this.equippedProgress = 0.0f;
    }
    
    private void renderPlayerArms(final AbstractClientPlayer abstractClientPlayer) {
        this.mc.getTextureManager().bindTexture(abstractClientPlayer.getLocationSkin());
        final RenderPlayer renderPlayer = (RenderPlayer)this.renderManager.getEntityRenderObject(this.mc.thePlayer);
        if (!abstractClientPlayer.isInvisible()) {
            this.renderRightArm(renderPlayer);
            this.renderLeftArm(renderPlayer);
        }
    }
    
    private void renderFireInFirstPerson(final float n) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 0.9f);
        GlStateManager.depthFunc(519);
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        final float n2 = 1.0f;
        while (true) {
            final TextureAtlasSprite atlasSprite = this.mc.getTextureMapBlocks().getAtlasSprite("minecraft:blocks/fire_layer_1");
            this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
            final float minU = atlasSprite.getMinU();
            final float maxU = atlasSprite.getMaxU();
            final float minV = atlasSprite.getMinV();
            final float maxV = atlasSprite.getMaxV();
            final float n3 = (0.0f - n2) / 2.0f;
            final float n4 = n3 + n2;
            final float n5 = 0.0f - n2 / 2.0f;
            final float n6 = n5 + n2;
            final float n7 = -0.5f;
            GlStateManager.translate(1 * 0.24f, -0.3f, 0.0f);
            GlStateManager.rotate(-1 * 10.0f, 0.0f, 1.0f, 0.0f);
            worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
            worldRenderer.pos(n3, n5, n7).tex(maxU, maxV).endVertex();
            worldRenderer.pos(n4, n5, n7).tex(minU, maxV).endVertex();
            worldRenderer.pos(n4, n6, n7).tex(minU, minV).endVertex();
            worldRenderer.pos(n3, n6, n7).tex(maxU, minV).endVertex();
            instance.draw();
            int n8 = 0;
            ++n8;
        }
    }
    
    private void renderWaterOverlayTexture(final float n) {
        this.mc.getTextureManager().bindTexture(ItemRenderer.RES_UNDERWATER_OVERLAY);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        final float brightness = this.mc.thePlayer.getBrightness(n);
        GlStateManager.color(brightness, brightness, brightness, 0.5f);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        final float n2 = -this.mc.thePlayer.rotationYaw / 64.0f;
        final float n3 = this.mc.thePlayer.rotationPitch / 64.0f;
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(-1.0, -1.0, -0.5).tex(4.0f + n2, 4.0f + n3).endVertex();
        worldRenderer.pos(1.0, -1.0, -0.5).tex(0.0f + n2, 4.0f + n3).endVertex();
        worldRenderer.pos(1.0, 1.0, -0.5).tex(0.0f + n2, 0.0f + n3).endVertex();
        worldRenderer.pos(-1.0, 1.0, -0.5).tex(4.0f + n2, 0.0f + n3).endVertex();
        instance.draw();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    private void func_178110_a(final EntityPlayerSP entityPlayerSP, final float n) {
        final float n2 = entityPlayerSP.prevRenderArmPitch + (entityPlayerSP.renderArmPitch - entityPlayerSP.prevRenderArmPitch) * n;
        final float n3 = entityPlayerSP.prevRenderArmYaw + (entityPlayerSP.renderArmYaw - entityPlayerSP.prevRenderArmYaw) * n;
        GlStateManager.rotate((entityPlayerSP.rotationPitch - n2) * 0.1f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate((entityPlayerSP.rotationYaw - n3) * 0.1f, 0.0f, 1.0f, 0.0f);
    }
    
    private void func_178098_a(final float n, final AbstractClientPlayer abstractClientPlayer) {
        GlStateManager.rotate(18.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(-32.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-8.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.translate(-0.9f, 0.2f, 0.0f);
        final float n2 = this.itemToRender.getMaxItemUseDuration() - (abstractClientPlayer.getItemInUseCount() - n + 1.0f);
        final float n3 = n2 / 20.0f;
        float n4 = (n3 * n3 + n3 * 2.0f) / 3.0f;
        if (n4 > 1.0f) {
            n4 = 1.0f;
        }
        if (n4 > 0.1f) {
            final float n5 = MathHelper.sin((n2 - 0.1f) * 1.3f) * (n4 - 0.1f);
            GlStateManager.translate(n5 * 0.0f, n5 * 0.01f, n5 * 0.0f);
        }
        GlStateManager.translate(n4 * 0.0f, n4 * 0.0f, n4 * 0.1f);
        GlStateManager.scale(1.0f, 1.0f, 1.0f + n4 * 0.2f);
    }
    
    private void func_178103_d() {
        GlStateManager.translate(-0.5f, 0.2f, 0.0f);
        GlStateManager.rotate(30.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-80.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(60.0f, 0.0f, 1.0f, 0.0f);
    }
    
    private void fan() {
        final float n = (float)(int)(System.currentTimeMillis() / 1.5 % 360.0);
        final float n2 = ((n > 180.0f) ? (360.0f - n) : n) * 2.0f / 180.0f;
        final float n3 = (float)(int)(System.currentTimeMillis() / 3.5 % 120.0);
        final float n4 = ((n3 > 30.0f) ? (120.0f - n3) : n3) * 2.0f / 1.0f;
        final float n5 = (float)(int)(System.currentTimeMillis() / 3.5 % 120.0);
        final float n6 = ((n5 > 30.0f) ? (110.0f - n5) : n5) * 2.0f / 1.0f;
        final float n7 = 0.0f;
        final int n8 = (int)(System.currentTimeMillis() / 2L % 360L);
        GlStateManager.translate(n7, 0.2f, -1.0f);
        GlStateManager.rotate(-59, -1, 0, 3);
        GlStateManager.rotate((float)(-n8), 1, 0, 0.0f);
        GlStateManager.rotate(100.0f, 0.0f, 1.0f, 0.0f);
    }
    
    private void func_178095_a(final AbstractClientPlayer abstractClientPlayer, final float n, final float n2) {
        GlStateManager.translate(-0.3f * MathHelper.sin(MathHelper.sqrt_float(n2) * 3.1415927f), 0.4f * MathHelper.sin(MathHelper.sqrt_float(n2) * 3.1415927f * 2.0f), -0.4f * MathHelper.sin(n2 * 3.1415927f));
        GlStateManager.translate(0.64000005f, -0.6f, -0.71999997f);
        GlStateManager.translate(0.0f, n * -0.6f, 0.0f);
        GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);
        final float sin = MathHelper.sin(n2 * n2 * 3.1415927f);
        GlStateManager.rotate(MathHelper.sin(MathHelper.sqrt_float(n2) * 3.1415927f) * 70.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(sin * -20.0f, 0.0f, 0.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(abstractClientPlayer.getLocationSkin());
        GlStateManager.translate(-1.0f, 3.6f, 3.5f);
        GlStateManager.rotate(120.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(200.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(-135.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.scale(1.0f, 1.0f, 1.0f);
        GlStateManager.translate(5.6f, 0.0f, 0.0f);
        ((RenderPlayer)this.renderManager.getEntityRenderObject(this.mc.thePlayer)).renderRightArm(this.mc.thePlayer);
    }
    
    static {
        RES_MAP_BACKGROUND = new ResourceLocation("textures/map/map_background.png");
        RES_UNDERWATER_OVERLAY = new ResourceLocation("textures/misc/underwater.png");
    }
    
    private void renderLeftArm(final RenderPlayer renderPlayer) {
        GlStateManager.rotate(92.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(45.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(41.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.translate(-0.3f, -1.1f, 0.45f);
        renderPlayer.renderLeftArm(this.mc.thePlayer);
    }
    
    public void renderItemInFirstPerson(final float p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_0        
        //     2: getfield        net/minecraft/client/renderer/ItemRenderer.prevEquippedProgress:F
        //     5: aload_0        
        //     6: getfield        net/minecraft/client/renderer/ItemRenderer.equippedProgress:F
        //     9: aload_0        
        //    10: getfield        net/minecraft/client/renderer/ItemRenderer.prevEquippedProgress:F
        //    13: fsub           
        //    14: fload_1        
        //    15: fmul           
        //    16: fadd           
        //    17: fsub           
        //    18: fstore_2       
        //    19: aload_0        
        //    20: getfield        net/minecraft/client/renderer/ItemRenderer.mc:Lnet/minecraft/client/Minecraft;
        //    23: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    26: astore_3       
        //    27: aload_3        
        //    28: fload_1        
        //    29: invokevirtual   net/minecraft/client/entity/AbstractClientPlayer.getSwingProgress:(F)F
        //    32: fstore          4
        //    34: aload_3        
        //    35: getfield        net/minecraft/client/entity/AbstractClientPlayer.prevRotationPitch:F
        //    38: aload_3        
        //    39: getfield        net/minecraft/client/entity/AbstractClientPlayer.rotationPitch:F
        //    42: aload_3        
        //    43: getfield        net/minecraft/client/entity/AbstractClientPlayer.prevRotationPitch:F
        //    46: fsub           
        //    47: fload_1        
        //    48: fmul           
        //    49: fadd           
        //    50: fstore          5
        //    52: aload_3        
        //    53: getfield        net/minecraft/client/entity/AbstractClientPlayer.prevRotationYaw:F
        //    56: aload_3        
        //    57: getfield        net/minecraft/client/entity/AbstractClientPlayer.rotationYaw:F
        //    60: aload_3        
        //    61: getfield        net/minecraft/client/entity/AbstractClientPlayer.prevRotationYaw:F
        //    64: fsub           
        //    65: fload_1        
        //    66: fmul           
        //    67: fadd           
        //    68: fstore          6
        //    70: aload_0        
        //    71: fload           5
        //    73: fload           6
        //    75: invokespecial   net/minecraft/client/renderer/ItemRenderer.func_178101_a:(FF)V
        //    78: aload_0        
        //    79: aload_3        
        //    80: invokespecial   net/minecraft/client/renderer/ItemRenderer.func_178109_a:(Lnet/minecraft/client/entity/AbstractClientPlayer;)V
        //    83: aload_0        
        //    84: aload_3        
        //    85: checkcast       Lnet/minecraft/client/entity/EntityPlayerSP;
        //    88: fload_1        
        //    89: invokespecial   net/minecraft/client/renderer/ItemRenderer.func_178110_a:(Lnet/minecraft/client/entity/EntityPlayerSP;F)V
        //    92: aload_0        
        //    93: getfield        net/minecraft/client/renderer/ItemRenderer.itemToRender:Lnet/minecraft/item/ItemStack;
        //    96: ifnull          794
        //    99: aload_0        
        //   100: getfield        net/minecraft/client/renderer/ItemRenderer.itemToRender:Lnet/minecraft/item/ItemStack;
        //   103: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   106: getstatic       net/minecraft/init/Items.filled_map:Lnet/minecraft/item/ItemMap;
        //   109: if_acmpne       125
        //   112: aload_0        
        //   113: aload_3        
        //   114: fload           5
        //   116: fload_2        
        //   117: fload           4
        //   119: invokespecial   net/minecraft/client/renderer/ItemRenderer.renderItemMap:(Lnet/minecraft/client/entity/AbstractClientPlayer;FFF)V
        //   122: goto            779
        //   125: aload_3        
        //   126: invokevirtual   net/minecraft/client/entity/AbstractClientPlayer.getItemInUseCount:()I
        //   129: ifle            766
        //   132: fload           4
        //   134: invokestatic    net/minecraft/util/MathHelper.sqrt_float:(F)F
        //   137: ldc             3.1415927
        //   139: fmul           
        //   140: invokestatic    net/minecraft/util/MathHelper.sin:(F)F
        //   143: fstore          7
        //   145: fload           4
        //   147: f2d            
        //   148: invokestatic    java/lang/Math.sqrt:(D)D
        //   151: ldc2_w          3.141592653589793
        //   154: dmul           
        //   155: invokestatic    java/lang/Math.sin:(D)D
        //   158: d2f            
        //   159: fstore          8
        //   161: aload_0        
        //   162: getfield        net/minecraft/client/renderer/ItemRenderer.itemToRender:Lnet/minecraft/item/ItemStack;
        //   165: invokevirtual   net/minecraft/item/ItemStack.getItemUseAction:()Lnet/minecraft/item/EnumAction;
        //   168: astore          9
        //   170: getstatic       com/nquantum/Asyncware.instance:Lcom/nquantum/Asyncware;
        //   173: getfield        com/nquantum/Asyncware.settingsManager:Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/SettingsManager;
        //   176: ldc_w           "Animation Mode"
        //   179: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/SettingsManager.getSettingByName:(Ljava/lang/String;)Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting;
        //   182: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting.getValString:()Ljava/lang/String;
        //   185: astore          10
        //   187: getstatic       net/minecraft/client/renderer/ItemRenderer$1.$SwitchMap$net$minecraft$item$EnumAction:[I
        //   190: aload           9
        //   192: invokevirtual   net/minecraft/item/EnumAction.ordinal:()I
        //   195: iaload         
        //   196: tableswitch {
        //                2: 232
        //                3: 241
        //                4: 241
        //                5: 256
        //                6: 751
        //          default: 763
        //        }
        //   232: aload_0        
        //   233: fload_2        
        //   234: fconst_0       
        //   235: invokespecial   net/minecraft/client/renderer/ItemRenderer.transformFirstPersonItem:(FF)V
        //   238: goto            779
        //   241: aload_0        
        //   242: aload_3        
        //   243: fload_1        
        //   244: invokespecial   net/minecraft/client/renderer/ItemRenderer.func_178104_a:(Lnet/minecraft/client/entity/AbstractClientPlayer;F)V
        //   247: aload_0        
        //   248: fload_2        
        //   249: fconst_0       
        //   250: invokespecial   net/minecraft/client/renderer/ItemRenderer.transformFirstPersonItem:(FF)V
        //   253: goto            779
        //   256: aload           10
        //   258: ldc_w           "Exhibition"
        //   261: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   264: ifeq            322
        //   267: aload_0        
        //   268: fload_2        
        //   269: fconst_2       
        //   270: fdiv           
        //   271: fconst_0       
        //   272: invokespecial   net/minecraft/client/renderer/ItemRenderer.transformFirstPersonItem:(FF)V
        //   275: fload           7
        //   277: fneg           
        //   278: ldc_w           40.0
        //   281: fmul           
        //   282: fconst_2       
        //   283: fdiv           
        //   284: fload           7
        //   286: fconst_2       
        //   287: fdiv           
        //   288: ldc_w           -0.0
        //   291: ldc_w           9.0
        //   294: invokestatic    org/lwjgl/opengl/GL11.glRotatef:(FFFF)V
        //   297: fload           7
        //   299: fneg           
        //   300: ldc_w           30.0
        //   303: fmul           
        //   304: fconst_1       
        //   305: fload           7
        //   307: fconst_2       
        //   308: fdiv           
        //   309: ldc_w           -0.0
        //   312: invokestatic    org/lwjgl/opengl/GL11.glRotatef:(FFFF)V
        //   315: aload_0        
        //   316: invokespecial   net/minecraft/client/renderer/ItemRenderer.func_178103_d:()V
        //   319: goto            779
        //   322: aload           10
        //   324: ldc_w           "Swong"
        //   327: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   330: ifeq            430
        //   333: aload_0        
        //   334: fload_2        
        //   335: fconst_0       
        //   336: invokespecial   net/minecraft/client/renderer/ItemRenderer.func_178096_b:(FF)V
        //   339: ldc             0.1
        //   341: ldc_w           0.435
        //   344: ldc_w           -0.15
        //   347: invokestatic    net/minecraft/client/renderer/GlStateManager.translate:(FFF)V
        //   350: fload           8
        //   352: fneg           
        //   353: fload           8
        //   355: ldc_w           2.5
        //   358: fdiv           
        //   359: fneg           
        //   360: fconst_0       
        //   361: invokestatic    org/lwjgl/opengl/GL11.glTranslatef:(FFF)V
        //   364: ldc2_w          12.0
        //   367: fload           8
        //   369: ldc_w           2.78
        //   372: fdiv           
        //   373: f2d            
        //   374: dconst_0       
        //   375: ldc2_w          50.0
        //   378: invokestatic    org/lwjgl/opengl/GL11.glRotated:(DDDD)V
        //   381: fload           8
        //   383: fneg           
        //   384: ldc_w           40.0
        //   387: fmul           
        //   388: f2d            
        //   389: fload           8
        //   391: ldc_w           2.78
        //   394: fdiv           
        //   395: f2d            
        //   396: dconst_0       
        //   397: ldc2_w          50.0
        //   400: invokestatic    org/lwjgl/opengl/GL11.glRotated:(DDDD)V
        //   403: fload           8
        //   405: fneg           
        //   406: ldc_w           -34.0
        //   409: fmul           
        //   410: f2d            
        //   411: ldc2_w          0.800000011920929
        //   414: fload           8
        //   416: fconst_2       
        //   417: fdiv           
        //   418: f2d            
        //   419: dconst_0       
        //   420: invokestatic    org/lwjgl/opengl/GL11.glRotated:(DDDD)V
        //   423: aload_0        
        //   424: invokespecial   net/minecraft/client/renderer/ItemRenderer.func_178103_d:()V
        //   427: goto            779
        //   430: aload           10
        //   432: ldc_w           "Swang"
        //   435: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   438: ifeq            494
        //   441: aload_0        
        //   442: fload_2        
        //   443: fconst_0       
        //   444: invokespecial   net/minecraft/client/renderer/ItemRenderer.func_178096_b:(FF)V
        //   447: fload           8
        //   449: fneg           
        //   450: ldc_w           -32.0
        //   453: fmul           
        //   454: f2d            
        //   455: fload           8
        //   457: fconst_2       
        //   458: fdiv           
        //   459: f2d            
        //   460: dconst_0       
        //   461: ldc2_w          7.0
        //   464: invokestatic    org/lwjgl/opengl/GL11.glRotated:(DDDD)V
        //   467: fload           8
        //   469: fneg           
        //   470: ldc_w           56.0
        //   473: fmul           
        //   474: f2d            
        //   475: ldc2_w          0.800000011920929
        //   478: fload           8
        //   480: fconst_2       
        //   481: fdiv           
        //   482: f2d            
        //   483: dconst_0       
        //   484: invokestatic    org/lwjgl/opengl/GL11.glRotated:(DDDD)V
        //   487: aload_0        
        //   488: invokespecial   net/minecraft/client/renderer/ItemRenderer.func_178103_d:()V
        //   491: goto            779
        //   494: aload           10
        //   496: ldc_w           "Fan"
        //   499: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   502: ifeq            518
        //   505: aload_0        
        //   506: fload_2        
        //   507: fconst_0       
        //   508: invokespecial   net/minecraft/client/renderer/ItemRenderer.transformFirstPersonItem:(FF)V
        //   511: aload_0        
        //   512: invokespecial   net/minecraft/client/renderer/ItemRenderer.fan:()V
        //   515: goto            779
        //   518: aload           10
        //   520: ldc_w           "Jew Stabber"
        //   523: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   526: ifeq            638
        //   529: aload_0        
        //   530: fload_2        
        //   531: fconst_0       
        //   532: invokespecial   net/minecraft/client/renderer/ItemRenderer.func_178096_b:(FF)V
        //   535: ldc_w           1.332
        //   538: ldc_w           0.335
        //   541: ldc_w           -0.15
        //   544: invokestatic    net/minecraft/client/renderer/GlStateManager.translate:(FFF)V
        //   547: fload           8
        //   549: fneg           
        //   550: ldc_w           32.0
        //   553: fmul           
        //   554: f2d            
        //   555: fload           8
        //   557: fconst_2       
        //   558: fdiv           
        //   559: f2d            
        //   560: dconst_0       
        //   561: ldc2_w          7.0
        //   564: invokestatic    org/lwjgl/opengl/GL11.glRotated:(DDDD)V
        //   567: fload           8
        //   569: fneg           
        //   570: ldc_w           -40.0
        //   573: fmul           
        //   574: f2d            
        //   575: ldc2_w          0.800000011920929
        //   578: fload           8
        //   580: fconst_2       
        //   581: fdiv           
        //   582: f2d            
        //   583: dconst_0       
        //   584: invokestatic    org/lwjgl/opengl/GL11.glRotated:(DDDD)V
        //   587: ldc             -0.5
        //   589: ldc_w           -0.37
        //   592: fconst_0       
        //   593: invokestatic    net/minecraft/client/renderer/GlStateManager.translate:(FFF)V
        //   596: ldc_w           20.0
        //   599: fconst_0       
        //   600: fconst_1       
        //   601: fconst_0       
        //   602: invokestatic    net/minecraft/client/renderer/GlStateManager.rotate:(FFFF)V
        //   605: ldc_w           -140.0
        //   608: fconst_1       
        //   609: fconst_0       
        //   610: fconst_0       
        //   611: invokestatic    net/minecraft/client/renderer/GlStateManager.rotate:(FFFF)V
        //   614: ldc_w           60.0
        //   617: fconst_0       
        //   618: fconst_1       
        //   619: fconst_0       
        //   620: invokestatic    net/minecraft/client/renderer/GlStateManager.rotate:(FFFF)V
        //   623: ldc_w           1.25
        //   626: ldc_w           1.25
        //   629: ldc_w           1.25
        //   632: invokestatic    net/minecraft/client/renderer/GlStateManager.scale:(FFF)V
        //   635: goto            779
        //   638: aload           10
        //   640: aload           10
        //   642: ldc_w           "Astolfo"
        //   645: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   648: ifeq            751
        //   651: fload           5
        //   653: invokestatic    net/minecraft/util/MathHelper.sqrt_float:(F)F
        //   656: ldc             3.1415927
        //   658: fmul           
        //   659: invokestatic    net/minecraft/util/MathHelper.sin:(F)F
        //   662: fstore          11
        //   664: aload_0        
        //   665: getfield        net/minecraft/client/renderer/ItemRenderer.delay:F
        //   668: fconst_0       
        //   669: fconst_0       
        //   670: ldc_w           -0.1
        //   673: invokestatic    net/minecraft/client/renderer/GlStateManager.rotate:(FFFF)V
        //   676: aload_0        
        //   677: fload_2        
        //   678: ldc_w           1.6
        //   681: fdiv           
        //   682: fconst_0       
        //   683: invokespecial   net/minecraft/client/renderer/ItemRenderer.func_178096_b:(FF)V
        //   686: fload           5
        //   688: invokestatic    net/minecraft/util/MathHelper.sqrt_float:(F)F
        //   691: ldc_w           3.0
        //   694: fmul           
        //   695: invokestatic    net/minecraft/util/MathHelper.sin:(F)F
        //   698: fstore          11
        //   700: aload_0        
        //   701: getfield        net/minecraft/client/renderer/ItemRenderer.rotateTimer:Lcom/nquantum/util/time/Timer;
        //   704: lconst_1       
        //   705: iconst_1       
        //   706: invokevirtual   com/nquantum/util/time/Timer.hasTimeElapsed:(JZ)Z
        //   709: ifeq            728
        //   712: aload_0        
        //   713: dup            
        //   714: getfield        net/minecraft/client/renderer/ItemRenderer.delay:F
        //   717: fconst_1       
        //   718: fadd           
        //   719: putfield        net/minecraft/client/renderer/ItemRenderer.delay:F
        //   722: iinc            12, 1
        //   725: goto            712
        //   728: aload_0        
        //   729: getfield        net/minecraft/client/renderer/ItemRenderer.delay:F
        //   732: ldc_w           360.0
        //   735: fcmpl          
        //   736: ifle            744
        //   739: aload_0        
        //   740: fconst_0       
        //   741: putfield        net/minecraft/client/renderer/ItemRenderer.delay:F
        //   744: aload_0        
        //   745: invokespecial   net/minecraft/client/renderer/ItemRenderer.func_178103_d:()V
        //   748: goto            779
        //   751: aload_0        
        //   752: fload_2        
        //   753: fconst_0       
        //   754: invokespecial   net/minecraft/client/renderer/ItemRenderer.transformFirstPersonItem:(FF)V
        //   757: aload_0        
        //   758: fload_1        
        //   759: aload_3        
        //   760: invokespecial   net/minecraft/client/renderer/ItemRenderer.func_178098_a:(FLnet/minecraft/client/entity/AbstractClientPlayer;)V
        //   763: goto            779
        //   766: aload_0        
        //   767: fload           4
        //   769: invokespecial   net/minecraft/client/renderer/ItemRenderer.func_178105_d:(F)V
        //   772: aload_0        
        //   773: fload_2        
        //   774: fload           4
        //   776: invokespecial   net/minecraft/client/renderer/ItemRenderer.transformFirstPersonItem:(FF)V
        //   779: aload_0        
        //   780: aload_3        
        //   781: aload_0        
        //   782: getfield        net/minecraft/client/renderer/ItemRenderer.itemToRender:Lnet/minecraft/item/ItemStack;
        //   785: getstatic       net/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType.FIRST_PERSON:Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;
        //   788: invokevirtual   net/minecraft/client/renderer/ItemRenderer.renderItem:(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V
        //   791: goto            809
        //   794: aload_3        
        //   795: invokevirtual   net/minecraft/client/entity/AbstractClientPlayer.isInvisible:()Z
        //   798: ifne            809
        //   801: aload_0        
        //   802: aload_3        
        //   803: fload_2        
        //   804: fload           4
        //   806: invokespecial   net/minecraft/client/renderer/ItemRenderer.func_178095_a:(Lnet/minecraft/client/entity/AbstractClientPlayer;FF)V
        //   809: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0751 (coming from #0648).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void func_178101_a(final float n, final float n2) {
        GlStateManager.rotate(n, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(n2, 0.0f, 1.0f, 0.0f);
    }
    
    private void func_178104_a(final AbstractClientPlayer abstractClientPlayer, final float n) {
        final float n2 = abstractClientPlayer.getItemInUseCount() - n + 1.0f;
        final float n3 = n2 / this.itemToRender.getMaxItemUseDuration();
        float abs = MathHelper.abs(MathHelper.cos(n2 / 4.0f * 3.1415927f) * 0.1f);
        if (n3 >= 0.8f) {
            abs = 0.0f;
        }
        GlStateManager.translate(0.0f, abs, 0.0f);
        final float n4 = 1.0f - (float)Math.pow(n3, 27.0);
        GlStateManager.translate(n4 * 0.6f, n4 * -0.5f, n4 * 0.0f);
        GlStateManager.rotate(n4 * 90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(n4 * 10.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(n4 * 30.0f, 0.0f, 0.0f, 1.0f);
    }
    
    private void renderRightArm(final RenderPlayer renderPlayer) {
        GlStateManager.rotate(54.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(64.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(-62.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.translate(0.25f, -0.85f, 0.75f);
        renderPlayer.renderRightArm(this.mc.thePlayer);
    }
    
    public void renderItem(final EntityLivingBase p0, final ItemStack p1, final ItemCameraTransforms.TransformType p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnull          64
        //     4: aload_2        
        //     5: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //     8: astore          4
        //    10: aload           4
        //    12: invokestatic    net/minecraft/block/Block.getBlockFromItem:(Lnet/minecraft/item/Item;)Lnet/minecraft/block/Block;
        //    15: astore          5
        //    17: aload_0        
        //    18: getfield        net/minecraft/client/renderer/ItemRenderer.itemRenderer:Lnet/minecraft/client/renderer/entity/RenderItem;
        //    21: aload_2        
        //    22: invokevirtual   net/minecraft/client/renderer/entity/RenderItem.shouldRenderItemIn3D:(Lnet/minecraft/item/ItemStack;)Z
        //    25: ifeq            44
        //    28: fconst_2       
        //    29: fconst_2       
        //    30: fconst_2       
        //    31: invokestatic    net/minecraft/client/renderer/GlStateManager.scale:(FFF)V
        //    34: aload_0        
        //    35: aload           5
        //    37: ifnull          44
        //    40: iconst_0       
        //    41: invokestatic    net/minecraft/client/renderer/GlStateManager.depthMask:(Z)V
        //    44: aload_0        
        //    45: getfield        net/minecraft/client/renderer/ItemRenderer.itemRenderer:Lnet/minecraft/client/renderer/entity/RenderItem;
        //    48: aload_2        
        //    49: aload_1        
        //    50: aload_3        
        //    51: invokevirtual   net/minecraft/client/renderer/entity/RenderItem.renderItemModelForEntity:(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V
        //    54: aload_0        
        //    55: aload           5
        //    57: ifnull          64
        //    60: iconst_1       
        //    61: invokestatic    net/minecraft/client/renderer/GlStateManager.depthMask:(Z)V
        //    64: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0064 (coming from #0057).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void func_178108_a(final float n, final TextureAtlasSprite textureAtlasSprite) {
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        GlStateManager.color(0.1f, 0.1f, 0.1f, 0.5f);
        final float minU = textureAtlasSprite.getMinU();
        final float maxU = textureAtlasSprite.getMaxU();
        final float minV = textureAtlasSprite.getMinV();
        final float maxV = textureAtlasSprite.getMaxV();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(-1.0, -1.0, -0.5).tex(maxU, maxV).endVertex();
        worldRenderer.pos(1.0, -1.0, -0.5).tex(minU, maxV).endVertex();
        worldRenderer.pos(1.0, 1.0, -0.5).tex(minU, minV).endVertex();
        worldRenderer.pos(-1.0, 1.0, -0.5).tex(maxU, minV).endVertex();
        instance.draw();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    private void func_178105_d(final float n) {
        GlStateManager.translate(-0.4f * MathHelper.sin(MathHelper.sqrt_float(n) * 3.1415927f), 0.2f * MathHelper.sin(MathHelper.sqrt_float(n) * 3.1415927f * 2.0f), -0.2f * MathHelper.sin(n * 3.1415927f));
    }
    
    private float func_178100_c(final float n) {
        return -MathHelper.cos(MathHelper.clamp_float(1.0f - n / 45.0f + 0.1f, 0.0f, 1.0f) * 3.1415927f) * 0.5f + 0.5f;
    }
    
    public void renderOverlays(final float n) {
        if (!this.mc.thePlayer.isEntityInsideOpaqueBlock()) {
            if (!this.mc.thePlayer.isSpectator()) {
                if (this.mc.thePlayer.isInsideOfMaterial(Material.water)) {
                    this.renderWaterOverlayTexture(n);
                }
                if (this.mc.thePlayer.isBurning()) {
                    this.renderFireInFirstPerson(n);
                }
            }
            return;
        }
        this.mc.theWorld.getBlockState(new BlockPos(this.mc.thePlayer));
        final EntityPlayerSP thePlayer = this.mc.thePlayer;
        while (true) {
            if (this.mc.theWorld.getBlockState(new BlockPos(thePlayer.posX + (0 - 0.5f) * thePlayer.width * 0.8f, thePlayer.posY + (0 - 0.5f) * 0.1f + thePlayer.getEyeHeight(), thePlayer.posZ + (0 - 0.5f) * thePlayer.width * 0.8f)).getBlock().isVisuallyOpaque()) {}
            int n2 = 0;
            ++n2;
        }
    }
    
    public void resetEquippedProgress2() {
        this.equippedProgress = 0.0f;
    }
}
