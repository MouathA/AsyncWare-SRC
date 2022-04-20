package net.minecraft.client.shader;

import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.texture.*;
import java.nio.*;

public class Framebuffer
{
    public int framebufferTextureHeight;
    public int framebufferWidth;
    public int framebufferTextureWidth;
    public int framebufferObject;
    public int depthBuffer;
    public float[] framebufferColor;
    public int framebufferTexture;
    public boolean useDepth;
    public int framebufferHeight;
    public int framebufferFilter;
    
    public void unbindFramebufferTexture() {
        if (OpenGlHelper.isFramebufferEnabled()) {
            GlStateManager.bindTexture(0);
        }
    }
    
    public void setFramebufferFilter(final int framebufferFilter) {
        if (OpenGlHelper.isFramebufferEnabled()) {
            this.framebufferFilter = framebufferFilter;
            GlStateManager.bindTexture(this.framebufferTexture);
            GL11.glTexParameterf(3553, 10241, (float)framebufferFilter);
            GL11.glTexParameterf(3553, 10240, (float)framebufferFilter);
            GL11.glTexParameterf(3553, 10242, 10496.0f);
            GL11.glTexParameterf(3553, 10243, 10496.0f);
            GlStateManager.bindTexture(0);
        }
    }
    
    public void bindFramebufferTexture() {
        if (OpenGlHelper.isFramebufferEnabled()) {
            GlStateManager.bindTexture(this.framebufferTexture);
        }
    }
    
    public void deleteFramebuffer() {
        if (OpenGlHelper.isFramebufferEnabled()) {
            this.unbindFramebufferTexture();
            this.unbindFramebuffer();
            if (this.depthBuffer > -1) {
                OpenGlHelper.glDeleteRenderbuffers(this.depthBuffer);
                this.depthBuffer = -1;
            }
            if (this.framebufferTexture > -1) {
                TextureUtil.deleteTexture(this.framebufferTexture);
                this.framebufferTexture = -1;
            }
            if (this.framebufferObject > -1) {
                OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
                OpenGlHelper.glDeleteFramebuffers(this.framebufferObject);
                this.framebufferObject = -1;
            }
        }
    }
    
    public void createFramebuffer(final int n, final int n2) {
        this.framebufferWidth = n;
        this.framebufferHeight = n2;
        this.framebufferTextureWidth = n;
        this.framebufferTextureHeight = n2;
        if (!OpenGlHelper.isFramebufferEnabled()) {
            this.framebufferClear();
        }
        else {
            this.framebufferObject = OpenGlHelper.glGenFramebuffers();
            this.framebufferTexture = TextureUtil.glGenTextures();
            if (this.useDepth) {
                this.depthBuffer = OpenGlHelper.glGenRenderbuffers();
            }
            this.setFramebufferFilter(9728);
            GlStateManager.bindTexture(this.framebufferTexture);
            GL11.glTexImage2D(3553, 0, 32856, this.framebufferTextureWidth, this.framebufferTextureHeight, 0, 6408, 5121, (ByteBuffer)null);
            OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, this.framebufferObject);
            OpenGlHelper.glFramebufferTexture2D(OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.GL_COLOR_ATTACHMENT0, 3553, this.framebufferTexture, 0);
            if (this.useDepth) {
                OpenGlHelper.glBindRenderbuffer(OpenGlHelper.GL_RENDERBUFFER, this.depthBuffer);
                OpenGlHelper.glRenderbufferStorage(OpenGlHelper.GL_RENDERBUFFER, 33190, this.framebufferTextureWidth, this.framebufferTextureHeight);
                OpenGlHelper.glFramebufferRenderbuffer(OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.GL_DEPTH_ATTACHMENT, OpenGlHelper.GL_RENDERBUFFER, this.depthBuffer);
            }
            this.framebufferClear();
            this.unbindFramebufferTexture();
        }
    }
    
    public Framebuffer(final int n, final int n2, final boolean useDepth) {
        this.useDepth = useDepth;
        this.framebufferObject = -1;
        this.framebufferTexture = -1;
        this.depthBuffer = -1;
        (this.framebufferColor = new float[4])[0] = 1.0f;
        this.framebufferColor[1] = 1.0f;
        this.framebufferColor[2] = 1.0f;
        this.framebufferColor[3] = 0.0f;
        this.createBindFramebuffer(n, n2);
    }
    
    public void framebufferRender(final int n, final int n2) {
        this.framebufferRenderExt(n, n2, true);
    }
    
    public void framebufferRenderExt(final int p0, final int p1, final boolean p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: ifeq            286
        //     6: iconst_1       
        //     7: iconst_1       
        //     8: iconst_1       
        //     9: iconst_0       
        //    10: invokestatic    net/minecraft/client/renderer/GlStateManager.colorMask:(ZZZZ)V
        //    13: iconst_0       
        //    14: invokestatic    net/minecraft/client/renderer/GlStateManager.depthMask:(Z)V
        //    17: sipush          5889
        //    20: invokestatic    net/minecraft/client/renderer/GlStateManager.matrixMode:(I)V
        //    23: dconst_0       
        //    24: iload_1        
        //    25: i2d            
        //    26: iload_2        
        //    27: i2d            
        //    28: dconst_0       
        //    29: ldc2_w          1000.0
        //    32: ldc2_w          3000.0
        //    35: invokestatic    net/minecraft/client/renderer/GlStateManager.ortho:(DDDDDD)V
        //    38: sipush          5888
        //    41: invokestatic    net/minecraft/client/renderer/GlStateManager.matrixMode:(I)V
        //    44: fconst_0       
        //    45: fconst_0       
        //    46: ldc             -2000.0
        //    48: invokestatic    net/minecraft/client/renderer/GlStateManager.translate:(FFF)V
        //    51: iconst_0       
        //    52: iconst_0       
        //    53: iload_1        
        //    54: iload_2        
        //    55: invokestatic    net/minecraft/client/renderer/GlStateManager.viewport:(IIII)V
        //    58: iload_3        
        //    59: fconst_1       
        //    60: fconst_1       
        //    61: fconst_1       
        //    62: fconst_1       
        //    63: invokestatic    net/minecraft/client/renderer/GlStateManager.color:(FFFF)V
        //    66: aload_0        
        //    67: invokevirtual   net/minecraft/client/shader/Framebuffer.bindFramebufferTexture:()V
        //    70: iload_1        
        //    71: i2f            
        //    72: fstore          4
        //    74: iload_2        
        //    75: i2f            
        //    76: fstore          5
        //    78: aload_0        
        //    79: getfield        net/minecraft/client/shader/Framebuffer.framebufferWidth:I
        //    82: i2f            
        //    83: aload_0        
        //    84: getfield        net/minecraft/client/shader/Framebuffer.framebufferTextureWidth:I
        //    87: i2f            
        //    88: fdiv           
        //    89: fstore          6
        //    91: aload_0        
        //    92: getfield        net/minecraft/client/shader/Framebuffer.framebufferHeight:I
        //    95: i2f            
        //    96: aload_0        
        //    97: getfield        net/minecraft/client/shader/Framebuffer.framebufferTextureHeight:I
        //   100: i2f            
        //   101: fdiv           
        //   102: fstore          7
        //   104: invokestatic    net/minecraft/client/renderer/Tessellator.getInstance:()Lnet/minecraft/client/renderer/Tessellator;
        //   107: astore          8
        //   109: aload           8
        //   111: invokevirtual   net/minecraft/client/renderer/Tessellator.getWorldRenderer:()Lnet/minecraft/client/renderer/WorldRenderer;
        //   114: astore          9
        //   116: aload           9
        //   118: bipush          7
        //   120: getstatic       net/minecraft/client/renderer/vertex/DefaultVertexFormats.POSITION_TEX_COLOR:Lnet/minecraft/client/renderer/vertex/VertexFormat;
        //   123: invokevirtual   net/minecraft/client/renderer/WorldRenderer.begin:(ILnet/minecraft/client/renderer/vertex/VertexFormat;)V
        //   126: aload           9
        //   128: dconst_0       
        //   129: fload           5
        //   131: f2d            
        //   132: dconst_0       
        //   133: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   136: dconst_0       
        //   137: dconst_0       
        //   138: invokevirtual   net/minecraft/client/renderer/WorldRenderer.tex:(DD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   141: sipush          255
        //   144: sipush          255
        //   147: sipush          255
        //   150: sipush          255
        //   153: invokevirtual   net/minecraft/client/renderer/WorldRenderer.color:(IIII)Lnet/minecraft/client/renderer/WorldRenderer;
        //   156: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //   159: aload           9
        //   161: fload           4
        //   163: f2d            
        //   164: fload           5
        //   166: f2d            
        //   167: dconst_0       
        //   168: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   171: fload           6
        //   173: f2d            
        //   174: dconst_0       
        //   175: invokevirtual   net/minecraft/client/renderer/WorldRenderer.tex:(DD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   178: sipush          255
        //   181: sipush          255
        //   184: sipush          255
        //   187: sipush          255
        //   190: invokevirtual   net/minecraft/client/renderer/WorldRenderer.color:(IIII)Lnet/minecraft/client/renderer/WorldRenderer;
        //   193: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //   196: aload           9
        //   198: fload           4
        //   200: f2d            
        //   201: dconst_0       
        //   202: dconst_0       
        //   203: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   206: fload           6
        //   208: f2d            
        //   209: fload           7
        //   211: f2d            
        //   212: invokevirtual   net/minecraft/client/renderer/WorldRenderer.tex:(DD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   215: sipush          255
        //   218: sipush          255
        //   221: sipush          255
        //   224: sipush          255
        //   227: invokevirtual   net/minecraft/client/renderer/WorldRenderer.color:(IIII)Lnet/minecraft/client/renderer/WorldRenderer;
        //   230: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //   233: aload           9
        //   235: dconst_0       
        //   236: dconst_0       
        //   237: dconst_0       
        //   238: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   241: dconst_0       
        //   242: fload           7
        //   244: f2d            
        //   245: invokevirtual   net/minecraft/client/renderer/WorldRenderer.tex:(DD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   248: sipush          255
        //   251: sipush          255
        //   254: sipush          255
        //   257: sipush          255
        //   260: invokevirtual   net/minecraft/client/renderer/WorldRenderer.color:(IIII)Lnet/minecraft/client/renderer/WorldRenderer;
        //   263: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //   266: aload           8
        //   268: invokevirtual   net/minecraft/client/renderer/Tessellator.draw:()V
        //   271: aload_0        
        //   272: invokevirtual   net/minecraft/client/shader/Framebuffer.unbindFramebufferTexture:()V
        //   275: iconst_1       
        //   276: invokestatic    net/minecraft/client/renderer/GlStateManager.depthMask:(Z)V
        //   279: iconst_1       
        //   280: iconst_1       
        //   281: iconst_1       
        //   282: iconst_1       
        //   283: invokestatic    net/minecraft/client/renderer/GlStateManager.colorMask:(ZZZZ)V
        //   286: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0286 (coming from #0283).
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
    
    public void bindFramebuffer(final boolean b) {
        if (OpenGlHelper.isFramebufferEnabled()) {
            OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, this.framebufferObject);
            if (b) {
                GlStateManager.viewport(0, 0, this.framebufferWidth, this.framebufferHeight);
            }
        }
    }
    
    public void setFramebufferColor(final float n, final float n2, final float n3, final float n4) {
        this.framebufferColor[0] = n;
        this.framebufferColor[1] = n2;
        this.framebufferColor[2] = n3;
        this.framebufferColor[3] = n4;
    }
    
    public void unbindFramebuffer() {
        if (OpenGlHelper.isFramebufferEnabled()) {
            OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
        }
    }
    
    public void framebufferClear() {
        this.bindFramebuffer(true);
        GlStateManager.clearColor(this.framebufferColor[0], this.framebufferColor[1], this.framebufferColor[2], this.framebufferColor[3]);
        if (this.useDepth) {
            GlStateManager.clearDepth(1.0);
        }
        GlStateManager.clear(16384);
        this.unbindFramebuffer();
    }
    
    public void checkFramebufferComplete() {
        final int glCheckFramebufferStatus = OpenGlHelper.glCheckFramebufferStatus(OpenGlHelper.GL_FRAMEBUFFER);
        if (glCheckFramebufferStatus == OpenGlHelper.GL_FRAMEBUFFER_COMPLETE) {
            return;
        }
        if (glCheckFramebufferStatus == OpenGlHelper.GL_FB_INCOMPLETE_ATTACHMENT) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT");
        }
        if (glCheckFramebufferStatus == OpenGlHelper.GL_FB_INCOMPLETE_MISS_ATTACH) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT");
        }
        if (glCheckFramebufferStatus == OpenGlHelper.GL_FB_INCOMPLETE_DRAW_BUFFER) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER");
        }
        if (glCheckFramebufferStatus == OpenGlHelper.GL_FB_INCOMPLETE_READ_BUFFER) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER");
        }
        throw new RuntimeException("glCheckFramebufferStatus returned unknown status:" + glCheckFramebufferStatus);
    }
    
    public void createBindFramebuffer(final int framebufferWidth, final int framebufferHeight) {
        if (!OpenGlHelper.isFramebufferEnabled()) {
            this.framebufferWidth = framebufferWidth;
            this.framebufferHeight = framebufferHeight;
        }
        else {
            if (this.framebufferObject >= 0) {
                this.deleteFramebuffer();
            }
            this.createFramebuffer(framebufferWidth, framebufferHeight);
            this.checkFramebufferComplete();
            OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
        }
    }
}
