package net.minecraft.item;

import net.minecraft.command.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.stats.*;
import java.util.*;
import net.minecraft.util.*;

public class ItemEditableBook extends Item
{
    private void resolveContents(final ItemStack itemStack, final EntityPlayer entityPlayer) {
        if (itemStack != null && itemStack.getTagCompound() != null) {
            final NBTTagCompound tagCompound = itemStack.getTagCompound();
            if (!tagCompound.getBoolean("resolved")) {
                tagCompound.setBoolean("resolved", true);
                if (tagCompound == 0) {
                    final NBTTagList tagList = tagCompound.getTagList("pages", 8);
                    while (0 < tagList.tagCount()) {
                        tagList.set(0, new NBTTagString(IChatComponent.Serializer.componentToJson(ChatComponentProcessor.processComponent(entityPlayer, IChatComponent.Serializer.jsonToComponent(tagList.getStringTagAt(0)), entityPlayer))));
                        int n = 0;
                        ++n;
                    }
                    tagCompound.setTag("pages", tagList);
                    if (entityPlayer instanceof EntityPlayerMP && entityPlayer.getCurrentEquippedItem() == itemStack) {
                        ((EntityPlayerMP)entityPlayer).playerNetServerHandler.sendPacket(new S2FPacketSetSlot(0, entityPlayer.openContainer.getSlotFromInventory(entityPlayer.inventory, entityPlayer.inventory.currentItem).slotNumber, itemStack));
                    }
                }
            }
        }
    }
    
    public static int getGeneration(final ItemStack itemStack) {
        return itemStack.getTagCompound().getInteger("generation");
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (!world.isRemote) {
            this.resolveContents(itemStack, entityPlayer);
        }
        entityPlayer.displayGUIBook(itemStack);
        entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return itemStack;
    }
    
    @Override
    public boolean hasEffect(final ItemStack itemStack) {
        return true;
    }
    
    @Override
    public String getItemStackDisplayName(final ItemStack itemStack) {
        if (itemStack.hasTagCompound()) {
            final String string = itemStack.getTagCompound().getString("title");
            if (!StringUtils.isNullOrEmpty(string)) {
                return string;
            }
        }
        return super.getItemStackDisplayName(itemStack);
    }
    
    @Override
    public void addInformation(final ItemStack itemStack, final EntityPlayer entityPlayer, final List list, final boolean b) {
        if (itemStack.hasTagCompound()) {
            final NBTTagCompound tagCompound = itemStack.getTagCompound();
            final String string = tagCompound.getString("author");
            if (!StringUtils.isNullOrEmpty(string)) {
                list.add(EnumChatFormatting.GRAY + StatCollector.translateToLocalFormatted("book.byAuthor", string));
            }
            list.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal("book.generation." + tagCompound.getInteger("generation")));
        }
    }
    
    public ItemEditableBook() {
        this.setMaxStackSize(1);
    }
}
