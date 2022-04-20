package net.minecraft.client.renderer.block.model;

import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;

public class BakedQuad
{
    private int[] vertexDataSingle;
    protected final int[] vertexData;
    protected final EnumFacing face;
    protected final int tintIndex;
    private TextureAtlasSprite sprite;
    private static final String __OBFID = "CL_00002512";
    
    public int[] getVertexData() {
        return this.vertexData;
    }
    
    public boolean hasTintIndex() {
        return this.tintIndex != -1;
    }
    
    public EnumFacing getFace() {
        return this.face;
    }
    
    public BakedQuad(final int[] vertexData, final int tintIndex, final EnumFacing face) {
        this.sprite = null;
        this.vertexDataSingle = null;
        this.vertexData = vertexData;
        this.tintIndex = tintIndex;
        this.face = face;
    }
    
    public TextureAtlasSprite getSprite() {
        return this.sprite;
    }
    
    @Override
    public String toString() {
        return "vertex: " + this.vertexData.length / 7 + ", tint: " + this.tintIndex + ", facing: " + this.face + ", sprite: " + this.sprite;
    }
    
    public BakedQuad(final int[] vertexData, final int tintIndex, final EnumFacing face, final TextureAtlasSprite sprite) {
        this.sprite = null;
        this.vertexDataSingle = null;
        this.vertexData = vertexData;
        this.tintIndex = tintIndex;
        this.face = face;
        this.sprite = sprite;
    }
    
    private static int[] makeVertexDataSingle(final int[] array, final TextureAtlasSprite textureAtlasSprite) {
        final int[] array2 = new int[array.length];
        while (0 < array2.length) {
            array2[0] = array[0];
            int n = 0;
            ++n;
        }
        int n = textureAtlasSprite.sheetWidth / textureAtlasSprite.getIconWidth();
        final int n2 = textureAtlasSprite.sheetHeight / textureAtlasSprite.getIconHeight();
        while (true) {
            final float intBitsToFloat = Float.intBitsToFloat(array2[4]);
            final float intBitsToFloat2 = Float.intBitsToFloat(array2[5]);
            final float singleU = textureAtlasSprite.toSingleU(intBitsToFloat);
            final float singleV = textureAtlasSprite.toSingleV(intBitsToFloat2);
            array2[4] = Float.floatToRawIntBits(singleU);
            array2[5] = Float.floatToRawIntBits(singleV);
            int n3 = 0;
            ++n3;
        }
    }
    
    public int getTintIndex() {
        return this.tintIndex;
    }
    
    public int[] getVertexDataSingle() {
        if (this.vertexDataSingle == null) {
            this.vertexDataSingle = makeVertexDataSingle(this.vertexData, this.sprite);
        }
        return this.vertexDataSingle;
    }
}
