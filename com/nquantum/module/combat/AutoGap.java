package com.nquantum.module.combat;

import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.settings.*;
import com.nquantum.module.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import com.nquantum.*;
import com.nquantum.event.impl.*;
import net.minecraft.potion.*;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import com.nquantum.event.*;

public class AutoGap extends Module
{
    private Timer4 timer;
    private Timer4 eatTimer;
    public static Setting DELAY;
    public static Setting HEALTH;
    private int prevSlot;
    
    public AutoGap() {
        super("Auto Gapple", 0, Category.COMBAT);
        this.timer = new Timer4();
        this.eatTimer = new Timer4();
        this.prevSlot = -1;
    }
    
    public static int getGAppleFromInventory() {
        final Minecraft minecraft = Minecraft.getMinecraft();
        while (true) {
            if (minecraft.thePlayer.inventoryContainer.getSlot(1).getHasStack() && Item.getIdFromItem(minecraft.thePlayer.inventoryContainer.getSlot(1).getStack().getItem()) == 322) {
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    protected void swap(final int n, final int n2) {
        this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, n, n2, 2, this.mc.thePlayer);
    }
    
    @Override
    public void setup() {
        super.setup();
        Asyncware.instance.settingsManager.rSetting(AutoGap.DELAY = new Setting("Auto Gap Delay", this, 100.0, 50.0, 350.0, false));
        Asyncware.instance.settingsManager.rSetting(AutoGap.HEALTH = new Setting("Auto Gap Max Health", this, 3.0, 1.0, 15.0, false));
    }
    
    @Punjabi
    @Override
    public void onPre(final EventPreMotionUpdate eventPreMotionUpdate) {
        if (getGAppleFromInventory() != -1 && this.prevSlot < 0 && this.mc.thePlayer.getHealth() < (float)AutoGap.HEALTH.getValDouble() && !this.mc.thePlayer.isPotionActive(Potion.regeneration.id) && this.timer.delay((float)AutoGap.DELAY.getValDouble())) {
            this.swap(getGAppleFromInventory(), 6);
            this.prevSlot = this.mc.thePlayer.inventory.currentItem;
            this.mc.thePlayer.inventory.currentItem = 6;
            this.mc.rightClickMouse();
            if (this.mc.thePlayer.getHeldItem().getItem() instanceof ItemAppleGold) {
                if (this.mc.thePlayer.getHealth() <= 9.0f) {
                    this.mc.gameSettings.keyBindUseItem.pressed = true;
                }
                else {
                    this.mc.gameSettings.keyBindUseItem.pressed = false;
                }
            }
            this.eatTimer.reset();
            this.timer.reset();
        }
        else if (this.prevSlot >= 0 && this.eatTimer.getDifference() > 1750L) {
            this.mc.playerController.onStoppedUsingItem(this.mc.thePlayer);
            this.mc.thePlayer.inventory.currentItem = this.prevSlot;
            this.prevSlot = -1;
            this.mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(this.mc.thePlayer.inventory.currentItem));
            this.timer.reset();
        }
    }
    
    public boolean isEating() {
        return this.prevSlot >= 0;
    }
    
    static class Timer4
    {
        private long prevMS;
        
        public long getDifference() {
            return this.getTime() - this.prevMS;
        }
        
        public void setDifference(final long n) {
            this.prevMS = this.getTime() - n;
        }
        
        public long getTime() {
            return System.nanoTime() / 1000000L;
        }
        
        public Timer4() {
            this.prevMS = 0L;
        }
        
        public boolean delay(final float n) {
            return this.getTime() - this.prevMS >= n;
        }
        
        public void reset() {
            this.prevMS = this.getTime();
        }
    }
}
