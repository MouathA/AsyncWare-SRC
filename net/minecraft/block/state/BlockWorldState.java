package net.minecraft.block.state;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;
import com.google.common.base.*;

public class BlockWorldState
{
    private boolean tileEntityInitialized;
    private final World world;
    private IBlockState state;
    private final BlockPos pos;
    private TileEntity tileEntity;
    private final boolean field_181628_c;
    
    public TileEntity getTileEntity() {
        if (this.tileEntity == null && !this.tileEntityInitialized) {
            this.tileEntity = this.world.getTileEntity(this.pos);
            this.tileEntityInitialized = true;
        }
        return this.tileEntity;
    }
    
    public BlockWorldState(final World world, final BlockPos pos, final boolean field_181628_c) {
        this.world = world;
        this.pos = pos;
        this.field_181628_c = field_181628_c;
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
    
    public IBlockState getBlockState() {
        if (this.state == null && (this.field_181628_c || this.world.isBlockLoaded(this.pos))) {
            this.state = this.world.getBlockState(this.pos);
        }
        return this.state;
    }
    
    public static Predicate hasState(final Predicate predicate) {
        return (Predicate)new Predicate(predicate) {
            final Predicate val$p_177510_0_;
            
            public boolean apply(final BlockWorldState blockWorldState) {
                return blockWorldState != null && this.val$p_177510_0_.apply((Object)blockWorldState.getBlockState());
            }
            
            public boolean apply(final Object o) {
                return this.apply((BlockWorldState)o);
            }
        };
    }
}
