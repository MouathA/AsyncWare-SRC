package net.minecraft.world.gen;

import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.util.*;

public class FlatLayerInfo
{
    private final int field_175902_a;
    private int layerCount;
    private int layerMinimumY;
    private IBlockState field_175901_b;
    
    private int getFillBlockMeta() {
        return this.field_175901_b.getBlock().getMetaFromState(this.field_175901_b);
    }
    
    public void setMinY(final int layerMinimumY) {
        this.layerMinimumY = layerMinimumY;
    }
    
    public int getMinY() {
        return this.layerMinimumY;
    }
    
    public FlatLayerInfo(final int field_175902_a, final int layerCount, final Block block) {
        this.layerCount = 1;
        this.field_175902_a = field_175902_a;
        this.layerCount = layerCount;
        this.field_175901_b = block.getDefaultState();
    }
    
    public int getLayerCount() {
        return this.layerCount;
    }
    
    @Override
    public String toString() {
        String s;
        if (this.field_175902_a >= 3) {
            final ResourceLocation resourceLocation = (ResourceLocation)Block.blockRegistry.getNameForObject(this.func_151536_b());
            s = ((resourceLocation == null) ? "null" : resourceLocation.toString());
            if (this.layerCount > 1) {
                s = this.layerCount + "*" + s;
            }
        }
        else {
            s = Integer.toString(Block.getIdFromBlock(this.func_151536_b()));
            if (this.layerCount > 1) {
                s = this.layerCount + "x" + s;
            }
        }
        final int fillBlockMeta = this.getFillBlockMeta();
        if (fillBlockMeta > 0) {
            s = s + ":" + fillBlockMeta;
        }
        return s;
    }
    
    private Block func_151536_b() {
        return this.field_175901_b.getBlock();
    }
    
    public FlatLayerInfo(final int n, final int n2, final Block block, final int n3) {
        this(n, n2, block);
        this.field_175901_b = block.getStateFromMeta(n3);
    }
    
    public IBlockState func_175900_c() {
        return this.field_175901_b;
    }
    
    public FlatLayerInfo(final int n, final Block block) {
        this(3, n, block);
    }
}
