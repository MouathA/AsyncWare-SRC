package net.minecraft.util;

import org.apache.commons.lang3.*;
import com.google.gson.*;

public class JsonUtils
{
    public static String getString(final JsonElement jsonElement, final String s) {
        if (jsonElement.isJsonPrimitive()) {
            return jsonElement.getAsString();
        }
        throw new JsonSyntaxException("Expected " + s + " to be a string, was " + toString(jsonElement));
    }
    
    public static float getFloat(final JsonObject jsonObject, final String s) {
        if (jsonObject.has(s)) {
            return getFloat(jsonObject.get(s), s);
        }
        throw new JsonSyntaxException("Missing " + s + ", expected to find a Float");
    }
    
    public static String getString(final JsonObject jsonObject, final String s) {
        if (jsonObject.has(s)) {
            return getString(jsonObject.get(s), s);
        }
        throw new JsonSyntaxException("Missing " + s + ", expected to find a string");
    }
    
    public static boolean isBoolean(final JsonObject jsonObject, final String s) {
        return s != null && jsonObject.getAsJsonPrimitive(s).isBoolean();
    }
    
    public static JsonArray getJsonArray(final JsonObject jsonObject, final String s) {
        if (jsonObject.has(s)) {
            return getJsonArray(jsonObject.get(s), s);
        }
        throw new JsonSyntaxException("Missing " + s + ", expected to find a JsonArray");
    }
    
    public static boolean isJsonArray(final JsonObject jsonObject, final String s) {
        return s != null && jsonObject.get(s).isJsonArray();
    }
    
    public static JsonArray getJsonArray(final JsonObject jsonObject, final String s, final JsonArray jsonArray) {
        return jsonObject.has(s) ? getJsonArray(jsonObject.get(s), s) : jsonArray;
    }
    
    public static JsonObject getJsonObject(final JsonObject jsonObject, final String s) {
        if (jsonObject.has(s)) {
            return getJsonObject(jsonObject.get(s), s);
        }
        throw new JsonSyntaxException("Missing " + s + ", expected to find a JsonObject");
    }
    
    public static boolean getBoolean(final JsonObject jsonObject, final String s, final boolean b) {
        return jsonObject.has(s) ? getBoolean(jsonObject.get(s), s) : b;
    }
    
    public static boolean isString(final JsonElement jsonElement) {
        return jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isString();
    }
    
    public static int getInt(final JsonElement jsonElement, final String s) {
        if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isNumber()) {
            return jsonElement.getAsInt();
        }
        throw new JsonSyntaxException("Expected " + s + " to be a Int, was " + toString(jsonElement));
    }
    
    public static JsonObject getJsonObject(final JsonObject jsonObject, final String s, final JsonObject jsonObject2) {
        return jsonObject.has(s) ? getJsonObject(jsonObject.get(s), s) : jsonObject2;
    }
    
    public static JsonObject getJsonObject(final JsonElement jsonElement, final String s) {
        if (jsonElement.isJsonObject()) {
            return jsonElement.getAsJsonObject();
        }
        throw new JsonSyntaxException("Expected " + s + " to be a JsonObject, was " + toString(jsonElement));
    }
    
    public static float getFloat(final JsonObject jsonObject, final String s, final float n) {
        return jsonObject.has(s) ? getFloat(jsonObject.get(s), s) : n;
    }
    
    public static boolean getBoolean(final JsonElement jsonElement, final String s) {
        if (jsonElement.isJsonPrimitive()) {
            return jsonElement.getAsBoolean();
        }
        throw new JsonSyntaxException("Expected " + s + " to be a Boolean, was " + toString(jsonElement));
    }
    
    public static String toString(final JsonElement jsonElement) {
        final String abbreviateMiddle = StringUtils.abbreviateMiddle(String.valueOf(jsonElement), "...", 10);
        if (jsonElement == null) {
            return "null (missing)";
        }
        if (jsonElement.isJsonNull()) {
            return "null (json)";
        }
        if (jsonElement.isJsonArray()) {
            return "an array (" + abbreviateMiddle + ")";
        }
        if (jsonElement.isJsonObject()) {
            return "an object (" + abbreviateMiddle + ")";
        }
        if (jsonElement.isJsonPrimitive()) {
            final JsonPrimitive asJsonPrimitive = jsonElement.getAsJsonPrimitive();
            if (asJsonPrimitive.isNumber()) {
                return "a number (" + abbreviateMiddle + ")";
            }
            if (asJsonPrimitive.isBoolean()) {
                return "a boolean (" + abbreviateMiddle + ")";
            }
        }
        return abbreviateMiddle;
    }
    
    public static float getFloat(final JsonElement jsonElement, final String s) {
        if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isNumber()) {
            return jsonElement.getAsFloat();
        }
        throw new JsonSyntaxException("Expected " + s + " to be a Float, was " + toString(jsonElement));
    }
    
    public static boolean getBoolean(final JsonObject jsonObject, final String s) {
        if (jsonObject.has(s)) {
            return getBoolean(jsonObject.get(s), s);
        }
        throw new JsonSyntaxException("Missing " + s + ", expected to find a Boolean");
    }
    
    public static String getString(final JsonObject jsonObject, final String s, final String s2) {
        return jsonObject.has(s) ? getString(jsonObject.get(s), s) : s2;
    }
    
    public static int getInt(final JsonObject jsonObject, final String s) {
        if (jsonObject.has(s)) {
            return getInt(jsonObject.get(s), s);
        }
        throw new JsonSyntaxException("Missing " + s + ", expected to find a Int");
    }
    
    public static boolean isString(final JsonObject jsonObject, final String s) {
        return s != null && jsonObject.getAsJsonPrimitive(s).isString();
    }
    
    public static int getInt(final JsonObject jsonObject, final String s, final int n) {
        return jsonObject.has(s) ? getInt(jsonObject.get(s), s) : n;
    }
    
    public static JsonArray getJsonArray(final JsonElement jsonElement, final String s) {
        if (jsonElement.isJsonArray()) {
            return jsonElement.getAsJsonArray();
        }
        throw new JsonSyntaxException("Expected " + s + " to be a JsonArray, was " + toString(jsonElement));
    }
}
