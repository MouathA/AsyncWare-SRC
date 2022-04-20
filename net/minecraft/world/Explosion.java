package net.minecraft.world;

import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.block.state.*;

public class Explosion
{
    private final double explosionX;
    private final boolean isFlaming;
    private final double explosionZ;
    private final World worldObj;
    private final Map playerKnockbackMap;
    private final float explosionSize;
    private final double explosionY;
    private final boolean isSmoking;
    private final Random explosionRNG;
    private final Entity exploder;
    private final List affectedBlockPositions;
    
    public EntityLivingBase getExplosivePlacedBy() {
        return (this.exploder == null) ? null : ((this.exploder instanceof EntityTNTPrimed) ? ((EntityTNTPrimed)this.exploder).getTntPlacedBy() : ((this.exploder instanceof EntityLivingBase) ? ((EntityLivingBase)this.exploder) : null));
    }
    
    public void doExplosionB(final boolean b) {
        this.worldObj.playSoundEffect(this.explosionX, this.explosionY, this.explosionZ, "random.explode", 4.0f, (1.0f + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2f) * 0.7f);
        if (this.explosionSize >= 2.0f && this.isSmoking) {
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.explosionX, this.explosionY, this.explosionZ, 1.0, 0.0, 0.0, new int[0]);
        }
        else {
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.explosionX, this.explosionY, this.explosionZ, 1.0, 0.0, 0.0, new int[0]);
        }
        if (this.isSmoking) {
            for (final BlockPos blockPos : this.affectedBlockPositions) {
                final Block block = this.worldObj.getBlockState(blockPos).getBlock();
                if (b) {
                    final double n = blockPos.getX() + this.worldObj.rand.nextFloat();
                    final double n2 = blockPos.getY() + this.worldObj.rand.nextFloat();
                    final double n3 = blockPos.getZ() + this.worldObj.rand.nextFloat();
                    final double n4 = n - this.explosionX;
                    final double n5 = n2 - this.explosionY;
                    final double n6 = n3 - this.explosionZ;
                    final double n7 = MathHelper.sqrt_double(n4 * n4 + n5 * n5 + n6 * n6);
                    final double n8 = n4 / n7;
                    final double n9 = n5 / n7;
                    final double n10 = n6 / n7;
                    final double n11 = 0.5 / (n7 / this.explosionSize + 0.1) * (this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3f);
                    final double n12 = n8 * n11;
                    final double n13 = n9 * n11;
                    final double n14 = n10 * n11;
                    this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (n + this.explosionX * 1.0) / 2.0, (n2 + this.explosionY * 1.0) / 2.0, (n3 + this.explosionZ * 1.0) / 2.0, n12, n13, n14, new int[0]);
                    this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, n, n2, n3, n12, n13, n14, new int[0]);
                }
                if (block.getMaterial() != Material.air) {
                    if (block.canDropFromExplosion(this)) {
                        block.dropBlockAsItemWithChance(this.worldObj, blockPos, this.worldObj.getBlockState(blockPos), 1.0f / this.explosionSize, 0);
                    }
                    this.worldObj.setBlockState(blockPos, Blocks.air.getDefaultState(), 3);
                    block.onBlockDestroyedByExplosion(this.worldObj, blockPos, this);
                }
            }
        }
        if (this.isFlaming) {
            for (final BlockPos blockPos2 : this.affectedBlockPositions) {
                if (this.worldObj.getBlockState(blockPos2).getBlock().getMaterial() == Material.air && this.worldObj.getBlockState(blockPos2.down()).getBlock().isFullBlock() && this.explosionRNG.nextInt(3) == 0) {
                    this.worldObj.setBlockState(blockPos2, Blocks.fire.getDefaultState());
                }
            }
        }
    }
    
    public Explosion(final World world, final Entity entity, final double n, final double n2, final double n3, final float n4, final boolean b, final boolean b2, final List list) {
        this(world, entity, n, n2, n3, n4, b, b2);
        this.affectedBlockPositions.addAll(list);
    }
    
    public List getAffectedBlockPositions() {
        return this.affectedBlockPositions;
    }
    
    public Explosion(final World world, final Entity entity, final double n, final double n2, final double n3, final float n4, final List list) {
        this(world, entity, n, n2, n3, n4, false, true, list);
    }
    
    public Map getPlayerKnockbackMap() {
        return this.playerKnockbackMap;
    }
    
    public void func_180342_d() {
        this.affectedBlockPositions.clear();
    }
    
    public Explosion(final World worldObj, final Entity exploder, final double explosionX, final double explosionY, final double explosionZ, final float explosionSize, final boolean isFlaming, final boolean isSmoking) {
        this.explosionRNG = new Random();
        this.affectedBlockPositions = Lists.newArrayList();
        this.playerKnockbackMap = Maps.newHashMap();
        this.worldObj = worldObj;
        this.exploder = exploder;
        this.explosionSize = explosionSize;
        this.explosionX = explosionX;
        this.explosionY = explosionY;
        this.explosionZ = explosionZ;
        this.isFlaming = isFlaming;
        this.isSmoking = isSmoking;
    }
    
    public void doExplosionA() {
        final HashSet hashSet = Sets.newHashSet();
        while (true) {
            final double n = 0 / 15.0f * 2.0f - 1.0f;
            final double n2 = 0 / 15.0f * 2.0f - 1.0f;
            final double n3 = 0 / 15.0f * 2.0f - 1.0f;
            final double sqrt = Math.sqrt(n * n + n2 * n2 + n3 * n3);
            final double n4 = n / sqrt;
            final double n5 = n2 / sqrt;
            final double n6 = n3 / sqrt;
            float n7 = this.explosionSize * (0.7f + this.worldObj.rand.nextFloat() * 0.6f);
            double explosionX = this.explosionX;
            double explosionY = this.explosionY;
            double explosionZ = this.explosionZ;
            while (n7 > 0.0f) {
                final BlockPos blockPos = new BlockPos(explosionX, explosionY, explosionZ);
                final IBlockState blockState = this.worldObj.getBlockState(blockPos);
                if (blockState.getBlock().getMaterial() != Material.air) {
                    n7 -= (((this.exploder != null) ? this.exploder.getExplosionResistance(this, this.worldObj, blockPos, blockState) : blockState.getBlock().getExplosionResistance(null)) + 0.3f) * 0.3f;
                }
                if (n7 > 0.0f && (this.exploder == null || this.exploder.verifyExplosion(this, this.worldObj, blockPos, blockState, n7))) {
                    hashSet.add(blockPos);
                }
                explosionX += n4 * 0.30000001192092896;
                explosionY += n5 * 0.30000001192092896;
                explosionZ += n6 * 0.30000001192092896;
                n7 -= 0.22500001f;
            }
            int n8 = 0;
            ++n8;
        }
    }
}
