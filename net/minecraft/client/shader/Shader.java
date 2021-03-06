package net.minecraft.client.shader;

import org.lwjgl.util.vector.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import java.util.*;
import net.minecraft.client.resources.*;
import com.google.common.collect.*;
import java.io.*;
import net.minecraft.client.util.*;

public class Shader
{
    public final Framebuffer framebufferIn;
    private final List listAuxWidths;
    private final List listAuxHeights;
    private final List listAuxNames;
    private Matrix4f projectionMatrix;
    private final ShaderManager manager;
    private final List listAuxFramebuffers;
    public final Framebuffer framebufferOut;
    
    public void loadShader(final float n) {
        this.preLoadShader();
        this.framebufferIn.unbindFramebuffer();
        final float n2 = (float)this.framebufferOut.framebufferTextureWidth;
        final float n3 = (float)this.framebufferOut.framebufferTextureHeight;
        GlStateManager.viewport(0, 0, (int)n2, (int)n3);
        this.manager.addSamplerTexture("DiffuseSampler", this.framebufferIn);
        while (0 < this.listAuxFramebuffers.size()) {
            this.manager.addSamplerTexture(this.listAuxNames.get(0), this.listAuxFramebuffers.get(0));
            this.manager.getShaderUniformOrDefault("AuxSize" + 0).set(this.listAuxWidths.get(0), this.listAuxHeights.get(0));
            int n4 = 0;
            ++n4;
        }
        this.manager.getShaderUniformOrDefault("ProjMat").set(this.projectionMatrix);
        this.manager.getShaderUniformOrDefault("InSize").set((float)this.framebufferIn.framebufferTextureWidth, (float)this.framebufferIn.framebufferTextureHeight);
        this.manager.getShaderUniformOrDefault("OutSize").set(n2, n3);
        this.manager.getShaderUniformOrDefault("Time").set(n);
        final Minecraft minecraft = Minecraft.getMinecraft();
        this.manager.getShaderUniformOrDefault("ScreenSize").set((float)minecraft.displayWidth, (float)minecraft.displayHeight);
        this.manager.useShader();
        this.framebufferOut.framebufferClear();
        this.framebufferOut.bindFramebuffer(false);
        GlStateManager.depthMask(false);
        GlStateManager.colorMask(true, true, true, true);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(0.0, n3, 500.0).color(255, 255, 255, 255).endVertex();
        worldRenderer.pos(n2, n3, 500.0).color(255, 255, 255, 255).endVertex();
        worldRenderer.pos(n2, 0.0, 500.0).color(255, 255, 255, 255).endVertex();
        worldRenderer.pos(0.0, 0.0, 500.0).color(255, 255, 255, 255).endVertex();
        instance.draw();
        GlStateManager.depthMask(true);
        GlStateManager.colorMask(true, true, true, true);
        this.manager.endShader();
        this.framebufferOut.unbindFramebuffer();
        this.framebufferIn.unbindFramebufferTexture();
        for (final Framebuffer next : this.listAuxFramebuffers) {
            if (next instanceof Framebuffer) {
                next.unbindFramebufferTexture();
            }
        }
    }
    
    public void addAuxFramebuffer(final String s, final Object o, final int n, final int n2) {
        this.listAuxNames.add(this.listAuxNames.size(), s);
        this.listAuxFramebuffers.add(this.listAuxFramebuffers.size(), o);
        this.listAuxWidths.add(this.listAuxWidths.size(), n);
        this.listAuxHeights.add(this.listAuxHeights.size(), n2);
    }
    
    public Shader(final IResourceManager resourceManager, final String s, final Framebuffer framebufferIn, final Framebuffer framebufferOut) throws IOException, JsonException {
        this.listAuxFramebuffers = Lists.newArrayList();
        this.listAuxNames = Lists.newArrayList();
        this.listAuxWidths = Lists.newArrayList();
        this.listAuxHeights = Lists.newArrayList();
        this.manager = new ShaderManager(resourceManager, s);
        this.framebufferIn = framebufferIn;
        this.framebufferOut = framebufferOut;
    }
    
    public ShaderManager getShaderManager() {
        return this.manager;
    }
    
    public void deleteShader() {
        this.manager.deleteShader();
    }
    
    private void preLoadShader() {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.bindTexture(0);
    }
    
    public void setProjectionMatrix(final Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }
}
