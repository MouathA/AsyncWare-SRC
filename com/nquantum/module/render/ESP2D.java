package com.nquantum.module.render;

import java.nio.*;
import javax.vecmath.*;
import org.lwjgl.util.glu.*;
import org.lwjgl.opengl.*;
import com.nquantum.event.impl.*;
import com.nquantum.event.*;
import com.nquantum.module.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import java.awt.*;

public class ESP2D extends Module
{
    private final FloatBuffer modelview;
    private final FloatBuffer projection;
    public final List collectedEntities;
    private final FloatBuffer vector;
    private final IntBuffer viewport;
    private final int black;
    private final int backgroundColor;
    
    private void collectEntities() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        com/nquantum/module/render/ESP2D.collectedEntities:Ljava/util/List;
        //     4: invokeinterface java/util/List.clear:()V
        //     9: aload_0        
        //    10: getfield        com/nquantum/module/render/ESP2D.mc:Lnet/minecraft/client/Minecraft;
        //    13: getfield        net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //    16: getfield        net/minecraft/client/multiplayer/WorldClient.loadedEntityList:Ljava/util/List;
        //    19: astore_1       
        //    20: aload_1        
        //    21: invokeinterface java/util/List.size:()I
        //    26: istore_3       
        //    27: iconst_0       
        //    28: iload_3        
        //    29: if_icmpge       68
        //    32: aload_1        
        //    33: iconst_0       
        //    34: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //    39: checkcast       Lnet/minecraft/entity/Entity;
        //    42: astore          4
        //    44: aload_0        
        //    45: aload           4
        //    47: ifeq            62
        //    50: aload_0        
        //    51: getfield        com/nquantum/module/render/ESP2D.collectedEntities:Ljava/util/List;
        //    54: aload           4
        //    56: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //    61: pop            
        //    62: iinc            2, 1
        //    65: goto            27
        //    68: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0027 (coming from #0065).
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
    
    private Vector3d project2D(final int n, final double n2, final double n3, final double n4) {
        GL11.glGetFloat(2982, this.modelview);
        GL11.glGetFloat(2983, this.projection);
        GL11.glGetInteger(2978, this.viewport);
        if (GLU.gluProject((float)n2, (float)n3, (float)n4, this.modelview, this.projection, this.viewport, this.vector)) {
            return new Vector3d((double)(this.vector.get(0) / n), (double)((Display.getHeight() - this.vector.get(1)) / n), (double)this.vector.get(2));
        }
        return null;
    }
    
    @Punjabi
    @Override
    public void on2D(final Event2D p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: aload_0        
        //     4: invokespecial   com/nquantum/module/render/ESP2D.collectEntities:()V
        //     7: aload_0        
        //     8: getfield        com/nquantum/module/render/ESP2D.mc:Lnet/minecraft/client/Minecraft;
        //    11: getfield        net/minecraft/client/Minecraft.timer:Lnet/minecraft/util/Timer;
        //    14: getfield        net/minecraft/util/Timer.renderPartialTicks:F
        //    17: fstore_2       
        //    18: new             Lnet/minecraft/client/gui/ScaledResolution;
        //    21: dup            
        //    22: aload_0        
        //    23: getfield        com/nquantum/module/render/ESP2D.mc:Lnet/minecraft/client/Minecraft;
        //    26: invokespecial   net/minecraft/client/gui/ScaledResolution.<init>:(Lnet/minecraft/client/Minecraft;)V
        //    29: astore_3       
        //    30: aload_3        
        //    31: invokevirtual   net/minecraft/client/gui/ScaledResolution.getScaleFactor:()I
        //    34: istore          4
        //    36: iload           4
        //    38: i2d            
        //    39: iload           4
        //    41: i2d            
        //    42: ldc2_w          2.0
        //    45: invokestatic    java/lang/Math.pow:(DD)D
        //    48: ddiv           
        //    49: dstore          5
        //    51: dload           5
        //    53: dload           5
        //    55: dload           5
        //    57: invokestatic    org/lwjgl/opengl/GL11.glScaled:(DDD)V
        //    60: aload_0        
        //    61: getfield        com/nquantum/module/render/ESP2D.black:I
        //    64: istore          7
        //    66: aload_0        
        //    67: getfield        com/nquantum/module/render/ESP2D.backgroundColor:I
        //    70: istore          8
        //    72: ldc             0.65
        //    74: fstore          9
        //    76: fconst_1       
        //    77: fload           9
        //    79: fdiv           
        //    80: fstore          10
        //    82: aload_0        
        //    83: getfield        com/nquantum/module/render/ESP2D.mc:Lnet/minecraft/client/Minecraft;
        //    86: invokevirtual   net/minecraft/client/Minecraft.getRenderManager:()Lnet/minecraft/client/renderer/entity/RenderManager;
        //    89: astore          11
        //    91: aload_0        
        //    92: getfield        com/nquantum/module/render/ESP2D.mc:Lnet/minecraft/client/Minecraft;
        //    95: getfield        net/minecraft/client/Minecraft.entityRenderer:Lnet/minecraft/client/renderer/EntityRenderer;
        //    98: astore          12
        //   100: aload_0        
        //   101: getfield        com/nquantum/module/render/ESP2D.collectedEntities:Ljava/util/List;
        //   104: astore          17
        //   106: aload           17
        //   108: invokeinterface java/util/List.size:()I
        //   113: istore          19
        //   115: iconst_0       
        //   116: iload           19
        //   118: if_icmpge       1495
        //   121: aload           17
        //   123: iconst_0       
        //   124: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   129: checkcast       Lnet/minecraft/entity/Entity;
        //   132: astore          20
        //   134: aload_0        
        //   135: aload           20
        //   137: ifeq            1489
        //   140: aload           20
        //   142: invokestatic    com/nquantum/util/render/RenderUtil.isInViewFrustrum:(Lnet/minecraft/entity/Entity;)Z
        //   145: ifeq            1489
        //   148: aload           20
        //   150: getfield        net/minecraft/entity/Entity.posX:D
        //   153: aload           20
        //   155: getfield        net/minecraft/entity/Entity.lastTickPosX:D
        //   158: fload_2        
        //   159: f2d            
        //   160: invokestatic    com/nquantum/util/render/RenderUtil.interpolate:(DDD)D
        //   163: dstore          21
        //   165: aload           20
        //   167: getfield        net/minecraft/entity/Entity.posY:D
        //   170: aload           20
        //   172: getfield        net/minecraft/entity/Entity.lastTickPosY:D
        //   175: fload_2        
        //   176: f2d            
        //   177: invokestatic    com/nquantum/util/render/RenderUtil.interpolate:(DDD)D
        //   180: dstore          23
        //   182: aload           20
        //   184: getfield        net/minecraft/entity/Entity.posZ:D
        //   187: aload           20
        //   189: getfield        net/minecraft/entity/Entity.lastTickPosZ:D
        //   192: fload_2        
        //   193: f2d            
        //   194: invokestatic    com/nquantum/util/render/RenderUtil.interpolate:(DDD)D
        //   197: dstore          25
        //   199: aload           20
        //   201: getfield        net/minecraft/entity/Entity.width:F
        //   204: f2d            
        //   205: ldc2_w          1.5
        //   208: ddiv           
        //   209: dstore          27
        //   211: aload           20
        //   213: getfield        net/minecraft/entity/Entity.height:F
        //   216: f2d            
        //   217: aload           20
        //   219: invokevirtual   net/minecraft/entity/Entity.isSneaking:()Z
        //   222: ifeq            231
        //   225: ldc2_w          -0.3
        //   228: goto            234
        //   231: ldc2_w          0.2
        //   234: dadd           
        //   235: dstore          29
        //   237: new             Lnet/minecraft/util/AxisAlignedBB;
        //   240: dup            
        //   241: dload           21
        //   243: dload           27
        //   245: dsub           
        //   246: dload           23
        //   248: dload           25
        //   250: dload           27
        //   252: dsub           
        //   253: dload           21
        //   255: dload           27
        //   257: dadd           
        //   258: dload           23
        //   260: dload           29
        //   262: dadd           
        //   263: dload           25
        //   265: dload           27
        //   267: dadd           
        //   268: invokespecial   net/minecraft/util/AxisAlignedBB.<init>:(DDDDDD)V
        //   271: astore          31
        //   273: bipush          8
        //   275: anewarray       Ljavax/vecmath/Vector3d;
        //   278: dup            
        //   279: iconst_0       
        //   280: new             Ljavax/vecmath/Vector3d;
        //   283: dup            
        //   284: aload           31
        //   286: getfield        net/minecraft/util/AxisAlignedBB.minX:D
        //   289: aload           31
        //   291: getfield        net/minecraft/util/AxisAlignedBB.minY:D
        //   294: aload           31
        //   296: getfield        net/minecraft/util/AxisAlignedBB.minZ:D
        //   299: invokespecial   javax/vecmath/Vector3d.<init>:(DDD)V
        //   302: aastore        
        //   303: dup            
        //   304: iconst_1       
        //   305: new             Ljavax/vecmath/Vector3d;
        //   308: dup            
        //   309: aload           31
        //   311: getfield        net/minecraft/util/AxisAlignedBB.minX:D
        //   314: aload           31
        //   316: getfield        net/minecraft/util/AxisAlignedBB.maxY:D
        //   319: aload           31
        //   321: getfield        net/minecraft/util/AxisAlignedBB.minZ:D
        //   324: invokespecial   javax/vecmath/Vector3d.<init>:(DDD)V
        //   327: aastore        
        //   328: dup            
        //   329: iconst_2       
        //   330: new             Ljavax/vecmath/Vector3d;
        //   333: dup            
        //   334: aload           31
        //   336: getfield        net/minecraft/util/AxisAlignedBB.maxX:D
        //   339: aload           31
        //   341: getfield        net/minecraft/util/AxisAlignedBB.minY:D
        //   344: aload           31
        //   346: getfield        net/minecraft/util/AxisAlignedBB.minZ:D
        //   349: invokespecial   javax/vecmath/Vector3d.<init>:(DDD)V
        //   352: aastore        
        //   353: dup            
        //   354: iconst_3       
        //   355: new             Ljavax/vecmath/Vector3d;
        //   358: dup            
        //   359: aload           31
        //   361: getfield        net/minecraft/util/AxisAlignedBB.maxX:D
        //   364: aload           31
        //   366: getfield        net/minecraft/util/AxisAlignedBB.maxY:D
        //   369: aload           31
        //   371: getfield        net/minecraft/util/AxisAlignedBB.minZ:D
        //   374: invokespecial   javax/vecmath/Vector3d.<init>:(DDD)V
        //   377: aastore        
        //   378: dup            
        //   379: iconst_4       
        //   380: new             Ljavax/vecmath/Vector3d;
        //   383: dup            
        //   384: aload           31
        //   386: getfield        net/minecraft/util/AxisAlignedBB.minX:D
        //   389: aload           31
        //   391: getfield        net/minecraft/util/AxisAlignedBB.minY:D
        //   394: aload           31
        //   396: getfield        net/minecraft/util/AxisAlignedBB.maxZ:D
        //   399: invokespecial   javax/vecmath/Vector3d.<init>:(DDD)V
        //   402: aastore        
        //   403: dup            
        //   404: iconst_5       
        //   405: new             Ljavax/vecmath/Vector3d;
        //   408: dup            
        //   409: aload           31
        //   411: getfield        net/minecraft/util/AxisAlignedBB.minX:D
        //   414: aload           31
        //   416: getfield        net/minecraft/util/AxisAlignedBB.maxY:D
        //   419: aload           31
        //   421: getfield        net/minecraft/util/AxisAlignedBB.maxZ:D
        //   424: invokespecial   javax/vecmath/Vector3d.<init>:(DDD)V
        //   427: aastore        
        //   428: dup            
        //   429: bipush          6
        //   431: new             Ljavax/vecmath/Vector3d;
        //   434: dup            
        //   435: aload           31
        //   437: getfield        net/minecraft/util/AxisAlignedBB.maxX:D
        //   440: aload           31
        //   442: getfield        net/minecraft/util/AxisAlignedBB.minY:D
        //   445: aload           31
        //   447: getfield        net/minecraft/util/AxisAlignedBB.maxZ:D
        //   450: invokespecial   javax/vecmath/Vector3d.<init>:(DDD)V
        //   453: aastore        
        //   454: dup            
        //   455: bipush          7
        //   457: new             Ljavax/vecmath/Vector3d;
        //   460: dup            
        //   461: aload           31
        //   463: getfield        net/minecraft/util/AxisAlignedBB.maxX:D
        //   466: aload           31
        //   468: getfield        net/minecraft/util/AxisAlignedBB.maxY:D
        //   471: aload           31
        //   473: getfield        net/minecraft/util/AxisAlignedBB.maxZ:D
        //   476: invokespecial   javax/vecmath/Vector3d.<init>:(DDD)V
        //   479: aastore        
        //   480: invokestatic    java/util/Arrays.asList:([Ljava/lang/Object;)Ljava/util/List;
        //   483: astore          32
        //   485: aload           12
        //   487: fload_2        
        //   488: iconst_0       
        //   489: invokevirtual   net/minecraft/client/renderer/EntityRenderer.setupCameraTransform:(FI)V
        //   492: aconst_null    
        //   493: astore          33
        //   495: aload           32
        //   497: invokeinterface java/util/List.size:()I
        //   502: istore          35
        //   504: aload           32
        //   506: iconst_0       
        //   507: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   512: checkcast       Ljavax/vecmath/Vector3d;
        //   515: astore          36
        //   517: aload_0        
        //   518: iload           4
        //   520: aload           36
        //   522: getfield        javax/vecmath/Vector3d.x:D
        //   525: aload           11
        //   527: getfield        net/minecraft/client/renderer/entity/RenderManager.viewerPosX:D
        //   530: dsub           
        //   531: aload           36
        //   533: getfield        javax/vecmath/Vector3d.y:D
        //   536: aload           11
        //   538: getfield        net/minecraft/client/renderer/entity/RenderManager.viewerPosY:D
        //   541: dsub           
        //   542: aload           36
        //   544: getfield        javax/vecmath/Vector3d.z:D
        //   547: aload           11
        //   549: getfield        net/minecraft/client/renderer/entity/RenderManager.viewerPosZ:D
        //   552: dsub           
        //   553: invokespecial   com/nquantum/module/render/ESP2D.project2D:(IDDD)Ljavax/vecmath/Vector3d;
        //   556: astore          36
        //   558: aload           36
        //   560: ifnull          685
        //   563: aload           36
        //   565: getfield        javax/vecmath/Vector3d.z:D
        //   568: dconst_0       
        //   569: dcmpl          
        //   570: iflt            685
        //   573: aload           36
        //   575: getfield        javax/vecmath/Vector3d.z:D
        //   578: dconst_1       
        //   579: dcmpg          
        //   580: ifge            685
        //   583: aload           33
        //   585: ifnonnull       613
        //   588: new             Ljavax/vecmath/Vector4d;
        //   591: dup            
        //   592: aload           36
        //   594: getfield        javax/vecmath/Vector3d.x:D
        //   597: aload           36
        //   599: getfield        javax/vecmath/Vector3d.y:D
        //   602: aload           36
        //   604: getfield        javax/vecmath/Vector3d.z:D
        //   607: dconst_0       
        //   608: invokespecial   javax/vecmath/Vector4d.<init>:(DDDD)V
        //   611: astore          33
        //   613: aload           33
        //   615: aload           36
        //   617: getfield        javax/vecmath/Vector3d.x:D
        //   620: aload           33
        //   622: getfield        javax/vecmath/Vector4d.x:D
        //   625: invokestatic    java/lang/Math.min:(DD)D
        //   628: putfield        javax/vecmath/Vector4d.x:D
        //   631: aload           33
        //   633: aload           36
        //   635: getfield        javax/vecmath/Vector3d.y:D
        //   638: aload           33
        //   640: getfield        javax/vecmath/Vector4d.y:D
        //   643: invokestatic    java/lang/Math.min:(DD)D
        //   646: putfield        javax/vecmath/Vector4d.y:D
        //   649: aload           33
        //   651: aload           36
        //   653: getfield        javax/vecmath/Vector3d.x:D
        //   656: aload           33
        //   658: getfield        javax/vecmath/Vector4d.z:D
        //   661: invokestatic    java/lang/Math.max:(DD)D
        //   664: putfield        javax/vecmath/Vector4d.z:D
        //   667: aload           33
        //   669: aload           36
        //   671: getfield        javax/vecmath/Vector3d.y:D
        //   674: aload           33
        //   676: getfield        javax/vecmath/Vector4d.w:D
        //   679: invokestatic    java/lang/Math.max:(DD)D
        //   682: putfield        javax/vecmath/Vector4d.w:D
        //   685: iinc            34, 1
        //   688: goto            504
        //   691: aload           33
        //   693: ifnull          932
        //   696: aload           12
        //   698: invokevirtual   net/minecraft/client/renderer/EntityRenderer.setupOverlayRendering:()V
        //   701: aload           33
        //   703: getfield        javax/vecmath/Vector4d.x:D
        //   706: dstore          34
        //   708: aload           33
        //   710: getfield        javax/vecmath/Vector4d.y:D
        //   713: dstore          36
        //   715: aload           33
        //   717: getfield        javax/vecmath/Vector4d.z:D
        //   720: dstore          38
        //   722: aload           33
        //   724: getfield        javax/vecmath/Vector4d.w:D
        //   727: dstore          40
        //   729: getstatic       com/nquantum/module/customize/HUD.hudColor:I
        //   732: istore          16
        //   734: iload           16
        //   736: istore          43
        //   738: dload           34
        //   740: dconst_1       
        //   741: dsub           
        //   742: dload           36
        //   744: dload           34
        //   746: ldc2_w          0.5
        //   749: dadd           
        //   750: dload           40
        //   752: ldc2_w          0.5
        //   755: dadd           
        //   756: iload           7
        //   758: invokestatic    net/minecraft/client/gui/Gui.drawRect:(DDDDI)V
        //   761: dload           34
        //   763: dconst_1       
        //   764: dsub           
        //   765: dload           36
        //   767: ldc2_w          0.5
        //   770: dsub           
        //   771: dload           38
        //   773: ldc2_w          0.5
        //   776: dadd           
        //   777: dload           36
        //   779: ldc2_w          0.5
        //   782: dadd           
        //   783: ldc2_w          0.5
        //   786: dadd           
        //   787: iload           7
        //   789: invokestatic    net/minecraft/client/gui/Gui.drawRect:(DDDDI)V
        //   792: dload           38
        //   794: ldc2_w          0.5
        //   797: dsub           
        //   798: ldc2_w          0.5
        //   801: dsub           
        //   802: dload           36
        //   804: dload           38
        //   806: ldc2_w          0.5
        //   809: dadd           
        //   810: dload           40
        //   812: ldc2_w          0.5
        //   815: dadd           
        //   816: iload           7
        //   818: invokestatic    net/minecraft/client/gui/Gui.drawRect:(DDDDI)V
        //   821: dload           34
        //   823: dconst_1       
        //   824: dsub           
        //   825: dload           40
        //   827: ldc2_w          0.5
        //   830: dsub           
        //   831: ldc2_w          0.5
        //   834: dsub           
        //   835: dload           38
        //   837: ldc2_w          0.5
        //   840: dadd           
        //   841: dload           40
        //   843: ldc2_w          0.5
        //   846: dadd           
        //   847: iload           7
        //   849: invokestatic    net/minecraft/client/gui/Gui.drawRect:(DDDDI)V
        //   852: dload           34
        //   854: ldc2_w          0.5
        //   857: dsub           
        //   858: dload           36
        //   860: dload           34
        //   862: ldc2_w          0.5
        //   865: dadd           
        //   866: ldc2_w          0.5
        //   869: dsub           
        //   870: dload           40
        //   872: iload           43
        //   874: invokestatic    net/minecraft/client/gui/Gui.drawRect:(DDDDI)V
        //   877: dload           34
        //   879: dload           40
        //   881: ldc2_w          0.5
        //   884: dsub           
        //   885: dload           38
        //   887: dload           40
        //   889: iload           43
        //   891: invokestatic    net/minecraft/client/gui/Gui.drawRect:(DDDDI)V
        //   894: dload           34
        //   896: ldc2_w          0.5
        //   899: dsub           
        //   900: dload           36
        //   902: dload           38
        //   904: dload           36
        //   906: ldc2_w          0.5
        //   909: dadd           
        //   910: iload           43
        //   912: invokestatic    net/minecraft/client/gui/Gui.drawRect:(DDDDI)V
        //   915: dload           38
        //   917: ldc2_w          0.5
        //   920: dsub           
        //   921: dload           36
        //   923: dload           38
        //   925: dload           40
        //   927: iload           43
        //   929: invokestatic    net/minecraft/client/gui/Gui.drawRect:(DDDDI)V
        //   932: aload           20
        //   934: instanceof      Lnet/minecraft/entity/EntityLivingBase;
        //   937: istore          34
        //   939: goto            1169
        //   942: aload           33
        //   944: getfield        javax/vecmath/Vector4d.x:D
        //   947: dstore          35
        //   949: aload           33
        //   951: getfield        javax/vecmath/Vector4d.y:D
        //   954: dstore          37
        //   956: aload           33
        //   958: getfield        javax/vecmath/Vector4d.z:D
        //   961: dstore          39
        //   963: aload           33
        //   965: getfield        javax/vecmath/Vector4d.w:D
        //   968: dstore          41
        //   970: aload           20
        //   972: checkcast       Lnet/minecraft/entity/EntityLivingBase;
        //   975: astore          43
        //   977: aload           43
        //   979: invokevirtual   net/minecraft/entity/EntityLivingBase.getHealth:()F
        //   982: fstore          44
        //   984: aload           43
        //   986: invokevirtual   net/minecraft/entity/EntityLivingBase.getMaxHealth:()F
        //   989: fstore          45
        //   991: fload           44
        //   993: fload           45
        //   995: fcmpl          
        //   996: ifle            1003
        //   999: fload           45
        //  1001: fstore          44
        //  1003: fload           44
        //  1005: fload           45
        //  1007: fdiv           
        //  1008: f2d            
        //  1009: dstore          46
        //  1011: dload           41
        //  1013: dload           37
        //  1015: dsub           
        //  1016: dload           46
        //  1018: dmul           
        //  1019: dstore          48
        //  1021: dload           35
        //  1023: ldc2_w          3.5
        //  1026: dsub           
        //  1027: dload           37
        //  1029: ldc2_w          0.5
        //  1032: dsub           
        //  1033: dload           35
        //  1035: ldc2_w          1.5
        //  1038: dsub           
        //  1039: dload           41
        //  1041: ldc2_w          0.5
        //  1044: dadd           
        //  1045: iload           8
        //  1047: invokestatic    net/minecraft/client/gui/Gui.drawRect:(DDDDI)V
        //  1050: fload           44
        //  1052: fconst_0       
        //  1053: fcmpl          
        //  1054: ifle            1169
        //  1057: new             Ljava/awt/Color;
        //  1060: dup            
        //  1061: bipush          20
        //  1063: sipush          255
        //  1066: iconst_5       
        //  1067: sipush          185
        //  1070: invokespecial   java/awt/Color.<init>:(IIII)V
        //  1073: invokevirtual   java/awt/Color.getRGB:()I
        //  1076: istore          50
        //  1078: dload           35
        //  1080: ldc2_w          3.0
        //  1083: dsub           
        //  1084: dload           41
        //  1086: dload           35
        //  1088: ldc2_w          2.0
        //  1091: dsub           
        //  1092: dload           41
        //  1094: dload           48
        //  1096: dsub           
        //  1097: iload           50
        //  1099: invokestatic    net/minecraft/client/gui/Gui.drawRect:(DDDDI)V
        //  1102: aload           43
        //  1104: invokevirtual   net/minecraft/entity/EntityLivingBase.getAbsorptionAmount:()F
        //  1107: fstore          51
        //  1109: fload           51
        //  1111: fconst_0       
        //  1112: fcmpl          
        //  1113: ifle            1169
        //  1116: dload           35
        //  1118: ldc2_w          3.0
        //  1121: dsub           
        //  1122: dload           41
        //  1124: dload           35
        //  1126: ldc2_w          2.0
        //  1129: dsub           
        //  1130: dload           41
        //  1132: dload           41
        //  1134: dload           37
        //  1136: dsub           
        //  1137: ldc2_w          6.0
        //  1140: ddiv           
        //  1141: fload           51
        //  1143: f2d            
        //  1144: dmul           
        //  1145: ldc2_w          2.0
        //  1148: ddiv           
        //  1149: dsub           
        //  1150: new             Ljava/awt/Color;
        //  1153: dup            
        //  1154: getstatic       net/minecraft/potion/Potion.absorption:Lnet/minecraft/potion/Potion;
        //  1157: invokevirtual   net/minecraft/potion/Potion.getLiquidColor:()I
        //  1160: invokespecial   java/awt/Color.<init>:(I)V
        //  1163: invokevirtual   java/awt/Color.getRGB:()I
        //  1166: invokestatic    net/minecraft/client/gui/Gui.drawRect:(DDDDI)V
        //  1169: aload           33
        //  1171: getfield        javax/vecmath/Vector4d.x:D
        //  1174: dstore          36
        //  1176: aload           33
        //  1178: getfield        javax/vecmath/Vector4d.y:D
        //  1181: dstore          38
        //  1183: aload           33
        //  1185: getfield        javax/vecmath/Vector4d.z:D
        //  1188: dstore          40
        //  1190: aload           33
        //  1192: getfield        javax/vecmath/Vector4d.w:D
        //  1195: dstore          42
        //  1197: ldc_w           10.0
        //  1200: fstore          51
        //  1202: aload           20
        //  1204: invokevirtual   net/minecraft/entity/Entity.getName:()Ljava/lang/String;
        //  1207: astore          52
        //  1209: aload           20
        //  1211: instanceof      Lnet/minecraft/entity/item/EntityItem;
        //  1214: ifeq            1230
        //  1217: aload           20
        //  1219: checkcast       Lnet/minecraft/entity/item/EntityItem;
        //  1222: invokevirtual   net/minecraft/entity/item/EntityItem.getEntityItem:()Lnet/minecraft/item/ItemStack;
        //  1225: invokevirtual   net/minecraft/item/ItemStack.getUnlocalizedName:()Ljava/lang/String;
        //  1228: astore          52
        //  1230: ldc_w           ""
        //  1233: astore          53
        //  1235: aload           20
        //  1237: instanceof      Lnet/minecraft/entity/passive/EntityAnimal;
        //  1240: ifne            1251
        //  1243: aload           20
        //  1245: instanceof      Lnet/minecraft/entity/monster/EntityMob;
        //  1248: ifeq            1275
        //  1251: new             Ljava/lang/StringBuilder;
        //  1254: dup            
        //  1255: invokespecial   java/lang/StringBuilder.<init>:()V
        //  1258: getstatic       com/mojang/realmsclient/gui/ChatFormatting.GREEN:Lcom/mojang/realmsclient/gui/ChatFormatting;
        //  1261: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //  1264: ldc_w           ""
        //  1267: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1270: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //  1273: astore          53
        //  1275: aload           20
        //  1277: instanceof      Lnet/minecraft/entity/item/EntityItem;
        //  1280: ifeq            1307
        //  1283: new             Ljava/lang/StringBuilder;
        //  1286: dup            
        //  1287: invokespecial   java/lang/StringBuilder.<init>:()V
        //  1290: getstatic       com/mojang/realmsclient/gui/ChatFormatting.BLUE:Lcom/mojang/realmsclient/gui/ChatFormatting;
        //  1293: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //  1296: ldc_w           ""
        //  1299: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1302: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //  1305: astore          53
        //  1307: dload           40
        //  1309: dload           36
        //  1311: dsub           
        //  1312: ldc2_w          2.0
        //  1315: ddiv           
        //  1316: dstore          46
        //  1318: aload_0        
        //  1319: getfield        com/nquantum/module/render/ESP2D.mc:Lnet/minecraft/client/Minecraft;
        //  1322: getfield        net/minecraft/client/Minecraft.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //  1325: aload           52
        //  1327: invokevirtual   net/minecraft/client/gui/FontRenderer.getStringWidth:(Ljava/lang/String;)I
        //  1330: i2f            
        //  1331: fload           9
        //  1333: fmul           
        //  1334: f2d            
        //  1335: dstore          48
        //  1337: dload           36
        //  1339: dload           46
        //  1341: dadd           
        //  1342: dload           48
        //  1344: ldc2_w          2.0
        //  1347: ddiv           
        //  1348: dsub           
        //  1349: fload           10
        //  1351: f2d            
        //  1352: dmul           
        //  1353: d2f            
        //  1354: fstore          54
        //  1356: dload           38
        //  1358: fload           10
        //  1360: f2d            
        //  1361: dmul           
        //  1362: d2f            
        //  1363: fload           51
        //  1365: fsub           
        //  1366: fstore          50
        //  1368: invokestatic    org/lwjgl/opengl/GL11.glPushMatrix:()V
        //  1371: fload           9
        //  1373: fload           9
        //  1375: fload           9
        //  1377: invokestatic    org/lwjgl/opengl/GL11.glScalef:(FFF)V
        //  1380: goto            1433
        //  1383: fload           54
        //  1385: fconst_2       
        //  1386: fsub           
        //  1387: f2d            
        //  1388: fload           50
        //  1390: fconst_2       
        //  1391: fsub           
        //  1392: f2d            
        //  1393: fload           54
        //  1395: f2d            
        //  1396: dload           48
        //  1398: fload           10
        //  1400: f2d            
        //  1401: dmul           
        //  1402: dadd           
        //  1403: ldc2_w          2.0
        //  1406: dadd           
        //  1407: fload           50
        //  1409: ldc_w           9.0
        //  1412: fadd           
        //  1413: f2d            
        //  1414: new             Ljava/awt/Color;
        //  1417: dup            
        //  1418: iconst_0       
        //  1419: iconst_0       
        //  1420: iconst_0       
        //  1421: sipush          140
        //  1424: invokespecial   java/awt/Color.<init>:(IIII)V
        //  1427: invokevirtual   java/awt/Color.getRGB:()I
        //  1430: invokestatic    net/minecraft/client/gui/Gui.drawRect:(DDDDI)V
        //  1433: aload_0        
        //  1434: getfield        com/nquantum/module/render/ESP2D.mc:Lnet/minecraft/client/Minecraft;
        //  1437: getfield        net/minecraft/client/Minecraft.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //  1440: new             Ljava/lang/StringBuilder;
        //  1443: dup            
        //  1444: invokespecial   java/lang/StringBuilder.<init>:()V
        //  1447: aload           53
        //  1449: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1452: aload           52
        //  1454: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1457: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //  1460: fload           54
        //  1462: fload           50
        //  1464: new             Ljava/awt/Color;
        //  1467: dup            
        //  1468: sipush          157
        //  1471: bipush          114
        //  1473: sipush          227
        //  1476: invokespecial   java/awt/Color.<init>:(III)V
        //  1479: invokevirtual   java/awt/Color.getRGB:()I
        //  1482: invokevirtual   net/minecraft/client/gui/FontRenderer.drawStringWithShadow:(Ljava/lang/String;FFI)I
        //  1485: pop            
        //  1486: invokestatic    org/lwjgl/opengl/GL11.glPopMatrix:()V
        //  1489: iinc            18, 1
        //  1492: goto            115
        //  1495: invokestatic    org/lwjgl/opengl/GL11.glPopMatrix:()V
        //  1498: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0115 (coming from #1492).
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
    
    private final void drawRat() {
    }
    
    public ESP2D() {
        super("ESP2D", 0, Category.RENDER);
        this.collectedEntities = new ArrayList();
        this.viewport = GLAllocation.createDirectIntBuffer(16);
        this.modelview = GLAllocation.createDirectFloatBuffer(16);
        this.projection = GLAllocation.createDirectFloatBuffer(16);
        this.vector = GLAllocation.createDirectFloatBuffer(4);
        this.backgroundColor = new Color(0, 0, 0, 120).getRGB();
        this.black = Color.BLACK.getRGB();
    }
}
