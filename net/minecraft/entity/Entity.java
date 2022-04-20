package net.minecraft.entity;

import net.minecraft.command.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.server.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.block.state.pattern.*;
import net.minecraft.enchantment.*;
import net.minecraft.nbt.*;
import net.minecraft.event.*;
import net.minecraft.crash.*;
import java.util.concurrent.*;
import net.minecraft.entity.player.*;

public abstract class Entity implements ICommandSender
{
    protected int portalCounter;
    private boolean isOutsideBorder;
    public double renderDistanceWeight;
    protected boolean isImmuneToFire;
    public Entity riddenByEntity;
    public int timeUntilPortal;
    private int fire;
    public boolean isDead;
    public double posY;
    public float height;
    public boolean noClip;
    public boolean isCollidedHorizontally;
    protected boolean inPortal;
    public Entity ridingEntity;
    public int chunkCoordZ;
    public float entityCollisionReduction;
    public int serverPosY;
    protected EnumFacing field_181018_ap;
    public double motionX;
    public float prevRotationYaw;
    protected Random rand;
    public float prevDistanceWalkedModified;
    public boolean preventEntitySpawning;
    public int serverPosZ;
    public int hurtResistantTime;
    protected Vec3 field_181017_ao;
    public World worldObj;
    private boolean invulnerable;
    public boolean forceSpawn;
    public int fireResistance;
    protected UUID entityUniqueID;
    private double entityRiderYawDelta;
    public float rotationYaw;
    public float prevRotationPitch;
    public int dimension;
    public boolean isCollidedVertically;
    public boolean addedToChunk;
    public boolean inWater;
    public int chunkCoordY;
    public double motionY;
    public double posZ;
    public boolean onGround;
    public float width;
    public double lastTickPosY;
    public float fallDistance;
    public int ticksExisted;
    public AxisAlignedBB boundingBox;
    public double prevPosX;
    protected boolean firstUpdate;
    public boolean velocityChanged;
    private static int nextEntityID;
    private final CommandResultStats cmdResultStats;
    private double entityRiderPitchDelta;
    protected BlockPos field_181016_an;
    public float rotationPitch;
    public int chunkCoordX;
    public float distanceWalkedModified;
    public boolean isAirBorne;
    public double posX;
    public int serverPosX;
    public boolean ignoreFrustumCheck;
    public float stepHeight;
    protected boolean isInWeb;
    private int nextStepDistance;
    public boolean isCollided;
    public double motionZ;
    protected DataWatcher dataWatcher;
    private static final AxisAlignedBB ZERO_AABB;
    public double prevPosY;
    public double lastTickPosX;
    public double lastTickPosZ;
    public float distanceWalkedOnStepModified;
    private int entityId;
    public double prevPosZ;
    
    public boolean isInRangeToRenderDist(final double n) {
        double averageEdgeLength = this.getEntityBoundingBox().getAverageEdgeLength();
        if (Double.isNaN(averageEdgeLength)) {
            averageEdgeLength = 1.0;
        }
        final double n2 = averageEdgeLength * 64.0 * this.renderDistanceWeight;
        return n < n2 * n2;
    }
    
    public void onStruckByLightning(final EntityLightningBolt entityLightningBolt) {
        this.attackEntityFrom(DamageSource.lightningBolt, 5.0f);
        ++this.fire;
        if (this.fire == 0) {
            this.setFire(8);
        }
    }
    
    public void updateRiderPosition() {
        if (this.riddenByEntity != null) {
            this.riddenByEntity.setPosition(this.posX, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ);
        }
    }
    
    protected void setOnFireFromLava() {
        if (!this.isImmuneToFire) {
            this.attackEntityFrom(DamageSource.lava, 4.0f);
            this.setFire(15);
        }
    }
    
    public void onKillEntity(final EntityLivingBase entityLivingBase) {
    }
    
    public final boolean isImmuneToFire() {
        return this.isImmuneToFire;
    }
    
    public void setOutsideBorder(final boolean isOutsideBorder) {
        this.isOutsideBorder = isOutsideBorder;
    }
    
    public boolean isOutsideBorder() {
        return this.isOutsideBorder;
    }
    
    public double getDistanceSq(final double n, final double n2, final double n3) {
        final double n4 = this.posX - n;
        final double n5 = this.posY - n2;
        final double n6 = this.posZ - n3;
        return n4 * n4 + n5 * n5 + n6 * n6;
    }
    
    public AxisAlignedBB getCollisionBox(final Entity entity) {
        return null;
    }
    
    protected String getSplashSound() {
        return "game.neutral.swim.splash";
    }
    
    protected boolean pushOutOfBlocks(final double n, final double n2, final double n3) {
        final BlockPos blockPos = new BlockPos(n, n2, n3);
        final double n4 = n - blockPos.getX();
        final double n5 = n2 - blockPos.getY();
        final double n6 = n3 - blockPos.getZ();
        if (this.worldObj.func_147461_a(this.getEntityBoundingBox()).isEmpty() && !this.worldObj.isBlockFullCube(blockPos)) {
            return false;
        }
        double n7 = 9999.0;
        if (!this.worldObj.isBlockFullCube(blockPos.west()) && n4 < n7) {
            n7 = n4;
        }
        if (!this.worldObj.isBlockFullCube(blockPos.east()) && 1.0 - n4 < n7) {
            n7 = 1.0 - n4;
        }
        if (!this.worldObj.isBlockFullCube(blockPos.up()) && 1.0 - n5 < n7) {
            n7 = 1.0 - n5;
        }
        if (!this.worldObj.isBlockFullCube(blockPos.north()) && n6 < n7) {
            n7 = n6;
        }
        if (!this.worldObj.isBlockFullCube(blockPos.south()) && 1.0 - n6 < n7) {}
        this.motionZ = this.rand.nextFloat() * 0.2f + 0.1f;
        return true;
    }
    
    public boolean isSneaking() {
        return this.getFlag(1);
    }
    
    public EntityItem dropItem(final Item item, final int n) {
        return this.dropItemWithOffset(item, n, 0.0f);
    }
    
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (damageSource != 0) {
            return false;
        }
        this.setBeenAttacked();
        return false;
    }
    
    public boolean isSprinting() {
        return this.getFlag(3);
    }
    
    public boolean isEntityInsideOpaqueBlock() {
        if (this.noClip) {
            return false;
        }
        final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
        while (true) {
            final int floor_double = MathHelper.floor_double(this.posY + (0 - 0.5f) * 0.1f + this.getEyeHeight());
            final int floor_double2 = MathHelper.floor_double(this.posX + (0 - 0.5f) * this.width * 0.8f);
            final int floor_double3 = MathHelper.floor_double(this.posZ + (0 - 0.5f) * this.width * 0.8f);
            if (mutableBlockPos.getX() != floor_double2 || mutableBlockPos.getY() != floor_double || mutableBlockPos.getZ() != floor_double3) {
                mutableBlockPos.func_181079_c(floor_double2, floor_double, floor_double3);
                if (this.worldObj.getBlockState(mutableBlockPos).getBlock().isVisuallyOpaque()) {
                    break;
                }
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    @Override
    public Entity getCommandSenderEntity() {
        return this;
    }
    
    public void func_174817_o(final Entity entity) {
        this.cmdResultStats.func_179671_a(entity.getCommandStats());
    }
    
    public boolean canAttackWithItem() {
        return true;
    }
    
    protected final String getEntityString() {
        return EntityList.getEntityString(this);
    }
    
    public void setAlwaysRenderNameTag(final boolean b) {
        this.dataWatcher.updateObject(3, (byte)(byte)(b ? 1 : 0));
    }
    
    public EntityItem entityDropItem(final ItemStack itemStack, final float n) {
        if (itemStack.stackSize != 0 && itemStack.getItem() != null) {
            final EntityItem entityItem = new EntityItem(this.worldObj, this.posX, this.posY + n, this.posZ, itemStack);
            entityItem.setDefaultPickupDelay();
            this.worldObj.spawnEntityInWorld(entityItem);
            return entityItem;
        }
        return null;
    }
    
    public void onDataWatcherUpdate(final int n) {
    }
    
    public DataWatcher getDataWatcher() {
        return this.dataWatcher;
    }
    
    @Override
    public String getName() {
        if (this > 0) {
            return this.getCustomNameTag();
        }
        String entityString = EntityList.getEntityString(this);
        if (entityString == null) {
            entityString = "generic";
        }
        return StatCollector.translateToLocal("entity." + entityString + ".name");
    }
    
    @Override
    public Vec3 getPositionVector() {
        return new Vec3(this.posX, this.posY, this.posZ);
    }
    
    protected final Vec3 getVectorForRotation(final float n, final float n2) {
        final float cos = MathHelper.cos(-n2 * 0.017453292f - 3.1415927f);
        final float sin = MathHelper.sin(-n2 * 0.017453292f - 3.1415927f);
        final float n3 = -MathHelper.cos(-n * 0.017453292f);
        return new Vec3(sin * n3, MathHelper.sin(-n * 0.017453292f), cos * n3);
    }
    
    public void addVelocity(final double n, final double n2, final double n3) {
        this.motionX += n;
        this.motionY += n2;
        this.motionZ += n3;
        this.isAirBorne = true;
    }
    
    public Entity(final World worldObj) {
        this.entityId = Entity.nextEntityID++;
        this.renderDistanceWeight = 1.0;
        this.boundingBox = Entity.ZERO_AABB;
        this.width = 0.6f;
        this.height = 1.8f;
        this.nextStepDistance = 1;
        this.rand = new Random();
        this.fireResistance = 1;
        this.firstUpdate = true;
        this.entityUniqueID = MathHelper.getRandomUuid(this.rand);
        this.cmdResultStats = new CommandResultStats();
        this.worldObj = worldObj;
        this.setPosition(0.0, 0.0, 0.0);
        if (worldObj != null) {
            this.dimension = worldObj.provider.getDimensionId();
        }
        (this.dataWatcher = new DataWatcher(this)).addObject(0, 0);
        this.dataWatcher.addObject(1, 300);
        this.dataWatcher.addObject(3, 0);
        this.dataWatcher.addObject(2, "");
        this.dataWatcher.addObject(4, 0);
        this.entityInit();
    }
    
    public void moveFlying(float n, float n2, final float n3) {
        final float n4 = n * n + n2 * n2;
        if (n4 >= 1.0E-4f) {
            float sqrt_float = MathHelper.sqrt_float(n4);
            if (sqrt_float < 1.0f) {
                sqrt_float = 1.0f;
            }
            final float n5 = n3 / sqrt_float;
            n *= n5;
            n2 *= n5;
            final float sin = MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f);
            final float cos = MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f);
            this.motionX += n * cos - n2 * sin;
            this.motionZ += n2 * cos + n * sin;
        }
    }
    
    public AxisAlignedBB getCollisionBoundingBox() {
        return null;
    }
    
    public void onChunkLoad() {
    }
    
    public int getAir() {
        return this.dataWatcher.getWatchableObjectShort(1);
    }
    
    protected void setFlag(final int n, final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte(0);
        if (b) {
            this.dataWatcher.updateObject(0, (byte)(watchableObjectByte | 1 << n));
        }
        else {
            this.dataWatcher.updateObject(0, (byte)(watchableObjectByte & ~(1 << n)));
        }
    }
    
    public int getMaxInPortalTime() {
        return 0;
    }
    
    public boolean isInsideOfMaterial(final Material material) {
        final double n = this.posY + this.getEyeHeight();
        final BlockPos blockPos = new BlockPos(this.posX, n, this.posZ);
        final IBlockState blockState = this.worldObj.getBlockState(blockPos);
        if (blockState.getBlock().getMaterial() == material) {
            final boolean b = n < blockPos.getY() + 1 - (BlockLiquid.getLiquidHeightPercent(blockState.getBlock().getMetaFromState(blockState)) - 0.11111111f);
            return (b || !(this instanceof EntityPlayer)) && b;
        }
        return false;
    }
    
    private boolean isLiquidPresentInAABB(final AxisAlignedBB axisAlignedBB) {
        return this.worldObj.getCollidingBoundingBoxes(this, axisAlignedBB).isEmpty() && !this.worldObj.isAnyLiquid(axisAlignedBB);
    }
    
    protected void setSize(final float width, final float height) {
        if (width != this.width || height != this.height) {
            final float width2 = this.width;
            this.width = width;
            this.height = height;
            this.setEntityBoundingBox(new AxisAlignedBB(this.getEntityBoundingBox().minX, this.getEntityBoundingBox().minY, this.getEntityBoundingBox().minZ, this.getEntityBoundingBox().minX + this.width, this.getEntityBoundingBox().minY + this.height, this.getEntityBoundingBox().minZ + this.width));
            if (this.width > width2 && !this.firstUpdate && !this.worldObj.isRemote) {
                this.moveEntity(width2 - this.width, 0.0, width2 - this.width);
            }
        }
    }
    
    public double getDistance(final double n, final double n2, final double n3) {
        final double n4 = this.posX - n;
        final double n5 = this.posY - n2;
        final double n6 = this.posZ - n3;
        return MathHelper.sqrt_double(n4 * n4 + n5 * n5 + n6 * n6);
    }
    
    public boolean isInWater() {
        return this.inWater;
    }
    
    public boolean isOffsetPositionInLiquid(final double n, final double n2, final double n3) {
        return this.isLiquidPresentInAABB(this.getEntityBoundingBox().offset(n, n2, n3));
    }
    
    public boolean isBurning() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/entity/Entity.worldObj:Lnet/minecraft/world/World;
        //     4: ifnull          21
        //     7: aload_0        
        //     8: getfield        net/minecraft/entity/Entity.worldObj:Lnet/minecraft/world/World;
        //    11: getfield        net/minecraft/world/World.isRemote:Z
        //    14: ifeq            21
        //    17: iconst_1       
        //    18: goto            22
        //    21: iconst_0       
        //    22: istore_1       
        //    23: aload_0        
        //    24: getfield        net/minecraft/entity/Entity.isImmuneToFire:Z
        //    27: ifne            49
        //    30: aload_0        
        //    31: getfield        net/minecraft/entity/Entity.fire:I
        //    34: ifgt            45
        //    37: iload_1        
        //    38: ifeq            49
        //    41: aload_0        
        //    42: goto            49
        //    45: iconst_1       
        //    46: goto            50
        //    49: iconst_0       
        //    50: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0049 (coming from #0042).
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
    
    public boolean isInLava() {
        return this.worldObj.isMaterialInBB(this.getEntityBoundingBox().expand(-0.10000000149011612, -0.4000000059604645, -0.10000000149011612), Material.lava);
    }
    
    public boolean verifyExplosion(final Explosion explosion, final World world, final BlockPos blockPos, final IBlockState blockState, final float n) {
        return true;
    }
    
    public void setSilent(final boolean b) {
        this.dataWatcher.updateObject(4, (byte)(byte)(b ? 1 : 0));
    }
    
    public Vec3 func_181014_aG() {
        return this.field_181017_ao;
    }
    
    public String getCustomNameTag() {
        return this.dataWatcher.getWatchableObjectString(2);
    }
    
    public boolean getAlwaysRenderNameTagForRender() {
        return this.getAlwaysRenderNameTag();
    }
    
    public void setAngles(final float n, final float n2) {
        final float rotationPitch = this.rotationPitch;
        final float rotationYaw = this.rotationYaw;
        this.rotationYaw += (float)(n * 0.15);
        this.rotationPitch -= (float)(n2 * 0.15);
        this.rotationPitch = MathHelper.clamp_float(this.rotationPitch, -90.0f, 90.0f);
        this.prevRotationPitch += this.rotationPitch - rotationPitch;
        this.prevRotationYaw += this.rotationYaw - rotationYaw;
    }
    
    protected String getSwimSound() {
        return "game.neutral.swim";
    }
    
    public void performHurtAnimation() {
    }
    
    public NBTTagCompound getNBTTagCompound() {
        return null;
    }
    
    public void setPositionAndRotation(final double n, final double n2, final double n3, final float n4, final float n5) {
        this.posX = n;
        this.prevPosX = n;
        this.posY = n2;
        this.prevPosY = n2;
        this.posZ = n3;
        this.prevPosZ = n3;
        this.rotationYaw = n4;
        this.prevRotationYaw = n4;
        this.rotationPitch = n5;
        this.prevRotationPitch = n5;
        final double n6 = this.prevRotationYaw - n4;
        if (n6 < -180.0) {
            this.prevRotationYaw += 360.0f;
        }
        if (n6 >= 180.0) {
            this.prevRotationYaw -= 360.0f;
        }
        this.setPosition(this.posX, this.posY, this.posZ);
        this.setRotation(n4, n5);
    }
    
    public void setSprinting(final boolean b) {
        this.setFlag(3, b);
    }
    
    protected void applyEnchantments(final EntityLivingBase entityLivingBase, final Entity entity) {
        if (entity instanceof EntityLivingBase) {
            EnchantmentHelper.applyThornEnchantments((EntityLivingBase)entity, entityLivingBase);
        }
        EnchantmentHelper.applyArthropodEnchantments(entityLivingBase, entity);
    }
    
    public void applyEntityCollision(final Entity entity) {
        if (entity.riddenByEntity != this && entity.ridingEntity != this && !entity.noClip && !this.noClip) {
            final double n = entity.posX - this.posX;
            final double n2 = entity.posZ - this.posZ;
            final double abs_max = MathHelper.abs_max(n, n2);
            if (abs_max >= 0.009999999776482582) {
                final double n3 = MathHelper.sqrt_double(abs_max);
                final double n4 = n / n3;
                final double n5 = n2 / n3;
                double n6 = 1.0 / n3;
                if (n6 > 1.0) {
                    n6 = 1.0;
                }
                final double n7 = n4 * n6;
                final double n8 = n5 * n6;
                final double n9 = n7 * 0.05000000074505806;
                final double n10 = n8 * 0.05000000074505806;
                final double n11 = n9 * (1.0f - this.entityCollisionReduction);
                final double n12 = n10 * (1.0f - this.entityCollisionReduction);
                if (this.riddenByEntity == null) {
                    this.addVelocity(-n11, 0.0, -n12);
                }
                if (entity.riddenByEntity == null) {
                    entity.addVelocity(n11, 0.0, n12);
                }
            }
        }
    }
    
    protected void setRotation(final float n, final float n2) {
        this.rotationYaw = n % 360.0f;
        this.rotationPitch = n2 % 360.0f;
    }
    
    public void setInvisible(final boolean b) {
        this.setFlag(5, b);
    }
    
    public void setPositionAndUpdate(final double n, final double n2, final double n3) {
        this.setLocationAndAngles(n, n2, n3, this.rotationYaw, this.rotationPitch);
    }
    
    public void setLocationAndAngles(final double lastTickPosX, final double lastTickPosY, final double lastTickPosZ, final float rotationYaw, final float rotationPitch) {
        this.posX = lastTickPosX;
        this.prevPosX = lastTickPosX;
        this.lastTickPosX = lastTickPosX;
        this.posY = lastTickPosY;
        this.prevPosY = lastTickPosY;
        this.lastTickPosY = lastTickPosY;
        this.posZ = lastTickPosZ;
        this.prevPosZ = lastTickPosZ;
        this.lastTickPosZ = lastTickPosZ;
        this.rotationYaw = rotationYaw;
        this.rotationPitch = rotationPitch;
        this.setPosition(this.posX, this.posY, this.posZ);
    }
    
    public void addToPlayerScore(final Entity entity, final int n) {
    }
    
    public double getDistanceSqToEntity(final Entity entity) {
        final double n = this.posX - entity.posX;
        final double n2 = this.posY - entity.posY;
        final double n3 = this.posZ - entity.posZ;
        return n * n + n2 * n2 + n3 * n3;
    }
    
    public ItemStack[] getInventory() {
        return null;
    }
    
    protected void resetHeight() {
        float n = MathHelper.sqrt_double(this.motionX * this.motionX * 0.20000000298023224 + this.motionY * this.motionY + this.motionZ * this.motionZ * 0.20000000298023224) * 0.2f;
        if (n > 1.0f) {
            n = 1.0f;
        }
        this.playSound(this.getSplashSound(), n, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
        final float n2 = (float)MathHelper.floor_double(this.getEntityBoundingBox().minY);
        int n3 = 0;
        while (0 < 1.0f + this.width * 20.0f) {
            this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + (this.rand.nextFloat() * 2.0f - 1.0f) * this.width, n2 + 1.0f, this.posZ + (this.rand.nextFloat() * 2.0f - 1.0f) * this.width, this.motionX, this.motionY - this.rand.nextFloat() * 0.2f, this.motionZ, new int[0]);
            ++n3;
        }
        while (0 < 1.0f + this.width * 20.0f) {
            this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + (this.rand.nextFloat() * 2.0f - 1.0f) * this.width, n2 + 1.0f, this.posZ + (this.rand.nextFloat() * 2.0f - 1.0f) * this.width, this.motionX, this.motionY, this.motionZ, new int[0]);
            ++n3;
        }
    }
    
    public boolean replaceItemInInventory(final int n, final ItemStack itemStack) {
        return false;
    }
    
    @Override
    public void addChatMessage(final IChatComponent chatComponent) {
    }
    
    public boolean canBeCollidedWith() {
        return false;
    }
    
    public boolean isImmuneToExplosions() {
        return false;
    }
    
    public float getEyeHeight() {
        return this.height * 0.85f;
    }
    
    static {
        ZERO_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }
    
    public boolean isRiding() {
        return this.ridingEntity != null;
    }
    
    protected abstract void readEntityFromNBT(final NBTTagCompound p0);
    
    public void fall(final float n, final float n2) {
        if (this.riddenByEntity != null) {
            this.riddenByEntity.fall(n, n2);
        }
    }
    
    protected void kill() {
        this.setDead();
    }
    
    @Override
    public void setCommandStat(final CommandResultStats.Type type, final int n) {
        this.cmdResultStats.func_179672_a(this, type, n);
    }
    
    public void travelToDimension(final int dimension) {
        if (!this.worldObj.isRemote && !this.isDead) {
            this.worldObj.theProfiler.startSection("changeDimension");
            final MinecraftServer server = MinecraftServer.getServer();
            final int dimension2 = this.dimension;
            final WorldServer worldServerForDimension = server.worldServerForDimension(dimension2);
            WorldServer worldServer = server.worldServerForDimension(dimension);
            this.dimension = dimension;
            if (dimension2 == 1 && dimension == 1) {
                worldServer = server.worldServerForDimension(0);
                this.dimension = 0;
            }
            this.worldObj.removeEntity(this);
            this.isDead = false;
            this.worldObj.theProfiler.startSection("reposition");
            server.getConfigurationManager().transferEntityToWorld(this, dimension2, worldServerForDimension, worldServer);
            this.worldObj.theProfiler.endStartSection("reloading");
            final Entity entityByName = EntityList.createEntityByName(EntityList.getEntityString(this), worldServer);
            if (entityByName != null) {
                entityByName.copyDataFromOld(this);
                if (dimension2 == 1 && dimension == 1) {
                    entityByName.moveToBlockPosAndAngles(this.worldObj.getTopSolidOrLiquidBlock(worldServer.getSpawnPoint()), entityByName.rotationYaw, entityByName.rotationPitch);
                }
                worldServer.spawnEntityInWorld(entityByName);
            }
            this.isDead = true;
            this.worldObj.theProfiler.endSection();
            worldServerForDimension.resetUpdateEntityTick();
            worldServer.resetUpdateEntityTick();
            this.worldObj.theProfiler.endSection();
        }
    }
    
    public void mountEntity(final Entity ridingEntity) {
        this.entityRiderPitchDelta = 0.0;
        this.entityRiderYawDelta = 0.0;
        if (ridingEntity == null) {
            if (this.ridingEntity != null) {
                this.setLocationAndAngles(this.ridingEntity.posX, this.ridingEntity.getEntityBoundingBox().minY + this.ridingEntity.height, this.ridingEntity.posZ, this.rotationYaw, this.rotationPitch);
                this.ridingEntity.riddenByEntity = null;
            }
            this.ridingEntity = null;
        }
        else {
            if (this.ridingEntity != null) {
                this.ridingEntity.riddenByEntity = null;
            }
            if (ridingEntity != null) {
                for (Entity entity = ridingEntity.ridingEntity; entity != null; entity = entity.ridingEntity) {
                    if (entity == this) {
                        return;
                    }
                }
            }
            this.ridingEntity = ridingEntity;
            ridingEntity.riddenByEntity = this;
        }
    }
    
    public MovingObjectPosition rayTrace(final double n, final float n2) {
        final Vec3 positionEyes = this.getPositionEyes(n2);
        final Vec3 look = this.getLook(n2);
        return this.worldObj.rayTraceBlocks(positionEyes, positionEyes.addVector(look.xCoord * n, look.yCoord * n, look.zCoord * n), false, false, true);
    }
    
    public boolean handleWaterMovement() {
        if (this.worldObj.handleMaterialAcceleration(this.getEntityBoundingBox().expand(0.0, -0.4000000059604645, 0.0).contract(0.001, 0.001, 0.001), Material.water, this)) {
            if (!this.inWater && !this.firstUpdate) {
                this.resetHeight();
            }
            this.fallDistance = 0.0f;
            this.inWater = true;
            this.fire = 0;
        }
        else {
            this.inWater = false;
        }
        return this.inWater;
    }
    
    public double getDistanceSq(final BlockPos blockPos) {
        return blockPos.distanceSq(this.posX, this.posY, this.posZ);
    }
    
    public float getExplosionResistance(final Explosion explosion, final World world, final BlockPos blockPos, final IBlockState blockState) {
        return blockState.getBlock().getExplosionResistance(this);
    }
    
    private void resetPositionToBB() {
        this.posX = (this.getEntityBoundingBox().minX + this.getEntityBoundingBox().maxX) / 2.0;
        this.posY = this.getEntityBoundingBox().minY;
        this.posZ = (this.getEntityBoundingBox().minZ + this.getEntityBoundingBox().maxZ) / 2.0;
    }
    
    public void onKillCommand() {
        this.setDead();
    }
    
    public void setInWeb() {
        this.isInWeb = true;
        this.fallDistance = 0.0f;
    }
    
    public void setRotationYawHead(final float n) {
    }
    
    public void setEating(final boolean b) {
        this.setFlag(4, b);
    }
    
    @Override
    public IChatComponent getDisplayName() {
        final ChatComponentText chatComponentText = new ChatComponentText(this.getName());
        chatComponentText.getChatStyle().setChatHoverEvent(this.getHoverEvent());
        chatComponentText.getChatStyle().setInsertion(this.getUniqueID().toString());
        return chatComponentText;
    }
    
    public float getCollisionBorderSize() {
        return 0.1f;
    }
    
    public void setEntityBoundingBox(final AxisAlignedBB boundingBox) {
        this.boundingBox = boundingBox;
    }
    
    public float getBrightness(final float n) {
        final BlockPos blockPos = new BlockPos(this.posX, this.posY + this.getEyeHeight(), this.posZ);
        return this.worldObj.isBlockLoaded(blockPos) ? this.worldObj.getLightBrightness(blockPos) : 0.0f;
    }
    
    public UUID getUniqueID() {
        return this.entityUniqueID;
    }
    
    public void copyLocationAndAnglesFrom(final Entity entity) {
        this.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
    }
    
    public void copyDataFromOld(final Entity entity) {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        entity.writeToNBT(nbtTagCompound);
        this.readFromNBT(nbtTagCompound);
        this.timeUntilPortal = entity.timeUntilPortal;
        this.field_181016_an = entity.field_181016_an;
        this.field_181017_ao = entity.field_181017_ao;
        this.field_181018_ap = entity.field_181018_ap;
    }
    
    protected void dealFireDamage(final int n) {
        if (!this.isImmuneToFire) {
            this.attackEntityFrom(DamageSource.inFire, (float)n);
        }
    }
    
    public double getDistanceSqToCenter(final BlockPos blockPos) {
        return blockPos.distanceSqToCenter(this.posX, this.posY, this.posZ);
    }
    
    protected void playStepSound(final BlockPos blockPos, final Block block) {
        final Block.SoundType stepSound = block.stepSound;
        if (this.worldObj.getBlockState(blockPos.up()).getBlock() == Blocks.snow_layer) {
            final Block.SoundType stepSound2 = Blocks.snow_layer.stepSound;
            this.playSound(stepSound2.getStepSound(), stepSound2.getVolume() * 0.15f, stepSound2.getFrequency());
        }
        else if (!block.getMaterial().isLiquid()) {
            this.playSound(stepSound.getStepSound(), stepSound.getVolume() * 0.15f, stepSound.getFrequency());
        }
    }
    
    public void moveEntity(double calculateXOffset, double n, double calculateZOffset) {
        if (this.noClip) {
            this.setEntityBoundingBox(this.getEntityBoundingBox().offset(calculateXOffset, n, calculateZOffset));
            this.resetPositionToBB();
        }
        else {
            this.worldObj.theProfiler.startSection("move");
            final double posX = this.posX;
            final double posY = this.posY;
            final double posZ = this.posZ;
            if (this.isInWeb) {
                this.isInWeb = false;
                calculateXOffset *= 0.25;
                n *= 0.05000000074505806;
                calculateZOffset *= 0.25;
                this.motionX = 0.0;
                this.motionY = 0.0;
                this.motionZ = 0.0;
            }
            double n2 = calculateXOffset;
            final double n3 = n;
            double n4 = calculateZOffset;
            final boolean b = this.onGround && this.isSneaking() && this instanceof EntityPlayer;
            if (b) {
                final double n5 = 0.05;
                while (calculateXOffset != 0.0 && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox().offset(calculateXOffset, -1.0, 0.0)).isEmpty()) {
                    if (calculateXOffset < n5 && calculateXOffset >= -n5) {
                        calculateXOffset = 0.0;
                    }
                    else if (calculateXOffset > 0.0) {
                        calculateXOffset -= n5;
                    }
                    else {
                        calculateXOffset += n5;
                    }
                    n2 = calculateXOffset;
                }
                while (calculateZOffset != 0.0 && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox().offset(0.0, -1.0, calculateZOffset)).isEmpty()) {
                    if (calculateZOffset < n5 && calculateZOffset >= -n5) {
                        calculateZOffset = 0.0;
                    }
                    else if (calculateZOffset > 0.0) {
                        calculateZOffset -= n5;
                    }
                    else {
                        calculateZOffset += n5;
                    }
                    n4 = calculateZOffset;
                }
                while (calculateXOffset != 0.0 && calculateZOffset != 0.0 && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox().offset(calculateXOffset, -1.0, calculateZOffset)).isEmpty()) {
                    if (calculateXOffset < n5 && calculateXOffset >= -n5) {
                        calculateXOffset = 0.0;
                    }
                    else if (calculateXOffset > 0.0) {
                        calculateXOffset -= n5;
                    }
                    else {
                        calculateXOffset += n5;
                    }
                    n2 = calculateXOffset;
                    if (calculateZOffset < n5 && calculateZOffset >= -n5) {
                        calculateZOffset = 0.0;
                    }
                    else if (calculateZOffset > 0.0) {
                        calculateZOffset -= n5;
                    }
                    else {
                        calculateZOffset += n5;
                    }
                    n4 = calculateZOffset;
                }
            }
            final List collidingBoundingBoxes = this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox().addCoord(calculateXOffset, n, calculateZOffset));
            final AxisAlignedBB entityBoundingBox = this.getEntityBoundingBox();
            final Iterator<AxisAlignedBB> iterator = collidingBoundingBoxes.iterator();
            while (iterator.hasNext()) {
                n = iterator.next().calculateYOffset(this.getEntityBoundingBox(), n);
            }
            this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0, n, 0.0));
            final boolean b2 = this.onGround || (n3 != n && n3 < 0.0);
            final Iterator<AxisAlignedBB> iterator2 = collidingBoundingBoxes.iterator();
            while (iterator2.hasNext()) {
                calculateXOffset = iterator2.next().calculateXOffset(this.getEntityBoundingBox(), calculateXOffset);
            }
            this.setEntityBoundingBox(this.getEntityBoundingBox().offset(calculateXOffset, 0.0, 0.0));
            final Iterator<AxisAlignedBB> iterator3 = collidingBoundingBoxes.iterator();
            while (iterator3.hasNext()) {
                calculateZOffset = iterator3.next().calculateZOffset(this.getEntityBoundingBox(), calculateZOffset);
            }
            this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0, 0.0, calculateZOffset));
            if (this.stepHeight > 0.0f && b2 && (n2 != calculateXOffset || n4 != calculateZOffset)) {
                final double n6 = calculateXOffset;
                final double n7 = n;
                final double n8 = calculateZOffset;
                final AxisAlignedBB entityBoundingBox2 = this.getEntityBoundingBox();
                this.setEntityBoundingBox(entityBoundingBox);
                n = this.stepHeight;
                final List collidingBoundingBoxes2 = this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox().addCoord(n2, n, n4));
                final AxisAlignedBB entityBoundingBox3 = this.getEntityBoundingBox();
                final AxisAlignedBB addCoord = entityBoundingBox3.addCoord(n2, 0.0, n4);
                double calculateYOffset = n;
                final Iterator<AxisAlignedBB> iterator4 = collidingBoundingBoxes2.iterator();
                while (iterator4.hasNext()) {
                    calculateYOffset = iterator4.next().calculateYOffset(addCoord, calculateYOffset);
                }
                final AxisAlignedBB offset = entityBoundingBox3.offset(0.0, calculateYOffset, 0.0);
                double calculateXOffset2 = n2;
                final Iterator<AxisAlignedBB> iterator5 = collidingBoundingBoxes2.iterator();
                while (iterator5.hasNext()) {
                    calculateXOffset2 = iterator5.next().calculateXOffset(offset, calculateXOffset2);
                }
                final AxisAlignedBB offset2 = offset.offset(calculateXOffset2, 0.0, 0.0);
                double calculateZOffset2 = n4;
                final Iterator<AxisAlignedBB> iterator6 = collidingBoundingBoxes2.iterator();
                while (iterator6.hasNext()) {
                    calculateZOffset2 = iterator6.next().calculateZOffset(offset2, calculateZOffset2);
                }
                final AxisAlignedBB offset3 = offset2.offset(0.0, 0.0, calculateZOffset2);
                final AxisAlignedBB entityBoundingBox4 = this.getEntityBoundingBox();
                double calculateYOffset2 = n;
                final Iterator<AxisAlignedBB> iterator7 = collidingBoundingBoxes2.iterator();
                while (iterator7.hasNext()) {
                    calculateYOffset2 = iterator7.next().calculateYOffset(entityBoundingBox4, calculateYOffset2);
                }
                final AxisAlignedBB offset4 = entityBoundingBox4.offset(0.0, calculateYOffset2, 0.0);
                double calculateXOffset3 = n2;
                final Iterator<AxisAlignedBB> iterator8 = collidingBoundingBoxes2.iterator();
                while (iterator8.hasNext()) {
                    calculateXOffset3 = iterator8.next().calculateXOffset(offset4, calculateXOffset3);
                }
                final AxisAlignedBB offset5 = offset4.offset(calculateXOffset3, 0.0, 0.0);
                double calculateZOffset3 = n4;
                final Iterator<AxisAlignedBB> iterator9 = collidingBoundingBoxes2.iterator();
                while (iterator9.hasNext()) {
                    calculateZOffset3 = iterator9.next().calculateZOffset(offset5, calculateZOffset3);
                }
                final AxisAlignedBB offset6 = offset5.offset(0.0, 0.0, calculateZOffset3);
                if (calculateXOffset2 * calculateXOffset2 + calculateZOffset2 * calculateZOffset2 > calculateXOffset3 * calculateXOffset3 + calculateZOffset3 * calculateZOffset3) {
                    calculateXOffset = calculateXOffset2;
                    calculateZOffset = calculateZOffset2;
                    n = -calculateYOffset;
                    this.setEntityBoundingBox(offset3);
                }
                else {
                    calculateXOffset = calculateXOffset3;
                    calculateZOffset = calculateZOffset3;
                    n = -calculateYOffset2;
                    this.setEntityBoundingBox(offset6);
                }
                final Iterator<AxisAlignedBB> iterator10 = collidingBoundingBoxes2.iterator();
                while (iterator10.hasNext()) {
                    n = iterator10.next().calculateYOffset(this.getEntityBoundingBox(), n);
                }
                this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0, n, 0.0));
                if (n6 * n6 + n8 * n8 >= calculateXOffset * calculateXOffset + calculateZOffset * calculateZOffset) {
                    calculateXOffset = n6;
                    n = n7;
                    calculateZOffset = n8;
                    this.setEntityBoundingBox(entityBoundingBox2);
                }
            }
            this.worldObj.theProfiler.endSection();
            this.worldObj.theProfiler.startSection("rest");
            this.resetPositionToBB();
            this.isCollidedHorizontally = (n2 != calculateXOffset || n4 != calculateZOffset);
            this.isCollidedVertically = (n3 != n);
            this.onGround = (this.isCollidedVertically && n3 < 0.0);
            this.isCollided = (this.isCollidedHorizontally || this.isCollidedVertically);
            BlockPos down = new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - 0.20000000298023224), MathHelper.floor_double(this.posZ));
            Block block = this.worldObj.getBlockState(down).getBlock();
            if (block.getMaterial() == Material.air) {
                final Block block2 = this.worldObj.getBlockState(down.down()).getBlock();
                if (block2 instanceof BlockFence || block2 instanceof BlockWall || block2 instanceof BlockFenceGate) {
                    block = block2;
                    down = down.down();
                }
            }
            this.updateFallState(n, this.onGround, block, down);
            if (n2 != calculateXOffset) {
                this.motionX = 0.0;
            }
            if (n4 != calculateZOffset) {
                this.motionZ = 0.0;
            }
            if (n3 != n) {
                block.onLanded(this.worldObj, this);
            }
            if (this.canTriggerWalking() && !b && this.ridingEntity == null) {
                final double n9 = this.posX - posX;
                double n10 = this.posY - posY;
                final double n11 = this.posZ - posZ;
                if (block != Blocks.ladder) {
                    n10 = 0.0;
                }
                if (block != null && this.onGround) {
                    block.onEntityCollidedWithBlock(this.worldObj, down, this);
                }
                this.distanceWalkedModified += (float)(MathHelper.sqrt_double(n9 * n9 + n11 * n11) * 0.6);
                this.distanceWalkedOnStepModified += (float)(MathHelper.sqrt_double(n9 * n9 + n10 * n10 + n11 * n11) * 0.6);
                if (this.distanceWalkedOnStepModified > this.nextStepDistance && block.getMaterial() != Material.air) {
                    this.nextStepDistance = (int)this.distanceWalkedOnStepModified + 1;
                    if (this.isInWater()) {
                        float n12 = MathHelper.sqrt_double(this.motionX * this.motionX * 0.20000000298023224 + this.motionY * this.motionY + this.motionZ * this.motionZ * 0.20000000298023224) * 0.35f;
                        if (n12 > 1.0f) {
                            n12 = 1.0f;
                        }
                        this.playSound(this.getSwimSound(), n12, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                    }
                    this.playStepSound(down, block);
                }
            }
            this.doBlockCollisions();
            final boolean wet = this.isWet();
            if (this.worldObj.isFlammableWithin(this.getEntityBoundingBox().contract(0.001, 0.001, 0.001))) {
                this.dealFireDamage(1);
                if (!wet) {
                    ++this.fire;
                    if (this.fire == 0) {
                        this.setFire(8);
                    }
                }
            }
            else if (this.fire <= 0) {
                this.fire = -this.fireResistance;
            }
            if (wet && this.fire > 0) {
                this.playSound("random.fizz", 0.7f, 1.6f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                this.fire = -this.fireResistance;
            }
            this.worldObj.theProfiler.endSection();
        }
    }
    
    public void setVelocity(final double motionX, final double motionY, final double motionZ) {
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
    }
    
    public void func_181013_g(final float n) {
    }
    
    public boolean isInRangeToRender3d(final double n, final double n2, final double n3) {
        final double n4 = this.posX - n;
        final double n5 = this.posY - n2;
        final double n6 = this.posZ - n3;
        return this.isInRangeToRenderDist(n4 * n4 + n5 * n5 + n6 * n6);
    }
    
    public Entity[] getParts() {
        return null;
    }
    
    public void setCurrentItemOrArmor(final int n, final ItemStack itemStack) {
    }
    
    public void func_181015_d(final BlockPos field_181016_an) {
        if (this.timeUntilPortal > 0) {
            this.timeUntilPortal = this.getPortalCooldown();
        }
        else {
            if (!this.worldObj.isRemote && !field_181016_an.equals(this.field_181016_an)) {
                this.field_181016_an = field_181016_an;
                final BlockPattern.PatternHelper func_181089_f = Blocks.portal.func_181089_f(this.worldObj, field_181016_an);
                final double n = (func_181089_f.getFinger().getAxis() == EnumFacing.Axis.X) ? func_181089_f.func_181117_a().getZ() : ((double)func_181089_f.func_181117_a().getX());
                this.field_181017_ao = new Vec3(Math.abs(MathHelper.func_181160_c(((func_181089_f.getFinger().getAxis() == EnumFacing.Axis.X) ? this.posZ : this.posX) - (double)((func_181089_f.getFinger().rotateY().getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE) ? 1 : 0), n, n - func_181089_f.func_181118_d())), MathHelper.func_181160_c(this.posY - 1.0, func_181089_f.func_181117_a().getY(), func_181089_f.func_181117_a().getY() - func_181089_f.func_181119_e()), 0.0);
                this.field_181018_ap = func_181089_f.getFinger();
            }
            this.inPortal = true;
        }
    }
    
    public void playSound(final String p0, final float p1, final float p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_icmpne       15
        //     4: aload_0        
        //     5: getfield        net/minecraft/entity/Entity.worldObj:Lnet/minecraft/world/World;
        //     8: aload_0        
        //     9: aload_1        
        //    10: fload_2        
        //    11: fload_3        
        //    12: invokevirtual   net/minecraft/world/World.playSoundAtEntity:(Lnet/minecraft/entity/Entity;Ljava/lang/String;FF)V
        //    15: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    protected void updateFallState(final double n, final boolean b, final Block block, final BlockPos blockPos) {
        if (b) {
            if (this.fallDistance > 0.0f) {
                if (block != null) {
                    block.onFallenUpon(this.worldObj, blockPos, this, this.fallDistance);
                }
                else {
                    this.fall(this.fallDistance, 1.0f);
                }
                this.fallDistance = 0.0f;
            }
        }
        else if (n < 0.0) {
            this.fallDistance -= (float)n;
        }
    }
    
    protected boolean canTriggerWalking() {
        return true;
    }
    
    public boolean doesEntityNotTriggerPressurePlate() {
        return false;
    }
    
    public void setPositionAndRotation2(final double n, double n2, final double n3, final float n4, final float n5, final int n6, final boolean b) {
        this.setPosition(n, n2, n3);
        this.setRotation(n4, n5);
        final List collidingBoundingBoxes = this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox().contract(0.03125, 0.0, 0.03125));
        if (!collidingBoundingBoxes.isEmpty()) {
            double maxY = 0.0;
            for (final AxisAlignedBB axisAlignedBB : collidingBoundingBoxes) {
                if (axisAlignedBB.maxY > maxY) {
                    maxY = axisAlignedBB.maxY;
                }
            }
            n2 += maxY - this.getEntityBoundingBox().minY;
            this.setPosition(n, n2, n3);
        }
    }
    
    public boolean isEntityEqual(final Entity entity) {
        return this == entity;
    }
    
    public void setFire(final int n) {
        final int fireTimeForEntity = EnchantmentProtection.getFireTimeForEntity(this, n * 20);
        if (this.fire < fireTimeForEntity) {
            this.fire = fireTimeForEntity;
        }
    }
    
    public int getBrightnessForRender(final float n) {
        final BlockPos blockPos = new BlockPos(this.posX, this.posY + this.getEyeHeight(), this.posZ);
        return this.worldObj.isBlockLoaded(blockPos) ? this.worldObj.getCombinedLight(blockPos, 0) : 0;
    }
    
    protected boolean shouldSetPosAfterLoading() {
        return true;
    }
    
    public boolean canBePushed() {
        return false;
    }
    
    public void onCollideWithPlayer(final EntityPlayer entityPlayer) {
    }
    
    protected NBTTagList newFloatNBTList(final float... array) {
        final NBTTagList list = new NBTTagList();
        while (0 < array.length) {
            list.appendTag(new NBTTagFloat(array[0]));
            int n = 0;
            ++n;
        }
        return list;
    }
    
    public void moveToBlockPosAndAngles(final BlockPos blockPos, final float n, final float n2) {
        this.setLocationAndAngles(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, n, n2);
    }
    
    public CommandResultStats getCommandStats() {
        return this.cmdResultStats;
    }
    
    public float getRotationYawHead() {
        return 0.0f;
    }
    
    @Override
    public String toString() {
        return String.format("%s['%s'/%d, l='%s', x=%.2f, y=%.2f, z=%.2f]", this.getClass().getSimpleName(), this.getName(), this.entityId, (this.worldObj == null) ? "~NULL~" : this.worldObj.getWorldInfo().getWorldName(), this.posX, this.posY, this.posZ);
    }
    
    public void setSneaking(final boolean b) {
        this.setFlag(1, b);
    }
    
    protected abstract void writeEntityToNBT(final NBTTagCompound p0);
    
    public Vec3 getLook(final float n) {
        if (n == 1.0f) {
            return this.getVectorForRotation(this.rotationPitch, this.rotationYaw);
        }
        return this.getVectorForRotation(this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * n, this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * n);
    }
    
    public EnumFacing getHorizontalFacing() {
        return EnumFacing.getHorizontal(MathHelper.floor_double(this.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3);
    }
    
    public boolean isEating() {
        return this.getFlag(4);
    }
    
    public void onUpdate() {
        this.onEntityUpdate();
    }
    
    public void setAir(final int n) {
        this.dataWatcher.updateObject(1, (short)n);
    }
    
    public boolean isWet() {
        return this.inWater || this.worldObj.canLightningStrike(new BlockPos(this.posX, this.posY, this.posZ)) || this.worldObj.canLightningStrike(new BlockPos(this.posX, this.posY + this.height, this.posZ));
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof Entity && ((Entity)o).entityId == this.entityId;
    }
    
    public EntityItem dropItemWithOffset(final Item item, final int n, final float n2) {
        return this.entityDropItem(new ItemStack(item, n, 0), n2);
    }
    
    public boolean canRenderOnFire() {
        return this.isBurning();
    }
    
    protected void createRunningParticles() {
        final IBlockState blockState = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - 0.20000000298023224), MathHelper.floor_double(this.posZ)));
        if (blockState.getBlock().getRenderType() != -1) {
            this.worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + (this.rand.nextFloat() - 0.5) * this.width, this.getEntityBoundingBox().minY + 0.1, this.posZ + (this.rand.nextFloat() - 0.5) * this.width, -this.motionX * 4.0, 1.5, -this.motionZ * 4.0, Block.getStateId(blockState));
        }
    }
    
    @Override
    public boolean sendCommandFeedback() {
        return false;
    }
    
    @Override
    public BlockPos getPosition() {
        return new BlockPos(this.posX, this.posY + 0.5, this.posZ);
    }
    
    public Vec3 getLookVec() {
        return null;
    }
    
    public void setDead() {
        this.isDead = true;
    }
    
    protected void preparePlayerToSpawn() {
        if (this.worldObj != null) {
            while (this.posY > 0.0 && this.posY < 256.0) {
                this.setPosition(this.posX, this.posY, this.posZ);
                if (this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty()) {
                    break;
                }
                ++this.posY;
            }
            final double motionX = 0.0;
            this.motionZ = motionX;
            this.motionY = motionX;
            this.motionX = motionX;
            this.rotationPitch = 0.0f;
        }
    }
    
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        final NBTTagList tagList = nbtTagCompound.getTagList("Pos", 6);
        final NBTTagList tagList2 = nbtTagCompound.getTagList("Motion", 6);
        final NBTTagList tagList3 = nbtTagCompound.getTagList("Rotation", 5);
        this.motionX = tagList2.getDoubleAt(0);
        this.motionY = tagList2.getDoubleAt(1);
        this.motionZ = tagList2.getDoubleAt(2);
        if (Math.abs(this.motionX) > 10.0) {
            this.motionX = 0.0;
        }
        if (Math.abs(this.motionY) > 10.0) {
            this.motionY = 0.0;
        }
        if (Math.abs(this.motionZ) > 10.0) {
            this.motionZ = 0.0;
        }
        final double double1 = tagList.getDoubleAt(0);
        this.posX = double1;
        this.lastTickPosX = double1;
        this.prevPosX = double1;
        final double double2 = tagList.getDoubleAt(1);
        this.posY = double2;
        this.lastTickPosY = double2;
        this.prevPosY = double2;
        final double double3 = tagList.getDoubleAt(2);
        this.posZ = double3;
        this.lastTickPosZ = double3;
        this.prevPosZ = double3;
        final float float1 = tagList3.getFloatAt(0);
        this.rotationYaw = float1;
        this.prevRotationYaw = float1;
        final float float2 = tagList3.getFloatAt(1);
        this.rotationPitch = float2;
        this.prevRotationPitch = float2;
        this.setRotationYawHead(this.rotationYaw);
        this.func_181013_g(this.rotationYaw);
        this.fallDistance = nbtTagCompound.getFloat("FallDistance");
        this.fire = nbtTagCompound.getShort("Fire");
        this.setAir(nbtTagCompound.getShort("Air"));
        this.onGround = nbtTagCompound.getBoolean("OnGround");
        this.dimension = nbtTagCompound.getInteger("Dimension");
        this.invulnerable = nbtTagCompound.getBoolean("Invulnerable");
        this.timeUntilPortal = nbtTagCompound.getInteger("PortalCooldown");
        if (nbtTagCompound.hasKey("UUIDMost", 4) && nbtTagCompound.hasKey("UUIDLeast", 4)) {
            this.entityUniqueID = new UUID(nbtTagCompound.getLong("UUIDMost"), nbtTagCompound.getLong("UUIDLeast"));
        }
        else if (nbtTagCompound.hasKey("UUID", 8)) {
            this.entityUniqueID = UUID.fromString(nbtTagCompound.getString("UUID"));
        }
        this.setPosition(this.posX, this.posY, this.posZ);
        this.setRotation(this.rotationYaw, this.rotationPitch);
        if (nbtTagCompound.hasKey("CustomName", 8) && nbtTagCompound.getString("CustomName").length() > 0) {
            this.setCustomNameTag(nbtTagCompound.getString("CustomName"));
        }
        this.setAlwaysRenderNameTag(nbtTagCompound.getBoolean("CustomNameVisible"));
        this.cmdResultStats.readStatsFromNBT(nbtTagCompound);
        this.setSilent(nbtTagCompound.getBoolean("Silent"));
        this.readEntityFromNBT(nbtTagCompound);
        if (this.shouldSetPosAfterLoading()) {
            this.setPosition(this.posX, this.posY, this.posZ);
        }
    }
    
    public void updateRidden() {
        if (this.ridingEntity.isDead) {
            this.ridingEntity = null;
        }
        else {
            this.motionX = 0.0;
            this.motionY = 0.0;
            this.motionZ = 0.0;
            this.onUpdate();
            if (this.ridingEntity != null) {
                this.ridingEntity.updateRiderPosition();
                this.entityRiderYawDelta += this.ridingEntity.rotationYaw - this.ridingEntity.prevRotationYaw;
                this.entityRiderPitchDelta += this.ridingEntity.rotationPitch - this.ridingEntity.prevRotationPitch;
                while (this.entityRiderYawDelta >= 180.0) {
                    this.entityRiderYawDelta -= 360.0;
                }
                while (this.entityRiderYawDelta < -180.0) {
                    this.entityRiderYawDelta += 360.0;
                }
                while (this.entityRiderPitchDelta >= 180.0) {
                    this.entityRiderPitchDelta -= 360.0;
                }
                while (this.entityRiderPitchDelta < -180.0) {
                    this.entityRiderPitchDelta += 360.0;
                }
                double n = this.entityRiderYawDelta * 0.5;
                double n2 = this.entityRiderPitchDelta * 0.5;
                final float n3 = 10.0f;
                if (n > n3) {
                    n = n3;
                }
                if (n < -n3) {
                    n = -n3;
                }
                if (n2 > n3) {
                    n2 = n3;
                }
                if (n2 < -n3) {
                    n2 = -n3;
                }
                this.entityRiderYawDelta -= n;
                this.entityRiderPitchDelta -= n2;
            }
        }
    }
    
    public void setPosition(final double posX, final double posY, final double posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        final float n = this.width / 2.0f;
        this.setEntityBoundingBox(new AxisAlignedBB(posX - n, posY, posZ - n, posX + n, posY + this.height, posZ + n));
    }
    
    protected NBTTagList newDoubleNBTList(final double... array) {
        final NBTTagList list = new NBTTagList();
        while (0 < array.length) {
            list.appendTag(new NBTTagDouble(array[0]));
            int n = 0;
            ++n;
        }
        return list;
    }
    
    protected HoverEvent getHoverEvent() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        final String entityString = EntityList.getEntityString(this);
        nbtTagCompound.setString("id", this.getUniqueID().toString());
        if (entityString != null) {
            nbtTagCompound.setString("type", entityString);
        }
        nbtTagCompound.setString("name", this.getName());
        return new HoverEvent(HoverEvent.Action.SHOW_ENTITY, new ChatComponentText(nbtTagCompound.toString()));
    }
    
    protected abstract void entityInit();
    
    public boolean isPushedByWater() {
        return true;
    }
    
    public boolean writeToNBTOptional(final NBTTagCompound nbtTagCompound) {
        final String entityString = this.getEntityString();
        if (!this.isDead && entityString != null && this.riddenByEntity == null) {
            nbtTagCompound.setString("id", entityString);
            this.writeToNBT(nbtTagCompound);
            return true;
        }
        return false;
    }
    
    public void setEntityId(final int entityId) {
        this.entityId = entityId;
    }
    
    public void onEntityUpdate() {
        this.worldObj.theProfiler.startSection("entityBaseTick");
        if (this.ridingEntity != null && this.ridingEntity.isDead) {
            this.ridingEntity = null;
        }
        this.prevDistanceWalkedModified = this.distanceWalkedModified;
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.prevRotationPitch = this.rotationPitch;
        this.prevRotationYaw = this.rotationYaw;
        if (!this.worldObj.isRemote && this.worldObj instanceof WorldServer) {
            this.worldObj.theProfiler.startSection("portal");
            final MinecraftServer minecraftServer = ((WorldServer)this.worldObj).getMinecraftServer();
            final int maxInPortalTime = this.getMaxInPortalTime();
            if (this.inPortal) {
                if (minecraftServer.getAllowNether()) {
                    if (this.ridingEntity == null && this.portalCounter++ >= maxInPortalTime) {
                        this.portalCounter = maxInPortalTime;
                        this.timeUntilPortal = this.getPortalCooldown();
                        if (this.worldObj.provider.getDimensionId() == -1) {}
                        this.travelToDimension(-1);
                    }
                    this.inPortal = false;
                }
            }
            else {
                if (this.portalCounter > 0) {
                    this.portalCounter -= 4;
                }
                if (this.portalCounter < 0) {
                    this.portalCounter = 0;
                }
            }
            if (this.timeUntilPortal > 0) {
                --this.timeUntilPortal;
            }
            this.worldObj.theProfiler.endSection();
        }
        this.spawnRunningParticles();
        this.handleWaterMovement();
        if (this.worldObj.isRemote) {
            this.fire = 0;
        }
        else if (this.fire > 0) {
            if (this.isImmuneToFire) {
                this.fire -= 4;
                if (this.fire < 0) {
                    this.fire = 0;
                }
            }
            else {
                if (this.fire % 20 == 0) {
                    this.attackEntityFrom(DamageSource.onFire, 1.0f);
                }
                --this.fire;
            }
        }
        if (this.isInLava()) {
            this.setOnFireFromLava();
            this.fallDistance *= 0.5f;
        }
        if (this.posY < -64.0) {
            this.kill();
        }
        if (!this.worldObj.isRemote) {
            this.setFlag(0, this.fire > 0);
        }
        this.firstUpdate = false;
        this.worldObj.theProfiler.endSection();
    }
    
    public void setCustomNameTag(final String s) {
        this.dataWatcher.updateObject(2, s);
    }
    
    public AxisAlignedBB getEntityBoundingBox() {
        return this.boundingBox;
    }
    
    public double getYOffset() {
        return 0.0;
    }
    
    public void extinguish() {
        this.fire = 0;
    }
    
    public float getDistanceToEntity(final Entity entity) {
        final float n = (float)(this.posX - entity.posX);
        final float n2 = (float)(this.posY - entity.posY);
        final float n3 = (float)(this.posZ - entity.posZ);
        return MathHelper.sqrt_float(n * n + n2 * n2 + n3 * n3);
    }
    
    public double getMountedYOffset() {
        return this.height * 0.75;
    }
    
    public void spawnRunningParticles() {
        if (this.isSprinting() && !this.isInWater()) {
            this.createRunningParticles();
        }
    }
    
    public int getPortalCooldown() {
        return 300;
    }
    
    public void handleStatusUpdate(final byte b) {
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final int n, final String s) {
        return true;
    }
    
    @Override
    public int hashCode() {
        return this.entityId;
    }
    
    public boolean isInvisible() {
        return this.getFlag(5);
    }
    
    public void addEntityCrashInfo(final CrashReportCategory crashReportCategory) {
        crashReportCategory.addCrashSectionCallable("Entity Type", new Callable(this) {
            final Entity this$0;
            
            @Override
            public String call() throws Exception {
                return EntityList.getEntityString(this.this$0) + " (" + this.this$0.getClass().getCanonicalName() + ")";
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
        });
        crashReportCategory.addCrashSection("Entity ID", this.entityId);
        crashReportCategory.addCrashSectionCallable("Entity Name", new Callable(this) {
            final Entity this$0;
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            @Override
            public String call() throws Exception {
                return this.this$0.getName();
            }
        });
        crashReportCategory.addCrashSection("Entity's Exact location", String.format("%.2f, %.2f, %.2f", this.posX, this.posY, this.posZ));
        crashReportCategory.addCrashSection("Entity's Block location", CrashReportCategory.getCoordinateInfo(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)));
        crashReportCategory.addCrashSection("Entity's Momentum", String.format("%.2f, %.2f, %.2f", this.motionX, this.motionY, this.motionZ));
        crashReportCategory.addCrashSectionCallable("Entity's Rider", new Callable(this) {
            final Entity this$0;
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            @Override
            public String call() throws Exception {
                return this.this$0.riddenByEntity.toString();
            }
        });
        crashReportCategory.addCrashSectionCallable("Entity's Vehicle", new Callable(this) {
            final Entity this$0;
            
            @Override
            public String call() throws Exception {
                return this.this$0.ridingEntity.toString();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
        });
    }
    
    public void clientUpdateEntityNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    public boolean hitByEntity(final Entity entity) {
        return false;
    }
    
    public Vec3 getPositionEyes(final float n) {
        if (n == 1.0f) {
            return new Vec3(this.posX, this.posY + this.getEyeHeight(), this.posZ);
        }
        return new Vec3(this.prevPosX + (this.posX - this.prevPosX) * n, this.prevPosY + (this.posY - this.prevPosY) * n + this.getEyeHeight(), this.prevPosZ + (this.posZ - this.prevPosZ) * n);
    }
    
    public boolean interactFirst(final EntityPlayer entityPlayer) {
        return false;
    }
    
    public boolean isSpectatedByPlayer(final EntityPlayerMP entityPlayerMP) {
        return true;
    }
    
    public void writeToNBT(final NBTTagCompound p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ldc_w           "Pos"
        //     4: aload_0        
        //     5: iconst_3       
        //     6: newarray        D
        //     8: dup            
        //     9: iconst_0       
        //    10: aload_0        
        //    11: getfield        net/minecraft/entity/Entity.posX:D
        //    14: dastore        
        //    15: dup            
        //    16: iconst_1       
        //    17: aload_0        
        //    18: getfield        net/minecraft/entity/Entity.posY:D
        //    21: dastore        
        //    22: dup            
        //    23: iconst_2       
        //    24: aload_0        
        //    25: getfield        net/minecraft/entity/Entity.posZ:D
        //    28: dastore        
        //    29: invokevirtual   net/minecraft/entity/Entity.newDoubleNBTList:([D)Lnet/minecraft/nbt/NBTTagList;
        //    32: invokevirtual   net/minecraft/nbt/NBTTagCompound.setTag:(Ljava/lang/String;Lnet/minecraft/nbt/NBTBase;)V
        //    35: aload_1        
        //    36: ldc_w           "Motion"
        //    39: aload_0        
        //    40: iconst_3       
        //    41: newarray        D
        //    43: dup            
        //    44: iconst_0       
        //    45: aload_0        
        //    46: getfield        net/minecraft/entity/Entity.motionX:D
        //    49: dastore        
        //    50: dup            
        //    51: iconst_1       
        //    52: aload_0        
        //    53: getfield        net/minecraft/entity/Entity.motionY:D
        //    56: dastore        
        //    57: dup            
        //    58: iconst_2       
        //    59: aload_0        
        //    60: getfield        net/minecraft/entity/Entity.motionZ:D
        //    63: dastore        
        //    64: invokevirtual   net/minecraft/entity/Entity.newDoubleNBTList:([D)Lnet/minecraft/nbt/NBTTagList;
        //    67: invokevirtual   net/minecraft/nbt/NBTTagCompound.setTag:(Ljava/lang/String;Lnet/minecraft/nbt/NBTBase;)V
        //    70: aload_1        
        //    71: ldc_w           "Rotation"
        //    74: aload_0        
        //    75: iconst_2       
        //    76: newarray        F
        //    78: dup            
        //    79: iconst_0       
        //    80: aload_0        
        //    81: getfield        net/minecraft/entity/Entity.rotationYaw:F
        //    84: fastore        
        //    85: dup            
        //    86: iconst_1       
        //    87: aload_0        
        //    88: getfield        net/minecraft/entity/Entity.rotationPitch:F
        //    91: fastore        
        //    92: invokevirtual   net/minecraft/entity/Entity.newFloatNBTList:([F)Lnet/minecraft/nbt/NBTTagList;
        //    95: invokevirtual   net/minecraft/nbt/NBTTagCompound.setTag:(Ljava/lang/String;Lnet/minecraft/nbt/NBTBase;)V
        //    98: aload_1        
        //    99: ldc_w           "FallDistance"
        //   102: aload_0        
        //   103: getfield        net/minecraft/entity/Entity.fallDistance:F
        //   106: invokevirtual   net/minecraft/nbt/NBTTagCompound.setFloat:(Ljava/lang/String;F)V
        //   109: aload_1        
        //   110: ldc_w           "Fire"
        //   113: aload_0        
        //   114: getfield        net/minecraft/entity/Entity.fire:I
        //   117: i2s            
        //   118: invokevirtual   net/minecraft/nbt/NBTTagCompound.setShort:(Ljava/lang/String;S)V
        //   121: aload_1        
        //   122: ldc_w           "Air"
        //   125: aload_0        
        //   126: invokevirtual   net/minecraft/entity/Entity.getAir:()I
        //   129: i2s            
        //   130: invokevirtual   net/minecraft/nbt/NBTTagCompound.setShort:(Ljava/lang/String;S)V
        //   133: aload_1        
        //   134: ldc_w           "OnGround"
        //   137: aload_0        
        //   138: getfield        net/minecraft/entity/Entity.onGround:Z
        //   141: invokevirtual   net/minecraft/nbt/NBTTagCompound.setBoolean:(Ljava/lang/String;Z)V
        //   144: aload_1        
        //   145: ldc_w           "Dimension"
        //   148: aload_0        
        //   149: getfield        net/minecraft/entity/Entity.dimension:I
        //   152: invokevirtual   net/minecraft/nbt/NBTTagCompound.setInteger:(Ljava/lang/String;I)V
        //   155: aload_1        
        //   156: ldc_w           "Invulnerable"
        //   159: aload_0        
        //   160: getfield        net/minecraft/entity/Entity.invulnerable:Z
        //   163: invokevirtual   net/minecraft/nbt/NBTTagCompound.setBoolean:(Ljava/lang/String;Z)V
        //   166: aload_1        
        //   167: ldc_w           "PortalCooldown"
        //   170: aload_0        
        //   171: getfield        net/minecraft/entity/Entity.timeUntilPortal:I
        //   174: invokevirtual   net/minecraft/nbt/NBTTagCompound.setInteger:(Ljava/lang/String;I)V
        //   177: aload_1        
        //   178: ldc_w           "UUIDMost"
        //   181: aload_0        
        //   182: invokevirtual   net/minecraft/entity/Entity.getUniqueID:()Ljava/util/UUID;
        //   185: invokevirtual   java/util/UUID.getMostSignificantBits:()J
        //   188: invokevirtual   net/minecraft/nbt/NBTTagCompound.setLong:(Ljava/lang/String;J)V
        //   191: aload_1        
        //   192: ldc_w           "UUIDLeast"
        //   195: aload_0        
        //   196: invokevirtual   net/minecraft/entity/Entity.getUniqueID:()Ljava/util/UUID;
        //   199: invokevirtual   java/util/UUID.getLeastSignificantBits:()J
        //   202: invokevirtual   net/minecraft/nbt/NBTTagCompound.setLong:(Ljava/lang/String;J)V
        //   205: aload_0        
        //   206: invokevirtual   net/minecraft/entity/Entity.getCustomNameTag:()Ljava/lang/String;
        //   209: ifnull          244
        //   212: aload_0        
        //   213: invokevirtual   net/minecraft/entity/Entity.getCustomNameTag:()Ljava/lang/String;
        //   216: invokevirtual   java/lang/String.length:()I
        //   219: ifle            244
        //   222: aload_1        
        //   223: ldc_w           "CustomName"
        //   226: aload_0        
        //   227: invokevirtual   net/minecraft/entity/Entity.getCustomNameTag:()Ljava/lang/String;
        //   230: invokevirtual   net/minecraft/nbt/NBTTagCompound.setString:(Ljava/lang/String;Ljava/lang/String;)V
        //   233: aload_1        
        //   234: ldc_w           "CustomNameVisible"
        //   237: aload_0        
        //   238: invokevirtual   net/minecraft/entity/Entity.getAlwaysRenderNameTag:()Z
        //   241: invokevirtual   net/minecraft/nbt/NBTTagCompound.setBoolean:(Ljava/lang/String;Z)V
        //   244: aload_0        
        //   245: getfield        net/minecraft/entity/Entity.cmdResultStats:Lnet/minecraft/command/CommandResultStats;
        //   248: aload_1        
        //   249: invokevirtual   net/minecraft/command/CommandResultStats.writeStatsToNBT:(Lnet/minecraft/nbt/NBTTagCompound;)V
        //   252: aload_0        
        //   253: if_icmpne       267
        //   256: aload_1        
        //   257: ldc_w           "Silent"
        //   260: aload_0        
        //   261: invokevirtual   net/minecraft/entity/Entity.isSilent:()Z
        //   264: invokevirtual   net/minecraft/nbt/NBTTagCompound.setBoolean:(Ljava/lang/String;Z)V
        //   267: aload_0        
        //   268: aload_1        
        //   269: invokevirtual   net/minecraft/entity/Entity.writeEntityToNBT:(Lnet/minecraft/nbt/NBTTagCompound;)V
        //   272: aload_0        
        //   273: getfield        net/minecraft/entity/Entity.ridingEntity:Lnet/minecraft/entity/Entity;
        //   276: ifnull          303
        //   279: new             Lnet/minecraft/nbt/NBTTagCompound;
        //   282: dup            
        //   283: invokespecial   net/minecraft/nbt/NBTTagCompound.<init>:()V
        //   286: astore_2       
        //   287: aload_0        
        //   288: getfield        net/minecraft/entity/Entity.ridingEntity:Lnet/minecraft/entity/Entity;
        //   291: aload_2        
        //   292: ifne            303
        //   295: aload_1        
        //   296: ldc_w           "Riding"
        //   299: aload_2        
        //   300: invokevirtual   net/minecraft/nbt/NBTTagCompound.setTag:(Ljava/lang/String;Lnet/minecraft/nbt/NBTBase;)V
        //   303: goto            339
        //   306: astore_2       
        //   307: aload_2        
        //   308: ldc_w           "Saving entity NBT"
        //   311: invokestatic    net/minecraft/crash/CrashReport.makeCrashReport:(Ljava/lang/Throwable;Ljava/lang/String;)Lnet/minecraft/crash/CrashReport;
        //   314: astore_3       
        //   315: aload_3        
        //   316: ldc_w           "Entity being saved"
        //   319: invokevirtual   net/minecraft/crash/CrashReport.makeCategory:(Ljava/lang/String;)Lnet/minecraft/crash/CrashReportCategory;
        //   322: astore          4
        //   324: aload_0        
        //   325: aload           4
        //   327: invokevirtual   net/minecraft/entity/Entity.addEntityCrashInfo:(Lnet/minecraft/crash/CrashReportCategory;)V
        //   330: new             Lnet/minecraft/util/ReportedException;
        //   333: dup            
        //   334: aload_3        
        //   335: invokespecial   net/minecraft/util/ReportedException.<init>:(Lnet/minecraft/crash/CrashReport;)V
        //   338: athrow         
        //   339: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public boolean interactAt(final EntityPlayer entityPlayer, final Vec3 vec3) {
        return false;
    }
    
    @Override
    public World getEntityWorld() {
        return this.worldObj;
    }
    
    public EnumFacing func_181012_aH() {
        return this.field_181018_ap;
    }
    
    protected void doBlockCollisions() {
        final BlockPos blockPos = new BlockPos(this.getEntityBoundingBox().minX + 0.001, this.getEntityBoundingBox().minY + 0.001, this.getEntityBoundingBox().minZ + 0.001);
        final BlockPos blockPos2 = new BlockPos(this.getEntityBoundingBox().maxX - 0.001, this.getEntityBoundingBox().maxY - 0.001, this.getEntityBoundingBox().maxZ - 0.001);
        if (this.worldObj.isAreaLoaded(blockPos, blockPos2)) {
            for (int i = blockPos.getX(); i <= blockPos2.getX(); ++i) {
                for (int j = blockPos.getY(); j <= blockPos2.getY(); ++j) {
                    for (int k = blockPos.getZ(); k <= blockPos2.getZ(); ++k) {
                        final BlockPos blockPos3 = new BlockPos(i, j, k);
                        final IBlockState blockState = this.worldObj.getBlockState(blockPos3);
                        blockState.getBlock().onEntityCollidedWithBlock(this.worldObj, blockPos3, blockState, this);
                    }
                }
            }
        }
    }
    
    public int getEntityId() {
        return this.entityId;
    }
    
    public boolean getAlwaysRenderNameTag() {
        return this.dataWatcher.getWatchableObjectByte(3) == 1;
    }
    
    public boolean isInvisibleToPlayer(final EntityPlayer entityPlayer) {
        return !entityPlayer.isSpectator() && this.isInvisible();
    }
    
    public void setWorld(final World worldObj) {
        this.worldObj = worldObj;
    }
    
    protected void setBeenAttacked() {
        this.velocityChanged = true;
    }
    
    public boolean isEntityAlive() {
        return !this.isDead;
    }
    
    public int getMaxFallHeight() {
        return 3;
    }
}
