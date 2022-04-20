package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public abstract class BlockWoodSlab extends BlockSlab
{
    public static final PropertyEnum VARIANT;
    
    @Override
    public IProperty getVariantProperty() {
        return BlockWoodSlab.VARIANT;
    }
    
    public BlockWoodSlab() {
        super(Material.wood);
        IBlockState blockState = this.blockState.getBaseState();
        if (!this.isDouble()) {
            blockState = blockState.withProperty(BlockWoodSlab.HALF, EnumBlockHalf.BOTTOM);
        }
        this.setDefaultState(blockState.withProperty(BlockWoodSlab.VARIANT, BlockPlanks.EnumType.OAK));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return ((BlockPlanks.EnumType)blockState.getValue(BlockWoodSlab.VARIANT)).func_181070_c();
    }
    
    @Override
    public String getUnlocalizedName(final int n) {
        return super.getUnlocalizedName() + "." + BlockPlanks.EnumType.byMetadata(n).getUnlocalizedName();
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        final int n = 0x0 | ((BlockPlanks.EnumType)blockState.getValue(BlockWoodSlab.VARIANT)).getMetadata();
        if (this.isDouble() || blockState.getValue(BlockWoodSlab.HALF) == EnumBlockHalf.TOP) {}
        return 0;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        IBlockState blockState = this.getDefaultState().withProperty(BlockWoodSlab.VARIANT, BlockPlanks.EnumType.byMetadata(n & 0x7));
        if (!this.isDouble()) {
            blockState = blockState.withProperty(BlockWoodSlab.HALF, ((n & 0x8) == 0x0) ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);
        }
        return blockState;
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        if (item != Item.getItemFromBlock(Blocks.double_wooden_slab)) {
            final BlockPlanks.EnumType[] values = BlockPlanks.EnumType.values();
            while (0 < values.length) {
                list.add(new ItemStack(item, 1, values[0].getMetadata()));
                int n = 0;
                ++n;
            }
        }
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Item.getItemFromBlock(Blocks.wooden_slab);
    }
    
    @Override
    protected BlockState createBlockState() {
        return this.isDouble() ? new BlockState(this, new IProperty[] { BlockWoodSlab.VARIANT }) : new BlockState(this, new IProperty[] { BlockWoodSlab.HALF, BlockWoodSlab.VARIANT });
    }
    
    @Override
    public Object getVariant(final ItemStack itemStack) {
        return BlockPlanks.EnumType.byMetadata(itemStack.getMetadata() & 0x7);
    }
    
    static {
        VARIANT = PropertyEnum.create("variant", BlockPlanks.EnumType.class);
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Item.getItemFromBlock(Blocks.wooden_slab);
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((BlockPlanks.EnumType)blockState.getValue(BlockWoodSlab.VARIANT)).getMetadata();
    }
}
