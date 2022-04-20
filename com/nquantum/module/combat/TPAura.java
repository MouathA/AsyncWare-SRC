package com.nquantum.module.combat;

import com.nquantum.util.time.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import com.nquantum.util.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import com.nquantum.event.*;
import com.nquantum.event.impl.*;
import com.nquantum.*;
import com.nquantum.util.player.*;
import net.minecraft.network.play.client.*;
import java.util.*;
import com.nquantum.util.raytrace.*;
import com.nquantum.module.*;
import net.minecraft.enchantment.*;
import net.minecraft.potion.*;
import net.minecraft.client.entity.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.settings.*;

public class TPAura extends Module
{
    public static final int maxYTP = 9;
    double z;
    double zPre;
    Timer timer;
    ArrayList triedPaths;
    ArrayList positions;
    int stage;
    boolean attack;
    private static EntityLivingBase en;
    double xPre;
    ArrayList positionsBack;
    double yPre;
    public static final double maxXZTP = 9.5;
    double yPreEn;
    double x;
    double xPreEn;
    double zPreEn;
    double y;
    
    public static boolean doBlock() {
        return TPAura.en != null;
    }
    
    static Minecraft access$500(final TPAura tpAura) {
        return tpAura.mc;
    }
    
    public void doReach(final double n, final boolean b, final ArrayList list) {
        if (this.mc.thePlayer.getDistanceToEntity(TPAura.en) <= 4.0f) {
            this.attack(TPAura.en);
            return;
        }
        this.attack = TPAuraUtil.infiniteReach(n, 9.5, 9.0, this.positionsBack, this.positions, TPAura.en);
    }
    
    public void sendPacket(final boolean b) {
        PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(this.x, this.y, this.z, true));
        if (b) {
            this.positionsBack.add(new Vec3(this.x, this.y, this.z));
            return;
        }
        this.positions.add(new Vec3(this.x, this.y, this.z));
    }
    
    @Punjabi
    public void onLateUpdate(final EventPostMotionUpdate eventPostMotionUpdate) {
        if (!this.attack) {
            return;
        }
        this.attack = false;
    }
    
    @Punjabi
    @Override
    public void onUpdate(final EventUpdate eventUpdate) {
        final KillAura killAura = new KillAura();
        final int n = (int)Asyncware.instance.settingsManager.getSettingByName("TP Aura APS").getValDouble();
        final int n2 = (int)Asyncware.instance.settingsManager.getSettingByName("TP Aura Range").getValDouble();
        if (!this.timer.hasTimeElapsed(1000 / n, true)) {
            return;
        }
        TargetUtil.targets.clear();
        TPAura.en = killAura.getClosest((float)n2);
        if (TPAura.en == null) {
            return;
        }
        if (!TargetUtil.hasEntity(TPAura.en)) {
            TargetUtil.targets.add(TPAura.en);
        }
        this.updateStages();
    }
    
    public void updateStages() {
        final int n = (int)Asyncware.instance.settingsManager.getSettingByName("TP Aura APS").getValDouble();
        final int n2 = (int)Asyncware.instance.settingsManager.getSettingByName("TP Aura Range").getValDouble();
        final int n3 = (int)Asyncware.instance.settingsManager.getSettingByName("Max Targets").getValDouble();
        final ArrayList closestEntities = this.getClosestEntities((float)n2);
        closestEntities.sort(new Comparator(this) {
            final TPAura this$0;
            
            @Override
            public int compare(final Object o, final Object o2) {
                return this.compare((EntityLivingBase)o, (EntityLivingBase)o2);
            }
            
            public int compare(final EntityLivingBase entityLivingBase, final EntityLivingBase entityLivingBase2) {
                if (TPAura.access$000(this.this$0).thePlayer.getDistanceToEntity(entityLivingBase) > TPAura.access$100(this.this$0).thePlayer.getDistanceToEntity(entityLivingBase2)) {
                    return 1;
                }
                if (TPAura.access$200(this.this$0).thePlayer.getDistanceToEntity(entityLivingBase) < TPAura.access$300(this.this$0).thePlayer.getDistanceToEntity(entityLivingBase2)) {
                    return -1;
                }
                if (TPAura.access$400(this.this$0).thePlayer.getDistanceToEntity(entityLivingBase) == TPAura.access$500(this.this$0).thePlayer.getDistanceToEntity(entityLivingBase2)) {
                    return 0;
                }
                return 0;
            }
        });
        for (final EntityLivingBase entityLivingBase : closestEntities) {
            if (0 >= n3) {
                break;
            }
            this.positions.clear();
            this.positionsBack.clear();
            this.triedPaths.clear();
            final TeleportResult pathFinderTeleportTo = TPAuraUtil.pathFinderTeleportTo(new Vec3(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ), new Vec3(entityLivingBase.posX, entityLivingBase.posY, entityLivingBase.posZ));
            this.triedPaths = pathFinderTeleportTo.triedPaths;
            if (!pathFinderTeleportTo.foundPath) {
                continue;
            }
            final Vec3 lastPos = pathFinderTeleportTo.lastPos;
            this.mc.thePlayer.swingItem();
            PacketUtil.sendPacket(new C02PacketUseEntity(entityLivingBase, C02PacketUseEntity.Action.ATTACK));
            this.positions = pathFinderTeleportTo.positions;
            this.positionsBack = TPAuraUtil.pathFinderTeleportBack(this.positions).positionsBack;
            int n4 = 0;
            ++n4;
        }
    }
    
    static Minecraft access$000(final TPAura tpAura) {
        return tpAura.mc;
    }
    
    public TPAura() {
        super("Teleport Aura", 0, Category.COMBAT);
        this.timer = new Timer();
        this.attack = false;
        this.stage = 0;
        this.positions = new ArrayList();
        this.positionsBack = new ArrayList();
        this.triedPaths = new ArrayList();
    }
    
    static Minecraft access$300(final TPAura tpAura) {
        return tpAura.mc;
    }
    
    static {
        TPAura.en = null;
    }
    
    private void attack(final EntityLivingBase entityLivingBase) {
        this.mc.thePlayer.swingItem();
        PacketUtil.sendPacket(new C02PacketUseEntity(entityLivingBase, C02PacketUseEntity.Action.ATTACK));
        final float func_152377_a = EnchantmentHelper.func_152377_a(this.mc.thePlayer.getHeldItem(), entityLivingBase.getCreatureAttribute());
        final boolean b = this.mc.thePlayer.fallDistance > 0.0f && !this.mc.thePlayer.onGround && !this.mc.thePlayer.isOnLadder() && !this.mc.thePlayer.isInWater() && !this.mc.thePlayer.isPotionActive(Potion.blindness) && this.mc.thePlayer.ridingEntity == null;
        if (func_152377_a > 0.0f) {
            this.mc.thePlayer.onEnchantmentCritical(entityLivingBase);
        }
    }
    
    static Minecraft access$400(final TPAura tpAura) {
        return tpAura.mc;
    }
    
    public ArrayList getClosestEntities(final float n) {
        final ArrayList<EntityLivingBase> list = new ArrayList<EntityLivingBase>();
        for (final Integer next : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (next == 0 && !(next instanceof EntityPlayerSP)) {
                final EntityLivingBase entityLivingBase = (EntityLivingBase)next;
                if (this != entityLivingBase) {
                    continue;
                }
                if (Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entityLivingBase) >= n) {
                    continue;
                }
                list.add(entityLivingBase);
            }
        }
        return list;
    }
    
    @Override
    public void onToggle() {
        TPAura.en = null;
        TargetUtil.targets.clear();
        this.stage = 0;
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
        this.xPreEn = 0.0;
        this.yPreEn = 0.0;
        this.zPreEn = 0.0;
        this.attack = false;
        super.onToggle();
    }
    
    private void attackInf(final EntityLivingBase entityLivingBase) {
        this.mc.thePlayer.swingItem();
        PacketUtil.sendPacketNoEvent(new C02PacketUseEntity(entityLivingBase, C02PacketUseEntity.Action.ATTACK));
        final float func_152377_a = EnchantmentHelper.func_152377_a(this.mc.thePlayer.getHeldItem(), entityLivingBase.getCreatureAttribute());
        final boolean b = this.mc.thePlayer.fallDistance > 0.0f && !this.mc.thePlayer.onGround && !this.mc.thePlayer.isOnLadder() && !this.mc.thePlayer.isInWater() && !this.mc.thePlayer.isPotionActive(Potion.blindness) && this.mc.thePlayer.ridingEntity == null;
        if (func_152377_a > 0.0f) {
            this.mc.thePlayer.onEnchantmentCritical(entityLivingBase);
        }
    }
    
    static Minecraft access$100(final TPAura tpAura) {
        return tpAura.mc;
    }
    
    @Override
    public void setup() {
        super.setup();
        Asyncware.instance.settingsManager.rSetting(new Setting("Max Targets", this, 1.0, 0.0, 50.0, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("TP Aura Range", this, 6.0, 0.0, 300.0, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("TP Aura APS", this, 2.0, 0.0, 20.0, true));
    }
    
    static Minecraft access$200(final TPAura tpAura) {
        return tpAura.mc;
    }
}
