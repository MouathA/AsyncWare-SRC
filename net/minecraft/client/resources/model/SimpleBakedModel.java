package net.minecraft.client.resources.model;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.client.renderer.block.model.*;

public class SimpleBakedModel implements IBakedModel
{
    protected final ItemCameraTransforms cameraTransforms;
    protected final List generalQuads;
    protected final boolean gui3d;
    protected final List faceQuads;
    protected final boolean ambientOcclusion;
    protected final TextureAtlasSprite texture;
    
    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return this.cameraTransforms;
    }
    
    @Override
    public boolean isGui3d() {
        return this.gui3d;
    }
    
    @Override
    public List getFaceQuads(final EnumFacing enumFacing) {
        return this.faceQuads.get(enumFacing.ordinal());
    }
    
    @Override
    public TextureAtlasSprite getParticleTexture() {
        return this.texture;
    }
    
    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }
    
    @Override
    public List getGeneralQuads() {
        return this.generalQuads;
    }
    
    public SimpleBakedModel(final List generalQuads, final List faceQuads, final boolean ambientOcclusion, final boolean gui3d, final TextureAtlasSprite texture, final ItemCameraTransforms cameraTransforms) {
        this.generalQuads = generalQuads;
        this.faceQuads = faceQuads;
        this.ambientOcclusion = ambientOcclusion;
        this.gui3d = gui3d;
        this.texture = texture;
        this.cameraTransforms = cameraTransforms;
    }
    
    @Override
    public boolean isAmbientOcclusion() {
        return this.ambientOcclusion;
    }
    
    public static class Builder
    {
        private final List builderFaceQuads;
        private final List builderGeneralQuads;
        private TextureAtlasSprite builderTexture;
        private boolean builderGui3d;
        private final boolean builderAmbientOcclusion;
        private ItemCameraTransforms builderCameraTransforms;
        
        public Builder(final IBakedModel bakedModel, final TextureAtlasSprite textureAtlasSprite) {
            this(bakedModel.isAmbientOcclusion(), bakedModel.isGui3d(), bakedModel.getItemCameraTransforms());
            this.builderTexture = bakedModel.getParticleTexture();
            final EnumFacing[] values = EnumFacing.values();
            while (0 < values.length) {
                this.addFaceBreakingFours(bakedModel, textureAtlasSprite, values[0]);
                int n = 0;
                ++n;
            }
            this.addGeneralBreakingFours(bakedModel, textureAtlasSprite);
        }
        
        private Builder(final boolean builderAmbientOcclusion, final boolean builderGui3d, final ItemCameraTransforms builderCameraTransforms) {
            this.builderGeneralQuads = Lists.newArrayList();
            this.builderFaceQuads = Lists.newArrayListWithCapacity(6);
            final EnumFacing[] values = EnumFacing.values();
            while (0 < values.length) {
                final EnumFacing enumFacing = values[0];
                this.builderFaceQuads.add(Lists.newArrayList());
                int n = 0;
                ++n;
            }
            this.builderAmbientOcclusion = builderAmbientOcclusion;
            this.builderGui3d = builderGui3d;
            this.builderCameraTransforms = builderCameraTransforms;
        }
        
        public Builder setTexture(final TextureAtlasSprite builderTexture) {
            this.builderTexture = builderTexture;
            return this;
        }
        
        private void addGeneralBreakingFours(final IBakedModel bakedModel, final TextureAtlasSprite textureAtlasSprite) {
            final Iterator<BakedQuad> iterator = bakedModel.getGeneralQuads().iterator();
            while (iterator.hasNext()) {
                this.addGeneralQuad(new BreakingFour(iterator.next(), textureAtlasSprite));
            }
        }
        
        public IBakedModel makeBakedModel() {
            if (this.builderTexture == null) {
                throw new RuntimeException("Missing particle!");
            }
            return new SimpleBakedModel(this.builderGeneralQuads, this.builderFaceQuads, this.builderAmbientOcclusion, this.builderGui3d, this.builderTexture, this.builderCameraTransforms);
        }
        
        private void addFaceBreakingFours(final IBakedModel bakedModel, final TextureAtlasSprite textureAtlasSprite, final EnumFacing enumFacing) {
            final Iterator<BakedQuad> iterator = bakedModel.getFaceQuads(enumFacing).iterator();
            while (iterator.hasNext()) {
                this.addFaceQuad(enumFacing, new BreakingFour(iterator.next(), textureAtlasSprite));
            }
        }
        
        public Builder addGeneralQuad(final BakedQuad bakedQuad) {
            this.builderGeneralQuads.add(bakedQuad);
            return this;
        }
        
        public Builder addFaceQuad(final EnumFacing enumFacing, final BakedQuad bakedQuad) {
            this.builderFaceQuads.get(enumFacing.ordinal()).add(bakedQuad);
            return this;
        }
        
        public Builder(final ModelBlock modelBlock) {
            this(modelBlock.isAmbientOcclusion(), modelBlock.isGui3d(), modelBlock.func_181682_g());
        }
    }
}
