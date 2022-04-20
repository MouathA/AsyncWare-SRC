package net.minecraft.client.renderer;

import net.minecraft.util.*;

public enum EnumFaceDirection
{
    SOUTH("SOUTH", 3, new VertexInformation[] { new VertexInformation(Constants.WEST_INDEX, Constants.UP_INDEX, Constants.SOUTH_INDEX, null), new VertexInformation(Constants.WEST_INDEX, Constants.DOWN_INDEX, Constants.SOUTH_INDEX, null), new VertexInformation(Constants.EAST_INDEX, Constants.DOWN_INDEX, Constants.SOUTH_INDEX, null), new VertexInformation(Constants.EAST_INDEX, Constants.UP_INDEX, Constants.SOUTH_INDEX, null) });
    
    private final VertexInformation[] vertexInfos;
    
    DOWN("DOWN", 0, new VertexInformation[] { new VertexInformation(Constants.WEST_INDEX, Constants.DOWN_INDEX, Constants.SOUTH_INDEX, null), new VertexInformation(Constants.WEST_INDEX, Constants.DOWN_INDEX, Constants.NORTH_INDEX, null), new VertexInformation(Constants.EAST_INDEX, Constants.DOWN_INDEX, Constants.NORTH_INDEX, null), new VertexInformation(Constants.EAST_INDEX, Constants.DOWN_INDEX, Constants.SOUTH_INDEX, null) }), 
    UP("UP", 1, new VertexInformation[] { new VertexInformation(Constants.WEST_INDEX, Constants.UP_INDEX, Constants.NORTH_INDEX, null), new VertexInformation(Constants.WEST_INDEX, Constants.UP_INDEX, Constants.SOUTH_INDEX, null), new VertexInformation(Constants.EAST_INDEX, Constants.UP_INDEX, Constants.SOUTH_INDEX, null), new VertexInformation(Constants.EAST_INDEX, Constants.UP_INDEX, Constants.NORTH_INDEX, null) }), 
    NORTH("NORTH", 2, new VertexInformation[] { new VertexInformation(Constants.EAST_INDEX, Constants.UP_INDEX, Constants.NORTH_INDEX, null), new VertexInformation(Constants.EAST_INDEX, Constants.DOWN_INDEX, Constants.NORTH_INDEX, null), new VertexInformation(Constants.WEST_INDEX, Constants.DOWN_INDEX, Constants.NORTH_INDEX, null), new VertexInformation(Constants.WEST_INDEX, Constants.UP_INDEX, Constants.NORTH_INDEX, null) }), 
    WEST("WEST", 4, new VertexInformation[] { new VertexInformation(Constants.WEST_INDEX, Constants.UP_INDEX, Constants.NORTH_INDEX, null), new VertexInformation(Constants.WEST_INDEX, Constants.DOWN_INDEX, Constants.NORTH_INDEX, null), new VertexInformation(Constants.WEST_INDEX, Constants.DOWN_INDEX, Constants.SOUTH_INDEX, null), new VertexInformation(Constants.WEST_INDEX, Constants.UP_INDEX, Constants.SOUTH_INDEX, null) });
    
    private static final EnumFaceDirection[] $VALUES;
    private static final EnumFaceDirection[] facings;
    
    EAST("EAST", 5, new VertexInformation[] { new VertexInformation(Constants.EAST_INDEX, Constants.UP_INDEX, Constants.SOUTH_INDEX, null), new VertexInformation(Constants.EAST_INDEX, Constants.DOWN_INDEX, Constants.SOUTH_INDEX, null), new VertexInformation(Constants.EAST_INDEX, Constants.DOWN_INDEX, Constants.NORTH_INDEX, null), new VertexInformation(Constants.EAST_INDEX, Constants.UP_INDEX, Constants.NORTH_INDEX, null) });
    
    private EnumFaceDirection(final String s, final int n, final VertexInformation[] vertexInfos) {
        this.vertexInfos = vertexInfos;
    }
    
    public static EnumFaceDirection getFacing(final EnumFacing enumFacing) {
        return EnumFaceDirection.facings[enumFacing.getIndex()];
    }
    
    static {
        $VALUES = new EnumFaceDirection[] { EnumFaceDirection.DOWN, EnumFaceDirection.UP, EnumFaceDirection.NORTH, EnumFaceDirection.SOUTH, EnumFaceDirection.WEST, EnumFaceDirection.EAST };
        (facings = new EnumFaceDirection[6])[Constants.DOWN_INDEX] = EnumFaceDirection.DOWN;
        EnumFaceDirection.facings[Constants.UP_INDEX] = EnumFaceDirection.UP;
        EnumFaceDirection.facings[Constants.NORTH_INDEX] = EnumFaceDirection.NORTH;
        EnumFaceDirection.facings[Constants.SOUTH_INDEX] = EnumFaceDirection.SOUTH;
        EnumFaceDirection.facings[Constants.WEST_INDEX] = EnumFaceDirection.WEST;
        EnumFaceDirection.facings[Constants.EAST_INDEX] = EnumFaceDirection.EAST;
    }
    
    public VertexInformation func_179025_a(final int n) {
        return this.vertexInfos[n];
    }
    
    public static class VertexInformation
    {
        public final int field_179184_a;
        public final int field_179182_b;
        public final int field_179183_c;
        
        private VertexInformation(final int field_179184_a, final int field_179182_b, final int field_179183_c) {
            this.field_179184_a = field_179184_a;
            this.field_179182_b = field_179182_b;
            this.field_179183_c = field_179183_c;
        }
        
        VertexInformation(final int n, final int n2, final int n3, final EnumFaceDirection$1 object) {
            this(n, n2, n3);
        }
    }
    
    public static final class Constants
    {
        public static final int NORTH_INDEX;
        public static final int UP_INDEX;
        public static final int EAST_INDEX;
        public static final int SOUTH_INDEX;
        public static final int WEST_INDEX;
        public static final int DOWN_INDEX;
        
        static {
            SOUTH_INDEX = EnumFacing.SOUTH.getIndex();
            UP_INDEX = EnumFacing.UP.getIndex();
            EAST_INDEX = EnumFacing.EAST.getIndex();
            NORTH_INDEX = EnumFacing.NORTH.getIndex();
            DOWN_INDEX = EnumFacing.DOWN.getIndex();
            WEST_INDEX = EnumFacing.WEST.getIndex();
        }
    }
}
