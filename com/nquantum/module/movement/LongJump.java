package com.nquantum.module.movement;

import com.nquantum.util.time.*;
import com.nquantum.*;
import com.nquantum.util.*;
import com.nquantum.event.*;
import com.nquantum.event.impl.*;
import net.minecraft.potion.*;
import java.util.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.settings.*;
import com.nquantum.module.*;

public class LongJump extends Module
{
    Timer timer;
    
    @Punjabi
    public void onNigger(final EventUpdate eventUpdate) {
        final String valString = Asyncware.instance.settingsManager.getSettingByName("Longjump Mode").getValString();
        if (valString.equalsIgnoreCase("Redesky")) {
            if (this.mc.gameSettings.keyBindJump.isKeyDown()) {
                this.mc.thePlayer.speedInAir = 0.09f;
                this.mc.thePlayer.motionY = 0.45;
                this.mc.timer.timerSpeed = 0.3f;
            }
            else {
                this.mc.timer.timerSpeed = 0.6f;
            }
        }
        if (valString.equalsIgnoreCase("Verus")) {
            MovementUtil.strafe(5.0f);
            if (this.timer.hasTimeElapsed(500L, true)) {
                this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 0.5, this.mc.thePlayer.posZ);
            }
        }
    }
    
    @Punjabi
    @Override
    public void onPost(final EventPostMotionUpdate eventPostMotionUpdate) {
        if (Asyncware.instance.settingsManager.getSettingByName("Longjump Mode").getValString().equalsIgnoreCase("Vanilla") && (this.mc.gameSettings.keyBindForward.isKeyDown() || this.mc.gameSettings.keyBindLeft.isKeyDown() || this.mc.gameSettings.keyBindRight.isKeyDown() || this.mc.gameSettings.keyBindBack.isKeyDown()) && this.mc.gameSettings.keyBindJump.isKeyDown()) {
            final float n = this.mc.thePlayer.rotationYaw + ((this.mc.thePlayer.moveForward < 0.0f) ? 180 : 0) + ((this.mc.thePlayer.moveStrafing > 0.0f) ? (-90.0f * ((this.mc.thePlayer.moveForward < 0.0f) ? -0.5f : ((this.mc.thePlayer.moveForward > 0.0f) ? 0.4f : 1.0f))) : 0.0f);
            final float n2 = (float)Math.cos((n + 90.0f) * 3.141592653589793 / 180.0);
            final float n3 = (float)Math.sin((n + 90.0f) * 3.141592653589793 / 180.0);
            if (this.mc.thePlayer.isCollidedVertically && (this.mc.gameSettings.keyBindForward.isKeyDown() || this.mc.gameSettings.keyBindLeft.isKeyDown() || this.mc.gameSettings.keyBindRight.isKeyDown() || this.mc.gameSettings.keyBindBack.isKeyDown()) && this.mc.gameSettings.keyBindJump.isKeyDown()) {
                this.mc.thePlayer.motionX = n2 * 0.29f;
                this.mc.thePlayer.motionZ = n3 * 0.29f;
            }
            if (this.mc.thePlayer.motionY == 0.33319999363422365 && (this.mc.gameSettings.keyBindForward.isKeyDown() || this.mc.gameSettings.keyBindLeft.isKeyDown() || this.mc.gameSettings.keyBindRight.isKeyDown() || this.mc.gameSettings.keyBindBack.isKeyDown())) {
                if (this.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                    this.mc.thePlayer.motionX = n2 * 1.34;
                    this.mc.thePlayer.motionZ = n3 * 1.34;
                }
                else {
                    this.mc.thePlayer.motionX = n2 * 1.261;
                    this.mc.thePlayer.motionZ = n3 * 1.261;
                }
            }
        }
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        this.mc.timer.timerSpeed = 1.0f;
        this.mc.thePlayer.speedInAir = 0.02f;
    }
    
    @Override
    public void onEnable() {
        final String valString = Asyncware.instance.settingsManager.getSettingByName("Longjump Mode").getValString();
        super.onEnable();
        if (valString.equalsIgnoreCase("Verus")) {
            this.mc.thePlayer.motionY = 1.0;
        }
    }
    
    @Override
    public void setup() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Vanilla");
        list.add("Redesky");
        list.add("Verus");
        Asyncware.instance.settingsManager.rSetting(new Setting("Longjump Mode", this, "Vanilla", list));
        super.setup();
    }
    
    public LongJump() {
        super("LongJump", 38, Category.MOVEMENT);
        this.timer = new Timer();
    }
}
