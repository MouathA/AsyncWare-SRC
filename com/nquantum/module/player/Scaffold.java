package com.nquantum.module.player;

import net.minecraft.client.entity.*;
import net.minecraft.client.multiplayer.*;
import com.nquantum.event.*;
import net.minecraft.client.gui.*;
import com.nquantum.*;
import com.nquantum.module.customize.*;
import com.nquantum.util.block.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import java.util.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.settings.*;
import com.nquantum.module.*;
import java.awt.*;
import com.nquantum.util.render.*;
import com.nquantum.event.impl.*;
import com.nquantum.util.*;
import net.minecraft.client.*;

public class Scaffold extends Module
{
    private Random rng;
    public static transient BlockPos lastPlace;
    public static transient BlockPos lastBlockPos;
    private boolean rotating;
    private int slot;
    private EnumFacing[] facings;
    private List invalidBlocks;
    private BlockPos[] blockPositions;
    private List validBlocks;
    public static transient BlockPos offsets;
    public static transient EnumFacing lastFacing;
    private float[] angles;
    
    @Punjabi
    @Override
    public void onUpdate(final EventUpdate eventUpdate) {
        final EntityPlayerSP thePlayer = this.mc.thePlayer;
        final WorldClient theWorld = this.mc.theWorld;
    }
    
    public float[] getRotations(final BlockUtil blockUtil) {
        if (blockUtil != null && blockUtil.blockPos != null && blockUtil.facing != null) {
            final double n = blockUtil.blockPos.getX() + 0.5 - this.mc.thePlayer.posX + blockUtil.facing.getFrontOffsetX() / 2.0;
            final double n2 = blockUtil.blockPos.getZ() + 0.5 - this.mc.thePlayer.posZ + blockUtil.facing.getFrontOffsetZ() / 2.0;
            return new float[] { (float)(Math.atan2(n2, n) * 180.0 / 3.141592653589793) - 45.0f, (float)(Math.atan2(this.mc.thePlayer.posY + this.mc.thePlayer.getEyeHeight() - (blockUtil.blockPos.getY() + 0.5), MathHelper.sqrt_double(n * n + n2 * n2)) * 180.0 / 3.141592653589793) };
        }
        return new float[] { this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch };
    }
    
    public static Vec3 getVec3(final BlockPos blockPos) {
        return new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }
    
    @Punjabi
    @Override
    public void onSend(final EventSendPacket eventSendPacket) {
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        this.mc.thePlayer.inventory.currentItem = this.slot;
    }
    
    @Punjabi
    @Override
    public void on2D(final Event2D event2D) {
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        final String string = this.getBlockCount() + "";
        Asyncware.s.drawStringWithShadow(string, (float)(scaledResolution.getScaledWidth() / 2 + 10), (float)(scaledResolution.getScaledHeight() / 2), HUD.hudColor);
        Asyncware.s.drawStringWithShadow("blocks", (float)(scaledResolution.getScaledWidth() / 2 + 10 + Asyncware.sf.getStringWidth(string) - 2), (float)(scaledResolution.getScaledHeight() / 2), -1);
    }
    
    private double randomNumber(final double n, final double n2) {
        return Math.random() * (n - n2) + n2;
    }
    
    private BlockData getBlockData(final BlockPos blockPos) {
        final BlockPos[] blockPositions = this.blockPositions;
        final EnumFacing[] facings = this.facings;
        final WorldClient theWorld = this.mc.theWorld;
        final BlockPos blockPos2 = new BlockPos(0, -1, 0);
        final List validBlocks = this.validBlocks;
        if (!validBlocks.contains(theWorld.getBlockState(blockPos.add(blockPos2)).getBlock())) {
            return new BlockData(blockPos.add(blockPos2), EnumFacing.UP);
        }
        final int length = blockPositions.length;
        while (0 < length) {
            final BlockPos add = blockPos.add(blockPositions[0]);
            if (!validBlocks.contains(theWorld.getBlockState(add).getBlock())) {
                return new BlockData(add, facings[0]);
            }
            while (0 < length) {
                final BlockPos add2 = blockPos.add(blockPositions[0]);
                final BlockPos add3 = add.add(blockPositions[0]);
                if (!validBlocks.contains(theWorld.getBlockState(add2).getBlock())) {
                    return new BlockData(add2, facings[0]);
                }
                if (!validBlocks.contains(theWorld.getBlockState(add3).getBlock())) {
                    return new BlockData(add3, facings[0]);
                }
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        return null;
    }
    
    private Vec3 getVec3(final BlockData blockData) {
        final BlockPos pos = blockData.pos;
        final EnumFacing face = blockData.face;
        final double n = pos.getX() + 0.5;
        final double n2 = pos.getY() + 0.5;
        final double n3 = pos.getZ() + 0.5;
        double n4 = n + face.getFrontOffsetX() / 2.0;
        double n5 = n3 + face.getFrontOffsetZ() / 2.0;
        double n6 = n2 + face.getFrontOffsetY() / 2.0;
        if (face != EnumFacing.UP && face != EnumFacing.DOWN) {
            n6 += this.randomNumber(0.49, 0.5);
        }
        else {
            n4 += this.randomNumber(0.3, -0.3);
            n5 += this.randomNumber(0.3, -0.3);
        }
        if (face == EnumFacing.WEST || face == EnumFacing.EAST) {
            n5 += this.randomNumber(0.3, -0.3);
        }
        if (face == EnumFacing.SOUTH || face == EnumFacing.NORTH) {
            n4 += this.randomNumber(0.3, -0.3);
        }
        return new Vec3(n4, n6, n5);
    }
    
    @Override
    public void setup() {
        super.setup();
        this.invalidBlocks = Arrays.asList(Blocks.enchanting_table, Blocks.furnace, Blocks.carpet, Blocks.crafting_table, Blocks.trapped_chest, Blocks.chest, Blocks.dispenser, Blocks.air, Blocks.water, Blocks.lava, Blocks.flowing_water, Blocks.flowing_lava, Blocks.sand, Blocks.snow_layer, Blocks.torch, Blocks.anvil, Blocks.jukebox, Blocks.stone_button, Blocks.wooden_button, Blocks.lever, Blocks.noteblock, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.wooden_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_slab, Blocks.wooden_slab, Blocks.stone_slab2, Blocks.red_mushroom, Blocks.brown_mushroom, Blocks.yellow_flower, Blocks.red_flower, Blocks.anvil, Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.cactus, Blocks.ladder, Blocks.web);
        this.validBlocks = Arrays.asList(Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava);
        this.blockPositions = new BlockPos[] { new BlockPos(-1, 0, 0), new BlockPos(1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, 0, 1) };
        this.facings = new EnumFacing[] { EnumFacing.EAST, EnumFacing.WEST, EnumFacing.SOUTH, EnumFacing.NORTH };
        this.rng = new Random();
        this.angles = new float[2];
        Asyncware.instance.settingsManager.rSetting(new Setting("Should Render", this, false));
    }
    
    @Punjabi
    @Override
    public void onReceive(final EventReceivePacket eventReceivePacket) {
    }
    
    public int getBlockCount() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        com/nquantum/module/player/Scaffold.mc:Lnet/minecraft/client/Minecraft;
        //     4: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //     7: getfield        net/minecraft/client/entity/EntityPlayerSP.inventoryContainer:Lnet/minecraft/inventory/Container;
        //    10: iconst_0       
        //    11: invokevirtual   net/minecraft/inventory/Container.getSlot:(I)Lnet/minecraft/inventory/Slot;
        //    14: invokevirtual   net/minecraft/inventory/Slot.getHasStack:()Z
        //    17: ifeq            67
        //    20: aload_0        
        //    21: getfield        com/nquantum/module/player/Scaffold.mc:Lnet/minecraft/client/Minecraft;
        //    24: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    27: getfield        net/minecraft/client/entity/EntityPlayerSP.inventoryContainer:Lnet/minecraft/inventory/Container;
        //    30: iconst_0       
        //    31: invokevirtual   net/minecraft/inventory/Container.getSlot:(I)Lnet/minecraft/inventory/Slot;
        //    34: invokevirtual   net/minecraft/inventory/Slot.getStack:()Lnet/minecraft/item/ItemStack;
        //    37: astore_3       
        //    38: aload_3        
        //    39: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //    42: astore          4
        //    44: aload_3        
        //    45: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //    48: instanceof      Lnet/minecraft/item/ItemBlock;
        //    51: ifeq            67
        //    54: aload_0        
        //    55: aload           4
        //    57: ifeq            67
        //    60: iconst_0       
        //    61: aload_3        
        //    62: getfield        net/minecraft/item/ItemStack.stackSize:I
        //    65: iadd           
        //    66: istore_1       
        //    67: iinc            2, 1
        //    70: goto            0
        //    73: iconst_0       
        //    74: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0067 (coming from #0057).
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
    
    static {
        Scaffold.lastPlace = null;
        Scaffold.lastBlockPos = null;
        Scaffold.offsets = BlockPos.ORIGIN;
        Scaffold.lastFacing = null;
    }
    
    public Scaffold() {
        super("Scaffold", 48, Category.MOVEMENT);
    }
    
    @Punjabi
    @Override
    public void on3D(final Event3D event3D) {
        final WorldClient theWorld = this.mc.theWorld;
        final EntityPlayerSP thePlayer = this.mc.thePlayer;
        if (Asyncware.instance.settingsManager.getSettingByName("Should Render").getValBoolean()) {
            for (double n = thePlayer.posY - 1.0; n > 0.0; --n) {
                final BlockData blockData = this.getBlockData(new BlockPos(thePlayer.posX + -Math.sin(Math.toRadians(this.mc.thePlayer.rotationYawHead)) * 2.0, n, thePlayer.posZ));
                if (blockData != null && thePlayer.posY - n <= 3.0) {
                    RenderUtil.drawBlockBox(blockData.pos, new Color(10, 255, 10, 255), false);
                    RenderUtil.drawBlockBox(blockData.pos, new Color(10, 255, 10, 255), false);
                    break;
                }
            }
        }
        final BlockPos blockPos = new BlockPos(this.mc.thePlayer.getPosition().add(-Math.sin(Math.toRadians(this.mc.thePlayer.rotationYawHead)) * 1.0, -1.0, 0.0));
    }
    
    @Punjabi
    @Override
    public void onPost(final EventPostMotionUpdate p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        com/nquantum/module/player/Scaffold.mc:Lnet/minecraft/client/Minecraft;
        //     4: getfield        net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //     7: astore_2       
        //     8: aload_0        
        //     9: getfield        com/nquantum/module/player/Scaffold.mc:Lnet/minecraft/client/Minecraft;
        //    12: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    15: astore_3       
        //    16: dconst_1       
        //    17: dstore          4
        //    19: aconst_null    
        //    20: astore          6
        //    22: aload_0        
        //    23: getfield        com/nquantum/module/player/Scaffold.mc:Lnet/minecraft/client/Minecraft;
        //    26: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    29: aload_0        
        //    30: getfield        com/nquantum/module/player/Scaffold.mc:Lnet/minecraft/client/Minecraft;
        //    33: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    36: getfield        net/minecraft/client/entity/EntityPlayerSP.prevRotationPitch:F
        //    39: putfield        net/minecraft/client/entity/EntityPlayerSP.rotationPitch:F
        //    42: aload_0        
        //    43: getfield        com/nquantum/module/player/Scaffold.mc:Lnet/minecraft/client/Minecraft;
        //    46: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    49: aload_0        
        //    50: getfield        com/nquantum/module/player/Scaffold.mc:Lnet/minecraft/client/Minecraft;
        //    53: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    56: getfield        net/minecraft/client/entity/EntityPlayerSP.prevRotationYaw:F
        //    59: putfield        net/minecraft/client/entity/EntityPlayerSP.rotationYaw:F
        //    62: aload_3        
        //    63: getfield        net/minecraft/client/entity/EntityPlayerSP.posY:D
        //    66: dconst_1       
        //    67: dsub           
        //    68: dstore          7
        //    70: dload           7
        //    72: dconst_0       
        //    73: dcmpl          
        //    74: ifle            139
        //    77: aload_0        
        //    78: new             Lnet/minecraft/util/BlockPos;
        //    81: dup            
        //    82: aload_3        
        //    83: getfield        net/minecraft/client/entity/EntityPlayerSP.posX:D
        //    86: dload           7
        //    88: aload_3        
        //    89: getfield        net/minecraft/client/entity/EntityPlayerSP.posZ:D
        //    92: invokespecial   net/minecraft/util/BlockPos.<init>:(DDD)V
        //    95: invokespecial   com/nquantum/module/player/Scaffold.getBlockData:(Lnet/minecraft/util/BlockPos;)Lcom/nquantum/util/block/BlockData;
        //    98: astore          9
        //   100: aload           9
        //   102: ifnull          130
        //   105: aload_3        
        //   106: getfield        net/minecraft/client/entity/EntityPlayerSP.posY:D
        //   109: dload           7
        //   111: dsub           
        //   112: dstore          4
        //   114: dload           4
        //   116: ldc2_w          3.0
        //   119: dcmpg          
        //   120: ifgt            130
        //   123: aload           9
        //   125: astore          6
        //   127: goto            139
        //   130: dload           7
        //   132: dconst_1       
        //   133: dsub           
        //   134: dstore          7
        //   136: goto            70
        //   139: aload_3        
        //   140: getfield        net/minecraft/client/entity/EntityPlayerSP.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //   143: iconst_0       
        //   144: invokevirtual   net/minecraft/entity/player/InventoryPlayer.getStackInSlot:(I)Lnet/minecraft/item/ItemStack;
        //   147: astore          10
        //   149: aload           10
        //   151: ifnull          180
        //   154: aload           10
        //   156: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   159: istore          11
        //   161: aload_0        
        //   162: aload           10
        //   164: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   167: ifeq            180
        //   170: iload           11
        //   172: iconst_0       
        //   173: if_icmple       180
        //   176: iload           11
        //   178: istore          8
        //   180: iinc            9, 1
        //   183: goto            139
        //   186: iconst_m1      
        //   187: iconst_m1      
        //   188: aload           6
        //   190: ifnull          311
        //   193: goto            311
        //   196: aload           6
        //   198: getfield        com/nquantum/util/block/BlockData.pos:Lnet/minecraft/util/BlockPos;
        //   201: astore          9
        //   203: aload_2        
        //   204: aload           9
        //   206: aload           6
        //   208: getfield        com/nquantum/util/block/BlockData.face:Lnet/minecraft/util/EnumFacing;
        //   211: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;
        //   214: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   217: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   222: astore          10
        //   224: aload_0        
        //   225: aload           6
        //   227: invokespecial   com/nquantum/module/player/Scaffold.getVec3:(Lcom/nquantum/util/block/BlockData;)Lnet/minecraft/util/Vec3;
        //   230: astore          11
        //   232: aload_0        
        //   233: getfield        com/nquantum/module/player/Scaffold.validBlocks:Ljava/util/List;
        //   236: aload           10
        //   238: invokeinterface java/util/List.contains:(Ljava/lang/Object;)Z
        //   243: ifeq            252
        //   246: aload_0        
        //   247: dload           4
        //   249: ifne            253
        //   252: return         
        //   253: aload_3        
        //   254: getfield        net/minecraft/client/entity/EntityPlayerSP.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //   257: getfield        net/minecraft/entity/player/InventoryPlayer.currentItem:I
        //   260: istore          12
        //   262: aload_3        
        //   263: getfield        net/minecraft/client/entity/EntityPlayerSP.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //   266: iconst_m1      
        //   267: putfield        net/minecraft/entity/player/InventoryPlayer.currentItem:I
        //   270: aload_0        
        //   271: getfield        com/nquantum/module/player/Scaffold.mc:Lnet/minecraft/client/Minecraft;
        //   274: getfield        net/minecraft/client/Minecraft.playerController:Lnet/minecraft/client/multiplayer/PlayerControllerMP;
        //   277: aload_3        
        //   278: aload_2        
        //   279: aload_3        
        //   280: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getCurrentEquippedItem:()Lnet/minecraft/item/ItemStack;
        //   283: aload           9
        //   285: aload           6
        //   287: getfield        com/nquantum/util/block/BlockData.face:Lnet/minecraft/util/EnumFacing;
        //   290: aload           11
        //   292: invokevirtual   net/minecraft/client/multiplayer/PlayerControllerMP.onPlayerRightClick:(Lnet/minecraft/client/entity/EntityPlayerSP;Lnet/minecraft/client/multiplayer/WorldClient;Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;Lnet/minecraft/util/Vec3;)Z
        //   295: ifeq            302
        //   298: aload_3        
        //   299: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.swingItem:()V
        //   302: aload_3        
        //   303: getfield        net/minecraft/client/entity/EntityPlayerSP.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //   306: iload           12
        //   308: putfield        net/minecraft/entity/player/InventoryPlayer.currentItem:I
        //   311: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0180 (coming from #0167).
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
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.angles[0] = 999.0f;
        this.angles[1] = 999.0f;
        this.slot = this.mc.thePlayer.inventory.currentItem;
    }
    
    @Punjabi
    @Override
    public void onPre(final EventPreMotionUpdate eventPreMotionUpdate) {
        final EntityPlayerSP thePlayer = this.mc.thePlayer;
        final float[] rotations = this.getRotations(new BlockUtil(new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0, this.mc.thePlayer.posZ), EnumFacing.DOWN));
        this.mc.thePlayer.rotationYaw = rotations[0];
        this.mc.thePlayer.rotationPitch = rotations[1];
        this.mc.thePlayer.rotationYawHead = rotations[0];
        this.mc.thePlayer.renderYawOffset = rotations[0];
        if (this.mc.gameSettings.keyBindJump.isKeyDown() && !MovementUtil.isMoving()) {
            thePlayer.motionX = 0.0;
            thePlayer.motionY = 0.42;
            thePlayer.motionZ = 0.0;
        }
    }
    
    public static float[] getFacePos(final Vec3 vec3) {
        final double n = vec3.xCoord + 0.5 - Minecraft.getMinecraft().thePlayer.posX;
        final double n2 = vec3.yCoord + 0.5 - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        final double n3 = vec3.zCoord + 0.5 - Minecraft.getMinecraft().thePlayer.posZ;
        return new float[] { Minecraft.getMinecraft().thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float((float)(Math.atan2(n3, n) * 180.0 / 3.141592653589793) - 90.0f - Minecraft.getMinecraft().thePlayer.rotationYaw), Minecraft.getMinecraft().thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float((float)(-(Math.atan2(n2, MathHelper.sqrt_double(n * n + n3 * n3)) * 180.0 / 3.141592653589793)) - Minecraft.getMinecraft().thePlayer.rotationPitch) };
    }
}
