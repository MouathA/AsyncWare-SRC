package net.minecraft.realms;

import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.*;
import java.nio.*;

public class RealmsBufferBuilder
{
    private WorldRenderer b;
    
    public void end() {
        this.b.finishDrawing();
    }
    
    public void fixupQuadColor(final int n) {
        this.b.putColor4(n);
    }
    
    public RealmsBufferBuilder normal(final float n, final float n2, final float n3) {
        return this.from(this.b.normal(n, n2, n3));
    }
    
    public RealmsBufferBuilder vertex(final double n, final double n2, final double n3) {
        return this.from(this.b.pos(n, n2, n3));
    }
    
    public RealmsBufferBuilder tex(final double n, final double n2) {
        return this.from(this.b.tex(n, n2));
    }
    
    public void fixupVertexColor(final float n, final float n2, final float n3, final int n4) {
        this.b.putColorRGB_F(n, n2, n3, n4);
    }
    
    public void sortQuads(final float n, final float n2, final float n3) {
        this.b.func_181674_a(n, n2, n3);
    }
    
    public void clear() {
        this.b.reset();
    }
    
    public int getVertexCount() {
        return this.b.getVertexCount();
    }
    
    public void postProcessFacePosition(final double n, final double n2, final double n3) {
        this.b.putPosition(n, n2, n3);
    }
    
    public void noColor() {
        this.b.markDirty();
    }
    
    public void postNormal(final float n, final float n2, final float n3) {
        this.b.putNormal(n, n2, n3);
    }
    
    public RealmsBufferBuilder tex2(final int n, final int n2) {
        return this.from(this.b.lightmap(n, n2));
    }
    
    public RealmsBufferBuilder(final WorldRenderer b) {
        this.b = b;
    }
    
    public void begin(final int n, final VertexFormat vertexFormat) {
        this.b.begin(n, vertexFormat);
    }
    
    public RealmsBufferBuilder color(final int n, final int n2, final int n3, final int n4) {
        return this.from(this.b.color(n, n2, n3, n4));
    }
    
    public RealmsVertexFormat getVertexFormat() {
        return new RealmsVertexFormat(this.b.getVertexFormat());
    }
    
    public void putBulkData(final int[] array) {
        this.b.addVertexData(array);
    }
    
    public void endVertex() {
        this.b.endVertex();
    }
    
    public void fixupQuadColor(final float n, final float n2, final float n3) {
        this.b.putColorRGB_F4(n, n2, n3);
    }
    
    public void faceTint(final float n, final float n2, final float n3, final int n4) {
        this.b.putColorMultiplier(n, n2, n3, n4);
    }
    
    public ByteBuffer getBuffer() {
        return this.b.getByteBuffer();
    }
    
    public void restoreState(final WorldRenderer.State vertexState) {
        this.b.setVertexState(vertexState);
    }
    
    public void faceTex2(final int n, final int n2, final int n3, final int n4) {
        this.b.putBrightness4(n, n2, n3, n4);
    }
    
    public void offset(final double n, final double n2, final double n3) {
        this.b.setTranslation(n, n2, n3);
    }
    
    public RealmsBufferBuilder color(final float n, final float n2, final float n3, final float n4) {
        return this.from(this.b.color(n, n2, n3, n4));
    }
    
    public RealmsBufferBuilder from(final WorldRenderer b) {
        this.b = b;
        return this;
    }
    
    public int getDrawMode() {
        return this.b.getDrawMode();
    }
}
