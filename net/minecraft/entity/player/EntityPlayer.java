package net.minecraft.entity.player;

import com.mojang.authlib.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.stats.*;
import net.minecraft.block.material.*;
import net.minecraft.tileentity.*;
import com.google.common.base.*;
import net.minecraft.inventory.*;
import net.minecraft.enchantment.*;
import net.minecraft.potion.*;
import net.minecraft.entity.monster.*;
import net.minecraft.block.properties.*;
import java.util.*;
import net.minecraft.event.*;
import net.minecraft.server.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.passive.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.command.server.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import com.google.common.collect.*;
import net.minecraft.block.state.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.item.*;
import net.minecraft.scoreboard.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;

public abstract class EntityPlayer extends EntityLivingBase
{
    private InventoryEnderChest theInventoryEnderChest;
    private BlockPos spawnChunk;
    private int sleepTimer;
    protected boolean sleeping;
    public Container inventoryContainer;
    public float speedInAir;
    private boolean hasReducedDebug;
    public int experienceLevel;
    public float experience;
    private BlockPos startMinecartRidingCoordinate;
    public int experienceTotal;
    protected int flyToggleTimer;
    protected FoodStats foodStats;
    public double chasingPosZ;
    public EntityFishHook fishEntity;
    public float prevCameraYaw;
    public double prevChasingPosX;
    public float renderOffsetZ;
    public float renderOffsetX;
    public PlayerCapabilities capabilities;
    public double chasingPosX;
    private int xpSeed;
    private boolean spawnForced;
    public double prevChasingPosY;
    private ItemStack itemInUse;
    public BlockPos playerLocation;
    private int lastXPSound;
    public double prevChasingPosZ;
    public Container openContainer;
    public int xpCooldown;
    public InventoryPlayer inventory;
    public float renderOffsetY;
    private int itemInUseCount;
    private final GameProfile gameProfile;
    public double chasingPosY;
    public float cameraYaw;
    protected float speedOnGround;
    
    @Override
    public double getYOffset() {
        return -0.35;
    }
    
    public void respawnPlayer() {
    }
    
    public void clearItemInUse() {
        this.itemInUse = null;
        this.itemInUseCount = 0;
        if (!this.worldObj.isRemote) {
            this.setEating(false);
        }
    }
    
    public void displayGUIChest(final IInventory inventory) {
    }
    
    public void onEnchantmentCritical(final Entity entity) {
    }
    
    public abstract boolean isSpectator();
    
    public boolean isAllowEdit() {
        return this.capabilities.allowEdit;
    }
    
    @Override
    public void setDead() {
        super.setDead();
        this.inventoryContainer.onContainerClosed(this);
        if (this.openContainer != null) {
            this.openContainer.onContainerClosed(this);
        }
    }
    
    public void displayGUIBook(final ItemStack itemStack) {
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.flyToggleTimer > 0) {
            --this.flyToggleTimer;
        }
        if (this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL && this.worldObj.getGameRules().getBoolean("naturalRegeneration")) {
            if (this.getHealth() < this.getMaxHealth() && this.ticksExisted % 20 == 0) {
                this.heal(1.0f);
            }
            if (this.foodStats.needFood() && this.ticksExisted % 10 == 0) {
                this.foodStats.setFoodLevel(this.foodStats.getFoodLevel() + 1);
            }
        }
        this.inventory.decrementAnimations();
        this.prevCameraYaw = this.cameraYaw;
        super.onLivingUpdate();
        final IAttributeInstance entityAttribute = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        if (!this.worldObj.isRemote) {
            entityAttribute.setBaseValue(this.capabilities.getWalkSpeed());
        }
        this.jumpMovementFactor = this.speedInAir;
        if (this.isSprinting()) {
            this.jumpMovementFactor += (float)(this.speedInAir * 0.3);
        }
        this.setAIMoveSpeed((float)entityAttribute.getAttributeValue());
        float sqrt_double = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        float n = (float)(Math.atan(-this.motionY * 0.20000000298023224) * 15.0);
        if (sqrt_double > 0.1f) {
            sqrt_double = 0.1f;
        }
        if (!this.onGround || this.getHealth() <= 0.0f) {
            sqrt_double = 0.0f;
        }
        if (this.onGround || this.getHealth() <= 0.0f) {
            n = 0.0f;
        }
        this.cameraYaw += (sqrt_double - this.cameraYaw) * 0.4f;
        this.cameraPitch += (n - this.cameraPitch) * 0.8f;
        if (this.getHealth() > 0.0f && !this.isSpectator()) {
            AxisAlignedBB axisAlignedBB;
            if (this.ridingEntity != null && !this.ridingEntity.isDead) {
                axisAlignedBB = this.getEntityBoundingBox().union(this.ridingEntity.getEntityBoundingBox()).expand(1.0, 0.0, 1.0);
            }
            else {
                axisAlignedBB = this.getEntityBoundingBox().expand(1.0, 0.5, 1.0);
            }
            final List entitiesWithinAABBExcludingEntity = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, axisAlignedBB);
            while (0 < entitiesWithinAABBExcludingEntity.size()) {
                final Entity entity = entitiesWithinAABBExcludingEntity.get(0);
                if (!entity.isDead) {
                    this.collideWithPlayer(entity);
                }
                int n2 = 0;
                ++n2;
            }
        }
    }
    
    public void addScore(final int n) {
        this.dataWatcher.updateObject(18, this.getScore() + n);
    }
    
    public void addChatComponentMessage(final IChatComponent chatComponent) {
    }
    
    @Override
    protected int getExperiencePoints(final EntityPlayer entityPlayer) {
        if (this.worldObj.getGameRules().getBoolean("keepInventory")) {
            return 0;
        }
        final int n = this.experienceLevel * 7;
        return (n > 100) ? 100 : n;
    }
    
    @Override
    public void setCurrentItemOrArmor(final int n, final ItemStack itemStack) {
        this.inventory.armorInventory[n] = itemStack;
    }
    
    public EntityItem dropItem(final ItemStack itemStack, final boolean b, final boolean b2) {
        if (itemStack == null) {
            return null;
        }
        if (itemStack.stackSize == 0) {
            return null;
        }
        final EntityItem entityItem = new EntityItem(this.worldObj, this.posX, this.posY - 0.30000001192092896 + this.getEyeHeight(), this.posZ, itemStack);
        entityItem.setPickupDelay(40);
        if (b2) {
            entityItem.setThrower(this.getName());
        }
        if (b) {
            final float n = this.rand.nextFloat() * 0.5f;
            final float n2 = this.rand.nextFloat() * 3.1415927f * 2.0f;
            entityItem.motionX = -MathHelper.sin(n2) * n;
            entityItem.motionZ = MathHelper.cos(n2) * n;
            entityItem.motionY = 0.20000000298023224;
        }
        else {
            final float n3 = 0.3f;
            entityItem.motionX = -MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * n3;
            entityItem.motionZ = MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * n3;
            entityItem.motionY = -MathHelper.sin(this.rotationPitch / 180.0f * 3.1415927f) * n3 + 0.1f;
            final float n4 = this.rand.nextFloat() * 3.1415927f * 2.0f;
            final float n5 = 0.02f * this.rand.nextFloat();
            final EntityItem entityItem2 = entityItem;
            entityItem2.motionX += Math.cos(n4) * n5;
            final EntityItem entityItem3 = entityItem;
            entityItem3.motionY += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f;
            final EntityItem entityItem4 = entityItem;
            entityItem4.motionZ += Math.sin(n4) * n5;
        }
        this.joinEntityItemWithWorld(entityItem);
        if (b2) {
            this.triggerAchievement(StatList.dropStat);
        }
        return entityItem;
    }
    
    @Override
    protected String getDeathSound() {
        return "game.player.die";
    }
    
    public void destroyCurrentEquippedItem() {
        this.inventory.setInventorySlotContents(this.inventory.currentItem, null);
    }
    
    @Override
    public boolean replaceItemInInventory(final int n, final ItemStack itemStack) {
        if (n >= 0 && n < this.inventory.mainInventory.length) {
            this.inventory.setInventorySlotContents(n, itemStack);
            return true;
        }
        final int n2 = n - 100;
        if (n2 >= 0 && n2 < this.inventory.armorInventory.length) {
            final int n3 = n2 + 1;
            if (itemStack != null && itemStack.getItem() != null) {
                if (itemStack.getItem() instanceof ItemArmor) {
                    if (EntityLiving.getArmorPosition(itemStack) != n3) {
                        return false;
                    }
                }
                else if (n3 != 4 || (itemStack.getItem() != Items.skull && !(itemStack.getItem() instanceof ItemBlock))) {
                    return false;
                }
            }
            this.inventory.setInventorySlotContents(n2 + this.inventory.mainInventory.length, itemStack);
            return true;
        }
        final int n4 = n - 200;
        if (n4 >= 0 && n4 < this.theInventoryEnderChest.getSizeInventory()) {
            this.theInventoryEnderChest.setInventorySlotContents(n4, itemStack);
            return true;
        }
        return false;
    }
    
    public void onCriticalHit(final Entity entity) {
    }
    
    @Override
    public void onKillEntity(final EntityLivingBase entityLivingBase) {
        if (entityLivingBase instanceof IMob) {
            this.triggerAchievement(AchievementList.killEnemy);
        }
        final EntityList.EntityEggInfo entityEggInfo = EntityList.entityEggs.get(EntityList.getEntityID(entityLivingBase));
        if (entityEggInfo != null) {
            this.triggerAchievement(entityEggInfo.field_151512_d);
        }
    }
    
    public void triggerAchievement(final StatBase statBase) {
        this.addStat(statBase, 1);
    }
    
    public void addMovementStat(final double n, final double n2, final double n3) {
        if (this.ridingEntity == null) {
            if (this.isInsideOfMaterial(Material.water)) {
                final int round = Math.round(MathHelper.sqrt_double(n * n + n2 * n2 + n3 * n3) * 100.0f);
                if (round > 0) {
                    this.addStat(StatList.distanceDoveStat, round);
                    this.addExhaustion(0.015f * round * 0.01f);
                }
            }
            else if (this.isInWater()) {
                final int round2 = Math.round(MathHelper.sqrt_double(n * n + n3 * n3) * 100.0f);
                if (round2 > 0) {
                    this.addStat(StatList.distanceSwumStat, round2);
                    this.addExhaustion(0.015f * round2 * 0.01f);
                }
            }
            else if (this.isOnLadder()) {
                if (n2 > 0.0) {
                    this.addStat(StatList.distanceClimbedStat, (int)Math.round(n2 * 100.0));
                }
            }
            else if (this.onGround) {
                final int round3 = Math.round(MathHelper.sqrt_double(n * n + n3 * n3) * 100.0f);
                if (round3 > 0) {
                    this.addStat(StatList.distanceWalkedStat, round3);
                    if (this.isSprinting()) {
                        this.addStat(StatList.distanceSprintedStat, round3);
                        this.addExhaustion(0.099999994f * round3 * 0.01f);
                    }
                    else {
                        if (this.isSneaking()) {
                            this.addStat(StatList.distanceCrouchedStat, round3);
                        }
                        this.addExhaustion(0.01f * round3 * 0.01f);
                    }
                }
            }
            else {
                final int round4 = Math.round(MathHelper.sqrt_double(n * n + n3 * n3) * 100.0f);
                if (round4 > 25) {
                    this.addStat(StatList.distanceFlownStat, round4);
                }
            }
        }
    }
    
    @Override
    public int getPortalCooldown() {
        return 10;
    }
    
    protected void joinEntityItemWithWorld(final EntityItem entityItem) {
        this.worldObj.spawnEntityInWorld(entityItem);
    }
    
    private void func_175139_a(final EnumFacing enumFacing) {
        this.renderOffsetX = 0.0f;
        this.renderOffsetZ = 0.0f;
        switch (EntityPlayer$1.$SwitchMap$net$minecraft$util$EnumFacing[enumFacing.ordinal()]) {
            case 1: {
                this.renderOffsetZ = -1.8f;
                break;
            }
            case 2: {
                this.renderOffsetZ = 1.8f;
                break;
            }
            case 3: {
                this.renderOffsetX = 1.8f;
                break;
            }
            case 4: {
                this.renderOffsetX = -1.8f;
                break;
            }
        }
    }
    
    @Override
    public ItemStack[] getInventory() {
        return this.inventory.armorInventory;
    }
    
    public void setItemInUse(final ItemStack itemInUse, final int itemInUseCount) {
        if (itemInUse != this.itemInUse) {
            this.itemInUse = itemInUse;
            this.itemInUseCount = itemInUseCount;
            if (!this.worldObj.isRemote) {
                this.setEating(true);
            }
        }
    }
    
    public void openEditSign(final TileEntitySign tileEntitySign) {
    }
    
    protected boolean isMovementBlocked() {
        return this.getHealth() <= 0.0f || this.isPlayerSleeping();
    }
    
    public int getItemInUseCount() {
        return this.itemInUseCount;
    }
    
    @Override
    public ItemStack getEquipmentInSlot(final int n) {
        return (n == 0) ? this.inventory.getCurrentItem() : this.inventory.armorInventory[n - 1];
    }
    
    @Override
    public void updateRidden() {
        if (!this.worldObj.isRemote && this.isSneaking()) {
            this.mountEntity(null);
            this.setSneaking(false);
        }
        else {
            final double posX = this.posX;
            final double posY = this.posY;
            final double posZ = this.posZ;
            final float rotationYaw = this.rotationYaw;
            final float rotationPitch = this.rotationPitch;
            super.updateRidden();
            this.prevCameraYaw = this.cameraYaw;
            this.cameraYaw = 0.0f;
            this.addMountedMovementStat(this.posX - posX, this.posY - posY, this.posZ - posZ);
            if (this.ridingEntity instanceof EntityPig) {
                this.rotationPitch = rotationPitch;
                this.rotationYaw = rotationYaw;
                this.renderYawOffset = ((EntityPig)this.ridingEntity).renderYawOffset;
            }
        }
    }
    
    @Override
    public String getName() {
        return this.gameProfile.getName();
    }
    
    @Override
    public int getTotalArmorValue() {
        return this.inventory.getTotalArmorValue();
    }
    
    public static UUID getUUID(final GameProfile gameProfile) {
        UUID uuid = gameProfile.getId();
        if (uuid == null) {
            uuid = getOfflineUUID(gameProfile.getName());
        }
        return uuid;
    }
    
    public void setScore(final int n) {
        this.dataWatcher.updateObject(18, n);
    }
    
    @Override
    public void onDeath(final DamageSource damageSource) {
        super.onDeath(damageSource);
        this.setSize(0.2f, 0.2f);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.motionY = 0.10000000149011612;
        if (this.getName().equals("Notch")) {
            this.dropItem(new ItemStack(Items.apple, 1), true, false);
        }
        if (!this.worldObj.getGameRules().getBoolean("keepInventory")) {
            this.inventory.dropAllItems();
        }
        if (damageSource != null) {
            this.motionX = -MathHelper.cos((this.attackedAtYaw + this.rotationYaw) * 3.1415927f / 180.0f) * 0.1f;
            this.motionZ = -MathHelper.sin((this.attackedAtYaw + this.rotationYaw) * 3.1415927f / 180.0f) * 0.1f;
        }
        else {
            final double n = 0.0;
            this.motionZ = n;
            this.motionX = n;
        }
        this.triggerAchievement(StatList.deathsStat);
        this.func_175145_a(StatList.timeSinceDeathStat);
    }
    
    @Override
    public void setAbsorptionAmount(float n) {
        if (n < 0.0f) {
            n = 0.0f;
        }
        this.getDataWatcher().updateObject(17, n);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, 0);
        this.dataWatcher.addObject(17, 0.0f);
        this.dataWatcher.addObject(18, 0);
        this.dataWatcher.addObject(10, 0);
    }
    
    public static UUID getOfflineUUID(final String s) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + s).getBytes(Charsets.UTF_8));
    }
    
    @Override
    protected String getSplashSound() {
        return "game.player.swim.splash";
    }
    
    public InventoryEnderChest getInventoryEnderChest() {
        return this.theInventoryEnderChest;
    }
    
    public boolean canEat(final boolean b) {
        return (b || this.foodStats.needFood()) && !this.capabilities.disableDamage;
    }
    
    public EntityPlayer(final World world, final GameProfile gameProfile) {
        super(world);
        this.inventory = new InventoryPlayer(this);
        this.theInventoryEnderChest = new InventoryEnderChest();
        this.foodStats = new FoodStats();
        this.capabilities = new PlayerCapabilities();
        this.speedOnGround = 0.1f;
        this.speedInAir = 0.02f;
        this.hasReducedDebug = false;
        this.entityUniqueID = getUUID(gameProfile);
        this.gameProfile = gameProfile;
        this.inventoryContainer = new ContainerPlayer(this.inventory, !world.isRemote, this);
        this.openContainer = this.inventoryContainer;
        final BlockPos spawnPoint = world.getSpawnPoint();
        this.setLocationAndAngles(spawnPoint.getX() + 0.5, spawnPoint.getY() + 1, spawnPoint.getZ() + 0.5, 0.0f, 0.0f);
        this.field_70741_aB = 180.0f;
        this.fireResistance = 20;
    }
    
    @Override
    public boolean isPushedByWater() {
        return !this.capabilities.isFlying;
    }
    
    public float getToolDigEfficiency(final Block block) {
        float strVsBlock = this.inventory.getStrVsBlock(block);
        if (strVsBlock > 1.0f) {
            final int efficiencyModifier = EnchantmentHelper.getEfficiencyModifier(this);
            final ItemStack currentItem = this.inventory.getCurrentItem();
            if (efficiencyModifier > 0 && currentItem != null) {
                strVsBlock += efficiencyModifier * efficiencyModifier + 1;
            }
        }
        if (this.isPotionActive(Potion.digSpeed)) {
            strVsBlock *= 1.0f + (this.getActivePotionEffect(Potion.digSpeed).getAmplifier() + 1) * 0.2f;
        }
        if (this.isPotionActive(Potion.digSlowdown)) {
            float n = 0.0f;
            switch (this.getActivePotionEffect(Potion.digSlowdown).getAmplifier()) {
                case 0: {
                    n = 0.3f;
                    break;
                }
                case 1: {
                    n = 0.09f;
                    break;
                }
                case 2: {
                    n = 0.0027f;
                    break;
                }
                default: {
                    n = 8.1E-4f;
                    break;
                }
            }
            strVsBlock *= n;
        }
        if (this.isInsideOfMaterial(Material.water) && !EnchantmentHelper.getAquaAffinityModifier(this)) {
            strVsBlock /= 5.0f;
        }
        if (!this.onGround) {
            strVsBlock /= 5.0f;
        }
        return strVsBlock;
    }
    
    public int xpBarCap() {
        return (this.experienceLevel >= 30) ? (112 + (this.experienceLevel - 30) * 9) : ((this.experienceLevel >= 15) ? (37 + (this.experienceLevel - 15) * 5) : (7 + this.experienceLevel * 2));
    }
    
    public EnumStatus trySleep(final BlockPos playerLocation) {
        if (!this.worldObj.isRemote) {
            if (this.isPlayerSleeping() || !this.isEntityAlive()) {
                return EnumStatus.OTHER_PROBLEM;
            }
            if (!this.worldObj.provider.isSurfaceWorld()) {
                return EnumStatus.NOT_POSSIBLE_HERE;
            }
            if (this.worldObj.isDaytime()) {
                return EnumStatus.NOT_POSSIBLE_NOW;
            }
            if (Math.abs(this.posX - playerLocation.getX()) > 3.0 || Math.abs(this.posY - playerLocation.getY()) > 2.0 || Math.abs(this.posZ - playerLocation.getZ()) > 3.0) {
                return EnumStatus.TOO_FAR_AWAY;
            }
            final double n = 8.0;
            final double n2 = 5.0;
            if (!this.worldObj.getEntitiesWithinAABB(EntityMob.class, new AxisAlignedBB(playerLocation.getX() - n, playerLocation.getY() - n2, playerLocation.getZ() - n, playerLocation.getX() + n, playerLocation.getY() + n2, playerLocation.getZ() + n)).isEmpty()) {
                return EnumStatus.NOT_SAFE;
            }
        }
        if (this.isRiding()) {
            this.mountEntity(null);
        }
        this.setSize(0.2f, 0.2f);
        if (this.worldObj.isBlockLoaded(playerLocation)) {
            final EnumFacing enumFacing = (EnumFacing)this.worldObj.getBlockState(playerLocation).getValue(BlockDirectional.FACING);
            float n3 = 0.5f;
            float n4 = 0.5f;
            switch (EntityPlayer$1.$SwitchMap$net$minecraft$util$EnumFacing[enumFacing.ordinal()]) {
                case 1: {
                    n4 = 0.9f;
                    break;
                }
                case 2: {
                    n4 = 0.1f;
                    break;
                }
                case 3: {
                    n3 = 0.1f;
                    break;
                }
                case 4: {
                    n3 = 0.9f;
                    break;
                }
            }
            this.func_175139_a(enumFacing);
            this.setPosition(playerLocation.getX() + n3, playerLocation.getY() + 0.6875f, playerLocation.getZ() + n4);
        }
        else {
            this.setPosition(playerLocation.getX() + 0.5f, playerLocation.getY() + 0.6875f, playerLocation.getZ() + 0.5f);
        }
        this.sleeping = true;
        this.sleepTimer = 0;
        this.playerLocation = playerLocation;
        final double motionX = 0.0;
        this.motionY = motionX;
        this.motionZ = motionX;
        this.motionX = motionX;
        if (!this.worldObj.isRemote) {
            this.worldObj.updateAllPlayersSleepingFlag();
        }
        return EnumStatus.OK;
    }
    
    public boolean canAttackPlayer(final EntityPlayer entityPlayer) {
        final Team team = this.getTeam();
        final Team team2 = entityPlayer.getTeam();
        return team == null || !team.isSameTeam(team2) || team.getAllowFriendlyFire();
    }
    
    public ItemStack getCurrentEquippedItem() {
        return this.inventory.getCurrentItem();
    }
    
    @Override
    public void addToPlayerScore(final Entity entity, final int n) {
        this.addScore(n);
        final Collection objectivesFromCriteria = this.getWorldScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.totalKillCount);
        if (entity instanceof EntityPlayer) {
            this.triggerAchievement(StatList.playerKillsStat);
            objectivesFromCriteria.addAll(this.getWorldScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.playerKillCount));
            objectivesFromCriteria.addAll(this.func_175137_e(entity));
        }
        else {
            this.triggerAchievement(StatList.mobKillsStat);
        }
        final Iterator<ScoreObjective> iterator = objectivesFromCriteria.iterator();
        while (iterator.hasNext()) {
            this.getWorldScoreboard().getValueFromObjective(this.getName(), iterator.next()).func_96648_a();
        }
    }
    
    public float getBedOrientationInDegrees() {
        if (this.playerLocation != null) {
            switch (EntityPlayer$1.$SwitchMap$net$minecraft$util$EnumFacing[((EnumFacing)this.worldObj.getBlockState(this.playerLocation).getValue(BlockDirectional.FACING)).ordinal()]) {
                case 1: {
                    return 90.0f;
                }
                case 2: {
                    return 270.0f;
                }
                case 3: {
                    return 0.0f;
                }
                case 4: {
                    return 180.0f;
                }
            }
        }
        return 0.0f;
    }
    
    @Override
    public IChatComponent getDisplayName() {
        final ChatComponentText chatComponentText = new ChatComponentText(ScorePlayerTeam.formatPlayerName(this.getTeam(), this.getName()));
        chatComponentText.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + this.getName() + " "));
        chatComponentText.getChatStyle().setChatHoverEvent(this.getHoverEvent());
        chatComponentText.getChatStyle().setInsertion(this.getName());
        return chatComponentText;
    }
    
    protected void closeScreen() {
        this.openContainer = this.inventoryContainer;
    }
    
    @Override
    public boolean getAlwaysRenderNameTagForRender() {
        return true;
    }
    
    @Override
    public boolean isInvisibleToPlayer(final EntityPlayer entityPlayer) {
        if (!this.isInvisible()) {
            return false;
        }
        if (entityPlayer.isSpectator()) {
            return false;
        }
        final Team team = this.getTeam();
        return team == null || entityPlayer == null || entityPlayer.getTeam() != team || !team.getSeeFriendlyInvisiblesEnabled();
    }
    
    @Override
    public float getEyeHeight() {
        float n = 1.62f;
        if (this.isPlayerSleeping()) {
            n = 0.2f;
        }
        if (this.isSneaking()) {
            n -= 0.08f;
        }
        return n;
    }
    
    public boolean isUser() {
        return false;
    }
    
    @Override
    public boolean sendCommandFeedback() {
        return MinecraftServer.getServer().worldServers[0].getGameRules().getBoolean("sendCommandFeedback");
    }
    
    @Override
    public ItemStack getHeldItem() {
        return this.inventory.getCurrentItem();
    }
    
    public void addExperienceLevel(final int n) {
        this.experienceLevel += n;
        if (this.experienceLevel < 0) {
            this.experienceLevel = 0;
            this.experience = 0.0f;
            this.experienceTotal = 0;
        }
        if (n > 0 && this.experienceLevel % 5 == 0 && this.lastXPSound < this.ticksExisted - 100.0f) {
            this.worldObj.playSoundAtEntity(this, "random.levelup", ((this.experienceLevel > 30) ? 1.0f : (this.experienceLevel / 30.0f)) * 0.75f, 1.0f);
            this.lastXPSound = this.ticksExisted;
        }
    }
    
    @Override
    public float getAbsorptionAmount() {
        return this.getDataWatcher().getWatchableObjectFloat(17);
    }
    
    public int getXPSeed() {
        return this.xpSeed;
    }
    
    @Override
    protected void damageEntity(final DamageSource damageSource, float n) {
        if (!this.isEntityInvulnerable(damageSource)) {
            if (!damageSource.isUnblockable() && this != 0 && n > 0.0f) {
                n = (1.0f + n) * 0.5f;
            }
            n = this.applyArmorCalculations(damageSource, n);
            final float applyPotionDamageCalculations;
            n = (applyPotionDamageCalculations = this.applyPotionDamageCalculations(damageSource, n));
            n = Math.max(n - this.getAbsorptionAmount(), 0.0f);
            this.setAbsorptionAmount(this.getAbsorptionAmount() - (applyPotionDamageCalculations - n));
            if (n != 0.0f) {
                this.addExhaustion(damageSource.getHungerDamage());
                final float health = this.getHealth();
                this.setHealth(this.getHealth() - n);
                this.getCombatTracker().trackDamage(damageSource, health, n);
                if (n < 3.4028235E37f) {
                    this.addStat(StatList.damageTakenStat, Math.round(n * 10.0f));
                }
            }
        }
    }
    
    public void stopUsingItem() {
        if (this.itemInUse != null) {
            this.itemInUse.onPlayerStoppedUsing(this.worldObj, this, this.itemInUseCount);
        }
        this.clearItemInUse();
    }
    
    @Override
    public void fall(final float n, final float n2) {
        if (!this.capabilities.allowFlying) {
            if (n >= 2.0f) {
                this.addStat(StatList.distanceFallenStat, (int)Math.round(n * 100.0));
            }
            super.fall(n, n2);
        }
    }
    
    public void setSpawnPoint(final BlockPos spawnChunk, final boolean spawnForced) {
        if (spawnChunk != null) {
            this.spawnChunk = spawnChunk;
            this.spawnForced = spawnForced;
        }
        else {
            this.spawnChunk = null;
            this.spawnForced = false;
        }
    }
    
    public void setReducedDebug(final boolean hasReducedDebug) {
        this.hasReducedDebug = hasReducedDebug;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, float n) {
        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        }
        if (this.capabilities.disableDamage && !damageSource.canHarmInCreative()) {
            return false;
        }
        this.entityAge = 0;
        if (this.getHealth() <= 0.0f) {
            return false;
        }
        if (this.isPlayerSleeping() && !this.worldObj.isRemote) {
            this.wakeUpPlayer(true, true, false);
        }
        if (damageSource.isDifficultyScaled()) {
            if (this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL) {
                n = 0.0f;
            }
            if (this.worldObj.getDifficulty() == EnumDifficulty.EASY) {
                n = n / 2.0f + 1.0f;
            }
            if (this.worldObj.getDifficulty() == EnumDifficulty.HARD) {
                n = n * 3.0f / 2.0f;
            }
        }
        if (n == 0.0f) {
            return false;
        }
        final Entity entity = damageSource.getEntity();
        if (entity instanceof EntityArrow && ((EntityArrow)entity).shootingEntity != null) {
            final Entity shootingEntity = ((EntityArrow)entity).shootingEntity;
        }
        return super.attackEntityFrom(damageSource, n);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setTag("Inventory", this.inventory.writeToNBT(new NBTTagList()));
        nbtTagCompound.setInteger("SelectedItemSlot", this.inventory.currentItem);
        nbtTagCompound.setBoolean("Sleeping", this.sleeping);
        nbtTagCompound.setShort("SleepTimer", (short)this.sleepTimer);
        nbtTagCompound.setFloat("XpP", this.experience);
        nbtTagCompound.setInteger("XpLevel", this.experienceLevel);
        nbtTagCompound.setInteger("XpTotal", this.experienceTotal);
        nbtTagCompound.setInteger("XpSeed", this.xpSeed);
        nbtTagCompound.setInteger("Score", this.getScore());
        if (this.spawnChunk != null) {
            nbtTagCompound.setInteger("SpawnX", this.spawnChunk.getX());
            nbtTagCompound.setInteger("SpawnY", this.spawnChunk.getY());
            nbtTagCompound.setInteger("SpawnZ", this.spawnChunk.getZ());
            nbtTagCompound.setBoolean("SpawnForced", this.spawnForced);
        }
        this.foodStats.writeNBT(nbtTagCompound);
        this.capabilities.writeCapabilitiesToNBT(nbtTagCompound);
        nbtTagCompound.setTag("EnderItems", this.theInventoryEnderChest.saveInventoryToNBT());
        final ItemStack currentItem = this.inventory.getCurrentItem();
        if (currentItem != null && currentItem.getItem() != null) {
            nbtTagCompound.setTag("SelectedItem", currentItem.writeToNBT(new NBTTagCompound()));
        }
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.10000000149011612);
    }
    
    public ItemStack getItemInUse() {
        return this.itemInUse;
    }
    
    @Override
    protected String getFallSoundString(final int n) {
        return (n > 4) ? "game.player.hurt.fall.big" : "game.player.hurt.fall.small";
    }
    
    public int getScore() {
        return this.dataWatcher.getWatchableObjectInt(18);
    }
    
    public void addStat(final StatBase statBase, final int n) {
    }
    
    public void removeExperienceLevel(final int n) {
        this.experienceLevel -= n;
        if (this.experienceLevel < 0) {
            this.experienceLevel = 0;
            this.experience = 0.0f;
            this.experienceTotal = 0;
        }
        this.xpSeed = this.rand.nextInt();
    }
    
    @Override
    public boolean isPlayerSleeping() {
        return this.sleeping;
    }
    
    public void displayGUIHorse(final EntityHorse entityHorse, final IInventory inventory) {
    }
    
    protected void updateItemUse(final ItemStack itemStack, final int n) {
        if (itemStack.getItemUseAction() == EnumAction.DRINK) {
            this.playSound("random.drink", 0.5f, this.worldObj.rand.nextFloat() * 0.1f + 0.9f);
        }
        if (itemStack.getItemUseAction() == EnumAction.EAT) {
            while (0 < n) {
                final Vec3 rotateYaw = new Vec3((this.rand.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0).rotatePitch(-this.rotationPitch * 3.1415927f / 180.0f).rotateYaw(-this.rotationYaw * 3.1415927f / 180.0f);
                final Vec3 addVector = new Vec3((this.rand.nextFloat() - 0.5) * 0.3, -this.rand.nextFloat() * 0.6 - 0.3, 0.6).rotatePitch(-this.rotationPitch * 3.1415927f / 180.0f).rotateYaw(-this.rotationYaw * 3.1415927f / 180.0f).addVector(this.posX, this.posY + this.getEyeHeight(), this.posZ);
                if (itemStack.getHasSubtypes()) {
                    this.worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, addVector.xCoord, addVector.yCoord, addVector.zCoord, rotateYaw.xCoord, rotateYaw.yCoord + 0.05, rotateYaw.zCoord, Item.getIdFromItem(itemStack.getItem()), itemStack.getMetadata());
                }
                else {
                    this.worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, addVector.xCoord, addVector.yCoord, addVector.zCoord, rotateYaw.xCoord, rotateYaw.yCoord + 0.05, rotateYaw.zCoord, Item.getIdFromItem(itemStack.getItem()));
                }
                int n2 = 0;
                ++n2;
            }
            this.playSound("random.eat", 0.5f + 0.5f * this.rand.nextInt(2), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
        }
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return !this.capabilities.isFlying;
    }
    
    @Override
    protected void damageArmor(final float n) {
        this.inventory.damageArmor(n);
    }
    
    public void openEditCommandBlock(final CommandBlockLogic commandBlockLogic) {
    }
    
    public boolean isSpawnForced() {
        return this.spawnForced;
    }
    
    public boolean shouldHeal() {
        return this.getHealth() > 0.0f && this.getHealth() < this.getMaxHealth();
    }
    
    @Override
    public void moveEntityWithHeading(final float n, final float n2) {
        final double posX = this.posX;
        final double posY = this.posY;
        final double posZ = this.posZ;
        if (this.capabilities.isFlying && this.ridingEntity == null) {
            final double motionY = this.motionY;
            final float jumpMovementFactor = this.jumpMovementFactor;
            this.jumpMovementFactor = this.capabilities.getFlySpeed() * (this.isSprinting() ? 2 : 1);
            super.moveEntityWithHeading(n, n2);
            this.motionY = motionY * 0.6;
            this.jumpMovementFactor = jumpMovementFactor;
        }
        else {
            super.moveEntityWithHeading(n, n2);
        }
        this.addMovementStat(this.posX - posX, this.posY - posY, this.posZ - posZ);
    }
    
    @Override
    protected void updateEntityActionState() {
        super.updateEntityActionState();
        this.updateArmSwingProgress();
        this.rotationYawHead = this.rotationYaw;
    }
    
    @Override
    public void handleStatusUpdate(final byte b) {
        if (b == 9) {
            this.onItemUseFinish();
        }
        else if (b == 23) {
            this.hasReducedDebug = false;
        }
        else if (b == 22) {
            this.hasReducedDebug = true;
        }
        else {
            super.handleStatusUpdate(b);
        }
    }
    
    public void addExperience(int n) {
        this.addScore(n);
        final int n2 = Integer.MAX_VALUE - this.experienceTotal;
        if (n > n2) {
            n = n2;
        }
        this.experience += n / (float)this.xpBarCap();
        this.experienceTotal += n;
        while (this.experience >= 1.0f) {
            this.experience = (this.experience - 1.0f) * this.xpBarCap();
            this.addExperienceLevel(1);
            this.experience /= this.xpBarCap();
        }
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.entityUniqueID = getUUID(this.gameProfile);
        this.inventory.readFromNBT(nbtTagCompound.getTagList("Inventory", 10));
        this.inventory.currentItem = nbtTagCompound.getInteger("SelectedItemSlot");
        this.sleeping = nbtTagCompound.getBoolean("Sleeping");
        this.sleepTimer = nbtTagCompound.getShort("SleepTimer");
        this.experience = nbtTagCompound.getFloat("XpP");
        this.experienceLevel = nbtTagCompound.getInteger("XpLevel");
        this.experienceTotal = nbtTagCompound.getInteger("XpTotal");
        this.xpSeed = nbtTagCompound.getInteger("XpSeed");
        if (this.xpSeed == 0) {
            this.xpSeed = this.rand.nextInt();
        }
        this.setScore(nbtTagCompound.getInteger("Score"));
        if (this.sleeping) {
            this.playerLocation = new BlockPos(this);
            this.wakeUpPlayer(true, true, false);
        }
        if (nbtTagCompound.hasKey("SpawnX", 99) && nbtTagCompound.hasKey("SpawnY", 99) && nbtTagCompound.hasKey("SpawnZ", 99)) {
            this.spawnChunk = new BlockPos(nbtTagCompound.getInteger("SpawnX"), nbtTagCompound.getInteger("SpawnY"), nbtTagCompound.getInteger("SpawnZ"));
            this.spawnForced = nbtTagCompound.getBoolean("SpawnForced");
        }
        this.foodStats.readNBT(nbtTagCompound);
        this.capabilities.readCapabilitiesFromNBT(nbtTagCompound);
        if (nbtTagCompound.hasKey("EnderItems", 9)) {
            this.theInventoryEnderChest.loadInventoryFromNBT(nbtTagCompound.getTagList("EnderItems", 10));
        }
    }
    
    public static BlockPos getBedSpawnLocation(final World world, final BlockPos blockPos, final boolean b) {
        final Block block = world.getBlockState(blockPos).getBlock();
        if (block == Blocks.bed) {
            return BlockBed.getSafeExitLocation(world, blockPos, 0);
        }
        if (!b) {
            return null;
        }
        final boolean func_181623_g = block.func_181623_g();
        final boolean func_181623_g2 = world.getBlockState(blockPos.up()).getBlock().func_181623_g();
        return (func_181623_g && func_181623_g2) ? blockPos : null;
    }
    
    @Override
    public void setInWeb() {
        if (!this.capabilities.isFlying) {
            super.setInWeb();
        }
    }
    
    @Override
    protected void resetHeight() {
        if (!this.isSpectator()) {
            super.resetHeight();
        }
    }
    
    public boolean interactWith(final Entity entity) {
        if (this.isSpectator()) {
            if (entity instanceof IInventory) {
                this.displayGUIChest((IInventory)entity);
            }
            return false;
        }
        ItemStack currentEquippedItem = this.getCurrentEquippedItem();
        final ItemStack itemStack = (currentEquippedItem != null) ? currentEquippedItem.copy() : null;
        if (!entity.interactFirst(this)) {
            if (currentEquippedItem != null && entity instanceof EntityLivingBase) {
                if (this.capabilities.isCreativeMode) {
                    currentEquippedItem = itemStack;
                }
                if (currentEquippedItem.interactWithEntity(this, (EntityLivingBase)entity)) {
                    if (currentEquippedItem.stackSize <= 0 && !this.capabilities.isCreativeMode) {
                        this.destroyCurrentEquippedItem();
                    }
                    return true;
                }
            }
            return false;
        }
        if (currentEquippedItem != null && currentEquippedItem == this.getCurrentEquippedItem()) {
            if (currentEquippedItem.stackSize <= 0 && !this.capabilities.isCreativeMode) {
                this.destroyCurrentEquippedItem();
            }
            else if (currentEquippedItem.stackSize < itemStack.stackSize && this.capabilities.isCreativeMode) {
                currentEquippedItem.stackSize = itemStack.stackSize;
            }
        }
        return true;
    }
    
    public float getArmorVisibility() {
        final ItemStack[] armorInventory = this.inventory.armorInventory;
        while (0 < armorInventory.length) {
            if (armorInventory[0] != null) {
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        return 0 / (float)this.inventory.armorInventory.length;
    }
    
    private Collection func_175137_e(final Entity entity) {
        final ScorePlayerTeam playersTeam = this.getWorldScoreboard().getPlayersTeam(this.getName());
        if (playersTeam != null) {
            final int colorIndex = playersTeam.getChatFormat().getColorIndex();
            if (colorIndex >= 0 && colorIndex < IScoreObjectiveCriteria.field_178793_i.length) {
                final Iterator iterator = this.getWorldScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.field_178793_i[colorIndex]).iterator();
                while (iterator.hasNext()) {
                    this.getWorldScoreboard().getValueFromObjective(entity.getName(), iterator.next()).func_96648_a();
                }
            }
        }
        final ScorePlayerTeam playersTeam2 = this.getWorldScoreboard().getPlayersTeam(entity.getName());
        if (playersTeam2 != null) {
            final int colorIndex2 = playersTeam2.getChatFormat().getColorIndex();
            if (colorIndex2 >= 0 && colorIndex2 < IScoreObjectiveCriteria.field_178792_h.length) {
                return this.getWorldScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.field_178792_h[colorIndex2]);
            }
        }
        return Lists.newArrayList();
    }
    
    public void wakeUpPlayer(final boolean b, final boolean b2, final boolean b3) {
        this.setSize(0.6f, 1.8f);
        final IBlockState blockState = this.worldObj.getBlockState(this.playerLocation);
        if (this.playerLocation != null && blockState.getBlock() == Blocks.bed) {
            this.worldObj.setBlockState(this.playerLocation, blockState.withProperty(BlockBed.OCCUPIED, false), 4);
            BlockPos blockPos = BlockBed.getSafeExitLocation(this.worldObj, this.playerLocation, 0);
            if (blockPos == null) {
                blockPos = this.playerLocation.up();
            }
            this.setPosition(blockPos.getX() + 0.5f, blockPos.getY() + 0.1f, blockPos.getZ() + 0.5f);
        }
        this.sleeping = false;
        if (!this.worldObj.isRemote && b2) {
            this.worldObj.updateAllPlayersSleepingFlag();
        }
        this.sleepTimer = (b ? 0 : 100);
        if (b3) {
            this.setSpawnPoint(this.playerLocation, false);
        }
    }
    
    public void jump() {
        super.jump();
        this.triggerAchievement(StatList.jumpStat);
        if (this.isSprinting()) {
            this.addExhaustion(0.8f);
        }
        else {
            this.addExhaustion(0.2f);
        }
    }
    
    @Override
    public void onUpdate() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_0        
        //     2: invokevirtual   net/minecraft/entity/player/EntityPlayer.isSpectator:()Z
        //     5: putfield        net/minecraft/entity/player/EntityPlayer.noClip:Z
        //     8: aload_0        
        //     9: invokevirtual   net/minecraft/entity/player/EntityPlayer.isSpectator:()Z
        //    12: ifeq            20
        //    15: aload_0        
        //    16: iconst_0       
        //    17: putfield        net/minecraft/entity/player/EntityPlayer.onGround:Z
        //    20: aload_0        
        //    21: getfield        net/minecraft/entity/player/EntityPlayer.itemInUse:Lnet/minecraft/item/ItemStack;
        //    24: ifnull          102
        //    27: aload_0        
        //    28: getfield        net/minecraft/entity/player/EntityPlayer.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //    31: invokevirtual   net/minecraft/entity/player/InventoryPlayer.getCurrentItem:()Lnet/minecraft/item/ItemStack;
        //    34: astore_1       
        //    35: aload_1        
        //    36: aload_0        
        //    37: getfield        net/minecraft/entity/player/EntityPlayer.itemInUse:Lnet/minecraft/item/ItemStack;
        //    40: if_acmpne       98
        //    43: aload_0        
        //    44: getfield        net/minecraft/entity/player/EntityPlayer.itemInUseCount:I
        //    47: bipush          25
        //    49: if_icmpgt       67
        //    52: aload_0        
        //    53: getfield        net/minecraft/entity/player/EntityPlayer.itemInUseCount:I
        //    56: iconst_4       
        //    57: irem           
        //    58: ifne            67
        //    61: aload_0        
        //    62: aload_1        
        //    63: iconst_5       
        //    64: invokevirtual   net/minecraft/entity/player/EntityPlayer.updateItemUse:(Lnet/minecraft/item/ItemStack;I)V
        //    67: aload_0        
        //    68: dup            
        //    69: getfield        net/minecraft/entity/player/EntityPlayer.itemInUseCount:I
        //    72: iconst_1       
        //    73: isub           
        //    74: dup_x1         
        //    75: putfield        net/minecraft/entity/player/EntityPlayer.itemInUseCount:I
        //    78: ifne            102
        //    81: aload_0        
        //    82: getfield        net/minecraft/entity/player/EntityPlayer.worldObj:Lnet/minecraft/world/World;
        //    85: getfield        net/minecraft/world/World.isRemote:Z
        //    88: ifne            102
        //    91: aload_0        
        //    92: invokevirtual   net/minecraft/entity/player/EntityPlayer.onItemUseFinish:()V
        //    95: goto            102
        //    98: aload_0        
        //    99: invokevirtual   net/minecraft/entity/player/EntityPlayer.clearItemInUse:()V
        //   102: aload_0        
        //   103: getfield        net/minecraft/entity/player/EntityPlayer.xpCooldown:I
        //   106: ifle            119
        //   109: aload_0        
        //   110: dup            
        //   111: getfield        net/minecraft/entity/player/EntityPlayer.xpCooldown:I
        //   114: iconst_1       
        //   115: isub           
        //   116: putfield        net/minecraft/entity/player/EntityPlayer.xpCooldown:I
        //   119: aload_0        
        //   120: invokevirtual   net/minecraft/entity/player/EntityPlayer.isPlayerSleeping:()Z
        //   123: ifeq            195
        //   126: aload_0        
        //   127: dup            
        //   128: getfield        net/minecraft/entity/player/EntityPlayer.sleepTimer:I
        //   131: iconst_1       
        //   132: iadd           
        //   133: putfield        net/minecraft/entity/player/EntityPlayer.sleepTimer:I
        //   136: aload_0        
        //   137: getfield        net/minecraft/entity/player/EntityPlayer.sleepTimer:I
        //   140: bipush          100
        //   142: if_icmple       151
        //   145: aload_0        
        //   146: bipush          100
        //   148: putfield        net/minecraft/entity/player/EntityPlayer.sleepTimer:I
        //   151: aload_0        
        //   152: getfield        net/minecraft/entity/player/EntityPlayer.worldObj:Lnet/minecraft/world/World;
        //   155: getfield        net/minecraft/world/World.isRemote:Z
        //   158: ifne            226
        //   161: aload_0        
        //   162: if_acmpne       175
        //   165: aload_0        
        //   166: iconst_1       
        //   167: iconst_1       
        //   168: iconst_0       
        //   169: invokevirtual   net/minecraft/entity/player/EntityPlayer.wakeUpPlayer:(ZZZ)V
        //   172: goto            226
        //   175: aload_0        
        //   176: getfield        net/minecraft/entity/player/EntityPlayer.worldObj:Lnet/minecraft/world/World;
        //   179: invokevirtual   net/minecraft/world/World.isDaytime:()Z
        //   182: ifeq            226
        //   185: aload_0        
        //   186: iconst_0       
        //   187: iconst_1       
        //   188: iconst_1       
        //   189: invokevirtual   net/minecraft/entity/player/EntityPlayer.wakeUpPlayer:(ZZZ)V
        //   192: goto            226
        //   195: aload_0        
        //   196: getfield        net/minecraft/entity/player/EntityPlayer.sleepTimer:I
        //   199: ifle            226
        //   202: aload_0        
        //   203: dup            
        //   204: getfield        net/minecraft/entity/player/EntityPlayer.sleepTimer:I
        //   207: iconst_1       
        //   208: iadd           
        //   209: putfield        net/minecraft/entity/player/EntityPlayer.sleepTimer:I
        //   212: aload_0        
        //   213: getfield        net/minecraft/entity/player/EntityPlayer.sleepTimer:I
        //   216: bipush          110
        //   218: if_icmplt       226
        //   221: aload_0        
        //   222: iconst_0       
        //   223: putfield        net/minecraft/entity/player/EntityPlayer.sleepTimer:I
        //   226: aload_0        
        //   227: invokespecial   net/minecraft/entity/EntityLivingBase.onUpdate:()V
        //   230: aload_0        
        //   231: getfield        net/minecraft/entity/player/EntityPlayer.worldObj:Lnet/minecraft/world/World;
        //   234: getfield        net/minecraft/world/World.isRemote:Z
        //   237: ifne            270
        //   240: aload_0        
        //   241: getfield        net/minecraft/entity/player/EntityPlayer.openContainer:Lnet/minecraft/inventory/Container;
        //   244: ifnull          270
        //   247: aload_0        
        //   248: getfield        net/minecraft/entity/player/EntityPlayer.openContainer:Lnet/minecraft/inventory/Container;
        //   251: aload_0        
        //   252: invokevirtual   net/minecraft/inventory/Container.canInteractWith:(Lnet/minecraft/entity/player/EntityPlayer;)Z
        //   255: ifne            270
        //   258: aload_0        
        //   259: invokevirtual   net/minecraft/entity/player/EntityPlayer.closeScreen:()V
        //   262: aload_0        
        //   263: aload_0        
        //   264: getfield        net/minecraft/entity/player/EntityPlayer.inventoryContainer:Lnet/minecraft/inventory/Container;
        //   267: putfield        net/minecraft/entity/player/EntityPlayer.openContainer:Lnet/minecraft/inventory/Container;
        //   270: aload_0        
        //   271: invokevirtual   net/minecraft/entity/player/EntityPlayer.isBurning:()Z
        //   274: ifeq            291
        //   277: aload_0        
        //   278: getfield        net/minecraft/entity/player/EntityPlayer.capabilities:Lnet/minecraft/entity/player/PlayerCapabilities;
        //   281: getfield        net/minecraft/entity/player/PlayerCapabilities.disableDamage:Z
        //   284: ifeq            291
        //   287: aload_0        
        //   288: invokevirtual   net/minecraft/entity/player/EntityPlayer.extinguish:()V
        //   291: aload_0        
        //   292: aload_0        
        //   293: getfield        net/minecraft/entity/player/EntityPlayer.chasingPosX:D
        //   296: putfield        net/minecraft/entity/player/EntityPlayer.prevChasingPosX:D
        //   299: aload_0        
        //   300: aload_0        
        //   301: getfield        net/minecraft/entity/player/EntityPlayer.chasingPosY:D
        //   304: putfield        net/minecraft/entity/player/EntityPlayer.prevChasingPosY:D
        //   307: aload_0        
        //   308: aload_0        
        //   309: getfield        net/minecraft/entity/player/EntityPlayer.chasingPosZ:D
        //   312: putfield        net/minecraft/entity/player/EntityPlayer.prevChasingPosZ:D
        //   315: aload_0        
        //   316: getfield        net/minecraft/entity/player/EntityPlayer.posX:D
        //   319: aload_0        
        //   320: getfield        net/minecraft/entity/player/EntityPlayer.chasingPosX:D
        //   323: dsub           
        //   324: dstore_1       
        //   325: aload_0        
        //   326: getfield        net/minecraft/entity/player/EntityPlayer.posY:D
        //   329: aload_0        
        //   330: getfield        net/minecraft/entity/player/EntityPlayer.chasingPosY:D
        //   333: dsub           
        //   334: dstore_3       
        //   335: aload_0        
        //   336: getfield        net/minecraft/entity/player/EntityPlayer.posZ:D
        //   339: aload_0        
        //   340: getfield        net/minecraft/entity/player/EntityPlayer.chasingPosZ:D
        //   343: dsub           
        //   344: dstore          5
        //   346: ldc2_w          10.0
        //   349: dstore          7
        //   351: dload_1        
        //   352: dload           7
        //   354: dcmpl          
        //   355: ifle            371
        //   358: aload_0        
        //   359: aload_0        
        //   360: aload_0        
        //   361: getfield        net/minecraft/entity/player/EntityPlayer.posX:D
        //   364: dup2_x1        
        //   365: putfield        net/minecraft/entity/player/EntityPlayer.chasingPosX:D
        //   368: putfield        net/minecraft/entity/player/EntityPlayer.prevChasingPosX:D
        //   371: dload           5
        //   373: dload           7
        //   375: dcmpl          
        //   376: ifle            392
        //   379: aload_0        
        //   380: aload_0        
        //   381: aload_0        
        //   382: getfield        net/minecraft/entity/player/EntityPlayer.posZ:D
        //   385: dup2_x1        
        //   386: putfield        net/minecraft/entity/player/EntityPlayer.chasingPosZ:D
        //   389: putfield        net/minecraft/entity/player/EntityPlayer.prevChasingPosZ:D
        //   392: dload_3        
        //   393: dload           7
        //   395: dcmpl          
        //   396: ifle            412
        //   399: aload_0        
        //   400: aload_0        
        //   401: aload_0        
        //   402: getfield        net/minecraft/entity/player/EntityPlayer.posY:D
        //   405: dup2_x1        
        //   406: putfield        net/minecraft/entity/player/EntityPlayer.chasingPosY:D
        //   409: putfield        net/minecraft/entity/player/EntityPlayer.prevChasingPosY:D
        //   412: dload_1        
        //   413: dload           7
        //   415: dneg           
        //   416: dcmpg          
        //   417: ifge            433
        //   420: aload_0        
        //   421: aload_0        
        //   422: aload_0        
        //   423: getfield        net/minecraft/entity/player/EntityPlayer.posX:D
        //   426: dup2_x1        
        //   427: putfield        net/minecraft/entity/player/EntityPlayer.chasingPosX:D
        //   430: putfield        net/minecraft/entity/player/EntityPlayer.prevChasingPosX:D
        //   433: dload           5
        //   435: dload           7
        //   437: dneg           
        //   438: dcmpg          
        //   439: ifge            455
        //   442: aload_0        
        //   443: aload_0        
        //   444: aload_0        
        //   445: getfield        net/minecraft/entity/player/EntityPlayer.posZ:D
        //   448: dup2_x1        
        //   449: putfield        net/minecraft/entity/player/EntityPlayer.chasingPosZ:D
        //   452: putfield        net/minecraft/entity/player/EntityPlayer.prevChasingPosZ:D
        //   455: dload_3        
        //   456: dload           7
        //   458: dneg           
        //   459: dcmpg          
        //   460: ifge            476
        //   463: aload_0        
        //   464: aload_0        
        //   465: aload_0        
        //   466: getfield        net/minecraft/entity/player/EntityPlayer.posY:D
        //   469: dup2_x1        
        //   470: putfield        net/minecraft/entity/player/EntityPlayer.chasingPosY:D
        //   473: putfield        net/minecraft/entity/player/EntityPlayer.prevChasingPosY:D
        //   476: aload_0        
        //   477: dup            
        //   478: getfield        net/minecraft/entity/player/EntityPlayer.chasingPosX:D
        //   481: dload_1        
        //   482: ldc2_w          0.25
        //   485: dmul           
        //   486: dadd           
        //   487: putfield        net/minecraft/entity/player/EntityPlayer.chasingPosX:D
        //   490: aload_0        
        //   491: dup            
        //   492: getfield        net/minecraft/entity/player/EntityPlayer.chasingPosZ:D
        //   495: dload           5
        //   497: ldc2_w          0.25
        //   500: dmul           
        //   501: dadd           
        //   502: putfield        net/minecraft/entity/player/EntityPlayer.chasingPosZ:D
        //   505: aload_0        
        //   506: dup            
        //   507: getfield        net/minecraft/entity/player/EntityPlayer.chasingPosY:D
        //   510: dload_3        
        //   511: ldc2_w          0.25
        //   514: dmul           
        //   515: dadd           
        //   516: putfield        net/minecraft/entity/player/EntityPlayer.chasingPosY:D
        //   519: aload_0        
        //   520: getfield        net/minecraft/entity/player/EntityPlayer.ridingEntity:Lnet/minecraft/entity/Entity;
        //   523: ifnonnull       531
        //   526: aload_0        
        //   527: aconst_null    
        //   528: putfield        net/minecraft/entity/player/EntityPlayer.startMinecartRidingCoordinate:Lnet/minecraft/util/BlockPos;
        //   531: aload_0        
        //   532: getfield        net/minecraft/entity/player/EntityPlayer.worldObj:Lnet/minecraft/world/World;
        //   535: getfield        net/minecraft/world/World.isRemote:Z
        //   538: ifne            570
        //   541: aload_0        
        //   542: getfield        net/minecraft/entity/player/EntityPlayer.foodStats:Lnet/minecraft/util/FoodStats;
        //   545: aload_0        
        //   546: invokevirtual   net/minecraft/util/FoodStats.onUpdate:(Lnet/minecraft/entity/player/EntityPlayer;)V
        //   549: aload_0        
        //   550: getstatic       net/minecraft/stats/StatList.minutesPlayedStat:Lnet/minecraft/stats/StatBase;
        //   553: invokevirtual   net/minecraft/entity/player/EntityPlayer.triggerAchievement:(Lnet/minecraft/stats/StatBase;)V
        //   556: aload_0        
        //   557: invokevirtual   net/minecraft/entity/player/EntityPlayer.isEntityAlive:()Z
        //   560: ifeq            570
        //   563: aload_0        
        //   564: getstatic       net/minecraft/stats/StatList.timeSinceDeathStat:Lnet/minecraft/stats/StatBase;
        //   567: invokevirtual   net/minecraft/entity/player/EntityPlayer.triggerAchievement:(Lnet/minecraft/stats/StatBase;)V
        //   570: aload_0        
        //   571: getfield        net/minecraft/entity/player/EntityPlayer.posX:D
        //   574: ldc2_w          -2.9999999E7
        //   577: ldc2_w          2.9999999E7
        //   580: invokestatic    net/minecraft/util/MathHelper.clamp_double:(DDD)D
        //   583: dstore          10
        //   585: aload_0        
        //   586: getfield        net/minecraft/entity/player/EntityPlayer.posZ:D
        //   589: ldc2_w          -2.9999999E7
        //   592: ldc2_w          2.9999999E7
        //   595: invokestatic    net/minecraft/util/MathHelper.clamp_double:(DDD)D
        //   598: dstore          12
        //   600: dload           10
        //   602: aload_0        
        //   603: getfield        net/minecraft/entity/player/EntityPlayer.posX:D
        //   606: dcmpl          
        //   607: ifne            620
        //   610: dload           12
        //   612: aload_0        
        //   613: getfield        net/minecraft/entity/player/EntityPlayer.posZ:D
        //   616: dcmpl          
        //   617: ifeq            632
        //   620: aload_0        
        //   621: dload           10
        //   623: aload_0        
        //   624: getfield        net/minecraft/entity/player/EntityPlayer.posY:D
        //   627: dload           12
        //   629: invokevirtual   net/minecraft/entity/player/EntityPlayer.setPosition:(DDD)V
        //   632: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public float getAIMoveSpeed() {
        return (float)this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
    }
    
    @Override
    public ItemStack getCurrentArmor(final int n) {
        return this.inventory.armorItemInSlot(n);
    }
    
    public void displayGui(final IInteractionObject interactionObject) {
    }
    
    @Override
    public int getMaxInPortalTime() {
        return this.capabilities.disableDamage ? 0 : 80;
    }
    
    @Override
    protected String getHurtSound() {
        return "game.player.hurt";
    }
    
    public void attackTargetEntityWithCurrentItem(final Entity lastAttacker) {
        if (lastAttacker.canAttackWithItem() && !lastAttacker.hitByEntity(this)) {
            float n = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
            float n2;
            if (lastAttacker instanceof EntityLivingBase) {
                n2 = EnchantmentHelper.func_152377_a(this.getHeldItem(), ((EntityLivingBase)lastAttacker).getCreatureAttribute());
            }
            else {
                n2 = EnchantmentHelper.func_152377_a(this.getHeldItem(), EnumCreatureAttribute.UNDEFINED);
            }
            int n3 = 0 + EnchantmentHelper.getKnockbackModifier(this);
            if (this.isSprinting()) {
                ++n3;
            }
            if (n > 0.0f || n2 > 0.0f) {
                final boolean b = this.fallDistance > 0.0f && !this.onGround && !this.isOnLadder() && !this.isInWater() && !this.isPotionActive(Potion.blindness) && this.ridingEntity == null && lastAttacker instanceof EntityLivingBase;
                if (b && n > 0.0f) {
                    n *= 1.5f;
                }
                final float n4 = n + n2;
                final int fireAspectModifier = EnchantmentHelper.getFireAspectModifier(this);
                if (lastAttacker instanceof EntityLivingBase && fireAspectModifier > 0 && !lastAttacker.isBurning()) {
                    lastAttacker.setFire(1);
                }
                final double motionX = lastAttacker.motionX;
                final double motionY = lastAttacker.motionY;
                final double motionZ = lastAttacker.motionZ;
                if (lastAttacker.attackEntityFrom(DamageSource.causePlayerDamage(this), n4)) {
                    if (lastAttacker instanceof EntityPlayerMP && lastAttacker.velocityChanged) {
                        ((EntityPlayerMP)lastAttacker).playerNetServerHandler.sendPacket(new S12PacketEntityVelocity(lastAttacker));
                        lastAttacker.velocityChanged = false;
                        lastAttacker.motionX = motionX;
                        lastAttacker.motionY = motionY;
                        lastAttacker.motionZ = motionZ;
                    }
                    if (b) {
                        this.onCriticalHit(lastAttacker);
                    }
                    if (n2 > 0.0f) {
                        this.onEnchantmentCritical(lastAttacker);
                    }
                    if (n4 >= 18.0f) {
                        this.triggerAchievement(AchievementList.overkill);
                    }
                    this.setLastAttacker(lastAttacker);
                    if (lastAttacker instanceof EntityLivingBase) {
                        EnchantmentHelper.applyThornEnchantments((EntityLivingBase)lastAttacker, this);
                    }
                    EnchantmentHelper.applyArthropodEnchantments(this, lastAttacker);
                    final ItemStack currentEquippedItem = this.getCurrentEquippedItem();
                    Entity entity = lastAttacker;
                    if (lastAttacker instanceof EntityDragonPart) {
                        final IEntityMultiPart entityDragonObj = ((EntityDragonPart)lastAttacker).entityDragonObj;
                        if (entityDragonObj instanceof EntityLivingBase) {
                            entity = (EntityLivingBase)entityDragonObj;
                        }
                    }
                    if (currentEquippedItem != null && entity instanceof EntityLivingBase) {
                        currentEquippedItem.hitEntity((EntityLivingBase)entity, this);
                        if (currentEquippedItem.stackSize <= 0) {
                            this.destroyCurrentEquippedItem();
                        }
                    }
                    if (lastAttacker instanceof EntityLivingBase) {
                        this.addStat(StatList.damageDealtStat, Math.round(n4 * 10.0f));
                        if (fireAspectModifier > 0) {
                            lastAttacker.setFire(fireAspectModifier * 4);
                        }
                    }
                    this.addExhaustion(0.3f);
                }
                else {
                    lastAttacker.extinguish();
                }
            }
        }
    }
    
    @Override
    public void playSound(final String s, final float n, final float n2) {
        this.worldObj.playSoundToNearExcept(this, s, n, n2);
    }
    
    @Override
    public Team getTeam() {
        return this.getWorldScoreboard().getPlayersTeam(this.getName());
    }
    
    public boolean canHarvestBlock(final Block block) {
        return this.inventory.canHeldItemHarvest(block);
    }
    
    private void addMountedMovementStat(final double n, final double n2, final double n3) {
        if (this.ridingEntity != null) {
            final int round = Math.round(MathHelper.sqrt_double(n * n + n2 * n2 + n3 * n3) * 100.0f);
            if (round > 0) {
                if (this.ridingEntity instanceof EntityMinecart) {
                    this.addStat(StatList.distanceByMinecartStat, round);
                    if (this.startMinecartRidingCoordinate == null) {
                        this.startMinecartRidingCoordinate = new BlockPos(this);
                    }
                    else if (this.startMinecartRidingCoordinate.distanceSq(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) >= 1000000.0) {
                        this.triggerAchievement(AchievementList.onARail);
                    }
                }
                else if (this.ridingEntity instanceof EntityBoat) {
                    this.addStat(StatList.distanceByBoatStat, round);
                }
                else if (this.ridingEntity instanceof EntityPig) {
                    this.addStat(StatList.distanceByPigStat, round);
                }
                else if (this.ridingEntity instanceof EntityHorse) {
                    this.addStat(StatList.distanceByHorseStat, round);
                }
            }
        }
    }
    
    private void collideWithPlayer(final Entity entity) {
        entity.onCollideWithPlayer(this);
    }
    
    public boolean hasReducedDebug() {
        return this.hasReducedDebug;
    }
    
    public void preparePlayerToSpawn() {
        this.setSize(0.6f, 1.8f);
        super.preparePlayerToSpawn();
        this.setHealth(this.getMaxHealth());
        this.deathTime = 0;
    }
    
    public int getSleepTimer() {
        return this.sleepTimer;
    }
    
    public Scoreboard getWorldScoreboard() {
        return this.worldObj.getScoreboard();
    }
    
    @Override
    public boolean isEntityInsideOpaqueBlock() {
        return !this.sleeping && super.isEntityInsideOpaqueBlock();
    }
    
    public boolean canPlayerEdit(final BlockPos blockPos, final EnumFacing enumFacing, final ItemStack itemStack) {
        return this.capabilities.allowEdit || (itemStack != null && (itemStack.canPlaceOn(this.worldObj.getBlockState(blockPos.offset(enumFacing.getOpposite())).getBlock()) || itemStack.canEditBlocks()));
    }
    
    public void setGameType(final WorldSettings.GameType gameType) {
    }
    
    @Override
    protected String getSwimSound() {
        return "game.player.swim";
    }
    
    protected void onItemUseFinish() {
        if (this.itemInUse != null) {
            this.updateItemUse(this.itemInUse, 16);
            final int stackSize = this.itemInUse.stackSize;
            final ItemStack onItemUseFinish = this.itemInUse.onItemUseFinish(this.worldObj, this);
            if (onItemUseFinish != this.itemInUse || (onItemUseFinish != null && onItemUseFinish.stackSize != stackSize)) {
                this.inventory.mainInventory[this.inventory.currentItem] = onItemUseFinish;
                if (onItemUseFinish.stackSize == 0) {
                    this.inventory.mainInventory[this.inventory.currentItem] = null;
                }
            }
            this.clearItemInUse();
        }
    }
    
    public boolean isPlayerFullyAsleep() {
        return this.sleeping && this.sleepTimer >= 100;
    }
    
    public BlockPos getBedLocation() {
        return this.spawnChunk;
    }
    
    @Override
    protected boolean isPlayer() {
        return true;
    }
    
    public void sendPlayerAbilities() {
    }
    
    public boolean isWearing(final EnumPlayerModelParts enumPlayerModelParts) {
        return (this.getDataWatcher().getWatchableObjectByte(10) & enumPlayerModelParts.getPartMask()) == enumPlayerModelParts.getPartMask();
    }
    
    public GameProfile getGameProfile() {
        return this.gameProfile;
    }
    
    public EntityItem dropPlayerItemWithRandomChoice(final ItemStack itemStack, final boolean b) {
        return this.dropItem(itemStack, false, false);
    }
    
    public boolean canOpen(final LockCode lockCode) {
        if (lockCode.isEmpty()) {
            return true;
        }
        final ItemStack currentEquippedItem = this.getCurrentEquippedItem();
        return currentEquippedItem != null && currentEquippedItem.hasDisplayName() && currentEquippedItem.getDisplayName().equals(lockCode.getLock());
    }
    
    public void displayVillagerTradeGui(final IMerchant merchant) {
    }
    
    public void addExhaustion(final float n) {
        if (!this.capabilities.disableDamage && !this.worldObj.isRemote) {
            this.foodStats.addExhaustion(n);
        }
    }
    
    public EntityItem dropOneItem(final boolean b) {
        return this.dropItem(this.inventory.decrStackSize(this.inventory.currentItem, (b && this.inventory.getCurrentItem() != null) ? this.inventory.getCurrentItem().stackSize : 1), false, true);
    }
    
    public int getItemInUseDuration() {
        return (this != null) ? (this.itemInUse.getMaxItemUseDuration() - this.itemInUseCount) : 0;
    }
    
    public void clonePlayer(final EntityPlayer entityPlayer, final boolean b) {
        if (b) {
            this.inventory.copyInventory(entityPlayer.inventory);
            this.setHealth(entityPlayer.getHealth());
            this.foodStats = entityPlayer.foodStats;
            this.experienceLevel = entityPlayer.experienceLevel;
            this.experienceTotal = entityPlayer.experienceTotal;
            this.experience = entityPlayer.experience;
            this.setScore(entityPlayer.getScore());
            this.field_181016_an = entityPlayer.field_181016_an;
            this.field_181017_ao = entityPlayer.field_181017_ao;
            this.field_181018_ap = entityPlayer.field_181018_ap;
        }
        else if (this.worldObj.getGameRules().getBoolean("keepInventory")) {
            this.inventory.copyInventory(entityPlayer.inventory);
            this.experienceLevel = entityPlayer.experienceLevel;
            this.experienceTotal = entityPlayer.experienceTotal;
            this.experience = entityPlayer.experience;
            this.setScore(entityPlayer.getScore());
        }
        this.xpSeed = entityPlayer.xpSeed;
        this.theInventoryEnderChest = entityPlayer.theInventoryEnderChest;
        this.getDataWatcher().updateObject(10, entityPlayer.getDataWatcher().getWatchableObjectByte(10));
    }
    
    public FoodStats getFoodStats() {
        return this.foodStats;
    }
    
    public void func_175145_a(final StatBase statBase) {
    }
    
    public enum EnumChatVisibility
    {
        HIDDEN("HIDDEN", 2, 2, "options.chat.visibility.hidden");
        
        private static final EnumChatVisibility[] ID_LOOKUP;
        
        SYSTEM("SYSTEM", 1, 1, "options.chat.visibility.system"), 
        FULL("FULL", 0, 0, "options.chat.visibility.full");
        
        private final int chatVisibility;
        private static final EnumChatVisibility[] $VALUES;
        private final String resourceKey;
        
        public String getResourceKey() {
            return this.resourceKey;
        }
        
        public int getChatVisibility() {
            return this.chatVisibility;
        }
        
        public static EnumChatVisibility getEnumChatVisibility(final int n) {
            return EnumChatVisibility.ID_LOOKUP[n % EnumChatVisibility.ID_LOOKUP.length];
        }
        
        static {
            $VALUES = new EnumChatVisibility[] { EnumChatVisibility.FULL, EnumChatVisibility.SYSTEM, EnumChatVisibility.HIDDEN };
            ID_LOOKUP = new EnumChatVisibility[values().length];
            final EnumChatVisibility[] values = values();
            while (0 < values.length) {
                final EnumChatVisibility enumChatVisibility = values[0];
                EnumChatVisibility.ID_LOOKUP[enumChatVisibility.chatVisibility] = enumChatVisibility;
                int n = 0;
                ++n;
            }
        }
        
        private EnumChatVisibility(final String s, final int n, final int chatVisibility, final String resourceKey) {
            this.chatVisibility = chatVisibility;
            this.resourceKey = resourceKey;
        }
    }
    
    public enum EnumStatus
    {
        NOT_POSSIBLE_NOW("NOT_POSSIBLE_NOW", 2), 
        NOT_SAFE("NOT_SAFE", 5), 
        OK("OK", 0), 
        OTHER_PROBLEM("OTHER_PROBLEM", 4), 
        NOT_POSSIBLE_HERE("NOT_POSSIBLE_HERE", 1);
        
        private static final EnumStatus[] $VALUES;
        
        TOO_FAR_AWAY("TOO_FAR_AWAY", 3);
        
        private EnumStatus(final String s, final int n) {
        }
        
        static {
            $VALUES = new EnumStatus[] { EnumStatus.OK, EnumStatus.NOT_POSSIBLE_HERE, EnumStatus.NOT_POSSIBLE_NOW, EnumStatus.TOO_FAR_AWAY, EnumStatus.OTHER_PROBLEM, EnumStatus.NOT_SAFE };
        }
    }
}
