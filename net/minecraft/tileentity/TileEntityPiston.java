package net.minecraft.tileentity;

import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.nbt.*;
import net.minecraft.block.*;

public class TileEntityPiston extends TileEntity implements ITickable
{
    private boolean extending;
    private float progress;
    private EnumFacing pistonFacing;
    private boolean shouldHeadBeRendered;
    private float lastProgress;
    private IBlockState pistonState;
    private List field_174933_k;
    
    public float getOffsetY(final float n) {
        return this.extending ? ((this.getProgress(n) - 1.0f) * this.pistonFacing.getFrontOffsetY()) : ((1.0f - this.getProgress(n)) * this.pistonFacing.getFrontOffsetY());
    }
    
    private void launchWithSlimeBlock(float n, final float n2) {
        if (this.extending) {
            n = 1.0f - n;
        }
        else {
            --n;
        }
        final AxisAlignedBB boundingBox = Blocks.piston_extension.getBoundingBox(this.worldObj, this.pos, this.pistonState, n, this.pistonFacing);
        if (boundingBox != null) {
            final List entitiesWithinAABBExcludingEntity = this.worldObj.getEntitiesWithinAABBExcludingEntity(null, boundingBox);
            if (!entitiesWithinAABBExcludingEntity.isEmpty()) {
                this.field_174933_k.addAll(entitiesWithinAABBExcludingEntity);
                for (final Entity entity : this.field_174933_k) {
                    if (this.pistonState.getBlock() == Blocks.slime_block && this.extending) {
                        switch (TileEntityPiston$1.$SwitchMap$net$minecraft$util$EnumFacing$Axis[this.pistonFacing.getAxis().ordinal()]) {
                            case 1: {
                                entity.motionX = this.pistonFacing.getFrontOffsetX();
                                continue;
                            }
                            case 2: {
                                entity.motionY = this.pistonFacing.getFrontOffsetY();
                                continue;
                            }
                            case 3: {
                                entity.motionZ = this.pistonFacing.getFrontOffsetZ();
                                continue;
                            }
                        }
                    }
                    else {
                        entity.moveEntity(n2 * this.pistonFacing.getFrontOffsetX(), n2 * this.pistonFacing.getFrontOffsetY(), n2 * this.pistonFacing.getFrontOffsetZ());
                    }
                }
                this.field_174933_k.clear();
            }
        }
    }
    
    public TileEntityPiston() {
        this.field_174933_k = Lists.newArrayList();
    }
    
    public void clearPistonTileEntity() {
        if (this.lastProgress < 1.0f && this.worldObj != null) {
            final float n = 1.0f;
            this.progress = n;
            this.lastProgress = n;
            this.worldObj.removeTileEntity(this.pos);
            this.invalidate();
            if (this.worldObj.getBlockState(this.pos).getBlock() == Blocks.piston_extension) {
                this.worldObj.setBlockState(this.pos, this.pistonState, 3);
                this.worldObj.notifyBlockOfStateChange(this.pos, this.pistonState.getBlock());
            }
        }
    }
    
    @Override
    public int getBlockMetadata() {
        return 0;
    }
    
    public float getOffsetX(final float n) {
        return this.extending ? ((this.getProgress(n) - 1.0f) * this.pistonFacing.getFrontOffsetX()) : ((1.0f - this.getProgress(n)) * this.pistonFacing.getFrontOffsetX());
    }
    
    public EnumFacing getFacing() {
        return this.pistonFacing;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        this.pistonState = Block.getBlockById(nbtTagCompound.getInteger("blockId")).getStateFromMeta(nbtTagCompound.getInteger("blockData"));
        this.pistonFacing = EnumFacing.getFront(nbtTagCompound.getInteger("facing"));
        final float float1 = nbtTagCompound.getFloat("progress");
        this.progress = float1;
        this.lastProgress = float1;
        this.extending = nbtTagCompound.getBoolean("extending");
    }
    
    public float getOffsetZ(final float n) {
        return this.extending ? ((this.getProgress(n) - 1.0f) * this.pistonFacing.getFrontOffsetZ()) : ((1.0f - this.getProgress(n)) * this.pistonFacing.getFrontOffsetZ());
    }
    
    public TileEntityPiston(final IBlockState pistonState, final EnumFacing pistonFacing, final boolean extending, final boolean shouldHeadBeRendered) {
        this.field_174933_k = Lists.newArrayList();
        this.pistonState = pistonState;
        this.pistonFacing = pistonFacing;
        this.extending = extending;
        this.shouldHeadBeRendered = shouldHeadBeRendered;
    }
    
    public IBlockState getPistonState() {
        return this.pistonState;
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("blockId", Block.getIdFromBlock(this.pistonState.getBlock()));
        nbtTagCompound.setInteger("blockData", this.pistonState.getBlock().getMetaFromState(this.pistonState));
        nbtTagCompound.setInteger("facing", this.pistonFacing.getIndex());
        nbtTagCompound.setFloat("progress", this.lastProgress);
        nbtTagCompound.setBoolean("extending", this.extending);
    }
    
    public float getProgress(float n) {
        if (n > 1.0f) {
            n = 1.0f;
        }
        return this.lastProgress + (this.progress - this.lastProgress) * n;
    }
    
    public boolean shouldPistonHeadBeRendered() {
        return this.shouldHeadBeRendered;
    }
    
    @Override
    public void update() {
        this.lastProgress = this.progress;
        if (this.lastProgress >= 1.0f) {
            this.launchWithSlimeBlock(1.0f, 0.25f);
            this.worldObj.removeTileEntity(this.pos);
            this.invalidate();
            if (this.worldObj.getBlockState(this.pos).getBlock() == Blocks.piston_extension) {
                this.worldObj.setBlockState(this.pos, this.pistonState, 3);
                this.worldObj.notifyBlockOfStateChange(this.pos, this.pistonState.getBlock());
            }
        }
        else {
            this.progress += 0.5f;
            if (this.progress >= 1.0f) {
                this.progress = 1.0f;
            }
            if (this.extending) {
                this.launchWithSlimeBlock(this.progress, this.progress - this.lastProgress + 0.0625f);
            }
        }
    }
    
    public boolean isExtending() {
        return this.extending;
    }
}
