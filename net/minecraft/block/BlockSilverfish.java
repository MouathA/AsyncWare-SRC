package net.minecraft.block;

import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import net.minecraft.block.properties.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockSilverfish extends Block
{
    public static final PropertyEnum VARIANT;
    
    @Override
    public int quantityDropped(final Random random) {
        return 0;
    }
    
    static {
        VARIANT = PropertyEnum.create("variant", EnumType.class);
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        final EnumType[] values = EnumType.values();
        while (0 < values.length) {
            list.add(new ItemStack(item, 1, values[0].getMetadata()));
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        if (!world.isRemote && world.getGameRules().getBoolean("doTileDrops")) {
            final EntitySilverfish entitySilverfish = new EntitySilverfish(world);
            entitySilverfish.setLocationAndAngles(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, 0.0f, 0.0f);
            world.spawnEntityInWorld(entitySilverfish);
            entitySilverfish.spawnExplosionParticle();
        }
    }
    
    @Override
    public int getDamageValue(final World world, final BlockPos blockPos) {
        final IBlockState blockState = world.getBlockState(blockPos);
        return blockState.getBlock().getMetaFromState(blockState);
    }
    
    @Override
    protected ItemStack createStackedBlock(final IBlockState blockState) {
        switch (BlockSilverfish$1.$SwitchMap$net$minecraft$block$BlockSilverfish$EnumType[((EnumType)blockState.getValue(BlockSilverfish.VARIANT)).ordinal()]) {
            case 1: {
                return new ItemStack(Blocks.cobblestone);
            }
            case 2: {
                return new ItemStack(Blocks.stonebrick);
            }
            case 3: {
                return new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.EnumType.MOSSY.getMetadata());
            }
            case 4: {
                return new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.EnumType.CRACKED.getMetadata());
            }
            case 5: {
                return new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.EnumType.CHISELED.getMetadata());
            }
            default: {
                return new ItemStack(Blocks.stone);
            }
        }
    }
    
    public BlockSilverfish() {
        super(Material.clay);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockSilverfish.VARIANT, EnumType.STONE));
        this.setHardness(0.0f);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockSilverfish.VARIANT)).getMetadata();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockSilverfish.VARIANT });
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockSilverfish.VARIANT, EnumType.byMetadata(n));
    }
    
    public static boolean canContainSilverfish(final IBlockState blockState) {
        final Block block = blockState.getBlock();
        return blockState == Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.STONE) || block == Blocks.cobblestone || block == Blocks.stonebrick;
    }
    
    public enum EnumType implements IStringSerializable
    {
        private final String name;
        private static final EnumType[] META_LOOKUP;
        
        CHISELED_STONEBRICK(5, "chiseled_brick", "chiseledbrick") {
            @Override
            public IBlockState getModelBlock() {
                return Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED);
            }
        };
        
        private final int meta;
        private final String unlocalizedName;
        
        MOSSY_STONEBRICK(3, "mossy_brick", "mossybrick") {
            @Override
            public IBlockState getModelBlock() {
                return Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.MOSSY);
            }
        }, 
        CRACKED_STONEBRICK(4, "cracked_brick", "crackedbrick") {
            @Override
            public IBlockState getModelBlock() {
                return Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CRACKED);
            }
        };
        
        private static final EnumType[] $VALUES;
        
        STONEBRICK(2, "stone_brick", "brick") {
            @Override
            public IBlockState getModelBlock() {
                return Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.DEFAULT);
            }
        }, 
        STONE(0, "stone") {
            @Override
            public IBlockState getModelBlock() {
                return Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.STONE);
            }
        }, 
        COBBLESTONE(1, "cobblestone", "cobble") {
            @Override
            public IBlockState getModelBlock() {
                return Blocks.cobblestone.getDefaultState();
            }
        };
        
        EnumType(final String s, final int n, final int n2, final String s2, final BlockSilverfish$1 object) {
            this(s, n, n2, s2);
        }
        
        public abstract IBlockState getModelBlock();
        
        @Override
        public String getName() {
            return this.name;
        }
        
        static {
            $VALUES = new EnumType[] { EnumType.STONE, EnumType.COBBLESTONE, EnumType.STONEBRICK, EnumType.MOSSY_STONEBRICK, EnumType.CRACKED_STONEBRICK, EnumType.CHISELED_STONEBRICK };
            META_LOOKUP = new EnumType[values().length];
            final EnumType[] values = values();
            while (0 < values.length) {
                final EnumType enumType = values[0];
                EnumType.META_LOOKUP[enumType.getMetadata()] = enumType;
                int n = 0;
                ++n;
            }
        }
        
        public static EnumType byMetadata(final int n) {
            if (0 >= EnumType.META_LOOKUP.length) {}
            return EnumType.META_LOOKUP[0];
        }
        
        private EnumType(final String s, final int n, final int n2, final String s2) {
            this(s, n, n2, s2, s2);
        }
        
        public static EnumType forModelBlock(final IBlockState blockState) {
            final EnumType[] values = values();
            while (0 < values.length) {
                final EnumType enumType = values[0];
                if (blockState == enumType.getModelBlock()) {
                    return enumType;
                }
                int n = 0;
                ++n;
            }
            return EnumType.STONE;
        }
        
        private EnumType(final String s, final int n, final int meta, final String name, final String unlocalizedName) {
            this.meta = meta;
            this.name = name;
            this.unlocalizedName = unlocalizedName;
        }
        
        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        public int getMetadata() {
            return this.meta;
        }
        
        EnumType(final String s, final int n, final int n2, final String s2, final String s3, final BlockSilverfish$1 object) {
            this(s, n, n2, s2, s3);
        }
    }
}
