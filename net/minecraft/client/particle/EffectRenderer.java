package net.minecraft.client.particle;

import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.*;
import optfine.*;
import com.google.common.collect.*;

public class EffectRenderer
{
    private static final ResourceLocation particleTextures;
    private Map particleTypes;
    private Random rand;
    protected World worldObj;
    private List[][] fxLayers;
    private List particleEmitters;
    private static final String __OBFID = "CL_00000915";
    private TextureManager renderer;
    
    private void updateEffectAlphaLayer(final List list) {
        final ArrayList arrayList = Lists.newArrayList();
        while (0 < list.size()) {
            final EntityFX entityFX = list.get(0);
            this.tickParticle(entityFX);
            if (entityFX.isDead) {
                arrayList.add(entityFX);
            }
            int n = 0;
            ++n;
        }
        list.removeAll(arrayList);
    }
    
    private void moveToLayer(final EntityFX entityFX, final int n, final int n2) {
        while (true) {
            if (this.fxLayers[0][n].contains(entityFX)) {
                this.fxLayers[0][n].remove(entityFX);
                this.fxLayers[0][n2].add(entityFX);
            }
            int n3 = 0;
            ++n3;
        }
    }
    
    public void updateEffects() {
        while (true) {
            this.updateEffectLayer(0);
            int n = 0;
            ++n;
        }
    }
    
    private void registerVanillaParticles() {
        this.registerParticle(EnumParticleTypes.EXPLOSION_NORMAL.getParticleID(), new EntityExplodeFX.Factory());
        this.registerParticle(EnumParticleTypes.WATER_BUBBLE.getParticleID(), new EntityBubbleFX.Factory());
        this.registerParticle(EnumParticleTypes.WATER_SPLASH.getParticleID(), new EntitySplashFX.Factory());
        this.registerParticle(EnumParticleTypes.WATER_WAKE.getParticleID(), new EntityFishWakeFX.Factory());
        this.registerParticle(EnumParticleTypes.WATER_DROP.getParticleID(), new EntityRainFX.Factory());
        this.registerParticle(EnumParticleTypes.SUSPENDED.getParticleID(), new EntitySuspendFX.Factory());
        this.registerParticle(EnumParticleTypes.SUSPENDED_DEPTH.getParticleID(), new EntityAuraFX.Factory());
        this.registerParticle(EnumParticleTypes.CRIT.getParticleID(), new EntityCrit2FX.Factory());
        this.registerParticle(EnumParticleTypes.CRIT_MAGIC.getParticleID(), new EntityCrit2FX.MagicFactory());
        this.registerParticle(EnumParticleTypes.SMOKE_NORMAL.getParticleID(), new EntitySmokeFX.Factory());
        this.registerParticle(EnumParticleTypes.SMOKE_LARGE.getParticleID(), new EntityCritFX.Factory());
        this.registerParticle(EnumParticleTypes.SPELL.getParticleID(), new EntitySpellParticleFX.Factory());
        this.registerParticle(EnumParticleTypes.SPELL_INSTANT.getParticleID(), new EntitySpellParticleFX.InstantFactory());
        this.registerParticle(EnumParticleTypes.SPELL_MOB.getParticleID(), new EntitySpellParticleFX.MobFactory());
        this.registerParticle(EnumParticleTypes.SPELL_MOB_AMBIENT.getParticleID(), new EntitySpellParticleFX.AmbientMobFactory());
        this.registerParticle(EnumParticleTypes.SPELL_WITCH.getParticleID(), new EntitySpellParticleFX.WitchFactory());
        this.registerParticle(EnumParticleTypes.DRIP_WATER.getParticleID(), new EntityDropParticleFX.WaterFactory());
        this.registerParticle(EnumParticleTypes.DRIP_LAVA.getParticleID(), new EntityDropParticleFX.LavaFactory());
        this.registerParticle(EnumParticleTypes.VILLAGER_ANGRY.getParticleID(), new EntityHeartFX.AngryVillagerFactory());
        this.registerParticle(EnumParticleTypes.VILLAGER_HAPPY.getParticleID(), new EntityAuraFX.HappyVillagerFactory());
        this.registerParticle(EnumParticleTypes.TOWN_AURA.getParticleID(), new EntityAuraFX.Factory());
        this.registerParticle(EnumParticleTypes.NOTE.getParticleID(), new EntityNoteFX.Factory());
        this.registerParticle(EnumParticleTypes.PORTAL.getParticleID(), new EntityPortalFX.Factory());
        this.registerParticle(EnumParticleTypes.ENCHANTMENT_TABLE.getParticleID(), new EntityEnchantmentTableParticleFX.EnchantmentTable());
        this.registerParticle(EnumParticleTypes.FLAME.getParticleID(), new EntityFlameFX.Factory());
        this.registerParticle(EnumParticleTypes.LAVA.getParticleID(), new EntityLavaFX.Factory());
        this.registerParticle(EnumParticleTypes.FOOTSTEP.getParticleID(), new EntityFootStepFX.Factory());
        this.registerParticle(EnumParticleTypes.CLOUD.getParticleID(), new EntityCloudFX.Factory());
        this.registerParticle(EnumParticleTypes.REDSTONE.getParticleID(), new EntityReddustFX.Factory());
        this.registerParticle(EnumParticleTypes.SNOWBALL.getParticleID(), new EntityBreakingFX.SnowballFactory());
        this.registerParticle(EnumParticleTypes.SNOW_SHOVEL.getParticleID(), new EntitySnowShovelFX.Factory());
        this.registerParticle(EnumParticleTypes.SLIME.getParticleID(), new EntityBreakingFX.SlimeFactory());
        this.registerParticle(EnumParticleTypes.HEART.getParticleID(), new EntityHeartFX.Factory());
        this.registerParticle(EnumParticleTypes.BARRIER.getParticleID(), new Barrier.Factory());
        this.registerParticle(EnumParticleTypes.ITEM_CRACK.getParticleID(), new EntityBreakingFX.Factory());
        this.registerParticle(EnumParticleTypes.BLOCK_CRACK.getParticleID(), new EntityDiggingFX.Factory());
        this.registerParticle(EnumParticleTypes.BLOCK_DUST.getParticleID(), new EntityBlockDustFX.Factory());
        this.registerParticle(EnumParticleTypes.EXPLOSION_HUGE.getParticleID(), new EntityHugeExplodeFX.Factory());
        this.registerParticle(EnumParticleTypes.EXPLOSION_LARGE.getParticleID(), new EntityLargeExplodeFX.Factory());
        this.registerParticle(EnumParticleTypes.FIREWORKS_SPARK.getParticleID(), new EntityFirework.Factory());
        this.registerParticle(EnumParticleTypes.MOB_APPEARANCE.getParticleID(), new MobAppearance.Factory());
    }
    
    public void moveToNoAlphaLayer(final EntityFX entityFX) {
        this.moveToLayer(entityFX, 0, 1);
    }
    
    public EntityFX spawnEffectParticle(final int n, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
        final IParticleFactory particleFactory = this.particleTypes.get(n);
        if (particleFactory != null) {
            final EntityFX entityFX = particleFactory.getEntityFX(n, this.worldObj, n2, n3, n4, n5, n6, n7, array);
            if (entityFX != null) {
                this.addEffect(entityFX);
                return entityFX;
            }
        }
        return null;
    }
    
    public void renderLitParticles(final Entity entity, final float n) {
        final float cos = MathHelper.cos(entity.rotationYaw * 0.017453292f);
        final float sin = MathHelper.sin(entity.rotationYaw * 0.017453292f);
        final float n2 = -sin * MathHelper.sin(entity.rotationPitch * 0.017453292f);
        final float n3 = cos * MathHelper.sin(entity.rotationPitch * 0.017453292f);
        final float cos2 = MathHelper.cos(entity.rotationPitch * 0.017453292f);
        while (true) {
            final List list = this.fxLayers[3][0];
            if (!list.isEmpty()) {
                final WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
                while (0 < list.size()) {
                    list.get(0).renderParticle(worldRenderer, entity, n, cos, cos2, sin, n2, n3);
                    int n4 = 0;
                    ++n4;
                }
            }
            int n5 = 0;
            ++n5;
        }
    }
    
    public void addBlockHitEffects(final BlockPos blockPos, final EnumFacing enumFacing) {
        final IBlockState blockState = this.worldObj.getBlockState(blockPos);
        final Block block = blockState.getBlock();
        if (block.getRenderType() != -1) {
            final int x = blockPos.getX();
            final int y = blockPos.getY();
            final int z = blockPos.getZ();
            final float n = 0.1f;
            double n2 = x + this.rand.nextDouble() * (block.getBlockBoundsMaxX() - block.getBlockBoundsMinX() - n * 2.0f) + n + block.getBlockBoundsMinX();
            double n3 = y + this.rand.nextDouble() * (block.getBlockBoundsMaxY() - block.getBlockBoundsMinY() - n * 2.0f) + n + block.getBlockBoundsMinY();
            double n4 = z + this.rand.nextDouble() * (block.getBlockBoundsMaxZ() - block.getBlockBoundsMinZ() - n * 2.0f) + n + block.getBlockBoundsMinZ();
            if (enumFacing == EnumFacing.DOWN) {
                n3 = y + block.getBlockBoundsMinY() - n;
            }
            if (enumFacing == EnumFacing.UP) {
                n3 = y + block.getBlockBoundsMaxY() + n;
            }
            if (enumFacing == EnumFacing.NORTH) {
                n4 = z + block.getBlockBoundsMinZ() - n;
            }
            if (enumFacing == EnumFacing.SOUTH) {
                n4 = z + block.getBlockBoundsMaxZ() + n;
            }
            if (enumFacing == EnumFacing.WEST) {
                n2 = x + block.getBlockBoundsMinX() - n;
            }
            if (enumFacing == EnumFacing.EAST) {
                n2 = x + block.getBlockBoundsMaxX() + n;
            }
            this.addEffect(new EntityDiggingFX(this.worldObj, n2, n3, n4, 0.0, 0.0, 0.0, blockState).func_174846_a(blockPos).multiplyVelocity(0.2f).multipleParticleScaleBy(0.6f));
        }
    }
    
    public String getStatistics() {
        while (true) {
            final int n = 0 + this.fxLayers[0][0].size();
            int n2 = 0;
            ++n2;
        }
    }
    
    public void addBlockDestroyEffects(final BlockPos blockPos, IBlockState actualState) {
        boolean b;
        if (Reflector.ForgeBlock_addDestroyEffects.exists() && Reflector.ForgeBlock_isAir.exists()) {
            final Block block = actualState.getBlock();
            Reflector.callBoolean(block, Reflector.ForgeBlock_isAir, this.worldObj, blockPos);
            b = (!Reflector.callBoolean(block, Reflector.ForgeBlock_isAir, this.worldObj, blockPos) && !Reflector.callBoolean(block, Reflector.ForgeBlock_addDestroyEffects, this.worldObj, blockPos, this));
        }
        else {
            b = (actualState.getBlock().getMaterial() != Material.air);
        }
        if (!b) {
            return;
        }
        actualState = actualState.getBlock().getActualState(actualState, this.worldObj, blockPos);
        while (true) {
            final double n = blockPos.getX() + (0 + 0.5) / 4;
            final double n2 = blockPos.getY() + (0 + 0.5) / 4;
            final double n3 = blockPos.getZ() + (0 + 0.5) / 4;
            this.addEffect(new EntityDiggingFX(this.worldObj, n, n2, n3, n - blockPos.getX() - 0.5, n2 - blockPos.getY() - 0.5, n3 - blockPos.getZ() - 0.5, actualState).func_174846_a(blockPos));
            int n4 = 0;
            ++n4;
        }
    }
    
    private void updateEffectLayer(final int n) {
        while (true) {
            this.updateEffectAlphaLayer(this.fxLayers[n][0]);
            int n2 = 0;
            ++n2;
        }
    }
    
    public void addBlockHitEffects(final BlockPos blockPos, final MovingObjectPosition movingObjectPosition) {
        final Block block = this.worldObj.getBlockState(blockPos).getBlock();
        final boolean callBoolean = Reflector.callBoolean(block, Reflector.ForgeBlock_addHitEffects, this.worldObj, movingObjectPosition, this);
        if (block != null && !callBoolean) {
            this.addBlockHitEffects(blockPos, movingObjectPosition.sideHit);
        }
    }
    
    public void registerParticle(final int n, final IParticleFactory particleFactory) {
        this.particleTypes.put(n, particleFactory);
    }
    
    public void renderParticles(final Entity entity, final float n) {
        final float rotationX = ActiveRenderInfo.getRotationX();
        final float rotationZ = ActiveRenderInfo.getRotationZ();
        final float rotationYZ = ActiveRenderInfo.getRotationYZ();
        final float rotationXY = ActiveRenderInfo.getRotationXY();
        final float rotationXZ = ActiveRenderInfo.getRotationXZ();
        EntityFX.interpPosX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * n;
        EntityFX.interpPosY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * n;
        EntityFX.interpPosZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * n;
        GlStateManager.blendFunc(770, 771);
        GlStateManager.alphaFunc(516, 0.003921569f);
        while (true) {
            if (!this.fxLayers[0][0].isEmpty()) {
                switch (false) {
                    case 0: {
                        GlStateManager.depthMask(false);
                        break;
                    }
                    case 1: {
                        GlStateManager.depthMask(true);
                        break;
                    }
                }
                switch (false) {
                    default: {
                        this.renderer.bindTexture(EffectRenderer.particleTextures);
                        break;
                    }
                    case 1: {
                        this.renderer.bindTexture(TextureMap.locationBlocksTexture);
                        break;
                    }
                }
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                final Tessellator instance = Tessellator.getInstance();
                final WorldRenderer worldRenderer = instance.getWorldRenderer();
                worldRenderer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
                while (0 < this.fxLayers[0][0].size()) {
                    this.fxLayers[0][0].get(0).renderParticle(worldRenderer, entity, n, rotationX, rotationXZ, rotationZ, rotationYZ, rotationXY);
                    int n2 = 0;
                    ++n2;
                }
                instance.draw();
            }
            int n3 = 0;
            ++n3;
        }
    }
    
    public void emitParticleAtEntity(final Entity entity, final EnumParticleTypes enumParticleTypes) {
        this.particleEmitters.add(new EntityParticleEmitter(this.worldObj, entity, enumParticleTypes));
    }
    
    public void addEffect(final EntityFX entityFX) {
        if (entityFX != null && (!(entityFX instanceof EntityFirework.SparkFX) || Config.isFireworkParticles())) {
            final int fxLayer = entityFX.getFXLayer();
            final int n = (entityFX.getAlpha() == 1.0f) ? 1 : 0;
            if (this.fxLayers[fxLayer][n].size() >= 4000) {
                this.fxLayers[fxLayer][n].remove(0);
            }
            this.fxLayers[fxLayer][n].add(entityFX);
        }
    }
    
    public void moveToAlphaLayer(final EntityFX entityFX) {
        this.moveToLayer(entityFX, 1, 0);
    }
    
    static {
        particleTextures = new ResourceLocation("textures/particle/particles.png");
    }
    
    private void tickParticle(final EntityFX entityFX) {
        entityFX.onUpdate();
    }
    
    public void clearEffects(final World worldObj) {
        this.worldObj = worldObj;
        while (true) {
            this.fxLayers[0][0].clear();
            int n = 0;
            ++n;
        }
    }
    
    public EffectRenderer(final World worldObj, final TextureManager renderer) {
        this.fxLayers = new List[4][];
        this.particleEmitters = Lists.newArrayList();
        this.rand = new Random();
        this.particleTypes = Maps.newHashMap();
        this.worldObj = worldObj;
        this.renderer = renderer;
        this.fxLayers[0] = new List[2];
        while (true) {
            this.fxLayers[0][0] = Lists.newArrayList();
            int n = 0;
            ++n;
        }
    }
}
