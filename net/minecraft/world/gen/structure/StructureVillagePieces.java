package net.minecraft.world.gen.structure;

import com.google.common.collect.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.nbt.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.biome.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class StructureVillagePieces
{
    private static StructureComponent func_176066_d(final Start start, final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
        if (n4 > 50) {
            return null;
        }
        if (Math.abs(n - start.getBoundingBox().minX) <= 112 && Math.abs(n3 - start.getBoundingBox().minZ) <= 112) {
            final Village func_176067_c = func_176067_c(start, list, random, n, n2, n3, enumFacing, n4 + 1);
            if (func_176067_c != null) {
                final int n5 = (func_176067_c.boundingBox.minX + func_176067_c.boundingBox.maxX) / 2;
                final int n6 = (func_176067_c.boundingBox.minZ + func_176067_c.boundingBox.maxZ) / 2;
                final int n7 = func_176067_c.boundingBox.maxX - func_176067_c.boundingBox.minX;
                final int n8 = func_176067_c.boundingBox.maxZ - func_176067_c.boundingBox.minZ;
                if (start.getWorldChunkManager().areBiomesViable(n5, n6, ((n7 > n8) ? n7 : n8) / 2 + 4, MapGenVillage.villageSpawnBiomes)) {
                    list.add(func_176067_c);
                    start.field_74932_i.add(func_176067_c);
                    return func_176067_c;
                }
            }
            return null;
        }
        return null;
    }
    
    private static Village func_176067_c(final Start start, final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
        final int func_75079_a = func_75079_a(start.structureVillageWeightedPieceList);
        if (func_75079_a <= 0) {
            return null;
        }
        PieceWeight structVillagePieceWeight = null;
        Village func_176065_a = null;
    Block_7:
        while (true) {
            int n5 = 0;
            ++n5;
            int nextInt = random.nextInt(func_75079_a);
            final Iterator<PieceWeight> iterator = (Iterator<PieceWeight>)start.structureVillageWeightedPieceList.iterator();
            while (iterator.hasNext()) {
                structVillagePieceWeight = iterator.next();
                nextInt -= structVillagePieceWeight.villagePieceWeight;
                if (nextInt < 0) {
                    if (!structVillagePieceWeight.canSpawnMoreVillagePiecesOfType(n4)) {
                        break;
                    }
                    if (structVillagePieceWeight == start.structVillagePieceWeight && start.structureVillageWeightedPieceList.size() > 1) {
                        break;
                    }
                    func_176065_a = func_176065_a(start, structVillagePieceWeight, list, random, n, n2, n3, enumFacing, n4);
                    if (func_176065_a != null) {
                        break Block_7;
                    }
                    continue;
                }
            }
        }
        final PieceWeight pieceWeight = structVillagePieceWeight;
        ++pieceWeight.villagePiecesSpawned;
        start.structVillagePieceWeight = structVillagePieceWeight;
        if (!structVillagePieceWeight.canSpawnMoreVillagePieces()) {
            start.structureVillageWeightedPieceList.remove(structVillagePieceWeight);
        }
        return func_176065_a;
    }
    
    private static Village func_176065_a(final Start start, final PieceWeight pieceWeight, final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
        final Class villagePieceClass = pieceWeight.villagePieceClass;
        Village village = null;
        if (villagePieceClass == House4Garden.class) {
            village = House4Garden.func_175858_a(start, list, random, n, n2, n3, enumFacing, n4);
        }
        else if (villagePieceClass == Church.class) {
            village = Church.func_175854_a(start, list, random, n, n2, n3, enumFacing, n4);
        }
        else if (villagePieceClass == House1.class) {
            village = House1.func_175850_a(start, list, random, n, n2, n3, enumFacing, n4);
        }
        else if (villagePieceClass == WoodHut.class) {
            village = WoodHut.func_175853_a(start, list, random, n, n2, n3, enumFacing, n4);
        }
        else if (villagePieceClass == Hall.class) {
            village = Hall.func_175857_a(start, list, random, n, n2, n3, enumFacing, n4);
        }
        else if (villagePieceClass == Field1.class) {
            village = Field1.func_175851_a(start, list, random, n, n2, n3, enumFacing, n4);
        }
        else if (villagePieceClass == Field2.class) {
            village = Field2.func_175852_a(start, list, random, n, n2, n3, enumFacing, n4);
        }
        else if (villagePieceClass == House2.class) {
            village = House2.func_175855_a(start, list, random, n, n2, n3, enumFacing, n4);
        }
        else if (villagePieceClass == House3.class) {
            village = House3.func_175849_a(start, list, random, n, n2, n3, enumFacing, n4);
        }
        return village;
    }
    
    public static void registerVillagePieces() {
        MapGenStructureIO.registerStructureComponent(House1.class, "ViBH");
        MapGenStructureIO.registerStructureComponent(Field1.class, "ViDF");
        MapGenStructureIO.registerStructureComponent(Field2.class, "ViF");
        MapGenStructureIO.registerStructureComponent(Torch.class, "ViL");
        MapGenStructureIO.registerStructureComponent(Hall.class, "ViPH");
        MapGenStructureIO.registerStructureComponent(House4Garden.class, "ViSH");
        MapGenStructureIO.registerStructureComponent(WoodHut.class, "ViSmH");
        MapGenStructureIO.registerStructureComponent(Church.class, "ViST");
        MapGenStructureIO.registerStructureComponent(House2.class, "ViS");
        MapGenStructureIO.registerStructureComponent(Start.class, "ViStart");
        MapGenStructureIO.registerStructureComponent(Path.class, "ViSR");
        MapGenStructureIO.registerStructureComponent(House3.class, "ViTRH");
        MapGenStructureIO.registerStructureComponent(Well.class, "ViW");
    }
    
    private static int func_75079_a(final List list) {
        for (final PieceWeight pieceWeight : list) {
            if (pieceWeight.villagePiecesLimit <= 0 || pieceWeight.villagePiecesSpawned < pieceWeight.villagePiecesLimit) {}
            final int n = 0 + pieceWeight.villagePieceWeight;
        }
        return 0;
    }
    
    static StructureComponent access$100(final Start start, final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
        return func_176066_d(start, list, random, n, n2, n3, enumFacing, n4);
    }
    
    static StructureComponent access$000(final Start start, final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
        return func_176069_e(start, list, random, n, n2, n3, enumFacing, n4);
    }
    
    public static List getStructureVillageWeightedPieceList(final Random random, final int n) {
        final ArrayList arrayList = Lists.newArrayList();
        arrayList.add(new PieceWeight(House4Garden.class, 4, MathHelper.getRandomIntegerInRange(random, 2 + n, 4 + n * 2)));
        arrayList.add(new PieceWeight(Church.class, 20, MathHelper.getRandomIntegerInRange(random, 0 + n, 1 + n)));
        arrayList.add(new PieceWeight(House1.class, 20, MathHelper.getRandomIntegerInRange(random, 0 + n, 2 + n)));
        arrayList.add(new PieceWeight(WoodHut.class, 3, MathHelper.getRandomIntegerInRange(random, 2 + n, 5 + n * 3)));
        arrayList.add(new PieceWeight(Hall.class, 15, MathHelper.getRandomIntegerInRange(random, 0 + n, 2 + n)));
        arrayList.add(new PieceWeight(Field1.class, 3, MathHelper.getRandomIntegerInRange(random, 1 + n, 4 + n)));
        arrayList.add(new PieceWeight(Field2.class, 3, MathHelper.getRandomIntegerInRange(random, 2 + n, 4 + n * 2)));
        arrayList.add(new PieceWeight(House2.class, 15, MathHelper.getRandomIntegerInRange(random, 0, 1 + n)));
        arrayList.add(new PieceWeight(House3.class, 8, MathHelper.getRandomIntegerInRange(random, 0 + n, 3 + n * 2)));
        final Iterator<PieceWeight> iterator = (Iterator<PieceWeight>)arrayList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().villagePiecesLimit == 0) {
                iterator.remove();
            }
        }
        return arrayList;
    }
    
    private static StructureComponent func_176069_e(final Start start, final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
        if (n4 > 3 + start.terrainType) {
            return null;
        }
        if (Math.abs(n - start.getBoundingBox().minX) <= 112 && Math.abs(n3 - start.getBoundingBox().minZ) <= 112) {
            final StructureBoundingBox func_175848_a = Path.func_175848_a(start, list, random, n, n2, n3, enumFacing);
            if (func_175848_a != null && func_175848_a.minY > 10) {
                final Path path = new Path(start, n4, random, func_175848_a, enumFacing);
                final int n5 = (path.boundingBox.minX + path.boundingBox.maxX) / 2;
                final int n6 = (path.boundingBox.minZ + path.boundingBox.maxZ) / 2;
                final int n7 = path.boundingBox.maxX - path.boundingBox.minX;
                final int n8 = path.boundingBox.maxZ - path.boundingBox.minZ;
                if (start.getWorldChunkManager().areBiomesViable(n5, n6, ((n7 > n8) ? n7 : n8) / 2 + 4, MapGenVillage.villageSpawnBiomes)) {
                    list.add(path);
                    start.field_74930_j.add(path);
                    return path;
                }
            }
            return null;
        }
        return null;
    }
    
    public static class WoodHut extends Village
    {
        private int tablePosition;
        private boolean isTallHouse;
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(world, structureBoundingBox);
                if (this.field_143015_k < 0) {
                    return true;
                }
                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
            }
            this.fillWithBlocks(world, structureBoundingBox, 1, 1, 1, 3, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 3, 0, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 0, 1, 2, 0, 3, Blocks.dirt.getDefaultState(), Blocks.dirt.getDefaultState(), false);
            if (this.isTallHouse) {
                this.fillWithBlocks(world, structureBoundingBox, 1, 4, 1, 2, 4, 3, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            }
            else {
                this.fillWithBlocks(world, structureBoundingBox, 1, 5, 1, 2, 5, 3, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            }
            this.setBlockState(world, Blocks.log.getDefaultState(), 1, 4, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.log.getDefaultState(), 2, 4, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.log.getDefaultState(), 1, 4, 4, structureBoundingBox);
            this.setBlockState(world, Blocks.log.getDefaultState(), 2, 4, 4, structureBoundingBox);
            this.setBlockState(world, Blocks.log.getDefaultState(), 0, 4, 1, structureBoundingBox);
            this.setBlockState(world, Blocks.log.getDefaultState(), 0, 4, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.log.getDefaultState(), 0, 4, 3, structureBoundingBox);
            this.setBlockState(world, Blocks.log.getDefaultState(), 3, 4, 1, structureBoundingBox);
            this.setBlockState(world, Blocks.log.getDefaultState(), 3, 4, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.log.getDefaultState(), 3, 4, 3, structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, 0, 1, 0, 0, 3, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 3, 1, 0, 3, 3, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 1, 4, 0, 3, 4, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 3, 1, 4, 3, 3, 4, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 1, 1, 0, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 3, 1, 1, 3, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 1, 0, 2, 3, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 1, 4, 2, 3, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 3, 2, 2, structureBoundingBox);
            if (this.tablePosition > 0) {
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), this.tablePosition, 1, 3, structureBoundingBox);
                this.setBlockState(world, Blocks.wooden_pressure_plate.getDefaultState(), this.tablePosition, 2, 3, structureBoundingBox);
            }
            this.setBlockState(world, Blocks.air.getDefaultState(), 1, 1, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), 1, 2, 0, structureBoundingBox);
            this.placeDoorCurrentPosition(world, structureBoundingBox, random, 1, 1, 0, EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, 1)));
            if (this.getBlockStateFromPos(world, 1, 0, -1, structureBoundingBox).getBlock().getMaterial() == Material.air && this.getBlockStateFromPos(world, 1, -1, -1, structureBoundingBox).getBlock().getMaterial() != Material.air) {
                this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 1, 0, -1, structureBoundingBox);
            }
            while (true) {
                this.clearCurrentPositionBlocksUpwards(world, 0, 6, 0, structureBoundingBox);
                this.replaceAirAndLiquidDownwards(world, Blocks.cobblestone.getDefaultState(), 0, -1, 0, structureBoundingBox);
                int n = 0;
                ++n;
            }
        }
        
        public static WoodHut func_175853_a(final Start start, final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, 0, 0, 0, 4, 6, 5, enumFacing);
            return (Village.canVillageGoDeeper(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) ? new WoodHut(start, n4, random, componentToAddBoundingBox, enumFacing) : null;
        }
        
        public WoodHut(final Start start, final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(start, n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            this.isTallHouse = random.nextBoolean();
            this.tablePosition = random.nextInt(3);
        }
        
        public WoodHut() {
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.tablePosition = nbtTagCompound.getInteger("T");
            this.isTallHouse = nbtTagCompound.getBoolean("C");
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setInteger("T", this.tablePosition);
            nbtTagCompound.setBoolean("C", this.isTallHouse);
        }
    }
    
    abstract static class Village extends StructureComponent
    {
        private boolean isDesertVillage;
        protected int field_143015_k;
        private int villagersSpawned;
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            nbtTagCompound.setInteger("HPos", this.field_143015_k);
            nbtTagCompound.setInteger("VCount", this.villagersSpawned);
            nbtTagCompound.setBoolean("Desert", this.isDesertVillage);
        }
        
        @Override
        protected void fillWithBlocks(final World world, final StructureBoundingBox structureBoundingBox, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final IBlockState blockState, final IBlockState blockState2, final boolean b) {
            super.fillWithBlocks(world, structureBoundingBox, n, n2, n3, n4, n5, n6, this.func_175847_a(blockState), this.func_175847_a(blockState2), b);
        }
        
        protected void func_175846_a(final boolean isDesertVillage) {
            this.isDesertVillage = isDesertVillage;
        }
        
        protected void spawnVillagers(final World world, final StructureBoundingBox structureBoundingBox, final int n, final int n2, final int n3, final int n4) {
            if (this.villagersSpawned < n4) {
                for (int i = this.villagersSpawned; i < n4; ++i) {
                    final int xWithOffset = this.getXWithOffset(n + i, n3);
                    final int yWithOffset = this.getYWithOffset(n2);
                    final int zWithOffset = this.getZWithOffset(n + i, n3);
                    if (!structureBoundingBox.isVecInside(new BlockPos(xWithOffset, yWithOffset, zWithOffset))) {
                        break;
                    }
                    ++this.villagersSpawned;
                    final EntityVillager entityVillager = new EntityVillager(world);
                    entityVillager.setLocationAndAngles(xWithOffset + 0.5, yWithOffset, zWithOffset + 0.5, 0.0f, 0.0f);
                    entityVillager.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entityVillager)), null);
                    entityVillager.setProfession(this.func_180779_c(i, entityVillager.getProfession()));
                    world.spawnEntityInWorld(entityVillager);
                }
            }
        }
        
        protected StructureComponent getNextComponentPP(final Start start, final List list, final Random random, final int n, final int n2) {
            if (this.coordBaseMode != null) {
                switch (StructureVillagePieces$1.$SwitchMap$net$minecraft$util$EnumFacing[this.coordBaseMode.ordinal()]) {
                    case 1: {
                        return StructureVillagePieces.access$100(start, list, random, this.boundingBox.maxX + 1, this.boundingBox.minY + n, this.boundingBox.minZ + n2, EnumFacing.EAST, this.getComponentType());
                    }
                    case 2: {
                        return StructureVillagePieces.access$100(start, list, random, this.boundingBox.maxX + 1, this.boundingBox.minY + n, this.boundingBox.minZ + n2, EnumFacing.EAST, this.getComponentType());
                    }
                    case 3: {
                        return StructureVillagePieces.access$100(start, list, random, this.boundingBox.minX + n2, this.boundingBox.minY + n, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
                    }
                    case 4: {
                        return StructureVillagePieces.access$100(start, list, random, this.boundingBox.minX + n2, this.boundingBox.minY + n, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
                    }
                }
            }
            return null;
        }
        
        protected IBlockState func_175847_a(final IBlockState blockState) {
            if (this.isDesertVillage) {
                if (blockState.getBlock() == Blocks.log || blockState.getBlock() == Blocks.log2) {
                    return Blocks.sandstone.getDefaultState();
                }
                if (blockState.getBlock() == Blocks.cobblestone) {
                    return Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.DEFAULT.getMetadata());
                }
                if (blockState.getBlock() == Blocks.planks) {
                    return Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata());
                }
                if (blockState.getBlock() == Blocks.oak_stairs) {
                    return Blocks.sandstone_stairs.getDefaultState().withProperty(BlockStairs.FACING, blockState.getValue(BlockStairs.FACING));
                }
                if (blockState.getBlock() == Blocks.stone_stairs) {
                    return Blocks.sandstone_stairs.getDefaultState().withProperty(BlockStairs.FACING, blockState.getValue(BlockStairs.FACING));
                }
                if (blockState.getBlock() == Blocks.gravel) {
                    return Blocks.sandstone.getDefaultState();
                }
            }
            return blockState;
        }
        
        @Override
        protected void replaceAirAndLiquidDownwards(final World world, final IBlockState blockState, final int n, final int n2, final int n3, final StructureBoundingBox structureBoundingBox) {
            super.replaceAirAndLiquidDownwards(world, this.func_175847_a(blockState), n, n2, n3, structureBoundingBox);
        }
        
        protected Village(final Start start, final int n) {
            super(n);
            this.field_143015_k = -1;
            if (start != null) {
                this.isDesertVillage = start.inDesert;
            }
        }
        
        protected StructureComponent getNextComponentNN(final Start start, final List list, final Random random, final int n, final int n2) {
            if (this.coordBaseMode != null) {
                switch (StructureVillagePieces$1.$SwitchMap$net$minecraft$util$EnumFacing[this.coordBaseMode.ordinal()]) {
                    case 1: {
                        return StructureVillagePieces.access$100(start, list, random, this.boundingBox.minX - 1, this.boundingBox.minY + n, this.boundingBox.minZ + n2, EnumFacing.WEST, this.getComponentType());
                    }
                    case 2: {
                        return StructureVillagePieces.access$100(start, list, random, this.boundingBox.minX - 1, this.boundingBox.minY + n, this.boundingBox.minZ + n2, EnumFacing.WEST, this.getComponentType());
                    }
                    case 3: {
                        return StructureVillagePieces.access$100(start, list, random, this.boundingBox.minX + n2, this.boundingBox.minY + n, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
                    }
                    case 4: {
                        return StructureVillagePieces.access$100(start, list, random, this.boundingBox.minX + n2, this.boundingBox.minY + n, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
                    }
                }
            }
            return null;
        }
        
        protected int func_180779_c(final int n, final int n2) {
            return n2;
        }
        
        @Override
        protected void setBlockState(final World world, final IBlockState blockState, final int n, final int n2, final int n3, final StructureBoundingBox structureBoundingBox) {
            super.setBlockState(world, this.func_175847_a(blockState), n, n2, n3, structureBoundingBox);
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            this.field_143015_k = nbtTagCompound.getInteger("HPos");
            this.villagersSpawned = nbtTagCompound.getInteger("VCount");
            this.isDesertVillage = nbtTagCompound.getBoolean("Desert");
        }
        
        public Village() {
            this.field_143015_k = -1;
        }
        
        protected static boolean canVillageGoDeeper(final StructureBoundingBox structureBoundingBox) {
            return structureBoundingBox != null && structureBoundingBox.minY > 10;
        }
        
        protected int getAverageGroundLevel(final World world, final StructureBoundingBox structureBoundingBox) {
            final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
            for (int i = this.boundingBox.minZ; i <= this.boundingBox.maxZ; ++i) {
                for (int j = this.boundingBox.minX; j <= this.boundingBox.maxX; ++j) {
                    mutableBlockPos.func_181079_c(j, 64, i);
                    if (structureBoundingBox.isVecInside(mutableBlockPos)) {
                        final int n = 0 + Math.max(world.getTopSolidOrLiquidBlock(mutableBlockPos).getY(), world.provider.getAverageGroundLevel());
                        int n2 = 0;
                        ++n2;
                    }
                }
            }
            return -1;
        }
    }
    
    public static class Start extends Well
    {
        public WorldChunkManager worldChunkMngr;
        public List field_74932_i;
        public boolean inDesert;
        public List structureVillageWeightedPieceList;
        public List field_74930_j;
        public int terrainType;
        public PieceWeight structVillagePieceWeight;
        
        public Start() {
            this.field_74932_i = Lists.newArrayList();
            this.field_74930_j = Lists.newArrayList();
        }
        
        public WorldChunkManager getWorldChunkManager() {
            return this.worldChunkMngr;
        }
        
        public Start(final WorldChunkManager worldChunkMngr, final int n, final Random random, final int n2, final int n3, final List structureVillageWeightedPieceList, final int terrainType) {
            super(null, 0, random, n2, n3);
            this.field_74932_i = Lists.newArrayList();
            this.field_74930_j = Lists.newArrayList();
            this.worldChunkMngr = worldChunkMngr;
            this.structureVillageWeightedPieceList = structureVillageWeightedPieceList;
            this.terrainType = terrainType;
            final BiomeGenBase biomeGenerator = worldChunkMngr.getBiomeGenerator(new BlockPos(n2, 0, n3), BiomeGenBase.field_180279_ad);
            this.func_175846_a(this.inDesert = (biomeGenerator == BiomeGenBase.desert || biomeGenerator == BiomeGenBase.desertHills));
        }
    }
    
    public static class PieceWeight
    {
        public int villagePiecesLimit;
        public final int villagePieceWeight;
        public Class villagePieceClass;
        public int villagePiecesSpawned;
        
        public boolean canSpawnMoreVillagePieces() {
            return this.villagePiecesLimit == 0 || this.villagePiecesSpawned < this.villagePiecesLimit;
        }
        
        public boolean canSpawnMoreVillagePiecesOfType(final int n) {
            return this.villagePiecesLimit == 0 || this.villagePiecesSpawned < this.villagePiecesLimit;
        }
        
        public PieceWeight(final Class villagePieceClass, final int villagePieceWeight, final int villagePiecesLimit) {
            this.villagePieceClass = villagePieceClass;
            this.villagePieceWeight = villagePieceWeight;
            this.villagePiecesLimit = villagePiecesLimit;
        }
    }
    
    public static class Well extends Village
    {
        public Well(final Start start, final int n, final Random random, final int n2, final int n3) {
            super(start, n);
            this.coordBaseMode = EnumFacing.Plane.HORIZONTAL.random(random);
            switch (StructureVillagePieces$1.$SwitchMap$net$minecraft$util$EnumFacing[this.coordBaseMode.ordinal()]) {
                case 1:
                case 2: {
                    this.boundingBox = new StructureBoundingBox(n2, 64, n3, n2 + 6 - 1, 78, n3 + 6 - 1);
                    break;
                }
                default: {
                    this.boundingBox = new StructureBoundingBox(n2, 64, n3, n2 + 6 - 1, 78, n3 + 6 - 1);
                    break;
                }
            }
        }
        
        public Well() {
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(world, structureBoundingBox);
                if (this.field_143015_k < 0) {
                    return true;
                }
                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 3, 0);
            }
            this.fillWithBlocks(world, structureBoundingBox, 1, 0, 1, 4, 12, 4, Blocks.cobblestone.getDefaultState(), Blocks.flowing_water.getDefaultState(), false);
            this.setBlockState(world, Blocks.air.getDefaultState(), 2, 12, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), 3, 12, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), 2, 12, 3, structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), 3, 12, 3, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 1, 13, 1, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 1, 14, 1, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 4, 13, 1, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 4, 14, 1, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 1, 13, 4, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 1, 14, 4, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 4, 13, 4, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 4, 14, 4, structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, 1, 15, 1, 4, 15, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            while (true) {
                this.setBlockState(world, Blocks.gravel.getDefaultState(), 0, 11, 0, structureBoundingBox);
                this.clearCurrentPositionBlocksUpwards(world, 0, 12, 0, structureBoundingBox);
                int n = 0;
                ++n;
            }
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List list, final Random random) {
            StructureVillagePieces.access$000((Start)structureComponent, list, random, this.boundingBox.minX - 1, this.boundingBox.maxY - 4, this.boundingBox.minZ + 1, EnumFacing.WEST, this.getComponentType());
            StructureVillagePieces.access$000((Start)structureComponent, list, random, this.boundingBox.maxX + 1, this.boundingBox.maxY - 4, this.boundingBox.minZ + 1, EnumFacing.EAST, this.getComponentType());
            StructureVillagePieces.access$000((Start)structureComponent, list, random, this.boundingBox.minX + 1, this.boundingBox.maxY - 4, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
            StructureVillagePieces.access$000((Start)structureComponent, list, random, this.boundingBox.minX + 1, this.boundingBox.maxY - 4, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
        }
    }
    
    public abstract static class Road extends Village
    {
        public Road() {
        }
        
        protected Road(final Start start, final int n) {
            super(start, n);
        }
    }
    
    public static class House4Garden extends Village
    {
        private boolean isRoofAccessible;
        
        public House4Garden(final Start start, final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(start, n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            this.isRoofAccessible = random.nextBoolean();
        }
        
        public static House4Garden func_175858_a(final Start start, final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, 0, 0, 0, 5, 6, 5, enumFacing);
            return (StructureComponent.findIntersecting(list, componentToAddBoundingBox) != null) ? null : new House4Garden(start, n4, random, componentToAddBoundingBox, enumFacing);
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setBoolean("Terrace", this.isRoofAccessible);
        }
        
        public House4Garden() {
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(world, structureBoundingBox);
                if (this.field_143015_k < 0) {
                    return true;
                }
                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
            }
            this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 4, 0, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 4, 0, 4, 4, 4, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 4, 1, 3, 4, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 0, 1, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 0, 2, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 0, 3, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 4, 1, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 4, 2, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 4, 3, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 0, 1, 4, structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 0, 2, 4, structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 0, 3, 4, structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 4, 1, 4, structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 4, 2, 4, structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 4, 3, 4, structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, 0, 1, 1, 0, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 4, 1, 1, 4, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 1, 4, 3, 3, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 2, 2, 4, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 4, 2, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.planks.getDefaultState(), 1, 1, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.planks.getDefaultState(), 1, 2, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.planks.getDefaultState(), 1, 3, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.planks.getDefaultState(), 2, 3, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.planks.getDefaultState(), 3, 3, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.planks.getDefaultState(), 3, 2, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.planks.getDefaultState(), 3, 1, 0, structureBoundingBox);
            if (this.getBlockStateFromPos(world, 2, 0, -1, structureBoundingBox).getBlock().getMaterial() == Material.air && this.getBlockStateFromPos(world, 2, -1, -1, structureBoundingBox).getBlock().getMaterial() != Material.air) {
                this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 2, 0, -1, structureBoundingBox);
            }
            this.fillWithBlocks(world, structureBoundingBox, 1, 1, 1, 3, 3, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            if (this.isRoofAccessible) {
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 0, 5, 0, structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 1, 5, 0, structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 2, 5, 0, structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 3, 5, 0, structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 4, 5, 0, structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 0, 5, 4, structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 1, 5, 4, structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 2, 5, 4, structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 3, 5, 4, structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 4, 5, 4, structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 4, 5, 1, structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 4, 5, 2, structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 4, 5, 3, structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 0, 5, 1, structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 0, 5, 2, structureBoundingBox);
                this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 0, 5, 3, structureBoundingBox);
            }
            if (this.isRoofAccessible) {
                this.getMetadataWithOffset(Blocks.ladder, 3);
                this.setBlockState(world, Blocks.ladder.getStateFromMeta(0), 3, 1, 3, structureBoundingBox);
                this.setBlockState(world, Blocks.ladder.getStateFromMeta(0), 3, 2, 3, structureBoundingBox);
                this.setBlockState(world, Blocks.ladder.getStateFromMeta(0), 3, 3, 3, structureBoundingBox);
                this.setBlockState(world, Blocks.ladder.getStateFromMeta(0), 3, 4, 3, structureBoundingBox);
            }
            this.setBlockState(world, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode), 2, 3, 1, structureBoundingBox);
            while (true) {
                this.clearCurrentPositionBlocksUpwards(world, 0, 6, 0, structureBoundingBox);
                this.replaceAirAndLiquidDownwards(world, Blocks.cobblestone.getDefaultState(), 0, -1, 0, structureBoundingBox);
                int n = 0;
                ++n;
            }
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.isRoofAccessible = nbtTagCompound.getBoolean("Terrace");
        }
    }
    
    public static class Path extends Road
    {
        private int length;
        
        public Path() {
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List list, final Random random) {
            for (int i = random.nextInt(5); i < this.length - 8; i += 2 + random.nextInt(5)) {
                final StructureComponent nextComponentNN = this.getNextComponentNN((Start)structureComponent, list, random, 0, i);
                if (nextComponentNN != null) {
                    i += Math.max(nextComponentNN.boundingBox.getXSize(), nextComponentNN.boundingBox.getZSize());
                }
            }
            for (int j = random.nextInt(5); j < this.length - 8; j += 2 + random.nextInt(5)) {
                final StructureComponent nextComponentPP = this.getNextComponentPP((Start)structureComponent, list, random, 0, j);
                if (nextComponentPP != null) {
                    j += Math.max(nextComponentPP.boundingBox.getXSize(), nextComponentPP.boundingBox.getZSize());
                }
            }
            if (random.nextInt(3) > 0 && this.coordBaseMode != null) {
                switch (StructureVillagePieces$1.$SwitchMap$net$minecraft$util$EnumFacing[this.coordBaseMode.ordinal()]) {
                    case 1: {
                        StructureVillagePieces.access$000((Start)structureComponent, list, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.WEST, this.getComponentType());
                        break;
                    }
                    case 2: {
                        StructureVillagePieces.access$000((Start)structureComponent, list, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, EnumFacing.WEST, this.getComponentType());
                        break;
                    }
                    case 3: {
                        StructureVillagePieces.access$000((Start)structureComponent, list, random, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
                        break;
                    }
                    case 4: {
                        StructureVillagePieces.access$000((Start)structureComponent, list, random, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
                        break;
                    }
                }
            }
            if (random.nextInt(3) > 0 && this.coordBaseMode != null) {
                switch (StructureVillagePieces$1.$SwitchMap$net$minecraft$util$EnumFacing[this.coordBaseMode.ordinal()]) {
                    case 1: {
                        StructureVillagePieces.access$000((Start)structureComponent, list, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.EAST, this.getComponentType());
                        break;
                    }
                    case 2: {
                        StructureVillagePieces.access$000((Start)structureComponent, list, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, EnumFacing.EAST, this.getComponentType());
                        break;
                    }
                    case 3: {
                        StructureVillagePieces.access$000((Start)structureComponent, list, random, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
                        break;
                    }
                    case 4: {
                        StructureVillagePieces.access$000((Start)structureComponent, list, random, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
                        break;
                    }
                }
            }
        }
        
        public static StructureBoundingBox func_175848_a(final Start start, final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing) {
            for (int i = 7 * MathHelper.getRandomIntegerInRange(random, 3, 5); i >= 7; i -= 7) {
                final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, 0, 0, 0, 3, 3, i, enumFacing);
                if (StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) {
                    return componentToAddBoundingBox;
                }
            }
            return null;
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setInteger("Length", this.length);
        }
        
        public Path(final Start start, final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(start, n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            this.length = Math.max(boundingBox.getXSize(), boundingBox.getZSize());
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            final IBlockState func_175847_a = this.func_175847_a(Blocks.gravel.getDefaultState());
            final IBlockState func_175847_a2 = this.func_175847_a(Blocks.cobblestone.getDefaultState());
            for (int i = this.boundingBox.minX; i <= this.boundingBox.maxX; ++i) {
                for (int j = this.boundingBox.minZ; j <= this.boundingBox.maxZ; ++j) {
                    final BlockPos blockPos = new BlockPos(i, 64, j);
                    if (structureBoundingBox.isVecInside(blockPos)) {
                        final BlockPos down = world.getTopSolidOrLiquidBlock(blockPos).down();
                        world.setBlockState(down, func_175847_a, 2);
                        world.setBlockState(down.down(), func_175847_a2, 2);
                    }
                }
            }
            return true;
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.length = nbtTagCompound.getInteger("Length");
        }
    }
    
    public static class Hall extends Village
    {
        @Override
        protected int func_180779_c(final int n, final int n2) {
            return (n == 0) ? 4 : super.func_180779_c(n, n2);
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(world, structureBoundingBox);
                if (this.field_143015_k < 0) {
                    return true;
                }
                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 7 - 1, 0);
            }
            this.fillWithBlocks(world, structureBoundingBox, 1, 1, 1, 7, 4, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 2, 1, 6, 8, 4, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 2, 0, 6, 8, 0, 10, Blocks.dirt.getDefaultState(), Blocks.dirt.getDefaultState(), false);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 6, 0, 6, structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, 2, 1, 6, 2, 1, 10, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 8, 1, 6, 8, 1, 10, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 3, 1, 10, 7, 1, 10, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 0, 1, 7, 0, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 0, 3, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 8, 0, 0, 8, 3, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 0, 0, 7, 1, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 0, 5, 7, 1, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 2, 0, 7, 3, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 2, 5, 7, 3, 5, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 4, 1, 8, 4, 1, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 4, 4, 8, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 5, 2, 8, 5, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.setBlockState(world, Blocks.planks.getDefaultState(), 0, 4, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.planks.getDefaultState(), 0, 4, 3, structureBoundingBox);
            this.setBlockState(world, Blocks.planks.getDefaultState(), 8, 4, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.planks.getDefaultState(), 8, 4, 3, structureBoundingBox);
            final int metadataWithOffset = this.getMetadataWithOffset(Blocks.oak_stairs, 3);
            final int metadataWithOffset2 = this.getMetadataWithOffset(Blocks.oak_stairs, 2);
            while (true) {
                this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(metadataWithOffset), 0, 4, 0, structureBoundingBox);
                this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(metadataWithOffset2), 0, 4, 5, structureBoundingBox);
                int n = 0;
                ++n;
            }
        }
        
        public static Hall func_175857_a(final Start start, final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, 0, 0, 0, 9, 7, 11, enumFacing);
            return (Village.canVillageGoDeeper(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) ? new Hall(start, n4, random, componentToAddBoundingBox, enumFacing) : null;
        }
        
        public Hall() {
        }
        
        public Hall(final Start start, final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(start, n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
    }
    
    public static class Church extends Village
    {
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(world, structureBoundingBox);
                if (this.field_143015_k < 0) {
                    return true;
                }
                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 12 - 1, 0);
            }
            this.fillWithBlocks(world, structureBoundingBox, 1, 1, 1, 3, 3, 7, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 5, 1, 3, 9, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 0, 0, 3, 0, 8, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 1, 0, 3, 10, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 1, 1, 0, 10, 3, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 4, 1, 1, 4, 10, 3, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 0, 4, 0, 4, 7, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 4, 0, 4, 4, 4, 7, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 1, 8, 3, 4, 8, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 5, 4, 3, 10, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 5, 5, 3, 5, 7, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 9, 0, 4, 9, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 4, 0, 4, 4, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 0, 11, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 4, 11, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 2, 11, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 2, 11, 4, structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 1, 1, 6, structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 1, 1, 7, structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 2, 1, 7, structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 3, 1, 6, structureBoundingBox);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 3, 1, 7, structureBoundingBox);
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 1, 1, 5, structureBoundingBox);
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 2, 1, 6, structureBoundingBox);
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 3, 1, 5, structureBoundingBox);
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 1)), 1, 2, 7, structureBoundingBox);
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 0)), 3, 2, 7, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0, 3, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 4, 2, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 4, 3, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0, 6, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0, 7, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 4, 6, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 4, 7, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 2, 6, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 2, 7, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 2, 6, 4, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 2, 7, 4, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0, 3, 6, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 4, 3, 6, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 2, 3, 8, structureBoundingBox);
            this.setBlockState(world, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode.getOpposite()), 2, 4, 7, structureBoundingBox);
            this.setBlockState(world, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode.rotateY()), 1, 4, 6, structureBoundingBox);
            this.setBlockState(world, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode.rotateYCCW()), 3, 4, 6, structureBoundingBox);
            this.setBlockState(world, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode), 2, 4, 5, structureBoundingBox);
            final int metadataWithOffset = this.getMetadataWithOffset(Blocks.ladder, 4);
            while (true) {
                this.setBlockState(world, Blocks.ladder.getStateFromMeta(metadataWithOffset), 3, 0, 3, structureBoundingBox);
                int n = 0;
                ++n;
            }
        }
        
        public Church(final Start start, final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(start, n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
        
        public static Church func_175854_a(final Start start, final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, 0, 0, 0, 5, 12, 9, enumFacing);
            return (Village.canVillageGoDeeper(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) ? new Church(start, n4, random, componentToAddBoundingBox, enumFacing) : null;
        }
        
        public Church() {
        }
        
        @Override
        protected int func_180779_c(final int n, final int n2) {
            return 2;
        }
    }
    
    public static class Torch extends Village
    {
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(world, structureBoundingBox);
                if (this.field_143015_k < 0) {
                    return true;
                }
                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 4 - 1, 0);
            }
            this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 2, 3, 1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 1, 0, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 1, 1, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 1, 2, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.wool.getStateFromMeta(EnumDyeColor.WHITE.getDyeDamage()), 1, 3, 0, structureBoundingBox);
            final boolean b = this.coordBaseMode == EnumFacing.EAST || this.coordBaseMode == EnumFacing.NORTH;
            this.setBlockState(world, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode.rotateY()), b ? 2 : 0, 3, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode), 1, 3, 1, structureBoundingBox);
            this.setBlockState(world, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode.rotateYCCW()), b ? 0 : 2, 3, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode.getOpposite()), 1, 3, -1, structureBoundingBox);
            return true;
        }
        
        public Torch() {
        }
        
        public Torch(final Start start, final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(start, n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
        
        public static StructureBoundingBox func_175856_a(final Start start, final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, 0, 0, 0, 3, 4, 2, enumFacing);
            return (StructureComponent.findIntersecting(list, componentToAddBoundingBox) != null) ? null : componentToAddBoundingBox;
        }
    }
    
    public static class House1 extends Village
    {
        public static House1 func_175850_a(final Start start, final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, 0, 0, 0, 9, 9, 6, enumFacing);
            return (Village.canVillageGoDeeper(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) ? new House1(start, n4, random, componentToAddBoundingBox, enumFacing) : null;
        }
        
        public House1(final Start start, final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(start, n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(world, structureBoundingBox);
                if (this.field_143015_k < 0) {
                    return true;
                }
                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 9 - 1, 0);
            }
            this.fillWithBlocks(world, structureBoundingBox, 1, 1, 1, 7, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 8, 0, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 5, 0, 8, 5, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 6, 1, 8, 6, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 7, 2, 8, 7, 3, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            final int metadataWithOffset = this.getMetadataWithOffset(Blocks.oak_stairs, 3);
            final int metadataWithOffset2 = this.getMetadataWithOffset(Blocks.oak_stairs, 2);
            while (true) {
                this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(metadataWithOffset), 0, 5, -1, structureBoundingBox);
                this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(metadataWithOffset2), 0, 5, 6, structureBoundingBox);
                int n = 0;
                ++n;
            }
        }
        
        public House1() {
        }
        
        @Override
        protected int func_180779_c(final int n, final int n2) {
            return 1;
        }
    }
    
    public static class House3 extends Village
    {
        public House3(final Start start, final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(start, n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(world, structureBoundingBox);
                if (this.field_143015_k < 0) {
                    return true;
                }
                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 7 - 1, 0);
            }
            this.fillWithBlocks(world, structureBoundingBox, 1, 1, 1, 7, 4, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 2, 1, 6, 8, 4, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 2, 0, 5, 8, 0, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 0, 1, 7, 0, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 0, 3, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 8, 0, 0, 8, 3, 10, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 0, 0, 7, 2, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 0, 5, 2, 1, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 2, 0, 6, 2, 3, 10, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 3, 0, 10, 7, 3, 10, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 2, 0, 7, 3, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 2, 5, 2, 3, 5, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 4, 1, 8, 4, 1, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 4, 4, 3, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 5, 2, 8, 5, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.setBlockState(world, Blocks.planks.getDefaultState(), 0, 4, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.planks.getDefaultState(), 0, 4, 3, structureBoundingBox);
            this.setBlockState(world, Blocks.planks.getDefaultState(), 8, 4, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.planks.getDefaultState(), 8, 4, 3, structureBoundingBox);
            this.setBlockState(world, Blocks.planks.getDefaultState(), 8, 4, 4, structureBoundingBox);
            final int metadataWithOffset = this.getMetadataWithOffset(Blocks.oak_stairs, 3);
            this.getMetadataWithOffset(Blocks.oak_stairs, 2);
            while (true) {
                this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(metadataWithOffset), 4, 3, -1, structureBoundingBox);
                int n = 0;
                ++n;
            }
        }
        
        public House3() {
        }
        
        public static House3 func_175849_a(final Start start, final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, 0, 0, 0, 9, 7, 12, enumFacing);
            return (Village.canVillageGoDeeper(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) ? new House3(start, n4, random, componentToAddBoundingBox, enumFacing) : null;
        }
    }
    
    public static class Field1 extends Village
    {
        private Block cropTypeC;
        private Block cropTypeB;
        private Block cropTypeA;
        private Block cropTypeD;
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.cropTypeA = Block.getBlockById(nbtTagCompound.getInteger("CA"));
            this.cropTypeB = Block.getBlockById(nbtTagCompound.getInteger("CB"));
            this.cropTypeC = Block.getBlockById(nbtTagCompound.getInteger("CC"));
            this.cropTypeD = Block.getBlockById(nbtTagCompound.getInteger("CD"));
        }
        
        public Field1(final Start start, final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(start, n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            this.cropTypeA = this.func_151559_a(random);
            this.cropTypeB = this.func_151559_a(random);
            this.cropTypeC = this.func_151559_a(random);
            this.cropTypeD = this.func_151559_a(random);
        }
        
        public Field1() {
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setInteger("CA", Block.blockRegistry.getIDForObject(this.cropTypeA));
            nbtTagCompound.setInteger("CB", Block.blockRegistry.getIDForObject(this.cropTypeB));
            nbtTagCompound.setInteger("CC", Block.blockRegistry.getIDForObject(this.cropTypeC));
            nbtTagCompound.setInteger("CD", Block.blockRegistry.getIDForObject(this.cropTypeD));
        }
        
        public static Field1 func_175851_a(final Start start, final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, 0, 0, 0, 13, 4, 9, enumFacing);
            return (Village.canVillageGoDeeper(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) ? new Field1(start, n4, random, componentToAddBoundingBox, enumFacing) : null;
        }
        
        private Block func_151559_a(final Random random) {
            switch (random.nextInt(5)) {
                case 0: {
                    return Blocks.carrots;
                }
                case 1: {
                    return Blocks.potatoes;
                }
                default: {
                    return Blocks.wheat;
                }
            }
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(world, structureBoundingBox);
                if (this.field_143015_k < 0) {
                    return true;
                }
                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 4 - 1, 0);
            }
            this.fillWithBlocks(world, structureBoundingBox, 0, 1, 0, 12, 4, 8, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 0, 1, 2, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 4, 0, 1, 5, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 7, 0, 1, 8, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 10, 0, 1, 11, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 0, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 6, 0, 0, 6, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 12, 0, 0, 12, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 0, 0, 11, 0, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 0, 8, 11, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 3, 0, 1, 3, 0, 7, Blocks.water.getDefaultState(), Blocks.water.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 9, 0, 1, 9, 0, 7, Blocks.water.getDefaultState(), Blocks.water.getDefaultState(), false);
            while (true) {
                this.setBlockState(world, this.cropTypeA.getStateFromMeta(MathHelper.getRandomIntegerInRange(random, 2, 7)), 1, 1, 0, structureBoundingBox);
                this.setBlockState(world, this.cropTypeA.getStateFromMeta(MathHelper.getRandomIntegerInRange(random, 2, 7)), 2, 1, 0, structureBoundingBox);
                this.setBlockState(world, this.cropTypeB.getStateFromMeta(MathHelper.getRandomIntegerInRange(random, 2, 7)), 4, 1, 0, structureBoundingBox);
                this.setBlockState(world, this.cropTypeB.getStateFromMeta(MathHelper.getRandomIntegerInRange(random, 2, 7)), 5, 1, 0, structureBoundingBox);
                this.setBlockState(world, this.cropTypeC.getStateFromMeta(MathHelper.getRandomIntegerInRange(random, 2, 7)), 7, 1, 0, structureBoundingBox);
                this.setBlockState(world, this.cropTypeC.getStateFromMeta(MathHelper.getRandomIntegerInRange(random, 2, 7)), 8, 1, 0, structureBoundingBox);
                this.setBlockState(world, this.cropTypeD.getStateFromMeta(MathHelper.getRandomIntegerInRange(random, 2, 7)), 10, 1, 0, structureBoundingBox);
                this.setBlockState(world, this.cropTypeD.getStateFromMeta(MathHelper.getRandomIntegerInRange(random, 2, 7)), 11, 1, 0, structureBoundingBox);
                int n = 0;
                ++n;
            }
        }
    }
    
    public static class Field2 extends Village
    {
        private Block cropTypeA;
        private Block cropTypeB;
        
        public static Field2 func_175852_a(final Start start, final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, 0, 0, 0, 7, 4, 9, enumFacing);
            return (Village.canVillageGoDeeper(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) ? new Field2(start, n4, random, componentToAddBoundingBox, enumFacing) : null;
        }
        
        public Field2(final Start start, final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(start, n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            this.cropTypeA = this.func_151560_a(random);
            this.cropTypeB = this.func_151560_a(random);
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(world, structureBoundingBox);
                if (this.field_143015_k < 0) {
                    return true;
                }
                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 4 - 1, 0);
            }
            this.fillWithBlocks(world, structureBoundingBox, 0, 1, 0, 6, 4, 8, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 0, 1, 2, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 4, 0, 1, 5, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 0, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 6, 0, 0, 6, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 0, 0, 5, 0, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 0, 8, 5, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 3, 0, 1, 3, 0, 7, Blocks.water.getDefaultState(), Blocks.water.getDefaultState(), false);
            while (true) {
                this.setBlockState(world, this.cropTypeA.getStateFromMeta(MathHelper.getRandomIntegerInRange(random, 2, 7)), 1, 1, 0, structureBoundingBox);
                this.setBlockState(world, this.cropTypeA.getStateFromMeta(MathHelper.getRandomIntegerInRange(random, 2, 7)), 2, 1, 0, structureBoundingBox);
                this.setBlockState(world, this.cropTypeB.getStateFromMeta(MathHelper.getRandomIntegerInRange(random, 2, 7)), 4, 1, 0, structureBoundingBox);
                this.setBlockState(world, this.cropTypeB.getStateFromMeta(MathHelper.getRandomIntegerInRange(random, 2, 7)), 5, 1, 0, structureBoundingBox);
                int n = 0;
                ++n;
            }
        }
        
        public Field2() {
        }
        
        private Block func_151560_a(final Random random) {
            switch (random.nextInt(5)) {
                case 0: {
                    return Blocks.carrots;
                }
                case 1: {
                    return Blocks.potatoes;
                }
                default: {
                    return Blocks.wheat;
                }
            }
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setInteger("CA", Block.blockRegistry.getIDForObject(this.cropTypeA));
            nbtTagCompound.setInteger("CB", Block.blockRegistry.getIDForObject(this.cropTypeB));
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.cropTypeA = Block.getBlockById(nbtTagCompound.getInteger("CA"));
            this.cropTypeB = Block.getBlockById(nbtTagCompound.getInteger("CB"));
        }
    }
    
    public static class House2 extends Village
    {
        private static final List villageBlacksmithChestContents;
        private boolean hasMadeChest;
        
        public static House2 func_175855_a(final Start start, final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, 0, 0, 0, 10, 6, 7, enumFacing);
            return (Village.canVillageGoDeeper(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) ? new House2(start, n4, random, componentToAddBoundingBox, enumFacing) : null;
        }
        
        public House2(final Start start, final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(start, n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(world, structureBoundingBox);
                if (this.field_143015_k < 0) {
                    return true;
                }
                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
            }
            this.fillWithBlocks(world, structureBoundingBox, 0, 1, 0, 9, 4, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 9, 0, 6, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 4, 0, 9, 4, 6, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 5, 0, 9, 5, 6, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 5, 1, 8, 5, 5, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 1, 0, 2, 3, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 1, 0, 0, 4, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 3, 1, 0, 3, 4, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 1, 6, 0, 4, 6, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
            this.setBlockState(world, Blocks.planks.getDefaultState(), 3, 3, 1, structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, 3, 1, 2, 3, 3, 2, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 4, 1, 3, 5, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 1, 1, 0, 3, 5, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 1, 6, 5, 3, 6, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 5, 1, 0, 5, 3, 0, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 9, 1, 0, 9, 3, 0, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 6, 1, 4, 9, 4, 6, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
            this.setBlockState(world, Blocks.flowing_lava.getDefaultState(), 7, 1, 5, structureBoundingBox);
            this.setBlockState(world, Blocks.flowing_lava.getDefaultState(), 8, 1, 5, structureBoundingBox);
            this.setBlockState(world, Blocks.iron_bars.getDefaultState(), 9, 2, 5, structureBoundingBox);
            this.setBlockState(world, Blocks.iron_bars.getDefaultState(), 9, 2, 4, structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, 7, 2, 4, 8, 2, 5, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 6, 1, 3, structureBoundingBox);
            this.setBlockState(world, Blocks.furnace.getDefaultState(), 6, 2, 3, structureBoundingBox);
            this.setBlockState(world, Blocks.furnace.getDefaultState(), 6, 3, 3, structureBoundingBox);
            this.setBlockState(world, Blocks.double_stone_slab.getDefaultState(), 8, 1, 1, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0, 2, 4, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 2, 2, 6, structureBoundingBox);
            this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 4, 2, 6, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 2, 1, 4, structureBoundingBox);
            this.setBlockState(world, Blocks.wooden_pressure_plate.getDefaultState(), 2, 2, 4, structureBoundingBox);
            this.setBlockState(world, Blocks.planks.getDefaultState(), 1, 1, 5, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.oak_stairs, 3)), 2, 1, 5, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.oak_stairs, 1)), 1, 1, 4, structureBoundingBox);
            if (!this.hasMadeChest && structureBoundingBox.isVecInside(new BlockPos(this.getXWithOffset(5, 5), this.getYWithOffset(1), this.getZWithOffset(5, 5)))) {
                this.hasMadeChest = true;
                this.generateChestContents(world, structureBoundingBox, random, 5, 1, 5, House2.villageBlacksmithChestContents, 3 + random.nextInt(6));
            }
            while (true) {
                if (this.getBlockStateFromPos(world, 0, 0, -1, structureBoundingBox).getBlock().getMaterial() == Material.air && this.getBlockStateFromPos(world, 0, -1, -1, structureBoundingBox).getBlock().getMaterial() != Material.air) {
                    this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 0, 0, -1, structureBoundingBox);
                }
                int n = 0;
                ++n;
            }
        }
        
        static {
            villageBlacksmithChestContents = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15), new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_sword, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_chestplate, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_helmet, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_leggings, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_boots, 0, 1, 1, 5), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.obsidian), 0, 3, 7, 5), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.sapling), 0, 3, 7, 5), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1) });
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setBoolean("Chest", this.hasMadeChest);
        }
        
        public House2() {
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.hasMadeChest = nbtTagCompound.getBoolean("Chest");
        }
        
        @Override
        protected int func_180779_c(final int n, final int n2) {
            return 3;
        }
    }
}
