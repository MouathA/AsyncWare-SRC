package net.minecraft.util;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.entity.projectile.*;

public class DamageSource
{
    public static DamageSource wither;
    public static DamageSource outOfWorld;
    private boolean fireDamage;
    private boolean projectile;
    public String damageType;
    private boolean damageIsAbsolute;
    public static DamageSource fall;
    private boolean magicDamage;
    public static DamageSource drown;
    private float hungerDamage;
    private boolean difficultyScaled;
    public static DamageSource inWall;
    public static DamageSource fallingBlock;
    public static DamageSource cactus;
    public static DamageSource lightningBolt;
    public static DamageSource magic;
    private boolean explosion;
    public static DamageSource starve;
    public static DamageSource lava;
    private boolean isUnblockable;
    private boolean isDamageAllowedInCreativeMode;
    public static DamageSource onFire;
    public static DamageSource inFire;
    public static DamageSource anvil;
    public static DamageSource generic;
    
    public boolean isProjectile() {
        return this.projectile;
    }
    
    public boolean isMagicDamage() {
        return this.magicDamage;
    }
    
    public String getDamageType() {
        return this.damageType;
    }
    
    public DamageSource setDifficultyScaled() {
        this.difficultyScaled = true;
        return this;
    }
    
    public static DamageSource causeThrownDamage(final Entity entity, final Entity entity2) {
        return new EntityDamageSourceIndirect("thrown", entity, entity2).setProjectile();
    }
    
    public static DamageSource causeMobDamage(final EntityLivingBase entityLivingBase) {
        return new EntityDamageSource("mob", entityLivingBase);
    }
    
    static {
        DamageSource.inFire = new DamageSource("inFire").setFireDamage();
        DamageSource.lightningBolt = new DamageSource("lightningBolt");
        DamageSource.onFire = new DamageSource("onFire").setDamageBypassesArmor().setFireDamage();
        DamageSource.lava = new DamageSource("lava").setFireDamage();
        DamageSource.inWall = new DamageSource("inWall").setDamageBypassesArmor();
        DamageSource.drown = new DamageSource("drown").setDamageBypassesArmor();
        DamageSource.starve = new DamageSource("starve").setDamageBypassesArmor().setDamageIsAbsolute();
        DamageSource.cactus = new DamageSource("cactus");
        DamageSource.fall = new DamageSource("fall").setDamageBypassesArmor();
        DamageSource.outOfWorld = new DamageSource("outOfWorld").setDamageBypassesArmor().setDamageAllowedInCreativeMode();
        DamageSource.generic = new DamageSource("generic").setDamageBypassesArmor();
        DamageSource.magic = new DamageSource("magic").setDamageBypassesArmor().setMagicDamage();
        DamageSource.wither = new DamageSource("wither").setDamageBypassesArmor();
        DamageSource.anvil = new DamageSource("anvil");
        DamageSource.fallingBlock = new DamageSource("fallingBlock");
    }
    
    public static DamageSource causeArrowDamage(final EntityArrow entityArrow, final Entity entity) {
        return new EntityDamageSourceIndirect("arrow", entityArrow, entity).setProjectile();
    }
    
    public boolean isFireDamage() {
        return this.fireDamage;
    }
    
    public boolean isDifficultyScaled() {
        return this.difficultyScaled;
    }
    
    protected DamageSource(final String damageType) {
        this.hungerDamage = 0.3f;
        this.damageType = damageType;
    }
    
    public boolean isDamageAbsolute() {
        return this.damageIsAbsolute;
    }
    
    public Entity getEntity() {
        return null;
    }
    
    public static DamageSource causePlayerDamage(final EntityPlayer entityPlayer) {
        return new EntityDamageSource("player", entityPlayer);
    }
    
    protected DamageSource setFireDamage() {
        this.fireDamage = true;
        return this;
    }
    
    public IChatComponent getDeathMessage(final EntityLivingBase entityLivingBase) {
        final EntityLivingBase func_94060_bK = entityLivingBase.func_94060_bK();
        final String string = "death.attack." + this.damageType;
        final String string2 = string + ".player";
        return (func_94060_bK != null && StatCollector.canTranslate(string2)) ? new ChatComponentTranslation(string2, new Object[] { entityLivingBase.getDisplayName(), func_94060_bK.getDisplayName() }) : new ChatComponentTranslation(string, new Object[] { entityLivingBase.getDisplayName() });
    }
    
    public boolean isUnblockable() {
        return this.isUnblockable;
    }
    
    public static DamageSource causeThornsDamage(final Entity entity) {
        return new EntityDamageSource("thorns", entity).setIsThornsDamage().setMagicDamage();
    }
    
    public boolean canHarmInCreative() {
        return this.isDamageAllowedInCreativeMode;
    }
    
    protected DamageSource setDamageBypassesArmor() {
        this.isUnblockable = true;
        this.hungerDamage = 0.0f;
        return this;
    }
    
    protected DamageSource setDamageIsAbsolute() {
        this.damageIsAbsolute = true;
        this.hungerDamage = 0.0f;
        return this;
    }
    
    public float getHungerDamage() {
        return this.hungerDamage;
    }
    
    public DamageSource setExplosion() {
        this.explosion = true;
        return this;
    }
    
    public boolean isCreativePlayer() {
        final Entity entity = this.getEntity();
        return entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode;
    }
    
    public static DamageSource setExplosionSource(final Explosion explosion) {
        return (explosion != null && explosion.getExplosivePlacedBy() != null) ? new EntityDamageSource("explosion.player", explosion.getExplosivePlacedBy()).setDifficultyScaled().setExplosion() : new DamageSource("explosion").setDifficultyScaled().setExplosion();
    }
    
    public DamageSource setMagicDamage() {
        this.magicDamage = true;
        return this;
    }
    
    public boolean isExplosion() {
        return this.explosion;
    }
    
    public static DamageSource causeFireballDamage(final EntityFireball entityFireball, final Entity entity) {
        return (entity == null) ? new EntityDamageSourceIndirect("onFire", entityFireball, entityFireball).setFireDamage().setProjectile() : new EntityDamageSourceIndirect("fireball", entityFireball, entity).setFireDamage().setProjectile();
    }
    
    public static DamageSource causeIndirectMagicDamage(final Entity entity, final Entity entity2) {
        return new EntityDamageSourceIndirect("indirectMagic", entity, entity2).setDamageBypassesArmor().setMagicDamage();
    }
    
    public DamageSource setProjectile() {
        this.projectile = true;
        return this;
    }
    
    public Entity getSourceOfDamage() {
        return this.getEntity();
    }
    
    protected DamageSource setDamageAllowedInCreativeMode() {
        this.isDamageAllowedInCreativeMode = true;
        return this;
    }
}
