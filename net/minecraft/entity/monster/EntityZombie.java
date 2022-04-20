package net.minecraft.entity.monster;

import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.passive.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.potion.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.block.*;

public class EntityZombie extends EntityMob
{
    private final EntityAIBreakDoor breakDoor;
    private int conversionTime;
    private boolean isBreakDoorsTaskSet;
    private static final UUID babySpeedBoostUUID;
    private float zombieHeight;
    private float zombieWidth;
    private static final AttributeModifier babySpeedBoostModifier;
    protected static final IAttribute reinforcementChance;
    
    @Override
    public void handleStatusUpdate(final byte b) {
        if (b == 16) {
            if (!this.isSilent()) {
                this.worldObj.playSound(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5, "mob.zombie.remedy", 1.0f + this.rand.nextFloat(), this.rand.nextFloat() * 0.7f + 0.3f, false);
            }
        }
        else {
            super.handleStatusUpdate(b);
        }
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        if (nbtTagCompound.getBoolean("IsBaby")) {
            this.setChild(true);
        }
        if (nbtTagCompound.getBoolean("IsVillager")) {
            this.setVillager(true);
        }
        if (nbtTagCompound.hasKey("ConversionTime", 99) && nbtTagCompound.getInteger("ConversionTime") > -1) {
            this.startConversion(nbtTagCompound.getInteger("ConversionTime"));
        }
        this.setBreakDoorsAItask(nbtTagCompound.getBoolean("CanBreakDoors"));
    }
    
    public void setBreakDoorsAItask(final boolean isBreakDoorsTaskSet) {
        if (this.isBreakDoorsTaskSet != isBreakDoorsTaskSet) {
            this.isBreakDoorsTaskSet = isBreakDoorsTaskSet;
            if (isBreakDoorsTaskSet) {
                this.tasks.addTask(1, this.breakDoor);
            }
            else {
                this.tasks.removeTask(this.breakDoor);
            }
        }
    }
    
    public void setChild(final boolean childSize) {
        this.getDataWatcher().updateObject(12, (byte)(byte)(childSize ? 1 : 0));
        if (this.worldObj != null && !this.worldObj.isRemote) {
            final IAttributeInstance entityAttribute = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
            entityAttribute.removeModifier(EntityZombie.babySpeedBoostModifier);
            if (childSize) {
                entityAttribute.applyModifier(EntityZombie.babySpeedBoostModifier);
            }
        }
        this.setChildSize(childSize);
    }
    
    @Override
    public void onKillEntity(final EntityLivingBase entityLivingBase) {
        super.onKillEntity(entityLivingBase);
        if ((this.worldObj.getDifficulty() == EnumDifficulty.NORMAL || this.worldObj.getDifficulty() == EnumDifficulty.HARD) && entityLivingBase instanceof EntityVillager) {
            if (this.worldObj.getDifficulty() != EnumDifficulty.HARD && this.rand.nextBoolean()) {
                return;
            }
            final EntityLiving entityLiving = (EntityLiving)entityLivingBase;
            final EntityZombie entityZombie = new EntityZombie(this.worldObj);
            entityZombie.copyLocationAndAnglesFrom(entityLivingBase);
            this.worldObj.removeEntity(entityLivingBase);
            entityZombie.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos(entityZombie)), null);
            entityZombie.setVillager(true);
            if (entityLivingBase.isChild()) {
                entityZombie.setChild(true);
            }
            entityZombie.setNoAI(entityLiving.isAIDisabled());
            if (entityLiving.hasCustomName()) {
                entityZombie.setCustomNameTag(entityLiving.getCustomNameTag());
                entityZombie.setAlwaysRenderNameTag(entityLiving.getAlwaysRenderNameTag());
            }
            this.worldObj.spawnEntityInWorld(entityZombie);
            this.worldObj.playAuxSFXAtEntity(null, 1016, new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ), 0);
        }
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity entity) {
        final boolean attackEntityAsMob = super.attackEntityAsMob(entity);
        if (attackEntityAsMob) {
            final int difficultyId = this.worldObj.getDifficulty().getDifficultyId();
            if (this.getHeldItem() == null && this.isBurning() && this.rand.nextFloat() < difficultyId * 0.3f) {
                entity.setFire(2 * difficultyId);
            }
        }
        return attackEntityAsMob;
    }
    
    @Override
    public void onUpdate() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/entity/monster/EntityZombie.worldObj:Lnet/minecraft/world/World;
        //     4: getfield        net/minecraft/world/World.isRemote:Z
        //     7: ifne            40
        //    10: aload_0        
        //    11: if_icmpne       40
        //    14: aload_0        
        //    15: invokevirtual   net/minecraft/entity/monster/EntityZombie.getConversionTimeBoost:()I
        //    18: istore_1       
        //    19: aload_0        
        //    20: dup            
        //    21: getfield        net/minecraft/entity/monster/EntityZombie.conversionTime:I
        //    24: iload_1        
        //    25: isub           
        //    26: putfield        net/minecraft/entity/monster/EntityZombie.conversionTime:I
        //    29: aload_0        
        //    30: getfield        net/minecraft/entity/monster/EntityZombie.conversionTime:I
        //    33: ifgt            40
        //    36: aload_0        
        //    37: invokevirtual   net/minecraft/entity/monster/EntityZombie.convertToVillager:()V
        //    40: aload_0        
        //    41: invokespecial   net/minecraft/entity/monster/EntityMob.onUpdate:()V
        //    44: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    static {
        reinforcementChance = new RangedAttribute(null, "zombie.spawnReinforcements", 0.0, 0.0, 1.0).setDescription("Spawn Reinforcements Chance");
        babySpeedBoostUUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
        babySpeedBoostModifier = new AttributeModifier(EntityZombie.babySpeedBoostUUID, "Baby speed boost", 0.5, 1);
    }
    
    @Override
    protected boolean canDespawn() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_icmpne       8
        //     4: iconst_1       
        //     5: goto            9
        //     8: iconst_0       
        //     9: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    protected int getExperiencePoints(final EntityPlayer p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_icmpne       18
        //     4: aload_0        
        //     5: aload_0        
        //     6: getfield        net/minecraft/entity/monster/EntityZombie.experienceValue:I
        //     9: i2f            
        //    10: ldc_w           2.5
        //    13: fmul           
        //    14: f2i            
        //    15: putfield        net/minecraft/entity/monster/EntityZombie.experienceValue:I
        //    18: aload_0        
        //    19: aload_1        
        //    20: invokespecial   net/minecraft/entity/monster/EntityMob.getExperiencePoints:(Lnet/minecraft/entity/player/EntityPlayer;)I
        //    23: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    protected final void setSize(final float zombieWidth, final float zombieHeight) {
        final boolean b = this.zombieWidth > 0.0f && this.zombieHeight > 0.0f;
        this.zombieWidth = zombieWidth;
        this.zombieHeight = zombieHeight;
        if (!b) {
            this.multiplySize(1.0f);
        }
    }
    
    public EntityZombie(final World world) {
        super(world);
        this.breakDoor = new EntityAIBreakDoor(this);
        this.isBreakDoorsTaskSet = false;
        this.zombieWidth = -1.0f;
        ((PathNavigateGround)this.getNavigator()).setBreakDoors(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
        this.setSize(0.6f, 1.95f);
    }
    
    @Override
    public float getEyeHeight() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: fstore_1       
        //     4: aload_0        
        //     5: if_icmpne       16
        //     8: fload_1        
        //     9: f2d            
        //    10: ldc2_w          0.81
        //    13: dsub           
        //    14: d2f            
        //    15: fstore_1       
        //    16: fload_1        
        //    17: freturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    protected void addRandomDrop() {
        switch (this.rand.nextInt(3)) {
            case 0: {
                this.dropItem(Items.iron_ingot, 1);
                break;
            }
            case 1: {
                this.dropItem(Items.carrot, 1);
                break;
            }
            case 2: {
                this.dropItem(Items.potato, 1);
                break;
            }
        }
    }
    
    @Override
    public IEntityLivingData onInitialSpawn(final DifficultyInstance difficultyInstance, IEntityLivingData onInitialSpawn) {
        onInitialSpawn = super.onInitialSpawn(difficultyInstance, (IEntityLivingData)onInitialSpawn);
        final float clampedAdditionalDifficulty = difficultyInstance.getClampedAdditionalDifficulty();
        this.setCanPickUpLoot(this.rand.nextFloat() < 0.55f * clampedAdditionalDifficulty);
        if (onInitialSpawn == null) {
            onInitialSpawn = new GroupData(this.worldObj.rand.nextFloat() < 0.05f, this.worldObj.rand.nextFloat() < 0.05f, null);
        }
        if (onInitialSpawn instanceof GroupData) {
            final GroupData groupData = (GroupData)onInitialSpawn;
            if (groupData.isVillager) {
                this.setVillager(true);
            }
            if (groupData.isChild) {
                this.setChild(true);
                if (this.worldObj.rand.nextFloat() < 0.05) {
                    final List entitiesWithinAABB = this.worldObj.getEntitiesWithinAABB(EntityChicken.class, this.getEntityBoundingBox().expand(5.0, 3.0, 5.0), EntitySelectors.IS_STANDALONE);
                    if (!entitiesWithinAABB.isEmpty()) {
                        final EntityChicken entityChicken = entitiesWithinAABB.get(0);
                        entityChicken.setChickenJockey(true);
                        this.mountEntity(entityChicken);
                    }
                }
                else if (this.worldObj.rand.nextFloat() < 0.05) {
                    final EntityChicken entityChicken2 = new EntityChicken(this.worldObj);
                    entityChicken2.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
                    entityChicken2.onInitialSpawn(difficultyInstance, null);
                    entityChicken2.setChickenJockey(true);
                    this.worldObj.spawnEntityInWorld(entityChicken2);
                    this.mountEntity(entityChicken2);
                }
            }
        }
        this.setBreakDoorsAItask(this.rand.nextFloat() < clampedAdditionalDifficulty * 0.1f);
        this.setEquipmentBasedOnDifficulty(difficultyInstance);
        this.setEnchantmentBasedOnDifficulty(difficultyInstance);
        if (this.getEquipmentInSlot(4) == null) {
            final Calendar currentDate = this.worldObj.getCurrentDate();
            if (currentDate.get(2) + 1 == 10 && currentDate.get(5) == 31 && this.rand.nextFloat() < 0.25f) {
                this.setCurrentItemOrArmor(4, new ItemStack((this.rand.nextFloat() < 0.1f) ? Blocks.lit_pumpkin : Blocks.pumpkin));
                this.equipmentDropChances[4] = 0.0f;
            }
        }
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextDouble() * 0.05000000074505806, 0));
        final double n = this.rand.nextDouble() * 1.5 * clampedAdditionalDifficulty;
        if (n > 1.0) {
            this.getEntityAttribute(SharedMonsterAttributes.followRange).applyModifier(new AttributeModifier("Random zombie-spawn bonus", n, 2));
        }
        if (this.rand.nextFloat() < clampedAdditionalDifficulty * 0.05f) {
            this.getEntityAttribute(EntityZombie.reinforcementChance).applyModifier(new AttributeModifier("Leader zombie bonus", this.rand.nextDouble() * 0.25 + 0.5, 0));
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).applyModifier(new AttributeModifier("Leader zombie bonus", this.rand.nextDouble() * 3.0 + 1.0, 2));
            this.setBreakDoorsAItask(true);
        }
        return (IEntityLivingData)onInitialSpawn;
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.zombie.say";
    }
    
    @Override
    protected void setEquipmentBasedOnDifficulty(final DifficultyInstance equipmentBasedOnDifficulty) {
        super.setEquipmentBasedOnDifficulty(equipmentBasedOnDifficulty);
        if (this.rand.nextFloat() < ((this.worldObj.getDifficulty() == EnumDifficulty.HARD) ? 0.05f : 0.01f)) {
            if (this.rand.nextInt(3) == 0) {
                this.setCurrentItemOrArmor(0, new ItemStack(Items.iron_sword));
            }
            else {
                this.setCurrentItemOrArmor(0, new ItemStack(Items.iron_shovel));
            }
        }
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (super.attackEntityFrom(damageSource, n)) {
            EntityLivingBase attackTarget = this.getAttackTarget();
            if (attackTarget == null && damageSource.getEntity() instanceof EntityLivingBase) {
                attackTarget = (EntityLivingBase)damageSource.getEntity();
            }
            if (attackTarget != null && this.worldObj.getDifficulty() == EnumDifficulty.HARD && this.rand.nextFloat() < this.getEntityAttribute(EntityZombie.reinforcementChance).getAttributeValue()) {
                final int floor_double = MathHelper.floor_double(this.posX);
                final int floor_double2 = MathHelper.floor_double(this.posY);
                final int floor_double3 = MathHelper.floor_double(this.posZ);
                final EntityZombie entityZombie = new EntityZombie(this.worldObj);
                while (true) {
                    final int n2 = floor_double + MathHelper.getRandomIntegerInRange(this.rand, 7, 40) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);
                    final int n3 = floor_double2 + MathHelper.getRandomIntegerInRange(this.rand, 7, 40) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);
                    final int n4 = floor_double3 + MathHelper.getRandomIntegerInRange(this.rand, 7, 40) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);
                    if (World.doesBlockHaveSolidTopSurface(this.worldObj, new BlockPos(n2, n3 - 1, n4)) && this.worldObj.getLightFromNeighbors(new BlockPos(n2, n3, n4)) < 10) {
                        entityZombie.setPosition(n2, n3, n4);
                        if (!this.worldObj.isAnyPlayerWithinRangeAt(n2, n3, n4, 7.0) && this.worldObj.checkNoEntityCollision(entityZombie.getEntityBoundingBox(), (Entity)entityZombie) && this.worldObj.getCollidingBoundingBoxes(entityZombie, entityZombie.getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(entityZombie.getEntityBoundingBox())) {
                            break;
                        }
                    }
                    int n5 = 0;
                    ++n5;
                }
                this.worldObj.spawnEntityInWorld(entityZombie);
                entityZombie.setAttackTarget(attackTarget);
                entityZombie.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos(entityZombie)), null);
                this.getEntityAttribute(EntityZombie.reinforcementChance).applyModifier(new AttributeModifier("Zombie reinforcement caller charge", -0.05000000074505806, 0));
                entityZombie.getEntityAttribute(EntityZombie.reinforcementChance).applyModifier(new AttributeModifier("Zombie reinforcement callee charge", -0.05000000074505806, 0));
            }
            return true;
        }
        return false;
    }
    
    protected void startConversion(final int conversionTime) {
        this.conversionTime = conversionTime;
        this.getDataWatcher().updateObject(14, 1);
        this.removePotionEffect(Potion.weakness.id);
        this.addPotionEffect(new PotionEffect(Potion.damageBoost.id, conversionTime, Math.min(this.worldObj.getDifficulty().getDifficultyId() - 1, 0)));
        this.worldObj.setEntityState(this, (byte)16);
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.zombie.hurt";
    }
    
    protected void applyEntityAI() {
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityVillager.class, 1.0, true));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityIronGolem.class, 1.0, true));
        this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0, false));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] { EntityPigZombie.class }));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
    }
    
    @Override
    public double getYOffset() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_icmpne       8
        //     4: dconst_0       
        //     5: goto            11
        //     8: ldc2_w          -0.35
        //    11: dreturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    protected final void multiplySize(final float n) {
        super.setSize(this.zombieWidth * n, this.zombieHeight * n);
    }
    
    @Override
    protected Item getDropItem() {
        return Items.rotten_flesh;
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1        
        //     2: invokespecial   net/minecraft/entity/monster/EntityMob.writeEntityToNBT:(Lnet/minecraft/nbt/NBTTagCompound;)V
        //     5: aload_0        
        //     6: if_icmpne       16
        //     9: aload_1        
        //    10: ldc             "IsBaby"
        //    12: iconst_1       
        //    13: invokevirtual   net/minecraft/nbt/NBTTagCompound.setBoolean:(Ljava/lang/String;Z)V
        //    16: aload_0        
        //    17: if_icmpne       27
        //    20: aload_1        
        //    21: ldc             "IsVillager"
        //    23: iconst_1       
        //    24: invokevirtual   net/minecraft/nbt/NBTTagCompound.setBoolean:(Ljava/lang/String;Z)V
        //    27: aload_1        
        //    28: ldc             "ConversionTime"
        //    30: aload_0        
        //    31: if_icmpne       41
        //    34: aload_0        
        //    35: getfield        net/minecraft/entity/monster/EntityZombie.conversionTime:I
        //    38: goto            42
        //    41: iconst_m1      
        //    42: invokevirtual   net/minecraft/nbt/NBTTagCompound.setInteger:(Ljava/lang/String;I)V
        //    45: aload_1        
        //    46: ldc             "CanBreakDoors"
        //    48: aload_0        
        //    49: invokevirtual   net/minecraft/entity/monster/EntityZombie.isBreakDoorsTaskSet:()Z
        //    52: invokevirtual   net/minecraft/nbt/NBTTagCompound.setBoolean:(Ljava/lang/String;Z)V
        //    55: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public boolean isBreakDoorsTaskSet() {
        return this.isBreakDoorsTaskSet;
    }
    
    protected int getConversionTimeBoost() {
        if (this.rand.nextFloat() < 0.01f) {
            final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
            for (int i = (int)this.posX - 4; i < (int)this.posX + 4; ++i) {
                for (int j = (int)this.posY - 4; j < (int)this.posY + 4; ++j) {
                    for (int k = (int)this.posZ - 4; k < (int)this.posZ + 4; ++k) {
                        final Block block = this.worldObj.getBlockState(mutableBlockPos.func_181079_c(i, j, k)).getBlock();
                        if (block == Blocks.iron_bars || block == Blocks.bed) {
                            if (this.rand.nextFloat() < 0.3f) {
                                int n = 0;
                                ++n;
                            }
                            int n2 = 0;
                            ++n2;
                        }
                    }
                }
            }
        }
        return 1;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(35.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0);
        this.getAttributeMap().registerAttribute(EntityZombie.reinforcementChance).setBaseValue(this.rand.nextDouble() * 0.10000000149011612);
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.zombie.death";
    }
    
    public boolean interact(final EntityPlayer p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   net/minecraft/entity/player/EntityPlayer.getCurrentEquippedItem:()Lnet/minecraft/item/ItemStack;
        //     4: astore_2       
        //     5: aload_2        
        //     6: ifnull          115
        //     9: aload_2        
        //    10: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //    13: getstatic       net/minecraft/init/Items.golden_apple:Lnet/minecraft/item/Item;
        //    16: if_acmpne       115
        //    19: aload_2        
        //    20: invokevirtual   net/minecraft/item/ItemStack.getMetadata:()I
        //    23: ifne            115
        //    26: aload_0        
        //    27: if_icmpne       115
        //    30: aload_0        
        //    31: getstatic       net/minecraft/potion/Potion.weakness:Lnet/minecraft/potion/Potion;
        //    34: invokevirtual   net/minecraft/entity/monster/EntityZombie.isPotionActive:(Lnet/minecraft/potion/Potion;)Z
        //    37: ifeq            115
        //    40: aload_1        
        //    41: getfield        net/minecraft/entity/player/EntityPlayer.capabilities:Lnet/minecraft/entity/player/PlayerCapabilities;
        //    44: getfield        net/minecraft/entity/player/PlayerCapabilities.isCreativeMode:Z
        //    47: ifne            60
        //    50: aload_2        
        //    51: dup            
        //    52: getfield        net/minecraft/item/ItemStack.stackSize:I
        //    55: iconst_1       
        //    56: isub           
        //    57: putfield        net/minecraft/item/ItemStack.stackSize:I
        //    60: aload_2        
        //    61: getfield        net/minecraft/item/ItemStack.stackSize:I
        //    64: ifgt            85
        //    67: aload_1        
        //    68: getfield        net/minecraft/entity/player/EntityPlayer.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //    71: aload_1        
        //    72: getfield        net/minecraft/entity/player/EntityPlayer.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //    75: getfield        net/minecraft/entity/player/InventoryPlayer.currentItem:I
        //    78: aconst_null    
        //    79: checkcast       Lnet/minecraft/item/ItemStack;
        //    82: invokevirtual   net/minecraft/entity/player/InventoryPlayer.setInventorySlotContents:(ILnet/minecraft/item/ItemStack;)V
        //    85: aload_0        
        //    86: getfield        net/minecraft/entity/monster/EntityZombie.worldObj:Lnet/minecraft/world/World;
        //    89: getfield        net/minecraft/world/World.isRemote:Z
        //    92: ifne            113
        //    95: aload_0        
        //    96: aload_0        
        //    97: getfield        net/minecraft/entity/monster/EntityZombie.rand:Ljava/util/Random;
        //   100: sipush          2401
        //   103: invokevirtual   java/util/Random.nextInt:(I)I
        //   106: sipush          3600
        //   109: iadd           
        //   110: invokevirtual   net/minecraft/entity/monster/EntityZombie.startConversion:(I)V
        //   113: iconst_1       
        //   114: ireturn        
        //   115: iconst_0       
        //   116: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataWatcher().addObject(12, 0);
        this.getDataWatcher().addObject(13, 0);
        this.getDataWatcher().addObject(14, 0);
    }
    
    public void setChildSize(final boolean b) {
        this.multiplySize(b ? 0.5f : 1.0f);
    }
    
    @Override
    public int getTotalArmorValue() {
        final int n = super.getTotalArmorValue() + 2;
        return 20;
    }
    
    @Override
    public void onLivingUpdate() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/entity/monster/EntityZombie.worldObj:Lnet/minecraft/world/World;
        //     4: invokevirtual   net/minecraft/world/World.isDaytime:()Z
        //     7: ifeq            171
        //    10: aload_0        
        //    11: getfield        net/minecraft/entity/monster/EntityZombie.worldObj:Lnet/minecraft/world/World;
        //    14: getfield        net/minecraft/world/World.isRemote:Z
        //    17: ifne            171
        //    20: aload_0        
        //    21: if_icmpne       171
        //    24: aload_0        
        //    25: fconst_1       
        //    26: invokevirtual   net/minecraft/entity/monster/EntityZombie.getBrightness:(F)F
        //    29: fstore_1       
        //    30: new             Lnet/minecraft/util/BlockPos;
        //    33: dup            
        //    34: aload_0        
        //    35: getfield        net/minecraft/entity/monster/EntityZombie.posX:D
        //    38: aload_0        
        //    39: getfield        net/minecraft/entity/monster/EntityZombie.posY:D
        //    42: invokestatic    java/lang/Math.round:(D)J
        //    45: l2d            
        //    46: aload_0        
        //    47: getfield        net/minecraft/entity/monster/EntityZombie.posZ:D
        //    50: invokespecial   net/minecraft/util/BlockPos.<init>:(DDD)V
        //    53: astore_2       
        //    54: fload_1        
        //    55: ldc_w           0.5
        //    58: fcmpl          
        //    59: ifle            171
        //    62: aload_0        
        //    63: getfield        net/minecraft/entity/monster/EntityZombie.rand:Ljava/util/Random;
        //    66: invokevirtual   java/util/Random.nextFloat:()F
        //    69: ldc_w           30.0
        //    72: fmul           
        //    73: fload_1        
        //    74: ldc_w           0.4
        //    77: fsub           
        //    78: fconst_2       
        //    79: fmul           
        //    80: fcmpg          
        //    81: ifge            171
        //    84: aload_0        
        //    85: getfield        net/minecraft/entity/monster/EntityZombie.worldObj:Lnet/minecraft/world/World;
        //    88: aload_2        
        //    89: invokevirtual   net/minecraft/world/World.canSeeSky:(Lnet/minecraft/util/BlockPos;)Z
        //    92: ifeq            171
        //    95: aload_0        
        //    96: iconst_4       
        //    97: invokevirtual   net/minecraft/entity/monster/EntityZombie.getEquipmentInSlot:(I)Lnet/minecraft/item/ItemStack;
        //   100: astore          4
        //   102: aload           4
        //   104: ifnull          162
        //   107: aload           4
        //   109: invokevirtual   net/minecraft/item/ItemStack.isItemStackDamageable:()Z
        //   112: ifeq            162
        //   115: aload           4
        //   117: aload           4
        //   119: invokevirtual   net/minecraft/item/ItemStack.getItemDamage:()I
        //   122: aload_0        
        //   123: getfield        net/minecraft/entity/monster/EntityZombie.rand:Ljava/util/Random;
        //   126: iconst_2       
        //   127: invokevirtual   java/util/Random.nextInt:(I)I
        //   130: iadd           
        //   131: invokevirtual   net/minecraft/item/ItemStack.setItemDamage:(I)V
        //   134: aload           4
        //   136: invokevirtual   net/minecraft/item/ItemStack.getItemDamage:()I
        //   139: aload           4
        //   141: invokevirtual   net/minecraft/item/ItemStack.getMaxDamage:()I
        //   144: if_icmplt       162
        //   147: aload_0        
        //   148: aload           4
        //   150: invokevirtual   net/minecraft/entity/monster/EntityZombie.renderBrokenItemStack:(Lnet/minecraft/item/ItemStack;)V
        //   153: aload_0        
        //   154: iconst_4       
        //   155: aconst_null    
        //   156: checkcast       Lnet/minecraft/item/ItemStack;
        //   159: invokevirtual   net/minecraft/entity/monster/EntityZombie.setCurrentItemOrArmor:(ILnet/minecraft/item/ItemStack;)V
        //   162: goto            171
        //   165: aload_0        
        //   166: bipush          8
        //   168: invokevirtual   net/minecraft/entity/monster/EntityZombie.setFire:(I)V
        //   171: aload_0        
        //   172: invokevirtual   net/minecraft/entity/monster/EntityZombie.isRiding:()Z
        //   175: ifeq            219
        //   178: aload_0        
        //   179: invokevirtual   net/minecraft/entity/monster/EntityZombie.getAttackTarget:()Lnet/minecraft/entity/EntityLivingBase;
        //   182: ifnull          219
        //   185: aload_0        
        //   186: getfield        net/minecraft/entity/monster/EntityZombie.ridingEntity:Lnet/minecraft/entity/Entity;
        //   189: instanceof      Lnet/minecraft/entity/passive/EntityChicken;
        //   192: ifeq            219
        //   195: aload_0        
        //   196: getfield        net/minecraft/entity/monster/EntityZombie.ridingEntity:Lnet/minecraft/entity/Entity;
        //   199: checkcast       Lnet/minecraft/entity/EntityLiving;
        //   202: invokevirtual   net/minecraft/entity/EntityLiving.getNavigator:()Lnet/minecraft/pathfinding/PathNavigate;
        //   205: aload_0        
        //   206: invokevirtual   net/minecraft/entity/monster/EntityZombie.getNavigator:()Lnet/minecraft/pathfinding/PathNavigate;
        //   209: invokevirtual   net/minecraft/pathfinding/PathNavigate.getPath:()Lnet/minecraft/pathfinding/PathEntity;
        //   212: ldc2_w          1.5
        //   215: invokevirtual   net/minecraft/pathfinding/PathNavigate.setPath:(Lnet/minecraft/pathfinding/PathEntity;D)Z
        //   218: pop            
        //   219: aload_0        
        //   220: invokespecial   net/minecraft/entity/monster/EntityMob.onLivingUpdate:()V
        //   223: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    protected void convertToVillager() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: aload_0        
        //     5: getfield        net/minecraft/entity/monster/EntityZombie.worldObj:Lnet/minecraft/world/World;
        //     8: invokespecial   net/minecraft/entity/passive/EntityVillager.<init>:(Lnet/minecraft/world/World;)V
        //    11: astore_1       
        //    12: aload_1        
        //    13: aload_0        
        //    14: invokevirtual   net/minecraft/entity/passive/EntityVillager.copyLocationAndAnglesFrom:(Lnet/minecraft/entity/Entity;)V
        //    17: aload_1        
        //    18: aload_0        
        //    19: getfield        net/minecraft/entity/monster/EntityZombie.worldObj:Lnet/minecraft/world/World;
        //    22: new             Lnet/minecraft/util/BlockPos;
        //    25: dup            
        //    26: aload_1        
        //    27: invokespecial   net/minecraft/util/BlockPos.<init>:(Lnet/minecraft/entity/Entity;)V
        //    30: invokevirtual   net/minecraft/world/World.getDifficultyForLocation:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/world/DifficultyInstance;
        //    33: aconst_null    
        //    34: checkcast       Lnet/minecraft/entity/IEntityLivingData;
        //    37: invokevirtual   net/minecraft/entity/passive/EntityVillager.onInitialSpawn:(Lnet/minecraft/world/DifficultyInstance;Lnet/minecraft/entity/IEntityLivingData;)Lnet/minecraft/entity/IEntityLivingData;
        //    40: pop            
        //    41: aload_1        
        //    42: invokevirtual   net/minecraft/entity/passive/EntityVillager.setLookingForHome:()V
        //    45: aload_0        
        //    46: if_icmpne       56
        //    49: aload_1        
        //    50: sipush          -24000
        //    53: invokevirtual   net/minecraft/entity/passive/EntityVillager.setGrowingAge:(I)V
        //    56: aload_0        
        //    57: getfield        net/minecraft/entity/monster/EntityZombie.worldObj:Lnet/minecraft/world/World;
        //    60: aload_0        
        //    61: invokevirtual   net/minecraft/world/World.removeEntity:(Lnet/minecraft/entity/Entity;)V
        //    64: aload_1        
        //    65: aload_0        
        //    66: invokevirtual   net/minecraft/entity/monster/EntityZombie.isAIDisabled:()Z
        //    69: invokevirtual   net/minecraft/entity/passive/EntityVillager.setNoAI:(Z)V
        //    72: aload_0        
        //    73: invokevirtual   net/minecraft/entity/monster/EntityZombie.hasCustomName:()Z
        //    76: ifeq            95
        //    79: aload_1        
        //    80: aload_0        
        //    81: invokevirtual   net/minecraft/entity/monster/EntityZombie.getCustomNameTag:()Ljava/lang/String;
        //    84: invokevirtual   net/minecraft/entity/passive/EntityVillager.setCustomNameTag:(Ljava/lang/String;)V
        //    87: aload_1        
        //    88: aload_0        
        //    89: invokevirtual   net/minecraft/entity/monster/EntityZombie.getAlwaysRenderNameTag:()Z
        //    92: invokevirtual   net/minecraft/entity/passive/EntityVillager.setAlwaysRenderNameTag:(Z)V
        //    95: aload_0        
        //    96: getfield        net/minecraft/entity/monster/EntityZombie.worldObj:Lnet/minecraft/world/World;
        //    99: aload_1        
        //   100: invokevirtual   net/minecraft/world/World.spawnEntityInWorld:(Lnet/minecraft/entity/Entity;)Z
        //   103: pop            
        //   104: aload_1        
        //   105: new             Lnet/minecraft/potion/PotionEffect;
        //   108: dup            
        //   109: getstatic       net/minecraft/potion/Potion.confusion:Lnet/minecraft/potion/Potion;
        //   112: getfield        net/minecraft/potion/Potion.id:I
        //   115: sipush          200
        //   118: iconst_0       
        //   119: invokespecial   net/minecraft/potion/PotionEffect.<init>:(III)V
        //   122: invokevirtual   net/minecraft/entity/passive/EntityVillager.addPotionEffect:(Lnet/minecraft/potion/PotionEffect;)V
        //   125: aload_0        
        //   126: getfield        net/minecraft/entity/monster/EntityZombie.worldObj:Lnet/minecraft/world/World;
        //   129: aconst_null    
        //   130: checkcast       Lnet/minecraft/entity/player/EntityPlayer;
        //   133: sipush          1017
        //   136: new             Lnet/minecraft/util/BlockPos;
        //   139: dup            
        //   140: aload_0        
        //   141: getfield        net/minecraft/entity/monster/EntityZombie.posX:D
        //   144: d2i            
        //   145: aload_0        
        //   146: getfield        net/minecraft/entity/monster/EntityZombie.posY:D
        //   149: d2i            
        //   150: aload_0        
        //   151: getfield        net/minecraft/entity/monster/EntityZombie.posZ:D
        //   154: d2i            
        //   155: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   158: iconst_0       
        //   159: invokevirtual   net/minecraft/world/World.playAuxSFXAtEntity:(Lnet/minecraft/entity/player/EntityPlayer;ILnet/minecraft/util/BlockPos;I)V
        //   162: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    protected boolean func_175448_a(final ItemStack p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //     4: getstatic       net/minecraft/init/Items.egg:Lnet/minecraft/item/Item;
        //     7: if_acmpne       25
        //    10: aload_0        
        //    11: if_icmpne       25
        //    14: aload_0        
        //    15: invokevirtual   net/minecraft/entity/monster/EntityZombie.isRiding:()Z
        //    18: ifeq            25
        //    21: iconst_0       
        //    22: goto            30
        //    25: aload_0        
        //    26: aload_1        
        //    27: invokespecial   net/minecraft/entity/monster/EntityMob.func_175448_a:(Lnet/minecraft/item/ItemStack;)Z
        //    30: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void setVillager(final boolean b) {
        this.getDataWatcher().updateObject(13, (byte)(byte)(b ? 1 : 0));
    }
    
    @Override
    protected void playStepSound(final BlockPos blockPos, final Block block) {
        this.playSound("mob.zombie.step", 0.15f, 1.0f);
    }
    
    @Override
    public void onDeath(final DamageSource damageSource) {
        super.onDeath(damageSource);
        if (damageSource.getEntity() instanceof EntityCreeper && !(this instanceof EntityPigZombie) && ((EntityCreeper)damageSource.getEntity()).getPowered() && ((EntityCreeper)damageSource.getEntity()).isAIEnabled()) {
            ((EntityCreeper)damageSource.getEntity()).func_175493_co();
            this.entityDropItem(new ItemStack(Items.skull, 1, 2), 0.0f);
        }
    }
    
    class GroupData implements IEntityLivingData
    {
        public boolean isVillager;
        public boolean isChild;
        final EntityZombie this$0;
        
        private GroupData(final EntityZombie this$0, final boolean isChild, final boolean isVillager) {
            this.this$0 = this$0;
            this.isChild = false;
            this.isVillager = false;
            this.isChild = isChild;
            this.isVillager = isVillager;
        }
        
        GroupData(final EntityZombie entityZombie, final boolean b, final boolean b2, final EntityZombie$1 object) {
            this(entityZombie, b, b2);
        }
    }
}
