package net.minecraft.dispenser;

public class PositionImpl implements IPosition
{
    protected final double z;
    protected final double y;
    protected final double x;
    
    @Override
    public double getY() {
        return this.y;
    }
    
    public PositionImpl(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    @Override
    public double getX() {
        return this.x;
    }
    
    @Override
    public double getZ() {
        return this.z;
    }
}
