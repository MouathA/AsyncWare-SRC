package net.minecraft.block;

import net.minecraft.entity.player.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;

public class BlockDragonEgg extends Block
{
    @Override
    public int tickRate(final World world) {
        return 5;
    }
    
    @Override
    public void onBlockClicked(final World world, final BlockPos blockPos, final EntityPlayer entityPlayer) {
        this.teleport(world, blockPos);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        world.scheduleUpdate(blockPos, this, this.tickRate(world));
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    private void teleport(final World world, final BlockPos blockToAir) {
        final IBlockState blockState = world.getBlockState(blockToAir);
        if (blockState.getBlock() != this) {
            return;
        }
        BlockPos add;
        while (true) {
            add = blockToAir.add(world.rand.nextInt(16) - world.rand.nextInt(16), world.rand.nextInt(8) - world.rand.nextInt(8), world.rand.nextInt(16) - world.rand.nextInt(16));
            if (world.getBlockState(add).getBlock().blockMaterial == Material.air) {
                break;
            }
            int n = 0;
            ++n;
        }
        if (!world.isRemote) {
            world.setBlockState(add, blockState, 2);
            world.setBlockToAir(blockToAir);
            return;
        }
        while (true) {
            final double nextDouble = world.rand.nextDouble();
            world.spawnParticle(EnumParticleTypes.PORTAL, add.getX() + (blockToAir.getX() - add.getX()) * nextDouble + (world.rand.nextDouble() - 0.5) * 1.0 + 0.5, add.getY() + (blockToAir.getY() - add.getY()) * nextDouble + world.rand.nextDouble() * 1.0 - 0.5, add.getZ() + (blockToAir.getZ() - add.getZ()) * nextDouble + (world.rand.nextDouble() - 0.5) * 1.0 + 0.5, (world.rand.nextFloat() - 0.5f) * 0.2f, (world.rand.nextFloat() - 0.5f) * 0.2f, (world.rand.nextFloat() - 0.5f) * 0.2f, new int[0]);
            int n2 = 0;
            ++n2;
        }
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        return true;
    }
    
    private void checkFall(final World world, final BlockPos blockToAir) {
        if (BlockFalling.canFallInto(world, blockToAir.down()) && blockToAir.getY() >= 0) {
            if (!BlockFalling.fallInstantly && world.isAreaLoaded(blockToAir.add(-32, -32, -32), blockToAir.add(32, 32, 32))) {
                world.spawnEntityInWorld(new EntityFallingBlock(world, blockToAir.getX() + 0.5f, blockToAir.getY(), blockToAir.getZ() + 0.5f, this.getDefaultState()));
            }
            else {
                world.setBlockToAir(blockToAir);
                BlockPos down;
                for (down = blockToAir; BlockFalling.canFallInto(world, down) && down.getY() > 0; down = down.down()) {}
                if (down.getY() > 0) {
                    world.setBlockState(down, this.getDefaultState(), 2);
                }
            }
        }
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        this.teleport(world, blockPos);
        return true;
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        this.checkFall(world, blockPos);
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        world.scheduleUpdate(blockPos, this, this.tickRate(world));
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return null;
    }
    
    public BlockDragonEgg() {
        super(Material.dragonEgg, MapColor.blackColor);
        this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 1.0f, 0.9375f);
    }
}
