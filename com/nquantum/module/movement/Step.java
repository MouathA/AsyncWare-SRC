package com.nquantum.module.movement;

import java.util.*;
import com.nquantum.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.settings.*;
import com.nquantum.module.*;
import com.nquantum.event.impl.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import com.nquantum.util.*;
import net.minecraft.network.*;
import com.nquantum.event.*;

public class Step extends Module
{
    @Override
    public void onDisable() {
        super.onDisable();
        this.mc.thePlayer.stepHeight = 0.5f;
    }
    
    @Override
    public void setup() {
        super.setup();
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Motion");
        list.add("NCP");
        list.add("Height");
        Asyncware.instance.settingsManager.rSetting(new Setting("Step Mode", this, "Motion", list));
    }
    
    public Step() {
        super("Step", 38, Category.MOVEMENT);
    }
    
    @Punjabi
    @Override
    public void onUpdate(final EventUpdate eventUpdate) {
        final String valString = Asyncware.instance.settingsManager.getSettingByName("Step Mode").getValString();
        this.setDisplayName("Step " + ChatFormatting.GRAY + valString);
        if (valString.equalsIgnoreCase("Motion") && this.mc.thePlayer.isCollidedHorizontally && this.mc.thePlayer.isCollidedVertically) {
            this.mc.thePlayer.setEntityBoundingBox(new AxisAlignedBB(this.mc.thePlayer.getPosition().add(0.0, 1.5, 0.0), this.mc.thePlayer.getPosition().add(0.0, 1.5, 0.0)));
            PacketUtil.sendPacketPlayer(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.5, this.mc.thePlayer.posZ, true));
        }
        if (valString.equalsIgnoreCase("Height")) {
            this.mc.thePlayer.stepHeight = 1.5f;
        }
    }
}
