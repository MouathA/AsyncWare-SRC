package com.nquantum.module.player;

import com.nquantum.event.impl.*;
import net.minecraft.util.*;
import com.nquantum.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.block.*;
import com.nquantum.event.*;
import com.nquantum.module.*;
import java.util.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.settings.*;

public class Jesus extends Module
{
    @Punjabi
    @Override
    public synchronized void onUpdate(final EventUpdate eventUpdate) {
        final BlockPos blockPos = new BlockPos(this.mc.thePlayer.getPosition().add(0, -1, 0));
        final String valString = Asyncware.instance.settingsManager.getSettingByName("Jesus Mode").getValString();
        this.setDisplayName("Jesus " + ChatFormatting.GRAY + valString);
        if (valString.equalsIgnoreCase("Solid") && blockPos.getBlock() instanceof BlockLiquid) {
            this.mc.thePlayer.onGround = true;
            this.mc.thePlayer.motionY = 0.0;
        }
    }
    
    public Jesus() {
        super("Jesus", 0, Category.MOVEMENT);
    }
    
    @Override
    public void setup() {
        super.setup();
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Solid");
        list.add("Motion");
        list.add("Spoof Ground");
        Asyncware.instance.settingsManager.rSetting(new Setting("Jesus Mode", this, "Solid", list));
    }
}
