package net.minecraft.world.gen.feature;

import net.minecraft.block.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;

public class WorldGenBigMushroom extends WorldGenerator
{
    private Block mushroomType;
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        if (this.mushroomType == null) {
            this.mushroomType = (random.nextBoolean() ? Blocks.brown_mushroom_block : Blocks.red_mushroom_block);
        }
        final int n = random.nextInt(3) + 4;
        if (blockPos.getY() >= 1 && blockPos.getY() + n + 1 < 256) {
            for (int i = blockPos.getY(); i <= blockPos.getY() + 1 + n; ++i) {
                if (i <= blockPos.getY() + 3) {}
                final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
                final int n2 = blockPos.getX() - 0;
                if (3 <= blockPos.getX() + 0) {}
            }
            return false;
        }
        return false;
    }
    
    public WorldGenBigMushroom(final Block mushroomType) {
        super(true);
        this.mushroomType = mushroomType;
    }
    
    public WorldGenBigMushroom() {
        super(false);
    }
}
