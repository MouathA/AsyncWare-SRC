package net.minecraft.inventory;

import java.util.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

public class InventoryHelper
{
    private static final Random RANDOM;
    
    private static void func_180174_a(final World world, final double n, final double n2, final double n3, final IInventory inventory) {
        while (0 < inventory.getSizeInventory()) {
            final ItemStack stackInSlot = inventory.getStackInSlot(0);
            if (stackInSlot != null) {
                spawnItemStack(world, n, n2, n3, stackInSlot);
            }
            int n4 = 0;
            ++n4;
        }
    }
    
    static {
        RANDOM = new Random();
    }
    
    public static void func_180176_a(final World world, final Entity entity, final IInventory inventory) {
        func_180174_a(world, entity.posX, entity.posY, entity.posZ, inventory);
    }
    
    private static void spawnItemStack(final World world, final double n, final double n2, final double n3, final ItemStack itemStack) {
        final float n4 = InventoryHelper.RANDOM.nextFloat() * 0.8f + 0.1f;
        final float n5 = InventoryHelper.RANDOM.nextFloat() * 0.8f + 0.1f;
        final float n6 = InventoryHelper.RANDOM.nextFloat() * 0.8f + 0.1f;
        while (itemStack.stackSize > 0) {
            int stackSize = InventoryHelper.RANDOM.nextInt(21) + 10;
            if (stackSize > itemStack.stackSize) {
                stackSize = itemStack.stackSize;
            }
            itemStack.stackSize -= stackSize;
            final EntityItem entityItem = new EntityItem(world, n + n4, n2 + n5, n3 + n6, new ItemStack(itemStack.getItem(), stackSize, itemStack.getMetadata()));
            if (itemStack.hasTagCompound()) {
                entityItem.getEntityItem().setTagCompound((NBTTagCompound)itemStack.getTagCompound().copy());
            }
            final float n7 = 0.05f;
            entityItem.motionX = InventoryHelper.RANDOM.nextGaussian() * n7;
            entityItem.motionY = InventoryHelper.RANDOM.nextGaussian() * n7 + 0.20000000298023224;
            entityItem.motionZ = InventoryHelper.RANDOM.nextGaussian() * n7;
            world.spawnEntityInWorld(entityItem);
        }
    }
    
    public static void dropInventoryItems(final World world, final BlockPos blockPos, final IInventory inventory) {
        func_180174_a(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), inventory);
    }
}
