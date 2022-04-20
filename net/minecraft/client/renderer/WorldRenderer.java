package net.minecraft.client.renderer;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.*;
import java.nio.*;
import com.nquantum.*;
import com.nquantum.util.color.*;
import java.util.*;
import optfine.*;
import net.minecraft.util.*;
import org.apache.logging.log4j.*;
import org.lwjgl.opengl.*;

public class WorldRenderer
{
    private ShortBuffer field_181676_c;
    private ByteBuffer byteBuffer;
    private boolean noColor;
    private FloatBuffer rawFloatBuffer;
    private double xOffset;
    private boolean[] drawnIcons;
    private boolean isDrawing;
    private int field_181678_g;
    private TextureAtlasSprite quadSprite;
    private EnumWorldBlockLayer blockLayer;
    private double zOffset;
    private double yOffset;
    private int vertexCount;
    private VertexFormat vertexFormat;
    private VertexFormatElement field_181677_f;
    private int drawMode;
    private TextureAtlasSprite[] quadSprites;
    private boolean needsUpdate;
    private IntBuffer rawIntBuffer;
    private static final String __OBFID = "CL_00000942";
    
    public void drawMultiTexture() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/client/renderer/WorldRenderer.quadSprites:[Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;
        //     4: ifnull          152
        //     7: invokestatic    optfine/Config.getMinecraft:()Lnet/minecraft/client/Minecraft;
        //    10: invokevirtual   net/minecraft/client/Minecraft.getTextureMapBlocks:()Lnet/minecraft/client/renderer/texture/TextureMap;
        //    13: invokevirtual   net/minecraft/client/renderer/texture/TextureMap.getCountRegisteredSprites:()I
        //    16: istore_1       
        //    17: aload_0        
        //    18: getfield        net/minecraft/client/renderer/WorldRenderer.drawnIcons:[Z
        //    21: arraylength    
        //    22: iload_1        
        //    23: if_icmpgt       35
        //    26: aload_0        
        //    27: iload_1        
        //    28: iconst_1       
        //    29: iadd           
        //    30: newarray        Z
        //    32: putfield        net/minecraft/client/renderer/WorldRenderer.drawnIcons:[Z
        //    35: aload_0        
        //    36: getfield        net/minecraft/client/renderer/WorldRenderer.drawnIcons:[Z
        //    39: iconst_0       
        //    40: invokestatic    java/util/Arrays.fill:([ZZ)V
        //    43: aload_0        
        //    44: getfield        net/minecraft/client/renderer/WorldRenderer.vertexCount:I
        //    47: iconst_4       
        //    48: idiv           
        //    49: istore          4
        //    51: iconst_0       
        //    52: iload           4
        //    54: if_icmpge       136
        //    57: aload_0        
        //    58: getfield        net/minecraft/client/renderer/WorldRenderer.quadSprites:[Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;
        //    61: iconst_0       
        //    62: aaload         
        //    63: astore          6
        //    65: aload           6
        //    67: ifnull          130
        //    70: aload           6
        //    72: invokevirtual   net/minecraft/client/renderer/texture/TextureAtlasSprite.getIndexInMap:()I
        //    75: istore          7
        //    77: aload_0        
        //    78: getfield        net/minecraft/client/renderer/WorldRenderer.drawnIcons:[Z
        //    81: iload           7
        //    83: baload         
        //    84: ifne            130
        //    87: aload           6
        //    89: getstatic       optfine/TextureUtils.iconGrassSideOverlay:Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;
        //    92: if_acmpne       98
        //    95: goto            130
        //    98: aload_0        
        //    99: aload           6
        //   101: iconst_0       
        //   102: invokespecial   net/minecraft/client/renderer/WorldRenderer.drawForIcon:(Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;I)I
        //   105: iconst_1       
        //   106: isub           
        //   107: istore          5
        //   109: iinc            2, 1
        //   112: aload_0        
        //   113: getfield        net/minecraft/client/renderer/WorldRenderer.blockLayer:Lnet/minecraft/util/EnumWorldBlockLayer;
        //   116: getstatic       net/minecraft/util/EnumWorldBlockLayer.TRANSLUCENT:Lnet/minecraft/util/EnumWorldBlockLayer;
        //   119: if_acmpeq       130
        //   122: aload_0        
        //   123: getfield        net/minecraft/client/renderer/WorldRenderer.drawnIcons:[Z
        //   126: iload           7
        //   128: iconst_1       
        //   129: bastore        
        //   130: iinc            5, 1
        //   133: goto            51
        //   136: goto            151
        //   139: aload_0        
        //   140: getstatic       optfine/TextureUtils.iconGrassSideOverlay:Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;
        //   143: iconst_m1      
        //   144: invokespecial   net/minecraft/client/renderer/WorldRenderer.drawForIcon:(Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;I)I
        //   147: pop            
        //   148: iinc            2, 1
        //   151: iconst_0       
        //   152: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0152 (coming from #0151).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public VertexFormat getVertexFormat() {
        return this.vertexFormat;
    }
    
    public WorldRenderer(final int n) {
        this.blockLayer = null;
        this.drawnIcons = new boolean[256];
        this.quadSprites = null;
        this.quadSprite = null;
        this.byteBuffer = GLAllocation.createDirectByteBuffer(n * 4);
        this.rawIntBuffer = this.byteBuffer.asIntBuffer();
        this.field_181676_c = this.byteBuffer.asShortBuffer();
        this.rawFloatBuffer = this.byteBuffer.asFloatBuffer();
    }
    
    public void putNormal(final float n, final float n2, final float n3) {
        final int n4 = ((byte)(n * 127.0f) & 0xFF) | ((byte)(n2 * 127.0f) & 0xFF) << 8 | ((byte)(n3 * 127.0f) & 0xFF) << 16;
        final int n5 = this.vertexFormat.getNextOffset() >> 2;
        final int n6 = (this.vertexCount - 4) * n5 + this.vertexFormat.getNormalOffset() / 4;
        this.rawIntBuffer.put(n6, n4);
        this.rawIntBuffer.put(n6 + n5, n4);
        this.rawIntBuffer.put(n6 + n5 * 2, n4);
        this.rawIntBuffer.put(n6 + n5 * 3, n4);
    }
    
    public void putColorMultiplier(final float n, final float n2, final float n3, final int n4) {
        final int colorIndex = this.getColorIndex(n4);
        int n5;
        int n6;
        int n7;
        if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
            n5 = (int)(255 * n);
            n6 = (int)(255 * n2);
            n7 = (int)(255 * n3);
        }
        else {
            n5 = (int)(255 * n);
            n6 = (int)(255 * n2);
            n7 = (int)(255 * n3);
        }
        if (this.noColor) {}
        if (Asyncware.instance.moduleManager.getModuleByName("World Color").isToggled()) {
            Colors.getColor(n5, n6, n7, 200);
        }
        this.rawIntBuffer.put(colorIndex, -1);
    }
    
    public void func_181674_a(final float n, final float n2, final float n3) {
        final int n4 = this.vertexCount / 4;
        final float[] array = new float[n4];
        while (0 < n4) {
            array[0] = func_181665_a(this.rawFloatBuffer, (float)(n + this.xOffset), (float)(n2 + this.yOffset), (float)(n3 + this.zOffset), this.vertexFormat.func_181719_f(), 0 * this.vertexFormat.getNextOffset());
            int n5 = 0;
            ++n5;
        }
        final Integer[] array2 = new Integer[n4];
        while (0 < array2.length) {
            array2[0] = 0;
            int n6 = 0;
            ++n6;
        }
        Arrays.sort(array2, new WorldRenderer$1(this, array));
        final BitSet set = new BitSet();
        final int nextOffset = this.vertexFormat.getNextOffset();
        final int[] array3 = new int[nextOffset];
        int nextClearBit;
        int n7 = 0;
        while ((nextClearBit = set.nextClearBit(0)) < array2.length) {
            final int intValue = array2[0];
            if (intValue != 0) {
                this.rawIntBuffer.limit(intValue * nextOffset + nextOffset);
                this.rawIntBuffer.position(intValue * nextOffset);
                this.rawIntBuffer.get(array3);
                n7 = intValue;
                array2[intValue];
                this.rawIntBuffer.limit(0 * nextOffset + nextOffset);
                this.rawIntBuffer.position(0 * nextOffset);
                this.rawIntBuffer.put(array3);
            }
            set.set(0);
            ++nextClearBit;
        }
        if (this.quadSprites != null) {
            final TextureAtlasSprite[] array4 = new TextureAtlasSprite[this.vertexCount / 4];
            final int n8 = this.vertexFormat.func_181719_f() / 4 * 4;
            while (0 < array2.length) {
                array4[0] = this.quadSprites[array2[0]];
                ++n7;
            }
            System.arraycopy(array4, 0, this.quadSprites, 0, array4.length);
        }
    }
    
    public void noColor() {
        this.noColor = true;
    }
    
    public void begin(final int drawMode, final VertexFormat vertexFormat) {
        if (this.isDrawing) {
            throw new IllegalStateException("Already building!");
        }
        this.isDrawing = true;
        this.reset();
        this.drawMode = drawMode;
        this.vertexFormat = vertexFormat;
        this.field_181677_f = vertexFormat.getElement(this.field_181678_g);
        this.needsUpdate = false;
        this.byteBuffer.limit(this.byteBuffer.capacity());
        if (Config.isMultiTexture()) {
            if (this.blockLayer != null && this.quadSprites == null) {
                this.quadSprites = new TextureAtlasSprite[this.getBufferQuadSize()];
            }
        }
        else {
            this.quadSprites = null;
        }
    }
    
    public void putColor4(final int n) {
        while (true) {
            this.putColor(n, 1);
            int n2 = 0;
            ++n2;
        }
    }
    
    public void putPosition(final double n, final double n2, final double n3) {
        final int func_181719_f = this.vertexFormat.func_181719_f();
        final int n4 = (this.vertexCount - 4) * func_181719_f;
        while (true) {
            final int n5 = n4 + 0 * func_181719_f;
            final int n6 = n5 + 1;
            final int n7 = n6 + 1;
            this.rawIntBuffer.put(n5, Float.floatToRawIntBits((float)(n + this.xOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(n5))));
            this.rawIntBuffer.put(n6, Float.floatToRawIntBits((float)(n2 + this.yOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(n6))));
            this.rawIntBuffer.put(n7, Float.floatToRawIntBits((float)(n3 + this.zOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(n7))));
            int n8 = 0;
            ++n8;
        }
    }
    
    public boolean isMultiTexture() {
        return this.quadSprites != null;
    }
    
    public void putColorRGB_F4(final float n, final float n2, final float n3) {
        while (true) {
            this.putColorRGB_F(n, n2, n3, 1);
            int n4 = 0;
            ++n4;
        }
    }
    
    public void addVertexData(final int[] array) {
        this.func_181670_b(array.length);
        this.rawIntBuffer.position(this.func_181664_j());
        this.rawIntBuffer.put(array);
        this.vertexCount += array.length / this.vertexFormat.func_181719_f();
    }
    
    public void setVertexState(final State state) {
        this.rawIntBuffer.clear();
        this.func_181670_b(state.getRawBuffer().length);
        this.rawIntBuffer.put(state.getRawBuffer());
        this.vertexCount = state.getVertexCount();
        this.vertexFormat = new VertexFormat(state.getVertexFormat());
        if (State.access$000(state) != null) {
            if (this.quadSprites == null) {
                this.quadSprites = new TextureAtlasSprite[this.getBufferQuadSize()];
            }
            final TextureAtlasSprite[] access$000 = State.access$000(state);
            System.arraycopy(access$000, 0, this.quadSprites, 0, access$000.length);
        }
        else {
            this.quadSprites = null;
        }
    }
    
    public void markDirty() {
        this.needsUpdate = true;
    }
    
    private static float func_181665_a(final FloatBuffer floatBuffer, final float n, final float n2, final float n3, final int n4, final int n5) {
        final float value = floatBuffer.get(n5 + n4 * 0 + 0);
        final float value2 = floatBuffer.get(n5 + n4 * 0 + 1);
        final float value3 = floatBuffer.get(n5 + n4 * 0 + 2);
        final float value4 = floatBuffer.get(n5 + n4 * 1 + 0);
        final float value5 = floatBuffer.get(n5 + n4 * 1 + 1);
        final float value6 = floatBuffer.get(n5 + n4 * 1 + 2);
        final float value7 = floatBuffer.get(n5 + n4 * 2 + 0);
        final float value8 = floatBuffer.get(n5 + n4 * 2 + 1);
        final float value9 = floatBuffer.get(n5 + n4 * 2 + 2);
        final float value10 = floatBuffer.get(n5 + n4 * 3 + 0);
        final float value11 = floatBuffer.get(n5 + n4 * 3 + 1);
        final float value12 = floatBuffer.get(n5 + n4 * 3 + 2);
        final float n6 = (value + value4 + value7 + value10) * 0.25f - n;
        final float n7 = (value2 + value5 + value8 + value11) * 0.25f - n2;
        final float n8 = (value3 + value6 + value9 + value12) * 0.25f - n3;
        return n6 * n6 + n7 * n7 + n8 * n8;
    }
    
    public WorldRenderer normal(final float n, final float n2, final float n3) {
        final int n4 = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.func_181720_d(this.field_181678_g);
        switch (WorldRenderer$2.field_181661_a[this.field_181677_f.getType().ordinal()]) {
            case 1: {
                this.byteBuffer.putFloat(n4, n);
                this.byteBuffer.putFloat(n4 + 4, n2);
                this.byteBuffer.putFloat(n4 + 8, n3);
                break;
            }
            case 2:
            case 3: {
                this.byteBuffer.putInt(n4, (int)n);
                this.byteBuffer.putInt(n4 + 4, (int)n2);
                this.byteBuffer.putInt(n4 + 8, (int)n3);
                break;
            }
            case 4:
            case 5: {
                this.byteBuffer.putShort(n4, (short)((int)n * 32767 & 0xFFFF));
                this.byteBuffer.putShort(n4 + 2, (short)((int)n2 * 32767 & 0xFFFF));
                this.byteBuffer.putShort(n4 + 4, (short)((int)n3 * 32767 & 0xFFFF));
                break;
            }
            case 6:
            case 7: {
                this.byteBuffer.put(n4, (byte)((int)n * 127 & 0xFF));
                this.byteBuffer.put(n4 + 1, (byte)((int)n2 * 127 & 0xFF));
                this.byteBuffer.put(n4 + 2, (byte)((int)n3 * 127 & 0xFF));
                break;
            }
        }
        this.func_181667_k();
        return this;
    }
    
    public void setSprite(final TextureAtlasSprite quadSprite) {
        if (this.quadSprites != null) {
            this.quadSprite = quadSprite;
        }
    }
    
    private int getColorIndex(final int n) {
        return ((this.vertexCount - n) * this.vertexFormat.getNextOffset() + this.vertexFormat.getColorOffset()) / 4;
    }
    
    public void setBlockLayer(final EnumWorldBlockLayer blockLayer) {
        this.blockLayer = blockLayer;
    }
    
    public void putSprite(final TextureAtlasSprite textureAtlasSprite) {
        if (this.quadSprites != null) {
            this.quadSprites[this.vertexCount / 4 - 1] = textureAtlasSprite;
        }
    }
    
    public void reset() {
        this.vertexCount = 0;
        this.field_181677_f = null;
        this.field_181678_g = 0;
        this.quadSprite = null;
    }
    
    public WorldRenderer color(final int n, final int n2, final int n3, final int n4) {
        if (this.needsUpdate) {
            return this;
        }
        final int n5 = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.func_181720_d(this.field_181678_g);
        switch (WorldRenderer$2.field_181661_a[this.field_181677_f.getType().ordinal()]) {
            case 1: {
                this.byteBuffer.putFloat(n5, n / 255.0f);
                this.byteBuffer.putFloat(n5 + 4, n2 / 255.0f);
                this.byteBuffer.putFloat(n5 + 8, n3 / 255.0f);
                this.byteBuffer.putFloat(n5 + 12, n4 / 255.0f);
                break;
            }
            case 2:
            case 3: {
                this.byteBuffer.putFloat(n5, (float)n);
                this.byteBuffer.putFloat(n5 + 4, (float)n2);
                this.byteBuffer.putFloat(n5 + 8, (float)n3);
                this.byteBuffer.putFloat(n5 + 12, (float)n4);
                break;
            }
            case 4:
            case 5: {
                this.byteBuffer.putShort(n5, (short)n);
                this.byteBuffer.putShort(n5 + 2, (short)n2);
                this.byteBuffer.putShort(n5 + 4, (short)n3);
                this.byteBuffer.putShort(n5 + 6, (short)n4);
                break;
            }
            case 6:
            case 7: {
                if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
                    this.byteBuffer.put(n5, (byte)n);
                    this.byteBuffer.put(n5 + 1, (byte)n2);
                    this.byteBuffer.put(n5 + 2, (byte)n3);
                    this.byteBuffer.put(n5 + 3, (byte)n4);
                    break;
                }
                this.byteBuffer.put(n5, (byte)n4);
                this.byteBuffer.put(n5 + 1, (byte)n3);
                this.byteBuffer.put(n5 + 2, (byte)n2);
                this.byteBuffer.put(n5 + 3, (byte)n);
                break;
            }
        }
        this.func_181667_k();
        return this;
    }
    
    private void func_181667_k() {
        ++this.field_181678_g;
        this.field_181678_g %= this.vertexFormat.getElementCount();
        this.field_181677_f = this.vertexFormat.getElement(this.field_181678_g);
        if (this.field_181677_f.getUsage() == VertexFormatElement.EnumUsage.PADDING) {
            this.func_181667_k();
        }
    }
    
    public void putColorRGB_F(final float n, final float n2, final float n3, final int n4) {
        this.putColorRGBA(this.getColorIndex(n4), MathHelper.clamp_int((int)(n * 255.0f), 0, 255), MathHelper.clamp_int((int)(n2 * 255.0f), 0, 255), MathHelper.clamp_int((int)(n3 * 255.0f), 0, 255), 255);
    }
    
    public WorldRenderer lightmap(final int n, final int n2) {
        final int n3 = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.func_181720_d(this.field_181678_g);
        switch (WorldRenderer$2.field_181661_a[this.field_181677_f.getType().ordinal()]) {
            case 1: {
                this.byteBuffer.putFloat(n3, (float)n);
                this.byteBuffer.putFloat(n3 + 4, (float)n2);
                break;
            }
            case 2:
            case 3: {
                this.byteBuffer.putInt(n3, n);
                this.byteBuffer.putInt(n3 + 4, n2);
                break;
            }
            case 4:
            case 5: {
                this.byteBuffer.putShort(n3, (short)n2);
                this.byteBuffer.putShort(n3 + 2, (short)n);
                break;
            }
            case 6:
            case 7: {
                this.byteBuffer.put(n3, (byte)n2);
                this.byteBuffer.put(n3 + 1, (byte)n);
                break;
            }
        }
        this.func_181667_k();
        return this;
    }
    
    public void endVertex() {
        ++this.vertexCount;
        this.func_181670_b(this.vertexFormat.func_181719_f());
    }
    
    public void setTranslation(final double xOffset, final double yOffset, final double zOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;
    }
    
    public WorldRenderer tex(double n, double n2) {
        if (this.quadSprite != null && this.quadSprites != null) {
            n = this.quadSprite.toSingleU((float)n);
            n2 = this.quadSprite.toSingleV((float)n2);
            this.quadSprites[this.vertexCount / 4] = this.quadSprite;
        }
        final int n3 = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.func_181720_d(this.field_181678_g);
        switch (WorldRenderer$2.field_181661_a[this.field_181677_f.getType().ordinal()]) {
            case 1: {
                this.byteBuffer.putFloat(n3, (float)n);
                this.byteBuffer.putFloat(n3 + 4, (float)n2);
                break;
            }
            case 2:
            case 3: {
                this.byteBuffer.putInt(n3, (int)n);
                this.byteBuffer.putInt(n3 + 4, (int)n2);
                break;
            }
            case 4:
            case 5: {
                this.byteBuffer.putShort(n3, (short)n2);
                this.byteBuffer.putShort(n3 + 2, (short)n);
                break;
            }
            case 6:
            case 7: {
                this.byteBuffer.put(n3, (byte)n2);
                this.byteBuffer.put(n3 + 1, (byte)n);
                break;
            }
        }
        this.func_181667_k();
        return this;
    }
    
    private int getBufferQuadSize() {
        return this.rawIntBuffer.capacity() * 4 / (this.vertexFormat.func_181719_f() * 4);
    }
    
    public void putBrightness4(final int n, final int n2, final int n3, final int n4) {
        final int n5 = (this.vertexCount - 4) * this.vertexFormat.func_181719_f() + this.vertexFormat.getUvOffsetById(1) / 4;
        final int n6 = this.vertexFormat.getNextOffset() >> 2;
        this.rawIntBuffer.put(n5, n);
        this.rawIntBuffer.put(n5 + n6, n2);
        this.rawIntBuffer.put(n5 + n6 * 2, n3);
        this.rawIntBuffer.put(n5 + n6 * 3, n4);
    }
    
    private void func_181670_b(final int n) {
        if (n > this.rawIntBuffer.remaining()) {
            final int capacity = this.byteBuffer.capacity();
            final int n2 = capacity % 2097152;
            final int n3 = n2 + (((this.rawIntBuffer.position() + n) * 4 - n2) / 2097152 + 1) * 2097152;
            LogManager.getLogger().warn("Needed to grow BufferBuilder buffer: Old size " + capacity + " bytes, new size " + n3 + " bytes.");
            final int position = this.rawIntBuffer.position();
            final ByteBuffer directByteBuffer = GLAllocation.createDirectByteBuffer(n3);
            this.byteBuffer.position(0);
            directByteBuffer.put(this.byteBuffer);
            directByteBuffer.rewind();
            this.byteBuffer = directByteBuffer;
            this.rawFloatBuffer = this.byteBuffer.asFloatBuffer().asReadOnlyBuffer();
            (this.rawIntBuffer = this.byteBuffer.asIntBuffer()).position(position);
            (this.field_181676_c = this.byteBuffer.asShortBuffer()).position(position << 1);
            if (this.quadSprites != null) {
                final TextureAtlasSprite[] quadSprites = this.quadSprites;
                System.arraycopy(quadSprites, 0, this.quadSprites = new TextureAtlasSprite[this.getBufferQuadSize()], 0, Math.min(quadSprites.length, this.quadSprites.length));
            }
        }
    }
    
    private int func_181664_j() {
        return this.vertexCount * this.vertexFormat.func_181719_f();
    }
    
    public int getDrawMode() {
        return this.drawMode;
    }
    
    public void finishDrawing() {
        if (!this.isDrawing) {
            throw new IllegalStateException("Not building!");
        }
        this.isDrawing = false;
        this.byteBuffer.position(0);
        this.byteBuffer.limit(this.func_181664_j() * 4);
    }
    
    public ByteBuffer getByteBuffer() {
        return this.byteBuffer;
    }
    
    private int drawForIcon(final TextureAtlasSprite textureAtlasSprite, final int n) {
        GL11.glBindTexture(3553, textureAtlasSprite.glSpriteTextureId);
        for (int n2 = this.vertexCount / 4, i = n; i < n2; ++i) {
            if (this.quadSprites[i] == textureAtlasSprite) {}
        }
        return -1;
    }
    
    private void putColor(final int n, final int n2) {
        this.putColorRGBA(this.getColorIndex(n2), n >> 16 & 0xFF, n >> 8 & 0xFF, n & 0xFF, 255);
    }
    
    public int getVertexCount() {
        return this.vertexCount;
    }
    
    private void putColorRGBA(final int n, final int n2, final int n3, final int n4, final int n5) {
        if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
            this.rawIntBuffer.put(n, n5 << 24 | n4 << 16 | n3 << 8 | n2);
        }
        else {
            this.rawIntBuffer.put(n, n2 << 24 | n3 << 16 | n4 << 8 | n5);
        }
    }
    
    public State func_181672_a() {
        this.rawIntBuffer.rewind();
        final int func_181664_j = this.func_181664_j();
        this.rawIntBuffer.limit(func_181664_j);
        final int[] array = new int[func_181664_j];
        this.rawIntBuffer.get(array);
        this.rawIntBuffer.limit(this.rawIntBuffer.capacity());
        this.rawIntBuffer.position(func_181664_j);
        TextureAtlasSprite[] array2 = null;
        if (this.quadSprites != null) {
            final int n = this.vertexCount / 4;
            array2 = new TextureAtlasSprite[n];
            System.arraycopy(this.quadSprites, 0, array2, 0, n);
        }
        return new State(array, new VertexFormat(this.vertexFormat), array2);
    }
    
    public WorldRenderer pos(final double n, final double n2, final double n3) {
        final int n4 = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.func_181720_d(this.field_181678_g);
        switch (WorldRenderer$2.field_181661_a[this.field_181677_f.getType().ordinal()]) {
            case 1: {
                this.byteBuffer.putFloat(n4, (float)(n + this.xOffset));
                this.byteBuffer.putFloat(n4 + 4, (float)(n2 + this.yOffset));
                this.byteBuffer.putFloat(n4 + 8, (float)(n3 + this.zOffset));
                break;
            }
            case 2:
            case 3: {
                this.byteBuffer.putInt(n4, Float.floatToRawIntBits((float)(n + this.xOffset)));
                this.byteBuffer.putInt(n4 + 4, Float.floatToRawIntBits((float)(n2 + this.yOffset)));
                this.byteBuffer.putInt(n4 + 8, Float.floatToRawIntBits((float)(n3 + this.zOffset)));
                break;
            }
            case 4:
            case 5: {
                this.byteBuffer.putShort(n4, (short)(n + this.xOffset));
                this.byteBuffer.putShort(n4 + 2, (short)(n2 + this.yOffset));
                this.byteBuffer.putShort(n4 + 4, (short)(n3 + this.zOffset));
                break;
            }
            case 6:
            case 7: {
                this.byteBuffer.put(n4, (byte)(n + this.xOffset));
                this.byteBuffer.put(n4 + 1, (byte)(n2 + this.yOffset));
                this.byteBuffer.put(n4 + 2, (byte)(n3 + this.zOffset));
                break;
            }
        }
        this.func_181667_k();
        return this;
    }
    
    public WorldRenderer color(final float n, final float n2, final float n3, final float n4) {
        return this.color((int)(n * 255.0f), (int)(n2 * 255.0f), (int)(n3 * 255.0f), 25500);
    }
    
    private void draw(final int n, final int n2) {
        final int n3 = n2 - n;
        if (n3 > 0) {
            GL11.glDrawArrays(this.drawMode, n * 4, n3 * 4);
        }
    }
    
    static final class WorldRenderer$2
    {
        private static final String __OBFID = "CL_00002569";
        
        static {
            (WorldRenderer$2.field_181661_a = new int[VertexFormatElement.EnumType.values().length])[VertexFormatElement.EnumType.FLOAT.ordinal()] = 1;
            WorldRenderer$2.field_181661_a[VertexFormatElement.EnumType.UINT.ordinal()] = 2;
            WorldRenderer$2.field_181661_a[VertexFormatElement.EnumType.INT.ordinal()] = 3;
            WorldRenderer$2.field_181661_a[VertexFormatElement.EnumType.USHORT.ordinal()] = 4;
            WorldRenderer$2.field_181661_a[VertexFormatElement.EnumType.SHORT.ordinal()] = 5;
            WorldRenderer$2.field_181661_a[VertexFormatElement.EnumType.UBYTE.ordinal()] = 6;
            WorldRenderer$2.field_181661_a[VertexFormatElement.EnumType.BYTE.ordinal()] = 7;
        }
    }
    
    public class State
    {
        private final int[] stateRawBuffer;
        final WorldRenderer this$0;
        private TextureAtlasSprite[] stateQuadSprites;
        private final VertexFormat stateVertexFormat;
        private static final String __OBFID = "CL_00002568";
        
        public State(final WorldRenderer this$0, final int[] stateRawBuffer, final VertexFormat stateVertexFormat) {
            this.this$0 = this$0;
            this.stateRawBuffer = stateRawBuffer;
            this.stateVertexFormat = stateVertexFormat;
        }
        
        public VertexFormat getVertexFormat() {
            return this.stateVertexFormat;
        }
        
        public State(final WorldRenderer this$0, final int[] stateRawBuffer, final VertexFormat stateVertexFormat, final TextureAtlasSprite[] stateQuadSprites) {
            this.this$0 = this$0;
            this.stateRawBuffer = stateRawBuffer;
            this.stateVertexFormat = stateVertexFormat;
            this.stateQuadSprites = stateQuadSprites;
        }
        
        public int[] getRawBuffer() {
            return this.stateRawBuffer;
        }
        
        static TextureAtlasSprite[] access$000(final State state) {
            return state.stateQuadSprites;
        }
        
        public int getVertexCount() {
            return this.stateRawBuffer.length / this.stateVertexFormat.func_181719_f();
        }
    }
}
