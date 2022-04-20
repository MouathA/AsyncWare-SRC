package net.minecraft.block;

import java.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.state.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;

public class BlockNote extends BlockContainer
{
    private static final List INSTRUMENTS;
    
    @Override
    public void onBlockClicked(final World world, final BlockPos blockPos, final EntityPlayer entityPlayer) {
        if (!world.isRemote) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityNote) {
                ((TileEntityNote)tileEntity).triggerNote(world, blockPos);
                entityPlayer.triggerAchievement(StatList.field_181734_R);
            }
        }
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityNote();
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return true;
        }
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityNote) {
            final TileEntityNote tileEntityNote = (TileEntityNote)tileEntity;
            tileEntityNote.changePitch();
            tileEntityNote.triggerNote(world, blockPos);
            entityPlayer.triggerAchievement(StatList.field_181735_S);
        }
        return true;
    }
    
    static {
        INSTRUMENTS = Lists.newArrayList((Object[])new String[] { "harp", "bd", "snare", "hat", "bassattack" });
    }
    
    @Override
    public boolean onBlockEventReceived(final World world, final BlockPos blockPos, final IBlockState blockState, final int n, final int n2) {
        world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, "note." + this.getInstrument(n), 3.0f, (float)Math.pow(2.0, (n2 - 12) / 12.0));
        world.spawnParticle(EnumParticleTypes.NOTE, blockPos.getX() + 0.5, blockPos.getY() + 1.2, blockPos.getZ() + 0.5, n2 / 24.0, 0.0, 0.0, new int[0]);
        return true;
    }
    
    private String getInstrument(final int n) {
        if (0 >= BlockNote.INSTRUMENTS.size()) {}
        return BlockNote.INSTRUMENTS.get(0);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        final boolean blockPowered = world.isBlockPowered(blockPos);
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityNote) {
            final TileEntityNote tileEntityNote = (TileEntityNote)tileEntity;
            if (tileEntityNote.previousRedstoneState != blockPowered) {
                if (blockPowered) {
                    tileEntityNote.triggerNote(world, blockPos);
                }
                tileEntityNote.previousRedstoneState = blockPowered;
            }
        }
    }
    
    @Override
    public int getRenderType() {
        return 3;
    }
    
    public BlockNote() {
        super(Material.wood);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
}
