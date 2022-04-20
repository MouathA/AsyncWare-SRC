package com.nquantum.module.combat;

import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.renderer.*;
import com.nquantum.module.customize.*;
import com.nquantum.util.color.*;
import org.lwjgl.opengl.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.*;
import com.nquantum.event.*;
import com.nquantum.module.*;
import com.nquantum.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.settings.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.entity.*;
import com.nquantum.event.impl.*;
import com.nquantum.util.*;
import java.util.*;
import com.nquantum.util.player.*;

public class InfAura extends Module
{
    private boolean resetPos;
    private double width;
    private double oldZ;
    private double oldY;
    private BlockPos serverPos;
    EntityPlayer target;
    private int colorSecondary;
    private ArrayList lastPath;
    private int colorPrimary;
    private long lastAttack;
    private double oldX;
    private double animHealth;
    
    @Punjabi
    public void onRenderUI(final Event2D event2D) {
        if (this.target != null) {
            GlStateManager.translate(300.0f, 300.0f, 0.0f);
            this.colorPrimary = Colors.darker(HUD.hudColor, 0.5f);
            this.colorSecondary = HUD.hudColor;
            GL11.glPushMatrix();
            this.width = 107.5;
            Gui.drawRect(-22.5, 0.0, 124.5, 50.0, new Color(24, 24, 24, 121).getRGB());
            GL11.glTranslatef(-22.0f, -2.2f, 0.0f);
            this.mc.fontRendererObj.drawString(this.target.getName(), 30.0f, 8.0f, -1, true);
            GL11.glScalef(2.0f, 2.0f, 2.0f);
            GL11.glTranslatef(-15.0f, -15.0f, 0.0f);
            this.mc.fontRendererObj.drawStringWithShadow(Math.round(this.target.getHealth()) + "", 30.0f, 25.0f, HUD.hudColor);
            this.mc.fontRendererObj.drawStringWithShadow("\u2764", (float)(this.mc.fontRendererObj.getStringWidth(Math.round(this.target.getHealth()) + "") + 32), 25.0f, HUD.hudColor);
            GL11.glTranslatef(15.0f, 15.0f, 0.0f);
            GL11.glScalef(0.5f, 0.5f, 0.5f);
            this.mc.fontRendererObj.drawString("", 30.0f, 25.0f, -1, true);
            GuiInventory.drawEntityOnScreen(15, 47, 20, 2.0f, 2.0f, this.target);
            this.animHealth += (this.target.getHealth() - this.animHealth) / 32.0 * 0.7;
            if (this.animHealth < 0.0 || this.animHealth > this.target.getMaxHealth()) {
                this.animHealth = this.target.getHealth();
            }
            else {
                GL11.glTranslatef(30.0f, 0.0f, 0.0f);
                Gui.drawRect(0.0, 40.5, (int)this.width, 48.5, this.colorPrimary);
                Gui.drawRect(0.0, 40.5, (int)(this.animHealth / this.target.getMaxHealth() * this.width), 48.5, this.colorSecondary);
            }
            GL11.glScalef(2.0f, 2.0f, 2.0f);
            GL11.glPopMatrix();
        }
    }
    
    @Punjabi
    @Override
    public void onPre(final EventPreMotionUpdate eventPreMotionUpdate) {
        this.preHit();
    }
    
    public InfAura() {
        super("InfiniteAura", 37, Category.COMBAT);
        this.lastPath = new ArrayList();
        this.lastAttack = 0L;
        this.animHealth = 1.0;
    }
    
    @Punjabi
    @Override
    public void onPost(final EventPostMotionUpdate eventPostMotionUpdate) {
        this.postHit();
    }
    
    @Override
    public void setup() {
        this.setDisplayName("Kill Aura §7TP");
        Asyncware.instance.settingsManager.rSetting(new Setting("Gaps Length", this, 4.0, 1.0, 10.0, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("Max Distance", this, 20.0, 10.0, 300.0, false));
        Asyncware.instance.settingsManager.rSetting(new Setting("Multi Targets", this, 1.0, 1.0, 10.0, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("Show RayTrace", this, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("APS", this, 2.0, 1.0, 10.0, false));
        Asyncware.instance.settingsManager.rSetting(new Setting("One Hit", this, false));
    }
    
    public void teleport(final double n, final double n2, final double n3) {
        PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(n, n2, n3, true));
        this.serverPos = new BlockPos(n, n2, n3);
    }
    
    private void reachAndAttackPlayer(final BlockPos blockPos, final EntityPlayer entityPlayer) {
        final double valDouble = Asyncware.instance.settingsManager.getSettingByName("Gaps Length").getValDouble();
        final BlockPos[] path = PathFinder.findPath(blockPos, entityPlayer.getPosition().add(0, 1, 0));
        BlockPos blockPos2 = blockPos;
        while (0 < path.length) {
            if (PathFinder.distanceBetween(blockPos2, path[0]) >= valDouble && PathFinder.distanceBetween(this.serverPos, entityPlayer.getPosition()) > 3.0) {
                this.teleport(path[0]);
                this.lastPath.add(path[0]);
                blockPos2 = path[0];
            }
            int n = 0;
            ++n;
        }
        this.mc.thePlayer.swingItem();
        this.mc.playerController.attackEntity(this.mc.thePlayer, entityPlayer);
        this.lastAttack = System.currentTimeMillis();
        this.resetPos = true;
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        if (Asyncware.instance.settingsManager.getSettingByName("One Hit").getValBoolean()) {
            this.preHit();
            this.postHit();
            this.toggle();
        }
    }
    
    @Punjabi
    public void onRender3D(final Event3D event3D) {
        if (System.currentTimeMillis() - this.lastAttack <= 500L && Asyncware.instance.settingsManager.getSettingByName("Show RayTrace").getValBoolean()) {
            final Iterator<BlockPos> iterator = this.lastPath.iterator();
            while (iterator.hasNext()) {
                ESPUtil.blockESPBox(iterator.next());
            }
        }
    }
    
    public void preHit() {
        final KillAura killAura = new KillAura();
        final long n = (long)(1000.0 / Asyncware.instance.settingsManager.getSettingByName("APS").getValDouble());
        final double valDouble = Asyncware.instance.settingsManager.getSettingByName("Max Distance").getValDouble();
        final int n2 = (int)Asyncware.instance.settingsManager.getSettingByName("Multi Targets").getValDouble();
        this.oldX = this.mc.thePlayer.posX;
        this.oldY = this.mc.thePlayer.posY;
        this.oldZ = this.mc.thePlayer.posZ;
        this.serverPos = this.mc.thePlayer.getPosition();
        if (System.currentTimeMillis() - this.lastAttack >= n) {
            if (n2 == 1) {
                this.target = (EntityPlayer)killAura.target;
                if (this.target != null && this != this.target) {
                    this.lastPath.clear();
                    this.reachAndAttackPlayer(this.serverPos, this.target);
                }
            }
            else {
                for (final EntityPlayer entityPlayer : PlayerUtil.getClosests(valDouble, n2)) {
                    if (entityPlayer != null && this != entityPlayer) {
                        this.reachAndAttackPlayer(this.serverPos, entityPlayer);
                    }
                }
            }
        }
    }
    
    public void teleport(final BlockPos serverPos) {
        PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(serverPos.getX(), serverPos.getY(), serverPos.getZ(), true));
        this.serverPos = serverPos;
    }
    
    public void postHit() {
        if (this.resetPos) {
            final double valDouble = Asyncware.instance.settingsManager.getSettingByName("Gaps Length").getValDouble();
            BlockPos serverPos = this.serverPos;
            final BlockPos[] path = PathFinder.findPath(serverPos, new BlockPos(this.oldX, this.oldY + 1.0, this.oldZ));
            while (0 < path.length) {
                final BlockPos blockPos = path[0];
                if (PathFinder.distanceBetween(serverPos, blockPos) >= valDouble) {
                    this.teleport(blockPos);
                    serverPos = blockPos;
                }
                int n = 0;
                ++n;
            }
            this.teleport(this.oldX, this.oldY, this.oldZ);
            this.resetPos = false;
        }
    }
}
