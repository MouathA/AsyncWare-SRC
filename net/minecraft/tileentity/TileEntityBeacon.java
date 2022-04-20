package net.minecraft.tileentity;

import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.potion.*;
import net.minecraft.item.*;
import net.minecraft.entity.passive.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.stats.*;
import net.minecraft.block.state.*;
import net.minecraft.nbt.*;
import com.google.common.collect.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;

public class TileEntityBeacon extends TileEntityLockable implements ITickable, IInventory
{
    private String customName;
    private int primaryEffect;
    private ItemStack payment;
    private long beamRenderCounter;
    private boolean isComplete;
    private final List beamSegments;
    private int levels;
    private float field_146014_j;
    public static final Potion[][] effectsList;
    private int secondaryEffect;
    
    @Override
    public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
        return new ContainerBeacon(inventoryPlayer, this);
    }
    
    @Override
    public String getGuiID() {
        return "minecraft:beacon";
    }
    
    @Override
    public int getFieldCount() {
        return 3;
    }
    
    private int func_183001_h(final int n) {
        if (n >= 0 && n < Potion.potionTypes.length && Potion.potionTypes[n] != null) {
            final Potion potion = Potion.potionTypes[n];
            return (potion != Potion.moveSpeed && potion != Potion.digSpeed && potion != Potion.resistance && potion != Potion.jump && potion != Potion.damageBoost && potion != Potion.regeneration) ? 0 : n;
        }
        return 0;
    }
    
    @Override
    public void setInventorySlotContents(final int n, final ItemStack payment) {
        if (n == 0) {
            this.payment = payment;
        }
    }
    
    @Override
    public void closeInventory(final EntityPlayer entityPlayer) {
    }
    
    private void addEffectsToPlayers() {
        if (this.isComplete && this.levels > 0 && !this.worldObj.isRemote && this.primaryEffect > 0) {
            final double n = this.levels * 10 + 10;
            if (this.levels < 4 || this.primaryEffect == this.secondaryEffect) {}
            final int x = this.pos.getX();
            final int y = this.pos.getY();
            final int z = this.pos.getZ();
            final List entitiesWithinAABB = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1).expand(n, n, n).addCoord(0.0, this.worldObj.getHeight(), 0.0));
            final Iterator<EntityPlayer> iterator = entitiesWithinAABB.iterator();
            while (iterator.hasNext()) {
                iterator.next().addPotionEffect(new PotionEffect(this.primaryEffect, 180, 1, true, true));
            }
            if (this.levels >= 4 && this.primaryEffect != this.secondaryEffect && this.secondaryEffect > 0) {
                final Iterator<EntityPlayer> iterator2 = entitiesWithinAABB.iterator();
                while (iterator2.hasNext()) {
                    iterator2.next().addPotionEffect(new PotionEffect(this.secondaryEffect, 180, 0, true, true));
                }
            }
        }
    }
    
    @Override
    public ItemStack decrStackSize(final int n, final int n2) {
        if (n != 0 || this.payment == null) {
            return null;
        }
        if (n2 >= this.payment.stackSize) {
            final ItemStack payment = this.payment;
            this.payment = null;
            return payment;
        }
        final ItemStack payment2 = this.payment;
        payment2.stackSize -= n2;
        return new ItemStack(this.payment.getItem(), n2, this.payment.getMetadata());
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer entityPlayer) {
        return this.worldObj.getTileEntity(this.pos) == this && entityPlayer.getDistanceSq(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64.0;
    }
    
    private void updateSegmentColors() {
        final int levels = this.levels;
        final int x = this.pos.getX();
        final int y = this.pos.getY();
        final int z = this.pos.getZ();
        this.levels = 0;
        this.beamSegments.clear();
        this.isComplete = true;
        BeamSegment beamSegment = new BeamSegment(EntitySheep.func_175513_a(EnumDyeColor.WHITE));
        this.beamSegments.add(beamSegment);
        final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        int n = y + 1;
        while (true) {
            final IBlockState blockState = this.worldObj.getBlockState(mutableBlockPos.func_181079_c(x, 1, z));
            Label_0326: {
                float[] array;
                if (blockState.getBlock() == Blocks.stained_glass) {
                    array = EntitySheep.func_175513_a((EnumDyeColor)blockState.getValue(BlockStainedGlass.COLOR));
                }
                else if (blockState.getBlock() != Blocks.stained_glass_pane) {
                    if (blockState.getBlock().getLightOpacity() >= 15 && blockState.getBlock() != Blocks.bedrock) {
                        break;
                    }
                    beamSegment.incrementHeight();
                    break Label_0326;
                }
                else {
                    array = EntitySheep.func_175513_a((EnumDyeColor)blockState.getValue(BlockStainedGlassPane.COLOR));
                }
                final float[] array2 = { (beamSegment.getColors()[0] + array[0]) / 2.0f, (beamSegment.getColors()[1] + array[1]) / 2.0f, (beamSegment.getColors()[2] + array[2]) / 2.0f };
                if (Arrays.equals(array2, beamSegment.getColors())) {
                    beamSegment.incrementHeight();
                }
                else {
                    beamSegment = new BeamSegment(array2);
                    this.beamSegments.add(beamSegment);
                }
            }
            ++n;
        }
        this.isComplete = false;
        this.beamSegments.clear();
        if (this.isComplete) {
            if (y - 1 >= 0) {
                if (x - 1 <= x + 1) {}
            }
            if (this.levels == 0) {
                this.isComplete = false;
            }
        }
        if (!this.worldObj.isRemote && this.levels == 4 && levels < this.levels) {
            final Iterator iterator = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(x, y, z, x, y - 4, z).expand(10.0, 5.0, 10.0)).iterator();
            while (iterator.hasNext()) {
                iterator.next().triggerAchievement(AchievementList.fullBeacon);
            }
        }
    }
    
    static {
        effectsList = new Potion[][] { { Potion.moveSpeed, Potion.digSpeed }, { Potion.resistance, Potion.jump }, { Potion.damageBoost }, { Potion.regeneration } };
    }
    
    @Override
    public void openInventory(final EntityPlayer entityPlayer) {
    }
    
    public void updateBeacon() {
        this.updateSegmentColors();
        this.addEffectsToPlayers();
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 1;
    }
    
    @Override
    public void setField(final int n, final int levels) {
        switch (n) {
            case 0: {
                this.levels = levels;
                break;
            }
            case 1: {
                this.primaryEffect = this.func_183001_h(levels);
                break;
            }
            case 2: {
                this.secondaryEffect = this.func_183001_h(levels);
                break;
            }
        }
    }
    
    @Override
    public String getName() {
        return (this != null) ? this.customName : "container.beacon";
    }
    
    @Override
    public boolean receiveClientEvent(final int n, final int n2) {
        if (n == 1) {
            this.updateBeacon();
            return true;
        }
        return super.receiveClientEvent(n, n2);
    }
    
    @Override
    public int getSizeInventory() {
        return 1;
    }
    
    public void setName(final String customName) {
        this.customName = customName;
    }
    
    @Override
    public int getField(final int n) {
        switch (n) {
            case 0: {
                return this.levels;
            }
            case 1: {
                return this.primaryEffect;
            }
            case 2: {
                return this.secondaryEffect;
            }
            default: {
                return 0;
            }
        }
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        this.primaryEffect = this.func_183001_h(nbtTagCompound.getInteger("Primary"));
        this.secondaryEffect = this.func_183001_h(nbtTagCompound.getInteger("Secondary"));
        this.levels = nbtTagCompound.getInteger("Levels");
    }
    
    @Override
    public double getMaxRenderDistanceSquared() {
        return 65536.0;
    }
    
    public TileEntityBeacon() {
        this.beamSegments = Lists.newArrayList();
        this.levels = -1;
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("Primary", this.primaryEffect);
        nbtTagCompound.setInteger("Secondary", this.secondaryEffect);
        nbtTagCompound.setInteger("Levels", this.levels);
    }
    
    @Override
    public Packet getDescriptionPacket() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.writeToNBT(nbtTagCompound);
        return new S35PacketUpdateTileEntity(this.pos, 3, nbtTagCompound);
    }
    
    @Override
    public ItemStack removeStackFromSlot(final int n) {
        if (n == 0 && this.payment != null) {
            final ItemStack payment = this.payment;
            this.payment = null;
            return payment;
        }
        return null;
    }
    
    @Override
    public void update() {
        if (this.worldObj.getTotalWorldTime() % 80L == 0L) {
            this.updateBeacon();
        }
    }
    
    @Override
    public void clear() {
        this.payment = null;
    }
    
    @Override
    public boolean isItemValidForSlot(final int n, final ItemStack itemStack) {
        return itemStack.getItem() == Items.emerald || itemStack.getItem() == Items.diamond || itemStack.getItem() == Items.gold_ingot || itemStack.getItem() == Items.iron_ingot;
    }
    
    public List getBeamSegments() {
        return this.beamSegments;
    }
    
    public float shouldBeamRender() {
        if (!this.isComplete) {
            return 0.0f;
        }
        final int n = (int)(this.worldObj.getTotalWorldTime() - this.beamRenderCounter);
        this.beamRenderCounter = this.worldObj.getTotalWorldTime();
        if (n > 1) {
            this.field_146014_j -= n / 40.0f;
            if (this.field_146014_j < 0.0f) {
                this.field_146014_j = 0.0f;
            }
        }
        this.field_146014_j += 0.025f;
        if (this.field_146014_j > 1.0f) {
            this.field_146014_j = 1.0f;
        }
        return this.field_146014_j;
    }
    
    @Override
    public ItemStack getStackInSlot(final int n) {
        return (n == 0) ? this.payment : null;
    }
    
    public static class BeamSegment
    {
        private int height;
        private final float[] colors;
        
        public int getHeight() {
            return this.height;
        }
        
        public BeamSegment(final float[] colors) {
            this.colors = colors;
            this.height = 1;
        }
        
        public float[] getColors() {
            return this.colors;
        }
        
        protected void incrementHeight() {
            ++this.height;
        }
    }
}
