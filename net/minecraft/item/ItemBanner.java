package net.minecraft.item;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

public class ItemBanner extends ItemBlock
{
    @Override
    public boolean onItemUse(final ItemStack itemValues, final EntityPlayer entityPlayer, final World world, BlockPos offset, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (enumFacing == EnumFacing.DOWN) {
            return false;
        }
        if (!world.getBlockState(offset).getBlock().getMaterial().isSolid()) {
            return false;
        }
        offset = offset.offset(enumFacing);
        if (!entityPlayer.canPlayerEdit(offset, enumFacing, itemValues)) {
            return false;
        }
        if (!Blocks.standing_banner.canPlaceBlockAt(world, offset)) {
            return false;
        }
        if (world.isRemote) {
            return true;
        }
        if (enumFacing == EnumFacing.UP) {
            world.setBlockState(offset, Blocks.standing_banner.getDefaultState().withProperty(BlockStandingSign.ROTATION, MathHelper.floor_double((entityPlayer.rotationYaw + 180.0f) * 16.0f / 360.0f + 0.5) & 0xF), 3);
        }
        else {
            world.setBlockState(offset, Blocks.wall_banner.getDefaultState().withProperty(BlockWallSign.FACING, enumFacing), 3);
        }
        --itemValues.stackSize;
        final TileEntity tileEntity = world.getTileEntity(offset);
        if (tileEntity instanceof TileEntityBanner) {
            ((TileEntityBanner)tileEntity).setItemValues(itemValues);
        }
        return true;
    }
    
    @Override
    public CreativeTabs getCreativeTab() {
        return CreativeTabs.tabDecorations;
    }
    
    @Override
    public void getSubItems(final Item item, final CreativeTabs creativeTabs, final List list) {
        final EnumDyeColor[] values = EnumDyeColor.values();
        while (0 < values.length) {
            final EnumDyeColor enumDyeColor = values[0];
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            TileEntityBanner.func_181020_a(nbtTagCompound, enumDyeColor.getDyeDamage(), null);
            final NBTTagCompound tagCompound = new NBTTagCompound();
            tagCompound.setTag("BlockEntityTag", nbtTagCompound);
            final ItemStack itemStack = new ItemStack(item, 1, enumDyeColor.getDyeDamage());
            itemStack.setTagCompound(tagCompound);
            list.add(itemStack);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void addInformation(final ItemStack itemStack, final EntityPlayer entityPlayer, final List list, final boolean b) {
        final NBTTagCompound subCompound = itemStack.getSubCompound("BlockEntityTag", false);
        if (subCompound != null && subCompound.hasKey("Patterns")) {
            final NBTTagList tagList = subCompound.getTagList("Patterns", 10);
            while (0 < tagList.tagCount()) {
                final NBTTagCompound compoundTag = tagList.getCompoundTagAt(0);
                final EnumDyeColor byDyeDamage = EnumDyeColor.byDyeDamage(compoundTag.getInteger("Color"));
                final TileEntityBanner.EnumBannerPattern patternByID = TileEntityBanner.EnumBannerPattern.getPatternByID(compoundTag.getString("Pattern"));
                if (patternByID != null) {
                    list.add(StatCollector.translateToLocal("item.banner." + patternByID.getPatternName() + "." + byDyeDamage.getUnlocalizedName()));
                }
                int n = 0;
                ++n;
            }
        }
    }
    
    @Override
    public String getItemStackDisplayName(final ItemStack itemStack) {
        return StatCollector.translateToLocal("item.banner." + this.getBaseColor(itemStack).getUnlocalizedName() + ".name");
    }
    
    public ItemBanner() {
        super(Blocks.standing_banner);
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }
    
    private EnumDyeColor getBaseColor(final ItemStack itemStack) {
        final NBTTagCompound subCompound = itemStack.getSubCompound("BlockEntityTag", false);
        EnumDyeColor enumDyeColor;
        if (subCompound != null && subCompound.hasKey("Base")) {
            enumDyeColor = EnumDyeColor.byDyeDamage(subCompound.getInteger("Base"));
        }
        else {
            enumDyeColor = EnumDyeColor.byDyeDamage(itemStack.getMetadata());
        }
        return enumDyeColor;
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack itemStack, final int n) {
        if (n == 0) {
            return 16777215;
        }
        return this.getBaseColor(itemStack).getMapColor().colorValue;
    }
}
