package net.minecraft.world.gen.structure;

import net.minecraft.nbt.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.init.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;

public class ComponentScatteredFeaturePieces
{
    public static void registerScatteredFeaturePieces() {
        MapGenStructureIO.registerStructureComponent(DesertPyramid.class, "TeDP");
        MapGenStructureIO.registerStructureComponent(JunglePyramid.class, "TeJP");
        MapGenStructureIO.registerStructureComponent(SwampHut.class, "TeSH");
    }
    
    public static class DesertPyramid extends Feature
    {
        private boolean[] field_74940_h;
        private static final List itemsToGenerateInTemple;
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.field_74940_h[0] = nbtTagCompound.getBoolean("hasPlacedChest0");
            this.field_74940_h[1] = nbtTagCompound.getBoolean("hasPlacedChest1");
            this.field_74940_h[2] = nbtTagCompound.getBoolean("hasPlacedChest2");
            this.field_74940_h[3] = nbtTagCompound.getBoolean("hasPlacedChest3");
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            this.fillWithBlocks(world, structureBoundingBox, 0, -4, 0, this.scatteredFeatureSizeX - 1, 0, this.scatteredFeatureSizeZ - 1, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
            while (true) {
                this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, this.scatteredFeatureSizeX - 1 - 0, 0, this.scatteredFeatureSizeZ - 1 - 0, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
                this.fillWithBlocks(world, structureBoundingBox, 1, 0, 1, this.scatteredFeatureSizeX - 2 - 0, 0, this.scatteredFeatureSizeZ - 2 - 0, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
                int n = 0;
                ++n;
            }
        }
        
        public DesertPyramid(final Random random, final int n, final int n2) {
            super(random, n, 64, n2, 21, 15, 21);
            this.field_74940_h = new boolean[4];
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setBoolean("hasPlacedChest0", this.field_74940_h[0]);
            nbtTagCompound.setBoolean("hasPlacedChest1", this.field_74940_h[1]);
            nbtTagCompound.setBoolean("hasPlacedChest2", this.field_74940_h[2]);
            nbtTagCompound.setBoolean("hasPlacedChest3", this.field_74940_h[3]);
        }
        
        public DesertPyramid() {
            this.field_74940_h = new boolean[4];
        }
        
        static {
            itemsToGenerateInTemple = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 2, 7, 15), new WeightedRandomChestContent(Items.emerald, 0, 1, 3, 2), new WeightedRandomChestContent(Items.bone, 0, 4, 6, 20), new WeightedRandomChestContent(Items.rotten_flesh, 0, 3, 7, 16), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1) });
        }
    }
    
    abstract static class Feature extends StructureComponent
    {
        protected int scatteredFeatureSizeY;
        protected int scatteredFeatureSizeZ;
        protected int field_74936_d;
        protected int scatteredFeatureSizeX;
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            this.scatteredFeatureSizeX = nbtTagCompound.getInteger("Width");
            this.scatteredFeatureSizeY = nbtTagCompound.getInteger("Height");
            this.scatteredFeatureSizeZ = nbtTagCompound.getInteger("Depth");
            this.field_74936_d = nbtTagCompound.getInteger("HPos");
        }
        
        public Feature() {
            this.field_74936_d = -1;
        }
        
        protected Feature(final Random random, final int n, final int n2, final int n3, final int scatteredFeatureSizeX, final int scatteredFeatureSizeY, final int scatteredFeatureSizeZ) {
            super(0);
            this.field_74936_d = -1;
            this.scatteredFeatureSizeX = scatteredFeatureSizeX;
            this.scatteredFeatureSizeY = scatteredFeatureSizeY;
            this.scatteredFeatureSizeZ = scatteredFeatureSizeZ;
            this.coordBaseMode = EnumFacing.Plane.HORIZONTAL.random(random);
            switch (ComponentScatteredFeaturePieces$1.$SwitchMap$net$minecraft$util$EnumFacing[this.coordBaseMode.ordinal()]) {
                case 1:
                case 2: {
                    this.boundingBox = new StructureBoundingBox(n, n2, n3, n + scatteredFeatureSizeX - 1, n2 + scatteredFeatureSizeY - 1, n3 + scatteredFeatureSizeZ - 1);
                    break;
                }
                default: {
                    this.boundingBox = new StructureBoundingBox(n, n2, n3, n + scatteredFeatureSizeZ - 1, n2 + scatteredFeatureSizeY - 1, n3 + scatteredFeatureSizeX - 1);
                    break;
                }
            }
        }
        
        protected boolean func_74935_a(final World world, final StructureBoundingBox structureBoundingBox, final int n) {
            if (this.field_74936_d >= 0) {
                return true;
            }
            final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
            for (int i = this.boundingBox.minZ; i <= this.boundingBox.maxZ; ++i) {
                for (int j = this.boundingBox.minX; j <= this.boundingBox.maxX; ++j) {
                    mutableBlockPos.func_181079_c(j, 64, i);
                    if (structureBoundingBox.isVecInside(mutableBlockPos)) {
                        final int n2 = 0 + Math.max(world.getTopSolidOrLiquidBlock(mutableBlockPos).getY(), world.provider.getAverageGroundLevel());
                        int n3 = 0;
                        ++n3;
                    }
                }
            }
            return false;
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            nbtTagCompound.setInteger("Width", this.scatteredFeatureSizeX);
            nbtTagCompound.setInteger("Height", this.scatteredFeatureSizeY);
            nbtTagCompound.setInteger("Depth", this.scatteredFeatureSizeZ);
            nbtTagCompound.setInteger("HPos", this.field_74936_d);
        }
    }
    
    public static class JunglePyramid extends Feature
    {
        private static Stones junglePyramidsRandomScatteredStones;
        private boolean field_74947_h;
        private boolean field_74948_i;
        private static final List field_175816_i;
        private boolean field_74946_k;
        private static final List field_175815_j;
        private boolean field_74945_j;
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (!this.func_74935_a(world, structureBoundingBox, 0)) {
                return false;
            }
            this.getMetadataWithOffset(Blocks.stone_stairs, 3);
            this.getMetadataWithOffset(Blocks.stone_stairs, 2);
            this.getMetadataWithOffset(Blocks.stone_stairs, 0);
            this.getMetadataWithOffset(Blocks.stone_stairs, 1);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0, -4, 0, this.scatteredFeatureSizeX - 1, 0, this.scatteredFeatureSizeZ - 1, false, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 2, 1, 2, 9, 2, 2, false, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 2, 1, 12, 9, 2, 12, false, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 2, 1, 3, 2, 2, 11, false, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 9, 1, 3, 9, 2, 11, false, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 1, 3, 1, 10, 6, 1, false, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 1, 3, 13, 10, 6, 13, false, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 1, 3, 2, 1, 6, 12, false, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 10, 3, 2, 10, 6, 12, false, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 2, 3, 2, 9, 3, 12, false, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 2, 6, 2, 9, 6, 12, false, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 3, 7, 3, 8, 7, 11, false, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 4, 8, 4, 7, 8, 10, false, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithAir(world, structureBoundingBox, 3, 1, 3, 8, 2, 11);
            this.fillWithAir(world, structureBoundingBox, 4, 3, 6, 7, 3, 9);
            this.fillWithAir(world, structureBoundingBox, 2, 4, 2, 9, 5, 12);
            this.fillWithAir(world, structureBoundingBox, 4, 6, 5, 7, 6, 9);
            this.fillWithAir(world, structureBoundingBox, 5, 7, 6, 6, 7, 8);
            this.fillWithAir(world, structureBoundingBox, 5, 1, 2, 6, 2, 2);
            this.fillWithAir(world, structureBoundingBox, 5, 2, 12, 6, 2, 12);
            this.fillWithAir(world, structureBoundingBox, 5, 5, 1, 6, 5, 1);
            this.fillWithAir(world, structureBoundingBox, 5, 5, 13, 6, 5, 13);
            this.setBlockState(world, Blocks.air.getDefaultState(), 1, 5, 5, structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), 10, 5, 5, structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), 1, 5, 9, structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), 10, 5, 9, structureBoundingBox);
            while (true) {
                this.fillWithRandomizedBlocks(world, structureBoundingBox, 2, 4, 2, 2, 5, 2, false, random, JunglePyramid.junglePyramidsRandomScatteredStones);
                this.fillWithRandomizedBlocks(world, structureBoundingBox, 4, 4, 2, 4, 5, 2, false, random, JunglePyramid.junglePyramidsRandomScatteredStones);
                this.fillWithRandomizedBlocks(world, structureBoundingBox, 7, 4, 2, 7, 5, 2, false, random, JunglePyramid.junglePyramidsRandomScatteredStones);
                this.fillWithRandomizedBlocks(world, structureBoundingBox, 9, 4, 2, 9, 5, 2, false, random, JunglePyramid.junglePyramidsRandomScatteredStones);
                final int n;
                n += 14;
            }
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.field_74947_h = nbtTagCompound.getBoolean("placedMainChest");
            this.field_74948_i = nbtTagCompound.getBoolean("placedHiddenChest");
            this.field_74945_j = nbtTagCompound.getBoolean("placedTrap1");
            this.field_74946_k = nbtTagCompound.getBoolean("placedTrap2");
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setBoolean("placedMainChest", this.field_74947_h);
            nbtTagCompound.setBoolean("placedHiddenChest", this.field_74948_i);
            nbtTagCompound.setBoolean("placedTrap1", this.field_74945_j);
            nbtTagCompound.setBoolean("placedTrap2", this.field_74946_k);
        }
        
        static {
            field_175816_i = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 2, 7, 15), new WeightedRandomChestContent(Items.emerald, 0, 1, 3, 2), new WeightedRandomChestContent(Items.bone, 0, 4, 6, 20), new WeightedRandomChestContent(Items.rotten_flesh, 0, 3, 7, 16), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1) });
            field_175815_j = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.arrow, 0, 2, 7, 30) });
            JunglePyramid.junglePyramidsRandomScatteredStones = new Stones(null);
        }
        
        public JunglePyramid(final Random random, final int n, final int n2) {
            super(random, n, 64, n2, 12, 10, 15);
        }
        
        public JunglePyramid() {
        }
        
        static class Stones extends BlockSelector
        {
            Stones(final ComponentScatteredFeaturePieces$1 object) {
                this();
            }
            
            private Stones() {
            }
            
            @Override
            public void selectBlocks(final Random random, final int n, final int n2, final int n3, final boolean b) {
                if (random.nextFloat() < 0.4f) {
                    this.blockstate = Blocks.cobblestone.getDefaultState();
                }
                else {
                    this.blockstate = Blocks.mossy_cobblestone.getDefaultState();
                }
            }
        }
    }
    
    public static class SwampHut extends Feature
    {
        private boolean hasWitch;
        
        public SwampHut() {
        }
        
        public SwampHut(final Random random, final int n, final int n2) {
            super(random, n, 64, n2, 7, 7, 9);
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.hasWitch = nbtTagCompound.getBoolean("Witch");
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (!this.func_74935_a(world, structureBoundingBox, 0)) {
                return false;
            }
            this.fillWithBlocks(world, structureBoundingBox, 1, 1, 1, 5, 1, 7, Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 4, 2, 5, 4, 7, Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), false);
            this.fillWithBlocks(world, structureBoundingBox, 2, 1, 0, 4, 1, 0, Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), false);
            this.fillWithBlocks(world, structureBoundingBox, 2, 2, 2, 3, 3, 2, Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 2, 3, 1, 3, 6, Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), false);
            this.fillWithBlocks(world, structureBoundingBox, 5, 2, 3, 5, 3, 6, Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), false);
            this.fillWithBlocks(world, structureBoundingBox, 2, 2, 7, 4, 3, 7, Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 0, 2, 1, 3, 2, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 5, 0, 2, 5, 3, 2, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 0, 7, 1, 3, 7, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 5, 0, 7, 5, 3, 7, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 2, 3, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 3, 3, 7, structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), 1, 3, 4, structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), 5, 3, 4, structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), 5, 3, 5, structureBoundingBox);
            this.setBlockState(world, Blocks.flower_pot.getDefaultState().withProperty(BlockFlowerPot.CONTENTS, BlockFlowerPot.EnumFlowerType.MUSHROOM_RED), 1, 3, 5, structureBoundingBox);
            this.setBlockState(world, Blocks.crafting_table.getDefaultState(), 3, 2, 6, structureBoundingBox);
            this.setBlockState(world, Blocks.cauldron.getDefaultState(), 4, 2, 6, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 1, 2, 1, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 5, 2, 1, structureBoundingBox);
            final int metadataWithOffset = this.getMetadataWithOffset(Blocks.oak_stairs, 3);
            final int metadataWithOffset2 = this.getMetadataWithOffset(Blocks.oak_stairs, 1);
            final int metadataWithOffset3 = this.getMetadataWithOffset(Blocks.oak_stairs, 0);
            final int metadataWithOffset4 = this.getMetadataWithOffset(Blocks.oak_stairs, 2);
            this.fillWithBlocks(world, structureBoundingBox, 0, 4, 1, 6, 4, 1, Blocks.spruce_stairs.getStateFromMeta(metadataWithOffset), Blocks.spruce_stairs.getStateFromMeta(metadataWithOffset), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 4, 2, 0, 4, 7, Blocks.spruce_stairs.getStateFromMeta(metadataWithOffset3), Blocks.spruce_stairs.getStateFromMeta(metadataWithOffset3), false);
            this.fillWithBlocks(world, structureBoundingBox, 6, 4, 2, 6, 4, 7, Blocks.spruce_stairs.getStateFromMeta(metadataWithOffset2), Blocks.spruce_stairs.getStateFromMeta(metadataWithOffset2), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 4, 8, 6, 4, 8, Blocks.spruce_stairs.getStateFromMeta(metadataWithOffset4), Blocks.spruce_stairs.getStateFromMeta(metadataWithOffset4), false);
            while (true) {
                this.replaceAirAndLiquidDownwards(world, Blocks.log.getDefaultState(), 1, -1, 2, structureBoundingBox);
                final int n;
                n += 4;
            }
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setBoolean("Witch", this.hasWitch);
        }
    }
}
