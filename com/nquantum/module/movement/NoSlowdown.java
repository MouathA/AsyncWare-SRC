package com.nquantum.module.movement;

import java.util.*;
import com.nquantum.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.settings.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.util.*;
import com.nquantum.util.*;
import net.minecraft.network.*;
import com.nquantum.event.*;
import com.nquantum.event.impl.*;
import net.minecraft.network.play.client.*;
import com.nquantum.module.*;

public class NoSlowdown extends Module
{
    @Override
    public void setup() {
        super.setup();
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Vanilla");
        list.add("NCP");
        Asyncware.instance.settingsManager.rSetting(new Setting("No Slowdown Mode", this, "NCP", list));
    }
    
    @Punjabi
    @Override
    public void onPre(final EventPreMotionUpdate eventPreMotionUpdate) {
        this.setDisplayName("No Slowdown " + ChatFormatting.GRAY + Asyncware.instance.settingsManager.getSettingByName("No Slowdown Mode").getValString());
        if (this.mc.thePlayer.isBlocking()) {
            PacketUtil.sendPacketPlayer(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
    }
    
    @Punjabi
    @Override
    public void onPost(final EventPostMotionUpdate eventPostMotionUpdate) {
        if (this.mc.thePlayer.isBlocking()) {
            PacketUtil.sendPacketPlayer(new C08PacketPlayerBlockPlacement(this.mc.thePlayer.inventory.getCurrentItem()));
        }
    }
    
    public NoSlowdown() {
        super("No Slowdown", 0, Category.MOVEMENT);
    }
}
