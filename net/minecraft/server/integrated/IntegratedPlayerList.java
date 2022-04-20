package net.minecraft.server.integrated;

import net.minecraft.server.management.*;
import net.minecraft.nbt.*;
import net.minecraft.server.*;
import net.minecraft.entity.player.*;
import java.net.*;
import com.mojang.authlib.*;

public class IntegratedPlayerList extends ServerConfigurationManager
{
    private NBTTagCompound hostPlayerData;
    
    @Override
    public NBTTagCompound getHostPlayerData() {
        return this.hostPlayerData;
    }
    
    public IntegratedPlayerList(final IntegratedServer integratedServer) {
        super(integratedServer);
        this.setViewDistance(10);
    }
    
    @Override
    protected void writePlayerData(final EntityPlayerMP entityPlayerMP) {
        if (entityPlayerMP.getName().equals(this.getServerInstance().getServerOwner())) {
            entityPlayerMP.writeToNBT(this.hostPlayerData = new NBTTagCompound());
        }
        super.writePlayerData(entityPlayerMP);
    }
    
    @Override
    public IntegratedServer getServerInstance() {
        return (IntegratedServer)super.getServerInstance();
    }
    
    @Override
    public String allowUserToConnect(final SocketAddress socketAddress, final GameProfile gameProfile) {
        return (gameProfile.getName().equalsIgnoreCase(this.getServerInstance().getServerOwner()) && this.getPlayerByUsername(gameProfile.getName()) != null) ? "That name is already taken." : super.allowUserToConnect(socketAddress, gameProfile);
    }
    
    @Override
    public MinecraftServer getServerInstance() {
        return this.getServerInstance();
    }
}
