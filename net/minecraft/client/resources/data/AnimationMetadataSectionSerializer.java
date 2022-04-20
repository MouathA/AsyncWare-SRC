package net.minecraft.client.resources.data;

import java.lang.reflect.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import org.apache.commons.lang3.*;
import java.util.*;
import com.google.gson.*;

public class AnimationMetadataSectionSerializer extends BaseMetadataSectionSerializer implements JsonSerializer
{
    public String getSectionName() {
        return "animation";
    }
    
    public JsonElement serialize(final AnimationMetadataSection animationMetadataSection, final Type type, final JsonSerializationContext jsonSerializationContext) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("frametime", (Number)animationMetadataSection.getFrameTime());
        if (animationMetadataSection.getFrameWidth() != -1) {
            jsonObject.addProperty("width", (Number)animationMetadataSection.getFrameWidth());
        }
        if (animationMetadataSection.getFrameHeight() != -1) {
            jsonObject.addProperty("height", (Number)animationMetadataSection.getFrameHeight());
        }
        if (animationMetadataSection.getFrameCount() > 0) {
            final JsonArray jsonArray = new JsonArray();
            while (0 < animationMetadataSection.getFrameCount()) {
                if (animationMetadataSection.frameHasTime(0)) {
                    final JsonObject jsonObject2 = new JsonObject();
                    jsonObject2.addProperty("index", (Number)animationMetadataSection.getFrameIndex(0));
                    jsonObject2.addProperty("time", (Number)animationMetadataSection.getFrameTimeSingle(0));
                    jsonArray.add((JsonElement)jsonObject2);
                }
                else {
                    jsonArray.add((JsonElement)new JsonPrimitive((Number)animationMetadataSection.getFrameIndex(0)));
                }
                int n = 0;
                ++n;
            }
            jsonObject.add("frames", (JsonElement)jsonArray);
        }
        return (JsonElement)jsonObject;
    }
    
    public AnimationMetadataSection deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final ArrayList arrayList = Lists.newArrayList();
        final JsonObject jsonObject = JsonUtils.getJsonObject(jsonElement, "metadata section");
        final int int1 = JsonUtils.getInt(jsonObject, "frametime", 1);
        if (int1 != 1) {
            Validate.inclusiveBetween(1L, 2147483647L, (long)int1, "Invalid default frame time");
        }
        if (jsonObject.has("frames")) {
            final JsonArray jsonArray = JsonUtils.getJsonArray(jsonObject, "frames");
            while (0 < jsonArray.size()) {
                final AnimationFrame animationFrame = this.parseAnimationFrame(0, jsonArray.get(0));
                if (animationFrame != null) {
                    arrayList.add(animationFrame);
                }
                int int2 = 0;
                ++int2;
            }
        }
        final int int3 = JsonUtils.getInt(jsonObject, "width", -1);
        int int2 = JsonUtils.getInt(jsonObject, "height", -1);
        if (int3 != -1) {
            Validate.inclusiveBetween(1L, 2147483647L, (long)int3, "Invalid width");
        }
        Validate.inclusiveBetween(1L, 2147483647L, (long)0, "Invalid height");
        return new AnimationMetadataSection(arrayList, int3, 0, int1, JsonUtils.getBoolean(jsonObject, "interpolate", false));
    }
    
    public JsonElement serialize(final Object o, final Type type, final JsonSerializationContext jsonSerializationContext) {
        return this.serialize((AnimationMetadataSection)o, type, jsonSerializationContext);
    }
    
    public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return this.deserialize(jsonElement, type, jsonDeserializationContext);
    }
    
    private AnimationFrame parseAnimationFrame(final int n, final JsonElement jsonElement) {
        if (jsonElement.isJsonPrimitive()) {
            return new AnimationFrame(JsonUtils.getInt(jsonElement, "frames[" + n + "]"));
        }
        if (jsonElement.isJsonObject()) {
            final JsonObject jsonObject = JsonUtils.getJsonObject(jsonElement, "frames[" + n + "]");
            final int int1 = JsonUtils.getInt(jsonObject, "time", -1);
            if (jsonObject.has("time")) {
                Validate.inclusiveBetween(1L, 2147483647L, (long)int1, "Invalid frame time");
            }
            final int int2 = JsonUtils.getInt(jsonObject, "index");
            Validate.inclusiveBetween(0L, 2147483647L, (long)int2, "Invalid frame index");
            return new AnimationFrame(int2, int1);
        }
        return null;
    }
}
