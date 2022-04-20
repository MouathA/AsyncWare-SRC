package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.*;

public class LayerCape implements LayerRenderer
{
    private static final String __OBFID = "CL_00002425";
    private final RenderPlayer playerRenderer;
    
    public void doRenderLayer(final AbstractClientPlayer p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6, final float p7) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   net/minecraft/client/entity/AbstractClientPlayer.hasPlayerInfo:()Z
        //     4: ifeq            442
        //     7: aload_1        
        //     8: invokevirtual   net/minecraft/client/entity/AbstractClientPlayer.isInvisible:()Z
        //    11: ifne            442
        //    14: aload_1        
        //    15: getstatic       net/minecraft/entity/player/EnumPlayerModelParts.CAPE:Lnet/minecraft/entity/player/EnumPlayerModelParts;
        //    18: invokevirtual   net/minecraft/client/entity/AbstractClientPlayer.isWearing:(Lnet/minecraft/entity/player/EnumPlayerModelParts;)Z
        //    21: ifeq            442
        //    24: aload_1        
        //    25: invokevirtual   net/minecraft/client/entity/AbstractClientPlayer.getName:()Ljava/lang/String;
        //    28: invokestatic    net/minecraft/client/Minecraft.getMinecraft:()Lnet/minecraft/client/Minecraft;
        //    31: invokevirtual   net/minecraft/client/Minecraft.getSession:()Lnet/minecraft/util/Session;
        //    34: invokevirtual   net/minecraft/util/Session.getUsername:()Ljava/lang/String;
        //    37: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //    40: fconst_1       
        //    41: fconst_1       
        //    42: fconst_1       
        //    43: fconst_1       
        //    44: invokestatic    net/minecraft/client/renderer/GlStateManager.color:(FFFF)V
        //    47: aload_0        
        //    48: getfield        net/minecraft/client/renderer/entity/layers/LayerCape.playerRenderer:Lnet/minecraft/client/renderer/entity/RenderPlayer;
        //    51: new             Lnet/minecraft/util/ResourceLocation;
        //    54: dup            
        //    55: ldc             "asyncware/capelol.png"
        //    57: invokespecial   net/minecraft/util/ResourceLocation.<init>:(Ljava/lang/String;)V
        //    60: invokevirtual   net/minecraft/client/renderer/entity/RenderPlayer.bindTexture:(Lnet/minecraft/util/ResourceLocation;)V
        //    63: fconst_0       
        //    64: fconst_0       
        //    65: ldc             0.125
        //    67: invokestatic    net/minecraft/client/renderer/GlStateManager.translate:(FFF)V
        //    70: aload_1        
        //    71: getfield        net/minecraft/client/entity/AbstractClientPlayer.prevChasingPosX:D
        //    74: aload_1        
        //    75: getfield        net/minecraft/client/entity/AbstractClientPlayer.chasingPosX:D
        //    78: aload_1        
        //    79: getfield        net/minecraft/client/entity/AbstractClientPlayer.prevChasingPosX:D
        //    82: dsub           
        //    83: fload           4
        //    85: f2d            
        //    86: dmul           
        //    87: dadd           
        //    88: aload_1        
        //    89: getfield        net/minecraft/client/entity/AbstractClientPlayer.prevPosX:D
        //    92: aload_1        
        //    93: getfield        net/minecraft/client/entity/AbstractClientPlayer.posX:D
        //    96: aload_1        
        //    97: getfield        net/minecraft/client/entity/AbstractClientPlayer.prevPosX:D
        //   100: dsub           
        //   101: fload           4
        //   103: f2d            
        //   104: dmul           
        //   105: dadd           
        //   106: dsub           
        //   107: dstore          9
        //   109: aload_1        
        //   110: getfield        net/minecraft/client/entity/AbstractClientPlayer.prevChasingPosY:D
        //   113: aload_1        
        //   114: getfield        net/minecraft/client/entity/AbstractClientPlayer.chasingPosY:D
        //   117: aload_1        
        //   118: getfield        net/minecraft/client/entity/AbstractClientPlayer.prevChasingPosY:D
        //   121: dsub           
        //   122: fload           4
        //   124: f2d            
        //   125: dmul           
        //   126: dadd           
        //   127: aload_1        
        //   128: getfield        net/minecraft/client/entity/AbstractClientPlayer.prevPosY:D
        //   131: aload_1        
        //   132: getfield        net/minecraft/client/entity/AbstractClientPlayer.posY:D
        //   135: aload_1        
        //   136: getfield        net/minecraft/client/entity/AbstractClientPlayer.prevPosY:D
        //   139: dsub           
        //   140: fload           4
        //   142: f2d            
        //   143: dmul           
        //   144: dadd           
        //   145: dsub           
        //   146: dstore          11
        //   148: aload_1        
        //   149: getfield        net/minecraft/client/entity/AbstractClientPlayer.prevChasingPosZ:D
        //   152: aload_1        
        //   153: getfield        net/minecraft/client/entity/AbstractClientPlayer.chasingPosZ:D
        //   156: aload_1        
        //   157: getfield        net/minecraft/client/entity/AbstractClientPlayer.prevChasingPosZ:D
        //   160: dsub           
        //   161: fload           4
        //   163: f2d            
        //   164: dmul           
        //   165: dadd           
        //   166: aload_1        
        //   167: getfield        net/minecraft/client/entity/AbstractClientPlayer.prevPosZ:D
        //   170: aload_1        
        //   171: getfield        net/minecraft/client/entity/AbstractClientPlayer.posZ:D
        //   174: aload_1        
        //   175: getfield        net/minecraft/client/entity/AbstractClientPlayer.prevPosZ:D
        //   178: dsub           
        //   179: fload           4
        //   181: f2d            
        //   182: dmul           
        //   183: dadd           
        //   184: dsub           
        //   185: dstore          13
        //   187: aload_1        
        //   188: getfield        net/minecraft/client/entity/AbstractClientPlayer.prevRenderYawOffset:F
        //   191: aload_1        
        //   192: getfield        net/minecraft/client/entity/AbstractClientPlayer.renderYawOffset:F
        //   195: aload_1        
        //   196: getfield        net/minecraft/client/entity/AbstractClientPlayer.prevRenderYawOffset:F
        //   199: fsub           
        //   200: fload           4
        //   202: fmul           
        //   203: fadd           
        //   204: fstore          15
        //   206: fload           15
        //   208: ldc             3.1415927
        //   210: fmul           
        //   211: ldc             180.0
        //   213: fdiv           
        //   214: invokestatic    net/minecraft/util/MathHelper.sin:(F)F
        //   217: f2d            
        //   218: dstore          16
        //   220: fload           15
        //   222: ldc             3.1415927
        //   224: fmul           
        //   225: ldc             180.0
        //   227: fdiv           
        //   228: invokestatic    net/minecraft/util/MathHelper.cos:(F)F
        //   231: fneg           
        //   232: f2d            
        //   233: dstore          18
        //   235: dload           11
        //   237: d2f            
        //   238: ldc             10.0
        //   240: fmul           
        //   241: fstore          20
        //   243: fload           20
        //   245: ldc             -6.0
        //   247: ldc             32.0
        //   249: invokestatic    net/minecraft/util/MathHelper.clamp_float:(FFF)F
        //   252: fstore          20
        //   254: dload           9
        //   256: dload           16
        //   258: dmul           
        //   259: dload           13
        //   261: dload           18
        //   263: dmul           
        //   264: dadd           
        //   265: d2f            
        //   266: ldc             100.0
        //   268: fmul           
        //   269: fstore          21
        //   271: dload           9
        //   273: dload           18
        //   275: dmul           
        //   276: dload           13
        //   278: dload           16
        //   280: dmul           
        //   281: dsub           
        //   282: d2f            
        //   283: ldc             100.0
        //   285: fmul           
        //   286: fstore          22
        //   288: fload           21
        //   290: fconst_0       
        //   291: fcmpg          
        //   292: ifge            298
        //   295: fconst_0       
        //   296: fstore          21
        //   298: fload           21
        //   300: ldc             165.0
        //   302: fcmpl          
        //   303: ifle            310
        //   306: ldc             165.0
        //   308: fstore          21
        //   310: aload_1        
        //   311: getfield        net/minecraft/client/entity/AbstractClientPlayer.prevCameraYaw:F
        //   314: aload_1        
        //   315: getfield        net/minecraft/client/entity/AbstractClientPlayer.cameraYaw:F
        //   318: aload_1        
        //   319: getfield        net/minecraft/client/entity/AbstractClientPlayer.prevCameraYaw:F
        //   322: fsub           
        //   323: fload           4
        //   325: fmul           
        //   326: fadd           
        //   327: fstore          23
        //   329: fload           20
        //   331: aload_1        
        //   332: getfield        net/minecraft/client/entity/AbstractClientPlayer.prevDistanceWalkedModified:F
        //   335: aload_1        
        //   336: getfield        net/minecraft/client/entity/AbstractClientPlayer.distanceWalkedModified:F
        //   339: aload_1        
        //   340: getfield        net/minecraft/client/entity/AbstractClientPlayer.prevDistanceWalkedModified:F
        //   343: fsub           
        //   344: fload           4
        //   346: fmul           
        //   347: fadd           
        //   348: ldc             6.0
        //   350: fmul           
        //   351: invokestatic    net/minecraft/util/MathHelper.sin:(F)F
        //   354: ldc             32.0
        //   356: fmul           
        //   357: fload           23
        //   359: fmul           
        //   360: fadd           
        //   361: fstore          20
        //   363: aload_1        
        //   364: invokevirtual   net/minecraft/client/entity/AbstractClientPlayer.isSneaking:()Z
        //   367: ifeq            385
        //   370: fload           20
        //   372: ldc             25.0
        //   374: fadd           
        //   375: fstore          20
        //   377: fconst_0       
        //   378: ldc             0.142
        //   380: ldc             -0.0178
        //   382: invokestatic    net/minecraft/client/renderer/GlStateManager.translate:(FFF)V
        //   385: ldc             6.0
        //   387: fload           21
        //   389: fconst_2       
        //   390: fdiv           
        //   391: fadd           
        //   392: fload           20
        //   394: fadd           
        //   395: fconst_1       
        //   396: fconst_0       
        //   397: fconst_0       
        //   398: invokestatic    net/minecraft/client/renderer/GlStateManager.rotate:(FFFF)V
        //   401: fload           22
        //   403: fconst_2       
        //   404: fdiv           
        //   405: fconst_0       
        //   406: fconst_0       
        //   407: fconst_1       
        //   408: invokestatic    net/minecraft/client/renderer/GlStateManager.rotate:(FFFF)V
        //   411: fload           22
        //   413: fneg           
        //   414: fconst_2       
        //   415: fdiv           
        //   416: fconst_0       
        //   417: fconst_1       
        //   418: fconst_0       
        //   419: invokestatic    net/minecraft/client/renderer/GlStateManager.rotate:(FFFF)V
        //   422: ldc             180.0
        //   424: fconst_0       
        //   425: fconst_1       
        //   426: fconst_0       
        //   427: invokestatic    net/minecraft/client/renderer/GlStateManager.rotate:(FFFF)V
        //   430: aload_0        
        //   431: getfield        net/minecraft/client/renderer/entity/layers/LayerCape.playerRenderer:Lnet/minecraft/client/renderer/entity/RenderPlayer;
        //   434: invokevirtual   net/minecraft/client/renderer/entity/RenderPlayer.getMainModel:()Lnet/minecraft/client/model/ModelPlayer;
        //   437: ldc             0.0625
        //   439: invokevirtual   net/minecraft/client/model/ModelPlayer.renderCape:(F)V
        //   442: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0442 (coming from #0439).
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
    
    public LayerCape(final RenderPlayer playerRenderer) {
        this.playerRenderer = playerRenderer;
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.doRenderLayer((AbstractClientPlayer)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
