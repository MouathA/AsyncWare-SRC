package optfine;

import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import org.lwjgl.util.vector.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.resources.model.*;
import java.util.*;

public class BlockModelUtils
{
    public static IBakedModel makeModelCube(final String s, final int n) {
        return makeModelCube(Config.getMinecraft().getTextureMapBlocks().getAtlasSprite(s), n);
    }
    
    private static BakedQuad makeBakedQuad(final EnumFacing enumFacing, final TextureAtlasSprite textureAtlasSprite, final int n) {
        return new FaceBakery().makeBakedQuad(new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(16.0f, 16.0f, 16.0f), new BlockPartFace(enumFacing, n, "#" + enumFacing.getName(), new BlockFaceUV(new float[] { 0.0f, 0.0f, 16.0f, 16.0f }, 0)), textureAtlasSprite, enumFacing, ModelRotation.X0_Y0, null, false, true);
    }
    
    public static IBakedModel makeModelCube(final TextureAtlasSprite textureAtlasSprite, final int n) {
        final ArrayList list = new ArrayList();
        final EnumFacing[] values = EnumFacing.values();
        final ArrayList list2 = new ArrayList<ArrayList<BakedQuad>>(values.length);
        while (0 < values.length) {
            final EnumFacing enumFacing = values[0];
            final ArrayList<BakedQuad> list3 = new ArrayList<BakedQuad>();
            list3.add(makeBakedQuad(enumFacing, textureAtlasSprite, n));
            list2.add(list3);
            int n2 = 0;
            ++n2;
        }
        return new SimpleBakedModel(list, list2, true, true, textureAtlasSprite, ItemCameraTransforms.DEFAULT);
    }
}
