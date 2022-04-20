package net.minecraft.world;

import net.minecraft.block.*;
import net.minecraft.util.*;

public class NextTickListEntry implements Comparable
{
    public int priority;
    private final Block block;
    public long scheduledTime;
    private long tickEntryID;
    public final BlockPos position;
    private static long nextTickEntryID;
    
    @Override
    public String toString() {
        return Block.getIdFromBlock(this.block) + ": " + this.position + ", " + this.scheduledTime + ", " + this.priority + ", " + this.tickEntryID;
    }
    
    public NextTickListEntry setScheduledTime(final long scheduledTime) {
        this.scheduledTime = scheduledTime;
        return this;
    }
    
    public NextTickListEntry(final BlockPos position, final Block block) {
        this.tickEntryID = NextTickListEntry.nextTickEntryID++;
        this.position = position;
        this.block = block;
    }
    
    public int compareTo(final NextTickListEntry nextTickListEntry) {
        return (this.scheduledTime < nextTickListEntry.scheduledTime) ? -1 : ((this.scheduledTime > nextTickListEntry.scheduledTime) ? 1 : ((this.priority != nextTickListEntry.priority) ? (this.priority - nextTickListEntry.priority) : ((this.tickEntryID < nextTickListEntry.tickEntryID) ? -1 : ((this.tickEntryID > nextTickListEntry.tickEntryID) ? 1 : 0))));
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof NextTickListEntry)) {
            return false;
        }
        final NextTickListEntry nextTickListEntry = (NextTickListEntry)o;
        return this.position.equals(nextTickListEntry.position) && Block.isEqualTo(this.block, nextTickListEntry.block);
    }
    
    public Block getBlock() {
        return this.block;
    }
    
    @Override
    public int hashCode() {
        return this.position.hashCode();
    }
    
    public void setPriority(final int priority) {
        this.priority = priority;
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((NextTickListEntry)o);
    }
}
