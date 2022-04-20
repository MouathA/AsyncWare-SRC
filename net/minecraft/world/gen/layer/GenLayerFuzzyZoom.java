package net.minecraft.world.gen.layer;

public class GenLayerFuzzyZoom extends GenLayerZoom
{
    @Override
    protected int selectModeOrRandom(final int n, final int n2, final int n3, final int n4) {
        return this.selectRandom(n, n2, n3, n4);
    }
    
    public GenLayerFuzzyZoom(final long n, final GenLayer genLayer) {
        super(n, genLayer);
    }
}
