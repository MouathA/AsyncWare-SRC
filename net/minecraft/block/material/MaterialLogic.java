package net.minecraft.block.material;

public class MaterialLogic extends Material
{
    @Override
    public boolean blocksLight() {
        return false;
    }
    
    @Override
    public boolean blocksMovement() {
        return false;
    }
    
    @Override
    public boolean isSolid() {
        return false;
    }
    
    public MaterialLogic(final MapColor mapColor) {
        super(mapColor);
        this.setAdventureModeExempt();
    }
}
