package net.minecraft.client.renderer.block.statemap;

import net.minecraft.block.state.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import java.util.*;

public class DefaultStateMapper extends StateMapperBase
{
    @Override
    protected ModelResourceLocation getModelResourceLocation(final IBlockState blockState) {
        return new ModelResourceLocation((ResourceLocation)Block.blockRegistry.getNameForObject(blockState.getBlock()), this.getPropertyString((Map)blockState.getProperties()));
    }
}
