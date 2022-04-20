package optfine;

import net.minecraft.world.*;
import net.minecraft.client.renderer.texture.*;

public class CustomSky
{
    private static CustomSkyLayer[][] worldSkyLayers;
    
    static {
        CustomSky.worldSkyLayers = null;
    }
    
    public static void update() {
        if (Config.isCustomSky()) {
            CustomSky.worldSkyLayers = readCustomSkies();
        }
    }
    
    public static void reset() {
        CustomSky.worldSkyLayers = null;
    }
    
    public static void renderSky(final World world, final TextureManager textureManager, final float n, final float n2) {
        if (CustomSky.worldSkyLayers != null && Config.getGameSettings().renderDistanceChunks >= 8) {
            final int dimensionId = world.provider.getDimensionId();
            if (dimensionId >= 0 && dimensionId < CustomSky.worldSkyLayers.length) {
                final CustomSkyLayer[] array = CustomSky.worldSkyLayers[dimensionId];
                if (array != null) {
                    final int n3 = (int)(world.getWorldTime() % 24000L);
                    while (0 < array.length) {
                        final CustomSkyLayer customSkyLayer = array[0];
                        if (customSkyLayer.isActive(n3)) {
                            customSkyLayer.render(n3, n, n2);
                        }
                        int n4 = 0;
                        ++n4;
                    }
                    Blender.clearBlend(n2);
                }
            }
        }
    }
    
    private static CustomSkyLayer[][] readCustomSkies() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     2: iconst_0       
        //     3: multianewarray  [[Loptfine/CustomSkyLayer;
        //     7: astore_0       
        //     8: ldc             "mcpatcher/sky/world"
        //    10: astore_1       
        //    11: iconst_0       
        //    12: aload_0        
        //    13: arraylength    
        //    14: if_icmpge       340
        //    17: new             Ljava/lang/StringBuilder;
        //    20: dup            
        //    21: invokespecial   java/lang/StringBuilder.<init>:()V
        //    24: aload_1        
        //    25: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    28: iconst_0       
        //    29: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //    32: ldc             "/sky"
        //    34: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    37: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    40: astore          4
        //    42: new             Ljava/util/ArrayList;
        //    45: dup            
        //    46: invokespecial   java/util/ArrayList.<init>:()V
        //    49: astore          5
        //    51: new             Ljava/lang/StringBuilder;
        //    54: dup            
        //    55: invokespecial   java/lang/StringBuilder.<init>:()V
        //    58: aload           4
        //    60: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    63: iconst_1       
        //    64: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //    67: ldc             ".properties"
        //    69: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    72: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    75: astore          7
        //    77: new             Lnet/minecraft/util/ResourceLocation;
        //    80: dup            
        //    81: aload           7
        //    83: invokespecial   net/minecraft/util/ResourceLocation.<init>:(Ljava/lang/String;)V
        //    86: astore          8
        //    88: aload           8
        //    90: invokestatic    optfine/Config.getResourceStream:(Lnet/minecraft/util/ResourceLocation;)Ljava/io/InputStream;
        //    93: astore          9
        //    95: aload           9
        //    97: ifnonnull       103
        //   100: goto            291
        //   103: new             Ljava/util/Properties;
        //   106: dup            
        //   107: invokespecial   java/util/Properties.<init>:()V
        //   110: astore          10
        //   112: aload           10
        //   114: aload           9
        //   116: invokevirtual   java/util/Properties.load:(Ljava/io/InputStream;)V
        //   119: new             Ljava/lang/StringBuilder;
        //   122: dup            
        //   123: invokespecial   java/lang/StringBuilder.<init>:()V
        //   126: ldc             "CustomSky properties: "
        //   128: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   131: aload           7
        //   133: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   136: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   139: invokestatic    optfine/Config.dbg:(Ljava/lang/String;)V
        //   142: new             Ljava/lang/StringBuilder;
        //   145: dup            
        //   146: invokespecial   java/lang/StringBuilder.<init>:()V
        //   149: aload           4
        //   151: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   154: iconst_1       
        //   155: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   158: ldc             ".png"
        //   160: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   163: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   166: astore          11
        //   168: new             Loptfine/CustomSkyLayer;
        //   171: dup            
        //   172: aload           10
        //   174: aload           11
        //   176: invokespecial   optfine/CustomSkyLayer.<init>:(Ljava/util/Properties;Ljava/lang/String;)V
        //   179: astore          12
        //   181: aload           12
        //   183: aload           7
        //   185: invokevirtual   optfine/CustomSkyLayer.isValid:(Ljava/lang/String;)Z
        //   188: ifeq            270
        //   191: new             Lnet/minecraft/util/ResourceLocation;
        //   194: dup            
        //   195: aload           12
        //   197: getfield        optfine/CustomSkyLayer.source:Ljava/lang/String;
        //   200: invokespecial   net/minecraft/util/ResourceLocation.<init>:(Ljava/lang/String;)V
        //   203: astore          13
        //   205: aload           13
        //   207: invokestatic    optfine/TextureUtils.getTexture:(Lnet/minecraft/util/ResourceLocation;)Lnet/minecraft/client/renderer/texture/ITextureObject;
        //   210: astore          14
        //   212: aload           14
        //   214: ifnonnull       243
        //   217: new             Ljava/lang/StringBuilder;
        //   220: dup            
        //   221: invokespecial   java/lang/StringBuilder.<init>:()V
        //   224: ldc             "CustomSky: Texture not found: "
        //   226: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   229: aload           13
        //   231: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   234: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   237: invokestatic    optfine/Config.log:(Ljava/lang/String;)V
        //   240: goto            285
        //   243: aload           12
        //   245: aload           14
        //   247: invokeinterface net/minecraft/client/renderer/texture/ITextureObject.getGlTextureId:()I
        //   252: putfield        optfine/CustomSkyLayer.textureId:I
        //   255: aload           5
        //   257: aload           12
        //   259: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   264: pop            
        //   265: aload           9
        //   267: invokevirtual   java/io/InputStream.close:()V
        //   270: goto            285
        //   273: astore          8
        //   275: goto            291
        //   278: astore          8
        //   280: aload           8
        //   282: invokevirtual   java/io/IOException.printStackTrace:()V
        //   285: iinc            6, 1
        //   288: goto            51
        //   291: aload           5
        //   293: invokeinterface java/util/List.size:()I
        //   298: ifle            334
        //   301: aload           5
        //   303: aload           5
        //   305: invokeinterface java/util/List.size:()I
        //   310: anewarray       Loptfine/CustomSkyLayer;
        //   313: invokeinterface java/util/List.toArray:([Ljava/lang/Object;)[Ljava/lang/Object;
        //   318: checkcast       [Loptfine/CustomSkyLayer;
        //   321: checkcast       [Loptfine/CustomSkyLayer;
        //   324: checkcast       [Loptfine/CustomSkyLayer;
        //   327: astore          6
        //   329: aload_0        
        //   330: iconst_0       
        //   331: aload           6
        //   333: aastore        
        //   334: iinc            3, 1
        //   337: goto            11
        //   340: aconst_null    
        //   341: checkcast       [[Loptfine/CustomSkyLayer;
        //   344: checkcast       [[Loptfine/CustomSkyLayer;
        //   347: areturn        
        //   348: iconst_0       
        //   349: iconst_0       
        //   350: multianewarray  [[Loptfine/CustomSkyLayer;
        //   354: astore          4
        //   356: iconst_0       
        //   357: aload           4
        //   359: arraylength    
        //   360: if_icmpge       376
        //   363: aload           4
        //   365: iconst_0       
        //   366: aload_0        
        //   367: iconst_0       
        //   368: aaload         
        //   369: aastore        
        //   370: iinc            5, 1
        //   373: goto            356
        //   376: aload           4
        //   378: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static boolean hasSkyLayers(final World world) {
        if (CustomSky.worldSkyLayers == null) {
            return false;
        }
        if (Config.getGameSettings().renderDistanceChunks < 8) {
            return false;
        }
        final int dimensionId = world.provider.getDimensionId();
        if (dimensionId >= 0 && dimensionId < CustomSky.worldSkyLayers.length) {
            final CustomSkyLayer[] array = CustomSky.worldSkyLayers[dimensionId];
            return array != null && array.length > 0;
        }
        return false;
    }
}
