package net.minecraft.item;

import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.stats.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class ItemMonsterPlacer extends Item
{
    @Override
    public String getItemStackDisplayName(final ItemStack itemStack) {
        String s = ("" + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name")).trim();
        final String stringFromID = EntityList.getStringFromID(itemStack.getMetadata());
        if (stringFromID != null) {
            s = s + " " + StatCollector.translateToLocal("entity." + stringFromID + ".name");
        }
        return s;
    }
    
    @Override
    public void getSubItems(final Item item, final CreativeTabs creativeTabs, final List list) {
        final Iterator<EntityList.EntityEggInfo> iterator = EntityList.entityEggs.values().iterator();
        while (iterator.hasNext()) {
            list.add(new ItemStack(item, 1, iterator.next().spawnedID));
        }
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (world.isRemote) {
            return itemStack;
        }
        final MovingObjectPosition movingObjectPositionFromPlayer = this.getMovingObjectPositionFromPlayer(world, entityPlayer, true);
        if (movingObjectPositionFromPlayer == null) {
            return itemStack;
        }
        if (movingObjectPositionFromPlayer.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            final BlockPos blockPos = movingObjectPositionFromPlayer.getBlockPos();
            if (!world.isBlockModifiable(entityPlayer, blockPos)) {
                return itemStack;
            }
            if (!entityPlayer.canPlayerEdit(blockPos, movingObjectPositionFromPlayer.sideHit, itemStack)) {
                return itemStack;
            }
            if (world.getBlockState(blockPos).getBlock() instanceof BlockLiquid) {
                final Entity spawnCreature = spawnCreature(world, itemStack.getMetadata(), blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
                if (spawnCreature != null) {
                    if (spawnCreature instanceof EntityLivingBase && itemStack.hasDisplayName()) {
                        ((EntityLiving)spawnCreature).setCustomNameTag(itemStack.getDisplayName());
                    }
                    if (!entityPlayer.capabilities.isCreativeMode) {
                        --itemStack.stackSize;
                    }
                    entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
                }
            }
        }
        return itemStack;
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, BlockPos offset, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return true;
        }
        if (!entityPlayer.canPlayerEdit(offset.offset(enumFacing), enumFacing, itemStack)) {
            return false;
        }
        final IBlockState blockState = world.getBlockState(offset);
        if (blockState.getBlock() == Blocks.mob_spawner) {
            final TileEntity tileEntity = world.getTileEntity(offset);
            if (tileEntity instanceof TileEntityMobSpawner) {
                ((TileEntityMobSpawner)tileEntity).getSpawnerBaseLogic().setEntityName(EntityList.getStringFromID(itemStack.getMetadata()));
                tileEntity.markDirty();
                world.markBlockForUpdate(offset);
                if (!entityPlayer.capabilities.isCreativeMode) {
                    --itemStack.stackSize;
                }
                return true;
            }
        }
        offset = offset.offset(enumFacing);
        double n4 = 0.0;
        if (enumFacing == EnumFacing.UP && blockState instanceof BlockFence) {
            n4 = 0.5;
        }
        final Entity spawnCreature = spawnCreature(world, itemStack.getMetadata(), offset.getX() + 0.5, offset.getY() + n4, offset.getZ() + 0.5);
        if (spawnCreature != null) {
            if (spawnCreature instanceof EntityLivingBase && itemStack.hasDisplayName()) {
                spawnCreature.setCustomNameTag(itemStack.getDisplayName());
            }
            if (!entityPlayer.capabilities.isCreativeMode) {
                --itemStack.stackSize;
            }
        }
        return true;
    }
    
    public static Entity spawnCreature(final World world, final int n, final double n2, final double n3, final double n4) {
        if (!EntityList.entityEggs.containsKey(n)) {
            return null;
        }
        while (true) {
            final Entity entityByID = EntityList.createEntityByID(n, world);
            if (entityByID instanceof EntityLivingBase) {
                final EntityLiving entityLiving = (EntityLiving)entityByID;
                entityByID.setLocationAndAngles(n2, n3, n4, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0f), 0.0f);
                entityLiving.rotationYawHead = entityLiving.rotationYaw;
                entityLiving.renderYawOffset = entityLiving.rotationYaw;
                entityLiving.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entityLiving)), null);
                world.spawnEntityInWorld(entityByID);
                entityLiving.playLivingSound();
            }
            int n5 = 0;
            ++n5;
        }
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack itemStack, final int n) {
        final EntityList.EntityEggInfo entityEggInfo = EntityList.entityEggs.get(itemStack.getMetadata());
        return (entityEggInfo != null) ? ((n == 0) ? entityEggInfo.primaryColor : entityEggInfo.secondaryColor) : 16777215;
    }
    
    public ItemMonsterPlacer() {
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
}
