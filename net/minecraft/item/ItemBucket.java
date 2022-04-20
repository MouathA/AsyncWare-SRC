package net.minecraft.item;

import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.creativetab.*;

public class ItemBucket extends Item
{
    private Block isFull;
    
    private ItemStack fillBucket(final ItemStack itemStack, final EntityPlayer entityPlayer, final Item item) {
        if (entityPlayer.capabilities.isCreativeMode) {
            return itemStack;
        }
        if (--itemStack.stackSize <= 0) {
            return new ItemStack(item);
        }
        if (!entityPlayer.inventory.addItemStackToInventory(new ItemStack(item))) {
            entityPlayer.dropPlayerItemWithRandomChoice(new ItemStack(item, 1, 0), false);
        }
        return itemStack;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack p0, final World p1, final EntityPlayer p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/item/ItemBucket.isFull:Lnet/minecraft/block/Block;
        //     4: getstatic       net/minecraft/init/Blocks.air:Lnet/minecraft/block/Block;
        //     7: if_acmpne       14
        //    10: iconst_1       
        //    11: goto            15
        //    14: iconst_0       
        //    15: istore          4
        //    17: aload_0        
        //    18: aload_2        
        //    19: aload_3        
        //    20: iload           4
        //    22: invokevirtual   net/minecraft/item/ItemBucket.getMovingObjectPositionFromPlayer:(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;Z)Lnet/minecraft/util/MovingObjectPosition;
        //    25: astore          5
        //    27: aload           5
        //    29: ifnonnull       34
        //    32: aload_1        
        //    33: areturn        
        //    34: aload           5
        //    36: getfield        net/minecraft/util/MovingObjectPosition.typeOfHit:Lnet/minecraft/util/MovingObjectPosition$MovingObjectType;
        //    39: getstatic       net/minecraft/util/MovingObjectPosition$MovingObjectType.BLOCK:Lnet/minecraft/util/MovingObjectPosition$MovingObjectType;
        //    42: if_acmpne       319
        //    45: aload           5
        //    47: invokevirtual   net/minecraft/util/MovingObjectPosition.getBlockPos:()Lnet/minecraft/util/BlockPos;
        //    50: astore          6
        //    52: aload_2        
        //    53: aload_3        
        //    54: aload           6
        //    56: invokevirtual   net/minecraft/world/World.isBlockModifiable:(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/BlockPos;)Z
        //    59: ifne            64
        //    62: aload_1        
        //    63: areturn        
        //    64: iload           4
        //    66: ifeq            229
        //    69: aload_3        
        //    70: aload           6
        //    72: aload           5
        //    74: getfield        net/minecraft/util/MovingObjectPosition.sideHit:Lnet/minecraft/util/EnumFacing;
        //    77: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;
        //    80: aload           5
        //    82: getfield        net/minecraft/util/MovingObjectPosition.sideHit:Lnet/minecraft/util/EnumFacing;
        //    85: aload_1        
        //    86: invokevirtual   net/minecraft/entity/player/EntityPlayer.canPlayerEdit:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;Lnet/minecraft/item/ItemStack;)Z
        //    89: ifne            94
        //    92: aload_1        
        //    93: areturn        
        //    94: aload_2        
        //    95: aload           6
        //    97: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   100: astore          7
        //   102: aload           7
        //   104: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   109: invokevirtual   net/minecraft/block/Block.getMaterial:()Lnet/minecraft/block/material/Material;
        //   112: astore          8
        //   114: aload           8
        //   116: getstatic       net/minecraft/block/material/Material.water:Lnet/minecraft/block/material/Material;
        //   119: if_acmpne       170
        //   122: aload           7
        //   124: getstatic       net/minecraft/block/BlockLiquid.LEVEL:Lnet/minecraft/block/properties/PropertyInteger;
        //   127: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //   132: checkcast       Ljava/lang/Integer;
        //   135: invokevirtual   java/lang/Integer.intValue:()I
        //   138: ifne            170
        //   141: aload_2        
        //   142: aload           6
        //   144: invokevirtual   net/minecraft/world/World.setBlockToAir:(Lnet/minecraft/util/BlockPos;)Z
        //   147: pop            
        //   148: aload_3        
        //   149: getstatic       net/minecraft/stats/StatList.objectUseStats:[Lnet/minecraft/stats/StatBase;
        //   152: aload_0        
        //   153: invokestatic    net/minecraft/item/Item.getIdFromItem:(Lnet/minecraft/item/Item;)I
        //   156: aaload         
        //   157: invokevirtual   net/minecraft/entity/player/EntityPlayer.triggerAchievement:(Lnet/minecraft/stats/StatBase;)V
        //   160: aload_0        
        //   161: aload_1        
        //   162: aload_3        
        //   163: getstatic       net/minecraft/init/Items.water_bucket:Lnet/minecraft/item/Item;
        //   166: invokespecial   net/minecraft/item/ItemBucket.fillBucket:(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/item/Item;)Lnet/minecraft/item/ItemStack;
        //   169: areturn        
        //   170: aload           8
        //   172: getstatic       net/minecraft/block/material/Material.lava:Lnet/minecraft/block/material/Material;
        //   175: if_acmpne       226
        //   178: aload           7
        //   180: getstatic       net/minecraft/block/BlockLiquid.LEVEL:Lnet/minecraft/block/properties/PropertyInteger;
        //   183: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //   188: checkcast       Ljava/lang/Integer;
        //   191: invokevirtual   java/lang/Integer.intValue:()I
        //   194: ifne            226
        //   197: aload_2        
        //   198: aload           6
        //   200: invokevirtual   net/minecraft/world/World.setBlockToAir:(Lnet/minecraft/util/BlockPos;)Z
        //   203: pop            
        //   204: aload_3        
        //   205: getstatic       net/minecraft/stats/StatList.objectUseStats:[Lnet/minecraft/stats/StatBase;
        //   208: aload_0        
        //   209: invokestatic    net/minecraft/item/Item.getIdFromItem:(Lnet/minecraft/item/Item;)I
        //   212: aaload         
        //   213: invokevirtual   net/minecraft/entity/player/EntityPlayer.triggerAchievement:(Lnet/minecraft/stats/StatBase;)V
        //   216: aload_0        
        //   217: aload_1        
        //   218: aload_3        
        //   219: getstatic       net/minecraft/init/Items.lava_bucket:Lnet/minecraft/item/Item;
        //   222: invokespecial   net/minecraft/item/ItemBucket.fillBucket:(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/item/Item;)Lnet/minecraft/item/ItemStack;
        //   225: areturn        
        //   226: goto            319
        //   229: aload_0        
        //   230: getfield        net/minecraft/item/ItemBucket.isFull:Lnet/minecraft/block/Block;
        //   233: getstatic       net/minecraft/init/Blocks.air:Lnet/minecraft/block/Block;
        //   236: if_acmpne       250
        //   239: new             Lnet/minecraft/item/ItemStack;
        //   242: dup            
        //   243: getstatic       net/minecraft/init/Items.bucket:Lnet/minecraft/item/Item;
        //   246: invokespecial   net/minecraft/item/ItemStack.<init>:(Lnet/minecraft/item/Item;)V
        //   249: areturn        
        //   250: aload           6
        //   252: aload           5
        //   254: getfield        net/minecraft/util/MovingObjectPosition.sideHit:Lnet/minecraft/util/EnumFacing;
        //   257: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;
        //   260: astore          7
        //   262: aload_3        
        //   263: aload           7
        //   265: aload           5
        //   267: getfield        net/minecraft/util/MovingObjectPosition.sideHit:Lnet/minecraft/util/EnumFacing;
        //   270: aload_1        
        //   271: invokevirtual   net/minecraft/entity/player/EntityPlayer.canPlayerEdit:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;Lnet/minecraft/item/ItemStack;)Z
        //   274: ifne            279
        //   277: aload_1        
        //   278: areturn        
        //   279: aload_0        
        //   280: aload_2        
        //   281: aload           7
        //   283: if_acmpne       319
        //   286: aload_3        
        //   287: getfield        net/minecraft/entity/player/EntityPlayer.capabilities:Lnet/minecraft/entity/player/PlayerCapabilities;
        //   290: getfield        net/minecraft/entity/player/PlayerCapabilities.isCreativeMode:Z
        //   293: ifne            319
        //   296: aload_3        
        //   297: getstatic       net/minecraft/stats/StatList.objectUseStats:[Lnet/minecraft/stats/StatBase;
        //   300: aload_0        
        //   301: invokestatic    net/minecraft/item/Item.getIdFromItem:(Lnet/minecraft/item/Item;)I
        //   304: aaload         
        //   305: invokevirtual   net/minecraft/entity/player/EntityPlayer.triggerAchievement:(Lnet/minecraft/stats/StatBase;)V
        //   308: new             Lnet/minecraft/item/ItemStack;
        //   311: dup            
        //   312: getstatic       net/minecraft/init/Items.bucket:Lnet/minecraft/item/Item;
        //   315: invokespecial   net/minecraft/item/ItemStack.<init>:(Lnet/minecraft/item/Item;)V
        //   318: areturn        
        //   319: aload_1        
        //   320: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0319 (coming from #0283).
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
    
    public ItemBucket(final Block isFull) {
        this.maxStackSize = 1;
        this.isFull = isFull;
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
}
