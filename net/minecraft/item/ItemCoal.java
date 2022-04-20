package net.minecraft.item;

import net.minecraft.creativetab.*;
import java.util.*;

public class ItemCoal extends Item
{
    @Override
    public void getSubItems(final Item item, final CreativeTabs creativeTabs, final List list) {
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 1));
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack itemStack) {
        return (itemStack.getMetadata() == 1) ? "item.charcoal" : "item.coal";
    }
    
    public ItemCoal() {
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }
}
