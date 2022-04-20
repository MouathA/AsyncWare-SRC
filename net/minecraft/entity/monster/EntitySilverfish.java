package net.minecraft.entity.monster;

import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.ai.*;
import net.minecraft.util.*;

public class EntitySilverfish extends EntityMob
{
    private AISummonSilverfish summonSilverfish;
    
    @Override
    public void onUpdate() {
        this.renderYawOffset = this.rotationYaw;
        super.onUpdate();
    }
    
    @Override
    protected Item getDropItem() {
        return null;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        }
        if (damageSource instanceof EntityDamageSource || damageSource == DamageSource.magic) {
            this.summonSilverfish.func_179462_f();
        }
        return super.attackEntityFrom(damageSource, n);
    }
    
    @Override
    protected void playStepSound(final BlockPos blockPos, final Block block) {
        this.playSound("mob.silverfish.step", 0.15f, 1.0f);
    }
    
    @Override
    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere() && this.worldObj.getClosestPlayerToEntity(this, 5.0) == null;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0);
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.silverfish.say";
    }
    
    @Override
    public double getYOffset() {
        return 0.2;
    }
    
    @Override
    public float getEyeHeight() {
        return 0.1f;
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.silverfish.kill";
    }
    
    @Override
    public float getBlockPathWeight(final BlockPos blockPos) {
        return (this.worldObj.getBlockState(blockPos.down()).getBlock() == Blocks.stone) ? 10.0f : super.getBlockPathWeight(blockPos);
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.silverfish.hit";
    }
    
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }
    
    public EntitySilverfish(final World world) {
        super(world);
        this.setSize(0.4f, 0.3f);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(3, this.summonSilverfish = new AISummonSilverfish(this));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0, false));
        this.tasks.addTask(5, new AIHideInStone(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }
    
    protected boolean isValidLightLevel() {
        return true;
    }
    
    static class AISummonSilverfish extends EntityAIBase
    {
        private int field_179463_b;
        private EntitySilverfish silverfish;
        
        @Override
        public boolean shouldExecute() {
            return this.field_179463_b > 0;
        }
        
        public void func_179462_f() {
            if (this.field_179463_b == 0) {
                this.field_179463_b = 20;
            }
        }
        
        public AISummonSilverfish(final EntitySilverfish silverfish) {
            this.silverfish = silverfish;
        }
        
        @Override
        public void updateTask() {
            --this.field_179463_b;
            if (this.field_179463_b <= 0) {
                final World worldObj = this.silverfish.worldObj;
                final Random rng = this.silverfish.getRNG();
                final BlockPos blockPos = new BlockPos(this.silverfish);
                while (true) {
                    final BlockPos add = blockPos.add(0, 0, 0);
                    final IBlockState blockState = worldObj.getBlockState(add);
                    if (blockState.getBlock() == Blocks.monster_egg) {
                        if (worldObj.getGameRules().getBoolean("mobGriefing")) {
                            worldObj.destroyBlock(add, true);
                        }
                        else {
                            worldObj.setBlockState(add, ((BlockSilverfish.EnumType)blockState.getValue(BlockSilverfish.VARIANT)).getModelBlock(), 3);
                        }
                        if (rng.nextBoolean()) {
                            break;
                        }
                        continue;
                    }
                }
            }
        }
    }
    
    static class AIHideInStone extends EntityAIWander
    {
        private EnumFacing facing;
        private final EntitySilverfish field_179485_a;
        private boolean field_179484_c;
        
        @Override
        public void startExecuting() {
            if (!this.field_179484_c) {
                super.startExecuting();
            }
            else {
                final World worldObj = this.field_179485_a.worldObj;
                final BlockPos offset = new BlockPos(this.field_179485_a.posX, this.field_179485_a.posY + 0.5, this.field_179485_a.posZ).offset(this.facing);
                final IBlockState blockState = worldObj.getBlockState(offset);
                if (BlockSilverfish.canContainSilverfish(blockState)) {
                    worldObj.setBlockState(offset, Blocks.monster_egg.getDefaultState().withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.forModelBlock(blockState)), 3);
                    this.field_179485_a.spawnExplosionParticle();
                    this.field_179485_a.setDead();
                }
            }
        }
        
        @Override
        public boolean shouldExecute() {
            if (this.field_179485_a.getAttackTarget() != null) {
                return false;
            }
            if (!this.field_179485_a.getNavigator().noPath()) {
                return false;
            }
            final Random rng = this.field_179485_a.getRNG();
            if (rng.nextInt(10) == 0) {
                this.facing = EnumFacing.random(rng);
                if (BlockSilverfish.canContainSilverfish(this.field_179485_a.worldObj.getBlockState(new BlockPos(this.field_179485_a.posX, this.field_179485_a.posY + 0.5, this.field_179485_a.posZ).offset(this.facing)))) {
                    return this.field_179484_c = true;
                }
            }
            this.field_179484_c = false;
            return super.shouldExecute();
        }
        
        public AIHideInStone(final EntitySilverfish field_179485_a) {
            super(field_179485_a, 1.0, 10);
            this.field_179485_a = field_179485_a;
            this.setMutexBits(1);
        }
        
        @Override
        public boolean continueExecuting() {
            return !this.field_179484_c && super.continueExecuting();
        }
    }
}
