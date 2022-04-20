package optfine;

import net.minecraft.block.*;
import java.util.*;

public class BlockUtils
{
    private static ReflectorMethod ForgeBlock_setLightOpacity;
    private static boolean directAccessValid;
    private static Map mapOriginalOpacity;
    private static ReflectorClass ForgeBlock;
    
    public static void restoreLightOpacity(final Block block) {
        if (BlockUtils.mapOriginalOpacity.containsKey(block)) {
            setLightOpacity(block, BlockUtils.mapOriginalOpacity.get(block));
        }
    }
    
    static {
        BlockUtils.ForgeBlock = new ReflectorClass(Block.class);
        BlockUtils.ForgeBlock_setLightOpacity = new ReflectorMethod(BlockUtils.ForgeBlock, "setLightOpacity");
        BlockUtils.directAccessValid = true;
        BlockUtils.mapOriginalOpacity = new IdentityHashMap();
    }
    
    public static void setLightOpacity(final Block block, final int lightOpacity) {
        if (!BlockUtils.mapOriginalOpacity.containsKey(block)) {
            BlockUtils.mapOriginalOpacity.put(block, block.getLightOpacity());
        }
        if (BlockUtils.directAccessValid) {
            block.setLightOpacity(lightOpacity);
            return;
        }
        Reflector.callVoid(block, BlockUtils.ForgeBlock_setLightOpacity, lightOpacity);
    }
}
