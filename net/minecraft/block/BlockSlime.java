package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;

public class BlockSlime extends BlockBreakable
{
    @Override
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockPos, final Entity entity) {
        if (Math.abs(entity.motionY) < 0.1 && !entity.isSneaking()) {
            final double n = 0.4 + Math.abs(entity.motionY) * 0.2;
            entity.motionX *= n;
            entity.motionZ *= n;
        }
        super.onEntityCollidedWithBlock(world, blockPos, entity);
    }
    
    @Override
    public void onLanded(final World world, final Entity entity) {
        if (entity.isSneaking()) {
            super.onLanded(world, entity);
        }
        else if (entity.motionY < 0.0) {
            entity.motionY = -entity.motionY;
        }
    }
    
    @Override
    public void onFallenUpon(final World world, final BlockPos blockPos, final Entity entity, final float n) {
        if (entity.isSneaking()) {
            super.onFallenUpon(world, blockPos, entity, n);
        }
        else {
            entity.fall(n, 0.0f);
        }
    }
    
    public BlockSlime() {
        super(Material.clay, false, MapColor.grassColor);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.slipperiness = 0.8f;
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.TRANSLUCENT;
    }
}
