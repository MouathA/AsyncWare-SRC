package net.minecraft.entity.ai;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.state.pattern.*;
import net.minecraft.block.*;
import com.google.common.base.*;
import net.minecraft.block.properties.*;

public class EntityAIEatGrass extends EntityAIBase
{
    private World entityWorld;
    int eatingGrassTimer;
    private static final Predicate field_179505_b;
    private EntityLiving grassEaterEntity;
    
    public int getEatingGrassTimer() {
        return this.eatingGrassTimer;
    }
    
    public EntityAIEatGrass(final EntityLiving grassEaterEntity) {
        this.grassEaterEntity = grassEaterEntity;
        this.entityWorld = grassEaterEntity.worldObj;
        this.setMutexBits(7);
    }
    
    @Override
    public void startExecuting() {
        this.eatingGrassTimer = 40;
        this.entityWorld.setEntityState(this.grassEaterEntity, (byte)10);
        this.grassEaterEntity.getNavigator().clearPathEntity();
    }
    
    @Override
    public void resetTask() {
        this.eatingGrassTimer = 0;
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.grassEaterEntity.getRNG().nextInt(this.grassEaterEntity.isChild() ? 50 : 1000) != 0) {
            return false;
        }
        final BlockPos blockPos = new BlockPos(this.grassEaterEntity.posX, this.grassEaterEntity.posY, this.grassEaterEntity.posZ);
        return EntityAIEatGrass.field_179505_b.apply((Object)this.entityWorld.getBlockState(blockPos)) || this.entityWorld.getBlockState(blockPos.down()).getBlock() == Blocks.grass;
    }
    
    @Override
    public boolean continueExecuting() {
        return this.eatingGrassTimer > 0;
    }
    
    @Override
    public void updateTask() {
        this.eatingGrassTimer = Math.max(0, this.eatingGrassTimer - 1);
        if (this.eatingGrassTimer == 4) {
            final BlockPos blockPos = new BlockPos(this.grassEaterEntity.posX, this.grassEaterEntity.posY, this.grassEaterEntity.posZ);
            if (EntityAIEatGrass.field_179505_b.apply((Object)this.entityWorld.getBlockState(blockPos))) {
                if (this.entityWorld.getGameRules().getBoolean("mobGriefing")) {
                    this.entityWorld.destroyBlock(blockPos, false);
                }
                this.grassEaterEntity.eatGrassBonus();
            }
            else {
                final BlockPos down = blockPos.down();
                if (this.entityWorld.getBlockState(down).getBlock() == Blocks.grass) {
                    if (this.entityWorld.getGameRules().getBoolean("mobGriefing")) {
                        this.entityWorld.playAuxSFX(2001, down, Block.getIdFromBlock(Blocks.grass));
                        this.entityWorld.setBlockState(down, Blocks.dirt.getDefaultState(), 2);
                    }
                    this.grassEaterEntity.eatGrassBonus();
                }
            }
        }
    }
    
    static {
        field_179505_b = (Predicate)BlockStateHelper.forBlock(Blocks.tallgrass).where(BlockTallGrass.TYPE, Predicates.equalTo((Object)BlockTallGrass.EnumType.GRASS));
    }
}
