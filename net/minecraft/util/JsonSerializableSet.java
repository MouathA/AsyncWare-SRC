package net.minecraft.util;

import com.google.common.collect.*;
import com.google.gson.*;
import java.util.*;

public class JsonSerializableSet extends ForwardingSet implements IJsonSerializable
{
    private final Set underlyingSet;
    
    public JsonSerializableSet() {
        this.underlyingSet = Sets.newHashSet();
    }
    
    protected Set delegate() {
        return this.underlyingSet;
    }
    
    protected Object delegate() {
        return this.delegate();
    }
    
    public JsonElement getSerializableElement() {
        final JsonArray jsonArray = new JsonArray();
        final Iterator iterator = this.iterator();
        while (iterator.hasNext()) {
            jsonArray.add((JsonElement)new JsonPrimitive((String)iterator.next()));
        }
        return (JsonElement)jsonArray;
    }
    
    public void fromJson(final JsonElement jsonElement) {
        if (jsonElement.isJsonArray()) {
            final Iterator iterator = jsonElement.getAsJsonArray().iterator();
            while (iterator.hasNext()) {
                this.add((Object)iterator.next().getAsString());
            }
        }
    }
    
    protected Collection delegate() {
        return this.delegate();
    }
}
