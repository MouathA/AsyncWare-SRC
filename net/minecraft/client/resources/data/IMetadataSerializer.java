package net.minecraft.client.resources.data;

import java.lang.reflect.*;
import net.minecraft.util.*;
import com.google.gson.*;

public class IMetadataSerializer
{
    private Gson gson;
    private final GsonBuilder gsonBuilder;
    private final IRegistry metadataSectionSerializerRegistry;
    
    public void registerMetadataSectionType(final IMetadataSectionSerializer metadataSectionSerializer, final Class clazz) {
        this.metadataSectionSerializerRegistry.putObject(metadataSectionSerializer.getSectionName(), new Registration(metadataSectionSerializer, clazz, null));
        this.gsonBuilder.registerTypeAdapter((Type)clazz, (Object)metadataSectionSerializer);
        this.gson = null;
    }
    
    public IMetadataSection parseMetadataSection(final String s, final JsonObject jsonObject) {
        if (s == null) {
            throw new IllegalArgumentException("Metadata section name cannot be null");
        }
        if (!jsonObject.has(s)) {
            return null;
        }
        if (!jsonObject.get(s).isJsonObject()) {
            throw new IllegalArgumentException("Invalid metadata for '" + s + "' - expected object, found " + jsonObject.get(s));
        }
        final Registration registration = (Registration)this.metadataSectionSerializerRegistry.getObject(s);
        if (registration == null) {
            throw new IllegalArgumentException("Don't know how to handle metadata section '" + s + "'");
        }
        return (IMetadataSection)this.getGson().fromJson((JsonElement)jsonObject.getAsJsonObject(s), registration.field_110500_b);
    }
    
    public IMetadataSerializer() {
        this.metadataSectionSerializerRegistry = new RegistrySimple();
        (this.gsonBuilder = new GsonBuilder()).registerTypeHierarchyAdapter((Class)IChatComponent.class, (Object)new IChatComponent.Serializer());
        this.gsonBuilder.registerTypeHierarchyAdapter((Class)ChatStyle.class, (Object)new ChatStyle.Serializer());
        this.gsonBuilder.registerTypeAdapterFactory((TypeAdapterFactory)new EnumTypeAdapterFactory());
    }
    
    private Gson getGson() {
        if (this.gson == null) {
            this.gson = this.gsonBuilder.create();
        }
        return this.gson;
    }
    
    class Registration
    {
        final Class field_110500_b;
        final IMetadataSectionSerializer field_110502_a;
        final IMetadataSerializer this$0;
        
        private Registration(final IMetadataSerializer this$0, final IMetadataSectionSerializer field_110502_a, final Class field_110500_b) {
            this.this$0 = this$0;
            this.field_110502_a = field_110502_a;
            this.field_110500_b = field_110500_b;
        }
        
        Registration(final IMetadataSerializer metadataSerializer, final IMetadataSectionSerializer metadataSectionSerializer, final Class clazz, final IMetadataSerializer$1 object) {
            this(metadataSerializer, metadataSectionSerializer, clazz);
        }
    }
}
