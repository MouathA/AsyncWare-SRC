package net.minecraft.client.renderer.block.model;

import org.lwjgl.util.vector.*;
import java.lang.reflect.*;
import net.minecraft.util.*;
import com.google.gson.*;

public class ItemTransformVec3f
{
    public static final ItemTransformVec3f DEFAULT;
    public final Vector3f translation;
    public final Vector3f scale;
    public final Vector3f rotation;
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        final ItemTransformVec3f itemTransformVec3f = (ItemTransformVec3f)o;
        return this.rotation.equals((Object)itemTransformVec3f.rotation) && this.scale.equals((Object)itemTransformVec3f.scale) && this.translation.equals((Object)itemTransformVec3f.translation);
    }
    
    static {
        DEFAULT = new ItemTransformVec3f(new Vector3f(), new Vector3f(), new Vector3f(1.0f, 1.0f, 1.0f));
    }
    
    public ItemTransformVec3f(final Vector3f vector3f, final Vector3f vector3f2, final Vector3f vector3f3) {
        this.rotation = new Vector3f((ReadableVector3f)vector3f);
        this.translation = new Vector3f((ReadableVector3f)vector3f2);
        this.scale = new Vector3f((ReadableVector3f)vector3f3);
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * this.rotation.hashCode() + this.translation.hashCode()) + this.scale.hashCode();
    }
    
    static class Deserializer implements JsonDeserializer
    {
        private static final Vector3f TRANSLATION_DEFAULT;
        private static final Vector3f SCALE_DEFAULT;
        private static final Vector3f ROTATION_DEFAULT;
        
        static {
            ROTATION_DEFAULT = new Vector3f(0.0f, 0.0f, 0.0f);
            TRANSLATION_DEFAULT = new Vector3f(0.0f, 0.0f, 0.0f);
            SCALE_DEFAULT = new Vector3f(1.0f, 1.0f, 1.0f);
        }
        
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
        
        public ItemTransformVec3f deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            final JsonObject asJsonObject = jsonElement.getAsJsonObject();
            final Vector3f vector3f = this.parseVector3f(asJsonObject, "rotation", Deserializer.ROTATION_DEFAULT);
            final Vector3f vector3f2 = this.parseVector3f(asJsonObject, "translation", Deserializer.TRANSLATION_DEFAULT);
            vector3f2.scale(0.0625f);
            vector3f2.x = MathHelper.clamp_float(vector3f2.x, -1.5f, 1.5f);
            vector3f2.y = MathHelper.clamp_float(vector3f2.y, -1.5f, 1.5f);
            vector3f2.z = MathHelper.clamp_float(vector3f2.z, -1.5f, 1.5f);
            final Vector3f vector3f3 = this.parseVector3f(asJsonObject, "scale", Deserializer.SCALE_DEFAULT);
            vector3f3.x = MathHelper.clamp_float(vector3f3.x, -4.0f, 4.0f);
            vector3f3.y = MathHelper.clamp_float(vector3f3.y, -4.0f, 4.0f);
            vector3f3.z = MathHelper.clamp_float(vector3f3.z, -4.0f, 4.0f);
            return new ItemTransformVec3f(vector3f, vector3f2, vector3f3);
        }
        
        private Vector3f parseVector3f(final JsonObject jsonObject, final String s, final Vector3f vector3f) {
            if (!jsonObject.has(s)) {
                return vector3f;
            }
            final JsonArray jsonArray = JsonUtils.getJsonArray(jsonObject, s);
            if (jsonArray.size() != 3) {
                throw new JsonParseException("Expected 3 " + s + " values, found: " + jsonArray.size());
            }
            final float[] array = new float[3];
            while (0 < array.length) {
                array[0] = JsonUtils.getFloat(jsonArray.get(0), s + "[" + 0 + "]");
                int n = 0;
                ++n;
            }
            return new Vector3f(array[0], array[1], array[2]);
        }
    }
}
