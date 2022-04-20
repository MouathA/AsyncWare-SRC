package net.minecraft.client.resources.model;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.*;

public interface IBakedModel
{
    boolean isBuiltInRenderer();
    
    boolean isGui3d();
    
    List getFaceQuads(final EnumFacing p0);
    
    ItemCameraTransforms getItemCameraTransforms();
    
    boolean isAmbientOcclusion();
    
    TextureAtlasSprite getParticleTexture();
    
    List getGeneralQuads();
}
