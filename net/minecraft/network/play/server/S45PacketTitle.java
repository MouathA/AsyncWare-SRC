package net.minecraft.network.play.server;

import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.*;
import net.minecraft.network.play.*;

public class S45PacketTitle implements Packet
{
    private IChatComponent message;
    private int fadeInTime;
    private Type type;
    private int fadeOutTime;
    private int displayTime;
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.type = (Type)packetBuffer.readEnumValue(Type.class);
        if (this.type == Type.TITLE || this.type == Type.SUBTITLE) {
            this.message = packetBuffer.readChatComponent();
        }
        if (this.type == Type.TIMES) {
            this.fadeInTime = packetBuffer.readInt();
            this.displayTime = packetBuffer.readInt();
            this.fadeOutTime = packetBuffer.readInt();
        }
    }
    
    public S45PacketTitle(final Type type, final IChatComponent message, final int fadeInTime, final int displayTime, final int fadeOutTime) {
        this.type = type;
        this.message = message;
        this.fadeInTime = fadeInTime;
        this.displayTime = displayTime;
        this.fadeOutTime = fadeOutTime;
    }
    
    public int getFadeOutTime() {
        return this.fadeOutTime;
    }
    
    public S45PacketTitle(final int n, final int n2, final int n3) {
        this(Type.TIMES, null, n, n2, n3);
    }
    
    public S45PacketTitle() {
    }
    
    public IChatComponent getMessage() {
        return this.message;
    }
    
    public Type getType() {
        return this.type;
    }
    
    public S45PacketTitle(final Type type, final IChatComponent chatComponent) {
        this(type, chatComponent, -1, -1, -1);
    }
    
    public int getDisplayTime() {
        return this.displayTime;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeEnumValue(this.type);
        if (this.type == Type.TITLE || this.type == Type.SUBTITLE) {
            packetBuffer.writeChatComponent(this.message);
        }
        if (this.type == Type.TIMES) {
            packetBuffer.writeInt(this.fadeInTime);
            packetBuffer.writeInt(this.displayTime);
            packetBuffer.writeInt(this.fadeOutTime);
        }
    }
    
    public int getFadeInTime() {
        return this.fadeInTime;
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleTitle(this);
    }
    
    public enum Type
    {
        TITLE("TITLE", 0), 
        TIMES("TIMES", 2), 
        SUBTITLE("SUBTITLE", 1), 
        CLEAR("CLEAR", 3);
        
        private static final Type[] $VALUES;
        
        RESET("RESET", 4);
        
        static {
            $VALUES = new Type[] { Type.TITLE, Type.SUBTITLE, Type.TIMES, Type.CLEAR, Type.RESET };
        }
        
        public static Type byName(final String s) {
            final Type[] values = values();
            while (0 < values.length) {
                final Type type = values[0];
                if (type.name().equalsIgnoreCase(s)) {
                    return type;
                }
                int n = 0;
                ++n;
            }
            return Type.TITLE;
        }
        
        public static String[] getNames() {
            final String[] array = new String[values().length];
            final Type[] values = values();
            while (0 < values.length) {
                final Type type = values[0];
                final String[] array2 = array;
                final int n = 0;
                int n2 = 0;
                ++n2;
                array2[n] = type.name().toLowerCase();
                int n3 = 0;
                ++n3;
            }
            return array;
        }
        
        private Type(final String s, final int n) {
        }
    }
}
