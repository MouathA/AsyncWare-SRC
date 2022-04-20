package net.minecraft.block;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;

public class BlockRedstoneOre extends Block
{
    private final boolean isOn;
    
    private void spawnParticles(final World world, final BlockPos blockPos) {
        final Random rand = world.rand;
        final double n = 0.0625;
        while (true) {
            final double n2 = blockPos.getX() + rand.nextFloat();
            double n3 = blockPos.getY() + rand.nextFloat();
            final double n4 = blockPos.getZ() + rand.nextFloat();
            if (!world.getBlockState(blockPos.up()).getBlock().isOpaqueCube()) {
                n3 = blockPos.getY() + n + 1.0;
            }
            if (n2 < blockPos.getX() || n2 > blockPos.getX() + 1 || n3 < 0.0 || n3 > blockPos.getY() + 1 || n4 < blockPos.getZ() || n4 > blockPos.getZ() + 1) {
                world.spawnParticle(EnumParticleTypes.REDSTONE, n2, n3, n4, 0.0, 0.0, 0.0, new int[0]);
            }
            int n5 = 0;
            ++n5;
        }
    }
    
    private void activate(final World world, final BlockPos blockPos) {
        this.spawnParticles(world, blockPos);
        if (this == Blocks.redstone_ore) {
            world.setBlockState(blockPos, Blocks.lit_redstone_ore.getDefaultState());
        }
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockPos, final Entity entity) {
        this.activate(world, blockPos);
        super.onEntityCollidedWithBlock(world, blockPos, entity);
    }
    
    @Override
    protected ItemStack createStackedBlock(final IBlockState blockState) {
        return new ItemStack(Blocks.redstone_ore);
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (this.isOn) {
            this.spawnParticles(world, blockPos);
        }
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        super.dropBlockAsItemWithChance(world, blockPos, blockState, n, n2);
        if (this.getItemDropped(blockState, world.rand, n2) != Item.getItemFromBlock(this)) {
            this.dropXpOnBlockBreak(world, blockPos, 1 + world.rand.nextInt(5));
        }
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return 4 + random.nextInt(2);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.redstone;
    }
    
    @Override
    public void onBlockClicked(final World world, final BlockPos blockPos, final EntityPlayer entityPlayer) {
        this.activate(world, blockPos);
        super.onBlockClicked(world, blockPos, entityPlayer);
    }
    
    @Override
    public int tickRate(final World world) {
        return 30;
    }
    
    @Override
    public int quantityDroppedWithBonus(final int n, final Random random) {
        return this.quantityDropped(random) + random.nextInt(n + 1);
    }
    
    public BlockRedstoneOre(final boolean isOn) {
        super(Material.rock);
        if (isOn) {
            this.setTickRandomly(true);
        }
        this.isOn = isOn;
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (this == Blocks.lit_redstone_ore) {
            world.setBlockState(blockPos, Blocks.redstone_ore.getDefaultState());
        }
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        this.activate(world, blockPos);
        return super.onBlockActivated(world, blockPos, blockState, entityPlayer, enumFacing, n, n2, n3);
    }
}
