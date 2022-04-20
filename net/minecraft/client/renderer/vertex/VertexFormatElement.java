package net.minecraft.client.renderer.vertex;

import org.apache.logging.log4j.*;

public class VertexFormatElement
{
    private int index;
    private int elementCount;
    private final EnumUsage usage;
    private static final Logger LOGGER;
    private final EnumType type;
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o != null && this.getClass() == o.getClass()) {
            final VertexFormatElement vertexFormatElement = (VertexFormatElement)o;
            return this.elementCount == vertexFormatElement.elementCount && this.index == vertexFormatElement.index && this.type == vertexFormatElement.type && this.usage == vertexFormatElement.usage;
        }
        return false;
    }
    
    public final int getElementCount() {
        return this.elementCount;
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * (31 * this.type.hashCode() + this.usage.hashCode()) + this.index) + this.elementCount;
    }
    
    public final int getSize() {
        return this.type.getSize() * this.elementCount;
    }
    
    public final EnumType getType() {
        return this.type;
    }
    
    public final EnumUsage getUsage() {
        return this.usage;
    }
    
    @Override
    public String toString() {
        return this.elementCount + "," + this.usage.getDisplayName() + "," + this.type.getDisplayName();
    }
    
    public VertexFormatElement(final int index, final EnumType type, final EnumUsage usage, final int elementCount) {
        if (usage != 0) {
            VertexFormatElement.LOGGER.warn("Multiple vertex elements of the same type other than UVs are not supported. Forcing type to UV.");
            this.usage = EnumUsage.UV;
        }
        else {
            this.usage = usage;
        }
        this.type = type;
        this.index = index;
        this.elementCount = elementCount;
    }
    
    public final int getIndex() {
        return this.index;
    }
    
    public final boolean isPositionElement() {
        return this.usage == EnumUsage.POSITION;
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
    
    public enum EnumUsage
    {
        PADDING("PADDING", 6, "Padding"), 
        COLOR("COLOR", 2, "Vertex Color"), 
        MATRIX("MATRIX", 4, "Bone Matrix");
        
        private final String displayName;
        
        POSITION("POSITION", 0, "Position"), 
        NORMAL("NORMAL", 1, "Normal");
        
        private static final EnumUsage[] $VALUES;
        
        BLEND_WEIGHT("BLEND_WEIGHT", 5, "Blend Weight"), 
        UV("UV", 3, "UV");
        
        public String getDisplayName() {
            return this.displayName;
        }
        
        static {
            $VALUES = new EnumUsage[] { EnumUsage.POSITION, EnumUsage.NORMAL, EnumUsage.COLOR, EnumUsage.UV, EnumUsage.MATRIX, EnumUsage.BLEND_WEIGHT, EnumUsage.PADDING };
        }
        
        private EnumUsage(final String s, final int n, final String displayName) {
            this.displayName = displayName;
        }
    }
    
    public enum EnumType
    {
        private static final EnumType[] $VALUES;
        
        SHORT("SHORT", 4, 2, "Short", 5122), 
        USHORT("USHORT", 3, 2, "Unsigned Short", 5123);
        
        private final String displayName;
        
        FLOAT("FLOAT", 0, 4, "Float", 5126), 
        BYTE("BYTE", 2, 1, "Byte", 5120), 
        UINT("UINT", 5, 4, "Unsigned Int", 5125), 
        UBYTE("UBYTE", 1, 1, "Unsigned Byte", 5121), 
        INT("INT", 6, 4, "Int", 5124);
        
        private final int size;
        private final int glConstant;
        
        public int getGlConstant() {
            return this.glConstant;
        }
        
        public int getSize() {
            return this.size;
        }
        
        public String getDisplayName() {
            return this.displayName;
        }
        
        static {
            $VALUES = new EnumType[] { EnumType.FLOAT, EnumType.UBYTE, EnumType.BYTE, EnumType.USHORT, EnumType.SHORT, EnumType.UINT, EnumType.INT };
        }
        
        private EnumType(final String s, final int n, final int size, final String displayName, final int glConstant) {
            this.size = size;
            this.displayName = displayName;
            this.glConstant = glConstant;
        }
    }
}
