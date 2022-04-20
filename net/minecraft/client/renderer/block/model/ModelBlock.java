package net.minecraft.client.renderer.block.model;

import java.io.*;
import org.apache.logging.log4j.*;
import java.lang.reflect.*;
import net.minecraft.util.*;
import com.google.gson.*;
import org.apache.commons.lang3.*;
import com.google.common.collect.*;
import java.util.*;

public class ModelBlock
{
    private static final Logger LOGGER;
    static final Gson SERIALIZER;
    public String name;
    protected ResourceLocation parentLocation;
    private final boolean gui3d;
    protected ModelBlock parent;
    protected final Map textures;
    private final boolean ambientOcclusion;
    private ItemCameraTransforms cameraTransforms;
    private final List elements;
    
    public boolean isAmbientOcclusion() {
        return (this != null) ? this.parent.isAmbientOcclusion() : this.ambientOcclusion;
    }
    
    public ModelBlock getRootModel() {
        return (this != null) ? this.parent.getRootModel() : this;
    }
    
    public ItemCameraTransforms func_181682_g() {
        return new ItemCameraTransforms(this.func_181681_a(ItemCameraTransforms.TransformType.THIRD_PERSON), this.func_181681_a(ItemCameraTransforms.TransformType.FIRST_PERSON), this.func_181681_a(ItemCameraTransforms.TransformType.HEAD), this.func_181681_a(ItemCameraTransforms.TransformType.GUI), this.func_181681_a(ItemCameraTransforms.TransformType.GROUND), this.func_181681_a(ItemCameraTransforms.TransformType.FIXED));
    }
    
    private String resolveTextureName(final String s, final Bookkeep bookkeep) {
        if (this != s) {
            return s;
        }
        if (this == bookkeep.modelExt) {
            ModelBlock.LOGGER.warn("Unable to resolve texture due to upward reference: " + s + " in " + this.name);
            return "missingno";
        }
        String s2 = this.textures.get(s.substring(1));
        if (s2 == null && this != null) {
            s2 = this.parent.resolveTextureName(s, bookkeep);
        }
        bookkeep.modelExt = this;
        if (s2 != null && this == s2) {
            s2 = bookkeep.model.resolveTextureName(s2, bookkeep);
        }
        return (s2 != null && this == s2) ? s2 : "missingno";
    }
    
    public String resolveTextureName(String string) {
        if (this == string) {
            string = '#' + string;
        }
        return this.resolveTextureName(string, new Bookkeep(this, null));
    }
    
    protected ModelBlock(final List list, final Map map, final boolean b, final boolean b2, final ItemCameraTransforms itemCameraTransforms) {
        this(null, list, map, b, b2, itemCameraTransforms);
    }
    
    protected ModelBlock(final ResourceLocation resourceLocation, final Map map, final boolean b, final boolean b2, final ItemCameraTransforms itemCameraTransforms) {
        this(resourceLocation, Collections.emptyList(), map, b, b2, itemCameraTransforms);
    }
    
    public static ModelBlock deserialize(final Reader reader) {
        return (ModelBlock)ModelBlock.SERIALIZER.fromJson(reader, (Class)ModelBlock.class);
    }
    
    public boolean isTexturePresent(final String s) {
        return !"missingno".equals(this.resolveTextureName(s));
    }
    
    public static ModelBlock deserialize(final String s) {
        return deserialize(new StringReader(s));
    }
    
    static {
        LOGGER = LogManager.getLogger();
        SERIALIZER = new GsonBuilder().registerTypeAdapter((Type)ModelBlock.class, (Object)new Deserializer()).registerTypeAdapter((Type)BlockPart.class, (Object)new BlockPart.Deserializer()).registerTypeAdapter((Type)BlockPartFace.class, (Object)new BlockPartFace.Deserializer()).registerTypeAdapter((Type)BlockFaceUV.class, (Object)new BlockFaceUV.Deserializer()).registerTypeAdapter((Type)ItemTransformVec3f.class, (Object)new ItemTransformVec3f.Deserializer()).registerTypeAdapter((Type)ItemCameraTransforms.class, (Object)new ItemCameraTransforms.Deserializer()).create();
    }
    
    public List getElements() {
        return (this != null) ? this.parent.getElements() : this.elements;
    }
    
    public static void checkModelHierarchy(final Map map) {
        final Iterator<ModelBlock> iterator = map.values().iterator();
        if (iterator.hasNext()) {
            for (ModelBlock modelBlock = iterator.next().parent, modelBlock2 = modelBlock.parent; modelBlock != modelBlock2; modelBlock = modelBlock.parent, modelBlock2 = modelBlock2.parent.parent) {}
            throw new LoopException();
        }
    }
    
    public ResourceLocation getParentLocation() {
        return this.parentLocation;
    }
    
    private ModelBlock(final ResourceLocation parentLocation, final List elements, final Map textures, final boolean ambientOcclusion, final boolean gui3d, final ItemCameraTransforms cameraTransforms) {
        this.name = "";
        this.elements = elements;
        this.ambientOcclusion = ambientOcclusion;
        this.gui3d = gui3d;
        this.textures = textures;
        this.parentLocation = parentLocation;
        this.cameraTransforms = cameraTransforms;
    }
    
    private ItemTransformVec3f func_181681_a(final ItemCameraTransforms.TransformType transformType) {
        return (this.parent != null && !this.cameraTransforms.func_181687_c(transformType)) ? this.parent.func_181681_a(transformType) : this.cameraTransforms.getTransform(transformType);
    }
    
    public void getParentFromMap(final Map map) {
        if (this.parentLocation != null) {
            this.parent = map.get(this.parentLocation);
        }
    }
    
    public boolean isGui3d() {
        return this.gui3d;
    }
    
    public static class Deserializer implements JsonDeserializer
    {
        protected List getModelElements(final JsonDeserializationContext jsonDeserializationContext, final JsonObject jsonObject) {
            final ArrayList arrayList = Lists.newArrayList();
            if (jsonObject.has("elements")) {
                final Iterator iterator = JsonUtils.getJsonArray(jsonObject, "elements").iterator();
                while (iterator.hasNext()) {
                    arrayList.add(jsonDeserializationContext.deserialize((JsonElement)iterator.next(), (Type)BlockPart.class));
                }
            }
            return arrayList;
        }
        
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
        
        private String getParent(final JsonObject jsonObject) {
            return JsonUtils.getString(jsonObject, "parent", "");
        }
        
        protected boolean getAmbientOcclusionEnabled(final JsonObject jsonObject) {
            return JsonUtils.getBoolean(jsonObject, "ambientocclusion", true);
        }
        
        public ModelBlock deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            final JsonObject asJsonObject = jsonElement.getAsJsonObject();
            final List modelElements = this.getModelElements(jsonDeserializationContext, asJsonObject);
            final String parent = this.getParent(asJsonObject);
            final boolean empty = StringUtils.isEmpty((CharSequence)parent);
            final boolean empty2 = modelElements.isEmpty();
            if (empty2 && empty) {
                throw new JsonParseException("BlockModel requires either elements or parent, found neither");
            }
            if (!empty && !empty2) {
                throw new JsonParseException("BlockModel requires either elements or parent, found both");
            }
            final Map textures = this.getTextures(asJsonObject);
            final boolean ambientOcclusionEnabled = this.getAmbientOcclusionEnabled(asJsonObject);
            ItemCameraTransforms default1 = ItemCameraTransforms.DEFAULT;
            if (asJsonObject.has("display")) {
                default1 = (ItemCameraTransforms)jsonDeserializationContext.deserialize((JsonElement)JsonUtils.getJsonObject(asJsonObject, "display"), (Type)ItemCameraTransforms.class);
            }
            return empty2 ? new ModelBlock(new ResourceLocation(parent), textures, ambientOcclusionEnabled, true, default1) : new ModelBlock(modelElements, textures, ambientOcclusionEnabled, true, default1);
        }
        
        private Map getTextures(final JsonObject jsonObject) {
            final HashMap hashMap = Maps.newHashMap();
            if (jsonObject.has("textures")) {
                for (final Map.Entry<Object, V> entry : jsonObject.getAsJsonObject("textures").entrySet()) {
                    hashMap.put(entry.getKey(), ((JsonElement)entry.getValue()).getAsString());
                }
            }
            return hashMap;
        }
    }
    
    public static class LoopException extends RuntimeException
    {
    }
    
    static final class Bookkeep
    {
        public ModelBlock modelExt;
        public final ModelBlock model;
        
        Bookkeep(final ModelBlock modelBlock, final ModelBlock$1 object) {
            this(modelBlock);
        }
        
        private Bookkeep(final ModelBlock model) {
            this.model = model;
        }
    }
}
