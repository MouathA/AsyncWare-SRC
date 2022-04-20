package net.minecraft.tileentity;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.util.*;

public class TileEntityEnchantmentTable extends TileEntity implements ITickable, IInteractionObject
{
    public float pageFlip;
    public int tickCount;
    private String customName;
    public float bookSpreadPrev;
    public float bookRotationPrev;
    public float bookRotation;
    public float field_145929_l;
    public float bookSpread;
    public float field_145924_q;
    public float pageFlipPrev;
    public float field_145932_k;
    private static Random rand;
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        if (this != null) {
            nbtTagCompound.setString("CustomName", this.customName);
        }
    }
    
    @Override
    public String getName() {
        return (this != null) ? this.customName : "container.enchant";
    }
    
    @Override
    public void update() {
        this.bookSpreadPrev = this.bookSpread;
        this.bookRotationPrev = this.bookRotation;
        final EntityPlayer closestPlayer = this.worldObj.getClosestPlayer(this.pos.getX() + 0.5f, this.pos.getY() + 0.5f, this.pos.getZ() + 0.5f, 3.0);
        if (closestPlayer != null) {
            this.field_145924_q = (float)MathHelper.func_181159_b(closestPlayer.posZ - (this.pos.getZ() + 0.5f), closestPlayer.posX - (this.pos.getX() + 0.5f));
            this.bookSpread += 0.1f;
            if (this.bookSpread < 0.5f || TileEntityEnchantmentTable.rand.nextInt(40) == 0) {
                do {
                    this.field_145932_k += TileEntityEnchantmentTable.rand.nextInt(4) - TileEntityEnchantmentTable.rand.nextInt(4);
                } while (this.field_145932_k == this.field_145932_k);
            }
        }
        else {
            this.field_145924_q += 0.02f;
            this.bookSpread -= 0.1f;
        }
        while (this.bookRotation >= 3.1415927f) {
            this.bookRotation -= 6.2831855f;
        }
        while (this.bookRotation < -3.1415927f) {
            this.bookRotation += 6.2831855f;
        }
        while (this.field_145924_q >= 3.1415927f) {
            this.field_145924_q -= 6.2831855f;
        }
        while (this.field_145924_q < -3.1415927f) {
            this.field_145924_q += 6.2831855f;
        }
        float n;
        for (n = this.field_145924_q - this.bookRotation; n >= 3.1415927f; n -= 6.2831855f) {}
        while (n < -3.1415927f) {
            n += 6.2831855f;
        }
        this.bookRotation += n * 0.4f;
        this.bookSpread = MathHelper.clamp_float(this.bookSpread, 0.0f, 1.0f);
        ++this.tickCount;
        this.pageFlipPrev = this.pageFlip;
        final float n2 = (this.field_145932_k - this.pageFlip) * 0.4f;
        final float n3 = 0.2f;
        this.field_145929_l += (MathHelper.clamp_float(n2, -n3, n3) - this.field_145929_l) * 0.9f;
        this.pageFlip += this.field_145929_l;
    }
    
    @Override
    public String getGuiID() {
        return "minecraft:enchanting_table";
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        if (nbtTagCompound.hasKey("CustomName", 8)) {
            this.customName = nbtTagCompound.getString("CustomName");
        }
    }
    
    static {
        TileEntityEnchantmentTable.rand = new Random();
    }
    
    @Override
    public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
        return new ContainerEnchantment(inventoryPlayer, this.worldObj, this.pos);
    }
    
    @Override
    public IChatComponent getDisplayName() {
        return (this != null) ? new ChatComponentText(this.getName()) : new ChatComponentTranslation(this.getName(), new Object[0]);
    }
    
    public void setCustomName(final String customName) {
        this.customName = customName;
    }
}
