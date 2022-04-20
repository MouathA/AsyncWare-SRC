package net.minecraft.client.resources.model;

import java.util.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import org.lwjgl.util.vector.*;

public enum ModelRotation
{
    X270_Y180("X270_Y180", 14, 270, 180);
    
    private final int quartersY;
    
    X0_Y0("X0_Y0", 0, 0, 0);
    
    private final int combinedXY;
    private final Matrix4f matrix4d;
    
    X180_Y270("X180_Y270", 11, 180, 270), 
    X90_Y90("X90_Y90", 5, 90, 90), 
    X90_Y180("X90_Y180", 6, 90, 180);
    
    private static final ModelRotation[] $VALUES;
    
    X180_Y0("X180_Y0", 8, 180, 0), 
    X180_Y180("X180_Y180", 10, 180, 180), 
    X90_Y270("X90_Y270", 7, 90, 270), 
    X0_Y90("X0_Y90", 1, 0, 90), 
    X0_Y270("X0_Y270", 3, 0, 270), 
    X0_Y180("X0_Y180", 2, 0, 180), 
    X270_Y90("X270_Y90", 13, 270, 90), 
    X270_Y270("X270_Y270", 15, 270, 270), 
    X270_Y0("X270_Y0", 12, 270, 0);
    
    private static final Map mapRotations;
    
    X180_Y90("X180_Y90", 9, 180, 90);
    
    private final int quartersX;
    
    X90_Y0("X90_Y0", 4, 90, 0);
    
    public Matrix4f getMatrix4d() {
        return this.matrix4d;
    }
    
    public int rotateVertex(final EnumFacing enumFacing, final int n) {
        int n2 = n;
        if (enumFacing.getAxis() == EnumFacing.Axis.X) {
            n2 = (n + this.quartersX) % 4;
        }
        EnumFacing rotateAround = enumFacing;
        while (0 < this.quartersX) {
            rotateAround = rotateAround.rotateAround(EnumFacing.Axis.X);
            int n3 = 0;
            ++n3;
        }
        if (rotateAround.getAxis() == EnumFacing.Axis.Y) {
            n2 = (n2 + this.quartersY) % 4;
        }
        return n2;
    }
    
    static {
        $VALUES = new ModelRotation[] { ModelRotation.X0_Y0, ModelRotation.X0_Y90, ModelRotation.X0_Y180, ModelRotation.X0_Y270, ModelRotation.X90_Y0, ModelRotation.X90_Y90, ModelRotation.X90_Y180, ModelRotation.X90_Y270, ModelRotation.X180_Y0, ModelRotation.X180_Y90, ModelRotation.X180_Y180, ModelRotation.X180_Y270, ModelRotation.X270_Y0, ModelRotation.X270_Y90, ModelRotation.X270_Y180, ModelRotation.X270_Y270 };
        mapRotations = Maps.newHashMap();
        final ModelRotation[] values = values();
        while (0 < values.length) {
            final ModelRotation modelRotation = values[0];
            ModelRotation.mapRotations.put(modelRotation.combinedXY, modelRotation);
            int n = 0;
            ++n;
        }
    }
    
    public EnumFacing rotateFace(final EnumFacing enumFacing) {
        EnumFacing enumFacing2 = enumFacing;
        int n = 0;
        while (0 < this.quartersX) {
            enumFacing2 = enumFacing2.rotateAround(EnumFacing.Axis.X);
            ++n;
        }
        if (enumFacing2.getAxis() != EnumFacing.Axis.Y) {
            while (0 < this.quartersY) {
                enumFacing2 = enumFacing2.rotateAround(EnumFacing.Axis.Y);
                ++n;
            }
        }
        return enumFacing2;
    }
    
    public static ModelRotation getModelRotation(final int n, final int n2) {
        return ModelRotation.mapRotations.get(combineXY(MathHelper.normalizeAngle(n, 360), MathHelper.normalizeAngle(n2, 360)));
    }
    
    private ModelRotation(final String s, final int n, final int n2, final int n3) {
        this.combinedXY = combineXY(n2, n3);
        this.matrix4d = new Matrix4f();
        final Matrix4f matrix4f = new Matrix4f();
        matrix4f.setIdentity();
        Matrix4f.rotate(-n2 * 0.017453292f, new Vector3f(1.0f, 0.0f, 0.0f), matrix4f, matrix4f);
        this.quartersX = MathHelper.abs_int(n2 / 90);
        final Matrix4f matrix4f2 = new Matrix4f();
        matrix4f2.setIdentity();
        Matrix4f.rotate(-n3 * 0.017453292f, new Vector3f(0.0f, 1.0f, 0.0f), matrix4f2, matrix4f2);
        this.quartersY = MathHelper.abs_int(n3 / 90);
        Matrix4f.mul(matrix4f2, matrix4f, this.matrix4d);
    }
    
    private static int combineXY(final int n, final int n2) {
        return n * 360 + n2;
    }
}
