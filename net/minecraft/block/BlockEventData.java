package net.minecraft.block;

import net.minecraft.util.*;

public class BlockEventData
{
    private int eventParameter;
    private int eventID;
    private BlockPos position;
    private Block blockType;
    
    public int getEventParameter() {
        return this.eventParameter;
    }
    
    public BlockPos getPosition() {
        return this.position;
    }
    
    public Block getBlock() {
        return this.blockType;
    }
    
    public BlockEventData(final BlockPos position, final Block blockType, final int eventID, final int eventParameter) {
        this.position = position;
        this.eventID = eventID;
        this.eventParameter = eventParameter;
        this.blockType = blockType;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof BlockEventData)) {
            return false;
        }
        final BlockEventData blockEventData = (BlockEventData)o;
        return this.position.equals(blockEventData.position) && this.eventID == blockEventData.eventID && this.eventParameter == blockEventData.eventParameter && this.blockType == blockEventData.blockType;
    }
    
    public int getEventID() {
        return this.eventID;
    }
    
    @Override
    public String toString() {
        return "TE(" + this.position + ")," + this.eventID + "," + this.eventParameter + "," + this.blockType;
    }
}
