package net.minecraft.client.renderer.block.model;

import org.lwjgl.util.vector.*;
import java.lang.reflect.*;
import com.google.common.collect.*;
import java.util.*;
import com.google.gson.*;
import net.minecraft.util.*;

public class BlockPart
{
    public final Vector3f positionFrom;
    public final Map mapFaces;
    public final BlockPartRotation partRotation;
    public final Vector3f positionTo;
    public final boolean shade;
    
    public BlockPart(final Vector3f positionFrom, final Vector3f positionTo, final Map mapFaces, final BlockPartRotation partRotation, final boolean shade) {
        this.positionFrom = positionFrom;
        this.positionTo = positionTo;
        this.mapFaces = mapFaces;
        this.partRotation = partRotation;
        this.shade = shade;
        this.setDefaultUvs();
    }
    
    private void setDefaultUvs() {
        for (final Map.Entry<EnumFacing, V> entry : this.mapFaces.entrySet()) {
            ((BlockPartFace)entry.getValue()).blockFaceUV.setUvs(this.getFaceUvs(entry.getKey()));
        }
    }
    
    private float[] getFaceUvs(final EnumFacing enumFacing) {
        float[] array = null;
        switch (BlockPart$1.$SwitchMap$net$minecraft$util$EnumFacing[enumFacing.ordinal()]) {
            case 1:
            case 2: {
                array = new float[] { this.positionFrom.x, this.positionFrom.z, this.positionTo.x, this.positionTo.z };
                break;
            }
            case 3:
            case 4: {
                array = new float[] { this.positionFrom.x, 16.0f - this.positionTo.y, this.positionTo.x, 16.0f - this.positionFrom.y };
                break;
            }
            case 5:
            case 6: {
                array = new float[] { this.positionFrom.z, 16.0f - this.positionTo.y, this.positionTo.z, 16.0f - this.positionFrom.y };
                break;
            }
            default: {
                throw new NullPointerException();
            }
        }
        return array;
    }
    
    static class Deserializer implements JsonDeserializer
    {
        public BlockPart deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            final JsonObject asJsonObject = jsonElement.getAsJsonObject();
            final Vector3f position = this.parsePositionFrom(asJsonObject);
            final Vector3f positionTo = this.parsePositionTo(asJsonObject);
            final BlockPartRotation rotation = this.parseRotation(asJsonObject);
            final Map facesCheck = this.parseFacesCheck(jsonDeserializationContext, asJsonObject);
            if (asJsonObject.has("shade") && !JsonUtils.isBoolean(asJsonObject, "shade")) {
                throw new JsonParseException("Expected shade to be a Boolean");
            }
            return new BlockPart(position, positionTo, facesCheck, rotation, JsonUtils.getBoolean(asJsonObject, "shade", true));
        }
        
        private Map parseFaces(final JsonDeserializationContext jsonDeserializationContext, final JsonObject jsonObject) {
            final EnumMap enumMap = Maps.newEnumMap((Class)EnumFacing.class);
            for (final Map.Entry<String, V> entry : JsonUtils.getJsonObject(jsonObject, "faces").entrySet()) {
                enumMap.put(this.parseEnumFacing(entry.getKey()), (BlockPartFace)jsonDeserializationContext.deserialize((JsonElement)entry.getValue(), (Type)BlockPartFace.class));
            }
            return enumMap;
        }
        
        private Vector3f parsePositionTo(final JsonObject jsonObject) {
            final Vector3f position = this.parsePosition(jsonObject, "to");
            if (position.x >= -16.0f && position.y >= -16.0f && position.z >= -16.0f && position.x <= 32.0f && position.y <= 32.0f && position.z <= 32.0f) {
                return position;
            }
            throw new JsonParseException("'to' specifier exceeds the allowed boundaries: " + position);
        }
        
        private EnumFacing parseEnumFacing(final String s) {
            final EnumFacing byName = EnumFacing.byName(s);
            if (byName == null) {
                throw new JsonParseException("Unknown facing: " + s);
            }
            return byName;
        }
        
        private Vector3f parsePosition(final JsonObject jsonObject, final String s) {
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
        
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
        
        private EnumFacing.Axis parseAxis(final JsonObject jsonObject) {
            final String string = JsonUtils.getString(jsonObject, "axis");
            final EnumFacing.Axis byName = EnumFacing.Axis.byName(string.toLowerCase());
            if (byName == null) {
                throw new JsonParseException("Invalid rotation axis: " + string);
            }
            return byName;
        }
        
        private Map parseFacesCheck(final JsonDeserializationContext jsonDeserializationContext, final JsonObject jsonObject) {
            final Map faces = this.parseFaces(jsonDeserializationContext, jsonObject);
            if (faces.isEmpty()) {
                throw new JsonParseException("Expected between 1 and 6 unique faces, got 0");
            }
            return faces;
        }
        
        private float parseAngle(final JsonObject jsonObject) {
            final float float1 = JsonUtils.getFloat(jsonObject, "angle");
            if (float1 != 0.0f && MathHelper.abs(float1) != 22.5f && MathHelper.abs(float1) != 45.0f) {
                throw new JsonParseException("Invalid rotation " + float1 + " found, only -45/-22.5/0/22.5/45 allowed");
            }
            return float1;
        }
        
        private BlockPartRotation parseRotation(final JsonObject jsonObject) {
            BlockPartRotation blockPartRotation = null;
            if (jsonObject.has("rotation")) {
                final JsonObject jsonObject2 = JsonUtils.getJsonObject(jsonObject, "rotation");
                final Vector3f position = this.parsePosition(jsonObject2, "origin");
                position.scale(0.0625f);
                blockPartRotation = new BlockPartRotation(position, this.parseAxis(jsonObject2), this.parseAngle(jsonObject2), JsonUtils.getBoolean(jsonObject2, "rescale", false));
            }
            return blockPartRotation;
        }
        
        private Vector3f parsePositionFrom(final JsonObject jsonObject) {
            final Vector3f position = this.parsePosition(jsonObject, "from");
            if (position.x >= -16.0f && position.y >= -16.0f && position.z >= -16.0f && position.x <= 32.0f && position.y <= 32.0f && position.z <= 32.0f) {
                return position;
            }
            throw new JsonParseException("'from' specifier exceeds the allowed boundaries: " + position);
        }
    }
}
