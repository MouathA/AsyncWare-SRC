package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.*;

public class RenderArrow extends Render
{
    private static final ResourceLocation arrowTextures;
    
    public void doRender(final EntityArrow p0, final double p1, final double p2, final double p3, final float p4, final float p5) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1        
        //     2: invokevirtual   net/minecraft/client/renderer/entity/RenderArrow.bindEntityTexture:(Lnet/minecraft/entity/Entity;)Z
        //     5: pop            
        //     6: fconst_1       
        //     7: fconst_1       
        //     8: fconst_1       
        //     9: fconst_1       
        //    10: invokestatic    net/minecraft/client/renderer/GlStateManager.color:(FFFF)V
        //    13: dload_2        
        //    14: d2f            
        //    15: dload           4
        //    17: d2f            
        //    18: dload           6
        //    20: d2f            
        //    21: invokestatic    net/minecraft/client/renderer/GlStateManager.translate:(FFF)V
        //    24: aload_1        
        //    25: getfield        net/minecraft/entity/projectile/EntityArrow.prevRotationYaw:F
        //    28: aload_1        
        //    29: getfield        net/minecraft/entity/projectile/EntityArrow.rotationYaw:F
        //    32: aload_1        
        //    33: getfield        net/minecraft/entity/projectile/EntityArrow.prevRotationYaw:F
        //    36: fsub           
        //    37: fload           9
        //    39: fmul           
        //    40: fadd           
        //    41: ldc             90.0
        //    43: fsub           
        //    44: fconst_0       
        //    45: fconst_1       
        //    46: fconst_0       
        //    47: invokestatic    net/minecraft/client/renderer/GlStateManager.rotate:(FFFF)V
        //    50: aload_1        
        //    51: getfield        net/minecraft/entity/projectile/EntityArrow.prevRotationPitch:F
        //    54: aload_1        
        //    55: getfield        net/minecraft/entity/projectile/EntityArrow.rotationPitch:F
        //    58: aload_1        
        //    59: getfield        net/minecraft/entity/projectile/EntityArrow.prevRotationPitch:F
        //    62: fsub           
        //    63: fload           9
        //    65: fmul           
        //    66: fadd           
        //    67: fconst_0       
        //    68: fconst_0       
        //    69: fconst_1       
        //    70: invokestatic    net/minecraft/client/renderer/GlStateManager.rotate:(FFFF)V
        //    73: invokestatic    net/minecraft/client/renderer/Tessellator.getInstance:()Lnet/minecraft/client/renderer/Tessellator;
        //    76: astore          10
        //    78: aload           10
        //    80: invokevirtual   net/minecraft/client/renderer/Tessellator.getWorldRenderer:()Lnet/minecraft/client/renderer/WorldRenderer;
        //    83: astore          11
        //    85: fconst_0       
        //    86: fstore          13
        //    88: ldc             0.5
        //    90: fstore          14
        //    92: iconst_0       
        //    93: i2f            
        //    94: ldc             32.0
        //    96: fdiv           
        //    97: fstore          15
        //    99: iconst_5       
        //   100: i2f            
        //   101: ldc             32.0
        //   103: fdiv           
        //   104: fstore          16
        //   106: fconst_0       
        //   107: fstore          17
        //   109: ldc             0.15625
        //   111: fstore          18
        //   113: iconst_5       
        //   114: i2f            
        //   115: ldc             32.0
        //   117: fdiv           
        //   118: fstore          19
        //   120: bipush          10
        //   122: i2f            
        //   123: ldc             32.0
        //   125: fdiv           
        //   126: fstore          20
        //   128: ldc             0.05625
        //   130: fstore          21
        //   132: aload_1        
        //   133: getfield        net/minecraft/entity/projectile/EntityArrow.arrowShake:I
        //   136: i2f            
        //   137: fload           9
        //   139: fsub           
        //   140: fstore          22
        //   142: fload           22
        //   144: fconst_0       
        //   145: fcmpl          
        //   146: ifle            171
        //   149: fload           22
        //   151: ldc             3.0
        //   153: fmul           
        //   154: invokestatic    net/minecraft/util/MathHelper.sin:(F)F
        //   157: fneg           
        //   158: fload           22
        //   160: fmul           
        //   161: fstore          23
        //   163: fload           23
        //   165: fconst_0       
        //   166: fconst_0       
        //   167: fconst_1       
        //   168: invokestatic    net/minecraft/client/renderer/GlStateManager.rotate:(FFFF)V
        //   171: ldc             45.0
        //   173: fconst_1       
        //   174: fconst_0       
        //   175: fconst_0       
        //   176: invokestatic    net/minecraft/client/renderer/GlStateManager.rotate:(FFFF)V
        //   179: fload           21
        //   181: fload           21
        //   183: fload           21
        //   185: invokestatic    net/minecraft/client/renderer/GlStateManager.scale:(FFF)V
        //   188: ldc             -4.0
        //   190: fconst_0       
        //   191: fconst_0       
        //   192: invokestatic    net/minecraft/client/renderer/GlStateManager.translate:(FFF)V
        //   195: fload           21
        //   197: fconst_0       
        //   198: fconst_0       
        //   199: invokestatic    org/lwjgl/opengl/GL11.glNormal3f:(FFF)V
        //   202: aload           11
        //   204: bipush          7
        //   206: getstatic       net/minecraft/client/renderer/vertex/DefaultVertexFormats.POSITION_TEX:Lnet/minecraft/client/renderer/vertex/VertexFormat;
        //   209: invokevirtual   net/minecraft/client/renderer/WorldRenderer.begin:(ILnet/minecraft/client/renderer/vertex/VertexFormat;)V
        //   212: aload           11
        //   214: ldc2_w          -7.0
        //   217: ldc2_w          -2.0
        //   220: ldc2_w          -2.0
        //   223: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   226: fload           17
        //   228: f2d            
        //   229: fload           19
        //   231: f2d            
        //   232: invokevirtual   net/minecraft/client/renderer/WorldRenderer.tex:(DD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   235: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //   238: aload           11
        //   240: ldc2_w          -7.0
        //   243: ldc2_w          -2.0
        //   246: ldc2_w          2.0
        //   249: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   252: fload           18
        //   254: f2d            
        //   255: fload           19
        //   257: f2d            
        //   258: invokevirtual   net/minecraft/client/renderer/WorldRenderer.tex:(DD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   261: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //   264: aload           11
        //   266: ldc2_w          -7.0
        //   269: ldc2_w          2.0
        //   272: ldc2_w          2.0
        //   275: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   278: fload           18
        //   280: f2d            
        //   281: fload           20
        //   283: f2d            
        //   284: invokevirtual   net/minecraft/client/renderer/WorldRenderer.tex:(DD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   287: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //   290: aload           11
        //   292: ldc2_w          -7.0
        //   295: ldc2_w          2.0
        //   298: ldc2_w          -2.0
        //   301: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   304: fload           17
        //   306: f2d            
        //   307: fload           20
        //   309: f2d            
        //   310: invokevirtual   net/minecraft/client/renderer/WorldRenderer.tex:(DD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   313: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //   316: aload           10
        //   318: invokevirtual   net/minecraft/client/renderer/Tessellator.draw:()V
        //   321: fload           21
        //   323: fneg           
        //   324: fconst_0       
        //   325: fconst_0       
        //   326: invokestatic    org/lwjgl/opengl/GL11.glNormal3f:(FFF)V
        //   329: aload           11
        //   331: bipush          7
        //   333: getstatic       net/minecraft/client/renderer/vertex/DefaultVertexFormats.POSITION_TEX:Lnet/minecraft/client/renderer/vertex/VertexFormat;
        //   336: invokevirtual   net/minecraft/client/renderer/WorldRenderer.begin:(ILnet/minecraft/client/renderer/vertex/VertexFormat;)V
        //   339: aload           11
        //   341: ldc2_w          -7.0
        //   344: ldc2_w          2.0
        //   347: ldc2_w          -2.0
        //   350: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   353: fload           17
        //   355: f2d            
        //   356: fload           19
        //   358: f2d            
        //   359: invokevirtual   net/minecraft/client/renderer/WorldRenderer.tex:(DD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   362: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //   365: aload           11
        //   367: ldc2_w          -7.0
        //   370: ldc2_w          2.0
        //   373: ldc2_w          2.0
        //   376: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   379: fload           18
        //   381: f2d            
        //   382: fload           19
        //   384: f2d            
        //   385: invokevirtual   net/minecraft/client/renderer/WorldRenderer.tex:(DD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   388: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //   391: aload           11
        //   393: ldc2_w          -7.0
        //   396: ldc2_w          -2.0
        //   399: ldc2_w          2.0
        //   402: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   405: fload           18
        //   407: f2d            
        //   408: fload           20
        //   410: f2d            
        //   411: invokevirtual   net/minecraft/client/renderer/WorldRenderer.tex:(DD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   414: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //   417: aload           11
        //   419: ldc2_w          -7.0
        //   422: ldc2_w          -2.0
        //   425: ldc2_w          -2.0
        //   428: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   431: fload           17
        //   433: f2d            
        //   434: fload           20
        //   436: f2d            
        //   437: invokevirtual   net/minecraft/client/renderer/WorldRenderer.tex:(DD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   440: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //   443: aload           10
        //   445: invokevirtual   net/minecraft/client/renderer/Tessellator.draw:()V
        //   448: ldc             90.0
        //   450: fconst_1       
        //   451: fconst_0       
        //   452: fconst_0       
        //   453: invokestatic    net/minecraft/client/renderer/GlStateManager.rotate:(FFFF)V
        //   456: fconst_0       
        //   457: fconst_0       
        //   458: fload           21
        //   460: invokestatic    org/lwjgl/opengl/GL11.glNormal3f:(FFF)V
        //   463: aload           11
        //   465: bipush          7
        //   467: getstatic       net/minecraft/client/renderer/vertex/DefaultVertexFormats.POSITION_TEX:Lnet/minecraft/client/renderer/vertex/VertexFormat;
        //   470: invokevirtual   net/minecraft/client/renderer/WorldRenderer.begin:(ILnet/minecraft/client/renderer/vertex/VertexFormat;)V
        //   473: aload           11
        //   475: ldc2_w          -8.0
        //   478: ldc2_w          -2.0
        //   481: dconst_0       
        //   482: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   485: fload           13
        //   487: f2d            
        //   488: fload           15
        //   490: f2d            
        //   491: invokevirtual   net/minecraft/client/renderer/WorldRenderer.tex:(DD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   494: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //   497: aload           11
        //   499: ldc2_w          8.0
        //   502: ldc2_w          -2.0
        //   505: dconst_0       
        //   506: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   509: fload           14
        //   511: f2d            
        //   512: fload           15
        //   514: f2d            
        //   515: invokevirtual   net/minecraft/client/renderer/WorldRenderer.tex:(DD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   518: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //   521: aload           11
        //   523: ldc2_w          8.0
        //   526: ldc2_w          2.0
        //   529: dconst_0       
        //   530: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   533: fload           14
        //   535: f2d            
        //   536: fload           16
        //   538: f2d            
        //   539: invokevirtual   net/minecraft/client/renderer/WorldRenderer.tex:(DD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   542: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //   545: aload           11
        //   547: ldc2_w          -8.0
        //   550: ldc2_w          2.0
        //   553: dconst_0       
        //   554: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   557: fload           13
        //   559: f2d            
        //   560: fload           16
        //   562: f2d            
        //   563: invokevirtual   net/minecraft/client/renderer/WorldRenderer.tex:(DD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   566: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //   569: aload           10
        //   571: invokevirtual   net/minecraft/client/renderer/Tessellator.draw:()V
        //   574: iinc            23, 1
        //   577: goto            448
        //   580: aload_0        
        //   581: aload_1        
        //   582: dload_2        
        //   583: dload           4
        //   585: dload           6
        //   587: fload           8
        //   589: fload           9
        //   591: invokespecial   net/minecraft/client/renderer/entity/Render.doRender:(Lnet/minecraft/entity/Entity;DDDFF)V
        //   594: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2895)
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
    
    public RenderArrow(final RenderManager renderManager) {
        super(renderManager);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityArrow)entity);
    }
    
    static {
        arrowTextures = new ResourceLocation("textures/entity/arrow.png");
    }
    
    protected ResourceLocation getEntityTexture(final EntityArrow entityArrow) {
        return RenderArrow.arrowTextures;
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityArrow)entity, n, n2, n3, n4, n5);
    }
}
