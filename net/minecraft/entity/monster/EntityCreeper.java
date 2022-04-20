package net.minecraft.entity.monster;

import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.init.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;

public class EntityCreeper extends EntityMob
{
    private int fuseTime;
    private int explosionRadius;
    private int field_175494_bm;
    private int timeSinceIgnited;
    private int lastActiveTime;
    
    public int getCreeperState() {
        return this.dataWatcher.getWatchableObjectByte(16);
    }
    
    public void func_175493_co() {
        ++this.field_175494_bm;
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.creeper.say";
    }
    
    @Override
    public int getMaxFallHeight() {
        return (this.getAttackTarget() == null) ? 3 : (3 + (int)(this.getHealth() - 1.0f));
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        if (this.dataWatcher.getWatchableObjectByte(17) == 1) {
            nbtTagCompound.setBoolean("powered", true);
        }
        nbtTagCompound.setShort("Fuse", (short)this.fuseTime);
        nbtTagCompound.setByte("ExplosionRadius", (byte)this.explosionRadius);
        nbtTagCompound.setBoolean("ignited", this.hasIgnited());
    }
    
    @Override
    public void onUpdate() {
        if (this.isEntityAlive()) {
            this.lastActiveTime = this.timeSinceIgnited;
            if (this != 0) {
                this.setCreeperState(1);
            }
            final int creeperState = this.getCreeperState();
            if (creeperState > 0 && this.timeSinceIgnited == 0) {
                this.playSound("creeper.primed", 1.0f, 0.5f);
            }
            this.timeSinceIgnited += creeperState;
            if (this.timeSinceIgnited < 0) {
                this.timeSinceIgnited = 0;
            }
            if (this.timeSinceIgnited >= this.fuseTime) {
                this.timeSinceIgnited = this.fuseTime;
                this.explode();
            }
        }
        super.onUpdate();
    }
    
    @Override
    public void onDeath(final DamageSource p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1        
        //     2: invokespecial   net/minecraft/entity/monster/EntityMob.onDeath:(Lnet/minecraft/util/DamageSource;)V
        //     5: aload_1        
        //     6: invokevirtual   net/minecraft/util/DamageSource.getEntity:()Lnet/minecraft/entity/Entity;
        //     9: instanceof      Lnet/minecraft/entity/monster/EntitySkeleton;
        //    12: ifeq            59
        //    15: getstatic       net/minecraft/init/Items.record_13:Lnet/minecraft/item/Item;
        //    18: invokestatic    net/minecraft/item/Item.getIdFromItem:(Lnet/minecraft/item/Item;)I
        //    21: istore_2       
        //    22: getstatic       net/minecraft/init/Items.record_wait:Lnet/minecraft/item/Item;
        //    25: invokestatic    net/minecraft/item/Item.getIdFromItem:(Lnet/minecraft/item/Item;)I
        //    28: istore_3       
        //    29: iload_2        
        //    30: aload_0        
        //    31: getfield        net/minecraft/entity/monster/EntityCreeper.rand:Ljava/util/Random;
        //    34: iload_3        
        //    35: iload_2        
        //    36: isub           
        //    37: iconst_1       
        //    38: iadd           
        //    39: invokevirtual   java/util/Random.nextInt:(I)I
        //    42: iadd           
        //    43: istore          4
        //    45: aload_0        
        //    46: iload           4
        //    48: invokestatic    net/minecraft/item/Item.getItemById:(I)Lnet/minecraft/item/Item;
        //    51: iconst_1       
        //    52: invokevirtual   net/minecraft/entity/monster/EntityCreeper.dropItem:(Lnet/minecraft/item/Item;I)Lnet/minecraft/entity/item/EntityItem;
        //    55: pop            
        //    56: goto            125
        //    59: aload_1        
        //    60: invokevirtual   net/minecraft/util/DamageSource.getEntity:()Lnet/minecraft/entity/Entity;
        //    63: instanceof      Lnet/minecraft/entity/monster/EntityCreeper;
        //    66: ifeq            125
        //    69: aload_1        
        //    70: invokevirtual   net/minecraft/util/DamageSource.getEntity:()Lnet/minecraft/entity/Entity;
        //    73: aload_0        
        //    74: if_acmpeq       125
        //    77: aload_1        
        //    78: invokevirtual   net/minecraft/util/DamageSource.getEntity:()Lnet/minecraft/entity/Entity;
        //    81: checkcast       Lnet/minecraft/entity/monster/EntityCreeper;
        //    84: if_icmpne       125
        //    87: aload_1        
        //    88: invokevirtual   net/minecraft/util/DamageSource.getEntity:()Lnet/minecraft/entity/Entity;
        //    91: checkcast       Lnet/minecraft/entity/monster/EntityCreeper;
        //    94: if_icmpge       125
        //    97: aload_1        
        //    98: invokevirtual   net/minecraft/util/DamageSource.getEntity:()Lnet/minecraft/entity/Entity;
        //   101: checkcast       Lnet/minecraft/entity/monster/EntityCreeper;
        //   104: invokevirtual   net/minecraft/entity/monster/EntityCreeper.func_175493_co:()V
        //   107: aload_0        
        //   108: new             Lnet/minecraft/item/ItemStack;
        //   111: dup            
        //   112: getstatic       net/minecraft/init/Items.skull:Lnet/minecraft/item/Item;
        //   115: iconst_1       
        //   116: iconst_4       
        //   117: invokespecial   net/minecraft/item/ItemStack.<init>:(Lnet/minecraft/item/Item;II)V
        //   120: fconst_0       
        //   121: invokevirtual   net/minecraft/entity/monster/EntityCreeper.entityDropItem:(Lnet/minecraft/item/ItemStack;F)Lnet/minecraft/entity/item/EntityItem;
        //   124: pop            
        //   125: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.dataWatcher.updateObject(17, (byte)(byte)(nbtTagCompound.getBoolean("powered") ? 1 : 0));
        if (nbtTagCompound.hasKey("Fuse", 99)) {
            this.fuseTime = nbtTagCompound.getShort("Fuse");
        }
        if (nbtTagCompound.hasKey("ExplosionRadius", 99)) {
            this.explosionRadius = nbtTagCompound.getByte("ExplosionRadius");
        }
        if (nbtTagCompound.getBoolean("ignited")) {
            this.ignite();
        }
    }
    
    public void setCreeperState(final int n) {
        this.dataWatcher.updateObject(16, (byte)n);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, -1);
        this.dataWatcher.addObject(17, 0);
        this.dataWatcher.addObject(18, 0);
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity entity) {
        return true;
    }
    
    public void ignite() {
        this.dataWatcher.updateObject(18, 1);
    }
    
    public EntityCreeper(final World world) {
        super(world);
        this.fuseTime = 30;
        this.explosionRadius = 3;
        this.field_175494_bm = 0;
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAICreeperSwell(this));
        this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0f, 1.0, 1.2));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0, false));
        this.tasks.addTask(5, new EntityAIWander(this, 0.8));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }
    
    public float getCreeperFlashIntensity(final float n) {
        return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * n) / (this.fuseTime - 2);
    }
    
    @Override
    protected Item getDropItem() {
        return Items.gunpowder;
    }
    
    @Override
    public void onStruckByLightning(final EntityLightningBolt entityLightningBolt) {
        super.onStruckByLightning(entityLightningBolt);
        this.dataWatcher.updateObject(17, 1);
    }
    
    @Override
    protected boolean interact(final EntityPlayer entityPlayer) {
        final ItemStack currentItem = entityPlayer.inventory.getCurrentItem();
        if (currentItem != null && currentItem.getItem() == Items.flint_and_steel) {
            this.worldObj.playSoundEffect(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5, "fire.ignite", 1.0f, this.rand.nextFloat() * 0.4f + 0.8f);
            entityPlayer.swingItem();
            if (!this.worldObj.isRemote) {
                this.ignite();
                currentItem.damageItem(1, entityPlayer);
                return true;
            }
        }
        return super.interact(entityPlayer);
    }
    
    @Override
    public void fall(final float n, final float n2) {
        super.fall(n, n2);
        this.timeSinceIgnited += (int)(n * 1.5f);
        if (this.timeSinceIgnited > this.fuseTime - 5) {
            this.timeSinceIgnited = this.fuseTime - 5;
        }
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.creeper.death";
    }
    
    private void explode() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/entity/monster/EntityCreeper.worldObj:Lnet/minecraft/world/World;
        //     4: getfield        net/minecraft/world/World.isRemote:Z
        //     7: ifne            67
        //    10: aload_0        
        //    11: getfield        net/minecraft/entity/monster/EntityCreeper.worldObj:Lnet/minecraft/world/World;
        //    14: invokevirtual   net/minecraft/world/World.getGameRules:()Lnet/minecraft/world/GameRules;
        //    17: ldc_w           "mobGriefing"
        //    20: invokevirtual   net/minecraft/world/GameRules.getBoolean:(Ljava/lang/String;)Z
        //    23: istore_1       
        //    24: aload_0        
        //    25: if_icmpne       32
        //    28: fconst_2       
        //    29: goto            33
        //    32: fconst_1       
        //    33: fstore_2       
        //    34: aload_0        
        //    35: getfield        net/minecraft/entity/monster/EntityCreeper.worldObj:Lnet/minecraft/world/World;
        //    38: aload_0        
        //    39: aload_0        
        //    40: getfield        net/minecraft/entity/monster/EntityCreeper.posX:D
        //    43: aload_0        
        //    44: getfield        net/minecraft/entity/monster/EntityCreeper.posY:D
        //    47: aload_0        
        //    48: getfield        net/minecraft/entity/monster/EntityCreeper.posZ:D
        //    51: aload_0        
        //    52: getfield        net/minecraft/entity/monster/EntityCreeper.explosionRadius:I
        //    55: i2f            
        //    56: fload_2        
        //    57: fmul           
        //    58: iload_1        
        //    59: invokevirtual   net/minecraft/world/World.createExplosion:(Lnet/minecraft/entity/Entity;DDDFZ)Lnet/minecraft/world/Explosion;
        //    62: pop            
        //    63: aload_0        
        //    64: invokevirtual   net/minecraft/entity/monster/EntityCreeper.setDead:()V
        //    67: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
