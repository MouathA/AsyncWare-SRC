package net.minecraft.client.resources.model;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.block.model.*;
import java.util.*;
import com.google.common.collect.*;

public class WeightedBakedModel implements IBakedModel
{
    private final int totalWeight;
    private final List models;
    private final IBakedModel baseModel;
    
    public WeightedBakedModel(final List models) {
        this.models = models;
        this.totalWeight = WeightedRandom.getTotalWeight(models);
        this.baseModel = models.get(0).model;
    }
    
    public IBakedModel getAlternativeModel(final long n) {
        return ((MyWeighedRandomItem)WeightedRandom.getRandomItem(this.models, Math.abs((int)n >> 16) % this.totalWeight)).model;
    }
    
    @Override
    public boolean isAmbientOcclusion() {
        return this.baseModel.isAmbientOcclusion();
    }
    
    @Override
    public TextureAtlasSprite getParticleTexture() {
        return this.baseModel.getParticleTexture();
    }
    
    @Override
    public List getFaceQuads(final EnumFacing enumFacing) {
        return this.baseModel.getFaceQuads(enumFacing);
    }
    
    @Override
    public boolean isBuiltInRenderer() {
        return this.baseModel.isBuiltInRenderer();
    }
    
    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return this.baseModel.getItemCameraTransforms();
    }
    
    @Override
    public boolean isGui3d() {
        return this.baseModel.isGui3d();
    }
    
    @Override
    public List getGeneralQuads() {
        return this.baseModel.getGeneralQuads();
    }
    
    static class MyWeighedRandomItem extends WeightedRandom.Item implements Comparable
    {
        protected final IBakedModel model;
        
        @Override
        public int compareTo(final Object o) {
            return this.compareTo((MyWeighedRandomItem)o);
        }
        
        protected int getCountQuads() {
            int size = this.model.getGeneralQuads().size();
            final EnumFacing[] values = EnumFacing.values();
            while (0 < values.length) {
                size += this.model.getFaceQuads(values[0]).size();
                int n = 0;
                ++n;
            }
            return size;
        }
        
        public int compareTo(final MyWeighedRandomItem myWeighedRandomItem) {
            return ComparisonChain.start().compare(myWeighedRandomItem.itemWeight, this.itemWeight).compare(this.getCountQuads(), myWeighedRandomItem.getCountQuads()).result();
        }
        
        @Override
        public String toString() {
            return "MyWeighedRandomItem{weight=" + this.itemWeight + ", model=" + this.model + '}';
        }
        
        public MyWeighedRandomItem(final IBakedModel model, final int n) {
            super(n);
            this.model = model;
        }
    }
    
    public static class Builder
    {
        private List listItems;
        
        public WeightedBakedModel build() {
            Collections.sort((List<Comparable>)this.listItems);
            return new WeightedBakedModel(this.listItems);
        }
        
        public IBakedModel first() {
            return this.listItems.get(0).model;
        }
        
        public Builder add(final IBakedModel bakedModel, final int n) {
            this.listItems.add(new MyWeighedRandomItem(bakedModel, n));
            return this;
        }
        
        public Builder() {
            this.listItems = Lists.newArrayList();
        }
    }
}
