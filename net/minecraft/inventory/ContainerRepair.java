package net.minecraft.inventory;

import net.minecraft.util.*;
import net.minecraft.world.*;
import org.apache.commons.lang3.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.enchantment.*;
import java.util.*;
import org.apache.logging.log4j.*;

public class ContainerRepair extends Container
{
    private String repairedItemName;
    private IInventory outputSlot;
    private IInventory inputSlots;
    private static final Logger logger;
    private int materialCost;
    public int maximumCost;
    private BlockPos selfPosition;
    private World theWorld;
    private final EntityPlayer thePlayer;
    
    @Override
    public void onCraftGuiOpened(final ICrafting crafting) {
        super.onCraftGuiOpened(crafting);
        crafting.sendProgressBarUpdate(this, 0, this.maximumCost);
    }
    
    @Override
    public void onCraftMatrixChanged(final IInventory inventory) {
        super.onCraftMatrixChanged(inventory);
        if (inventory == this.inputSlots) {
            this.updateRepairOutput();
        }
    }
    
    public void updateItemName(final String repairedItemName) {
        this.repairedItemName = repairedItemName;
        if (this.getSlot(2).getHasStack()) {
            final ItemStack stack = this.getSlot(2).getStack();
            if (StringUtils.isBlank((CharSequence)repairedItemName)) {
                stack.clearCustomName();
            }
            else {
                stack.setStackDisplayName(this.repairedItemName);
            }
        }
        this.updateRepairOutput();
    }
    
    static IInventory access$000(final ContainerRepair containerRepair) {
        return containerRepair.inputSlots;
    }
    
    static int access$100(final ContainerRepair containerRepair) {
        return containerRepair.materialCost;
    }
    
    public ContainerRepair(final InventoryPlayer inventoryPlayer, final World world, final EntityPlayer entityPlayer) {
        this(inventoryPlayer, world, BlockPos.ORIGIN, entityPlayer);
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int n) {
        ItemStack copy = null;
        final Slot slot = this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            final ItemStack stack = slot.getStack();
            copy = stack.copy();
            if (n == 2) {
                if (!this.mergeItemStack(stack, 3, 39, true)) {
                    return null;
                }
                slot.onSlotChange(stack, copy);
            }
            else if (n != 0 && n != 1) {
                if (n >= 3 && n < 39 && !this.mergeItemStack(stack, 0, 2, false)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(stack, 3, 39, false)) {
                return null;
            }
            if (stack.stackSize == 0) {
                slot.putStack(null);
            }
            else {
                slot.onSlotChanged();
            }
            if (stack.stackSize == copy.stackSize) {
                return null;
            }
            slot.onPickupFromSlot(entityPlayer, stack);
        }
        return copy;
    }
    
    public ContainerRepair(final InventoryPlayer inventoryPlayer, final World theWorld, final BlockPos selfPosition, final EntityPlayer thePlayer) {
        this.outputSlot = new InventoryCraftResult();
        this.inputSlots = new InventoryBasic(this, "Repair", true, 2) {
            final ContainerRepair this$0;
            
            @Override
            public void markDirty() {
                super.markDirty();
                this.this$0.onCraftMatrixChanged(this);
            }
        };
        this.selfPosition = selfPosition;
        this.theWorld = theWorld;
        this.thePlayer = thePlayer;
        this.addSlotToContainer(new Slot(this.inputSlots, 0, 27, 47));
        this.addSlotToContainer(new Slot(this.inputSlots, 1, 76, 47));
        this.addSlotToContainer(new Slot(this, this.outputSlot, 2, 134, 47, theWorld, selfPosition) {
            final BlockPos val$blockPosIn;
            final ContainerRepair this$0;
            final World val$worldIn;
            
            @Override
            public void onPickupFromSlot(final EntityPlayer entityPlayer, final ItemStack itemStack) {
                if (!entityPlayer.capabilities.isCreativeMode) {
                    entityPlayer.addExperienceLevel(-this.this$0.maximumCost);
                }
                ContainerRepair.access$000(this.this$0).setInventorySlotContents(0, null);
                if (ContainerRepair.access$100(this.this$0) > 0) {
                    final ItemStack stackInSlot = ContainerRepair.access$000(this.this$0).getStackInSlot(1);
                    if (stackInSlot != null && stackInSlot.stackSize > ContainerRepair.access$100(this.this$0)) {
                        final ItemStack itemStack2 = stackInSlot;
                        itemStack2.stackSize -= ContainerRepair.access$100(this.this$0);
                        ContainerRepair.access$000(this.this$0).setInventorySlotContents(1, stackInSlot);
                    }
                    else {
                        ContainerRepair.access$000(this.this$0).setInventorySlotContents(1, null);
                    }
                }
                else {
                    ContainerRepair.access$000(this.this$0).setInventorySlotContents(1, null);
                }
                this.this$0.maximumCost = 0;
                final IBlockState blockState = this.val$worldIn.getBlockState(this.val$blockPosIn);
                if (!entityPlayer.capabilities.isCreativeMode && !this.val$worldIn.isRemote && blockState.getBlock() == Blocks.anvil && entityPlayer.getRNG().nextFloat() < 0.12f) {
                    int intValue = (int)blockState.getValue(BlockAnvil.DAMAGE);
                    if (++intValue > 2) {
                        this.val$worldIn.setBlockToAir(this.val$blockPosIn);
                        this.val$worldIn.playAuxSFX(1020, this.val$blockPosIn, 0);
                    }
                    else {
                        this.val$worldIn.setBlockState(this.val$blockPosIn, blockState.withProperty(BlockAnvil.DAMAGE, intValue), 2);
                        this.val$worldIn.playAuxSFX(1021, this.val$blockPosIn, 0);
                    }
                }
                else if (!this.val$worldIn.isRemote) {
                    this.val$worldIn.playAuxSFX(1021, this.val$blockPosIn, 0);
                }
            }
            
            @Override
            public boolean canTakeStack(final EntityPlayer entityPlayer) {
                return (entityPlayer.capabilities.isCreativeMode || entityPlayer.experienceLevel >= this.this$0.maximumCost) && this.this$0.maximumCost > 0 && this.getHasStack();
            }
            
            @Override
            public boolean isItemValid(final ItemStack itemStack) {
                return false;
            }
        });
        while (true) {
            this.addSlotToContainer(new Slot(inventoryPlayer, 9, 8, 84));
            int n = 0;
            ++n;
        }
    }
    
    public void updateRepairOutput() {
        final ItemStack stackInSlot = this.inputSlots.getStackInSlot(0);
        this.maximumCost = 1;
        if (stackInSlot == null) {
            this.outputSlot.setInventorySlotContents(0, null);
            this.maximumCost = 0;
        }
        else {
            final ItemStack copy = stackInSlot.copy();
            final ItemStack stackInSlot2 = this.inputSlots.getStackInSlot(1);
            final Map enchantments = EnchantmentHelper.getEnchantments(copy);
            final int n = 0 + stackInSlot.getRepairCost() + ((stackInSlot2 == null) ? 0 : stackInSlot2.getRepairCost());
            this.materialCost = 0;
            if (stackInSlot2 != null) {
                final boolean b = stackInSlot2.getItem() == Items.enchanted_book && Items.enchanted_book.getEnchantments(stackInSlot2).tagCount() > 0;
                if (copy.isItemStackDamageable() && copy.getItem().getIsRepairable(stackInSlot, stackInSlot2)) {
                    int n2 = Math.min(copy.getItemDamage(), copy.getMaxDamage() / 4);
                    if (n2 <= 0) {
                        this.outputSlot.setInventorySlotContents(0, null);
                        this.maximumCost = 0;
                        return;
                    }
                    while (n2 > 0 && 0 < stackInSlot2.stackSize) {
                        copy.setItemDamage(copy.getItemDamage() - n2);
                        int n3 = 0;
                        ++n3;
                        n2 = Math.min(copy.getItemDamage(), copy.getMaxDamage() / 4);
                        int n4 = 0;
                        ++n4;
                    }
                    this.materialCost = 0;
                }
                else {
                    if (copy.getItem() != stackInSlot2.getItem() || !copy.isItemStackDamageable()) {
                        this.outputSlot.setInventorySlotContents(0, null);
                        this.maximumCost = 0;
                        return;
                    }
                    int n3 = 0;
                    if (copy.isItemStackDamageable()) {
                        final int n5 = stackInSlot.getMaxDamage() - stackInSlot.getItemDamage();
                        final int n4 = stackInSlot2.getMaxDamage() - stackInSlot2.getItemDamage();
                        final int n6 = copy.getMaxDamage() - (n5 + (0 + copy.getMaxDamage() * 12 / 100));
                        if (0 < copy.getMetadata()) {
                            copy.setItemDamage(0);
                            n3 += 2;
                        }
                    }
                    final Map enchantments2 = EnchantmentHelper.getEnchantments(stackInSlot2);
                    for (final int intValue : enchantments2.keySet()) {
                        final Enchantment enchantmentById = Enchantment.getEnchantmentById(intValue);
                        if (enchantmentById != null) {
                            final int n7 = enchantments.containsKey(intValue) ? enchantments.get(intValue) : 0;
                            int intValue2 = (int)enchantments2.get(intValue);
                            if (intValue2 == 0) {
                                ++intValue2;
                            }
                            else {
                                Math.max(intValue2, 0);
                            }
                            enchantmentById.canApply(stackInSlot);
                            if (this.thePlayer.capabilities.isCreativeMode || stackInSlot.getItem() == Items.enchanted_book) {}
                            final Iterator<K> iterator2 = enchantments.keySet().iterator();
                            while (iterator2.hasNext()) {
                                (int)iterator2.next();
                                if (intValue == 0 && !enchantmentById.canApplyTogether(Enchantment.getEnchantmentById(1))) {
                                    ++n3;
                                }
                            }
                        }
                    }
                }
            }
            if (StringUtils.isBlank((CharSequence)this.repairedItemName)) {
                if (stackInSlot.hasDisplayName()) {
                    copy.clearCustomName();
                }
            }
            else if (!this.repairedItemName.equals(stackInSlot.getDisplayName())) {
                copy.setStackDisplayName(this.repairedItemName);
            }
            this.maximumCost = 0;
            ItemStack itemStack = null;
            if (this.maximumCost >= 40 && !this.thePlayer.capabilities.isCreativeMode) {
                itemStack = null;
            }
            if (itemStack != null) {
                int n8 = itemStack.getRepairCost();
                if (stackInSlot2 != null && n8 < stackInSlot2.getRepairCost()) {
                    n8 = stackInSlot2.getRepairCost();
                }
                itemStack.setRepairCost(n8 * 2 + 1);
                EnchantmentHelper.setEnchantments(enchantments, itemStack);
            }
            this.outputSlot.setInventorySlotContents(0, itemStack);
            this.detectAndSendChanges();
        }
    }
    
    @Override
    public void onContainerClosed(final EntityPlayer entityPlayer) {
        super.onContainerClosed(entityPlayer);
        if (!this.theWorld.isRemote) {
            while (0 < this.inputSlots.getSizeInventory()) {
                final ItemStack removeStackFromSlot = this.inputSlots.removeStackFromSlot(0);
                if (removeStackFromSlot != null) {
                    entityPlayer.dropPlayerItemWithRandomChoice(removeStackFromSlot, false);
                }
                int n = 0;
                ++n;
            }
        }
    }
    
    @Override
    public void updateProgressBar(final int n, final int maximumCost) {
        if (n == 0) {
            this.maximumCost = maximumCost;
        }
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer entityPlayer) {
        return this.theWorld.getBlockState(this.selfPosition).getBlock() == Blocks.anvil && entityPlayer.getDistanceSq(this.selfPosition.getX() + 0.5, this.selfPosition.getY() + 0.5, this.selfPosition.getZ() + 0.5) <= 64.0;
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
