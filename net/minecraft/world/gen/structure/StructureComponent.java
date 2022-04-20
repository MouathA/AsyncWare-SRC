package net.minecraft.world.gen.structure;

import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;
import net.minecraft.inventory.*;

public abstract class StructureComponent
{
    protected StructureBoundingBox boundingBox;
    protected EnumFacing coordBaseMode;
    protected int componentType;
    
    protected int getZWithOffset(final int n, final int n2) {
        if (this.coordBaseMode == null) {
            return n2;
        }
        switch (StructureComponent$1.$SwitchMap$net$minecraft$util$EnumFacing[this.coordBaseMode.ordinal()]) {
            case 1: {
                return this.boundingBox.maxZ - n2;
            }
            case 2: {
                return this.boundingBox.minZ + n2;
            }
            case 3:
            case 4: {
                return this.boundingBox.minZ + n;
            }
            default: {
                return n2;
            }
        }
    }
    
    protected void clearCurrentPositionBlocksUpwards(final World world, final int n, final int n2, final int n3, final StructureBoundingBox structureBoundingBox) {
        BlockPos up = new BlockPos(this.getXWithOffset(n, n3), this.getYWithOffset(n2), this.getZWithOffset(n, n3));
        if (structureBoundingBox.isVecInside(up)) {
            while (!world.isAirBlock(up) && up.getY() < 255) {
                world.setBlockState(up, Blocks.air.getDefaultState(), 2);
                up = up.up();
            }
        }
    }
    
    public void func_181138_a(final int n, final int n2, final int n3) {
        this.boundingBox.offset(n, n2, n3);
    }
    
    protected int getXWithOffset(final int n, final int n2) {
        if (this.coordBaseMode == null) {
            return n;
        }
        switch (StructureComponent$1.$SwitchMap$net$minecraft$util$EnumFacing[this.coordBaseMode.ordinal()]) {
            case 1:
            case 2: {
                return this.boundingBox.minX + n;
            }
            case 3: {
                return this.boundingBox.maxX - n2;
            }
            case 4: {
                return this.boundingBox.minX + n2;
            }
            default: {
                return n;
            }
        }
    }
    
    protected void fillWithBlocks(final World world, final StructureBoundingBox structureBoundingBox, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final IBlockState blockState, final IBlockState blockState2, final boolean b) {
        for (int i = n2; i <= n5; ++i) {
            for (int j = n; j <= n4; ++j) {
                for (int k = n3; k <= n6; ++k) {
                    if (!b || this.getBlockStateFromPos(world, j, i, k, structureBoundingBox).getBlock().getMaterial() != Material.air) {
                        if (i != n2 && i != n5 && j != n && j != n4 && k != n3 && k != n6) {
                            this.setBlockState(world, blockState2, j, i, k, structureBoundingBox);
                        }
                        else {
                            this.setBlockState(world, blockState, j, i, k, structureBoundingBox);
                        }
                    }
                }
            }
        }
    }
    
    protected IBlockState getBlockStateFromPos(final World world, final int n, final int n2, final int n3, final StructureBoundingBox structureBoundingBox) {
        final BlockPos blockPos = new BlockPos(this.getXWithOffset(n, n3), this.getYWithOffset(n2), this.getZWithOffset(n, n3));
        return structureBoundingBox.isVecInside(blockPos) ? world.getBlockState(blockPos) : Blocks.air.getDefaultState();
    }
    
    protected void fillWithRandomizedBlocks(final World world, final StructureBoundingBox structureBoundingBox, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final boolean b, final Random random, final BlockSelector blockSelector) {
        for (int i = n2; i <= n5; ++i) {
            for (int j = n; j <= n4; ++j) {
                for (int k = n3; k <= n6; ++k) {
                    if (!b || this.getBlockStateFromPos(world, j, i, k, structureBoundingBox).getBlock().getMaterial() != Material.air) {
                        blockSelector.selectBlocks(random, j, i, k, i == n2 || i == n5 || j == n || j == n4 || k == n3 || k == n6);
                        this.setBlockState(world, blockSelector.getBlockState(), j, i, k, structureBoundingBox);
                    }
                }
            }
        }
    }
    
    protected boolean generateDispenserContents(final World world, final StructureBoundingBox structureBoundingBox, final Random random, final int n, final int n2, final int n3, final int n4, final List list, final int n5) {
        final BlockPos blockPos = new BlockPos(this.getXWithOffset(n, n3), this.getYWithOffset(n2), this.getZWithOffset(n, n3));
        if (structureBoundingBox.isVecInside(blockPos) && world.getBlockState(blockPos).getBlock() != Blocks.dispenser) {
            world.setBlockState(blockPos, Blocks.dispenser.getStateFromMeta(this.getMetadataWithOffset(Blocks.dispenser, n4)), 2);
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityDispenser) {
                WeightedRandomChestContent.generateDispenserContents(random, list, (TileEntityDispenser)tileEntity, n5);
            }
            return true;
        }
        return false;
    }
    
    public BlockPos getBoundingBoxCenter() {
        return new BlockPos(this.boundingBox.getCenter());
    }
    
    protected void fillWithAir(final World world, final StructureBoundingBox structureBoundingBox, final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        for (int i = n2; i <= n5; ++i) {
            for (int j = n; j <= n4; ++j) {
                for (int k = n3; k <= n6; ++k) {
                    this.setBlockState(world, Blocks.air.getDefaultState(), j, i, k, structureBoundingBox);
                }
            }
        }
    }
    
    public abstract boolean addComponentParts(final World p0, final Random p1, final StructureBoundingBox p2);
    
    protected int getYWithOffset(final int n) {
        return (this.coordBaseMode == null) ? n : (n + this.boundingBox.minY);
    }
    
    protected abstract void writeStructureToNBT(final NBTTagCompound p0);
    
    protected void replaceAirAndLiquidDownwards(final World world, final IBlockState blockState, final int n, final int n2, final int n3, final StructureBoundingBox structureBoundingBox) {
        final int xWithOffset = this.getXWithOffset(n, n3);
        int yWithOffset = this.getYWithOffset(n2);
        final int zWithOffset = this.getZWithOffset(n, n3);
        if (structureBoundingBox.isVecInside(new BlockPos(xWithOffset, yWithOffset, zWithOffset))) {
            while ((world.isAirBlock(new BlockPos(xWithOffset, yWithOffset, zWithOffset)) || world.getBlockState(new BlockPos(xWithOffset, yWithOffset, zWithOffset)).getBlock().getMaterial().isLiquid()) && yWithOffset > 1) {
                world.setBlockState(new BlockPos(xWithOffset, yWithOffset, zWithOffset), blockState, 2);
                --yWithOffset;
            }
        }
    }
    
    public static StructureComponent findIntersecting(final List list, final StructureBoundingBox structureBoundingBox) {
        for (final StructureComponent structureComponent : list) {
            if (structureComponent.getBoundingBox() != null && structureComponent.getBoundingBox().intersectsWith(structureBoundingBox)) {
                return structureComponent;
            }
        }
        return null;
    }
    
    protected void setBlockState(final World world, final IBlockState blockState, final int n, final int n2, final int n3, final StructureBoundingBox structureBoundingBox) {
        final BlockPos blockPos = new BlockPos(this.getXWithOffset(n, n3), this.getYWithOffset(n2), this.getZWithOffset(n, n3));
        if (structureBoundingBox.isVecInside(blockPos)) {
            world.setBlockState(blockPos, blockState, 2);
        }
    }
    
    protected void randomlyRareFillWithBlocks(final World world, final StructureBoundingBox structureBoundingBox, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final IBlockState blockState, final boolean b) {
        final float n7 = (float)(n4 - n + 1);
        final float n8 = (float)(n5 - n2 + 1);
        final float n9 = (float)(n6 - n3 + 1);
        final float n10 = n + n7 / 2.0f;
        final float n11 = n3 + n9 / 2.0f;
        for (int i = n2; i <= n5; ++i) {
            final float n12 = (i - n2) / n8;
            for (int j = n; j <= n4; ++j) {
                final float n13 = (j - n10) / (n7 * 0.5f);
                for (int k = n3; k <= n6; ++k) {
                    final float n14 = (k - n11) / (n9 * 0.5f);
                    if ((!b || this.getBlockStateFromPos(world, j, i, k, structureBoundingBox).getBlock().getMaterial() != Material.air) && n13 * n13 + n12 * n12 + n14 * n14 <= 1.05f) {
                        this.setBlockState(world, blockState, j, i, k, structureBoundingBox);
                    }
                }
            }
        }
    }
    
    public NBTTagCompound createStructureBaseNBT() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setString("id", MapGenStructureIO.getStructureComponentName(this));
        nbtTagCompound.setTag("BB", this.boundingBox.toNBTTagIntArray());
        nbtTagCompound.setInteger("O", (this.coordBaseMode == null) ? -1 : this.coordBaseMode.getHorizontalIndex());
        nbtTagCompound.setInteger("GD", this.componentType);
        this.writeStructureToNBT(nbtTagCompound);
        return nbtTagCompound;
    }
    
    protected StructureComponent(final int componentType) {
        this.componentType = componentType;
    }
    
    public void readStructureBaseNBT(final World world, final NBTTagCompound nbtTagCompound) {
        if (nbtTagCompound.hasKey("BB")) {
            this.boundingBox = new StructureBoundingBox(nbtTagCompound.getIntArray("BB"));
        }
        final int integer = nbtTagCompound.getInteger("O");
        this.coordBaseMode = ((integer == -1) ? null : EnumFacing.getHorizontal(integer));
        this.componentType = nbtTagCompound.getInteger("GD");
        this.readStructureFromNBT(nbtTagCompound);
    }
    
    protected boolean isLiquidInStructureBoundingBox(final World world, final StructureBoundingBox structureBoundingBox) {
        final int max = Math.max(this.boundingBox.minX - 1, structureBoundingBox.minX);
        final int max2 = Math.max(this.boundingBox.minY - 1, structureBoundingBox.minY);
        final int max3 = Math.max(this.boundingBox.minZ - 1, structureBoundingBox.minZ);
        final int min = Math.min(this.boundingBox.maxX + 1, structureBoundingBox.maxX);
        final int min2 = Math.min(this.boundingBox.maxY + 1, structureBoundingBox.maxY);
        final int min3 = Math.min(this.boundingBox.maxZ + 1, structureBoundingBox.maxZ);
        final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (int i = max; i <= min; ++i) {
            for (int j = max3; j <= min3; ++j) {
                if (world.getBlockState(mutableBlockPos.func_181079_c(i, max2, j)).getBlock().getMaterial().isLiquid()) {
                    return true;
                }
                if (world.getBlockState(mutableBlockPos.func_181079_c(i, min2, j)).getBlock().getMaterial().isLiquid()) {
                    return true;
                }
            }
        }
        for (int k = max; k <= min; ++k) {
            for (int l = max2; l <= min2; ++l) {
                if (world.getBlockState(mutableBlockPos.func_181079_c(k, l, max3)).getBlock().getMaterial().isLiquid()) {
                    return true;
                }
                if (world.getBlockState(mutableBlockPos.func_181079_c(k, l, min3)).getBlock().getMaterial().isLiquid()) {
                    return true;
                }
            }
        }
        for (int n = max3; n <= min3; ++n) {
            for (int n2 = max2; n2 <= min2; ++n2) {
                if (world.getBlockState(mutableBlockPos.func_181079_c(max, n2, n)).getBlock().getMaterial().isLiquid()) {
                    return true;
                }
                if (world.getBlockState(mutableBlockPos.func_181079_c(min, n2, n)).getBlock().getMaterial().isLiquid()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    protected abstract void readStructureFromNBT(final NBTTagCompound p0);
    
    public StructureBoundingBox getBoundingBox() {
        return this.boundingBox;
    }
    
    protected void placeDoorCurrentPosition(final World world, final StructureBoundingBox structureBoundingBox, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing) {
        final BlockPos blockPos = new BlockPos(this.getXWithOffset(n, n3), this.getYWithOffset(n2), this.getZWithOffset(n, n3));
        if (structureBoundingBox.isVecInside(blockPos)) {
            ItemDoor.placeDoor(world, blockPos, enumFacing.rotateYCCW(), Blocks.oak_door);
        }
    }
    
    public StructureComponent() {
    }
    
    public void buildComponent(final StructureComponent structureComponent, final List list, final Random random) {
    }
    
    protected void func_175805_a(final World world, final StructureBoundingBox structureBoundingBox, final Random random, final float n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final IBlockState blockState, final IBlockState blockState2, final boolean b) {
        for (int i = n3; i <= n6; ++i) {
            for (int j = n2; j <= n5; ++j) {
                for (int k = n4; k <= n7; ++k) {
                    if (random.nextFloat() <= n && (!b || this.getBlockStateFromPos(world, j, i, k, structureBoundingBox).getBlock().getMaterial() != Material.air)) {
                        if (i != n3 && i != n6 && j != n2 && j != n5 && k != n4 && k != n7) {
                            this.setBlockState(world, blockState2, j, i, k, structureBoundingBox);
                        }
                        else {
                            this.setBlockState(world, blockState, j, i, k, structureBoundingBox);
                        }
                    }
                }
            }
        }
    }
    
    public int getComponentType() {
        return this.componentType;
    }
    
    protected int getMetadataWithOffset(final Block block, final int n) {
        if (block == Blocks.rail) {
            if (this.coordBaseMode == EnumFacing.WEST || this.coordBaseMode == EnumFacing.EAST) {
                if (n == 1) {
                    return 0;
                }
                return 1;
            }
        }
        else if (block instanceof BlockDoor) {
            if (this.coordBaseMode == EnumFacing.SOUTH) {
                if (n == 0) {
                    return 2;
                }
                if (n == 2) {
                    return 0;
                }
            }
            else {
                if (this.coordBaseMode == EnumFacing.WEST) {
                    return n + 1 & 0x3;
                }
                if (this.coordBaseMode == EnumFacing.EAST) {
                    return n + 3 & 0x3;
                }
            }
        }
        else if (block != Blocks.stone_stairs && block != Blocks.oak_stairs && block != Blocks.nether_brick_stairs && block != Blocks.stone_brick_stairs && block != Blocks.sandstone_stairs) {
            if (block == Blocks.ladder) {
                if (this.coordBaseMode == EnumFacing.SOUTH) {
                    if (n == EnumFacing.NORTH.getIndex()) {
                        return EnumFacing.SOUTH.getIndex();
                    }
                    if (n == EnumFacing.SOUTH.getIndex()) {
                        return EnumFacing.NORTH.getIndex();
                    }
                }
                else if (this.coordBaseMode == EnumFacing.WEST) {
                    if (n == EnumFacing.NORTH.getIndex()) {
                        return EnumFacing.WEST.getIndex();
                    }
                    if (n == EnumFacing.SOUTH.getIndex()) {
                        return EnumFacing.EAST.getIndex();
                    }
                    if (n == EnumFacing.WEST.getIndex()) {
                        return EnumFacing.NORTH.getIndex();
                    }
                    if (n == EnumFacing.EAST.getIndex()) {
                        return EnumFacing.SOUTH.getIndex();
                    }
                }
                else if (this.coordBaseMode == EnumFacing.EAST) {
                    if (n == EnumFacing.NORTH.getIndex()) {
                        return EnumFacing.EAST.getIndex();
                    }
                    if (n == EnumFacing.SOUTH.getIndex()) {
                        return EnumFacing.WEST.getIndex();
                    }
                    if (n == EnumFacing.WEST.getIndex()) {
                        return EnumFacing.NORTH.getIndex();
                    }
                    if (n == EnumFacing.EAST.getIndex()) {
                        return EnumFacing.SOUTH.getIndex();
                    }
                }
            }
            else if (block == Blocks.stone_button) {
                if (this.coordBaseMode == EnumFacing.SOUTH) {
                    if (n == 3) {
                        return 4;
                    }
                    if (n == 4) {
                        return 3;
                    }
                }
                else if (this.coordBaseMode == EnumFacing.WEST) {
                    if (n == 3) {
                        return 1;
                    }
                    if (n == 4) {
                        return 2;
                    }
                    if (n == 2) {
                        return 3;
                    }
                    if (n == 1) {
                        return 4;
                    }
                }
                else if (this.coordBaseMode == EnumFacing.EAST) {
                    if (n == 3) {
                        return 2;
                    }
                    if (n == 4) {
                        return 1;
                    }
                    if (n == 2) {
                        return 3;
                    }
                    if (n == 1) {
                        return 4;
                    }
                }
            }
            else if (block != Blocks.tripwire_hook && !(block instanceof BlockDirectional)) {
                if (block == Blocks.piston || block == Blocks.sticky_piston || block == Blocks.lever || block == Blocks.dispenser) {
                    if (this.coordBaseMode == EnumFacing.SOUTH) {
                        if (n == EnumFacing.NORTH.getIndex() || n == EnumFacing.SOUTH.getIndex()) {
                            return EnumFacing.getFront(n).getOpposite().getIndex();
                        }
                    }
                    else if (this.coordBaseMode == EnumFacing.WEST) {
                        if (n == EnumFacing.NORTH.getIndex()) {
                            return EnumFacing.WEST.getIndex();
                        }
                        if (n == EnumFacing.SOUTH.getIndex()) {
                            return EnumFacing.EAST.getIndex();
                        }
                        if (n == EnumFacing.WEST.getIndex()) {
                            return EnumFacing.NORTH.getIndex();
                        }
                        if (n == EnumFacing.EAST.getIndex()) {
                            return EnumFacing.SOUTH.getIndex();
                        }
                    }
                    else if (this.coordBaseMode == EnumFacing.EAST) {
                        if (n == EnumFacing.NORTH.getIndex()) {
                            return EnumFacing.EAST.getIndex();
                        }
                        if (n == EnumFacing.SOUTH.getIndex()) {
                            return EnumFacing.WEST.getIndex();
                        }
                        if (n == EnumFacing.WEST.getIndex()) {
                            return EnumFacing.NORTH.getIndex();
                        }
                        if (n == EnumFacing.EAST.getIndex()) {
                            return EnumFacing.SOUTH.getIndex();
                        }
                    }
                }
            }
            else {
                final EnumFacing horizontal = EnumFacing.getHorizontal(n);
                if (this.coordBaseMode == EnumFacing.SOUTH) {
                    if (horizontal == EnumFacing.SOUTH || horizontal == EnumFacing.NORTH) {
                        return horizontal.getOpposite().getHorizontalIndex();
                    }
                }
                else if (this.coordBaseMode == EnumFacing.WEST) {
                    if (horizontal == EnumFacing.NORTH) {
                        return EnumFacing.WEST.getHorizontalIndex();
                    }
                    if (horizontal == EnumFacing.SOUTH) {
                        return EnumFacing.EAST.getHorizontalIndex();
                    }
                    if (horizontal == EnumFacing.WEST) {
                        return EnumFacing.NORTH.getHorizontalIndex();
                    }
                    if (horizontal == EnumFacing.EAST) {
                        return EnumFacing.SOUTH.getHorizontalIndex();
                    }
                }
                else if (this.coordBaseMode == EnumFacing.EAST) {
                    if (horizontal == EnumFacing.NORTH) {
                        return EnumFacing.EAST.getHorizontalIndex();
                    }
                    if (horizontal == EnumFacing.SOUTH) {
                        return EnumFacing.WEST.getHorizontalIndex();
                    }
                    if (horizontal == EnumFacing.WEST) {
                        return EnumFacing.NORTH.getHorizontalIndex();
                    }
                    if (horizontal == EnumFacing.EAST) {
                        return EnumFacing.SOUTH.getHorizontalIndex();
                    }
                }
            }
        }
        else if (this.coordBaseMode == EnumFacing.SOUTH) {
            if (n == 2) {
                return 3;
            }
            if (n == 3) {
                return 2;
            }
        }
        else if (this.coordBaseMode == EnumFacing.WEST) {
            if (n == 0) {
                return 2;
            }
            if (n == 1) {
                return 3;
            }
            if (n == 2) {
                return 0;
            }
            if (n == 3) {
                return 1;
            }
        }
        else if (this.coordBaseMode == EnumFacing.EAST) {
            if (n == 0) {
                return 2;
            }
            if (n == 1) {
                return 3;
            }
            if (n == 2) {
                return 1;
            }
            if (n == 3) {
                return 0;
            }
        }
        return n;
    }
    
    protected boolean generateChestContents(final World world, final StructureBoundingBox structureBoundingBox, final Random random, final int n, final int n2, final int n3, final List list, final int n4) {
        final BlockPos blockPos = new BlockPos(this.getXWithOffset(n, n3), this.getYWithOffset(n2), this.getZWithOffset(n, n3));
        if (structureBoundingBox.isVecInside(blockPos) && world.getBlockState(blockPos).getBlock() != Blocks.chest) {
            world.setBlockState(blockPos, Blocks.chest.correctFacing(world, blockPos, Blocks.chest.getDefaultState()), 2);
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityChest) {
                WeightedRandomChestContent.generateChestContents(random, list, (IInventory)tileEntity, n4);
            }
            return true;
        }
        return false;
    }
    
    protected void randomlyPlaceBlock(final World world, final StructureBoundingBox structureBoundingBox, final Random random, final float n, final int n2, final int n3, final int n4, final IBlockState blockState) {
        if (random.nextFloat() < n) {
            this.setBlockState(world, blockState, n2, n3, n4, structureBoundingBox);
        }
    }
    
    public abstract static class BlockSelector
    {
        protected IBlockState blockstate;
        
        public IBlockState getBlockState() {
            return this.blockstate;
        }
        
        public abstract void selectBlocks(final Random p0, final int p1, final int p2, final int p3, final boolean p4);
        
        public BlockSelector() {
            this.blockstate = Blocks.air.getDefaultState();
        }
    }
}
