package net.minecraft.entity.item;

import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;

public class EntityMinecartChest extends EntityMinecartContainer
{
    @Override
    public int getDefaultDisplayTileOffset() {
        return 8;
    }
    
    @Override
    public int getSizeInventory() {
        return 27;
    }
    
    @Override
    public String getGuiID() {
        return "minecraft:chest";
    }
    
    public EntityMinecartChest(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3);
    }
    
    @Override
    public IBlockState getDefaultDisplayTile() {
        return Blocks.chest.getDefaultState().withProperty(BlockChest.FACING, EnumFacing.NORTH);
    }
    
    @Override
    public void killMinecart(final DamageSource damageSource) {
        super.killMinecart(damageSource);
        if (this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
            this.dropItemWithOffset(Item.getItemFromBlock(Blocks.chest), 1, 0.0f);
        }
    }
    
    @Override
    public EnumMinecartType getMinecartType() {
        return EnumMinecartType.CHEST;
    }
    
    @Override
    public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
        return new ContainerChest(inventoryPlayer, this, entityPlayer);
    }
    
    public EntityMinecartChest(final World world) {
        super(world);
    }
}
