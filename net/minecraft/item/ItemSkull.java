package net.minecraft.item;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import com.mojang.authlib.*;
import net.minecraft.tileentity.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;

public class ItemSkull extends Item
{
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, BlockPos offset, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (enumFacing == EnumFacing.DOWN) {
            return false;
        }
        if (!world.getBlockState(offset).getBlock().isReplaceable(world, offset)) {
            if (!world.getBlockState(offset).getBlock().getMaterial().isSolid()) {
                return false;
            }
            offset = offset.offset(enumFacing);
        }
        if (!entityPlayer.canPlayerEdit(offset, enumFacing, itemStack)) {
            return false;
        }
        if (!Blocks.skull.canPlaceBlockAt(world, offset)) {
            return false;
        }
        if (!world.isRemote) {
            world.setBlockState(offset, Blocks.skull.getDefaultState().withProperty(BlockSkull.FACING, enumFacing), 3);
            if (enumFacing == EnumFacing.UP) {
                final int n4 = MathHelper.floor_double(entityPlayer.rotationYaw * 16.0f / 360.0f + 0.5) & 0xF;
            }
            final TileEntity tileEntity = world.getTileEntity(offset);
            if (tileEntity instanceof TileEntitySkull) {
                final TileEntitySkull tileEntitySkull = (TileEntitySkull)tileEntity;
                if (itemStack.getMetadata() == 3) {
                    GameProfile gameProfileFromNBT = null;
                    if (itemStack.hasTagCompound()) {
                        final NBTTagCompound tagCompound = itemStack.getTagCompound();
                        if (tagCompound.hasKey("SkullOwner", 10)) {
                            gameProfileFromNBT = NBTUtil.readGameProfileFromNBT(tagCompound.getCompoundTag("SkullOwner"));
                        }
                        else if (tagCompound.hasKey("SkullOwner", 8) && tagCompound.getString("SkullOwner").length() > 0) {
                            gameProfileFromNBT = new GameProfile((UUID)null, tagCompound.getString("SkullOwner"));
                        }
                    }
                    tileEntitySkull.setPlayerProfile(gameProfileFromNBT);
                }
                else {
                    tileEntitySkull.setType(itemStack.getMetadata());
                }
                tileEntitySkull.setSkullRotation(0);
                Blocks.skull.checkWitherSpawn(world, offset, tileEntitySkull);
            }
            --itemStack.stackSize;
        }
        return true;
    }
    
    static {
        ItemSkull.skullTypes = new String[] { "skeleton", "wither", "zombie", "char", "creeper" };
    }
    
    public ItemSkull() {
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    @Override
    public void getSubItems(final Item item, final CreativeTabs creativeTabs, final List list) {
        while (0 < ItemSkull.skullTypes.length) {
            list.add(new ItemStack(item, 1, 0));
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public String getItemStackDisplayName(final ItemStack itemStack) {
        if (itemStack.getMetadata() == 3 && itemStack.hasTagCompound()) {
            if (itemStack.getTagCompound().hasKey("SkullOwner", 8)) {
                return StatCollector.translateToLocalFormatted("item.skull.player.name", itemStack.getTagCompound().getString("SkullOwner"));
            }
            if (itemStack.getTagCompound().hasKey("SkullOwner", 10)) {
                final NBTTagCompound compoundTag = itemStack.getTagCompound().getCompoundTag("SkullOwner");
                if (compoundTag.hasKey("Name", 8)) {
                    return StatCollector.translateToLocalFormatted("item.skull.player.name", compoundTag.getString("Name"));
                }
            }
        }
        return super.getItemStackDisplayName(itemStack);
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack itemStack) {
        itemStack.getMetadata();
        if (0 >= ItemSkull.skullTypes.length) {}
        return super.getUnlocalizedName() + "." + ItemSkull.skullTypes[0];
    }
    
    @Override
    public boolean updateItemStackNBT(final NBTTagCompound nbtTagCompound) {
        super.updateItemStackNBT(nbtTagCompound);
        if (nbtTagCompound.hasKey("SkullOwner", 8) && nbtTagCompound.getString("SkullOwner").length() > 0) {
            nbtTagCompound.setTag("SkullOwner", NBTUtil.writeGameProfile(new NBTTagCompound(), TileEntitySkull.updateGameprofile(new GameProfile((UUID)null, nbtTagCompound.getString("SkullOwner")))));
            return true;
        }
        return false;
    }
    
    @Override
    public int getMetadata(final int n) {
        return n;
    }
}
