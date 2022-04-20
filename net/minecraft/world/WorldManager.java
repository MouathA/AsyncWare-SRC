package net.minecraft.world;

import net.minecraft.server.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.server.*;

public class WorldManager implements IWorldAccess
{
    private WorldServer theWorldServer;
    private MinecraftServer mcServer;
    
    @Override
    public void notifyLightSet(final BlockPos blockPos) {
    }
    
    @Override
    public void spawnParticle(final int n, final boolean b, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
    }
    
    @Override
    public void onEntityAdded(final Entity entity) {
        this.theWorldServer.getEntityTracker().trackEntity(entity);
    }
    
    @Override
    public void sendBlockBreakProgress(final int n, final BlockPos blockPos, final int n2) {
        for (final EntityPlayerMP entityPlayerMP : this.mcServer.getConfigurationManager().func_181057_v()) {
            if (entityPlayerMP != null && entityPlayerMP.worldObj == this.theWorldServer && entityPlayerMP.getEntityId() != n) {
                final double n3 = blockPos.getX() - entityPlayerMP.posX;
                final double n4 = blockPos.getY() - entityPlayerMP.posY;
                final double n5 = blockPos.getZ() - entityPlayerMP.posZ;
                if (n3 * n3 + n4 * n4 + n5 * n5 >= 1024.0) {
                    continue;
                }
                entityPlayerMP.playerNetServerHandler.sendPacket(new S25PacketBlockBreakAnim(n, blockPos, n2));
            }
        }
    }
    
    @Override
    public void broadcastSound(final int n, final BlockPos blockPos, final int n2) {
        this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S28PacketEffect(n, blockPos, n2, true));
    }
    
    @Override
    public void playSoundToNearExcept(final EntityPlayer entityPlayer, final String s, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.mcServer.getConfigurationManager().sendToAllNearExcept(entityPlayer, n, n2, n3, (n4 > 1.0f) ? ((double)(16.0f * n4)) : 16.0, this.theWorldServer.provider.getDimensionId(), new S29PacketSoundEffect(s, n, n2, n3, n4, n5));
    }
    
    @Override
    public void playSound(final String s, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.mcServer.getConfigurationManager().sendToAllNear(n, n2, n3, (n4 > 1.0f) ? ((double)(16.0f * n4)) : 16.0, this.theWorldServer.provider.getDimensionId(), new S29PacketSoundEffect(s, n, n2, n3, n4, n5));
    }
    
    @Override
    public void markBlockRangeForRenderUpdate(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
    }
    
    public WorldManager(final MinecraftServer mcServer, final WorldServer theWorldServer) {
        this.mcServer = mcServer;
        this.theWorldServer = theWorldServer;
    }
    
    @Override
    public void playAuxSFX(final EntityPlayer entityPlayer, final int n, final BlockPos blockPos, final int n2) {
        this.mcServer.getConfigurationManager().sendToAllNearExcept(entityPlayer, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 64.0, this.theWorldServer.provider.getDimensionId(), new S28PacketEffect(n, blockPos, n2, false));
    }
    
    @Override
    public void playRecord(final String s, final BlockPos blockPos) {
    }
    
    @Override
    public void markBlockForUpdate(final BlockPos blockPos) {
        this.theWorldServer.getPlayerManager().markBlockForUpdate(blockPos);
    }
    
    @Override
    public void onEntityRemoved(final Entity entity) {
        this.theWorldServer.getEntityTracker().untrackEntity(entity);
        this.theWorldServer.getScoreboard().func_181140_a(entity);
    }
}
