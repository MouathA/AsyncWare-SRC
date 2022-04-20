package net.minecraft.client.renderer;

import net.minecraft.util.*;

public class RenderList extends ChunkRenderContainer
{
    private static final String __OBFID = "CL_00000957";
    
    @Override
    public void renderChunkLayer(final EnumWorldBlockLayer p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/client/renderer/RenderList.initialized:Z
        //     4: ifeq            89
        //     7: aload_0        
        //     8: getfield        net/minecraft/client/renderer/RenderList.renderChunks:Ljava/util/List;
        //    11: invokeinterface java/util/List.size:()I
        //    16: ifne            20
        //    19: return         
        //    20: aload_0        
        //    21: getfield        net/minecraft/client/renderer/RenderList.renderChunks:Ljava/util/List;
        //    24: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //    29: astore_2       
        //    30: aload_2        
        //    31: invokeinterface java/util/Iterator.hasNext:()Z
        //    36: ifeq            77
        //    39: aload_2        
        //    40: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    45: checkcast       Lnet/minecraft/client/renderer/chunk/RenderChunk;
        //    48: astore_3       
        //    49: aload_3        
        //    50: checkcast       Lnet/minecraft/client/renderer/chunk/ListedRenderChunk;
        //    53: astore          4
        //    55: aload_0        
        //    56: aload_3        
        //    57: invokevirtual   net/minecraft/client/renderer/RenderList.preRenderChunk:(Lnet/minecraft/client/renderer/chunk/RenderChunk;)V
        //    60: aload           4
        //    62: aload_1        
        //    63: aload           4
        //    65: invokevirtual   net/minecraft/client/renderer/chunk/ListedRenderChunk.getCompiledChunk:()Lnet/minecraft/client/renderer/chunk/CompiledChunk;
        //    68: invokevirtual   net/minecraft/client/renderer/chunk/ListedRenderChunk.getDisplayList:(Lnet/minecraft/util/EnumWorldBlockLayer;Lnet/minecraft/client/renderer/chunk/CompiledChunk;)I
        //    71: invokestatic    org/lwjgl/opengl/GL11.glCallList:(I)V
        //    74: goto            30
        //    77: invokestatic    optfine/Config.isMultiTexture:()Z
        //    80: aload_0        
        //    81: getfield        net/minecraft/client/renderer/RenderList.renderChunks:Ljava/util/List;
        //    84: invokeinterface java/util/List.clear:()V
        //    89: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0089 (coming from #0084).
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
}
