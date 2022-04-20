package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.world.chunk.*;
import net.minecraft.entity.player.*;
import com.google.common.base.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import optfine.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import com.google.common.collect.*;

public class GuiOverlayDebug extends Gui
{
    private static final String __OBFID = "CL_00001956";
    private final FontRenderer fontRenderer;
    private final Minecraft mc;
    
    private void func_181554_e() {
        final FrameTimer func_181539_aj = this.mc.func_181539_aj();
        final int func_181749_a = func_181539_aj.func_181749_a();
        final int func_181750_b = func_181539_aj.func_181750_b();
        final long[] func_181746_c = func_181539_aj.func_181746_c();
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        int i = func_181749_a;
        Gui.drawRect(0.0, scaledResolution.getScaledHeight() - 60, 240.0, scaledResolution.getScaledHeight(), -1873784752);
        while (i != func_181750_b) {
            final int func_181748_a = func_181539_aj.func_181748_a(func_181746_c[i], 30);
            this.drawVerticalLine(0, scaledResolution.getScaledHeight(), scaledResolution.getScaledHeight() - func_181748_a, this.func_181552_c(MathHelper.clamp_int(func_181748_a, 0, 60), 0, 30, 60));
            int n = 0;
            ++n;
            i = func_181539_aj.func_181751_b(i + 1);
        }
        Gui.drawRect(1.0, scaledResolution.getScaledHeight() - 30 + 1, 14.0, scaledResolution.getScaledHeight() - 30 + 10, -1873784752);
        this.fontRenderer.drawString("60", 2.0, scaledResolution.getScaledHeight() - 30 + 2, 14737632);
        this.drawHorizontalLine(0, 239, scaledResolution.getScaledHeight() - 30, -1);
        Gui.drawRect(1.0, scaledResolution.getScaledHeight() - 60 + 1, 14.0, scaledResolution.getScaledHeight() - 60 + 10, -1873784752);
        this.fontRenderer.drawString("30", 2.0, scaledResolution.getScaledHeight() - 60 + 2, 14737632);
        this.drawHorizontalLine(0, 239, scaledResolution.getScaledHeight() - 60, -1);
        this.drawHorizontalLine(0, 239, scaledResolution.getScaledHeight() - 1, -1);
        this.drawVerticalLine(0, scaledResolution.getScaledHeight() - 60, scaledResolution.getScaledHeight(), -1);
        this.drawVerticalLine(239, scaledResolution.getScaledHeight() - 60, scaledResolution.getScaledHeight(), -1);
        if (this.mc.gameSettings.limitFramerate <= 120) {
            this.drawHorizontalLine(0, 239, scaledResolution.getScaledHeight() - 60 + this.mc.gameSettings.limitFramerate / 2, -16711681);
        }
    }
    
    private int func_181552_c(final int n, final int n2, final int n3, final int n4) {
        return (n < n3) ? this.func_181553_a(-16711936, -256, n / (float)n3) : this.func_181553_a(-256, -65536, (n - n3) / (float)(n4 - n3));
    }
    
    protected List call() {
        final BlockPos blockPos = new BlockPos(this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().getEntityBoundingBox().minY, this.mc.getRenderViewEntity().posZ);
        if (this == 0) {
            return Lists.newArrayList((Object[])new String[] { "Minecraft 1.8.8 (" + this.mc.getVersion() + "/" + ClientBrandRetriever.getClientModName() + ")", this.mc.debug, this.mc.renderGlobal.getDebugInfoRenders(), this.mc.renderGlobal.getDebugInfoEntities(), "P: " + this.mc.effectRenderer.getStatistics() + ". T: " + this.mc.theWorld.getDebugLoadedEntities(), this.mc.theWorld.getProviderName(), "", String.format("Chunk-relative: %d %d %d", blockPos.getX() & 0xF, blockPos.getY() & 0xF, blockPos.getZ() & 0xF) });
        }
        final Entity renderViewEntity = this.mc.getRenderViewEntity();
        final EnumFacing horizontalFacing = renderViewEntity.getHorizontalFacing();
        String s = "Invalid";
        switch (GuiOverlayDebug$1.field_178907_a[horizontalFacing.ordinal()]) {
            case 1: {
                s = "Towards negative Z";
                break;
            }
            case 2: {
                s = "Towards positive Z";
                break;
            }
            case 3: {
                s = "Towards negative X";
                break;
            }
            case 4: {
                s = "Towards positive X";
                break;
            }
        }
        final ArrayList arrayList = Lists.newArrayList((Object[])new String[] { "Minecraft 1.8.8 (" + this.mc.getVersion() + "/" + ClientBrandRetriever.getClientModName() + ")", this.mc.debug, this.mc.renderGlobal.getDebugInfoRenders(), this.mc.renderGlobal.getDebugInfoEntities(), "P: " + this.mc.effectRenderer.getStatistics() + ". T: " + this.mc.theWorld.getDebugLoadedEntities(), this.mc.theWorld.getProviderName(), "", String.format("XYZ: %.3f / %.5f / %.3f", this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().getEntityBoundingBox().minY, this.mc.getRenderViewEntity().posZ), String.format("Block: %d %d %d", blockPos.getX(), blockPos.getY(), blockPos.getZ()), String.format("Chunk: %d %d %d in %d %d %d", blockPos.getX() & 0xF, blockPos.getY() & 0xF, blockPos.getZ() & 0xF, blockPos.getX() >> 4, blockPos.getY() >> 4, blockPos.getZ() >> 4), String.format("Facing: %s (%s) (%.1f / %.1f)", horizontalFacing, s, MathHelper.wrapAngleTo180_float(renderViewEntity.rotationYaw), MathHelper.wrapAngleTo180_float(renderViewEntity.rotationPitch)) });
        if (this.mc.theWorld != null && this.mc.theWorld.isBlockLoaded(blockPos)) {
            final Chunk chunkFromBlockCoords = this.mc.theWorld.getChunkFromBlockCoords(blockPos);
            arrayList.add("Biome: " + chunkFromBlockCoords.getBiome(blockPos, this.mc.theWorld.getWorldChunkManager()).biomeName);
            arrayList.add("Light: " + chunkFromBlockCoords.getLightSubtracted(blockPos, 0) + " (" + chunkFromBlockCoords.getLightFor(EnumSkyBlock.SKY, blockPos) + " sky, " + chunkFromBlockCoords.getLightFor(EnumSkyBlock.BLOCK, blockPos) + " block)");
            DifficultyInstance difficultyInstance = this.mc.theWorld.getDifficultyForLocation(blockPos);
            if (this.mc.isIntegratedServerRunning() && this.mc.getIntegratedServer() != null) {
                final EntityPlayerMP playerByUUID = this.mc.getIntegratedServer().getConfigurationManager().getPlayerByUUID(this.mc.thePlayer.getUniqueID());
                if (playerByUUID != null) {
                    difficultyInstance = playerByUUID.worldObj.getDifficultyForLocation(new BlockPos(playerByUUID));
                }
            }
            arrayList.add(String.format("Local Difficulty: %.2f (Day %d)", difficultyInstance.getAdditionalDifficulty(), this.mc.theWorld.getWorldTime() / 24000L));
        }
        if (this.mc.entityRenderer != null && this.mc.entityRenderer.isShaderActive()) {
            arrayList.add("Shader: " + this.mc.entityRenderer.getShaderGroup().getShaderGroupName());
        }
        if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.mc.objectMouseOver.getBlockPos() != null) {
            final BlockPos blockPos2 = this.mc.objectMouseOver.getBlockPos();
            arrayList.add(String.format("Looking at: %d %d %d", blockPos2.getX(), blockPos2.getY(), blockPos2.getZ()));
        }
        return arrayList;
    }
    
    protected void renderDebugInfoLeft() {
        final List call = this.call();
        while (0 < call.size()) {
            final String s = call.get(0);
            if (!Strings.isNullOrEmpty(s)) {
                final int font_HEIGHT = this.fontRenderer.FONT_HEIGHT;
                final int stringWidth = this.fontRenderer.getStringWidth(s);
                final int n = 2 + font_HEIGHT * 0;
                Gui.drawRect(1.0, n - 1, 2 + stringWidth + 1, n + font_HEIGHT - 1, -1873784752);
                this.fontRenderer.drawString(s, 2.0, n, 14737632);
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    public GuiOverlayDebug(final Minecraft mc) {
        this.mc = mc;
        this.fontRenderer = mc.fontRendererObj;
    }
    
    private int func_181553_a(final int n, final int n2, final float n3) {
        final int n4 = n >> 24 & 0xFF;
        final int n5 = n >> 16 & 0xFF;
        final int n6 = n >> 8 & 0xFF;
        final int n7 = n & 0xFF;
        return MathHelper.clamp_int((int)(n4 + ((n2 >> 24 & 0xFF) - n4) * n3), 0, 255) << 24 | MathHelper.clamp_int((int)(n5 + ((n2 >> 16 & 0xFF) - n5) * n3), 0, 255) << 16 | MathHelper.clamp_int((int)(n6 + ((n2 >> 8 & 0xFF) - n6) * n3), 0, 255) << 8 | MathHelper.clamp_int((int)(n7 + ((n2 & 0xFF) - n7) * n3), 0, 255);
    }
    
    protected List getDebugInfoRight() {
        final long maxMemory = Runtime.getRuntime().maxMemory();
        final long totalMemory = Runtime.getRuntime().totalMemory();
        final long n = totalMemory - Runtime.getRuntime().freeMemory();
        final ArrayList arrayList = Lists.newArrayList((Object[])new String[] { String.format("Java: %s %dbit", "\u8f59\u8f52\u8f45\u8f52\u8f1d\u8f45\u8f56\u8f41\u8f40\u8f5a\u8f5c\u8f5d", this.mc.isJava64bit() ? 64 : 32), String.format("Mem: % 2d%% %03d/%03dMB", n * 100L / maxMemory, bytesToMb(n), bytesToMb(maxMemory)), String.format("Allocated: % 2d%% %03dMB", totalMemory * 100L / maxMemory, bytesToMb(totalMemory)), "", String.format("CPU: %s", OpenGlHelper.func_183029_j()), "", String.format("Display: %dx%d (%s)", Display.getWidth(), Display.getHeight(), GL11.glGetString(7936)), GL11.glGetString(7937), GL11.glGetString(7938) });
        if (Reflector.FMLCommonHandler_getBrandings.exists()) {
            final Object call = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
            arrayList.add("");
            arrayList.addAll((Collection<? extends String>)Reflector.call(call, Reflector.FMLCommonHandler_getBrandings, false));
        }
        if (this == 0) {
            return arrayList;
        }
        if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.mc.objectMouseOver.getBlockPos() != null) {
            final BlockPos blockPos = this.mc.objectMouseOver.getBlockPos();
            IBlockState blockState = this.mc.theWorld.getBlockState(blockPos);
            if (this.mc.theWorld.getWorldType() != WorldType.DEBUG_WORLD) {
                blockState = blockState.getBlock().getActualState(blockState, this.mc.theWorld, blockPos);
            }
            arrayList.add("");
            arrayList.add(String.valueOf(Block.blockRegistry.getNameForObject(blockState.getBlock())));
            for (final Map.Entry<K, Comparable<?>> entry : blockState.getProperties().entrySet()) {
                String s = entry.getValue().toString();
                if (entry.getValue() == Boolean.TRUE) {
                    s = EnumChatFormatting.GREEN + s;
                }
                else if (entry.getValue() == Boolean.FALSE) {
                    s = EnumChatFormatting.RED + s;
                }
                arrayList.add(entry.getKey().getName() + ": " + s);
            }
        }
        return arrayList;
    }
    
    protected void renderDebugInfoRight(final ScaledResolution scaledResolution) {
        final List debugInfoRight = this.getDebugInfoRight();
        while (0 < debugInfoRight.size()) {
            final String s = debugInfoRight.get(0);
            if (!Strings.isNullOrEmpty(s)) {
                final int font_HEIGHT = this.fontRenderer.FONT_HEIGHT;
                final int stringWidth = this.fontRenderer.getStringWidth(s);
                final int n = scaledResolution.getScaledWidth() - 2 - stringWidth;
                final int n2 = 2 + font_HEIGHT * 0;
                Gui.drawRect(n - 1, n2 - 1, n + stringWidth + 1, n2 + font_HEIGHT - 1, -1873784752);
                this.fontRenderer.drawString(s, n, n2, 14737632);
            }
            int n3 = 0;
            ++n3;
        }
    }
    
    private static long bytesToMb(final long n) {
        return n / 1024L / 1024L;
    }
    
    public void renderDebugInfo(final ScaledResolution scaledResolution) {
        this.mc.mcProfiler.startSection("debug");
        this.renderDebugInfoLeft();
        this.renderDebugInfoRight(scaledResolution);
        this.mc.mcProfiler.endSection();
    }
    
    static final class GuiOverlayDebug$1
    {
        private static final String __OBFID = "CL_00001955";
        
        static {
            (GuiOverlayDebug$1.field_178907_a = new int[EnumFacing.values().length])[EnumFacing.NORTH.ordinal()] = 1;
            GuiOverlayDebug$1.field_178907_a[EnumFacing.SOUTH.ordinal()] = 2;
            GuiOverlayDebug$1.field_178907_a[EnumFacing.WEST.ordinal()] = 3;
            GuiOverlayDebug$1.field_178907_a[EnumFacing.EAST.ordinal()] = 4;
        }
    }
}
