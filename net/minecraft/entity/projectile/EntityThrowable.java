package net.minecraft.entity.projectile;

import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;

public abstract class EntityThrowable extends Entity implements IProjectile
{
    private Block inTile;
    private int yTile;
    protected boolean inGround;
    private int ticksInGround;
    private int ticksInAir;
    public int throwableShake;
    private int xTile;
    private String throwerName;
    private EntityLivingBase thrower;
    private int zTile;
    
    @Override
    public void onUpdate() {
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;
        super.onUpdate();
        if (this.throwableShake > 0) {
            --this.throwableShake;
        }
        if (this.inGround) {
            if (this.worldObj.getBlockState(new BlockPos(this.xTile, this.yTile, this.zTile)).getBlock() == this.inTile) {
                ++this.ticksInGround;
                if (this.ticksInGround == 1200) {
                    this.setDead();
                }
                return;
            }
            this.inGround = false;
            this.motionX *= this.rand.nextFloat() * 0.2f;
            this.motionY *= this.rand.nextFloat() * 0.2f;
            this.motionZ *= this.rand.nextFloat() * 0.2f;
            this.ticksInGround = 0;
            this.ticksInAir = 0;
        }
        else {
            ++this.ticksInAir;
        }
        MovingObjectPosition rayTraceBlocks = this.worldObj.rayTraceBlocks(new Vec3(this.posX, this.posY, this.posZ), new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ));
        final Vec3 vec3 = new Vec3(this.posX, this.posY, this.posZ);
        Vec3 vec4 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        if (rayTraceBlocks != null) {
            vec4 = new Vec3(rayTraceBlocks.hitVec.xCoord, rayTraceBlocks.hitVec.yCoord, rayTraceBlocks.hitVec.zCoord);
        }
        if (!this.worldObj.isRemote) {
            Entity entity = null;
            final List entitiesWithinAABBExcludingEntity = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0));
            double n = 0.0;
            final EntityLivingBase thrower = this.getThrower();
            while (0 < entitiesWithinAABBExcludingEntity.size()) {
                final Entity entity2 = entitiesWithinAABBExcludingEntity.get(0);
                if (entity2.canBeCollidedWith() && (entity2 != thrower || this.ticksInAir >= 5)) {
                    final float n2 = 0.3f;
                    final MovingObjectPosition calculateIntercept = entity2.getEntityBoundingBox().expand(n2, n2, n2).calculateIntercept(vec3, vec4);
                    if (calculateIntercept != null) {
                        final double squareDistanceTo = vec3.squareDistanceTo(calculateIntercept.hitVec);
                        if (squareDistanceTo < n || n == 0.0) {
                            entity = entity2;
                            n = squareDistanceTo;
                        }
                    }
                }
                int n3 = 0;
                ++n3;
            }
            if (entity != null) {
                rayTraceBlocks = new MovingObjectPosition(entity);
            }
        }
        if (rayTraceBlocks != null) {
            if (rayTraceBlocks.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.worldObj.getBlockState(rayTraceBlocks.getBlockPos()).getBlock() == Blocks.portal) {
                this.func_181015_d(rayTraceBlocks.getBlockPos());
            }
            else {
                this.onImpact(rayTraceBlocks);
            }
        }
        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        final float sqrt_double = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(MathHelper.func_181159_b(this.motionX, this.motionZ) * 180.0 / 3.141592653589793);
        this.rotationPitch = (float)(MathHelper.func_181159_b(this.motionY, sqrt_double) * 180.0 / 3.141592653589793);
        while (this.rotationPitch - this.prevRotationPitch < -180.0f) {
            this.prevRotationPitch -= 360.0f;
        }
        while (this.rotationPitch - this.prevRotationPitch >= 180.0f) {
            this.prevRotationPitch += 360.0f;
        }
        while (this.rotationYaw - this.prevRotationYaw < -180.0f) {
            this.prevRotationYaw -= 360.0f;
        }
        while (this.rotationYaw - this.prevRotationYaw >= 180.0f) {
            this.prevRotationYaw += 360.0f;
        }
        this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2f;
        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2f;
        final float n4 = 0.99f;
        final float gravityVelocity = this.getGravityVelocity();
        if (!this.isInWater()) {
            this.motionX *= n4;
            this.motionY *= n4;
            this.motionZ *= n4;
            this.motionY -= gravityVelocity;
            this.setPosition(this.posX, this.posY, this.posZ);
            return;
        }
        while (true) {
            final float n5 = 0.25f;
            this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * n5, this.posY - this.motionY * n5, this.posZ - this.motionZ * n5, this.motionX, this.motionY, this.motionZ, new int[0]);
            int n6 = 0;
            ++n6;
        }
    }
    
    protected float getInaccuracy() {
        return 0.0f;
    }
    
    @Override
    protected void entityInit() {
    }
    
    public EntityThrowable(final World world, final EntityLivingBase thrower) {
        super(world);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.thrower = thrower;
        this.setSize(0.25f, 0.25f);
        this.setLocationAndAngles(thrower.posX, thrower.posY + thrower.getEyeHeight(), thrower.posZ, thrower.rotationYaw, thrower.rotationPitch);
        this.posX -= MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        this.posY -= 0.10000000149011612;
        this.posZ -= MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        this.setPosition(this.posX, this.posY, this.posZ);
        final float n = 0.4f;
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * n;
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * n;
        this.motionY = -MathHelper.sin((this.rotationPitch + this.getInaccuracy()) / 180.0f * 3.1415927f) * n;
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, this.getVelocity(), 1.0f);
    }
    
    public EntityLivingBase getThrower() {
        if (this.thrower == null && this.throwerName != null && this.throwerName.length() > 0) {
            this.thrower = this.worldObj.getPlayerEntityByName(this.throwerName);
            if (this.thrower == null && this.worldObj instanceof WorldServer) {
                final Entity entityFromUuid = ((WorldServer)this.worldObj).getEntityFromUuid(UUID.fromString(this.throwerName));
                if (entityFromUuid instanceof EntityLivingBase) {
                    this.thrower = (EntityLivingBase)entityFromUuid;
                }
            }
        }
        return this.thrower;
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setShort("xTile", (short)this.xTile);
        nbtTagCompound.setShort("yTile", (short)this.yTile);
        nbtTagCompound.setShort("zTile", (short)this.zTile);
        final ResourceLocation resourceLocation = (ResourceLocation)Block.blockRegistry.getNameForObject(this.inTile);
        nbtTagCompound.setString("inTile", (resourceLocation == null) ? "" : resourceLocation.toString());
        nbtTagCompound.setByte("shake", (byte)this.throwableShake);
        nbtTagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
        if ((this.throwerName == null || this.throwerName.length() == 0) && this.thrower instanceof EntityPlayer) {
            this.throwerName = this.thrower.getName();
        }
        nbtTagCompound.setString("ownerName", (this.throwerName == null) ? "" : this.throwerName);
    }
    
    protected float getGravityVelocity() {
        return 0.03f;
    }
    
    @Override
    public void setVelocity(final double motionX, final double motionY, final double motionZ) {
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        if (this.prevRotationPitch == 0.0f && this.prevRotationYaw == 0.0f) {
            final float sqrt_double = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
            final float n = (float)(MathHelper.func_181159_b(motionX, motionZ) * 180.0 / 3.141592653589793);
            this.rotationYaw = n;
            this.prevRotationYaw = n;
            final float n2 = (float)(MathHelper.func_181159_b(motionY, sqrt_double) * 180.0 / 3.141592653589793);
            this.rotationPitch = n2;
            this.prevRotationPitch = n2;
        }
    }
    
    @Override
    public void setThrowableHeading(double motionX, double motionY, double motionZ, final float n, final float n2) {
        final float sqrt_double = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
        motionX /= sqrt_double;
        motionY /= sqrt_double;
        motionZ /= sqrt_double;
        motionX += this.rand.nextGaussian() * 0.007499999832361937 * n2;
        motionY += this.rand.nextGaussian() * 0.007499999832361937 * n2;
        motionZ += this.rand.nextGaussian() * 0.007499999832361937 * n2;
        motionX *= n;
        motionY *= n;
        motionZ *= n;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        final float sqrt_double2 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
        final float n3 = (float)(MathHelper.func_181159_b(motionX, motionZ) * 180.0 / 3.141592653589793);
        this.rotationYaw = n3;
        this.prevRotationYaw = n3;
        final float n4 = (float)(MathHelper.func_181159_b(motionY, sqrt_double2) * 180.0 / 3.141592653589793);
        this.rotationPitch = n4;
        this.prevRotationPitch = n4;
        this.ticksInGround = 0;
    }
    
    protected float getVelocity() {
        return 1.5f;
    }
    
    protected abstract void onImpact(final MovingObjectPosition p0);
    
    public EntityThrowable(final World world) {
        super(world);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.setSize(0.25f, 0.25f);
    }
    
    @Override
    public boolean isInRangeToRenderDist(final double n) {
        double n2 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0;
        if (Double.isNaN(n2)) {
            n2 = 4.0;
        }
        final double n3 = n2 * 64.0;
        return n < n3 * n3;
    }
    
    public EntityThrowable(final World world, final double n, final double n2, final double n3) {
        super(world);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.ticksInGround = 0;
        this.setSize(0.25f, 0.25f);
        this.setPosition(n, n2, n3);
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        this.xTile = nbtTagCompound.getShort("xTile");
        this.yTile = nbtTagCompound.getShort("yTile");
        this.zTile = nbtTagCompound.getShort("zTile");
        if (nbtTagCompound.hasKey("inTile", 8)) {
            this.inTile = Block.getBlockFromName(nbtTagCompound.getString("inTile"));
        }
        else {
            this.inTile = Block.getBlockById(nbtTagCompound.getByte("inTile") & 0xFF);
        }
        this.throwableShake = (nbtTagCompound.getByte("shake") & 0xFF);
        this.inGround = (nbtTagCompound.getByte("inGround") == 1);
        this.thrower = null;
        this.throwerName = nbtTagCompound.getString("ownerName");
        if (this.throwerName != null && this.throwerName.length() == 0) {
            this.throwerName = null;
        }
        this.thrower = this.getThrower();
    }
}
