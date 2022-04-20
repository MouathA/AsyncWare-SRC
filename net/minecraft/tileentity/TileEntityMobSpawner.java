package net.minecraft.tileentity;

import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;

public class TileEntityMobSpawner extends TileEntity implements ITickable
{
    private final MobSpawnerBaseLogic spawnerLogic;
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        this.spawnerLogic.writeToNBT(nbtTagCompound);
    }
    
    @Override
    public Packet getDescriptionPacket() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.writeToNBT(nbtTagCompound);
        nbtTagCompound.removeTag("SpawnPotentials");
        return new S35PacketUpdateTileEntity(this.pos, 1, nbtTagCompound);
    }
    
    public TileEntityMobSpawner() {
        this.spawnerLogic = new MobSpawnerBaseLogic() {
            final TileEntityMobSpawner this$0;
            
            @Override
            public World getSpawnerWorld() {
                return this.this$0.worldObj;
            }
            
            @Override
            public BlockPos getSpawnerPosition() {
                return this.this$0.pos;
            }
            
            @Override
            public void setRandomEntity(final WeightedRandomMinecart randomEntity) {
                super.setRandomEntity(randomEntity);
                if (this.getSpawnerWorld() != null) {
                    this.getSpawnerWorld().markBlockForUpdate(this.this$0.pos);
                }
            }
            
            @Override
            public void func_98267_a(final int n) {
                this.this$0.worldObj.addBlockEvent(this.this$0.pos, Blocks.mob_spawner, n, 0);
            }
        };
    }
    
    @Override
    public void update() {
        this.spawnerLogic.updateSpawner();
    }
    
    @Override
    public boolean receiveClientEvent(final int delayToMin, final int n) {
        return this.spawnerLogic.setDelayToMin(delayToMin) || super.receiveClientEvent(delayToMin, n);
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        this.spawnerLogic.readFromNBT(nbtTagCompound);
    }
    
    @Override
    public boolean func_183000_F() {
        return true;
    }
    
    public MobSpawnerBaseLogic getSpawnerBaseLogic() {
        return this.spawnerLogic;
    }
}
