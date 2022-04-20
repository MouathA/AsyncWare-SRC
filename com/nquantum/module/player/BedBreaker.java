package com.nquantum.module.player;

import com.nquantum.module.*;
import com.nquantum.event.impl.*;
import net.minecraft.block.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import com.nquantum.event.*;

public class BedBreaker extends Module
{
    BlockPos blockToBreak;
    private int xPos;
    private int yPos;
    private int zPos;
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        super.onEnable();
    }
    
    public BedBreaker() {
        super("BedBreaker", 21, Category.PLAYER);
    }
    
    @Punjabi
    @Override
    public void onUpdate(final EventUpdate eventUpdate) {
        this.setDisplayName("Bed Breaker");
        for (int i = -5; i < 5; ++i) {
            for (int j = 5; j > -5; --j) {
                for (int k = -5; k < 5; ++k) {
                    this.xPos = (int)this.mc.thePlayer.posX + i;
                    this.yPos = (int)this.mc.thePlayer.posY + j;
                    this.zPos = (int)this.mc.thePlayer.posZ + k;
                    final BlockPos blockPos = new BlockPos(this.xPos, this.yPos, this.zPos);
                    if (this.mc.theWorld.getBlockState(blockPos).getBlock().getBlockState().getBlock() == Block.getBlockById(26)) {
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
                    }
                }
            }
        }
    }
}
