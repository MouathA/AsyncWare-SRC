package net.minecraft.entity.ai;

import net.minecraft.entity.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;

public class EntityMinecartMobSpawner extends EntityMinecart
{
    private final MobSpawnerBaseLogic mobSpawnerLogic;
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        this.mobSpawnerLogic.updateSpawner();
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.mobSpawnerLogic.readFromNBT(nbtTagCompound);
    }
    
    public EntityMinecartMobSpawner(final World world) {
        super(world);
        this.mobSpawnerLogic = new MobSpawnerBaseLogic() {
            final EntityMinecartMobSpawner this$0;
            
            @Override
            public void func_98267_a(final int n) {
                this.this$0.worldObj.setEntityState(this.this$0, (byte)n);
            }
            
            @Override
            public World getSpawnerWorld() {
                return this.this$0.worldObj;
            }
            
            @Override
            public BlockPos getSpawnerPosition() {
                return new BlockPos(this.this$0);
            }
        };
    }
    
    @Override
    public IBlockState getDefaultDisplayTile() {
        return Blocks.mob_spawner.getDefaultState();
    }
    
    @Override
    public void handleStatusUpdate(final byte delayToMin) {
        this.mobSpawnerLogic.setDelayToMin(delayToMin);
    }
    
    public MobSpawnerBaseLogic func_98039_d() {
        return this.mobSpawnerLogic;
    }
    
    public EntityMinecartMobSpawner(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3);
        this.mobSpawnerLogic = new MobSpawnerBaseLogic() {
            final EntityMinecartMobSpawner this$0;
            
            @Override
            public void func_98267_a(final int n) {
                this.this$0.worldObj.setEntityState(this.this$0, (byte)n);
            }
            
            @Override
            public World getSpawnerWorld() {
                return this.this$0.worldObj;
            }
            
            @Override
            public BlockPos getSpawnerPosition() {
                return new BlockPos(this.this$0);
            }
        };
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        this.mobSpawnerLogic.writeToNBT(nbtTagCompound);
    }
    
    @Override
    public EnumMinecartType getMinecartType() {
        return EnumMinecartType.SPAWNER;
    }
}
