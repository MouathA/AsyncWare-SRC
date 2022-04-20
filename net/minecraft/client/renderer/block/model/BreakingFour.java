package net.minecraft.client.renderer.block.model;

import net.minecraft.client.renderer.texture.*;
import java.util.*;

public class BreakingFour extends BakedQuad
{
    private final TextureAtlasSprite texture;
    
    private void func_178217_e() {
        while (true) {
            this.func_178216_a(0);
            int n = 0;
            ++n;
        }
    }
    
    private void func_178216_a(final int n) {
        final int n2 = 7 * n;
        final float intBitsToFloat = Float.intBitsToFloat(this.vertexData[n2]);
        final float intBitsToFloat2 = Float.intBitsToFloat(this.vertexData[n2 + 1]);
        final float intBitsToFloat3 = Float.intBitsToFloat(this.vertexData[n2 + 2]);
        float n3 = 0.0f;
        float n4 = 0.0f;
        switch (BreakingFour$1.$SwitchMap$net$minecraft$util$EnumFacing[this.face.ordinal()]) {
            case 1: {
                n3 = intBitsToFloat * 16.0f;
                n4 = (1.0f - intBitsToFloat3) * 16.0f;
                break;
            }
            case 2: {
                n3 = intBitsToFloat * 16.0f;
                n4 = intBitsToFloat3 * 16.0f;
                break;
            }
            case 3: {
                n3 = (1.0f - intBitsToFloat) * 16.0f;
                n4 = (1.0f - intBitsToFloat2) * 16.0f;
                break;
            }
            case 4: {
                n3 = intBitsToFloat * 16.0f;
                n4 = (1.0f - intBitsToFloat2) * 16.0f;
                break;
            }
            case 5: {
                n3 = intBitsToFloat3 * 16.0f;
                n4 = (1.0f - intBitsToFloat2) * 16.0f;
                break;
            }
            case 6: {
                n3 = (1.0f - intBitsToFloat3) * 16.0f;
                n4 = (1.0f - intBitsToFloat2) * 16.0f;
                break;
            }
        }
        this.vertexData[n2 + 4] = Float.floatToRawIntBits(this.texture.getInterpolatedU(n3));
        this.vertexData[n2 + 4 + 1] = Float.floatToRawIntBits(this.texture.getInterpolatedV(n4));
    }
    
    public BreakingFour(final BakedQuad bakedQuad, final TextureAtlasSprite texture) {
        super(Arrays.copyOf(bakedQuad.getVertexData(), bakedQuad.getVertexData().length), bakedQuad.tintIndex, FaceBakery.getFacingFromVertexData(bakedQuad.getVertexData()));
        this.texture = texture;
        this.func_178217_e();
    }
}
