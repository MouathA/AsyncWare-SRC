package com.nquantum.module.player;

import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import com.nquantum.*;
import com.nquantum.util.color.*;
import com.nquantum.event.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import com.nquantum.util.time.*;
import net.minecraft.network.play.client.*;
import com.nquantum.util.*;
import net.minecraft.network.*;
import net.minecraft.block.*;
import java.util.*;
import com.nquantum.module.*;
import com.nquantum.event.impl.*;

public class trash extends Module
{
    private boolean raycast;
    private boolean rotated;
    private BlockPos curPos;
    double posY;
    private EnumFacing currentFacing;
    private boolean keepRots;
    private float[] rotations;
    private static List invalid;
    private BlockPos currentPos;
    
    @Punjabi
    @Override
    public void on2D(final Event2D event2D) {
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        final String string = this.getBlockCount() + "";
        Asyncware.s.drawStringWithShadow(string, (float)(scaledResolution.getScaledWidth() / 2), (float)(scaledResolution.getScaledHeight() / 2), Colors.RGB());
        Asyncware.s.drawStringWithShadow("blocks", (float)(scaledResolution.getScaledWidth() / 2 + Asyncware.sf.getStringWidth(string) - 2), (float)(scaledResolution.getScaledHeight() / 2), -1);
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    private void setBlockAndFacing(final BlockPos blockPos) {
        if (this.mc.theWorld.getBlockState(blockPos.add(0, -5, 0)).getBlock() != Blocks.air) {
            this.currentPos = blockPos.add(0, -5, 0);
            this.currentFacing = EnumFacing.UP;
        }
        else if (this.mc.theWorld.getBlockState(blockPos.add(-5, 0, 0)).getBlock() != Blocks.air) {
            this.currentPos = blockPos.add(-5, 0, 0);
            this.currentFacing = EnumFacing.EAST;
        }
        else if (this.mc.theWorld.getBlockState(blockPos.add(5, 0, 0)).getBlock() != Blocks.air) {
            this.currentPos = blockPos.add(5, 0, 0);
            this.currentFacing = EnumFacing.WEST;
        }
        else if (this.mc.theWorld.getBlockState(blockPos.add(0, 0, -5)).getBlock() != Blocks.air) {
            this.currentPos = blockPos.add(0, 0, -5);
            this.currentFacing = EnumFacing.SOUTH;
        }
        else if (this.mc.theWorld.getBlockState(blockPos.add(0, 0, 5)).getBlock() != Blocks.air) {
            this.currentPos = blockPos.add(0, 0, 5);
            this.currentFacing = EnumFacing.NORTH;
        }
        else {
            this.currentPos = null;
            this.currentFacing = null;
        }
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
    
    @Punjabi
    @Override
    public void onPost(final EventPostMotionUpdate eventPostMotionUpdate) {
        final Timer timer = new Timer();
        BlockPos add = new BlockPos(this.mc.thePlayer.posX, this.posY - 0.1, this.mc.thePlayer.posZ);
        if (this.mc.gameSettings.keyBindJump.isKeyDown() && !MovementUtil.isMoving()) {
            MovementUtil.setMotion(0.0);
            this.mc.rightClickDelayTimer = 0;
            if (this.mc.thePlayer.onGround) {
                if (MovementUtil.isOnGround(0.76) && !MovementUtil.isOnGround(0.75) && this.mc.thePlayer.motionY > 0.23 && this.mc.thePlayer.motionY < 0.25) {
                    this.mc.thePlayer.motionY = Math.round(this.mc.thePlayer.posY) - this.mc.thePlayer.posY;
                }
                if (MovementUtil.isOnGround(1.0E-4)) {
                    this.mc.thePlayer.swingItem();
                    this.mc.thePlayer.motionY = 0.41999998688697815;
                    if (timer.hasTimeElapsed(1500L, true)) {
                        this.mc.thePlayer.motionY = -0.28;
                        timer.reset();
                    }
                }
                else if (this.mc.thePlayer.posY >= Math.round(this.mc.thePlayer.posY) - 1.0E-4 && this.mc.thePlayer.posY <= Math.round(this.mc.thePlayer.posY) + 1.0E-4) {
                    this.mc.thePlayer.motionY = 0.0;
                }
            }
            else if (this.mc.theWorld.getBlockState(add).getBlock().getMaterial().isReplaceable()) {
                this.mc.thePlayer.motionY = 0.41955;
            }
        }
        if (this.mc.gameSettings.keyBindSneak.isKeyDown()) {
            add = this.mc.thePlayer.getPosition().add(0.0, -2.0, 0.0);
        }
        if (this.mc.thePlayer.getCurrentEquippedItem() != null && this.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock) {
            this.mc.thePlayer.swingItem();
            PacketUtil.sendPacket(new C08PacketPlayerBlockPlacement(add, 1, this.mc.thePlayer.getHeldItem(), 0.0f, 0.0f, 0.0f));
        }
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void setup() {
        super.setup();
    }
    
    static {
        trash.invalid = Arrays.asList(Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.enchanting_table, Blocks.carpet, Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.snow_layer, Blocks.ice, Blocks.packed_ice, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore, Blocks.chest, Blocks.torch, Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.gold_ore, Blocks.iron_ore, Blocks.lapis_ore, Blocks.lit_redstone_ore, Blocks.quartz_ore, Blocks.redstone_ore, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button, Blocks.wooden_button, Blocks.lever);
    }
    
    public trash() {
        super("Scaffold", 48, Category.PLAYER);
        this.raycast = true;
        this.keepRots = true;
        this.rotations = new float[2];
    }
    
    public static BlockPos moveBlockToFace(final BlockPos blockPos, final EnumFacing enumFacing) {
        if (enumFacing == EnumFacing.DOWN) {
            return blockPos.add(0, -1, 0);
        }
        if (enumFacing == EnumFacing.UP) {
            return blockPos.add(0, 1, 0);
        }
        if (enumFacing == EnumFacing.EAST) {
            return blockPos.add(1, 0, 0);
        }
        if (enumFacing == EnumFacing.WEST) {
            return blockPos.add(-1, 0, 0);
        }
        if (enumFacing == EnumFacing.NORTH) {
            return blockPos.add(0, 0, -1);
        }
        if (enumFacing == EnumFacing.SOUTH) {
            return blockPos.add(0, 0, 1);
        }
        return null;
    }
    
    @Punjabi
    @Override
    public void onPre(final EventPreMotionUpdate p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: iconst_0       
        //     2: putfield        com/nquantum/module/player/trash.rotated:Z
        //     5: aload_0        
        //     6: aconst_null    
        //     7: putfield        com/nquantum/module/player/trash.currentPos:Lnet/minecraft/util/BlockPos;
        //    10: aload_0        
        //    11: aconst_null    
        //    12: putfield        com/nquantum/module/player/trash.currentFacing:Lnet/minecraft/util/EnumFacing;
        //    15: aload_0        
        //    16: aload_0        
        //    17: getfield        com/nquantum/module/player/trash.mc:Lnet/minecraft/client/Minecraft;
        //    20: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    23: getfield        net/minecraft/client/entity/EntityPlayerSP.posY:D
        //    26: putfield        com/nquantum/module/player/trash.posY:D
        //    29: invokestatic    com/nquantum/util/MovementUtil.isMoving:()Z
        //    32: ifne            51
        //    35: aload_0        
        //    36: getfield        com/nquantum/module/player/trash.mc:Lnet/minecraft/client/Minecraft;
        //    39: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //    42: getfield        net/minecraft/client/settings/GameSettings.keyBindJump:Lnet/minecraft/client/settings/KeyBinding;
        //    45: invokevirtual   net/minecraft/client/settings/KeyBinding.isKeyDown:()Z
        //    48: ifne            77
        //    51: aload_0        
        //    52: getfield        com/nquantum/module/player/trash.mc:Lnet/minecraft/client/Minecraft;
        //    55: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    58: getfield        net/minecraft/client/entity/EntityPlayerSP.isCollidedVertically:Z
        //    61: ifne            77
        //    64: aload_0        
        //    65: getfield        com/nquantum/module/player/trash.mc:Lnet/minecraft/client/Minecraft;
        //    68: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    71: getfield        net/minecraft/client/entity/EntityPlayerSP.onGround:Z
        //    74: ifeq            95
        //    77: aload_0        
        //    78: aload_0        
        //    79: getfield        com/nquantum/module/player/trash.mc:Lnet/minecraft/client/Minecraft;
        //    82: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    85: getfield        net/minecraft/client/entity/EntityPlayerSP.posY:D
        //    88: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    91: i2d            
        //    92: putfield        com/nquantum/module/player/trash.posY:D
        //    95: new             Lnet/minecraft/util/BlockPos;
        //    98: dup            
        //    99: aload_0        
        //   100: getfield        com/nquantum/module/player/trash.mc:Lnet/minecraft/client/Minecraft;
        //   103: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   106: getfield        net/minecraft/client/entity/EntityPlayerSP.posX:D
        //   109: aload_0        
        //   110: getfield        com/nquantum/module/player/trash.mc:Lnet/minecraft/client/Minecraft;
        //   113: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   116: getfield        net/minecraft/client/entity/EntityPlayerSP.posY:D
        //   119: dconst_1       
        //   120: dsub           
        //   121: aload_0        
        //   122: getfield        com/nquantum/module/player/trash.mc:Lnet/minecraft/client/Minecraft;
        //   125: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   128: getfield        net/minecraft/client/entity/EntityPlayerSP.posZ:D
        //   131: invokespecial   net/minecraft/util/BlockPos.<init>:(DDD)V
        //   134: astore_2       
        //   135: aload_0        
        //   136: getfield        com/nquantum/module/player/trash.mc:Lnet/minecraft/client/Minecraft;
        //   139: getfield        net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   142: aload_2        
        //   143: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   146: instanceof      Lnet/minecraft/block/BlockAir;
        //   149: ifeq            389
        //   152: aload_0        
        //   153: iconst_0       
        //   154: putfield        com/nquantum/module/player/trash.rotated:Z
        //   157: aload_0        
        //   158: aconst_null    
        //   159: putfield        com/nquantum/module/player/trash.currentPos:Lnet/minecraft/util/BlockPos;
        //   162: aload_0        
        //   163: aconst_null    
        //   164: putfield        com/nquantum/module/player/trash.currentFacing:Lnet/minecraft/util/EnumFacing;
        //   167: new             Lnet/minecraft/util/BlockPos;
        //   170: dup            
        //   171: aload_0        
        //   172: getfield        com/nquantum/module/player/trash.mc:Lnet/minecraft/client/Minecraft;
        //   175: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   178: getfield        net/minecraft/client/entity/EntityPlayerSP.posX:D
        //   181: aload_0        
        //   182: getfield        com/nquantum/module/player/trash.mc:Lnet/minecraft/client/Minecraft;
        //   185: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   188: getfield        net/minecraft/client/entity/EntityPlayerSP.posY:D
        //   191: dconst_1       
        //   192: dsub           
        //   193: aload_0        
        //   194: getfield        com/nquantum/module/player/trash.mc:Lnet/minecraft/client/Minecraft;
        //   197: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   200: getfield        net/minecraft/client/entity/EntityPlayerSP.posZ:D
        //   203: invokespecial   net/minecraft/util/BlockPos.<init>:(DDD)V
        //   206: astore_3       
        //   207: aload_0        
        //   208: getfield        com/nquantum/module/player/trash.mc:Lnet/minecraft/client/Minecraft;
        //   211: getfield        net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   214: aload_3        
        //   215: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   218: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   223: instanceof      Lnet/minecraft/block/BlockAir;
        //   226: ifeq            348
        //   229: aload_0        
        //   230: aload_3        
        //   231: invokespecial   com/nquantum/module/player/trash.setBlockAndFacing:(Lnet/minecraft/util/BlockPos;)V
        //   234: aload_0        
        //   235: getfield        com/nquantum/module/player/trash.currentPos:Lnet/minecraft/util/BlockPos;
        //   238: ifnull          375
        //   241: aload_0        
        //   242: getfield        com/nquantum/module/player/trash.currentPos:Lnet/minecraft/util/BlockPos;
        //   245: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //   248: aload_0        
        //   249: getfield        com/nquantum/module/player/trash.currentPos:Lnet/minecraft/util/BlockPos;
        //   252: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   255: aload_0        
        //   256: getfield        com/nquantum/module/player/trash.currentPos:Lnet/minecraft/util/BlockPos;
        //   259: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //   262: aload_0        
        //   263: getfield        com/nquantum/module/player/trash.currentFacing:Lnet/minecraft/util/EnumFacing;
        //   266: invokestatic    com/nquantum/util/block/BlockUtil.getDirectionToBlock:(IIILnet/minecraft/util/EnumFacing;)[F
        //   269: astore          4
        //   271: aload           4
        //   273: iconst_0       
        //   274: faload         
        //   275: fstore          5
        //   277: ldc_w           90.0
        //   280: aload           4
        //   282: iconst_1       
        //   283: faload         
        //   284: ldc_w           9.0
        //   287: fadd           
        //   288: invokestatic    java/lang/Math.min:(FF)F
        //   291: fstore          6
        //   293: aload_0        
        //   294: getfield        com/nquantum/module/player/trash.rotations:[F
        //   297: iconst_0       
        //   298: fload           5
        //   300: fastore        
        //   301: aload_0        
        //   302: getfield        com/nquantum/module/player/trash.rotations:[F
        //   305: iconst_1       
        //   306: fload           6
        //   308: fastore        
        //   309: aload_0        
        //   310: aload_0        
        //   311: getfield        com/nquantum/module/player/trash.raycast:Z
        //   314: ifeq            325
        //   317: aload_0        
        //   318: fload           5
        //   320: fload           6
        //   322: ifnull          329
        //   325: iconst_1       
        //   326: goto            330
        //   329: iconst_0       
        //   330: putfield        com/nquantum/module/player/trash.rotated:Z
        //   333: aload_1        
        //   334: fload           5
        //   336: invokevirtual   com/nquantum/event/impl/EventPreMotionUpdate.setYaw:(F)V
        //   339: aload_1        
        //   340: fload           6
        //   342: invokevirtual   com/nquantum/event/impl/EventPreMotionUpdate.setPitch:(F)V
        //   345: goto            375
        //   348: aload_0        
        //   349: getfield        com/nquantum/module/player/trash.keepRots:Z
        //   352: ifeq            375
        //   355: aload_1        
        //   356: aload_0        
        //   357: getfield        com/nquantum/module/player/trash.rotations:[F
        //   360: iconst_0       
        //   361: faload         
        //   362: invokevirtual   com/nquantum/event/impl/EventPreMotionUpdate.setYaw:(F)V
        //   365: aload_1        
        //   366: aload_0        
        //   367: getfield        com/nquantum/module/player/trash.rotations:[F
        //   370: iconst_1       
        //   371: faload         
        //   372: invokevirtual   com/nquantum/event/impl/EventPreMotionUpdate.setPitch:(F)V
        //   375: aload_0        
        //   376: getfield        com/nquantum/module/player/trash.mc:Lnet/minecraft/client/Minecraft;
        //   379: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   382: aload_1        
        //   383: invokevirtual   com/nquantum/event/impl/EventPreMotionUpdate.getYaw:()F
        //   386: putfield        net/minecraft/client/entity/EntityPlayerSP.rotationYawHead:F
        //   389: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0325 (coming from #0322).
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
}
