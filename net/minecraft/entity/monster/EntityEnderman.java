package net.minecraft.entity.monster;

import net.minecraft.entity.ai.attributes.*;
import net.minecraft.nbt.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import com.google.common.collect.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import com.google.common.base.*;
import net.minecraft.entity.ai.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import java.util.*;

public class EntityEnderman extends EntityMob
{
    private static final Set carriableBlocks;
    private static final UUID attackingSpeedBoostModifierUUID;
    private static final AttributeModifier attackingSpeedBoostModifier;
    private boolean isAggressive;
    
    @Override
    protected void updateAITasks() {
        if (this.isWet()) {
            this.attackEntityFrom(DamageSource.drown, 1.0f);
        }
        if (this > 0 && !this.isAggressive && this.rand.nextInt(100) == 0) {
            this.setScreaming(false);
        }
        if (this.worldObj.isDaytime()) {
            final float brightness = this.getBrightness(1.0f);
            if (brightness > 0.5f && this.worldObj.canSeeSky(new BlockPos(this)) && this.rand.nextFloat() * 30.0f < (brightness - 0.4f) * 2.0f) {
                this.setAttackTarget(null);
                this.setScreaming(false);
                this.isAggressive = false;
                this.teleportRandomly();
            }
        }
        super.updateAITasks();
    }
    
    static Set access$300() {
        return EntityEnderman.carriableBlocks;
    }
    
    @Override
    protected String getLivingSound() {
        return (this > 0) ? "mob.endermen.scream" : "mob.endermen.idle";
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        IBlockState heldBlockState;
        if (nbtTagCompound.hasKey("carried", 8)) {
            heldBlockState = Block.getBlockFromName(nbtTagCompound.getString("carried")).getStateFromMeta(nbtTagCompound.getShort("carriedData") & 0xFFFF);
        }
        else {
            heldBlockState = Block.getBlockById(nbtTagCompound.getShort("carried")).getStateFromMeta(nbtTagCompound.getShort("carriedData") & 0xFFFF);
        }
        this.setHeldBlockState(heldBlockState);
    }
    
    @Override
    protected Item getDropItem() {
        return Items.ender_pearl;
    }
    
    public void setHeldBlockState(final IBlockState blockState) {
        this.dataWatcher.updateObject(16, (short)(Block.getStateId(blockState) & 0xFFFF));
    }
    
    public IBlockState getHeldBlockState() {
        return Block.getStateById(this.dataWatcher.getWatchableObjectShort(16) & 0xFFFF);
    }
    
    static {
        attackingSpeedBoostModifierUUID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
        attackingSpeedBoostModifier = new AttributeModifier(EntityEnderman.attackingSpeedBoostModifierUUID, "Attacking speed boost", 0.15000000596046448, 0).setSaved(false);
        (carriableBlocks = Sets.newIdentityHashSet()).add(Blocks.grass);
        EntityEnderman.carriableBlocks.add(Blocks.dirt);
        EntityEnderman.carriableBlocks.add(Blocks.sand);
        EntityEnderman.carriableBlocks.add(Blocks.gravel);
        EntityEnderman.carriableBlocks.add(Blocks.yellow_flower);
        EntityEnderman.carriableBlocks.add(Blocks.red_flower);
        EntityEnderman.carriableBlocks.add(Blocks.brown_mushroom);
        EntityEnderman.carriableBlocks.add(Blocks.red_mushroom);
        EntityEnderman.carriableBlocks.add(Blocks.tnt);
        EntityEnderman.carriableBlocks.add(Blocks.cactus);
        EntityEnderman.carriableBlocks.add(Blocks.clay);
        EntityEnderman.carriableBlocks.add(Blocks.pumpkin);
        EntityEnderman.carriableBlocks.add(Blocks.melon_block);
        EntityEnderman.carriableBlocks.add(Blocks.mycelium);
    }
    
    public EntityEnderman(final World world) {
        super(world);
        this.setSize(0.6f, 2.9f);
        this.stepHeight = 1.0f;
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, 1.0, false));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(10, new AIPlaceBlock(this));
        this.tasks.addTask(11, new AITakeBlock(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new AIFindPlayer(this));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityEndermite.class, 10, true, false, (Predicate)new Predicate(this) {
            final EntityEnderman this$0;
            
            public boolean apply(final EntityEndermite entityEndermite) {
                return entityEndermite.isSpawnedByPlayer();
            }
            
            public boolean apply(final Object o) {
                return this.apply((EntityEndermite)o);
            }
        }));
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.endermen.hit";
    }
    
    public void setScreaming(final boolean b) {
        this.dataWatcher.updateObject(18, (byte)(byte)(b ? 1 : 0));
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        final IBlockState heldBlockState = this.getHeldBlockState();
        nbtTagCompound.setShort("carried", (short)Block.getIdFromBlock(heldBlockState.getBlock()));
        nbtTagCompound.setShort("carriedData", (short)heldBlockState.getBlock().getMetaFromState(heldBlockState));
    }
    
    protected boolean teleportTo(final double posX, final double posY, final double posZ) {
        final double posX2 = this.posX;
        final double posY2 = this.posY;
        final double posZ2 = this.posZ;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        if (this.worldObj.isBlockLoaded(new BlockPos(this.posX, this.posY, this.posZ))) {}
        while (true) {
            final double n = 0 / (128 - 1.0);
            this.worldObj.spawnParticle(EnumParticleTypes.PORTAL, posX2 + (this.posX - posX2) * n + (this.rand.nextDouble() - 0.5) * this.width * 2.0, posY2 + (this.posY - posY2) * n + this.rand.nextDouble() * this.height, posZ2 + (this.posZ - posZ2) * n + (this.rand.nextDouble() - 0.5) * this.width * 2.0, (this.rand.nextFloat() - 0.5f) * 0.2f, (this.rand.nextFloat() - 0.5f) * 0.2f, (this.rand.nextFloat() - 0.5f) * 0.2f, new int[0]);
            int n2 = 0;
            ++n2;
        }
    }
    
    @Override
    public void onLivingUpdate() {
        if (!this.worldObj.isRemote) {
            this.isJumping = false;
            super.onLivingUpdate();
            return;
        }
        while (true) {
            this.worldObj.spawnParticle(EnumParticleTypes.PORTAL, this.posX + (this.rand.nextDouble() - 0.5) * this.width, this.posY + this.rand.nextDouble() * this.height - 0.25, this.posZ + (this.rand.nextDouble() - 0.5) * this.width, (this.rand.nextDouble() - 0.5) * 2.0, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5) * 2.0, new int[0]);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public float getEyeHeight() {
        return 2.55f;
    }
    
    private boolean shouldAttackPlayer(final EntityPlayer entityPlayer) {
        final ItemStack itemStack = entityPlayer.inventory.armorInventory[3];
        if (itemStack != null && itemStack.getItem() == Item.getItemFromBlock(Blocks.pumpkin)) {
            return false;
        }
        final Vec3 normalize = entityPlayer.getLook(1.0f).normalize();
        final Vec3 vec3 = new Vec3(this.posX - entityPlayer.posX, this.getEntityBoundingBox().minY + this.height / 2.0f - (entityPlayer.posY + entityPlayer.getEyeHeight()), this.posZ - entityPlayer.posZ);
        return normalize.dotProduct(vec3.normalize()) > 1.0 - 0.025 / vec3.lengthVector() && entityPlayer.canEntityBeSeen(this);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Short((short)0));
        this.dataWatcher.addObject(17, new Byte((byte)0));
        this.dataWatcher.addObject(18, new Byte((byte)0));
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        }
        if (damageSource.getEntity() == null || !(damageSource.getEntity() instanceof EntityEndermite)) {
            if (!this.worldObj.isRemote) {
                this.setScreaming(true);
            }
            if (damageSource instanceof EntityDamageSource && damageSource.getEntity() instanceof EntityPlayer) {
                if (damageSource.getEntity() instanceof EntityPlayerMP && ((EntityPlayerMP)damageSource.getEntity()).theItemInWorldManager.isCreative()) {
                    this.setScreaming(false);
                }
                else {
                    this.isAggressive = true;
                }
            }
            if (damageSource instanceof EntityDamageSourceIndirect) {
                this.isAggressive = false;
                while (!this.teleportRandomly()) {
                    int attackEntity = 0;
                    ++attackEntity;
                }
                return true;
            }
        }
        int attackEntity = super.attackEntityFrom(damageSource, n) ? 1 : 0;
        if (damageSource.isUnblockable() && this.rand.nextInt(10) != 0) {
            this.teleportRandomly();
        }
        return false;
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        final Item dropItem = this.getDropItem();
        if (dropItem != null) {
            while (0 < this.rand.nextInt(2 + n)) {
                this.dropItem(dropItem, 1);
                int n2 = 0;
                ++n2;
            }
        }
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.endermen.death";
    }
    
    static AttributeModifier access$000() {
        return EntityEnderman.attackingSpeedBoostModifier;
    }
    
    static boolean access$202(final EntityEnderman entityEnderman, final boolean isAggressive) {
        return entityEnderman.isAggressive = isAggressive;
    }
    
    protected boolean teleportToEntity(final Entity entity) {
        final Vec3 normalize = new Vec3(this.posX - entity.posX, this.getEntityBoundingBox().minY + this.height / 2.0f - entity.posY + entity.getEyeHeight(), this.posZ - entity.posZ).normalize();
        final double n = 16.0;
        return this.teleportTo(this.posX + (this.rand.nextDouble() - 0.5) * 8.0 - normalize.xCoord * n, this.posY + (this.rand.nextInt(16) - 8) - normalize.yCoord * n, this.posZ + (this.rand.nextDouble() - 0.5) * 8.0 - normalize.zCoord * n);
    }
    
    protected boolean teleportRandomly() {
        return this.teleportTo(this.posX + (this.rand.nextDouble() - 0.5) * 64.0, this.posY + (this.rand.nextInt(64) - 32), this.posZ + (this.rand.nextDouble() - 0.5) * 64.0);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(64.0);
    }
    
    static boolean access$100(final EntityEnderman entityEnderman, final EntityPlayer entityPlayer) {
        return entityEnderman.shouldAttackPlayer(entityPlayer);
    }
    
    static class AITakeBlock extends EntityAIBase
    {
        private EntityEnderman enderman;
        
        @Override
        public boolean shouldExecute() {
            return this.enderman.worldObj.getGameRules().getBoolean("mobGriefing") && this.enderman.getHeldBlockState().getBlock().getMaterial() == Material.air && this.enderman.getRNG().nextInt(20) == 0;
        }
        
        @Override
        public void updateTask() {
            final Random rng = this.enderman.getRNG();
            final World worldObj = this.enderman.worldObj;
            final BlockPos blockPos = new BlockPos(MathHelper.floor_double(this.enderman.posX - 2.0 + rng.nextDouble() * 4.0), MathHelper.floor_double(this.enderman.posY + rng.nextDouble() * 3.0), MathHelper.floor_double(this.enderman.posZ - 2.0 + rng.nextDouble() * 4.0));
            final IBlockState blockState = worldObj.getBlockState(blockPos);
            if (EntityEnderman.access$300().contains(blockState.getBlock())) {
                this.enderman.setHeldBlockState(blockState);
                worldObj.setBlockState(blockPos, Blocks.air.getDefaultState());
            }
        }
        
        public AITakeBlock(final EntityEnderman enderman) {
            this.enderman = enderman;
        }
    }
    
    static class AIPlaceBlock extends EntityAIBase
    {
        private EntityEnderman enderman;
        
        public AIPlaceBlock(final EntityEnderman enderman) {
            this.enderman = enderman;
        }
        
        @Override
        public boolean shouldExecute() {
            return this.enderman.worldObj.getGameRules().getBoolean("mobGriefing") && this.enderman.getHeldBlockState().getBlock().getMaterial() != Material.air && this.enderman.getRNG().nextInt(2000) == 0;
        }
        
        @Override
        public void updateTask() {
            final Random rng = this.enderman.getRNG();
            final World worldObj = this.enderman.worldObj;
            final BlockPos blockPos = new BlockPos(MathHelper.floor_double(this.enderman.posX - 1.0 + rng.nextDouble() * 2.0), MathHelper.floor_double(this.enderman.posY + rng.nextDouble() * 2.0), MathHelper.floor_double(this.enderman.posZ - 1.0 + rng.nextDouble() * 2.0));
            worldObj.getBlockState(blockPos).getBlock();
            final Block block = worldObj.getBlockState(blockPos.down()).getBlock();
            this.enderman.getHeldBlockState().getBlock();
            if (block == 0) {
                worldObj.setBlockState(blockPos, this.enderman.getHeldBlockState(), 3);
                this.enderman.setHeldBlockState(Blocks.air.getDefaultState());
            }
        }
    }
    
    static class AIFindPlayer extends EntityAINearestAttackableTarget
    {
        private int field_179450_h;
        private EntityEnderman enderman;
        private int field_179451_i;
        private EntityPlayer player;
        
        @Override
        public void resetTask() {
            this.player = null;
            this.enderman.setScreaming(false);
            this.enderman.getEntityAttribute(SharedMonsterAttributes.movementSpeed).removeModifier(EntityEnderman.access$000());
            super.resetTask();
        }
        
        @Override
        public void updateTask() {
            if (this.player != null) {
                if (--this.field_179450_h <= 0) {
                    this.targetEntity = this.player;
                    this.player = null;
                    super.startExecuting();
                    this.enderman.playSound("mob.endermen.stare", 1.0f, 1.0f);
                    this.enderman.setScreaming(true);
                    this.enderman.getEntityAttribute(SharedMonsterAttributes.movementSpeed).applyModifier(EntityEnderman.access$000());
                }
            }
            else {
                if (this.targetEntity != null) {
                    if (this.targetEntity instanceof EntityPlayer && EntityEnderman.access$100(this.enderman, (EntityPlayer)this.targetEntity)) {
                        if (this.targetEntity.getDistanceSqToEntity(this.enderman) < 16.0) {
                            this.enderman.teleportRandomly();
                        }
                        this.field_179451_i = 0;
                    }
                    else if (this.targetEntity.getDistanceSqToEntity(this.enderman) > 256.0 && this.field_179451_i++ >= 30 && this.enderman.teleportToEntity(this.targetEntity)) {
                        this.field_179451_i = 0;
                    }
                }
                super.updateTask();
            }
        }
        
        @Override
        public boolean continueExecuting() {
            if (this.player == null) {
                return super.continueExecuting();
            }
            if (!EntityEnderman.access$100(this.enderman, this.player)) {
                return false;
            }
            EntityEnderman.access$202(this.enderman, true);
            this.enderman.faceEntity(this.player, 10.0f, 10.0f);
            return true;
        }
        
        public AIFindPlayer(final EntityEnderman enderman) {
            super(enderman, EntityPlayer.class, true);
            this.enderman = enderman;
        }
        
        @Override
        public boolean shouldExecute() {
            final double targetDistance = this.getTargetDistance();
            final List entitiesWithinAABB = this.taskOwner.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.taskOwner.getEntityBoundingBox().expand(targetDistance, 4.0, targetDistance), this.targetEntitySelector);
            Collections.sort((List<Object>)entitiesWithinAABB, this.theNearestAttackableTargetSorter);
            if (entitiesWithinAABB.isEmpty()) {
                return false;
            }
            this.player = entitiesWithinAABB.get(0);
            return true;
        }
        
        @Override
        public void startExecuting() {
            this.field_179450_h = 5;
            this.field_179451_i = 0;
        }
    }
}
