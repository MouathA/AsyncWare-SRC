package net.minecraft.client.renderer.block.model;

import java.io.*;
import java.lang.reflect.*;
import com.google.common.collect.*;
import java.util.*;
import com.google.gson.*;
import net.minecraft.util.*;
import net.minecraft.client.resources.model.*;

public class ModelBlockDefinition
{
    private final Map mapVariants;
    static final Gson GSON;
    
    public ModelBlockDefinition(final List list) {
        this.mapVariants = Maps.newHashMap();
        final Iterator<ModelBlockDefinition> iterator = list.iterator();
        while (iterator.hasNext()) {
            this.mapVariants.putAll(iterator.next().mapVariants);
        }
    }
    
    public static ModelBlockDefinition parseFromReader(final Reader reader) {
        return (ModelBlockDefinition)ModelBlockDefinition.GSON.fromJson(reader, (Class)ModelBlockDefinition.class);
    }
    
    @Override
    public int hashCode() {
        return this.mapVariants.hashCode();
    }
    
    public ModelBlockDefinition(final Collection collection) {
        this.mapVariants = Maps.newHashMap();
        for (final Variants variants : collection) {
            this.mapVariants.put(Variants.access$000(variants), variants);
        }
    }
    
    static {
        GSON = new GsonBuilder().registerTypeAdapter((Type)ModelBlockDefinition.class, (Object)new Deserializer()).registerTypeAdapter((Type)Variant.class, (Object)new Variant.Deserializer()).create();
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o instanceof ModelBlockDefinition && this.mapVariants.equals(((ModelBlockDefinition)o).mapVariants));
    }
    
    public Variants getVariants(final String s) {
        final Variants variants = this.mapVariants.get(s);
        if (variants == null) {
            throw new MissingVariantException();
        }
        return variants;
    }
    
    public static class Deserializer implements JsonDeserializer
    {
        public ModelBlockDefinition deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return new ModelBlockDefinition((Collection)this.parseVariantsList(jsonDeserializationContext, jsonElement.getAsJsonObject()));
        }
        
        protected Variants parseVariants(final JsonDeserializationContext jsonDeserializationContext, final Map.Entry entry) {
            final String s = entry.getKey();
            final ArrayList arrayList = Lists.newArrayList();
            final JsonElement jsonElement = (JsonElement)entry.getValue();
            if (jsonElement.isJsonArray()) {
                final Iterator iterator = jsonElement.getAsJsonArray().iterator();
                while (iterator.hasNext()) {
                    arrayList.add(jsonDeserializationContext.deserialize((JsonElement)iterator.next(), (Type)Variant.class));
                }
            }
            else {
                arrayList.add(jsonDeserializationContext.deserialize(jsonElement, (Type)Variant.class));
            }
            return new Variants(s, arrayList);
        }
        
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
        
        protected List parseVariantsList(final JsonDeserializationContext jsonDeserializationContext, final JsonObject jsonObject) {
            final JsonObject jsonObject2 = JsonUtils.getJsonObject(jsonObject, "variants");
            final ArrayList arrayList = Lists.newArrayList();
            final Iterator iterator = jsonObject2.entrySet().iterator();
            while (iterator.hasNext()) {
                arrayList.add(this.parseVariants(jsonDeserializationContext, iterator.next()));
            }
            return arrayList;
        }
    }
    
    public static class Variants
    {
        private final String name;
        private final List listVariants;
        
        public Variants(final String name, final List listVariants) {
            this.name = name;
            this.listVariants = listVariants;
        }
        
        @Override
        public int hashCode() {
            return 31 * this.name.hashCode() + this.listVariants.hashCode();
        }
        
        public List getVariants() {
            return this.listVariants;
        }
        
        static String access$000(final Variants variants) {
            return variants.name;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Variants)) {
                return false;
            }
            final Variants variants = (Variants)o;
            return this.name.equals(variants.name) && this.listVariants.equals(variants.listVariants);
        }
    }
    
    public static class Variant
    {
        private final ResourceLocation modelLocation;
        private final int weight;
        private final ModelRotation modelRotation;
        private final boolean uvLock;
        
        public int getWeight() {
            return this.weight;
        }
        
        @Override
        public int hashCode() {
            return 31 * (31 * this.modelLocation.hashCode() + ((this.modelRotation != null) ? this.modelRotation.hashCode() : 0)) + (this.uvLock ? 1 : 0);
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Variant)) {
                return false;
            }
            final Variant variant = (Variant)o;
            return this.modelLocation.equals(variant.modelLocation) && this.modelRotation == variant.modelRotation && this.uvLock == variant.uvLock;
        }
        
        public ResourceLocation getModelLocation() {
            return this.modelLocation;
        }
        
        public ModelRotation getRotation() {
            return this.modelRotation;
        }
        
        public boolean isUvLocked() {
            return this.uvLock;
        }
        
        public Variant(final ResourceLocation modelLocation, final ModelRotation modelRotation, final boolean uvLock, final int weight) {
            this.modelLocation = modelLocation;
            this.modelRotation = modelRotation;
            this.uvLock = uvLock;
            this.weight = weight;
        }
        
        public static class Deserializer implements JsonDeserializer
        {
            public Variant deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                final JsonObject asJsonObject = jsonElement.getAsJsonObject();
                return new Variant(this.makeModelLocation(this.parseModel(asJsonObject)), this.parseRotation(asJsonObject), this.parseUvLock(asJsonObject), this.parseWeight(asJsonObject));
            }
            
            protected ModelRotation parseRotation(final JsonObject jsonObject) {
                final int int1 = JsonUtils.getInt(jsonObject, "x", 0);
                final int int2 = JsonUtils.getInt(jsonObject, "y", 0);
                final ModelRotation modelRotation = ModelRotation.getModelRotation(int1, int2);
                if (modelRotation == null) {
                    throw new JsonParseException("Invalid BlockModelRotation x: " + int1 + ", y: " + int2);
                }
                return modelRotation;
            }
            
            private boolean parseUvLock(final JsonObject jsonObject) {
                return JsonUtils.getBoolean(jsonObject, "uvlock", false);
            }
            
            protected String parseModel(final JsonObject jsonObject) {
                return JsonUtils.getString(jsonObject, "model");
            }
            
            protected int parseWeight(final JsonObject jsonObject) {
                return JsonUtils.getInt(jsonObject, "weight", 1);
            }
            
            public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return this.deserialize(jsonElement, type, jsonDeserializationContext);
            }
            
            private ResourceLocation makeModelLocation(final String s) {
                final ResourceLocation resourceLocation = new ResourceLocation(s);
                return new ResourceLocation(resourceLocation.getResourceDomain(), "block/" + resourceLocation.getResourcePath());
            }
        }
    }
    
    public class MissingVariantException extends RuntimeException
    {
        final ModelBlockDefinition this$0;
        
        public MissingVariantException(final ModelBlockDefinition this$0) {
            this.this$0 = this$0;
        }
    }
}
