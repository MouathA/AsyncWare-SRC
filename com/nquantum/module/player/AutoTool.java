package com.nquantum.module.player;

import com.nquantum.module.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import com.nquantum.event.impl.*;
import com.nquantum.event.*;

public class AutoTool extends Module
{
    public AutoTool() {
        super("Auto Tool", 0, Category.PLAYER);
    }
    
    public void updateTool(final BlockPos blockPos) {
        final Block block = this.mc.theWorld.getBlockState(blockPos).getBlock();
        float strVsBlock = 1.0f;
        while (true) {
            final ItemStack itemStack = this.mc.thePlayer.inventory.mainInventory[0];
            if (itemStack != null) {
                if (itemStack.getStrVsBlock(block) > strVsBlock) {
                    strVsBlock = itemStack.getStrVsBlock(block);
                }
            }
            int n = 0;
            ++n;
        }
    }
    
    @Punjabi
    @Override
    public void onUpdate(final EventUpdate eventUpdate) {
        if (!this.mc.gameSettings.keyBindAttack.pressed) {
            return;
        }
        if (this.mc.objectMouseOver == null) {
            return;
        }
        final BlockPos blockPos = this.mc.objectMouseOver.getBlockPos();
        if (blockPos == null) {
            return;
        }
        this.updateTool(blockPos);
    }
}
