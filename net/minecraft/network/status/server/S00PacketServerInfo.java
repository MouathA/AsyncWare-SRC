package net.minecraft.network.status.server;

import java.lang.reflect.*;
import net.minecraft.util.*;
import com.google.gson.*;
import java.io.*;
import net.minecraft.network.*;
import net.minecraft.network.status.*;

public class S00PacketServerInfo implements Packet
{
    private static final Gson GSON;
    private ServerStatusResponse response;
    
    static {
        GSON = new GsonBuilder().registerTypeAdapter((Type)ServerStatusResponse.MinecraftProtocolVersionIdentifier.class, (Object)new ServerStatusResponse.MinecraftProtocolVersionIdentifier.Serializer()).registerTypeAdapter((Type)ServerStatusResponse.PlayerCountData.class, (Object)new ServerStatusResponse.PlayerCountData.Serializer()).registerTypeAdapter((Type)ServerStatusResponse.class, (Object)new ServerStatusResponse.Serializer()).registerTypeHierarchyAdapter((Class)IChatComponent.class, (Object)new IChatComponent.Serializer()).registerTypeHierarchyAdapter((Class)ChatStyle.class, (Object)new ChatStyle.Serializer()).registerTypeAdapterFactory((TypeAdapterFactory)new EnumTypeAdapterFactory()).create();
    }
    
    public ServerStatusResponse getResponse() {
        return this.response;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.response = (ServerStatusResponse)S00PacketServerInfo.GSON.fromJson(packetBuffer.readStringFromBuffer(32767), (Class)ServerStatusResponse.class);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(S00PacketServerInfo.GSON.toJson((Object)this.response));
    }
    
    public S00PacketServerInfo() {
    }
    
    public S00PacketServerInfo(final ServerStatusResponse response) {
        this.response = response;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerStatusClient)netHandler);
    }
    
    public void processPacket(final INetHandlerStatusClient netHandlerStatusClient) {
        netHandlerStatusClient.handleServerInfo(this);
    }
}
