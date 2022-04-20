package net.minecraft.entity.item;

import net.minecraft.entity.player.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.block.properties.*;
import net.minecraft.server.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import java.util.*;
import com.google.common.collect.*;

public abstract class EntityMinecart extends Entity implements IWorldNameable
{
    private double minecartZ;
    private double velocityY;
    private double minecartYaw;
    private String entityName;
    private int turnProgress;
    private boolean isInReverse;
    private double velocityZ;
    private double minecartPitch;
    private double minecartY;
    private double minecartX;
    private static final int[][][] matrix;
    private double velocityX;
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.worldObj.isRemote || this.isDead) {
            return true;
        }
        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        }
        this.setRollingDirection(-this.getRollingDirection());
        this.setRollingAmplitude(10);
        this.setBeenAttacked();
        this.setDamage(this.getDamage() + n * 10.0f);
        final boolean b = damageSource.getEntity() instanceof EntityPlayer && ((EntityPlayer)damageSource.getEntity()).capabilities.isCreativeMode;
        if (b || this.getDamage() > 40.0f) {
            if (this.riddenByEntity != null) {
                this.riddenByEntity.mountEntity(null);
            }
            if (b && this != null) {
                this.setDead();
            }
            else {
                this.killMinecart(damageSource);
            }
        }
        return true;
    }
    
    public EntityMinecart(final World world, final double prevPosX, final double prevPosY, final double prevPosZ) {
        this(world);
        this.setPosition(prevPosX, prevPosY, prevPosZ);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = prevPosX;
        this.prevPosY = prevPosY;
        this.prevPosZ = prevPosZ;
    }
    
    public IBlockState getDisplayTile() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_icmpne       11
        //     4: aload_0        
        //     5: invokevirtual   net/minecraft/entity/item/EntityMinecart.getDefaultDisplayTile:()Lnet/minecraft/block/state/IBlockState;
        //     8: goto            23
        //    11: aload_0        
        //    12: invokevirtual   net/minecraft/entity/item/EntityMinecart.getDataWatcher:()Lnet/minecraft/entity/DataWatcher;
        //    15: bipush          20
        //    17: invokevirtual   net/minecraft/entity/DataWatcher.getWatchableObjectInt:(I)I
        //    20: invokestatic    net/minecraft/block/Block.getStateById:(I)Lnet/minecraft/block/state/IBlockState;
        //    23: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public void onUpdate() {
        if (this.getRollingAmplitude() > 0) {
            this.setRollingAmplitude(this.getRollingAmplitude() - 1);
        }
        if (this.getDamage() > 0.0f) {
            this.setDamage(this.getDamage() - 1.0f);
        }
        if (this.posY < -64.0) {
            this.kill();
        }
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
        if (this.worldObj.isRemote) {
            if (this.turnProgress > 0) {
                final double n = this.posX + (this.minecartX - this.posX) / this.turnProgress;
                final double n2 = this.posY + (this.minecartY - this.posY) / this.turnProgress;
                final double n3 = this.posZ + (this.minecartZ - this.posZ) / this.turnProgress;
                this.rotationYaw += (float)(MathHelper.wrapAngleTo180_double(this.minecartYaw - this.rotationYaw) / this.turnProgress);
                this.rotationPitch += (float)((this.minecartPitch - this.rotationPitch) / this.turnProgress);
                --this.turnProgress;
                this.setPosition(n, n2, n3);
                this.setRotation(this.rotationYaw, this.rotationPitch);
            }
            else {
                this.setPosition(this.posX, this.posY, this.posZ);
                this.setRotation(this.rotationYaw, this.rotationPitch);
            }
        }
        else {
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            this.motionY -= 0.03999999910593033;
            final int floor_double = MathHelper.floor_double(this.posX);
            int floor_double2 = MathHelper.floor_double(this.posY);
            MathHelper.floor_double(this.posZ);
            if (BlockRailBase.isRailBlock(this.worldObj, new BlockPos(floor_double, floor_double2 - 1, -1))) {
                --floor_double2;
            }
            final BlockPos blockPos = new BlockPos(floor_double, floor_double2, -1);
            final IBlockState blockState = this.worldObj.getBlockState(blockPos);
            if (BlockRailBase.isRailBlock(blockState)) {
                this.func_180460_a(blockPos, blockState);
                if (blockState.getBlock() == Blocks.activator_rail) {
                    this.onActivatorRailPass(floor_double, floor_double2, -1, (boolean)blockState.getValue(BlockRailPowered.POWERED));
                }
            }
            else {
                this.moveDerailedMinecart();
            }
            this.doBlockCollisions();
            this.rotationPitch = 0.0f;
            final double n4 = this.prevPosX - this.posX;
            final double n5 = this.prevPosZ - this.posZ;
            if (n4 * n4 + n5 * n5 > 0.001) {
                this.rotationYaw = (float)(MathHelper.func_181159_b(n5, n4) * 180.0 / 3.141592653589793);
                if (this.isInReverse) {
                    this.rotationYaw += 180.0f;
                }
            }
            final double n6 = MathHelper.wrapAngleTo180_float(this.rotationYaw - this.prevRotationYaw);
            if (n6 < -170.0 || n6 >= 170.0) {
                this.rotationYaw += 180.0f;
                this.isInReverse = !this.isInReverse;
            }
            this.setRotation(this.rotationYaw, this.rotationPitch);
            for (final Entity entity : this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(0.20000000298023224, 0.0, 0.20000000298023224))) {
                if (entity != this.riddenByEntity && entity.canBePushed() && entity instanceof EntityMinecart) {
                    entity.applyEntityCollision(this);
                }
            }
            if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
                if (this.riddenByEntity.ridingEntity == this) {
                    this.riddenByEntity.ridingEntity = null;
                }
                this.riddenByEntity = null;
            }
            this.handleWaterMovement();
        }
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_icmpne       82
        //     4: aload_1        
        //     5: ldc_w           "CustomDisplayTile"
        //     8: iconst_1       
        //     9: invokevirtual   net/minecraft/nbt/NBTTagCompound.setBoolean:(Ljava/lang/String;Z)V
        //    12: aload_0        
        //    13: invokevirtual   net/minecraft/entity/item/EntityMinecart.getDisplayTile:()Lnet/minecraft/block/state/IBlockState;
        //    16: astore_2       
        //    17: getstatic       net/minecraft/block/Block.blockRegistry:Lnet/minecraft/util/RegistryNamespacedDefaultedByKey;
        //    20: aload_2        
        //    21: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //    26: invokevirtual   net/minecraft/util/RegistryNamespacedDefaultedByKey.getNameForObject:(Ljava/lang/Object;)Ljava/lang/Object;
        //    29: checkcast       Lnet/minecraft/util/ResourceLocation;
        //    32: astore_3       
        //    33: aload_1        
        //    34: ldc_w           "DisplayTile"
        //    37: aload_3        
        //    38: ifnonnull       47
        //    41: ldc_w           ""
        //    44: goto            51
        //    47: aload_3        
        //    48: invokevirtual   net/minecraft/util/ResourceLocation.toString:()Ljava/lang/String;
        //    51: invokevirtual   net/minecraft/nbt/NBTTagCompound.setString:(Ljava/lang/String;Ljava/lang/String;)V
        //    54: aload_1        
        //    55: ldc_w           "DisplayData"
        //    58: aload_2        
        //    59: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //    64: aload_2        
        //    65: invokevirtual   net/minecraft/block/Block.getMetaFromState:(Lnet/minecraft/block/state/IBlockState;)I
        //    68: invokevirtual   net/minecraft/nbt/NBTTagCompound.setInteger:(Ljava/lang/String;I)V
        //    71: aload_1        
        //    72: ldc_w           "DisplayOffset"
        //    75: aload_0        
        //    76: invokevirtual   net/minecraft/entity/item/EntityMinecart.getDisplayTileOffset:()I
        //    79: invokevirtual   net/minecraft/nbt/NBTTagCompound.setInteger:(Ljava/lang/String;I)V
        //    82: aload_0        
        //    83: getfield        net/minecraft/entity/item/EntityMinecart.entityName:Ljava/lang/String;
        //    86: ifnull          110
        //    89: aload_0        
        //    90: getfield        net/minecraft/entity/item/EntityMinecart.entityName:Ljava/lang/String;
        //    93: invokevirtual   java/lang/String.length:()I
        //    96: ifle            110
        //    99: aload_1        
        //   100: ldc_w           "CustomName"
        //   103: aload_0        
        //   104: getfield        net/minecraft/entity/item/EntityMinecart.entityName:Ljava/lang/String;
        //   107: invokevirtual   net/minecraft/nbt/NBTTagCompound.setString:(Ljava/lang/String;Ljava/lang/String;)V
        //   110: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public void applyEntityCollision(final Entity entity) {
        if (!this.worldObj.isRemote && !entity.noClip && !this.noClip && entity != this.riddenByEntity) {
            if (entity instanceof EntityLivingBase && !(entity instanceof EntityPlayer) && !(entity instanceof EntityIronGolem) && this.getMinecartType() == EnumMinecartType.RIDEABLE && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.01 && this.riddenByEntity == null && entity.ridingEntity == null) {
                entity.mountEntity(this);
            }
            final double n = entity.posX - this.posX;
            final double n2 = entity.posZ - this.posZ;
            final double n3 = n * n + n2 * n2;
            if (n3 >= 9.999999747378752E-5) {
                final double n4 = MathHelper.sqrt_double(n3);
                final double n5 = n / n4;
                final double n6 = n2 / n4;
                double n7 = 1.0 / n4;
                if (n7 > 1.0) {
                    n7 = 1.0;
                }
                final double n8 = n5 * n7;
                final double n9 = n6 * n7;
                final double n10 = n8 * 0.10000000149011612;
                final double n11 = n9 * 0.10000000149011612;
                final double n12 = n10 * (1.0f - this.entityCollisionReduction);
                final double n13 = n11 * (1.0f - this.entityCollisionReduction);
                final double n14 = n12 * 0.5;
                final double n15 = n13 * 0.5;
                if (entity instanceof EntityMinecart) {
                    if (Math.abs(new Vec3(entity.posX - this.posX, 0.0, entity.posZ - this.posZ).normalize().dotProduct(new Vec3(MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f), 0.0, MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f)).normalize())) < 0.800000011920929) {
                        return;
                    }
                    final double n16 = entity.motionX + this.motionX;
                    final double n17 = entity.motionZ + this.motionZ;
                    if (((EntityMinecart)entity).getMinecartType() == EnumMinecartType.FURNACE && this.getMinecartType() != EnumMinecartType.FURNACE) {
                        this.motionX *= 0.20000000298023224;
                        this.motionZ *= 0.20000000298023224;
                        this.addVelocity(entity.motionX - n14, 0.0, entity.motionZ - n15);
                        entity.motionX *= 0.949999988079071;
                        entity.motionZ *= 0.949999988079071;
                    }
                    else if (((EntityMinecart)entity).getMinecartType() != EnumMinecartType.FURNACE && this.getMinecartType() == EnumMinecartType.FURNACE) {
                        entity.motionX *= 0.20000000298023224;
                        entity.motionZ *= 0.20000000298023224;
                        entity.addVelocity(this.motionX + n14, 0.0, this.motionZ + n15);
                        this.motionX *= 0.949999988079071;
                        this.motionZ *= 0.949999988079071;
                    }
                    else {
                        final double n18 = n16 / 2.0;
                        final double n19 = n17 / 2.0;
                        this.motionX *= 0.20000000298023224;
                        this.motionZ *= 0.20000000298023224;
                        this.addVelocity(n18 - n14, 0.0, n19 - n15);
                        entity.motionX *= 0.20000000298023224;
                        entity.motionZ *= 0.20000000298023224;
                        entity.addVelocity(n18 + n14, 0.0, n19 + n15);
                    }
                }
                else {
                    this.addVelocity(-n14, 0.0, -n15);
                    entity.addVelocity(n14 / 4.0, 0.0, n15 / 4.0);
                }
            }
        }
    }
    
    public int getRollingAmplitude() {
        return this.dataWatcher.getWatchableObjectInt(17);
    }
    
    public int getDefaultDisplayTileOffset() {
        return 6;
    }
    
    public int getDisplayTileOffset() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_icmpne       11
        //     4: aload_0        
        //     5: invokevirtual   net/minecraft/entity/item/EntityMinecart.getDefaultDisplayTileOffset:()I
        //     8: goto            20
        //    11: aload_0        
        //    12: invokevirtual   net/minecraft/entity/item/EntityMinecart.getDataWatcher:()Lnet/minecraft/entity/DataWatcher;
        //    15: bipush          21
        //    17: invokevirtual   net/minecraft/entity/DataWatcher.getWatchableObjectInt:(I)I
        //    20: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public void performHurtAnimation() {
        this.setRollingDirection(-this.getRollingDirection());
        this.setRollingAmplitude(10);
        this.setDamage(this.getDamage() + this.getDamage() * 10.0f);
    }
    
    @Override
    public void setPositionAndRotation2(final double minecartX, final double minecartY, final double minecartZ, final float n, final float n2, final int n3, final boolean b) {
        this.minecartX = minecartX;
        this.minecartY = minecartY;
        this.minecartZ = minecartZ;
        this.minecartYaw = n;
        this.minecartPitch = n2;
        this.turnProgress = n3 + 2;
        this.motionX = this.velocityX;
        this.motionY = this.velocityY;
        this.motionZ = this.velocityZ;
    }
    
    protected void applyDrag() {
        if (this.riddenByEntity != null) {
            this.motionX *= 0.996999979019165;
            this.motionY *= 0.0;
            this.motionZ *= 0.996999979019165;
        }
        else {
            this.motionX *= 0.9599999785423279;
            this.motionY *= 0.0;
            this.motionZ *= 0.9599999785423279;
        }
    }
    
    @Override
    public void setPosition(final double posX, final double posY, final double posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        final float n = this.width / 2.0f;
        this.setEntityBoundingBox(new AxisAlignedBB(posX - n, posY, posZ - n, posX + n, posY + this.height, posZ + n));
    }
    
    @Override
    public void setCustomNameTag(final String entityName) {
        this.entityName = entityName;
    }
    
    public int getRollingDirection() {
        return this.dataWatcher.getWatchableObjectInt(18);
    }
    
    public void setDisplayTileOffset(final int n) {
        this.getDataWatcher().updateObject(21, n);
        this.setHasDisplayTile(true);
    }
    
    public void setDamage(final float n) {
        this.dataWatcher.updateObject(19, n);
    }
    
    public void setHasDisplayTile(final boolean b) {
        this.getDataWatcher().updateObject(22, (byte)(byte)(b ? 1 : 0));
    }
    
    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(17, new Integer(0));
        this.dataWatcher.addObject(18, new Integer(1));
        this.dataWatcher.addObject(19, new Float(0.0f));
        this.dataWatcher.addObject(20, new Integer(0));
        this.dataWatcher.addObject(21, new Integer(6));
        this.dataWatcher.addObject(22, 0);
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    protected void func_180460_a(final BlockPos blockPos, final IBlockState blockState) {
        this.fallDistance = 0.0f;
        final Vec3 func_70489_a = this.func_70489_a(this.posX, this.posY, this.posZ);
        this.posY = blockPos.getY();
        final BlockRailBase blockRailBase = (BlockRailBase)blockState.getBlock();
        if (blockRailBase == Blocks.golden_rail) {
            (boolean)blockState.getValue(BlockRailPowered.POWERED);
        }
        final BlockRailBase.EnumRailDirection enumRailDirection = (BlockRailBase.EnumRailDirection)blockState.getValue(blockRailBase.getShapeProperty());
        switch (EntityMinecart$1.$SwitchMap$net$minecraft$block$BlockRailBase$EnumRailDirection[enumRailDirection.ordinal()]) {
            case 1: {
                this.motionX -= 0.0078125;
                ++this.posY;
                break;
            }
            case 2: {
                this.motionX += 0.0078125;
                ++this.posY;
                break;
            }
            case 3: {
                this.motionZ += 0.0078125;
                ++this.posY;
                break;
            }
            case 4: {
                this.motionZ -= 0.0078125;
                ++this.posY;
                break;
            }
        }
        final int[][] array = EntityMinecart.matrix[enumRailDirection.getMetadata()];
        double n = array[1][0] - array[0][0];
        double n2 = array[1][2] - array[0][2];
        final double sqrt = Math.sqrt(n * n + n2 * n2);
        if (this.motionX * n + this.motionZ * n2 < 0.0) {
            n = -n;
            n2 = -n2;
        }
        double sqrt2 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        if (sqrt2 > 2.0) {
            sqrt2 = 2.0;
        }
        this.motionX = sqrt2 * n / sqrt;
        this.motionZ = sqrt2 * n2 / sqrt;
        if (this.riddenByEntity instanceof EntityLivingBase && ((EntityLivingBase)this.riddenByEntity).moveForward > 0.0) {
            final double n3 = -Math.sin(this.riddenByEntity.rotationYaw * 3.1415927f / 180.0f);
            final double cos = Math.cos(this.riddenByEntity.rotationYaw * 3.1415927f / 180.0f);
            if (this.motionX * this.motionX + this.motionZ * this.motionZ < 0.01) {
                this.motionX += n3 * 0.1;
                this.motionZ += cos * 0.1;
            }
        }
        final double n4 = blockPos.getX() + 0.5 + array[0][0] * 0.5;
        final double n5 = blockPos.getZ() + 0.5 + array[0][2] * 0.5;
        final double n6 = blockPos.getX() + 0.5 + array[1][0] * 0.5;
        final double n7 = blockPos.getZ() + 0.5 + array[1][2] * 0.5;
        final double n8 = n6 - n4;
        final double n9 = n7 - n5;
        double n10;
        if (n8 == 0.0) {
            this.posX = blockPos.getX() + 0.5;
            n10 = this.posZ - blockPos.getZ();
        }
        else if (n9 == 0.0) {
            this.posZ = blockPos.getZ() + 0.5;
            n10 = this.posX - blockPos.getX();
        }
        else {
            n10 = ((this.posX - n4) * n8 + (this.posZ - n5) * n9) * 2.0;
        }
        this.posX = n4 + n8 * n10;
        this.posZ = n5 + n9 * n10;
        this.setPosition(this.posX, this.posY, this.posZ);
        double motionX = this.motionX;
        double motionZ = this.motionZ;
        if (this.riddenByEntity != null) {
            motionX *= 0.75;
            motionZ *= 0.75;
        }
        final double maximumSpeed = this.getMaximumSpeed();
        this.moveEntity(MathHelper.clamp_double(motionX, -maximumSpeed, maximumSpeed), 0.0, MathHelper.clamp_double(motionZ, -maximumSpeed, maximumSpeed));
        if (array[0][1] != 0 && MathHelper.floor_double(this.posX) - blockPos.getX() == array[0][0] && MathHelper.floor_double(this.posZ) - blockPos.getZ() == array[0][2]) {
            this.setPosition(this.posX, this.posY + array[0][1], this.posZ);
        }
        else if (array[1][1] != 0 && MathHelper.floor_double(this.posX) - blockPos.getX() == array[1][0] && MathHelper.floor_double(this.posZ) - blockPos.getZ() == array[1][2]) {
            this.setPosition(this.posX, this.posY + array[1][1], this.posZ);
        }
        this.applyDrag();
        final Vec3 func_70489_a2 = this.func_70489_a(this.posX, this.posY, this.posZ);
        if (func_70489_a2 != null && func_70489_a != null) {
            final double n11 = (func_70489_a.yCoord - func_70489_a2.yCoord) * 0.05;
            final double sqrt3 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            if (sqrt3 > 0.0) {
                this.motionX = this.motionX / sqrt3 * (sqrt3 + n11);
                this.motionZ = this.motionZ / sqrt3 * (sqrt3 + n11);
            }
            this.setPosition(this.posX, func_70489_a2.yCoord, this.posZ);
        }
        final int floor_double = MathHelper.floor_double(this.posX);
        final int floor_double2 = MathHelper.floor_double(this.posZ);
        if (floor_double != blockPos.getX() || floor_double2 != blockPos.getZ()) {
            final double sqrt4 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.motionX = sqrt4 * (floor_double - blockPos.getX());
            this.motionZ = sqrt4 * (floor_double2 - blockPos.getZ());
        }
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }
    
    public static EntityMinecart func_180458_a(final World world, final double n, final double n2, final double n3, final EnumMinecartType enumMinecartType) {
        switch (EntityMinecart$1.$SwitchMap$net$minecraft$entity$item$EntityMinecart$EnumMinecartType[enumMinecartType.ordinal()]) {
            case 1: {
                return new EntityMinecartChest(world, n, n2, n3);
            }
            case 2: {
                return new EntityMinecartFurnace(world, n, n2, n3);
            }
            case 3: {
                return new EntityMinecartTNT(world, n, n2, n3);
            }
            case 4: {
                return new EntityMinecartMobSpawner(world, n, n2, n3);
            }
            case 5: {
                return new EntityMinecartHopper(world, n, n2, n3);
            }
            case 6: {
                return new EntityMinecartCommandBlock(world, n, n2, n3);
            }
            default: {
                return new EntityMinecartEmpty(world, n, n2, n3);
            }
        }
    }
    
    public Vec3 func_70489_a(double n, double n2, double n3) {
        final int floor_double = MathHelper.floor_double(n);
        int floor_double2 = MathHelper.floor_double(n2);
        final int floor_double3 = MathHelper.floor_double(n3);
        if (BlockRailBase.isRailBlock(this.worldObj, new BlockPos(floor_double, floor_double2 - 1, floor_double3))) {
            --floor_double2;
        }
        final IBlockState blockState = this.worldObj.getBlockState(new BlockPos(floor_double, floor_double2, floor_double3));
        if (BlockRailBase.isRailBlock(blockState)) {
            final int[][] array = EntityMinecart.matrix[((BlockRailBase.EnumRailDirection)blockState.getValue(((BlockRailBase)blockState.getBlock()).getShapeProperty())).getMetadata()];
            final double n4 = floor_double + 0.5 + array[0][0] * 0.5;
            final double n5 = floor_double2 + 0.0625 + array[0][1] * 0.5;
            final double n6 = floor_double3 + 0.5 + array[0][2] * 0.5;
            final double n7 = floor_double + 0.5 + array[1][0] * 0.5;
            final double n8 = floor_double2 + 0.0625 + array[1][1] * 0.5;
            final double n9 = floor_double3 + 0.5 + array[1][2] * 0.5;
            final double n10 = n7 - n4;
            final double n11 = (n8 - n5) * 2.0;
            final double n12 = n9 - n6;
            double n13;
            if (n10 == 0.0) {
                n = floor_double + 0.5;
                n13 = n3 - floor_double3;
            }
            else if (n12 == 0.0) {
                n3 = floor_double3 + 0.5;
                n13 = n - floor_double;
            }
            else {
                n13 = ((n - n4) * n10 + (n3 - n6) * n12) * 2.0;
            }
            n = n4 + n10 * n13;
            n2 = n5 + n11 * n13;
            n3 = n6 + n12 * n13;
            if (n11 < 0.0) {
                ++n2;
            }
            if (n11 > 0.0) {
                n2 += 0.5;
            }
            return new Vec3(n, n2, n3);
        }
        return null;
    }
    
    static {
        matrix = new int[][][] { { { 0, 0, -1 }, { 0, 0, 1 } }, { { -1, 0, 0 }, { 1, 0, 0 } }, { { -1, -1, 0 }, { 1, 0, 0 } }, { { -1, 0, 0 }, { 1, -1, 0 } }, { { 0, 0, -1 }, { 0, -1, 1 } }, { { 0, -1, -1 }, { 0, 0, 1 } }, { { 0, 0, 1 }, { 1, 0, 0 } }, { { 0, 0, 1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { 1, 0, 0 } } };
    }
    
    public void onActivatorRailPass(final int n, final int n2, final int n3, final boolean b) {
    }
    
    protected void moveDerailedMinecart() {
        final double maximumSpeed = this.getMaximumSpeed();
        this.motionX = MathHelper.clamp_double(this.motionX, -maximumSpeed, maximumSpeed);
        this.motionZ = MathHelper.clamp_double(this.motionZ, -maximumSpeed, maximumSpeed);
        if (this.onGround) {
            this.motionX *= 0.5;
            this.motionY *= 0.5;
            this.motionZ *= 0.5;
        }
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        if (!this.onGround) {
            this.motionX *= 0.949999988079071;
            this.motionY *= 0.949999988079071;
            this.motionZ *= 0.949999988079071;
        }
    }
    
    @Override
    public String getName() {
        return (this.entityName != null) ? this.entityName : super.getName();
    }
    
    protected double getMaximumSpeed() {
        return 0.4;
    }
    
    public IBlockState getDefaultDisplayTile() {
        return Blocks.air.getDefaultState();
    }
    
    @Override
    public void setDead() {
        super.setDead();
    }
    
    public float getDamage() {
        return this.dataWatcher.getWatchableObjectFloat(19);
    }
    
    public void setRollingDirection(final int n) {
        this.dataWatcher.updateObject(18, n);
    }
    
    @Override
    public String getCustomNameTag() {
        return this.entityName;
    }
    
    public void setRollingAmplitude(final int n) {
        this.dataWatcher.updateObject(17, n);
    }
    
    @Override
    public IChatComponent getDisplayName() {
        if (this != null) {
            final ChatComponentText chatComponentText = new ChatComponentText(this.entityName);
            chatComponentText.getChatStyle().setChatHoverEvent(this.getHoverEvent());
            chatComponentText.getChatStyle().setInsertion(this.getUniqueID().toString());
            return chatComponentText;
        }
        final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation(this.getName(), new Object[0]);
        chatComponentTranslation.getChatStyle().setChatHoverEvent(this.getHoverEvent());
        chatComponentTranslation.getChatStyle().setInsertion(this.getUniqueID().toString());
        return chatComponentTranslation;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox() {
        return null;
    }
    
    public abstract EnumMinecartType getMinecartType();
    
    public EntityMinecart(final World world) {
        super(world);
        this.preventEntitySpawning = true;
        this.setSize(0.98f, 0.7f);
    }
    
    public void killMinecart(final DamageSource damageSource) {
        this.setDead();
        if (this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
            final ItemStack itemStack = new ItemStack(Items.minecart, 1);
            if (this.entityName != null) {
                itemStack.setStackDisplayName(this.entityName);
            }
            this.entityDropItem(itemStack, 0.0f);
        }
    }
    
    @Override
    public void setVelocity(final double n, final double n2, final double n3) {
        this.motionX = n;
        this.velocityX = n;
        this.motionY = n2;
        this.velocityY = n2;
        this.motionZ = n3;
        this.velocityZ = n3;
    }
    
    public Vec3 func_70495_a(double n, double n2, double n3, final double n4) {
        final int floor_double = MathHelper.floor_double(n);
        int floor_double2 = MathHelper.floor_double(n2);
        final int floor_double3 = MathHelper.floor_double(n3);
        if (BlockRailBase.isRailBlock(this.worldObj, new BlockPos(floor_double, floor_double2 - 1, floor_double3))) {
            --floor_double2;
        }
        final IBlockState blockState = this.worldObj.getBlockState(new BlockPos(floor_double, floor_double2, floor_double3));
        if (BlockRailBase.isRailBlock(blockState)) {
            final BlockRailBase.EnumRailDirection enumRailDirection = (BlockRailBase.EnumRailDirection)blockState.getValue(((BlockRailBase)blockState.getBlock()).getShapeProperty());
            n2 = floor_double2;
            if (enumRailDirection.isAscending()) {
                n2 = floor_double2 + 1;
            }
            final int[][] array = EntityMinecart.matrix[enumRailDirection.getMetadata()];
            final double n5 = array[1][0] - array[0][0];
            final double n6 = array[1][2] - array[0][2];
            final double sqrt = Math.sqrt(n5 * n5 + n6 * n6);
            final double n7 = n5 / sqrt;
            final double n8 = n6 / sqrt;
            n += n7 * n4;
            n3 += n8 * n4;
            if (array[0][1] != 0 && MathHelper.floor_double(n) - floor_double == array[0][0] && MathHelper.floor_double(n3) - floor_double3 == array[0][2]) {
                n2 += array[0][1];
            }
            else if (array[1][1] != 0 && MathHelper.floor_double(n) - floor_double == array[1][0] && MathHelper.floor_double(n3) - floor_double3 == array[1][2]) {
                n2 += array[1][1];
            }
            return this.func_70489_a(n, n2, n3);
        }
        return null;
    }
    
    @Override
    public boolean canBePushed() {
        return true;
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        if (nbtTagCompound.getBoolean("CustomDisplayTile")) {
            final int integer = nbtTagCompound.getInteger("DisplayData");
            if (nbtTagCompound.hasKey("DisplayTile", 8)) {
                final Block blockFromName = Block.getBlockFromName(nbtTagCompound.getString("DisplayTile"));
                if (blockFromName == null) {
                    this.func_174899_a(Blocks.air.getDefaultState());
                }
                else {
                    this.func_174899_a(blockFromName.getStateFromMeta(integer));
                }
            }
            else {
                final Block blockById = Block.getBlockById(nbtTagCompound.getInteger("DisplayTile"));
                if (blockById == null) {
                    this.func_174899_a(Blocks.air.getDefaultState());
                }
                else {
                    this.func_174899_a(blockById.getStateFromMeta(integer));
                }
            }
            this.setDisplayTileOffset(nbtTagCompound.getInteger("DisplayOffset"));
        }
        if (nbtTagCompound.hasKey("CustomName", 8) && nbtTagCompound.getString("CustomName").length() > 0) {
            this.entityName = nbtTagCompound.getString("CustomName");
        }
    }
    
    public void func_174899_a(final IBlockState blockState) {
        this.getDataWatcher().updateObject(20, Block.getStateId(blockState));
        this.setHasDisplayTile(true);
    }
    
    @Override
    public AxisAlignedBB getCollisionBox(final Entity entity) {
        return entity.canBePushed() ? entity.getEntityBoundingBox() : null;
    }
    
    @Override
    public double getMountedYOffset() {
        return 0.0;
    }
    
    public enum EnumMinecartType
    {
        CHEST("CHEST", 1, 1, "MinecartChest"), 
        COMMAND_BLOCK("COMMAND_BLOCK", 6, 6, "MinecartCommandBlock");
        
        private final String name;
        
        SPAWNER("SPAWNER", 4, 4, "MinecartSpawner");
        
        private final int networkID;
        private static final Map ID_LOOKUP;
        
        RIDEABLE("RIDEABLE", 0, 0, "MinecartRideable");
        
        private static final EnumMinecartType[] $VALUES;
        
        TNT("TNT", 3, 3, "MinecartTNT"), 
        HOPPER("HOPPER", 5, 5, "MinecartHopper"), 
        FURNACE("FURNACE", 2, 2, "MinecartFurnace");
        
        private EnumMinecartType(final String s, final int n, final int networkID, final String name) {
            this.networkID = networkID;
            this.name = name;
        }
        
        public int getNetworkID() {
            return this.networkID;
        }
        
        static {
            $VALUES = new EnumMinecartType[] { EnumMinecartType.RIDEABLE, EnumMinecartType.CHEST, EnumMinecartType.FURNACE, EnumMinecartType.TNT, EnumMinecartType.SPAWNER, EnumMinecartType.HOPPER, EnumMinecartType.COMMAND_BLOCK };
            ID_LOOKUP = Maps.newHashMap();
            final EnumMinecartType[] values = values();
            while (0 < values.length) {
                final EnumMinecartType enumMinecartType = values[0];
                EnumMinecartType.ID_LOOKUP.put(enumMinecartType.getNetworkID(), enumMinecartType);
                int n = 0;
                ++n;
            }
        }
        
        public static EnumMinecartType byNetworkID(final int n) {
            final EnumMinecartType enumMinecartType = EnumMinecartType.ID_LOOKUP.get(n);
            return (enumMinecartType == null) ? EnumMinecartType.RIDEABLE : enumMinecartType;
        }
        
        public String getName() {
            return this.name;
        }
    }
}
