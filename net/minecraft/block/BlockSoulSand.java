package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;

public class BlockSoulSand extends Block
{
    @Override
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockPos, final IBlockState blockState, final Entity entity) {
        entity.motionX *= 0.4;
        entity.motionZ *= 0.4;
    }
    
    public BlockSoulSand() {
        super(Material.sand, MapColor.brownColor);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return new AxisAlignedBB(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos.getX() + 1, blockPos.getY() + 1 - 0.125f, blockPos.getZ() + 1);
    }
}
