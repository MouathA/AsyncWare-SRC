package com.nquantum.module.movement;

import java.util.*;
import com.nquantum.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.settings.*;
import com.nquantum.util.player.*;
import com.nquantum.event.*;
import com.nquantum.module.*;
import net.minecraft.util.*;
import com.nquantum.util.*;
import com.nquantum.event.impl.*;

public class Phase extends Module
{
    private double dist;
    private int reset;
    
    @Override
    public void setup() {
        super.setup();
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Pass Through");
        list.add("Bounding Box");
        list.add("Collision");
        Asyncware.instance.settingsManager.rSetting(new Setting("Phase Mode", this, "Pass Through", list));
    }
    
    @Punjabi
    @Override
    public void onUpdate(final EventUpdate eventUpdate) {
        if (Asyncware.instance.settingsManager.getSettingByName("Phase Mode").getValString().equalsIgnoreCase("Pass Through")) {
            --this.reset;
            final double cos = Math.cos(Math.toRadians(this.mc.thePlayer.rotationYaw + 90.0f));
            final double sin = Math.sin(Math.toRadians(this.mc.thePlayer.rotationYaw + 90.0f));
            final double n = this.mc.thePlayer.moveForward * 2.6 * cos + this.mc.thePlayer.moveStrafing * 2.6 * sin;
            final double n2 = this.mc.thePlayer.moveForward * 2.6 * sin + this.mc.thePlayer.moveStrafing * 2.6 * cos;
            if (PlayerUtils.isInsideBlock() && this.mc.thePlayer.isSneaking()) {
                this.reset = 1;
            }
            if (this.reset > 0) {
                this.mc.thePlayer.boundingBox.offsetAndUpdate(n, 0.0, n2);
            }
        }
    }
    
    public Phase() {
        super("Phase", 0, Category.MOVEMENT);
        this.dist = 1.0;
    }
    
    @Punjabi
    public void onCollision(final EventCollide eventCollide) {
        final String valString = Asyncware.instance.settingsManager.getSettingByName("Phase Mode").getValString();
        if (valString.equalsIgnoreCase("Pass Through") && ((PlayerUtils.isInsideBlock() && this.mc.gameSettings.keyBindJump.isKeyDown()) || (!PlayerUtils.isInsideBlock() && eventCollide.getBoundingBox() != null && eventCollide.getBoundingBox().maxY > this.mc.thePlayer.boundingBox.minY && this.mc.thePlayer.isSneaking()))) {
            eventCollide.setBoundingBox(null);
        }
        if (valString.equalsIgnoreCase("Collision") && MovementUtil.isMoving()) {
            eventCollide.cancel();
        }
    }
    
    @Punjabi
    public void onBB(final EventBoundingBox eventBoundingBox) {
        if (Asyncware.instance.settingsManager.getSettingByName("Phase Mode").getValString().equalsIgnoreCase("Bounding Box") && MovementUtil.isMoving()) {
            eventBoundingBox.cancel();
        }
    }
}
