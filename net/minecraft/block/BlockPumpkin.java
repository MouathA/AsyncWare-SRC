package net.minecraft.block;

import com.google.common.base.*;
import net.minecraft.init.*;
import net.minecraft.block.state.pattern.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.entity.monster.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;

public class BlockPumpkin extends BlockDirectional
{
    private static final Predicate field_181085_Q;
    private BlockPattern golemPattern;
    private BlockPattern snowmanBasePattern;
    private BlockPattern snowmanPattern;
    private BlockPattern golemBasePattern;
    
    protected BlockPattern getGolemBasePattern() {
        if (this.golemBasePattern == null) {
            this.golemBasePattern = FactoryBlockPattern.start().aisle("~ ~", "###", "~#~").where('#', BlockWorldState.hasState((Predicate)BlockStateHelper.forBlock(Blocks.iron_block))).where('~', BlockWorldState.hasState((Predicate)BlockStateHelper.forBlock(Blocks.air))).build();
        }
        return this.golemBasePattern;
    }
    
    protected BlockPattern getGolemPattern() {
        if (this.golemPattern == null) {
            this.golemPattern = FactoryBlockPattern.start().aisle("~^~", "###", "~#~").where('^', BlockWorldState.hasState(BlockPumpkin.field_181085_Q)).where('#', BlockWorldState.hasState((Predicate)BlockStateHelper.forBlock(Blocks.iron_block))).where('~', BlockWorldState.hasState((Predicate)BlockStateHelper.forBlock(Blocks.air))).build();
        }
        return this.golemPattern;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockPumpkin.FACING, EnumFacing.getHorizontal(n));
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState().withProperty(BlockPumpkin.FACING, entityLivingBase.getHorizontalFacing().getOpposite());
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        super.onBlockAdded(world, blockPos, blockState);
        this.trySpawnGolem(world, blockPos);
    }
    
    private void trySpawnGolem(final World world, final BlockPos blockPos) {
        final BlockPattern.PatternHelper match;
        if ((match = this.getSnowmanPattern().match(world, blockPos)) != null) {
            while (0 < this.getSnowmanPattern().getThumbLength()) {
                world.setBlockState(match.translateOffset(0, 0, 0).getPos(), Blocks.air.getDefaultState(), 2);
                int n = 0;
                ++n;
            }
            final EntitySnowman entitySnowman = new EntitySnowman(world);
            final BlockPos pos = match.translateOffset(0, 2, 0).getPos();
            entitySnowman.setLocationAndAngles(pos.getX() + 0.5, pos.getY() + 0.05, pos.getZ() + 0.5, 0.0f, 0.0f);
            world.spawnEntityInWorld(entitySnowman);
            while (true) {
                world.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, pos.getX() + world.rand.nextDouble(), pos.getY() + world.rand.nextDouble() * 2.5, pos.getZ() + world.rand.nextDouble(), 0.0, 0.0, 0.0, new int[0]);
                int n2 = 0;
                ++n2;
            }
        }
        else {
            final BlockPattern.PatternHelper match2;
            if ((match2 = this.getGolemPattern().match(world, blockPos)) == null) {
                return;
            }
            while (0 < this.getGolemPattern().getPalmLength()) {
                while (0 < this.getGolemPattern().getThumbLength()) {
                    world.setBlockState(match2.translateOffset(0, 0, 0).getPos(), Blocks.air.getDefaultState(), 2);
                    int n3 = 0;
                    ++n3;
                }
                int n = 0;
                ++n;
            }
            final BlockPos pos2 = match2.translateOffset(1, 2, 0).getPos();
            final EntityIronGolem entityIronGolem = new EntityIronGolem(world);
            entityIronGolem.setPlayerCreated(true);
            entityIronGolem.setLocationAndAngles(pos2.getX() + 0.5, pos2.getY() + 0.05, pos2.getZ() + 0.5, 0.0f, 0.0f);
            world.spawnEntityInWorld(entityIronGolem);
            while (true) {
                world.spawnParticle(EnumParticleTypes.SNOWBALL, pos2.getX() + world.rand.nextDouble(), pos2.getY() + world.rand.nextDouble() * 3.9, pos2.getZ() + world.rand.nextDouble(), 0.0, 0.0, 0.0, new int[0]);
                int n2 = 0;
                ++n2;
            }
        }
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return world.getBlockState(blockPos).getBlock().blockMaterial.isReplaceable() && World.doesBlockHaveSolidTopSurface(world, blockPos.down());
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumFacing)blockState.getValue(BlockPumpkin.FACING)).getHorizontalIndex();
    }
    
    protected BlockPumpkin() {
        super(Material.gourd, MapColor.adobeColor);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockPumpkin.FACING, EnumFacing.NORTH));
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockPumpkin.FACING });
    }
    
    protected BlockPattern getSnowmanPattern() {
        if (this.snowmanPattern == null) {
            this.snowmanPattern = FactoryBlockPattern.start().aisle("^", "#", "#").where('^', BlockWorldState.hasState(BlockPumpkin.field_181085_Q)).where('#', BlockWorldState.hasState((Predicate)BlockStateHelper.forBlock(Blocks.snow))).build();
        }
        return this.snowmanPattern;
    }
    
    protected BlockPattern getSnowmanBasePattern() {
        if (this.snowmanBasePattern == null) {
            this.snowmanBasePattern = FactoryBlockPattern.start().aisle(" ", "#", "#").where('#', BlockWorldState.hasState((Predicate)BlockStateHelper.forBlock(Blocks.snow))).build();
        }
        return this.snowmanBasePattern;
    }
    
    public boolean canDispenserPlace(final World world, final BlockPos blockPos) {
        return this.getSnowmanBasePattern().match(world, blockPos) != null || this.getGolemBasePattern().match(world, blockPos) != null;
    }
    
    static {
        field_181085_Q = (Predicate)new Predicate() {
            public boolean apply(final IBlockState blockState) {
                return blockState != null && (blockState.getBlock() == Blocks.pumpkin || blockState.getBlock() == Blocks.lit_pumpkin);
            }
            
            public boolean apply(final Object o) {
                return this.apply((IBlockState)o);
            }
        };
    }
}
