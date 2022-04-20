package net.minecraft.network;

public enum EnumPacketDirection
{
    private static final EnumPacketDirection[] $VALUES;
    
    CLIENTBOUND("CLIENTBOUND", 1), 
    SERVERBOUND("SERVERBOUND", 0);
    
    static {
        $VALUES = new EnumPacketDirection[] { EnumPacketDirection.SERVERBOUND, EnumPacketDirection.CLIENTBOUND };
    }
    
    private EnumPacketDirection(final String s, final int n) {
    }
}
