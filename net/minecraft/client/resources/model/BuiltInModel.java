package net.minecraft.client.resources.model;

import net.minecraft.client.renderer.block.model.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;

public class BuiltInModel implements IBakedModel
{
    private ItemCameraTransforms cameraTransforms;
    
    @Override
    public boolean isBuiltInRenderer() {
        return true;
    }
    
    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return this.cameraTransforms;
    }
    
    @Override
    public boolean isAmbientOcclusion() {
        return false;
    }
    
    @Override
    public List getGeneralQuads() {
        return null;
    }
    
    @Override
    public List getFaceQuads(final EnumFacing enumFacing) {
        return null;
    }
    
    @Override
    public TextureAtlasSprite getParticleTexture() {
        return null;
    }
    
    public BuiltInModel(final ItemCameraTransforms cameraTransforms) {
        this.cameraTransforms = cameraTransforms;
    }
    
    @Override
    public boolean isGui3d() {
        return true;
    }
}
