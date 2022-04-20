package com.nquantum.module.render;

import net.minecraft.util.*;
import com.nquantum.event.*;
import com.nquantum.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.settings.*;
import com.nquantum.event.impl.*;
import net.minecraft.block.*;
import java.awt.*;
import com.nquantum.util.render.*;
import com.nquantum.module.*;

public class BlockOverlay extends Module
{
    BlockPos position;
    
    @Punjabi
    @Override
    public void onUpdate(final EventUpdate eventUpdate) {
        this.position = this.mc.objectMouseOver.getBlockPos();
    }
    
    @Override
    public void setup() {
        super.setup();
        Asyncware.instance.settingsManager.rSetting(new Setting("Block R", this, 255.0, 0.0, 255.0, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("Block G", this, 255.0, 0.0, 255.0, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("Block B", this, 255.0, 0.0, 255.0, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("Block Opacity", this, 255.0, 0.0, 255.0, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("Outline", this, false));
    }
    
    @Punjabi
    @Override
    public void on3D(final Event3D event3D) {
        final int n = (int)Asyncware.instance.settingsManager.getSettingByName("Block R").getValDouble();
        final int n2 = (int)Asyncware.instance.settingsManager.getSettingByName("Block G").getValDouble();
        final int n3 = (int)Asyncware.instance.settingsManager.getSettingByName("Block B").getValDouble();
        final int n4 = (int)Asyncware.instance.settingsManager.getSettingByName("Block Opacity").getValDouble();
        final boolean valBoolean = Asyncware.instance.settingsManager.getSettingByName("Outline").getValBoolean();
        if (!(this.position.getBlock() instanceof BlockAir)) {
            RenderUtil.drawBlockBox(this.position, new Color(n, n2, n3, n4), valBoolean);
        }
    }
    
    public BlockOverlay() {
        super("Block Overlay", 0, Category.RENDER);
    }
}
