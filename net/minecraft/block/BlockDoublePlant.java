package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import net.minecraft.world.biome.*;
import net.minecraft.entity.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;

public class BlockDoublePlant extends BlockBush implements IGrowable
{
    public static final PropertyEnum VARIANT;
    public static final PropertyEnum HALF;
    public static final PropertyEnum field_181084_N;
    
    @Override
    protected void checkAndDropBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (blockPos == blockState) {
            final boolean b = blockState.getValue(BlockDoublePlant.HALF) == EnumBlockHalf.UPPER;
            final BlockPos blockPos2 = b ? blockPos : blockPos.up();
            final BlockPos blockPos3 = b ? blockPos.down() : blockPos;
            final Block block = b ? this : world.getBlockState(blockPos2).getBlock();
            final Block block2 = b ? world.getBlockState(blockPos3).getBlock() : this;
            if (block == this) {
                world.setBlockState(blockPos2, Blocks.air.getDefaultState(), 2);
            }
            if (block2 == this) {
                world.setBlockState(blockPos3, Blocks.air.getDefaultState(), 3);
                if (!b) {
                    this.dropBlockAsItem(world, blockPos3, blockState, 0);
                }
            }
        }
    }
    
    @Override
    public int getDamageValue(final World world, final BlockPos blockPos) {
        return this.getVariant(world, blockPos).getMeta();
    }
    
    static {
        VARIANT = PropertyEnum.create("variant", EnumPlantType.class);
        HALF = PropertyEnum.create("half", EnumBlockHalf.class);
        field_181084_N = BlockDirectional.FACING;
    }
    
    @Override
    public void grow(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        Block.spawnAsEntity(world, blockPos, new ItemStack(this, 1, this.getVariant(world, blockPos).getMeta()));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return (blockState.getValue(BlockDoublePlant.HALF) == EnumBlockHalf.UPPER) ? (0x8 | ((EnumFacing)blockState.getValue(BlockDoublePlant.field_181084_N)).getHorizontalIndex()) : ((EnumPlantType)blockState.getValue(BlockDoublePlant.VARIANT)).getMeta();
    }
    
    @Override
    public EnumOffsetType getOffsetType() {
        return EnumOffsetType.XZ;
    }
    
    @Override
    public void onBlockHarvested(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer) {
        if (blockState.getValue(BlockDoublePlant.HALF) == EnumBlockHalf.UPPER) {
            if (world.getBlockState(blockPos.down()).getBlock() == this) {
                if (!entityPlayer.capabilities.isCreativeMode) {
                    final IBlockState blockState2 = world.getBlockState(blockPos.down());
                    final EnumPlantType enumPlantType = (EnumPlantType)blockState2.getValue(BlockDoublePlant.VARIANT);
                    if (enumPlantType != EnumPlantType.FERN && enumPlantType != EnumPlantType.GRASS) {
                        world.destroyBlock(blockPos.down(), true);
                    }
                    else if (!world.isRemote) {
                        if (entityPlayer.getCurrentEquippedItem() != null && entityPlayer.getCurrentEquippedItem().getItem() == Items.shears) {
                            this.onHarvest(world, blockPos, blockState2, entityPlayer);
                            world.setBlockToAir(blockPos.down());
                        }
                        else {
                            world.destroyBlock(blockPos.down(), true);
                        }
                    }
                    else {
                        world.setBlockToAir(blockPos.down());
                    }
                }
                else {
                    world.setBlockToAir(blockPos.down());
                }
            }
        }
        else if (entityPlayer.capabilities.isCreativeMode && world.getBlockState(blockPos.up()).getBlock() == this) {
            world.setBlockState(blockPos.up(), Blocks.air.getDefaultState(), 2);
        }
        super.onBlockHarvested(world, blockPos, blockState, entityPlayer);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return ((n & 0x8) > 0) ? this.getDefaultState().withProperty(BlockDoublePlant.HALF, EnumBlockHalf.UPPER) : this.getDefaultState().withProperty(BlockDoublePlant.HALF, EnumBlockHalf.LOWER).withProperty(BlockDoublePlant.VARIANT, EnumPlantType.byMetadata(n & 0x7));
    }
    
    public BlockDoublePlant() {
        super(Material.vine);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockDoublePlant.VARIANT, EnumPlantType.SUNFLOWER).withProperty(BlockDoublePlant.HALF, EnumBlockHalf.LOWER).withProperty(BlockDoublePlant.field_181084_N, EnumFacing.NORTH));
        this.setHardness(0.0f);
        this.setStepSound(BlockDoublePlant.soundTypeGrass);
        this.setUnlocalizedName("doublePlant");
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockDoublePlant.HALF, BlockDoublePlant.VARIANT, BlockDoublePlant.field_181084_N });
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        if (blockState.getValue(BlockDoublePlant.HALF) == EnumBlockHalf.UPPER) {
            return null;
        }
        final EnumPlantType enumPlantType = (EnumPlantType)blockState.getValue(BlockDoublePlant.VARIANT);
        return (enumPlantType == EnumPlantType.FERN) ? null : ((enumPlantType == EnumPlantType.GRASS) ? ((random.nextInt(8) == 0) ? Items.wheat_seeds : null) : Item.getItemFromBlock(this));
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return super.canPlaceBlockAt(world, blockPos) && world.isAirBlock(blockPos.up());
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public EnumPlantType getVariant(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        if (blockState.getBlock() == this) {
            return (EnumPlantType)this.getActualState(blockState, blockAccess, blockPos).getValue(BlockDoublePlant.VARIANT);
        }
        return EnumPlantType.FERN;
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        final EnumPlantType variant = this.getVariant(blockAccess, blockPos);
        return (variant != EnumPlantType.GRASS && variant != EnumPlantType.FERN) ? 16777215 : BiomeColorHelper.getGrassColorAtPos(blockAccess, blockPos);
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return (blockState.getValue(BlockDoublePlant.HALF) != EnumBlockHalf.UPPER && blockState.getValue(BlockDoublePlant.VARIANT) != EnumPlantType.GRASS) ? ((EnumPlantType)blockState.getValue(BlockDoublePlant.VARIANT)).getMeta() : 0;
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        world.setBlockState(blockPos.up(), this.getDefaultState().withProperty(BlockDoublePlant.HALF, EnumBlockHalf.UPPER), 2);
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        final EnumPlantType[] values = EnumPlantType.values();
        while (0 < values.length) {
            list.add(new ItemStack(item, 1, values[0].getMeta()));
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void harvestBlock(final World p0, final EntityPlayer p1, final BlockPos p2, final IBlockState p3, final TileEntity p4) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/world/World.isRemote:Z
        //     4: ifne            52
        //     7: aload_2        
        //     8: invokevirtual   net/minecraft/entity/player/EntityPlayer.getCurrentEquippedItem:()Lnet/minecraft/item/ItemStack;
        //    11: ifnull          52
        //    14: aload_2        
        //    15: invokevirtual   net/minecraft/entity/player/EntityPlayer.getCurrentEquippedItem:()Lnet/minecraft/item/ItemStack;
        //    18: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //    21: getstatic       net/minecraft/init/Items.shears:Lnet/minecraft/item/ItemShears;
        //    24: if_acmpne       52
        //    27: aload           4
        //    29: getstatic       net/minecraft/block/BlockDoublePlant.HALF:Lnet/minecraft/block/properties/PropertyEnum;
        //    32: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //    37: getstatic       net/minecraft/block/BlockDoublePlant$EnumBlockHalf.LOWER:Lnet/minecraft/block/BlockDoublePlant$EnumBlockHalf;
        //    40: if_acmpne       52
        //    43: aload_0        
        //    44: aload_1        
        //    45: aload_3        
        //    46: aload           4
        //    48: aload_2        
        //    49: if_acmpeq       63
        //    52: aload_0        
        //    53: aload_1        
        //    54: aload_2        
        //    55: aload_3        
        //    56: aload           4
        //    58: aload           5
        //    60: invokespecial   net/minecraft/block/BlockBush.harvestBlock:(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/tileentity/TileEntity;)V
        //    63: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0052 (coming from #0049).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
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
    
    @Override
    public boolean isReplaceable(final World world, final BlockPos blockPos) {
        final IBlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() != this) {
            return true;
        }
        final EnumPlantType enumPlantType = (EnumPlantType)this.getActualState(blockState, world, blockPos).getValue(BlockDoublePlant.VARIANT);
        return enumPlantType == EnumPlantType.FERN || enumPlantType == EnumPlantType.GRASS;
    }
    
    @Override
    public IBlockState getActualState(IBlockState withProperty, final IBlockAccess blockAccess, final BlockPos blockPos) {
        if (withProperty.getValue(BlockDoublePlant.HALF) == EnumBlockHalf.UPPER) {
            final IBlockState blockState = blockAccess.getBlockState(blockPos.down());
            if (blockState.getBlock() == this) {
                withProperty = withProperty.withProperty(BlockDoublePlant.VARIANT, blockState.getValue(BlockDoublePlant.VARIANT));
            }
        }
        return withProperty;
    }
    
    public void placeAt(final World world, final BlockPos blockPos, final EnumPlantType enumPlantType, final int n) {
        world.setBlockState(blockPos, this.getDefaultState().withProperty(BlockDoublePlant.HALF, EnumBlockHalf.LOWER).withProperty(BlockDoublePlant.VARIANT, enumPlantType), n);
        world.setBlockState(blockPos.up(), this.getDefaultState().withProperty(BlockDoublePlant.HALF, EnumBlockHalf.UPPER), n);
    }
    
    @Override
    public boolean canUseBonemeal(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        return true;
    }
    
    @Override
    public boolean canGrow(final World world, final BlockPos blockPos, final IBlockState blockState, final boolean b) {
        final EnumPlantType variant = this.getVariant(world, blockPos);
        return variant != EnumPlantType.GRASS && variant != EnumPlantType.FERN;
    }
    
    public enum EnumBlockHalf implements IStringSerializable
    {
        LOWER("LOWER", 1);
        
        private static final EnumBlockHalf[] $VALUES;
        
        UPPER("UPPER", 0);
        
        @Override
        public String getName() {
            return (this == EnumBlockHalf.UPPER) ? "upper" : "lower";
        }
        
        static {
            $VALUES = new EnumBlockHalf[] { EnumBlockHalf.UPPER, EnumBlockHalf.LOWER };
        }
        
        private EnumBlockHalf(final String s, final int n) {
        }
        
        @Override
        public String toString() {
            return this.getName();
        }
    }
    
    public enum EnumPlantType implements IStringSerializable
    {
        FERN("FERN", 3, 3, "double_fern", "fern");
        
        private final int meta;
        private static final EnumPlantType[] META_LOOKUP;
        
        SYRINGA("SYRINGA", 1, 1, "syringa"), 
        PAEONIA("PAEONIA", 5, 5, "paeonia");
        
        private static final EnumPlantType[] $VALUES;
        
        SUNFLOWER("SUNFLOWER", 0, 0, "sunflower"), 
        GRASS("GRASS", 2, 2, "double_grass", "grass");
        
        private final String unlocalizedName;
        
        ROSE("ROSE", 4, 4, "double_rose", "rose");
        
        private final String name;
        
        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }
        
        private EnumPlantType(final String s, final int n, final int n2, final String s2) {
            this(s, n, n2, s2, s2);
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        public int getMeta() {
            return this.meta;
        }
        
        static {
            $VALUES = new EnumPlantType[] { EnumPlantType.SUNFLOWER, EnumPlantType.SYRINGA, EnumPlantType.GRASS, EnumPlantType.FERN, EnumPlantType.ROSE, EnumPlantType.PAEONIA };
            META_LOOKUP = new EnumPlantType[values().length];
            final EnumPlantType[] values = values();
            while (0 < values.length) {
                final EnumPlantType enumPlantType = values[0];
                EnumPlantType.META_LOOKUP[enumPlantType.getMeta()] = enumPlantType;
                int n = 0;
                ++n;
            }
        }
        
        public static EnumPlantType byMetadata(final int n) {
            if (0 >= EnumPlantType.META_LOOKUP.length) {}
            return EnumPlantType.META_LOOKUP[0];
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        private EnumPlantType(final String s, final int n, final int meta, final String name, final String unlocalizedName) {
            this.meta = meta;
            this.name = name;
            this.unlocalizedName = unlocalizedName;
        }
    }
}
