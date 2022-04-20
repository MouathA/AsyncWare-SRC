package net.minecraft.client.renderer.block.model;

import net.minecraft.util.*;
import org.lwjgl.util.vector.*;

public class BlockPartRotation
{
    public final EnumFacing.Axis axis;
    public final Vector3f origin;
    public final boolean rescale;
    public final float angle;
    
    public BlockPartRotation(final Vector3f origin, final EnumFacing.Axis axis, final float angle, final boolean rescale) {
        this.origin = origin;
        this.axis = axis;
        this.angle = angle;
        this.rescale = rescale;
    }
}
