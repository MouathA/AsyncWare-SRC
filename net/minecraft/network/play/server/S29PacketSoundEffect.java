package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import org.apache.commons.lang3.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.*;

public class S29PacketSoundEffect implements Packet
{
    private int soundPitch;
    private int posX;
    private int posY;
    private float soundVolume;
    private String soundName;
    private int posZ;
    
    public float getPitch() {
        return this.soundPitch / 63.0f;
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleSoundEffect(this);
    }
    
    public double getX() {
        return this.posX / 8.0f;
    }
    
    public S29PacketSoundEffect(final String soundName, final double n, final double n2, final double n3, final float soundVolume, float clamp_float) {
        this.posY = Integer.MAX_VALUE;
        Validate.notNull((Object)soundName, "name", new Object[0]);
        this.soundName = soundName;
        this.posX = (int)(n * 8.0);
        this.posY = (int)(n2 * 8.0);
        this.posZ = (int)(n3 * 8.0);
        this.soundVolume = soundVolume;
        this.soundPitch = (int)(clamp_float * 63.0f);
        clamp_float = MathHelper.clamp_float(clamp_float, 0.0f, 255.0f);
    }
    
    public String getSoundName() {
        return this.soundName;
    }
    
    public float getVolume() {
        return this.soundVolume;
    }
    
    public S29PacketSoundEffect() {
        this.posY = Integer.MAX_VALUE;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.soundName);
        packetBuffer.writeInt(this.posX);
        packetBuffer.writeInt(this.posY);
        packetBuffer.writeInt(this.posZ);
        packetBuffer.writeFloat(this.soundVolume);
        packetBuffer.writeByte(this.soundPitch);
    }
    
    public double getZ() {
        return this.posZ / 8.0f;
    }
    
    public double getY() {
        return this.posY / 8.0f;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.soundName = packetBuffer.readStringFromBuffer(256);
        this.posX = packetBuffer.readInt();
        this.posY = packetBuffer.readInt();
        this.posZ = packetBuffer.readInt();
        this.soundVolume = packetBuffer.readFloat();
        this.soundPitch = packetBuffer.readUnsignedByte();
    }
}
