package net.minecraft.client.renderer;

import net.minecraft.client.resources.*;
import net.minecraft.client.shader.*;
import java.nio.*;
import net.minecraft.client.*;
import net.minecraft.block.material.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.potion.*;
import net.minecraft.client.multiplayer.*;
import com.nquantum.*;
import net.minecraft.client.entity.*;
import org.apache.logging.log4j.*;
import org.lwjgl.util.glu.*;
import net.minecraft.client.settings.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.passive.*;
import net.minecraft.block.state.*;
import net.minecraft.client.gui.*;
import optfine.*;
import net.minecraft.server.integrated.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.culling.*;
import com.nquantum.event.impl.*;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.world.biome.*;
import net.minecraft.util.*;
import com.google.common.base.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import java.util.*;
import java.lang.reflect.*;
import net.minecraft.world.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import org.lwjgl.opengl.*;

public class EntityRenderer implements IResourceManagerReloadListener
{
    public static final int shaderCount;
    private double cameraYaw;
    private long prevFrameTime;
    private static final ResourceLocation locationSnowPng;
    private int rainSoundCounter;
    private boolean useShader;
    public static int anaglyphField;
    private int rendererUpdateCount;
    private float[] rainXCoords;
    private Entity pointedEntity;
    private boolean initialized;
    private final IResourceManager resourceManager;
    private float bossColorModifier;
    private float smoothCamPartialTicks;
    private float bossColorModifierPrev;
    public ItemRenderer itemRenderer;
    public float fogColorRed;
    private float smoothCamPitch;
    private int shaderIndex;
    private double cameraZoom;
    private final MapItemRenderer theMapItemRenderer;
    private Random random;
    private static final ResourceLocation locationRainPng;
    private int frameCount;
    private boolean renderHand;
    public float fogColorGreen;
    public float fogColorBlue;
    private static final Logger logger;
    private boolean showDebugInfo;
    private static final ResourceLocation[] shaderResourceLocations;
    private float torchFlickerDX;
    private boolean drawBlockOutline;
    private float thirdPersonDistance;
    private float avgServerTimeDiff;
    private int debugViewDirection;
    private float fogColor1;
    public ShaderGroup theShaderGroup;
    private float fogColor2;
    private float torchFlickerX;
    public static boolean anaglyphEnable;
    private int serverWaitTimeCurrent;
    private float thirdPersonDistanceTemp;
    private int lastServerTicks;
    private FloatBuffer fogColorBuffer;
    private float fovModifierHand;
    private float smoothCamYaw;
    private long renderEndNanoTime;
    private double cameraPitch;
    private final ResourceLocation locationLightMap;
    private boolean cloudFog;
    private int serverWaitTime;
    private long lastServerTime;
    private final DynamicTexture lightmapTexture;
    private World updatedWorld;
    private boolean debugView;
    public boolean fogStandard;
    private MouseFilter mouseFilterYAxis;
    private Minecraft mc;
    private static final String __OBFID = "CL_00000947";
    private long lastErrorCheckTimeMs;
    private float avgServerTickDiff;
    private float[] rainYCoords;
    private final int[] lightmapColors;
    private float clipDistance;
    private boolean lightmapUpdateNeeded;
    private float smoothCamFilterX;
    private float farPlaneDistance;
    private float smoothCamFilterY;
    private float fovModifierHandPrev;
    private MouseFilter mouseFilterXAxis;
    
    private void updateFogColor(final float n) {
        final WorldClient theWorld = this.mc.theWorld;
        final Entity renderViewEntity = this.mc.getRenderViewEntity();
        final float n2 = 1.0f - (float)Math.pow(0.25f + 0.75f * this.mc.gameSettings.renderDistanceChunks / 32.0f, 0.25);
        final Vec3 worldSkyColor = CustomColorizer.getWorldSkyColor(theWorld.getSkyColor(this.mc.getRenderViewEntity(), n), theWorld, this.mc.getRenderViewEntity(), n);
        final float n3 = (float)worldSkyColor.xCoord;
        final float n4 = (float)worldSkyColor.yCoord;
        final float n5 = (float)worldSkyColor.zCoord;
        final Vec3 worldFogColor = CustomColorizer.getWorldFogColor(theWorld.getFogColor(n), theWorld, this.mc.getRenderViewEntity(), n);
        this.fogColorRed = (float)worldFogColor.xCoord;
        this.fogColorGreen = (float)worldFogColor.yCoord;
        this.fogColorBlue = (float)worldFogColor.zCoord;
        if (this.mc.gameSettings.renderDistanceChunks >= 4) {
            final double n6 = -1.0;
            float n7 = (float)renderViewEntity.getLook(n).dotProduct((MathHelper.sin(theWorld.getCelestialAngleRadians(n)) > 0.0f) ? new Vec3(n6, 0.0, 0.0) : new Vec3(1.0, 0.0, 0.0));
            if (n7 < 0.0f) {
                n7 = 0.0f;
            }
            if (n7 > 0.0f) {
                final float[] calcSunriseSunsetColors = theWorld.provider.calcSunriseSunsetColors(theWorld.getCelestialAngle(n), n);
                if (calcSunriseSunsetColors != null) {
                    final float n8 = n7 * calcSunriseSunsetColors[3];
                    this.fogColorRed = this.fogColorRed * (1.0f - n8) + calcSunriseSunsetColors[0] * n8;
                    this.fogColorGreen = this.fogColorGreen * (1.0f - n8) + calcSunriseSunsetColors[1] * n8;
                    this.fogColorBlue = this.fogColorBlue * (1.0f - n8) + calcSunriseSunsetColors[2] * n8;
                }
            }
        }
        this.fogColorRed += (n3 - this.fogColorRed) * n2;
        this.fogColorGreen += (n4 - this.fogColorGreen) * n2;
        this.fogColorBlue += (n5 - this.fogColorBlue) * n2;
        final float rainStrength = theWorld.getRainStrength(n);
        if (rainStrength > 0.0f) {
            final float n9 = 1.0f - rainStrength * 0.5f;
            final float n10 = 1.0f - rainStrength * 0.4f;
            this.fogColorRed *= n9;
            this.fogColorGreen *= n9;
            this.fogColorBlue *= n10;
        }
        final float thunderStrength = theWorld.getThunderStrength(n);
        if (thunderStrength > 0.0f) {
            final float n11 = 1.0f - thunderStrength * 0.5f;
            this.fogColorRed *= n11;
            this.fogColorGreen *= n11;
            this.fogColorBlue *= n11;
        }
        final Block blockAtEntityViewpoint = ActiveRenderInfo.getBlockAtEntityViewpoint(this.mc.theWorld, renderViewEntity, n);
        if (this.cloudFog) {
            final Vec3 cloudColour = theWorld.getCloudColour(n);
            this.fogColorRed = (float)cloudColour.xCoord;
            this.fogColorGreen = (float)cloudColour.yCoord;
            this.fogColorBlue = (float)cloudColour.zCoord;
        }
        else if (blockAtEntityViewpoint.getMaterial() == Material.water) {
            float n12 = EnchantmentHelper.getRespiration(renderViewEntity) * 0.2f;
            if (renderViewEntity instanceof EntityLivingBase && ((EntityLivingBase)renderViewEntity).isPotionActive(Potion.waterBreathing)) {
                n12 = n12 * 0.3f + 0.6f;
            }
            this.fogColorRed = 0.02f + n12;
            this.fogColorGreen = 0.02f + n12;
            this.fogColorBlue = 0.2f + n12;
            final Vec3 underwaterColor = CustomColorizer.getUnderwaterColor(this.mc.theWorld, this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().posY + 1.0, this.mc.getRenderViewEntity().posZ);
            if (underwaterColor != null) {
                this.fogColorRed = (float)underwaterColor.xCoord;
                this.fogColorGreen = (float)underwaterColor.yCoord;
                this.fogColorBlue = (float)underwaterColor.zCoord;
            }
        }
        else if (blockAtEntityViewpoint.getMaterial() == Material.lava) {
            this.fogColorRed = 0.6f;
            this.fogColorGreen = 0.1f;
            this.fogColorBlue = 0.0f;
        }
        final float n13 = this.fogColor2 + (this.fogColor1 - this.fogColor2) * n;
        this.fogColorRed *= n13;
        this.fogColorGreen *= n13;
        this.fogColorBlue *= n13;
        double n14 = (renderViewEntity.lastTickPosY + (renderViewEntity.posY - renderViewEntity.lastTickPosY) * n) * theWorld.provider.getVoidFogYFactor();
        if (renderViewEntity instanceof EntityLivingBase && ((EntityLivingBase)renderViewEntity).isPotionActive(Potion.blindness)) {
            final int duration = ((EntityLivingBase)renderViewEntity).getActivePotionEffect(Potion.blindness).getDuration();
            if (duration < 20) {
                n14 *= 1.0f - duration / 20.0f;
            }
            else {
                n14 = 0.0;
            }
        }
        if (n14 < 1.0) {
            if (n14 < 0.0) {
                n14 = 0.0;
            }
            final double n15 = n14 * n14;
            this.fogColorRed *= (float)n15;
            this.fogColorGreen *= (float)n15;
            this.fogColorBlue *= (float)n15;
        }
        if (this.bossColorModifier > 0.0f) {
            final float n16 = this.bossColorModifierPrev + (this.bossColorModifier - this.bossColorModifierPrev) * n;
            this.fogColorRed = this.fogColorRed * (1.0f - n16) + this.fogColorRed * 0.7f * n16;
            this.fogColorGreen = this.fogColorGreen * (1.0f - n16) + this.fogColorGreen * 0.6f * n16;
            this.fogColorBlue = this.fogColorBlue * (1.0f - n16) + this.fogColorBlue * 0.6f * n16;
        }
        if (renderViewEntity instanceof EntityLivingBase && ((EntityLivingBase)renderViewEntity).isPotionActive(Potion.nightVision)) {
            final float nightVisionBrightness = this.getNightVisionBrightness((EntityLivingBase)renderViewEntity, n);
            float n17 = 1.0f / this.fogColorRed;
            if (n17 > 1.0f / this.fogColorGreen) {
                n17 = 1.0f / this.fogColorGreen;
            }
            if (n17 > 1.0f / this.fogColorBlue) {
                n17 = 1.0f / this.fogColorBlue;
            }
            this.fogColorRed = this.fogColorRed * (1.0f - nightVisionBrightness) + this.fogColorRed * n17 * nightVisionBrightness;
            this.fogColorGreen = this.fogColorGreen * (1.0f - nightVisionBrightness) + this.fogColorGreen * n17 * nightVisionBrightness;
            this.fogColorBlue = this.fogColorBlue * (1.0f - nightVisionBrightness) + this.fogColorBlue * n17 * nightVisionBrightness;
        }
        if (this.mc.gameSettings.anaglyph) {
            final float fogColorRed = (this.fogColorRed * 30.0f + this.fogColorGreen * 59.0f + this.fogColorBlue * 11.0f) / 100.0f;
            final float fogColorGreen = (this.fogColorRed * 30.0f + this.fogColorGreen * 70.0f) / 100.0f;
            final float fogColorBlue = (this.fogColorRed * 30.0f + this.fogColorBlue * 70.0f) / 100.0f;
            this.fogColorRed = fogColorRed;
            this.fogColorGreen = fogColorGreen;
            this.fogColorBlue = fogColorBlue;
        }
        if (Reflector.EntityViewRenderEvent_FogColors_Constructor.exists()) {
            final Object instance = Reflector.newInstance(Reflector.EntityViewRenderEvent_FogColors_Constructor, this, renderViewEntity, blockAtEntityViewpoint, n, this.fogColorRed, this.fogColorGreen, this.fogColorBlue);
            Reflector.postForgeBusEvent(instance);
            this.fogColorRed = Reflector.getFieldValueFloat(instance, Reflector.EntityViewRenderEvent_FogColors_red, this.fogColorRed);
            this.fogColorGreen = Reflector.getFieldValueFloat(instance, Reflector.EntityViewRenderEvent_FogColors_green, this.fogColorGreen);
            this.fogColorBlue = Reflector.getFieldValueFloat(instance, Reflector.EntityViewRenderEvent_FogColors_blue, this.fogColorBlue);
        }
        GlStateManager.clearColor(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 0.0f);
    }
    
    private void frameInit() {
        if (!this.initialized) {
            if (Config.getBitsOs() == 64 && Config.getBitsJre() == 32) {
                Config.setNotify64BitJava(true);
            }
            this.initialized = true;
        }
        Config.isActing();
        final WorldClient theWorld = this.mc.theWorld;
        if (theWorld != null) {
            if (Config.getNewRelease() != null) {
                this.mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("A new §eOptiFine§f version is available: §e" + ("HD_U".replace("HD_U", "HD Ultra").replace("L", "Light") + " " + Config.getNewRelease()) + "§f"));
                Config.setNewRelease(null);
            }
            if (Config.isNotify64BitJava()) {
                Config.setNotify64BitJava(false);
                this.mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("You can install §e64-bit Java§f to increase performance"));
            }
        }
        if (this.mc.currentScreen instanceof GuiMainMenu) {
            this.updateMainMenu((GuiMainMenu)this.mc.currentScreen);
        }
        if (this.updatedWorld != theWorld) {
            RandomMobs.worldChanged(this.updatedWorld, theWorld);
            this.lastServerTime = 0L;
            this.lastServerTicks = 0;
            this.updatedWorld = theWorld;
        }
    }
    
    private void hurtCameraEffect(final float n) {
        if (Asyncware.instance.moduleManager.getModuleByName("NoHurtCam").isToggled()) {
            return;
        }
        if (this.mc.getRenderViewEntity() instanceof EntityLivingBase) {
            final EntityLivingBase entityLivingBase = (EntityLivingBase)this.mc.getRenderViewEntity();
            final float n2 = entityLivingBase.hurtTime - n;
            if (entityLivingBase.getHealth() <= 0.0f) {
                GlStateManager.rotate(40.0f - 8000.0f / (entityLivingBase.deathTime + n + 200.0f), 0.0f, 0.0f, 1.0f);
            }
            if (n2 < 0.0f) {
                return;
            }
            final float n3 = n2 / entityLivingBase.maxHurtTime;
            final float sin = MathHelper.sin(n3 * n3 * n3 * n3 * 3.1415927f);
            final float attackedAtYaw = entityLivingBase.attackedAtYaw;
            GlStateManager.rotate(-attackedAtYaw, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(-sin * 14.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(attackedAtYaw, 0.0f, 1.0f, 0.0f);
        }
    }
    
    private FloatBuffer setFogColorBuffer(final float n, final float n2, final float n3, final float n4) {
        this.fogColorBuffer.clear();
        this.fogColorBuffer.put(n).put(n2).put(n3).put(n4);
        this.fogColorBuffer.flip();
        return this.fogColorBuffer;
    }
    
    private void updateLightmap(final float n) {
        if (this.lightmapUpdateNeeded) {
            this.mc.mcProfiler.startSection("lightTex");
            final WorldClient theWorld = this.mc.theWorld;
            if (theWorld != null) {
                if (CustomColorizer.updateLightmap(theWorld, this.torchFlickerX, this.lightmapColors, this.mc.thePlayer.isPotionActive(Potion.nightVision))) {
                    this.lightmapTexture.updateDynamicTexture();
                    this.lightmapUpdateNeeded = false;
                    this.mc.mcProfiler.endSection();
                    return;
                }
                final float sunBrightness = theWorld.getSunBrightness(1.0f);
                final float n2 = sunBrightness * 0.95f + 0.05f;
                while (true) {
                    float n3 = theWorld.provider.getLightBrightnessTable()[0] * n2;
                    final float n4 = theWorld.provider.getLightBrightnessTable()[0] * (this.torchFlickerX * 0.1f + 1.5f);
                    if (theWorld.getLastLightningBolt() > 0) {
                        n3 = theWorld.provider.getLightBrightnessTable()[0];
                    }
                    final float n5 = n3 * (sunBrightness * 0.65f + 0.35f);
                    final float n6 = n3 * (sunBrightness * 0.65f + 0.35f);
                    final float n7 = n4 * ((n4 * 0.6f + 0.4f) * 0.6f + 0.4f);
                    final float n8 = n4 * (n4 * n4 * 0.6f + 0.4f);
                    final float n9 = n5 + n4;
                    final float n10 = n6 + n7;
                    final float n11 = n3 + n8;
                    float n12 = n9 * 0.96f + 0.03f;
                    float n13 = n10 * 0.96f + 0.03f;
                    float n14 = n11 * 0.96f + 0.03f;
                    if (this.bossColorModifier > 0.0f) {
                        final float n15 = this.bossColorModifierPrev + (this.bossColorModifier - this.bossColorModifierPrev) * n;
                        n12 = n12 * (1.0f - n15) + n12 * 0.7f * n15;
                        n13 = n13 * (1.0f - n15) + n13 * 0.6f * n15;
                        n14 = n14 * (1.0f - n15) + n14 * 0.6f * n15;
                    }
                    if (theWorld.provider.getDimensionId() == 1) {
                        n12 = 0.22f + n4 * 0.75f;
                        n13 = 0.28f + n7 * 0.75f;
                        n14 = 0.25f + n8 * 0.75f;
                    }
                    if (this.mc.thePlayer.isPotionActive(Potion.nightVision)) {
                        final float nightVisionBrightness = this.getNightVisionBrightness(this.mc.thePlayer, n);
                        float n16 = 1.0f / n12;
                        if (n16 > 1.0f / n13) {
                            n16 = 1.0f / n13;
                        }
                        if (n16 > 1.0f / n14) {
                            n16 = 1.0f / n14;
                        }
                        n12 = n12 * (1.0f - nightVisionBrightness) + n12 * n16 * nightVisionBrightness;
                        n13 = n13 * (1.0f - nightVisionBrightness) + n13 * n16 * nightVisionBrightness;
                        n14 = n14 * (1.0f - nightVisionBrightness) + n14 * n16 * nightVisionBrightness;
                    }
                    if (n12 > 1.0f) {
                        n12 = 1.0f;
                    }
                    if (n13 > 1.0f) {
                        n13 = 1.0f;
                    }
                    if (n14 > 1.0f) {
                        n14 = 1.0f;
                    }
                    final float gammaSetting = this.mc.gameSettings.gammaSetting;
                    final float n17 = 1.0f - n12;
                    final float n18 = 1.0f - n13;
                    final float n19 = 1.0f - n14;
                    final float n20 = 1.0f - n17 * n17 * n17 * n17;
                    final float n21 = 1.0f - n18 * n18 * n18 * n18;
                    final float n22 = 1.0f - n19 * n19 * n19 * n19;
                    final float n23 = n12 * (1.0f - gammaSetting) + n20 * gammaSetting;
                    final float n24 = n13 * (1.0f - gammaSetting) + n21 * gammaSetting;
                    final float n25 = n14 * (1.0f - gammaSetting) + n22 * gammaSetting;
                    float n26 = n23 * 0.96f + 0.03f;
                    float n27 = n24 * 0.96f + 0.03f;
                    float n28 = n25 * 0.96f + 0.03f;
                    if (n26 > 1.0f) {
                        n26 = 1.0f;
                    }
                    if (n27 > 1.0f) {
                        n27 = 1.0f;
                    }
                    if (n28 > 1.0f) {
                        n28 = 1.0f;
                    }
                    if (n26 < 0.0f) {
                        n26 = 0.0f;
                    }
                    if (n27 < 0.0f) {
                        n27 = 0.0f;
                    }
                    if (n28 < 0.0f) {
                        n28 = 0.0f;
                    }
                    this.lightmapColors[0] = (0xFF000000 | (int)(n26 * 255.0f) << 16 | (int)(n27 * 255.0f) << 8 | (int)(n28 * 255.0f));
                    int n29 = 0;
                    ++n29;
                }
            }
        }
    }
    
    private void updateFovModifierHand() {
        float fovModifier = 1.0f;
        if (this.mc.getRenderViewEntity() instanceof AbstractClientPlayer) {
            fovModifier = ((AbstractClientPlayer)this.mc.getRenderViewEntity()).getFovModifier();
        }
        this.fovModifierHandPrev = this.fovModifierHand;
        this.fovModifierHand += (fovModifier - this.fovModifierHand) * 0.5f;
        if (this.fovModifierHand > 1.5f) {
            this.fovModifierHand = 1.5f;
        }
        if (this.fovModifierHand < 0.1f) {
            this.fovModifierHand = 0.1f;
        }
    }
    
    private void renderCloudsCheck(final RenderGlobal renderGlobal, final float n, final int n2) {
        if (this.mc.gameSettings.renderDistanceChunks >= 4 && !Config.isCloudsOff()) {
            this.mc.mcProfiler.endStartSection("clouds");
            GlStateManager.matrixMode(5889);
            Project.gluPerspective(this.getFOVModifier(n, true), this.mc.displayWidth / (float)this.mc.displayHeight, 0.05f, this.clipDistance * 4.0f);
            GlStateManager.matrixMode(5888);
            this.setupFog(0, n);
            renderGlobal.renderClouds(n, n2);
            GlStateManager.matrixMode(5889);
            Project.gluPerspective(this.getFOVModifier(n, true), this.mc.displayWidth / (float)this.mc.displayHeight, 0.05f, this.clipDistance);
            GlStateManager.matrixMode(5888);
        }
    }
    
    public void func_181022_b() {
        if (this.theShaderGroup != null) {
            this.theShaderGroup.deleteShaderGroup();
        }
        this.theShaderGroup = null;
        this.shaderIndex = EntityRenderer.shaderCount;
    }
    
    public void setupCameraTransform(final float n, final int n2) {
        this.farPlaneDistance = (float)(this.mc.gameSettings.renderDistanceChunks * 16);
        if (Config.isFogFancy()) {
            this.farPlaneDistance *= 0.95f;
        }
        if (Config.isFogFast()) {
            this.farPlaneDistance *= 0.83f;
        }
        GlStateManager.matrixMode(5889);
        final float n3 = 0.07f;
        if (this.mc.gameSettings.anaglyph) {
            GlStateManager.translate(-(n2 * 2 - 1) * n3, 0.0f, 0.0f);
        }
        this.clipDistance = this.farPlaneDistance * 2.0f;
        if (this.clipDistance < 173.0f) {
            this.clipDistance = 173.0f;
        }
        if (this.mc.theWorld.provider.getDimensionId() == 1) {
            this.clipDistance = 256.0f;
        }
        if (this.cameraZoom != 1.0) {
            GlStateManager.translate((float)this.cameraYaw, (float)(-this.cameraPitch), 0.0f);
            GlStateManager.scale(this.cameraZoom, this.cameraZoom, 1.0);
        }
        Project.gluPerspective(this.getFOVModifier(n, true), this.mc.displayWidth / (float)this.mc.displayHeight, 0.05f, this.clipDistance);
        GlStateManager.matrixMode(5888);
        if (this.mc.gameSettings.anaglyph) {
            GlStateManager.translate((n2 * 2 - 1) * 0.1f, 0.0f, 0.0f);
        }
        this.hurtCameraEffect(n);
        if (this.mc.gameSettings.viewBobbing) {
            this.setupViewBobbing(n);
        }
        final float n4 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * n;
        if (n4 > 0.0f) {
            if (this.mc.thePlayer.isPotionActive(Potion.confusion)) {}
            final float n5 = 5.0f / (n4 * n4 + 5.0f) - n4 * 0.04f;
            final float n6 = n5 * n5;
            GlStateManager.rotate((this.rendererUpdateCount + n) * 7, 0.0f, 1.0f, 1.0f);
            GlStateManager.scale(1.0f / n6, 1.0f, 1.0f);
            GlStateManager.rotate(-(this.rendererUpdateCount + n) * 7, 0.0f, 1.0f, 1.0f);
        }
        this.orientCamera(n);
        if (this.debugView) {
            switch (this.debugViewDirection) {
                case 0: {
                    GlStateManager.rotate(90.0f, 0.0f, 1.0f, 0.0f);
                    break;
                }
                case 1: {
                    GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                    break;
                }
                case 2: {
                    GlStateManager.rotate(-90.0f, 0.0f, 1.0f, 0.0f);
                    break;
                }
                case 3: {
                    GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                    break;
                }
                case 4: {
                    GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
                    break;
                }
            }
        }
    }
    
    static Minecraft access$000(final EntityRenderer entityRenderer) {
        return entityRenderer.mc;
    }
    
    static {
        logger = LogManager.getLogger();
        locationRainPng = new ResourceLocation("textures/environment/rain.png");
        locationSnowPng = new ResourceLocation("textures/environment/snow.png");
        shaderResourceLocations = new ResourceLocation[] { new ResourceLocation("shaders/post/notch.json"), new ResourceLocation("shaders/post/fxaa.json"), new ResourceLocation("shaders/post/art.json"), new ResourceLocation("shaders/post/bumpy.json"), new ResourceLocation("shaders/post/blobs2.json"), new ResourceLocation("shaders/post/pencil.json"), new ResourceLocation("shaders/post/color_convolve.json"), new ResourceLocation("shaders/post/deconverge.json"), new ResourceLocation("shaders/post/flip.json"), new ResourceLocation("shaders/post/invert.json"), new ResourceLocation("shaders/post/ntsc.json"), new ResourceLocation("shaders/post/outline.json"), new ResourceLocation("shaders/post/phosphor.json"), new ResourceLocation("shaders/post/scan_pincushion.json"), new ResourceLocation("shaders/post/sobel.json"), new ResourceLocation("shaders/post/bits.json"), new ResourceLocation("shaders/post/desaturate.json"), new ResourceLocation("shaders/post/green.json"), new ResourceLocation("shaders/post/blur.json"), new ResourceLocation("shaders/post/wobble.json"), new ResourceLocation("shaders/post/blobs.json"), new ResourceLocation("shaders/post/antialias.json"), new ResourceLocation("shaders/post/creeper.json"), new ResourceLocation("shaders/post/spider.json") };
        shaderCount = EntityRenderer.shaderResourceLocations.length;
    }
    
    private void frameFinish() {
        if (this.mc.theWorld != null) {
            final long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis > this.lastErrorCheckTimeMs + 10000L) {
                this.lastErrorCheckTimeMs = currentTimeMillis;
                final int glGetError = GL11.glGetError();
                if (glGetError != 0) {
                    this.mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("§eOpenGL Error§f: " + glGetError + " (" + GLU.gluErrorString(glGetError) + ")"));
                }
            }
        }
    }
    
    private float getFOVModifier(final float n, final boolean b) {
        if (this.debugView) {
            return 90.0f;
        }
        final Entity renderViewEntity = this.mc.getRenderViewEntity();
        float n2 = 70.0f;
        if (b) {
            n2 = this.mc.gameSettings.fovSetting * (this.fovModifierHandPrev + (this.fovModifierHand - this.fovModifierHandPrev) * n);
        }
        if (this.mc.currentScreen == null) {
            final GameSettings gameSettings = this.mc.gameSettings;
            GameSettings.isKeyDown(this.mc.gameSettings.ofKeyBindZoom);
        }
        if (Config.zoomMode) {
            Config.zoomMode = false;
            this.mc.gameSettings.smoothCamera = false;
            this.mouseFilterXAxis = new MouseFilter();
            this.mouseFilterYAxis = new MouseFilter();
            this.mc.renderGlobal.displayListEntitiesDirty = true;
        }
        if (renderViewEntity instanceof EntityLivingBase && ((EntityLivingBase)renderViewEntity).getHealth() <= 0.0f) {
            n2 /= (1.0f - 500.0f / (((EntityLivingBase)renderViewEntity).deathTime + n + 500.0f)) * 2.0f + 1.0f;
        }
        if (ActiveRenderInfo.getBlockAtEntityViewpoint(this.mc.theWorld, renderViewEntity, n).getMaterial() == Material.water) {
            n2 = n2 * 60.0f / 70.0f;
        }
        return n2;
    }
    
    public EntityRenderer(final Minecraft mc, final IResourceManager resourceManager) {
        this.random = new Random();
        this.mouseFilterXAxis = new MouseFilter();
        this.mouseFilterYAxis = new MouseFilter();
        this.thirdPersonDistance = 4.0f;
        this.thirdPersonDistanceTemp = 4.0f;
        this.renderHand = true;
        this.drawBlockOutline = true;
        this.prevFrameTime = Minecraft.getSystemTime();
        this.rainXCoords = new float[1024];
        this.rainYCoords = new float[1024];
        this.fogColorBuffer = GLAllocation.createDirectFloatBuffer(16);
        this.debugViewDirection = 0;
        this.debugView = false;
        this.cameraZoom = 1.0;
        this.initialized = false;
        this.updatedWorld = null;
        this.showDebugInfo = false;
        this.fogStandard = false;
        this.clipDistance = 128.0f;
        this.lastServerTime = 0L;
        this.lastServerTicks = 0;
        this.serverWaitTime = 0;
        this.serverWaitTimeCurrent = 0;
        this.avgServerTimeDiff = 0.0f;
        this.avgServerTickDiff = 0.0f;
        this.lastErrorCheckTimeMs = 0L;
        this.shaderIndex = EntityRenderer.shaderCount;
        this.useShader = false;
        this.frameCount = 0;
        this.mc = mc;
        this.resourceManager = resourceManager;
        this.itemRenderer = mc.getItemRenderer();
        this.theMapItemRenderer = new MapItemRenderer(mc.getTextureManager());
        this.lightmapTexture = new DynamicTexture(16, 16);
        this.locationLightMap = mc.getTextureManager().getDynamicTextureLocation("lightMap", this.lightmapTexture);
        this.lightmapColors = this.lightmapTexture.getTextureData();
        this.theShaderGroup = null;
        while (true) {
            final float n = -16;
            final float n2 = -16;
            final float sqrt_float = MathHelper.sqrt_float(n * n + n2 * n2);
            this.rainXCoords[0] = -n2 / sqrt_float;
            this.rainYCoords[0] = n / sqrt_float;
            int n3 = 0;
            ++n3;
        }
    }
    
    public boolean isShaderActive() {
        return OpenGlHelper.shadersSupported && this.theShaderGroup != null;
    }
    
    public void updateRenderer() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: ifeq            9
        //     6: invokestatic    net/minecraft/client/shader/ShaderLinkHelper.getStaticShaderLinkHelper:()Lnet/minecraft/client/shader/ShaderLinkHelper;
        //     9: aload_0        
        //    10: invokespecial   net/minecraft/client/renderer/EntityRenderer.updateFovModifierHand:()V
        //    13: aload_0        
        //    14: invokespecial   net/minecraft/client/renderer/EntityRenderer.updateTorchFlicker:()V
        //    17: aload_0        
        //    18: aload_0        
        //    19: getfield        net/minecraft/client/renderer/EntityRenderer.fogColor1:F
        //    22: putfield        net/minecraft/client/renderer/EntityRenderer.fogColor2:F
        //    25: aload_0        
        //    26: aload_0        
        //    27: getfield        net/minecraft/client/renderer/EntityRenderer.thirdPersonDistance:F
        //    30: putfield        net/minecraft/client/renderer/EntityRenderer.thirdPersonDistanceTemp:F
        //    33: aload_0        
        //    34: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //    37: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //    40: getfield        net/minecraft/client/settings/GameSettings.smoothCamera:Z
        //    43: ifeq            133
        //    46: aload_0        
        //    47: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //    50: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //    53: getfield        net/minecraft/client/settings/GameSettings.mouseSensitivity:F
        //    56: ldc_w           0.6
        //    59: fmul           
        //    60: ldc_w           0.2
        //    63: fadd           
        //    64: fstore_1       
        //    65: fload_1        
        //    66: fload_1        
        //    67: fmul           
        //    68: fload_1        
        //    69: fmul           
        //    70: ldc_w           8.0
        //    73: fmul           
        //    74: fstore_2       
        //    75: aload_0        
        //    76: aload_0        
        //    77: getfield        net/minecraft/client/renderer/EntityRenderer.mouseFilterXAxis:Lnet/minecraft/util/MouseFilter;
        //    80: aload_0        
        //    81: getfield        net/minecraft/client/renderer/EntityRenderer.smoothCamYaw:F
        //    84: ldc_w           0.05
        //    87: fload_2        
        //    88: fmul           
        //    89: invokevirtual   net/minecraft/util/MouseFilter.smooth:(FF)F
        //    92: putfield        net/minecraft/client/renderer/EntityRenderer.smoothCamFilterX:F
        //    95: aload_0        
        //    96: aload_0        
        //    97: getfield        net/minecraft/client/renderer/EntityRenderer.mouseFilterYAxis:Lnet/minecraft/util/MouseFilter;
        //   100: aload_0        
        //   101: getfield        net/minecraft/client/renderer/EntityRenderer.smoothCamPitch:F
        //   104: ldc_w           0.05
        //   107: fload_2        
        //   108: fmul           
        //   109: invokevirtual   net/minecraft/util/MouseFilter.smooth:(FF)F
        //   112: putfield        net/minecraft/client/renderer/EntityRenderer.smoothCamFilterY:F
        //   115: aload_0        
        //   116: fconst_0       
        //   117: putfield        net/minecraft/client/renderer/EntityRenderer.smoothCamPartialTicks:F
        //   120: aload_0        
        //   121: fconst_0       
        //   122: putfield        net/minecraft/client/renderer/EntityRenderer.smoothCamYaw:F
        //   125: aload_0        
        //   126: fconst_0       
        //   127: putfield        net/minecraft/client/renderer/EntityRenderer.smoothCamPitch:F
        //   130: goto            157
        //   133: aload_0        
        //   134: fconst_0       
        //   135: putfield        net/minecraft/client/renderer/EntityRenderer.smoothCamFilterX:F
        //   138: aload_0        
        //   139: fconst_0       
        //   140: putfield        net/minecraft/client/renderer/EntityRenderer.smoothCamFilterY:F
        //   143: aload_0        
        //   144: getfield        net/minecraft/client/renderer/EntityRenderer.mouseFilterXAxis:Lnet/minecraft/util/MouseFilter;
        //   147: invokevirtual   net/minecraft/util/MouseFilter.reset:()V
        //   150: aload_0        
        //   151: getfield        net/minecraft/client/renderer/EntityRenderer.mouseFilterYAxis:Lnet/minecraft/util/MouseFilter;
        //   154: invokevirtual   net/minecraft/util/MouseFilter.reset:()V
        //   157: aload_0        
        //   158: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   161: invokevirtual   net/minecraft/client/Minecraft.getRenderViewEntity:()Lnet/minecraft/entity/Entity;
        //   164: ifnonnull       181
        //   167: aload_0        
        //   168: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   171: aload_0        
        //   172: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   175: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   178: invokevirtual   net/minecraft/client/Minecraft.setRenderViewEntity:(Lnet/minecraft/entity/Entity;)V
        //   181: aload_0        
        //   182: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   185: getfield        net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   188: new             Lnet/minecraft/util/BlockPos;
        //   191: dup            
        //   192: aload_0        
        //   193: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   196: invokevirtual   net/minecraft/client/Minecraft.getRenderViewEntity:()Lnet/minecraft/entity/Entity;
        //   199: invokespecial   net/minecraft/util/BlockPos.<init>:(Lnet/minecraft/entity/Entity;)V
        //   202: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getLightBrightness:(Lnet/minecraft/util/BlockPos;)F
        //   205: fstore_1       
        //   206: aload_0        
        //   207: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   210: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   213: getfield        net/minecraft/client/settings/GameSettings.renderDistanceChunks:I
        //   216: i2f            
        //   217: ldc             32.0
        //   219: fdiv           
        //   220: fstore_2       
        //   221: fload_1        
        //   222: fconst_1       
        //   223: fload_2        
        //   224: fsub           
        //   225: fmul           
        //   226: fload_2        
        //   227: fadd           
        //   228: fstore_3       
        //   229: aload_0        
        //   230: dup            
        //   231: getfield        net/minecraft/client/renderer/EntityRenderer.fogColor1:F
        //   234: fload_3        
        //   235: aload_0        
        //   236: getfield        net/minecraft/client/renderer/EntityRenderer.fogColor1:F
        //   239: fsub           
        //   240: ldc_w           0.1
        //   243: fmul           
        //   244: fadd           
        //   245: putfield        net/minecraft/client/renderer/EntityRenderer.fogColor1:F
        //   248: aload_0        
        //   249: dup            
        //   250: getfield        net/minecraft/client/renderer/EntityRenderer.rendererUpdateCount:I
        //   253: iconst_1       
        //   254: iadd           
        //   255: putfield        net/minecraft/client/renderer/EntityRenderer.rendererUpdateCount:I
        //   258: aload_0        
        //   259: getfield        net/minecraft/client/renderer/EntityRenderer.itemRenderer:Lnet/minecraft/client/renderer/ItemRenderer;
        //   262: invokevirtual   net/minecraft/client/renderer/ItemRenderer.updateEquippedItem:()V
        //   265: aload_0        
        //   266: invokespecial   net/minecraft/client/renderer/EntityRenderer.addRainParticles:()V
        //   269: aload_0        
        //   270: aload_0        
        //   271: getfield        net/minecraft/client/renderer/EntityRenderer.bossColorModifier:F
        //   274: putfield        net/minecraft/client/renderer/EntityRenderer.bossColorModifierPrev:F
        //   277: getstatic       net/minecraft/entity/boss/BossStatus.hasColorModifier:Z
        //   280: ifeq            316
        //   283: aload_0        
        //   284: dup            
        //   285: getfield        net/minecraft/client/renderer/EntityRenderer.bossColorModifier:F
        //   288: ldc_w           0.05
        //   291: fadd           
        //   292: putfield        net/minecraft/client/renderer/EntityRenderer.bossColorModifier:F
        //   295: aload_0        
        //   296: getfield        net/minecraft/client/renderer/EntityRenderer.bossColorModifier:F
        //   299: fconst_1       
        //   300: fcmpl          
        //   301: ifle            309
        //   304: aload_0        
        //   305: fconst_1       
        //   306: putfield        net/minecraft/client/renderer/EntityRenderer.bossColorModifier:F
        //   309: iconst_0       
        //   310: putstatic       net/minecraft/entity/boss/BossStatus.hasColorModifier:Z
        //   313: goto            337
        //   316: aload_0        
        //   317: getfield        net/minecraft/client/renderer/EntityRenderer.bossColorModifier:F
        //   320: fconst_0       
        //   321: fcmpl          
        //   322: ifle            337
        //   325: aload_0        
        //   326: dup            
        //   327: getfield        net/minecraft/client/renderer/EntityRenderer.bossColorModifier:F
        //   330: ldc_w           0.0125
        //   333: fsub           
        //   334: putfield        net/minecraft/client/renderer/EntityRenderer.bossColorModifier:F
        //   337: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0009 (coming from #0006).
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
    
    public void enableLightmap() {
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.matrixMode(5890);
        final float n = 0.00390625f;
        GlStateManager.scale(n, n, n);
        GlStateManager.translate(8.0f, 8.0f, 8.0f);
        GlStateManager.matrixMode(5888);
        this.mc.getTextureManager().bindTexture(this.locationLightMap);
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glTexParameteri(3553, 10240, 9729);
        GL11.glTexParameteri(3553, 10242, 10496);
        GL11.glTexParameteri(3553, 10243, 10496);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
    
    public void renderStreamIndicator(final float n) {
        this.setupOverlayRendering();
        this.mc.ingameGUI.renderStreamIndicator(new ScaledResolution(this.mc));
    }
    
    private void renderHand(final float n, final int n2) {
        if (!this.debugView) {
            GlStateManager.matrixMode(5889);
            final float n3 = 0.07f;
            if (this.mc.gameSettings.anaglyph) {
                GlStateManager.translate(-(n2 * 2 - 1) * n3, 0.0f, 0.0f);
            }
            Project.gluPerspective(this.getFOVModifier(n, false), this.mc.displayWidth / (float)this.mc.displayHeight, 0.05f, this.farPlaneDistance * 2.0f);
            GlStateManager.matrixMode(5888);
            if (this.mc.gameSettings.anaglyph) {
                GlStateManager.translate((n2 * 2 - 1) * 0.1f, 0.0f, 0.0f);
            }
            this.hurtCameraEffect(n);
            if (this.mc.gameSettings.viewBobbing) {
                this.setupViewBobbing(n);
            }
            final boolean b = this.mc.getRenderViewEntity() instanceof EntityLivingBase && ((EntityLivingBase)this.mc.getRenderViewEntity()).isPlayerSleeping();
            if (this.mc.gameSettings.thirdPersonView == 0 && !b && !this.mc.gameSettings.hideGUI && !this.mc.playerController.isSpectator()) {
                this.enableLightmap();
                this.itemRenderer.renderItemInFirstPerson(n);
                this.disableLightmap();
            }
            if (this.mc.gameSettings.thirdPersonView == 0 && !b) {
                this.itemRenderer.renderOverlays(n);
                this.hurtCameraEffect(n);
            }
            if (this.mc.gameSettings.viewBobbing) {
                this.setupViewBobbing(n);
            }
        }
    }
    
    @Override
    public void onResourceManagerReload(final IResourceManager resourceManager) {
        if (this.theShaderGroup != null) {
            this.theShaderGroup.deleteShaderGroup();
        }
        this.theShaderGroup = null;
        if (this.shaderIndex != EntityRenderer.shaderCount) {
            this.loadShader(EntityRenderer.shaderResourceLocations[this.shaderIndex]);
        }
        else {
            this.loadEntityShader(this.mc.getRenderViewEntity());
        }
    }
    
    private void addRainParticles() {
        float rainStrength = this.mc.theWorld.getRainStrength(1.0f);
        if (!Config.isRainFancy()) {
            rainStrength /= 2.0f;
        }
        if (rainStrength != 0.0f && Config.isRainSplash()) {
            this.random.setSeed(this.rendererUpdateCount * 312987231L);
            final Entity renderViewEntity = this.mc.getRenderViewEntity();
            final WorldClient theWorld = this.mc.theWorld;
            final BlockPos blockPos = new BlockPos(renderViewEntity);
            final int n = (int)(100.0f * rainStrength * rainStrength);
            if (this.mc.gameSettings.particleSetting != 1) {
                if (this.mc.gameSettings.particleSetting == 2) {}
            }
        }
    }
    
    private void orientCamera(final float n) {
        final Entity renderViewEntity = this.mc.getRenderViewEntity();
        float eyeHeight = renderViewEntity.getEyeHeight();
        final double n2 = renderViewEntity.prevPosX + (renderViewEntity.posX - renderViewEntity.prevPosX) * n;
        final double n3 = renderViewEntity.prevPosY + (renderViewEntity.posY - renderViewEntity.prevPosY) * n + eyeHeight;
        final double n4 = renderViewEntity.prevPosZ + (renderViewEntity.posZ - renderViewEntity.prevPosZ) * n;
        if (renderViewEntity instanceof EntityLivingBase && ((EntityLivingBase)renderViewEntity).isPlayerSleeping()) {
            ++eyeHeight;
            GlStateManager.translate(0.0f, 0.3f, 0.0f);
            if (!this.mc.gameSettings.debugCamEnable) {
                final BlockPos blockPos = new BlockPos(renderViewEntity);
                final IBlockState blockState = this.mc.theWorld.getBlockState(blockPos);
                final Block block = blockState.getBlock();
                if (Reflector.ForgeHooksClient_orientBedCamera.exists()) {
                    Reflector.callVoid(Reflector.ForgeHooksClient_orientBedCamera, this.mc.theWorld, blockPos, blockState, renderViewEntity);
                }
                else if (block == Blocks.bed) {
                    GlStateManager.rotate((float)(((EnumFacing)blockState.getValue(BlockBed.FACING)).getHorizontalIndex() * 90), 0.0f, 1.0f, 0.0f);
                }
                GlStateManager.rotate(renderViewEntity.prevRotationYaw + (renderViewEntity.rotationYaw - renderViewEntity.prevRotationYaw) * n + 180.0f, 0.0f, -1.0f, 0.0f);
                GlStateManager.rotate(renderViewEntity.prevRotationPitch + (renderViewEntity.rotationPitch - renderViewEntity.prevRotationPitch) * n, -1.0f, 0.0f, 0.0f);
            }
        }
        else if (this.mc.gameSettings.thirdPersonView > 0) {
            double n5 = this.thirdPersonDistanceTemp + (this.thirdPersonDistance - this.thirdPersonDistanceTemp) * n;
            if (this.mc.gameSettings.debugCamEnable) {
                GlStateManager.translate(0.0f, 0.0f, (float)(-n5));
            }
            else {
                final float rotationYaw = renderViewEntity.rotationYaw;
                float rotationPitch = renderViewEntity.rotationPitch;
                if (this.mc.gameSettings.thirdPersonView == 2) {
                    rotationPitch += 180.0f;
                }
                final double n6 = -MathHelper.sin(rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(rotationPitch / 180.0f * 3.1415927f) * n5;
                final double n7 = MathHelper.cos(rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(rotationPitch / 180.0f * 3.1415927f) * n5;
                final double n8 = -MathHelper.sin(rotationPitch / 180.0f * 3.1415927f) * n5;
                while (true) {
                    final float n9 = -1;
                    final float n10 = -1;
                    final float n11 = -1;
                    final float n12 = n9 * 0.1f;
                    final float n13 = n10 * 0.1f;
                    final float n14 = n11 * 0.1f;
                    final MovingObjectPosition rayTraceBlocks = this.mc.theWorld.rayTraceBlocks(new Vec3(n2 + n12, n3 + n13, n4 + n14), new Vec3(n2 - n6 + n12 + n14, n3 - n8 + n13, n4 - n7 + n14));
                    if (rayTraceBlocks != null) {
                        final double distanceTo = rayTraceBlocks.hitVec.distanceTo(new Vec3(n2, n3, n4));
                        if (distanceTo < n5) {
                            n5 = distanceTo;
                        }
                    }
                    int n15 = 0;
                    ++n15;
                }
            }
        }
        else {
            GlStateManager.translate(0.0f, 0.0f, -0.1f);
        }
        if (!this.mc.gameSettings.debugCamEnable) {
            GlStateManager.rotate(renderViewEntity.prevRotationPitch + (renderViewEntity.rotationPitch - renderViewEntity.prevRotationPitch) * n, 1.0f, 0.0f, 0.0f);
            if (renderViewEntity instanceof EntityAnimal) {
                final EntityAnimal entityAnimal = (EntityAnimal)renderViewEntity;
                GlStateManager.rotate(entityAnimal.prevRotationYawHead + (entityAnimal.rotationYawHead - entityAnimal.prevRotationYawHead) * n + 180.0f, 0.0f, 1.0f, 0.0f);
            }
            else {
                GlStateManager.rotate(renderViewEntity.prevRotationYaw + (renderViewEntity.rotationYaw - renderViewEntity.prevRotationYaw) * n + 180.0f, 0.0f, 1.0f, 0.0f);
            }
        }
        GlStateManager.translate(0.0f, -eyeHeight, 0.0f);
        this.cloudFog = this.mc.renderGlobal.hasCloudFog(renderViewEntity.prevPosX + (renderViewEntity.posX - renderViewEntity.prevPosX) * n, renderViewEntity.prevPosY + (renderViewEntity.posY - renderViewEntity.prevPosY) * n + eyeHeight, renderViewEntity.prevPosZ + (renderViewEntity.posZ - renderViewEntity.prevPosZ) * n, n);
    }
    
    public void loadShader(final ResourceLocation resourceLocation) {
        (this.theShaderGroup = new ShaderGroup(this.mc.getTextureManager(), this.resourceManager, this.mc.getFramebuffer(), resourceLocation)).createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
        this.useShader = true;
    }
    
    private void waitForServerThread() {
        this.serverWaitTimeCurrent = 0;
        if (Config.isSmoothWorld() && Config.isSingleProcessor()) {
            if (this.mc.isIntegratedServerRunning()) {
                final IntegratedServer integratedServer = this.mc.getIntegratedServer();
                if (integratedServer != null) {
                    if (!this.mc.isGamePaused() && !(this.mc.currentScreen instanceof GuiDownloadTerrain)) {
                        if (this.serverWaitTime > 0) {
                            Lagometer.timerServer.start();
                            Config.sleep(this.serverWaitTime);
                            Lagometer.timerServer.end();
                            this.serverWaitTimeCurrent = this.serverWaitTime;
                        }
                        final long lastServerTime = System.nanoTime() / 1000000L;
                        if (this.lastServerTime != 0L && this.lastServerTicks != 0) {
                            long n = lastServerTime - this.lastServerTime;
                            if (n < 0L) {
                                this.lastServerTime = lastServerTime;
                                n = 0L;
                            }
                            if (n >= 50L) {
                                this.lastServerTime = lastServerTime;
                                final int tickCounter = integratedServer.getTickCounter();
                                final int n2 = tickCounter - this.lastServerTicks;
                                if (this.serverWaitTime < 100) {
                                    this.serverWaitTime += 2;
                                }
                                this.lastServerTicks = tickCounter;
                            }
                        }
                        else {
                            this.lastServerTime = lastServerTime;
                            this.lastServerTicks = integratedServer.getTickCounter();
                            this.avgServerTickDiff = 1.0f;
                            this.avgServerTimeDiff = 50.0f;
                        }
                    }
                    else {
                        if (this.mc.currentScreen instanceof GuiDownloadTerrain) {
                            Config.sleep(20L);
                        }
                        this.lastServerTime = 0L;
                        this.lastServerTicks = 0;
                    }
                }
            }
        }
        else {
            this.lastServerTime = 0L;
            this.lastServerTicks = 0;
        }
    }
    
    public void renderWorld(final float n, final long n2) {
        this.updateLightmap(n);
        if (this.mc.getRenderViewEntity() == null) {
            this.mc.setRenderViewEntity(this.mc.thePlayer);
        }
        this.getMouseOver(n);
        GlStateManager.alphaFunc(516, 0.1f);
        this.mc.mcProfiler.startSection("center");
        if (this.mc.gameSettings.anaglyph) {
            EntityRenderer.anaglyphField = 0;
            GlStateManager.colorMask(false, true, true, false);
            this.renderWorldPass(0, n, n2);
            EntityRenderer.anaglyphField = 1;
            GlStateManager.colorMask(true, false, false, false);
            this.renderWorldPass(1, n, n2);
            GlStateManager.colorMask(true, true, true, false);
        }
        else {
            this.renderWorldPass(2, n, n2);
        }
        this.mc.mcProfiler.endSection();
    }
    
    private float getNightVisionBrightness(final EntityLivingBase entityLivingBase, final float n) {
        final int duration = entityLivingBase.getActivePotionEffect(Potion.nightVision).getDuration();
        return (duration > 200) ? 1.0f : (0.7f + MathHelper.sin((duration - n) * 3.1415927f * 0.2f) * 0.3f);
    }
    
    public void activateNextShader() {
        if (OpenGlHelper.shadersSupported && this.mc.getRenderViewEntity() instanceof EntityPlayer) {
            if (this.theShaderGroup != null) {
                this.theShaderGroup.deleteShaderGroup();
            }
            this.shaderIndex = (this.shaderIndex + 1) % (EntityRenderer.shaderResourceLocations.length + 1);
            if (this.shaderIndex != EntityRenderer.shaderCount) {
                this.loadShader(EntityRenderer.shaderResourceLocations[this.shaderIndex]);
            }
            else {
                this.theShaderGroup = null;
            }
        }
    }
    
    public void switchUseShader() {
        this.useShader = !this.useShader;
    }
    
    private void updateTorchFlicker() {
        this.torchFlickerDX += (float)((Math.random() - Math.random()) * Math.random() * Math.random());
        this.torchFlickerDX *= (float)0.9;
        this.torchFlickerX += (this.torchFlickerDX - this.torchFlickerX) * 1.0f;
        this.lightmapUpdateNeeded = true;
    }
    
    public void setupOverlayRendering() {
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        GlStateManager.clear(256);
        GlStateManager.matrixMode(5889);
        GlStateManager.ortho(0.0, scaledResolution.getScaledWidth_double(), scaledResolution.getScaledHeight_double(), 0.0, 1000.0, 3000.0);
        GlStateManager.matrixMode(5888);
        GlStateManager.translate(0.0f, 0.0f, -2000.0f);
    }
    
    public MapItemRenderer getMapItemRenderer() {
        return this.theMapItemRenderer;
    }
    
    public void updateShaderGroupSize(final int n, final int n2) {
        if (OpenGlHelper.shadersSupported) {
            if (this.theShaderGroup != null) {
                this.theShaderGroup.createBindFramebuffers(n, n2);
            }
            this.mc.renderGlobal.createBindEntityOutlineFbs(n, n2);
        }
    }
    
    private void renderWorldPass(final int n, final float n2, final long n3) {
        final RenderGlobal renderGlobal = this.mc.renderGlobal;
        final EffectRenderer effectRenderer = this.mc.effectRenderer;
        final boolean drawBlockOutline = this.isDrawBlockOutline();
        this.mc.mcProfiler.endStartSection("clear");
        GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        this.updateFogColor(n2);
        GlStateManager.clear(16640);
        this.mc.mcProfiler.endStartSection("camera");
        this.setupCameraTransform(n2, n);
        ActiveRenderInfo.updateRenderInfo(this.mc.thePlayer, this.mc.gameSettings.thirdPersonView == 2);
        this.mc.mcProfiler.endStartSection("frustum");
        ClippingHelperImpl.getInstance();
        this.mc.mcProfiler.endStartSection("culling");
        final Frustum frustum = new Frustum();
        final Entity renderViewEntity = this.mc.getRenderViewEntity();
        frustum.setPosition(renderViewEntity.lastTickPosX + (renderViewEntity.posX - renderViewEntity.lastTickPosX) * n2, renderViewEntity.lastTickPosY + (renderViewEntity.posY - renderViewEntity.lastTickPosY) * n2, renderViewEntity.lastTickPosZ + (renderViewEntity.posZ - renderViewEntity.lastTickPosZ) * n2);
        if (Config.isSkyEnabled() || Config.isSunMoonEnabled() || Config.isStarsEnabled()) {
            this.setupFog(-1, n2);
            this.mc.mcProfiler.endStartSection("sky");
            GlStateManager.matrixMode(5889);
            Project.gluPerspective(this.getFOVModifier(n2, true), this.mc.displayWidth / (float)this.mc.displayHeight, 0.05f, this.clipDistance);
            GlStateManager.matrixMode(5888);
            renderGlobal.renderSky(n2, n);
            GlStateManager.matrixMode(5889);
            Project.gluPerspective(this.getFOVModifier(n2, true), this.mc.displayWidth / (float)this.mc.displayHeight, 0.05f, this.clipDistance);
            GlStateManager.matrixMode(5888);
        }
        this.setupFog(0, n2);
        GlStateManager.shadeModel(7425);
        if (renderViewEntity.posY + renderViewEntity.getEyeHeight() < 128.0 + this.mc.gameSettings.ofCloudsHeight * 128.0f) {
            this.renderCloudsCheck(renderGlobal, n2, n);
        }
        this.mc.mcProfiler.endStartSection("prepareterrain");
        this.setupFog(0, n2);
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        this.mc.mcProfiler.endStartSection("terrain_setup");
        renderGlobal.setupTerrain(renderViewEntity, n2, frustum, this.frameCount++, this.mc.thePlayer.isSpectator());
        if (n == 0 || n == 2) {
            this.mc.mcProfiler.endStartSection("updatechunks");
            Lagometer.timerChunkUpload.start();
            this.mc.renderGlobal.updateChunks(n3);
            Lagometer.timerChunkUpload.end();
        }
        this.mc.mcProfiler.endStartSection("terrain");
        Lagometer.timerTerrain.start();
        if (this.mc.gameSettings.ofSmoothFps && n > 0) {
            GL11.glFinish();
        }
        GlStateManager.matrixMode(5888);
        renderGlobal.renderBlockLayer(EnumWorldBlockLayer.SOLID, n2, n, renderViewEntity);
        renderGlobal.renderBlockLayer(EnumWorldBlockLayer.CUTOUT_MIPPED, n2, n, renderViewEntity);
        this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
        renderGlobal.renderBlockLayer(EnumWorldBlockLayer.CUTOUT, n2, n, renderViewEntity);
        this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
        Lagometer.timerTerrain.end();
        GlStateManager.shadeModel(7424);
        GlStateManager.alphaFunc(516, 0.1f);
        if (!this.debugView) {
            GlStateManager.matrixMode(5888);
            this.mc.mcProfiler.endStartSection("entities");
            if (Reflector.ForgeHooksClient_setRenderPass.exists()) {
                Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, 0);
            }
            renderGlobal.renderEntities(renderViewEntity, frustum, n2);
            if (Reflector.ForgeHooksClient_setRenderPass.exists()) {
                Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, -1);
            }
            this.disableLightmap();
            GlStateManager.matrixMode(5888);
            if (this.mc.objectMouseOver != null && renderViewEntity.isInsideOfMaterial(Material.water) && drawBlockOutline) {
                final EntityPlayer entityPlayer = (EntityPlayer)renderViewEntity;
                this.mc.mcProfiler.endStartSection("outline");
                if ((!Reflector.ForgeHooksClient_onDrawBlockHighlight.exists() || !Reflector.callBoolean(Reflector.ForgeHooksClient_onDrawBlockHighlight, renderGlobal, entityPlayer, this.mc.objectMouseOver, 0, entityPlayer.getHeldItem(), n2)) && !this.mc.gameSettings.hideGUI) {
                    renderGlobal.drawSelectionBox(entityPlayer, this.mc.objectMouseOver, 0, n2);
                }
            }
        }
        GlStateManager.matrixMode(5888);
        if (drawBlockOutline && this.mc.objectMouseOver != null && !renderViewEntity.isInsideOfMaterial(Material.water)) {
            final EntityPlayer entityPlayer2 = (EntityPlayer)renderViewEntity;
            this.mc.mcProfiler.endStartSection("outline");
            if ((!Reflector.ForgeHooksClient_onDrawBlockHighlight.exists() || !Reflector.callBoolean(Reflector.ForgeHooksClient_onDrawBlockHighlight, renderGlobal, entityPlayer2, this.mc.objectMouseOver, 0, entityPlayer2.getHeldItem(), n2)) && !this.mc.gameSettings.hideGUI) {
                renderGlobal.drawSelectionBox(entityPlayer2, this.mc.objectMouseOver, 0, n2);
            }
        }
        this.mc.mcProfiler.endStartSection("destroyProgress");
        GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
        this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
        renderGlobal.drawBlockDamageTexture(Tessellator.getInstance(), Tessellator.getInstance().getWorldRenderer(), renderViewEntity, n2);
        this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
        if (!this.debugView) {
            this.enableLightmap();
            this.mc.mcProfiler.endStartSection("litParticles");
            effectRenderer.renderLitParticles(renderViewEntity, n2);
            this.setupFog(0, n2);
            this.mc.mcProfiler.endStartSection("particles");
            effectRenderer.renderParticles(renderViewEntity, n2);
            this.disableLightmap();
        }
        GlStateManager.depthMask(false);
        this.mc.mcProfiler.endStartSection("weather");
        this.renderRainSnow(n2);
        GlStateManager.depthMask(true);
        renderGlobal.renderWorldBorder(renderViewEntity, n2);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.alphaFunc(516, 0.1f);
        this.setupFog(0, n2);
        GlStateManager.depthMask(false);
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        GlStateManager.shadeModel(7425);
        this.mc.mcProfiler.endStartSection("translucent");
        renderGlobal.renderBlockLayer(EnumWorldBlockLayer.TRANSLUCENT, n2, n, renderViewEntity);
        if (Reflector.ForgeHooksClient_setRenderPass.exists() && !this.debugView) {
            this.mc.mcProfiler.endStartSection("entities");
            Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, 1);
            this.mc.renderGlobal.renderEntities(renderViewEntity, frustum, n2);
            Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, -1);
        }
        GlStateManager.shadeModel(7424);
        GlStateManager.depthMask(true);
        if (renderViewEntity.posY + renderViewEntity.getEyeHeight() >= 128.0 + this.mc.gameSettings.ofCloudsHeight * 128.0f) {
            this.mc.mcProfiler.endStartSection("aboveClouds");
            this.renderCloudsCheck(renderGlobal, n2, n);
        }
        if (Reflector.ForgeHooksClient_dispatchRenderLast.exists()) {
            this.mc.mcProfiler.endStartSection("forge_render_last");
            Reflector.callVoid(Reflector.ForgeHooksClient_dispatchRenderLast, renderGlobal, n2);
        }
        new Event3D(n2).call();
        this.mc.mcProfiler.endStartSection("hand");
        if (!Reflector.callBoolean(Reflector.ForgeHooksClient_renderFirstPersonHand, this.mc.renderGlobal, n2, n) && this.renderHand) {
            GlStateManager.clear(256);
            this.renderHand(n2, n);
            this.renderWorldDirections(n2);
        }
    }
    
    protected void renderRainSnow(final float n) {
        if (Reflector.ForgeWorldProvider_getWeatherRenderer.exists()) {
            final Object call = Reflector.call(this.mc.theWorld.provider, Reflector.ForgeWorldProvider_getWeatherRenderer, new Object[0]);
            if (call != null) {
                Reflector.callVoid(call, Reflector.IRenderHandler_render, n, this.mc.theWorld, this.mc);
                return;
            }
        }
        final float rainStrength = this.mc.theWorld.getRainStrength(n);
        if (rainStrength > 0.0f) {
            if (Config.isRainOff()) {
                return;
            }
            this.enableLightmap();
            final Entity renderViewEntity = this.mc.getRenderViewEntity();
            final WorldClient theWorld = this.mc.theWorld;
            final int floor_double = MathHelper.floor_double(renderViewEntity.posX);
            final int floor_double2 = MathHelper.floor_double(renderViewEntity.posY);
            final int floor_double3 = MathHelper.floor_double(renderViewEntity.posZ);
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.alphaFunc(516, 0.1f);
            final double n2 = renderViewEntity.lastTickPosX + (renderViewEntity.posX - renderViewEntity.lastTickPosX) * n;
            final double n3 = renderViewEntity.lastTickPosY + (renderViewEntity.posY - renderViewEntity.lastTickPosY) * n;
            final double n4 = renderViewEntity.lastTickPosZ + (renderViewEntity.posZ - renderViewEntity.lastTickPosZ) * n;
            final int floor_double4 = MathHelper.floor_double(n3);
            if (Config.isRainFancy()) {}
            final float n5 = this.rendererUpdateCount + n;
            worldRenderer.setTranslation(-n2, -n3, -n4);
            if (Config.isRainFancy()) {}
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
            for (int i = floor_double3 - 10; i <= floor_double3 + 10; ++i) {
                for (int j = floor_double - 10; j <= floor_double + 10; ++j) {
                    final int n6 = (i - floor_double3 + 16) * 32 + j - floor_double + 16;
                    final double n7 = this.rainXCoords[n6] * 0.5;
                    final double n8 = this.rainYCoords[n6] * 0.5;
                    mutableBlockPos.func_181079_c(j, 0, i);
                    final BiomeGenBase biomeGenForCoords = theWorld.getBiomeGenForCoords(mutableBlockPos);
                    if (biomeGenForCoords.canSpawnLightningBolt() || biomeGenForCoords.getEnableSnow()) {
                        final int y = theWorld.getPrecipitationHeight(mutableBlockPos).getY();
                        int n9 = floor_double2 - 10;
                        int n10 = floor_double2 + 10;
                        if (n9 < y) {
                            n9 = y;
                        }
                        if (n10 < y) {
                            n10 = y;
                        }
                        int n11;
                        if ((n11 = y) < floor_double4) {
                            n11 = floor_double4;
                        }
                        if (n9 != n10) {
                            this.random.setSeed(j * j * 3121 + j * 45238971 ^ i * i * 418711 + i * 13761);
                            mutableBlockPos.func_181079_c(j, n9, i);
                            if (theWorld.getWorldChunkManager().getTemperatureAtHeight(biomeGenForCoords.getFloatTemperature(mutableBlockPos), y) >= 0.15f) {
                                instance.draw();
                                this.mc.getTextureManager().bindTexture(EntityRenderer.locationRainPng);
                                worldRenderer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
                                final double n12 = ((this.rendererUpdateCount + j * j * 3121 + j * 45238971 + i * i * 418711 + i * 13761 & 0x1F) + (double)n) / 32.0 * (3.0 + this.random.nextDouble());
                                final double n13 = j + 0.5f - renderViewEntity.posX;
                                final double n14 = i + 0.5f - renderViewEntity.posZ;
                                final float n15 = MathHelper.sqrt_double(n13 * n13 + n14 * n14) / 10;
                                final float n16 = ((1.0f - n15 * n15) * 0.5f + 0.5f) * rainStrength;
                                mutableBlockPos.func_181079_c(j, n11, i);
                                final int combinedLight = theWorld.getCombinedLight(mutableBlockPos, 0);
                                final int n17 = combinedLight >> 16 & 0xFFFF;
                                final int n18 = combinedLight & 0xFFFF;
                                worldRenderer.pos(j - n7 + 0.5, n9, i - n8 + 0.5).tex(0.0, n9 * 0.25 + n12).color(1.0f, 1.0f, 1.0f, n16).lightmap(n17, n18).endVertex();
                                worldRenderer.pos(j + n7 + 0.5, n9, i + n8 + 0.5).tex(1.0, n9 * 0.25 + n12).color(1.0f, 1.0f, 1.0f, n16).lightmap(n17, n18).endVertex();
                                worldRenderer.pos(j + n7 + 0.5, n10, i + n8 + 0.5).tex(1.0, n10 * 0.25 + n12).color(1.0f, 1.0f, 1.0f, n16).lightmap(n17, n18).endVertex();
                                worldRenderer.pos(j - n7 + 0.5, n10, i - n8 + 0.5).tex(0.0, n10 * 0.25 + n12).color(1.0f, 1.0f, 1.0f, n16).lightmap(n17, n18).endVertex();
                            }
                            else {
                                final double n19 = ((this.rendererUpdateCount & 0x1FF) + n) / 512.0f;
                                final double n20 = this.random.nextDouble() + n5 * 0.01 * (float)this.random.nextGaussian();
                                final double n21 = this.random.nextDouble() + n5 * (float)this.random.nextGaussian() * 0.001;
                                final double n22 = j + 0.5f - renderViewEntity.posX;
                                final double n23 = i + 0.5f - renderViewEntity.posZ;
                                final float n24 = MathHelper.sqrt_double(n22 * n22 + n23 * n23) / 10;
                                final float n25 = ((1.0f - n24 * n24) * 0.3f + 0.5f) * rainStrength;
                                mutableBlockPos.func_181079_c(j, n11, i);
                                final int n26 = (theWorld.getCombinedLight(mutableBlockPos, 0) * 3 + 15728880) / 4;
                                final int n27 = n26 >> 16 & 0xFFFF;
                                final int n28 = n26 & 0xFFFF;
                                worldRenderer.pos(j - n7 + 0.5, n9, i - n8 + 0.5).tex(0.0 + n20, n9 * 0.25 + n19 + n21).color(1.0f, 1.0f, 1.0f, n25).lightmap(n27, n28).endVertex();
                                worldRenderer.pos(j + n7 + 0.5, n9, i + n8 + 0.5).tex(1.0 + n20, n9 * 0.25 + n19 + n21).color(1.0f, 1.0f, 1.0f, n25).lightmap(n27, n28).endVertex();
                                worldRenderer.pos(j + n7 + 0.5, n10, i + n8 + 0.5).tex(1.0 + n20, n10 * 0.25 + n19 + n21).color(1.0f, 1.0f, 1.0f, n25).lightmap(n27, n28).endVertex();
                                worldRenderer.pos(j - n7 + 0.5, n10, i - n8 + 0.5).tex(0.0 + n20, n10 * 0.25 + n19 + n21).color(1.0f, 1.0f, 1.0f, n25).lightmap(n27, n28).endVertex();
                            }
                        }
                    }
                }
            }
            instance.draw();
            worldRenderer.setTranslation(0.0, 0.0, 0.0);
            GlStateManager.alphaFunc(516, 0.1f);
            this.disableLightmap();
        }
    }
    
    private void renderWorldDirections(final float n) {
        if (this.mc.gameSettings.showDebugInfo && !this.mc.gameSettings.hideGUI && !this.mc.thePlayer.hasReducedDebug() && !this.mc.gameSettings.reducedDebugInfo) {
            final Entity renderViewEntity = this.mc.getRenderViewEntity();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GL11.glLineWidth(1.0f);
            GlStateManager.depthMask(false);
            GlStateManager.matrixMode(5888);
            this.orientCamera(n);
            GlStateManager.translate(0.0f, renderViewEntity.getEyeHeight(), 0.0f);
            RenderGlobal.func_181563_a(new AxisAlignedBB(0.0, 0.0, 0.0, 0.005, 1.0E-4, 1.0E-4), 255, 0, 0, 255);
            RenderGlobal.func_181563_a(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0E-4, 1.0E-4, 0.005), 0, 0, 255, 255);
            RenderGlobal.func_181563_a(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0E-4, 0.0033, 1.0E-4), 0, 255, 0, 255);
            GlStateManager.depthMask(true);
        }
    }
    
    public void getMouseOver(final float n) {
        final Entity renderViewEntity = this.mc.getRenderViewEntity();
        if (renderViewEntity != null && this.mc.theWorld != null) {
            this.mc.mcProfiler.startSection("pick");
            this.mc.pointedEntity = null;
            final double n2 = this.mc.playerController.getBlockReachDistance();
            this.mc.objectMouseOver = renderViewEntity.rayTrace(n2, n);
            double distanceTo = n2;
            final Vec3 positionEyes = renderViewEntity.getPositionEyes(n);
            double n3;
            if (this.mc.playerController.extendedReach()) {
                n3 = 6.0;
                distanceTo = 6.0;
            }
            else {
                if (n2 > 3.0) {}
                n3 = n2;
            }
            if (this.mc.objectMouseOver != null) {
                distanceTo = this.mc.objectMouseOver.hitVec.distanceTo(positionEyes);
            }
            final Vec3 look = renderViewEntity.getLook(n);
            final Vec3 addVector = positionEyes.addVector(look.xCoord * n3, look.yCoord * n3, look.zCoord * n3);
            this.pointedEntity = null;
            Vec3 vec3 = null;
            final float n4 = 1.0f;
            final List entitiesInAABBexcluding = this.mc.theWorld.getEntitiesInAABBexcluding(renderViewEntity, renderViewEntity.getEntityBoundingBox().addCoord(look.xCoord * n3, look.yCoord * n3, look.zCoord * n3).expand(n4, n4, n4), Predicates.and(EntitySelectors.NOT_SPECTATING, (Predicate)new EntityRenderer1(this)));
            double n5 = distanceTo;
            while (0 < entitiesInAABBexcluding.size()) {
                final Entity pointedEntity = entitiesInAABBexcluding.get(0);
                final float collisionBorderSize = pointedEntity.getCollisionBorderSize();
                final AxisAlignedBB expand = pointedEntity.getEntityBoundingBox().expand(collisionBorderSize, collisionBorderSize, collisionBorderSize);
                final MovingObjectPosition calculateIntercept = expand.calculateIntercept(positionEyes, addVector);
                if (expand.isVecInside(positionEyes)) {
                    if (n5 >= 0.0) {
                        this.pointedEntity = pointedEntity;
                        vec3 = ((calculateIntercept == null) ? positionEyes : calculateIntercept.hitVec);
                        n5 = 0.0;
                    }
                }
                else if (calculateIntercept != null) {
                    final double distanceTo2 = positionEyes.distanceTo(calculateIntercept.hitVec);
                    if (distanceTo2 < n5 || n5 == 0.0) {
                        if (Reflector.ForgeEntity_canRiderInteract.exists()) {
                            Reflector.callBoolean(pointedEntity, Reflector.ForgeEntity_canRiderInteract, new Object[0]);
                        }
                        if (pointedEntity == renderViewEntity.ridingEntity) {
                            if (n5 == 0.0) {
                                this.pointedEntity = pointedEntity;
                                vec3 = calculateIntercept.hitVec;
                            }
                        }
                        else {
                            this.pointedEntity = pointedEntity;
                            vec3 = calculateIntercept.hitVec;
                            n5 = distanceTo2;
                        }
                    }
                }
                int n6 = 0;
                ++n6;
            }
            if (this.pointedEntity != null && positionEyes.distanceTo(vec3) > 3.0) {
                this.pointedEntity = null;
                this.mc.objectMouseOver = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, vec3, null, new BlockPos(vec3));
            }
            if (this.pointedEntity != null && (n5 < distanceTo || this.mc.objectMouseOver == null)) {
                this.mc.objectMouseOver = new MovingObjectPosition(this.pointedEntity, vec3);
                if (this.pointedEntity instanceof EntityLivingBase || this.pointedEntity instanceof EntityItemFrame) {
                    this.mc.pointedEntity = this.pointedEntity;
                }
            }
            this.mc.mcProfiler.endSection();
        }
    }
    
    public void disableLightmap() {
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
    
    public ShaderGroup getShaderGroup() {
        return this.theShaderGroup;
    }
    
    public void loadEntityShader(final Entity entity) {
        if (OpenGlHelper.shadersSupported) {
            if (this.theShaderGroup != null) {
                this.theShaderGroup.deleteShaderGroup();
            }
            this.theShaderGroup = null;
            if (entity instanceof EntityCreeper) {
                this.loadShader(new ResourceLocation("shaders/post/creeper.json"));
            }
            else if (entity instanceof EntitySpider) {
                this.loadShader(new ResourceLocation("shaders/post/spider.json"));
            }
            else if (entity instanceof EntityEnderman) {
                this.loadShader(new ResourceLocation("shaders/post/invert.json"));
            }
        }
    }
    
    public void func_181560_a(final float p0, final long p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokespecial   net/minecraft/client/renderer/EntityRenderer.frameInit:()V
        //     4: invokestatic    org/lwjgl/opengl/Display.isActive:()Z
        //     7: istore          4
        //     9: iload           4
        //    11: ifne            72
        //    14: aload_0        
        //    15: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //    18: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //    21: getfield        net/minecraft/client/settings/GameSettings.pauseOnLostFocus:Z
        //    24: ifeq            72
        //    27: aload_0        
        //    28: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //    31: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //    34: getfield        net/minecraft/client/settings/GameSettings.touchscreen:Z
        //    37: ifeq            47
        //    40: iconst_1       
        //    41: invokestatic    org/lwjgl/input/Mouse.isButtonDown:(I)Z
        //    44: ifne            72
        //    47: invokestatic    net/minecraft/client/Minecraft.getSystemTime:()J
        //    50: aload_0        
        //    51: getfield        net/minecraft/client/renderer/EntityRenderer.prevFrameTime:J
        //    54: lsub           
        //    55: ldc2_w          500
        //    58: lcmp           
        //    59: ifle            79
        //    62: aload_0        
        //    63: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //    66: invokevirtual   net/minecraft/client/Minecraft.displayInGameMenu:()V
        //    69: goto            79
        //    72: aload_0        
        //    73: invokestatic    net/minecraft/client/Minecraft.getSystemTime:()J
        //    76: putfield        net/minecraft/client/renderer/EntityRenderer.prevFrameTime:J
        //    79: aload_0        
        //    80: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //    83: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //    86: ldc_w           "mouse"
        //    89: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //    92: iload           4
        //    94: ifeq            140
        //    97: getstatic       net/minecraft/client/Minecraft.isRunningOnMac:Z
        //   100: ifeq            140
        //   103: aload_0        
        //   104: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   107: getfield        net/minecraft/client/Minecraft.inGameHasFocus:Z
        //   110: ifeq            140
        //   113: invokestatic    org/lwjgl/input/Mouse.isInsideWindow:()Z
        //   116: ifne            140
        //   119: iconst_0       
        //   120: invokestatic    org/lwjgl/input/Mouse.setGrabbed:(Z)V
        //   123: invokestatic    org/lwjgl/opengl/Display.getWidth:()I
        //   126: iconst_2       
        //   127: idiv           
        //   128: invokestatic    org/lwjgl/opengl/Display.getHeight:()I
        //   131: iconst_2       
        //   132: idiv           
        //   133: invokestatic    org/lwjgl/input/Mouse.setCursorPosition:(II)V
        //   136: iconst_1       
        //   137: invokestatic    org/lwjgl/input/Mouse.setGrabbed:(Z)V
        //   140: aload_0        
        //   141: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   144: getfield        net/minecraft/client/Minecraft.inGameHasFocus:Z
        //   147: ifeq            357
        //   150: iload           4
        //   152: ifeq            357
        //   155: aload_0        
        //   156: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   159: getfield        net/minecraft/client/Minecraft.mouseHelper:Lnet/minecraft/util/MouseHelper;
        //   162: invokevirtual   net/minecraft/util/MouseHelper.mouseXYChange:()V
        //   165: aload_0        
        //   166: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   169: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   172: getfield        net/minecraft/client/settings/GameSettings.mouseSensitivity:F
        //   175: ldc_w           0.6
        //   178: fmul           
        //   179: ldc_w           0.2
        //   182: fadd           
        //   183: fstore          5
        //   185: fload           5
        //   187: fload           5
        //   189: fmul           
        //   190: fload           5
        //   192: fmul           
        //   193: ldc_w           8.0
        //   196: fmul           
        //   197: fstore          6
        //   199: aload_0        
        //   200: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   203: getfield        net/minecraft/client/Minecraft.mouseHelper:Lnet/minecraft/util/MouseHelper;
        //   206: getfield        net/minecraft/util/MouseHelper.deltaX:I
        //   209: i2f            
        //   210: fload           6
        //   212: fmul           
        //   213: fstore          7
        //   215: aload_0        
        //   216: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   219: getfield        net/minecraft/client/Minecraft.mouseHelper:Lnet/minecraft/util/MouseHelper;
        //   222: getfield        net/minecraft/util/MouseHelper.deltaY:I
        //   225: i2f            
        //   226: fload           6
        //   228: fmul           
        //   229: fstore          8
        //   231: aload_0        
        //   232: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   235: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   238: getfield        net/minecraft/client/settings/GameSettings.invertMouse:Z
        //   241: ifeq            244
        //   244: aload_0        
        //   245: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   248: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   251: getfield        net/minecraft/client/settings/GameSettings.smoothCamera:Z
        //   254: ifeq            330
        //   257: aload_0        
        //   258: dup            
        //   259: getfield        net/minecraft/client/renderer/EntityRenderer.smoothCamYaw:F
        //   262: fload           7
        //   264: fadd           
        //   265: putfield        net/minecraft/client/renderer/EntityRenderer.smoothCamYaw:F
        //   268: aload_0        
        //   269: dup            
        //   270: getfield        net/minecraft/client/renderer/EntityRenderer.smoothCamPitch:F
        //   273: fload           8
        //   275: fadd           
        //   276: putfield        net/minecraft/client/renderer/EntityRenderer.smoothCamPitch:F
        //   279: fload_1        
        //   280: aload_0        
        //   281: getfield        net/minecraft/client/renderer/EntityRenderer.smoothCamPartialTicks:F
        //   284: fsub           
        //   285: fstore          10
        //   287: aload_0        
        //   288: fload_1        
        //   289: putfield        net/minecraft/client/renderer/EntityRenderer.smoothCamPartialTicks:F
        //   292: aload_0        
        //   293: getfield        net/minecraft/client/renderer/EntityRenderer.smoothCamFilterX:F
        //   296: fload           10
        //   298: fmul           
        //   299: fstore          7
        //   301: aload_0        
        //   302: getfield        net/minecraft/client/renderer/EntityRenderer.smoothCamFilterY:F
        //   305: fload           10
        //   307: fmul           
        //   308: fstore          8
        //   310: aload_0        
        //   311: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   314: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   317: fload           7
        //   319: fload           8
        //   321: iconst_m1      
        //   322: i2f            
        //   323: fmul           
        //   324: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.setAngles:(FF)V
        //   327: goto            357
        //   330: aload_0        
        //   331: fconst_0       
        //   332: putfield        net/minecraft/client/renderer/EntityRenderer.smoothCamYaw:F
        //   335: aload_0        
        //   336: fconst_0       
        //   337: putfield        net/minecraft/client/renderer/EntityRenderer.smoothCamPitch:F
        //   340: aload_0        
        //   341: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   344: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   347: fload           7
        //   349: fload           8
        //   351: iconst_m1      
        //   352: i2f            
        //   353: fmul           
        //   354: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.setAngles:(FF)V
        //   357: aload_0        
        //   358: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   361: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   364: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //   367: aload_0        
        //   368: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   371: getfield        net/minecraft/client/Minecraft.skipRenderWorld:Z
        //   374: ifne            930
        //   377: aload_0        
        //   378: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   381: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   384: getfield        net/minecraft/client/settings/GameSettings.anaglyph:Z
        //   387: putstatic       net/minecraft/client/renderer/EntityRenderer.anaglyphEnable:Z
        //   390: new             Lnet/minecraft/client/gui/ScaledResolution;
        //   393: dup            
        //   394: aload_0        
        //   395: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   398: invokespecial   net/minecraft/client/gui/ScaledResolution.<init>:(Lnet/minecraft/client/Minecraft;)V
        //   401: astore          5
        //   403: aload           5
        //   405: invokevirtual   net/minecraft/client/gui/ScaledResolution.getScaledWidth:()I
        //   408: istore          6
        //   410: aload           5
        //   412: invokevirtual   net/minecraft/client/gui/ScaledResolution.getScaledHeight:()I
        //   415: istore          7
        //   417: invokestatic    org/lwjgl/input/Mouse.getX:()I
        //   420: iload           6
        //   422: imul           
        //   423: aload_0        
        //   424: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   427: getfield        net/minecraft/client/Minecraft.displayWidth:I
        //   430: idiv           
        //   431: istore          8
        //   433: iload           7
        //   435: invokestatic    org/lwjgl/input/Mouse.getY:()I
        //   438: iload           7
        //   440: imul           
        //   441: aload_0        
        //   442: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   445: getfield        net/minecraft/client/Minecraft.displayHeight:I
        //   448: idiv           
        //   449: isub           
        //   450: iconst_1       
        //   451: isub           
        //   452: istore          9
        //   454: aload_0        
        //   455: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   458: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   461: getfield        net/minecraft/client/settings/GameSettings.limitFramerate:I
        //   464: istore          10
        //   466: aload_0        
        //   467: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   470: getfield        net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   473: ifnull          716
        //   476: aload_0        
        //   477: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   480: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   483: ldc_w           "level"
        //   486: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //   489: invokestatic    net/minecraft/client/Minecraft.getDebugFPS:()I
        //   492: iload           10
        //   494: invokestatic    java/lang/Math.min:(II)I
        //   497: istore          11
        //   499: iload           11
        //   501: bipush          60
        //   503: invokestatic    java/lang/Math.max:(II)I
        //   506: istore          11
        //   508: invokestatic    java/lang/System.nanoTime:()J
        //   511: lload_2        
        //   512: lsub           
        //   513: lstore          12
        //   515: ldc_w           1000000000
        //   518: iload           11
        //   520: idiv           
        //   521: iconst_4       
        //   522: idiv           
        //   523: i2l            
        //   524: lload           12
        //   526: lsub           
        //   527: lconst_0       
        //   528: invokestatic    java/lang/Math.max:(JJ)J
        //   531: lstore          14
        //   533: aload_0        
        //   534: fload_1        
        //   535: invokestatic    java/lang/System.nanoTime:()J
        //   538: lload           14
        //   540: ladd           
        //   541: invokevirtual   net/minecraft/client/renderer/EntityRenderer.renderWorld:(FJ)V
        //   544: getstatic       net/minecraft/client/renderer/OpenGlHelper.shadersSupported:Z
        //   547: ifeq            599
        //   550: aload_0        
        //   551: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   554: getfield        net/minecraft/client/Minecraft.renderGlobal:Lnet/minecraft/client/renderer/RenderGlobal;
        //   557: invokevirtual   net/minecraft/client/renderer/RenderGlobal.renderEntityOutlineFramebuffer:()V
        //   560: aload_0        
        //   561: getfield        net/minecraft/client/renderer/EntityRenderer.theShaderGroup:Lnet/minecraft/client/shader/ShaderGroup;
        //   564: ifnull          588
        //   567: aload_0        
        //   568: getfield        net/minecraft/client/renderer/EntityRenderer.useShader:Z
        //   571: ifeq            588
        //   574: sipush          5890
        //   577: invokestatic    net/minecraft/client/renderer/GlStateManager.matrixMode:(I)V
        //   580: aload_0        
        //   581: getfield        net/minecraft/client/renderer/EntityRenderer.theShaderGroup:Lnet/minecraft/client/shader/ShaderGroup;
        //   584: fload_1        
        //   585: invokevirtual   net/minecraft/client/shader/ShaderGroup.loadShaderGroup:(F)V
        //   588: aload_0        
        //   589: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   592: invokevirtual   net/minecraft/client/Minecraft.getFramebuffer:()Lnet/minecraft/client/shader/Framebuffer;
        //   595: iconst_1       
        //   596: invokevirtual   net/minecraft/client/shader/Framebuffer.bindFramebuffer:(Z)V
        //   599: aload_0        
        //   600: invokestatic    java/lang/System.nanoTime:()J
        //   603: putfield        net/minecraft/client/renderer/EntityRenderer.renderEndNanoTime:J
        //   606: aload_0        
        //   607: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   610: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   613: ldc_w           "gui"
        //   616: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   619: aload_0        
        //   620: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   623: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   626: getfield        net/minecraft/client/settings/GameSettings.hideGUI:Z
        //   629: ifeq            642
        //   632: aload_0        
        //   633: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   636: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
        //   639: ifnull          703
        //   642: sipush          516
        //   645: ldc_w           0.1
        //   648: invokestatic    net/minecraft/client/renderer/GlStateManager.alphaFunc:(IF)V
        //   651: aload_0        
        //   652: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   655: getfield        net/minecraft/client/Minecraft.ingameGUI:Lnet/minecraft/client/gui/GuiIngame;
        //   658: fload_1        
        //   659: invokevirtual   net/minecraft/client/gui/GuiIngame.renderGameOverlay:(F)V
        //   662: aload_0        
        //   663: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   666: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   669: getfield        net/minecraft/client/settings/GameSettings.ofShowFps:Z
        //   672: ifeq            685
        //   675: aload_0        
        //   676: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   679: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   682: getfield        net/minecraft/client/settings/GameSettings.showDebugInfo:Z
        //   685: aload_0        
        //   686: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   689: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   692: getfield        net/minecraft/client/settings/GameSettings.showDebugInfo:Z
        //   695: ifeq            703
        //   698: aload           5
        //   700: invokestatic    optfine/Lagometer.showLagometer:(Lnet/minecraft/client/gui/ScaledResolution;)V
        //   703: aload_0        
        //   704: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   707: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   710: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //   713: goto            758
        //   716: iconst_0       
        //   717: iconst_0       
        //   718: aload_0        
        //   719: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   722: getfield        net/minecraft/client/Minecraft.displayWidth:I
        //   725: aload_0        
        //   726: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   729: getfield        net/minecraft/client/Minecraft.displayHeight:I
        //   732: invokestatic    net/minecraft/client/renderer/GlStateManager.viewport:(IIII)V
        //   735: sipush          5889
        //   738: invokestatic    net/minecraft/client/renderer/GlStateManager.matrixMode:(I)V
        //   741: sipush          5888
        //   744: invokestatic    net/minecraft/client/renderer/GlStateManager.matrixMode:(I)V
        //   747: aload_0        
        //   748: invokevirtual   net/minecraft/client/renderer/EntityRenderer.setupOverlayRendering:()V
        //   751: aload_0        
        //   752: invokestatic    java/lang/System.nanoTime:()J
        //   755: putfield        net/minecraft/client/renderer/EntityRenderer.renderEndNanoTime:J
        //   758: aload_0        
        //   759: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   762: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
        //   765: ifnull          930
        //   768: sipush          256
        //   771: invokestatic    net/minecraft/client/renderer/GlStateManager.clear:(I)V
        //   774: getstatic       optfine/Reflector.ForgeHooksClient_drawScreen:Loptfine/ReflectorMethod;
        //   777: invokevirtual   optfine/ReflectorMethod.exists:()Z
        //   780: ifeq            828
        //   783: getstatic       optfine/Reflector.ForgeHooksClient_drawScreen:Loptfine/ReflectorMethod;
        //   786: iconst_4       
        //   787: anewarray       Ljava/lang/Object;
        //   790: dup            
        //   791: iconst_0       
        //   792: aload_0        
        //   793: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   796: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
        //   799: aastore        
        //   800: dup            
        //   801: iconst_1       
        //   802: iload           8
        //   804: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   807: aastore        
        //   808: dup            
        //   809: iconst_2       
        //   810: iconst_m1      
        //   811: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   814: aastore        
        //   815: dup            
        //   816: iconst_3       
        //   817: fload_1        
        //   818: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   821: aastore        
        //   822: invokestatic    optfine/Reflector.callVoid:(Loptfine/ReflectorMethod;[Ljava/lang/Object;)V
        //   825: goto            930
        //   828: aload_0        
        //   829: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   832: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
        //   835: iload           8
        //   837: iconst_m1      
        //   838: fload_1        
        //   839: invokevirtual   net/minecraft/client/gui/GuiScreen.drawScreen:(IIF)V
        //   842: goto            930
        //   845: astore          11
        //   847: aload           11
        //   849: ldc_w           "Rendering screen"
        //   852: invokestatic    net/minecraft/crash/CrashReport.makeCrashReport:(Ljava/lang/Throwable;Ljava/lang/String;)Lnet/minecraft/crash/CrashReport;
        //   855: astore          12
        //   857: aload           12
        //   859: ldc_w           "Screen render details"
        //   862: invokevirtual   net/minecraft/crash/CrashReport.makeCategory:(Ljava/lang/String;)Lnet/minecraft/crash/CrashReportCategory;
        //   865: astore          13
        //   867: aload           13
        //   869: ldc_w           "Screen name"
        //   872: new             Lnet/minecraft/client/renderer/EntityRenderer2;
        //   875: dup            
        //   876: aload_0        
        //   877: invokespecial   net/minecraft/client/renderer/EntityRenderer2.<init>:(Lnet/minecraft/client/renderer/EntityRenderer;)V
        //   880: invokevirtual   net/minecraft/crash/CrashReportCategory.addCrashSectionCallable:(Ljava/lang/String;Ljava/util/concurrent/Callable;)V
        //   883: aload           13
        //   885: ldc_w           "Mouse location"
        //   888: new             Lnet/minecraft/client/renderer/EntityRenderer$1;
        //   891: dup            
        //   892: aload_0        
        //   893: iload           8
        //   895: iconst_m1      
        //   896: invokespecial   net/minecraft/client/renderer/EntityRenderer$1.<init>:(Lnet/minecraft/client/renderer/EntityRenderer;II)V
        //   899: invokevirtual   net/minecraft/crash/CrashReportCategory.addCrashSectionCallable:(Ljava/lang/String;Ljava/util/concurrent/Callable;)V
        //   902: aload           13
        //   904: ldc_w           "Screen size"
        //   907: new             Lnet/minecraft/client/renderer/EntityRenderer$2;
        //   910: dup            
        //   911: aload_0        
        //   912: aload           5
        //   914: invokespecial   net/minecraft/client/renderer/EntityRenderer$2.<init>:(Lnet/minecraft/client/renderer/EntityRenderer;Lnet/minecraft/client/gui/ScaledResolution;)V
        //   917: invokevirtual   net/minecraft/crash/CrashReportCategory.addCrashSectionCallable:(Ljava/lang/String;Ljava/util/concurrent/Callable;)V
        //   920: new             Lnet/minecraft/util/ReportedException;
        //   923: dup            
        //   924: aload           12
        //   926: invokespecial   net/minecraft/util/ReportedException.<init>:(Lnet/minecraft/crash/CrashReport;)V
        //   929: athrow         
        //   930: aload_0        
        //   931: invokespecial   net/minecraft/client/renderer/EntityRenderer.frameFinish:()V
        //   934: aload_0        
        //   935: invokespecial   net/minecraft/client/renderer/EntityRenderer.waitForServerThread:()V
        //   938: aload_0        
        //   939: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   942: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   945: getfield        net/minecraft/client/settings/GameSettings.ofProfiler:Z
        //   948: ifeq            962
        //   951: aload_0        
        //   952: getfield        net/minecraft/client/renderer/EntityRenderer.mc:Lnet/minecraft/client/Minecraft;
        //   955: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   958: iconst_1       
        //   959: putfield        net/minecraft/client/settings/GameSettings.showDebugProfilerChart:Z
        //   962: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0685 (coming from #0682).
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
    
    private void updateMainMenu(final GuiMainMenu guiMainMenu) {
        Object o = null;
        final Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        final int value = instance.get(5);
        final int n = instance.get(2) + 1;
        if (value == 8 && n == 4) {
            o = "Happy birthday, OptiFine!";
        }
        if (value == 14 && n == 8) {
            o = "Happy birthday, sp614x!";
        }
        if (o == null) {
            return;
        }
        final Field[] declaredFields = GuiMainMenu.class.getDeclaredFields();
        while (0 < declaredFields.length) {
            if (declaredFields[0].getType() == String.class) {
                declaredFields[0].setAccessible(true);
                declaredFields[0].set(guiMainMenu, o);
                return;
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    private void setupViewBobbing(final float n) {
        if (this.mc.getRenderViewEntity() instanceof EntityPlayer) {
            final EntityPlayer entityPlayer = (EntityPlayer)this.mc.getRenderViewEntity();
            final float n2 = -(entityPlayer.distanceWalkedModified + (entityPlayer.distanceWalkedModified - entityPlayer.prevDistanceWalkedModified) * n);
            final float n3 = entityPlayer.prevCameraYaw + (entityPlayer.cameraYaw - entityPlayer.prevCameraYaw) * n;
            final float n4 = entityPlayer.prevCameraPitch + (entityPlayer.cameraPitch - entityPlayer.prevCameraPitch) * n;
            GlStateManager.translate(MathHelper.sin(n2 * 3.1415927f) * n3 * 0.5f, -Math.abs(MathHelper.cos(n2 * 3.1415927f) * n3), 0.0f);
            GlStateManager.rotate(MathHelper.sin(n2 * 3.1415927f) * n3 * 3.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(Math.abs(MathHelper.cos(n2 * 3.1415927f - 0.2f) * n3) * 5.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(n4, 1.0f, 0.0f, 0.0f);
        }
    }
    
    private boolean isDrawBlockOutline() {
        if (!this.drawBlockOutline) {
            return false;
        }
        final Entity renderViewEntity = this.mc.getRenderViewEntity();
        boolean b = renderViewEntity instanceof EntityPlayer && !this.mc.gameSettings.hideGUI;
        if (b && !((EntityPlayer)renderViewEntity).capabilities.allowEdit) {
            final ItemStack currentEquippedItem = ((EntityPlayer)renderViewEntity).getCurrentEquippedItem();
            if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                final BlockPos blockPos = this.mc.objectMouseOver.getBlockPos();
                final Block block = this.mc.theWorld.getBlockState(blockPos).getBlock();
                if (this.mc.playerController.getCurrentGameType() == WorldSettings.GameType.SPECTATOR) {
                    boolean b2;
                    if (Reflector.ForgeBlock_hasTileEntity.exists()) {
                        b2 = Reflector.callBoolean(block, Reflector.ForgeBlock_hasTileEntity, this.mc.theWorld.getBlockState(blockPos));
                    }
                    else {
                        b2 = block.hasTileEntity();
                    }
                    b = (b2 && this.mc.theWorld.getTileEntity(blockPos) instanceof IInventory);
                }
                else {
                    b = (currentEquippedItem != null && (currentEquippedItem.canDestroy(block) || currentEquippedItem.canPlaceOn(block)));
                }
            }
        }
        return b;
    }
    
    private void setupFog(final int n, final float n2) {
        final Entity renderViewEntity = this.mc.getRenderViewEntity();
        this.fogStandard = false;
        if (renderViewEntity instanceof EntityPlayer) {
            final boolean isCreativeMode = ((EntityPlayer)renderViewEntity).capabilities.isCreativeMode;
        }
        GL11.glFog(2918, this.setFogColorBuffer(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 1.0f));
        GL11.glNormal3f(0.0f, -1.0f, 0.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final Block blockAtEntityViewpoint = ActiveRenderInfo.getBlockAtEntityViewpoint(this.mc.theWorld, renderViewEntity, n2);
        final Object instance = Reflector.newInstance(Reflector.EntityViewRenderEvent_FogDensity_Constructor, this, renderViewEntity, blockAtEntityViewpoint, n2, 0.1f);
        if (Reflector.postForgeBusEvent(instance)) {
            GL11.glFogf(2914, Reflector.getFieldValueFloat(instance, Reflector.EntityViewRenderEvent_FogDensity_density, 0.0f));
        }
        else if (renderViewEntity instanceof EntityLivingBase && ((EntityPlayer)renderViewEntity).isPotionActive(Potion.blindness)) {
            float fogEnd = 5.0f;
            final int duration = ((EntityPlayer)renderViewEntity).getActivePotionEffect(Potion.blindness).getDuration();
            if (duration < 20) {
                fogEnd = 5.0f + (this.farPlaneDistance - 5.0f) * (1.0f - duration / 20.0f);
            }
            GlStateManager.setFog(9729);
            if (n == -1) {
                GlStateManager.setFogStart(0.0f);
                GlStateManager.setFogEnd(fogEnd * 0.8f);
            }
            else {
                GlStateManager.setFogStart(fogEnd * 0.25f);
                GlStateManager.setFogEnd(fogEnd);
            }
            if (GLContext.getCapabilities().GL_NV_fog_distance && Config.isFogFancy()) {
                GL11.glFogi(34138, 34139);
            }
        }
        else if (this.cloudFog) {
            GlStateManager.setFog(2048);
            GlStateManager.setFogDensity(0.1f);
        }
        else if (blockAtEntityViewpoint.getMaterial() == Material.water) {
            GlStateManager.setFog(2048);
            if (renderViewEntity instanceof EntityLivingBase && ((EntityPlayer)renderViewEntity).isPotionActive(Potion.waterBreathing)) {
                GlStateManager.setFogDensity(0.01f);
            }
            else {
                GlStateManager.setFogDensity(0.1f - EnchantmentHelper.getRespiration(renderViewEntity) * 0.03f);
            }
            if (Config.isClearWater()) {
                GL11.glFogf(2914, 0.02f);
            }
        }
        else if (blockAtEntityViewpoint.getMaterial() == Material.lava) {
            GlStateManager.setFog(2048);
            GlStateManager.setFogDensity(2.0f);
        }
        else {
            final float farPlaneDistance = this.farPlaneDistance;
            this.fogStandard = true;
            GlStateManager.setFog(9729);
            if (n == -1) {
                GlStateManager.setFogStart(0.0f);
                GlStateManager.setFogEnd(farPlaneDistance);
            }
            else {
                GlStateManager.setFogStart(farPlaneDistance * Config.getFogStart());
                GlStateManager.setFogEnd(farPlaneDistance);
            }
            if (GLContext.getCapabilities().GL_NV_fog_distance) {
                if (Config.isFogFancy()) {
                    GL11.glFogi(34138, 34139);
                }
                if (Config.isFogFast()) {
                    GL11.glFogi(34138, 34140);
                }
            }
            if (this.mc.theWorld.provider.doesXZShowFog((int)renderViewEntity.posX, (int)renderViewEntity.posZ)) {
                GlStateManager.setFogStart(farPlaneDistance * 0.05f);
                GlStateManager.setFogEnd(farPlaneDistance);
            }
            if (Reflector.ForgeHooksClient_onFogRender.exists()) {
                Reflector.callVoid(Reflector.ForgeHooksClient_onFogRender, this, renderViewEntity, blockAtEntityViewpoint, n2, n, farPlaneDistance);
            }
        }
        GlStateManager.colorMaterial(1028, 4608);
    }
}
