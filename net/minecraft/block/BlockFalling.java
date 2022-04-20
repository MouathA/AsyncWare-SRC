package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;

public class BlockFalling extends Block
{
    public static boolean fallInstantly;
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        world.scheduleUpdate(blockPos, this, this.tickRate(world));
    }
    
    public void onEndFalling(final World world, final BlockPos blockPos) {
    }
    
    @Override
    public int tickRate(final World world) {
        return 2;
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (!world.isRemote) {
            this.checkFallable(world, blockPos);
        }
    }
    
    private void checkFallable(final World world, final BlockPos blockToAir) {
        if (world != blockToAir.down() && blockToAir.getY() >= 0) {
            if (!BlockFalling.fallInstantly && world.isAreaLoaded(blockToAir.add(-32, -32, -32), blockToAir.add(32, 32, 32))) {
                if (!world.isRemote) {
                    final EntityFallingBlock entityFallingBlock = new EntityFallingBlock(world, blockToAir.getX() + 0.5, blockToAir.getY(), blockToAir.getZ() + 0.5, world.getBlockState(blockToAir));
                    this.onStartFalling(entityFallingBlock);
                    world.spawnEntityInWorld(entityFallingBlock);
                }
            }
            else {
                world.setBlockToAir(blockToAir);
                BlockPos blockPos;
                for (blockPos = blockToAir.down(); world != blockPos && blockPos.getY() > 0; blockPos = blockPos.down()) {}
                if (blockPos.getY() > 0) {
                    world.setBlockState(blockPos.up(), this.getDefaultState());
                }
            }
        }
    }
    
    public BlockFalling() {
        super(Material.sand);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        world.scheduleUpdate(blockPos, this, this.tickRate(world));
    }
    
    protected void onStartFalling(final EntityFallingBlock entityFallingBlock) {
    }
    
    public BlockFalling(final Material material) {
        super(material);
    }
}
