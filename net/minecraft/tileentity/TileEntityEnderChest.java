package net.minecraft.tileentity;

import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;

public class TileEntityEnderChest extends TileEntity implements ITickable
{
    public float prevLidAngle;
    private int ticksSinceSync;
    public int numPlayersUsing;
    public float lidAngle;
    
    @Override
    public void invalidate() {
        this.updateContainingBlockInfo();
        super.invalidate();
    }
    
    public boolean canBeUsed(final EntityPlayer entityPlayer) {
        return this.worldObj.getTileEntity(this.pos) == this && entityPlayer.getDistanceSq(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64.0;
    }
    
    public void openChest() {
        ++this.numPlayersUsing;
        this.worldObj.addBlockEvent(this.pos, Blocks.ender_chest, 1, this.numPlayersUsing);
    }
    
    public void closeChest() {
        --this.numPlayersUsing;
        this.worldObj.addBlockEvent(this.pos, Blocks.ender_chest, 1, this.numPlayersUsing);
    }
    
    @Override
    public boolean receiveClientEvent(final int n, final int numPlayersUsing) {
        if (n == 1) {
            this.numPlayersUsing = numPlayersUsing;
            return true;
        }
        return super.receiveClientEvent(n, numPlayersUsing);
    }
    
    @Override
    public void update() {
        if (++this.ticksSinceSync % 20 * 4 == 0) {
            this.worldObj.addBlockEvent(this.pos, Blocks.ender_chest, 1, this.numPlayersUsing);
        }
        this.prevLidAngle = this.lidAngle;
        final int x = this.pos.getX();
        final int y = this.pos.getY();
        final int z = this.pos.getZ();
        final float n = 0.1f;
        if (this.numPlayersUsing > 0 && this.lidAngle == 0.0f) {
            this.worldObj.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "random.chestopen", 0.5f, this.worldObj.rand.nextFloat() * 0.1f + 0.9f);
        }
        if ((this.numPlayersUsing == 0 && this.lidAngle > 0.0f) || (this.numPlayersUsing > 0 && this.lidAngle < 1.0f)) {
            final float lidAngle = this.lidAngle;
            if (this.numPlayersUsing > 0) {
                this.lidAngle += n;
            }
            else {
                this.lidAngle -= n;
            }
            if (this.lidAngle > 1.0f) {
                this.lidAngle = 1.0f;
            }
            final float n2 = 0.5f;
            if (this.lidAngle < n2 && lidAngle >= n2) {
                this.worldObj.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "random.chestclosed", 0.5f, this.worldObj.rand.nextFloat() * 0.1f + 0.9f);
            }
            if (this.lidAngle < 0.0f) {
                this.lidAngle = 0.0f;
            }
        }
    }
}
