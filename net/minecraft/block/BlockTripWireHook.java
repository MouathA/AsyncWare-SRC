package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;
import com.google.common.base.*;
import net.minecraft.util.*;
import java.util.*;

public class BlockTripWireHook extends Block
{
    public static final PropertyBool POWERED;
    public static final PropertyBool SUSPENDED;
    public static final PropertyDirection FACING;
    public static final PropertyBool ATTACHED;
    
    @Override
    public boolean canPlaceBlockOnSide(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        return enumFacing.getAxis().isHorizontal() && world.getBlockState(blockPos.offset(enumFacing.getOpposite())).getBlock().isNormalCube();
    }
    
    private void func_180694_a(final World world, final BlockPos blockPos, final boolean b, final boolean b2, final boolean b3, final boolean b4) {
        if (b2 && !b4) {
            world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.1, blockPos.getZ() + 0.5, "random.click", 0.4f, 0.6f);
        }
        else if (!b2 && b4) {
            world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.1, blockPos.getZ() + 0.5, "random.click", 0.4f, 0.5f);
        }
        else if (b && !b3) {
            world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.1, blockPos.getZ() + 0.5, "random.click", 0.4f, 0.7f);
        }
        else if (!b && b3) {
            world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.1, blockPos.getZ() + 0.5, "random.bowhit", 0.4f, 1.2f / (world.rand.nextFloat() * 0.2f + 0.9f));
        }
    }
    
    public void func_176260_a(final World world, final BlockPos blockPos, final IBlockState blockState, final boolean b, final boolean b2, final int n, final IBlockState blockState2) {
        final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockTripWireHook.FACING);
        final boolean booleanValue = (boolean)blockState.getValue(BlockTripWireHook.ATTACHED);
        final boolean booleanValue2 = (boolean)blockState.getValue(BlockTripWireHook.POWERED);
        final boolean b3 = !World.doesBlockHaveSolidTopSurface(world, blockPos.down());
        final boolean b4 = !b;
        final IBlockState[] array = new IBlockState[42];
        IBlockState blockState3;
        while (true) {
            blockState3 = world.getBlockState(blockPos.offset(enumFacing, 1));
            if (blockState3.getBlock() == Blocks.tripwire_hook) {
                break;
            }
            if (blockState3.getBlock() != Blocks.tripwire && 1 != n) {
                array[1] = null;
            }
            else {
                if (1 == n) {
                    blockState3 = (IBlockState)Objects.firstNonNull((Object)blockState2, (Object)blockState3);
                }
                final boolean b5 = !(boolean)blockState3.getValue(BlockTripWire.DISARMED);
                final boolean booleanValue3 = (boolean)blockState3.getValue(BlockTripWire.POWERED);
                final boolean b6 = false & (boolean)blockState3.getValue(BlockTripWire.SUSPENDED) == b3;
                final boolean b7 = false | (b5 && booleanValue3);
                array[1] = blockState3;
                if (1 == n) {
                    world.scheduleUpdate(blockPos, this, this.tickRate(world));
                }
            }
            int n2 = 0;
            ++n2;
        }
        if (blockState3.getValue(BlockTripWireHook.FACING) == enumFacing.getOpposite()) {}
        final IBlockState withProperty = this.getDefaultState().withProperty(BlockTripWireHook.ATTACHED, false).withProperty(BlockTripWireHook.POWERED, false);
        this.func_180694_a(world, blockPos, false, false, booleanValue, booleanValue2);
        if (!b) {
            world.setBlockState(blockPos, withProperty.withProperty(BlockTripWireHook.FACING, enumFacing), 3);
            if (b2) {
                this.func_176262_b(world, blockPos, enumFacing);
            }
        }
        if (booleanValue) {}
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockTripWireHook.FACING, EnumFacing.getHorizontal(n & 0x3)).withProperty(BlockTripWireHook.POWERED, (n & 0x8) > 0).withProperty(BlockTripWireHook.ATTACHED, (n & 0x4) > 0);
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final boolean booleanValue = (boolean)blockState.getValue(BlockTripWireHook.ATTACHED);
        final boolean booleanValue2 = (boolean)blockState.getValue(BlockTripWireHook.POWERED);
        if (booleanValue || booleanValue2) {
            this.func_176260_a(world, blockPos, blockState, true, false, -1, null);
        }
        if (booleanValue2) {
            world.notifyNeighborsOfStateChange(blockPos, this);
            world.notifyNeighborsOfStateChange(blockPos.offset(((EnumFacing)blockState.getValue(BlockTripWireHook.FACING)).getOpposite()), this);
        }
        super.breakBlock(world, blockPos, blockState);
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        this.func_176260_a(world, blockPos, blockState, false, false, -1, null);
    }
    
    @Override
    public void onNeighborBlockChange(final World p0, final BlockPos p1, final IBlockState p2, final Block p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     2: aload_0        
        //     3: if_acmpeq       65
        //     6: aload_0        
        //     7: aload_1        
        //     8: aload_2        
        //     9: aload_3        
        //    10: ifeq            65
        //    13: aload_3        
        //    14: getstatic       net/minecraft/block/BlockTripWireHook.FACING:Lnet/minecraft/block/properties/PropertyDirection;
        //    17: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //    22: checkcast       Lnet/minecraft/util/EnumFacing;
        //    25: astore          5
        //    27: aload_1        
        //    28: aload_2        
        //    29: aload           5
        //    31: invokevirtual   net/minecraft/util/EnumFacing.getOpposite:()Lnet/minecraft/util/EnumFacing;
        //    34: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;
        //    37: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //    40: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //    45: invokevirtual   net/minecraft/block/Block.isNormalCube:()Z
        //    48: ifne            65
        //    51: aload_0        
        //    52: aload_1        
        //    53: aload_2        
        //    54: aload_3        
        //    55: iconst_0       
        //    56: invokevirtual   net/minecraft/block/BlockTripWireHook.dropBlockAsItem:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)V
        //    59: aload_1        
        //    60: aload_2        
        //    61: invokevirtual   net/minecraft/world/World.setBlockToAir:(Lnet/minecraft/util/BlockPos;)Z
        //    64: pop            
        //    65: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0065 (coming from #0010).
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
    
    public BlockTripWireHook() {
        super(Material.circuits);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockTripWireHook.FACING, EnumFacing.NORTH).withProperty(BlockTripWireHook.POWERED, false).withProperty(BlockTripWireHook.ATTACHED, false).withProperty(BlockTripWireHook.SUSPENDED, false));
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.setTickRandomly(true);
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockTripWireHook.FACING, BlockTripWireHook.POWERED, BlockTripWireHook.ATTACHED, BlockTripWireHook.SUSPENDED });
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT_MIPPED;
    }
    
    @Override
    public int getStrongPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return blockState.getValue(BlockTripWireHook.POWERED) ? ((blockState.getValue(BlockTripWireHook.FACING) == enumFacing) ? 15 : 0) : 0;
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        IBlockState blockState = this.getDefaultState().withProperty(BlockTripWireHook.POWERED, false).withProperty(BlockTripWireHook.ATTACHED, false).withProperty(BlockTripWireHook.SUSPENDED, false);
        if (enumFacing.getAxis().isHorizontal()) {
            blockState = blockState.withProperty(BlockTripWireHook.FACING, enumFacing);
        }
        return blockState;
    }
    
    static {
        FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
        POWERED = PropertyBool.create("powered");
        ATTACHED = PropertyBool.create("attached");
        SUSPENDED = PropertyBool.create("suspended");
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        final int n = 0x0 | ((EnumFacing)blockState.getValue(BlockTripWireHook.FACING)).getHorizontalIndex();
        if (blockState.getValue(BlockTripWireHook.POWERED)) {}
        if (blockState.getValue(BlockTripWireHook.ATTACHED)) {}
        return 0;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    @Override
    public boolean canProvidePower() {
        return true;
    }
    
    private void func_176262_b(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        world.notifyNeighborsOfStateChange(blockPos, this);
        world.notifyNeighborsOfStateChange(blockPos.offset(enumFacing.getOpposite()), this);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final float n = 0.1875f;
        switch (BlockTripWireHook$1.$SwitchMap$net$minecraft$util$EnumFacing[((EnumFacing)blockAccess.getBlockState(blockPos).getValue(BlockTripWireHook.FACING)).ordinal()]) {
            case 1: {
                this.setBlockBounds(0.0f, 0.2f, 0.5f - n, n * 2.0f, 0.8f, 0.5f + n);
                break;
            }
            case 2: {
                this.setBlockBounds(1.0f - n * 2.0f, 0.2f, 0.5f - n, 1.0f, 0.8f, 0.5f + n);
                break;
            }
            case 3: {
                this.setBlockBounds(0.5f - n, 0.2f, 0.0f, 0.5f + n, 0.8f, n * 2.0f);
                break;
            }
            case 4: {
                this.setBlockBounds(0.5f - n, 0.2f, 1.0f - n * 2.0f, 0.5f + n, 0.8f, 1.0f);
                break;
            }
        }
    }
    
    @Override
    public IBlockState getActualState(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        return blockState.withProperty(BlockTripWireHook.SUSPENDED, !World.doesBlockHaveSolidTopSurface(blockAccess, blockPos.down()));
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public void randomTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        this.func_176260_a(world, blockPos, blockState, false, true, -1, null);
    }
    
    @Override
    public int getWeakPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return blockState.getValue(BlockTripWireHook.POWERED) ? 15 : 0;
    }
}
