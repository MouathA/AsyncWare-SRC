package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.block.state.pattern.*;
import net.minecraft.block.material.*;
import com.google.common.cache.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.util.*;

public class BlockPortal extends BlockBreakable
{
    public static final PropertyEnum AXIS;
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return getMetaForAxis((EnumFacing.Axis)blockState.getValue(BlockPortal.AXIS));
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.TRANSLUCENT;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockPortal.AXIS });
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockPos, final IBlockState blockState, final Entity entity) {
        if (entity.ridingEntity == null && entity.riddenByEntity == null) {
            entity.func_181015_d(blockPos);
        }
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        EnumFacing.Axis axis = null;
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        if (blockAccess.getBlockState(blockPos).getBlock() == this) {
            axis = (EnumFacing.Axis)blockState.getValue(BlockPortal.AXIS);
            if (axis == null) {
                return false;
            }
            if (axis == EnumFacing.Axis.Z && enumFacing != EnumFacing.EAST && enumFacing != EnumFacing.WEST) {
                return false;
            }
            if (axis == EnumFacing.Axis.X && enumFacing != EnumFacing.SOUTH && enumFacing != EnumFacing.NORTH) {
                return false;
            }
        }
        final boolean b = blockAccess.getBlockState(blockPos.west()).getBlock() == this && blockAccess.getBlockState(blockPos.west(2)).getBlock() != this;
        final boolean b2 = blockAccess.getBlockState(blockPos.east()).getBlock() == this && blockAccess.getBlockState(blockPos.east(2)).getBlock() != this;
        final boolean b3 = blockAccess.getBlockState(blockPos.north()).getBlock() == this && blockAccess.getBlockState(blockPos.north(2)).getBlock() != this;
        final boolean b4 = blockAccess.getBlockState(blockPos.south()).getBlock() == this && blockAccess.getBlockState(blockPos.south(2)).getBlock() != this;
        final boolean b5 = b || b2 || axis == EnumFacing.Axis.X;
        final boolean b6 = b3 || b4 || axis == EnumFacing.Axis.Z;
        return (b5 && enumFacing == EnumFacing.WEST) || (b5 && enumFacing == EnumFacing.EAST) || (b6 && enumFacing == EnumFacing.NORTH) || (b6 && enumFacing == EnumFacing.SOUTH);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final EnumFacing.Axis axis = (EnumFacing.Axis)blockAccess.getBlockState(blockPos).getValue(BlockPortal.AXIS);
        float n = 0.125f;
        float n2 = 0.125f;
        if (axis == EnumFacing.Axis.X) {
            n = 0.5f;
        }
        if (axis == EnumFacing.Axis.Z) {
            n2 = 0.5f;
        }
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n2, 0.5f + n, 1.0f, 0.5f + n2);
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        super.updateTick(world, blockPos, blockState, random);
        if (world.provider.isSurfaceWorld() && world.getGameRules().getBoolean("doMobSpawning") && random.nextInt(2000) < world.getDifficulty().getDifficultyId()) {
            final int y = blockPos.getY();
            BlockPos down;
            for (down = blockPos; !World.doesBlockHaveSolidTopSurface(world, down) && down.getY() > 0; down = down.down()) {}
            if (y > 0 && !world.getBlockState(down.up()).getBlock().isNormalCube()) {
                final Entity spawnCreature = ItemMonsterPlacer.spawnCreature(world, 57, down.getX() + 0.5, down.getY() + 1.1, down.getZ() + 0.5);
                if (spawnCreature != null) {
                    spawnCreature.timeUntilPortal = spawnCreature.getPortalCooldown();
                }
            }
        }
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockPortal.AXIS, ((n & 0x3) == 0x2) ? EnumFacing.Axis.Z : EnumFacing.Axis.X);
    }
    
    public BlockPattern.PatternHelper func_181089_f(final World world, final BlockPos blockPos) {
        EnumFacing.Axis axis = EnumFacing.Axis.Z;
        Size size = new Size(world, blockPos, EnumFacing.Axis.X);
        final LoadingCache func_181627_a = BlockPattern.func_181627_a(world, true);
        if (!size.func_150860_b()) {
            axis = EnumFacing.Axis.X;
            size = new Size(world, blockPos, EnumFacing.Axis.Z);
        }
        if (!size.func_150860_b()) {
            return new BlockPattern.PatternHelper(blockPos, EnumFacing.NORTH, EnumFacing.UP, func_181627_a, 1, 1, 1);
        }
        final int[] array = new int[EnumFacing.AxisDirection.values().length];
        final EnumFacing rotateYCCW = Size.access$300(size).rotateYCCW();
        final BlockPos up = Size.access$400(size).up(size.func_181100_a() - 1);
        final EnumFacing.AxisDirection[] values = EnumFacing.AxisDirection.values();
        while (0 < values.length) {
            final EnumFacing.AxisDirection axisDirection = values[0];
            final BlockPattern.PatternHelper patternHelper = new BlockPattern.PatternHelper((rotateYCCW.getAxisDirection() == axisDirection) ? up : up.offset(Size.access$300(size), size.func_181101_b() - 1), EnumFacing.func_181076_a(axisDirection, axis), EnumFacing.UP, func_181627_a, size.func_181101_b(), size.func_181100_a(), 1);
            while (0 < size.func_181101_b()) {
                while (0 < size.func_181100_a()) {
                    final BlockWorldState translateOffset = patternHelper.translateOffset(0, 0, 1);
                    if (translateOffset.getBlockState() != null && translateOffset.getBlockState().getBlock().getMaterial() != Material.air) {
                        final int[] array2 = array;
                        final int ordinal = axisDirection.ordinal();
                        ++array2[ordinal];
                    }
                    int n = 0;
                    ++n;
                }
                int n2 = 0;
                ++n2;
            }
            int length = 0;
            ++length;
        }
        final EnumFacing.AxisDirection positive = EnumFacing.AxisDirection.POSITIVE;
        int length = EnumFacing.AxisDirection.values().length;
        return new BlockPattern.PatternHelper((rotateYCCW.getAxisDirection() == positive) ? up : up.offset(Size.access$300(size), size.func_181101_b() - 1), EnumFacing.func_181076_a(positive, axis), EnumFacing.UP, func_181627_a, size.func_181101_b(), size.func_181100_a(), 1);
    }
    
    public static int getMetaForAxis(final EnumFacing.Axis axis) {
        return (axis == EnumFacing.Axis.X) ? 1 : ((axis == EnumFacing.Axis.Z) ? 2 : 0);
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (random.nextInt(100) == 0) {
            world.playSound(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, "portal.portal", 0.5f, random.nextFloat() * 0.4f + 0.8f, false);
        }
        while (true) {
            double n = blockPos.getX() + random.nextFloat();
            final double n2 = blockPos.getY() + random.nextFloat();
            double n3 = blockPos.getZ() + random.nextFloat();
            double n4 = (random.nextFloat() - 0.5) * 0.5;
            final double n5 = (random.nextFloat() - 0.5) * 0.5;
            double n6 = (random.nextFloat() - 0.5) * 0.5;
            final int n7 = random.nextInt(2) * 2 - 1;
            if (world.getBlockState(blockPos.west()).getBlock() != this && world.getBlockState(blockPos.east()).getBlock() != this) {
                n = blockPos.getX() + 0.5 + 0.25 * n7;
                n4 = random.nextFloat() * 2.0f * n7;
            }
            else {
                n3 = blockPos.getZ() + 0.5 + 0.25 * n7;
                n6 = random.nextFloat() * 2.0f * n7;
            }
            world.spawnParticle(EnumParticleTypes.PORTAL, n, n2, n3, n4, n5, n6, new int[0]);
            int n8 = 0;
            ++n8;
        }
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return 0;
    }
    
    public BlockPortal() {
        super(Material.portal, false);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockPortal.AXIS, EnumFacing.Axis.X));
        this.setTickRandomly(true);
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return null;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        final EnumFacing.Axis axis = (EnumFacing.Axis)blockState.getValue(BlockPortal.AXIS);
        if (axis == EnumFacing.Axis.X) {
            final Size size = new Size(world, blockPos, EnumFacing.Axis.X);
            if (!size.func_150860_b() || Size.access$000(size) < Size.access$100(size) * Size.access$200(size)) {
                world.setBlockState(blockPos, Blocks.air.getDefaultState());
            }
        }
        else if (axis == EnumFacing.Axis.Z) {
            final Size size2 = new Size(world, blockPos, EnumFacing.Axis.Z);
            if (!size2.func_150860_b() || Size.access$000(size2) < Size.access$100(size2) * Size.access$200(size2)) {
                world.setBlockState(blockPos, Blocks.air.getDefaultState());
            }
        }
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    public boolean func_176548_d(final World world, final BlockPos blockPos) {
        final Size size = new Size(world, blockPos, EnumFacing.Axis.X);
        if (size.func_150860_b() && Size.access$000(size) == 0) {
            size.func_150859_c();
            return true;
        }
        final Size size2 = new Size(world, blockPos, EnumFacing.Axis.Z);
        if (size2.func_150860_b() && Size.access$000(size2) == 0) {
            size2.func_150859_c();
            return true;
        }
        return false;
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    static {
        AXIS = PropertyEnum.create("axis", EnumFacing.Axis.class, EnumFacing.Axis.X, EnumFacing.Axis.Z);
    }
    
    public static class Size
    {
        private int field_150862_g;
        private final EnumFacing field_150863_d;
        private final EnumFacing.Axis axis;
        private int field_150864_e;
        private BlockPos field_150861_f;
        private final EnumFacing field_150866_c;
        private int field_150868_h;
        private final World world;
        
        static int access$100(final Size size) {
            return size.field_150868_h;
        }
        
        static EnumFacing access$300(final Size size) {
            return size.field_150866_c;
        }
        
        public void func_150859_c() {
            while (0 < this.field_150868_h) {
                final BlockPos offset = this.field_150861_f.offset(this.field_150866_c, 0);
                while (0 < this.field_150862_g) {
                    this.world.setBlockState(offset.up(0), Blocks.portal.getDefaultState().withProperty(BlockPortal.AXIS, this.axis), 2);
                    int n = 0;
                    ++n;
                }
                int n2 = 0;
                ++n2;
            }
        }
        
        protected int func_150858_a() {
            this.field_150862_g = 0;
            int n = 0;
        Label_0172:
            while (this.field_150862_g < 21) {
                while (0 < this.field_150868_h) {
                    final BlockPos up = this.field_150861_f.offset(this.field_150866_c, 0).up(this.field_150862_g);
                    final Block block = this.world.getBlockState(up).getBlock();
                    if (this != block) {
                        break Label_0172;
                    }
                    if (block == Blocks.portal) {
                        ++this.field_150864_e;
                    }
                    if (this.world.getBlockState(up.offset(this.field_150863_d)).getBlock() != Blocks.obsidian) {
                        break Label_0172;
                    }
                    ++n;
                }
                ++this.field_150862_g;
            }
            while (0 < this.field_150868_h) {
                if (this.world.getBlockState(this.field_150861_f.offset(this.field_150866_c, 0).up(this.field_150862_g)).getBlock() != Blocks.obsidian) {
                    this.field_150862_g = 0;
                    break;
                }
                ++n;
            }
            if (this.field_150862_g <= 21 && this.field_150862_g >= 3) {
                return this.field_150862_g;
            }
            this.field_150861_f = null;
            this.field_150868_h = 0;
            return this.field_150862_g = 0;
        }
        
        public int func_181100_a() {
            return this.field_150862_g;
        }
        
        public Size(final World world, BlockPos down, final EnumFacing.Axis axis) {
            this.field_150864_e = 0;
            this.world = world;
            this.axis = axis;
            if (axis == EnumFacing.Axis.X) {
                this.field_150863_d = EnumFacing.EAST;
                this.field_150866_c = EnumFacing.WEST;
            }
            else {
                this.field_150863_d = EnumFacing.NORTH;
                this.field_150866_c = EnumFacing.SOUTH;
            }
            while (down.getY() > down.getY() - 21 && down.getY() > 0 && this != world.getBlockState(down.down()).getBlock()) {
                down = down.down();
            }
            final int n = this.func_180120_a(down, this.field_150863_d) - 1;
            if (n >= 0) {
                this.field_150861_f = down.offset(this.field_150863_d, n);
                this.field_150868_h = this.func_180120_a(this.field_150861_f, this.field_150866_c);
                if (this.field_150868_h < 2 || this.field_150868_h > 21) {
                    this.field_150861_f = null;
                    this.field_150868_h = 0;
                }
            }
            if (this.field_150861_f != null) {
                this.field_150862_g = this.func_150858_a();
            }
        }
        
        protected int func_180120_a(final BlockPos blockPos, final EnumFacing enumFacing) {
            while (true) {
                final BlockPos offset = blockPos.offset(enumFacing, 0);
                if (this == this.world.getBlockState(offset).getBlock() || this.world.getBlockState(offset.down()).getBlock() != Blocks.obsidian) {
                    break;
                }
                int n = 0;
                ++n;
            }
            return (this.world.getBlockState(blockPos.offset(enumFacing, 0)).getBlock() == Blocks.obsidian && false) ? 1 : 0;
        }
        
        public int func_181101_b() {
            return this.field_150868_h;
        }
        
        static int access$200(final Size size) {
            return size.field_150862_g;
        }
        
        static BlockPos access$400(final Size size) {
            return size.field_150861_f;
        }
        
        static int access$000(final Size size) {
            return size.field_150864_e;
        }
        
        public boolean func_150860_b() {
            return this.field_150861_f != null && this.field_150868_h >= 2 && this.field_150868_h <= 21 && this.field_150862_g >= 3 && this.field_150862_g <= 21;
        }
    }
}
