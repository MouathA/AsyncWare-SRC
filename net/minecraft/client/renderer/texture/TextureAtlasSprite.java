package net.minecraft.client.renderer.texture;

import net.minecraft.client.resources.data.*;
import com.google.common.collect.*;
import java.awt.image.*;
import java.io.*;
import net.minecraft.client.resources.*;
import net.minecraft.util.*;
import optfine.*;
import java.util.*;

public class TextureAtlasSprite
{
    public int sheetWidth;
    private float maxV;
    private int indexInMap;
    protected int width;
    private float minU;
    private static String locationNameClock;
    public int mipmapLevels;
    protected List framesTextureData;
    public boolean isSpriteSingle;
    protected int originX;
    public float baseV;
    protected int height;
    public int sheetHeight;
    protected boolean rotated;
    private final String iconName;
    protected int tickCounter;
    private static String locationNameCompass;
    protected int frameCounter;
    public int glSpriteTextureId;
    protected int[][] interpolatedFrameData;
    private float minV;
    private AnimationMetadataSection animationMetadata;
    private static final String __OBFID = "CL_00001062";
    public TextureAtlasSprite spriteSingle;
    protected int originY;
    private float maxU;
    public float baseU;
    
    private void allocateFrameTextureData(final int n) {
        if (this.framesTextureData.size() <= n) {
            for (int i = this.framesTextureData.size(); i <= n; ++i) {
                this.framesTextureData.add(null);
            }
        }
        if (this.spriteSingle != null) {
            this.spriteSingle.allocateFrameTextureData(n);
        }
    }
    
    private TextureAtlasSprite(final TextureAtlasSprite textureAtlasSprite) {
        this.framesTextureData = Lists.newArrayList();
        this.indexInMap = -1;
        this.glSpriteTextureId = -1;
        this.spriteSingle = null;
        this.isSpriteSingle = false;
        this.mipmapLevels = 0;
        this.iconName = textureAtlasSprite.iconName;
        this.isSpriteSingle = true;
    }
    
    public double getSpriteU16(final float n) {
        return (n - this.minU) / (this.maxU - this.minU) * 16.0f;
    }
    
    public float toSingleU(float n) {
        n -= this.baseU;
        n *= this.sheetWidth / (float)this.width;
        return n;
    }
    
    public int getOriginY() {
        return this.originY;
    }
    
    public void loadSprite(final BufferedImage[] p0, final AnimationMetadataSection p1) throws IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokespecial   net/minecraft/client/renderer/texture/TextureAtlasSprite.resetSprite:()V
        //     4: aload_1        
        //     5: iconst_0       
        //     6: aaload         
        //     7: invokevirtual   java/awt/image/BufferedImage.getWidth:()I
        //    10: istore_3       
        //    11: aload_1        
        //    12: iconst_0       
        //    13: aaload         
        //    14: invokevirtual   java/awt/image/BufferedImage.getHeight:()I
        //    17: istore          4
        //    19: aload_0        
        //    20: iload_3        
        //    21: putfield        net/minecraft/client/renderer/texture/TextureAtlasSprite.width:I
        //    24: aload_0        
        //    25: iload           4
        //    27: putfield        net/minecraft/client/renderer/texture/TextureAtlasSprite.height:I
        //    30: aload_1        
        //    31: arraylength    
        //    32: anewarray       [I
        //    35: astore          5
        //    37: iconst_0       
        //    38: aload_1        
        //    39: arraylength    
        //    40: if_icmpge       195
        //    43: aload_1        
        //    44: iconst_0       
        //    45: aaload         
        //    46: astore          7
        //    48: aload           7
        //    50: ifnull          189
        //    53: goto            144
        //    56: aload           7
        //    58: invokevirtual   java/awt/image/BufferedImage.getWidth:()I
        //    61: iload_3        
        //    62: iconst_0       
        //    63: ishr           
        //    64: if_icmpne       79
        //    67: aload           7
        //    69: invokevirtual   java/awt/image/BufferedImage.getHeight:()I
        //    72: iload           4
        //    74: iconst_0       
        //    75: ishr           
        //    76: if_icmpeq       144
        //    79: new             Ljava/lang/RuntimeException;
        //    82: dup            
        //    83: ldc             "Unable to load miplevel: %d, image is size: %dx%d, expected %dx%d"
        //    85: iconst_5       
        //    86: anewarray       Ljava/lang/Object;
        //    89: dup            
        //    90: iconst_0       
        //    91: iconst_0       
        //    92: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //    95: aastore        
        //    96: dup            
        //    97: iconst_1       
        //    98: aload           7
        //   100: invokevirtual   java/awt/image/BufferedImage.getWidth:()I
        //   103: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   106: aastore        
        //   107: dup            
        //   108: iconst_2       
        //   109: aload           7
        //   111: invokevirtual   java/awt/image/BufferedImage.getHeight:()I
        //   114: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   117: aastore        
        //   118: dup            
        //   119: iconst_3       
        //   120: iload_3        
        //   121: iconst_0       
        //   122: ishr           
        //   123: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   126: aastore        
        //   127: dup            
        //   128: iconst_4       
        //   129: iload           4
        //   131: iconst_0       
        //   132: ishr           
        //   133: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   136: aastore        
        //   137: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   140: invokespecial   java/lang/RuntimeException.<init>:(Ljava/lang/String;)V
        //   143: athrow         
        //   144: aload           5
        //   146: iconst_0       
        //   147: aload           7
        //   149: invokevirtual   java/awt/image/BufferedImage.getWidth:()I
        //   152: aload           7
        //   154: invokevirtual   java/awt/image/BufferedImage.getHeight:()I
        //   157: imul           
        //   158: newarray        I
        //   160: aastore        
        //   161: aload           7
        //   163: iconst_0       
        //   164: iconst_0       
        //   165: aload           7
        //   167: invokevirtual   java/awt/image/BufferedImage.getWidth:()I
        //   170: aload           7
        //   172: invokevirtual   java/awt/image/BufferedImage.getHeight:()I
        //   175: aload           5
        //   177: iconst_0       
        //   178: aaload         
        //   179: iconst_0       
        //   180: aload           7
        //   182: invokevirtual   java/awt/image/BufferedImage.getWidth:()I
        //   185: invokevirtual   java/awt/image/BufferedImage.getRGB:(IIII[III)[I
        //   188: pop            
        //   189: iinc            6, 1
        //   192: goto            37
        //   195: aload_2        
        //   196: ifnonnull       230
        //   199: iload           4
        //   201: iload_3        
        //   202: if_icmpeq       215
        //   205: new             Ljava/lang/RuntimeException;
        //   208: dup            
        //   209: ldc             "broken aspect ratio and not an animation"
        //   211: invokespecial   java/lang/RuntimeException.<init>:(Ljava/lang/String;)V
        //   214: athrow         
        //   215: aload_0        
        //   216: getfield        net/minecraft/client/renderer/texture/TextureAtlasSprite.framesTextureData:Ljava/util/List;
        //   219: aload           5
        //   221: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   226: pop            
        //   227: goto            433
        //   230: iload           4
        //   232: iload_3        
        //   233: idiv           
        //   234: istore          6
        //   236: iload_3        
        //   237: istore          7
        //   239: iload_3        
        //   240: istore          8
        //   242: aload_0        
        //   243: aload_0        
        //   244: getfield        net/minecraft/client/renderer/texture/TextureAtlasSprite.width:I
        //   247: putfield        net/minecraft/client/renderer/texture/TextureAtlasSprite.height:I
        //   250: aload_2        
        //   251: invokevirtual   net/minecraft/client/resources/data/AnimationMetadataSection.getFrameCount:()I
        //   254: ifle            356
        //   257: aload_2        
        //   258: invokevirtual   net/minecraft/client/resources/data/AnimationMetadataSection.getFrameIndexSet:()Ljava/util/Set;
        //   261: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
        //   266: astore          9
        //   268: aload           9
        //   270: invokeinterface java/util/Iterator.hasNext:()Z
        //   275: ifeq            348
        //   278: aload           9
        //   280: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   285: checkcast       Ljava/lang/Integer;
        //   288: invokevirtual   java/lang/Integer.intValue:()I
        //   291: istore          10
        //   293: new             Ljava/lang/RuntimeException;
        //   296: dup            
        //   297: new             Ljava/lang/StringBuilder;
        //   300: dup            
        //   301: invokespecial   java/lang/StringBuilder.<init>:()V
        //   304: ldc             "invalid frameindex "
        //   306: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   309: iconst_0       
        //   310: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   313: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   316: invokespecial   java/lang/RuntimeException.<init>:(Ljava/lang/String;)V
        //   319: athrow         
        //   320: aload_0        
        //   321: iconst_0       
        //   322: invokespecial   net/minecraft/client/renderer/texture/TextureAtlasSprite.allocateFrameTextureData:(I)V
        //   325: aload_0        
        //   326: getfield        net/minecraft/client/renderer/texture/TextureAtlasSprite.framesTextureData:Ljava/util/List;
        //   329: iconst_0       
        //   330: aload           5
        //   332: iload           7
        //   334: iconst_0       
        //   335: iconst_0       
        //   336: invokestatic    net/minecraft/client/renderer/texture/TextureAtlasSprite.getFrameTextureData:([[IIII)[[I
        //   339: invokeinterface java/util/List.set:(ILjava/lang/Object;)Ljava/lang/Object;
        //   344: pop            
        //   345: goto            268
        //   348: aload_0        
        //   349: aload_2        
        //   350: putfield        net/minecraft/client/renderer/texture/TextureAtlasSprite.animationMetadata:Lnet/minecraft/client/resources/data/AnimationMetadataSection;
        //   353: goto            433
        //   356: invokestatic    com/google/common/collect/Lists.newArrayList:()Ljava/util/ArrayList;
        //   359: astore          9
        //   361: goto            404
        //   364: aload_0        
        //   365: getfield        net/minecraft/client/renderer/texture/TextureAtlasSprite.framesTextureData:Ljava/util/List;
        //   368: aload           5
        //   370: iload           7
        //   372: iconst_0       
        //   373: iconst_0       
        //   374: invokestatic    net/minecraft/client/renderer/texture/TextureAtlasSprite.getFrameTextureData:([[IIII)[[I
        //   377: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   382: pop            
        //   383: aload           9
        //   385: new             Lnet/minecraft/client/resources/data/AnimationFrame;
        //   388: dup            
        //   389: iconst_0       
        //   390: iconst_m1      
        //   391: invokespecial   net/minecraft/client/resources/data/AnimationFrame.<init>:(II)V
        //   394: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   397: pop            
        //   398: iinc            10, 1
        //   401: goto            361
        //   404: aload_0        
        //   405: new             Lnet/minecraft/client/resources/data/AnimationMetadataSection;
        //   408: dup            
        //   409: aload           9
        //   411: aload_0        
        //   412: getfield        net/minecraft/client/renderer/texture/TextureAtlasSprite.width:I
        //   415: aload_0        
        //   416: getfield        net/minecraft/client/renderer/texture/TextureAtlasSprite.height:I
        //   419: aload_2        
        //   420: invokevirtual   net/minecraft/client/resources/data/AnimationMetadataSection.getFrameTime:()I
        //   423: aload_2        
        //   424: invokevirtual   net/minecraft/client/resources/data/AnimationMetadataSection.isInterpolate:()Z
        //   427: invokespecial   net/minecraft/client/resources/data/AnimationMetadataSection.<init>:(Ljava/util/List;IIIZ)V
        //   430: putfield        net/minecraft/client/renderer/texture/TextureAtlasSprite.animationMetadata:Lnet/minecraft/client/resources/data/AnimationMetadataSection;
        //   433: iconst_0       
        //   434: aload_0        
        //   435: getfield        net/minecraft/client/renderer/texture/TextureAtlasSprite.framesTextureData:Ljava/util/List;
        //   438: invokeinterface java/util/List.size:()I
        //   443: if_icmpge       515
        //   446: aload_0        
        //   447: getfield        net/minecraft/client/renderer/texture/TextureAtlasSprite.framesTextureData:Ljava/util/List;
        //   450: iconst_0       
        //   451: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   456: checkcast       [[I
        //   459: checkcast       [[I
        //   462: checkcast       [[I
        //   465: astore          7
        //   467: aload           7
        //   469: ifnull          509
        //   472: aload_0        
        //   473: getfield        net/minecraft/client/renderer/texture/TextureAtlasSprite.iconName:Ljava/lang/String;
        //   476: ldc             "minecraft:blocks/leaves_"
        //   478: invokevirtual   java/lang/String.startsWith:(Ljava/lang/String;)Z
        //   481: ifne            509
        //   484: iconst_0       
        //   485: aload           7
        //   487: arraylength    
        //   488: if_icmpge       509
        //   491: aload           7
        //   493: iconst_0       
        //   494: aaload         
        //   495: astore          9
        //   497: aload_0        
        //   498: aload           9
        //   500: invokespecial   net/minecraft/client/renderer/texture/TextureAtlasSprite.fixTransparentColor:([I)V
        //   503: iinc            8, 1
        //   506: goto            484
        //   509: iinc            6, 1
        //   512: goto            433
        //   515: aload_0        
        //   516: getfield        net/minecraft/client/renderer/texture/TextureAtlasSprite.spriteSingle:Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;
        //   519: ifnull          531
        //   522: aload_0        
        //   523: getfield        net/minecraft/client/renderer/texture/TextureAtlasSprite.spriteSingle:Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;
        //   526: aload_1        
        //   527: aload_2        
        //   528: invokevirtual   net/minecraft/client/renderer/texture/TextureAtlasSprite.loadSprite:([Ljava/awt/image/BufferedImage;Lnet/minecraft/client/resources/data/AnimationMetadataSection;)V
        //   531: return         
        //    Exceptions:
        //  throws java.io.IOException
        // 
        // The error that occurred was:
        // 
        // java.util.ConcurrentModificationException
        //     at java.util.ArrayList$Itr.checkForComodification(Unknown Source)
        //     at java.util.ArrayList$Itr.next(Unknown Source)
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2863)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
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
    
    private void fixTransparentColor(final int[] array) {
        if (array != null) {
            long n = 0L;
            long n2 = 0L;
            long n3 = 0L;
            long n4 = 0L;
            int n7 = 0;
            while (0 < array.length) {
                final int n5 = array[0];
                if ((n5 >> 24 & 0xFF) >= 16) {
                    final int n6 = n5 >> 16 & 0xFF;
                    n7 = (n5 >> 8 & 0xFF);
                    final int n8 = n5 & 0xFF;
                    n += n6;
                    n2 += 0;
                    n3 += n8;
                    ++n4;
                }
                int n9 = 0;
                ++n9;
            }
            if (n4 > 0L) {
                final int n9 = (int)(n / n4);
                final int n10 = 0x0 | (int)(n2 / n4) << 8 | (int)(n3 / n4);
                while (0 < array.length) {
                    if ((array[0] >> 24 & 0xFF) <= 16) {
                        array[0] = n10;
                    }
                    ++n7;
                }
            }
        }
    }
    
    public static void setLocationNameCompass(final String locationNameCompass) {
        TextureAtlasSprite.locationNameCompass = locationNameCompass;
    }
    
    public float getInterpolatedV(final double n) {
        return this.minV + (this.maxV - this.minV) * ((float)n / 16.0f);
    }
    
    public int getIconWidth() {
        return this.width;
    }
    
    public int[][] getFrameTextureData(final int n) {
        return this.framesTextureData.get(n);
    }
    
    public void copyFrom(final TextureAtlasSprite textureAtlasSprite) {
        this.originX = textureAtlasSprite.originX;
        this.originY = textureAtlasSprite.originY;
        this.width = textureAtlasSprite.width;
        this.height = textureAtlasSprite.height;
        this.rotated = textureAtlasSprite.rotated;
        this.minU = textureAtlasSprite.minU;
        this.maxU = textureAtlasSprite.maxU;
        this.minV = textureAtlasSprite.minV;
        this.maxV = textureAtlasSprite.maxV;
        if (this.spriteSingle != null) {
            this.spriteSingle.initSprite(this.width, this.height, 0, 0, false);
        }
    }
    
    public void setFramesTextureData(final List list) {
        this.framesTextureData = list;
        if (this.spriteSingle != null) {
            this.spriteSingle.setFramesTextureData(list);
        }
    }
    
    static {
        TextureAtlasSprite.locationNameClock = "builtin/clock";
        TextureAtlasSprite.locationNameCompass = "builtin/compass";
    }
    
    public float getMinU() {
        return this.minU;
    }
    
    public boolean load(final IResourceManager resourceManager, final ResourceLocation resourceLocation) {
        return true;
    }
    
    public void updateAnimation() {
        ++this.tickCounter;
        if (this.tickCounter >= this.animationMetadata.getFrameTimeSingle(this.frameCounter)) {
            final int frameIndex = this.animationMetadata.getFrameIndex(this.frameCounter);
            this.frameCounter = (this.frameCounter + 1) % ((this.animationMetadata.getFrameCount() == 0) ? this.framesTextureData.size() : this.animationMetadata.getFrameCount());
            this.tickCounter = 0;
            final int frameIndex2 = this.animationMetadata.getFrameIndex(this.frameCounter);
            final boolean isSpriteSingle = this.isSpriteSingle;
            if (frameIndex != frameIndex2 && frameIndex2 >= 0 && frameIndex2 < this.framesTextureData.size()) {
                TextureUtil.uploadTextureMipmap(this.framesTextureData.get(frameIndex2), this.width, this.height, this.originX, this.originY, false, isSpriteSingle);
            }
        }
        else if (this.animationMetadata.isInterpolate()) {
            this.updateAnimationInterpolated();
        }
    }
    
    public float getMinV() {
        return this.minV;
    }
    
    protected static TextureAtlasSprite makeAtlasSprite(final ResourceLocation resourceLocation) {
        final String string = resourceLocation.toString();
        return TextureAtlasSprite.locationNameClock.equals(string) ? new TextureClock(string) : (TextureAtlasSprite.locationNameCompass.equals(string) ? new TextureCompass(string) : new TextureAtlasSprite(string));
    }
    
    public boolean hasAnimationMetadata() {
        return this.animationMetadata != null;
    }
    
    public double getSpriteV16(final float n) {
        return (n - this.minV) / (this.maxV - this.minV) * 16.0f;
    }
    
    public void setIndexInMap(final int indexInMap) {
        this.indexInMap = indexInMap;
    }
    
    public boolean hasCustomLoader(final IResourceManager resourceManager, final ResourceLocation resourceLocation) {
        return false;
    }
    
    private void updateAnimationInterpolated() {
        final double n = 1.0 - this.tickCounter / (double)this.animationMetadata.getFrameTimeSingle(this.frameCounter);
        final int frameIndex = this.animationMetadata.getFrameIndex(this.frameCounter);
        final int frameIndex2 = this.animationMetadata.getFrameIndex((this.frameCounter + 1) % ((this.animationMetadata.getFrameCount() == 0) ? this.framesTextureData.size() : this.animationMetadata.getFrameCount()));
        if (frameIndex != frameIndex2 && frameIndex2 >= 0 && frameIndex2 < this.framesTextureData.size()) {
            final int[][] array = this.framesTextureData.get(frameIndex);
            final int[][] array2 = this.framesTextureData.get(frameIndex2);
            if (this.interpolatedFrameData == null || this.interpolatedFrameData.length != array.length) {
                this.interpolatedFrameData = new int[array.length][];
            }
            while (0 < array.length) {
                if (this.interpolatedFrameData[0] == null) {
                    this.interpolatedFrameData[0] = new int[array[0].length];
                }
                if (0 < array2.length && array2[0].length == array[0].length) {
                    while (0 < array[0].length) {
                        final int n2 = array[0][0];
                        final int n3 = array2[0][0];
                        this.interpolatedFrameData[0][0] = ((n2 & 0xFF000000) | (int)(((n2 & 0xFF0000) >> 16) * n + ((n3 & 0xFF0000) >> 16) * (1.0 - n)) << 16 | (int)(((n2 & 0xFF00) >> 8) * n + ((n3 & 0xFF00) >> 8) * (1.0 - n)) << 8 | (int)((n2 & 0xFF) * n + (n3 & 0xFF) * (1.0 - n)));
                        int n4 = 0;
                        ++n4;
                    }
                }
                int n5 = 0;
                ++n5;
            }
            TextureUtil.uploadTextureMipmap(this.interpolatedFrameData, this.width, this.height, this.originX, this.originY, false, false);
        }
    }
    
    public void bindSpriteTexture() {
        if (this.glSpriteTextureId < 0) {
            TextureUtil.allocateTextureImpl(this.glSpriteTextureId = TextureUtil.glGenTextures(), this.mipmapLevels, this.width, this.height);
        }
        TextureUtils.bindTexture(this.glSpriteTextureId);
    }
    
    @Override
    public String toString() {
        return "TextureAtlasSprite{name='" + this.iconName + '\'' + ", frameCount=" + this.framesTextureData.size() + ", rotated=" + this.rotated + ", x=" + this.originX + ", y=" + this.originY + ", height=" + this.height + ", width=" + this.width + ", u0=" + this.minU + ", u1=" + this.maxU + ", v0=" + this.minV + ", v1=" + this.maxV + '}';
    }
    
    public void setIconHeight(final int height) {
        this.height = height;
        if (this.spriteSingle != null) {
            this.spriteSingle.setIconHeight(this.height);
        }
    }
    
    public float getMaxU() {
        return this.maxU;
    }
    
    private void resetSprite() {
        this.animationMetadata = null;
        this.setFramesTextureData(Lists.newArrayList());
        this.frameCounter = 0;
        this.tickCounter = 0;
        if (this.spriteSingle != null) {
            this.spriteSingle.resetSprite();
        }
    }
    
    public int getIndexInMap() {
        return this.indexInMap;
    }
    
    public void setIconWidth(final int width) {
        this.width = width;
        if (this.spriteSingle != null) {
            this.spriteSingle.setIconWidth(this.width);
        }
    }
    
    public float getMaxV() {
        return this.maxV;
    }
    
    public String getIconName() {
        return this.iconName;
    }
    
    public void initSprite(final int n, final int n2, final int originX, final int originY, final boolean rotated) {
        this.originX = originX;
        this.originY = originY;
        this.rotated = rotated;
        final float n3 = (float)(0.009999999776482582 / n);
        final float n4 = (float)(0.009999999776482582 / n2);
        this.minU = originX / (float)n + n3;
        this.maxU = (originX + this.width) / (float)n - n3;
        this.minV = originY / (float)n2 + n4;
        this.maxV = (originY + this.height) / (float)n2 - n4;
        this.baseU = Math.min(this.minU, this.maxU);
        this.baseV = Math.min(this.minV, this.maxV);
        if (this.spriteSingle != null) {
            this.spriteSingle.initSprite(this.width, this.height, 0, 0, false);
        }
    }
    
    public void clearFramesTextureData() {
        this.framesTextureData.clear();
        if (this.spriteSingle != null) {
            this.spriteSingle.clearFramesTextureData();
        }
    }
    
    private static int[][] getFrameTextureData(final int[][] array, final int n, final int n2, final int n3) {
        final int[][] array2 = new int[array.length][];
        while (0 < array.length) {
            final int[] array3 = array[0];
            if (array3 != null) {
                array2[0] = new int[(n >> 0) * (n2 >> 0)];
                System.arraycopy(array3, n3 * array2[0].length, array2[0], 0, array2[0].length);
            }
            int n4 = 0;
            ++n4;
        }
        return array2;
    }
    
    public static void setLocationNameClock(final String locationNameClock) {
        TextureAtlasSprite.locationNameClock = locationNameClock;
    }
    
    public int getIconHeight() {
        return this.height;
    }
    
    protected TextureAtlasSprite(final String iconName) {
        this.framesTextureData = Lists.newArrayList();
        this.indexInMap = -1;
        this.glSpriteTextureId = -1;
        this.spriteSingle = null;
        this.isSpriteSingle = false;
        this.mipmapLevels = 0;
        this.iconName = iconName;
        if (Config.isMultiTexture()) {
            this.spriteSingle = new TextureAtlasSprite(this);
        }
    }
    
    public void deleteSpriteTexture() {
        if (this.glSpriteTextureId >= 0) {
            TextureUtil.deleteTexture(this.glSpriteTextureId);
            this.glSpriteTextureId = -1;
        }
    }
    
    public int getFrameCount() {
        return this.framesTextureData.size();
    }
    
    public float toSingleV(float n) {
        n -= this.baseV;
        n *= this.sheetHeight / (float)this.height;
        return n;
    }
    
    public void generateMipmaps(final int n) {
        final ArrayList arrayList = Lists.newArrayList();
        while (0 < this.framesTextureData.size()) {
            final int[][] array = this.framesTextureData.get(0);
            if (array != null) {
                arrayList.add(TextureUtil.generateMipmapData(n, this.width, array));
            }
            int n2 = 0;
            ++n2;
        }
        this.setFramesTextureData(arrayList);
        if (this.spriteSingle != null) {
            this.spriteSingle.generateMipmaps(n);
        }
    }
    
    public int getOriginX() {
        return this.originX;
    }
    
    public float getInterpolatedU(final double n) {
        return this.minU + (this.maxU - this.minU) * (float)n / 16.0f;
    }
}
