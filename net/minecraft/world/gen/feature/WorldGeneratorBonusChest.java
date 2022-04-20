package net.minecraft.world.gen.feature;

import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;

public class WorldGeneratorBonusChest extends WorldGenerator
{
    private final List chestItems;
    private final int itemsToGenerateInBonusChest;
    
    public WorldGeneratorBonusChest(final List chestItems, final int itemsToGenerateInBonusChest) {
        this.chestItems = chestItems;
        this.itemsToGenerateInBonusChest = itemsToGenerateInBonusChest;
    }
    
    @Override
    public boolean generate(final World world, final Random random, BlockPos blockPos) {
        Block block;
        while (((block = world.getBlockState(blockPos).getBlock()).getMaterial() == Material.air || block.getMaterial() == Material.leaves) && blockPos.getY() > 1) {
            blockPos = blockPos.down();
        }
        if (blockPos.getY() < 1) {
            return false;
        }
        blockPos = blockPos.up();
        BlockPos add;
        while (true) {
            add = blockPos.add(random.nextInt(4) - random.nextInt(4), random.nextInt(3) - random.nextInt(3), random.nextInt(4) - random.nextInt(4));
            if (world.isAirBlock(add) && World.doesBlockHaveSolidTopSurface(world, add.down())) {
                break;
            }
            int n = 0;
            ++n;
        }
        world.setBlockState(add, Blocks.chest.getDefaultState(), 2);
        final TileEntity tileEntity = world.getTileEntity(add);
        if (tileEntity instanceof TileEntityChest) {
            WeightedRandomChestContent.generateChestContents(random, this.chestItems, (IInventory)tileEntity, this.itemsToGenerateInBonusChest);
        }
        final BlockPos east = add.east();
        final BlockPos west = add.west();
        final BlockPos north = add.north();
        final BlockPos south = add.south();
        if (world.isAirBlock(west) && World.doesBlockHaveSolidTopSurface(world, west.down())) {
            world.setBlockState(west, Blocks.torch.getDefaultState(), 2);
        }
        if (world.isAirBlock(east) && World.doesBlockHaveSolidTopSurface(world, east.down())) {
            world.setBlockState(east, Blocks.torch.getDefaultState(), 2);
        }
        if (world.isAirBlock(north) && World.doesBlockHaveSolidTopSurface(world, north.down())) {
            world.setBlockState(north, Blocks.torch.getDefaultState(), 2);
        }
        if (world.isAirBlock(south) && World.doesBlockHaveSolidTopSurface(world, south.down())) {
            world.setBlockState(south, Blocks.torch.getDefaultState(), 2);
        }
        return true;
    }
}
