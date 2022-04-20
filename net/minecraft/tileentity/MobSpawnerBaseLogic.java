package net.minecraft.tileentity;

import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.entity.item.*;

public abstract class MobSpawnerBaseLogic
{
    private int spawnRange;
    private double prevMobRotation;
    private int spawnDelay;
    private int activatingRangeFromPlayer;
    private final List minecartToSpawn;
    private double mobRotation;
    private String mobID;
    private int minSpawnDelay;
    private Entity cachedEntity;
    private int spawnCount;
    private WeightedRandomMinecart randomEntity;
    private int maxNearbyEntities;
    private int maxSpawnDelay;
    
    private String getEntityNameToSpawn() {
        if (this.getRandomEntity() == null) {
            if (this.mobID != null && this.mobID.equals("Minecart")) {
                this.mobID = "MinecartRideable";
            }
            return this.mobID;
        }
        return WeightedRandomMinecart.access$000(this.getRandomEntity());
    }
    
    public void setEntityName(final String mobID) {
        this.mobID = mobID;
    }
    
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        this.mobID = nbtTagCompound.getString("EntityId");
        this.spawnDelay = nbtTagCompound.getShort("Delay");
        this.minecartToSpawn.clear();
        if (nbtTagCompound.hasKey("SpawnPotentials", 9)) {
            final NBTTagList tagList = nbtTagCompound.getTagList("SpawnPotentials", 10);
            while (0 < tagList.tagCount()) {
                this.minecartToSpawn.add(new WeightedRandomMinecart(tagList.getCompoundTagAt(0)));
                int n = 0;
                ++n;
            }
        }
        if (nbtTagCompound.hasKey("SpawnData", 10)) {
            this.setRandomEntity(new WeightedRandomMinecart(nbtTagCompound.getCompoundTag("SpawnData"), this.mobID));
        }
        else {
            this.setRandomEntity(null);
        }
        if (nbtTagCompound.hasKey("MinSpawnDelay", 99)) {
            this.minSpawnDelay = nbtTagCompound.getShort("MinSpawnDelay");
            this.maxSpawnDelay = nbtTagCompound.getShort("MaxSpawnDelay");
            this.spawnCount = nbtTagCompound.getShort("SpawnCount");
        }
        if (nbtTagCompound.hasKey("MaxNearbyEntities", 99)) {
            this.maxNearbyEntities = nbtTagCompound.getShort("MaxNearbyEntities");
            this.activatingRangeFromPlayer = nbtTagCompound.getShort("RequiredPlayerRange");
        }
        if (nbtTagCompound.hasKey("SpawnRange", 99)) {
            this.spawnRange = nbtTagCompound.getShort("SpawnRange");
        }
        if (this.getSpawnerWorld() != null) {
            this.cachedEntity = null;
        }
    }
    
    public abstract World getSpawnerWorld();
    
    public boolean setDelayToMin(final int n) {
        if (n == 1 && this.getSpawnerWorld().isRemote) {
            this.spawnDelay = this.minSpawnDelay;
            return true;
        }
        return false;
    }
    
    public abstract BlockPos getSpawnerPosition();
    
    public Entity func_180612_a(final World world) {
        if (this.cachedEntity == null) {
            final Entity entityByName = EntityList.createEntityByName(this.getEntityNameToSpawn(), world);
            if (entityByName != null) {
                this.cachedEntity = this.spawnNewEntity(entityByName, false);
            }
        }
        return this.cachedEntity;
    }
    
    private WeightedRandomMinecart getRandomEntity() {
        return this.randomEntity;
    }
    
    public double getPrevMobRotation() {
        return this.prevMobRotation;
    }
    
    private boolean isActivated() {
        final BlockPos spawnerPosition = this.getSpawnerPosition();
        return this.getSpawnerWorld().isAnyPlayerWithinRangeAt(spawnerPosition.getX() + 0.5, spawnerPosition.getY() + 0.5, spawnerPosition.getZ() + 0.5, this.activatingRangeFromPlayer);
    }
    
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        final String entityNameToSpawn = this.getEntityNameToSpawn();
        if (!StringUtils.isNullOrEmpty(entityNameToSpawn)) {
            nbtTagCompound.setString("EntityId", entityNameToSpawn);
            nbtTagCompound.setShort("Delay", (short)this.spawnDelay);
            nbtTagCompound.setShort("MinSpawnDelay", (short)this.minSpawnDelay);
            nbtTagCompound.setShort("MaxSpawnDelay", (short)this.maxSpawnDelay);
            nbtTagCompound.setShort("SpawnCount", (short)this.spawnCount);
            nbtTagCompound.setShort("MaxNearbyEntities", (short)this.maxNearbyEntities);
            nbtTagCompound.setShort("RequiredPlayerRange", (short)this.activatingRangeFromPlayer);
            nbtTagCompound.setShort("SpawnRange", (short)this.spawnRange);
            if (this.getRandomEntity() != null) {
                nbtTagCompound.setTag("SpawnData", WeightedRandomMinecart.access$100(this.getRandomEntity()).copy());
            }
            if (this.getRandomEntity() != null || this.minecartToSpawn.size() > 0) {
                final NBTTagList list = new NBTTagList();
                if (this.minecartToSpawn.size() > 0) {
                    final Iterator<WeightedRandomMinecart> iterator = this.minecartToSpawn.iterator();
                    while (iterator.hasNext()) {
                        list.appendTag(iterator.next().toNBT());
                    }
                }
                else {
                    list.appendTag(this.getRandomEntity().toNBT());
                }
                nbtTagCompound.setTag("SpawnPotentials", list);
            }
        }
    }
    
    public abstract void func_98267_a(final int p0);
    
    private Entity spawnNewEntity(final Entity entity, final boolean b) {
        if (this.getRandomEntity() != null) {
            NBTTagCompound nbtTagCompound = new NBTTagCompound();
            entity.writeToNBTOptional(nbtTagCompound);
            for (final String s : WeightedRandomMinecart.access$100(this.getRandomEntity()).getKeySet()) {
                nbtTagCompound.setTag(s, WeightedRandomMinecart.access$100(this.getRandomEntity()).getTag(s).copy());
            }
            entity.readFromNBT(nbtTagCompound);
            if (entity.worldObj != null && b) {
                entity.worldObj.spawnEntityInWorld(entity);
            }
            Entity entity2 = entity;
            while (nbtTagCompound.hasKey("Riding", 10)) {
                final NBTTagCompound compoundTag = nbtTagCompound.getCompoundTag("Riding");
                final Entity entityByName = EntityList.createEntityByName(compoundTag.getString("id"), entity.worldObj);
                if (entityByName != null) {
                    final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                    entityByName.writeToNBTOptional(nbtTagCompound2);
                    for (final String s2 : compoundTag.getKeySet()) {
                        nbtTagCompound2.setTag(s2, compoundTag.getTag(s2).copy());
                    }
                    entityByName.readFromNBT(nbtTagCompound2);
                    entityByName.setLocationAndAngles(entity2.posX, entity2.posY, entity2.posZ, entity2.rotationYaw, entity2.rotationPitch);
                    if (entity.worldObj != null && b) {
                        entity.worldObj.spawnEntityInWorld(entityByName);
                    }
                    entity2.mountEntity(entityByName);
                }
                entity2 = entityByName;
                nbtTagCompound = compoundTag;
            }
        }
        else if (entity instanceof EntityLivingBase && entity.worldObj != null && b) {
            if (entity instanceof EntityLiving) {
                ((EntityLiving)entity).onInitialSpawn(entity.worldObj.getDifficultyForLocation(new BlockPos(entity)), null);
            }
            entity.worldObj.spawnEntityInWorld(entity);
        }
        return entity;
    }
    
    public double getMobRotation() {
        return this.mobRotation;
    }
    
    public void updateSpawner() {
        if (this.isActivated()) {
            final BlockPos spawnerPosition = this.getSpawnerPosition();
            if (this.getSpawnerWorld().isRemote) {
                final double n = spawnerPosition.getX() + this.getSpawnerWorld().rand.nextFloat();
                final double n2 = spawnerPosition.getY() + this.getSpawnerWorld().rand.nextFloat();
                final double n3 = spawnerPosition.getZ() + this.getSpawnerWorld().rand.nextFloat();
                this.getSpawnerWorld().spawnParticle(EnumParticleTypes.SMOKE_NORMAL, n, n2, n3, 0.0, 0.0, 0.0, new int[0]);
                this.getSpawnerWorld().spawnParticle(EnumParticleTypes.FLAME, n, n2, n3, 0.0, 0.0, 0.0, new int[0]);
                if (this.spawnDelay > 0) {
                    --this.spawnDelay;
                }
                this.prevMobRotation = this.mobRotation;
                this.mobRotation = (this.mobRotation + 1000.0f / (this.spawnDelay + 200.0f)) % 360.0;
            }
            else {
                if (this.spawnDelay == -1) {
                    this.resetTimer();
                }
                if (this.spawnDelay > 0) {
                    --this.spawnDelay;
                    return;
                }
                while (0 < this.spawnCount) {
                    final Entity entityByName = EntityList.createEntityByName(this.getEntityNameToSpawn(), this.getSpawnerWorld());
                    if (entityByName == null) {
                        return;
                    }
                    if (this.getSpawnerWorld().getEntitiesWithinAABB(((EntityLiving)entityByName).getClass(), new AxisAlignedBB(spawnerPosition.getX(), spawnerPosition.getY(), spawnerPosition.getZ(), spawnerPosition.getX() + 1, spawnerPosition.getY() + 1, spawnerPosition.getZ() + 1).expand(this.spawnRange, this.spawnRange, this.spawnRange)).size() >= this.maxNearbyEntities) {
                        this.resetTimer();
                        return;
                    }
                    final double n4 = spawnerPosition.getX() + (this.getSpawnerWorld().rand.nextDouble() - this.getSpawnerWorld().rand.nextDouble()) * this.spawnRange + 0.5;
                    final double n5 = spawnerPosition.getY() + this.getSpawnerWorld().rand.nextInt(3) - 1;
                    final double n6 = spawnerPosition.getZ() + (this.getSpawnerWorld().rand.nextDouble() - this.getSpawnerWorld().rand.nextDouble()) * this.spawnRange + 0.5;
                    final EntityLiving entityLiving = (entityByName instanceof EntityLiving) ? ((EntityLiving)entityByName) : null;
                    entityByName.setLocationAndAngles(n4, n5, n6, this.getSpawnerWorld().rand.nextFloat() * 360.0f, 0.0f);
                    if (entityLiving == null || (entityLiving.getCanSpawnHere() && entityLiving.isNotColliding())) {
                        this.spawnNewEntity(entityByName, true);
                        this.getSpawnerWorld().playAuxSFX(2004, spawnerPosition, 0);
                        if (entityLiving != null) {
                            entityLiving.spawnExplosionParticle();
                        }
                    }
                    int n7 = 0;
                    ++n7;
                }
                this.resetTimer();
            }
        }
    }
    
    public void setRandomEntity(final WeightedRandomMinecart randomEntity) {
        this.randomEntity = randomEntity;
    }
    
    public MobSpawnerBaseLogic() {
        this.spawnDelay = 20;
        this.mobID = "Pig";
        this.minecartToSpawn = Lists.newArrayList();
        this.minSpawnDelay = 200;
        this.maxSpawnDelay = 800;
        this.spawnCount = 4;
        this.maxNearbyEntities = 6;
        this.activatingRangeFromPlayer = 16;
        this.spawnRange = 4;
    }
    
    private void resetTimer() {
        if (this.maxSpawnDelay <= this.minSpawnDelay) {
            this.spawnDelay = this.minSpawnDelay;
        }
        else {
            this.spawnDelay = this.minSpawnDelay + this.getSpawnerWorld().rand.nextInt(this.maxSpawnDelay - this.minSpawnDelay);
        }
        if (this.minecartToSpawn.size() > 0) {
            this.setRandomEntity((WeightedRandomMinecart)WeightedRandom.getRandomItem(this.getSpawnerWorld().rand, this.minecartToSpawn));
        }
        this.func_98267_a(1);
    }
    
    public class WeightedRandomMinecart extends WeightedRandom.Item
    {
        private final String entityType;
        private final NBTTagCompound nbtData;
        final MobSpawnerBaseLogic this$0;
        
        static String access$000(final WeightedRandomMinecart weightedRandomMinecart) {
            return weightedRandomMinecart.entityType;
        }
        
        public WeightedRandomMinecart(final MobSpawnerBaseLogic mobSpawnerBaseLogic, final NBTTagCompound nbtTagCompound) {
            this(mobSpawnerBaseLogic, nbtTagCompound.getCompoundTag("Properties"), nbtTagCompound.getString("Type"), nbtTagCompound.getInteger("Weight"));
        }
        
        public NBTTagCompound toNBT() {
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            nbtTagCompound.setTag("Properties", this.nbtData);
            nbtTagCompound.setString("Type", this.entityType);
            nbtTagCompound.setInteger("Weight", this.itemWeight);
            return nbtTagCompound;
        }
        
        public WeightedRandomMinecart(final MobSpawnerBaseLogic mobSpawnerBaseLogic, final NBTTagCompound nbtTagCompound, final String s) {
            this(mobSpawnerBaseLogic, nbtTagCompound, s, 1);
        }
        
        static NBTTagCompound access$100(final WeightedRandomMinecart weightedRandomMinecart) {
            return weightedRandomMinecart.nbtData;
        }
        
        private WeightedRandomMinecart(final MobSpawnerBaseLogic this$0, final NBTTagCompound nbtData, String name, final int n) {
            this.this$0 = this$0;
            super(n);
            if (name.equals("Minecart")) {
                if (nbtData != null) {
                    name = EntityMinecart.EnumMinecartType.byNetworkID(nbtData.getInteger("Type")).getName();
                }
                else {
                    name = "MinecartRideable";
                }
            }
            this.nbtData = nbtData;
            this.entityType = name;
        }
    }
}
