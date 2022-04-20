package net.minecraft.entity.item;

import net.minecraft.entity.*;
import net.minecraft.block.state.*;
import com.google.common.collect.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.crash.*;
import net.minecraft.block.material.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

public class EntityFallingBlock extends Entity
{
    private boolean canSetAsBlock;
    public boolean shouldDropItem;
    public int fallTime;
    private IBlockState fallTile;
    public NBTTagCompound tileEntityData;
    private float fallHurtAmount;
    private boolean hurtEntities;
    private int fallHurtMax;
    
    @Override
    public void fall(final float n, final float n2) {
        final Block block = this.fallTile.getBlock();
        if (this.hurtEntities) {
            final int ceiling_float_int = MathHelper.ceiling_float_int(n - 1.0f);
            if (ceiling_float_int > 0) {
                final ArrayList arrayList = Lists.newArrayList((Iterable)this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox()));
                final boolean b = block == Blocks.anvil;
                final DamageSource damageSource = b ? DamageSource.anvil : DamageSource.fallingBlock;
                final Iterator<Entity> iterator = (Iterator<Entity>)arrayList.iterator();
                while (iterator.hasNext()) {
                    iterator.next().attackEntityFrom(damageSource, (float)Math.min(MathHelper.floor_float(ceiling_float_int * this.fallHurtAmount), this.fallHurtMax));
                }
                if (b && this.rand.nextFloat() < 0.05000000074505806 + ceiling_float_int * 0.05) {
                    int intValue = (int)this.fallTile.getValue(BlockAnvil.DAMAGE);
                    if (++intValue > 2) {
                        this.canSetAsBlock = true;
                    }
                    else {
                        this.fallTile = this.fallTile.withProperty(BlockAnvil.DAMAGE, intValue);
                    }
                }
            }
        }
    }
    
    public World getWorldObj() {
        return this.worldObj;
    }
    
    @Override
    public void addEntityCrashInfo(final CrashReportCategory crashReportCategory) {
        super.addEntityCrashInfo(crashReportCategory);
        if (this.fallTile != null) {
            final Block block = this.fallTile.getBlock();
            crashReportCategory.addCrashSection("Immitating block ID", Block.getIdFromBlock(block));
            crashReportCategory.addCrashSection("Immitating block data", block.getMetaFromState(this.fallTile));
        }
    }
    
    @Override
    public void onUpdate() {
        final Block block = this.fallTile.getBlock();
        if (block.getMaterial() == Material.air) {
            this.setDead();
        }
        else {
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            if (this.fallTime++ == 0) {
                final BlockPos blockToAir = new BlockPos(this);
                if (this.worldObj.getBlockState(blockToAir).getBlock() == block) {
                    this.worldObj.setBlockToAir(blockToAir);
                }
                else if (!this.worldObj.isRemote) {
                    this.setDead();
                    return;
                }
            }
            this.motionY -= 0.03999999910593033;
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.9800000190734863;
            this.motionY *= 0.9800000190734863;
            this.motionZ *= 0.9800000190734863;
            if (!this.worldObj.isRemote) {
                final BlockPos blockPos = new BlockPos(this);
                if (this.onGround) {
                    this.motionX *= 0.699999988079071;
                    this.motionZ *= 0.699999988079071;
                    this.motionY *= -0.5;
                    if (this.worldObj.getBlockState(blockPos).getBlock() != Blocks.piston_extension) {
                        this.setDead();
                        if (!this.canSetAsBlock) {
                            if (this.worldObj.canBlockBePlaced(block, blockPos, true, EnumFacing.UP, null, null) && !BlockFalling.canFallInto(this.worldObj, blockPos.down()) && this.worldObj.setBlockState(blockPos, this.fallTile, 3)) {
                                if (block instanceof BlockFalling) {
                                    ((BlockFalling)block).onEndFalling(this.worldObj, blockPos);
                                }
                                if (this.tileEntityData != null && block instanceof ITileEntityProvider) {
                                    final TileEntity tileEntity = this.worldObj.getTileEntity(blockPos);
                                    if (tileEntity != null) {
                                        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                                        tileEntity.writeToNBT(nbtTagCompound);
                                        for (final String s : this.tileEntityData.getKeySet()) {
                                            final NBTBase tag = this.tileEntityData.getTag(s);
                                            if (!s.equals("x") && !s.equals("y") && !s.equals("z")) {
                                                nbtTagCompound.setTag(s, tag.copy());
                                            }
                                        }
                                        tileEntity.readFromNBT(nbtTagCompound);
                                        tileEntity.markDirty();
                                    }
                                }
                            }
                            else if (this.shouldDropItem && this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
                                this.entityDropItem(new ItemStack(block, 1, block.damageDropped(this.fallTile)), 0.0f);
                            }
                        }
                    }
                }
                else if ((this.fallTime > 100 && !this.worldObj.isRemote && (blockPos.getY() < 1 || blockPos.getY() > 256)) || this.fallTime > 600) {
                    if (this.shouldDropItem && this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
                        this.entityDropItem(new ItemStack(block, 1, block.damageDropped(this.fallTile)), 0.0f);
                    }
                    this.setDead();
                }
            }
        }
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        final int n = nbtTagCompound.getByte("Data") & 0xFF;
        if (nbtTagCompound.hasKey("Block", 8)) {
            this.fallTile = Block.getBlockFromName(nbtTagCompound.getString("Block")).getStateFromMeta(n);
        }
        else if (nbtTagCompound.hasKey("TileID", 99)) {
            this.fallTile = Block.getBlockById(nbtTagCompound.getInteger("TileID")).getStateFromMeta(n);
        }
        else {
            this.fallTile = Block.getBlockById(nbtTagCompound.getByte("Tile") & 0xFF).getStateFromMeta(n);
        }
        this.fallTime = (nbtTagCompound.getByte("Time") & 0xFF);
        final Block block = this.fallTile.getBlock();
        if (nbtTagCompound.hasKey("HurtEntities", 99)) {
            this.hurtEntities = nbtTagCompound.getBoolean("HurtEntities");
            this.fallHurtAmount = nbtTagCompound.getFloat("FallHurtAmount");
            this.fallHurtMax = nbtTagCompound.getInteger("FallHurtMax");
        }
        else if (block == Blocks.anvil) {
            this.hurtEntities = true;
        }
        if (nbtTagCompound.hasKey("DropItem", 99)) {
            this.shouldDropItem = nbtTagCompound.getBoolean("DropItem");
        }
        if (nbtTagCompound.hasKey("TileEntityData", 10)) {
            this.tileEntityData = nbtTagCompound.getCompoundTag("TileEntityData");
        }
        if (block == null || block.getMaterial() == Material.air) {
            this.fallTile = Blocks.sand.getDefaultState();
        }
    }
    
    @Override
    public boolean canRenderOnFire() {
        return false;
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }
    
    public EntityFallingBlock(final World world) {
        super(world);
        this.shouldDropItem = true;
        this.fallHurtMax = 40;
        this.fallHurtAmount = 2.0f;
    }
    
    @Override
    protected void entityInit() {
    }
    
    public void setHurtEntities(final boolean hurtEntities) {
        this.hurtEntities = hurtEntities;
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        final Block block = (this.fallTile != null) ? this.fallTile.getBlock() : Blocks.air;
        final ResourceLocation resourceLocation = (ResourceLocation)Block.blockRegistry.getNameForObject(block);
        nbtTagCompound.setString("Block", (resourceLocation == null) ? "" : resourceLocation.toString());
        nbtTagCompound.setByte("Data", (byte)block.getMetaFromState(this.fallTile));
        nbtTagCompound.setByte("Time", (byte)this.fallTime);
        nbtTagCompound.setBoolean("DropItem", this.shouldDropItem);
        nbtTagCompound.setBoolean("HurtEntities", this.hurtEntities);
        nbtTagCompound.setFloat("FallHurtAmount", this.fallHurtAmount);
        nbtTagCompound.setInteger("FallHurtMax", this.fallHurtMax);
        if (this.tileEntityData != null) {
            nbtTagCompound.setTag("TileEntityData", this.tileEntityData);
        }
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    public EntityFallingBlock(final World world, final double prevPosX, final double prevPosY, final double prevPosZ, final IBlockState fallTile) {
        super(world);
        this.shouldDropItem = true;
        this.fallHurtMax = 40;
        this.fallHurtAmount = 2.0f;
        this.fallTile = fallTile;
        this.preventEntitySpawning = true;
        this.setSize(0.98f, 0.98f);
        this.setPosition(prevPosX, prevPosY, prevPosZ);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = prevPosX;
        this.prevPosY = prevPosY;
        this.prevPosZ = prevPosZ;
    }
    
    public IBlockState getBlock() {
        return this.fallTile;
    }
}
