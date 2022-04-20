package com.nquantum.module.player;

import com.nquantum.util.time.*;
import net.minecraft.block.*;
import com.nquantum.*;
import net.minecraft.block.material.*;
import com.nquantum.util.*;
import com.nquantum.event.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import java.util.*;
import com.nquantum.event.impl.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import com.nquantum.util.color.*;
import net.minecraft.item.*;
import com.nquantum.module.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.settings.*;

public class OldScaffold extends Module
{
    public boolean grounded;
    public static float progressYaw;
    BlockPos blockUnder;
    private static List invalid;
    public TimerHelper timer;
    BlockPos currentBlockPos;
    public float yaw;
    int currentSlot;
    public float pitch;
    public static float progressPitch;
    BlockPos blockBef;
    int currentItem;
    public static boolean isEnabled;
    int itemStackSize;
    EnumFacing facing;
    public Random rand;
    private int slot;
    public TimerHelper timerMotion;
    public boolean headTurned;
    public int startSlot;
    private static transient List invalidBlocks;
    public static boolean expand;
    private float rotation;
    public static boolean placing;
    
    public Block getBlock(final BlockPos blockPos) {
        return this.mc.theWorld.getBlockState(blockPos).getBlock();
    }
    
    @Punjabi
    @Override
    public void onUpdate(final EventUpdate eventUpdate) {
        this.mc.timer.timerSpeed = 1.0f;
        final boolean valBoolean = Asyncware.instance.settingsManager.getSettingByName("Tower").getValBoolean();
        final double lastTickPosX = this.mc.thePlayer.lastTickPosX;
        final double lastTickPosY = this.mc.thePlayer.lastTickPosY;
        final double lastTickPosZ = this.mc.thePlayer.lastTickPosZ;
        this.blockUnder = null;
        this.blockBef = null;
        if (this.mc.thePlayer.getCurrentEquippedItem() == null) {
            return;
        }
        if (this.mc.thePlayer.getCurrentEquippedItem().getItem() == null) {
            return;
        }
        if (!(this.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock)) {
            return;
        }
        final BlockPos blockPos = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0, this.mc.thePlayer.posZ);
        final BlockPos blockPos2 = new BlockPos(this.mc.thePlayer.posX + 1.0 + this.mc.thePlayer.motionX * 2.0, this.mc.thePlayer.posY - 0.01, this.mc.thePlayer.posZ + 1.0 + this.mc.thePlayer.motionZ * 2.0);
        int n = 0;
        if (this.getBlock(blockPos).getMaterial() == Material.air) {
            this.blockUnder = new BlockPos(this.mc.thePlayer.posX + this.mc.thePlayer.motionX * 2.0, this.mc.thePlayer.posY - 0.01, this.mc.thePlayer.posZ + this.mc.thePlayer.motionZ * 2.0);
            final EnumFacing[] values = EnumFacing.values();
            while (0 < values.length) {
                final EnumFacing facing = values[0];
                final BlockPos offset = this.blockUnder.offset(facing);
                if (this.getBlock(offset).getMaterial() != Material.air) {
                    this.facing = facing;
                    this.blockBef = offset;
                    break;
                }
                ++n;
            }
            if (this.blockBef != null) {
                getFacePos(getVec3(this.blockBef));
            }
        }
        if (this.getBlock(blockPos).getMaterial() == Material.air) {
            this.blockUnder = new BlockPos(this.mc.thePlayer.posX + this.mc.thePlayer.motionX * 2.0, this.mc.thePlayer.posY - 0.01, this.mc.thePlayer.posZ + this.mc.thePlayer.motionZ * 2.0 - 0.10000000149011612);
            final EnumFacing[] values2 = EnumFacing.values();
            while (0 < values2.length) {
                final EnumFacing facing2 = values2[0];
                final BlockPos offset2 = this.blockUnder.offset(facing2);
                if (this.getBlock(offset2).getMaterial() != Material.air) {
                    this.facing = facing2;
                    this.blockBef = offset2;
                    break;
                }
                ++n;
            }
            if (this.blockBef != null) {
                getFacePos(getVec3(this.blockBef));
            }
        }
        final MovingObjectPosition rayTraceBlocks = this.mc.theWorld.rayTraceBlocks(getVec3(this.blockUnder).addVector(0.5, 0.5, 0.5), getVec3(this.blockBef).addVector(0.5, 0.5, 0.5));
        final Vec3 hitVec = rayTraceBlocks.hitVec;
        if (this.mc.gameSettings.keyBindJump.isKeyDown() && !MovementUtil.isMoving() && valBoolean) {
            MovementUtil.setMotion(0.0);
            this.mc.rightClickDelayTimer = 0;
            if (this.mc.thePlayer.onGround) {
                if (MovementUtil.isOnGround(0.76) && !MovementUtil.isOnGround(0.75) && this.mc.thePlayer.motionY > 0.23 && this.mc.thePlayer.motionY < 0.25) {
                    this.mc.thePlayer.motionY = Math.round(this.mc.thePlayer.posY) - this.mc.thePlayer.posY;
                    this.mc.playerController.onPlayerRightClick(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.getCurrentEquippedItem(), this.blockBef, rayTraceBlocks.sideHit, hitVec);
                }
                if (MovementUtil.isOnGround(1.0E-4)) {
                    this.mc.thePlayer.swingItem();
                    this.mc.playerController.onPlayerRightClick(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.getCurrentEquippedItem(), this.blockBef, rayTraceBlocks.sideHit, hitVec);
                    this.mc.thePlayer.motionY = 0.41999998688697815;
                    if (this.timer.hasTimeElapsed(1500.0)) {
                        this.mc.thePlayer.motionY = -0.28;
                        this.timer.reset();
                    }
                }
                else if (this.mc.thePlayer.posY >= Math.round(this.mc.thePlayer.posY) - 1.0E-4 && this.mc.thePlayer.posY <= Math.round(this.mc.thePlayer.posY) + 1.0E-4) {
                    this.mc.thePlayer.motionY = 0.0;
                }
            }
            else if (this.mc.theWorld.getBlockState(this.blockUnder).getBlock().getMaterial().isReplaceable()) {
                this.mc.playerController.onPlayerRightClick(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.getCurrentEquippedItem(), this.blockBef, rayTraceBlocks.sideHit, hitVec);
                this.mc.thePlayer.motionY = 0.41955;
            }
        }
        OldScaffold.placing = false;
        if (this.blockUnder == null) {
            return;
        }
        if (this.blockBef == null) {
            return;
        }
        OldScaffold.placing = true;
        if (rayTraceBlocks == null) {
            return;
        }
        this.mc.thePlayer.swingItem();
        if (this.mc.thePlayer.fallDistance > 1.0f) {
            this.mc.thePlayer.setPosition(lastTickPosX, lastTickPosY, lastTickPosZ);
        }
        this.mc.thePlayer.swingItem();
        this.mc.playerController.onPlayerRightClick(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.getCurrentEquippedItem(), this.blockBef, rayTraceBlocks.sideHit, hitVec);
        this.mc.playerController.onPlayerRightClick(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.getCurrentEquippedItem(), this.blockBef, rayTraceBlocks.sideHit, hitVec);
    }
    
    public static float[] getFacePos(final Vec3 vec3) {
        final double n = vec3.xCoord + 0.5 - Minecraft.getMinecraft().thePlayer.posX;
        final double n2 = vec3.yCoord + 0.5 - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        final double n3 = vec3.zCoord + 0.5 - Minecraft.getMinecraft().thePlayer.posZ;
        return new float[] { Minecraft.getMinecraft().thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float((float)(Math.atan2(n3, n) * 180.0 / 3.141592653589793) - 90.0f - Minecraft.getMinecraft().thePlayer.rotationYaw), Minecraft.getMinecraft().thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float((float)(-(Math.atan2(n2, MathHelper.sqrt_double(n * n + n3 * n3)) * 180.0 / 3.141592653589793)) - Minecraft.getMinecraft().thePlayer.rotationPitch) };
    }
    
    static {
        OldScaffold.invalid = Arrays.asList(Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.enchanting_table, Blocks.carpet, Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.snow_layer, Blocks.ice, Blocks.packed_ice, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore, Blocks.chest, Blocks.torch, Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.gold_ore, Blocks.iron_ore, Blocks.lapis_ore, Blocks.lit_redstone_ore, Blocks.quartz_ore, Blocks.redstone_ore, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button, Blocks.wooden_button, Blocks.lever);
        OldScaffold.placing = false;
        OldScaffold.invalidBlocks = Arrays.asList(Blocks.enchanting_table, Blocks.carpet, Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.snow_layer, Blocks.chest, Blocks.torch, Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button, Blocks.wooden_button, Blocks.lever, Blocks.crafting_table, Blocks.furnace, Blocks.stone_slab, Blocks.wooden_slab, Blocks.stone_slab2, Blocks.brown_mushroom, Blocks.red_mushroom, Blocks.red_flower, Blocks.yellow_flower, Blocks.flower_pot);
    }
    
    @Punjabi
    public void onRenderUI(final Event2D event2D) {
        final Gui gui = new Gui();
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        final String string = this.getBlockCount() + "";
        GL11.glPushMatrix();
        GL11.glTranslatef(scaledResolution.getScaledWidth() / 2.0f - 30.0f, scaledResolution.getScaledHeight() / 2.0f - 20.0f, 30.0f);
        Asyncware.sf.drawString(string, 42.0f, 13.5f, Colors.RGB(), true);
        Asyncware.sf.drawString("blocks", (float)(Asyncware.sf.getStringWidth(string) + 44), 13.5f, -1, true);
        GL11.glPopMatrix();
    }
    
    public int getBlockCount() {
        while (true) {
            if (this.mc.thePlayer.inventoryContainer.getSlot(0).getHasStack()) {
                final ItemStack stack = this.mc.thePlayer.inventoryContainer.getSlot(0).getStack();
                final Item item = stack.getItem();
                if (stack.getItem() instanceof ItemBlock && item == 0) {
                    final int n = 0 + stack.stackSize;
                }
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    public OldScaffold() {
        super("Scaffold", 48, Category.PLAYER);
        this.rand = new Random();
        this.timer = new TimerHelper();
        this.timerMotion = new TimerHelper();
        this.currentBlockPos = new BlockPos(-1, -1, -1);
        this.rotation = 999.0f;
        this.blockUnder = null;
        this.blockBef = null;
        this.facing = null;
    }
    
    @Override
    public void setup() {
        Asyncware.instance.settingsManager.rSetting(new Setting("Tower", this, false));
        Asyncware.instance.settingsManager.rSetting(new Setting("Expand", this, false));
        OldScaffold.expand = Asyncware.instance.settingsManager.getSettingByName("Expand").getValBoolean();
    }
    
    public static Vec3 getVec3(final BlockPos blockPos) {
        return new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }
    
    public float getPlus(float n) {
        if (n < 0.0f) {
            n = n - n - n;
        }
        return n;
    }
    
    public BlockPos getExpanded(BlockPos add) {
        final float n = 1.0f;
        final float n2 = -1.0f;
        final boolean b = this.mc.thePlayer.motionX == this.mc.thePlayer.motionZ;
        add = add.add((!b && this.getPlus((float)this.mc.thePlayer.motionX) > this.getPlus((float)this.mc.thePlayer.motionZ)) ? ((this.mc.thePlayer.motionX > 0.0) ? n : ((this.mc.thePlayer.motionX < 0.0) ? n2 : 0.0f)) : 0.0f, 0.0, (!b && this.getPlus((float)this.mc.thePlayer.motionZ) > this.getPlus((float)this.mc.thePlayer.motionX)) ? ((this.mc.thePlayer.motionZ > 0.0) ? n : ((this.mc.thePlayer.motionZ < 0.0) ? n2 : 0.0f)) : 0.0f);
        return add;
    }
}
