package net.minecraft.item;

import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.world.storage.*;
import net.minecraft.world.*;
import net.minecraft.stats.*;
import net.minecraft.creativetab.*;

public class ItemEmptyMap extends ItemMapBase
{
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        final ItemStack itemStack2 = new ItemStack(Items.filled_map, 1, world.getUniqueDataId("map"));
        final String string = "map_" + itemStack2.getMetadata();
        final MapData mapData = new MapData(string);
        world.setItemData(string, mapData);
        mapData.scale = 0;
        mapData.calculateMapCenter(entityPlayer.posX, entityPlayer.posZ, mapData.scale);
        mapData.dimension = (byte)world.provider.getDimensionId();
        mapData.markDirty();
        --itemStack.stackSize;
        if (itemStack.stackSize <= 0) {
            return itemStack2;
        }
        if (!entityPlayer.inventory.addItemStackToInventory(itemStack2.copy())) {
            entityPlayer.dropPlayerItemWithRandomChoice(itemStack2, false);
        }
        entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return itemStack;
    }
    
    protected ItemEmptyMap() {
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
}
