package net.minecraft.client.renderer.block.model;

import net.minecraft.client.renderer.texture.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.util.*;
import org.lwjgl.util.vector.*;

public class ItemModelGenerator
{
    public static final List LAYERS;
    
    private List func_178393_a(final TextureAtlasSprite p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   net/minecraft/client/renderer/texture/TextureAtlasSprite.getIconWidth:()I
        //     4: istore_2       
        //     5: aload_1        
        //     6: invokevirtual   net/minecraft/client/renderer/texture/TextureAtlasSprite.getIconHeight:()I
        //     9: istore_3       
        //    10: invokestatic    com/google/common/collect/Lists.newArrayList:()Ljava/util/ArrayList;
        //    13: astore          4
        //    15: iconst_0       
        //    16: aload_1        
        //    17: invokevirtual   net/minecraft/client/renderer/texture/TextureAtlasSprite.getFrameCount:()I
        //    20: if_icmpge       145
        //    23: aload_1        
        //    24: iconst_0       
        //    25: invokevirtual   net/minecraft/client/renderer/texture/TextureAtlasSprite.getFrameTextureData:(I)[[I
        //    28: iconst_0       
        //    29: aaload         
        //    30: astore          6
        //    32: iconst_0       
        //    33: iload_3        
        //    34: if_icmpge       139
        //    37: iconst_0       
        //    38: iload_2        
        //    39: if_icmpge       133
        //    42: aload_0        
        //    43: aload           6
        //    45: iconst_0       
        //    46: iconst_0       
        //    47: iload_2        
        //    48: iload_3        
        //    49: iflt            56
        //    52: iconst_1       
        //    53: goto            57
        //    56: iconst_0       
        //    57: istore          9
        //    59: aload_0        
        //    60: getstatic       net/minecraft/client/renderer/block/model/ItemModelGenerator$SpanFacing.UP:Lnet/minecraft/client/renderer/block/model/ItemModelGenerator$SpanFacing;
        //    63: aload           4
        //    65: aload           6
        //    67: iconst_0       
        //    68: iconst_0       
        //    69: iload_2        
        //    70: iload_3        
        //    71: iload           9
        //    73: invokespecial   net/minecraft/client/renderer/block/model/ItemModelGenerator.func_178396_a:(Lnet/minecraft/client/renderer/block/model/ItemModelGenerator$SpanFacing;Ljava/util/List;[IIIIIZ)V
        //    76: aload_0        
        //    77: getstatic       net/minecraft/client/renderer/block/model/ItemModelGenerator$SpanFacing.DOWN:Lnet/minecraft/client/renderer/block/model/ItemModelGenerator$SpanFacing;
        //    80: aload           4
        //    82: aload           6
        //    84: iconst_0       
        //    85: iconst_0       
        //    86: iload_2        
        //    87: iload_3        
        //    88: iload           9
        //    90: invokespecial   net/minecraft/client/renderer/block/model/ItemModelGenerator.func_178396_a:(Lnet/minecraft/client/renderer/block/model/ItemModelGenerator$SpanFacing;Ljava/util/List;[IIIIIZ)V
        //    93: aload_0        
        //    94: getstatic       net/minecraft/client/renderer/block/model/ItemModelGenerator$SpanFacing.LEFT:Lnet/minecraft/client/renderer/block/model/ItemModelGenerator$SpanFacing;
        //    97: aload           4
        //    99: aload           6
        //   101: iconst_0       
        //   102: iconst_0       
        //   103: iload_2        
        //   104: iload_3        
        //   105: iload           9
        //   107: invokespecial   net/minecraft/client/renderer/block/model/ItemModelGenerator.func_178396_a:(Lnet/minecraft/client/renderer/block/model/ItemModelGenerator$SpanFacing;Ljava/util/List;[IIIIIZ)V
        //   110: aload_0        
        //   111: getstatic       net/minecraft/client/renderer/block/model/ItemModelGenerator$SpanFacing.RIGHT:Lnet/minecraft/client/renderer/block/model/ItemModelGenerator$SpanFacing;
        //   114: aload           4
        //   116: aload           6
        //   118: iconst_0       
        //   119: iconst_0       
        //   120: iload_2        
        //   121: iload_3        
        //   122: iload           9
        //   124: invokespecial   net/minecraft/client/renderer/block/model/ItemModelGenerator.func_178396_a:(Lnet/minecraft/client/renderer/block/model/ItemModelGenerator$SpanFacing;Ljava/util/List;[IIIIIZ)V
        //   127: iinc            8, 1
        //   130: goto            37
        //   133: iinc            7, 1
        //   136: goto            32
        //   139: iinc            5, 1
        //   142: goto            15
        //   145: aload           4
        //   147: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0037 (coming from #0130).
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
    
    public ModelBlock makeItemModel(final TextureMap textureMap, final ModelBlock modelBlock) {
        final HashMap hashMap = Maps.newHashMap();
        final ArrayList arrayList = Lists.newArrayList();
        while (0 < ItemModelGenerator.LAYERS.size()) {
            final String s = ItemModelGenerator.LAYERS.get(0);
            if (!modelBlock.isTexturePresent(s)) {
                break;
            }
            final String resolveTextureName = modelBlock.resolveTextureName(s);
            hashMap.put(s, resolveTextureName);
            arrayList.addAll(this.func_178394_a(0, s, textureMap.getAtlasSprite(new ResourceLocation(resolveTextureName).toString())));
            int n = 0;
            ++n;
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        hashMap.put("particle", modelBlock.isTexturePresent("particle") ? modelBlock.resolveTextureName("particle") : ((String)hashMap.get("layer0")));
        return new ModelBlock(arrayList, hashMap, false, false, modelBlock.func_181682_g());
    }
    
    private void func_178395_a(final List list, final SpanFacing spanFacing, final int n, final int n2) {
        Span span = null;
        for (final Span span2 : list) {
            if (span2.func_178383_a() == spanFacing && span2.func_178381_d() == (SpanFacing.access$000(spanFacing) ? n2 : n)) {
                span = span2;
                break;
            }
        }
        final int n3 = SpanFacing.access$000(spanFacing) ? n2 : n;
        final int n4 = SpanFacing.access$000(spanFacing) ? n : n2;
        if (span == null) {
            list.add(new Span(spanFacing, n4, n3));
        }
        else {
            span.func_178382_a(n4);
        }
    }
    
    private List func_178394_a(final int n, final String s, final TextureAtlasSprite textureAtlasSprite) {
        final HashMap hashMap = Maps.newHashMap();
        hashMap.put(EnumFacing.SOUTH, new BlockPartFace(null, n, s, new BlockFaceUV(new float[] { 0.0f, 0.0f, 16.0f, 16.0f }, 0)));
        hashMap.put(EnumFacing.NORTH, new BlockPartFace(null, n, s, new BlockFaceUV(new float[] { 16.0f, 0.0f, 0.0f, 16.0f }, 0)));
        final ArrayList arrayList = Lists.newArrayList();
        arrayList.add(new BlockPart(new Vector3f(0.0f, 0.0f, 7.5f), new Vector3f(16.0f, 16.0f, 8.5f), hashMap, null, true));
        arrayList.addAll(this.func_178397_a(textureAtlasSprite, s, n));
        return arrayList;
    }
    
    private List func_178397_a(final TextureAtlasSprite textureAtlasSprite, final String s, final int n) {
        final float n2 = (float)textureAtlasSprite.getIconWidth();
        final float n3 = (float)textureAtlasSprite.getIconHeight();
        final ArrayList arrayList = Lists.newArrayList();
        for (final Span span : this.func_178393_a(textureAtlasSprite)) {
            float n4 = 0.0f;
            float n5 = 0.0f;
            float n6 = 0.0f;
            float n7 = 0.0f;
            float n8 = 0.0f;
            float n9 = 0.0f;
            float n10 = 0.0f;
            float n11 = 0.0f;
            float n12 = 0.0f;
            float n13 = 0.0f;
            final float n14 = (float)span.func_178385_b();
            final float n15 = (float)span.func_178384_c();
            final float n16 = (float)span.func_178381_d();
            final SpanFacing func_178383_a = span.func_178383_a();
            switch (ItemModelGenerator$1.$SwitchMap$net$minecraft$client$renderer$block$model$ItemModelGenerator$SpanFacing[func_178383_a.ordinal()]) {
                case 1: {
                    n8 = n14;
                    n4 = n14;
                    n9 = (n6 = n15 + 1.0f);
                    n10 = n16;
                    n5 = n16;
                    n11 = n16;
                    n7 = n16;
                    n12 = 16.0f / n2;
                    n13 = 16.0f / (n3 - 1.0f);
                    break;
                }
                case 2: {
                    n11 = n16;
                    n10 = n16;
                    n8 = n14;
                    n4 = n14;
                    n9 = (n6 = n15 + 1.0f);
                    n5 = n16 + 1.0f;
                    n7 = n16 + 1.0f;
                    n12 = 16.0f / n2;
                    n13 = 16.0f / (n3 - 1.0f);
                    break;
                }
                case 3: {
                    n8 = n16;
                    n4 = n16;
                    n9 = n16;
                    n6 = n16;
                    n11 = n14;
                    n5 = n14;
                    n10 = (n7 = n15 + 1.0f);
                    n12 = 16.0f / (n2 - 1.0f);
                    n13 = 16.0f / n3;
                    break;
                }
                case 4: {
                    n9 = n16;
                    n8 = n16;
                    n4 = n16 + 1.0f;
                    n6 = n16 + 1.0f;
                    n11 = n14;
                    n5 = n14;
                    n10 = (n7 = n15 + 1.0f);
                    n12 = 16.0f / (n2 - 1.0f);
                    n13 = 16.0f / n3;
                    break;
                }
            }
            final float n17 = 16.0f / n2;
            final float n18 = 16.0f / n3;
            final float n19 = n4 * n17;
            final float n20 = n6 * n17;
            final float n21 = n5 * n18;
            final float n22 = n7 * n18;
            final float n23 = 16.0f - n21;
            final float n24 = 16.0f - n22;
            final float n25 = n8 * n12;
            final float n26 = n9 * n12;
            final float n27 = n10 * n13;
            final float n28 = n11 * n13;
            final HashMap hashMap = Maps.newHashMap();
            hashMap.put(func_178383_a.getFacing(), new BlockPartFace(null, n, s, new BlockFaceUV(new float[] { n25, n27, n26, n28 }, 0)));
            switch (ItemModelGenerator$1.$SwitchMap$net$minecraft$client$renderer$block$model$ItemModelGenerator$SpanFacing[func_178383_a.ordinal()]) {
                case 1: {
                    arrayList.add(new BlockPart(new Vector3f(n19, n23, 7.5f), new Vector3f(n20, n23, 8.5f), hashMap, null, true));
                    continue;
                }
                case 2: {
                    arrayList.add(new BlockPart(new Vector3f(n19, n24, 7.5f), new Vector3f(n20, n24, 8.5f), hashMap, null, true));
                    continue;
                }
                case 3: {
                    arrayList.add(new BlockPart(new Vector3f(n19, n23, 7.5f), new Vector3f(n19, n24, 8.5f), hashMap, null, true));
                    continue;
                }
                case 4: {
                    arrayList.add(new BlockPart(new Vector3f(n20, n23, 7.5f), new Vector3f(n20, n24, 8.5f), hashMap, null, true));
                    continue;
                }
            }
        }
        return arrayList;
    }
    
    static {
        LAYERS = Lists.newArrayList((Object[])new String[] { "layer0", "layer1", "layer2", "layer3", "layer4" });
    }
    
    private void func_178396_a(final SpanFacing spanFacing, final List list, final int[] array, final int n, final int n2, final int n3, final int n4, final boolean b) {
        n + spanFacing.func_178372_b();
        n2 + spanFacing.func_178371_c();
        if (n4 >= 0 && b) {
            this.func_178395_a(list, spanFacing, n, n2);
        }
    }
    
    static class Span
    {
        private int field_178388_c;
        private final int field_178386_d;
        private final SpanFacing spanFacing;
        private int field_178387_b;
        
        public int func_178385_b() {
            return this.field_178387_b;
        }
        
        public void func_178382_a(final int n) {
            if (n < this.field_178387_b) {
                this.field_178387_b = n;
            }
            else if (n > this.field_178388_c) {
                this.field_178388_c = n;
            }
        }
        
        public int func_178381_d() {
            return this.field_178386_d;
        }
        
        public int func_178384_c() {
            return this.field_178388_c;
        }
        
        public Span(final SpanFacing spanFacing, final int n, final int field_178386_d) {
            this.spanFacing = spanFacing;
            this.field_178387_b = n;
            this.field_178388_c = n;
            this.field_178386_d = field_178386_d;
        }
        
        public SpanFacing func_178383_a() {
            return this.spanFacing;
        }
    }
    
    enum SpanFacing
    {
        private final EnumFacing facing;
        private final int field_178374_g;
        private final int field_178373_f;
        
        UP("UP", 0, EnumFacing.UP, 0, -1), 
        DOWN("DOWN", 1, EnumFacing.DOWN, 0, 1), 
        LEFT("LEFT", 2, EnumFacing.EAST, -1, 0);
        
        private static final SpanFacing[] $VALUES;
        
        RIGHT("RIGHT", 3, EnumFacing.WEST, 1, 0);
        
        public int func_178371_c() {
            return this.field_178374_g;
        }
        
        private SpanFacing(final String s, final int n, final EnumFacing facing, final int field_178373_f, final int field_178374_g) {
            this.facing = facing;
            this.field_178373_f = field_178373_f;
            this.field_178374_g = field_178374_g;
        }
        
        static {
            $VALUES = new SpanFacing[] { SpanFacing.UP, SpanFacing.DOWN, SpanFacing.LEFT, SpanFacing.RIGHT };
        }
        
        public EnumFacing getFacing() {
            return this.facing;
        }
        
        static boolean access$000(final SpanFacing spanFacing) {
            return spanFacing.func_178369_d();
        }
        
        private boolean func_178369_d() {
            return this == SpanFacing.DOWN || this == SpanFacing.UP;
        }
        
        public int func_178372_b() {
            return this.field_178373_f;
        }
    }
}
