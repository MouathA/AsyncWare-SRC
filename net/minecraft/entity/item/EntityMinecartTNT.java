package net.minecraft.entity.item;

import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;

public class EntityMinecartTNT extends EntityMinecart
{
    private int minecartTNTFuse;
    
    @Override
    public boolean verifyExplosion(final Explosion p0, final World p1, final BlockPos p2, final IBlockState p3, final float p4) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_icmple       23
        //     4: aload           4
        //     6: invokestatic    net/minecraft/block/BlockRailBase.isRailBlock:(Lnet/minecraft/block/state/IBlockState;)Z
        //     9: ifne            37
        //    12: aload_2        
        //    13: aload_3        
        //    14: invokevirtual   net/minecraft/util/BlockPos.up:()Lnet/minecraft/util/BlockPos;
        //    17: invokestatic    net/minecraft/block/BlockRailBase.isRailBlock:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;)Z
        //    20: ifne            37
        //    23: aload_0        
        //    24: aload_1        
        //    25: aload_2        
        //    26: aload_3        
        //    27: aload           4
        //    29: fload           5
        //    31: invokespecial   net/minecraft/entity/item/EntityMinecart.verifyExplosion:(Lnet/minecraft/world/Explosion;Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;F)Z
        //    34: goto            38
        //    37: iconst_0       
        //    38: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException: -1
        //     at java.util.ArrayList.elementData(Unknown Source)
        //     at java.util.ArrayList.remove(Unknown Source)
        //     at com.strobel.assembler.ir.StackMappingVisitor.pop(StackMappingVisitor.java:267)
        //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.execute(StackMappingVisitor.java:600)
        //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.visit(StackMappingVisitor.java:398)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2030)
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
    public void fall(final float n, final float n2) {
        if (n >= 3.0f) {
            final float n3 = n / 10.0f;
            this.explodeCart(n3 * n3);
        }
        super.fall(n, n2);
    }
    
    @Override
    public EnumMinecartType getMinecartType() {
        return EnumMinecartType.TNT;
    }
    
    public EntityMinecartTNT(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3);
        this.minecartTNTFuse = -1;
    }
    
    public EntityMinecartTNT(final World world) {
        super(world);
        this.minecartTNTFuse = -1;
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.minecartTNTFuse > 0) {
            --this.minecartTNTFuse;
            this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.5, this.posZ, 0.0, 0.0, 0.0, new int[0]);
        }
        else if (this.minecartTNTFuse == 0) {
            this.explodeCart(this.motionX * this.motionX + this.motionZ * this.motionZ);
        }
        if (this.isCollidedHorizontally) {
            final double n = this.motionX * this.motionX + this.motionZ * this.motionZ;
            if (n >= 0.009999999776482582) {
                this.explodeCart(n);
            }
        }
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        if (nbtTagCompound.hasKey("TNTFuse", 99)) {
            this.minecartTNTFuse = nbtTagCompound.getInteger("TNTFuse");
        }
    }
    
    @Override
    public void killMinecart(final DamageSource damageSource) {
        super.killMinecart(damageSource);
        final double n = this.motionX * this.motionX + this.motionZ * this.motionZ;
        if (!damageSource.isExplosion() && this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
            this.entityDropItem(new ItemStack(Blocks.tnt, 1), 0.0f);
        }
        if (damageSource.isFireDamage() || damageSource.isExplosion() || n >= 0.009999999776482582) {
            this.explodeCart(n);
        }
    }
    
    @Override
    public IBlockState getDefaultDisplayTile() {
        return Blocks.tnt.getDefaultState();
    }
    
    @Override
    public void onActivatorRailPass(final int n, final int n2, final int n3, final boolean b) {
        if (b && this.minecartTNTFuse < 0) {
            this.ignite();
        }
    }
    
    protected void explodeCart(final double n) {
        if (!this.worldObj.isRemote) {
            double sqrt = Math.sqrt(n);
            if (sqrt > 5.0) {
                sqrt = 5.0;
            }
            this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (float)(4.0 + this.rand.nextDouble() * 1.5 * sqrt), true);
            this.setDead();
        }
    }
    
    public void ignite() {
        this.minecartTNTFuse = 80;
        if (!this.worldObj.isRemote) {
            this.worldObj.setEntityState(this, (byte)10);
            if (!this.isSilent()) {
                this.worldObj.playSoundAtEntity(this, "game.tnt.primed", 1.0f, 1.0f);
            }
        }
    }
    
    @Override
    public void handleStatusUpdate(final byte b) {
        if (b == 10) {
            this.ignite();
        }
        else {
            super.handleStatusUpdate(b);
        }
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("TNTFuse", this.minecartTNTFuse);
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        final Entity sourceOfDamage = damageSource.getSourceOfDamage();
        if (sourceOfDamage instanceof EntityArrow) {
            final EntityArrow entityArrow = (EntityArrow)sourceOfDamage;
            if (entityArrow.isBurning()) {
                this.explodeCart(entityArrow.motionX * entityArrow.motionX + entityArrow.motionY * entityArrow.motionY + entityArrow.motionZ * entityArrow.motionZ);
            }
        }
        return super.attackEntityFrom(damageSource, n);
    }
    
    public int getFuseTicks() {
        return this.minecartTNTFuse;
    }
    
    @Override
    public float getExplosionResistance(final Explosion p0, final World p1, final BlockPos p2, final IBlockState p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_icmple       23
        //     4: aload           4
        //     6: invokestatic    net/minecraft/block/BlockRailBase.isRailBlock:(Lnet/minecraft/block/state/IBlockState;)Z
        //     9: ifne            35
        //    12: aload_2        
        //    13: aload_3        
        //    14: invokevirtual   net/minecraft/util/BlockPos.up:()Lnet/minecraft/util/BlockPos;
        //    17: invokestatic    net/minecraft/block/BlockRailBase.isRailBlock:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;)Z
        //    20: ifne            35
        //    23: aload_0        
        //    24: aload_1        
        //    25: aload_2        
        //    26: aload_3        
        //    27: aload           4
        //    29: invokespecial   net/minecraft/entity/item/EntityMinecart.getExplosionResistance:(Lnet/minecraft/world/Explosion;Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;)F
        //    32: goto            36
        //    35: fconst_0       
        //    36: freturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException: -1
        //     at java.util.ArrayList.elementData(Unknown Source)
        //     at java.util.ArrayList.remove(Unknown Source)
        //     at com.strobel.assembler.ir.StackMappingVisitor.pop(StackMappingVisitor.java:267)
        //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.execute(StackMappingVisitor.java:600)
        //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.visit(StackMappingVisitor.java:398)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2030)
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
}
