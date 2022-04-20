package net.minecraft.client.renderer.block.model;

import net.minecraft.util.*;
import java.lang.reflect.*;
import com.google.gson.*;

public class BlockPartFace
{
    public final BlockFaceUV blockFaceUV;
    public final int tintIndex;
    public static final EnumFacing FACING_DEFAULT;
    public final String texture;
    public final EnumFacing cullFace;
    
    static {
        FACING_DEFAULT = null;
    }
    
    public BlockPartFace(final EnumFacing cullFace, final int tintIndex, final String texture, final BlockFaceUV blockFaceUV) {
        this.cullFace = cullFace;
        this.tintIndex = tintIndex;
        this.texture = texture;
        this.blockFaceUV = blockFaceUV;
    }
    
    static class Deserializer implements JsonDeserializer
    {
        protected int parseTintIndex(final JsonObject jsonObject) {
            return JsonUtils.getInt(jsonObject, "tintindex", -1);
        }
        
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
        
        private EnumFacing parseCullFace(final JsonObject jsonObject) {
            return EnumFacing.byName(JsonUtils.getString(jsonObject, "cullface", ""));
        }
        
        private String parseTexture(final JsonObject jsonObject) {
            return JsonUtils.getString(jsonObject, "texture");
        }
        
        public BlockPartFace deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            final JsonObject asJsonObject = jsonElement.getAsJsonObject();
            return new BlockPartFace(this.parseCullFace(asJsonObject), this.parseTintIndex(asJsonObject), this.parseTexture(asJsonObject), (BlockFaceUV)jsonDeserializationContext.deserialize((JsonElement)asJsonObject, (Type)BlockFaceUV.class));
        }
    }
}
