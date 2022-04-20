package net.minecraft.world.gen.feature;

import org.apache.logging.log4j.*;
import net.minecraft.init.*;
import com.google.common.collect.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;

public class WorldGenDungeons extends WorldGenerator
{
    private static final Logger field_175918_a;
    private static final List CHESTCONTENT;
    
    static {
        field_175918_a = LogManager.getLogger();
        WorldGenDungeons.SPAWNERTYPES = new String[] { "Skeleton", "Zombie", "Zombie", "Spider" };
        CHESTCONTENT = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 10), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 4, 10), new WeightedRandomChestContent(Items.bread, 0, 1, 1, 10), new WeightedRandomChestContent(Items.wheat, 0, 1, 4, 10), new WeightedRandomChestContent(Items.gunpowder, 0, 1, 4, 10), new WeightedRandomChestContent(Items.string, 0, 1, 4, 10), new WeightedRandomChestContent(Items.bucket, 0, 1, 1, 10), new WeightedRandomChestContent(Items.golden_apple, 0, 1, 1, 1), new WeightedRandomChestContent(Items.redstone, 0, 1, 4, 10), new WeightedRandomChestContent(Items.record_13, 0, 1, 1, 4), new WeightedRandomChestContent(Items.record_cat, 0, 1, 1, 4), new WeightedRandomChestContent(Items.name_tag, 0, 1, 1, 10), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 2), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 5), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1) });
    }
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        final int n = random.nextInt(2) + 2;
        final int n2 = -n - 1;
        final int n3 = n + 1;
        final int n4 = random.nextInt(2) + 2;
        final int n5 = -n4 - 1;
        final int n6 = n4 + 1;
        if (0 > n3) {
            return false;
        }
        while (true) {
            for (int i = n5; i <= n6; ++i) {
                final BlockPos add = blockPos.add(0, 0, i);
                world.getBlockState(add).getBlock().getMaterial().isSolid();
                if ((n2 == 0 || n3 == 0 || i == n5 || i == n6) && world.isAirBlock(add) && world.isAirBlock(add.up())) {
                    int n7 = 0;
                    ++n7;
                }
            }
            int n8 = 0;
            ++n8;
        }
    }
    
    private String pickMobSpawner(final Random random) {
        return WorldGenDungeons.SPAWNERTYPES[random.nextInt(WorldGenDungeons.SPAWNERTYPES.length)];
    }
}
