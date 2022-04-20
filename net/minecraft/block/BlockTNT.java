package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class BlockTNT extends Block
{
    public static final PropertyBool EXPLODE;
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockTNT.EXPLODE });
    }
    
    public BlockTNT() {
        super(Material.tnt);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockTNT.EXPLODE, false));
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    static {
        EXPLODE = PropertyBool.create("explode");
    }
    
    @Override
    public boolean canDropFromExplosion(final Explosion explosion) {
        return false;
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockToAir, final IBlockState blockState) {
        super.onBlockAdded(world, blockToAir, blockState);
        if (world.isBlockPowered(blockToAir)) {
            this.onBlockDestroyedByPlayer(world, blockToAir, blockState.withProperty(BlockTNT.EXPLODE, true));
            world.setBlockToAir(blockToAir);
        }
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((boolean)blockState.getValue(BlockTNT.EXPLODE)) ? 1 : 0;
    }
    
    public void explode(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase) {
        if (!world.isRemote && (boolean)blockState.getValue(BlockTNT.EXPLODE)) {
            final EntityTNTPrimed entityTNTPrimed = new EntityTNTPrimed(world, blockPos.getX() + 0.5f, blockPos.getY(), blockPos.getZ() + 0.5f, entityLivingBase);
            world.spawnEntityInWorld(entityTNTPrimed);
            world.playSoundAtEntity(entityTNTPrimed, "game.tnt.primed", 1.0f, 1.0f);
        }
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockToAir, final IBlockState blockState, final Entity entity) {
        if (!world.isRemote && entity instanceof EntityArrow) {
            final EntityArrow entityArrow = (EntityArrow)entity;
            if (entityArrow.isBurning()) {
                this.explode(world, blockToAir, world.getBlockState(blockToAir).withProperty(BlockTNT.EXPLODE, true), (entityArrow.shootingEntity instanceof EntityLivingBase) ? ((EntityLivingBase)entityArrow.shootingEntity) : null);
                world.setBlockToAir(blockToAir);
            }
        }
    }
    
    @Override
    public void onBlockDestroyedByPlayer(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.explode(world, blockPos, blockState, null);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (world.isBlockPowered(blockToAir)) {
            this.onBlockDestroyedByPlayer(world, blockToAir, blockState.withProperty(BlockTNT.EXPLODE, true));
            world.setBlockToAir(blockToAir);
        }
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockTNT.EXPLODE, (n & 0x1) > 0);
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockToAir, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (entityPlayer.getCurrentEquippedItem() != null) {
            final Item item = entityPlayer.getCurrentEquippedItem().getItem();
            if (item == Items.flint_and_steel || item == Items.fire_charge) {
                this.explode(world, blockToAir, blockState.withProperty(BlockTNT.EXPLODE, true), entityPlayer);
                world.setBlockToAir(blockToAir);
                if (item == Items.flint_and_steel) {
                    entityPlayer.getCurrentEquippedItem().damageItem(1, entityPlayer);
                }
                else if (!entityPlayer.capabilities.isCreativeMode) {
                    final ItemStack currentEquippedItem = entityPlayer.getCurrentEquippedItem();
                    --currentEquippedItem.stackSize;
                }
                return true;
            }
        }
        return super.onBlockActivated(world, blockToAir, blockState, entityPlayer, enumFacing, n, n2, n3);
    }
    
    @Override
    public void onBlockDestroyedByExplosion(final World world, final BlockPos blockPos, final Explosion explosion) {
        if (!world.isRemote) {
            final EntityTNTPrimed entityTNTPrimed = new EntityTNTPrimed(world, blockPos.getX() + 0.5f, blockPos.getY(), blockPos.getZ() + 0.5f, explosion.getExplosivePlacedBy());
            entityTNTPrimed.fuse = world.rand.nextInt(entityTNTPrimed.fuse / 4) + entityTNTPrimed.fuse / 8;
            world.spawnEntityInWorld(entityTNTPrimed);
        }
    }
}
