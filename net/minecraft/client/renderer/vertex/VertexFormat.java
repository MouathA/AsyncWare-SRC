package net.minecraft.client.renderer.vertex;

import java.util.*;
import org.apache.logging.log4j.*;
import com.google.common.collect.*;

public class VertexFormat
{
    private int nextOffset;
    private List uvOffsetsById;
    private final List offsets;
    private int normalElementOffset;
    private final List elements;
    private int colorElementOffset;
    private static final Logger LOGGER;
    
    public int getNormalOffset() {
        return this.normalElementOffset;
    }
    
    public boolean hasColor() {
        return this.colorElementOffset >= 0;
    }
    
    public void clear() {
        this.elements.clear();
        this.offsets.clear();
        this.colorElementOffset = -1;
        this.uvOffsetsById.clear();
        this.normalElementOffset = -1;
        this.nextOffset = 0;
    }
    
    public VertexFormat func_181721_a(final VertexFormatElement p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   net/minecraft/client/renderer/vertex/VertexFormatElement.isPositionElement:()Z
        //     4: ifeq            23
        //     7: aload_0        
        //     8: if_icmpge       23
        //    11: getstatic       net/minecraft/client/renderer/vertex/VertexFormat.LOGGER:Lorg/apache/logging/log4j/Logger;
        //    14: ldc             "VertexFormat error: Trying to add a position VertexFormatElement when one already exists, ignoring."
        //    16: invokeinterface org/apache/logging/log4j/Logger.warn:(Ljava/lang/String;)V
        //    21: aload_0        
        //    22: areturn        
        //    23: aload_0        
        //    24: getfield        net/minecraft/client/renderer/vertex/VertexFormat.elements:Ljava/util/List;
        //    27: aload_1        
        //    28: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //    33: pop            
        //    34: aload_0        
        //    35: getfield        net/minecraft/client/renderer/vertex/VertexFormat.offsets:Ljava/util/List;
        //    38: aload_0        
        //    39: getfield        net/minecraft/client/renderer/vertex/VertexFormat.nextOffset:I
        //    42: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //    45: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //    50: pop            
        //    51: getstatic       net/minecraft/client/renderer/vertex/VertexFormat$1.$SwitchMap$net$minecraft$client$renderer$vertex$VertexFormatElement$EnumUsage:[I
        //    54: aload_1        
        //    55: invokevirtual   net/minecraft/client/renderer/vertex/VertexFormatElement.getUsage:()Lnet/minecraft/client/renderer/vertex/VertexFormatElement$EnumUsage;
        //    58: invokevirtual   net/minecraft/client/renderer/vertex/VertexFormatElement$EnumUsage.ordinal:()I
        //    61: iaload         
        //    62: tableswitch {
        //                2: 88
        //                3: 99
        //                4: 110
        //          default: 130
        //        }
        //    88: aload_0        
        //    89: aload_0        
        //    90: getfield        net/minecraft/client/renderer/vertex/VertexFormat.nextOffset:I
        //    93: putfield        net/minecraft/client/renderer/vertex/VertexFormat.normalElementOffset:I
        //    96: goto            130
        //    99: aload_0        
        //   100: aload_0        
        //   101: getfield        net/minecraft/client/renderer/vertex/VertexFormat.nextOffset:I
        //   104: putfield        net/minecraft/client/renderer/vertex/VertexFormat.colorElementOffset:I
        //   107: goto            130
        //   110: aload_0        
        //   111: getfield        net/minecraft/client/renderer/vertex/VertexFormat.uvOffsetsById:Ljava/util/List;
        //   114: aload_1        
        //   115: invokevirtual   net/minecraft/client/renderer/vertex/VertexFormatElement.getIndex:()I
        //   118: aload_0        
        //   119: getfield        net/minecraft/client/renderer/vertex/VertexFormat.nextOffset:I
        //   122: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   125: invokeinterface java/util/List.add:(ILjava/lang/Object;)V
        //   130: aload_0        
        //   131: dup            
        //   132: getfield        net/minecraft/client/renderer/vertex/VertexFormat.nextOffset:I
        //   135: aload_1        
        //   136: invokevirtual   net/minecraft/client/renderer/vertex/VertexFormatElement.getSize:()I
        //   139: iadd           
        //   140: putfield        net/minecraft/client/renderer/vertex/VertexFormat.nextOffset:I
        //   143: aload_0        
        //   144: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public int getNextOffset() {
        return this.nextOffset;
    }
    
    @Override
    public String toString() {
        String s = "format: " + this.elements.size() + " elements: ";
        while (0 < this.elements.size()) {
            s += this.elements.get(0).toString();
            if (0 != this.elements.size() - 1) {
                s += " ";
            }
            int n = 0;
            ++n;
        }
        return s;
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
    
    public int getUvOffsetById(final int n) {
        return this.uvOffsetsById.get(n);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o != null && this.getClass() == o.getClass()) {
            final VertexFormat vertexFormat = (VertexFormat)o;
            return this.nextOffset == vertexFormat.nextOffset && this.elements.equals(vertexFormat.elements) && this.offsets.equals(vertexFormat.offsets);
        }
        return false;
    }
    
    public int func_181719_f() {
        return this.getNextOffset() / 4;
    }
    
    public VertexFormat() {
        this.elements = Lists.newArrayList();
        this.offsets = Lists.newArrayList();
        this.nextOffset = 0;
        this.colorElementOffset = -1;
        this.uvOffsetsById = Lists.newArrayList();
        this.normalElementOffset = -1;
    }
    
    public boolean hasNormal() {
        return this.normalElementOffset >= 0;
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * this.elements.hashCode() + this.offsets.hashCode()) + this.nextOffset;
    }
    
    public int getColorOffset() {
        return this.colorElementOffset;
    }
    
    public boolean hasUvOffset(final int n) {
        return this.uvOffsetsById.size() - 1 >= n;
    }
    
    public VertexFormat(final VertexFormat vertexFormat) {
        this();
        while (0 < vertexFormat.getElementCount()) {
            this.func_181721_a(vertexFormat.getElement(0));
            int n = 0;
            ++n;
        }
        this.nextOffset = vertexFormat.getNextOffset();
    }
    
    public int getElementCount() {
        return this.elements.size();
    }
    
    public int func_181720_d(final int n) {
        return this.offsets.get(n);
    }
    
    public VertexFormatElement getElement(final int n) {
        return this.elements.get(n);
    }
    
    public List getElements() {
        return this.elements;
    }
}
