package net.minecraft.block;

import com.google.common.base.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.item.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.init.*;
import net.minecraft.util.*;

public class BlockAnvil extends BlockFalling
{
    public static final PropertyInteger DAMAGE;
    public static final PropertyDirection FACING;
    
    static {
        FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
        DAMAGE = PropertyInteger.create("damage", 0, 2);
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 1));
        list.add(new ItemStack(item, 1, 2));
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        if (((EnumFacing)blockAccess.getBlockState(blockPos).getValue(BlockAnvil.FACING)).getAxis() == EnumFacing.Axis.X) {
            this.setBlockBounds(0.0f, 0.0f, 0.125f, 1.0f, 1.0f, 0.875f);
        }
        else {
            this.setBlockBounds(0.125f, 0.0f, 0.0f, 0.875f, 1.0f, 1.0f);
        }
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public IBlockState getStateForEntityRender(final IBlockState blockState) {
        return this.getDefaultState().withProperty(BlockAnvil.FACING, EnumFacing.SOUTH);
    }
    
    @Override
    protected void onStartFalling(final EntityFallingBlock entityFallingBlock) {
        entityFallingBlock.setHurtEntities(true);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        final int n = 0x0 | ((EnumFacing)blockState.getValue(BlockAnvil.FACING)).getHorizontalIndex();
        final int n2 = 0x0 | (int)blockState.getValue(BlockAnvil.DAMAGE) << 2;
        return 0;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockAnvil.FACING, EnumFacing.getHorizontal(n & 0x3)).withProperty(BlockAnvil.DAMAGE, (n & 0xF) >> 2);
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        return true;
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return (int)blockState.getValue(BlockAnvil.DAMAGE);
    }
    
    @Override
    public void onEndFalling(final World world, final BlockPos blockPos) {
        world.playAuxSFX(1022, blockPos, 0);
    }
    
    protected BlockAnvil() {
        super(Material.anvil);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockAnvil.FACING, EnumFacing.NORTH).withProperty(BlockAnvil.DAMAGE, 0));
        this.setLightOpacity(0);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockAnvil.FACING, BlockAnvil.DAMAGE });
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return super.onBlockPlaced(world, blockPos, enumFacing, n, n2, n3, n4, entityLivingBase).withProperty(BlockAnvil.FACING, entityLivingBase.getHorizontalFacing().rotateY()).withProperty(BlockAnvil.DAMAGE, n4 >> 2);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (!world.isRemote) {
            entityPlayer.displayGui(new Anvil(world, blockPos));
        }
        return true;
    }
    
    public static class Anvil implements IInteractionObject
    {
        private final BlockPos position;
        private final World world;
        
        @Override
        public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
            return new ContainerRepair(inventoryPlayer, this.world, this.position, entityPlayer);
        }
        
        public Anvil(final World world, final BlockPos position) {
            this.world = world;
            this.position = position;
        }
        
        @Override
        public IChatComponent getDisplayName() {
            return new ChatComponentTranslation(Blocks.anvil.getUnlocalizedName() + ".name", new Object[0]);
        }
        
        @Override
        public String getGuiID() {
            return "minecraft:anvil";
        }
        
        @Override
        public String getName() {
            return "anvil";
        }
        
        @Override
        public boolean hasCustomName() {
            return false;
        }
    }
}
