package net.minecraft.world.demo;

import net.minecraft.server.management.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.util.*;

public class DemoWorldManager extends ItemInWorldManager
{
    private int field_73102_f;
    private boolean field_73105_c;
    private boolean demoTimeExpired;
    private int field_73104_e;
    
    @Override
    public void blockRemoving(final BlockPos blockPos) {
        if (!this.demoTimeExpired) {
            super.blockRemoving(blockPos);
        }
    }
    
    public DemoWorldManager(final World world) {
        super(world);
    }
    
    @Override
    public boolean tryUseItem(final EntityPlayer entityPlayer, final World world, final ItemStack itemStack) {
        if (this.demoTimeExpired) {
            this.sendDemoReminder();
            return false;
        }
        return super.tryUseItem(entityPlayer, world, itemStack);
    }
    
    @Override
    public boolean tryHarvestBlock(final BlockPos blockPos) {
        return !this.demoTimeExpired && super.tryHarvestBlock(blockPos);
    }
    
    @Override
    public boolean activateBlockOrUseItem(final EntityPlayer entityPlayer, final World world, final ItemStack itemStack, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (this.demoTimeExpired) {
            this.sendDemoReminder();
            return false;
        }
        return super.activateBlockOrUseItem(entityPlayer, world, itemStack, blockPos, enumFacing, n, n2, n3);
    }
    
    @Override
    public void updateBlockRemoving() {
        super.updateBlockRemoving();
        ++this.field_73102_f;
        final long totalWorldTime = this.theWorld.getTotalWorldTime();
        final long n = totalWorldTime / 24000L + 1L;
        if (!this.field_73105_c && this.field_73102_f > 20) {
            this.field_73105_c = true;
            this.thisPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(5, 0.0f));
        }
        this.demoTimeExpired = (totalWorldTime > 120500L);
        if (this.demoTimeExpired) {
            ++this.field_73104_e;
        }
        if (totalWorldTime % 24000L == 500L) {
            if (n <= 6L) {
                this.thisPlayerMP.addChatMessage(new ChatComponentTranslation("demo.day." + n, new Object[0]));
            }
        }
        else if (n == 1L) {
            if (totalWorldTime == 100L) {
                this.thisPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(5, 101.0f));
            }
            else if (totalWorldTime == 175L) {
                this.thisPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(5, 102.0f));
            }
            else if (totalWorldTime == 250L) {
                this.thisPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(5, 103.0f));
            }
        }
        else if (n == 5L && totalWorldTime % 24000L == 22000L) {
            this.thisPlayerMP.addChatMessage(new ChatComponentTranslation("demo.day.warning", new Object[0]));
        }
    }
    
    private void sendDemoReminder() {
        if (this.field_73104_e > 100) {
            this.thisPlayerMP.addChatMessage(new ChatComponentTranslation("demo.reminder", new Object[0]));
            this.field_73104_e = 0;
        }
    }
    
    @Override
    public void onBlockClicked(final BlockPos blockPos, final EnumFacing enumFacing) {
        if (this.demoTimeExpired) {
            this.sendDemoReminder();
        }
        else {
            super.onBlockClicked(blockPos, enumFacing);
        }
    }
}
