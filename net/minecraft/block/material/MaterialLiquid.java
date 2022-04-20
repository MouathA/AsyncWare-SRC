package net.minecraft.block.material;

public class MaterialLiquid extends Material
{
    @Override
    public boolean blocksMovement() {
        return false;
    }
    
    @Override
    public boolean isSolid() {
        return false;
    }
    
    public MaterialLiquid(final MapColor mapColor) {
        super(mapColor);
        this.setReplaceable();
        this.setNoPushMobility();
    }
    
    @Override
    public boolean isLiquid() {
        return true;
    }
}
