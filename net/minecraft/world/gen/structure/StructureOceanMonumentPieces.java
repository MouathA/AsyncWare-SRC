package net.minecraft.world.gen.structure;

import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import com.google.common.collect.*;
import java.util.*;

public class StructureOceanMonumentPieces
{
    public static void registerOceanMonumentPieces() {
        MapGenStructureIO.registerStructureComponent(MonumentBuilding.class, "OMB");
        MapGenStructureIO.registerStructureComponent(MonumentCoreRoom.class, "OMCR");
        MapGenStructureIO.registerStructureComponent(DoubleXRoom.class, "OMDXR");
        MapGenStructureIO.registerStructureComponent(DoubleXYRoom.class, "OMDXYR");
        MapGenStructureIO.registerStructureComponent(DoubleYRoom.class, "OMDYR");
        MapGenStructureIO.registerStructureComponent(DoubleYZRoom.class, "OMDYZR");
        MapGenStructureIO.registerStructureComponent(DoubleZRoom.class, "OMDZR");
        MapGenStructureIO.registerStructureComponent(EntryRoom.class, "OMEntry");
        MapGenStructureIO.registerStructureComponent(Penthouse.class, "OMPenthouse");
        MapGenStructureIO.registerStructureComponent(SimpleRoom.class, "OMSimple");
        MapGenStructureIO.registerStructureComponent(SimpleTopRoom.class, "OMSimpleT");
    }
    
    static class XDoubleRoomFitHelper implements MonumentRoomFitHelper
    {
        @Override
        public Piece func_175968_a(final EnumFacing enumFacing, final RoomDefinition roomDefinition, final Random random) {
            roomDefinition.field_175963_d = true;
            roomDefinition.field_175965_b[EnumFacing.EAST.getIndex()].field_175963_d = true;
            return new DoubleXRoom(enumFacing, roomDefinition, random);
        }
        
        XDoubleRoomFitHelper(final StructureOceanMonumentPieces$1 object) {
            this();
        }
        
        private XDoubleRoomFitHelper() {
        }
        
        @Override
        public boolean func_175969_a(final RoomDefinition roomDefinition) {
            return roomDefinition.field_175966_c[EnumFacing.EAST.getIndex()] && !roomDefinition.field_175965_b[EnumFacing.EAST.getIndex()].field_175963_d;
        }
    }
    
    public static class DoubleXRoom extends Piece
    {
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            final RoomDefinition roomDefinition = this.field_175830_k.field_175965_b[EnumFacing.EAST.getIndex()];
            final RoomDefinition field_175830_k = this.field_175830_k;
            if (this.field_175830_k.field_175967_a / 25 > 0) {
                this.func_175821_a(world, structureBoundingBox, 8, 0, roomDefinition.field_175966_c[EnumFacing.DOWN.getIndex()]);
                this.func_175821_a(world, structureBoundingBox, 0, 0, field_175830_k.field_175966_c[EnumFacing.DOWN.getIndex()]);
            }
            if (field_175830_k.field_175965_b[EnumFacing.UP.getIndex()] == null) {
                this.func_175819_a(world, structureBoundingBox, 1, 4, 1, 7, 4, 6, DoubleXRoom.field_175828_a);
            }
            if (roomDefinition.field_175965_b[EnumFacing.UP.getIndex()] == null) {
                this.func_175819_a(world, structureBoundingBox, 8, 4, 1, 14, 4, 6, DoubleXRoom.field_175828_a);
            }
            this.fillWithBlocks(world, structureBoundingBox, 0, 3, 0, 0, 3, 7, DoubleXRoom.field_175826_b, DoubleXRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 15, 3, 0, 15, 3, 7, DoubleXRoom.field_175826_b, DoubleXRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 3, 0, 15, 3, 0, DoubleXRoom.field_175826_b, DoubleXRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 3, 7, 14, 3, 7, DoubleXRoom.field_175826_b, DoubleXRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 2, 0, 0, 2, 7, DoubleXRoom.field_175828_a, DoubleXRoom.field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 15, 2, 0, 15, 2, 7, DoubleXRoom.field_175828_a, DoubleXRoom.field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 2, 0, 15, 2, 0, DoubleXRoom.field_175828_a, DoubleXRoom.field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 2, 7, 14, 2, 7, DoubleXRoom.field_175828_a, DoubleXRoom.field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 1, 0, 0, 1, 7, DoubleXRoom.field_175826_b, DoubleXRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 15, 1, 0, 15, 1, 7, DoubleXRoom.field_175826_b, DoubleXRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 1, 0, 15, 1, 0, DoubleXRoom.field_175826_b, DoubleXRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 1, 7, 14, 1, 7, DoubleXRoom.field_175826_b, DoubleXRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 5, 1, 0, 10, 1, 4, DoubleXRoom.field_175826_b, DoubleXRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 6, 2, 0, 9, 2, 3, DoubleXRoom.field_175828_a, DoubleXRoom.field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 5, 3, 0, 10, 3, 4, DoubleXRoom.field_175826_b, DoubleXRoom.field_175826_b, false);
            this.setBlockState(world, DoubleXRoom.field_175825_e, 6, 2, 3, structureBoundingBox);
            this.setBlockState(world, DoubleXRoom.field_175825_e, 9, 2, 3, structureBoundingBox);
            if (field_175830_k.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, 3, 1, 0, 4, 2, 0, false);
            }
            if (field_175830_k.field_175966_c[EnumFacing.NORTH.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, 3, 1, 7, 4, 2, 7, false);
            }
            if (field_175830_k.field_175966_c[EnumFacing.WEST.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, 0, 1, 3, 0, 2, 4, false);
            }
            if (roomDefinition.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, 11, 1, 0, 12, 2, 0, false);
            }
            if (roomDefinition.field_175966_c[EnumFacing.NORTH.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, 11, 1, 7, 12, 2, 7, false);
            }
            if (roomDefinition.field_175966_c[EnumFacing.EAST.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, 15, 1, 3, 15, 2, 4, false);
            }
            return true;
        }
        
        public DoubleXRoom(final EnumFacing enumFacing, final RoomDefinition roomDefinition, final Random random) {
            super(1, enumFacing, roomDefinition, 2, 1, 1);
        }
        
        public DoubleXRoom() {
        }
    }
    
    public abstract static class Piece extends StructureComponent
    {
        protected RoomDefinition field_175830_k;
        protected static final IBlockState field_175825_e;
        protected static final int field_175831_h;
        protected static final int field_175823_g;
        protected static final IBlockState field_175826_b;
        protected static final IBlockState field_175822_f;
        protected static final int field_175829_j;
        protected static final IBlockState field_175828_a;
        protected static final IBlockState field_175827_c;
        protected static final int field_175832_i;
        protected static final IBlockState field_175824_d;
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
        }
        
        protected boolean func_175817_a(final World world, final StructureBoundingBox structureBoundingBox, final int n, final int n2, final int n3) {
            final int xWithOffset = this.getXWithOffset(n, n3);
            final int yWithOffset = this.getYWithOffset(n2);
            final int zWithOffset = this.getZWithOffset(n, n3);
            if (structureBoundingBox.isVecInside(new BlockPos(xWithOffset, yWithOffset, zWithOffset))) {
                final EntityGuardian entityGuardian = new EntityGuardian(world);
                entityGuardian.setElder(true);
                entityGuardian.heal(entityGuardian.getMaxHealth());
                entityGuardian.setLocationAndAngles(xWithOffset + 0.5, yWithOffset, zWithOffset + 0.5, 0.0f, 0.0f);
                entityGuardian.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entityGuardian)), null);
                world.spawnEntityInWorld(entityGuardian);
                return true;
            }
            return false;
        }
        
        static {
            field_175828_a = Blocks.prismarine.getStateFromMeta(BlockPrismarine.ROUGH_META);
            field_175826_b = Blocks.prismarine.getStateFromMeta(BlockPrismarine.BRICKS_META);
            field_175827_c = Blocks.prismarine.getStateFromMeta(BlockPrismarine.DARK_META);
            field_175824_d = Piece.field_175826_b;
            field_175825_e = Blocks.sea_lantern.getDefaultState();
            field_175822_f = Blocks.water.getDefaultState();
            field_175823_g = func_175820_a(2, 0, 0);
            field_175831_h = func_175820_a(2, 2, 0);
            field_175832_i = func_175820_a(0, 1, 0);
            field_175829_j = func_175820_a(4, 1, 0);
        }
        
        protected Piece(final int n, final EnumFacing coordBaseMode, final RoomDefinition field_175830_k, final int n2, final int n3, final int n4) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.field_175830_k = field_175830_k;
            final int field_175967_a = field_175830_k.field_175967_a;
            final int n5 = field_175967_a % 5;
            final int n6 = field_175967_a / 5 % 5;
            final int n7 = field_175967_a / 25;
            if (coordBaseMode != EnumFacing.NORTH && coordBaseMode != EnumFacing.SOUTH) {
                this.boundingBox = new StructureBoundingBox(0, 0, 0, n4 * 8 - 1, n3 * 4 - 1, n2 * 8 - 1);
            }
            else {
                this.boundingBox = new StructureBoundingBox(0, 0, 0, n2 * 8 - 1, n3 * 4 - 1, n4 * 8 - 1);
            }
            switch (StructureOceanMonumentPieces$1.$SwitchMap$net$minecraft$util$EnumFacing[coordBaseMode.ordinal()]) {
                case 1: {
                    this.boundingBox.offset(n5 * 8, n7 * 4, -(n6 + n4) * 8 + 1);
                    break;
                }
                case 2: {
                    this.boundingBox.offset(n5 * 8, n7 * 4, n6 * 8);
                    break;
                }
                case 3: {
                    this.boundingBox.offset(-(n6 + n4) * 8 + 1, n7 * 4, n5 * 8);
                    break;
                }
                default: {
                    this.boundingBox.offset(n6 * 8, n7 * 4, n5 * 8);
                    break;
                }
            }
        }
        
        protected static final int func_175820_a(final int n, final int n2, final int n3) {
            return n2 * 25 + n3 * 5 + n;
        }
        
        public Piece() {
            super(0);
        }
        
        protected boolean func_175818_a(final StructureBoundingBox structureBoundingBox, final int n, final int n2, final int n3, final int n4) {
            final int xWithOffset = this.getXWithOffset(n, n2);
            final int zWithOffset = this.getZWithOffset(n, n2);
            final int xWithOffset2 = this.getXWithOffset(n3, n4);
            final int zWithOffset2 = this.getZWithOffset(n3, n4);
            return structureBoundingBox.intersectsWith(Math.min(xWithOffset, xWithOffset2), Math.min(zWithOffset, zWithOffset2), Math.max(xWithOffset, xWithOffset2), Math.max(zWithOffset, zWithOffset2));
        }
        
        protected void func_181655_a(final World world, final StructureBoundingBox structureBoundingBox, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final boolean b) {
            for (int i = n2; i <= n5; ++i) {
                for (int j = n; j <= n4; ++j) {
                    for (int k = n3; k <= n6; ++k) {
                        if (!b || this.getBlockStateFromPos(world, j, i, k, structureBoundingBox).getBlock().getMaterial() != Material.air) {
                            if (this.getYWithOffset(i) >= world.func_181545_F()) {
                                this.setBlockState(world, Blocks.air.getDefaultState(), j, i, k, structureBoundingBox);
                            }
                            else {
                                this.setBlockState(world, Piece.field_175822_f, j, i, k, structureBoundingBox);
                            }
                        }
                    }
                }
            }
        }
        
        public Piece(final EnumFacing coordBaseMode, final StructureBoundingBox boundingBox) {
            super(1);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
        
        protected void func_175819_a(final World world, final StructureBoundingBox structureBoundingBox, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final IBlockState blockState) {
            for (int i = n2; i <= n5; ++i) {
                for (int j = n; j <= n4; ++j) {
                    for (int k = n3; k <= n6; ++k) {
                        if (this.getBlockStateFromPos(world, j, i, k, structureBoundingBox) == Piece.field_175822_f) {
                            this.setBlockState(world, blockState, j, i, k, structureBoundingBox);
                        }
                    }
                }
            }
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
        }
        
        public Piece(final int n) {
            super(n);
        }
        
        protected void func_175821_a(final World world, final StructureBoundingBox structureBoundingBox, final int n, final int n2, final boolean b) {
            if (b) {
                this.fillWithBlocks(world, structureBoundingBox, n + 0, 0, n2 + 0, n + 2, 0, n2 + 8 - 1, Piece.field_175828_a, Piece.field_175828_a, false);
                this.fillWithBlocks(world, structureBoundingBox, n + 5, 0, n2 + 0, n + 8 - 1, 0, n2 + 8 - 1, Piece.field_175828_a, Piece.field_175828_a, false);
                this.fillWithBlocks(world, structureBoundingBox, n + 3, 0, n2 + 0, n + 4, 0, n2 + 2, Piece.field_175828_a, Piece.field_175828_a, false);
                this.fillWithBlocks(world, structureBoundingBox, n + 3, 0, n2 + 5, n + 4, 0, n2 + 8 - 1, Piece.field_175828_a, Piece.field_175828_a, false);
                this.fillWithBlocks(world, structureBoundingBox, n + 3, 0, n2 + 2, n + 4, 0, n2 + 2, Piece.field_175826_b, Piece.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, n + 3, 0, n2 + 5, n + 4, 0, n2 + 5, Piece.field_175826_b, Piece.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, n + 2, 0, n2 + 3, n + 2, 0, n2 + 4, Piece.field_175826_b, Piece.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, n + 5, 0, n2 + 3, n + 5, 0, n2 + 4, Piece.field_175826_b, Piece.field_175826_b, false);
            }
            else {
                this.fillWithBlocks(world, structureBoundingBox, n + 0, 0, n2 + 0, n + 8 - 1, 0, n2 + 8 - 1, Piece.field_175828_a, Piece.field_175828_a, false);
            }
        }
    }
    
    static class RoomDefinition
    {
        int field_175962_f;
        RoomDefinition[] field_175965_b;
        int field_175967_a;
        boolean field_175964_e;
        boolean field_175963_d;
        boolean[] field_175966_c;
        
        public boolean func_175961_b() {
            return this.field_175967_a >= 75;
        }
        
        public void func_175958_a() {
            while (true) {
                this.field_175966_c[0] = (this.field_175965_b[0] != null);
                int n = 0;
                ++n;
            }
        }
        
        public int func_175960_c() {
            while (true) {
                if (this.field_175966_c[0]) {
                    int n = 0;
                    ++n;
                }
                int n2 = 0;
                ++n2;
            }
        }
        
        public void func_175957_a(final EnumFacing enumFacing, final RoomDefinition roomDefinition) {
            this.field_175965_b[enumFacing.getIndex()] = roomDefinition;
            roomDefinition.field_175965_b[enumFacing.getOpposite().getIndex()] = this;
        }
        
        public RoomDefinition(final int field_175967_a) {
            this.field_175965_b = new RoomDefinition[6];
            this.field_175966_c = new boolean[6];
            this.field_175967_a = field_175967_a;
        }
    }
    
    interface MonumentRoomFitHelper
    {
        boolean func_175969_a(final RoomDefinition p0);
        
        Piece func_175968_a(final EnumFacing p0, final RoomDefinition p1, final Random p2);
    }
    
    static class XYDoubleRoomFitHelper implements MonumentRoomFitHelper
    {
        private XYDoubleRoomFitHelper() {
        }
        
        @Override
        public Piece func_175968_a(final EnumFacing enumFacing, final RoomDefinition roomDefinition, final Random random) {
            roomDefinition.field_175963_d = true;
            roomDefinition.field_175965_b[EnumFacing.EAST.getIndex()].field_175963_d = true;
            roomDefinition.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = true;
            roomDefinition.field_175965_b[EnumFacing.EAST.getIndex()].field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = true;
            return new DoubleXYRoom(enumFacing, roomDefinition, random);
        }
        
        XYDoubleRoomFitHelper(final StructureOceanMonumentPieces$1 object) {
            this();
        }
        
        @Override
        public boolean func_175969_a(final RoomDefinition roomDefinition) {
            if (roomDefinition.field_175966_c[EnumFacing.EAST.getIndex()] && !roomDefinition.field_175965_b[EnumFacing.EAST.getIndex()].field_175963_d && roomDefinition.field_175966_c[EnumFacing.UP.getIndex()] && !roomDefinition.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d) {
                final RoomDefinition roomDefinition2 = roomDefinition.field_175965_b[EnumFacing.EAST.getIndex()];
                return roomDefinition2.field_175966_c[EnumFacing.UP.getIndex()] && !roomDefinition2.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d;
            }
            return false;
        }
    }
    
    public static class DoubleXYRoom extends Piece
    {
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            final RoomDefinition roomDefinition = this.field_175830_k.field_175965_b[EnumFacing.EAST.getIndex()];
            final RoomDefinition field_175830_k = this.field_175830_k;
            final RoomDefinition roomDefinition2 = field_175830_k.field_175965_b[EnumFacing.UP.getIndex()];
            final RoomDefinition roomDefinition3 = roomDefinition.field_175965_b[EnumFacing.UP.getIndex()];
            if (this.field_175830_k.field_175967_a / 25 > 0) {
                this.func_175821_a(world, structureBoundingBox, 8, 0, roomDefinition.field_175966_c[EnumFacing.DOWN.getIndex()]);
                this.func_175821_a(world, structureBoundingBox, 0, 0, field_175830_k.field_175966_c[EnumFacing.DOWN.getIndex()]);
            }
            if (roomDefinition2.field_175965_b[EnumFacing.UP.getIndex()] == null) {
                this.func_175819_a(world, structureBoundingBox, 1, 8, 1, 7, 8, 6, DoubleXYRoom.field_175828_a);
            }
            if (roomDefinition3.field_175965_b[EnumFacing.UP.getIndex()] == null) {
                this.func_175819_a(world, structureBoundingBox, 8, 8, 1, 14, 8, 6, DoubleXYRoom.field_175828_a);
            }
            while (true) {
                final IBlockState field_175826_b = DoubleXYRoom.field_175826_b;
                this.fillWithBlocks(world, structureBoundingBox, 0, 1, 0, 0, 1, 7, field_175826_b, field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 15, 1, 0, 15, 1, 7, field_175826_b, field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 1, 1, 0, 15, 1, 0, field_175826_b, field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 1, 1, 7, 14, 1, 7, field_175826_b, field_175826_b, false);
                int n = 0;
                ++n;
            }
        }
        
        public DoubleXYRoom() {
        }
        
        public DoubleXYRoom(final EnumFacing enumFacing, final RoomDefinition roomDefinition, final Random random) {
            super(1, enumFacing, roomDefinition, 2, 2, 1);
        }
    }
    
    static class FitSimpleRoomTopHelper implements MonumentRoomFitHelper
    {
        private FitSimpleRoomTopHelper() {
        }
        
        @Override
        public boolean func_175969_a(final RoomDefinition roomDefinition) {
            return !roomDefinition.field_175966_c[EnumFacing.WEST.getIndex()] && !roomDefinition.field_175966_c[EnumFacing.EAST.getIndex()] && !roomDefinition.field_175966_c[EnumFacing.NORTH.getIndex()] && !roomDefinition.field_175966_c[EnumFacing.SOUTH.getIndex()] && !roomDefinition.field_175966_c[EnumFacing.UP.getIndex()];
        }
        
        @Override
        public Piece func_175968_a(final EnumFacing enumFacing, final RoomDefinition roomDefinition, final Random random) {
            roomDefinition.field_175963_d = true;
            return new SimpleTopRoom(enumFacing, roomDefinition, random);
        }
        
        FitSimpleRoomTopHelper(final StructureOceanMonumentPieces$1 object) {
            this();
        }
    }
    
    public static class SimpleTopRoom extends Piece
    {
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.field_175830_k.field_175967_a / 25 > 0) {
                this.func_175821_a(world, structureBoundingBox, 0, 0, this.field_175830_k.field_175966_c[EnumFacing.DOWN.getIndex()]);
            }
            if (this.field_175830_k.field_175965_b[EnumFacing.UP.getIndex()] == null) {
                this.func_175819_a(world, structureBoundingBox, 1, 4, 1, 6, 4, 6, SimpleTopRoom.field_175828_a);
            }
            while (true) {
                if (random.nextInt(3) != 0) {
                    this.fillWithBlocks(world, structureBoundingBox, 1, 2 + ((random.nextInt(4) != 0) ? 1 : 0), 1, 1, 3, 1, Blocks.sponge.getStateFromMeta(1), Blocks.sponge.getStateFromMeta(1), false);
                }
                int n = 0;
                ++n;
            }
        }
        
        public SimpleTopRoom(final EnumFacing enumFacing, final RoomDefinition roomDefinition, final Random random) {
            super(1, enumFacing, roomDefinition, 1, 1, 1);
        }
        
        public SimpleTopRoom() {
        }
    }
    
    static class YDoubleRoomFitHelper implements MonumentRoomFitHelper
    {
        private YDoubleRoomFitHelper() {
        }
        
        @Override
        public Piece func_175968_a(final EnumFacing enumFacing, final RoomDefinition roomDefinition, final Random random) {
            roomDefinition.field_175963_d = true;
            roomDefinition.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = true;
            return new DoubleYRoom(enumFacing, roomDefinition, random);
        }
        
        @Override
        public boolean func_175969_a(final RoomDefinition roomDefinition) {
            return roomDefinition.field_175966_c[EnumFacing.UP.getIndex()] && !roomDefinition.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d;
        }
        
        YDoubleRoomFitHelper(final StructureOceanMonumentPieces$1 object) {
            this();
        }
    }
    
    public static class DoubleYRoom extends Piece
    {
        public DoubleYRoom(final EnumFacing enumFacing, final RoomDefinition roomDefinition, final Random random) {
            super(1, enumFacing, roomDefinition, 1, 2, 1);
        }
        
        public DoubleYRoom() {
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.field_175830_k.field_175967_a / 25 > 0) {
                this.func_175821_a(world, structureBoundingBox, 0, 0, this.field_175830_k.field_175966_c[EnumFacing.DOWN.getIndex()]);
            }
            final RoomDefinition roomDefinition = this.field_175830_k.field_175965_b[EnumFacing.UP.getIndex()];
            if (roomDefinition.field_175965_b[EnumFacing.UP.getIndex()] == null) {
                this.func_175819_a(world, structureBoundingBox, 1, 8, 1, 6, 8, 6, DoubleYRoom.field_175828_a);
            }
            this.fillWithBlocks(world, structureBoundingBox, 0, 4, 0, 0, 4, 7, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 7, 4, 0, 7, 4, 7, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 4, 0, 6, 4, 0, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 4, 7, 6, 4, 7, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 2, 4, 1, 2, 4, 2, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 4, 2, 1, 4, 2, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 5, 4, 1, 5, 4, 2, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 6, 4, 2, 6, 4, 2, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 2, 4, 5, 2, 4, 6, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 4, 5, 1, 4, 5, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 5, 4, 5, 5, 4, 6, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 6, 4, 5, 6, 4, 5, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
            RoomDefinition field_175830_k = this.field_175830_k;
            while (true) {
                if (field_175830_k.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
                    this.fillWithBlocks(world, structureBoundingBox, 2, 1, 7, 2, 3, 7, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
                    this.fillWithBlocks(world, structureBoundingBox, 5, 1, 7, 5, 3, 7, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
                    this.fillWithBlocks(world, structureBoundingBox, 3, 3, 7, 4, 3, 7, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
                }
                else {
                    this.fillWithBlocks(world, structureBoundingBox, 0, 1, 7, 7, 3, 7, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
                    this.fillWithBlocks(world, structureBoundingBox, 0, 2, 7, 7, 2, 7, DoubleYRoom.field_175828_a, DoubleYRoom.field_175828_a, false);
                }
                if (field_175830_k.field_175966_c[EnumFacing.NORTH.getIndex()]) {
                    this.fillWithBlocks(world, structureBoundingBox, 2, 1, 7, 2, 3, 7, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
                    this.fillWithBlocks(world, structureBoundingBox, 5, 1, 7, 5, 3, 7, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
                    this.fillWithBlocks(world, structureBoundingBox, 3, 3, 7, 4, 3, 7, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
                }
                else {
                    this.fillWithBlocks(world, structureBoundingBox, 0, 1, 7, 7, 3, 7, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
                    this.fillWithBlocks(world, structureBoundingBox, 0, 2, 7, 7, 2, 7, DoubleYRoom.field_175828_a, DoubleYRoom.field_175828_a, false);
                }
                if (field_175830_k.field_175966_c[EnumFacing.WEST.getIndex()]) {
                    this.fillWithBlocks(world, structureBoundingBox, 7, 1, 2, 7, 3, 2, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
                    this.fillWithBlocks(world, structureBoundingBox, 7, 1, 5, 7, 3, 5, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
                    this.fillWithBlocks(world, structureBoundingBox, 7, 3, 3, 7, 3, 4, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
                }
                else {
                    this.fillWithBlocks(world, structureBoundingBox, 7, 1, 0, 7, 3, 7, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
                    this.fillWithBlocks(world, structureBoundingBox, 7, 2, 0, 7, 2, 7, DoubleYRoom.field_175828_a, DoubleYRoom.field_175828_a, false);
                }
                if (field_175830_k.field_175966_c[EnumFacing.EAST.getIndex()]) {
                    this.fillWithBlocks(world, structureBoundingBox, 7, 1, 2, 7, 3, 2, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
                    this.fillWithBlocks(world, structureBoundingBox, 7, 1, 5, 7, 3, 5, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
                    this.fillWithBlocks(world, structureBoundingBox, 7, 3, 3, 7, 3, 4, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
                }
                else {
                    this.fillWithBlocks(world, structureBoundingBox, 7, 1, 0, 7, 3, 7, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
                    this.fillWithBlocks(world, structureBoundingBox, 7, 2, 0, 7, 2, 7, DoubleYRoom.field_175828_a, DoubleYRoom.field_175828_a, false);
                }
                field_175830_k = roomDefinition;
                final int n;
                n += 4;
            }
        }
    }
    
    static class ZDoubleRoomFitHelper implements MonumentRoomFitHelper
    {
        private ZDoubleRoomFitHelper() {
        }
        
        ZDoubleRoomFitHelper(final StructureOceanMonumentPieces$1 object) {
            this();
        }
        
        @Override
        public Piece func_175968_a(final EnumFacing enumFacing, final RoomDefinition roomDefinition, final Random random) {
            RoomDefinition roomDefinition2 = roomDefinition;
            if (!roomDefinition.field_175966_c[EnumFacing.NORTH.getIndex()] || roomDefinition.field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d) {
                roomDefinition2 = roomDefinition.field_175965_b[EnumFacing.SOUTH.getIndex()];
            }
            roomDefinition2.field_175963_d = true;
            roomDefinition2.field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d = true;
            return new DoubleZRoom(enumFacing, roomDefinition2, random);
        }
        
        @Override
        public boolean func_175969_a(final RoomDefinition roomDefinition) {
            return roomDefinition.field_175966_c[EnumFacing.NORTH.getIndex()] && !roomDefinition.field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d;
        }
    }
    
    public static class DoubleZRoom extends Piece
    {
        public DoubleZRoom(final EnumFacing enumFacing, final RoomDefinition roomDefinition, final Random random) {
            super(1, enumFacing, roomDefinition, 1, 1, 2);
        }
        
        public DoubleZRoom() {
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            final RoomDefinition roomDefinition = this.field_175830_k.field_175965_b[EnumFacing.NORTH.getIndex()];
            final RoomDefinition field_175830_k = this.field_175830_k;
            if (this.field_175830_k.field_175967_a / 25 > 0) {
                this.func_175821_a(world, structureBoundingBox, 0, 8, roomDefinition.field_175966_c[EnumFacing.DOWN.getIndex()]);
                this.func_175821_a(world, structureBoundingBox, 0, 0, field_175830_k.field_175966_c[EnumFacing.DOWN.getIndex()]);
            }
            if (field_175830_k.field_175965_b[EnumFacing.UP.getIndex()] == null) {
                this.func_175819_a(world, structureBoundingBox, 1, 4, 1, 6, 4, 7, DoubleZRoom.field_175828_a);
            }
            if (roomDefinition.field_175965_b[EnumFacing.UP.getIndex()] == null) {
                this.func_175819_a(world, structureBoundingBox, 1, 4, 8, 6, 4, 14, DoubleZRoom.field_175828_a);
            }
            this.fillWithBlocks(world, structureBoundingBox, 0, 3, 0, 0, 3, 15, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 7, 3, 0, 7, 3, 15, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 3, 0, 7, 3, 0, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 3, 15, 6, 3, 15, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 2, 0, 0, 2, 15, DoubleZRoom.field_175828_a, DoubleZRoom.field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 7, 2, 0, 7, 2, 15, DoubleZRoom.field_175828_a, DoubleZRoom.field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 2, 0, 7, 2, 0, DoubleZRoom.field_175828_a, DoubleZRoom.field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 2, 15, 6, 2, 15, DoubleZRoom.field_175828_a, DoubleZRoom.field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 1, 0, 0, 1, 15, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 7, 1, 0, 7, 1, 15, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 1, 0, 7, 1, 0, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 1, 15, 6, 1, 15, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 1, 1, 1, 1, 2, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 6, 1, 1, 6, 1, 2, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 3, 1, 1, 3, 2, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 6, 3, 1, 6, 3, 2, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 1, 13, 1, 1, 14, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 6, 1, 13, 6, 1, 14, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 3, 13, 1, 3, 14, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 6, 3, 13, 6, 3, 14, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 2, 1, 6, 2, 3, 6, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 5, 1, 6, 5, 3, 6, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 2, 1, 9, 2, 3, 9, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 5, 1, 9, 5, 3, 9, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 3, 2, 6, 4, 2, 6, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 3, 2, 9, 4, 2, 9, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 2, 2, 7, 2, 2, 8, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 5, 2, 7, 5, 2, 8, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
            this.setBlockState(world, DoubleZRoom.field_175825_e, 2, 2, 5, structureBoundingBox);
            this.setBlockState(world, DoubleZRoom.field_175825_e, 5, 2, 5, structureBoundingBox);
            this.setBlockState(world, DoubleZRoom.field_175825_e, 2, 2, 10, structureBoundingBox);
            this.setBlockState(world, DoubleZRoom.field_175825_e, 5, 2, 10, structureBoundingBox);
            this.setBlockState(world, DoubleZRoom.field_175826_b, 2, 3, 5, structureBoundingBox);
            this.setBlockState(world, DoubleZRoom.field_175826_b, 5, 3, 5, structureBoundingBox);
            this.setBlockState(world, DoubleZRoom.field_175826_b, 2, 3, 10, structureBoundingBox);
            this.setBlockState(world, DoubleZRoom.field_175826_b, 5, 3, 10, structureBoundingBox);
            if (field_175830_k.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, 3, 1, 0, 4, 2, 0, false);
            }
            if (field_175830_k.field_175966_c[EnumFacing.EAST.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, 7, 1, 3, 7, 2, 4, false);
            }
            if (field_175830_k.field_175966_c[EnumFacing.WEST.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, 0, 1, 3, 0, 2, 4, false);
            }
            if (roomDefinition.field_175966_c[EnumFacing.NORTH.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, 3, 1, 15, 4, 2, 15, false);
            }
            if (roomDefinition.field_175966_c[EnumFacing.WEST.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, 0, 1, 11, 0, 2, 12, false);
            }
            if (roomDefinition.field_175966_c[EnumFacing.EAST.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, 7, 1, 11, 7, 2, 12, false);
            }
            return true;
        }
    }
    
    public static class EntryRoom extends Piece
    {
        public EntryRoom(final EnumFacing enumFacing, final RoomDefinition roomDefinition) {
            super(1, enumFacing, roomDefinition, 1, 1, 1);
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            this.fillWithBlocks(world, structureBoundingBox, 0, 3, 0, 2, 3, 7, EntryRoom.field_175826_b, EntryRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 5, 3, 0, 7, 3, 7, EntryRoom.field_175826_b, EntryRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 2, 0, 1, 2, 7, EntryRoom.field_175826_b, EntryRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 6, 2, 0, 7, 2, 7, EntryRoom.field_175826_b, EntryRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 1, 0, 0, 1, 7, EntryRoom.field_175826_b, EntryRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 7, 1, 0, 7, 1, 7, EntryRoom.field_175826_b, EntryRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 1, 7, 7, 3, 7, EntryRoom.field_175826_b, EntryRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 1, 0, 2, 3, 0, EntryRoom.field_175826_b, EntryRoom.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 5, 1, 0, 6, 3, 0, EntryRoom.field_175826_b, EntryRoom.field_175826_b, false);
            if (this.field_175830_k.field_175966_c[EnumFacing.NORTH.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, 3, 1, 7, 4, 2, 7, false);
            }
            if (this.field_175830_k.field_175966_c[EnumFacing.WEST.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, 0, 1, 3, 1, 2, 4, false);
            }
            if (this.field_175830_k.field_175966_c[EnumFacing.EAST.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, 6, 1, 3, 7, 2, 4, false);
            }
            return true;
        }
        
        public EntryRoom() {
        }
    }
    
    public static class MonumentBuilding extends Piece
    {
        private List field_175843_q;
        private RoomDefinition field_175845_o;
        private RoomDefinition field_175844_p;
        
        private void func_175840_a(final boolean b, final int n, final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (!this.func_175818_a(structureBoundingBox, n, 0, n + 23, 20)) {
                return;
            }
            this.fillWithBlocks(world, structureBoundingBox, n + 0, 0, 0, n + 24, 0, 20, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_181655_a(world, structureBoundingBox, n + 0, 1, 0, n + 24, 10, 20, false);
            while (true) {
                this.fillWithBlocks(world, structureBoundingBox, n + 0, 1, 0, n + 0, 1, 20, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, n + 0 + 7, 5, 7, n + 0 + 7, 5, 20, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, n + 17 - 0, 5, 7, n + 17 - 0, 5, 20, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, n + 24 - 0, 1, 0, n + 24 - 0, 1, 20, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, n + 0 + 1, 1, 0, n + 23 - 0, 1, 0, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, n + 0 + 8, 5, 7, n + 16 - 0, 5, 7, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
                int n2 = 0;
                ++n2;
            }
        }
        
        private void func_175837_c(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (!this.func_175818_a(structureBoundingBox, 15, 20, 42, 21)) {
                return;
            }
            this.fillWithBlocks(world, structureBoundingBox, 15, 0, 21, 42, 0, 21, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_181655_a(world, structureBoundingBox, 26, 1, 21, 31, 3, 21, false);
            this.fillWithBlocks(world, structureBoundingBox, 21, 12, 21, 36, 12, 21, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 17, 11, 21, 40, 11, 21, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 16, 10, 21, 41, 10, 21, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 15, 7, 21, 42, 9, 21, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 16, 6, 21, 41, 6, 21, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 17, 5, 21, 40, 5, 21, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 21, 4, 21, 36, 4, 21, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 22, 3, 21, 26, 3, 21, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 31, 3, 21, 35, 3, 21, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 23, 2, 21, 25, 2, 21, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 32, 2, 21, 34, 2, 21, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 28, 4, 20, 29, 4, 21, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
            this.setBlockState(world, MonumentBuilding.field_175826_b, 27, 3, 21, structureBoundingBox);
            this.setBlockState(world, MonumentBuilding.field_175826_b, 30, 3, 21, structureBoundingBox);
            this.setBlockState(world, MonumentBuilding.field_175826_b, 26, 2, 21, structureBoundingBox);
            this.setBlockState(world, MonumentBuilding.field_175826_b, 31, 2, 21, structureBoundingBox);
            this.setBlockState(world, MonumentBuilding.field_175826_b, 25, 1, 21, structureBoundingBox);
            this.setBlockState(world, MonumentBuilding.field_175826_b, 32, 1, 21, structureBoundingBox);
            while (true) {
                this.setBlockState(world, MonumentBuilding.field_175827_c, 28, 6, 21, structureBoundingBox);
                this.setBlockState(world, MonumentBuilding.field_175827_c, 29, 6, 21, structureBoundingBox);
                int n = 0;
                ++n;
            }
        }
        
        private void func_175839_b(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (!this.func_175818_a(structureBoundingBox, 22, 5, 35, 17)) {
                return;
            }
            this.func_181655_a(world, structureBoundingBox, 25, 0, 0, 32, 8, 20, false);
            while (true) {
                this.fillWithBlocks(world, structureBoundingBox, 24, 2, 5, 24, 4, 5, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 22, 4, 5, 23, 4, 5, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
                this.setBlockState(world, MonumentBuilding.field_175826_b, 25, 5, 5, structureBoundingBox);
                this.setBlockState(world, MonumentBuilding.field_175826_b, 26, 6, 5, structureBoundingBox);
                this.setBlockState(world, MonumentBuilding.field_175825_e, 26, 5, 5, structureBoundingBox);
                this.fillWithBlocks(world, structureBoundingBox, 33, 2, 5, 33, 4, 5, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 34, 4, 5, 35, 4, 5, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
                this.setBlockState(world, MonumentBuilding.field_175826_b, 32, 5, 5, structureBoundingBox);
                this.setBlockState(world, MonumentBuilding.field_175826_b, 31, 6, 5, structureBoundingBox);
                this.setBlockState(world, MonumentBuilding.field_175825_e, 31, 5, 5, structureBoundingBox);
                this.fillWithBlocks(world, structureBoundingBox, 27, 6, 5, 30, 6, 5, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
                int n = 0;
                ++n;
            }
        }
        
        private void func_175835_e(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.func_175818_a(structureBoundingBox, 0, 21, 6, 58)) {
                this.fillWithBlocks(world, structureBoundingBox, 0, 0, 21, 6, 0, 57, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
                this.func_181655_a(world, structureBoundingBox, 0, 1, 21, 6, 7, 57, false);
                this.fillWithBlocks(world, structureBoundingBox, 4, 4, 21, 6, 4, 53, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
                while (true) {
                    this.fillWithBlocks(world, structureBoundingBox, 0, 1, 21, 0, 1, 57, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
                    int n = 0;
                    ++n;
                }
            }
            else if (this.func_175818_a(structureBoundingBox, 51, 21, 58, 58)) {
                this.fillWithBlocks(world, structureBoundingBox, 51, 0, 21, 57, 0, 57, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
                this.func_181655_a(world, structureBoundingBox, 51, 1, 21, 57, 7, 57, false);
                this.fillWithBlocks(world, structureBoundingBox, 51, 4, 21, 53, 4, 53, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
                while (true) {
                    this.fillWithBlocks(world, structureBoundingBox, 57, 1, 21, 57, 1, 57, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
                    int n = 0;
                    ++n;
                }
            }
            else {
                if (!this.func_175818_a(structureBoundingBox, 0, 51, 57, 57)) {
                    return;
                }
                this.fillWithBlocks(world, structureBoundingBox, 7, 0, 51, 50, 0, 57, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
                this.func_181655_a(world, structureBoundingBox, 7, 1, 51, 50, 10, 57, false);
                while (true) {
                    this.fillWithBlocks(world, structureBoundingBox, 1, 1, 57, 56, 1, 57, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
                    int n = 0;
                    ++n;
                }
            }
        }
        
        @Override
        public boolean addComponentParts(final World p0, final Random p1, final StructureBoundingBox p2) {
            // 
            // This method could not be decompiled.
            // 
            // Could not show original bytecode, likely due to the same error.
            // 
            // The error that occurred was:
            // 
            // com.strobel.assembler.metadata.MethodBodyParseException: An error occurred while parsing the bytecode of method 'net/minecraft/world/gen/structure/StructureOceanMonumentPieces$MonumentBuilding.addComponentParts:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/world/gen/structure/StructureBoundingBox;)Z'.
            //     at com.strobel.assembler.metadata.MethodReader.readBody(MethodReader.java:66)
            //     at com.strobel.assembler.metadata.MethodDefinition.tryLoadBody(MethodDefinition.java:729)
            //     at com.strobel.assembler.metadata.MethodDefinition.getBody(MethodDefinition.java:83)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:202)
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
            // Caused by: java.lang.IndexOutOfBoundsException: No instruction found at offset 95.
            //     at com.strobel.assembler.ir.InstructionCollection.atOffset(InstructionCollection.java:38)
            //     at com.strobel.assembler.metadata.MethodReader.readBodyCore(MethodReader.java:235)
            //     at com.strobel.assembler.metadata.MethodReader.readBody(MethodReader.java:62)
            //     ... 20 more
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
        
        private void func_175841_d(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (!this.func_175818_a(structureBoundingBox, 21, 21, 36, 36)) {
                return;
            }
            this.fillWithBlocks(world, structureBoundingBox, 21, 0, 22, 36, 0, 36, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
            this.func_181655_a(world, structureBoundingBox, 21, 1, 22, 36, 23, 36, false);
            while (true) {
                this.fillWithBlocks(world, structureBoundingBox, 21, 13, 21, 36, 13, 21, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 21, 13, 36, 36, 13, 36, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 21, 13, 22, 21, 13, 35, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 36, 13, 22, 36, 13, 35, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
                int n = 0;
                ++n;
            }
        }
        
        public MonumentBuilding(final Random random, final int n, final int n2, final EnumFacing coordBaseMode) {
            super(0);
            this.field_175843_q = Lists.newArrayList();
            this.coordBaseMode = coordBaseMode;
            switch (StructureOceanMonumentPieces$1.$SwitchMap$net$minecraft$util$EnumFacing[this.coordBaseMode.ordinal()]) {
                case 1:
                case 2: {
                    this.boundingBox = new StructureBoundingBox(n, 39, n2, n + 58 - 1, 61, n2 + 58 - 1);
                    break;
                }
                default: {
                    this.boundingBox = new StructureBoundingBox(n, 39, n2, n + 58 - 1, 61, n2 + 58 - 1);
                    break;
                }
            }
            final List func_175836_a = this.func_175836_a(random);
            this.field_175845_o.field_175963_d = true;
            this.field_175843_q.add(new EntryRoom(this.coordBaseMode, this.field_175845_o));
            this.field_175843_q.add(new MonumentCoreRoom(this.coordBaseMode, this.field_175844_p, random));
            final ArrayList arrayList = Lists.newArrayList();
            arrayList.add(new XYDoubleRoomFitHelper(null));
            arrayList.add(new YZDoubleRoomFitHelper(null));
            arrayList.add(new ZDoubleRoomFitHelper(null));
            arrayList.add(new XDoubleRoomFitHelper(null));
            arrayList.add(new YDoubleRoomFitHelper(null));
            arrayList.add(new FitSimpleRoomTopHelper(null));
            arrayList.add(new FitSimpleRoomHelper(null));
            for (final RoomDefinition roomDefinition : func_175836_a) {
                if (!roomDefinition.field_175963_d && !roomDefinition.func_175961_b()) {
                    for (final MonumentRoomFitHelper monumentRoomFitHelper : arrayList) {
                        if (monumentRoomFitHelper.func_175969_a(roomDefinition)) {
                            this.field_175843_q.add(monumentRoomFitHelper.func_175968_a(this.coordBaseMode, roomDefinition, random));
                            break;
                        }
                    }
                }
            }
            final int minY = this.boundingBox.minY;
            final int xWithOffset = this.getXWithOffset(9, 22);
            final int zWithOffset = this.getZWithOffset(9, 22);
            final Iterator<Piece> iterator3 = (Iterator<Piece>)this.field_175843_q.iterator();
            while (iterator3.hasNext()) {
                iterator3.next().getBoundingBox().offset(xWithOffset, minY, zWithOffset);
            }
            final StructureBoundingBox func_175899_a = StructureBoundingBox.func_175899_a(this.getXWithOffset(1, 1), this.getYWithOffset(1), this.getZWithOffset(1, 1), this.getXWithOffset(23, 21), this.getYWithOffset(8), this.getZWithOffset(23, 21));
            final StructureBoundingBox func_175899_a2 = StructureBoundingBox.func_175899_a(this.getXWithOffset(34, 1), this.getYWithOffset(1), this.getZWithOffset(34, 1), this.getXWithOffset(56, 21), this.getYWithOffset(8), this.getZWithOffset(56, 21));
            final StructureBoundingBox func_175899_a3 = StructureBoundingBox.func_175899_a(this.getXWithOffset(22, 22), this.getYWithOffset(13), this.getZWithOffset(22, 22), this.getXWithOffset(35, 35), this.getYWithOffset(17), this.getZWithOffset(35, 35));
            int nextInt = random.nextInt();
            this.field_175843_q.add(new WingRoom(this.coordBaseMode, func_175899_a, nextInt++));
            this.field_175843_q.add(new WingRoom(this.coordBaseMode, func_175899_a2, nextInt++));
            this.field_175843_q.add(new Penthouse(this.coordBaseMode, func_175899_a3));
        }
        
        private void func_175842_f(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.func_175818_a(structureBoundingBox, 7, 21, 13, 50)) {
                this.fillWithBlocks(world, structureBoundingBox, 7, 0, 21, 13, 0, 50, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
                this.func_181655_a(world, structureBoundingBox, 7, 1, 21, 13, 10, 50, false);
                this.fillWithBlocks(world, structureBoundingBox, 11, 8, 21, 13, 8, 53, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
                while (true) {
                    this.fillWithBlocks(world, structureBoundingBox, 7, 5, 21, 7, 5, 54, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
                    int n = 0;
                    ++n;
                }
            }
            else if (this.func_175818_a(structureBoundingBox, 44, 21, 50, 54)) {
                this.fillWithBlocks(world, structureBoundingBox, 44, 0, 21, 50, 0, 50, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
                this.func_181655_a(world, structureBoundingBox, 44, 1, 21, 50, 10, 50, false);
                this.fillWithBlocks(world, structureBoundingBox, 44, 8, 21, 46, 8, 53, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
                while (true) {
                    this.fillWithBlocks(world, structureBoundingBox, 50, 5, 21, 50, 5, 54, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
                    int n = 0;
                    ++n;
                }
            }
            else {
                if (!this.func_175818_a(structureBoundingBox, 8, 44, 49, 54)) {
                    return;
                }
                this.fillWithBlocks(world, structureBoundingBox, 14, 0, 44, 43, 0, 50, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
                this.func_181655_a(world, structureBoundingBox, 14, 1, 44, 43, 10, 50, false);
                while (true) {
                    this.setBlockState(world, MonumentBuilding.field_175824_d, 0, 9, 45, structureBoundingBox);
                    this.setBlockState(world, MonumentBuilding.field_175824_d, 0, 9, 52, structureBoundingBox);
                    final int n;
                    n += 3;
                }
            }
        }
        
        private List func_175836_a(final Random random) {
            final RoomDefinition[] array = new RoomDefinition[75];
            while (true) {
                final int func_175820_a = Piece.func_175820_a(0, 0, 0);
                array[func_175820_a] = new RoomDefinition(func_175820_a);
                int n = 0;
                ++n;
            }
        }
        
        private void func_175838_g(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.func_175818_a(structureBoundingBox, 14, 21, 20, 43)) {
                this.fillWithBlocks(world, structureBoundingBox, 14, 0, 21, 20, 0, 43, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
                this.func_181655_a(world, structureBoundingBox, 14, 1, 22, 20, 14, 43, false);
                this.fillWithBlocks(world, structureBoundingBox, 18, 12, 22, 20, 12, 39, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
                this.fillWithBlocks(world, structureBoundingBox, 18, 12, 21, 20, 12, 21, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
                while (true) {
                    this.setBlockState(world, MonumentBuilding.field_175824_d, 19, 13, 21, structureBoundingBox);
                    final int n;
                    n += 3;
                }
            }
            else if (this.func_175818_a(structureBoundingBox, 37, 21, 43, 43)) {
                this.fillWithBlocks(world, structureBoundingBox, 37, 0, 21, 43, 0, 43, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
                this.func_181655_a(world, structureBoundingBox, 37, 1, 22, 43, 14, 43, false);
                this.fillWithBlocks(world, structureBoundingBox, 37, 12, 22, 39, 12, 39, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
                this.fillWithBlocks(world, structureBoundingBox, 37, 12, 21, 39, 12, 21, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, false);
                while (true) {
                    this.setBlockState(world, MonumentBuilding.field_175824_d, 38, 13, 21, structureBoundingBox);
                    final int n;
                    n += 3;
                }
            }
            else {
                if (!this.func_175818_a(structureBoundingBox, 15, 37, 42, 43)) {
                    return;
                }
                this.fillWithBlocks(world, structureBoundingBox, 21, 0, 37, 36, 0, 43, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
                this.func_181655_a(world, structureBoundingBox, 21, 1, 37, 36, 14, 43, false);
                this.fillWithBlocks(world, structureBoundingBox, 21, 12, 37, 36, 12, 39, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, false);
                while (true) {
                    this.setBlockState(world, MonumentBuilding.field_175824_d, 21, 13, 38, structureBoundingBox);
                    final int n;
                    n += 3;
                }
            }
        }
        
        public MonumentBuilding() {
            this.field_175843_q = Lists.newArrayList();
        }
    }
    
    static class YZDoubleRoomFitHelper implements MonumentRoomFitHelper
    {
        @Override
        public Piece func_175968_a(final EnumFacing enumFacing, final RoomDefinition roomDefinition, final Random random) {
            roomDefinition.field_175963_d = true;
            roomDefinition.field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d = true;
            roomDefinition.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = true;
            roomDefinition.field_175965_b[EnumFacing.NORTH.getIndex()].field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = true;
            return new DoubleYZRoom(enumFacing, roomDefinition, random);
        }
        
        YZDoubleRoomFitHelper(final StructureOceanMonumentPieces$1 object) {
            this();
        }
        
        private YZDoubleRoomFitHelper() {
        }
        
        @Override
        public boolean func_175969_a(final RoomDefinition roomDefinition) {
            if (roomDefinition.field_175966_c[EnumFacing.NORTH.getIndex()] && !roomDefinition.field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d && roomDefinition.field_175966_c[EnumFacing.UP.getIndex()] && !roomDefinition.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d) {
                final RoomDefinition roomDefinition2 = roomDefinition.field_175965_b[EnumFacing.NORTH.getIndex()];
                return roomDefinition2.field_175966_c[EnumFacing.UP.getIndex()] && !roomDefinition2.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d;
            }
            return false;
        }
    }
    
    public static class DoubleYZRoom extends Piece
    {
        public DoubleYZRoom() {
        }
        
        public DoubleYZRoom(final EnumFacing enumFacing, final RoomDefinition roomDefinition, final Random random) {
            super(1, enumFacing, roomDefinition, 1, 2, 2);
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            final RoomDefinition roomDefinition = this.field_175830_k.field_175965_b[EnumFacing.NORTH.getIndex()];
            final RoomDefinition field_175830_k = this.field_175830_k;
            final RoomDefinition roomDefinition2 = roomDefinition.field_175965_b[EnumFacing.UP.getIndex()];
            final RoomDefinition roomDefinition3 = field_175830_k.field_175965_b[EnumFacing.UP.getIndex()];
            if (this.field_175830_k.field_175967_a / 25 > 0) {
                this.func_175821_a(world, structureBoundingBox, 0, 8, roomDefinition.field_175966_c[EnumFacing.DOWN.getIndex()]);
                this.func_175821_a(world, structureBoundingBox, 0, 0, field_175830_k.field_175966_c[EnumFacing.DOWN.getIndex()]);
            }
            if (roomDefinition3.field_175965_b[EnumFacing.UP.getIndex()] == null) {
                this.func_175819_a(world, structureBoundingBox, 1, 8, 1, 6, 8, 7, DoubleYZRoom.field_175828_a);
            }
            if (roomDefinition2.field_175965_b[EnumFacing.UP.getIndex()] == null) {
                this.func_175819_a(world, structureBoundingBox, 1, 8, 8, 6, 8, 14, DoubleYZRoom.field_175828_a);
            }
            while (true) {
                final IBlockState field_175826_b = DoubleYZRoom.field_175826_b;
                this.fillWithBlocks(world, structureBoundingBox, 0, 1, 0, 0, 1, 15, field_175826_b, field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 7, 1, 0, 7, 1, 15, field_175826_b, field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 1, 1, 0, 6, 1, 0, field_175826_b, field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 1, 1, 15, 6, 1, 15, field_175826_b, field_175826_b, false);
                int n = 0;
                ++n;
            }
        }
    }
    
    public static class WingRoom extends Piece
    {
        private int field_175834_o;
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.field_175834_o == 0) {
                this.fillWithBlocks(world, structureBoundingBox, 7, 0, 6, 15, 0, 16, WingRoom.field_175826_b, WingRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 6, 0, 6, 6, 3, 20, WingRoom.field_175826_b, WingRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 16, 0, 6, 16, 3, 20, WingRoom.field_175826_b, WingRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 7, 1, 7, 7, 1, 20, WingRoom.field_175826_b, WingRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 15, 1, 7, 15, 1, 20, WingRoom.field_175826_b, WingRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 7, 1, 6, 9, 3, 6, WingRoom.field_175826_b, WingRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 13, 1, 6, 15, 3, 6, WingRoom.field_175826_b, WingRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 8, 1, 7, 9, 1, 7, WingRoom.field_175826_b, WingRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 13, 1, 7, 14, 1, 7, WingRoom.field_175826_b, WingRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 9, 0, 5, 13, 0, 5, WingRoom.field_175826_b, WingRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 10, 0, 7, 12, 0, 7, WingRoom.field_175827_c, WingRoom.field_175827_c, false);
                this.fillWithBlocks(world, structureBoundingBox, 8, 0, 10, 8, 0, 12, WingRoom.field_175827_c, WingRoom.field_175827_c, false);
                this.fillWithBlocks(world, structureBoundingBox, 14, 0, 10, 14, 0, 12, WingRoom.field_175827_c, WingRoom.field_175827_c, false);
                while (true) {
                    this.setBlockState(world, WingRoom.field_175825_e, 6, 3, 14, structureBoundingBox);
                    this.setBlockState(world, WingRoom.field_175825_e, 16, 3, 14, structureBoundingBox);
                    final int n;
                    n -= 3;
                }
            }
            else {
                if (this.field_175834_o != 1) {
                    return true;
                }
                this.fillWithBlocks(world, structureBoundingBox, 9, 3, 18, 13, 3, 20, WingRoom.field_175826_b, WingRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 9, 0, 18, 9, 2, 18, WingRoom.field_175826_b, WingRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 13, 0, 18, 13, 2, 18, WingRoom.field_175826_b, WingRoom.field_175826_b, false);
                while (true) {
                    this.setBlockState(world, WingRoom.field_175826_b, 14, 6, 20, structureBoundingBox);
                    this.setBlockState(world, WingRoom.field_175825_e, 14, 5, 20, structureBoundingBox);
                    this.setBlockState(world, WingRoom.field_175826_b, 14, 4, 20, structureBoundingBox);
                    int n2 = 0;
                    ++n2;
                }
            }
        }
        
        public WingRoom() {
        }
        
        public WingRoom(final EnumFacing enumFacing, final StructureBoundingBox structureBoundingBox, final int n) {
            super(enumFacing, structureBoundingBox);
            this.field_175834_o = (n & 0x1);
        }
    }
    
    static class FitSimpleRoomHelper implements MonumentRoomFitHelper
    {
        @Override
        public Piece func_175968_a(final EnumFacing enumFacing, final RoomDefinition roomDefinition, final Random random) {
            roomDefinition.field_175963_d = true;
            return new SimpleRoom(enumFacing, roomDefinition, random);
        }
        
        private FitSimpleRoomHelper() {
        }
        
        @Override
        public boolean func_175969_a(final RoomDefinition roomDefinition) {
            return true;
        }
        
        FitSimpleRoomHelper(final StructureOceanMonumentPieces$1 object) {
            this();
        }
    }
    
    public static class SimpleRoom extends Piece
    {
        private int field_175833_o;
        
        public SimpleRoom(final EnumFacing enumFacing, final RoomDefinition roomDefinition, final Random random) {
            super(1, enumFacing, roomDefinition, 1, 1, 1);
            this.field_175833_o = random.nextInt(3);
        }
        
        public SimpleRoom() {
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.field_175830_k.field_175967_a / 25 > 0) {
                this.func_175821_a(world, structureBoundingBox, 0, 0, this.field_175830_k.field_175966_c[EnumFacing.DOWN.getIndex()]);
            }
            if (this.field_175830_k.field_175965_b[EnumFacing.UP.getIndex()] == null) {
                this.func_175819_a(world, structureBoundingBox, 1, 4, 1, 6, 4, 6, SimpleRoom.field_175828_a);
            }
            final boolean b = this.field_175833_o != 0 && random.nextBoolean() && !this.field_175830_k.field_175966_c[EnumFacing.DOWN.getIndex()] && !this.field_175830_k.field_175966_c[EnumFacing.UP.getIndex()] && this.field_175830_k.func_175960_c() > 1;
            if (this.field_175833_o == 0) {
                this.fillWithBlocks(world, structureBoundingBox, 0, 1, 0, 2, 1, 2, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 0, 3, 0, 2, 3, 2, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 0, 2, 0, 0, 2, 2, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, false);
                this.fillWithBlocks(world, structureBoundingBox, 1, 2, 0, 2, 2, 0, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, false);
                this.setBlockState(world, SimpleRoom.field_175825_e, 1, 2, 1, structureBoundingBox);
                this.fillWithBlocks(world, structureBoundingBox, 5, 1, 0, 7, 1, 2, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 5, 3, 0, 7, 3, 2, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 7, 2, 0, 7, 2, 2, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, false);
                this.fillWithBlocks(world, structureBoundingBox, 5, 2, 0, 6, 2, 0, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, false);
                this.setBlockState(world, SimpleRoom.field_175825_e, 6, 2, 1, structureBoundingBox);
                this.fillWithBlocks(world, structureBoundingBox, 0, 1, 5, 2, 1, 7, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 0, 3, 5, 2, 3, 7, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 0, 2, 5, 0, 2, 7, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, false);
                this.fillWithBlocks(world, structureBoundingBox, 1, 2, 7, 2, 2, 7, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, false);
                this.setBlockState(world, SimpleRoom.field_175825_e, 1, 2, 6, structureBoundingBox);
                this.fillWithBlocks(world, structureBoundingBox, 5, 1, 5, 7, 1, 7, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 5, 3, 5, 7, 3, 7, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 7, 2, 5, 7, 2, 7, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, false);
                this.fillWithBlocks(world, structureBoundingBox, 5, 2, 7, 6, 2, 7, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, false);
                this.setBlockState(world, SimpleRoom.field_175825_e, 6, 2, 6, structureBoundingBox);
                if (this.field_175830_k.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
                    this.fillWithBlocks(world, structureBoundingBox, 3, 3, 0, 4, 3, 0, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                }
                else {
                    this.fillWithBlocks(world, structureBoundingBox, 3, 3, 0, 4, 3, 1, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                    this.fillWithBlocks(world, structureBoundingBox, 3, 2, 0, 4, 2, 0, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, false);
                    this.fillWithBlocks(world, structureBoundingBox, 3, 1, 0, 4, 1, 1, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                }
                if (this.field_175830_k.field_175966_c[EnumFacing.NORTH.getIndex()]) {
                    this.fillWithBlocks(world, structureBoundingBox, 3, 3, 7, 4, 3, 7, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                }
                else {
                    this.fillWithBlocks(world, structureBoundingBox, 3, 3, 6, 4, 3, 7, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                    this.fillWithBlocks(world, structureBoundingBox, 3, 2, 7, 4, 2, 7, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, false);
                    this.fillWithBlocks(world, structureBoundingBox, 3, 1, 6, 4, 1, 7, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                }
                if (this.field_175830_k.field_175966_c[EnumFacing.WEST.getIndex()]) {
                    this.fillWithBlocks(world, structureBoundingBox, 0, 3, 3, 0, 3, 4, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                }
                else {
                    this.fillWithBlocks(world, structureBoundingBox, 0, 3, 3, 1, 3, 4, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                    this.fillWithBlocks(world, structureBoundingBox, 0, 2, 3, 0, 2, 4, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, false);
                    this.fillWithBlocks(world, structureBoundingBox, 0, 1, 3, 1, 1, 4, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                }
                if (this.field_175830_k.field_175966_c[EnumFacing.EAST.getIndex()]) {
                    this.fillWithBlocks(world, structureBoundingBox, 7, 3, 3, 7, 3, 4, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                }
                else {
                    this.fillWithBlocks(world, structureBoundingBox, 6, 3, 3, 7, 3, 4, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                    this.fillWithBlocks(world, structureBoundingBox, 7, 2, 3, 7, 2, 4, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, false);
                    this.fillWithBlocks(world, structureBoundingBox, 6, 1, 3, 7, 1, 4, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                }
            }
            else if (this.field_175833_o == 1) {
                this.fillWithBlocks(world, structureBoundingBox, 2, 1, 2, 2, 3, 2, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 2, 1, 5, 2, 3, 5, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 5, 1, 5, 5, 3, 5, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 5, 1, 2, 5, 3, 2, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.setBlockState(world, SimpleRoom.field_175825_e, 2, 2, 2, structureBoundingBox);
                this.setBlockState(world, SimpleRoom.field_175825_e, 2, 2, 5, structureBoundingBox);
                this.setBlockState(world, SimpleRoom.field_175825_e, 5, 2, 5, structureBoundingBox);
                this.setBlockState(world, SimpleRoom.field_175825_e, 5, 2, 2, structureBoundingBox);
                this.fillWithBlocks(world, structureBoundingBox, 0, 1, 0, 1, 3, 0, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 0, 1, 1, 0, 3, 1, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 0, 1, 7, 1, 3, 7, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 0, 1, 6, 0, 3, 6, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 6, 1, 7, 7, 3, 7, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 7, 1, 6, 7, 3, 6, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 6, 1, 0, 7, 3, 0, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 7, 1, 1, 7, 3, 1, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.setBlockState(world, SimpleRoom.field_175828_a, 1, 2, 0, structureBoundingBox);
                this.setBlockState(world, SimpleRoom.field_175828_a, 0, 2, 1, structureBoundingBox);
                this.setBlockState(world, SimpleRoom.field_175828_a, 1, 2, 7, structureBoundingBox);
                this.setBlockState(world, SimpleRoom.field_175828_a, 0, 2, 6, structureBoundingBox);
                this.setBlockState(world, SimpleRoom.field_175828_a, 6, 2, 7, structureBoundingBox);
                this.setBlockState(world, SimpleRoom.field_175828_a, 7, 2, 6, structureBoundingBox);
                this.setBlockState(world, SimpleRoom.field_175828_a, 6, 2, 0, structureBoundingBox);
                this.setBlockState(world, SimpleRoom.field_175828_a, 7, 2, 1, structureBoundingBox);
                if (!this.field_175830_k.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
                    this.fillWithBlocks(world, structureBoundingBox, 1, 3, 0, 6, 3, 0, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                    this.fillWithBlocks(world, structureBoundingBox, 1, 2, 0, 6, 2, 0, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, false);
                    this.fillWithBlocks(world, structureBoundingBox, 1, 1, 0, 6, 1, 0, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                }
                if (!this.field_175830_k.field_175966_c[EnumFacing.NORTH.getIndex()]) {
                    this.fillWithBlocks(world, structureBoundingBox, 1, 3, 7, 6, 3, 7, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                    this.fillWithBlocks(world, structureBoundingBox, 1, 2, 7, 6, 2, 7, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, false);
                    this.fillWithBlocks(world, structureBoundingBox, 1, 1, 7, 6, 1, 7, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                }
                if (!this.field_175830_k.field_175966_c[EnumFacing.WEST.getIndex()]) {
                    this.fillWithBlocks(world, structureBoundingBox, 0, 3, 1, 0, 3, 6, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                    this.fillWithBlocks(world, structureBoundingBox, 0, 2, 1, 0, 2, 6, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, false);
                    this.fillWithBlocks(world, structureBoundingBox, 0, 1, 1, 0, 1, 6, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                }
                if (!this.field_175830_k.field_175966_c[EnumFacing.EAST.getIndex()]) {
                    this.fillWithBlocks(world, structureBoundingBox, 7, 3, 1, 7, 3, 6, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                    this.fillWithBlocks(world, structureBoundingBox, 7, 2, 1, 7, 2, 6, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, false);
                    this.fillWithBlocks(world, structureBoundingBox, 7, 1, 1, 7, 1, 6, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                }
            }
            else if (this.field_175833_o == 2) {
                this.fillWithBlocks(world, structureBoundingBox, 0, 1, 0, 0, 1, 7, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 7, 1, 0, 7, 1, 7, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 1, 1, 0, 6, 1, 0, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 1, 1, 7, 6, 1, 7, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 0, 2, 0, 0, 2, 7, SimpleRoom.field_175827_c, SimpleRoom.field_175827_c, false);
                this.fillWithBlocks(world, structureBoundingBox, 7, 2, 0, 7, 2, 7, SimpleRoom.field_175827_c, SimpleRoom.field_175827_c, false);
                this.fillWithBlocks(world, structureBoundingBox, 1, 2, 0, 6, 2, 0, SimpleRoom.field_175827_c, SimpleRoom.field_175827_c, false);
                this.fillWithBlocks(world, structureBoundingBox, 1, 2, 7, 6, 2, 7, SimpleRoom.field_175827_c, SimpleRoom.field_175827_c, false);
                this.fillWithBlocks(world, structureBoundingBox, 0, 3, 0, 0, 3, 7, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 7, 3, 0, 7, 3, 7, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 1, 3, 0, 6, 3, 0, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 1, 3, 7, 6, 3, 7, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 0, 1, 3, 0, 2, 4, SimpleRoom.field_175827_c, SimpleRoom.field_175827_c, false);
                this.fillWithBlocks(world, structureBoundingBox, 7, 1, 3, 7, 2, 4, SimpleRoom.field_175827_c, SimpleRoom.field_175827_c, false);
                this.fillWithBlocks(world, structureBoundingBox, 3, 1, 0, 4, 2, 0, SimpleRoom.field_175827_c, SimpleRoom.field_175827_c, false);
                this.fillWithBlocks(world, structureBoundingBox, 3, 1, 7, 4, 2, 7, SimpleRoom.field_175827_c, SimpleRoom.field_175827_c, false);
                if (this.field_175830_k.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
                    this.func_181655_a(world, structureBoundingBox, 3, 1, 0, 4, 2, 0, false);
                }
                if (this.field_175830_k.field_175966_c[EnumFacing.NORTH.getIndex()]) {
                    this.func_181655_a(world, structureBoundingBox, 3, 1, 7, 4, 2, 7, false);
                }
                if (this.field_175830_k.field_175966_c[EnumFacing.WEST.getIndex()]) {
                    this.func_181655_a(world, structureBoundingBox, 0, 1, 3, 0, 2, 4, false);
                }
                if (this.field_175830_k.field_175966_c[EnumFacing.EAST.getIndex()]) {
                    this.func_181655_a(world, structureBoundingBox, 7, 1, 3, 7, 2, 4, false);
                }
            }
            if (b) {
                this.fillWithBlocks(world, structureBoundingBox, 3, 1, 3, 4, 1, 4, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.fillWithBlocks(world, structureBoundingBox, 3, 2, 3, 4, 2, 4, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, false);
                this.fillWithBlocks(world, structureBoundingBox, 3, 3, 3, 4, 3, 4, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            }
            return true;
        }
    }
    
    public static class Penthouse extends Piece
    {
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            this.fillWithBlocks(world, structureBoundingBox, 2, -1, 2, 11, -1, 11, Penthouse.field_175826_b, Penthouse.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 0, -1, 0, 1, -1, 11, Penthouse.field_175828_a, Penthouse.field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 12, -1, 0, 13, -1, 11, Penthouse.field_175828_a, Penthouse.field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 2, -1, 0, 11, -1, 1, Penthouse.field_175828_a, Penthouse.field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 2, -1, 12, 11, -1, 13, Penthouse.field_175828_a, Penthouse.field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 0, 0, 13, Penthouse.field_175826_b, Penthouse.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 13, 0, 0, 13, 0, 13, Penthouse.field_175826_b, Penthouse.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 0, 0, 12, 0, 0, Penthouse.field_175826_b, Penthouse.field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 0, 13, 12, 0, 13, Penthouse.field_175826_b, Penthouse.field_175826_b, false);
            while (true) {
                this.setBlockState(world, Penthouse.field_175825_e, 0, 0, 10, structureBoundingBox);
                this.setBlockState(world, Penthouse.field_175825_e, 13, 0, 10, structureBoundingBox);
                this.setBlockState(world, Penthouse.field_175825_e, 10, 0, 0, structureBoundingBox);
                final int n;
                n += 3;
            }
        }
        
        public Penthouse() {
        }
        
        public Penthouse(final EnumFacing enumFacing, final StructureBoundingBox structureBoundingBox) {
            super(enumFacing, structureBoundingBox);
        }
    }
    
    public static class MonumentCoreRoom extends Piece
    {
        public MonumentCoreRoom(final EnumFacing enumFacing, final RoomDefinition roomDefinition, final Random random) {
            super(1, enumFacing, roomDefinition, 2, 2, 2);
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            this.func_175819_a(world, structureBoundingBox, 1, 8, 0, 14, 8, 14, MonumentCoreRoom.field_175828_a);
            final IBlockState field_175826_b = MonumentCoreRoom.field_175826_b;
            this.fillWithBlocks(world, structureBoundingBox, 0, 3, 0, 0, 3, 15, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 15, 3, 0, 15, 3, 15, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 3, 0, 15, 3, 0, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 3, 15, 14, 3, 15, field_175826_b, field_175826_b, false);
            final IBlockState field_175826_b2 = MonumentCoreRoom.field_175826_b;
            while (true) {
                this.fillWithBlocks(world, structureBoundingBox, 6, 3, 0, 6, 3, 1, field_175826_b2, field_175826_b2, false);
                this.fillWithBlocks(world, structureBoundingBox, 6, 3, 6, 6, 3, 9, field_175826_b2, field_175826_b2, false);
                this.fillWithBlocks(world, structureBoundingBox, 6, 3, 14, 6, 3, 15, field_175826_b2, field_175826_b2, false);
                final int n;
                n += 15;
            }
        }
        
        public MonumentCoreRoom() {
        }
    }
}
