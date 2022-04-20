package net.minecraft.entity.passive;

import com.google.common.base.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import net.minecraft.block.*;
import net.minecraft.inventory.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import java.util.*;

public class EntityHorse extends EntityAnimal implements IInvBasic
{
    private static final String[] HORSE_MARKING_TEXTURES_ABBR;
    private int gallopTime;
    protected int temper;
    private float prevMouthOpenness;
    private float mouthOpenness;
    private float headLean;
    private float rearingAmount;
    private static final Predicate horseBreedingSelector;
    private String texturePrefix;
    private boolean field_175508_bO;
    private static final String[] horseMarkingTextures;
    private int openMouthCounter;
    protected float jumpPower;
    private int jumpRearingCounter;
    private float prevHeadLean;
    public int field_110278_bp;
    private static final String[] HORSE_ARMOR_TEXTURES_ABBR;
    private static final String[] HORSE_TEXTURES_ABBR;
    public int field_110279_bq;
    private AnimalChest horseChest;
    private float prevRearingAmount;
    private String[] horseTexturesArray;
    private static final String[] horseTextures;
    private int eatingHaystackCounter;
    private boolean field_110294_bI;
    private static final IAttribute horseJumpStrength;
    protected boolean horseJumping;
    private boolean hasReproduced;
    
    public boolean func_175507_cI() {
        return this.field_175508_bO;
    }
    
    public boolean isRearing() {
        return this.getHorseWatchableBoolean(64);
    }
    
    private double getModifiedJumpStrength() {
        return 0.4000000059604645 + this.rand.nextDouble() * 0.2 + this.rand.nextDouble() * 0.2 + this.rand.nextDouble() * 0.2;
    }
    
    @Override
    protected String getDeathSound() {
        this.openHorseMouth();
        final int horseType = this.getHorseType();
        return (horseType == 3) ? "mob.horse.zombie.death" : ((horseType == 4) ? "mob.horse.skeleton.death" : ((horseType != 1 && horseType != 2) ? "mob.horse.death" : "mob.horse.donkey.death"));
    }
    
    @Override
    public int getMaxSpawnedInChunk() {
        return 6;
    }
    
    private void func_110210_cH() {
        this.field_110278_bp = 1;
    }
    
    @Override
    public void updateRiderPosition() {
        super.updateRiderPosition();
        if (this.prevRearingAmount > 0.0f) {
            final float sin = MathHelper.sin(this.renderYawOffset * 3.1415927f / 180.0f);
            final float cos = MathHelper.cos(this.renderYawOffset * 3.1415927f / 180.0f);
            final float n = 0.7f * this.prevRearingAmount;
            this.riddenByEntity.setPosition(this.posX + n * sin, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset() + 0.15f * this.prevRearingAmount, this.posZ - n * cos);
            if (this.riddenByEntity instanceof EntityLivingBase) {
                ((EntityLivingBase)this.riddenByEntity).renderYawOffset = this.renderYawOffset;
            }
        }
    }
    
    private void dropItemsInChest(final Entity entity, final AnimalChest animalChest) {
        if (animalChest != null && !this.worldObj.isRemote) {
            while (0 < animalChest.getSizeInventory()) {
                final ItemStack stackInSlot = animalChest.getStackInSlot(0);
                if (stackInSlot != null) {
                    this.entityDropItem(stackInSlot, 0.0f);
                }
                int n = 0;
                ++n;
            }
        }
    }
    
    @Override
    public void setEating(final boolean b) {
        this.setHorseWatchableBoolean(32, b);
    }
    
    public double getHorseJumpStrength() {
        return this.getEntityAttribute(EntityHorse.horseJumpStrength).getAttributeValue();
    }
    
    public int getHorseArmorIndexSynced() {
        return this.dataWatcher.getWatchableObjectInt(22);
    }
    
    public EntityHorse(final World world) {
        super(world);
        this.horseTexturesArray = new String[3];
        this.field_175508_bO = false;
        this.setSize(1.4f, 1.6f);
        this.setChested(this.isImmuneToFire = false);
        ((PathNavigateGround)this.getNavigator()).setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.2));
        this.tasks.addTask(1, new EntityAIRunAroundLikeCrazy(this, 1.2));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.0));
        this.tasks.addTask(6, new EntityAIWander(this, 0.7));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.initHorseChest();
    }
    
    public boolean func_110253_bW() {
        return this.isAdultHorse();
    }
    
    private void makeHorseRear() {
        if (!this.worldObj.isRemote) {
            this.jumpRearingCounter = 1;
            this.setRearing(true);
        }
    }
    
    public void setBreeding(final boolean b) {
        this.setHorseWatchableBoolean(16, b);
    }
    
    private void openHorseMouth() {
        if (!this.worldObj.isRemote) {
            this.openMouthCounter = 1;
            this.setHorseWatchableBoolean(128, true);
        }
    }
    
    public void setHorseTamed(final boolean b) {
        this.setHorseWatchableBoolean(2, b);
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        final Entity entity = damageSource.getEntity();
        return (this.riddenByEntity == null || !this.riddenByEntity.equals(entity)) && super.attackEntityFrom(damageSource, n);
    }
    
    public int increaseTemper(final int n) {
        final int clamp_int = MathHelper.clamp_int(this.getTemper() + n, 0, this.getMaxTemper());
        this.setTemper(clamp_int);
        return clamp_int;
    }
    
    public int getHorseVariant() {
        return this.dataWatcher.getWatchableObjectInt(20);
    }
    
    public boolean isTame() {
        return this.getHorseWatchableBoolean(2);
    }
    
    @Override
    protected float getSoundVolume() {
        return 0.8f;
    }
    
    public String getOwnerId() {
        return this.dataWatcher.getWatchableObjectString(21);
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.rand.nextInt(200) == 0) {
            this.func_110210_cH();
        }
        super.onLivingUpdate();
        if (!this.worldObj.isRemote) {
            if (this.rand.nextInt(900) == 0 && this.deathTime == 0) {
                this.heal(1.0f);
            }
            if (!this.isEatingHaystack() && this.riddenByEntity == null && this.rand.nextInt(300) == 0 && this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY) - 1, MathHelper.floor_double(this.posZ))).getBlock() == Blocks.grass) {
                this.setEatingHaystack(true);
            }
            if (this.isEatingHaystack() && ++this.eatingHaystackCounter > 50) {
                this.eatingHaystackCounter = 0;
                this.setEatingHaystack(false);
            }
            if (this.isBreeding() && this == 0 && !this.isEatingHaystack()) {
                final EntityHorse closestHorse = this.getClosestHorse(this, 16.0);
                if (closestHorse != null && this.getDistanceSqToEntity(closestHorse) > 4.0) {
                    this.navigator.getPathToEntityLiving(closestHorse);
                }
            }
        }
    }
    
    private void initHorseChest() {
        final AnimalChest horseChest = this.horseChest;
        (this.horseChest = new AnimalChest("HorseChest", this.getChestSize())).setCustomName(this.getName());
        if (horseChest != null) {
            horseChest.func_110132_b(this);
            while (0 < Math.min(horseChest.getSizeInventory(), this.horseChest.getSizeInventory())) {
                final ItemStack stackInSlot = horseChest.getStackInSlot(0);
                if (stackInSlot != null) {
                    this.horseChest.setInventorySlotContents(0, stackInSlot.copy());
                }
                int n = 0;
                ++n;
            }
        }
        this.horseChest.func_110134_a(this);
        this.updateHorseSlots();
    }
    
    public boolean allowLeashing() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_icmpeq       15
        //     4: aload_0        
        //     5: invokespecial   net/minecraft/entity/passive/EntityAnimal.allowLeashing:()Z
        //     8: ifeq            15
        //    11: iconst_1       
        //    12: goto            16
        //    15: iconst_0       
        //    16: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public int getHorseType() {
        return this.dataWatcher.getWatchableObjectByte(19);
    }
    
    private void mountTo(final EntityPlayer entityPlayer) {
        entityPlayer.rotationYaw = this.rotationYaw;
        entityPlayer.rotationPitch = this.rotationPitch;
        this.setEatingHaystack(false);
        this.setRearing(false);
        if (!this.worldObj.isRemote) {
            entityPlayer.mountEntity(this);
        }
    }
    
    public void setHorseArmorStack(final ItemStack itemStack) {
        this.dataWatcher.updateObject(22, this.getHorseArmorIndex(itemStack));
        this.resetTexturePrefix();
    }
    
    public void setTemper(final int temper) {
        this.temper = temper;
    }
    
    @Override
    public String getName() {
        if (this.hasCustomName()) {
            return this.getCustomNameTag();
        }
        switch (this.getHorseType()) {
            default: {
                return "\u7cbe\u7cb5\u7caf\u7cb2\u7caf\u7ca2\u7cf5\u7cb3\u7cb4\u7ca9\u7ca8\u7cbe\u7cf5\u7cb5\u7cba\u7cb6\u7cbe";
            }
            case 1: {
                return "\u7cbe\u7cb5\u7caf\u7cb2\u7caf\u7ca2\u7cf5\u7cbf\u7cb4\u7cb5\u7cb0\u7cbe\u7ca2\u7cf5\u7cb5\u7cba\u7cb6\u7cbe";
            }
            case 2: {
                return "\u7cbe\u7cb5\u7caf\u7cb2\u7caf\u7ca2\u7cf5\u7cb6\u7cae\u7cb7\u7cbe\u7cf5\u7cb5\u7cba\u7cb6\u7cbe";
            }
            case 3: {
                return "\u7cbe\u7cb5\u7caf\u7cb2\u7caf\u7ca2\u7cf5\u7ca1\u7cb4\u7cb6\u7cb9\u7cb2\u7cbe\u7cb3\u7cb4\u7ca9\u7ca8\u7cbe\u7cf5\u7cb5\u7cba\u7cb6\u7cbe";
            }
            case 4: {
                return "\u7cbe\u7cb5\u7caf\u7cb2\u7caf\u7ca2\u7cf5\u7ca8\u7cb0\u7cbe\u7cb7\u7cbe\u7caf\u7cb4\u7cb5\u7cb3\u7cb4\u7ca9\u7ca8\u7cbe\u7cf5\u7cb5\u7cba\u7cb6\u7cbe";
            }
        }
    }
    
    public void setOwnerId(final String s) {
        this.dataWatcher.updateObject(21, s);
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
        //     8: aload_2        
        //     9: ifnull          28
        //    12: aload_2        
        //    13: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //    16: getstatic       net/minecraft/init/Items.spawn_egg:Lnet/minecraft/item/Item;
        //    19: if_acmpne       28
        //    22: aload_0        
        //    23: aload_1        
        //    24: invokespecial   net/minecraft/entity/passive/EntityAnimal.interact:(Lnet/minecraft/entity/player/EntityPlayer;)Z
        //    27: ireturn        
        //    28: aload_0        
        //    29: invokevirtual   net/minecraft/entity/passive/EntityHorse.isTame:()Z
        //    32: ifne            41
        //    35: aload_0        
        //    36: if_icmpeq       41
        //    39: iconst_0       
        //    40: ireturn        
        //    41: aload_0        
        //    42: invokevirtual   net/minecraft/entity/passive/EntityHorse.isTame:()Z
        //    45: ifeq            66
        //    48: aload_0        
        //    49: ifne            66
        //    52: aload_1        
        //    53: invokevirtual   net/minecraft/entity/player/EntityPlayer.isSneaking:()Z
        //    56: ifeq            66
        //    59: aload_0        
        //    60: aload_1        
        //    61: invokevirtual   net/minecraft/entity/passive/EntityHorse.openGUI:(Lnet/minecraft/entity/player/EntityPlayer;)V
        //    64: iconst_1       
        //    65: ireturn        
        //    66: aload_0        
        //    67: invokevirtual   net/minecraft/entity/passive/EntityHorse.func_110253_bW:()Z
        //    70: ifeq            86
        //    73: aload_0        
        //    74: getfield        net/minecraft/entity/passive/EntityHorse.riddenByEntity:Lnet/minecraft/entity/Entity;
        //    77: ifnull          86
        //    80: aload_0        
        //    81: aload_1        
        //    82: invokespecial   net/minecraft/entity/passive/EntityAnimal.interact:(Lnet/minecraft/entity/player/EntityPlayer;)Z
        //    85: ireturn        
        //    86: aload_2        
        //    87: ifnull          542
        //    90: aload_0        
        //    91: ifne            150
        //    94: aload_2        
        //    95: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //    98: getstatic       net/minecraft/init/Items.iron_horse_armor:Lnet/minecraft/item/Item;
        //   101: if_acmpne       107
        //   104: goto            130
        //   107: aload_2        
        //   108: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   111: getstatic       net/minecraft/init/Items.golden_horse_armor:Lnet/minecraft/item/Item;
        //   114: if_acmpne       120
        //   117: goto            130
        //   120: aload_2        
        //   121: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   124: getstatic       net/minecraft/init/Items.diamond_horse_armor:Lnet/minecraft/item/Item;
        //   127: if_acmpne       130
        //   130: aload_0        
        //   131: invokevirtual   net/minecraft/entity/passive/EntityHorse.isTame:()Z
        //   134: ifne            143
        //   137: aload_0        
        //   138: invokevirtual   net/minecraft/entity/passive/EntityHorse.makeHorseRearWithSound:()V
        //   141: iconst_1       
        //   142: ireturn        
        //   143: aload_0        
        //   144: aload_1        
        //   145: invokevirtual   net/minecraft/entity/passive/EntityHorse.openGUI:(Lnet/minecraft/entity/player/EntityPlayer;)V
        //   148: iconst_1       
        //   149: ireturn        
        //   150: goto            368
        //   153: aload_0        
        //   154: if_icmpeq       368
        //   157: fconst_0       
        //   158: fstore          4
        //   160: aload_2        
        //   161: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   164: getstatic       net/minecraft/init/Items.wheat:Lnet/minecraft/item/Item;
        //   167: if_acmpne       176
        //   170: fconst_2       
        //   171: fstore          4
        //   173: goto            302
        //   176: aload_2        
        //   177: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   180: getstatic       net/minecraft/init/Items.sugar:Lnet/minecraft/item/Item;
        //   183: if_acmpne       192
        //   186: fconst_1       
        //   187: fstore          4
        //   189: goto            302
        //   192: aload_2        
        //   193: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   196: invokestatic    net/minecraft/block/Block.getBlockFromItem:(Lnet/minecraft/item/Item;)Lnet/minecraft/block/Block;
        //   199: getstatic       net/minecraft/init/Blocks.hay_block:Lnet/minecraft/block/Block;
        //   202: if_acmpne       213
        //   205: ldc_w           20.0
        //   208: fstore          4
        //   210: goto            302
        //   213: aload_2        
        //   214: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   217: getstatic       net/minecraft/init/Items.apple:Lnet/minecraft/item/Item;
        //   220: if_acmpne       231
        //   223: ldc_w           3.0
        //   226: fstore          4
        //   228: goto            302
        //   231: aload_2        
        //   232: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   235: getstatic       net/minecraft/init/Items.golden_carrot:Lnet/minecraft/item/Item;
        //   238: if_acmpne       268
        //   241: ldc_w           4.0
        //   244: fstore          4
        //   246: aload_0        
        //   247: invokevirtual   net/minecraft/entity/passive/EntityHorse.isTame:()Z
        //   250: ifeq            302
        //   253: aload_0        
        //   254: invokevirtual   net/minecraft/entity/passive/EntityHorse.getGrowingAge:()I
        //   257: ifne            302
        //   260: aload_0        
        //   261: aload_1        
        //   262: invokevirtual   net/minecraft/entity/passive/EntityHorse.setInLove:(Lnet/minecraft/entity/player/EntityPlayer;)V
        //   265: goto            302
        //   268: aload_2        
        //   269: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   272: getstatic       net/minecraft/init/Items.golden_apple:Lnet/minecraft/item/Item;
        //   275: if_acmpne       302
        //   278: ldc_w           10.0
        //   281: fstore          4
        //   283: aload_0        
        //   284: invokevirtual   net/minecraft/entity/passive/EntityHorse.isTame:()Z
        //   287: ifeq            302
        //   290: aload_0        
        //   291: invokevirtual   net/minecraft/entity/passive/EntityHorse.getGrowingAge:()I
        //   294: ifne            302
        //   297: aload_0        
        //   298: aload_1        
        //   299: invokevirtual   net/minecraft/entity/passive/EntityHorse.setInLove:(Lnet/minecraft/entity/player/EntityPlayer;)V
        //   302: aload_0        
        //   303: invokevirtual   net/minecraft/entity/passive/EntityHorse.getHealth:()F
        //   306: aload_0        
        //   307: invokevirtual   net/minecraft/entity/passive/EntityHorse.getMaxHealth:()F
        //   310: fcmpg          
        //   311: ifge            327
        //   314: fload           4
        //   316: fconst_0       
        //   317: fcmpl          
        //   318: ifle            327
        //   321: aload_0        
        //   322: fload           4
        //   324: invokevirtual   net/minecraft/entity/passive/EntityHorse.heal:(F)V
        //   327: aload_0        
        //   328: ifne            338
        //   331: aload_0        
        //   332: sipush          240
        //   335: invokevirtual   net/minecraft/entity/passive/EntityHorse.addGrowth:(I)V
        //   338: goto            348
        //   341: aload_0        
        //   342: invokevirtual   net/minecraft/entity/passive/EntityHorse.isTame:()Z
        //   345: ifne            364
        //   348: bipush          10
        //   350: aload_0        
        //   351: invokevirtual   net/minecraft/entity/passive/EntityHorse.getMaxTemper:()I
        //   354: if_icmpge       364
        //   357: aload_0        
        //   358: bipush          10
        //   360: invokevirtual   net/minecraft/entity/passive/EntityHorse.increaseTemper:(I)I
        //   363: pop            
        //   364: aload_0        
        //   365: invokespecial   net/minecraft/entity/passive/EntityHorse.func_110266_cB:()V
        //   368: aload_0        
        //   369: invokevirtual   net/minecraft/entity/passive/EntityHorse.isTame:()Z
        //   372: ifne            399
        //   375: goto            464
        //   378: aload_2        
        //   379: ifnull          393
        //   382: aload_2        
        //   383: aload_1        
        //   384: aload_0        
        //   385: invokevirtual   net/minecraft/item/ItemStack.interactWithEntity:(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/entity/EntityLivingBase;)Z
        //   388: ifeq            393
        //   391: iconst_1       
        //   392: ireturn        
        //   393: aload_0        
        //   394: invokevirtual   net/minecraft/entity/passive/EntityHorse.makeHorseRearWithSound:()V
        //   397: iconst_1       
        //   398: ireturn        
        //   399: goto            498
        //   402: aload_0        
        //   403: if_icmpeq       464
        //   406: aload_0        
        //   407: invokevirtual   net/minecraft/entity/passive/EntityHorse.isChested:()Z
        //   410: ifne            464
        //   413: aload_2        
        //   414: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   417: getstatic       net/minecraft/init/Blocks.chest:Lnet/minecraft/block/BlockChest;
        //   420: invokestatic    net/minecraft/item/Item.getItemFromBlock:(Lnet/minecraft/block/Block;)Lnet/minecraft/item/Item;
        //   423: if_acmpne       464
        //   426: aload_0        
        //   427: iconst_1       
        //   428: invokevirtual   net/minecraft/entity/passive/EntityHorse.setChested:(Z)V
        //   431: aload_0        
        //   432: ldc_w           "mob.chickenplop"
        //   435: fconst_1       
        //   436: aload_0        
        //   437: getfield        net/minecraft/entity/passive/EntityHorse.rand:Ljava/util/Random;
        //   440: invokevirtual   java/util/Random.nextFloat:()F
        //   443: aload_0        
        //   444: getfield        net/minecraft/entity/passive/EntityHorse.rand:Ljava/util/Random;
        //   447: invokevirtual   java/util/Random.nextFloat:()F
        //   450: fsub           
        //   451: ldc_w           0.2
        //   454: fmul           
        //   455: fconst_1       
        //   456: fadd           
        //   457: invokevirtual   net/minecraft/entity/passive/EntityHorse.playSound:(Ljava/lang/String;FF)V
        //   460: aload_0        
        //   461: invokespecial   net/minecraft/entity/passive/EntityHorse.initHorseChest:()V
        //   464: goto            498
        //   467: aload_0        
        //   468: invokevirtual   net/minecraft/entity/passive/EntityHorse.func_110253_bW:()Z
        //   471: ifeq            498
        //   474: aload_0        
        //   475: invokevirtual   net/minecraft/entity/passive/EntityHorse.isHorseSaddled:()Z
        //   478: ifne            498
        //   481: aload_2        
        //   482: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   485: getstatic       net/minecraft/init/Items.saddle:Lnet/minecraft/item/Item;
        //   488: if_acmpne       498
        //   491: aload_0        
        //   492: aload_1        
        //   493: invokevirtual   net/minecraft/entity/passive/EntityHorse.openGUI:(Lnet/minecraft/entity/player/EntityPlayer;)V
        //   496: iconst_1       
        //   497: ireturn        
        //   498: aload_1        
        //   499: getfield        net/minecraft/entity/player/EntityPlayer.capabilities:Lnet/minecraft/entity/player/PlayerCapabilities;
        //   502: getfield        net/minecraft/entity/player/PlayerCapabilities.isCreativeMode:Z
        //   505: ifne            540
        //   508: aload_2        
        //   509: dup            
        //   510: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   513: iconst_1       
        //   514: isub           
        //   515: dup_x1         
        //   516: putfield        net/minecraft/item/ItemStack.stackSize:I
        //   519: ifne            540
        //   522: aload_1        
        //   523: getfield        net/minecraft/entity/player/EntityPlayer.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //   526: aload_1        
        //   527: getfield        net/minecraft/entity/player/EntityPlayer.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //   530: getfield        net/minecraft/entity/player/InventoryPlayer.currentItem:I
        //   533: aconst_null    
        //   534: checkcast       Lnet/minecraft/item/ItemStack;
        //   537: invokevirtual   net/minecraft/entity/player/InventoryPlayer.setInventorySlotContents:(ILnet/minecraft/item/ItemStack;)V
        //   540: iconst_1       
        //   541: ireturn        
        //   542: aload_0        
        //   543: invokevirtual   net/minecraft/entity/passive/EntityHorse.func_110253_bW:()Z
        //   546: ifeq            578
        //   549: aload_0        
        //   550: getfield        net/minecraft/entity/passive/EntityHorse.riddenByEntity:Lnet/minecraft/entity/Entity;
        //   553: ifnonnull       578
        //   556: aload_2        
        //   557: ifnull          571
        //   560: aload_2        
        //   561: aload_1        
        //   562: aload_0        
        //   563: invokevirtual   net/minecraft/item/ItemStack.interactWithEntity:(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/entity/EntityLivingBase;)Z
        //   566: ifeq            571
        //   569: iconst_1       
        //   570: ireturn        
        //   571: aload_0        
        //   572: aload_1        
        //   573: invokespecial   net/minecraft/entity/passive/EntityHorse.mountTo:(Lnet/minecraft/entity/player/EntityPlayer;)V
        //   576: iconst_1       
        //   577: ireturn        
        //   578: aload_0        
        //   579: aload_1        
        //   580: invokespecial   net/minecraft/entity/passive/EntityAnimal.interact:(Lnet/minecraft/entity/player/EntityPlayer;)Z
        //   583: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    protected void func_142017_o(final float n) {
        if (n > 6.0f && this.isEatingHaystack()) {
            this.setEatingHaystack(false);
        }
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable entityAgeable) {
        final EntityHorse entityHorse = (EntityHorse)entityAgeable;
        final EntityHorse entityHorse2 = new EntityHorse(this.worldObj);
        final int horseType = this.getHorseType();
        final int horseType2 = entityHorse.getHorseType();
        if (horseType != horseType2) {
            if ((horseType == 0 && horseType2 == 1) || horseType != 1 || horseType2 == 0) {}
        }
        entityHorse2.setHorseType(2);
        entityHorse2.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue((this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue() + entityAgeable.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue() + this.getModifiedMaxHealth()) / 3.0);
        entityHorse2.getEntityAttribute(EntityHorse.horseJumpStrength).setBaseValue((this.getEntityAttribute(EntityHorse.horseJumpStrength).getBaseValue() + entityAgeable.getEntityAttribute(EntityHorse.horseJumpStrength).getBaseValue() + this.getModifiedJumpStrength()) / 3.0);
        entityHorse2.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue((this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue() + entityAgeable.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue() + this.getModifiedMovementSpeed()) / 3.0);
        return entityHorse2;
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1        
        //     2: invokespecial   net/minecraft/entity/passive/EntityAnimal.readEntityFromNBT:(Lnet/minecraft/nbt/NBTTagCompound;)V
        //     5: aload_0        
        //     6: aload_1        
        //     7: ldc_w           "EatingHaystack"
        //    10: invokevirtual   net/minecraft/nbt/NBTTagCompound.getBoolean:(Ljava/lang/String;)Z
        //    13: invokevirtual   net/minecraft/entity/passive/EntityHorse.setEatingHaystack:(Z)V
        //    16: aload_0        
        //    17: aload_1        
        //    18: ldc_w           "Bred"
        //    21: invokevirtual   net/minecraft/nbt/NBTTagCompound.getBoolean:(Ljava/lang/String;)Z
        //    24: invokevirtual   net/minecraft/entity/passive/EntityHorse.setBreeding:(Z)V
        //    27: aload_0        
        //    28: aload_1        
        //    29: ldc_w           "ChestedHorse"
        //    32: invokevirtual   net/minecraft/nbt/NBTTagCompound.getBoolean:(Ljava/lang/String;)Z
        //    35: invokevirtual   net/minecraft/entity/passive/EntityHorse.setChested:(Z)V
        //    38: aload_0        
        //    39: aload_1        
        //    40: ldc_w           "HasReproduced"
        //    43: invokevirtual   net/minecraft/nbt/NBTTagCompound.getBoolean:(Ljava/lang/String;)Z
        //    46: invokevirtual   net/minecraft/entity/passive/EntityHorse.setHasReproduced:(Z)V
        //    49: aload_0        
        //    50: aload_1        
        //    51: ldc_w           "Type"
        //    54: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //    57: invokevirtual   net/minecraft/entity/passive/EntityHorse.setHorseType:(I)V
        //    60: aload_0        
        //    61: aload_1        
        //    62: ldc_w           "Variant"
        //    65: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //    68: invokevirtual   net/minecraft/entity/passive/EntityHorse.setHorseVariant:(I)V
        //    71: aload_0        
        //    72: aload_1        
        //    73: ldc_w           "Temper"
        //    76: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //    79: invokevirtual   net/minecraft/entity/passive/EntityHorse.setTemper:(I)V
        //    82: aload_0        
        //    83: aload_1        
        //    84: ldc_w           "Tame"
        //    87: invokevirtual   net/minecraft/nbt/NBTTagCompound.getBoolean:(Ljava/lang/String;)Z
        //    90: invokevirtual   net/minecraft/entity/passive/EntityHorse.setHorseTamed:(Z)V
        //    93: ldc_w           ""
        //    96: astore_2       
        //    97: aload_1        
        //    98: ldc_w           "OwnerUUID"
        //   101: bipush          8
        //   103: invokevirtual   net/minecraft/nbt/NBTTagCompound.hasKey:(Ljava/lang/String;I)Z
        //   106: ifeq            120
        //   109: aload_1        
        //   110: ldc_w           "OwnerUUID"
        //   113: invokevirtual   net/minecraft/nbt/NBTTagCompound.getString:(Ljava/lang/String;)Ljava/lang/String;
        //   116: astore_2       
        //   117: goto            133
        //   120: aload_1        
        //   121: ldc_w           "Owner"
        //   124: invokevirtual   net/minecraft/nbt/NBTTagCompound.getString:(Ljava/lang/String;)Ljava/lang/String;
        //   127: astore_3       
        //   128: aload_3        
        //   129: invokestatic    net/minecraft/server/management/PreYggdrasilConverter.getStringUUIDFromName:(Ljava/lang/String;)Ljava/lang/String;
        //   132: astore_2       
        //   133: aload_2        
        //   134: invokevirtual   java/lang/String.length:()I
        //   137: ifle            145
        //   140: aload_0        
        //   141: aload_2        
        //   142: invokevirtual   net/minecraft/entity/passive/EntityHorse.setOwnerId:(Ljava/lang/String;)V
        //   145: aload_0        
        //   146: invokevirtual   net/minecraft/entity/passive/EntityHorse.getAttributeMap:()Lnet/minecraft/entity/ai/attributes/BaseAttributeMap;
        //   149: ldc_w           "Speed"
        //   152: invokevirtual   net/minecraft/entity/ai/attributes/BaseAttributeMap.getAttributeInstanceByName:(Ljava/lang/String;)Lnet/minecraft/entity/ai/attributes/IAttributeInstance;
        //   155: astore_3       
        //   156: aload_3        
        //   157: ifnull          182
        //   160: aload_0        
        //   161: getstatic       net/minecraft/entity/SharedMonsterAttributes.movementSpeed:Lnet/minecraft/entity/ai/attributes/IAttribute;
        //   164: invokevirtual   net/minecraft/entity/passive/EntityHorse.getEntityAttribute:(Lnet/minecraft/entity/ai/attributes/IAttribute;)Lnet/minecraft/entity/ai/attributes/IAttributeInstance;
        //   167: aload_3        
        //   168: invokeinterface net/minecraft/entity/ai/attributes/IAttributeInstance.getBaseValue:()D
        //   173: ldc2_w          0.25
        //   176: dmul           
        //   177: invokeinterface net/minecraft/entity/ai/attributes/IAttributeInstance.setBaseValue:(D)V
        //   182: aload_0        
        //   183: invokevirtual   net/minecraft/entity/passive/EntityHorse.isChested:()Z
        //   186: ifeq            273
        //   189: aload_1        
        //   190: ldc_w           "Items"
        //   193: bipush          10
        //   195: invokevirtual   net/minecraft/nbt/NBTTagCompound.getTagList:(Ljava/lang/String;I)Lnet/minecraft/nbt/NBTTagList;
        //   198: astore          4
        //   200: aload_0        
        //   201: invokespecial   net/minecraft/entity/passive/EntityHorse.initHorseChest:()V
        //   204: iconst_0       
        //   205: aload           4
        //   207: invokevirtual   net/minecraft/nbt/NBTTagList.tagCount:()I
        //   210: if_icmpge       273
        //   213: aload           4
        //   215: iconst_0       
        //   216: invokevirtual   net/minecraft/nbt/NBTTagList.getCompoundTagAt:(I)Lnet/minecraft/nbt/NBTTagCompound;
        //   219: astore          6
        //   221: aload           6
        //   223: ldc_w           "Slot"
        //   226: invokevirtual   net/minecraft/nbt/NBTTagCompound.getByte:(Ljava/lang/String;)B
        //   229: sipush          255
        //   232: iand           
        //   233: istore          7
        //   235: iload           7
        //   237: iconst_2       
        //   238: if_icmplt       267
        //   241: iload           7
        //   243: aload_0        
        //   244: getfield        net/minecraft/entity/passive/EntityHorse.horseChest:Lnet/minecraft/inventory/AnimalChest;
        //   247: invokevirtual   net/minecraft/inventory/AnimalChest.getSizeInventory:()I
        //   250: if_icmpge       267
        //   253: aload_0        
        //   254: getfield        net/minecraft/entity/passive/EntityHorse.horseChest:Lnet/minecraft/inventory/AnimalChest;
        //   257: iload           7
        //   259: aload           6
        //   261: invokestatic    net/minecraft/item/ItemStack.loadItemStackFromNBT:(Lnet/minecraft/nbt/NBTTagCompound;)Lnet/minecraft/item/ItemStack;
        //   264: invokevirtual   net/minecraft/inventory/AnimalChest.setInventorySlotContents:(ILnet/minecraft/item/ItemStack;)V
        //   267: iinc            5, 1
        //   270: goto            204
        //   273: aload_1        
        //   274: ldc_w           "ArmorItem"
        //   277: bipush          10
        //   279: invokevirtual   net/minecraft/nbt/NBTTagCompound.hasKey:(Ljava/lang/String;I)Z
        //   282: ifeq            320
        //   285: aload_1        
        //   286: ldc_w           "ArmorItem"
        //   289: invokevirtual   net/minecraft/nbt/NBTTagCompound.getCompoundTag:(Ljava/lang/String;)Lnet/minecraft/nbt/NBTTagCompound;
        //   292: invokestatic    net/minecraft/item/ItemStack.loadItemStackFromNBT:(Lnet/minecraft/nbt/NBTTagCompound;)Lnet/minecraft/item/ItemStack;
        //   295: astore          4
        //   297: aload           4
        //   299: ifnull          320
        //   302: aload           4
        //   304: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   307: if_acmpeq       320
        //   310: aload_0        
        //   311: getfield        net/minecraft/entity/passive/EntityHorse.horseChest:Lnet/minecraft/inventory/AnimalChest;
        //   314: iconst_1       
        //   315: aload           4
        //   317: invokevirtual   net/minecraft/inventory/AnimalChest.setInventorySlotContents:(ILnet/minecraft/item/ItemStack;)V
        //   320: aload_1        
        //   321: ldc_w           "SaddleItem"
        //   324: bipush          10
        //   326: invokevirtual   net/minecraft/nbt/NBTTagCompound.hasKey:(Ljava/lang/String;I)Z
        //   329: ifeq            373
        //   332: aload_1        
        //   333: ldc_w           "SaddleItem"
        //   336: invokevirtual   net/minecraft/nbt/NBTTagCompound.getCompoundTag:(Ljava/lang/String;)Lnet/minecraft/nbt/NBTTagCompound;
        //   339: invokestatic    net/minecraft/item/ItemStack.loadItemStackFromNBT:(Lnet/minecraft/nbt/NBTTagCompound;)Lnet/minecraft/item/ItemStack;
        //   342: astore          4
        //   344: aload           4
        //   346: ifnull          370
        //   349: aload           4
        //   351: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   354: getstatic       net/minecraft/init/Items.saddle:Lnet/minecraft/item/Item;
        //   357: if_acmpne       370
        //   360: aload_0        
        //   361: getfield        net/minecraft/entity/passive/EntityHorse.horseChest:Lnet/minecraft/inventory/AnimalChest;
        //   364: iconst_0       
        //   365: aload           4
        //   367: invokevirtual   net/minecraft/inventory/AnimalChest.setInventorySlotContents:(ILnet/minecraft/item/ItemStack;)V
        //   370: goto            401
        //   373: aload_1        
        //   374: ldc_w           "Saddle"
        //   377: invokevirtual   net/minecraft/nbt/NBTTagCompound.getBoolean:(Ljava/lang/String;)Z
        //   380: ifeq            401
        //   383: aload_0        
        //   384: getfield        net/minecraft/entity/passive/EntityHorse.horseChest:Lnet/minecraft/inventory/AnimalChest;
        //   387: iconst_0       
        //   388: new             Lnet/minecraft/item/ItemStack;
        //   391: dup            
        //   392: getstatic       net/minecraft/init/Items.saddle:Lnet/minecraft/item/Item;
        //   395: invokespecial   net/minecraft/item/ItemStack.<init>:(Lnet/minecraft/item/Item;)V
        //   398: invokevirtual   net/minecraft/inventory/AnimalChest.setInventorySlotContents:(ILnet/minecraft/item/ItemStack;)V
        //   401: aload_0        
        //   402: invokespecial   net/minecraft/entity/passive/EntityHorse.updateHorseSlots:()V
        //   405: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void setChested(final boolean b) {
        this.setHorseWatchableBoolean(8, b);
    }
    
    public void setJumpPower(final int n) {
        if (this.isHorseSaddled()) {
            this.field_110294_bI = true;
            this.makeHorseRear();
            this.jumpPower = 0.4f + 0.4f * 0 / 90.0f;
        }
    }
    
    @Override
    public void handleStatusUpdate(final byte b) {
        if (b == 7) {
            this.spawnHorseParticles(true);
        }
        else if (b == 6) {
            this.spawnHorseParticles(false);
        }
        else {
            super.handleStatusUpdate(b);
        }
    }
    
    @Override
    protected String getLivingSound() {
        this.openHorseMouth();
        if (this.rand.nextInt(10) == 0 && this != null) {
            this.makeHorseRear();
        }
        final int horseType = this.getHorseType();
        return (horseType == 3) ? "mob.horse.zombie.idle" : ((horseType == 4) ? "mob.horse.skeleton.idle" : ((horseType != 1 && horseType != 2) ? "mob.horse.idle" : "mob.horse.donkey.idle"));
    }
    
    @Override
    public void onDeath(final DamageSource damageSource) {
        super.onDeath(damageSource);
        if (!this.worldObj.isRemote) {
            this.dropChestItems();
        }
    }
    
    @Override
    public float getEyeHeight() {
        return this.height;
    }
    
    public boolean isBreeding() {
        return this.getHorseWatchableBoolean(16);
    }
    
    public boolean prepareChunkForSpawn() {
        this.worldObj.getBiomeGenForCoords(new BlockPos(MathHelper.floor_double(this.posX), 0, MathHelper.floor_double(this.posZ)));
        return true;
    }
    
    @Override
    public boolean getCanSpawnHere() {
        this.prepareChunkForSpawn();
        return super.getCanSpawnHere();
    }
    
    @Override
    public void onInventoryChanged(final InventoryBasic inventoryBasic) {
        final int horseArmorIndexSynced = this.getHorseArmorIndexSynced();
        final boolean horseSaddled = this.isHorseSaddled();
        this.updateHorseSlots();
        if (this.ticksExisted > 20) {
            if (horseArmorIndexSynced == 0 && horseArmorIndexSynced != this.getHorseArmorIndexSynced()) {
                this.playSound("mob.horse.armor", 0.5f, 1.0f);
            }
            else if (horseArmorIndexSynced != this.getHorseArmorIndexSynced()) {
                this.playSound("mob.horse.armor", 0.5f, 1.0f);
            }
            if (!horseSaddled && this.isHorseSaddled()) {
                this.playSound("mob.horse.leather", 0.5f, 1.0f);
            }
        }
    }
    
    protected String getAngrySoundName() {
        this.openHorseMouth();
        this.makeHorseRear();
        final int horseType = this.getHorseType();
        return (horseType != 3 && horseType != 4) ? ((horseType != 1 && horseType != 2) ? "mob.horse.angry" : "mob.horse.donkey.angry") : null;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, 0);
        this.dataWatcher.addObject(19, 0);
        this.dataWatcher.addObject(20, 0);
        this.dataWatcher.addObject(21, String.valueOf(""));
        this.dataWatcher.addObject(22, 0);
    }
    
    public int getTemper() {
        return this.temper;
    }
    
    public void dropChestItems() {
        this.dropItemsInChest(this, this.horseChest);
        this.dropChests();
    }
    
    private void setHorseTexturePaths() {
        this.texturePrefix = "horse/";
        this.horseTexturesArray[0] = null;
        this.horseTexturesArray[1] = null;
        this.horseTexturesArray[2] = null;
        final int horseType = this.getHorseType();
        final int horseVariant = this.getHorseVariant();
        if (horseType == 0) {
            final int n = horseVariant & 0xFF;
            final int n2 = (horseVariant & 0xFF00) >> 8;
            if (n >= EntityHorse.horseTextures.length) {
                this.field_175508_bO = false;
                return;
            }
            this.horseTexturesArray[0] = EntityHorse.horseTextures[n];
            this.texturePrefix += EntityHorse.HORSE_TEXTURES_ABBR[n];
            if (n2 >= EntityHorse.horseMarkingTextures.length) {
                this.field_175508_bO = false;
                return;
            }
            this.horseTexturesArray[1] = EntityHorse.horseMarkingTextures[n2];
            this.texturePrefix += EntityHorse.HORSE_MARKING_TEXTURES_ABBR[n2];
        }
        else {
            this.horseTexturesArray[0] = "";
            this.texturePrefix = this.texturePrefix + "_" + horseType + "_";
        }
        final int horseArmorIndexSynced = this.getHorseArmorIndexSynced();
        if (horseArmorIndexSynced >= EntityHorse.horseArmorTextures.length) {
            this.field_175508_bO = false;
        }
        else {
            this.horseTexturesArray[2] = EntityHorse.horseArmorTextures[horseArmorIndexSynced];
            this.texturePrefix += EntityHorse.HORSE_ARMOR_TEXTURES_ABBR[horseArmorIndexSynced];
            this.field_175508_bO = true;
        }
    }
    
    private void updateHorseSlots() {
        if (!this.worldObj.isRemote) {
            this.setHorseSaddled(this.horseChest.getStackInSlot(0) != null);
            if (this == 0) {
                this.setHorseArmorStack(this.horseChest.getStackInSlot(1));
            }
        }
    }
    
    public float getGrassEatingAmount(final float n) {
        return this.prevHeadLean + (this.headLean - this.prevHeadLean) * n;
    }
    
    @Override
    protected Item getDropItem() {
        final boolean b = this.rand.nextInt(4) == 0;
        final int horseType = this.getHorseType();
        return (horseType == 4) ? Items.bone : ((horseType == 3) ? (b ? null : Items.rotten_flesh) : Items.leather);
    }
    
    @Override
    public boolean canMateWith(final EntityAnimal entityAnimal) {
        if (entityAnimal == this) {
            return false;
        }
        if (entityAnimal.getClass() != this.getClass()) {
            return false;
        }
        final EntityHorse entityHorse = (EntityHorse)entityAnimal;
        if (this == null && entityHorse == null) {
            final int horseType = this.getHorseType();
            final int horseType2 = entityHorse.getHorseType();
            return horseType == horseType2 || (horseType == 0 && horseType2 == 1) || (horseType == 1 && horseType2 == 0);
        }
        return false;
    }
    
    private void setHorseWatchableBoolean(final int n, final boolean b) {
        final int watchableObjectInt = this.dataWatcher.getWatchableObjectInt(16);
        if (b) {
            this.dataWatcher.updateObject(16, watchableObjectInt | n);
        }
        else {
            this.dataWatcher.updateObject(16, watchableObjectInt & ~n);
        }
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(EntityHorse.horseJumpStrength);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(53.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22499999403953552);
    }
    
    private void resetTexturePrefix() {
        this.texturePrefix = null;
    }
    
    private void func_110266_cB() {
        this.openHorseMouth();
        if (!this.isSilent()) {
            this.worldObj.playSoundAtEntity(this, "eating", 1.0f, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f);
        }
    }
    
    public boolean isChested() {
        return this.getHorseWatchableBoolean(8);
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.worldObj.isRemote && this.dataWatcher.hasObjectChanged()) {
            this.dataWatcher.func_111144_e();
            this.resetTexturePrefix();
        }
        if (this.openMouthCounter > 0 && ++this.openMouthCounter > 30) {
            this.openMouthCounter = 0;
            this.setHorseWatchableBoolean(128, false);
        }
        if (!this.worldObj.isRemote && this.jumpRearingCounter > 0 && ++this.jumpRearingCounter > 20) {
            this.jumpRearingCounter = 0;
            this.setRearing(false);
        }
        if (this.field_110278_bp > 0 && ++this.field_110278_bp > 8) {
            this.field_110278_bp = 0;
        }
        if (this.field_110279_bq > 0) {
            ++this.field_110279_bq;
            if (this.field_110279_bq > 300) {
                this.field_110279_bq = 0;
            }
        }
        this.prevHeadLean = this.headLean;
        if (this.isEatingHaystack()) {
            this.headLean += (1.0f - this.headLean) * 0.4f + 0.05f;
            if (this.headLean > 1.0f) {
                this.headLean = 1.0f;
            }
        }
        else {
            this.headLean += (0.0f - this.headLean) * 0.4f - 0.05f;
            if (this.headLean < 0.0f) {
                this.headLean = 0.0f;
            }
        }
        this.prevRearingAmount = this.rearingAmount;
        if (this.isRearing()) {
            final float n = 0.0f;
            this.headLean = n;
            this.prevHeadLean = n;
            this.rearingAmount += (1.0f - this.rearingAmount) * 0.4f + 0.05f;
            if (this.rearingAmount > 1.0f) {
                this.rearingAmount = 1.0f;
            }
        }
        else {
            this.field_110294_bI = false;
            this.rearingAmount += (0.8f * this.rearingAmount * this.rearingAmount * this.rearingAmount - this.rearingAmount) * 0.6f - 0.05f;
            if (this.rearingAmount < 0.0f) {
                this.rearingAmount = 0.0f;
            }
        }
        this.prevMouthOpenness = this.mouthOpenness;
        this.mouthOpenness += (0.0f - this.mouthOpenness) * 0.7f - 0.05f;
        if (this.mouthOpenness < 0.0f) {
            this.mouthOpenness = 0.0f;
        }
    }
    
    private double getModifiedMovementSpeed() {
        return (0.44999998807907104 + this.rand.nextDouble() * 0.3 + this.rand.nextDouble() * 0.3 + this.rand.nextDouble() * 0.3) * 0.25;
    }
    
    @Override
    public void moveEntityWithHeading(float n, float moveForward) {
        if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase && this.isHorseSaddled()) {
            final float rotationYaw = this.riddenByEntity.rotationYaw;
            this.rotationYaw = rotationYaw;
            this.prevRotationYaw = rotationYaw;
            this.rotationPitch = this.riddenByEntity.rotationPitch * 0.5f;
            this.setRotation(this.rotationYaw, this.rotationPitch);
            final float rotationYaw2 = this.rotationYaw;
            this.renderYawOffset = rotationYaw2;
            this.rotationYawHead = rotationYaw2;
            n = ((EntityLivingBase)this.riddenByEntity).moveStrafing * 0.5f;
            moveForward = ((EntityLivingBase)this.riddenByEntity).moveForward;
            if (moveForward <= 0.0f) {
                moveForward *= 0.25f;
                this.gallopTime = 0;
            }
            if (this.onGround && this.jumpPower == 0.0f && this.isRearing() && !this.field_110294_bI) {
                n = 0.0f;
                moveForward = 0.0f;
            }
            if (this.jumpPower > 0.0f && !this.isHorseJumping() && this.onGround) {
                this.motionY = this.getHorseJumpStrength() * this.jumpPower;
                if (this.isPotionActive(Potion.jump)) {
                    this.motionY += (this.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1f;
                }
                this.setHorseJumping(true);
                this.isAirBorne = true;
                if (moveForward > 0.0f) {
                    final float sin = MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f);
                    final float cos = MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f);
                    this.motionX += -0.4f * sin * this.jumpPower;
                    this.motionZ += 0.4f * cos * this.jumpPower;
                    this.playSound("mob.horse.jump", 0.4f, 1.0f);
                }
                this.jumpPower = 0.0f;
            }
            this.stepHeight = 1.0f;
            this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1f;
            if (!this.worldObj.isRemote) {
                this.setAIMoveSpeed((float)this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
                super.moveEntityWithHeading(n, moveForward);
            }
            if (this.onGround) {
                this.jumpPower = 0.0f;
                this.setHorseJumping(false);
            }
            this.prevLimbSwingAmount = this.limbSwingAmount;
            final double n2 = this.posX - this.prevPosX;
            final double n3 = this.posZ - this.prevPosZ;
            float n4 = MathHelper.sqrt_double(n2 * n2 + n3 * n3) * 4.0f;
            if (n4 > 1.0f) {
                n4 = 1.0f;
            }
            this.limbSwingAmount += (n4 - this.limbSwingAmount) * 0.4f;
            this.limbSwing += this.limbSwingAmount;
        }
        else {
            this.stepHeight = 0.5f;
            this.jumpMovementFactor = 0.02f;
            super.moveEntityWithHeading(n, moveForward);
        }
    }
    
    public boolean isEatingHaystack() {
        return this.getHorseWatchableBoolean(32);
    }
    
    private int getHorseArmorIndex(final ItemStack itemStack) {
        if (itemStack == null) {
            return 0;
        }
        final Item item = itemStack.getItem();
        return (item == Items.iron_horse_armor) ? 1 : ((item == Items.golden_horse_armor) ? 2 : ((item == Items.diamond_horse_armor) ? 3 : 0));
    }
    
    public float getRearingAmount(final float n) {
        return this.prevRearingAmount + (this.rearingAmount - this.prevRearingAmount) * n;
    }
    
    public void dropChests() {
        if (!this.worldObj.isRemote && this.isChested()) {
            this.dropItem(Item.getItemFromBlock(Blocks.chest), 1);
            this.setChested(false);
        }
    }
    
    public boolean setTamedBy(final EntityPlayer entityPlayer) {
        this.setOwnerId(entityPlayer.getUniqueID().toString());
        this.setHorseTamed(true);
        return true;
    }
    
    private int getChestSize() {
        final int horseType = this.getHorseType();
        return (!this.isChested() || (horseType != 1 && horseType != 2)) ? 2 : 17;
    }
    
    @Override
    protected String getHurtSound() {
        this.openHorseMouth();
        if (this.rand.nextInt(3) == 0) {
            this.makeHorseRear();
        }
        final int horseType = this.getHorseType();
        return (horseType == 3) ? "mob.horse.zombie.hit" : ((horseType == 4) ? "mob.horse.skeleton.hit" : ((horseType != 1 && horseType != 2) ? "mob.horse.hit" : "mob.horse.donkey.hit"));
    }
    
    public void openGUI(final EntityPlayer entityPlayer) {
        if (!this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == entityPlayer) && this.isTame()) {
            this.horseChest.setCustomName(this.getName());
            entityPlayer.displayGUIHorse(this, this.horseChest);
        }
    }
    
    @Override
    public int getTotalArmorValue() {
        return EntityHorse.armorValues[this.getHorseArmorIndexSynced()];
    }
    
    public boolean isOnLadder() {
        return false;
    }
    
    public String getHorseTexture() {
        if (this.texturePrefix == null) {
            this.setHorseTexturePaths();
        }
        return this.texturePrefix;
    }
    
    public String[] getVariantTexturePaths() {
        if (this.texturePrefix == null) {
            this.setHorseTexturePaths();
        }
        return this.horseTexturesArray;
    }
    
    public void setHorseVariant(final int n) {
        this.dataWatcher.updateObject(20, n);
        this.resetTexturePrefix();
    }
    
    public boolean getHasReproduced() {
        return this.hasReproduced;
    }
    
    public void makeHorseRearWithSound() {
        this.makeHorseRear();
        final String angrySoundName = this.getAngrySoundName();
        if (angrySoundName != null) {
            this.playSound(angrySoundName, this.getSoundVolume(), this.getSoundPitch());
        }
    }
    
    @Override
    public int getTalkInterval() {
        return 400;
    }
    
    @Override
    public boolean replaceItemInInventory(final int p0, final ItemStack p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: sipush          499
        //     4: if_icmpne       68
        //     7: aload_0        
        //     8: if_icmpeq       68
        //    11: aload_2        
        //    12: ifnonnull       33
        //    15: aload_0        
        //    16: invokevirtual   net/minecraft/entity/passive/EntityHorse.isChested:()Z
        //    19: ifeq            33
        //    22: aload_0        
        //    23: iconst_0       
        //    24: invokevirtual   net/minecraft/entity/passive/EntityHorse.setChested:(Z)V
        //    27: aload_0        
        //    28: invokespecial   net/minecraft/entity/passive/EntityHorse.initHorseChest:()V
        //    31: iconst_1       
        //    32: ireturn        
        //    33: aload_2        
        //    34: ifnull          68
        //    37: aload_2        
        //    38: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //    41: getstatic       net/minecraft/init/Blocks.chest:Lnet/minecraft/block/BlockChest;
        //    44: invokestatic    net/minecraft/item/Item.getItemFromBlock:(Lnet/minecraft/block/Block;)Lnet/minecraft/item/Item;
        //    47: if_acmpne       68
        //    50: aload_0        
        //    51: invokevirtual   net/minecraft/entity/passive/EntityHorse.isChested:()Z
        //    54: ifne            68
        //    57: aload_0        
        //    58: iconst_1       
        //    59: invokevirtual   net/minecraft/entity/passive/EntityHorse.setChested:(Z)V
        //    62: aload_0        
        //    63: invokespecial   net/minecraft/entity/passive/EntityHorse.initHorseChest:()V
        //    66: iconst_1       
        //    67: ireturn        
        //    68: iload_1        
        //    69: sipush          400
        //    72: isub           
        //    73: istore_3       
        //    74: iload_3        
        //    75: iflt            151
        //    78: iload_3        
        //    79: iconst_2       
        //    80: if_icmpge       151
        //    83: iload_3        
        //    84: aload_0        
        //    85: getfield        net/minecraft/entity/passive/EntityHorse.horseChest:Lnet/minecraft/inventory/AnimalChest;
        //    88: invokevirtual   net/minecraft/inventory/AnimalChest.getSizeInventory:()I
        //    91: if_icmpge       151
        //    94: iload_3        
        //    95: ifne            114
        //    98: aload_2        
        //    99: ifnull          114
        //   102: aload_2        
        //   103: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   106: getstatic       net/minecraft/init/Items.saddle:Lnet/minecraft/item/Item;
        //   109: if_acmpeq       114
        //   112: iconst_0       
        //   113: ireturn        
        //   114: iload_3        
        //   115: iconst_1       
        //   116: if_icmpne       134
        //   119: aload_2        
        //   120: ifnull          130
        //   123: aload_2        
        //   124: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   127: if_acmpeq       149
        //   130: aload_0        
        //   131: ifne            149
        //   134: aload_0        
        //   135: getfield        net/minecraft/entity/passive/EntityHorse.horseChest:Lnet/minecraft/inventory/AnimalChest;
        //   138: iload_3        
        //   139: aload_2        
        //   140: invokevirtual   net/minecraft/inventory/AnimalChest.setInventorySlotContents:(ILnet/minecraft/item/ItemStack;)V
        //   143: aload_0        
        //   144: invokespecial   net/minecraft/entity/passive/EntityHorse.updateHorseSlots:()V
        //   147: iconst_1       
        //   148: ireturn        
        //   149: iconst_0       
        //   150: ireturn        
        //   151: iload_1        
        //   152: sipush          500
        //   155: isub           
        //   156: iconst_2       
        //   157: iadd           
        //   158: istore          4
        //   160: iload           4
        //   162: iconst_2       
        //   163: if_icmplt       190
        //   166: iload           4
        //   168: aload_0        
        //   169: getfield        net/minecraft/entity/passive/EntityHorse.horseChest:Lnet/minecraft/inventory/AnimalChest;
        //   172: invokevirtual   net/minecraft/inventory/AnimalChest.getSizeInventory:()I
        //   175: if_icmpge       190
        //   178: aload_0        
        //   179: getfield        net/minecraft/entity/passive/EntityHorse.horseChest:Lnet/minecraft/inventory/AnimalChest;
        //   182: iload           4
        //   184: aload_2        
        //   185: invokevirtual   net/minecraft/inventory/AnimalChest.setInventorySlotContents:(ILnet/minecraft/item/ItemStack;)V
        //   188: iconst_1       
        //   189: ireturn        
        //   190: iconst_0       
        //   191: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void setHasReproduced(final boolean hasReproduced) {
        this.hasReproduced = hasReproduced;
    }
    
    public boolean isHorseJumping() {
        return this.horseJumping;
    }
    
    public void setHorseType(final int n) {
        this.dataWatcher.updateObject(19, (byte)n);
        this.resetTexturePrefix();
    }
    
    public boolean isHorseSaddled() {
        return this.getHorseWatchableBoolean(4);
    }
    
    public boolean isBreedingItem(final ItemStack itemStack) {
        return false;
    }
    
    public float getHorseSize() {
        return 0.5f;
    }
    
    public void setHorseSaddled(final boolean b) {
        this.setHorseWatchableBoolean(4, b);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean("EatingHaystack", this.isEatingHaystack());
        nbtTagCompound.setBoolean("ChestedHorse", this.isChested());
        nbtTagCompound.setBoolean("HasReproduced", this.getHasReproduced());
        nbtTagCompound.setBoolean("Bred", this.isBreeding());
        nbtTagCompound.setInteger("Type", this.getHorseType());
        nbtTagCompound.setInteger("Variant", this.getHorseVariant());
        nbtTagCompound.setInteger("Temper", this.getTemper());
        nbtTagCompound.setBoolean("Tame", this.isTame());
        nbtTagCompound.setString("OwnerUUID", this.getOwnerId());
        if (this.isChested()) {
            final NBTTagList list = new NBTTagList();
            while (2 < this.horseChest.getSizeInventory()) {
                final ItemStack stackInSlot = this.horseChest.getStackInSlot(2);
                if (stackInSlot != null) {
                    final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                    nbtTagCompound2.setByte("Slot", (byte)2);
                    stackInSlot.writeToNBT(nbtTagCompound2);
                    list.appendTag(nbtTagCompound2);
                }
                int n = 0;
                ++n;
            }
            nbtTagCompound.setTag("Items", list);
        }
        if (this.horseChest.getStackInSlot(1) != null) {
            nbtTagCompound.setTag("ArmorItem", this.horseChest.getStackInSlot(1).writeToNBT(new NBTTagCompound()));
        }
        if (this.horseChest.getStackInSlot(0) != null) {
            nbtTagCompound.setTag("SaddleItem", this.horseChest.getStackInSlot(0).writeToNBT(new NBTTagCompound()));
        }
    }
    
    static {
        horseBreedingSelector = (Predicate)new Predicate() {
            public boolean apply(final Entity entity) {
                return entity instanceof EntityHorse && ((EntityHorse)entity).isBreeding();
            }
            
            public boolean apply(final Object o) {
                return this.apply((Entity)o);
            }
        };
        horseJumpStrength = new RangedAttribute(null, "horse.jumpStrength", 0.7, 0.0, 2.0).setDescription("Jump Strength").setShouldWatch(true);
        EntityHorse.horseArmorTextures = new String[] { null, "textures/entity/horse/armor/horse_armor_iron.png", "textures/entity/horse/armor/horse_armor_gold.png", "textures/entity/horse/armor/horse_armor_diamond.png" };
        HORSE_ARMOR_TEXTURES_ABBR = new String[] { "", "meo", "goo", "dio" };
        EntityHorse.armorValues = new int[] { 0, 5, 7, 11 };
        horseTextures = new String[] { "textures/entity/horse/horse_white.png", "textures/entity/horse/horse_creamy.png", "textures/entity/horse/horse_chestnut.png", "textures/entity/horse/horse_brown.png", "textures/entity/horse/horse_black.png", "textures/entity/horse/horse_gray.png", "textures/entity/horse/horse_darkbrown.png" };
        HORSE_TEXTURES_ABBR = new String[] { "hwh", "hcr", "hch", "hbr", "hbl", "hgr", "hdb" };
        horseMarkingTextures = new String[] { null, "textures/entity/horse/horse_markings_white.png", "textures/entity/horse/horse_markings_whitefield.png", "textures/entity/horse/horse_markings_whitedots.png", "textures/entity/horse/horse_markings_blackdots.png" };
        HORSE_MARKING_TEXTURES_ABBR = new String[] { "", "wo_", "wmo", "wdo", "bdo" };
    }
    
    @Override
    protected void playStepSound(final BlockPos blockPos, final Block block) {
        Block.SoundType soundType = block.stepSound;
        if (this.worldObj.getBlockState(blockPos.up()).getBlock() == Blocks.snow_layer) {
            soundType = Blocks.snow_layer.stepSound;
        }
        if (!block.getMaterial().isLiquid()) {
            final int horseType = this.getHorseType();
            if (this.riddenByEntity != null && horseType != 1 && horseType != 2) {
                ++this.gallopTime;
                if (this.gallopTime > 5 && this.gallopTime % 3 == 0) {
                    this.playSound("mob.horse.gallop", soundType.getVolume() * 0.15f, soundType.getFrequency());
                    if (horseType == 0 && this.rand.nextInt(10) == 0) {
                        this.playSound("mob.horse.breathe", soundType.getVolume() * 0.6f, soundType.getFrequency());
                    }
                }
                else if (this.gallopTime <= 5) {
                    this.playSound("mob.horse.wood", soundType.getVolume() * 0.15f, soundType.getFrequency());
                }
            }
            else if (soundType == Block.soundTypeWood) {
                this.playSound("mob.horse.wood", soundType.getVolume() * 0.15f, soundType.getFrequency());
            }
            else {
                this.playSound("mob.horse.soft", soundType.getVolume() * 0.15f, soundType.getFrequency());
            }
        }
    }
    
    public void setHorseJumping(final boolean horseJumping) {
        this.horseJumping = horseJumping;
    }
    
    public void setRearing(final boolean b) {
        if (b) {
            this.setEatingHaystack(false);
        }
        this.setHorseWatchableBoolean(64, b);
    }
    
    @Override
    public void fall(final float n, final float n2) {
        if (n > 1.0f) {
            this.playSound("mob.horse.land", 0.4f, 1.0f);
        }
        final int ceiling_float_int = MathHelper.ceiling_float_int((n * 0.5f - 3.0f) * n2);
        if (ceiling_float_int > 0) {
            this.attackEntityFrom(DamageSource.fall, (float)ceiling_float_int);
            if (this.riddenByEntity != null) {
                this.riddenByEntity.attackEntityFrom(DamageSource.fall, (float)ceiling_float_int);
            }
            final Block block = this.worldObj.getBlockState(new BlockPos(this.posX, this.posY - 0.2 - this.prevRotationYaw, this.posZ)).getBlock();
            if (block.getMaterial() != Material.air && !this.isSilent()) {
                final Block.SoundType stepSound = block.stepSound;
                this.worldObj.playSoundAtEntity(this, stepSound.getStepSound(), stepSound.getVolume() * 0.5f, stepSound.getFrequency() * 0.75f);
            }
        }
    }
    
    public float getMouthOpennessAngle(final float n) {
        return this.prevMouthOpenness + (this.mouthOpenness - this.prevMouthOpenness) * n;
    }
    
    public int getMaxTemper() {
        return 100;
    }
    
    protected void spawnHorseParticles(final boolean b) {
        final EnumParticleTypes enumParticleTypes = b ? EnumParticleTypes.HEART : EnumParticleTypes.SMOKE_NORMAL;
        while (true) {
            this.worldObj.spawnParticle(enumParticleTypes, this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width, this.posY + 0.5 + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width, this.rand.nextGaussian() * 0.02, this.rand.nextGaussian() * 0.02, this.rand.nextGaussian() * 0.02, new int[0]);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public IEntityLivingData onInitialSpawn(final DifficultyInstance difficultyInstance, IEntityLivingData onInitialSpawn) {
        onInitialSpawn = super.onInitialSpawn(difficultyInstance, onInitialSpawn);
        if (onInitialSpawn instanceof GroupData) {
            final int horseType = ((GroupData)onInitialSpawn).horseType;
            final int n = (((GroupData)onInitialSpawn).horseVariant & 0xFF) | this.rand.nextInt(5) << 8;
        }
        else {
            if (this.rand.nextInt(10) != 0) {
                final int n2 = this.rand.nextInt(7) | this.rand.nextInt(5) << 8;
            }
            onInitialSpawn = new GroupData(0, 0);
        }
        this.setHorseType(0);
        this.setHorseVariant(0);
        if (this.rand.nextInt(5) == 0) {
            this.setGrowingAge(-24000);
        }
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(this.getModifiedMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(this.getModifiedMovementSpeed());
        this.getEntityAttribute(EntityHorse.horseJumpStrength).setBaseValue(this.getModifiedJumpStrength());
        this.setHealth(this.getMaxHealth());
        return onInitialSpawn;
    }
    
    protected EntityHorse getClosestHorse(final Entity entity, final double n) {
        double n2 = Double.MAX_VALUE;
        Entity entity2 = null;
        for (final Entity entity3 : this.worldObj.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().addCoord(n, n, n), EntityHorse.horseBreedingSelector)) {
            final double distanceSq = entity3.getDistanceSq(entity.posX, entity.posY, entity.posZ);
            if (distanceSq < n2) {
                entity2 = entity3;
                n2 = distanceSq;
            }
        }
        return (EntityHorse)entity2;
    }
    
    @Override
    public boolean canBePushed() {
        return this.riddenByEntity == null;
    }
    
    public boolean func_110239_cn() {
        return this.getHorseType() == 0 || this.getHorseArmorIndexSynced() > 0;
    }
    
    public void setEatingHaystack(final boolean eating) {
        this.setEating(eating);
    }
    
    private float getModifiedMaxHealth() {
        return 15.0f + this.rand.nextInt(8) + this.rand.nextInt(9);
    }
    
    @Override
    public void setScaleForAge(final boolean b) {
        if (b) {
            this.setScale(this.getHorseSize());
        }
        else {
            this.setScale(1.0f);
        }
    }
    
    public static class GroupData implements IEntityLivingData
    {
        public int horseVariant;
        public int horseType;
        
        public GroupData(final int horseType, final int horseVariant) {
            this.horseType = horseType;
            this.horseVariant = horseVariant;
        }
    }
}
