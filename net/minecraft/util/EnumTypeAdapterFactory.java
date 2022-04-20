package net.minecraft.util;

import com.google.gson.reflect.*;
import com.google.gson.*;
import com.google.common.collect.*;
import java.io.*;
import com.google.gson.stream.*;
import java.util.*;

public class EnumTypeAdapterFactory implements TypeAdapterFactory
{
    private String func_151232_a(final Object o) {
        return (o instanceof Enum) ? ((Enum)o).name().toLowerCase(Locale.US) : o.toString().toLowerCase(Locale.US);
    }
    
    public TypeAdapter create(final Gson gson, final TypeToken typeToken) {
        final Class rawType = typeToken.getRawType();
        if (!rawType.isEnum()) {
            return null;
        }
        final HashMap hashMap = Maps.newHashMap();
        final Object[] enumConstants = rawType.getEnumConstants();
        while (0 < enumConstants.length) {
            final Object o = enumConstants[0];
            hashMap.put(this.func_151232_a(o), o);
            int n = 0;
            ++n;
        }
        return new TypeAdapter(this, hashMap) {
            final Map val$map;
            final EnumTypeAdapterFactory this$0;
            
            public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
                if (o == null) {
                    jsonWriter.nullValue();
                }
                else {
                    jsonWriter.value(EnumTypeAdapterFactory.access$000(this.this$0, o));
                }
            }
            
            public Object read(final JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return this.val$map.get(jsonReader.nextString());
            }
        };
    }
    
    static String access$000(final EnumTypeAdapterFactory enumTypeAdapterFactory, final Object o) {
        return enumTypeAdapterFactory.func_151232_a(o);
    }
}
