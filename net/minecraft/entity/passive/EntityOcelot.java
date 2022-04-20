package net.minecraft.entity.passive;

import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.pathfinding.*;
import com.google.common.base.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;

public class EntityOcelot extends EntityTameable
{
    private EntityAITempt aiTempt;
    private EntityAIAvoidEntity avoidEntity;
    
    public void updateAITasks() {
        if (this.getMoveHelper().isUpdating()) {
            final double speed = this.getMoveHelper().getSpeed();
            if (speed == 0.6) {
                this.setSneaking(true);
                this.setSprinting(false);
            }
            else if (speed == 1.33) {
                this.setSneaking(false);
                this.setSprinting(true);
            }
            else {
                this.setSneaking(false);
                this.setSprinting(false);
            }
        }
        else {
            this.setSneaking(false);
            this.setSprinting(false);
        }
    }
    
    @Override
    public IEntityLivingData onInitialSpawn(final DifficultyInstance difficultyInstance, IEntityLivingData onInitialSpawn) {
        onInitialSpawn = super.onInitialSpawn(difficultyInstance, onInitialSpawn);
        if (this.worldObj.rand.nextInt(7) != 0) {
            return onInitialSpawn;
        }
        while (true) {
            final EntityOcelot entityOcelot = new EntityOcelot(this.worldObj);
            entityOcelot.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
            entityOcelot.setGrowingAge(-24000);
            this.worldObj.spawnEntityInWorld(entityOcelot);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void fall(final float n, final float n2) {
    }
    
    @Override
    protected Item getDropItem() {
        return Items.leather;
    }
    
    @Override
    public boolean canMateWith(final EntityAnimal entityAnimal) {
        if (entityAnimal == this) {
            return false;
        }
        if (!this.isTamed()) {
            return false;
        }
        if (!(entityAnimal instanceof EntityOcelot)) {
            return false;
        }
        final EntityOcelot entityOcelot = (EntityOcelot)entityAnimal;
        return entityOcelot.isTamed() && (this.isInLove() && entityOcelot.isInLove());
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896);
    }
    
    @Override
    public void setTamed(final boolean tamed) {
        super.setTamed(tamed);
    }
    
    @Override
    public boolean interact(final EntityPlayer p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/entity/player/EntityPlayer.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //     4: invokevirtual   net/minecraft/entity/player/InventoryPlayer.getCurrentItem:()Lnet/minecraft/item/ItemStack;
        //     7: astore_2       
        //     8: aload_0        
        //     9: invokevirtual   net/minecraft/entity/passive/EntityOcelot.isTamed:()Z
        //    12: ifeq            60
        //    15: aload_0        
        //    16: aload_1        
        //    17: invokevirtual   net/minecraft/entity/passive/EntityOcelot.isOwner:(Lnet/minecraft/entity/EntityLivingBase;)Z
        //    20: ifeq            238
        //    23: aload_0        
        //    24: getfield        net/minecraft/entity/passive/EntityOcelot.worldObj:Lnet/minecraft/world/World;
        //    27: getfield        net/minecraft/world/World.isRemote:Z
        //    30: ifne            238
        //    33: aload_0        
        //    34: aload_2        
        //    35: ifnull          238
        //    38: aload_0        
        //    39: getfield        net/minecraft/entity/passive/EntityOcelot.aiSit:Lnet/minecraft/entity/ai/EntityAISit;
        //    42: aload_0        
        //    43: invokevirtual   net/minecraft/entity/passive/EntityOcelot.isSitting:()Z
        //    46: ifne            53
        //    49: iconst_1       
        //    50: goto            54
        //    53: iconst_0       
        //    54: invokevirtual   net/minecraft/entity/ai/EntityAISit.setSitting:(Z)V
        //    57: goto            238
        //    60: aload_0        
        //    61: getfield        net/minecraft/entity/passive/EntityOcelot.aiTempt:Lnet/minecraft/entity/ai/EntityAITempt;
        //    64: invokevirtual   net/minecraft/entity/ai/EntityAITempt.isRunning:()Z
        //    67: ifeq            238
        //    70: aload_2        
        //    71: ifnull          238
        //    74: aload_2        
        //    75: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //    78: getstatic       net/minecraft/init/Items.fish:Lnet/minecraft/item/Item;
        //    81: if_acmpne       238
        //    84: aload_1        
        //    85: aload_0        
        //    86: invokevirtual   net/minecraft/entity/player/EntityPlayer.getDistanceSqToEntity:(Lnet/minecraft/entity/Entity;)D
        //    89: ldc2_w          9.0
        //    92: dcmpg          
        //    93: ifge            238
        //    96: aload_1        
        //    97: getfield        net/minecraft/entity/player/EntityPlayer.capabilities:Lnet/minecraft/entity/player/PlayerCapabilities;
        //   100: getfield        net/minecraft/entity/player/PlayerCapabilities.isCreativeMode:Z
        //   103: ifne            116
        //   106: aload_2        
        //   107: dup            
        //   108: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   111: iconst_1       
        //   112: isub           
        //   113: putfield        net/minecraft/item/ItemStack.stackSize:I
        //   116: aload_2        
        //   117: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   120: ifgt            141
        //   123: aload_1        
        //   124: getfield        net/minecraft/entity/player/EntityPlayer.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //   127: aload_1        
        //   128: getfield        net/minecraft/entity/player/EntityPlayer.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //   131: getfield        net/minecraft/entity/player/InventoryPlayer.currentItem:I
        //   134: aconst_null    
        //   135: checkcast       Lnet/minecraft/item/ItemStack;
        //   138: invokevirtual   net/minecraft/entity/player/InventoryPlayer.setInventorySlotContents:(ILnet/minecraft/item/ItemStack;)V
        //   141: aload_0        
        //   142: getfield        net/minecraft/entity/passive/EntityOcelot.worldObj:Lnet/minecraft/world/World;
        //   145: getfield        net/minecraft/world/World.isRemote:Z
        //   148: ifne            236
        //   151: aload_0        
        //   152: getfield        net/minecraft/entity/passive/EntityOcelot.rand:Ljava/util/Random;
        //   155: iconst_3       
        //   156: invokevirtual   java/util/Random.nextInt:(I)I
        //   159: ifne            221
        //   162: aload_0        
        //   163: iconst_1       
        //   164: invokevirtual   net/minecraft/entity/passive/EntityOcelot.setTamed:(Z)V
        //   167: aload_0        
        //   168: iconst_1       
        //   169: aload_0        
        //   170: getfield        net/minecraft/entity/passive/EntityOcelot.worldObj:Lnet/minecraft/world/World;
        //   173: getfield        net/minecraft/world/World.rand:Ljava/util/Random;
        //   176: iconst_3       
        //   177: invokevirtual   java/util/Random.nextInt:(I)I
        //   180: iadd           
        //   181: invokevirtual   net/minecraft/entity/passive/EntityOcelot.setTameSkin:(I)V
        //   184: aload_0        
        //   185: aload_1        
        //   186: invokevirtual   net/minecraft/entity/player/EntityPlayer.getUniqueID:()Ljava/util/UUID;
        //   189: invokevirtual   java/util/UUID.toString:()Ljava/lang/String;
        //   192: invokevirtual   net/minecraft/entity/passive/EntityOcelot.setOwnerId:(Ljava/lang/String;)V
        //   195: aload_0        
        //   196: iconst_1       
        //   197: invokevirtual   net/minecraft/entity/passive/EntityOcelot.playTameEffect:(Z)V
        //   200: aload_0        
        //   201: getfield        net/minecraft/entity/passive/EntityOcelot.aiSit:Lnet/minecraft/entity/ai/EntityAISit;
        //   204: iconst_1       
        //   205: invokevirtual   net/minecraft/entity/ai/EntityAISit.setSitting:(Z)V
        //   208: aload_0        
        //   209: getfield        net/minecraft/entity/passive/EntityOcelot.worldObj:Lnet/minecraft/world/World;
        //   212: aload_0        
        //   213: bipush          7
        //   215: invokevirtual   net/minecraft/world/World.setEntityState:(Lnet/minecraft/entity/Entity;B)V
        //   218: goto            236
        //   221: aload_0        
        //   222: iconst_0       
        //   223: invokevirtual   net/minecraft/entity/passive/EntityOcelot.playTameEffect:(Z)V
        //   226: aload_0        
        //   227: getfield        net/minecraft/entity/passive/EntityOcelot.worldObj:Lnet/minecraft/world/World;
        //   230: aload_0        
        //   231: bipush          6
        //   233: invokevirtual   net/minecraft/world/World.setEntityState:(Lnet/minecraft/entity/Entity;B)V
        //   236: iconst_1       
        //   237: ireturn        
        //   238: aload_0        
        //   239: aload_1        
        //   240: invokespecial   net/minecraft/entity/passive/EntityTameable.interact:(Lnet/minecraft/entity/player/EntityPlayer;)Z
        //   243: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0238 (coming from #0035).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public boolean getCanSpawnHere() {
        return this.worldObj.rand.nextInt(3) != 0;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(18, 0);
    }
    
    @Override
    public EntityOcelot createChild(final EntityAgeable entityAgeable) {
        final EntityOcelot entityOcelot = new EntityOcelot(this.worldObj);
        if (this.isTamed()) {
            entityOcelot.setOwnerId(this.getOwnerId());
            entityOcelot.setTamed(true);
            entityOcelot.setTameSkin(this.getTameSkin());
        }
        return entityOcelot;
    }
    
    @Override
    protected float getSoundVolume() {
        return 0.4f;
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("CatType", this.getTameSkin());
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        }
        this.aiSit.setSitting(false);
        return super.attackEntityFrom(damageSource, n);
    }
    
    public int getTameSkin() {
        return this.dataWatcher.getWatchableObjectByte(18);
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
    }
    
    @Override
    protected String getLivingSound() {
        return this.isTamed() ? (this.isInLove() ? "mob.cat.purr" : ((this.rand.nextInt(4) == 0) ? "mob.cat.purreow" : "mob.cat.meow")) : "";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.cat.hitt";
    }
    
    @Override
    public String getName() {
        return this.hasCustomName() ? this.getCustomNameTag() : (this.isTamed() ? "\u0567\u056c\u0576\u056b\u0576\u057b\u052c\u0541\u0563\u0576\u052c\u056c\u0563\u056f\u0567" : super.getName());
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.setTameSkin(nbtTagCompound.getInteger("CatType"));
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.cat.hitt";
    }
    
    public EntityOcelot(final World world) {
        super(world);
        this.setSize(0.6f, 0.7f);
        ((PathNavigateGround)this.getNavigator()).setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, this.aiTempt = new EntityAITempt(this, 0.6, Items.fish, true));
        this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0, 10.0f, 5.0f));
        this.tasks.addTask(6, new EntityAIOcelotSit(this, 0.8));
        this.tasks.addTask(7, new EntityAILeapAtTarget(this, 0.3f));
        this.tasks.addTask(8, new EntityAIOcelotAttack(this));
        this.tasks.addTask(9, new EntityAIMate(this, 0.8));
        this.tasks.addTask(10, new EntityAIWander(this, 0.8));
        this.tasks.addTask(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0f));
        this.targetTasks.addTask(1, new EntityAITargetNonTamed(this, EntityChicken.class, false, null));
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable entityAgeable) {
        return this.createChild(entityAgeable);
    }
    
    @Override
    protected boolean canDespawn() {
        return !this.isTamed() && this.ticksExisted > 2400;
    }
    
    @Override
    protected void setupTamedAI() {
        if (this.avoidEntity == null) {
            this.avoidEntity = new EntityAIAvoidEntity(this, EntityPlayer.class, 16.0f, 0.8, 1.33);
        }
        this.tasks.removeTask(this.avoidEntity);
        if (!this.isTamed()) {
            this.tasks.addTask(4, this.avoidEntity);
        }
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity entity) {
        return entity.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0f);
    }
    
    public void setTameSkin(final int n) {
        this.dataWatcher.updateObject(18, (byte)n);
    }
    
    @Override
    public boolean isNotColliding() {
        if (this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), (Entity)this) && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(this.getEntityBoundingBox())) {
            final BlockPos blockPos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);
            if (blockPos.getY() < this.worldObj.func_181545_F()) {
                return false;
            }
            final Block block = this.worldObj.getBlockState(blockPos.down()).getBlock();
            if (block == Blocks.grass || block.getMaterial() == Material.leaves) {
                return true;
            }
        }
        return false;
    }
}
