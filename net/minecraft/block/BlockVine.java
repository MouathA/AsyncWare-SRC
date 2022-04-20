package net.minecraft.block;

import net.minecraft.block.properties.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import net.minecraft.stats.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;

public class BlockVine extends Block
{
    public static final PropertyBool[] ALL_FACES;
    public static final PropertyBool NORTH;
    public static final PropertyBool SOUTH;
    public static final PropertyBool EAST;
    public static final PropertyBool UP;
    public static final PropertyBool WEST;
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        if (blockState.getValue(BlockVine.SOUTH)) {}
        if (blockState.getValue(BlockVine.WEST)) {}
        if (blockState.getValue(BlockVine.NORTH)) {}
        if (blockState.getValue(BlockVine.EAST)) {}
        return 0;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockVine.SOUTH, (n & 0x1) > 0).withProperty(BlockVine.WEST, (n & 0x2) > 0).withProperty(BlockVine.NORTH, (n & 0x4) > 0).withProperty(BlockVine.EAST, (n & 0x8) > 0);
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return 0;
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        return blockAccess.getBiomeGenForCoords(blockPos).getFoliageColorAtPos(blockPos);
    }
    
    @Override
    public int getRenderColor(final IBlockState blockState) {
        return ColorizerFoliage.getFoliageColorBasic();
    }
    
    @Override
    public boolean canPlaceBlockOnSide(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        switch (BlockVine$1.$SwitchMap$net$minecraft$util$EnumFacing[enumFacing.ordinal()]) {
            case 1: {
                return this.canPlaceOn(world.getBlockState(blockPos.up()).getBlock());
            }
            case 2:
            case 3:
            case 4:
            case 5: {
                return this.canPlaceOn(world.getBlockState(blockPos.offset(enumFacing.getOpposite())).getBlock());
            }
            default: {
                return false;
            }
        }
    }
    
    @Override
    public boolean isReplaceable(final World world, final BlockPos blockPos) {
        return true;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return null;
    }
    
    @Override
    public void harvestBlock(final World world, final EntityPlayer entityPlayer, final BlockPos blockPos, final IBlockState blockState, final TileEntity tileEntity) {
        if (!world.isRemote && entityPlayer.getCurrentEquippedItem() != null && entityPlayer.getCurrentEquippedItem().getItem() == Items.shears) {
            entityPlayer.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
            Block.spawnAsEntity(world, blockPos, new ItemStack(Blocks.vine, 1, 0));
        }
        else {
            super.harvestBlock(world, entityPlayer, blockPos, blockState, tileEntity);
        }
    }
    
    public static PropertyBool getPropertyFor(final EnumFacing enumFacing) {
        switch (BlockVine$1.$SwitchMap$net$minecraft$util$EnumFacing[enumFacing.ordinal()]) {
            case 1: {
                return BlockVine.UP;
            }
            case 2: {
                return BlockVine.NORTH;
            }
            case 3: {
                return BlockVine.SOUTH;
            }
            case 4: {
                return BlockVine.EAST;
            }
            case 5: {
                return BlockVine.WEST;
            }
            default: {
                throw new IllegalArgumentException(enumFacing + " is an invalid choice");
            }
        }
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (world.isRemote || world.rand.nextInt(4) != 0) {
            return;
        }
        while (true) {
            if (world.getBlockState(blockPos.add(-4, -1, -4)).getBlock() == this) {
                int n = 0;
                --n;
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockVine.UP, BlockVine.NORTH, BlockVine.EAST, BlockVine.SOUTH, BlockVine.WEST });
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    public static int getNumGrownFaces(final IBlockState blockState) {
        final PropertyBool[] all_FACES = BlockVine.ALL_FACES;
        while (0 < all_FACES.length) {
            if (blockState.getValue(all_FACES[0])) {
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    @Override
    public int getBlockColor() {
        return ColorizerFoliage.getFoliageColorBasic();
    }
    
    @Override
    public IBlockState getActualState(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        return blockState.withProperty(BlockVine.UP, blockAccess.getBlockState(blockPos.up()).getBlock().isBlockNormalCube());
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public void onNeighborBlockChange(final World p0, final BlockPos p1, final IBlockState p2, final Block p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/world/World.isRemote:Z
        //     4: ifne            28
        //     7: aload_0        
        //     8: aload_1        
        //     9: aload_2        
        //    10: aload_3        
        //    11: ifeq            28
        //    14: aload_0        
        //    15: aload_1        
        //    16: aload_2        
        //    17: aload_3        
        //    18: iconst_0       
        //    19: invokevirtual   net/minecraft/block/BlockVine.dropBlockAsItem:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)V
        //    22: aload_1        
        //    23: aload_2        
        //    24: invokevirtual   net/minecraft/world/World.setBlockToAir:(Lnet/minecraft/util/BlockPos;)Z
        //    27: pop            
        //    28: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0028 (coming from #0011).
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
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        final IBlockState withProperty = this.getDefaultState().withProperty(BlockVine.UP, false).withProperty(BlockVine.NORTH, false).withProperty(BlockVine.EAST, false).withProperty(BlockVine.SOUTH, false).withProperty(BlockVine.WEST, false);
        return enumFacing.getAxis().isHorizontal() ? withProperty.withProperty(getPropertyFor(enumFacing.getOpposite()), true) : withProperty;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        float min = 1.0f;
        float n = 1.0f;
        float min2 = 1.0f;
        float max = 0.0f;
        float n2 = 0.0f;
        float max2 = 0.0f;
        if (blockAccess.getBlockState(blockPos).getValue(BlockVine.WEST)) {
            max = Math.max(max, 0.0625f);
            min = 0.0f;
            n = 0.0f;
            n2 = 1.0f;
            min2 = 0.0f;
            max2 = 1.0f;
        }
        if (blockAccess.getBlockState(blockPos).getValue(BlockVine.EAST)) {
            min = Math.min(min, 0.9375f);
            max = 1.0f;
            n = 0.0f;
            n2 = 1.0f;
            min2 = 0.0f;
            max2 = 1.0f;
        }
        if (blockAccess.getBlockState(blockPos).getValue(BlockVine.NORTH)) {
            max2 = Math.max(max2, 0.0625f);
            min2 = 0.0f;
            min = 0.0f;
            max = 1.0f;
            n = 0.0f;
            n2 = 1.0f;
        }
        if (blockAccess.getBlockState(blockPos).getValue(BlockVine.SOUTH)) {
            min2 = Math.min(min2, 0.9375f);
            max2 = 1.0f;
            min = 0.0f;
            max = 1.0f;
            n = 0.0f;
            n2 = 1.0f;
        }
        this.setBlockBounds(min, n, min2, max, n2, max2);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    static {
        UP = PropertyBool.create("up");
        NORTH = PropertyBool.create("north");
        EAST = PropertyBool.create("east");
        SOUTH = PropertyBool.create("south");
        WEST = PropertyBool.create("west");
        ALL_FACES = new PropertyBool[] { BlockVine.UP, BlockVine.NORTH, BlockVine.SOUTH, BlockVine.WEST, BlockVine.EAST };
    }
    
    public BlockVine() {
        super(Material.vine);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockVine.UP, false).withProperty(BlockVine.NORTH, false).withProperty(BlockVine.EAST, false).withProperty(BlockVine.SOUTH, false).withProperty(BlockVine.WEST, false));
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
}
