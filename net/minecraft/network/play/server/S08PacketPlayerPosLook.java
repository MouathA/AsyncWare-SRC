package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.io.*;
import java.util.*;

public class S08PacketPlayerPosLook implements Packet
{
    private double y;
    private Set field_179835_f;
    private double x;
    private double z;
    private float yaw;
    private float pitch;
    
    public float getPitch() {
        return this.pitch;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public Set func_179834_f() {
        return this.field_179835_f;
    }
    
    public double getY() {
        return this.y;
    }
    
    public void setPitch(final float pitch) {
        this.pitch = pitch;
    }
    
    public double getX() {
        return this.x;
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeDouble(this.x);
        packetBuffer.writeDouble(this.y);
        packetBuffer.writeDouble(this.z);
        packetBuffer.writeFloat(this.yaw);
        packetBuffer.writeFloat(this.pitch);
        packetBuffer.writeByte(EnumFlags.func_180056_a(this.field_179835_f));
    }
    
    public void setYaw(final float yaw) {
        this.yaw = yaw;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.x = packetBuffer.readDouble();
        this.y = packetBuffer.readDouble();
        this.z = packetBuffer.readDouble();
        this.yaw = packetBuffer.readFloat();
        this.pitch = packetBuffer.readFloat();
        this.field_179835_f = EnumFlags.func_180053_a(packetBuffer.readUnsignedByte());
    }
    
    public S08PacketPlayerPosLook() {
    }
    
    public S08PacketPlayerPosLook(final double x, final double y, final double z, final float yaw, final float pitch, final Set field_179835_f) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.field_179835_f = field_179835_f;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handlePlayerPosLook(this);
    }
    
    public enum EnumFlags
    {
        private int field_180058_f;
        
        Y("Y", 1, 1), 
        Y_ROT("Y_ROT", 3, 3), 
        X("X", 0, 0), 
        Z("Z", 2, 2);
        
        private static final EnumFlags[] $VALUES;
        
        X_ROT("X_ROT", 4, 4);
        
        public static Set func_180053_a(final int n) {
            final EnumSet<EnumFlags> none = EnumSet.noneOf(EnumFlags.class);
            final EnumFlags[] values = values();
            while (0 < values.length) {
                final EnumFlags enumFlags = values[0];
                if (enumFlags == n) {
                    none.add(enumFlags);
                }
                int n2 = 0;
                ++n2;
            }
            return none;
        }
        
        public static int func_180056_a(final Set set) {
            final Iterator<EnumFlags> iterator = set.iterator();
            while (iterator.hasNext()) {
                final int n = 0x0 | iterator.next().func_180055_a();
            }
            return 0;
        }
        
        private EnumFlags(final String s, final int n, final int field_180058_f) {
            this.field_180058_f = field_180058_f;
        }
        
        private int func_180055_a() {
            return 1 << this.field_180058_f;
        }
        
        static {
            $VALUES = new EnumFlags[] { EnumFlags.X, EnumFlags.Y, EnumFlags.Z, EnumFlags.Y_ROT, EnumFlags.X_ROT };
        }
    }
}
