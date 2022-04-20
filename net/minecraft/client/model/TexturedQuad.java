package net.minecraft.client.model;

import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.util.*;

public class TexturedQuad
{
    private boolean invertNormal;
    public PositionTextureVertex[] vertexPositions;
    public int nVertices;
    
    public TexturedQuad(final PositionTextureVertex[] vertexPositions) {
        this.vertexPositions = vertexPositions;
        this.nVertices = vertexPositions.length;
    }
    
    public void draw(final WorldRenderer worldRenderer, final float n) {
        final Vec3 normalize = this.vertexPositions[1].vector3D.subtractReverse(this.vertexPositions[2].vector3D).crossProduct(this.vertexPositions[1].vector3D.subtractReverse(this.vertexPositions[0].vector3D)).normalize();
        float n2 = (float)normalize.xCoord;
        float n3 = (float)normalize.yCoord;
        float n4 = (float)normalize.zCoord;
        if (this.invertNormal) {
            n2 = -n2;
            n3 = -n3;
            n4 = -n4;
        }
        worldRenderer.begin(7, DefaultVertexFormats.OLDMODEL_POSITION_TEX_NORMAL);
        while (true) {
            final PositionTextureVertex positionTextureVertex = this.vertexPositions[0];
            worldRenderer.pos(positionTextureVertex.vector3D.xCoord * n, positionTextureVertex.vector3D.yCoord * n, positionTextureVertex.vector3D.zCoord * n).tex(positionTextureVertex.texturePositionX, positionTextureVertex.texturePositionY).normal(n2, n3, n4).endVertex();
            int n5 = 0;
            ++n5;
        }
    }
    
    public void flipFace() {
        final PositionTextureVertex[] vertexPositions = new PositionTextureVertex[this.vertexPositions.length];
        while (0 < this.vertexPositions.length) {
            vertexPositions[0] = this.vertexPositions[this.vertexPositions.length - 0 - 1];
            int n = 0;
            ++n;
        }
        this.vertexPositions = vertexPositions;
    }
    
    public TexturedQuad(final PositionTextureVertex[] array, final int n, final int n2, final int n3, final int n4, final float n5, final float n6) {
        this(array);
        final float n7 = 0.0f / n5;
        final float n8 = 0.0f / n6;
        array[0] = array[0].setTexturePosition(n3 / n5 - n7, n2 / n6 + n8);
        array[1] = array[1].setTexturePosition(n / n5 + n7, n2 / n6 + n8);
        array[2] = array[2].setTexturePosition(n / n5 + n7, n4 / n6 - n8);
        array[3] = array[3].setTexturePosition(n3 / n5 - n7, n4 / n6 - n8);
    }
}
