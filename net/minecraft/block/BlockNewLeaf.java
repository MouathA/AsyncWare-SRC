package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.item.*;
import net.minecraft.creativetab.*;
import java.util.*;
import com.google.common.base.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import net.minecraft.stats.*;

public class BlockNewLeaf extends BlockLeaves
{
    public static final PropertyEnum VARIANT;
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((BlockPlanks.EnumType)blockState.getValue(BlockNewLeaf.VARIANT)).getMetadata();
    }
    
    @Override
    protected ItemStack createStackedBlock(final IBlockState blockState) {
        return new ItemStack(Item.getItemFromBlock(this), 1, ((BlockPlanks.EnumType)blockState.getValue(BlockNewLeaf.VARIANT)).getMetadata() - 4);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        final int n = 0x0 | ((BlockPlanks.EnumType)blockState.getValue(BlockNewLeaf.VARIANT)).getMetadata() - 4;
        if (!(boolean)blockState.getValue(BlockNewLeaf.DECAYABLE)) {}
        if (blockState.getValue(BlockNewLeaf.CHECK_DECAY)) {}
        return 0;
    }
    
    public BlockNewLeaf() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockNewLeaf.VARIANT, BlockPlanks.EnumType.ACACIA).withProperty(BlockNewLeaf.CHECK_DECAY, true).withProperty(BlockNewLeaf.DECAYABLE, true));
    }
    
    @Override
    public BlockPlanks.EnumType getWoodType(final int n) {
        return BlockPlanks.EnumType.byMetadata((n & 0x3) + 4);
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 1));
    }
    
    static {
        VARIANT = PropertyEnum.create("variant", BlockPlanks.EnumType.class, (Predicate)new Predicate() {
            public boolean apply(final BlockPlanks.EnumType enumType) {
                return enumType.getMetadata() >= 4;
            }
            
            public boolean apply(final Object o) {
                return this.apply((BlockPlanks.EnumType)o);
            }
        });
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockNewLeaf.VARIANT, BlockNewLeaf.CHECK_DECAY, BlockNewLeaf.DECAYABLE });
    }
    
    @Override
    public int getDamageValue(final World world, final BlockPos blockPos) {
        final IBlockState blockState = world.getBlockState(blockPos);
        return blockState.getBlock().getMetaFromState(blockState) & 0x3;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockNewLeaf.VARIANT, this.getWoodType(n)).withProperty(BlockNewLeaf.DECAYABLE, (n & 0x4) == 0x0).withProperty(BlockNewLeaf.CHECK_DECAY, (n & 0x8) > 0);
    }
    
    @Override
    protected void dropApple(final World world, final BlockPos blockPos, final IBlockState blockState, final int n) {
        if (blockState.getValue(BlockNewLeaf.VARIANT) == BlockPlanks.EnumType.DARK_OAK && world.rand.nextInt(n) == 0) {
            Block.spawnAsEntity(world, blockPos, new ItemStack(Items.apple, 1, 0));
        }
    }
    
    @Override
    public void harvestBlock(final World world, final EntityPlayer entityPlayer, final BlockPos blockPos, final IBlockState blockState, final TileEntity tileEntity) {
        if (!world.isRemote && entityPlayer.getCurrentEquippedItem() != null && entityPlayer.getCurrentEquippedItem().getItem() == Items.shears) {
            entityPlayer.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
            Block.spawnAsEntity(world, blockPos, new ItemStack(Item.getItemFromBlock(this), 1, ((BlockPlanks.EnumType)blockState.getValue(BlockNewLeaf.VARIANT)).getMetadata() - 4));
        }
        else {
            super.harvestBlock(world, entityPlayer, blockPos, blockState, tileEntity);
        }
    }
}
