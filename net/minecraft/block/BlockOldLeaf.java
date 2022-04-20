package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.init.*;
import net.minecraft.stats.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import com.google.common.base.*;
import net.minecraft.world.*;
import net.minecraft.creativetab.*;
import java.util.*;

public class BlockOldLeaf extends BlockLeaves
{
    public static final PropertyEnum VARIANT;
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        final int n = 0x0 | ((BlockPlanks.EnumType)blockState.getValue(BlockOldLeaf.VARIANT)).getMetadata();
        if (!(boolean)blockState.getValue(BlockOldLeaf.DECAYABLE)) {}
        if (blockState.getValue(BlockOldLeaf.CHECK_DECAY)) {}
        return 0;
    }
    
    @Override
    public BlockPlanks.EnumType getWoodType(final int n) {
        return BlockPlanks.EnumType.byMetadata((n & 0x3) % 4);
    }
    
    @Override
    public void harvestBlock(final World world, final EntityPlayer entityPlayer, final BlockPos blockPos, final IBlockState blockState, final TileEntity tileEntity) {
        if (!world.isRemote && entityPlayer.getCurrentEquippedItem() != null && entityPlayer.getCurrentEquippedItem().getItem() == Items.shears) {
            entityPlayer.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
            Block.spawnAsEntity(world, blockPos, new ItemStack(Item.getItemFromBlock(this), 1, ((BlockPlanks.EnumType)blockState.getValue(BlockOldLeaf.VARIANT)).getMetadata()));
        }
        else {
            super.harvestBlock(world, entityPlayer, blockPos, blockState, tileEntity);
        }
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockOldLeaf.VARIANT, this.getWoodType(n)).withProperty(BlockOldLeaf.DECAYABLE, (n & 0x4) == 0x0).withProperty(BlockOldLeaf.CHECK_DECAY, (n & 0x8) > 0);
    }
    
    public BlockOldLeaf() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.OAK).withProperty(BlockOldLeaf.CHECK_DECAY, true).withProperty(BlockOldLeaf.DECAYABLE, true));
    }
    
    @Override
    protected void dropApple(final World world, final BlockPos blockPos, final IBlockState blockState, final int n) {
        if (blockState.getValue(BlockOldLeaf.VARIANT) == BlockPlanks.EnumType.OAK && world.rand.nextInt(n) == 0) {
            Block.spawnAsEntity(world, blockPos, new ItemStack(Items.apple, 1, 0));
        }
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockOldLeaf.VARIANT, BlockOldLeaf.CHECK_DECAY, BlockOldLeaf.DECAYABLE });
    }
    
    @Override
    protected int getSaplingDropChance(final IBlockState blockState) {
        return (blockState.getValue(BlockOldLeaf.VARIANT) == BlockPlanks.EnumType.JUNGLE) ? 40 : super.getSaplingDropChance(blockState);
    }
    
    @Override
    protected ItemStack createStackedBlock(final IBlockState blockState) {
        return new ItemStack(Item.getItemFromBlock(this), 1, ((BlockPlanks.EnumType)blockState.getValue(BlockOldLeaf.VARIANT)).getMetadata());
    }
    
    @Override
    public int getRenderColor(final IBlockState blockState) {
        if (blockState.getBlock() != this) {
            return super.getRenderColor(blockState);
        }
        final BlockPlanks.EnumType enumType = (BlockPlanks.EnumType)blockState.getValue(BlockOldLeaf.VARIANT);
        return (enumType == BlockPlanks.EnumType.SPRUCE) ? ColorizerFoliage.getFoliageColorPine() : ((enumType == BlockPlanks.EnumType.BIRCH) ? ColorizerFoliage.getFoliageColorBirch() : super.getRenderColor(blockState));
    }
    
    static {
        VARIANT = PropertyEnum.create("variant", BlockPlanks.EnumType.class, (Predicate)new Predicate() {
            public boolean apply(final BlockPlanks.EnumType enumType) {
                return enumType.getMetadata() < 4;
            }
            
            public boolean apply(final Object o) {
                return this.apply((BlockPlanks.EnumType)o);
            }
        });
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        if (blockState.getBlock() == this) {
            final BlockPlanks.EnumType enumType = (BlockPlanks.EnumType)blockState.getValue(BlockOldLeaf.VARIANT);
            if (enumType == BlockPlanks.EnumType.SPRUCE) {
                return ColorizerFoliage.getFoliageColorPine();
            }
            if (enumType == BlockPlanks.EnumType.BIRCH) {
                return ColorizerFoliage.getFoliageColorBirch();
            }
        }
        return super.colorMultiplier(blockAccess, blockPos, n);
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        list.add(new ItemStack(item, 1, BlockPlanks.EnumType.OAK.getMetadata()));
        list.add(new ItemStack(item, 1, BlockPlanks.EnumType.SPRUCE.getMetadata()));
        list.add(new ItemStack(item, 1, BlockPlanks.EnumType.BIRCH.getMetadata()));
        list.add(new ItemStack(item, 1, BlockPlanks.EnumType.JUNGLE.getMetadata()));
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((BlockPlanks.EnumType)blockState.getValue(BlockOldLeaf.VARIANT)).getMetadata();
    }
}
