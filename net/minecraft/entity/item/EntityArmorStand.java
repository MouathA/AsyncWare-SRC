package net.minecraft.entity.item;

import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.*;

public class EntityArmorStand extends EntityLivingBase
{
    private static final Rotations DEFAULT_LEFTARM_ROTATION;
    private static final Rotations DEFAULT_RIGHTLEG_ROTATION;
    private Rotations leftLegRotation;
    private Rotations bodyRotation;
    private int disabledSlots;
    private static final Rotations DEFAULT_BODY_ROTATION;
    private Rotations rightArmRotation;
    private long punchCooldown;
    private Rotations rightLegRotation;
    private static final Rotations DEFAULT_LEFTLEG_ROTATION;
    private boolean canInteract;
    private boolean field_181028_bj;
    private Rotations leftArmRotation;
    private static final Rotations DEFAULT_RIGHTARM_ROTATION;
    private Rotations headRotation;
    private static final Rotations DEFAULT_HEAD_ROTATION;
    private final ItemStack[] contents;
    
    public void setHeadRotation(final Rotations headRotation) {
        this.headRotation = headRotation;
        this.dataWatcher.updateObject(11, headRotation);
    }
    
    public Rotations getBodyRotation() {
        return this.bodyRotation;
    }
    
    private void dropBlock() {
        Block.spawnAsEntity(this.worldObj, new BlockPos(this), new ItemStack(Items.armor_stand));
        this.dropContents();
    }
    
    @Override
    public void moveEntityWithHeading(final float n, final float n2) {
        if (this != 0) {
            super.moveEntityWithHeading(n, n2);
        }
    }
    
    public EntityArmorStand(final World world, final double n, final double n2, final double n3) {
        this(world);
        this.setPosition(n, n2, n3);
    }
    
    @Override
    public void onKillCommand() {
        this.setDead();
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(10, 0);
        this.dataWatcher.addObject(11, EntityArmorStand.DEFAULT_HEAD_ROTATION);
        this.dataWatcher.addObject(12, EntityArmorStand.DEFAULT_BODY_ROTATION);
        this.dataWatcher.addObject(13, EntityArmorStand.DEFAULT_LEFTARM_ROTATION);
        this.dataWatcher.addObject(14, EntityArmorStand.DEFAULT_RIGHTARM_ROTATION);
        this.dataWatcher.addObject(15, EntityArmorStand.DEFAULT_LEFTLEG_ROTATION);
        this.dataWatcher.addObject(16, EntityArmorStand.DEFAULT_RIGHTLEG_ROTATION);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        if (nbtTagCompound.hasKey("Equipment", 9)) {
            final NBTTagList tagList = nbtTagCompound.getTagList("Equipment", 10);
            while (0 < this.contents.length) {
                this.contents[0] = ItemStack.loadItemStackFromNBT(tagList.getCompoundTagAt(0));
                int n = 0;
                ++n;
            }
        }
        this.setInvisible(nbtTagCompound.getBoolean("Invisible"));
        this.setSmall(nbtTagCompound.getBoolean("Small"));
        this.setShowArms(nbtTagCompound.getBoolean("ShowArms"));
        this.disabledSlots = nbtTagCompound.getInteger("DisabledSlots");
        this.setNoGravity(nbtTagCompound.getBoolean("NoGravity"));
        this.setNoBasePlate(nbtTagCompound.getBoolean("NoBasePlate"));
        this.func_181027_m(nbtTagCompound.getBoolean("Marker"));
        this.field_181028_bj = (this != 0);
        this.noClip = this.hasNoGravity();
        this.writePoseToNBT(nbtTagCompound.getCompoundTag("Pose"));
    }
    
    public boolean hasNoBasePlate() {
        return (this.dataWatcher.getWatchableObjectByte(10) & 0x8) != 0x0;
    }
    
    private void func_175422_a(final EntityPlayer entityPlayer, final int n) {
        final ItemStack itemStack = this.contents[n];
        if ((itemStack == null || (this.disabledSlots & 1 << n + 8) == 0x0) && (itemStack != null || (this.disabledSlots & 1 << n + 16) == 0x0)) {
            final int currentItem = entityPlayer.inventory.currentItem;
            final ItemStack stackInSlot = entityPlayer.inventory.getStackInSlot(currentItem);
            if (entityPlayer.capabilities.isCreativeMode && (itemStack == null || itemStack.getItem() == Item.getItemFromBlock(Blocks.air)) && stackInSlot != null) {
                final ItemStack copy = stackInSlot.copy();
                copy.stackSize = 1;
                this.setCurrentItemOrArmor(n, copy);
            }
            else if (stackInSlot != null && stackInSlot.stackSize > 1) {
                if (itemStack == null) {
                    final ItemStack copy2 = stackInSlot.copy();
                    copy2.stackSize = 1;
                    this.setCurrentItemOrArmor(n, copy2);
                    final ItemStack itemStack2 = stackInSlot;
                    --itemStack2.stackSize;
                }
            }
            else {
                this.setCurrentItemOrArmor(n, stackInSlot);
                entityPlayer.inventory.setInventorySlotContents(currentItem, itemStack);
            }
        }
    }
    
    private void playParticles() {
        if (this.worldObj instanceof WorldServer) {
            ((WorldServer)this.worldObj).spawnParticle(EnumParticleTypes.BLOCK_DUST, this.posX, this.posY + this.height / 1.5, this.posZ, 10, this.width / 4.0f, this.height / 4.0f, this.width / 4.0f, 0.05, Block.getStateId(Blocks.planks.getDefaultState()));
        }
    }
    
    private void func_181550_a(final boolean b) {
        final double posX = this.posX;
        final double posY = this.posY;
        final double posZ = this.posZ;
        if (b) {
            this.setSize(0.5f, 1.975f);
        }
        else {
            this.setSize(0.0f, 0.0f);
        }
        this.setPosition(posX, posY, posZ);
    }
    
    public Rotations getRightLegRotation() {
        return this.rightLegRotation;
    }
    
    public Rotations getRightArmRotation() {
        return this.rightArmRotation;
    }
    
    @Override
    public boolean isChild() {
        return this.isSmall();
    }
    
    public Rotations getHeadRotation() {
        return this.headRotation;
    }
    
    public boolean isServerWorld() {
        return super.isServerWorld() && this != 0;
    }
    
    public void setRightLegRotation(final Rotations rightLegRotation) {
        this.rightLegRotation = rightLegRotation;
        this.dataWatcher.updateObject(16, rightLegRotation);
    }
    
    static {
        DEFAULT_HEAD_ROTATION = new Rotations(0.0f, 0.0f, 0.0f);
        DEFAULT_BODY_ROTATION = new Rotations(0.0f, 0.0f, 0.0f);
        DEFAULT_LEFTARM_ROTATION = new Rotations(-10.0f, 0.0f, -10.0f);
        DEFAULT_RIGHTARM_ROTATION = new Rotations(-15.0f, 0.0f, 10.0f);
        DEFAULT_LEFTLEG_ROTATION = new Rotations(-1.0f, 0.0f, -1.0f);
        DEFAULT_RIGHTLEG_ROTATION = new Rotations(1.0f, 0.0f, 1.0f);
    }
    
    @Override
    public boolean isInRangeToRenderDist(final double n) {
        double n2 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0;
        if (Double.isNaN(n2) || n2 == 0.0) {
            n2 = 4.0;
        }
        final double n3 = n2 * 64.0;
        return n < n3 * n3;
    }
    
    @Override
    protected void collideWithNearbyEntities() {
        final List entitiesWithinAABBExcludingEntity = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox());
        if (entitiesWithinAABBExcludingEntity != null && !entitiesWithinAABBExcludingEntity.isEmpty()) {
            while (0 < entitiesWithinAABBExcludingEntity.size()) {
                final Entity entity = entitiesWithinAABBExcludingEntity.get(0);
                if (entity instanceof EntityMinecart && ((EntityMinecart)entity).getMinecartType() == EntityMinecart.EnumMinecartType.RIDEABLE && this.getDistanceSqToEntity(entity) <= 0.2) {
                    entity.applyEntityCollision(this);
                }
                int n = 0;
                ++n;
            }
        }
    }
    
    @Override
    public boolean interactAt(final EntityPlayer entityPlayer, final Vec3 vec3) {
        if (this != 0) {
            return false;
        }
        if (this.worldObj.isRemote || entityPlayer.isSpectator()) {
            return true;
        }
        final ItemStack currentEquippedItem = entityPlayer.getCurrentEquippedItem();
        final boolean b = currentEquippedItem != null;
        if (b && currentEquippedItem.getItem() instanceof ItemArmor) {
            final ItemArmor itemArmor = (ItemArmor)currentEquippedItem.getItem();
            if (itemArmor.armorType != 3) {
                if (itemArmor.armorType != 2) {
                    if (itemArmor.armorType != 1) {
                        if (itemArmor.armorType == 0) {}
                    }
                }
            }
        }
        if (!b || currentEquippedItem.getItem() == Items.skull || currentEquippedItem.getItem() == Item.getItemFromBlock(Blocks.pumpkin)) {}
        final boolean small = this.isSmall();
        final double n = small ? (vec3.yCoord * 2.0) : vec3.yCoord;
        if (n < 0.1 || n >= 0.1 + (small ? 0.8 : 0.45) || this.contents[1] == null) {
            if (n < 0.9 + (small ? 0.3 : 0.0) || n >= 0.9 + (small ? 1.0 : 0.7) || this.contents[3] == null) {
                if (n < 0.4 || n >= 0.4 + (small ? 1.0 : 0.8) || this.contents[2] == null) {
                    if (n < 1.6 || this.contents[4] != null) {}
                }
            }
        }
        final boolean b2 = this.contents[0] != null;
        if (((this.disabledSlots & 0x1) != 0x0 || (this.disabledSlots & 0x10) != 0x0) && (this.disabledSlots & 0x10) != 0x0 && (this.disabledSlots & 0x1) != 0x0) {
            return true;
        }
        if (b) {}
        if (b) {
            this.func_175422_a(entityPlayer, 4);
        }
        else if (b2) {
            this.func_175422_a(entityPlayer, 0);
        }
        return true;
    }
    
    private void damageArmorStand(final float n) {
        final float health = this.getHealth() - n;
        if (health <= 0.5f) {
            this.dropContents();
            this.setDead();
        }
        else {
            this.setHealth(health);
        }
    }
    
    @Override
    public void setInvisible(final boolean canInteract) {
        super.setInvisible(this.canInteract = canInteract);
    }
    
    @Override
    public boolean canBePushed() {
        return false;
    }
    
    private NBTTagCompound readPoseFromNBT() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        if (!EntityArmorStand.DEFAULT_HEAD_ROTATION.equals(this.headRotation)) {
            nbtTagCompound.setTag("Head", this.headRotation.writeToNBT());
        }
        if (!EntityArmorStand.DEFAULT_BODY_ROTATION.equals(this.bodyRotation)) {
            nbtTagCompound.setTag("Body", this.bodyRotation.writeToNBT());
        }
        if (!EntityArmorStand.DEFAULT_LEFTARM_ROTATION.equals(this.leftArmRotation)) {
            nbtTagCompound.setTag("LeftArm", this.leftArmRotation.writeToNBT());
        }
        if (!EntityArmorStand.DEFAULT_RIGHTARM_ROTATION.equals(this.rightArmRotation)) {
            nbtTagCompound.setTag("RightArm", this.rightArmRotation.writeToNBT());
        }
        if (!EntityArmorStand.DEFAULT_LEFTLEG_ROTATION.equals(this.leftLegRotation)) {
            nbtTagCompound.setTag("LeftLeg", this.leftLegRotation.writeToNBT());
        }
        if (!EntityArmorStand.DEFAULT_RIGHTLEG_ROTATION.equals(this.rightLegRotation)) {
            nbtTagCompound.setTag("RightLeg", this.rightLegRotation.writeToNBT());
        }
        return nbtTagCompound;
    }
    
    private void dropContents() {
        while (0 < this.contents.length) {
            if (this.contents[0] != null && this.contents[0].stackSize > 0) {
                if (this.contents[0] != null) {
                    Block.spawnAsEntity(this.worldObj, new BlockPos(this).up(), this.contents[0]);
                }
                this.contents[0] = null;
            }
            int n = 0;
            ++n;
        }
    }
    
    @Override
    protected void updatePotionMetadata() {
        this.setInvisible(this.canInteract);
    }
    
    @Override
    public void setCurrentItemOrArmor(final int n, final ItemStack itemStack) {
        this.contents[n] = itemStack;
    }
    
    public void setLeftArmRotation(final Rotations leftArmRotation) {
        this.leftArmRotation = leftArmRotation;
        this.dataWatcher.updateObject(13, leftArmRotation);
    }
    
    public EntityArmorStand(final World world) {
        super(world);
        this.contents = new ItemStack[5];
        this.headRotation = EntityArmorStand.DEFAULT_HEAD_ROTATION;
        this.bodyRotation = EntityArmorStand.DEFAULT_BODY_ROTATION;
        this.leftArmRotation = EntityArmorStand.DEFAULT_LEFTARM_ROTATION;
        this.rightArmRotation = EntityArmorStand.DEFAULT_RIGHTARM_ROTATION;
        this.leftLegRotation = EntityArmorStand.DEFAULT_LEFTLEG_ROTATION;
        this.rightLegRotation = EntityArmorStand.DEFAULT_RIGHTLEG_ROTATION;
        this.setSilent(true);
        this.noClip = this.hasNoGravity();
        this.setSize(0.5f, 1.975f);
    }
    
    @Override
    public float getEyeHeight() {
        return this.isChild() ? (this.height * 0.5f) : (this.height * 0.9f);
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return super.canBeCollidedWith() && this != 0;
    }
    
    @Override
    protected void collideWithEntity(final Entity entity) {
    }
    
    public Rotations getLeftLegRotation() {
        return this.leftLegRotation;
    }
    
    public void setRightArmRotation(final Rotations rightArmRotation) {
        this.rightArmRotation = rightArmRotation;
        this.dataWatcher.updateObject(14, rightArmRotation);
    }
    
    private void setNoBasePlate(final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte(10);
        byte b2;
        if (b) {
            b2 = (byte)(watchableObjectByte | 0x8);
        }
        else {
            b2 = (byte)(watchableObjectByte & 0xFFFFFFF7);
        }
        this.dataWatcher.updateObject(10, b2);
    }
    
    @Override
    public ItemStack getCurrentArmor(final int n) {
        return this.contents[n + 1];
    }
    
    public void setBodyRotation(final Rotations bodyRotation) {
        this.bodyRotation = bodyRotation;
        this.dataWatcher.updateObject(12, bodyRotation);
    }
    
    private void func_181027_m(final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte(10);
        byte b2;
        if (b) {
            b2 = (byte)(watchableObjectByte | 0x10);
        }
        else {
            b2 = (byte)(watchableObjectByte & 0xFFFFFFEF);
        }
        this.dataWatcher.updateObject(10, b2);
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.worldObj.isRemote) {
            return false;
        }
        if (DamageSource.outOfWorld.equals(damageSource)) {
            this.setDead();
            return false;
        }
        if (this.isEntityInvulnerable(damageSource) || this.canInteract || this == 0) {
            return false;
        }
        if (damageSource.isExplosion()) {
            this.dropContents();
            this.setDead();
            return false;
        }
        if (DamageSource.inFire.equals(damageSource)) {
            if (!this.isBurning()) {
                this.setFire(5);
            }
            else {
                this.damageArmorStand(0.15f);
            }
            return false;
        }
        if (DamageSource.onFire.equals(damageSource) && this.getHealth() > 0.5f) {
            this.damageArmorStand(4.0f);
            return false;
        }
        final boolean equals = "arrow".equals(damageSource.getDamageType());
        if (!"player".equals(damageSource.getDamageType()) && !equals) {
            return false;
        }
        if (damageSource.getSourceOfDamage() instanceof EntityArrow) {
            damageSource.getSourceOfDamage().setDead();
        }
        if (damageSource.getEntity() instanceof EntityPlayer && !((EntityPlayer)damageSource.getEntity()).capabilities.allowEdit) {
            return false;
        }
        if (damageSource.isCreativePlayer()) {
            this.playParticles();
            this.setDead();
            return false;
        }
        final long totalWorldTime = this.worldObj.getTotalWorldTime();
        if (totalWorldTime - this.punchCooldown > 5L && !equals) {
            this.punchCooldown = totalWorldTime;
        }
        else {
            this.dropBlock();
            this.playParticles();
            this.setDead();
        }
        return false;
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        final Rotations watchableObjectRotations = this.dataWatcher.getWatchableObjectRotations(11);
        if (!this.headRotation.equals(watchableObjectRotations)) {
            this.setHeadRotation(watchableObjectRotations);
        }
        final Rotations watchableObjectRotations2 = this.dataWatcher.getWatchableObjectRotations(12);
        if (!this.bodyRotation.equals(watchableObjectRotations2)) {
            this.setBodyRotation(watchableObjectRotations2);
        }
        final Rotations watchableObjectRotations3 = this.dataWatcher.getWatchableObjectRotations(13);
        if (!this.leftArmRotation.equals(watchableObjectRotations3)) {
            this.setLeftArmRotation(watchableObjectRotations3);
        }
        final Rotations watchableObjectRotations4 = this.dataWatcher.getWatchableObjectRotations(14);
        if (!this.rightArmRotation.equals(watchableObjectRotations4)) {
            this.setRightArmRotation(watchableObjectRotations4);
        }
        final Rotations watchableObjectRotations5 = this.dataWatcher.getWatchableObjectRotations(15);
        if (!this.leftLegRotation.equals(watchableObjectRotations5)) {
            this.setLeftLegRotation(watchableObjectRotations5);
        }
        final Rotations watchableObjectRotations6 = this.dataWatcher.getWatchableObjectRotations(16);
        if (!this.rightLegRotation.equals(watchableObjectRotations6)) {
            this.setRightLegRotation(watchableObjectRotations6);
        }
        final boolean func_181026_s = this.func_181026_s();
        if (!this.field_181028_bj && func_181026_s) {
            this.func_181550_a(false);
        }
        else {
            if (!this.field_181028_bj || func_181026_s) {
                return;
            }
            this.func_181550_a(true);
        }
        this.field_181028_bj = func_181026_s;
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        final NBTTagList list = new NBTTagList();
        while (0 < this.contents.length) {
            final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
            if (this.contents[0] != null) {
                this.contents[0].writeToNBT(nbtTagCompound2);
            }
            list.appendTag(nbtTagCompound2);
            int n = 0;
            ++n;
        }
        nbtTagCompound.setTag("Equipment", list);
        if (this.getAlwaysRenderNameTag() && (this.getCustomNameTag() == null || this.getCustomNameTag().length() == 0)) {
            nbtTagCompound.setBoolean("CustomNameVisible", this.getAlwaysRenderNameTag());
        }
        nbtTagCompound.setBoolean("Invisible", this.isInvisible());
        nbtTagCompound.setBoolean("Small", this.isSmall());
        nbtTagCompound.setBoolean("ShowArms", this.getShowArms());
        nbtTagCompound.setInteger("DisabledSlots", this.disabledSlots);
        nbtTagCompound.setBoolean("NoGravity", this.hasNoGravity());
        nbtTagCompound.setBoolean("NoBasePlate", this.hasNoBasePlate());
        if (this != 0) {
            nbtTagCompound.setBoolean("Marker", this.func_181026_s());
        }
        nbtTagCompound.setTag("Pose", this.readPoseFromNBT());
    }
    
    private void setNoGravity(final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte(10);
        byte b2;
        if (b) {
            b2 = (byte)(watchableObjectByte | 0x2);
        }
        else {
            b2 = (byte)(watchableObjectByte & 0xFFFFFFFD);
        }
        this.dataWatcher.updateObject(10, b2);
    }
    
    public void setLeftLegRotation(final Rotations leftLegRotation) {
        this.leftLegRotation = leftLegRotation;
        this.dataWatcher.updateObject(15, leftLegRotation);
    }
    
    public boolean isSmall() {
        return (this.dataWatcher.getWatchableObjectByte(10) & 0x1) != 0x0;
    }
    
    private void setShowArms(final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte(10);
        byte b2;
        if (b) {
            b2 = (byte)(watchableObjectByte | 0x4);
        }
        else {
            b2 = (byte)(watchableObjectByte & 0xFFFFFFFB);
        }
        this.dataWatcher.updateObject(10, b2);
    }
    
    @Override
    protected float func_110146_f(final float n, final float n2) {
        this.prevRenderYawOffset = this.prevRotationYaw;
        this.renderYawOffset = this.rotationYaw;
        return 0.0f;
    }
    
    @Override
    public boolean replaceItemInInventory(final int n, final ItemStack itemStack) {
        if (n != 99) {
            if (0 >= this.contents.length) {
                return false;
            }
        }
        if (itemStack != null && EntityLiving.getArmorPosition(itemStack) != 0) {
            return false;
        }
        this.setCurrentItemOrArmor(0, itemStack);
        return true;
    }
    
    public Rotations getLeftArmRotation() {
        return this.leftArmRotation;
    }
    
    @Override
    public ItemStack getHeldItem() {
        return this.contents[0];
    }
    
    private void setSmall(final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte(10);
        byte b2;
        if (b) {
            b2 = (byte)(watchableObjectByte | 0x1);
        }
        else {
            b2 = (byte)(watchableObjectByte & 0xFFFFFFFE);
        }
        this.dataWatcher.updateObject(10, b2);
    }
    
    @Override
    public ItemStack[] getInventory() {
        return this.contents;
    }
    
    @Override
    public ItemStack getEquipmentInSlot(final int n) {
        return this.contents[n];
    }
    
    private void writePoseToNBT(final NBTTagCompound nbtTagCompound) {
        final NBTTagList tagList = nbtTagCompound.getTagList("Head", 5);
        if (tagList.tagCount() > 0) {
            this.setHeadRotation(new Rotations(tagList));
        }
        else {
            this.setHeadRotation(EntityArmorStand.DEFAULT_HEAD_ROTATION);
        }
        final NBTTagList tagList2 = nbtTagCompound.getTagList("Body", 5);
        if (tagList2.tagCount() > 0) {
            this.setBodyRotation(new Rotations(tagList2));
        }
        else {
            this.setBodyRotation(EntityArmorStand.DEFAULT_BODY_ROTATION);
        }
        final NBTTagList tagList3 = nbtTagCompound.getTagList("LeftArm", 5);
        if (tagList3.tagCount() > 0) {
            this.setLeftArmRotation(new Rotations(tagList3));
        }
        else {
            this.setLeftArmRotation(EntityArmorStand.DEFAULT_LEFTARM_ROTATION);
        }
        final NBTTagList tagList4 = nbtTagCompound.getTagList("RightArm", 5);
        if (tagList4.tagCount() > 0) {
            this.setRightArmRotation(new Rotations(tagList4));
        }
        else {
            this.setRightArmRotation(EntityArmorStand.DEFAULT_RIGHTARM_ROTATION);
        }
        final NBTTagList tagList5 = nbtTagCompound.getTagList("LeftLeg", 5);
        if (tagList5.tagCount() > 0) {
            this.setLeftLegRotation(new Rotations(tagList5));
        }
        else {
            this.setLeftLegRotation(EntityArmorStand.DEFAULT_LEFTLEG_ROTATION);
        }
        final NBTTagList tagList6 = nbtTagCompound.getTagList("RightLeg", 5);
        if (tagList6.tagCount() > 0) {
            this.setRightLegRotation(new Rotations(tagList6));
        }
        else {
            this.setRightLegRotation(EntityArmorStand.DEFAULT_RIGHTLEG_ROTATION);
        }
    }
    
    @Override
    public boolean isImmuneToExplosions() {
        return this.isInvisible();
    }
}
