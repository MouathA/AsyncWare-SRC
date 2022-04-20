package net.minecraft.item;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.creativetab.*;

public class ItemHangingEntity extends Item
{
    private final Class hangingEntityClass;
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (enumFacing == EnumFacing.DOWN) {
            return false;
        }
        if (enumFacing == EnumFacing.UP) {
            return false;
        }
        final BlockPos offset = blockPos.offset(enumFacing);
        if (!entityPlayer.canPlayerEdit(offset, enumFacing, itemStack)) {
            return false;
        }
        final EntityHanging entity = this.createEntity(world, offset, enumFacing);
        if (entity != null && entity.onValidSurface()) {
            if (!world.isRemote) {
                world.spawnEntityInWorld(entity);
            }
            --itemStack.stackSize;
        }
        return true;
    }
    
    private EntityHanging createEntity(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        return (this.hangingEntityClass == EntityPainting.class) ? new EntityPainting(world, blockPos, enumFacing) : ((this.hangingEntityClass == EntityItemFrame.class) ? new EntityItemFrame(world, blockPos, enumFacing) : null);
    }
    
    public ItemHangingEntity(final Class hangingEntityClass) {
        this.hangingEntityClass = hangingEntityClass;
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
}
