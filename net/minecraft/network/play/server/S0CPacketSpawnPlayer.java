package net.minecraft.network.play.server;

import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.io.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.item.*;

public class S0CPacketSpawnPlayer implements Packet
{
    private UUID playerId;
    private int currentItem;
    private int z;
    private List field_148958_j;
    private int x;
    private int y;
    private int entityId;
    private byte pitch;
    private byte yaw;
    private DataWatcher watcher;
    
    public UUID getPlayer() {
        return this.playerId;
    }
    
    public int getCurrentItemID() {
        return this.currentItem;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public int getX() {
        return this.x;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
        this.playerId = packetBuffer.readUuid();
        this.x = packetBuffer.readInt();
        this.y = packetBuffer.readInt();
        this.z = packetBuffer.readInt();
        this.yaw = packetBuffer.readByte();
        this.pitch = packetBuffer.readByte();
        this.currentItem = packetBuffer.readShort();
        this.field_148958_j = DataWatcher.readWatchedListFromPacketBuffer(packetBuffer);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
        packetBuffer.writeUuid(this.playerId);
        packetBuffer.writeInt(this.x);
        packetBuffer.writeInt(this.y);
        packetBuffer.writeInt(this.z);
        packetBuffer.writeByte(this.yaw);
        packetBuffer.writeByte(this.pitch);
        packetBuffer.writeShort(this.currentItem);
        this.watcher.writeTo(packetBuffer);
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleSpawnPlayer(this);
    }
    
    public byte getYaw() {
        return this.yaw;
    }
    
    public byte getPitch() {
        return this.pitch;
    }
    
    public int getY() {
        return this.y;
    }
    
    public S0CPacketSpawnPlayer(final EntityPlayer entityPlayer) {
        this.entityId = entityPlayer.getEntityId();
        this.playerId = entityPlayer.getGameProfile().getId();
        this.x = MathHelper.floor_double(entityPlayer.posX * 32.0);
        this.y = MathHelper.floor_double(entityPlayer.posY * 32.0);
        this.z = MathHelper.floor_double(entityPlayer.posZ * 32.0);
        this.yaw = (byte)(entityPlayer.rotationYaw * 256.0f / 360.0f);
        this.pitch = (byte)(entityPlayer.rotationPitch * 256.0f / 360.0f);
        final ItemStack currentItem = entityPlayer.inventory.getCurrentItem();
        this.currentItem = ((currentItem == null) ? 0 : Item.getIdFromItem(currentItem.getItem()));
        this.watcher = entityPlayer.getDataWatcher();
    }
    
    public int getEntityID() {
        return this.entityId;
    }
    
    public int getZ() {
        return this.z;
    }
    
    public List func_148944_c() {
        if (this.field_148958_j == null) {
            this.field_148958_j = this.watcher.getAllWatched();
        }
        return this.field_148958_j;
    }
    
    public S0CPacketSpawnPlayer() {
    }
}
