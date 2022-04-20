package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.network.*;
import com.google.common.collect.*;
import java.io.*;
import java.util.*;
import net.minecraft.util.*;

public class S27PacketExplosion implements Packet
{
    private float strength;
    private float field_149159_h;
    private double posZ;
    private List affectedBlockPositions;
    private float field_149152_f;
    private double posX;
    private float field_149153_g;
    private double posY;
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.posX = packetBuffer.readFloat();
        this.posY = packetBuffer.readFloat();
        this.posZ = packetBuffer.readFloat();
        this.strength = packetBuffer.readFloat();
        final int int1 = packetBuffer.readInt();
        this.affectedBlockPositions = Lists.newArrayListWithCapacity(int1);
        final int n = (int)this.posX;
        final int n2 = (int)this.posY;
        final int n3 = (int)this.posZ;
        while (0 < int1) {
            this.affectedBlockPositions.add(new BlockPos(packetBuffer.readByte() + n, packetBuffer.readByte() + n2, packetBuffer.readByte() + n3));
            int n4 = 0;
            ++n4;
        }
        this.field_149152_f = packetBuffer.readFloat();
        this.field_149153_g = packetBuffer.readFloat();
        this.field_149159_h = packetBuffer.readFloat();
    }
    
    public double getZ() {
        return this.posZ;
    }
    
    public float func_149147_e() {
        return this.field_149159_h;
    }
    
    public double getY() {
        return this.posY;
    }
    
    public List getAffectedBlockPositions() {
        return this.affectedBlockPositions;
    }
    
    public double getX() {
        return this.posX;
    }
    
    public S27PacketExplosion() {
    }
    
    public float func_149149_c() {
        return this.field_149152_f;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeFloat((float)this.posX);
        packetBuffer.writeFloat((float)this.posY);
        packetBuffer.writeFloat((float)this.posZ);
        packetBuffer.writeFloat(this.strength);
        packetBuffer.writeInt(this.affectedBlockPositions.size());
        final int n = (int)this.posX;
        final int n2 = (int)this.posY;
        final int n3 = (int)this.posZ;
        for (final BlockPos blockPos : this.affectedBlockPositions) {
            final int n4 = blockPos.getX() - n;
            final int n5 = blockPos.getY() - n2;
            final int n6 = blockPos.getZ() - n3;
            packetBuffer.writeByte(n4);
            packetBuffer.writeByte(n5);
            packetBuffer.writeByte(n6);
        }
        packetBuffer.writeFloat(this.field_149152_f);
        packetBuffer.writeFloat(this.field_149153_g);
        packetBuffer.writeFloat(this.field_149159_h);
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleExplosion(this);
    }
    
    public S27PacketExplosion(final double posX, final double posY, final double posZ, final float strength, final List list, final Vec3 vec3) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.strength = strength;
        this.affectedBlockPositions = Lists.newArrayList((Iterable)list);
        if (vec3 != null) {
            this.field_149152_f = (float)vec3.xCoord;
            this.field_149153_g = (float)vec3.yCoord;
            this.field_149159_h = (float)vec3.zCoord;
        }
    }
    
    public float getStrength() {
        return this.strength;
    }
    
    public float func_149144_d() {
        return this.field_149153_g;
    }
}
