package net.minecraft.entity.ai;

import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.scoreboard.*;

public abstract class EntityAITarget extends EntityAIBase
{
    protected final EntityCreature taskOwner;
    private int targetUnseenTicks;
    private int targetSearchDelay;
    protected boolean shouldCheckSight;
    private boolean nearbyOnly;
    private int targetSearchStatus;
    
    @Override
    public void resetTask() {
        this.taskOwner.setAttackTarget(null);
    }
    
    protected double getTargetDistance() {
        final IAttributeInstance entityAttribute = this.taskOwner.getEntityAttribute(SharedMonsterAttributes.followRange);
        return (entityAttribute == null) ? 16.0 : entityAttribute.getAttributeValue();
    }
    
    public EntityAITarget(final EntityCreature taskOwner, final boolean shouldCheckSight, final boolean nearbyOnly) {
        this.taskOwner = taskOwner;
        this.shouldCheckSight = shouldCheckSight;
        this.nearbyOnly = nearbyOnly;
    }
    
    @Override
    public boolean continueExecuting() {
        final EntityLivingBase attackTarget = this.taskOwner.getAttackTarget();
        if (attackTarget == null) {
            return false;
        }
        if (!attackTarget.isEntityAlive()) {
            return false;
        }
        final Team team = this.taskOwner.getTeam();
        final Team team2 = attackTarget.getTeam();
        if (team != null && team2 == team) {
            return false;
        }
        final double targetDistance = this.getTargetDistance();
        if (this.taskOwner.getDistanceSqToEntity(attackTarget) > targetDistance * targetDistance) {
            return false;
        }
        if (this.shouldCheckSight) {
            if (this.taskOwner.getEntitySenses().canSee(attackTarget)) {
                this.targetUnseenTicks = 0;
            }
            else if (++this.targetUnseenTicks > 60) {
                return false;
            }
        }
        return !(attackTarget instanceof EntityPlayer) || !((EntityPlayer)attackTarget).capabilities.disableDamage;
    }
    
    protected boolean isSuitableTarget(final EntityLivingBase p0, final boolean p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/entity/ai/EntityAITarget.taskOwner:Lnet/minecraft/entity/EntityCreature;
        //     4: aload_1        
        //     5: iload_2        
        //     6: aload_0        
        //     7: getfield        net/minecraft/entity/ai/EntityAITarget.shouldCheckSight:Z
        //    10: ifnonnull       15
        //    13: iconst_0       
        //    14: ireturn        
        //    15: aload_0        
        //    16: getfield        net/minecraft/entity/ai/EntityAITarget.taskOwner:Lnet/minecraft/entity/EntityCreature;
        //    19: new             Lnet/minecraft/util/BlockPos;
        //    22: dup            
        //    23: aload_1        
        //    24: invokespecial   net/minecraft/util/BlockPos.<init>:(Lnet/minecraft/entity/Entity;)V
        //    27: invokevirtual   net/minecraft/entity/EntityCreature.isWithinHomeDistanceFromPosition:(Lnet/minecraft/util/BlockPos;)Z
        //    30: ifne            35
        //    33: iconst_0       
        //    34: ireturn        
        //    35: aload_0        
        //    36: getfield        net/minecraft/entity/ai/EntityAITarget.nearbyOnly:Z
        //    39: ifeq            92
        //    42: aload_0        
        //    43: dup            
        //    44: getfield        net/minecraft/entity/ai/EntityAITarget.targetSearchDelay:I
        //    47: iconst_1       
        //    48: isub           
        //    49: dup_x1         
        //    50: putfield        net/minecraft/entity/ai/EntityAITarget.targetSearchDelay:I
        //    53: ifgt            61
        //    56: aload_0        
        //    57: iconst_0       
        //    58: putfield        net/minecraft/entity/ai/EntityAITarget.targetSearchStatus:I
        //    61: aload_0        
        //    62: getfield        net/minecraft/entity/ai/EntityAITarget.targetSearchStatus:I
        //    65: ifne            82
        //    68: aload_0        
        //    69: aload_0        
        //    70: aload_1        
        //    71: ifnonnull       78
        //    74: iconst_1       
        //    75: goto            79
        //    78: iconst_2       
        //    79: putfield        net/minecraft/entity/ai/EntityAITarget.targetSearchStatus:I
        //    82: aload_0        
        //    83: getfield        net/minecraft/entity/ai/EntityAITarget.targetSearchStatus:I
        //    86: iconst_2       
        //    87: if_icmpne       92
        //    90: iconst_0       
        //    91: ireturn        
        //    92: iconst_1       
        //    93: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0082 (coming from #0079).
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
    
    @Override
    public void startExecuting() {
        this.targetSearchStatus = 0;
        this.targetSearchDelay = 0;
        this.targetUnseenTicks = 0;
    }
    
    public EntityAITarget(final EntityCreature entityCreature, final boolean b) {
        this(entityCreature, b, false);
    }
}
