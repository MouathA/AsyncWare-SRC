package net.minecraft.client.renderer.block.model;

import net.minecraft.client.renderer.*;
import java.lang.reflect.*;
import com.google.gson.*;

public class ItemCameraTransforms
{
    public final ItemTransformVec3f head;
    public static final ItemCameraTransforms DEFAULT;
    public final ItemTransformVec3f firstPerson;
    public final ItemTransformVec3f fixed;
    public final ItemTransformVec3f gui;
    public final ItemTransformVec3f ground;
    public final ItemTransformVec3f thirdPerson;
    
    public void applyTransform(final TransformType transformType) {
        final ItemTransformVec3f transform = this.getTransform(transformType);
        if (transform != ItemTransformVec3f.DEFAULT) {
            GlStateManager.translate(transform.translation.x + 0.0f, transform.translation.y + 0.0f, transform.translation.z + 0.0f);
            GlStateManager.rotate(transform.rotation.y + 0.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(transform.rotation.x + 0.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(transform.rotation.z + 0.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.scale(transform.scale.x + 0.0f, transform.scale.y + 0.0f, transform.scale.z + 0.0f);
        }
    }
    
    public ItemTransformVec3f getTransform(final TransformType transformType) {
        switch (ItemCameraTransforms$1.$SwitchMap$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType[transformType.ordinal()]) {
            case 1: {
                return this.thirdPerson;
            }
            case 2: {
                return this.firstPerson;
            }
            case 3: {
                return this.head;
            }
            case 4: {
                return this.gui;
            }
            case 5: {
                return this.ground;
            }
            case 6: {
                return this.fixed;
            }
            default: {
                return ItemTransformVec3f.DEFAULT;
            }
        }
    }
    
    public boolean func_181687_c(final TransformType transformType) {
        return !this.getTransform(transformType).equals(ItemTransformVec3f.DEFAULT);
    }
    
    private ItemCameraTransforms() {
        this(ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT);
    }
    
    static {
        DEFAULT = new ItemCameraTransforms();
    }
    
    public ItemCameraTransforms(final ItemCameraTransforms itemCameraTransforms) {
        this.thirdPerson = itemCameraTransforms.thirdPerson;
        this.firstPerson = itemCameraTransforms.firstPerson;
        this.head = itemCameraTransforms.head;
        this.gui = itemCameraTransforms.gui;
        this.ground = itemCameraTransforms.ground;
        this.fixed = itemCameraTransforms.fixed;
    }
    
    public ItemCameraTransforms(final ItemTransformVec3f thirdPerson, final ItemTransformVec3f firstPerson, final ItemTransformVec3f head, final ItemTransformVec3f gui, final ItemTransformVec3f ground, final ItemTransformVec3f fixed) {
        this.thirdPerson = thirdPerson;
        this.firstPerson = firstPerson;
        this.head = head;
        this.gui = gui;
        this.ground = ground;
        this.fixed = fixed;
    }
    
    public enum TransformType
    {
        GUI("GUI", 4), 
        NONE("NONE", 0);
        
        private static final TransformType[] $VALUES;
        
        GROUND("GROUND", 5), 
        THIRD_PERSON("THIRD_PERSON", 1), 
        FIXED("FIXED", 6), 
        FIRST_PERSON("FIRST_PERSON", 2), 
        HEAD("HEAD", 3);
        
        private TransformType(final String s, final int n) {
        }
        
        static {
            $VALUES = new TransformType[] { TransformType.NONE, TransformType.THIRD_PERSON, TransformType.FIRST_PERSON, TransformType.HEAD, TransformType.GUI, TransformType.GROUND, TransformType.FIXED };
        }
    }
    
    static class Deserializer implements JsonDeserializer
    {
        private ItemTransformVec3f func_181683_a(final JsonDeserializationContext jsonDeserializationContext, final JsonObject jsonObject, final String s) {
            return (ItemTransformVec3f)(jsonObject.has(s) ? jsonDeserializationContext.deserialize(jsonObject.get(s), (Type)ItemTransformVec3f.class) : ItemTransformVec3f.DEFAULT);
        }
        
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
        
        public ItemCameraTransforms deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            final JsonObject asJsonObject = jsonElement.getAsJsonObject();
            return new ItemCameraTransforms(this.func_181683_a(jsonDeserializationContext, asJsonObject, "thirdperson"), this.func_181683_a(jsonDeserializationContext, asJsonObject, "firstperson"), this.func_181683_a(jsonDeserializationContext, asJsonObject, "head"), this.func_181683_a(jsonDeserializationContext, asJsonObject, "gui"), this.func_181683_a(jsonDeserializationContext, asJsonObject, "ground"), this.func_181683_a(jsonDeserializationContext, asJsonObject, "fixed"));
        }
    }
}
