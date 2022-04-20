package net.minecraft.block;

import java.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.stats.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockFlowerPot extends BlockContainer
{
    public static final PropertyInteger LEGACY_DATA;
    public static final PropertyEnum CONTENTS;
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final TileEntityFlowerPot tileEntity = this.getTileEntity(world, blockPos);
        if (tileEntity != null && tileEntity.getFlowerPotItem() != null) {
            Block.spawnAsEntity(world, blockPos, new ItemStack(tileEntity.getFlowerPotItem(), 1, tileEntity.getFlowerPotData()));
        }
        super.breakBlock(world, blockPos, blockState);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.flower_pot;
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        final float n = 0.375f;
        final float n2 = n / 2.0f;
        this.setBlockBounds(0.5f - n2, 0.0f, 0.5f - n2, 0.5f + n2, n, 0.5f + n2);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (!World.doesBlockHaveSolidTopSurface(world, blockToAir.down())) {
            this.dropBlockAsItem(world, blockToAir, blockState, 0);
            world.setBlockToAir(blockToAir);
        }
    }
    
    @Override
    public void onBlockHarvested(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer) {
        super.onBlockHarvested(world, blockPos, blockState, entityPlayer);
        if (entityPlayer.capabilities.isCreativeMode) {
            final TileEntityFlowerPot tileEntity = this.getTileEntity(world, blockPos);
            if (tileEntity != null) {
                tileEntity.setFlowerPotData(null, 0);
            }
        }
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        final TileEntity tileEntity = blockAccess.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityFlowerPot) {
            final Item flowerPotItem = ((TileEntityFlowerPot)tileEntity).getFlowerPotItem();
            if (flowerPotItem instanceof ItemBlock) {
                return Block.getBlockFromItem(flowerPotItem).colorMultiplier(blockAccess, blockPos, n);
            }
        }
        return 16777215;
    }
    
    @Override
    public IBlockState getActualState(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        EnumFlowerType enumFlowerType = EnumFlowerType.EMPTY;
        final TileEntity tileEntity = blockAccess.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityFlowerPot) {
            final TileEntityFlowerPot tileEntityFlowerPot = (TileEntityFlowerPot)tileEntity;
            final Item flowerPotItem = tileEntityFlowerPot.getFlowerPotItem();
            if (flowerPotItem instanceof ItemBlock) {
                final int flowerPotData = tileEntityFlowerPot.getFlowerPotData();
                final Block blockFromItem = Block.getBlockFromItem(flowerPotItem);
                if (blockFromItem == Blocks.sapling) {
                    switch (BlockFlowerPot$1.$SwitchMap$net$minecraft$block$BlockPlanks$EnumType[BlockPlanks.EnumType.byMetadata(flowerPotData).ordinal()]) {
                        case 1: {
                            enumFlowerType = EnumFlowerType.OAK_SAPLING;
                            break;
                        }
                        case 2: {
                            enumFlowerType = EnumFlowerType.SPRUCE_SAPLING;
                            break;
                        }
                        case 3: {
                            enumFlowerType = EnumFlowerType.BIRCH_SAPLING;
                            break;
                        }
                        case 4: {
                            enumFlowerType = EnumFlowerType.JUNGLE_SAPLING;
                            break;
                        }
                        case 5: {
                            enumFlowerType = EnumFlowerType.ACACIA_SAPLING;
                            break;
                        }
                        case 6: {
                            enumFlowerType = EnumFlowerType.DARK_OAK_SAPLING;
                            break;
                        }
                        default: {
                            enumFlowerType = EnumFlowerType.EMPTY;
                            break;
                        }
                    }
                }
                else if (blockFromItem == Blocks.tallgrass) {
                    switch (flowerPotData) {
                        case 0: {
                            enumFlowerType = EnumFlowerType.DEAD_BUSH;
                            break;
                        }
                        case 2: {
                            enumFlowerType = EnumFlowerType.FERN;
                            break;
                        }
                        default: {
                            enumFlowerType = EnumFlowerType.EMPTY;
                            break;
                        }
                    }
                }
                else if (blockFromItem == Blocks.yellow_flower) {
                    enumFlowerType = EnumFlowerType.DANDELION;
                }
                else if (blockFromItem == Blocks.red_flower) {
                    switch (BlockFlowerPot$1.$SwitchMap$net$minecraft$block$BlockFlower$EnumFlowerType[BlockFlower.EnumFlowerType.getType(BlockFlower.EnumFlowerColor.RED, flowerPotData).ordinal()]) {
                        case 1: {
                            enumFlowerType = EnumFlowerType.POPPY;
                            break;
                        }
                        case 2: {
                            enumFlowerType = EnumFlowerType.BLUE_ORCHID;
                            break;
                        }
                        case 3: {
                            enumFlowerType = EnumFlowerType.ALLIUM;
                            break;
                        }
                        case 4: {
                            enumFlowerType = EnumFlowerType.HOUSTONIA;
                            break;
                        }
                        case 5: {
                            enumFlowerType = EnumFlowerType.RED_TULIP;
                            break;
                        }
                        case 6: {
                            enumFlowerType = EnumFlowerType.ORANGE_TULIP;
                            break;
                        }
                        case 7: {
                            enumFlowerType = EnumFlowerType.WHITE_TULIP;
                            break;
                        }
                        case 8: {
                            enumFlowerType = EnumFlowerType.PINK_TULIP;
                            break;
                        }
                        case 9: {
                            enumFlowerType = EnumFlowerType.OXEYE_DAISY;
                            break;
                        }
                        default: {
                            enumFlowerType = EnumFlowerType.EMPTY;
                            break;
                        }
                    }
                }
                else if (blockFromItem == Blocks.red_mushroom) {
                    enumFlowerType = EnumFlowerType.MUSHROOM_RED;
                }
                else if (blockFromItem == Blocks.brown_mushroom) {
                    enumFlowerType = EnumFlowerType.MUSHROOM_BROWN;
                }
                else if (blockFromItem == Blocks.deadbush) {
                    enumFlowerType = EnumFlowerType.DEAD_BUSH;
                }
                else if (blockFromItem == Blocks.cactus) {
                    enumFlowerType = EnumFlowerType.CACTUS;
                }
            }
        }
        return blockState.withProperty(BlockFlowerPot.CONTENTS, enumFlowerType);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return (int)blockState.getValue(BlockFlowerPot.LEGACY_DATA);
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        final ItemStack currentItem = entityPlayer.inventory.getCurrentItem();
        if (currentItem == null || !(currentItem.getItem() instanceof ItemBlock)) {
            return false;
        }
        final TileEntityFlowerPot tileEntity = this.getTileEntity(world, blockPos);
        if (tileEntity == null) {
            return false;
        }
        if (tileEntity.getFlowerPotItem() != null) {
            return false;
        }
        if (Block.getBlockFromItem(currentItem.getItem()) != currentItem.getMetadata()) {
            return false;
        }
        tileEntity.setFlowerPotData(currentItem.getItem(), currentItem.getMetadata());
        tileEntity.markDirty();
        world.markBlockForUpdate(blockPos);
        entityPlayer.triggerAchievement(StatList.field_181736_T);
        if (!entityPlayer.capabilities.isCreativeMode) {
            final ItemStack itemStack = currentItem;
            if (--itemStack.stackSize <= 0) {
                entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
            }
        }
        return true;
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    static {
        LEGACY_DATA = PropertyInteger.create("legacy_data", 0, 15);
        CONTENTS = PropertyEnum.create("contents", EnumFlowerType.class);
    }
    
    @Override
    public boolean isFlowerPot() {
        return true;
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        Block block = null;
        switch (n) {
            case 1: {
                block = Blocks.red_flower;
                BlockFlower.EnumFlowerType.POPPY.getMeta();
                break;
            }
            case 2: {
                block = Blocks.yellow_flower;
                break;
            }
            case 3: {
                block = Blocks.sapling;
                BlockPlanks.EnumType.OAK.getMetadata();
                break;
            }
            case 4: {
                block = Blocks.sapling;
                BlockPlanks.EnumType.SPRUCE.getMetadata();
                break;
            }
            case 5: {
                block = Blocks.sapling;
                BlockPlanks.EnumType.BIRCH.getMetadata();
                break;
            }
            case 6: {
                block = Blocks.sapling;
                BlockPlanks.EnumType.JUNGLE.getMetadata();
                break;
            }
            case 7: {
                block = Blocks.red_mushroom;
                break;
            }
            case 8: {
                block = Blocks.brown_mushroom;
                break;
            }
            case 9: {
                block = Blocks.cactus;
                break;
            }
            case 10: {
                block = Blocks.deadbush;
                break;
            }
            case 11: {
                block = Blocks.tallgrass;
                BlockTallGrass.EnumType.FERN.getMeta();
                break;
            }
            case 12: {
                block = Blocks.sapling;
                BlockPlanks.EnumType.ACACIA.getMetadata();
                break;
            }
            case 13: {
                block = Blocks.sapling;
                BlockPlanks.EnumType.DARK_OAK.getMetadata();
                break;
            }
        }
        return new TileEntityFlowerPot(Item.getItemFromBlock(block), 0);
    }
    
    @Override
    public int getRenderType() {
        return 3;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    public BlockFlowerPot() {
        super(Material.circuits);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockFlowerPot.CONTENTS, EnumFlowerType.EMPTY).withProperty(BlockFlowerPot.LEGACY_DATA, 0));
        this.setBlockBoundsForItemRender();
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return super.canPlaceBlockAt(world, blockPos) && World.doesBlockHaveSolidTopSurface(world, blockPos.down());
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockFlowerPot.CONTENTS, BlockFlowerPot.LEGACY_DATA });
    }
    
    @Override
    public int getDamageValue(final World world, final BlockPos blockPos) {
        final TileEntityFlowerPot tileEntity = this.getTileEntity(world, blockPos);
        return (tileEntity != null && tileEntity.getFlowerPotItem() != null) ? tileEntity.getFlowerPotData() : 0;
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        final TileEntityFlowerPot tileEntity = this.getTileEntity(world, blockPos);
        return (tileEntity != null && tileEntity.getFlowerPotItem() != null) ? tileEntity.getFlowerPotItem() : Items.flower_pot;
    }
    
    @Override
    public String getLocalizedName() {
        return "\ucf8a\ucf97\ucf86\ucf8e\ucfcd\ucf85\ucf8f\ucf8c\ucf94\ucf86\ucf91\ucfb3\ucf8c\ucf97\ucfcd\ucf8d\ucf82\ucf8e\ucf86";
    }
    
    private TileEntityFlowerPot getTileEntity(final World world, final BlockPos blockPos) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        return (tileEntity instanceof TileEntityFlowerPot) ? ((TileEntityFlowerPot)tileEntity) : null;
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    public enum EnumFlowerType implements IStringSerializable
    {
        OAK_SAPLING("OAK_SAPLING", 11, "oak_sapling"), 
        WHITE_TULIP("WHITE_TULIP", 7, "white_tulip"), 
        ACACIA_SAPLING("ACACIA_SAPLING", 15, "acacia_sapling"), 
        BLUE_ORCHID("BLUE_ORCHID", 2, "blue_orchid"), 
        DARK_OAK_SAPLING("DARK_OAK_SAPLING", 16, "dark_oak_sapling"), 
        PINK_TULIP("PINK_TULIP", 8, "pink_tulip"), 
        BIRCH_SAPLING("BIRCH_SAPLING", 13, "birch_sapling"), 
        ORANGE_TULIP("ORANGE_TULIP", 6, "orange_tulip"), 
        MUSHROOM_RED("MUSHROOM_RED", 17, "mushroom_red"), 
        SPRUCE_SAPLING("SPRUCE_SAPLING", 12, "spruce_sapling"), 
        MUSHROOM_BROWN("MUSHROOM_BROWN", 18, "mushroom_brown"), 
        FERN("FERN", 20, "fern"), 
        ALLIUM("ALLIUM", 3, "allium"), 
        EMPTY("EMPTY", 0, "empty"), 
        DEAD_BUSH("DEAD_BUSH", 19, "dead_bush"), 
        POPPY("POPPY", 1, "rose"), 
        JUNGLE_SAPLING("JUNGLE_SAPLING", 14, "jungle_sapling");
        
        private final String name;
        
        RED_TULIP("RED_TULIP", 5, "red_tulip"), 
        OXEYE_DAISY("OXEYE_DAISY", 9, "oxeye_daisy"), 
        DANDELION("DANDELION", 10, "dandelion"), 
        HOUSTONIA("HOUSTONIA", 4, "houstonia");
        
        private static final EnumFlowerType[] $VALUES;
        
        CACTUS("CACTUS", 21, "cactus");
        
        @Override
        public String toString() {
            return this.name;
        }
        
        private EnumFlowerType(final String s, final int n, final String name) {
            this.name = name;
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        static {
            $VALUES = new EnumFlowerType[] { EnumFlowerType.EMPTY, EnumFlowerType.POPPY, EnumFlowerType.BLUE_ORCHID, EnumFlowerType.ALLIUM, EnumFlowerType.HOUSTONIA, EnumFlowerType.RED_TULIP, EnumFlowerType.ORANGE_TULIP, EnumFlowerType.WHITE_TULIP, EnumFlowerType.PINK_TULIP, EnumFlowerType.OXEYE_DAISY, EnumFlowerType.DANDELION, EnumFlowerType.OAK_SAPLING, EnumFlowerType.SPRUCE_SAPLING, EnumFlowerType.BIRCH_SAPLING, EnumFlowerType.JUNGLE_SAPLING, EnumFlowerType.ACACIA_SAPLING, EnumFlowerType.DARK_OAK_SAPLING, EnumFlowerType.MUSHROOM_RED, EnumFlowerType.MUSHROOM_BROWN, EnumFlowerType.DEAD_BUSH, EnumFlowerType.FERN, EnumFlowerType.CACTUS };
        }
    }
}
