package net.minecraft.client.renderer;

import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.client.renderer.block.statemap.*;
import net.minecraft.block.state.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.client.renderer.texture.*;

public class BlockModelShapes
{
    private final Map bakedModelStore;
    private final BlockStateMapper blockStateMapper;
    private final ModelManager modelManager;
    
    public ModelManager getModelManager() {
        return this.modelManager;
    }
    
    public void registerBuiltInBlocks(final Block... array) {
        this.blockStateMapper.registerBuiltInBlocks(array);
    }
    
    private void registerAllBlocks() {
        this.registerBuiltInBlocks(Blocks.air, Blocks.flowing_water, Blocks.water, Blocks.flowing_lava, Blocks.lava, Blocks.piston_extension, Blocks.chest, Blocks.ender_chest, Blocks.trapped_chest, Blocks.standing_sign, Blocks.skull, Blocks.end_portal, Blocks.barrier, Blocks.wall_sign, Blocks.wall_banner, Blocks.standing_banner);
        this.registerBlockWithStateMapper(Blocks.stone, new StateMap.Builder().withName(BlockStone.VARIANT).build());
        this.registerBlockWithStateMapper(Blocks.prismarine, new StateMap.Builder().withName(BlockPrismarine.VARIANT).build());
        this.registerBlockWithStateMapper(Blocks.leaves, new StateMap.Builder().withName(BlockOldLeaf.VARIANT).withSuffix("_leaves").ignore(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE).build());
        this.registerBlockWithStateMapper(Blocks.leaves2, new StateMap.Builder().withName(BlockNewLeaf.VARIANT).withSuffix("_leaves").ignore(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE).build());
        this.registerBlockWithStateMapper(Blocks.cactus, new StateMap.Builder().ignore(BlockCactus.AGE).build());
        this.registerBlockWithStateMapper(Blocks.reeds, new StateMap.Builder().ignore(BlockReed.AGE).build());
        this.registerBlockWithStateMapper(Blocks.jukebox, new StateMap.Builder().ignore(BlockJukebox.HAS_RECORD).build());
        this.registerBlockWithStateMapper(Blocks.command_block, new StateMap.Builder().ignore(BlockCommandBlock.TRIGGERED).build());
        this.registerBlockWithStateMapper(Blocks.cobblestone_wall, new StateMap.Builder().withName(BlockWall.VARIANT).withSuffix("_wall").build());
        this.registerBlockWithStateMapper(Blocks.double_plant, new StateMap.Builder().withName(BlockDoublePlant.VARIANT).ignore(BlockDoublePlant.field_181084_N).build());
        this.registerBlockWithStateMapper(Blocks.oak_fence_gate, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        this.registerBlockWithStateMapper(Blocks.spruce_fence_gate, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        this.registerBlockWithStateMapper(Blocks.birch_fence_gate, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        this.registerBlockWithStateMapper(Blocks.jungle_fence_gate, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        this.registerBlockWithStateMapper(Blocks.dark_oak_fence_gate, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        this.registerBlockWithStateMapper(Blocks.acacia_fence_gate, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        this.registerBlockWithStateMapper(Blocks.tripwire, new StateMap.Builder().ignore(BlockTripWire.DISARMED, BlockTripWire.POWERED).build());
        this.registerBlockWithStateMapper(Blocks.double_wooden_slab, new StateMap.Builder().withName(BlockPlanks.VARIANT).withSuffix("_double_slab").build());
        this.registerBlockWithStateMapper(Blocks.wooden_slab, new StateMap.Builder().withName(BlockPlanks.VARIANT).withSuffix("_slab").build());
        this.registerBlockWithStateMapper(Blocks.tnt, new StateMap.Builder().ignore(BlockTNT.EXPLODE).build());
        this.registerBlockWithStateMapper(Blocks.fire, new StateMap.Builder().ignore(BlockFire.AGE).build());
        this.registerBlockWithStateMapper(Blocks.redstone_wire, new StateMap.Builder().ignore(BlockRedstoneWire.POWER).build());
        this.registerBlockWithStateMapper(Blocks.oak_door, new StateMap.Builder().ignore(BlockDoor.POWERED).build());
        this.registerBlockWithStateMapper(Blocks.spruce_door, new StateMap.Builder().ignore(BlockDoor.POWERED).build());
        this.registerBlockWithStateMapper(Blocks.birch_door, new StateMap.Builder().ignore(BlockDoor.POWERED).build());
        this.registerBlockWithStateMapper(Blocks.jungle_door, new StateMap.Builder().ignore(BlockDoor.POWERED).build());
        this.registerBlockWithStateMapper(Blocks.acacia_door, new StateMap.Builder().ignore(BlockDoor.POWERED).build());
        this.registerBlockWithStateMapper(Blocks.dark_oak_door, new StateMap.Builder().ignore(BlockDoor.POWERED).build());
        this.registerBlockWithStateMapper(Blocks.iron_door, new StateMap.Builder().ignore(BlockDoor.POWERED).build());
        this.registerBlockWithStateMapper(Blocks.wool, new StateMap.Builder().withName(BlockColored.COLOR).withSuffix("_wool").build());
        this.registerBlockWithStateMapper(Blocks.carpet, new StateMap.Builder().withName(BlockColored.COLOR).withSuffix("_carpet").build());
        this.registerBlockWithStateMapper(Blocks.stained_hardened_clay, new StateMap.Builder().withName(BlockColored.COLOR).withSuffix("_stained_hardened_clay").build());
        this.registerBlockWithStateMapper(Blocks.stained_glass_pane, new StateMap.Builder().withName(BlockColored.COLOR).withSuffix("_stained_glass_pane").build());
        this.registerBlockWithStateMapper(Blocks.stained_glass, new StateMap.Builder().withName(BlockColored.COLOR).withSuffix("_stained_glass").build());
        this.registerBlockWithStateMapper(Blocks.sandstone, new StateMap.Builder().withName(BlockSandStone.TYPE).build());
        this.registerBlockWithStateMapper(Blocks.red_sandstone, new StateMap.Builder().withName(BlockRedSandstone.TYPE).build());
        this.registerBlockWithStateMapper(Blocks.tallgrass, new StateMap.Builder().withName(BlockTallGrass.TYPE).build());
        this.registerBlockWithStateMapper(Blocks.bed, new StateMap.Builder().ignore(BlockBed.OCCUPIED).build());
        this.registerBlockWithStateMapper(Blocks.yellow_flower, new StateMap.Builder().withName(Blocks.yellow_flower.getTypeProperty()).build());
        this.registerBlockWithStateMapper(Blocks.red_flower, new StateMap.Builder().withName(Blocks.red_flower.getTypeProperty()).build());
        this.registerBlockWithStateMapper(Blocks.stone_slab, new StateMap.Builder().withName(BlockStoneSlab.VARIANT).withSuffix("_slab").build());
        this.registerBlockWithStateMapper(Blocks.stone_slab2, new StateMap.Builder().withName(BlockStoneSlabNew.VARIANT).withSuffix("_slab").build());
        this.registerBlockWithStateMapper(Blocks.monster_egg, new StateMap.Builder().withName(BlockSilverfish.VARIANT).withSuffix("_monster_egg").build());
        this.registerBlockWithStateMapper(Blocks.stonebrick, new StateMap.Builder().withName(BlockStoneBrick.VARIANT).build());
        this.registerBlockWithStateMapper(Blocks.dispenser, new StateMap.Builder().ignore(BlockDispenser.TRIGGERED).build());
        this.registerBlockWithStateMapper(Blocks.dropper, new StateMap.Builder().ignore(BlockDropper.TRIGGERED).build());
        this.registerBlockWithStateMapper(Blocks.log, new StateMap.Builder().withName(BlockOldLog.VARIANT).withSuffix("_log").build());
        this.registerBlockWithStateMapper(Blocks.log2, new StateMap.Builder().withName(BlockNewLog.VARIANT).withSuffix("_log").build());
        this.registerBlockWithStateMapper(Blocks.planks, new StateMap.Builder().withName(BlockPlanks.VARIANT).withSuffix("_planks").build());
        this.registerBlockWithStateMapper(Blocks.sapling, new StateMap.Builder().withName(BlockSapling.TYPE).withSuffix("_sapling").build());
        this.registerBlockWithStateMapper(Blocks.sand, new StateMap.Builder().withName(BlockSand.VARIANT).build());
        this.registerBlockWithStateMapper(Blocks.hopper, new StateMap.Builder().ignore(BlockHopper.ENABLED).build());
        this.registerBlockWithStateMapper(Blocks.flower_pot, new StateMap.Builder().ignore(BlockFlowerPot.LEGACY_DATA).build());
        this.registerBlockWithStateMapper(Blocks.quartz_block, new StateMapperBase(this) {
            final BlockModelShapes this$0;
            
            @Override
            protected ModelResourceLocation getModelResourceLocation(final IBlockState blockState) {
                switch (BlockModelShapes$8.$SwitchMap$net$minecraft$block$BlockQuartz$EnumType[((BlockQuartz.EnumType)blockState.getValue(BlockQuartz.VARIANT)).ordinal()]) {
                    default: {
                        return new ModelResourceLocation("quartz_block", "normal");
                    }
                    case 2: {
                        return new ModelResourceLocation("chiseled_quartz_block", "normal");
                    }
                    case 3: {
                        return new ModelResourceLocation("quartz_column", "axis=y");
                    }
                    case 4: {
                        return new ModelResourceLocation("quartz_column", "axis=x");
                    }
                    case 5: {
                        return new ModelResourceLocation("quartz_column", "axis=z");
                    }
                }
            }
        });
        this.registerBlockWithStateMapper(Blocks.deadbush, new StateMapperBase(this) {
            final BlockModelShapes this$0;
            
            @Override
            protected ModelResourceLocation getModelResourceLocation(final IBlockState blockState) {
                return new ModelResourceLocation("dead_bush", "normal");
            }
        });
        this.registerBlockWithStateMapper(Blocks.pumpkin_stem, new StateMapperBase(this) {
            final BlockModelShapes this$0;
            
            @Override
            protected ModelResourceLocation getModelResourceLocation(final IBlockState blockState) {
                final LinkedHashMap linkedHashMap = Maps.newLinkedHashMap((Map)blockState.getProperties());
                if (blockState.getValue(BlockStem.FACING) != EnumFacing.UP) {
                    linkedHashMap.remove(BlockStem.AGE);
                }
                return new ModelResourceLocation((ResourceLocation)Block.blockRegistry.getNameForObject(blockState.getBlock()), this.getPropertyString(linkedHashMap));
            }
        });
        this.registerBlockWithStateMapper(Blocks.melon_stem, new StateMapperBase(this) {
            final BlockModelShapes this$0;
            
            @Override
            protected ModelResourceLocation getModelResourceLocation(final IBlockState blockState) {
                final LinkedHashMap linkedHashMap = Maps.newLinkedHashMap((Map)blockState.getProperties());
                if (blockState.getValue(BlockStem.FACING) != EnumFacing.UP) {
                    linkedHashMap.remove(BlockStem.AGE);
                }
                return new ModelResourceLocation((ResourceLocation)Block.blockRegistry.getNameForObject(blockState.getBlock()), this.getPropertyString(linkedHashMap));
            }
        });
        this.registerBlockWithStateMapper(Blocks.dirt, new StateMapperBase(this) {
            final BlockModelShapes this$0;
            
            @Override
            protected ModelResourceLocation getModelResourceLocation(final IBlockState blockState) {
                final LinkedHashMap linkedHashMap = Maps.newLinkedHashMap((Map)blockState.getProperties());
                final String name = BlockDirt.VARIANT.getName(linkedHashMap.remove(BlockDirt.VARIANT));
                if (BlockDirt.DirtType.PODZOL != blockState.getValue(BlockDirt.VARIANT)) {
                    linkedHashMap.remove(BlockDirt.SNOWY);
                }
                return new ModelResourceLocation(name, this.getPropertyString(linkedHashMap));
            }
        });
        this.registerBlockWithStateMapper(Blocks.double_stone_slab, new StateMapperBase(this) {
            final BlockModelShapes this$0;
            
            @Override
            protected ModelResourceLocation getModelResourceLocation(final IBlockState blockState) {
                final LinkedHashMap linkedHashMap = Maps.newLinkedHashMap((Map)blockState.getProperties());
                final String name = BlockStoneSlab.VARIANT.getName(linkedHashMap.remove(BlockStoneSlab.VARIANT));
                linkedHashMap.remove(BlockStoneSlab.SEAMLESS);
                return new ModelResourceLocation(name + "_double_slab", blockState.getValue(BlockStoneSlab.SEAMLESS) ? "all" : "normal");
            }
        });
        this.registerBlockWithStateMapper(Blocks.double_stone_slab2, new StateMapperBase(this) {
            final BlockModelShapes this$0;
            
            @Override
            protected ModelResourceLocation getModelResourceLocation(final IBlockState blockState) {
                final LinkedHashMap linkedHashMap = Maps.newLinkedHashMap((Map)blockState.getProperties());
                final String name = BlockStoneSlabNew.VARIANT.getName(linkedHashMap.remove(BlockStoneSlabNew.VARIANT));
                linkedHashMap.remove(BlockStoneSlab.SEAMLESS);
                return new ModelResourceLocation(name + "_double_slab", blockState.getValue(BlockStoneSlabNew.SEAMLESS) ? "all" : "normal");
            }
        });
    }
    
    public void reloadModels() {
        this.bakedModelStore.clear();
        for (final Map.Entry<Object, V> entry : this.blockStateMapper.putAllStateModelLocations().entrySet()) {
            this.bakedModelStore.put(entry.getKey(), this.modelManager.getModel((ModelResourceLocation)entry.getValue()));
        }
    }
    
    public IBakedModel getModelForState(final IBlockState blockState) {
        IBakedModel missingModel = this.bakedModelStore.get(blockState);
        if (missingModel == null) {
            missingModel = this.modelManager.getMissingModel();
        }
        return missingModel;
    }
    
    public BlockModelShapes(final ModelManager modelManager) {
        this.bakedModelStore = Maps.newIdentityHashMap();
        this.blockStateMapper = new BlockStateMapper();
        this.modelManager = modelManager;
        this.registerAllBlocks();
    }
    
    public void registerBlockWithStateMapper(final Block block, final IStateMapper stateMapper) {
        this.blockStateMapper.registerBlockStateMapper(block, stateMapper);
    }
    
    public BlockStateMapper getBlockStateMapper() {
        return this.blockStateMapper;
    }
    
    public TextureAtlasSprite getTexture(final IBlockState blockState) {
        final Block block = blockState.getBlock();
        IBakedModel bakedModel = this.getModelForState(blockState);
        if (bakedModel == null || bakedModel == this.modelManager.getMissingModel()) {
            if (block == Blocks.wall_sign || block == Blocks.standing_sign || block == Blocks.chest || block == Blocks.trapped_chest || block == Blocks.standing_banner || block == Blocks.wall_banner) {
                return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/planks_oak");
            }
            if (block == Blocks.ender_chest) {
                return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/obsidian");
            }
            if (block == Blocks.flowing_lava || block == Blocks.lava) {
                return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/lava_still");
            }
            if (block == Blocks.flowing_water || block == Blocks.water) {
                return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/water_still");
            }
            if (block == Blocks.skull) {
                return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/soul_sand");
            }
            if (block == Blocks.barrier) {
                return this.modelManager.getTextureMap().getAtlasSprite("minecraft:items/barrier");
            }
        }
        if (bakedModel == null) {
            bakedModel = this.modelManager.getMissingModel();
        }
        return bakedModel.getParticleTexture();
    }
}
