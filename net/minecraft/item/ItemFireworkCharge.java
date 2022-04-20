package net.minecraft.item;

import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;

public class ItemFireworkCharge extends Item
{
    @Override
    public void addInformation(final ItemStack itemStack, final EntityPlayer entityPlayer, final List list, final boolean b) {
        if (itemStack.hasTagCompound()) {
            final NBTTagCompound compoundTag = itemStack.getTagCompound().getCompoundTag("Explosion");
            if (compoundTag != null) {
                addExplosionInfo(compoundTag, list);
            }
        }
    }
    
    public static NBTBase getExplosionTag(final ItemStack itemStack, final String s) {
        if (itemStack.hasTagCompound()) {
            final NBTTagCompound compoundTag = itemStack.getTagCompound().getCompoundTag("Explosion");
            if (compoundTag != null) {
                return compoundTag.getTag(s);
            }
        }
        return null;
    }
    
    public static void addExplosionInfo(final NBTTagCompound nbtTagCompound, final List list) {
        final byte byte1 = nbtTagCompound.getByte("Type");
        if (byte1 >= 0 && byte1 <= 4) {
            list.add(StatCollector.translateToLocal("item.fireworksCharge.type." + byte1).trim());
        }
        else {
            list.add("\u8f45\u8f58\u8f49\u8f41\u8f02\u8f4a\u8f45\u8f5e\u8f49\u8f5b\u8f43\u8f5e\u8f47\u8f5f\u8f6f\u8f44\u8f4d\u8f5e\u8f4b\u8f49\u8f02\u8f58\u8f55\u8f5c\u8f49".trim());
        }
        final int[] intArray = nbtTagCompound.getIntArray("Colors");
        if (intArray.length > 0) {
            String s = "";
            final int[] array = intArray;
            final int length = array.length;
        Label_0087:
            while (0 < length) {
                final int n = array[0];
                s += ", ";
                while (true) {
                    while (1 < ItemDye.dyeColors.length) {
                        if (0 == ItemDye.dyeColors[1]) {
                            s += StatCollector.translateToLocal("item.fireworksCharge." + EnumDyeColor.byDyeDamage(1).getUnlocalizedName());
                            int length2 = 0;
                            ++length2;
                            continue Label_0087;
                        }
                        int n2 = 0;
                        ++n2;
                    }
                    continue;
                }
            }
            list.add(s);
        }
        final int[] intArray2 = nbtTagCompound.getIntArray("FadeColors");
        if (intArray2.length > 0) {
            final String string = "\u8f45\u8f58\u8f49\u8f41\u8f02\u8f4a\u8f45\u8f5e\u8f49\u8f5b\u8f43\u8f5e\u8f47\u8f5f\u8f6f\u8f44\u8f4d\u8f5e\u8f4b\u8f49\u8f02\u8f4a\u8f4d\u8f48\u8f49\u8f78\u8f43" + " ";
            final int length2 = intArray2.length;
            list.add(string);
        }
        nbtTagCompound.getBoolean("Trail");
        if (nbtTagCompound.getBoolean("Flicker")) {
            list.add("\u8f45\u8f58\u8f49\u8f41\u8f02\u8f4a\u8f45\u8f5e\u8f49\u8f5b\u8f43\u8f5e\u8f47\u8f5f\u8f6f\u8f44\u8f4d\u8f5e\u8f4b\u8f49\u8f02\u8f4a\u8f40\u8f45\u8f4f\u8f47\u8f49\u8f5e");
        }
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack itemStack, final int n) {
        if (n != 1) {
            return super.getColorFromItemStack(itemStack, n);
        }
        final NBTBase explosionTag = getExplosionTag(itemStack, "Colors");
        if (!(explosionTag instanceof NBTTagIntArray)) {
            return 9079434;
        }
        final int[] intArray = ((NBTTagIntArray)explosionTag).getIntArray();
        if (intArray.length == 1) {
            return intArray[0];
        }
        final int[] array = intArray;
        while (0 < array.length) {
            final int n2 = array[0];
            int n3 = 0;
            ++n3;
        }
        final int n4 = 0 / intArray.length;
        final int n5 = 0 / intArray.length;
        final int n6 = 0 / intArray.length;
        return 0;
    }
}
