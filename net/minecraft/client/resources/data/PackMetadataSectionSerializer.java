package net.minecraft.client.resources.data;

import java.lang.reflect.*;
import com.google.gson.*;
import net.minecraft.util.*;

public class PackMetadataSectionSerializer extends BaseMetadataSectionSerializer implements JsonSerializer
{
    public String getSectionName() {
        return "pack";
    }
    
    public JsonElement serialize(final Object o, final Type type, final JsonSerializationContext jsonSerializationContext) {
        return this.serialize((PackMetadataSection)o, type, jsonSerializationContext);
    }
    
    public JsonElement serialize(final PackMetadataSection packMetadataSection, final Type type, final JsonSerializationContext jsonSerializationContext) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("pack_format", (Number)packMetadataSection.getPackFormat());
        jsonObject.add("description", jsonSerializationContext.serialize((Object)packMetadataSection.getPackDescription()));
        return (JsonElement)jsonObject;
    }
    
    public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return this.deserialize(jsonElement, type, jsonDeserializationContext);
    }
    
    public PackMetadataSection deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final JsonObject asJsonObject = jsonElement.getAsJsonObject();
        final IChatComponent chatComponent = (IChatComponent)jsonDeserializationContext.deserialize(asJsonObject.get("description"), (Type)IChatComponent.class);
        if (chatComponent == null) {
            throw new JsonParseException("Invalid/missing description!");
        }
        return new PackMetadataSection(chatComponent, JsonUtils.getInt(asJsonObject, "pack_format"));
    }
}
