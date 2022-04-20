package net.minecraft.client.renderer;

public class Tessellator
{
    private WorldVertexBufferUploader vboUploader;
    private static final Tessellator instance;
    private WorldRenderer worldRenderer;
    
    static {
        instance = new Tessellator(2097152);
    }
    
    public WorldRenderer getWorldRenderer() {
        return this.worldRenderer;
    }
    
    public Tessellator(final int n) {
        this.vboUploader = new WorldVertexBufferUploader();
        this.worldRenderer = new WorldRenderer(n);
    }
    
    public void draw() {
        this.worldRenderer.finishDrawing();
        this.vboUploader.func_181679_a(this.worldRenderer);
    }
    
    public static Tessellator getInstance() {
        return Tessellator.instance;
    }
}
