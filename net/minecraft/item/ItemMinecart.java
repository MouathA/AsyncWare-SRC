package net.minecraft.item;

import net.minecraft.entity.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;
import net.minecraft.dispenser.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;

public class ItemMinecart extends Item
{
    private static final IBehaviorDispenseItem dispenserMinecartBehavior;
    private final EntityMinecart.EnumMinecartType minecartType;
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        final IBlockState blockState = world.getBlockState(blockPos);
        if (BlockRailBase.isRailBlock(blockState)) {
            if (!world.isRemote) {
                final BlockRailBase.EnumRailDirection enumRailDirection = (BlockRailBase.EnumRailDirection)((blockState.getBlock() instanceof BlockRailBase) ? blockState.getValue(((BlockRailBase)blockState.getBlock()).getShapeProperty()) : BlockRailBase.EnumRailDirection.NORTH_SOUTH);
                double n4 = 0.0;
                if (enumRailDirection.isAscending()) {
                    n4 = 0.5;
                }
                final EntityMinecart func_180458_a = EntityMinecart.func_180458_a(world, blockPos.getX() + 0.5, blockPos.getY() + 0.0625 + n4, blockPos.getZ() + 0.5, this.minecartType);
                if (itemStack.hasDisplayName()) {
                    func_180458_a.setCustomNameTag(itemStack.getDisplayName());
                }
                world.spawnEntityInWorld(func_180458_a);
            }
            --itemStack.stackSize;
            return true;
        }
        return false;
    }
    
    static {
        dispenserMinecartBehavior = new BehaviorDefaultDispenseItem() {
            private final BehaviorDefaultDispenseItem behaviourDefaultDispenseItem = new BehaviorDefaultDispenseItem();
            
            @Override
            protected void playDispenseSound(final IBlockSource blockSource) {
                blockSource.getWorld().playAuxSFX(1000, blockSource.getBlockPos(), 0);
            }
            
            public ItemStack dispenseStack(final IBlockSource blockSource, final ItemStack itemStack) {
                final EnumFacing facing = BlockDispenser.getFacing(blockSource.getBlockMetadata());
                final World world = blockSource.getWorld();
                final double n = blockSource.getX() + facing.getFrontOffsetX() * 1.125;
                final double n2 = Math.floor(blockSource.getY()) + facing.getFrontOffsetY();
                final double n3 = blockSource.getZ() + facing.getFrontOffsetZ() * 1.125;
                final BlockPos offset = blockSource.getBlockPos().offset(facing);
                final IBlockState blockState = world.getBlockState(offset);
                final BlockRailBase.EnumRailDirection enumRailDirection = (BlockRailBase.EnumRailDirection)((blockState.getBlock() instanceof BlockRailBase) ? blockState.getValue(((BlockRailBase)blockState.getBlock()).getShapeProperty()) : BlockRailBase.EnumRailDirection.NORTH_SOUTH);
                double n4;
                if (BlockRailBase.isRailBlock(blockState)) {
                    if (enumRailDirection.isAscending()) {
                        n4 = 0.6;
                    }
                    else {
                        n4 = 0.1;
                    }
                }
                else {
                    if (blockState.getBlock().getMaterial() != Material.air || !BlockRailBase.isRailBlock(world.getBlockState(offset.down()))) {
                        return this.behaviourDefaultDispenseItem.dispense(blockSource, itemStack);
                    }
                    final IBlockState blockState2 = world.getBlockState(offset.down());
                    final BlockRailBase.EnumRailDirection enumRailDirection2 = (BlockRailBase.EnumRailDirection)((blockState2.getBlock() instanceof BlockRailBase) ? blockState2.getValue(((BlockRailBase)blockState2.getBlock()).getShapeProperty()) : BlockRailBase.EnumRailDirection.NORTH_SOUTH);
                    if (facing != EnumFacing.DOWN && enumRailDirection2.isAscending()) {
                        n4 = -0.4;
                    }
                    else {
                        n4 = -0.9;
                    }
                }
                final EntityMinecart func_180458_a = EntityMinecart.func_180458_a(world, n, n2 + n4, n3, ItemMinecart.access$000((ItemMinecart)itemStack.getItem()));
                if (itemStack.hasDisplayName()) {
                    func_180458_a.setCustomNameTag(itemStack.getDisplayName());
                }
                world.spawnEntityInWorld(func_180458_a);
                itemStack.splitStack(1);
                return itemStack;
            }
        };
    }
    
    public ItemMinecart(final EntityMinecart.EnumMinecartType minecartType) {
        this.maxStackSize = 1;
        this.minecartType = minecartType;
        this.setCreativeTab(CreativeTabs.tabTransport);
        BlockDispenser.dispenseBehaviorRegistry.putObject(this, ItemMinecart.dispenserMinecartBehavior);
    }
    
    static EntityMinecart.EnumMinecartType access$000(final ItemMinecart itemMinecart) {
        return itemMinecart.minecartType;
    }
}
