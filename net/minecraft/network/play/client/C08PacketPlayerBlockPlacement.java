package net.minecraft.network.play.client;

import net.minecraft.util.*;
import net.minecraft.item.*;
import java.io.*;
import net.minecraft.network.*;
import net.minecraft.network.play.*;

public class C08PacketPlayerBlockPlacement implements Packet
{
    private float facingX;
    private BlockPos position;
    private ItemStack stack;
    private int placedBlockDirection;
    private float facingY;
    private float facingZ;
    private static final BlockPos field_179726_a;
    
    public C08PacketPlayerBlockPlacement(final ItemStack itemStack) {
        this(C08PacketPlayerBlockPlacement.field_179726_a, 255, itemStack, 0.0f, 0.0f, 0.0f);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.position = packetBuffer.readBlockPos();
        this.placedBlockDirection = packetBuffer.readUnsignedByte();
        this.stack = packetBuffer.readItemStackFromBuffer();
        this.facingX = packetBuffer.readUnsignedByte() / 16.0f;
        this.facingY = packetBuffer.readUnsignedByte() / 16.0f;
        this.facingZ = packetBuffer.readUnsignedByte() / 16.0f;
    }
    
    public float getPlacedBlockOffsetY() {
        return this.facingY;
    }
    
    public BlockPos getPosition() {
        return this.position;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    public ItemStack getStack() {
        return this.stack;
    }
    
    public float getPlacedBlockOffsetX() {
        return this.facingX;
    }
    
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processPlayerBlockPlacement(this);
    }
    
    static {
        field_179726_a = new BlockPos(-1, -1, -1);
    }
    
    public C08PacketPlayerBlockPlacement() {
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.position);
        packetBuffer.writeByte(this.placedBlockDirection);
        packetBuffer.writeItemStackToBuffer(this.stack);
        packetBuffer.writeByte((int)(this.facingX * 16.0f));
        packetBuffer.writeByte((int)(this.facingY * 16.0f));
        packetBuffer.writeByte((int)(this.facingZ * 16.0f));
    }
    
    public float getPlacedBlockOffsetZ() {
        return this.facingZ;
    }
    
    public C08PacketPlayerBlockPlacement(final BlockPos position, final int placedBlockDirection, final ItemStack itemStack, final float facingX, final float facingY, final float facingZ) {
        this.position = position;
        this.placedBlockDirection = placedBlockDirection;
        this.stack = ((itemStack != null) ? itemStack.copy() : null);
        this.facingX = facingX;
        this.facingY = facingY;
        this.facingZ = facingZ;
    }
    
    public int getPlacedBlockDirection() {
        return this.placedBlockDirection;
    }
}
