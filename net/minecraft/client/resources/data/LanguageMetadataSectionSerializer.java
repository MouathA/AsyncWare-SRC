package net.minecraft.client.resources.data;

import java.lang.reflect.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import net.minecraft.client.resources.*;
import com.google.gson.*;
import java.util.*;

public class LanguageMetadataSectionSerializer extends BaseMetadataSectionSerializer
{
    @Override
    public String getSectionName() {
        return "language";
    }
    
    public LanguageMetadataSection deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final JsonObject asJsonObject = jsonElement.getAsJsonObject();
        final HashSet hashSet = Sets.newHashSet();
        for (final Map.Entry<String, V> entry : asJsonObject.entrySet()) {
            final String s = entry.getKey();
            final JsonObject jsonObject = JsonUtils.getJsonObject((JsonElement)entry.getValue(), "language");
            final String string = JsonUtils.getString(jsonObject, "region");
            final String string2 = JsonUtils.getString(jsonObject, "name");
            final boolean boolean1 = JsonUtils.getBoolean(jsonObject, "bidirectional", false);
            if (string.isEmpty()) {
                throw new JsonParseException("Invalid language->'" + s + "'->region: empty value");
            }
            if (string2.isEmpty()) {
                throw new JsonParseException("Invalid language->'" + s + "'->name: empty value");
            }
            if (!hashSet.add(new Language(s, string, string2, boolean1))) {
                throw new JsonParseException("Duplicate language->'" + s + "' defined");
            }
        }
        return new LanguageMetadataSection(hashSet);
    }
    
    public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return this.deserialize(jsonElement, type, jsonDeserializationContext);
    }
}
