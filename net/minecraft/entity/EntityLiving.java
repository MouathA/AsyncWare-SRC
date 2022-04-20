package net.minecraft.entity;

import net.minecraft.world.biome.*;
import net.minecraft.entity.ai.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.passive.*;
import net.minecraft.nbt.*;
import net.minecraft.init.*;
import net.minecraft.entity.ai.attributes.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.item.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.monster.*;
import optfine.*;
import net.minecraft.world.*;

public abstract class EntityLiving extends EntityLivingBase
{
    private EntityLookHelper lookHelper;
    private EntitySenses senses;
    protected int experienceValue;
    private Entity leashedToEntity;
    protected float[] equipmentDropChances;
    private boolean canPickUpLoot;
    protected EntityJumpHelper jumpHelper;
    protected final EntityAITasks targetTasks;
    private boolean persistenceRequired;
    protected PathNavigate navigator;
    public BiomeGenBase spawnBiome;
    public BlockPos spawnPosition;
    private EntityLivingBase attackTarget;
    private ItemStack[] equipment;
    protected final EntityAITasks tasks;
    private EntityBodyHelper bodyHelper;
    private static final String __OBFID = "CL_00001550";
    public int randomMobsId;
    private boolean isLeashed;
    public int livingSoundTime;
    private NBTTagCompound leashNBTTag;
    protected EntityMoveHelper moveHelper;
    
    public boolean canPickUpLoot() {
        return this.canPickUpLoot;
    }
    
    protected PathNavigate getNewNavigator(final World world) {
        return new PathNavigateGround(this, world);
    }
    
    public float getRenderSizeModifier() {
        return 1.0f;
    }
    
    public void eatGrassBonus() {
    }
    
    protected void updateAITasks() {
    }
    
    @Override
    public ItemStack[] getInventory() {
        return this.equipment;
    }
    
    public int getTalkInterval() {
        return 80;
    }
    
    @Override
    public ItemStack getCurrentArmor(final int n) {
        return this.equipment[n + 1];
    }
    
    public boolean getLeashed() {
        return this.isLeashed;
    }
    
    public void setMoveForward(final float moveForward) {
        this.moveForward = moveForward;
    }
    
    @Override
    public final boolean interactFirst(final EntityPlayer entityPlayer) {
        if (this.getLeashed() && this.getLeashedToEntity() == entityPlayer) {
            this.clearLeashed(true, !entityPlayer.capabilities.isCreativeMode);
            return true;
        }
        final ItemStack currentItem = entityPlayer.inventory.getCurrentItem();
        if (currentItem != null && currentItem.getItem() == Items.lead && this == 0) {
            if (!(this instanceof EntityTameable) || !((EntityTameable)this).isTamed()) {
                this.setLeashedToEntity(entityPlayer, true);
                final ItemStack itemStack = currentItem;
                --itemStack.stackSize;
                return true;
            }
            if (((EntityTameable)this).isOwner(entityPlayer)) {
                this.setLeashedToEntity(entityPlayer, true);
                final ItemStack itemStack2 = currentItem;
                --itemStack2.stackSize;
                return true;
            }
        }
        return this.interact(entityPlayer) || super.interactFirst(entityPlayer);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        if (nbtTagCompound.hasKey("CanPickUpLoot", 1)) {
            this.setCanPickUpLoot(nbtTagCompound.getBoolean("CanPickUpLoot"));
        }
        this.persistenceRequired = nbtTagCompound.getBoolean("PersistenceRequired");
        int n = 0;
        if (nbtTagCompound.hasKey("Equipment", 9)) {
            final NBTTagList tagList = nbtTagCompound.getTagList("Equipment", 10);
            while (0 < this.equipment.length) {
                this.equipment[0] = ItemStack.loadItemStackFromNBT(tagList.getCompoundTagAt(0));
                ++n;
            }
        }
        if (nbtTagCompound.hasKey("DropChances", 9)) {
            final NBTTagList tagList2 = nbtTagCompound.getTagList("DropChances", 5);
            while (0 < tagList2.tagCount()) {
                this.equipmentDropChances[0] = tagList2.getFloatAt(0);
                ++n;
            }
        }
        this.isLeashed = nbtTagCompound.getBoolean("Leashed");
        if (this.isLeashed && nbtTagCompound.hasKey("Leash", 10)) {
            this.leashNBTTag = nbtTagCompound.getCompoundTag("Leash");
        }
        this.setNoAI(nbtTagCompound.getBoolean("NoAI"));
    }
    
    public boolean canAttackClass(final Class clazz) {
        return clazz != EntityGhast.class;
    }
    
    public static int getArmorPosition(final ItemStack itemStack) {
        if (itemStack.getItem() != Item.getItemFromBlock(Blocks.pumpkin) && itemStack.getItem() != Items.skull) {
            if (itemStack.getItem() instanceof ItemArmor) {
                switch (((ItemArmor)itemStack.getItem()).armorType) {
                    case 0: {
                        return 4;
                    }
                    case 1: {
                        return 3;
                    }
                    case 2: {
                        return 2;
                    }
                    case 3: {
                        return 1;
                    }
                }
            }
            return 0;
        }
        return 4;
    }
    
    public void setCanPickUpLoot(final boolean canPickUpLoot) {
        this.canPickUpLoot = canPickUpLoot;
    }
    
    public IEntityLivingData onInitialSpawn(final DifficultyInstance difficultyInstance, final IEntityLivingData entityLivingData) {
        this.getEntityAttribute(SharedMonsterAttributes.followRange).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextGaussian() * 0.05, 1));
        return entityLivingData;
    }
    
    public void spawnExplosionParticle() {
        if (!this.worldObj.isRemote) {
            this.worldObj.setEntityState(this, (byte)20);
            return;
        }
        while (true) {
            final double n = this.rand.nextGaussian() * 0.02;
            final double n2 = this.rand.nextGaussian() * 0.02;
            final double n3 = this.rand.nextGaussian() * 0.02;
            final double n4 = 10.0;
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width - n * n4, this.posY + this.rand.nextFloat() * this.height - n2 * n4, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width - n3 * n4, n, n2, n3, new int[0]);
            int n5 = 0;
            ++n5;
        }
    }
    
    @Override
    protected void dropEquipment(final boolean b, final int n) {
        while (0 < this.getInventory().length) {
            final ItemStack equipmentInSlot = this.getEquipmentInSlot(0);
            final boolean b2 = this.equipmentDropChances[0] > 1.0f;
            if (equipmentInSlot != null && (b || b2) && this.rand.nextFloat() - n * 0.01f < this.equipmentDropChances[0]) {
                if (!b2 && equipmentInSlot.isItemStackDamageable()) {
                    final int max = Math.max(equipmentInSlot.getMaxDamage() - 25, 1);
                    final int n2 = equipmentInSlot.getMaxDamage() - this.rand.nextInt(this.rand.nextInt(max) + 1);
                    if (1 > max) {}
                    equipmentInSlot.setItemDamage(1);
                }
                this.entityDropItem(equipmentInSlot, 0.0f);
            }
            int n3 = 0;
            ++n3;
        }
    }
    
    private void recreateLeash() {
        if (this.isLeashed && this.leashNBTTag != null) {
            if (this.leashNBTTag.hasKey("UUIDMost", 4) && this.leashNBTTag.hasKey("UUIDLeast", 4)) {
                final UUID uuid = new UUID(this.leashNBTTag.getLong("UUIDMost"), this.leashNBTTag.getLong("UUIDLeast"));
                for (final EntityLivingBase leashedToEntity : this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().expand(10.0, 10.0, 10.0))) {
                    if (leashedToEntity.getUniqueID().equals(uuid)) {
                        this.leashedToEntity = leashedToEntity;
                        break;
                    }
                }
            }
            else if (this.leashNBTTag.hasKey("X", 99) && this.leashNBTTag.hasKey("Y", 99) && this.leashNBTTag.hasKey("Z", 99)) {
                final BlockPos blockPos = new BlockPos(this.leashNBTTag.getInteger("X"), this.leashNBTTag.getInteger("Y"), this.leashNBTTag.getInteger("Z"));
                EntityLeashKnot leashedToEntity2 = EntityLeashKnot.getKnotForPosition(this.worldObj, blockPos);
                if (leashedToEntity2 == null) {
                    leashedToEntity2 = EntityLeashKnot.createKnot(this.worldObj, blockPos);
                }
                this.leashedToEntity = leashedToEntity2;
            }
            else {
                this.clearLeashed(false, true);
            }
        }
        this.leashNBTTag = null;
    }
    
    public static Item getArmorItemForSlot(final int n, final int n2) {
        switch (n) {
            case 4: {
                if (n2 == 0) {
                    return Items.leather_helmet;
                }
                if (n2 == 1) {
                    return Items.golden_helmet;
                }
                if (n2 == 2) {
                    return Items.chainmail_helmet;
                }
                if (n2 == 3) {
                    return Items.iron_helmet;
                }
                if (n2 == 4) {
                    return Items.diamond_helmet;
                }
            }
            case 3: {
                if (n2 == 0) {
                    return Items.leather_chestplate;
                }
                if (n2 == 1) {
                    return Items.golden_chestplate;
                }
                if (n2 == 2) {
                    return Items.chainmail_chestplate;
                }
                if (n2 == 3) {
                    return Items.iron_chestplate;
                }
                if (n2 == 4) {
                    return Items.diamond_chestplate;
                }
            }
            case 2: {
                if (n2 == 0) {
                    return Items.leather_leggings;
                }
                if (n2 == 1) {
                    return Items.golden_leggings;
                }
                if (n2 == 2) {
                    return Items.chainmail_leggings;
                }
                if (n2 == 3) {
                    return Items.iron_leggings;
                }
                if (n2 == 4) {
                    return Items.diamond_leggings;
                }
            }
            case 1: {
                if (n2 == 0) {
                    return Items.leather_boots;
                }
                if (n2 == 1) {
                    return Items.golden_boots;
                }
                if (n2 == 2) {
                    return Items.chainmail_boots;
                }
                if (n2 == 3) {
                    return Items.iron_boots;
                }
                if (n2 == 4) {
                    return Items.diamond_boots;
                }
                break;
            }
        }
        return null;
    }
    
    @Override
    protected final void updateEntityActionState() {
        ++this.entityAge;
        this.worldObj.theProfiler.startSection("checkDespawn");
        this.despawnEntity();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("sensing");
        this.senses.clearSensingCache();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("targetSelector");
        this.targetTasks.onUpdateTasks();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("goalSelector");
        this.tasks.onUpdateTasks();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("navigation");
        this.navigator.onUpdateNavigation();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("mob tick");
        this.updateAITasks();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("controls");
        this.worldObj.theProfiler.startSection("move");
        this.moveHelper.onUpdateMoveHelper();
        this.worldObj.theProfiler.endStartSection("look");
        this.lookHelper.onUpdateLook();
        this.worldObj.theProfiler.endStartSection("jump");
        this.jumpHelper.doJump();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.endSection();
    }
    
    protected boolean func_175448_a(final ItemStack itemStack) {
        return true;
    }
    
    public EntityJumpHelper getJumpHelper() {
        return this.jumpHelper;
    }
    
    public EntityLiving(final World world) {
        super(world);
        this.equipment = new ItemStack[5];
        this.equipmentDropChances = new float[5];
        this.randomMobsId = 0;
        this.spawnBiome = null;
        this.spawnPosition = null;
        this.tasks = new EntityAITasks((world != null && world.theProfiler != null) ? world.theProfiler : null);
        this.targetTasks = new EntityAITasks((world != null && world.theProfiler != null) ? world.theProfiler : null);
        this.lookHelper = new EntityLookHelper(this);
        this.moveHelper = new EntityMoveHelper(this);
        this.jumpHelper = new EntityJumpHelper(this);
        this.bodyHelper = new EntityBodyHelper(this);
        this.navigator = this.getNewNavigator(world);
        this.senses = new EntitySenses(this);
        while (0 < this.equipmentDropChances.length) {
            this.equipmentDropChances[0] = 0.085f;
            int n = 0;
            ++n;
        }
        this.randomMobsId = (int)(this.getUniqueID().getLeastSignificantBits() & 0x7FFFFFFFL);
    }
    
    @Override
    public void onUpdate() {
        if (Config.isSmoothWorld() && this != 0) {
            this.onUpdateMinimal();
        }
        else {
            super.onUpdate();
            if (!this.worldObj.isRemote) {
                this.updateLeashedState();
            }
        }
    }
    
    public void faceEntity(final Entity entity, final float n, final float n2) {
        final double n3 = entity.posX - this.posX;
        final double n4 = entity.posZ - this.posZ;
        double n5;
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
            n5 = entityLivingBase.posY + entityLivingBase.getEyeHeight() - (this.posY + this.getEyeHeight());
        }
        else {
            n5 = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0 - (this.posY + this.getEyeHeight());
        }
        final double n6 = MathHelper.sqrt_double(n3 * n3 + n4 * n4);
        final float n7 = (float)(MathHelper.func_181159_b(n4, n3) * 180.0 / 3.141592653589793) - 90.0f;
        this.rotationPitch = this.updateRotation(this.rotationPitch, (float)(-(MathHelper.func_181159_b(n5, n6) * 180.0 / 3.141592653589793)), n2);
        this.rotationYaw = this.updateRotation(this.rotationYaw, n7, n);
    }
    
    protected void updateEquipmentIfNeeded(final EntityItem entityItem) {
        final ItemStack entityItem2 = entityItem.getEntityItem();
        final int armorPosition = getArmorPosition(entityItem2);
        if (armorPosition > -1) {
            final ItemStack equipmentInSlot = this.getEquipmentInSlot(armorPosition);
            if (equipmentInSlot != null) {
                if (armorPosition == 0) {
                    if (!(entityItem2.getItem() instanceof ItemSword) || equipmentInSlot.getItem() instanceof ItemSword) {
                        if (entityItem2.getItem() instanceof ItemSword && equipmentInSlot.getItem() instanceof ItemSword) {
                            final ItemSword itemSword = (ItemSword)entityItem2.getItem();
                            final ItemSword itemSword2 = (ItemSword)equipmentInSlot.getItem();
                            if (itemSword.getDamageVsEntity() != itemSword2.getDamageVsEntity()) {
                                final boolean b = itemSword.getDamageVsEntity() > itemSword2.getDamageVsEntity();
                            }
                            else {
                                final boolean b2 = entityItem2.getMetadata() > equipmentInSlot.getMetadata() || (entityItem2.hasTagCompound() && !equipmentInSlot.hasTagCompound());
                            }
                        }
                        else if (entityItem2.getItem() instanceof ItemBow && equipmentInSlot.getItem() instanceof ItemBow) {
                            final boolean b3 = entityItem2.hasTagCompound() && !equipmentInSlot.hasTagCompound();
                        }
                    }
                }
                else if (!(entityItem2.getItem() instanceof ItemArmor) || equipmentInSlot.getItem() instanceof ItemArmor) {
                    if (entityItem2.getItem() instanceof ItemArmor && equipmentInSlot.getItem() instanceof ItemArmor) {
                        final ItemArmor itemArmor = (ItemArmor)entityItem2.getItem();
                        final ItemArmor itemArmor2 = (ItemArmor)equipmentInSlot.getItem();
                        if (itemArmor.damageReduceAmount != itemArmor2.damageReduceAmount) {
                            final boolean b4 = itemArmor.damageReduceAmount > itemArmor2.damageReduceAmount;
                        }
                        else {
                            final boolean b5 = entityItem2.getMetadata() > equipmentInSlot.getMetadata() || (entityItem2.hasTagCompound() && !equipmentInSlot.hasTagCompound());
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.worldObj.theProfiler.startSection("looting");
        if (!this.worldObj.isRemote && this.canPickUpLoot() && !this.dead && this.worldObj.getGameRules().getBoolean("mobGriefing")) {
            for (final EntityItem entityItem : this.worldObj.getEntitiesWithinAABB(EntityItem.class, this.getEntityBoundingBox().expand(1.0, 0.0, 1.0))) {
                if (!entityItem.isDead && entityItem.getEntityItem() != null && !entityItem.cannotPickup()) {
                    this.updateEquipmentIfNeeded(entityItem);
                }
            }
        }
        this.worldObj.theProfiler.endSection();
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0);
    }
    
    public boolean isNoDespawnRequired() {
        return this.persistenceRequired;
    }
    
    protected boolean canDespawn() {
        return true;
    }
    
    public void setLeashedToEntity(final Entity leashedToEntity, final boolean b) {
        this.isLeashed = true;
        this.leashedToEntity = leashedToEntity;
        if (!this.worldObj.isRemote && b && this.worldObj instanceof WorldServer) {
            ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, new S1BPacketEntityAttach(1, this, this.leashedToEntity));
        }
    }
    
    public void enablePersistence() {
        this.persistenceRequired = true;
    }
    
    protected void updateLeashedState() {
        if (this.leashNBTTag != null) {
            this.recreateLeash();
        }
        if (this.isLeashed) {
            if (!this.isEntityAlive()) {
                this.clearLeashed(true, true);
            }
            if (this.leashedToEntity == null || this.leashedToEntity.isDead) {
                this.clearLeashed(true, true);
            }
        }
    }
    
    public int getVerticalFaceSpeed() {
        return 40;
    }
    
    public boolean canBeSteered() {
        return false;
    }
    
    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        this.worldObj.theProfiler.startSection("mobBaseTick");
        if (this.isEntityAlive() && this.rand.nextInt(1000) < this.livingSoundTime++) {
            this.livingSoundTime = -this.getTalkInterval();
            this.playLivingSound();
        }
        this.worldObj.theProfiler.endSection();
    }
    
    public int getMaxSpawnedInChunk() {
        return 4;
    }
    
    @Override
    public ItemStack getHeldItem() {
        return this.equipment[0];
    }
    
    @Override
    public void setAIMoveSpeed(final float n) {
        super.setAIMoveSpeed(n);
        this.setMoveForward(n);
    }
    
    public EntitySenses getEntitySenses() {
        return this.senses;
    }
    
    public Entity getLeashedToEntity() {
        return this.leashedToEntity;
    }
    
    public void setNoAI(final boolean b) {
        this.dataWatcher.updateObject(15, (byte)(byte)(b ? 1 : 0));
    }
    
    public boolean getCanSpawnHere() {
        return true;
    }
    
    @Override
    protected float func_110146_f(final float n, final float n2) {
        this.bodyHelper.updateRenderAngles();
        return n2;
    }
    
    @Override
    public ItemStack getEquipmentInSlot(final int n) {
        return this.equipment[n];
    }
    
    public EntityLookHelper getLookHelper() {
        return this.lookHelper;
    }
    
    public void setEquipmentDropChance(final int n, final float n2) {
        this.equipmentDropChances[n] = n2;
    }
    
    protected boolean interact(final EntityPlayer entityPlayer) {
        return false;
    }
    
    public void playLivingSound() {
        final String livingSound = this.getLivingSound();
        if (livingSound != null) {
            this.playSound(livingSound, this.getSoundVolume(), this.getSoundPitch());
        }
    }
    
    public EntityLivingBase getAttackTarget() {
        return this.attackTarget;
    }
    
    protected void despawnEntity() {
        final Object fieldValue = Reflector.getFieldValue(Reflector.Event_Result_DEFAULT);
        final Object fieldValue2 = Reflector.getFieldValue(Reflector.Event_Result_DENY);
        if (this.persistenceRequired) {
            this.entityAge = 0;
        }
        else {
            final Object call;
            if ((this.entityAge & 0x1F) == 0x1F && (call = Reflector.call(Reflector.ForgeEventFactory_canEntityDespawn, this)) != fieldValue) {
                if (call == fieldValue2) {
                    this.entityAge = 0;
                }
                else {
                    this.setDead();
                }
            }
            else {
                final EntityPlayer closestPlayerToEntity = this.worldObj.getClosestPlayerToEntity(this, -1.0);
                if (closestPlayerToEntity != null) {
                    final double n = closestPlayerToEntity.posX - this.posX;
                    final double n2 = closestPlayerToEntity.posY - this.posY;
                    final double n3 = closestPlayerToEntity.posZ - this.posZ;
                    final double n4 = n * n + n2 * n2 + n3 * n3;
                    if (this.canDespawn() && n4 > 16384.0) {
                        this.setDead();
                    }
                    if (this.entityAge > 600 && this.rand.nextInt(800) == 0 && n4 > 1024.0 && this.canDespawn()) {
                        this.setDead();
                    }
                    else if (n4 < 1024.0) {
                        this.entityAge = 0;
                    }
                }
            }
        }
    }
    
    protected void setEnchantmentBasedOnDifficulty(final DifficultyInstance difficultyInstance) {
        final float clampedAdditionalDifficulty = difficultyInstance.getClampedAdditionalDifficulty();
        if (this.getHeldItem() != null && this.rand.nextFloat() < 0.25f * clampedAdditionalDifficulty) {
            EnchantmentHelper.addRandomEnchantment(this.rand, this.getHeldItem(), (int)(5.0f + clampedAdditionalDifficulty * this.rand.nextInt(18)));
        }
        while (true) {
            final ItemStack currentArmor = this.getCurrentArmor(0);
            if (currentArmor != null && this.rand.nextFloat() < 0.5f * clampedAdditionalDifficulty) {
                EnchantmentHelper.addRandomEnchantment(this.rand, currentArmor, (int)(5.0f + clampedAdditionalDifficulty * this.rand.nextInt(18)));
            }
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void setCurrentItemOrArmor(final int n, final ItemStack itemStack) {
        this.equipment[n] = itemStack;
    }
    
    public void setAttackTarget(final EntityLivingBase attackTarget) {
        this.attackTarget = attackTarget;
        Reflector.callVoid(Reflector.ForgeHooks_onLivingSetAttackTarget, this, attackTarget);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1        
        //     2: invokespecial   net/minecraft/entity/EntityLivingBase.writeEntityToNBT:(Lnet/minecraft/nbt/NBTTagCompound;)V
        //     5: aload_1        
        //     6: ldc             "CanPickUpLoot"
        //     8: aload_0        
        //     9: invokevirtual   net/minecraft/entity/EntityLiving.canPickUpLoot:()Z
        //    12: invokevirtual   net/minecraft/nbt/NBTTagCompound.setBoolean:(Ljava/lang/String;Z)V
        //    15: aload_1        
        //    16: ldc             "PersistenceRequired"
        //    18: aload_0        
        //    19: getfield        net/minecraft/entity/EntityLiving.persistenceRequired:Z
        //    22: invokevirtual   net/minecraft/nbt/NBTTagCompound.setBoolean:(Ljava/lang/String;Z)V
        //    25: new             Lnet/minecraft/nbt/NBTTagList;
        //    28: dup            
        //    29: invokespecial   net/minecraft/nbt/NBTTagList.<init>:()V
        //    32: astore_2       
        //    33: iconst_0       
        //    34: aload_0        
        //    35: getfield        net/minecraft/entity/EntityLiving.equipment:[Lnet/minecraft/item/ItemStack;
        //    38: arraylength    
        //    39: if_icmpge       84
        //    42: new             Lnet/minecraft/nbt/NBTTagCompound;
        //    45: dup            
        //    46: invokespecial   net/minecraft/nbt/NBTTagCompound.<init>:()V
        //    49: astore          4
        //    51: aload_0        
        //    52: getfield        net/minecraft/entity/EntityLiving.equipment:[Lnet/minecraft/item/ItemStack;
        //    55: iconst_0       
        //    56: aaload         
        //    57: ifnull          72
        //    60: aload_0        
        //    61: getfield        net/minecraft/entity/EntityLiving.equipment:[Lnet/minecraft/item/ItemStack;
        //    64: iconst_0       
        //    65: aaload         
        //    66: aload           4
        //    68: invokevirtual   net/minecraft/item/ItemStack.writeToNBT:(Lnet/minecraft/nbt/NBTTagCompound;)Lnet/minecraft/nbt/NBTTagCompound;
        //    71: pop            
        //    72: aload_2        
        //    73: aload           4
        //    75: invokevirtual   net/minecraft/nbt/NBTTagList.appendTag:(Lnet/minecraft/nbt/NBTBase;)V
        //    78: iinc            3, 1
        //    81: goto            33
        //    84: aload_1        
        //    85: ldc             "Equipment"
        //    87: aload_2        
        //    88: invokevirtual   net/minecraft/nbt/NBTTagCompound.setTag:(Ljava/lang/String;Lnet/minecraft/nbt/NBTBase;)V
        //    91: new             Lnet/minecraft/nbt/NBTTagList;
        //    94: dup            
        //    95: invokespecial   net/minecraft/nbt/NBTTagList.<init>:()V
        //    98: astore_3       
        //    99: iconst_0       
        //   100: aload_0        
        //   101: getfield        net/minecraft/entity/EntityLiving.equipmentDropChances:[F
        //   104: arraylength    
        //   105: if_icmpge       131
        //   108: aload_3        
        //   109: new             Lnet/minecraft/nbt/NBTTagFloat;
        //   112: dup            
        //   113: aload_0        
        //   114: getfield        net/minecraft/entity/EntityLiving.equipmentDropChances:[F
        //   117: iconst_0       
        //   118: faload         
        //   119: invokespecial   net/minecraft/nbt/NBTTagFloat.<init>:(F)V
        //   122: invokevirtual   net/minecraft/nbt/NBTTagList.appendTag:(Lnet/minecraft/nbt/NBTBase;)V
        //   125: iinc            4, 1
        //   128: goto            99
        //   131: aload_1        
        //   132: ldc             "DropChances"
        //   134: aload_3        
        //   135: invokevirtual   net/minecraft/nbt/NBTTagCompound.setTag:(Ljava/lang/String;Lnet/minecraft/nbt/NBTBase;)V
        //   138: aload_1        
        //   139: ldc             "Leashed"
        //   141: aload_0        
        //   142: getfield        net/minecraft/entity/EntityLiving.isLeashed:Z
        //   145: invokevirtual   net/minecraft/nbt/NBTTagCompound.setBoolean:(Ljava/lang/String;Z)V
        //   148: aload_0        
        //   149: getfield        net/minecraft/entity/EntityLiving.leashedToEntity:Lnet/minecraft/entity/Entity;
        //   152: ifnull          282
        //   155: new             Lnet/minecraft/nbt/NBTTagCompound;
        //   158: dup            
        //   159: invokespecial   net/minecraft/nbt/NBTTagCompound.<init>:()V
        //   162: astore          4
        //   164: aload_0        
        //   165: getfield        net/minecraft/entity/EntityLiving.leashedToEntity:Lnet/minecraft/entity/Entity;
        //   168: instanceof      Lnet/minecraft/entity/EntityLivingBase;
        //   171: ifeq            213
        //   174: aload           4
        //   176: ldc_w           "UUIDMost"
        //   179: aload_0        
        //   180: getfield        net/minecraft/entity/EntityLiving.leashedToEntity:Lnet/minecraft/entity/Entity;
        //   183: invokevirtual   net/minecraft/entity/Entity.getUniqueID:()Ljava/util/UUID;
        //   186: invokevirtual   java/util/UUID.getMostSignificantBits:()J
        //   189: invokevirtual   net/minecraft/nbt/NBTTagCompound.setLong:(Ljava/lang/String;J)V
        //   192: aload           4
        //   194: ldc_w           "UUIDLeast"
        //   197: aload_0        
        //   198: getfield        net/minecraft/entity/EntityLiving.leashedToEntity:Lnet/minecraft/entity/Entity;
        //   201: invokevirtual   net/minecraft/entity/Entity.getUniqueID:()Ljava/util/UUID;
        //   204: invokevirtual   java/util/UUID.getLeastSignificantBits:()J
        //   207: invokevirtual   net/minecraft/nbt/NBTTagCompound.setLong:(Ljava/lang/String;J)V
        //   210: goto            274
        //   213: aload_0        
        //   214: getfield        net/minecraft/entity/EntityLiving.leashedToEntity:Lnet/minecraft/entity/Entity;
        //   217: instanceof      Lnet/minecraft/entity/EntityHanging;
        //   220: ifeq            274
        //   223: aload_0        
        //   224: getfield        net/minecraft/entity/EntityLiving.leashedToEntity:Lnet/minecraft/entity/Entity;
        //   227: checkcast       Lnet/minecraft/entity/EntityHanging;
        //   230: invokevirtual   net/minecraft/entity/EntityHanging.getHangingPosition:()Lnet/minecraft/util/BlockPos;
        //   233: astore          5
        //   235: aload           4
        //   237: ldc_w           "X"
        //   240: aload           5
        //   242: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //   245: invokevirtual   net/minecraft/nbt/NBTTagCompound.setInteger:(Ljava/lang/String;I)V
        //   248: aload           4
        //   250: ldc_w           "Y"
        //   253: aload           5
        //   255: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   258: invokevirtual   net/minecraft/nbt/NBTTagCompound.setInteger:(Ljava/lang/String;I)V
        //   261: aload           4
        //   263: ldc_w           "Z"
        //   266: aload           5
        //   268: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //   271: invokevirtual   net/minecraft/nbt/NBTTagCompound.setInteger:(Ljava/lang/String;I)V
        //   274: aload_1        
        //   275: ldc             "Leash"
        //   277: aload           4
        //   279: invokevirtual   net/minecraft/nbt/NBTTagCompound.setTag:(Ljava/lang/String;Lnet/minecraft/nbt/NBTBase;)V
        //   282: aload_0        
        //   283: ifeq            296
        //   286: aload_1        
        //   287: ldc             "NoAI"
        //   289: aload_0        
        //   290: invokevirtual   net/minecraft/entity/EntityLiving.isAIDisabled:()Z
        //   293: invokevirtual   net/minecraft/nbt/NBTTagCompound.setBoolean:(Ljava/lang/String;Z)V
        //   296: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public EntityMoveHelper getMoveHelper() {
        return this.moveHelper;
    }
    
    protected Item getDropItem() {
        return null;
    }
    
    private float updateRotation(final float n, final float n2, final float n3) {
        float wrapAngleTo180_float = MathHelper.wrapAngleTo180_float(n2 - n);
        if (wrapAngleTo180_float > n3) {
            wrapAngleTo180_float = n3;
        }
        if (wrapAngleTo180_float < -n3) {
            wrapAngleTo180_float = -n3;
        }
        return n + wrapAngleTo180_float;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(15, 0);
    }
    
    protected String getLivingSound() {
        return null;
    }
    
    @Override
    public boolean replaceItemInInventory(final int n, final ItemStack itemStack) {
        if (n != 99) {
            if (0 >= this.equipment.length) {
                return false;
            }
        }
        if (itemStack != null && getArmorPosition(itemStack) != 0) {
            return false;
        }
        this.setCurrentItemOrArmor(0, itemStack);
        return true;
    }
    
    public PathNavigate getNavigator() {
        return this.navigator;
    }
    
    private void onUpdateMinimal() {
        ++this.entityAge;
        if (this instanceof EntityMob && this.getBrightness(1.0f) > 0.5f) {
            this.entityAge += 2;
        }
        this.despawnEntity();
    }
    
    @Override
    protected int getExperiencePoints(final EntityPlayer entityPlayer) {
        if (this.experienceValue > 0) {
            int experienceValue = this.experienceValue;
            final ItemStack[] inventory = this.getInventory();
            while (0 < inventory.length) {
                if (inventory[0] != null && this.equipmentDropChances[0] <= 1.0f) {
                    experienceValue += 1 + this.rand.nextInt(3);
                }
                int n = 0;
                ++n;
            }
            return experienceValue;
        }
        return this.experienceValue;
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        final Item dropItem = this.getDropItem();
        if (dropItem != null) {
            int nextInt = this.rand.nextInt(3);
            if (n > 0) {
                nextInt += this.rand.nextInt(n + 1);
            }
            while (0 < nextInt) {
                this.dropItem(dropItem, 1);
                int n2 = 0;
                ++n2;
            }
        }
    }
    
    public void clearLeashed(final boolean b, final boolean b2) {
        if (this.isLeashed) {
            this.isLeashed = false;
            this.leashedToEntity = null;
            if (!this.worldObj.isRemote && b2) {
                this.dropItem(Items.lead, 1);
            }
            if (!this.worldObj.isRemote && b && this.worldObj instanceof WorldServer) {
                ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, new S1BPacketEntityAttach(1, this, null));
            }
        }
    }
    
    public boolean isServerWorld() {
        return super.isServerWorld() && this != 0;
    }
    
    @Override
    public boolean isEntityInsideOpaqueBlock() {
        if (this.noClip) {
            return false;
        }
        final BlockPosM blockPosM = new BlockPosM(0, 0, 0);
        while (true) {
            blockPosM.setXyz(this.posX + (0 - 0.5f) * this.width * 0.8f, this.posY + (0 - 0.5f) * 0.1f + this.getEyeHeight(), this.posZ + (0 - 0.5f) * this.width * 0.8f);
            if (this.worldObj.getBlockState(blockPosM).getBlock().isVisuallyOpaque()) {
                break;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    @Override
    public int getMaxFallHeight() {
        if (this.getAttackTarget() == null) {
            return 3;
        }
        final int n = (int)(this.getHealth() - this.getMaxHealth() * 0.33f);
        final int n2 = 0 - (3 - this.worldObj.getDifficulty().getDifficultyId()) * 4;
        return 3;
    }
    
    @Override
    public void handleStatusUpdate(final byte b) {
        if (b == 20) {
            this.spawnExplosionParticle();
        }
        else {
            super.handleStatusUpdate(b);
        }
    }
    
    protected void setEquipmentBasedOnDifficulty(final DifficultyInstance difficultyInstance) {
        if (this.rand.nextFloat() >= 0.15f * difficultyInstance.getClampedAdditionalDifficulty()) {
            return;
        }
        int nextInt = this.rand.nextInt(2);
        final float n = (this.worldObj.getDifficulty() == EnumDifficulty.HARD) ? 0.1f : 0.25f;
        if (this.rand.nextFloat() < 0.095f) {
            ++nextInt;
        }
        if (this.rand.nextFloat() < 0.095f) {
            ++nextInt;
        }
        if (this.rand.nextFloat() < 0.095f) {
            ++nextInt;
        }
        while (true) {
            if (this.getCurrentArmor(3) == null) {
                final Item armorItemForSlot = getArmorItemForSlot(4, nextInt);
                if (armorItemForSlot != null) {
                    this.setCurrentItemOrArmor(4, new ItemStack(armorItemForSlot));
                }
            }
            int n2 = 0;
            --n2;
        }
    }
    
    public boolean isNotColliding() {
        return this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), (Entity)this) && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(this.getEntityBoundingBox());
    }
    
    public enum SpawnPlacementType
    {
        private static final String __OBFID = "CL_00002255";
        private static final SpawnPlacementType[] $VALUES$;
        private static final SpawnPlacementType[] $VALUES;
        
        IN_WATER("IN_WATER", 2, "IN_WATER", 2), 
        ON_GROUND("ON_GROUND", 0, "ON_GROUND", 0), 
        IN_AIR("IN_AIR", 1, "IN_AIR", 1);
        
        static {
            $VALUES$ = new SpawnPlacementType[] { SpawnPlacementType.ON_GROUND, SpawnPlacementType.IN_AIR, SpawnPlacementType.IN_WATER };
            $VALUES = new SpawnPlacementType[] { SpawnPlacementType.ON_GROUND, SpawnPlacementType.IN_AIR, SpawnPlacementType.IN_WATER };
        }
        
        private SpawnPlacementType(final String s, final int n, final String s2, final int n2) {
        }
    }
}
