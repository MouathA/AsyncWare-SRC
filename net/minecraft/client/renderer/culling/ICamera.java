package net.minecraft.client.renderer.culling;

import net.minecraft.util.*;

public interface ICamera
{
    void setPosition(final double p0, final double p1, final double p2);
    
    boolean isBoundingBoxInFrustum(final AxisAlignedBB p0);
}
