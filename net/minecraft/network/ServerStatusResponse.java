package net.minecraft.network;

import com.mojang.authlib.*;
import java.lang.reflect.*;
import java.util.*;
import net.minecraft.util.*;
import com.google.gson.*;

public class ServerStatusResponse
{
    private MinecraftProtocolVersionIdentifier protocolVersion;
    private String favicon;
    private PlayerCountData playerCount;
    private IChatComponent serverMotd;
    
    public IChatComponent getServerDescription() {
        return this.serverMotd;
    }
    
    public void setFavicon(final String favicon) {
        this.favicon = favicon;
    }
    
    public PlayerCountData getPlayerCountData() {
        return this.playerCount;
    }
    
    public MinecraftProtocolVersionIdentifier getProtocolVersionInfo() {
        return this.protocolVersion;
    }
    
    public String getFavicon() {
        return this.favicon;
    }
    
    public void setProtocolVersionInfo(final MinecraftProtocolVersionIdentifier protocolVersion) {
        this.protocolVersion = protocolVersion;
    }
    
    public void setPlayerCountData(final PlayerCountData playerCount) {
        this.playerCount = playerCount;
    }
    
    public void setServerDescription(final IChatComponent serverMotd) {
        this.serverMotd = serverMotd;
    }
    
    public static class PlayerCountData
    {
        private GameProfile[] players;
        private final int onlinePlayerCount;
        private final int maxPlayers;
        
        public GameProfile[] getPlayers() {
            return this.players;
        }
        
        public int getMaxPlayers() {
            return this.maxPlayers;
        }
        
        public void setPlayers(final GameProfile[] players) {
            this.players = players;
        }
        
        public PlayerCountData(final int maxPlayers, final int onlinePlayerCount) {
            this.maxPlayers = maxPlayers;
            this.onlinePlayerCount = onlinePlayerCount;
        }
        
        public int getOnlinePlayerCount() {
            return this.onlinePlayerCount;
        }
        
        public static class Serializer implements JsonSerializer, JsonDeserializer
        {
            public JsonElement serialize(final PlayerCountData playerCountData, final Type type, final JsonSerializationContext jsonSerializationContext) {
                final JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("max", (Number)playerCountData.getMaxPlayers());
                jsonObject.addProperty("online", (Number)playerCountData.getOnlinePlayerCount());
                if (playerCountData.getPlayers() != null && playerCountData.getPlayers().length > 0) {
                    final JsonArray jsonArray = new JsonArray();
                    while (0 < playerCountData.getPlayers().length) {
                        final JsonObject jsonObject2 = new JsonObject();
                        final UUID id = playerCountData.getPlayers()[0].getId();
                        jsonObject2.addProperty("id", (id == null) ? "" : id.toString());
                        jsonObject2.addProperty("name", playerCountData.getPlayers()[0].getName());
                        jsonArray.add((JsonElement)jsonObject2);
                        int n = 0;
                        ++n;
                    }
                    jsonObject.add("sample", (JsonElement)jsonArray);
                }
                return (JsonElement)jsonObject;
            }
            
            public JsonElement serialize(final Object o, final Type type, final JsonSerializationContext jsonSerializationContext) {
                return this.serialize((PlayerCountData)o, type, jsonSerializationContext);
            }
            
            public PlayerCountData deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                final JsonObject jsonObject = JsonUtils.getJsonObject(jsonElement, "players");
                final PlayerCountData playerCountData = new PlayerCountData(JsonUtils.getInt(jsonObject, "max"), JsonUtils.getInt(jsonObject, "online"));
                if (JsonUtils.isJsonArray(jsonObject, "sample")) {
                    final JsonArray jsonArray = JsonUtils.getJsonArray(jsonObject, "sample");
                    if (jsonArray.size() > 0) {
                        final GameProfile[] players = new GameProfile[jsonArray.size()];
                        while (0 < players.length) {
                            final JsonObject jsonObject2 = JsonUtils.getJsonObject(jsonArray.get(0), "player[" + 0 + "]");
                            players[0] = new GameProfile(UUID.fromString(JsonUtils.getString(jsonObject2, "id")), JsonUtils.getString(jsonObject2, "name"));
                            int n = 0;
                            ++n;
                        }
                        playerCountData.setPlayers(players);
                    }
                }
                return playerCountData;
            }
            
            public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return this.deserialize(jsonElement, type, jsonDeserializationContext);
            }
        }
    }
    
    public static class Serializer implements JsonDeserializer, JsonSerializer
    {
        public ServerStatusResponse deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            final JsonObject jsonObject = JsonUtils.getJsonObject(jsonElement, "status");
            final ServerStatusResponse serverStatusResponse = new ServerStatusResponse();
            if (jsonObject.has("description")) {
                serverStatusResponse.setServerDescription((IChatComponent)jsonDeserializationContext.deserialize(jsonObject.get("description"), (Type)IChatComponent.class));
            }
            if (jsonObject.has("players")) {
                serverStatusResponse.setPlayerCountData((PlayerCountData)jsonDeserializationContext.deserialize(jsonObject.get("players"), (Type)PlayerCountData.class));
            }
            if (jsonObject.has("version")) {
                serverStatusResponse.setProtocolVersionInfo((MinecraftProtocolVersionIdentifier)jsonDeserializationContext.deserialize(jsonObject.get("version"), (Type)MinecraftProtocolVersionIdentifier.class));
            }
            if (jsonObject.has("favicon")) {
                serverStatusResponse.setFavicon(JsonUtils.getString(jsonObject, "favicon"));
            }
            return serverStatusResponse;
        }
        
        public JsonElement serialize(final Object o, final Type type, final JsonSerializationContext jsonSerializationContext) {
            return this.serialize((ServerStatusResponse)o, type, jsonSerializationContext);
        }
        
        public JsonElement serialize(final ServerStatusResponse serverStatusResponse, final Type type, final JsonSerializationContext jsonSerializationContext) {
            final JsonObject jsonObject = new JsonObject();
            if (serverStatusResponse.getServerDescription() != null) {
                jsonObject.add("description", jsonSerializationContext.serialize((Object)serverStatusResponse.getServerDescription()));
            }
            if (serverStatusResponse.getPlayerCountData() != null) {
                jsonObject.add("players", jsonSerializationContext.serialize((Object)serverStatusResponse.getPlayerCountData()));
            }
            if (serverStatusResponse.getProtocolVersionInfo() != null) {
                jsonObject.add("version", jsonSerializationContext.serialize((Object)serverStatusResponse.getProtocolVersionInfo()));
            }
            if (serverStatusResponse.getFavicon() != null) {
                jsonObject.addProperty("favicon", serverStatusResponse.getFavicon());
            }
            return (JsonElement)jsonObject;
        }
        
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }
    
    public static class MinecraftProtocolVersionIdentifier
    {
        private final int protocol;
        private final String name;
        
        public MinecraftProtocolVersionIdentifier(final String name, final int protocol) {
            this.name = name;
            this.protocol = protocol;
        }
        
        public String getName() {
            return this.name;
        }
        
        public int getProtocol() {
            return this.protocol;
        }
        
        public static class Serializer implements JsonSerializer, JsonDeserializer
        {
            public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return this.deserialize(jsonElement, type, jsonDeserializationContext);
            }
            
            public JsonElement serialize(final Object o, final Type type, final JsonSerializationContext jsonSerializationContext) {
                return this.serialize((MinecraftProtocolVersionIdentifier)o, type, jsonSerializationContext);
            }
            
            public JsonElement serialize(final MinecraftProtocolVersionIdentifier minecraftProtocolVersionIdentifier, final Type type, final JsonSerializationContext jsonSerializationContext) {
                final JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("name", minecraftProtocolVersionIdentifier.getName());
                jsonObject.addProperty("protocol", (Number)minecraftProtocolVersionIdentifier.getProtocol());
                return (JsonElement)jsonObject;
            }
            
            public MinecraftProtocolVersionIdentifier deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                final JsonObject jsonObject = JsonUtils.getJsonObject(jsonElement, "version");
                return new MinecraftProtocolVersionIdentifier(JsonUtils.getString(jsonObject, "name"), JsonUtils.getInt(jsonObject, "protocol"));
            }
        }
    }
}
