package net.minecraft.client.gui;

import java.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.*;
import net.minecraft.client.resources.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.*;
import optfine.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import com.nquantum.event.impl.*;
import net.minecraft.scoreboard.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.*;
import net.minecraft.world.border.*;

public class GuiIngame extends Gui
{
    private int field_175192_A;
    private final GuiPlayerTabOverlay overlayPlayerList;
    private int lastPlayerHealth;
    private String field_175200_y;
    private int playerHealth;
    public float prevVignetteBrightness;
    private int field_175193_B;
    private int field_175199_z;
    private static final ResourceLocation vignetteTexPath;
    private String field_175201_x;
    private final Random rand;
    private static final ResourceLocation pumpkinBlurTexPath;
    private ItemStack highlightingItemStack;
    private int recordPlayingUpFor;
    private long lastSystemTime;
    private final GuiSpectator spectatorGui;
    private static final ResourceLocation widgetsTexPath;
    private int remainingHighlightTicks;
    private int updateCounter;
    private final RenderItem itemRenderer;
    private String recordPlaying;
    private final GuiStreamIndicator streamIndicator;
    private final GuiNewChat persistantChatGUI;
    private final Minecraft mc;
    private int field_175195_w;
    private final GuiOverlayDebug overlayDebug;
    private static final String __OBFID = "CL_00000661";
    private long healthUpdateCounter;
    private boolean recordIsPlaying;
    
    public GuiSpectator getSpectatorGui() {
        return this.spectatorGui;
    }
    
    public void setRecordPlayingMessage(final String s) {
        this.setRecordPlaying(I18n.format("record.nowPlaying", s), true);
    }
    
    public void renderDemo(final ScaledResolution scaledResolution) {
        this.mc.mcProfiler.startSection("demo");
        String s;
        if (this.mc.theWorld.getTotalWorldTime() >= 120500L) {
            s = I18n.format("demo.demoExpired", new Object[0]);
        }
        else {
            s = I18n.format("demo.remainingTime", StringUtils.ticksToElapsedTime((int)(120500L - this.mc.theWorld.getTotalWorldTime())));
        }
        this.getFontRenderer().drawStringWithShadow(s, (float)(scaledResolution.getScaledWidth() - this.getFontRenderer().getStringWidth(s) - 10), 5.0f, 16777215);
        this.mc.mcProfiler.endSection();
    }
    
    private void func_180474_b(float n, final ScaledResolution scaledResolution) {
        if (n < 1.0f) {
            n *= n;
            n *= n;
            n = n * 0.8f + 0.2f;
        }
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(1.0f, 1.0f, 1.0f, n);
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        final TextureAtlasSprite texture = this.mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(Blocks.portal.getDefaultState());
        final float minU = texture.getMinU();
        final float minV = texture.getMinV();
        final float maxU = texture.getMaxU();
        final float maxV = texture.getMaxV();
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(0.0, scaledResolution.getScaledHeight(), -90.0).tex(minU, maxV).endVertex();
        worldRenderer.pos(scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), -90.0).tex(maxU, maxV).endVertex();
        worldRenderer.pos(scaledResolution.getScaledWidth(), 0.0, -90.0).tex(maxU, minV).endVertex();
        worldRenderer.pos(0.0, 0.0, -90.0).tex(minU, minV).endVertex();
        instance.draw();
        GlStateManager.depthMask(true);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public void setRecordPlaying(final IChatComponent chatComponent, final boolean b) {
        this.setRecordPlaying(chatComponent.getUnformattedText(), b);
    }
    
    public void renderHorseJumpBar(final ScaledResolution scaledResolution, final int n) {
        this.mc.mcProfiler.startSection("jumpBar");
        this.mc.getTextureManager().bindTexture(Gui.icons);
        final int n2 = (int)(this.mc.thePlayer.getHorseJumpPower() * 183);
        final int n3 = scaledResolution.getScaledHeight() - 32 + 3;
        this.drawTexturedModalRect(n, n3, 0, 84, 182, 5);
        if (n2 > 0) {
            this.drawTexturedModalRect(n, n3, 0, 89, n2, 5);
        }
        this.mc.mcProfiler.endSection();
    }
    
    private void renderScoreboard(final ScoreObjective scoreObjective, final ScaledResolution scaledResolution) {
    }
    
    public void func_181551_a(final ScaledResolution scaledResolution) {
        this.mc.mcProfiler.startSection("selectedItemName");
        if (this.remainingHighlightTicks > 0 && this.highlightingItemStack != null) {
            String s = this.highlightingItemStack.getDisplayName();
            if (this.highlightingItemStack.hasDisplayName()) {
                s = EnumChatFormatting.ITALIC + s;
            }
            final int n = (scaledResolution.getScaledWidth() - this.getFontRenderer().getStringWidth(s)) / 2;
            int n2 = scaledResolution.getScaledHeight() - 59;
            if (!this.mc.playerController.shouldDrawHUD()) {
                n2 += 14;
            }
            final int n3 = (int)(this.remainingHighlightTicks * 256.0f / 10.0f);
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            this.getFontRenderer().drawStringWithShadow(s, (float)n, (float)n2, -1);
        }
        this.mc.mcProfiler.endSection();
    }
    
    public void renderGameOverlay(final float n) {
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        final int scaledWidth = scaledResolution.getScaledWidth();
        final int scaledHeight = scaledResolution.getScaledHeight();
        this.mc.entityRenderer.setupOverlayRendering();
        if (Config.isVignetteEnabled()) {
            this.renderVignette(this.mc.thePlayer.getBrightness(n), scaledResolution);
        }
        else {
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        }
        final ItemStack armorItemInSlot = this.mc.thePlayer.inventory.armorItemInSlot(3);
        if (this.mc.gameSettings.thirdPersonView == 0 && armorItemInSlot != null && armorItemInSlot.getItem() == Item.getItemFromBlock(Blocks.pumpkin)) {
            this.renderPumpkinOverlay(scaledResolution);
        }
        if (!this.mc.thePlayer.isPotionActive(Potion.confusion)) {
            final float n2 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * n;
            if (n2 > 0.0f) {
                this.func_180474_b(n2, scaledResolution);
            }
        }
        if (this.mc.playerController.isSpectator()) {
            this.spectatorGui.renderTooltip(scaledResolution, n);
        }
        else {
            this.renderTooltip(scaledResolution, n);
        }
        new Event2D((float)scaledResolution.getScaledWidth(), (float)scaledResolution.getScaledHeight()).call();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiIngame.icons);
        if (this != 0) {
            GlStateManager.tryBlendFuncSeparate(775, 769, 1, 0);
            this.drawTexturedModalRect(scaledWidth / 2 - 7, scaledHeight / 2 - 7, 0, 0, 16, 16);
        }
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        this.mc.mcProfiler.startSection("bossHealth");
        this.renderBossHealth();
        this.mc.mcProfiler.endSection();
        if (this.mc.playerController.shouldDrawHUD()) {
            this.renderPlayerStats(scaledResolution);
        }
        if (this.mc.thePlayer.getSleepTimer() > 0) {
            this.mc.mcProfiler.startSection("sleep");
            final int sleepTimer = this.mc.thePlayer.getSleepTimer();
            float n3 = sleepTimer / 100.0f;
            if (n3 > 1.0f) {
                n3 = 1.0f - (sleepTimer - 100) / 10.0f;
            }
            final int n4 = (int)(220.0f * n3) << 24 | 0x101020;
            Gui.drawRect(0.0, 0.0, scaledWidth, scaledHeight, 255);
            this.mc.mcProfiler.endSection();
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final int n5 = scaledWidth / 2 - 91;
        if (this.mc.thePlayer.isRidingHorse()) {
            this.renderHorseJumpBar(scaledResolution, n5);
        }
        else if (this.mc.playerController.gameIsSurvivalOrAdventure()) {
            this.renderExpBar(scaledResolution, n5);
        }
        if (this.mc.gameSettings.heldItemTooltips && !this.mc.playerController.isSpectator()) {
            this.func_181551_a(scaledResolution);
        }
        else if (this.mc.thePlayer.isSpectator()) {
            this.spectatorGui.func_175263_a(scaledResolution);
        }
        if (this.mc.isDemo()) {
            this.renderDemo(scaledResolution);
        }
        if (this.mc.gameSettings.showDebugInfo) {
            this.overlayDebug.renderDebugInfo(scaledResolution);
        }
        if (this.recordPlayingUpFor > 0) {
            this.mc.mcProfiler.startSection("overlayMessage");
            final float n6 = this.recordPlayingUpFor - n;
            final int n7 = (int)(n6 * 255.0f / 20.0f);
            GlStateManager.translate((float)(scaledWidth / 2), (float)(scaledHeight - 68), 0.0f);
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            if (this.recordIsPlaying) {
                final int n8 = MathHelper.func_181758_c(n6 / 50.0f, 0.7f, 0.6f) & 0xFFFFFF;
            }
            this.getFontRenderer().drawString(this.recordPlaying, -this.getFontRenderer().getStringWidth(this.recordPlaying) / 2, -4.0, -1);
            this.mc.mcProfiler.endSection();
        }
        if (this.field_175195_w > 0) {
            this.mc.mcProfiler.startSection("titleAndSubtitle");
            final float n9 = this.field_175195_w - n;
            if (this.field_175195_w > this.field_175193_B + this.field_175192_A) {
                final int n10 = (int)((this.field_175199_z + this.field_175192_A + this.field_175193_B - n9) * 255.0f / this.field_175199_z);
            }
            if (this.field_175195_w <= this.field_175193_B) {
                final int n11 = (int)(n9 * 255.0f / this.field_175193_B);
            }
            MathHelper.clamp_int(255, 0, 255);
            GlStateManager.translate((float)(scaledWidth / 2), (float)(scaledHeight / 2), 0.0f);
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.scale(4.0f, 4.0f, 4.0f);
            this.getFontRenderer().drawString(this.field_175201_x, (float)(-this.getFontRenderer().getStringWidth(this.field_175201_x) / 2), -10.0f, 16777215, true);
            GlStateManager.scale(2.0f, 2.0f, 2.0f);
            this.getFontRenderer().drawString(this.field_175200_y.replace("hypixel.net", "asyncware.wtf").replace("craftplay.pl", "asyncware.wtf"), (float)(-this.getFontRenderer().getStringWidth(this.field_175200_y) / 2), 5.0f, 16777215, true);
            this.mc.mcProfiler.endSection();
        }
        final Scoreboard scoreboard = this.mc.theWorld.getScoreboard();
        ScoreObjective objectiveInDisplaySlot = null;
        final ScorePlayerTeam playersTeam = scoreboard.getPlayersTeam(this.mc.thePlayer.getName());
        if (playersTeam != null) {
            final int colorIndex = playersTeam.getChatFormat().getColorIndex();
            if (colorIndex >= 0) {
                objectiveInDisplaySlot = scoreboard.getObjectiveInDisplaySlot(3 + colorIndex);
            }
        }
        final ScoreObjective scoreObjective = (objectiveInDisplaySlot != null) ? objectiveInDisplaySlot : scoreboard.getObjectiveInDisplaySlot(1);
        if (scoreObjective != null) {
            this.renderScoreboard(scoreObjective, scaledResolution);
        }
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.translate(0.0f, (float)(scaledHeight - 48), 0.0f);
        this.mc.mcProfiler.startSection("chat");
        this.persistantChatGUI.drawChat(this.updateCounter);
        this.mc.mcProfiler.endSection();
        final ScoreObjective objectiveInDisplaySlot2 = scoreboard.getObjectiveInDisplaySlot(0);
        if (!this.mc.gameSettings.keyBindPlayerList.isKeyDown() || (this.mc.isIntegratedServerRunning() && this.mc.thePlayer.sendQueue.getPlayerInfoMap().size() <= 1 && objectiveInDisplaySlot2 == null)) {
            this.overlayPlayerList.updatePlayerList(false);
        }
        else {
            this.overlayPlayerList.updatePlayerList(true);
            this.overlayPlayerList.renderPlayerlist(scaledWidth, scoreboard, objectiveInDisplaySlot2);
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public int getUpdateCounter() {
        return this.updateCounter;
    }
    
    private void renderPumpkinOverlay(final ScaledResolution scaledResolution) {
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiIngame.pumpkinBlurTexPath);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(0.0, scaledResolution.getScaledHeight(), -90.0).tex(0.0, 1.0).endVertex();
        worldRenderer.pos(scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), -90.0).tex(1.0, 1.0).endVertex();
        worldRenderer.pos(scaledResolution.getScaledWidth(), 0.0, -90.0).tex(1.0, 0.0).endVertex();
        worldRenderer.pos(0.0, 0.0, -90.0).tex(0.0, 0.0).endVertex();
        instance.draw();
        GlStateManager.depthMask(true);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public void func_181029_i() {
        this.overlayPlayerList.func_181030_a();
    }
    
    public FontRenderer getFontRenderer() {
        return this.mc.fontRendererObj;
    }
    
    public GuiIngame(final Minecraft mc) {
        this.rand = new Random();
        this.recordPlaying = "";
        this.prevVignetteBrightness = 1.0f;
        this.field_175201_x = "";
        this.field_175200_y = "";
        this.playerHealth = 0;
        this.lastPlayerHealth = 0;
        this.lastSystemTime = 0L;
        this.healthUpdateCounter = 0L;
        this.mc = mc;
        this.itemRenderer = mc.getRenderItem();
        this.overlayDebug = new GuiOverlayDebug(mc);
        this.spectatorGui = new GuiSpectator(mc);
        this.persistantChatGUI = new GuiNewChat(mc);
        this.streamIndicator = new GuiStreamIndicator(mc);
        this.overlayPlayerList = new GuiPlayerTabOverlay(mc, this);
        this.func_175177_a();
    }
    
    private void renderPlayerStats(final ScaledResolution scaledResolution) {
        if (!(this.mc.getRenderViewEntity() instanceof EntityPlayer)) {
            return;
        }
        final EntityPlayer entityPlayer = (EntityPlayer)this.mc.getRenderViewEntity();
        final int ceiling_float_int = MathHelper.ceiling_float_int(entityPlayer.getHealth());
        final boolean b = this.healthUpdateCounter > this.updateCounter && (this.healthUpdateCounter - this.updateCounter) / 3L % 2L == 1L;
        if (ceiling_float_int < this.playerHealth && entityPlayer.hurtResistantTime > 0) {
            this.lastSystemTime = Minecraft.getSystemTime();
            this.healthUpdateCounter = this.updateCounter + 20;
        }
        else if (ceiling_float_int > this.playerHealth && entityPlayer.hurtResistantTime > 0) {
            this.lastSystemTime = Minecraft.getSystemTime();
            this.healthUpdateCounter = this.updateCounter + 10;
        }
        if (Minecraft.getSystemTime() - this.lastSystemTime > 1000L) {
            this.playerHealth = ceiling_float_int;
            this.lastPlayerHealth = ceiling_float_int;
            this.lastSystemTime = Minecraft.getSystemTime();
        }
        this.playerHealth = ceiling_float_int;
        final int lastPlayerHealth = this.lastPlayerHealth;
        this.rand.setSeed(this.updateCounter * 312871);
        final FoodStats foodStats = entityPlayer.getFoodStats();
        foodStats.getFoodLevel();
        foodStats.getPrevFoodLevel();
        final IAttributeInstance entityAttribute = entityPlayer.getEntityAttribute(SharedMonsterAttributes.maxHealth);
        final int n = scaledResolution.getScaledWidth() / 2 - 91;
        final int n2 = scaledResolution.getScaledWidth() / 2 + 91;
        final int n3 = scaledResolution.getScaledHeight() - 39;
        final float n4 = (float)entityAttribute.getAttributeValue();
        final int ceiling_float_int2 = MathHelper.ceiling_float_int((n4 + entityPlayer.getAbsorptionAmount()) / 2.0f / 10.0f);
        final int n5 = n3 - (ceiling_float_int2 - 1) * Math.max(10 - (ceiling_float_int2 - 2), 3) - 10;
        final int totalArmorValue = entityPlayer.getTotalArmorValue();
        if (entityPlayer.isPotionActive(Potion.regeneration)) {
            final int n6 = this.updateCounter % MathHelper.ceiling_float_int(n4 + 5.0f);
        }
        this.mc.mcProfiler.startSection("armor");
        while (true) {
            if (totalArmorValue > 0) {
                if (1 < totalArmorValue) {
                    this.drawTexturedModalRect(0, n5, 34, 9, 9, 9);
                }
                if (totalArmorValue != 0) {
                    this.drawTexturedModalRect(0, n5, 25, 9, 9, 9);
                }
                if (1 > totalArmorValue) {
                    this.drawTexturedModalRect(0, n5, 16, 9, 9, 9);
                }
            }
            int n7 = 0;
            ++n7;
        }
    }
    
    private void renderBossHealth() {
        if (BossStatus.bossName != null && BossStatus.statusBarTime > 0) {
            --BossStatus.statusBarTime;
            final FontRenderer fontRendererObj = this.mc.fontRendererObj;
            final int scaledWidth = new ScaledResolution(this.mc).getScaledWidth();
            final int n = scaledWidth / 2 - 1;
            final int n2 = (int)(BossStatus.healthScale * 183);
            this.drawTexturedModalRect(n, 12, 0, 74, 182, 5);
            this.drawTexturedModalRect(n, 12, 0, 74, 182, 5);
            if (n2 > 0) {
                this.drawTexturedModalRect(n, 12, 0, 79, n2, 5);
            }
            final String bossName = BossStatus.bossName;
            this.getFontRenderer().drawStringWithShadow(bossName, (float)(scaledWidth / 2 - this.getFontRenderer().getStringWidth(bossName) / 2), 2, 16777215);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.mc.getTextureManager().bindTexture(GuiIngame.icons);
        }
    }
    
    private void renderVignette(float clamp_float, final ScaledResolution scaledResolution) {
        if (Config.isVignetteEnabled()) {
            clamp_float = 1.0f - clamp_float;
            clamp_float = MathHelper.clamp_float(clamp_float, 0.0f, 1.0f);
            final WorldBorder worldBorder = this.mc.theWorld.getWorldBorder();
            final float n = (float)worldBorder.getClosestDistance(this.mc.thePlayer);
            final double max = Math.max(worldBorder.getWarningDistance(), Math.min(worldBorder.getResizeSpeed() * worldBorder.getWarningTime() * 1000.0, Math.abs(worldBorder.getTargetSize() - worldBorder.getDiameter())));
            float n2;
            if (n < max) {
                n2 = 1.0f - (float)(n / max);
            }
            else {
                n2 = 0.0f;
            }
            this.prevVignetteBrightness += (float)((clamp_float - this.prevVignetteBrightness) * 0.01);
            GlStateManager.depthMask(false);
            GlStateManager.tryBlendFuncSeparate(0, 769, 1, 0);
            if (n2 > 0.0f) {
                GlStateManager.color(0.0f, n2, n2, 1.0f);
            }
            else {
                GlStateManager.color(this.prevVignetteBrightness, this.prevVignetteBrightness, this.prevVignetteBrightness, 1.0f);
            }
            this.mc.getTextureManager().bindTexture(GuiIngame.vignetteTexPath);
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
            worldRenderer.pos(0.0, scaledResolution.getScaledHeight(), -90.0).tex(0.0, 1.0).endVertex();
            worldRenderer.pos(scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), -90.0).tex(1.0, 1.0).endVertex();
            worldRenderer.pos(scaledResolution.getScaledWidth(), 0.0, -90.0).tex(1.0, 0.0).endVertex();
            worldRenderer.pos(0.0, 0.0, -90.0).tex(0.0, 0.0).endVertex();
            instance.draw();
            GlStateManager.depthMask(true);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        }
    }
    
    public void updateTick() {
        if (this.recordPlayingUpFor > 0) {
            --this.recordPlayingUpFor;
        }
        if (this.field_175195_w > 0) {
            --this.field_175195_w;
            if (this.field_175195_w <= 0) {
                this.field_175201_x = "";
                this.field_175200_y = "";
            }
        }
        ++this.updateCounter;
        this.streamIndicator.func_152439_a();
        if (this.mc.thePlayer != null) {
            final ItemStack currentItem = this.mc.thePlayer.inventory.getCurrentItem();
            if (currentItem == null) {
                this.remainingHighlightTicks = 0;
            }
            else if (this.highlightingItemStack != null && currentItem.getItem() == this.highlightingItemStack.getItem() && ItemStack.areItemStackTagsEqual(currentItem, this.highlightingItemStack) && (currentItem.isItemStackDamageable() || currentItem.getMetadata() == this.highlightingItemStack.getMetadata())) {
                if (this.remainingHighlightTicks > 0) {
                    --this.remainingHighlightTicks;
                }
            }
            else {
                this.remainingHighlightTicks = 40;
            }
            this.highlightingItemStack = currentItem;
        }
    }
    
    public GuiPlayerTabOverlay getTabList() {
        return this.overlayPlayerList;
    }
    
    public void func_175177_a() {
        this.field_175199_z = 10;
        this.field_175192_A = 70;
        this.field_175193_B = 20;
    }
    
    public void renderExpBar(final ScaledResolution scaledResolution, final int n) {
        this.mc.mcProfiler.startSection("expBar");
        this.mc.getTextureManager().bindTexture(Gui.icons);
        if (this.mc.thePlayer.xpBarCap() > 0) {
            final int n2 = (int)(this.mc.thePlayer.experience * 8453921);
            final int n3 = scaledResolution.getScaledHeight() - 32 + 3;
            this.drawTexturedModalRect(n, n3, 0, 64, 8453920, 5);
            if (n2 > 0) {
                this.drawTexturedModalRect(n, n3, 0, 69, n2, 5);
            }
        }
        this.mc.mcProfiler.endSection();
        if (this.mc.thePlayer.experienceLevel > 0) {
            this.mc.mcProfiler.startSection("expLevel");
            final String string = "" + this.mc.thePlayer.experienceLevel;
            final int n4 = (scaledResolution.getScaledWidth() - this.getFontRenderer().getStringWidth(string)) / 2;
            final int n5 = scaledResolution.getScaledHeight() - 31 - 4;
            this.getFontRenderer().drawString(string, n4 + 1, n5, 0);
            this.getFontRenderer().drawString(string, n4 - 1, n5, 0);
            this.getFontRenderer().drawString(string, n4, n5 + 1, 0);
            this.getFontRenderer().drawString(string, n4, n5 - 1, 0);
            this.getFontRenderer().drawString(string, n4, n5, 8453920);
            this.mc.mcProfiler.endSection();
        }
    }
    
    public void displayTitle(final String field_175201_x, final String field_175200_y, final int field_175199_z, final int field_175192_A, final int field_175193_B) {
        if (field_175201_x == null && field_175200_y == null && field_175199_z < 0 && field_175192_A < 0 && field_175193_B < 0) {
            this.field_175201_x = "";
            this.field_175200_y = "";
            this.field_175195_w = 0;
        }
        else if (field_175201_x != null) {
            this.field_175201_x = field_175201_x;
            this.field_175195_w = this.field_175199_z + this.field_175192_A + this.field_175193_B;
        }
        else if (field_175200_y != null) {
            this.field_175200_y = field_175200_y;
        }
        else {
            if (field_175199_z >= 0) {
                this.field_175199_z = field_175199_z;
            }
            if (field_175192_A >= 0) {
                this.field_175192_A = field_175192_A;
            }
            if (field_175193_B >= 0) {
                this.field_175193_B = field_175193_B;
            }
            if (this.field_175195_w > 0) {
                this.field_175195_w = this.field_175199_z + this.field_175192_A + this.field_175193_B;
            }
        }
    }
    
    protected void renderTooltip(final ScaledResolution scaledResolution, final float n) {
        if (!(this.mc.getRenderViewEntity() instanceof EntityPlayer)) {
            return;
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiIngame.widgetsTexPath);
        final EntityPlayer entityPlayer = (EntityPlayer)this.mc.getRenderViewEntity();
        final int n2 = scaledResolution.getScaledWidth() / 2;
        final float zLevel = this.zLevel;
        this.zLevel = -90.0f;
        this.drawTexturedModalRect(n2 - 91, scaledResolution.getScaledHeight() - 22, 0, 0, 182, 22);
        this.drawTexturedModalRect(n2 - 91 - 1 + entityPlayer.inventory.currentItem * 20, scaledResolution.getScaledHeight() - 22 - 1, 0, 22, 24, 22);
        this.zLevel = zLevel;
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        while (true) {
            this.renderHotbarItem(0, scaledResolution.getScaledWidth() / 2 - 90 + 0 + 2, scaledResolution.getScaledHeight() - 16 - 3, n, entityPlayer);
            int n3 = 0;
            ++n3;
        }
    }
    
    private void renderHotbarItem(final int p0, final int p1, final int p2, final float p3, final EntityPlayer p4) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     2: getfield        net/minecraft/entity/player/EntityPlayer.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //     5: getfield        net/minecraft/entity/player/InventoryPlayer.mainInventory:[Lnet/minecraft/item/ItemStack;
        //     8: iload_1        
        //     9: aaload         
        //    10: astore          6
        //    12: aload           6
        //    14: ifnull          121
        //    17: aload           6
        //    19: getfield        net/minecraft/item/ItemStack.animationsToGo:I
        //    22: i2f            
        //    23: fload           4
        //    25: fsub           
        //    26: fstore          7
        //    28: fload           7
        //    30: fconst_0       
        //    31: fcmpl          
        //    32: ifle            88
        //    35: fconst_1       
        //    36: fload           7
        //    38: ldc             5.0
        //    40: fdiv           
        //    41: fadd           
        //    42: fstore          8
        //    44: iload_2        
        //    45: bipush          8
        //    47: iadd           
        //    48: i2f            
        //    49: iload_3        
        //    50: bipush          12
        //    52: iadd           
        //    53: i2f            
        //    54: fconst_0       
        //    55: invokestatic    net/minecraft/client/renderer/GlStateManager.translate:(FFF)V
        //    58: fconst_1       
        //    59: fload           8
        //    61: fdiv           
        //    62: fload           8
        //    64: fconst_1       
        //    65: fadd           
        //    66: fconst_2       
        //    67: fdiv           
        //    68: fconst_1       
        //    69: invokestatic    net/minecraft/client/renderer/GlStateManager.scale:(FFF)V
        //    72: iload_2        
        //    73: bipush          8
        //    75: iadd           
        //    76: ineg           
        //    77: i2f            
        //    78: iload_3        
        //    79: bipush          12
        //    81: iadd           
        //    82: ineg           
        //    83: i2f            
        //    84: fconst_0       
        //    85: invokestatic    net/minecraft/client/renderer/GlStateManager.translate:(FFF)V
        //    88: aload_0        
        //    89: getfield        net/minecraft/client/gui/GuiIngame.itemRenderer:Lnet/minecraft/client/renderer/entity/RenderItem;
        //    92: aload           6
        //    94: iload_2        
        //    95: iload_3        
        //    96: invokevirtual   net/minecraft/client/renderer/entity/RenderItem.renderItemAndEffectIntoGUI:(Lnet/minecraft/item/ItemStack;II)V
        //    99: fload           7
        //   101: fconst_0       
        //   102: fcmpl          
        //   103: aload_0        
        //   104: getfield        net/minecraft/client/gui/GuiIngame.itemRenderer:Lnet/minecraft/client/renderer/entity/RenderItem;
        //   107: aload_0        
        //   108: getfield        net/minecraft/client/gui/GuiIngame.mc:Lnet/minecraft/client/Minecraft;
        //   111: getfield        net/minecraft/client/Minecraft.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //   114: aload           6
        //   116: iload_2        
        //   117: iload_3        
        //   118: invokevirtual   net/minecraft/client/renderer/entity/RenderItem.renderItemOverlays:(Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/item/ItemStack;II)V
        //   121: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0121 (coming from #0118).
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
    
    public void renderStreamIndicator(final ScaledResolution scaledResolution) {
        this.streamIndicator.render(scaledResolution.getScaledWidth() - 10, 10);
    }
    
    static {
        vignetteTexPath = new ResourceLocation("textures/misc/vignette.png");
        widgetsTexPath = new ResourceLocation("textures/gui/widgets.png");
        pumpkinBlurTexPath = new ResourceLocation("textures/misc/pumpkinblur.png");
    }
    
    public GuiNewChat getChatGUI() {
        return this.persistantChatGUI;
    }
    
    public void setRecordPlaying(final String recordPlaying, final boolean recordIsPlaying) {
        this.recordPlaying = recordPlaying;
        this.recordPlayingUpFor = 60;
        this.recordIsPlaying = recordIsPlaying;
    }
}
