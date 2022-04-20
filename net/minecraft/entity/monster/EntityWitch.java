package net.minecraft.entity.monster;

import net.minecraft.item.*;
import net.minecraft.entity.ai.attributes.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;

public class EntityWitch extends EntityMob implements IRangedAttackMob
{
    private static final Item[] witchDrops;
    private static final AttributeModifier MODIFIER;
    private static final UUID MODIFIER_UUID;
    private int witchAttackTimer;
    
    @Override
    protected float applyPotionDamageCalculations(final DamageSource damageSource, float applyPotionDamageCalculations) {
        applyPotionDamageCalculations = super.applyPotionDamageCalculations(damageSource, applyPotionDamageCalculations);
        if (damageSource.getEntity() == this) {
            applyPotionDamageCalculations = 0.0f;
        }
        if (damageSource.isMagicDamage()) {
            applyPotionDamageCalculations *= (float)0.15;
        }
        return applyPotionDamageCalculations;
    }
    
    @Override
    public void handleStatusUpdate(final byte b) {
        if (b == 15) {
            while (0 < this.rand.nextInt(35) + 10) {
                this.worldObj.spawnParticle(EnumParticleTypes.SPELL_WITCH, this.posX + this.rand.nextGaussian() * 0.12999999523162842, this.getEntityBoundingBox().maxY + 0.5 + this.rand.nextGaussian() * 0.12999999523162842, this.posZ + this.rand.nextGaussian() * 0.12999999523162842, 0.0, 0.0, 0.0, new int[0]);
                int n = 0;
                ++n;
            }
        }
        else {
            super.handleStatusUpdate(b);
        }
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(26.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }
    
    static {
        MODIFIER_UUID = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
        MODIFIER = new AttributeModifier(EntityWitch.MODIFIER_UUID, "Drinking speed penalty", -0.25, 0).setSaved(false);
        witchDrops = new Item[] { Items.glowstone_dust, Items.sugar, Items.redstone, Items.spider_eye, Items.glass_bottle, Items.gunpowder, Items.stick, Items.stick };
    }
    
    @Override
    public void onLivingUpdate() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/entity/monster/EntityWitch.worldObj:Lnet/minecraft/world/World;
        //     4: getfield        net/minecraft/world/World.isRemote:Z
        //     7: ifne            412
        //    10: aload_0        
        //    11: if_icmpne       134
        //    14: aload_0        
        //    15: dup            
        //    16: getfield        net/minecraft/entity/monster/EntityWitch.witchAttackTimer:I
        //    19: dup_x1         
        //    20: iconst_1       
        //    21: isub           
        //    22: putfield        net/minecraft/entity/monster/EntityWitch.witchAttackTimer:I
        //    25: ifgt            388
        //    28: aload_0        
        //    29: iconst_0       
        //    30: invokevirtual   net/minecraft/entity/monster/EntityWitch.setAggressive:(Z)V
        //    33: aload_0        
        //    34: invokevirtual   net/minecraft/entity/monster/EntityWitch.getHeldItem:()Lnet/minecraft/item/ItemStack;
        //    37: astore_1       
        //    38: aload_0        
        //    39: iconst_0       
        //    40: aconst_null    
        //    41: checkcast       Lnet/minecraft/item/ItemStack;
        //    44: invokevirtual   net/minecraft/entity/monster/EntityWitch.setCurrentItemOrArmor:(ILnet/minecraft/item/ItemStack;)V
        //    47: aload_1        
        //    48: ifnull          116
        //    51: aload_1        
        //    52: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //    55: getstatic       net/minecraft/init/Items.potionitem:Lnet/minecraft/item/ItemPotion;
        //    58: if_acmpne       116
        //    61: getstatic       net/minecraft/init/Items.potionitem:Lnet/minecraft/item/ItemPotion;
        //    64: aload_1        
        //    65: invokevirtual   net/minecraft/item/ItemPotion.getEffects:(Lnet/minecraft/item/ItemStack;)Ljava/util/List;
        //    68: astore_2       
        //    69: aload_2        
        //    70: ifnull          116
        //    73: aload_2        
        //    74: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //    79: astore_3       
        //    80: aload_3        
        //    81: invokeinterface java/util/Iterator.hasNext:()Z
        //    86: ifeq            116
        //    89: aload_3        
        //    90: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    95: checkcast       Lnet/minecraft/potion/PotionEffect;
        //    98: astore          4
        //   100: aload_0        
        //   101: new             Lnet/minecraft/potion/PotionEffect;
        //   104: dup            
        //   105: aload           4
        //   107: invokespecial   net/minecraft/potion/PotionEffect.<init>:(Lnet/minecraft/potion/PotionEffect;)V
        //   110: invokevirtual   net/minecraft/entity/monster/EntityWitch.addPotionEffect:(Lnet/minecraft/potion/PotionEffect;)V
        //   113: goto            80
        //   116: aload_0        
        //   117: getstatic       net/minecraft/entity/SharedMonsterAttributes.movementSpeed:Lnet/minecraft/entity/ai/attributes/IAttribute;
        //   120: invokevirtual   net/minecraft/entity/monster/EntityWitch.getEntityAttribute:(Lnet/minecraft/entity/ai/attributes/IAttribute;)Lnet/minecraft/entity/ai/attributes/IAttributeInstance;
        //   123: getstatic       net/minecraft/entity/monster/EntityWitch.MODIFIER:Lnet/minecraft/entity/ai/attributes/AttributeModifier;
        //   126: invokeinterface net/minecraft/entity/ai/attributes/IAttributeInstance.removeModifier:(Lnet/minecraft/entity/ai/attributes/AttributeModifier;)V
        //   131: goto            388
        //   134: aload_0        
        //   135: getfield        net/minecraft/entity/monster/EntityWitch.rand:Ljava/util/Random;
        //   138: invokevirtual   java/util/Random.nextFloat:()F
        //   141: ldc             0.15
        //   143: fcmpg          
        //   144: ifge            170
        //   147: aload_0        
        //   148: getstatic       net/minecraft/block/material/Material.water:Lnet/minecraft/block/material/Material;
        //   151: invokevirtual   net/minecraft/entity/monster/EntityWitch.isInsideOfMaterial:(Lnet/minecraft/block/material/Material;)Z
        //   154: ifeq            170
        //   157: aload_0        
        //   158: getstatic       net/minecraft/potion/Potion.waterBreathing:Lnet/minecraft/potion/Potion;
        //   161: invokevirtual   net/minecraft/entity/monster/EntityWitch.isPotionActive:(Lnet/minecraft/potion/Potion;)Z
        //   164: ifne            170
        //   167: goto            327
        //   170: aload_0        
        //   171: getfield        net/minecraft/entity/monster/EntityWitch.rand:Ljava/util/Random;
        //   174: invokevirtual   java/util/Random.nextFloat:()F
        //   177: ldc             0.15
        //   179: fcmpg          
        //   180: ifge            203
        //   183: aload_0        
        //   184: invokevirtual   net/minecraft/entity/monster/EntityWitch.isBurning:()Z
        //   187: ifeq            203
        //   190: aload_0        
        //   191: getstatic       net/minecraft/potion/Potion.fireResistance:Lnet/minecraft/potion/Potion;
        //   194: invokevirtual   net/minecraft/entity/monster/EntityWitch.isPotionActive:(Lnet/minecraft/potion/Potion;)Z
        //   197: ifne            203
        //   200: goto            327
        //   203: aload_0        
        //   204: getfield        net/minecraft/entity/monster/EntityWitch.rand:Ljava/util/Random;
        //   207: invokevirtual   java/util/Random.nextFloat:()F
        //   210: ldc_w           0.05
        //   213: fcmpg          
        //   214: ifge            232
        //   217: aload_0        
        //   218: invokevirtual   net/minecraft/entity/monster/EntityWitch.getHealth:()F
        //   221: aload_0        
        //   222: invokevirtual   net/minecraft/entity/monster/EntityWitch.getMaxHealth:()F
        //   225: fcmpg          
        //   226: ifge            232
        //   229: goto            327
        //   232: aload_0        
        //   233: getfield        net/minecraft/entity/monster/EntityWitch.rand:Ljava/util/Random;
        //   236: invokevirtual   java/util/Random.nextFloat:()F
        //   239: ldc_w           0.25
        //   242: fcmpg          
        //   243: ifge            281
        //   246: aload_0        
        //   247: invokevirtual   net/minecraft/entity/monster/EntityWitch.getAttackTarget:()Lnet/minecraft/entity/EntityLivingBase;
        //   250: ifnull          281
        //   253: aload_0        
        //   254: getstatic       net/minecraft/potion/Potion.moveSpeed:Lnet/minecraft/potion/Potion;
        //   257: invokevirtual   net/minecraft/entity/monster/EntityWitch.isPotionActive:(Lnet/minecraft/potion/Potion;)Z
        //   260: ifne            281
        //   263: aload_0        
        //   264: invokevirtual   net/minecraft/entity/monster/EntityWitch.getAttackTarget:()Lnet/minecraft/entity/EntityLivingBase;
        //   267: aload_0        
        //   268: invokevirtual   net/minecraft/entity/EntityLivingBase.getDistanceSqToEntity:(Lnet/minecraft/entity/Entity;)D
        //   271: ldc2_w          121.0
        //   274: dcmpl          
        //   275: ifle            281
        //   278: goto            327
        //   281: aload_0        
        //   282: getfield        net/minecraft/entity/monster/EntityWitch.rand:Ljava/util/Random;
        //   285: invokevirtual   java/util/Random.nextFloat:()F
        //   288: ldc_w           0.25
        //   291: fcmpg          
        //   292: ifge            327
        //   295: aload_0        
        //   296: invokevirtual   net/minecraft/entity/monster/EntityWitch.getAttackTarget:()Lnet/minecraft/entity/EntityLivingBase;
        //   299: ifnull          327
        //   302: aload_0        
        //   303: getstatic       net/minecraft/potion/Potion.moveSpeed:Lnet/minecraft/potion/Potion;
        //   306: invokevirtual   net/minecraft/entity/monster/EntityWitch.isPotionActive:(Lnet/minecraft/potion/Potion;)Z
        //   309: ifne            327
        //   312: aload_0        
        //   313: invokevirtual   net/minecraft/entity/monster/EntityWitch.getAttackTarget:()Lnet/minecraft/entity/EntityLivingBase;
        //   316: aload_0        
        //   317: invokevirtual   net/minecraft/entity/EntityLivingBase.getDistanceSqToEntity:(Lnet/minecraft/entity/Entity;)D
        //   320: ldc2_w          121.0
        //   323: dcmpl          
        //   324: ifle            327
        //   327: aload_0        
        //   328: iconst_0       
        //   329: new             Lnet/minecraft/item/ItemStack;
        //   332: dup            
        //   333: getstatic       net/minecraft/init/Items.potionitem:Lnet/minecraft/item/ItemPotion;
        //   336: iconst_1       
        //   337: sipush          16274
        //   340: invokespecial   net/minecraft/item/ItemStack.<init>:(Lnet/minecraft/item/Item;II)V
        //   343: invokevirtual   net/minecraft/entity/monster/EntityWitch.setCurrentItemOrArmor:(ILnet/minecraft/item/ItemStack;)V
        //   346: aload_0        
        //   347: aload_0        
        //   348: invokevirtual   net/minecraft/entity/monster/EntityWitch.getHeldItem:()Lnet/minecraft/item/ItemStack;
        //   351: invokevirtual   net/minecraft/item/ItemStack.getMaxItemUseDuration:()I
        //   354: putfield        net/minecraft/entity/monster/EntityWitch.witchAttackTimer:I
        //   357: aload_0        
        //   358: iconst_1       
        //   359: invokevirtual   net/minecraft/entity/monster/EntityWitch.setAggressive:(Z)V
        //   362: aload_0        
        //   363: getstatic       net/minecraft/entity/SharedMonsterAttributes.movementSpeed:Lnet/minecraft/entity/ai/attributes/IAttribute;
        //   366: invokevirtual   net/minecraft/entity/monster/EntityWitch.getEntityAttribute:(Lnet/minecraft/entity/ai/attributes/IAttribute;)Lnet/minecraft/entity/ai/attributes/IAttributeInstance;
        //   369: astore_2       
        //   370: aload_2        
        //   371: getstatic       net/minecraft/entity/monster/EntityWitch.MODIFIER:Lnet/minecraft/entity/ai/attributes/AttributeModifier;
        //   374: invokeinterface net/minecraft/entity/ai/attributes/IAttributeInstance.removeModifier:(Lnet/minecraft/entity/ai/attributes/AttributeModifier;)V
        //   379: aload_2        
        //   380: getstatic       net/minecraft/entity/monster/EntityWitch.MODIFIER:Lnet/minecraft/entity/ai/attributes/AttributeModifier;
        //   383: invokeinterface net/minecraft/entity/ai/attributes/IAttributeInstance.applyModifier:(Lnet/minecraft/entity/ai/attributes/AttributeModifier;)V
        //   388: aload_0        
        //   389: getfield        net/minecraft/entity/monster/EntityWitch.rand:Ljava/util/Random;
        //   392: invokevirtual   java/util/Random.nextFloat:()F
        //   395: ldc_w           7.5E-4
        //   398: fcmpg          
        //   399: ifge            412
        //   402: aload_0        
        //   403: getfield        net/minecraft/entity/monster/EntityWitch.worldObj:Lnet/minecraft/world/World;
        //   406: aload_0        
        //   407: bipush          15
        //   409: invokevirtual   net/minecraft/world/World.setEntityState:(Lnet/minecraft/entity/Entity;B)V
        //   412: aload_0        
        //   413: invokespecial   net/minecraft/entity/monster/EntityMob.onLivingUpdate:()V
        //   416: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    protected String getLivingSound() {
        return null;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataWatcher().addObject(21, 0);
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        while (0 < this.rand.nextInt(3) + 1) {
            int nextInt = this.rand.nextInt(3);
            final Item item = EntityWitch.witchDrops[this.rand.nextInt(EntityWitch.witchDrops.length)];
            if (n > 0) {
                nextInt += this.rand.nextInt(n + 1);
            }
            while (0 < nextInt) {
                this.dropItem(item, 1);
                int n2 = 0;
                ++n2;
            }
            int n3 = 0;
            ++n3;
        }
    }
    
    @Override
    public void attackEntityWithRangedAttack(final EntityLivingBase p0, final float p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_icmpne       238
        //     4: new             Lnet/minecraft/entity/projectile/EntityPotion;
        //     7: dup            
        //     8: aload_0        
        //     9: getfield        net/minecraft/entity/monster/EntityWitch.worldObj:Lnet/minecraft/world/World;
        //    12: aload_0        
        //    13: sipush          32732
        //    16: invokespecial   net/minecraft/entity/projectile/EntityPotion.<init>:(Lnet/minecraft/world/World;Lnet/minecraft/entity/EntityLivingBase;I)V
        //    19: astore_3       
        //    20: aload_1        
        //    21: getfield        net/minecraft/entity/EntityLivingBase.posY:D
        //    24: aload_1        
        //    25: invokevirtual   net/minecraft/entity/EntityLivingBase.getEyeHeight:()F
        //    28: f2d            
        //    29: dadd           
        //    30: ldc2_w          1.100000023841858
        //    33: dsub           
        //    34: dstore          4
        //    36: aload_3        
        //    37: dup            
        //    38: getfield        net/minecraft/entity/projectile/EntityPotion.rotationPitch:F
        //    41: ldc_w           -20.0
        //    44: fsub           
        //    45: putfield        net/minecraft/entity/projectile/EntityPotion.rotationPitch:F
        //    48: aload_1        
        //    49: getfield        net/minecraft/entity/EntityLivingBase.posX:D
        //    52: aload_1        
        //    53: getfield        net/minecraft/entity/EntityLivingBase.motionX:D
        //    56: dadd           
        //    57: aload_0        
        //    58: getfield        net/minecraft/entity/monster/EntityWitch.posX:D
        //    61: dsub           
        //    62: dstore          6
        //    64: dload           4
        //    66: aload_0        
        //    67: getfield        net/minecraft/entity/monster/EntityWitch.posY:D
        //    70: dsub           
        //    71: dstore          8
        //    73: aload_1        
        //    74: getfield        net/minecraft/entity/EntityLivingBase.posZ:D
        //    77: aload_1        
        //    78: getfield        net/minecraft/entity/EntityLivingBase.motionZ:D
        //    81: dadd           
        //    82: aload_0        
        //    83: getfield        net/minecraft/entity/monster/EntityWitch.posZ:D
        //    86: dsub           
        //    87: dstore          10
        //    89: dload           6
        //    91: dload           6
        //    93: dmul           
        //    94: dload           10
        //    96: dload           10
        //    98: dmul           
        //    99: dadd           
        //   100: invokestatic    net/minecraft/util/MathHelper.sqrt_double:(D)F
        //   103: fstore          12
        //   105: fload           12
        //   107: ldc_w           8.0
        //   110: fcmpl          
        //   111: iflt            134
        //   114: aload_1        
        //   115: getstatic       net/minecraft/potion/Potion.moveSlowdown:Lnet/minecraft/potion/Potion;
        //   118: invokevirtual   net/minecraft/entity/EntityLivingBase.isPotionActive:(Lnet/minecraft/potion/Potion;)Z
        //   121: ifne            134
        //   124: aload_3        
        //   125: sipush          32698
        //   128: invokevirtual   net/minecraft/entity/projectile/EntityPotion.setPotionDamage:(I)V
        //   131: goto            205
        //   134: aload_1        
        //   135: invokevirtual   net/minecraft/entity/EntityLivingBase.getHealth:()F
        //   138: ldc_w           8.0
        //   141: fcmpl          
        //   142: iflt            165
        //   145: aload_1        
        //   146: getstatic       net/minecraft/potion/Potion.poison:Lnet/minecraft/potion/Potion;
        //   149: invokevirtual   net/minecraft/entity/EntityLivingBase.isPotionActive:(Lnet/minecraft/potion/Potion;)Z
        //   152: ifne            165
        //   155: aload_3        
        //   156: sipush          32660
        //   159: invokevirtual   net/minecraft/entity/projectile/EntityPotion.setPotionDamage:(I)V
        //   162: goto            205
        //   165: fload           12
        //   167: ldc_w           3.0
        //   170: fcmpg          
        //   171: ifgt            205
        //   174: aload_1        
        //   175: getstatic       net/minecraft/potion/Potion.weakness:Lnet/minecraft/potion/Potion;
        //   178: invokevirtual   net/minecraft/entity/EntityLivingBase.isPotionActive:(Lnet/minecraft/potion/Potion;)Z
        //   181: ifne            205
        //   184: aload_0        
        //   185: getfield        net/minecraft/entity/monster/EntityWitch.rand:Ljava/util/Random;
        //   188: invokevirtual   java/util/Random.nextFloat:()F
        //   191: ldc_w           0.25
        //   194: fcmpg          
        //   195: ifge            205
        //   198: aload_3        
        //   199: sipush          32696
        //   202: invokevirtual   net/minecraft/entity/projectile/EntityPotion.setPotionDamage:(I)V
        //   205: aload_3        
        //   206: dload           6
        //   208: dload           8
        //   210: fload           12
        //   212: ldc_w           0.2
        //   215: fmul           
        //   216: f2d            
        //   217: dadd           
        //   218: dload           10
        //   220: ldc_w           0.75
        //   223: ldc_w           8.0
        //   226: invokevirtual   net/minecraft/entity/projectile/EntityPotion.setThrowableHeading:(DDDFF)V
        //   229: aload_0        
        //   230: getfield        net/minecraft/entity/monster/EntityWitch.worldObj:Lnet/minecraft/world/World;
        //   233: aload_3        
        //   234: invokevirtual   net/minecraft/world/World.spawnEntityInWorld:(Lnet/minecraft/entity/Entity;)Z
        //   237: pop            
        //   238: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void setAggressive(final boolean b) {
        this.getDataWatcher().updateObject(21, (byte)(byte)(b ? 1 : 0));
    }
    
    public EntityWitch(final World world) {
        super(world);
        this.setSize(0.6f, 1.95f);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIArrowAttack(this, 1.0, 60, 10.0f));
        this.tasks.addTask(2, new EntityAIWander(this, 1.0));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(3, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }
    
    @Override
    public float getEyeHeight() {
        return 1.62f;
    }
    
    @Override
    protected String getDeathSound() {
        return null;
    }
    
    @Override
    protected String getHurtSound() {
        return null;
    }
}
