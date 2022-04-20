package net.minecraft.tileentity;

import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;

public class TileEntityNote extends TileEntity
{
    public byte note;
    public boolean previousRedstoneState;
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setByte("note", this.note);
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        this.note = nbtTagCompound.getByte("note");
        this.note = (byte)MathHelper.clamp_int(this.note, 0, 24);
    }
    
    public void triggerNote(final World world, final BlockPos blockPos) {
        if (world.getBlockState(blockPos.up()).getBlock().getMaterial() == Material.air) {
            final Material material = world.getBlockState(blockPos.down()).getBlock().getMaterial();
            if (material == Material.rock) {}
            if (material == Material.sand) {}
            if (material == Material.glass) {}
            if (material == Material.wood) {}
            world.addBlockEvent(blockPos, Blocks.noteblock, 4, this.note);
        }
    }
    
    public void changePitch() {
        this.note = (byte)((this.note + 1) % 25);
        this.markDirty();
    }
}
