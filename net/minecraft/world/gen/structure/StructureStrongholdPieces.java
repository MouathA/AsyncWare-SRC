package net.minecraft.world.gen.structure;

import java.util.*;
import com.google.common.collect.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class StructureStrongholdPieces
{
    static int totalWeight;
    private static Class strongComponentType;
    private static final PieceWeight[] pieceWeightArray;
    private static List structurePieceList;
    private static final Stones strongholdStones;
    
    static {
        pieceWeightArray = new PieceWeight[] { new PieceWeight(Straight.class, 40, 0), new PieceWeight(Prison.class, 5, 5), new PieceWeight(LeftTurn.class, 20, 0), new PieceWeight(RightTurn.class, 20, 0), new PieceWeight(RoomCrossing.class, 10, 6), new PieceWeight(StairsStraight.class, 5, 5), new PieceWeight(Stairs.class, 5, 5), new PieceWeight(Crossing.class, 5, 4), new PieceWeight(ChestCorridor.class, 5, 4), new PieceWeight(Library.class, 10, 2) {
                @Override
                public boolean canSpawnMoreStructuresOfType(final int n) {
                    return super.canSpawnMoreStructuresOfType(n) && n > 4;
                }
            }, new PieceWeight(PortalRoom.class, 20, 1) {
                @Override
                public boolean canSpawnMoreStructuresOfType(final int n) {
                    return super.canSpawnMoreStructuresOfType(n) && n > 5;
                }
            } };
        strongholdStones = new Stones(null);
    }
    
    static Class access$202(final Class strongComponentType) {
        return StructureStrongholdPieces.strongComponentType = strongComponentType;
    }
    
    private static Stronghold func_175955_b(final Stairs2 p0, final List p1, final Random p2, final int p3, final int p4, final int p5, final EnumFacing p6, final int p7) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: aconst_null    
        //     4: areturn        
        //     5: getstatic       net/minecraft/world/gen/structure/StructureStrongholdPieces.strongComponentType:Ljava/lang/Class;
        //     8: ifnull          42
        //    11: getstatic       net/minecraft/world/gen/structure/StructureStrongholdPieces.strongComponentType:Ljava/lang/Class;
        //    14: aload_1        
        //    15: aload_2        
        //    16: iload_3        
        //    17: iload           4
        //    19: iload           5
        //    21: aload           6
        //    23: iload           7
        //    25: invokestatic    net/minecraft/world/gen/structure/StructureStrongholdPieces.func_175954_a:(Ljava/lang/Class;Ljava/util/List;Ljava/util/Random;IIILnet/minecraft/util/EnumFacing;I)Lnet/minecraft/world/gen/structure/StructureStrongholdPieces$Stronghold;
        //    28: astore          8
        //    30: aconst_null    
        //    31: putstatic       net/minecraft/world/gen/structure/StructureStrongholdPieces.strongComponentType:Ljava/lang/Class;
        //    34: aload           8
        //    36: ifnull          42
        //    39: aload           8
        //    41: areturn        
        //    42: iinc            8, 1
        //    45: aload_2        
        //    46: getstatic       net/minecraft/world/gen/structure/StructureStrongholdPieces.totalWeight:I
        //    49: invokevirtual   java/util/Random.nextInt:(I)I
        //    52: istore          9
        //    54: getstatic       net/minecraft/world/gen/structure/StructureStrongholdPieces.structurePieceList:Ljava/util/List;
        //    57: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //    62: astore          10
        //    64: aload           10
        //    66: invokeinterface java/util/Iterator.hasNext:()Z
        //    71: ifeq            191
        //    74: aload           10
        //    76: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    81: checkcast       Lnet/minecraft/world/gen/structure/StructureStrongholdPieces$PieceWeight;
        //    84: astore          11
        //    86: iload           9
        //    88: aload           11
        //    90: getfield        net/minecraft/world/gen/structure/StructureStrongholdPieces$PieceWeight.pieceWeight:I
        //    93: isub           
        //    94: istore          9
        //    96: iload           9
        //    98: ifge            188
        //   101: aload           11
        //   103: iload           7
        //   105: invokevirtual   net/minecraft/world/gen/structure/StructureStrongholdPieces$PieceWeight.canSpawnMoreStructuresOfType:(I)Z
        //   108: ifeq            191
        //   111: aload           11
        //   113: aload_0        
        //   114: getfield        net/minecraft/world/gen/structure/StructureStrongholdPieces$Stairs2.strongholdPieceWeight:Lnet/minecraft/world/gen/structure/StructureStrongholdPieces$PieceWeight;
        //   117: if_acmpne       123
        //   120: goto            42
        //   123: aload           11
        //   125: getfield        net/minecraft/world/gen/structure/StructureStrongholdPieces$PieceWeight.pieceClass:Ljava/lang/Class;
        //   128: aload_1        
        //   129: aload_2        
        //   130: iload_3        
        //   131: iload           4
        //   133: iload           5
        //   135: aload           6
        //   137: iload           7
        //   139: invokestatic    net/minecraft/world/gen/structure/StructureStrongholdPieces.func_175954_a:(Ljava/lang/Class;Ljava/util/List;Ljava/util/Random;IIILnet/minecraft/util/EnumFacing;I)Lnet/minecraft/world/gen/structure/StructureStrongholdPieces$Stronghold;
        //   142: astore          12
        //   144: aload           12
        //   146: ifnull          188
        //   149: aload           11
        //   151: dup            
        //   152: getfield        net/minecraft/world/gen/structure/StructureStrongholdPieces$PieceWeight.instancesSpawned:I
        //   155: iconst_1       
        //   156: iadd           
        //   157: putfield        net/minecraft/world/gen/structure/StructureStrongholdPieces$PieceWeight.instancesSpawned:I
        //   160: aload_0        
        //   161: aload           11
        //   163: putfield        net/minecraft/world/gen/structure/StructureStrongholdPieces$Stairs2.strongholdPieceWeight:Lnet/minecraft/world/gen/structure/StructureStrongholdPieces$PieceWeight;
        //   166: aload           11
        //   168: invokevirtual   net/minecraft/world/gen/structure/StructureStrongholdPieces$PieceWeight.canSpawnMoreStructures:()Z
        //   171: ifne            185
        //   174: getstatic       net/minecraft/world/gen/structure/StructureStrongholdPieces.structurePieceList:Ljava/util/List;
        //   177: aload           11
        //   179: invokeinterface java/util/List.remove:(Ljava/lang/Object;)Z
        //   184: pop            
        //   185: aload           12
        //   187: areturn        
        //   188: goto            64
        //   191: goto            42
        //   194: aload_1        
        //   195: aload_2        
        //   196: iload_3        
        //   197: iload           4
        //   199: iload           5
        //   201: aload           6
        //   203: invokestatic    net/minecraft/world/gen/structure/StructureStrongholdPieces$Corridor.func_175869_a:(Ljava/util/List;Ljava/util/Random;IIILnet/minecraft/util/EnumFacing;)Lnet/minecraft/world/gen/structure/StructureBoundingBox;
        //   206: astore          9
        //   208: aload           9
        //   210: ifnull          237
        //   213: aload           9
        //   215: getfield        net/minecraft/world/gen/structure/StructureBoundingBox.minY:I
        //   218: iconst_1       
        //   219: if_icmple       237
        //   222: new             Lnet/minecraft/world/gen/structure/StructureStrongholdPieces$Corridor;
        //   225: dup            
        //   226: iload           7
        //   228: aload_2        
        //   229: aload           9
        //   231: aload           6
        //   233: invokespecial   net/minecraft/world/gen/structure/StructureStrongholdPieces$Corridor.<init>:(ILjava/util/Random;Lnet/minecraft/world/gen/structure/StructureBoundingBox;Lnet/minecraft/util/EnumFacing;)V
        //   236: areturn        
        //   237: aconst_null    
        //   238: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static void registerStrongholdPieces() {
        MapGenStructureIO.registerStructureComponent(ChestCorridor.class, "SHCC");
        MapGenStructureIO.registerStructureComponent(Corridor.class, "SHFC");
        MapGenStructureIO.registerStructureComponent(Crossing.class, "SH5C");
        MapGenStructureIO.registerStructureComponent(LeftTurn.class, "SHLT");
        MapGenStructureIO.registerStructureComponent(Library.class, "SHLi");
        MapGenStructureIO.registerStructureComponent(PortalRoom.class, "SHPR");
        MapGenStructureIO.registerStructureComponent(Prison.class, "SHPH");
        MapGenStructureIO.registerStructureComponent(RightTurn.class, "SHRT");
        MapGenStructureIO.registerStructureComponent(RoomCrossing.class, "SHRC");
        MapGenStructureIO.registerStructureComponent(Stairs.class, "SHSD");
        MapGenStructureIO.registerStructureComponent(Stairs2.class, "SHStart");
        MapGenStructureIO.registerStructureComponent(Straight.class, "SHS");
        MapGenStructureIO.registerStructureComponent(StairsStraight.class, "SHSSD");
    }
    
    private static StructureComponent func_175953_c(final Stairs2 stairs2, final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
        if (n4 > 50) {
            return null;
        }
        if (Math.abs(n - stairs2.getBoundingBox().minX) <= 112 && Math.abs(n3 - stairs2.getBoundingBox().minZ) <= 112) {
            final Stronghold func_175955_b = func_175955_b(stairs2, list, random, n, n2, n3, enumFacing, n4 + 1);
            if (func_175955_b != null) {
                list.add(func_175955_b);
                stairs2.field_75026_c.add(func_175955_b);
            }
            return func_175955_b;
        }
        return null;
    }
    
    static Stones access$100() {
        return StructureStrongholdPieces.strongholdStones;
    }
    
    private static Stronghold func_175954_a(final Class clazz, final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
        Stronghold stronghold = null;
        if (clazz == Straight.class) {
            stronghold = Straight.func_175862_a(list, random, n, n2, n3, enumFacing, n4);
        }
        else if (clazz == Prison.class) {
            stronghold = Prison.func_175860_a(list, random, n, n2, n3, enumFacing, n4);
        }
        else if (clazz == LeftTurn.class) {
            stronghold = LeftTurn.func_175867_a(list, random, n, n2, n3, enumFacing, n4);
        }
        else if (clazz == RightTurn.class) {
            stronghold = LeftTurn.func_175867_a(list, random, n, n2, n3, enumFacing, n4);
        }
        else if (clazz == RoomCrossing.class) {
            stronghold = RoomCrossing.func_175859_a(list, random, n, n2, n3, enumFacing, n4);
        }
        else if (clazz == StairsStraight.class) {
            stronghold = StairsStraight.func_175861_a(list, random, n, n2, n3, enumFacing, n4);
        }
        else if (clazz == Stairs.class) {
            stronghold = Stairs.func_175863_a(list, random, n, n2, n3, enumFacing, n4);
        }
        else if (clazz == Crossing.class) {
            stronghold = Crossing.func_175866_a(list, random, n, n2, n3, enumFacing, n4);
        }
        else if (clazz == ChestCorridor.class) {
            stronghold = ChestCorridor.func_175868_a(list, random, n, n2, n3, enumFacing, n4);
        }
        else if (clazz == Library.class) {
            stronghold = Library.func_175864_a(list, random, n, n2, n3, enumFacing, n4);
        }
        else if (clazz == PortalRoom.class) {
            stronghold = PortalRoom.func_175865_a(list, random, n, n2, n3, enumFacing, n4);
        }
        return stronghold;
    }
    
    public static void prepareStructurePieces() {
        StructureStrongholdPieces.structurePieceList = Lists.newArrayList();
        final PieceWeight[] pieceWeightArray = StructureStrongholdPieces.pieceWeightArray;
        while (0 < pieceWeightArray.length) {
            final PieceWeight pieceWeight = pieceWeightArray[0];
            pieceWeight.instancesSpawned = 0;
            StructureStrongholdPieces.structurePieceList.add(pieceWeight);
            int n = 0;
            ++n;
        }
        StructureStrongholdPieces.strongComponentType = null;
    }
    
    static StructureComponent access$300(final Stairs2 stairs2, final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
        return func_175953_c(stairs2, list, random, n, n2, n3, enumFacing, n4);
    }
    
    abstract static class Stronghold extends StructureComponent
    {
        protected Door field_143013_d;
        
        protected void placeDoor(final World world, final Random random, final StructureBoundingBox structureBoundingBox, final Door door, final int n, final int n2, final int n3) {
            switch (door) {
                default: {
                    this.fillWithBlocks(world, structureBoundingBox, n, n2, n3, n + 3 - 1, n2 + 3 - 1, n3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
                    break;
                }
                case WOOD_DOOR: {
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), n, n2, n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), n, n2 + 1, n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), n, n2 + 2, n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), n + 1, n2 + 2, n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), n + 2, n2 + 2, n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), n + 2, n2 + 1, n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), n + 2, n2, n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.oak_door.getDefaultState(), n + 1, n2, n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.oak_door.getStateFromMeta(8), n + 1, n2 + 1, n3, structureBoundingBox);
                    break;
                }
                case GRATES: {
                    this.setBlockState(world, Blocks.air.getDefaultState(), n + 1, n2, n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.air.getDefaultState(), n + 1, n2 + 1, n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.iron_bars.getDefaultState(), n, n2, n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.iron_bars.getDefaultState(), n, n2 + 1, n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.iron_bars.getDefaultState(), n, n2 + 2, n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.iron_bars.getDefaultState(), n + 1, n2 + 2, n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.iron_bars.getDefaultState(), n + 2, n2 + 2, n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.iron_bars.getDefaultState(), n + 2, n2 + 1, n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.iron_bars.getDefaultState(), n + 2, n2, n3, structureBoundingBox);
                    break;
                }
                case IRON_DOOR: {
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), n, n2, n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), n, n2 + 1, n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), n, n2 + 2, n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), n + 1, n2 + 2, n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), n + 2, n2 + 2, n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), n + 2, n2 + 1, n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), n + 2, n2, n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.iron_door.getDefaultState(), n + 1, n2, n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.iron_door.getStateFromMeta(8), n + 1, n2 + 1, n3, structureBoundingBox);
                    this.setBlockState(world, Blocks.stone_button.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_button, 4)), n + 2, n2 + 1, n3 + 1, structureBoundingBox);
                    this.setBlockState(world, Blocks.stone_button.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_button, 3)), n + 2, n2 + 1, n3 - 1, structureBoundingBox);
                    break;
                }
            }
        }
        
        protected Door getRandomDoor(final Random random) {
            switch (random.nextInt(5)) {
                default: {
                    return Door.OPENING;
                }
                case 2: {
                    return Door.WOOD_DOOR;
                }
                case 3: {
                    return Door.GRATES;
                }
                case 4: {
                    return Door.IRON_DOOR;
                }
            }
        }
        
        protected static boolean canStrongholdGoDeeper(final StructureBoundingBox structureBoundingBox) {
            return structureBoundingBox != null && structureBoundingBox.minY > 10;
        }
        
        protected StructureComponent getNextComponentX(final Stairs2 stairs2, final List list, final Random random, final int n, final int n2) {
            if (this.coordBaseMode != null) {
                switch (StructureStrongholdPieces$3.$SwitchMap$net$minecraft$util$EnumFacing[this.coordBaseMode.ordinal()]) {
                    case 4: {
                        return StructureStrongholdPieces.access$300(stairs2, list, random, this.boundingBox.minX - 1, this.boundingBox.minY + n, this.boundingBox.minZ + n2, EnumFacing.WEST, this.getComponentType());
                    }
                    case 1: {
                        return StructureStrongholdPieces.access$300(stairs2, list, random, this.boundingBox.minX - 1, this.boundingBox.minY + n, this.boundingBox.minZ + n2, EnumFacing.WEST, this.getComponentType());
                    }
                    case 2: {
                        return StructureStrongholdPieces.access$300(stairs2, list, random, this.boundingBox.minX + n2, this.boundingBox.minY + n, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
                    }
                    case 3: {
                        return StructureStrongholdPieces.access$300(stairs2, list, random, this.boundingBox.minX + n2, this.boundingBox.minY + n, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
                    }
                }
            }
            return null;
        }
        
        protected Stronghold(final int n) {
            super(n);
            this.field_143013_d = Door.OPENING;
        }
        
        public Stronghold() {
            this.field_143013_d = Door.OPENING;
        }
        
        protected StructureComponent getNextComponentZ(final Stairs2 stairs2, final List list, final Random random, final int n, final int n2) {
            if (this.coordBaseMode != null) {
                switch (StructureStrongholdPieces$3.$SwitchMap$net$minecraft$util$EnumFacing[this.coordBaseMode.ordinal()]) {
                    case 4: {
                        return StructureStrongholdPieces.access$300(stairs2, list, random, this.boundingBox.maxX + 1, this.boundingBox.minY + n, this.boundingBox.minZ + n2, EnumFacing.EAST, this.getComponentType());
                    }
                    case 1: {
                        return StructureStrongholdPieces.access$300(stairs2, list, random, this.boundingBox.maxX + 1, this.boundingBox.minY + n, this.boundingBox.minZ + n2, EnumFacing.EAST, this.getComponentType());
                    }
                    case 2: {
                        return StructureStrongholdPieces.access$300(stairs2, list, random, this.boundingBox.minX + n2, this.boundingBox.minY + n, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
                    }
                    case 3: {
                        return StructureStrongholdPieces.access$300(stairs2, list, random, this.boundingBox.minX + n2, this.boundingBox.minY + n, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
                    }
                }
            }
            return null;
        }
        
        protected StructureComponent getNextComponentNormal(final Stairs2 stairs2, final List list, final Random random, final int n, final int n2) {
            if (this.coordBaseMode != null) {
                switch (StructureStrongholdPieces$3.$SwitchMap$net$minecraft$util$EnumFacing[this.coordBaseMode.ordinal()]) {
                    case 4: {
                        return StructureStrongholdPieces.access$300(stairs2, list, random, this.boundingBox.minX + n, this.boundingBox.minY + n2, this.boundingBox.minZ - 1, this.coordBaseMode, this.getComponentType());
                    }
                    case 1: {
                        return StructureStrongholdPieces.access$300(stairs2, list, random, this.boundingBox.minX + n, this.boundingBox.minY + n2, this.boundingBox.maxZ + 1, this.coordBaseMode, this.getComponentType());
                    }
                    case 2: {
                        return StructureStrongholdPieces.access$300(stairs2, list, random, this.boundingBox.minX - 1, this.boundingBox.minY + n2, this.boundingBox.minZ + n, this.coordBaseMode, this.getComponentType());
                    }
                    case 3: {
                        return StructureStrongholdPieces.access$300(stairs2, list, random, this.boundingBox.maxX + 1, this.boundingBox.minY + n2, this.boundingBox.minZ + n, this.coordBaseMode, this.getComponentType());
                    }
                }
            }
            return null;
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            nbtTagCompound.setString("EntryDoor", this.field_143013_d.name());
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            this.field_143013_d = Door.valueOf(nbtTagCompound.getString("EntryDoor"));
        }
        
        public enum Door
        {
            OPENING("OPENING", 0);
            
            private static final Door[] $VALUES;
            
            WOOD_DOOR("WOOD_DOOR", 1), 
            GRATES("GRATES", 2), 
            IRON_DOOR("IRON_DOOR", 3);
            
            static {
                $VALUES = new Door[] { Door.OPENING, Door.WOOD_DOOR, Door.GRATES, Door.IRON_DOOR };
            }
            
            private Door(final String s, final int n) {
            }
        }
    }
    
    public static class Stairs2 extends Stairs
    {
        public PieceWeight strongholdPieceWeight;
        public List field_75026_c;
        public PortalRoom strongholdPortalRoom;
        
        public Stairs2(final int n, final Random random, final int n2, final int n3) {
            super(0, random, n2, n3);
            this.field_75026_c = Lists.newArrayList();
        }
        
        @Override
        public BlockPos getBoundingBoxCenter() {
            return (this.strongholdPortalRoom != null) ? this.strongholdPortalRoom.getBoundingBoxCenter() : super.getBoundingBoxCenter();
        }
        
        public Stairs2() {
            this.field_75026_c = Lists.newArrayList();
        }
    }
    
    static class PieceWeight
    {
        public final int pieceWeight;
        public int instancesSpawned;
        public Class pieceClass;
        public int instancesLimit;
        
        public PieceWeight(final Class pieceClass, final int pieceWeight, final int instancesLimit) {
            this.pieceClass = pieceClass;
            this.pieceWeight = pieceWeight;
            this.instancesLimit = instancesLimit;
        }
        
        public boolean canSpawnMoreStructures() {
            return this.instancesLimit == 0 || this.instancesSpawned < this.instancesLimit;
        }
        
        public boolean canSpawnMoreStructuresOfType(final int n) {
            return this.instancesLimit == 0 || this.instancesSpawned < this.instancesLimit;
        }
    }
    
    public static class Stairs extends Stronghold
    {
        private boolean field_75024_a;
        
        public Stairs(final int n, final Random random, final int n2, final int n3) {
            super(n);
            this.field_75024_a = true;
            this.coordBaseMode = EnumFacing.Plane.HORIZONTAL.random(random);
            this.field_143013_d = Door.OPENING;
            switch (StructureStrongholdPieces$3.$SwitchMap$net$minecraft$util$EnumFacing[this.coordBaseMode.ordinal()]) {
                case 1:
                case 4: {
                    this.boundingBox = new StructureBoundingBox(n2, 64, n3, n2 + 5 - 1, 74, n3 + 5 - 1);
                    break;
                }
                default: {
                    this.boundingBox = new StructureBoundingBox(n2, 64, n3, n2 + 5 - 1, 74, n3 + 5 - 1);
                    break;
                }
            }
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
                return false;
            }
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0, 0, 0, 4, 10, 4, true, random, StructureStrongholdPieces.access$100());
            this.placeDoor(world, random, structureBoundingBox, this.field_143013_d, 1, 7, 0);
            this.placeDoor(world, random, structureBoundingBox, Door.OPENING, 1, 1, 4);
            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 2, 6, 1, structureBoundingBox);
            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 1, 5, 1, structureBoundingBox);
            this.setBlockState(world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 1, 6, 1, structureBoundingBox);
            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 1, 5, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 1, 4, 3, structureBoundingBox);
            this.setBlockState(world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 1, 5, 3, structureBoundingBox);
            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 2, 4, 3, structureBoundingBox);
            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 3, 3, 3, structureBoundingBox);
            this.setBlockState(world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 3, 4, 3, structureBoundingBox);
            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 3, 3, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 3, 2, 1, structureBoundingBox);
            this.setBlockState(world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 3, 3, 1, structureBoundingBox);
            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 2, 2, 1, structureBoundingBox);
            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 1, 1, 1, structureBoundingBox);
            this.setBlockState(world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 1, 2, 1, structureBoundingBox);
            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 1, 1, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 1, 1, 3, structureBoundingBox);
            return true;
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List list, final Random random) {
            if (this.field_75024_a) {
                StructureStrongholdPieces.access$202(Crossing.class);
            }
            this.getNextComponentNormal((Stairs2)structureComponent, list, random, 1, 1);
        }
        
        public Stairs() {
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.field_75024_a = nbtTagCompound.getBoolean("Source");
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setBoolean("Source", this.field_75024_a);
        }
        
        public Stairs(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.field_75024_a = false;
            this.coordBaseMode = coordBaseMode;
            this.field_143013_d = this.getRandomDoor(random);
            this.boundingBox = boundingBox;
        }
        
        public static Stairs func_175863_a(final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -1, -7, 0, 5, 11, 5, enumFacing);
            return (Stronghold.canStrongholdGoDeeper(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) ? new Stairs(n4, random, componentToAddBoundingBox, enumFacing) : null;
        }
    }
    
    static class Stones extends StructureComponent.BlockSelector
    {
        private Stones() {
        }
        
        @Override
        public void selectBlocks(final Random random, final int n, final int n2, final int n3, final boolean b) {
            if (b) {
                final float nextFloat = random.nextFloat();
                if (nextFloat < 0.2f) {
                    this.blockstate = Blocks.stonebrick.getStateFromMeta(BlockStoneBrick.CRACKED_META);
                }
                else if (nextFloat < 0.5f) {
                    this.blockstate = Blocks.stonebrick.getStateFromMeta(BlockStoneBrick.MOSSY_META);
                }
                else if (nextFloat < 0.55f) {
                    this.blockstate = Blocks.monster_egg.getStateFromMeta(BlockSilverfish.EnumType.STONEBRICK.getMetadata());
                }
                else {
                    this.blockstate = Blocks.stonebrick.getDefaultState();
                }
            }
            else {
                this.blockstate = Blocks.air.getDefaultState();
            }
        }
        
        Stones(final StructureStrongholdPieces$1 pieceWeight) {
            this();
        }
    }
    
    public static class Crossing extends Stronghold
    {
        private boolean field_74996_b;
        private boolean field_74995_d;
        private boolean field_74997_c;
        private boolean field_74999_h;
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.field_74996_b = nbtTagCompound.getBoolean("leftLow");
            this.field_74997_c = nbtTagCompound.getBoolean("leftHigh");
            this.field_74995_d = nbtTagCompound.getBoolean("rightLow");
            this.field_74999_h = nbtTagCompound.getBoolean("rightHigh");
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setBoolean("leftLow", this.field_74996_b);
            nbtTagCompound.setBoolean("leftHigh", this.field_74997_c);
            nbtTagCompound.setBoolean("rightLow", this.field_74995_d);
            nbtTagCompound.setBoolean("rightHigh", this.field_74999_h);
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
                return false;
            }
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0, 0, 0, 9, 8, 10, true, random, StructureStrongholdPieces.access$100());
            this.placeDoor(world, random, structureBoundingBox, this.field_143013_d, 4, 3, 0);
            if (this.field_74996_b) {
                this.fillWithBlocks(world, structureBoundingBox, 0, 3, 1, 0, 5, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            }
            if (this.field_74995_d) {
                this.fillWithBlocks(world, structureBoundingBox, 9, 3, 1, 9, 5, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            }
            if (this.field_74997_c) {
                this.fillWithBlocks(world, structureBoundingBox, 0, 5, 7, 0, 7, 9, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            }
            if (this.field_74999_h) {
                this.fillWithBlocks(world, structureBoundingBox, 9, 5, 7, 9, 7, 9, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            }
            this.fillWithBlocks(world, structureBoundingBox, 5, 1, 10, 7, 3, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 1, 2, 1, 8, 2, 6, false, random, StructureStrongholdPieces.access$100());
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 4, 1, 5, 4, 4, 9, false, random, StructureStrongholdPieces.access$100());
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 8, 1, 5, 8, 4, 9, false, random, StructureStrongholdPieces.access$100());
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 1, 4, 7, 3, 4, 9, false, random, StructureStrongholdPieces.access$100());
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 1, 3, 5, 3, 3, 6, false, random, StructureStrongholdPieces.access$100());
            this.fillWithBlocks(world, structureBoundingBox, 1, 3, 4, 3, 3, 4, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 4, 6, 3, 4, 6, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 5, 1, 7, 7, 1, 8, false, random, StructureStrongholdPieces.access$100());
            this.fillWithBlocks(world, structureBoundingBox, 5, 1, 9, 7, 1, 9, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 5, 2, 7, 7, 2, 7, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 4, 5, 7, 4, 5, 9, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 8, 5, 7, 8, 5, 9, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 5, 5, 7, 7, 5, 9, Blocks.double_stone_slab.getDefaultState(), Blocks.double_stone_slab.getDefaultState(), false);
            this.setBlockState(world, Blocks.torch.getDefaultState(), 6, 5, 6, structureBoundingBox);
            return true;
        }
        
        public Crossing(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.field_143013_d = this.getRandomDoor(random);
            this.boundingBox = boundingBox;
            this.field_74996_b = random.nextBoolean();
            this.field_74997_c = random.nextBoolean();
            this.field_74995_d = random.nextBoolean();
            this.field_74999_h = (random.nextInt(3) > 0);
        }
        
        public Crossing() {
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List list, final Random random) {
            if (this.coordBaseMode == EnumFacing.WEST || this.coordBaseMode == EnumFacing.NORTH) {}
            this.getNextComponentNormal((Stairs2)structureComponent, list, random, 5, 1);
            if (this.field_74996_b) {
                this.getNextComponentX((Stairs2)structureComponent, list, random, 3, 1);
            }
            if (this.field_74997_c) {
                this.getNextComponentX((Stairs2)structureComponent, list, random, 5, 7);
            }
            if (this.field_74995_d) {
                this.getNextComponentZ((Stairs2)structureComponent, list, random, 3, 1);
            }
            if (this.field_74999_h) {
                this.getNextComponentZ((Stairs2)structureComponent, list, random, 5, 7);
            }
        }
        
        public static Crossing func_175866_a(final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -4, -3, 0, 10, 9, 11, enumFacing);
            return (Stronghold.canStrongholdGoDeeper(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) ? new Crossing(n4, random, componentToAddBoundingBox, enumFacing) : null;
        }
    }
    
    public static class PortalRoom extends Stronghold
    {
        private boolean hasSpawner;
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List list, final Random random) {
            if (structureComponent != null) {
                ((Stairs2)structureComponent).strongholdPortalRoom = this;
            }
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setBoolean("Mob", this.hasSpawner);
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.hasSpawner = nbtTagCompound.getBoolean("Mob");
        }
        
        public PortalRoom(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0, 0, 0, 10, 7, 15, false, random, StructureStrongholdPieces.access$100());
            this.placeDoor(world, random, structureBoundingBox, Door.GRATES, 4, 1, 0);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 1, 6, 1, 1, 6, 14, false, random, StructureStrongholdPieces.access$100());
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 9, 6, 1, 9, 6, 14, false, random, StructureStrongholdPieces.access$100());
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 2, 6, 1, 8, 6, 2, false, random, StructureStrongholdPieces.access$100());
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 2, 6, 14, 8, 6, 14, false, random, StructureStrongholdPieces.access$100());
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 1, 1, 1, 2, 1, 4, false, random, StructureStrongholdPieces.access$100());
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 8, 1, 1, 9, 1, 4, false, random, StructureStrongholdPieces.access$100());
            this.fillWithBlocks(world, structureBoundingBox, 1, 1, 1, 1, 1, 3, Blocks.flowing_lava.getDefaultState(), Blocks.flowing_lava.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 9, 1, 1, 9, 1, 3, Blocks.flowing_lava.getDefaultState(), Blocks.flowing_lava.getDefaultState(), false);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 3, 1, 8, 7, 1, 12, false, random, StructureStrongholdPieces.access$100());
            this.fillWithBlocks(world, structureBoundingBox, 4, 1, 9, 6, 1, 11, Blocks.flowing_lava.getDefaultState(), Blocks.flowing_lava.getDefaultState(), false);
            while (true) {
                this.fillWithBlocks(world, structureBoundingBox, 0, 3, 2, 0, 4, 2, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), false);
                this.fillWithBlocks(world, structureBoundingBox, 10, 3, 2, 10, 4, 2, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), false);
                final int n;
                n += 2;
            }
        }
        
        public PortalRoom() {
        }
        
        public static PortalRoom func_175865_a(final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -4, -1, 0, 11, 8, 16, enumFacing);
            return (Stronghold.canStrongholdGoDeeper(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) ? new PortalRoom(n4, random, componentToAddBoundingBox, enumFacing) : null;
        }
    }
    
    public static class ChestCorridor extends Stronghold
    {
        private static final List strongholdChestContents;
        private boolean hasMadeChest;
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
                return false;
            }
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0, 0, 0, 4, 4, 6, true, random, StructureStrongholdPieces.access$100());
            this.placeDoor(world, random, structureBoundingBox, this.field_143013_d, 1, 1, 0);
            this.placeDoor(world, random, structureBoundingBox, Door.OPENING, 1, 1, 6);
            this.fillWithBlocks(world, structureBoundingBox, 3, 1, 2, 3, 1, 4, Blocks.stonebrick.getDefaultState(), Blocks.stonebrick.getDefaultState(), false);
            this.setBlockState(world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), 3, 1, 1, structureBoundingBox);
            this.setBlockState(world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), 3, 1, 5, structureBoundingBox);
            this.setBlockState(world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), 3, 2, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), 3, 2, 4, structureBoundingBox);
            while (true) {
                this.setBlockState(world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), 2, 1, 2, structureBoundingBox);
                int n = 0;
                ++n;
            }
        }
        
        static {
            strongholdChestContents = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.ender_pearl, 0, 1, 1, 10), new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5), new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15), new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_sword, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_chestplate, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_helmet, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_leggings, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_boots, 0, 1, 1, 5), new WeightedRandomChestContent(Items.golden_apple, 0, 1, 1, 1), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 1), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1) });
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.hasMadeChest = nbtTagCompound.getBoolean("Chest");
        }
        
        public static ChestCorridor func_175868_a(final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -1, -1, 0, 5, 5, 7, enumFacing);
            return (Stronghold.canStrongholdGoDeeper(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) ? new ChestCorridor(n4, random, componentToAddBoundingBox, enumFacing) : null;
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List list, final Random random) {
            this.getNextComponentNormal((Stairs2)structureComponent, list, random, 1, 1);
        }
        
        public ChestCorridor(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.field_143013_d = this.getRandomDoor(random);
            this.boundingBox = boundingBox;
        }
        
        public ChestCorridor() {
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setBoolean("Chest", this.hasMadeChest);
        }
    }
    
    public static class RoomCrossing extends Stronghold
    {
        protected int roomType;
        private static final List strongholdRoomCrossingChestContents;
        
        public RoomCrossing() {
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setInteger("Type", this.roomType);
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List list, final Random random) {
            this.getNextComponentNormal((Stairs2)structureComponent, list, random, 4, 1);
            this.getNextComponentX((Stairs2)structureComponent, list, random, 1, 4);
            this.getNextComponentZ((Stairs2)structureComponent, list, random, 1, 4);
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
                return false;
            }
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0, 0, 0, 10, 6, 10, true, random, StructureStrongholdPieces.access$100());
            this.placeDoor(world, random, structureBoundingBox, this.field_143013_d, 4, 1, 0);
            this.fillWithBlocks(world, structureBoundingBox, 4, 1, 10, 6, 3, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 1, 4, 0, 3, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 10, 1, 4, 10, 3, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            switch (this.roomType) {
                case 0: {
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 5, 1, 5, structureBoundingBox);
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 5, 2, 5, structureBoundingBox);
                    this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 5, 3, 5, structureBoundingBox);
                    this.setBlockState(world, Blocks.torch.getDefaultState(), 4, 3, 5, structureBoundingBox);
                    this.setBlockState(world, Blocks.torch.getDefaultState(), 6, 3, 5, structureBoundingBox);
                    this.setBlockState(world, Blocks.torch.getDefaultState(), 5, 3, 4, structureBoundingBox);
                    this.setBlockState(world, Blocks.torch.getDefaultState(), 5, 3, 6, structureBoundingBox);
                    this.setBlockState(world, Blocks.stone_slab.getDefaultState(), 4, 1, 4, structureBoundingBox);
                    this.setBlockState(world, Blocks.stone_slab.getDefaultState(), 4, 1, 5, structureBoundingBox);
                    this.setBlockState(world, Blocks.stone_slab.getDefaultState(), 4, 1, 6, structureBoundingBox);
                    this.setBlockState(world, Blocks.stone_slab.getDefaultState(), 6, 1, 4, structureBoundingBox);
                    this.setBlockState(world, Blocks.stone_slab.getDefaultState(), 6, 1, 5, structureBoundingBox);
                    this.setBlockState(world, Blocks.stone_slab.getDefaultState(), 6, 1, 6, structureBoundingBox);
                    this.setBlockState(world, Blocks.stone_slab.getDefaultState(), 5, 1, 4, structureBoundingBox);
                    this.setBlockState(world, Blocks.stone_slab.getDefaultState(), 5, 1, 6, structureBoundingBox);
                    break;
                }
                case 1: {
                    while (true) {
                        this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 3, 1, 5, structureBoundingBox);
                        this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 7, 1, 5, structureBoundingBox);
                        this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 5, 1, 3, structureBoundingBox);
                        this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 5, 1, 7, structureBoundingBox);
                        int n = 0;
                        ++n;
                    }
                    break;
                }
                case 2: {
                    while (true) {
                        this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 1, 3, 2, structureBoundingBox);
                        this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 9, 3, 2, structureBoundingBox);
                        int n = 0;
                        ++n;
                    }
                    break;
                }
            }
            return true;
        }
        
        static {
            strongholdRoomCrossingChestContents = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5), new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5), new WeightedRandomChestContent(Items.coal, 0, 3, 8, 10), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15), new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 1) });
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.roomType = nbtTagCompound.getInteger("Type");
        }
        
        public RoomCrossing(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.field_143013_d = this.getRandomDoor(random);
            this.boundingBox = boundingBox;
            this.roomType = random.nextInt(5);
        }
        
        public static RoomCrossing func_175859_a(final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -4, -1, 0, 11, 7, 11, enumFacing);
            return (Stronghold.canStrongholdGoDeeper(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) ? new RoomCrossing(n4, random, componentToAddBoundingBox, enumFacing) : null;
        }
    }
    
    public static class StairsStraight extends Stronghold
    {
        public static StairsStraight func_175861_a(final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -1, -7, 0, 5, 11, 8, enumFacing);
            return (Stronghold.canStrongholdGoDeeper(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) ? new StairsStraight(n4, random, componentToAddBoundingBox, enumFacing) : null;
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List list, final Random random) {
            this.getNextComponentNormal((Stairs2)structureComponent, list, random, 1, 1);
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
                return false;
            }
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0, 0, 0, 4, 10, 7, true, random, StructureStrongholdPieces.access$100());
            this.placeDoor(world, random, structureBoundingBox, this.field_143013_d, 1, 7, 0);
            this.placeDoor(world, random, structureBoundingBox, Door.OPENING, 1, 1, 7);
            final int metadataWithOffset = this.getMetadataWithOffset(Blocks.stone_stairs, 2);
            while (true) {
                this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(metadataWithOffset), 1, 6, 1, structureBoundingBox);
                this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(metadataWithOffset), 2, 6, 1, structureBoundingBox);
                this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(metadataWithOffset), 3, 6, 1, structureBoundingBox);
                this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 1, 5, 1, structureBoundingBox);
                this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 2, 5, 1, structureBoundingBox);
                this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 3, 5, 1, structureBoundingBox);
                int n = 0;
                ++n;
            }
        }
        
        public StairsStraight(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.field_143013_d = this.getRandomDoor(random);
            this.boundingBox = boundingBox;
        }
        
        public StairsStraight() {
        }
    }
    
    public static class Library extends Stronghold
    {
        private static final List strongholdLibraryChestContents;
        private boolean isLargeRoom;
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.isLargeRoom = nbtTagCompound.getBoolean("Tall");
        }
        
        public static Library func_175864_a(final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            StructureBoundingBox structureBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -4, -1, 0, 14, 11, 15, enumFacing);
            if (!Stronghold.canStrongholdGoDeeper(structureBoundingBox) || StructureComponent.findIntersecting(list, structureBoundingBox) != null) {
                structureBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -4, -1, 0, 14, 6, 15, enumFacing);
                if (!Stronghold.canStrongholdGoDeeper(structureBoundingBox) || StructureComponent.findIntersecting(list, structureBoundingBox) != null) {
                    return null;
                }
            }
            return new Library(n4, random, structureBoundingBox, enumFacing);
        }
        
        public Library() {
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
                return false;
            }
            if (!this.isLargeRoom) {}
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0, 0, 0, 13, 5, 14, true, random, StructureStrongholdPieces.access$100());
            this.placeDoor(world, random, structureBoundingBox, this.field_143013_d, 4, 1, 0);
            this.func_175805_a(world, structureBoundingBox, random, 0.07f, 2, 1, 1, 11, 4, 13, Blocks.web.getDefaultState(), Blocks.web.getDefaultState(), false);
            while (true) {
                this.fillWithBlocks(world, structureBoundingBox, 1, 1, 3, 1, 4, 3, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
                this.fillWithBlocks(world, structureBoundingBox, 12, 1, 3, 12, 4, 3, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
                if (this.isLargeRoom) {
                    this.fillWithBlocks(world, structureBoundingBox, 1, 6, 3, 1, 9, 3, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
                    this.fillWithBlocks(world, structureBoundingBox, 12, 6, 3, 12, 9, 3, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
                }
                int n = 0;
                ++n;
            }
        }
        
        public Library(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.field_143013_d = this.getRandomDoor(random);
            this.boundingBox = boundingBox;
            this.isLargeRoom = (boundingBox.getYSize() > 6);
        }
        
        static {
            strongholdLibraryChestContents = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.book, 0, 1, 3, 20), new WeightedRandomChestContent(Items.paper, 0, 2, 7, 20), new WeightedRandomChestContent(Items.map, 0, 1, 1, 1), new WeightedRandomChestContent(Items.compass, 0, 1, 1, 1) });
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setBoolean("Tall", this.isLargeRoom);
        }
    }
    
    public static class Corridor extends Stronghold
    {
        private int field_74993_a;
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
                return false;
            }
            if (0 >= this.field_74993_a) {
                return true;
            }
            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 0, 0, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 1, 0, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 2, 0, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 3, 0, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 4, 0, 0, structureBoundingBox);
            while (true) {
                this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 0, 1, 0, structureBoundingBox);
                this.setBlockState(world, Blocks.air.getDefaultState(), 1, 1, 0, structureBoundingBox);
                this.setBlockState(world, Blocks.air.getDefaultState(), 2, 1, 0, structureBoundingBox);
                this.setBlockState(world, Blocks.air.getDefaultState(), 3, 1, 0, structureBoundingBox);
                this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 4, 1, 0, structureBoundingBox);
                int n = 0;
                ++n;
            }
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.field_74993_a = nbtTagCompound.getInteger("Steps");
        }
        
        public Corridor(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            this.field_74993_a = ((coordBaseMode != EnumFacing.NORTH && coordBaseMode != EnumFacing.SOUTH) ? boundingBox.getXSize() : boundingBox.getZSize());
        }
        
        public static StructureBoundingBox func_175869_a(final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -1, -1, 0, 5, 5, 4, enumFacing);
            final StructureComponent intersecting = StructureComponent.findIntersecting(list, componentToAddBoundingBox);
            if (intersecting == null) {
                return null;
            }
            if (intersecting.getBoundingBox().minY == componentToAddBoundingBox.minY) {
                while (intersecting.getBoundingBox().intersectsWith(StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -1, -1, 0, 5, 5, 2, enumFacing))) {
                    int n4 = 0;
                    --n4;
                }
                return StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -1, -1, 0, 5, 5, 3, enumFacing);
            }
            return null;
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setInteger("Steps", this.field_74993_a);
        }
        
        public Corridor() {
        }
    }
    
    public static class Prison extends Stronghold
    {
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List list, final Random random) {
            this.getNextComponentNormal((Stairs2)structureComponent, list, random, 1, 1);
        }
        
        public Prison() {
        }
        
        public static Prison func_175860_a(final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -1, -1, 0, 9, 5, 11, enumFacing);
            return (Stronghold.canStrongholdGoDeeper(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) ? new Prison(n4, random, componentToAddBoundingBox, enumFacing) : null;
        }
        
        public Prison(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.field_143013_d = this.getRandomDoor(random);
            this.boundingBox = boundingBox;
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
                return false;
            }
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0, 0, 0, 8, 4, 10, true, random, StructureStrongholdPieces.access$100());
            this.placeDoor(world, random, structureBoundingBox, this.field_143013_d, 1, 1, 0);
            this.fillWithBlocks(world, structureBoundingBox, 1, 1, 10, 3, 3, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 4, 1, 1, 4, 3, 1, false, random, StructureStrongholdPieces.access$100());
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 4, 1, 3, 4, 3, 3, false, random, StructureStrongholdPieces.access$100());
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 4, 1, 7, 4, 3, 7, false, random, StructureStrongholdPieces.access$100());
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 4, 1, 9, 4, 3, 9, false, random, StructureStrongholdPieces.access$100());
            this.fillWithBlocks(world, structureBoundingBox, 4, 1, 4, 4, 3, 6, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 5, 1, 5, 7, 3, 5, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), false);
            this.setBlockState(world, Blocks.iron_bars.getDefaultState(), 4, 3, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.iron_bars.getDefaultState(), 4, 3, 8, structureBoundingBox);
            this.setBlockState(world, Blocks.iron_door.getStateFromMeta(this.getMetadataWithOffset(Blocks.iron_door, 3)), 4, 1, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.iron_door.getStateFromMeta(this.getMetadataWithOffset(Blocks.iron_door, 3) + 8), 4, 2, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.iron_door.getStateFromMeta(this.getMetadataWithOffset(Blocks.iron_door, 3)), 4, 1, 8, structureBoundingBox);
            this.setBlockState(world, Blocks.iron_door.getStateFromMeta(this.getMetadataWithOffset(Blocks.iron_door, 3) + 8), 4, 2, 8, structureBoundingBox);
            return true;
        }
    }
    
    public static class Straight extends Stronghold
    {
        private boolean expandsZ;
        private boolean expandsX;
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List list, final Random random) {
            this.getNextComponentNormal((Stairs2)structureComponent, list, random, 1, 1);
            if (this.expandsX) {
                this.getNextComponentX((Stairs2)structureComponent, list, random, 1, 2);
            }
            if (this.expandsZ) {
                this.getNextComponentZ((Stairs2)structureComponent, list, random, 1, 2);
            }
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
                return false;
            }
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0, 0, 0, 4, 4, 6, true, random, StructureStrongholdPieces.access$100());
            this.placeDoor(world, random, structureBoundingBox, this.field_143013_d, 1, 1, 0);
            this.placeDoor(world, random, structureBoundingBox, Door.OPENING, 1, 1, 6);
            this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.1f, 1, 2, 1, Blocks.torch.getDefaultState());
            this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.1f, 3, 2, 1, Blocks.torch.getDefaultState());
            this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.1f, 1, 2, 5, Blocks.torch.getDefaultState());
            this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.1f, 3, 2, 5, Blocks.torch.getDefaultState());
            if (this.expandsX) {
                this.fillWithBlocks(world, structureBoundingBox, 0, 1, 2, 0, 3, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            }
            if (this.expandsZ) {
                this.fillWithBlocks(world, structureBoundingBox, 4, 1, 2, 4, 3, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            }
            return true;
        }
        
        public Straight(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.field_143013_d = this.getRandomDoor(random);
            this.boundingBox = boundingBox;
            this.expandsX = (random.nextInt(2) == 0);
            this.expandsZ = (random.nextInt(2) == 0);
        }
        
        public Straight() {
        }
        
        public static Straight func_175862_a(final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -1, -1, 0, 5, 5, 7, enumFacing);
            return (Stronghold.canStrongholdGoDeeper(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) ? new Straight(n4, random, componentToAddBoundingBox, enumFacing) : null;
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setBoolean("Left", this.expandsX);
            nbtTagCompound.setBoolean("Right", this.expandsZ);
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.expandsX = nbtTagCompound.getBoolean("Left");
            this.expandsZ = nbtTagCompound.getBoolean("Right");
        }
    }
    
    public static class RightTurn extends LeftTurn
    {
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List list, final Random random) {
            if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.EAST) {
                this.getNextComponentX((Stairs2)structureComponent, list, random, 1, 1);
            }
            else {
                this.getNextComponentZ((Stairs2)structureComponent, list, random, 1, 1);
            }
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
                return false;
            }
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0, 0, 0, 4, 4, 4, true, random, StructureStrongholdPieces.access$100());
            this.placeDoor(world, random, structureBoundingBox, this.field_143013_d, 1, 1, 0);
            if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.EAST) {
                this.fillWithBlocks(world, structureBoundingBox, 0, 1, 1, 0, 3, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            }
            else {
                this.fillWithBlocks(world, structureBoundingBox, 4, 1, 1, 4, 3, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            }
            return true;
        }
    }
    
    public static class LeftTurn extends Stronghold
    {
        public LeftTurn() {
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
                return false;
            }
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0, 0, 0, 4, 4, 4, true, random, StructureStrongholdPieces.access$100());
            this.placeDoor(world, random, structureBoundingBox, this.field_143013_d, 1, 1, 0);
            if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.EAST) {
                this.fillWithBlocks(world, structureBoundingBox, 4, 1, 1, 4, 3, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            }
            else {
                this.fillWithBlocks(world, structureBoundingBox, 0, 1, 1, 0, 3, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            }
            return true;
        }
        
        public static LeftTurn func_175867_a(final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -1, -1, 0, 5, 5, 5, enumFacing);
            return (Stronghold.canStrongholdGoDeeper(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) ? new LeftTurn(n4, random, componentToAddBoundingBox, enumFacing) : null;
        }
        
        public LeftTurn(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.field_143013_d = this.getRandomDoor(random);
            this.boundingBox = boundingBox;
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List list, final Random random) {
            if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.EAST) {
                this.getNextComponentZ((Stairs2)structureComponent, list, random, 1, 1);
            }
            else {
                this.getNextComponentX((Stairs2)structureComponent, list, random, 1, 1);
            }
        }
    }
}
