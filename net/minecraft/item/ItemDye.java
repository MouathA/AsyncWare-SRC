package net.minecraft.item;

import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.util.*;

public class ItemDye extends Item
{
    @Override
    public void getSubItems(final Item item, final CreativeTabs creativeTabs, final List list) {
        while (true) {
            list.add(new ItemStack(item, 1, 0));
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public boolean itemInteractionForEntity(final ItemStack itemStack, final EntityPlayer entityPlayer, final EntityLivingBase entityLivingBase) {
        if (entityLivingBase instanceof EntitySheep) {
            final EntitySheep entitySheep = (EntitySheep)entityLivingBase;
            final EnumDyeColor byDyeDamage = EnumDyeColor.byDyeDamage(itemStack.getMetadata());
            if (!entitySheep.getSheared() && entitySheep.getFleeceColor() != byDyeDamage) {
                entitySheep.setFleeceColor(byDyeDamage);
                --itemStack.stackSize;
            }
            return true;
        }
        return false;
    }
    
    public ItemDye() {
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }
    
    static {
        ItemDye.dyeColors = new int[] { 1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 11250603, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320 };
    }
    
    public static void spawnBonemealParticles(final World world, final BlockPos blockPos, final int n) {
        final Block block = world.getBlockState(blockPos).getBlock();
        if (block.getMaterial() == Material.air) {
            return;
        }
        block.setBlockBoundsBasedOnState(world, blockPos);
        while (true) {
            world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, blockPos.getX() + ItemDye.itemRand.nextFloat(), blockPos.getY() + ItemDye.itemRand.nextFloat() * block.getBlockBoundsMaxY(), blockPos.getZ() + ItemDye.itemRand.nextFloat(), ItemDye.itemRand.nextGaussian() * 0.02, ItemDye.itemRand.nextGaussian() * 0.02, ItemDye.itemRand.nextGaussian() * 0.02, new int[0]);
            int n2 = 0;
            ++n2;
        }
    }
    
    @Override
    public boolean onItemUse(final ItemStack p0, final EntityPlayer p1, final World p2, final BlockPos p3, final EnumFacing p4, final float p5, final float p6, final float p7) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload           4
        //     3: aload           5
        //     5: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;
        //     8: aload           5
        //    10: aload_1        
        //    11: invokevirtual   net/minecraft/entity/player/EntityPlayer.canPlayerEdit:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;Lnet/minecraft/item/ItemStack;)Z
        //    14: ifne            19
        //    17: iconst_0       
        //    18: ireturn        
        //    19: aload_1        
        //    20: invokevirtual   net/minecraft/item/ItemStack.getMetadata:()I
        //    23: invokestatic    net/minecraft/item/EnumDyeColor.byDyeDamage:(I)Lnet/minecraft/item/EnumDyeColor;
        //    26: astore          9
        //    28: aload           9
        //    30: getstatic       net/minecraft/item/EnumDyeColor.WHITE:Lnet/minecraft/item/EnumDyeColor;
        //    33: if_acmpne       62
        //    36: aload_1        
        //    37: aload_3        
        //    38: aload           4
        //    40: ifeq            202
        //    43: aload_3        
        //    44: getfield        net/minecraft/world/World.isRemote:Z
        //    47: ifne            60
        //    50: aload_3        
        //    51: sipush          2005
        //    54: aload           4
        //    56: iconst_0       
        //    57: invokevirtual   net/minecraft/world/World.playAuxSFX:(ILnet/minecraft/util/BlockPos;I)V
        //    60: iconst_1       
        //    61: ireturn        
        //    62: aload           9
        //    64: getstatic       net/minecraft/item/EnumDyeColor.BROWN:Lnet/minecraft/item/EnumDyeColor;
        //    67: if_acmpne       202
        //    70: aload_3        
        //    71: aload           4
        //    73: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //    76: astore          10
        //    78: aload           10
        //    80: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //    85: astore          11
        //    87: aload           11
        //    89: getstatic       net/minecraft/init/Blocks.log:Lnet/minecraft/block/Block;
        //    92: if_acmpne       202
        //    95: aload           10
        //    97: getstatic       net/minecraft/block/BlockPlanks.VARIANT:Lnet/minecraft/block/properties/PropertyEnum;
        //   100: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //   105: getstatic       net/minecraft/block/BlockPlanks$EnumType.JUNGLE:Lnet/minecraft/block/BlockPlanks$EnumType;
        //   108: if_acmpne       202
        //   111: aload           5
        //   113: getstatic       net/minecraft/util/EnumFacing.DOWN:Lnet/minecraft/util/EnumFacing;
        //   116: if_acmpne       121
        //   119: iconst_0       
        //   120: ireturn        
        //   121: aload           5
        //   123: getstatic       net/minecraft/util/EnumFacing.UP:Lnet/minecraft/util/EnumFacing;
        //   126: if_acmpne       131
        //   129: iconst_0       
        //   130: ireturn        
        //   131: aload           4
        //   133: aload           5
        //   135: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;
        //   138: astore          4
        //   140: aload_3        
        //   141: aload           4
        //   143: invokevirtual   net/minecraft/world/World.isAirBlock:(Lnet/minecraft/util/BlockPos;)Z
        //   146: ifeq            200
        //   149: getstatic       net/minecraft/init/Blocks.cocoa:Lnet/minecraft/block/Block;
        //   152: aload_3        
        //   153: aload           4
        //   155: aload           5
        //   157: fload           6
        //   159: fload           7
        //   161: fload           8
        //   163: iconst_0       
        //   164: aload_2        
        //   165: invokevirtual   net/minecraft/block/Block.onBlockPlaced:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;FFFILnet/minecraft/entity/EntityLivingBase;)Lnet/minecraft/block/state/IBlockState;
        //   168: astore          12
        //   170: aload_3        
        //   171: aload           4
        //   173: aload           12
        //   175: iconst_2       
        //   176: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z
        //   179: pop            
        //   180: aload_2        
        //   181: getfield        net/minecraft/entity/player/EntityPlayer.capabilities:Lnet/minecraft/entity/player/PlayerCapabilities;
        //   184: getfield        net/minecraft/entity/player/PlayerCapabilities.isCreativeMode:Z
        //   187: ifne            200
        //   190: aload_1        
        //   191: dup            
        //   192: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   195: iconst_1       
        //   196: isub           
        //   197: putfield        net/minecraft/item/ItemStack.stackSize:I
        //   200: iconst_1       
        //   201: ireturn        
        //   202: iconst_0       
        //   203: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0202 (coming from #0040).
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
    public String getUnlocalizedName(final ItemStack itemStack) {
        return super.getUnlocalizedName() + "." + EnumDyeColor.byDyeDamage(itemStack.getMetadata()).getUnlocalizedName();
    }
}
