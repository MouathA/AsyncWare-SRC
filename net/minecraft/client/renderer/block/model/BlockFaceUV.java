package net.minecraft.client.renderer.block.model;

import net.minecraft.util.*;
import java.lang.reflect.*;
import com.google.gson.*;

public class BlockFaceUV
{
    public final int rotation;
    public float[] uvs;
    
    public int func_178345_c(final int n) {
        return (n + (4 - this.rotation / 90)) % 4;
    }
    
    public BlockFaceUV(final float[] uvs, final int rotation) {
        this.uvs = uvs;
        this.rotation = rotation;
    }
    
    private int func_178347_d(final int n) {
        return (n + this.rotation / 90) % 4;
    }
    
    public void setUvs(final float[] uvs) {
        if (this.uvs == null) {
            this.uvs = uvs;
        }
    }
    
    public float func_178348_a(final int n) {
        if (this.uvs == null) {
            throw new NullPointerException("uvs");
        }
        final int func_178347_d = this.func_178347_d(n);
        return (func_178347_d != 0 && func_178347_d != 1) ? this.uvs[2] : this.uvs[0];
    }
    
    public float func_178346_b(final int n) {
        if (this.uvs == null) {
            throw new NullPointerException("uvs");
        }
        final int func_178347_d = this.func_178347_d(n);
        return (func_178347_d != 0 && func_178347_d != 3) ? this.uvs[3] : this.uvs[1];
    }
    
    static class Deserializer implements JsonDeserializer
    {
        protected int parseRotation(final JsonObject jsonObject) {
            final int int1 = JsonUtils.getInt(jsonObject, "rotation", 0);
            if (int1 >= 0 && int1 % 90 == 0 && int1 / 90 <= 3) {
                return int1;
            }
            throw new JsonParseException("Invalid rotation " + int1 + " found, only 0/90/180/270 allowed");
        }
        
        private float[] parseUV(final JsonObject jsonObject) {
            if (!jsonObject.has("uv")) {
                return null;
            }
            final JsonArray jsonArray = JsonUtils.getJsonArray(jsonObject, "uv");
            if (jsonArray.size() != 4) {
                throw new JsonParseException("Expected 4 uv values, found: " + jsonArray.size());
            }
            final float[] array = new float[4];
            while (0 < array.length) {
                array[0] = JsonUtils.getFloat(jsonArray.get(0), "uv[" + 0 + "]");
                int n = 0;
                ++n;
            }
            return array;
        }
        
        public BlockFaceUV deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            final JsonObject asJsonObject = jsonElement.getAsJsonObject();
            return new BlockFaceUV(this.parseUV(asJsonObject), this.parseRotation(asJsonObject));
        }
        
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }
}
