package net.minecraft.network.play.server;

import net.minecraft.item.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S04PacketEntityEquipment implements Packet
{
    private int equipmentSlot;
    private ItemStack itemStack;
    private int entityID;
    
    public S04PacketEntityEquipment(final int entityID, final int equipmentSlot, final ItemStack itemStack) {
        this.entityID = entityID;
        this.equipmentSlot = equipmentSlot;
        this.itemStack = ((itemStack == null) ? null : itemStack.copy());
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityID = packetBuffer.readVarIntFromBuffer();
        this.equipmentSlot = packetBuffer.readShort();
        this.itemStack = packetBuffer.readItemStackFromBuffer();
    }
    
    public S04PacketEntityEquipment() {
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleEntityEquipment(this);
    }
    
    public int getEntityID() {
        return this.entityID;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityID);
        packetBuffer.writeShort(this.equipmentSlot);
        packetBuffer.writeItemStackToBuffer(this.itemStack);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public int getEquipmentSlot() {
        return this.equipmentSlot;
    }
    
    public ItemStack getItemStack() {
        return this.itemStack;
    }
}
