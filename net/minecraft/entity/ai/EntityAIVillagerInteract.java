package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.init.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;

public class EntityAIVillagerInteract extends EntityAIWatchClosest2
{
    private EntityVillager villager;
    private int interactionDelay;
    
    @Override
    public void updateTask() {
        super.updateTask();
        if (this.interactionDelay > 0) {
            --this.interactionDelay;
            if (this.interactionDelay == 0) {
                final InventoryBasic villagerInventory = this.villager.getVillagerInventory();
                while (0 < villagerInventory.getSizeInventory()) {
                    final ItemStack stackInSlot = villagerInventory.getStackInSlot(0);
                    ItemStack itemStack = null;
                    if (stackInSlot != null) {
                        final Item item = stackInSlot.getItem();
                        if ((item == Items.bread || item == Items.potato || item == Items.carrot) && stackInSlot.stackSize > 3) {
                            final int n = stackInSlot.stackSize / 2;
                            final ItemStack itemStack2 = stackInSlot;
                            itemStack2.stackSize -= n;
                            itemStack = new ItemStack(item, n, stackInSlot.getMetadata());
                        }
                        else if (item == Items.wheat && stackInSlot.stackSize > 5) {
                            final int n2 = stackInSlot.stackSize / 2 / 3 * 3;
                            final int n3 = n2 / 3;
                            final ItemStack itemStack3 = stackInSlot;
                            itemStack3.stackSize -= n2;
                            itemStack = new ItemStack(Items.bread, n3, 0);
                        }
                        if (stackInSlot.stackSize <= 0) {
                            villagerInventory.setInventorySlotContents(0, null);
                        }
                    }
                    if (itemStack != null) {
                        final EntityItem entityItem = new EntityItem(this.villager.worldObj, this.villager.posX, this.villager.posY - 0.30000001192092896 + this.villager.getEyeHeight(), this.villager.posZ, itemStack);
                        final float n4 = 0.3f;
                        final float rotationYawHead = this.villager.rotationYawHead;
                        final float rotationPitch = this.villager.rotationPitch;
                        entityItem.motionX = -MathHelper.sin(rotationYawHead / 180.0f * 3.1415927f) * MathHelper.cos(rotationPitch / 180.0f * 3.1415927f) * n4;
                        entityItem.motionZ = MathHelper.cos(rotationYawHead / 180.0f * 3.1415927f) * MathHelper.cos(rotationPitch / 180.0f * 3.1415927f) * n4;
                        entityItem.motionY = -MathHelper.sin(rotationPitch / 180.0f * 3.1415927f) * n4 + 0.1f;
                        entityItem.setDefaultPickupDelay();
                        this.villager.worldObj.spawnEntityInWorld(entityItem);
                        break;
                    }
                    int n5 = 0;
                    ++n5;
                }
            }
        }
    }
    
    public EntityAIVillagerInteract(final EntityVillager villager) {
        super(villager, EntityVillager.class, 3.0f, 0.02f);
        this.villager = villager;
    }
    
    @Override
    public void startExecuting() {
        super.startExecuting();
        if (this.villager.canAbondonItems() && this.closestEntity instanceof EntityVillager && ((EntityVillager)this.closestEntity).func_175557_cr()) {
            this.interactionDelay = 10;
        }
        else {
            this.interactionDelay = 0;
        }
    }
}
