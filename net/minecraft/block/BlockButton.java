package net.minecraft.block;

import java.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;

public abstract class BlockButton extends Block
{
    public static final PropertyBool POWERED;
    private final boolean wooden;
    public static final PropertyDirection FACING;
    
    @Override
    public void randomTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (!world.isRemote && (boolean)blockState.getValue(BlockButton.POWERED)) {
            if (this.wooden) {
                this.checkForArrows(world, blockPos, blockState);
            }
            else {
                world.setBlockState(blockPos, blockState.withProperty(BlockButton.POWERED, false));
                this.notifyNeighbors(world, blockPos, (EnumFacing)blockState.getValue(BlockButton.FACING));
                world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, "random.click", 0.3f, 0.5f);
                world.markBlockRangeForRenderUpdate(blockPos, blockPos);
            }
        }
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        EnumFacing enumFacing = null;
        switch (n & 0x7) {
            case 0: {
                enumFacing = EnumFacing.DOWN;
                break;
            }
            case 1: {
                enumFacing = EnumFacing.EAST;
                break;
            }
            case 2: {
                enumFacing = EnumFacing.WEST;
                break;
            }
            case 3: {
                enumFacing = EnumFacing.SOUTH;
                break;
            }
            case 4: {
                enumFacing = EnumFacing.NORTH;
                break;
            }
            default: {
                enumFacing = EnumFacing.UP;
                break;
            }
        }
        return this.getDefaultState().withProperty(BlockButton.FACING, enumFacing).withProperty(BlockButton.POWERED, (n & 0x8) > 0);
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return (blockPos == enumFacing.getOpposite()) ? this.getDefaultState().withProperty(BlockButton.FACING, enumFacing).withProperty(BlockButton.POWERED, false) : this.getDefaultState().withProperty(BlockButton.FACING, EnumFacing.DOWN).withProperty(BlockButton.POWERED, false);
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        final float n = 0.1875f;
        final float n2 = 0.125f;
        final float n3 = 0.125f;
        this.setBlockBounds(0.5f - n, 0.5f - n2, 0.5f - n3, 0.5f + n, 0.5f + n2, 0.5f + n3);
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public int getWeakPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return blockState.getValue(BlockButton.POWERED) ? 15 : 0;
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (blockState.getValue(BlockButton.POWERED)) {
            return true;
        }
        world.setBlockState(blockPos, blockState.withProperty(BlockButton.POWERED, true), 3);
        world.markBlockRangeForRenderUpdate(blockPos, blockPos);
        world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, "random.click", 0.3f, 0.6f);
        this.notifyNeighbors(world, blockPos, (EnumFacing)blockState.getValue(BlockButton.FACING));
        world.scheduleUpdate(blockPos, this, this.tickRate(world));
        return true;
    }
    
    private void notifyNeighbors(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        world.notifyNeighborsOfStateChange(blockPos, this);
        world.notifyNeighborsOfStateChange(blockPos.offset(enumFacing.getOpposite()), this);
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockPos, final IBlockState blockState, final Entity entity) {
        if (!world.isRemote && this.wooden && !(boolean)blockState.getValue(BlockButton.POWERED)) {
            this.checkForArrows(world, blockPos, blockState);
        }
    }
    
    private void checkForArrows(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.updateBlockBounds(blockState);
        final boolean b = !world.getEntitiesWithinAABB(EntityArrow.class, new AxisAlignedBB(blockPos.getX() + this.minX, blockPos.getY() + this.minY, blockPos.getZ() + this.minZ, blockPos.getX() + this.maxX, blockPos.getY() + this.maxY, blockPos.getZ() + this.maxZ)).isEmpty();
        final boolean booleanValue = (boolean)blockState.getValue(BlockButton.POWERED);
        if (b && !booleanValue) {
            world.setBlockState(blockPos, blockState.withProperty(BlockButton.POWERED, true));
            this.notifyNeighbors(world, blockPos, (EnumFacing)blockState.getValue(BlockButton.FACING));
            world.markBlockRangeForRenderUpdate(blockPos, blockPos);
            world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, "random.click", 0.3f, 0.6f);
        }
        if (!b && booleanValue) {
            world.setBlockState(blockPos, blockState.withProperty(BlockButton.POWERED, false));
            this.notifyNeighbors(world, blockPos, (EnumFacing)blockState.getValue(BlockButton.FACING));
            world.markBlockRangeForRenderUpdate(blockPos, blockPos);
            world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, "random.click", 0.3f, 0.5f);
        }
        if (b) {
            world.scheduleUpdate(blockPos, this, this.tickRate(world));
        }
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        this.updateBlockBounds(blockAccess.getBlockState(blockPos));
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockButton.FACING, BlockButton.POWERED });
    }
    
    @Override
    public int getStrongPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return blockState.getValue(BlockButton.POWERED) ? ((blockState.getValue(BlockButton.FACING) == enumFacing) ? 15 : 0) : 0;
    }
    
    protected BlockButton(final boolean wooden) {
        super(Material.circuits);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockButton.FACING, EnumFacing.NORTH).withProperty(BlockButton.POWERED, false));
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.wooden = wooden;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        switch (BlockButton$1.$SwitchMap$net$minecraft$util$EnumFacing[((EnumFacing)blockState.getValue(BlockButton.FACING)).ordinal()]) {
            default: {}
            case 4:
            case 6: {
                if (blockState.getValue(BlockButton.POWERED)) {}
                return 0;
            }
            case 1: {}
            case 2: {}
            case 3: {}
        }
    }
    
    @Override
    public boolean canProvidePower() {
        return true;
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (blockState.getValue(BlockButton.POWERED)) {
            this.notifyNeighbors(world, blockPos, (EnumFacing)blockState.getValue(BlockButton.FACING));
        }
        super.breakBlock(world, blockPos, blockState);
    }
    
    @Override
    public int tickRate(final World world) {
        return this.wooden ? 30 : 20;
    }
    
    @Override
    public void onNeighborBlockChange(final World p0, final BlockPos p1, final IBlockState p2, final Block p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1        
        //     2: aload_2        
        //     3: aload_3        
        //     4: if_icmpge       41
        //     7: aload_1        
        //     8: aload_2        
        //     9: aload_3        
        //    10: getstatic       net/minecraft/block/BlockButton.FACING:Lnet/minecraft/block/properties/PropertyDirection;
        //    13: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //    18: checkcast       Lnet/minecraft/util/EnumFacing;
        //    21: invokevirtual   net/minecraft/util/EnumFacing.getOpposite:()Lnet/minecraft/util/EnumFacing;
        //    24: if_acmpne       41
        //    27: aload_0        
        //    28: aload_1        
        //    29: aload_2        
        //    30: aload_3        
        //    31: iconst_0       
        //    32: invokevirtual   net/minecraft/block/BlockButton.dropBlockAsItem:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)V
        //    35: aload_1        
        //    36: aload_2        
        //    37: invokevirtual   net/minecraft/world/World.setBlockToAir:(Lnet/minecraft/util/BlockPos;)Z
        //    40: pop            
        //    41: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0041 (coming from #0024).
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
    
    private void updateBlockBounds(final IBlockState blockState) {
        final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockButton.FACING);
        final float n = (blockState.getValue(BlockButton.POWERED) ? 1 : 2) / 16.0f;
        switch (BlockButton$1.$SwitchMap$net$minecraft$util$EnumFacing[enumFacing.ordinal()]) {
            case 1: {
                this.setBlockBounds(0.0f, 0.375f, 0.3125f, n, 0.625f, 0.6875f);
                break;
            }
            case 2: {
                this.setBlockBounds(1.0f - n, 0.375f, 0.3125f, 1.0f, 0.625f, 0.6875f);
                break;
            }
            case 3: {
                this.setBlockBounds(0.3125f, 0.375f, 0.0f, 0.6875f, 0.625f, n);
                break;
            }
            case 4: {
                this.setBlockBounds(0.3125f, 0.375f, 1.0f - n, 0.6875f, 0.625f, 1.0f);
                break;
            }
            case 5: {
                this.setBlockBounds(0.3125f, 0.0f, 0.375f, 0.6875f, 0.0f + n, 0.625f);
                break;
            }
            case 6: {
                this.setBlockBounds(0.3125f, 1.0f - n, 0.375f, 0.6875f, 1.0f, 0.625f);
                break;
            }
        }
    }
    
    static {
        FACING = PropertyDirection.create("facing");
        POWERED = PropertyBool.create("powered");
    }
    
    @Override
    public boolean canPlaceBlockOnSide(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        return func_181088_a(world, blockPos, enumFacing.getOpposite());
    }
}
