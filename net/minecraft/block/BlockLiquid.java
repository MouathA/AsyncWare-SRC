package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.world.biome.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public abstract class BlockLiquid extends Block
{
    public static final PropertyInteger LEVEL;
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        return blockAccess.getBlockState(blockPos).getBlock().getMaterial() != this.blockMaterial && (enumFacing == EnumFacing.UP || super.shouldSideBeRendered(blockAccess, blockPos, enumFacing));
    }
    
    protected BlockLiquid(final Material material) {
        super(material);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockLiquid.LEVEL, 0));
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        this.setTickRandomly(true);
    }
    
    public static double getFlowDirection(final IBlockAccess blockAccess, final BlockPos blockPos, final Material material) {
        final Vec3 flowVector = getFlowingBlock(material).getFlowVector(blockAccess, blockPos);
        return (flowVector.xCoord == 0.0 && flowVector.zCoord == 0.0) ? -1000.0 : (MathHelper.func_181159_b(flowVector.zCoord, flowVector.xCoord) - 1.5707963267948966);
    }
    
    @Override
    public boolean canCollideCheck(final IBlockState blockState, final boolean b) {
        return b && (int)blockState.getValue(BlockLiquid.LEVEL) == 0;
    }
    
    public static BlockStaticLiquid getStaticBlock(final Material material) {
        if (material == Material.water) {
            return Blocks.water;
        }
        if (material == Material.lava) {
            return Blocks.lava;
        }
        throw new IllegalArgumentException("Invalid material");
    }
    
    @Override
    public int tickRate(final World world) {
        return (this.blockMaterial == Material.water) ? 5 : ((this.blockMaterial == Material.lava) ? (world.provider.getHasNoSky() ? 10 : 30) : 0);
    }
    
    @Override
    public Vec3 modifyAcceleration(final World world, final BlockPos blockPos, final Entity entity, final Vec3 vec3) {
        return vec3.add(this.getFlowVector(world, blockPos));
    }
    
    public static BlockDynamicLiquid getFlowingBlock(final Material material) {
        if (material == Material.water) {
            return Blocks.flowing_water;
        }
        if (material == Material.lava) {
            return Blocks.flowing_lava;
        }
        throw new IllegalArgumentException("Invalid material");
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return (this.blockMaterial == Material.water) ? EnumWorldBlockLayer.TRANSLUCENT : EnumWorldBlockLayer.SOLID;
    }
    
    protected int getEffectiveFlowDecay(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final int level = this.getLevel(blockAccess, blockPos);
        return (level >= 8) ? 0 : level;
    }
    
    protected int getLevel(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return (int)((blockAccess.getBlockState(blockPos).getBlock().getMaterial() == this.blockMaterial) ? blockAccess.getBlockState(blockPos).getValue(BlockLiquid.LEVEL) : -1);
    }
    
    @Override
    public boolean isPassable(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return this.blockMaterial != Material.lava;
    }
    
    protected Vec3 getFlowVector(final IBlockAccess p0, final BlockPos p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: dconst_0       
        //     5: dconst_0       
        //     6: dconst_0       
        //     7: invokespecial   net/minecraft/util/Vec3.<init>:(DDD)V
        //    10: astore_3       
        //    11: aload_0        
        //    12: aload_1        
        //    13: aload_2        
        //    14: invokevirtual   net/minecraft/block/BlockLiquid.getEffectiveFlowDecay:(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/BlockPos;)I
        //    17: istore          4
        //    19: getstatic       net/minecraft/util/EnumFacing$Plane.HORIZONTAL:Lnet/minecraft/util/EnumFacing$Plane;
        //    22: invokevirtual   net/minecraft/util/EnumFacing$Plane.iterator:()Ljava/util/Iterator;
        //    25: astore          5
        //    27: aload           5
        //    29: invokeinterface java/util/Iterator.hasNext:()Z
        //    34: ifeq            236
        //    37: aload           5
        //    39: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    44: astore          6
        //    46: aload           6
        //    48: checkcast       Lnet/minecraft/util/EnumFacing;
        //    51: astore          7
        //    53: aload_2        
        //    54: aload           7
        //    56: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;
        //    59: astore          8
        //    61: aload_0        
        //    62: aload_1        
        //    63: aload           8
        //    65: invokevirtual   net/minecraft/block/BlockLiquid.getEffectiveFlowDecay:(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/BlockPos;)I
        //    68: istore          9
        //    70: iload           9
        //    72: ifge            174
        //    75: aload_1        
        //    76: aload           8
        //    78: invokeinterface net/minecraft/world/IBlockAccess.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //    83: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //    88: invokevirtual   net/minecraft/block/Block.getMaterial:()Lnet/minecraft/block/material/Material;
        //    91: invokevirtual   net/minecraft/block/material/Material.blocksMovement:()Z
        //    94: ifne            233
        //    97: aload_0        
        //    98: aload_1        
        //    99: aload           8
        //   101: invokevirtual   net/minecraft/util/BlockPos.down:()Lnet/minecraft/util/BlockPos;
        //   104: invokevirtual   net/minecraft/block/BlockLiquid.getEffectiveFlowDecay:(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/BlockPos;)I
        //   107: istore          9
        //   109: iload           9
        //   111: iflt            233
        //   114: iload           9
        //   116: iload           4
        //   118: bipush          8
        //   120: isub           
        //   121: isub           
        //   122: istore          10
        //   124: aload_3        
        //   125: aload           8
        //   127: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //   130: aload_2        
        //   131: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //   134: isub           
        //   135: iload           10
        //   137: imul           
        //   138: i2d            
        //   139: aload           8
        //   141: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   144: aload_2        
        //   145: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   148: isub           
        //   149: iload           10
        //   151: imul           
        //   152: i2d            
        //   153: aload           8
        //   155: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //   158: aload_2        
        //   159: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //   162: isub           
        //   163: iload           10
        //   165: imul           
        //   166: i2d            
        //   167: invokevirtual   net/minecraft/util/Vec3.addVector:(DDD)Lnet/minecraft/util/Vec3;
        //   170: astore_3       
        //   171: goto            27
        //   174: iload           9
        //   176: iflt            233
        //   179: iload           9
        //   181: iload           4
        //   183: isub           
        //   184: istore          10
        //   186: aload_3        
        //   187: aload           8
        //   189: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //   192: aload_2        
        //   193: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //   196: isub           
        //   197: iload           10
        //   199: imul           
        //   200: i2d            
        //   201: aload           8
        //   203: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   206: aload_2        
        //   207: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   210: isub           
        //   211: iload           10
        //   213: imul           
        //   214: i2d            
        //   215: aload           8
        //   217: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //   220: aload_2        
        //   221: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //   224: isub           
        //   225: iload           10
        //   227: imul           
        //   228: i2d            
        //   229: invokevirtual   net/minecraft/util/Vec3.addVector:(DDD)Lnet/minecraft/util/Vec3;
        //   232: astore_3       
        //   233: goto            27
        //   236: aload_1        
        //   237: aload_2        
        //   238: invokeinterface net/minecraft/world/IBlockAccess.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   243: getstatic       net/minecraft/block/BlockLiquid.LEVEL:Lnet/minecraft/block/properties/PropertyInteger;
        //   246: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //   251: checkcast       Ljava/lang/Integer;
        //   254: invokevirtual   java/lang/Integer.intValue:()I
        //   257: bipush          8
        //   259: if_icmplt       344
        //   262: getstatic       net/minecraft/util/EnumFacing$Plane.HORIZONTAL:Lnet/minecraft/util/EnumFacing$Plane;
        //   265: invokevirtual   net/minecraft/util/EnumFacing$Plane.iterator:()Ljava/util/Iterator;
        //   268: astore          5
        //   270: aload           5
        //   272: invokeinterface java/util/Iterator.hasNext:()Z
        //   277: ifeq            344
        //   280: aload           5
        //   282: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   287: astore          6
        //   289: aload           6
        //   291: checkcast       Lnet/minecraft/util/EnumFacing;
        //   294: astore          7
        //   296: aload_2        
        //   297: aload           7
        //   299: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;
        //   302: astore          8
        //   304: aload_0        
        //   305: aload_1        
        //   306: aload           8
        //   308: aload           7
        //   310: if_acmpne       325
        //   313: aload_0        
        //   314: aload_1        
        //   315: aload           8
        //   317: invokevirtual   net/minecraft/util/BlockPos.up:()Lnet/minecraft/util/BlockPos;
        //   320: aload           7
        //   322: if_acmpne       341
        //   325: aload_3        
        //   326: invokevirtual   net/minecraft/util/Vec3.normalize:()Lnet/minecraft/util/Vec3;
        //   329: dconst_0       
        //   330: ldc2_w          -6.0
        //   333: dconst_0       
        //   334: invokevirtual   net/minecraft/util/Vec3.addVector:(DDD)Lnet/minecraft/util/Vec3;
        //   337: astore_3       
        //   338: goto            344
        //   341: goto            270
        //   344: aload_3        
        //   345: invokevirtual   net/minecraft/util/Vec3.normalize:()Lnet/minecraft/util/Vec3;
        //   348: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0344 (coming from #0338).
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
    public int getRenderType() {
        return 1;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        this.checkForMixing(world, blockPos, blockState);
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return 0;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockLiquid.LEVEL });
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        return (this.blockMaterial == Material.water) ? BiomeColorHelper.getWaterColorAtPos(blockAccess, blockPos) : 16777215;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return null;
    }
    
    protected void triggerMixEffects(final World world, final BlockPos blockPos) {
        final double n = blockPos.getX();
        final double n2 = blockPos.getY();
        final double n3 = blockPos.getZ();
        world.playSoundEffect(n + 0.5, n2 + 0.5, n3 + 0.5, "random.fizz", 0.5f, 2.6f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8f);
        while (true) {
            world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, n + Math.random(), n2 + 1.2, n3 + Math.random(), 0.0, 0.0, 0.0, new int[0]);
            int n4 = 0;
            ++n4;
        }
    }
    
    @Override
    public int getMixedBrightnessForBlock(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final int combinedLight = blockAccess.getCombinedLight(blockPos, 0);
        final int combinedLight2 = blockAccess.getCombinedLight(blockPos.up(), 0);
        final int n = combinedLight & 0xFF;
        final int n2 = combinedLight2 & 0xFF;
        final int n3 = combinedLight >> 16 & 0xFF;
        final int n4 = combinedLight2 >> 16 & 0xFF;
        return ((n > n2) ? n : n2) | ((n3 > n4) ? n3 : n4) << 16;
    }
    
    public boolean checkForMixing(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (this.blockMaterial == Material.lava) {
            final EnumFacing[] values = EnumFacing.values();
            while (0 < values.length) {
                final EnumFacing enumFacing = values[0];
                if (enumFacing != EnumFacing.DOWN && world.getBlockState(blockPos.offset(enumFacing)).getBlock().getMaterial() == Material.water) {
                    break;
                }
                int n = 0;
                ++n;
            }
            final Integer n2 = (Integer)blockState.getValue(BlockLiquid.LEVEL);
            if (n2 == 0) {
                world.setBlockState(blockPos, Blocks.obsidian.getDefaultState());
                this.triggerMixEffects(world, blockPos);
                return true;
            }
            if (n2 <= 4) {
                world.setBlockState(blockPos, Blocks.cobblestone.getDefaultState());
                this.triggerMixEffects(world, blockPos);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean func_176364_g(final IBlockAccess blockAccess, final BlockPos blockPos) {
        while (true) {
            final Block block = blockAccess.getBlockState(blockPos.add(-1, 0, -1)).getBlock();
            if (block.getMaterial() != this.blockMaterial && !block.isFullBlock()) {
                break;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        final double n = blockPos.getX();
        final double n2 = blockPos.getY();
        final double n3 = blockPos.getZ();
        if (this.blockMaterial == Material.water) {
            final int intValue = (int)blockState.getValue(BlockLiquid.LEVEL);
            if (intValue > 0 && intValue < 8) {
                if (random.nextInt(64) == 0) {
                    world.playSound(n + 0.5, n2 + 0.5, n3 + 0.5, "liquid.water", random.nextFloat() * 0.25f + 0.75f, random.nextFloat() * 1.0f + 0.5f, false);
                }
            }
            else if (random.nextInt(10) == 0) {
                world.spawnParticle(EnumParticleTypes.SUSPENDED, n + random.nextFloat(), n2 + random.nextFloat(), n3 + random.nextFloat(), 0.0, 0.0, 0.0, new int[0]);
            }
        }
        if (this.blockMaterial == Material.lava && world.getBlockState(blockPos.up()).getBlock().getMaterial() == Material.air && !world.getBlockState(blockPos.up()).getBlock().isOpaqueCube()) {
            if (random.nextInt(100) == 0) {
                final double n4 = n + random.nextFloat();
                final double n5 = n2 + this.maxY;
                final double n6 = n3 + random.nextFloat();
                world.spawnParticle(EnumParticleTypes.LAVA, n4, n5, n6, 0.0, 0.0, 0.0, new int[0]);
                world.playSound(n4, n5, n6, "liquid.lavapop", 0.2f + random.nextFloat() * 0.2f, 0.9f + random.nextFloat() * 0.15f, false);
            }
            if (random.nextInt(200) == 0) {
                world.playSound(n, n2, n3, "liquid.lava", 0.2f + random.nextFloat() * 0.2f, 0.9f + random.nextFloat() * 0.15f, false);
            }
        }
        if (random.nextInt(10) == 0 && World.doesBlockHaveSolidTopSurface(world, blockPos.down())) {
            final Material material = world.getBlockState(blockPos.down(2)).getBlock().getMaterial();
            if (!material.blocksMovement() && !material.isLiquid()) {
                final double n7 = n + random.nextFloat();
                final double n8 = n2 - 1.05;
                final double n9 = n3 + random.nextFloat();
                if (this.blockMaterial == Material.water) {
                    world.spawnParticle(EnumParticleTypes.DRIP_WATER, n7, n8, n9, 0.0, 0.0, 0.0, new int[0]);
                }
                else {
                    world.spawnParticle(EnumParticleTypes.DRIP_LAVA, n7, n8, n9, 0.0, 0.0, 0.0, new int[0]);
                }
            }
        }
    }
    
    static {
        LEVEL = PropertyInteger.create("level", 0, 15);
    }
    
    public static float getLiquidHeightPercent(final int n) {
        return 1 / 9.0f;
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.checkForMixing(world, blockPos, blockState);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return (int)blockState.getValue(BlockLiquid.LEVEL);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockLiquid.LEVEL, n);
    }
}
