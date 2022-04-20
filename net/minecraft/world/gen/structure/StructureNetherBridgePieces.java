package net.minecraft.world.gen.structure;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import com.google.common.collect.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

public class StructureNetherBridgePieces
{
    private static final PieceWeight[] primaryComponents;
    private static final PieceWeight[] secondaryComponents;
    
    static PieceWeight[] access$100() {
        return StructureNetherBridgePieces.primaryComponents;
    }
    
    static {
        primaryComponents = new PieceWeight[] { new PieceWeight(Straight.class, 30, 0, true), new PieceWeight(Crossing3.class, 10, 4), new PieceWeight(Crossing.class, 10, 4), new PieceWeight(Stairs.class, 10, 3), new PieceWeight(Throne.class, 5, 2), new PieceWeight(Entrance.class, 5, 1) };
        secondaryComponents = new PieceWeight[] { new PieceWeight(Corridor5.class, 25, 0, true), new PieceWeight(Crossing2.class, 15, 5), new PieceWeight(Corridor2.class, 5, 10), new PieceWeight(Corridor.class, 5, 10), new PieceWeight(Corridor3.class, 10, 3, true), new PieceWeight(Corridor4.class, 7, 2), new PieceWeight(NetherStalkRoom.class, 5, 2) };
    }
    
    public static void registerNetherFortressPieces() {
        MapGenStructureIO.registerStructureComponent(Crossing3.class, "NeBCr");
        MapGenStructureIO.registerStructureComponent(End.class, "NeBEF");
        MapGenStructureIO.registerStructureComponent(Straight.class, "NeBS");
        MapGenStructureIO.registerStructureComponent(Corridor3.class, "NeCCS");
        MapGenStructureIO.registerStructureComponent(Corridor4.class, "NeCTB");
        MapGenStructureIO.registerStructureComponent(Entrance.class, "NeCE");
        MapGenStructureIO.registerStructureComponent(Crossing2.class, "NeSCSC");
        MapGenStructureIO.registerStructureComponent(Corridor.class, "NeSCLT");
        MapGenStructureIO.registerStructureComponent(Corridor5.class, "NeSC");
        MapGenStructureIO.registerStructureComponent(Corridor2.class, "NeSCRT");
        MapGenStructureIO.registerStructureComponent(NetherStalkRoom.class, "NeCSR");
        MapGenStructureIO.registerStructureComponent(Throne.class, "NeMT");
        MapGenStructureIO.registerStructureComponent(Crossing.class, "NeRC");
        MapGenStructureIO.registerStructureComponent(Stairs.class, "NeSR");
        MapGenStructureIO.registerStructureComponent(Start.class, "NeStart");
    }
    
    private static Piece func_175887_b(final PieceWeight pieceWeight, final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
        final Class weightClass = pieceWeight.weightClass;
        Piece piece = null;
        if (weightClass == Straight.class) {
            piece = Straight.func_175882_a(list, random, n, n2, n3, enumFacing, n4);
        }
        else if (weightClass == Crossing3.class) {
            piece = Crossing3.func_175885_a(list, random, n, n2, n3, enumFacing, n4);
        }
        else if (weightClass == Crossing.class) {
            piece = Crossing.func_175873_a(list, random, n, n2, n3, enumFacing, n4);
        }
        else if (weightClass == Stairs.class) {
            piece = Stairs.func_175872_a(list, random, n, n2, n3, n4, enumFacing);
        }
        else if (weightClass == Throne.class) {
            piece = Throne.func_175874_a(list, random, n, n2, n3, n4, enumFacing);
        }
        else if (weightClass == Entrance.class) {
            piece = Entrance.func_175881_a(list, random, n, n2, n3, enumFacing, n4);
        }
        else if (weightClass == Corridor5.class) {
            piece = Corridor5.func_175877_a(list, random, n, n2, n3, enumFacing, n4);
        }
        else if (weightClass == Corridor2.class) {
            piece = Corridor2.func_175876_a(list, random, n, n2, n3, enumFacing, n4);
        }
        else if (weightClass == Corridor.class) {
            piece = Corridor.func_175879_a(list, random, n, n2, n3, enumFacing, n4);
        }
        else if (weightClass == Corridor3.class) {
            piece = Corridor3.func_175883_a(list, random, n, n2, n3, enumFacing, n4);
        }
        else if (weightClass == Corridor4.class) {
            piece = Corridor4.func_175880_a(list, random, n, n2, n3, enumFacing, n4);
        }
        else if (weightClass == Crossing2.class) {
            piece = Crossing2.func_175878_a(list, random, n, n2, n3, enumFacing, n4);
        }
        else if (weightClass == NetherStalkRoom.class) {
            piece = NetherStalkRoom.func_175875_a(list, random, n, n2, n3, enumFacing, n4);
        }
        return piece;
    }
    
    static Piece access$000(final PieceWeight pieceWeight, final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
        return func_175887_b(pieceWeight, list, random, n, n2, n3, enumFacing, n4);
    }
    
    static PieceWeight[] access$200() {
        return StructureNetherBridgePieces.secondaryComponents;
    }
    
    public static class Corridor3 extends Piece
    {
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List list, final Random random) {
            this.getNextComponentNormal((Start)structureComponent, list, random, 1, 0, true);
        }
        
        public static Corridor3 func_175883_a(final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -1, -7, 0, 5, 14, 10, enumFacing);
            return (Piece.isAboveGround(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) ? new Corridor3(n4, random, componentToAddBoundingBox, enumFacing) : null;
        }
        
        public Corridor3() {
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            final int metadataWithOffset = this.getMetadataWithOffset(Blocks.nether_brick_stairs, 2);
            final int max = Math.max(1, 7);
            final int min = Math.min(Math.max(max + 5, 14), 13);
            this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 4, max, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, max + 1, 0, 3, min - 1, 0, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.setBlockState(world, Blocks.nether_brick_stairs.getStateFromMeta(metadataWithOffset), 1, max + 1, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.nether_brick_stairs.getStateFromMeta(metadataWithOffset), 2, max + 1, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.nether_brick_stairs.getStateFromMeta(metadataWithOffset), 3, max + 1, 0, structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, 0, min, 0, 4, min, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, max + 1, 0, 0, min - 1, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 4, max + 1, 0, 4, min - 1, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, max + 2, 0, 0, max + 3, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 4, max + 2, 0, 4, max + 3, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            while (true) {
                this.replaceAirAndLiquidDownwards(world, Blocks.nether_brick.getDefaultState(), 0, -1, 0, structureBoundingBox);
                int n = 0;
                ++n;
            }
        }
        
        public Corridor3(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
    }
    
    abstract static class Piece extends StructureComponent
    {
        protected static final List field_111019_a;
        
        protected Piece(final int n) {
            super(n);
        }
        
        public Piece() {
        }
        
        private Piece func_175871_a(final Start start, final List list, final List list2, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final int totalWeight = this.getTotalWeight(list);
            final boolean b = totalWeight > 0 && n4 <= 30;
            while (b) {
                int n5 = 0;
                ++n5;
                int nextInt = random.nextInt(totalWeight);
                for (final PieceWeight theNetherBridgePieceWeight : list) {
                    nextInt -= theNetherBridgePieceWeight.field_78826_b;
                    if (nextInt < 0) {
                        if (!theNetherBridgePieceWeight.func_78822_a(n4)) {
                            break;
                        }
                        if (theNetherBridgePieceWeight == start.theNetherBridgePieceWeight && !theNetherBridgePieceWeight.field_78825_e) {
                            break;
                        }
                        final Piece access$000 = StructureNetherBridgePieces.access$000(theNetherBridgePieceWeight, list2, random, n, n2, n3, enumFacing, n4);
                        if (access$000 != null) {
                            final PieceWeight pieceWeight = theNetherBridgePieceWeight;
                            ++pieceWeight.field_78827_c;
                            start.theNetherBridgePieceWeight = theNetherBridgePieceWeight;
                            if (!theNetherBridgePieceWeight.func_78823_a()) {
                                list.remove(theNetherBridgePieceWeight);
                            }
                            return access$000;
                        }
                        continue;
                    }
                }
            }
            return End.func_175884_a(list2, random, n, n2, n3, enumFacing, n4);
        }
        
        private StructureComponent func_175870_a(final Start start, final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4, final boolean b) {
            if (Math.abs(n - start.getBoundingBox().minX) <= 112 && Math.abs(n3 - start.getBoundingBox().minZ) <= 112) {
                List list2 = start.primaryWeights;
                if (b) {
                    list2 = start.secondaryWeights;
                }
                final Piece func_175871_a = this.func_175871_a(start, list2, list, random, n, n2, n3, enumFacing, n4 + 1);
                if (func_175871_a != null) {
                    list.add(func_175871_a);
                    start.field_74967_d.add(func_175871_a);
                }
                return func_175871_a;
            }
            return End.func_175884_a(list, random, n, n2, n3, enumFacing, n4);
        }
        
        static {
            field_111019_a = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 5), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 5), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 15), new WeightedRandomChestContent(Items.golden_sword, 0, 1, 1, 5), new WeightedRandomChestContent(Items.golden_chestplate, 0, 1, 1, 5), new WeightedRandomChestContent(Items.flint_and_steel, 0, 1, 1, 5), new WeightedRandomChestContent(Items.nether_wart, 0, 3, 7, 5), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 10), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 8), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 5), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 3), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.obsidian), 0, 2, 4, 2) });
        }
        
        protected StructureComponent getNextComponentZ(final Start start, final List list, final Random random, final int n, final int n2, final boolean b) {
            if (this.coordBaseMode != null) {
                switch (StructureNetherBridgePieces$1.$SwitchMap$net$minecraft$util$EnumFacing[this.coordBaseMode.ordinal()]) {
                    case 1: {
                        return this.func_175870_a(start, list, random, this.boundingBox.maxX + 1, this.boundingBox.minY + n, this.boundingBox.minZ + n2, EnumFacing.EAST, this.getComponentType(), b);
                    }
                    case 2: {
                        return this.func_175870_a(start, list, random, this.boundingBox.maxX + 1, this.boundingBox.minY + n, this.boundingBox.minZ + n2, EnumFacing.EAST, this.getComponentType(), b);
                    }
                    case 3: {
                        return this.func_175870_a(start, list, random, this.boundingBox.minX + n2, this.boundingBox.minY + n, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType(), b);
                    }
                    case 4: {
                        return this.func_175870_a(start, list, random, this.boundingBox.minX + n2, this.boundingBox.minY + n, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType(), b);
                    }
                }
            }
            return null;
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
        }
        
        private int getTotalWeight(final List list) {
            for (final PieceWeight pieceWeight : list) {
                if (pieceWeight.field_78824_d <= 0 || pieceWeight.field_78827_c < pieceWeight.field_78824_d) {}
                final int n = 0 + pieceWeight.field_78826_b;
            }
            return 0;
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
        }
        
        protected static boolean isAboveGround(final StructureBoundingBox structureBoundingBox) {
            return structureBoundingBox != null && structureBoundingBox.minY > 10;
        }
        
        protected StructureComponent getNextComponentNormal(final Start start, final List list, final Random random, final int n, final int n2, final boolean b) {
            if (this.coordBaseMode != null) {
                switch (StructureNetherBridgePieces$1.$SwitchMap$net$minecraft$util$EnumFacing[this.coordBaseMode.ordinal()]) {
                    case 1: {
                        return this.func_175870_a(start, list, random, this.boundingBox.minX + n, this.boundingBox.minY + n2, this.boundingBox.minZ - 1, this.coordBaseMode, this.getComponentType(), b);
                    }
                    case 2: {
                        return this.func_175870_a(start, list, random, this.boundingBox.minX + n, this.boundingBox.minY + n2, this.boundingBox.maxZ + 1, this.coordBaseMode, this.getComponentType(), b);
                    }
                    case 3: {
                        return this.func_175870_a(start, list, random, this.boundingBox.minX - 1, this.boundingBox.minY + n2, this.boundingBox.minZ + n, this.coordBaseMode, this.getComponentType(), b);
                    }
                    case 4: {
                        return this.func_175870_a(start, list, random, this.boundingBox.maxX + 1, this.boundingBox.minY + n2, this.boundingBox.minZ + n, this.coordBaseMode, this.getComponentType(), b);
                    }
                }
            }
            return null;
        }
        
        protected StructureComponent getNextComponentX(final Start start, final List list, final Random random, final int n, final int n2, final boolean b) {
            if (this.coordBaseMode != null) {
                switch (StructureNetherBridgePieces$1.$SwitchMap$net$minecraft$util$EnumFacing[this.coordBaseMode.ordinal()]) {
                    case 1: {
                        return this.func_175870_a(start, list, random, this.boundingBox.minX - 1, this.boundingBox.minY + n, this.boundingBox.minZ + n2, EnumFacing.WEST, this.getComponentType(), b);
                    }
                    case 2: {
                        return this.func_175870_a(start, list, random, this.boundingBox.minX - 1, this.boundingBox.minY + n, this.boundingBox.minZ + n2, EnumFacing.WEST, this.getComponentType(), b);
                    }
                    case 3: {
                        return this.func_175870_a(start, list, random, this.boundingBox.minX + n2, this.boundingBox.minY + n, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType(), b);
                    }
                    case 4: {
                        return this.func_175870_a(start, list, random, this.boundingBox.minX + n2, this.boundingBox.minY + n, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType(), b);
                    }
                }
            }
            return null;
        }
    }
    
    static class PieceWeight
    {
        public int field_78824_d;
        public final int field_78826_b;
        public boolean field_78825_e;
        public Class weightClass;
        public int field_78827_c;
        
        public PieceWeight(final Class weightClass, final int field_78826_b, final int field_78824_d, final boolean field_78825_e) {
            this.weightClass = weightClass;
            this.field_78826_b = field_78826_b;
            this.field_78824_d = field_78824_d;
            this.field_78825_e = field_78825_e;
        }
        
        public boolean func_78823_a() {
            return this.field_78824_d == 0 || this.field_78827_c < this.field_78824_d;
        }
        
        public PieceWeight(final Class clazz, final int n, final int n2) {
            this(clazz, n, n2, false);
        }
        
        public boolean func_78822_a(final int n) {
            return this.field_78824_d == 0 || this.field_78827_c < this.field_78824_d;
        }
    }
    
    public static class End extends Piece
    {
        private int fillSeed;
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.fillSeed = nbtTagCompound.getInteger("Seed");
        }
        
        public End(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            this.fillSeed = random.nextInt();
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            final Random random2 = new Random(this.fillSeed);
            while (true) {
                this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 0, 0, random2.nextInt(8), Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
                int n = 0;
                ++n;
            }
        }
        
        public static End func_175884_a(final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -1, -3, 0, 5, 10, 8, enumFacing);
            return (Piece.isAboveGround(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) ? new End(n4, random, componentToAddBoundingBox, enumFacing) : null;
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setInteger("Seed", this.fillSeed);
        }
        
        public End() {
        }
    }
    
    public static class Start extends Crossing3
    {
        public PieceWeight theNetherBridgePieceWeight;
        public List primaryWeights;
        public List field_74967_d;
        public List secondaryWeights;
        
        public Start(final Random random, final int n, final int n2) {
            super(random, n, n2);
            this.field_74967_d = Lists.newArrayList();
            this.primaryWeights = Lists.newArrayList();
            final PieceWeight[] access$100 = StructureNetherBridgePieces.access$100();
            int n3 = 0;
            while (0 < access$100.length) {
                final PieceWeight pieceWeight = access$100[0];
                pieceWeight.field_78827_c = 0;
                this.primaryWeights.add(pieceWeight);
                ++n3;
            }
            this.secondaryWeights = Lists.newArrayList();
            final PieceWeight[] access$101 = StructureNetherBridgePieces.access$200();
            while (0 < access$101.length) {
                final PieceWeight pieceWeight2 = access$101[0];
                pieceWeight2.field_78827_c = 0;
                this.secondaryWeights.add(pieceWeight2);
                ++n3;
            }
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
        }
        
        public Start() {
            this.field_74967_d = Lists.newArrayList();
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
        }
    }
    
    public static class Crossing3 extends Piece
    {
        public static Crossing3 func_175885_a(final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -8, -3, 0, 19, 10, 19, enumFacing);
            return (Piece.isAboveGround(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) ? new Crossing3(n4, random, componentToAddBoundingBox, enumFacing) : null;
        }
        
        public Crossing3() {
        }
        
        protected Crossing3(final Random random, final int n, final int n2) {
            super(0);
            this.coordBaseMode = EnumFacing.Plane.HORIZONTAL.random(random);
            switch (StructureNetherBridgePieces$1.$SwitchMap$net$minecraft$util$EnumFacing[this.coordBaseMode.ordinal()]) {
                case 1:
                case 2: {
                    this.boundingBox = new StructureBoundingBox(n, 64, n2, n + 19 - 1, 73, n2 + 19 - 1);
                    break;
                }
                default: {
                    this.boundingBox = new StructureBoundingBox(n, 64, n2, n + 19 - 1, 73, n2 + 19 - 1);
                    break;
                }
            }
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List list, final Random random) {
            this.getNextComponentNormal((Start)structureComponent, list, random, 8, 3, false);
            this.getNextComponentX((Start)structureComponent, list, random, 3, 8, false);
            this.getNextComponentZ((Start)structureComponent, list, random, 3, 8, false);
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            this.fillWithBlocks(world, structureBoundingBox, 7, 3, 0, 11, 4, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 3, 7, 18, 4, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 8, 5, 0, 10, 7, 18, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 5, 8, 18, 7, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 7, 5, 0, 7, 5, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 7, 5, 11, 7, 5, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 11, 5, 0, 11, 5, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 11, 5, 11, 11, 5, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 5, 7, 7, 5, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 11, 5, 7, 18, 5, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 5, 11, 7, 5, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 11, 5, 11, 18, 5, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 7, 2, 0, 11, 2, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 7, 2, 13, 11, 2, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 7, 0, 0, 11, 1, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 7, 0, 15, 11, 1, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            while (true) {
                int n = 0;
                ++n;
            }
        }
        
        public Crossing3(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
    }
    
    public static class Corridor5 extends Piece
    {
        public static Corridor5 func_175877_a(final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -1, 0, 0, 5, 7, 5, enumFacing);
            return (Piece.isAboveGround(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) ? new Corridor5(n4, random, componentToAddBoundingBox, enumFacing) : null;
        }
        
        public Corridor5() {
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 4, 1, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 2, 0, 4, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 2, 0, 0, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 4, 2, 0, 4, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 3, 1, 0, 4, 1, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 3, 3, 0, 4, 3, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 4, 3, 1, 4, 4, 1, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 4, 3, 3, 4, 4, 3, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 6, 0, 4, 6, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            while (true) {
                this.replaceAirAndLiquidDownwards(world, Blocks.nether_brick.getDefaultState(), 0, -1, 0, structureBoundingBox);
                int n = 0;
                ++n;
            }
        }
        
        public Corridor5(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List list, final Random random) {
            this.getNextComponentNormal((Start)structureComponent, list, random, 1, 0, true);
        }
    }
    
    public static class Corridor extends Piece
    {
        private boolean field_111021_b;
        
        public static Corridor func_175879_a(final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -1, 0, 0, 5, 7, 5, enumFacing);
            return (Piece.isAboveGround(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) ? new Corridor(n4, random, componentToAddBoundingBox, enumFacing) : null;
        }
        
        public Corridor(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            this.field_111021_b = (random.nextInt(3) == 0);
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List list, final Random random) {
            this.getNextComponentX((Start)structureComponent, list, random, 0, 1, true);
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setBoolean("Chest", this.field_111021_b);
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.field_111021_b = nbtTagCompound.getBoolean("Chest");
        }
        
        public Corridor() {
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 4, 1, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 2, 0, 4, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 4, 2, 0, 4, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 4, 3, 1, 4, 4, 1, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 4, 3, 3, 4, 4, 3, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 2, 0, 0, 5, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 2, 4, 3, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 3, 4, 1, 4, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 3, 3, 4, 3, 4, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            if (this.field_111021_b && structureBoundingBox.isVecInside(new BlockPos(this.getXWithOffset(3, 3), this.getYWithOffset(2), this.getZWithOffset(3, 3)))) {
                this.field_111021_b = false;
                this.generateChestContents(world, structureBoundingBox, random, 3, 2, 3, Corridor.field_111019_a, 2 + random.nextInt(4));
            }
            this.fillWithBlocks(world, structureBoundingBox, 0, 6, 0, 4, 6, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            while (true) {
                this.replaceAirAndLiquidDownwards(world, Blocks.nether_brick.getDefaultState(), 0, -1, 0, structureBoundingBox);
                int n = 0;
                ++n;
            }
        }
    }
    
    public static class NetherStalkRoom extends Piece
    {
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List list, final Random random) {
            this.getNextComponentNormal((Start)structureComponent, list, random, 5, 3, true);
            this.getNextComponentNormal((Start)structureComponent, list, random, 5, 11, true);
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            this.fillWithBlocks(world, structureBoundingBox, 0, 3, 0, 12, 4, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 5, 0, 12, 13, 12, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 5, 0, 1, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 11, 5, 0, 12, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 2, 5, 11, 4, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 8, 5, 11, 10, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 5, 9, 11, 7, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 2, 5, 0, 4, 12, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 8, 5, 0, 10, 12, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 5, 9, 0, 7, 12, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 2, 11, 2, 10, 12, 10, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            while (true) {
                this.fillWithBlocks(world, structureBoundingBox, 3, 10, 0, 3, 11, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
                this.fillWithBlocks(world, structureBoundingBox, 3, 10, 12, 3, 11, 12, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
                this.fillWithBlocks(world, structureBoundingBox, 0, 10, 3, 0, 11, 3, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
                this.fillWithBlocks(world, structureBoundingBox, 12, 10, 3, 12, 11, 3, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
                this.setBlockState(world, Blocks.nether_brick.getDefaultState(), 3, 13, 0, structureBoundingBox);
                this.setBlockState(world, Blocks.nether_brick.getDefaultState(), 3, 13, 12, structureBoundingBox);
                this.setBlockState(world, Blocks.nether_brick.getDefaultState(), 0, 13, 3, structureBoundingBox);
                this.setBlockState(world, Blocks.nether_brick.getDefaultState(), 12, 13, 3, structureBoundingBox);
                this.setBlockState(world, Blocks.nether_brick_fence.getDefaultState(), 4, 13, 0, structureBoundingBox);
                this.setBlockState(world, Blocks.nether_brick_fence.getDefaultState(), 4, 13, 12, structureBoundingBox);
                this.setBlockState(world, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 4, structureBoundingBox);
                this.setBlockState(world, Blocks.nether_brick_fence.getDefaultState(), 12, 13, 4, structureBoundingBox);
                final int n;
                n += 2;
            }
        }
        
        public NetherStalkRoom() {
        }
        
        public NetherStalkRoom(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
        
        public static NetherStalkRoom func_175875_a(final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -5, -3, 0, 13, 14, 13, enumFacing);
            return (Piece.isAboveGround(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) ? new NetherStalkRoom(n4, random, componentToAddBoundingBox, enumFacing) : null;
        }
    }
    
    public static class Throne extends Piece
    {
        private boolean hasSpawner;
        
        @Override
        public boolean addComponentParts(final World p0, final Random p1, final StructureBoundingBox p2) {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     1: aload_1        
            //     2: aload_3        
            //     3: iconst_0       
            //     4: iconst_2       
            //     5: iconst_0       
            //     6: bipush          6
            //     8: bipush          7
            //    10: bipush          7
            //    12: getstatic       net/minecraft/init/Blocks.air:Lnet/minecraft/block/Block;
            //    15: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
            //    18: getstatic       net/minecraft/init/Blocks.air:Lnet/minecraft/block/Block;
            //    21: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
            //    24: iconst_0       
            //    25: invokevirtual   net/minecraft/world/gen/structure/StructureNetherBridgePieces$Throne.fillWithBlocks:(Lnet/minecraft/world/World;Lnet/minecraft/world/gen/structure/StructureBoundingBox;IIIIIILnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/state/IBlockState;Z)V
            //    28: aload_0        
            //    29: aload_1        
            //    30: aload_3        
            //    31: iconst_1       
            //    32: iconst_0       
            //    33: iconst_0       
            //    34: iconst_5       
            //    35: iconst_1       
            //    36: bipush          7
            //    38: getstatic       net/minecraft/init/Blocks.nether_brick:Lnet/minecraft/block/Block;
            //    41: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
            //    44: getstatic       net/minecraft/init/Blocks.nether_brick:Lnet/minecraft/block/Block;
            //    47: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
            //    50: iconst_0       
            //    51: invokevirtual   net/minecraft/world/gen/structure/StructureNetherBridgePieces$Throne.fillWithBlocks:(Lnet/minecraft/world/World;Lnet/minecraft/world/gen/structure/StructureBoundingBox;IIIIIILnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/state/IBlockState;Z)V
            //    54: aload_0        
            //    55: aload_1        
            //    56: aload_3        
            //    57: iconst_1       
            //    58: iconst_2       
            //    59: iconst_1       
            //    60: iconst_5       
            //    61: iconst_2       
            //    62: bipush          7
            //    64: getstatic       net/minecraft/init/Blocks.nether_brick:Lnet/minecraft/block/Block;
            //    67: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
            //    70: getstatic       net/minecraft/init/Blocks.nether_brick:Lnet/minecraft/block/Block;
            //    73: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
            //    76: iconst_0       
            //    77: invokevirtual   net/minecraft/world/gen/structure/StructureNetherBridgePieces$Throne.fillWithBlocks:(Lnet/minecraft/world/World;Lnet/minecraft/world/gen/structure/StructureBoundingBox;IIIIIILnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/state/IBlockState;Z)V
            //    80: aload_0        
            //    81: aload_1        
            //    82: aload_3        
            //    83: iconst_1       
            //    84: iconst_3       
            //    85: iconst_2       
            //    86: iconst_5       
            //    87: iconst_3       
            //    88: bipush          7
            //    90: getstatic       net/minecraft/init/Blocks.nether_brick:Lnet/minecraft/block/Block;
            //    93: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
            //    96: getstatic       net/minecraft/init/Blocks.nether_brick:Lnet/minecraft/block/Block;
            //    99: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
            //   102: iconst_0       
            //   103: invokevirtual   net/minecraft/world/gen/structure/StructureNetherBridgePieces$Throne.fillWithBlocks:(Lnet/minecraft/world/World;Lnet/minecraft/world/gen/structure/StructureBoundingBox;IIIIIILnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/state/IBlockState;Z)V
            //   106: aload_0        
            //   107: aload_1        
            //   108: aload_3        
            //   109: iconst_1       
            //   110: iconst_4       
            //   111: iconst_3       
            //   112: iconst_5       
            //   113: iconst_4       
            //   114: bipush          7
            //   116: getstatic       net/minecraft/init/Blocks.nether_brick:Lnet/minecraft/block/Block;
            //   119: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
            //   122: getstatic       net/minecraft/init/Blocks.nether_brick:Lnet/minecraft/block/Block;
            //   125: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
            //   128: iconst_0       
            //   129: invokevirtual   net/minecraft/world/gen/structure/StructureNetherBridgePieces$Throne.fillWithBlocks:(Lnet/minecraft/world/World;Lnet/minecraft/world/gen/structure/StructureBoundingBox;IIIIIILnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/state/IBlockState;Z)V
            //   132: aload_0        
            //   133: aload_1        
            //   134: aload_3        
            //   135: iconst_1       
            //   136: iconst_2       
            //   137: iconst_0       
            //   138: iconst_1       
            //   139: iconst_4       
            //   140: iconst_2       
            //   141: getstatic       net/minecraft/init/Blocks.nether_brick:Lnet/minecraft/block/Block;
            //   144: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
            //   147: getstatic       net/minecraft/init/Blocks.nether_brick:Lnet/minecraft/block/Block;
            //   150: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
            //   153: iconst_0       
            //   154: invokevirtual   net/minecraft/world/gen/structure/StructureNetherBridgePieces$Throne.fillWithBlocks:(Lnet/minecraft/world/World;Lnet/minecraft/world/gen/structure/StructureBoundingBox;IIIIIILnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/state/IBlockState;Z)V
            //   157: aload_0        
            //   158: aload_1        
            //   159: aload_3        
            //   160: iconst_5       
            //   161: iconst_2       
            //   162: iconst_0       
            //   163: iconst_5       
            //   164: iconst_4       
            //   165: iconst_2       
            //   166: getstatic       net/minecraft/init/Blocks.nether_brick:Lnet/minecraft/block/Block;
            //   169: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
            //   172: getstatic       net/minecraft/init/Blocks.nether_brick:Lnet/minecraft/block/Block;
            //   175: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
            //   178: iconst_0       
            //   179: invokevirtual   net/minecraft/world/gen/structure/StructureNetherBridgePieces$Throne.fillWithBlocks:(Lnet/minecraft/world/World;Lnet/minecraft/world/gen/structure/StructureBoundingBox;IIIIIILnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/state/IBlockState;Z)V
            //   182: aload_0        
            //   183: aload_1        
            //   184: aload_3        
            //   185: iconst_1       
            //   186: iconst_5       
            //   187: iconst_2       
            //   188: iconst_1       
            //   189: iconst_5       
            //   190: iconst_3       
            //   191: getstatic       net/minecraft/init/Blocks.nether_brick:Lnet/minecraft/block/Block;
            //   194: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
            //   197: getstatic       net/minecraft/init/Blocks.nether_brick:Lnet/minecraft/block/Block;
            //   200: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
            //   203: iconst_0       
            //   204: invokevirtual   net/minecraft/world/gen/structure/StructureNetherBridgePieces$Throne.fillWithBlocks:(Lnet/minecraft/world/World;Lnet/minecraft/world/gen/structure/StructureBoundingBox;IIIIIILnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/state/IBlockState;Z)V
            //   207: aload_0        
            //   208: aload_1        
            //   209: aload_3        
            //   210: iconst_5       
            //   211: iconst_5       
            //   212: iconst_2       
            //   213: iconst_5       
            //   214: iconst_5       
            //   215: iconst_3       
            //   216: getstatic       net/minecraft/init/Blocks.nether_brick:Lnet/minecraft/block/Block;
            //   219: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
            //   222: getstatic       net/minecraft/init/Blocks.nether_brick:Lnet/minecraft/block/Block;
            //   225: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
            //   228: iconst_0       
            //   229: invokevirtual   net/minecraft/world/gen/structure/StructureNetherBridgePieces$Throne.fillWithBlocks:(Lnet/minecraft/world/World;Lnet/minecraft/world/gen/structure/StructureBoundingBox;IIIIIILnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/state/IBlockState;Z)V
            //   232: aload_0        
            //   233: aload_1        
            //   234: aload_3        
            //   235: iconst_0       
            //   236: iconst_5       
            //   237: iconst_3       
            //   238: iconst_0       
            //   239: iconst_5       
            //   240: bipush          8
            //   242: getstatic       net/minecraft/init/Blocks.nether_brick:Lnet/minecraft/block/Block;
            //   245: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
            //   248: getstatic       net/minecraft/init/Blocks.nether_brick:Lnet/minecraft/block/Block;
            //   251: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
            //   254: iconst_0       
            //   255: invokevirtual   net/minecraft/world/gen/structure/StructureNetherBridgePieces$Throne.fillWithBlocks:(Lnet/minecraft/world/World;Lnet/minecraft/world/gen/structure/StructureBoundingBox;IIIIIILnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/state/IBlockState;Z)V
            //   258: aload_0        
            //   259: aload_1        
            //   260: aload_3        
            //   261: bipush          6
            //   263: iconst_5       
            //   264: iconst_3       
            //   265: bipush          6
            //   267: iconst_5       
            //   268: bipush          8
            //   270: getstatic       net/minecraft/init/Blocks.nether_brick:Lnet/minecraft/block/Block;
            //   273: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
            //   276: getstatic       net/minecraft/init/Blocks.nether_brick:Lnet/minecraft/block/Block;
            //   279: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
            //   282: iconst_0       
            //   283: invokevirtual   net/minecraft/world/gen/structure/StructureNetherBridgePieces$Throne.fillWithBlocks:(Lnet/minecraft/world/World;Lnet/minecraft/world/gen/structure/StructureBoundingBox;IIIIIILnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/state/IBlockState;Z)V
            //   286: aload_0        
            //   287: aload_1        
            //   288: aload_3        
            //   289: iconst_1       
            //   290: iconst_5       
            //   291: bipush          8
            //   293: iconst_5       
            //   294: iconst_5       
            //   295: bipush          8
            //   297: getstatic       net/minecraft/init/Blocks.nether_brick:Lnet/minecraft/block/Block;
            //   300: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
            //   303: getstatic       net/minecraft/init/Blocks.nether_brick:Lnet/minecraft/block/Block;
            //   306: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
            //   309: iconst_0       
            //   310: invokevirtual   net/minecraft/world/gen/structure/StructureNetherBridgePieces$Throne.fillWithBlocks:(Lnet/minecraft/world/World;Lnet/minecraft/world/gen/structure/StructureBoundingBox;IIIIIILnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/state/IBlockState;Z)V
            //   313: aload_0        
            //   314: aload_1        
            //   315: getstatic       net/minecraft/init/Blocks.nether_brick_fence:Lnet/minecraft/block/Block;
            //   318: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
            //   321: iconst_1       
            //   322: bipush          6
            //   324: iconst_3       
            //   325: aload_3        
            //   326: invokevirtual   net/minecraft/world/gen/structure/StructureNetherBridgePieces$Throne.setBlockState:(Lnet/minecraft/world/World;Lnet/minecraft/block/state/IBlockState;IIILnet/minecraft/world/gen/structure/StructureBoundingBox;)V
            //   329: aload_0        
            //   330: aload_1        
            //   331: getstatic       net/minecraft/init/Blocks.nether_brick_fence:Lnet/minecraft/block/Block;
            //   334: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
            //   337: iconst_5       
            //   338: bipush          6
            //   340: iconst_3       
            //   341: aload_3        
            //   342: invokevirtual   net/minecraft/world/gen/structure/StructureNetherBridgePieces$Throne.setBlockState:(Lnet/minecraft/world/World;Lnet/minecraft/block/state/IBlockState;IIILnet/minecraft/world/gen/structure/StructureBoundingBox;)V
            //   345: aload_0        
            //   346: aload_1        
            //   347: aload_3        
            //   348: iconst_0       
            //   349: bipush          6
            //   351: iconst_3       
            //   352: iconst_0       
            //   353: bipush          6
            //   355: bipush          8
            //   357: getstatic       net/minecraft/init/Blocks.nether_brick_fence:Lnet/minecraft/block/Block;
            //   360: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
            //   363: getstatic       net/minecraft/init/Blocks.nether_brick_fence:Lnet/minecraft/block/Block;
            //   366: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
            //   369: iconst_0       
            //   370: invokevirtual   net/minecraft/world/gen/structure/StructureNetherBridgePieces$Throne.fillWithBlocks:(Lnet/minecraft/world/World;Lnet/minecraft/world/gen/structure/StructureBoundingBox;IIIIIILnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/state/IBlockState;Z)V
            //   373: aload_0        
            //   374: aload_1        
            //   375: aload_3        
            //   376: bipush          6
            //   378: bipush          6
            //   380: iconst_3       
            //   381: bipush          6
            //   383: bipush          6
            //   385: bipush          8
            //   387: getstatic       net/minecraft/init/Blocks.nether_brick_fence:Lnet/minecraft/block/Block;
            //   390: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
            //   393: getstatic       net/minecraft/init/Blocks.nether_brick_fence:Lnet/minecraft/block/Block;
            //   396: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
            //   399: iconst_0       
            //   400: invokevirtual   net/minecraft/world/gen/structure/StructureNetherBridgePieces$Throne.fillWithBlocks:(Lnet/minecraft/world/World;Lnet/minecraft/world/gen/structure/StructureBoundingBox;IIIIIILnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/state/IBlockState;Z)V
            //   403: aload_0        
            //   404: aload_1        
            //   405: aload_3        
            //   406: iconst_1       
            //   407: bipush          6
            //   409: bipush          8
            //   411: iconst_5       
            //   412: bipush          7
            //   414: bipush          8
            //   416: getstatic       net/minecraft/init/Blocks.nether_brick_fence:Lnet/minecraft/block/Block;
            //   419: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
            //   422: getstatic       net/minecraft/init/Blocks.nether_brick_fence:Lnet/minecraft/block/Block;
            //   425: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
            //   428: iconst_0       
            //   429: invokevirtual   net/minecraft/world/gen/structure/StructureNetherBridgePieces$Throne.fillWithBlocks:(Lnet/minecraft/world/World;Lnet/minecraft/world/gen/structure/StructureBoundingBox;IIIIIILnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/state/IBlockState;Z)V
            //   432: aload_0        
            //   433: aload_1        
            //   434: aload_3        
            //   435: iconst_2       
            //   436: bipush          8
            //   438: bipush          8
            //   440: iconst_4       
            //   441: bipush          8
            //   443: bipush          8
            //   445: getstatic       net/minecraft/init/Blocks.nether_brick_fence:Lnet/minecraft/block/Block;
            //   448: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
            //   451: getstatic       net/minecraft/init/Blocks.nether_brick_fence:Lnet/minecraft/block/Block;
            //   454: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
            //   457: iconst_0       
            //   458: invokevirtual   net/minecraft/world/gen/structure/StructureNetherBridgePieces$Throne.fillWithBlocks:(Lnet/minecraft/world/World;Lnet/minecraft/world/gen/structure/StructureBoundingBox;IIIIIILnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/state/IBlockState;Z)V
            //   461: aload_0        
            //   462: getfield        net/minecraft/world/gen/structure/StructureNetherBridgePieces$Throne.hasSpawner:Z
            //   465: ifne            551
            //   468: new             Lnet/minecraft/util/BlockPos;
            //   471: dup            
            //   472: aload_0        
            //   473: iconst_3       
            //   474: iconst_5       
            //   475: invokevirtual   net/minecraft/world/gen/structure/StructureNetherBridgePieces$Throne.getXWithOffset:(II)I
            //   478: aload_0        
            //   479: iconst_5       
            //   480: invokevirtual   net/minecraft/world/gen/structure/StructureNetherBridgePieces$Throne.getYWithOffset:(I)I
            //   483: aload_0        
            //   484: iconst_3       
            //   485: iconst_5       
            //   486: invokevirtual   net/minecraft/world/gen/structure/StructureNetherBridgePieces$Throne.getZWithOffset:(II)I
            //   489: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
            //   492: astore          4
            //   494: aload_3        
            //   495: aload           4
            //   497: invokevirtual   net/minecraft/world/gen/structure/StructureBoundingBox.isVecInside:(Lnet/minecraft/util/Vec3i;)Z
            //   500: ifeq            551
            //   503: aload_0        
            //   504: iconst_1       
            //   505: putfield        net/minecraft/world/gen/structure/StructureNetherBridgePieces$Throne.hasSpawner:Z
            //   508: aload_1        
            //   509: aload           4
            //   511: getstatic       net/minecraft/init/Blocks.mob_spawner:Lnet/minecraft/block/Block;
            //   514: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
            //   517: iconst_2       
            //   518: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z
            //   521: pop            
            //   522: aload_1        
            //   523: aload           4
            //   525: invokevirtual   net/minecraft/world/World.getTileEntity:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/tileentity/TileEntity;
            //   528: astore          5
            //   530: aload           5
            //   532: instanceof      Lnet/minecraft/tileentity/TileEntityMobSpawner;
            //   535: ifeq            551
            //   538: aload           5
            //   540: checkcast       Lnet/minecraft/tileentity/TileEntityMobSpawner;
            //   543: invokevirtual   net/minecraft/tileentity/TileEntityMobSpawner.getSpawnerBaseLogic:()Lnet/minecraft/tileentity/MobSpawnerBaseLogic;
            //   546: ldc             "Blaze"
            //   548: invokevirtual   net/minecraft/tileentity/MobSpawnerBaseLogic.setEntityName:(Ljava/lang/String;)V
            //   551: aload_0        
            //   552: aload_1        
            //   553: getstatic       net/minecraft/init/Blocks.nether_brick:Lnet/minecraft/block/Block;
            //   556: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
            //   559: iconst_0       
            //   560: iconst_m1      
            //   561: iconst_0       
            //   562: aload_3        
            //   563: invokevirtual   net/minecraft/world/gen/structure/StructureNetherBridgePieces$Throne.replaceAirAndLiquidDownwards:(Lnet/minecraft/world/World;Lnet/minecraft/block/state/IBlockState;IIILnet/minecraft/world/gen/structure/StructureBoundingBox;)V
            //   566: iinc            5, 1
            //   569: goto            551
            //   572: iinc            4, 1
            //   575: goto            551
            //   578: iconst_1       
            //   579: ireturn        
            // 
            // The error that occurred was:
            // 
            // java.lang.NullPointerException
            //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2895)
            //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
            //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:576)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
            //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
            //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
            //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
            //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
            //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
            //     at java.lang.Thread.run(Unknown Source)
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
        
        public static Throne func_175874_a(final List list, final Random random, final int n, final int n2, final int n3, final int n4, final EnumFacing enumFacing) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -2, 0, 0, 7, 8, 9, enumFacing);
            return (Piece.isAboveGround(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) ? new Throne(n4, random, componentToAddBoundingBox, enumFacing) : null;
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setBoolean("Mob", this.hasSpawner);
        }
        
        public Throne(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.hasSpawner = nbtTagCompound.getBoolean("Mob");
        }
        
        public Throne() {
        }
    }
    
    public static class Corridor4 extends Piece
    {
        public Corridor4(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
        
        public Corridor4() {
        }
        
        public static Corridor4 func_175880_a(final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -3, 0, 0, 9, 7, 9, enumFacing);
            return (Piece.isAboveGround(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) ? new Corridor4(n4, random, componentToAddBoundingBox, enumFacing) : null;
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 8, 1, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 2, 0, 8, 5, 8, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 6, 0, 8, 6, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 2, 0, 2, 5, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 6, 2, 0, 8, 5, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 3, 0, 1, 4, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 7, 3, 0, 7, 4, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 2, 4, 8, 2, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 1, 4, 2, 2, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 6, 1, 4, 7, 2, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 3, 8, 8, 3, 8, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 3, 6, 0, 3, 7, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 8, 3, 6, 8, 3, 7, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 3, 4, 0, 5, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 8, 3, 4, 8, 5, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 3, 5, 2, 5, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 6, 3, 5, 7, 5, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 4, 5, 1, 5, 5, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 7, 4, 5, 7, 5, 5, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            while (true) {
                this.replaceAirAndLiquidDownwards(world, Blocks.nether_brick.getDefaultState(), 0, -1, 0, structureBoundingBox);
                int n = 0;
                ++n;
            }
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List list, final Random random) {
            if (this.coordBaseMode == EnumFacing.WEST || this.coordBaseMode == EnumFacing.NORTH) {}
            this.getNextComponentX((Start)structureComponent, list, random, 0, 5, random.nextInt(8) > 0);
            this.getNextComponentZ((Start)structureComponent, list, random, 0, 5, random.nextInt(8) > 0);
        }
    }
    
    public static class Stairs extends Piece
    {
        public static Stairs func_175872_a(final List list, final Random random, final int n, final int n2, final int n3, final int n4, final EnumFacing enumFacing) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -2, 0, 0, 7, 11, 7, enumFacing);
            return (Piece.isAboveGround(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) ? new Stairs(n4, random, componentToAddBoundingBox, enumFacing) : null;
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List list, final Random random) {
            this.getNextComponentZ((Start)structureComponent, list, random, 6, 2, false);
        }
        
        public Stairs() {
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 6, 1, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 2, 0, 6, 10, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 2, 0, 1, 8, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 5, 2, 0, 6, 8, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 2, 1, 0, 8, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 6, 2, 1, 6, 8, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 2, 6, 5, 8, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 3, 2, 0, 5, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 6, 3, 2, 6, 5, 2, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 6, 3, 4, 6, 5, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.setBlockState(world, Blocks.nether_brick.getDefaultState(), 5, 2, 5, structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, 4, 2, 5, 4, 3, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 3, 2, 5, 3, 4, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 2, 2, 5, 2, 5, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 2, 5, 1, 6, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 7, 1, 5, 7, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 6, 8, 2, 6, 8, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 2, 6, 0, 4, 8, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 2, 5, 0, 4, 5, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            while (true) {
                this.replaceAirAndLiquidDownwards(world, Blocks.nether_brick.getDefaultState(), 0, -1, 0, structureBoundingBox);
                int n = 0;
                ++n;
            }
        }
        
        public Stairs(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
    }
    
    public static class Corridor2 extends Piece
    {
        private boolean field_111020_b;
        
        public Corridor2() {
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 4, 1, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 2, 0, 4, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 2, 0, 0, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 3, 1, 0, 4, 1, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 3, 3, 0, 4, 3, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 4, 2, 0, 4, 5, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 2, 4, 4, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 3, 4, 1, 4, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 3, 3, 4, 3, 4, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            if (this.field_111020_b && structureBoundingBox.isVecInside(new BlockPos(this.getXWithOffset(1, 3), this.getYWithOffset(2), this.getZWithOffset(1, 3)))) {
                this.field_111020_b = false;
                this.generateChestContents(world, structureBoundingBox, random, 1, 2, 3, Corridor2.field_111019_a, 2 + random.nextInt(4));
            }
            this.fillWithBlocks(world, structureBoundingBox, 0, 6, 0, 4, 6, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            while (true) {
                this.replaceAirAndLiquidDownwards(world, Blocks.nether_brick.getDefaultState(), 0, -1, 0, structureBoundingBox);
                int n = 0;
                ++n;
            }
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List list, final Random random) {
            this.getNextComponentZ((Start)structureComponent, list, random, 0, 1, true);
        }
        
        public static Corridor2 func_175876_a(final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -1, 0, 0, 5, 7, 5, enumFacing);
            return (Piece.isAboveGround(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) ? new Corridor2(n4, random, componentToAddBoundingBox, enumFacing) : null;
        }
        
        public Corridor2(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            this.field_111020_b = (random.nextInt(3) == 0);
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.field_111020_b = nbtTagCompound.getBoolean("Chest");
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setBoolean("Chest", this.field_111020_b);
        }
    }
    
    public static class Straight extends Piece
    {
        public Straight(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List list, final Random random) {
            this.getNextComponentNormal((Start)structureComponent, list, random, 1, 3, false);
        }
        
        public static Straight func_175882_a(final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -1, -3, 0, 5, 10, 19, enumFacing);
            return (Piece.isAboveGround(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) ? new Straight(n4, random, componentToAddBoundingBox, enumFacing) : null;
        }
        
        public Straight() {
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            this.fillWithBlocks(world, structureBoundingBox, 0, 3, 0, 4, 4, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 5, 0, 3, 7, 18, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 5, 0, 0, 5, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 4, 5, 0, 4, 5, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 2, 0, 4, 2, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 2, 13, 4, 2, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 4, 1, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 0, 15, 4, 1, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            while (true) {
                this.replaceAirAndLiquidDownwards(world, Blocks.nether_brick.getDefaultState(), 0, -1, 0, structureBoundingBox);
                this.replaceAirAndLiquidDownwards(world, Blocks.nether_brick.getDefaultState(), 0, -1, 18, structureBoundingBox);
                int n = 0;
                ++n;
            }
        }
    }
    
    public static class Crossing2 extends Piece
    {
        public Crossing2() {
        }
        
        public Crossing2(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List list, final Random random) {
            this.getNextComponentNormal((Start)structureComponent, list, random, 1, 0, true);
            this.getNextComponentX((Start)structureComponent, list, random, 0, 1, true);
            this.getNextComponentZ((Start)structureComponent, list, random, 0, 1, true);
        }
        
        public static Crossing2 func_175878_a(final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -1, 0, 0, 5, 7, 5, enumFacing);
            return (Piece.isAboveGround(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) ? new Crossing2(n4, random, componentToAddBoundingBox, enumFacing) : null;
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 4, 1, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 2, 0, 4, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 2, 0, 0, 5, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 4, 2, 0, 4, 5, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 2, 4, 0, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 4, 2, 4, 4, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 6, 0, 4, 6, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            while (true) {
                this.replaceAirAndLiquidDownwards(world, Blocks.nether_brick.getDefaultState(), 0, -1, 0, structureBoundingBox);
                int n = 0;
                ++n;
            }
        }
    }
    
    public static class Entrance extends Piece
    {
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            this.fillWithBlocks(world, structureBoundingBox, 0, 3, 0, 12, 4, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 5, 0, 12, 13, 12, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 5, 0, 1, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 11, 5, 0, 12, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 2, 5, 11, 4, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 8, 5, 11, 10, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 5, 9, 11, 7, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 2, 5, 0, 4, 12, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 8, 5, 0, 10, 12, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 5, 9, 0, 7, 12, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 2, 11, 2, 10, 12, 10, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 5, 8, 0, 7, 8, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            while (true) {
                this.fillWithBlocks(world, structureBoundingBox, 0, 10, 0, 0, 11, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
                this.fillWithBlocks(world, structureBoundingBox, 0, 10, 12, 0, 11, 12, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
                this.fillWithBlocks(world, structureBoundingBox, 0, 10, 0, 0, 11, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
                this.fillWithBlocks(world, structureBoundingBox, 12, 10, 0, 12, 11, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
                this.setBlockState(world, Blocks.nether_brick.getDefaultState(), 0, 13, 0, structureBoundingBox);
                this.setBlockState(world, Blocks.nether_brick.getDefaultState(), 0, 13, 12, structureBoundingBox);
                this.setBlockState(world, Blocks.nether_brick.getDefaultState(), 0, 13, 0, structureBoundingBox);
                this.setBlockState(world, Blocks.nether_brick.getDefaultState(), 12, 13, 0, structureBoundingBox);
                this.setBlockState(world, Blocks.nether_brick_fence.getDefaultState(), 1, 13, 0, structureBoundingBox);
                this.setBlockState(world, Blocks.nether_brick_fence.getDefaultState(), 1, 13, 12, structureBoundingBox);
                this.setBlockState(world, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 1, structureBoundingBox);
                this.setBlockState(world, Blocks.nether_brick_fence.getDefaultState(), 12, 13, 1, structureBoundingBox);
                final int n;
                n += 2;
            }
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List list, final Random random) {
            this.getNextComponentNormal((Start)structureComponent, list, random, 5, 3, true);
        }
        
        public Entrance(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
        
        public static Entrance func_175881_a(final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -5, -3, 0, 13, 14, 13, enumFacing);
            return (Piece.isAboveGround(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) ? new Entrance(n4, random, componentToAddBoundingBox, enumFacing) : null;
        }
        
        public Entrance() {
        }
    }
    
    public static class Crossing extends Piece
    {
        public static Crossing func_175873_a(final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -2, 0, 0, 7, 9, 7, enumFacing);
            return (Piece.isAboveGround(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) ? new Crossing(n4, random, componentToAddBoundingBox, enumFacing) : null;
        }
        
        public Crossing() {
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List list, final Random random) {
            this.getNextComponentNormal((Start)structureComponent, list, random, 2, 0, false);
            this.getNextComponentX((Start)structureComponent, list, random, 0, 2, false);
            this.getNextComponentZ((Start)structureComponent, list, random, 0, 2, false);
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 6, 1, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 2, 0, 6, 7, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 2, 0, 1, 6, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 2, 6, 1, 6, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 5, 2, 0, 6, 6, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 5, 2, 6, 6, 6, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 2, 0, 0, 6, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 2, 5, 0, 6, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 6, 2, 0, 6, 6, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 6, 2, 5, 6, 6, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 2, 6, 0, 4, 6, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 2, 5, 0, 4, 5, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 2, 6, 6, 4, 6, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 2, 5, 6, 4, 5, 6, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 6, 2, 0, 6, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 5, 2, 0, 5, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 6, 6, 2, 6, 6, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 6, 5, 2, 6, 5, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            while (true) {
                this.replaceAirAndLiquidDownwards(world, Blocks.nether_brick.getDefaultState(), 0, -1, 0, structureBoundingBox);
                int n = 0;
                ++n;
            }
        }
        
        public Crossing(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
    }
}
