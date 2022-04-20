package net.minecraft.client.resources.model;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.resources.*;

public class ModelManager implements IResourceManagerReloadListener
{
    private final TextureMap texMap;
    private IBakedModel defaultModel;
    private IRegistry modelRegistry;
    private final BlockModelShapes modelProvider;
    
    public IBakedModel getModel(final ModelResourceLocation modelResourceLocation) {
        if (modelResourceLocation == null) {
            return this.defaultModel;
        }
        final IBakedModel bakedModel = (IBakedModel)this.modelRegistry.getObject(modelResourceLocation);
        return (bakedModel == null) ? this.defaultModel : bakedModel;
    }
    
    public ModelManager(final TextureMap texMap) {
        this.texMap = texMap;
        this.modelProvider = new BlockModelShapes(this);
    }
    
    public BlockModelShapes getBlockModelShapes() {
        return this.modelProvider;
    }
    
    @Override
    public void onResourceManagerReload(final IResourceManager resourceManager) {
        this.modelRegistry = new ModelBakery(resourceManager, this.texMap, this.modelProvider).setupModelRegistry();
        this.defaultModel = (IBakedModel)this.modelRegistry.getObject(ModelBakery.MODEL_MISSING);
        this.modelProvider.reloadModels();
    }
    
    public TextureMap getTextureMap() {
        return this.texMap;
    }
    
    public IBakedModel getMissingModel() {
        return this.defaultModel;
    }
}
