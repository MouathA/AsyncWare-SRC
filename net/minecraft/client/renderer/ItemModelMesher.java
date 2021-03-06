package net.minecraft.client.renderer;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.item.*;
import net.minecraft.client.resources.model.*;
import java.util.*;
import com.google.common.collect.*;

public class ItemModelMesher
{
    private final Map simpleShapesCache;
    private final Map simpleShapes;
    private final ModelManager modelManager;
    private final Map shapers;
    
    public TextureAtlasSprite getParticleIcon(final Item item, final int n) {
        return this.getItemModel(new ItemStack(item, 1, n)).getParticleTexture();
    }
    
    public TextureAtlasSprite getParticleIcon(final Item item) {
        return this.getParticleIcon(item, 0);
    }
    
    protected int getMetadata(final ItemStack itemStack) {
        return itemStack.isItemStackDamageable() ? 0 : itemStack.getMetadata();
    }
    
    protected IBakedModel getItemModel(final Item item, final int n) {
        return this.simpleShapesCache.get(this.getIndex(item, n));
    }
    
    public void rebuildCache() {
        this.simpleShapesCache.clear();
        for (final Map.Entry<Object, V> entry : this.simpleShapes.entrySet()) {
            this.simpleShapesCache.put(entry.getKey(), this.modelManager.getModel((ModelResourceLocation)entry.getValue()));
        }
    }
    
    public ModelManager getModelManager() {
        return this.modelManager;
    }
    
    public IBakedModel getItemModel(final ItemStack itemStack) {
        final Item item = itemStack.getItem();
        IBakedModel bakedModel = this.getItemModel(item, this.getMetadata(itemStack));
        if (bakedModel == null) {
            final ItemMeshDefinition itemMeshDefinition = this.shapers.get(item);
            if (itemMeshDefinition != null) {
                bakedModel = this.modelManager.getModel(itemMeshDefinition.getModelLocation(itemStack));
            }
        }
        if (bakedModel == null) {
            bakedModel = this.modelManager.getMissingModel();
        }
        return bakedModel;
    }
    
    public void register(final Item item, final int n, final ModelResourceLocation modelResourceLocation) {
        this.simpleShapes.put(this.getIndex(item, n), modelResourceLocation);
        this.simpleShapesCache.put(this.getIndex(item, n), this.modelManager.getModel(modelResourceLocation));
    }
    
    public ItemModelMesher(final ModelManager modelManager) {
        this.simpleShapes = Maps.newHashMap();
        this.simpleShapesCache = Maps.newHashMap();
        this.shapers = Maps.newHashMap();
        this.modelManager = modelManager;
    }
    
    public void register(final Item item, final ItemMeshDefinition itemMeshDefinition) {
        this.shapers.put(item, itemMeshDefinition);
    }
    
    private int getIndex(final Item item, final int n) {
        return Item.getIdFromItem(item) << 16 | n;
    }
}
