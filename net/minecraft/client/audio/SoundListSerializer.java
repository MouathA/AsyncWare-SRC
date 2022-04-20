package net.minecraft.client.audio;

import java.lang.reflect.*;
import net.minecraft.util.*;
import org.apache.commons.lang3.*;
import com.google.gson.*;

public class SoundListSerializer implements JsonDeserializer
{
    public SoundList deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final JsonObject jsonObject = JsonUtils.getJsonObject(jsonElement, "entry");
        final SoundList list = new SoundList();
        list.setReplaceExisting(JsonUtils.getBoolean(jsonObject, "replace", false));
        final SoundCategory category = SoundCategory.getCategory(JsonUtils.getString(jsonObject, "category", SoundCategory.MASTER.getCategoryName()));
        list.setSoundCategory(category);
        Validate.notNull((Object)category, "Invalid category", new Object[0]);
        if (jsonObject.has("sounds")) {
            final JsonArray jsonArray = JsonUtils.getJsonArray(jsonObject, "sounds");
            while (0 < jsonArray.size()) {
                final JsonElement value = jsonArray.get(0);
                final SoundList.SoundEntry soundEntry = new SoundList.SoundEntry();
                if (JsonUtils.isString(value)) {
                    soundEntry.setSoundEntryName(JsonUtils.getString(value, "sound"));
                }
                else {
                    final JsonObject jsonObject2 = JsonUtils.getJsonObject(value, "sound");
                    soundEntry.setSoundEntryName(JsonUtils.getString(jsonObject2, "name"));
                    if (jsonObject2.has("type")) {
                        final SoundList.SoundEntry.Type type2 = SoundList.SoundEntry.Type.getType(JsonUtils.getString(jsonObject2, "type"));
                        Validate.notNull((Object)type2, "Invalid type", new Object[0]);
                        soundEntry.setSoundEntryType(type2);
                    }
                    if (jsonObject2.has("volume")) {
                        final float float1 = JsonUtils.getFloat(jsonObject2, "volume");
                        Validate.isTrue(float1 > 0.0f, "Invalid volume", new Object[0]);
                        soundEntry.setSoundEntryVolume(float1);
                    }
                    if (jsonObject2.has("pitch")) {
                        final float float2 = JsonUtils.getFloat(jsonObject2, "pitch");
                        Validate.isTrue(float2 > 0.0f, "Invalid pitch", new Object[0]);
                        soundEntry.setSoundEntryPitch(float2);
                    }
                    if (jsonObject2.has("weight")) {
                        final int int1 = JsonUtils.getInt(jsonObject2, "weight");
                        Validate.isTrue(int1 > 0, "Invalid weight", new Object[0]);
                        soundEntry.setSoundEntryWeight(int1);
                    }
                    if (jsonObject2.has("stream")) {
                        soundEntry.setStreaming(JsonUtils.getBoolean(jsonObject2, "stream"));
                    }
                }
                list.getSoundList().add(soundEntry);
                int n = 0;
                ++n;
            }
        }
        return list;
    }
    
    public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return this.deserialize(jsonElement, type, jsonDeserializationContext);
    }
}
