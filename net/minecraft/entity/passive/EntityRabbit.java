package net.minecraft.entity.passive;

import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.pathfinding.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.ai.*;

public class EntityRabbit extends EntityAnimal
{
    private EnumMoveType moveType;
    private boolean field_175537_bp;
    private EntityPlayer field_175543_bt;
    private int field_175535_bn;
    private int field_175540_bm;
    private int currentMoveTypeDuration;
    private boolean field_175536_bo;
    private AIAvoidEntity aiAvoidWolves;
    private int carrotTicks;
    
    private void func_175520_cs() {
        ((RabbitJumpHelper)this.jumpHelper).func_180066_a(false);
    }
    
    @Override
    public void spawnRunningParticles() {
    }
    
    public boolean isBreedingItem(final ItemStack itemStack) {
        return itemStack != null && this != itemStack.getItem();
    }
    
    public boolean func_175523_cj() {
        return this.field_175536_bo;
    }
    
    protected int getMoveTypeDuration() {
        return this.moveType.getDuration();
    }
    
    public void setJumping(final boolean b, final EnumMoveType enumMoveType) {
        super.setJumping(b);
        if (!b) {
            if (this.moveType == EnumMoveType.ATTACK) {
                this.moveType = EnumMoveType.HOP;
            }
        }
        else {
            this.setMovementSpeed(1.5 * enumMoveType.getSpeed());
            this.playSound(this.getJumpingSound(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f) * 0.8f);
        }
        this.field_175536_bo = b;
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity entity) {
        if (this.getRabbitType() == 99) {
            this.playSound("mob.attack", 1.0f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            return entity.attackEntityFrom(DamageSource.causeMobDamage(this), 8.0f);
        }
        return entity.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0f);
    }
    
    public int getRabbitType() {
        return this.dataWatcher.getWatchableObjectByte(18);
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.rabbit.idle";
    }
    
    @Override
    public IEntityLivingData onInitialSpawn(final DifficultyInstance difficultyInstance, IEntityLivingData onInitialSpawn) {
        onInitialSpawn = super.onInitialSpawn(difficultyInstance, onInitialSpawn);
        int rabbitType = this.rand.nextInt(6);
        if (onInitialSpawn instanceof RabbitTypeData) {
            rabbitType = ((RabbitTypeData)onInitialSpawn).typeData;
        }
        else {
            onInitialSpawn = new RabbitTypeData(rabbitType);
        }
        this.setRabbitType(rabbitType);
        this.setGrowingAge(-24000);
        return onInitialSpawn;
    }
    
    public void setRabbitType(final int n) {
        if (n == 99) {
            this.tasks.removeTask(this.aiAvoidWolves);
            this.tasks.addTask(4, new AIEvilAttack(this));
            this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
            this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
            this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityWolf.class, true));
            if (!this.hasCustomName()) {
                this.setCustomNameTag("\uc350\uc35b\uc341\uc35c\uc341\uc34c\uc31b\uc37e\uc35c\uc359\uc359\uc350\uc347\uc377\uc340\uc35b\uc35b\uc34c\uc31b\uc35b\uc354\uc358\uc350");
            }
        }
        this.dataWatcher.updateObject(18, (byte)n);
    }
    
    private void calculateRotationYaw(final double n, final double n2) {
        this.rotationYaw = (float)(MathHelper.func_181159_b(n2 - this.posZ, n - this.posX) * 180.0 / 3.141592653589793) - 90.0f;
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("RabbitType", this.getRabbitType());
        nbtTagCompound.setInteger("MoreCarrotTicks", this.carrotTicks);
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        return !this.isEntityInvulnerable(damageSource) && super.attackEntityFrom(damageSource, n);
    }
    
    protected void createEatingParticles() {
        this.worldObj.spawnParticle(EnumParticleTypes.BLOCK_DUST, this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width, this.posY + 0.5 + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width, 0.0, 0.0, 0.0, Block.getStateId(Blocks.carrots.getStateFromMeta(7)));
        this.carrotTicks = 100;
    }
    
    private void func_175518_cr() {
        ((RabbitJumpHelper)this.jumpHelper).func_180066_a(true);
    }
    
    public void doMovementAction(final EnumMoveType enumMoveType) {
        this.setJumping(true, enumMoveType);
        this.field_175535_bn = enumMoveType.func_180073_d();
        this.field_175540_bm = 0;
    }
    
    public EntityRabbit(final World world) {
        super(world);
        this.field_175540_bm = 0;
        this.field_175535_bn = 0;
        this.field_175536_bo = false;
        this.field_175537_bp = false;
        this.currentMoveTypeDuration = 0;
        this.moveType = EnumMoveType.HOP;
        this.carrotTicks = 0;
        this.field_175543_bt = null;
        this.setSize(0.6f, 0.7f);
        this.jumpHelper = new RabbitJumpHelper(this);
        this.moveHelper = new RabbitMoveHelper(this);
        ((PathNavigateGround)this.getNavigator()).setAvoidsWater(true);
        this.navigator.setHeightRequirement(2.5f);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(1, new AIPanic(this, 1.33));
        this.tasks.addTask(2, new EntityAITempt(this, 1.0, Items.carrot, false));
        this.tasks.addTask(2, new EntityAITempt(this, 1.0, Items.golden_carrot, false));
        this.tasks.addTask(2, new EntityAITempt(this, 1.0, Item.getItemFromBlock(Blocks.yellow_flower), false));
        this.tasks.addTask(3, new EntityAIMate(this, 0.8));
        this.tasks.addTask(5, new AIRaidFarm(this));
        this.tasks.addTask(5, new EntityAIWander(this, 0.6));
        this.tasks.addTask(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0f));
        this.aiAvoidWolves = new AIAvoidEntity(this, EntityWolf.class, 16.0f, 1.33, 1.33);
        this.tasks.addTask(4, this.aiAvoidWolves);
        this.setMovementSpeed(0.0);
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable entityAgeable) {
        return this.createChild(entityAgeable);
    }
    
    private boolean isCarrotEaten() {
        return this.carrotTicks == 0;
    }
    
    public void setMovementSpeed(final double speed) {
        this.getNavigator().setSpeed(speed);
        this.moveHelper.setMoveTo(this.moveHelper.getX(), this.moveHelper.getY(), this.moveHelper.getZ(), speed);
    }
    
    private void func_175517_cu() {
        this.updateMoveTypeDuration();
        this.func_175520_cs();
    }
    
    private void updateMoveTypeDuration() {
        this.currentMoveTypeDuration = this.getMoveTypeDuration();
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.setRabbitType(nbtTagCompound.getInteger("RabbitType"));
        this.carrotTicks = nbtTagCompound.getInteger("MoreCarrotTicks");
    }
    
    public float func_175521_o(final float n) {
        return (this.field_175535_bn == 0) ? 0.0f : ((this.field_175540_bm + n) / this.field_175535_bn);
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        int n2 = 0;
        while (0 < this.rand.nextInt(2) + this.rand.nextInt(1 + n)) {
            this.dropItem(Items.rabbit_hide, 1);
            ++n2;
        }
        while (0 < this.rand.nextInt(2)) {
            if (this.isBurning()) {
                this.dropItem(Items.cooked_rabbit, 1);
            }
            else {
                this.dropItem(Items.rabbit, 1);
            }
            ++n2;
        }
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.field_175540_bm != this.field_175535_bn) {
            if (this.field_175540_bm == 0 && !this.worldObj.isRemote) {
                this.worldObj.setEntityState(this, (byte)1);
            }
            ++this.field_175540_bm;
        }
        else if (this.field_175535_bn != 0) {
            this.field_175540_bm = 0;
            this.field_175535_bn = 0;
        }
    }
    
    @Override
    public void handleStatusUpdate(final byte b) {
        if (b == 1) {
            this.createRunningParticles();
            this.field_175535_bn = 10;
            this.field_175540_bm = 0;
        }
        else {
            super.handleStatusUpdate(b);
        }
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.rabbit.death";
    }
    
    protected String getJumpingSound() {
        return "mob.rabbit.hop";
    }
    
    @Override
    public EntityRabbit createChild(final EntityAgeable entityAgeable) {
        final EntityRabbit entityRabbit = new EntityRabbit(this.worldObj);
        if (entityAgeable instanceof EntityRabbit) {
            entityRabbit.setRabbitType(this.rand.nextBoolean() ? this.getRabbitType() : ((EntityRabbit)entityAgeable).getRabbitType());
        }
        return entityRabbit;
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.rabbit.hurt";
    }
    
    @Override
    protected void addRandomDrop() {
        this.entityDropItem(new ItemStack(Items.rabbit_foot, 1), 0.0f);
    }
    
    static boolean access$000(final EntityRabbit entityRabbit) {
        return entityRabbit.isCarrotEaten();
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(18, 0);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896);
    }
    
    public void setMoveType(final EnumMoveType moveType) {
        this.moveType = moveType;
    }
    
    @Override
    public int getTotalArmorValue() {
        return (this.getRabbitType() == 99) ? 8 : super.getTotalArmorValue();
    }
    
    @Override
    protected float getJumpUpwardsMotion() {
        return (this.moveHelper.isUpdating() && this.moveHelper.getY() > this.posY + 0.5) ? 0.5f : this.moveType.func_180074_b();
    }
    
    public void updateAITasks() {
        if (this.moveHelper.getSpeed() > 0.8) {
            this.setMoveType(EnumMoveType.SPRINT);
        }
        else if (this.moveType != EnumMoveType.ATTACK) {
            this.setMoveType(EnumMoveType.HOP);
        }
        if (this.currentMoveTypeDuration > 0) {
            --this.currentMoveTypeDuration;
        }
        if (this.carrotTicks > 0) {
            this.carrotTicks -= this.rand.nextInt(3);
            if (this.carrotTicks < 0) {
                this.carrotTicks = 0;
            }
        }
        if (this.onGround) {
            if (!this.field_175537_bp) {
                this.setJumping(false, EnumMoveType.NONE);
                this.func_175517_cu();
            }
            if (this.getRabbitType() == 99 && this.currentMoveTypeDuration == 0) {
                final EntityLivingBase attackTarget = this.getAttackTarget();
                if (attackTarget != null && this.getDistanceSqToEntity(attackTarget) < 16.0) {
                    this.calculateRotationYaw(attackTarget.posX, attackTarget.posZ);
                    this.moveHelper.setMoveTo(attackTarget.posX, attackTarget.posY, attackTarget.posZ, this.moveHelper.getSpeed());
                    this.doMovementAction(EnumMoveType.ATTACK);
                    this.field_175537_bp = true;
                }
            }
            final RabbitJumpHelper rabbitJumpHelper = (RabbitJumpHelper)this.jumpHelper;
            if (!rabbitJumpHelper.getIsJumping()) {
                if (this.moveHelper.isUpdating() && this.currentMoveTypeDuration == 0) {
                    final PathEntity path = this.navigator.getPath();
                    Vec3 position = new Vec3(this.moveHelper.getX(), this.moveHelper.getY(), this.moveHelper.getZ());
                    if (path != null && path.getCurrentPathIndex() < path.getCurrentPathLength()) {
                        position = path.getPosition(this);
                    }
                    this.calculateRotationYaw(position.xCoord, position.zCoord);
                    this.doMovementAction(this.moveType);
                }
            }
            else if (!rabbitJumpHelper.func_180065_d()) {
                this.func_175518_cr();
            }
        }
        this.field_175537_bp = this.onGround;
    }
    
    static class AIRaidFarm extends EntityAIMoveToBlock
    {
        private boolean field_179499_e;
        private final EntityRabbit field_179500_c;
        private boolean field_179498_d;
        
        @Override
        public void updateTask() {
            super.updateTask();
            this.field_179500_c.getLookHelper().setLookPosition(this.destinationBlock.getX() + 0.5, this.destinationBlock.getY() + 1, this.destinationBlock.getZ() + 0.5, 10.0f, (float)this.field_179500_c.getVerticalFaceSpeed());
            if (this.getIsAboveDestination()) {
                final World worldObj = this.field_179500_c.worldObj;
                final BlockPos up = this.destinationBlock.up();
                final IBlockState blockState = worldObj.getBlockState(up);
                final Block block = blockState.getBlock();
                if (this.field_179499_e && block instanceof BlockCarrot && (int)blockState.getValue(BlockCarrot.AGE) == 7) {
                    worldObj.setBlockState(up, Blocks.air.getDefaultState(), 2);
                    worldObj.destroyBlock(up, true);
                    this.field_179500_c.createEatingParticles();
                }
                this.field_179499_e = false;
                this.runDelay = 10;
            }
        }
        
        @Override
        public void startExecuting() {
            super.startExecuting();
        }
        
        @Override
        public boolean shouldExecute() {
            if (this.runDelay <= 0) {
                if (!this.field_179500_c.worldObj.getGameRules().getBoolean("mobGriefing")) {
                    return false;
                }
                this.field_179499_e = false;
                this.field_179498_d = EntityRabbit.access$000(this.field_179500_c);
            }
            return super.shouldExecute();
        }
        
        @Override
        protected boolean shouldMoveTo(final World world, BlockPos up) {
            if (world.getBlockState(up).getBlock() == Blocks.farmland) {
                up = up.up();
                final IBlockState blockState = world.getBlockState(up);
                if (blockState.getBlock() instanceof BlockCarrot && (int)blockState.getValue(BlockCarrot.AGE) == 7 && this.field_179498_d && !this.field_179499_e) {
                    return this.field_179499_e = true;
                }
            }
            return false;
        }
        
        @Override
        public void resetTask() {
            super.resetTask();
        }
        
        public AIRaidFarm(final EntityRabbit field_179500_c) {
            super(field_179500_c, 0.699999988079071, 16);
            this.field_179499_e = false;
            this.field_179500_c = field_179500_c;
        }
        
        @Override
        public boolean continueExecuting() {
            return this.field_179499_e && super.continueExecuting();
        }
    }
    
    static class AIPanic extends EntityAIPanic
    {
        private EntityRabbit theEntity;
        
        @Override
        public void updateTask() {
            super.updateTask();
            this.theEntity.setMovementSpeed(this.speed);
        }
        
        public AIPanic(final EntityRabbit theEntity, final double n) {
            super(theEntity, n);
            this.theEntity = theEntity;
        }
    }
    
    enum EnumMoveType
    {
        private static final EnumMoveType[] $VALUES;
        private final float field_180077_g;
        
        STEP("STEP", 2, 1.0f, 0.45f, 14, 14);
        
        private final float speed;
        
        HOP("HOP", 1, 0.8f, 0.2f, 20, 10);
        
        private final int field_180085_i;
        
        ATTACK("ATTACK", 4, 2.0f, 0.7f, 7, 8), 
        NONE("NONE", 0, 0.0f, 0.0f, 30, 1), 
        SPRINT("SPRINT", 3, 1.75f, 0.4f, 1, 8);
        
        private final int duration;
        
        public int getDuration() {
            return this.duration;
        }
        
        private EnumMoveType(final String s, final int n, final float speed, final float field_180077_g, final int duration, final int field_180085_i) {
            this.speed = speed;
            this.field_180077_g = field_180077_g;
            this.duration = duration;
            this.field_180085_i = field_180085_i;
        }
        
        public float func_180074_b() {
            return this.field_180077_g;
        }
        
        static {
            $VALUES = new EnumMoveType[] { EnumMoveType.NONE, EnumMoveType.HOP, EnumMoveType.STEP, EnumMoveType.SPRINT, EnumMoveType.ATTACK };
        }
        
        public int func_180073_d() {
            return this.field_180085_i;
        }
        
        public float getSpeed() {
            return this.speed;
        }
    }
    
    static class AIEvilAttack extends EntityAIAttackOnCollide
    {
        public AIEvilAttack(final EntityRabbit entityRabbit) {
            super(entityRabbit, EntityLivingBase.class, 1.4, true);
        }
        
        @Override
        protected double func_179512_a(final EntityLivingBase entityLivingBase) {
            return 4.0f + entityLivingBase.width;
        }
    }
    
    static class AIAvoidEntity extends EntityAIAvoidEntity
    {
        private EntityRabbit entityInstance;
        
        @Override
        public void updateTask() {
            super.updateTask();
        }
        
        public AIAvoidEntity(final EntityRabbit entityInstance, final Class clazz, final float n, final double n2, final double n3) {
            super(entityInstance, clazz, n, n2, n3);
            this.entityInstance = entityInstance;
        }
    }
    
    static class RabbitMoveHelper extends EntityMoveHelper
    {
        private EntityRabbit theEntity;
        
        @Override
        public void onUpdateMoveHelper() {
            if (this.theEntity.onGround && !this.theEntity.func_175523_cj()) {
                this.theEntity.setMovementSpeed(0.0);
            }
            super.onUpdateMoveHelper();
        }
        
        public RabbitMoveHelper(final EntityRabbit theEntity) {
            super(theEntity);
            this.theEntity = theEntity;
        }
    }
    
    public static class RabbitTypeData implements IEntityLivingData
    {
        public int typeData;
        
        public RabbitTypeData(final int typeData) {
            this.typeData = typeData;
        }
    }
    
    public class RabbitJumpHelper extends EntityJumpHelper
    {
        private EntityRabbit theEntity;
        private boolean field_180068_d;
        final EntityRabbit this$0;
        
        @Override
        public void doJump() {
            if (this.isJumping) {
                this.theEntity.doMovementAction(EnumMoveType.STEP);
                this.isJumping = false;
            }
        }
        
        public boolean func_180065_d() {
            return this.field_180068_d;
        }
        
        public RabbitJumpHelper(final EntityRabbit this$0, final EntityRabbit theEntity) {
            this.this$0 = this$0;
            super(theEntity);
            this.field_180068_d = false;
            this.theEntity = theEntity;
        }
        
        public void func_180066_a(final boolean field_180068_d) {
            this.field_180068_d = field_180068_d;
        }
        
        public boolean getIsJumping() {
            return this.isJumping;
        }
    }
}
