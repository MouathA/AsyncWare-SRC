package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.block.properties.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.*;

public class EntityAIHarvestFarmland extends EntityAIMoveToBlock
{
    private final EntityVillager theVillager;
    private int field_179501_f;
    private boolean hasFarmItem;
    private boolean field_179503_e;
    
    @Override
    public void resetTask() {
        super.resetTask();
    }
    
    @Override
    public void updateTask() {
        super.updateTask();
        this.theVillager.getLookHelper().setLookPosition(this.destinationBlock.getX() + 0.5, this.destinationBlock.getY() + 1, this.destinationBlock.getZ() + 0.5, 10.0f, (float)this.theVillager.getVerticalFaceSpeed());
        if (this.getIsAboveDestination()) {
            final World worldObj = this.theVillager.worldObj;
            final BlockPos up = this.destinationBlock.up();
            final IBlockState blockState = worldObj.getBlockState(up);
            final Block block = blockState.getBlock();
            if (this.field_179501_f == 0 && block instanceof BlockCrops && (int)blockState.getValue(BlockCrops.AGE) == 7) {
                worldObj.destroyBlock(up, true);
            }
            else if (this.field_179501_f == 1 && block == Blocks.air) {
                final InventoryBasic villagerInventory = this.theVillager.getVillagerInventory();
                if (0 < villagerInventory.getSizeInventory()) {
                    final ItemStack stackInSlot = villagerInventory.getStackInSlot(0);
                    if (stackInSlot != null) {
                        if (stackInSlot.getItem() == Items.wheat_seeds) {
                            worldObj.setBlockState(up, Blocks.wheat.getDefaultState(), 3);
                        }
                        else if (stackInSlot.getItem() == Items.potato) {
                            worldObj.setBlockState(up, Blocks.potatoes.getDefaultState(), 3);
                        }
                        else if (stackInSlot.getItem() == Items.carrot) {
                            worldObj.setBlockState(up, Blocks.carrots.getDefaultState(), 3);
                        }
                    }
                    final ItemStack itemStack = stackInSlot;
                    --itemStack.stackSize;
                    if (stackInSlot.stackSize <= 0) {
                        villagerInventory.setInventorySlotContents(0, null);
                    }
                }
            }
            this.field_179501_f = -1;
            this.runDelay = 10;
        }
    }
    
    public EntityAIHarvestFarmland(final EntityVillager theVillager, final double n) {
        super(theVillager, n, 16);
        this.theVillager = theVillager;
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.runDelay <= 0) {
            if (!this.theVillager.worldObj.getGameRules().getBoolean("mobGriefing")) {
                return false;
            }
            this.field_179501_f = -1;
            this.hasFarmItem = this.theVillager.isFarmItemInInventory();
            this.field_179503_e = this.theVillager.func_175557_cr();
        }
        return super.shouldExecute();
    }
    
    @Override
    public boolean continueExecuting() {
        return this.field_179501_f >= 0 && super.continueExecuting();
    }
    
    @Override
    public void startExecuting() {
        super.startExecuting();
    }
    
    @Override
    protected boolean shouldMoveTo(final World world, BlockPos up) {
        if (world.getBlockState(up).getBlock() == Blocks.farmland) {
            up = up.up();
            final IBlockState blockState = world.getBlockState(up);
            final Block block = blockState.getBlock();
            if (block instanceof BlockCrops && (int)blockState.getValue(BlockCrops.AGE) == 7 && this.field_179503_e && (this.field_179501_f == 0 || this.field_179501_f < 0)) {
                this.field_179501_f = 0;
                return true;
            }
            if (block == Blocks.air && this.hasFarmItem && (this.field_179501_f == 1 || this.field_179501_f < 0)) {
                this.field_179501_f = 1;
                return true;
            }
        }
        return false;
    }
}
