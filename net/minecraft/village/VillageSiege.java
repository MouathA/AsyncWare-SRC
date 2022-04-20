package net.minecraft.village;

import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;

public class VillageSiege
{
    private int field_75539_i;
    private int field_75532_g;
    private int field_75536_c;
    private int field_75538_h;
    private int field_75533_d;
    private boolean field_75535_b;
    private int field_75534_e;
    private Village theVillage;
    private World worldObj;
    
    private Vec3 func_179867_a(final BlockPos blockPos) {
        BlockPos add;
        while (true) {
            add = blockPos.add(this.worldObj.rand.nextInt(16) - 8, this.worldObj.rand.nextInt(6) - 3, this.worldObj.rand.nextInt(16) - 8);
            if (this.theVillage.func_179866_a(add) && SpawnerAnimals.canCreatureTypeSpawnAtLocation(EntityLiving.SpawnPlacementType.ON_GROUND, this.worldObj, add)) {
                break;
            }
            int n = 0;
            ++n;
        }
        return new Vec3(add.getX(), add.getY(), add.getZ());
    }
    
    public void tick() {
        if (this.worldObj.isDaytime()) {
            this.field_75536_c = 0;
        }
        else if (this.field_75536_c != 2) {
            if (this.field_75536_c == 0) {
                final float celestialAngle = this.worldObj.getCelestialAngle(0.0f);
                if (celestialAngle < 0.5 || celestialAngle > 0.501) {
                    return;
                }
                this.field_75536_c = ((this.worldObj.rand.nextInt(10) == 0) ? 1 : 2);
                this.field_75535_b = false;
                if (this.field_75536_c == 2) {
                    return;
                }
            }
            if (this.field_75536_c != -1) {
                if (!this.field_75535_b) {
                    if (this == 0) {
                        return;
                    }
                    this.field_75535_b = true;
                }
                if (this.field_75534_e > 0) {
                    --this.field_75534_e;
                }
                else {
                    this.field_75534_e = 2;
                    if (this.field_75533_d > 0) {
                        this.spawnZombie();
                        --this.field_75533_d;
                    }
                    else {
                        this.field_75536_c = 2;
                    }
                }
            }
        }
    }
    
    public VillageSiege(final World worldObj) {
        this.field_75536_c = -1;
        this.worldObj = worldObj;
    }
    
    private boolean spawnZombie() {
        final Vec3 func_179867_a = this.func_179867_a(new BlockPos(this.field_75532_g, this.field_75538_h, this.field_75539_i));
        if (func_179867_a == null) {
            return false;
        }
        final EntityZombie entityZombie = new EntityZombie(this.worldObj);
        entityZombie.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos(entityZombie)), null);
        entityZombie.setVillager(false);
        entityZombie.setLocationAndAngles(func_179867_a.xCoord, func_179867_a.yCoord, func_179867_a.zCoord, this.worldObj.rand.nextFloat() * 360.0f, 0.0f);
        this.worldObj.spawnEntityInWorld(entityZombie);
        entityZombie.setHomePosAndDistance(this.theVillage.getCenter(), this.theVillage.getVillageRadius());
        return true;
    }
}
