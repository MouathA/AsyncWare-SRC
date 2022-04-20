package net.minecraft.client.resources.model;

import net.minecraft.client.renderer.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.client.resources.*;
import com.google.common.base.*;
import org.apache.commons.io.*;
import org.apache.logging.log4j.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.block.model.*;
import java.io.*;
import com.google.common.collect.*;
import java.util.*;

public class ModelBakery
{
    private static final Joiner JOINER;
    private static final ModelBlock MODEL_ENTITY;
    private final BlockModelShapes blockModelShapes;
    private final Map models;
    private final FaceBakery faceBakery;
    private RegistrySimple bakedRegistry;
    private Map variantNames;
    private final TextureMap textureMap;
    private final IResourceManager resourceManager;
    private static final Logger LOGGER;
    private static final ModelBlock MODEL_GENERATED;
    private static final ModelBlock MODEL_CLOCK;
    private final Map blockDefinitions;
    protected static final ModelResourceLocation MODEL_MISSING;
    private final Map sprites;
    private Map itemLocations;
    private static final Map BUILT_IN_MODELS;
    private final Map variants;
    private final ItemModelGenerator itemModelGenerator;
    private static final ModelBlock MODEL_COMPASS;
    private static final Set LOCATIONS_BUILTIN_TEXTURES;
    
    private void registerVariantNames() {
        this.variantNames.put(Item.getItemFromBlock(Blocks.stone), Lists.newArrayList((Object[])new String[] { "stone", "granite", "granite_smooth", "diorite", "diorite_smooth", "andesite", "andesite_smooth" }));
        this.variantNames.put(Item.getItemFromBlock(Blocks.dirt), Lists.newArrayList((Object[])new String[] { "dirt", "coarse_dirt", "podzol" }));
        this.variantNames.put(Item.getItemFromBlock(Blocks.planks), Lists.newArrayList((Object[])new String[] { "oak_planks", "spruce_planks", "birch_planks", "jungle_planks", "acacia_planks", "dark_oak_planks" }));
        this.variantNames.put(Item.getItemFromBlock(Blocks.sapling), Lists.newArrayList((Object[])new String[] { "oak_sapling", "spruce_sapling", "birch_sapling", "jungle_sapling", "acacia_sapling", "dark_oak_sapling" }));
        this.variantNames.put(Item.getItemFromBlock(Blocks.sand), Lists.newArrayList((Object[])new String[] { "sand", "red_sand" }));
        this.variantNames.put(Item.getItemFromBlock(Blocks.log), Lists.newArrayList((Object[])new String[] { "oak_log", "spruce_log", "birch_log", "jungle_log" }));
        this.variantNames.put(Item.getItemFromBlock(Blocks.leaves), Lists.newArrayList((Object[])new String[] { "oak_leaves", "spruce_leaves", "birch_leaves", "jungle_leaves" }));
        this.variantNames.put(Item.getItemFromBlock(Blocks.sponge), Lists.newArrayList((Object[])new String[] { "sponge", "sponge_wet" }));
        this.variantNames.put(Item.getItemFromBlock(Blocks.sandstone), Lists.newArrayList((Object[])new String[] { "sandstone", "chiseled_sandstone", "smooth_sandstone" }));
        this.variantNames.put(Item.getItemFromBlock(Blocks.red_sandstone), Lists.newArrayList((Object[])new String[] { "red_sandstone", "chiseled_red_sandstone", "smooth_red_sandstone" }));
        this.variantNames.put(Item.getItemFromBlock(Blocks.tallgrass), Lists.newArrayList((Object[])new String[] { "dead_bush", "tall_grass", "fern" }));
        this.variantNames.put(Item.getItemFromBlock(Blocks.deadbush), Lists.newArrayList((Object[])new String[] { "dead_bush" }));
        this.variantNames.put(Item.getItemFromBlock(Blocks.wool), Lists.newArrayList((Object[])new String[] { "black_wool", "red_wool", "green_wool", "brown_wool", "blue_wool", "purple_wool", "cyan_wool", "silver_wool", "gray_wool", "pink_wool", "lime_wool", "yellow_wool", "light_blue_wool", "magenta_wool", "orange_wool", "white_wool" }));
        this.variantNames.put(Item.getItemFromBlock(Blocks.yellow_flower), Lists.newArrayList((Object[])new String[] { "dandelion" }));
        this.variantNames.put(Item.getItemFromBlock(Blocks.red_flower), Lists.newArrayList((Object[])new String[] { "poppy", "blue_orchid", "allium", "houstonia", "red_tulip", "orange_tulip", "white_tulip", "pink_tulip", "oxeye_daisy" }));
        this.variantNames.put(Item.getItemFromBlock(Blocks.stone_slab), Lists.newArrayList((Object[])new String[] { "stone_slab", "sandstone_slab", "cobblestone_slab", "brick_slab", "stone_brick_slab", "nether_brick_slab", "quartz_slab" }));
        this.variantNames.put(Item.getItemFromBlock(Blocks.stone_slab2), Lists.newArrayList((Object[])new String[] { "red_sandstone_slab" }));
        this.variantNames.put(Item.getItemFromBlock(Blocks.stained_glass), Lists.newArrayList((Object[])new String[] { "black_stained_glass", "red_stained_glass", "green_stained_glass", "brown_stained_glass", "blue_stained_glass", "purple_stained_glass", "cyan_stained_glass", "silver_stained_glass", "gray_stained_glass", "pink_stained_glass", "lime_stained_glass", "yellow_stained_glass", "light_blue_stained_glass", "magenta_stained_glass", "orange_stained_glass", "white_stained_glass" }));
        this.variantNames.put(Item.getItemFromBlock(Blocks.monster_egg), Lists.newArrayList((Object[])new String[] { "stone_monster_egg", "cobblestone_monster_egg", "stone_brick_monster_egg", "mossy_brick_monster_egg", "cracked_brick_monster_egg", "chiseled_brick_monster_egg" }));
        this.variantNames.put(Item.getItemFromBlock(Blocks.stonebrick), Lists.newArrayList((Object[])new String[] { "stonebrick", "mossy_stonebrick", "cracked_stonebrick", "chiseled_stonebrick" }));
        this.variantNames.put(Item.getItemFromBlock(Blocks.wooden_slab), Lists.newArrayList((Object[])new String[] { "oak_slab", "spruce_slab", "birch_slab", "jungle_slab", "acacia_slab", "dark_oak_slab" }));
        this.variantNames.put(Item.getItemFromBlock(Blocks.cobblestone_wall), Lists.newArrayList((Object[])new String[] { "cobblestone_wall", "mossy_cobblestone_wall" }));
        this.variantNames.put(Item.getItemFromBlock(Blocks.anvil), Lists.newArrayList((Object[])new String[] { "anvil_intact", "anvil_slightly_damaged", "anvil_very_damaged" }));
        this.variantNames.put(Item.getItemFromBlock(Blocks.quartz_block), Lists.newArrayList((Object[])new String[] { "quartz_block", "chiseled_quartz_block", "quartz_column" }));
        this.variantNames.put(Item.getItemFromBlock(Blocks.stained_hardened_clay), Lists.newArrayList((Object[])new String[] { "black_stained_hardened_clay", "red_stained_hardened_clay", "green_stained_hardened_clay", "brown_stained_hardened_clay", "blue_stained_hardened_clay", "purple_stained_hardened_clay", "cyan_stained_hardened_clay", "silver_stained_hardened_clay", "gray_stained_hardened_clay", "pink_stained_hardened_clay", "lime_stained_hardened_clay", "yellow_stained_hardened_clay", "light_blue_stained_hardened_clay", "magenta_stained_hardened_clay", "orange_stained_hardened_clay", "white_stained_hardened_clay" }));
        this.variantNames.put(Item.getItemFromBlock(Blocks.stained_glass_pane), Lists.newArrayList((Object[])new String[] { "black_stained_glass_pane", "red_stained_glass_pane", "green_stained_glass_pane", "brown_stained_glass_pane", "blue_stained_glass_pane", "purple_stained_glass_pane", "cyan_stained_glass_pane", "silver_stained_glass_pane", "gray_stained_glass_pane", "pink_stained_glass_pane", "lime_stained_glass_pane", "yellow_stained_glass_pane", "light_blue_stained_glass_pane", "magenta_stained_glass_pane", "orange_stained_glass_pane", "white_stained_glass_pane" }));
        this.variantNames.put(Item.getItemFromBlock(Blocks.leaves2), Lists.newArrayList((Object[])new String[] { "acacia_leaves", "dark_oak_leaves" }));
        this.variantNames.put(Item.getItemFromBlock(Blocks.log2), Lists.newArrayList((Object[])new String[] { "acacia_log", "dark_oak_log" }));
        this.variantNames.put(Item.getItemFromBlock(Blocks.prismarine), Lists.newArrayList((Object[])new String[] { "prismarine", "prismarine_bricks", "dark_prismarine" }));
        this.variantNames.put(Item.getItemFromBlock(Blocks.carpet), Lists.newArrayList((Object[])new String[] { "black_carpet", "red_carpet", "green_carpet", "brown_carpet", "blue_carpet", "purple_carpet", "cyan_carpet", "silver_carpet", "gray_carpet", "pink_carpet", "lime_carpet", "yellow_carpet", "light_blue_carpet", "magenta_carpet", "orange_carpet", "white_carpet" }));
        this.variantNames.put(Item.getItemFromBlock(Blocks.double_plant), Lists.newArrayList((Object[])new String[] { "sunflower", "syringa", "double_grass", "double_fern", "double_rose", "paeonia" }));
        this.variantNames.put(Items.bow, Lists.newArrayList((Object[])new String[] { "bow", "bow_pulling_0", "bow_pulling_1", "bow_pulling_2" }));
        this.variantNames.put(Items.coal, Lists.newArrayList((Object[])new String[] { "coal", "charcoal" }));
        this.variantNames.put(Items.fishing_rod, Lists.newArrayList((Object[])new String[] { "fishing_rod", "fishing_rod_cast" }));
        this.variantNames.put(Items.fish, Lists.newArrayList((Object[])new String[] { "cod", "salmon", "clownfish", "pufferfish" }));
        this.variantNames.put(Items.cooked_fish, Lists.newArrayList((Object[])new String[] { "cooked_cod", "cooked_salmon" }));
        this.variantNames.put(Items.dye, Lists.newArrayList((Object[])new String[] { "dye_black", "dye_red", "dye_green", "dye_brown", "dye_blue", "dye_purple", "dye_cyan", "dye_silver", "dye_gray", "dye_pink", "dye_lime", "dye_yellow", "dye_light_blue", "dye_magenta", "dye_orange", "dye_white" }));
        this.variantNames.put(Items.potionitem, Lists.newArrayList((Object[])new String[] { "bottle_drinkable", "bottle_splash" }));
        this.variantNames.put(Items.skull, Lists.newArrayList((Object[])new String[] { "skull_skeleton", "skull_wither", "skull_zombie", "skull_char", "skull_creeper" }));
        this.variantNames.put(Item.getItemFromBlock(Blocks.oak_fence_gate), Lists.newArrayList((Object[])new String[] { "oak_fence_gate" }));
        this.variantNames.put(Item.getItemFromBlock(Blocks.oak_fence), Lists.newArrayList((Object[])new String[] { "oak_fence" }));
        this.variantNames.put(Items.oak_door, Lists.newArrayList((Object[])new String[] { "oak_door" }));
    }
    
    static Map access$000(final ModelBakery modelBakery) {
        return modelBakery.sprites;
    }
    
    private void registerVariant(final ModelBlockDefinition modelBlockDefinition, final ModelResourceLocation modelResourceLocation) {
        this.variants.put(modelResourceLocation, modelBlockDefinition.getVariants(modelResourceLocation.getVariant()));
    }
    
    private ResourceLocation getParentLocation(final ResourceLocation resourceLocation) {
        for (final Map.Entry<K, ModelBlock> entry : this.models.entrySet()) {
            final ModelBlock modelBlock = entry.getValue();
            if (modelBlock != null && resourceLocation.equals(modelBlock.getParentLocation())) {
                return (ResourceLocation)entry.getKey();
            }
        }
        return null;
    }
    
    private void loadVariantModels() {
        final Iterator<ModelResourceLocation> iterator = this.variants.keySet().iterator();
        while (iterator.hasNext()) {
            final Iterator iterator2 = this.variants.get(iterator.next()).getVariants().iterator();
            while (iterator2.hasNext()) {
                final ResourceLocation modelLocation = iterator2.next().getModelLocation();
                if (this.models.get(modelLocation) == null) {
                    this.models.put(modelLocation, this.loadModel(modelLocation));
                }
            }
        }
    }
    
    private void bakeBlockModels() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/client/resources/model/ModelBakery.variants:Ljava/util/Map;
        //     4: invokeinterface java/util/Map.keySet:()Ljava/util/Set;
        //     9: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
        //    14: astore_1       
        //    15: aload_1        
        //    16: invokeinterface java/util/Iterator.hasNext:()Z
        //    21: ifeq            246
        //    24: aload_1        
        //    25: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    30: checkcast       Lnet/minecraft/client/resources/model/ModelResourceLocation;
        //    33: astore_2       
        //    34: new             Lnet/minecraft/client/resources/model/WeightedBakedModel$Builder;
        //    37: dup            
        //    38: invokespecial   net/minecraft/client/resources/model/WeightedBakedModel$Builder.<init>:()V
        //    41: astore_3       
        //    42: aload_0        
        //    43: getfield        net/minecraft/client/resources/model/ModelBakery.variants:Ljava/util/Map;
        //    46: aload_2        
        //    47: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //    52: checkcast       Lnet/minecraft/client/renderer/block/model/ModelBlockDefinition$Variants;
        //    55: invokevirtual   net/minecraft/client/renderer/block/model/ModelBlockDefinition$Variants.getVariants:()Ljava/util/List;
        //    58: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //    63: astore          5
        //    65: aload           5
        //    67: invokeinterface java/util/Iterator.hasNext:()Z
        //    72: ifeq            182
        //    75: aload           5
        //    77: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    82: checkcast       Lnet/minecraft/client/renderer/block/model/ModelBlockDefinition$Variant;
        //    85: astore          6
        //    87: aload_0        
        //    88: getfield        net/minecraft/client/resources/model/ModelBakery.models:Ljava/util/Map;
        //    91: aload           6
        //    93: invokevirtual   net/minecraft/client/renderer/block/model/ModelBlockDefinition$Variant.getModelLocation:()Lnet/minecraft/util/ResourceLocation;
        //    96: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //   101: checkcast       Lnet/minecraft/client/renderer/block/model/ModelBlock;
        //   104: astore          7
        //   106: aload           7
        //   108: ifnull          151
        //   111: aload           7
        //   113: invokevirtual   net/minecraft/client/renderer/block/model/ModelBlock.isResolved:()Z
        //   116: ifeq            151
        //   119: iinc            4, 1
        //   122: aload_3        
        //   123: aload_0        
        //   124: aload           7
        //   126: aload           6
        //   128: invokevirtual   net/minecraft/client/renderer/block/model/ModelBlockDefinition$Variant.getRotation:()Lnet/minecraft/client/resources/model/ModelRotation;
        //   131: aload           6
        //   133: invokevirtual   net/minecraft/client/renderer/block/model/ModelBlockDefinition$Variant.isUvLocked:()Z
        //   136: invokespecial   net/minecraft/client/resources/model/ModelBakery.bakeModel:(Lnet/minecraft/client/renderer/block/model/ModelBlock;Lnet/minecraft/client/resources/model/ModelRotation;Z)Lnet/minecraft/client/resources/model/IBakedModel;
        //   139: aload           6
        //   141: invokevirtual   net/minecraft/client/renderer/block/model/ModelBlockDefinition$Variant.getWeight:()I
        //   144: invokevirtual   net/minecraft/client/resources/model/WeightedBakedModel$Builder.add:(Lnet/minecraft/client/resources/model/IBakedModel;I)Lnet/minecraft/client/resources/model/WeightedBakedModel$Builder;
        //   147: pop            
        //   148: goto            65
        //   151: getstatic       net/minecraft/client/resources/model/ModelBakery.LOGGER:Lorg/apache/logging/log4j/Logger;
        //   154: new             Ljava/lang/StringBuilder;
        //   157: dup            
        //   158: invokespecial   java/lang/StringBuilder.<init>:()V
        //   161: ldc_w           "Missing model for: "
        //   164: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   167: aload_2        
        //   168: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   171: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   174: invokeinterface org/apache/logging/log4j/Logger.warn:(Ljava/lang/String;)V
        //   179: goto            65
        //   182: getstatic       net/minecraft/client/resources/model/ModelBakery.LOGGER:Lorg/apache/logging/log4j/Logger;
        //   185: new             Ljava/lang/StringBuilder;
        //   188: dup            
        //   189: invokespecial   java/lang/StringBuilder.<init>:()V
        //   192: ldc_w           "No weighted models for: "
        //   195: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   198: aload_2        
        //   199: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   202: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   205: invokeinterface org/apache/logging/log4j/Logger.warn:(Ljava/lang/String;)V
        //   210: goto            15
        //   213: goto            231
        //   216: aload_0        
        //   217: getfield        net/minecraft/client/resources/model/ModelBakery.bakedRegistry:Lnet/minecraft/util/RegistrySimple;
        //   220: aload_2        
        //   221: aload_3        
        //   222: invokevirtual   net/minecraft/client/resources/model/WeightedBakedModel$Builder.first:()Lnet/minecraft/client/resources/model/IBakedModel;
        //   225: invokevirtual   net/minecraft/util/RegistrySimple.putObject:(Ljava/lang/Object;Ljava/lang/Object;)V
        //   228: goto            15
        //   231: aload_0        
        //   232: getfield        net/minecraft/client/resources/model/ModelBakery.bakedRegistry:Lnet/minecraft/util/RegistrySimple;
        //   235: aload_2        
        //   236: aload_3        
        //   237: invokevirtual   net/minecraft/client/resources/model/WeightedBakedModel$Builder.build:()Lnet/minecraft/client/resources/model/WeightedBakedModel;
        //   240: invokevirtual   net/minecraft/util/RegistrySimple.putObject:(Ljava/lang/Object;Ljava/lang/Object;)V
        //   243: goto            15
        //   246: aload_0        
        //   247: getfield        net/minecraft/client/resources/model/ModelBakery.itemLocations:Ljava/util/Map;
        //   250: invokeinterface java/util/Map.entrySet:()Ljava/util/Set;
        //   255: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
        //   260: astore_1       
        //   261: aload_1        
        //   262: invokeinterface java/util/Iterator.hasNext:()Z
        //   267: ifeq            422
        //   270: aload_1        
        //   271: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   276: checkcast       Ljava/util/Map$Entry;
        //   279: astore_2       
        //   280: aload_2        
        //   281: invokeinterface java/util/Map$Entry.getValue:()Ljava/lang/Object;
        //   286: checkcast       Lnet/minecraft/util/ResourceLocation;
        //   289: astore_3       
        //   290: new             Lnet/minecraft/client/resources/model/ModelResourceLocation;
        //   293: dup            
        //   294: aload_2        
        //   295: invokeinterface java/util/Map$Entry.getKey:()Ljava/lang/Object;
        //   300: checkcast       Ljava/lang/String;
        //   303: ldc_w           "inventory"
        //   306: invokespecial   net/minecraft/client/resources/model/ModelResourceLocation.<init>:(Ljava/lang/String;Ljava/lang/String;)V
        //   309: astore          4
        //   311: aload_0        
        //   312: getfield        net/minecraft/client/resources/model/ModelBakery.models:Ljava/util/Map;
        //   315: aload_3        
        //   316: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //   321: checkcast       Lnet/minecraft/client/renderer/block/model/ModelBlock;
        //   324: astore          5
        //   326: aload           5
        //   328: ifnull          391
        //   331: aload           5
        //   333: invokevirtual   net/minecraft/client/renderer/block/model/ModelBlock.isResolved:()Z
        //   336: ifeq            391
        //   339: aload_0        
        //   340: aload           5
        //   342: ifnonnull       369
        //   345: aload_0        
        //   346: getfield        net/minecraft/client/resources/model/ModelBakery.bakedRegistry:Lnet/minecraft/util/RegistrySimple;
        //   349: aload           4
        //   351: new             Lnet/minecraft/client/resources/model/BuiltInModel;
        //   354: dup            
        //   355: aload           5
        //   357: invokevirtual   net/minecraft/client/renderer/block/model/ModelBlock.func_181682_g:()Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms;
        //   360: invokespecial   net/minecraft/client/resources/model/BuiltInModel.<init>:(Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms;)V
        //   363: invokevirtual   net/minecraft/util/RegistrySimple.putObject:(Ljava/lang/Object;Ljava/lang/Object;)V
        //   366: goto            261
        //   369: aload_0        
        //   370: getfield        net/minecraft/client/resources/model/ModelBakery.bakedRegistry:Lnet/minecraft/util/RegistrySimple;
        //   373: aload           4
        //   375: aload_0        
        //   376: aload           5
        //   378: getstatic       net/minecraft/client/resources/model/ModelRotation.X0_Y0:Lnet/minecraft/client/resources/model/ModelRotation;
        //   381: iconst_0       
        //   382: invokespecial   net/minecraft/client/resources/model/ModelBakery.bakeModel:(Lnet/minecraft/client/renderer/block/model/ModelBlock;Lnet/minecraft/client/resources/model/ModelRotation;Z)Lnet/minecraft/client/resources/model/IBakedModel;
        //   385: invokevirtual   net/minecraft/util/RegistrySimple.putObject:(Ljava/lang/Object;Ljava/lang/Object;)V
        //   388: goto            261
        //   391: getstatic       net/minecraft/client/resources/model/ModelBakery.LOGGER:Lorg/apache/logging/log4j/Logger;
        //   394: new             Ljava/lang/StringBuilder;
        //   397: dup            
        //   398: invokespecial   java/lang/StringBuilder.<init>:()V
        //   401: ldc_w           "Missing model for: "
        //   404: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   407: aload_3        
        //   408: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   411: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   414: invokeinterface org/apache/logging/log4j/Logger.warn:(Ljava/lang/String;)V
        //   419: goto            261
        //   422: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0261 (coming from #0388).
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
    
    private void loadItemModels() {
        this.registerVariantNames();
        final Iterator iterator = Item.itemRegistry.iterator();
        while (iterator.hasNext()) {
            for (final String s : this.getVariantNames(iterator.next())) {
                final ResourceLocation itemLocation = this.getItemLocation(s);
                this.itemLocations.put(s, itemLocation);
                if (this.models.get(itemLocation) == null) {
                    this.models.put(itemLocation, this.loadModel(itemLocation));
                }
            }
        }
    }
    
    private ResourceLocation getItemLocation(final String s) {
        final ResourceLocation resourceLocation = new ResourceLocation(s);
        return new ResourceLocation(resourceLocation.getResourceDomain(), "item/" + resourceLocation.getResourcePath());
    }
    
    private ModelBlockDefinition getModelBlockDefinition(final ResourceLocation resourceLocation) {
        final ResourceLocation blockStateLocation = this.getBlockStateLocation(resourceLocation);
        ModelBlockDefinition modelBlockDefinition = this.blockDefinitions.get(blockStateLocation);
        if (modelBlockDefinition == null) {
            final ArrayList arrayList = Lists.newArrayList();
            final Iterator iterator = this.resourceManager.getAllResources(blockStateLocation).iterator();
            while (iterator.hasNext()) {
                final InputStream inputStream = iterator.next().getInputStream();
                arrayList.add(ModelBlockDefinition.parseFromReader(new InputStreamReader(inputStream, Charsets.UTF_8)));
                IOUtils.closeQuietly(inputStream);
            }
            modelBlockDefinition = new ModelBlockDefinition(arrayList);
            this.blockDefinitions.put(blockStateLocation, modelBlockDefinition);
        }
        return modelBlockDefinition;
    }
    
    static {
        LOCATIONS_BUILTIN_TEXTURES = Sets.newHashSet((Object[])new ResourceLocation[] { new ResourceLocation("blocks/water_flow"), new ResourceLocation("blocks/water_still"), new ResourceLocation("blocks/lava_flow"), new ResourceLocation("blocks/lava_still"), new ResourceLocation("blocks/destroy_stage_0"), new ResourceLocation("blocks/destroy_stage_1"), new ResourceLocation("blocks/destroy_stage_2"), new ResourceLocation("blocks/destroy_stage_3"), new ResourceLocation("blocks/destroy_stage_4"), new ResourceLocation("blocks/destroy_stage_5"), new ResourceLocation("blocks/destroy_stage_6"), new ResourceLocation("blocks/destroy_stage_7"), new ResourceLocation("blocks/destroy_stage_8"), new ResourceLocation("blocks/destroy_stage_9"), new ResourceLocation("items/empty_armor_slot_helmet"), new ResourceLocation("items/empty_armor_slot_chestplate"), new ResourceLocation("items/empty_armor_slot_leggings"), new ResourceLocation("items/empty_armor_slot_boots") });
        LOGGER = LogManager.getLogger();
        MODEL_MISSING = new ModelResourceLocation("builtin/missing", "missing");
        BUILT_IN_MODELS = Maps.newHashMap();
        JOINER = Joiner.on(" -> ");
        MODEL_GENERATED = ModelBlock.deserialize("{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}");
        MODEL_COMPASS = ModelBlock.deserialize("{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}");
        MODEL_CLOCK = ModelBlock.deserialize("{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}");
        MODEL_ENTITY = ModelBlock.deserialize("{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}");
        ModelBakery.BUILT_IN_MODELS.put("missing", "{ \"textures\": {   \"particle\": \"missingno\",   \"missingno\": \"missingno\"}, \"elements\": [ {     \"from\": [ 0, 0, 0 ],     \"to\": [ 16, 16, 16 ],     \"faces\": {         \"down\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"down\", \"texture\": \"#missingno\" },         \"up\":    { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"up\", \"texture\": \"#missingno\" },         \"north\": { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"north\", \"texture\": \"#missingno\" },         \"south\": { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"south\", \"texture\": \"#missingno\" },         \"west\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"west\", \"texture\": \"#missingno\" },         \"east\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"east\", \"texture\": \"#missingno\" }    }}]}");
        ModelBakery.MODEL_GENERATED.name = "generation marker";
        ModelBakery.MODEL_COMPASS.name = "compass generation marker";
        ModelBakery.MODEL_CLOCK.name = "class generation marker";
        ModelBakery.MODEL_ENTITY.name = "block entity marker";
    }
    
    public IRegistry setupModelRegistry() {
        this.loadVariantItemModels();
        this.loadModelsCheck();
        this.loadSprites();
        this.bakeItemModels();
        this.bakeBlockModels();
        return this.bakedRegistry;
    }
    
    private Set getVariantsTextureLocations() {
        final HashSet hashSet = Sets.newHashSet();
        final ArrayList arrayList = Lists.newArrayList((Iterable)this.variants.keySet());
        Collections.sort((List<Object>)arrayList, new Comparator(this) {
            final ModelBakery this$0;
            
            @Override
            public int compare(final Object o, final Object o2) {
                return this.compare((ModelResourceLocation)o, (ModelResourceLocation)o2);
            }
            
            public int compare(final ModelResourceLocation modelResourceLocation, final ModelResourceLocation modelResourceLocation2) {
                return modelResourceLocation.toString().compareTo(modelResourceLocation2.toString());
            }
        });
        for (final ModelResourceLocation modelResourceLocation : arrayList) {
            final Iterator iterator2 = this.variants.get(modelResourceLocation).getVariants().iterator();
            while (iterator2.hasNext()) {
                final ModelBlock modelBlock = this.models.get(iterator2.next().getModelLocation());
                if (modelBlock == null) {
                    ModelBakery.LOGGER.warn("Missing model for: " + modelResourceLocation);
                }
                else {
                    hashSet.addAll(this.getTextureLocations(modelBlock));
                }
            }
        }
        hashSet.addAll(ModelBakery.LOCATIONS_BUILTIN_TEXTURES);
        return hashSet;
    }
    
    private void loadSprites() {
        final Set variantsTextureLocations = this.getVariantsTextureLocations();
        variantsTextureLocations.addAll(this.getItemsTextureLocations());
        variantsTextureLocations.remove(TextureMap.LOCATION_MISSING_TEXTURE);
        this.textureMap.loadSprites(this.resourceManager, new IIconCreator(this, variantsTextureLocations) {
            final Set val$set;
            final ModelBakery this$0;
            
            @Override
            public void registerSprites(final TextureMap textureMap) {
                for (final ResourceLocation resourceLocation : this.val$set) {
                    ModelBakery.access$000(this.this$0).put(resourceLocation, textureMap.registerSprite(resourceLocation));
                }
            }
        });
        this.sprites.put(new ResourceLocation("missingno"), this.textureMap.getMissingSprite());
    }
    
    private ResourceLocation getModelLocation(final ResourceLocation resourceLocation) {
        return new ResourceLocation(resourceLocation.getResourceDomain(), "models/" + resourceLocation.getResourcePath() + ".json");
    }
    
    private void loadVariantItemModels() {
        this.loadVariants(this.blockModelShapes.getBlockStateMapper().putAllStateModelLocations().values());
        this.variants.put(ModelBakery.MODEL_MISSING, new ModelBlockDefinition.Variants(ModelBakery.MODEL_MISSING.getVariant(), Lists.newArrayList((Object[])new ModelBlockDefinition.Variant[] { new ModelBlockDefinition.Variant(new ResourceLocation(ModelBakery.MODEL_MISSING.getResourcePath()), ModelRotation.X0_Y0, false, 1) })));
        final ResourceLocation resourceLocation = new ResourceLocation("item_frame");
        final ModelBlockDefinition modelBlockDefinition = this.getModelBlockDefinition(resourceLocation);
        this.registerVariant(modelBlockDefinition, new ModelResourceLocation(resourceLocation, "normal"));
        this.registerVariant(modelBlockDefinition, new ModelResourceLocation(resourceLocation, "map"));
        this.loadVariantModels();
        this.loadItemModels();
    }
    
    private BakedQuad makeBakedQuad(final BlockPart blockPart, final BlockPartFace blockPartFace, final TextureAtlasSprite textureAtlasSprite, final EnumFacing enumFacing, final ModelRotation modelRotation, final boolean b) {
        return this.faceBakery.makeBakedQuad(blockPart.positionFrom, blockPart.positionTo, blockPartFace, textureAtlasSprite, enumFacing, modelRotation, blockPart.partRotation, b, blockPart.shade);
    }
    
    private IBakedModel bakeModel(final ModelBlock modelBlock, final ModelRotation modelRotation, final boolean b) {
        final SimpleBakedModel.Builder setTexture = new SimpleBakedModel.Builder(modelBlock).setTexture(this.sprites.get(new ResourceLocation(modelBlock.resolveTextureName("particle"))));
        for (final BlockPart blockPart : modelBlock.getElements()) {
            for (final EnumFacing enumFacing : blockPart.mapFaces.keySet()) {
                final BlockPartFace blockPartFace = blockPart.mapFaces.get(enumFacing);
                final TextureAtlasSprite textureAtlasSprite = this.sprites.get(new ResourceLocation(modelBlock.resolveTextureName(blockPartFace.texture)));
                if (blockPartFace.cullFace == null) {
                    setTexture.addGeneralQuad(this.makeBakedQuad(blockPart, blockPartFace, textureAtlasSprite, enumFacing, modelRotation, b));
                }
                else {
                    setTexture.addFaceQuad(modelRotation.rotateFace(blockPartFace.cullFace), this.makeBakedQuad(blockPart, blockPartFace, textureAtlasSprite, enumFacing, modelRotation, b));
                }
            }
        }
        return setTexture.makeBakedModel();
    }
    
    private void loadModelsCheck() {
        this.loadModels();
        final Iterator<ModelBlock> iterator = this.models.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().getParentFromMap(this.models);
        }
        ModelBlock.checkModelHierarchy(this.models);
    }
    
    private List getVariantNames(final Item item) {
        List<String> singletonList = this.variantNames.get(item);
        if (singletonList == null) {
            singletonList = Collections.singletonList(((ResourceLocation)Item.itemRegistry.getNameForObject(item)).toString());
        }
        return singletonList;
    }
    
    private void bakeItemModels() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/client/resources/model/ModelBakery.itemLocations:Ljava/util/Map;
        //     4: invokeinterface java/util/Map.values:()Ljava/util/Collection;
        //     9: invokeinterface java/util/Collection.iterator:()Ljava/util/Iterator;
        //    14: astore_1       
        //    15: aload_1        
        //    16: invokeinterface java/util/Iterator.hasNext:()Z
        //    21: ifeq            110
        //    24: aload_1        
        //    25: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    30: checkcast       Lnet/minecraft/util/ResourceLocation;
        //    33: astore_2       
        //    34: aload_0        
        //    35: getfield        net/minecraft/client/resources/model/ModelBakery.models:Ljava/util/Map;
        //    38: aload_2        
        //    39: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //    44: checkcast       Lnet/minecraft/client/renderer/block/model/ModelBlock;
        //    47: astore_3       
        //    48: aload_0        
        //    49: aload_3        
        //    50: ifnonnull       90
        //    53: aload_0        
        //    54: aload_3        
        //    55: invokespecial   net/minecraft/client/resources/model/ModelBakery.makeItemModel:(Lnet/minecraft/client/renderer/block/model/ModelBlock;)Lnet/minecraft/client/renderer/block/model/ModelBlock;
        //    58: astore          4
        //    60: aload           4
        //    62: ifnull          74
        //    65: aload           4
        //    67: aload_2        
        //    68: invokevirtual   net/minecraft/util/ResourceLocation.toString:()Ljava/lang/String;
        //    71: putfield        net/minecraft/client/renderer/block/model/ModelBlock.name:Ljava/lang/String;
        //    74: aload_0        
        //    75: getfield        net/minecraft/client/resources/model/ModelBakery.models:Ljava/util/Map;
        //    78: aload_2        
        //    79: aload           4
        //    81: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //    86: pop            
        //    87: goto            15
        //    90: aload_0        
        //    91: aload_3        
        //    92: ifnonnull       107
        //    95: aload_0        
        //    96: getfield        net/minecraft/client/resources/model/ModelBakery.models:Ljava/util/Map;
        //    99: aload_2        
        //   100: aload_3        
        //   101: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   106: pop            
        //   107: goto            15
        //   110: aload_0        
        //   111: getfield        net/minecraft/client/resources/model/ModelBakery.sprites:Ljava/util/Map;
        //   114: invokeinterface java/util/Map.values:()Ljava/util/Collection;
        //   119: invokeinterface java/util/Collection.iterator:()Ljava/util/Iterator;
        //   124: astore_1       
        //   125: aload_1        
        //   126: invokeinterface java/util/Iterator.hasNext:()Z
        //   131: ifeq            158
        //   134: aload_1        
        //   135: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   140: checkcast       Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;
        //   143: astore_2       
        //   144: aload_2        
        //   145: invokevirtual   net/minecraft/client/renderer/texture/TextureAtlasSprite.hasAnimationMetadata:()Z
        //   148: ifne            155
        //   151: aload_2        
        //   152: invokevirtual   net/minecraft/client/renderer/texture/TextureAtlasSprite.clearFramesTextureData:()V
        //   155: goto            125
        //   158: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0015 (coming from #0107).
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
    
    public ModelBakery(final IResourceManager resourceManager, final TextureMap textureMap, final BlockModelShapes blockModelShapes) {
        this.sprites = Maps.newHashMap();
        this.models = Maps.newLinkedHashMap();
        this.variants = Maps.newLinkedHashMap();
        this.faceBakery = new FaceBakery();
        this.itemModelGenerator = new ItemModelGenerator();
        this.bakedRegistry = new RegistrySimple();
        this.itemLocations = Maps.newLinkedHashMap();
        this.blockDefinitions = Maps.newHashMap();
        this.variantNames = Maps.newIdentityHashMap();
        this.resourceManager = resourceManager;
        this.textureMap = textureMap;
        this.blockModelShapes = blockModelShapes;
    }
    
    private void loadVariants(final Collection collection) {
        for (final ModelResourceLocation modelResourceLocation : collection) {
            this.registerVariant(this.getModelBlockDefinition(modelResourceLocation), modelResourceLocation);
        }
    }
    
    private ModelBlock makeItemModel(final ModelBlock modelBlock) {
        return this.itemModelGenerator.makeItemModel(this.textureMap, modelBlock);
    }
    
    private Set getTextureLocations(final ModelBlock modelBlock) {
        final HashSet hashSet = Sets.newHashSet();
        final Iterator<BlockPart> iterator = modelBlock.getElements().iterator();
        while (iterator.hasNext()) {
            final Iterator<BlockPartFace> iterator2 = iterator.next().mapFaces.values().iterator();
            while (iterator2.hasNext()) {
                hashSet.add(new ResourceLocation(modelBlock.resolveTextureName(iterator2.next().texture)));
            }
        }
        hashSet.add(new ResourceLocation(modelBlock.resolveTextureName("particle")));
        return hashSet;
    }
    
    private ModelBlock loadModel(final ResourceLocation resourceLocation) throws IOException {
        final String resourcePath = resourceLocation.getResourcePath();
        if ("builtin/generated".equals(resourcePath)) {
            return ModelBakery.MODEL_GENERATED;
        }
        if ("builtin/compass".equals(resourcePath)) {
            return ModelBakery.MODEL_COMPASS;
        }
        if ("builtin/clock".equals(resourcePath)) {
            return ModelBakery.MODEL_CLOCK;
        }
        if ("builtin/entity".equals(resourcePath)) {
            return ModelBakery.MODEL_ENTITY;
        }
        Reader reader;
        if (resourcePath.startsWith("builtin/")) {
            final String s = ModelBakery.BUILT_IN_MODELS.get(resourcePath.substring(8));
            if (s == null) {
                throw new FileNotFoundException(resourceLocation.toString());
            }
            reader = new StringReader(s);
        }
        else {
            reader = new InputStreamReader(this.resourceManager.getResource(this.getModelLocation(resourceLocation)).getInputStream(), Charsets.UTF_8);
        }
        final ModelBlock deserialize = ModelBlock.deserialize(reader);
        deserialize.name = resourceLocation.toString();
        final ModelBlock modelBlock = deserialize;
        reader.close();
        return modelBlock;
    }
    
    private ResourceLocation getBlockStateLocation(final ResourceLocation resourceLocation) {
        return new ResourceLocation(resourceLocation.getResourceDomain(), "blockstates/" + resourceLocation.getResourcePath() + ".json");
    }
    
    private Set getItemsTextureLocations() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: astore_1       
        //     4: aload_0        
        //     5: getfield        net/minecraft/client/resources/model/ModelBakery.itemLocations:Ljava/util/Map;
        //     8: invokeinterface java/util/Map.values:()Ljava/util/Collection;
        //    13: invokeinterface java/util/Collection.iterator:()Ljava/util/Iterator;
        //    18: astore_2       
        //    19: aload_2        
        //    20: invokeinterface java/util/Iterator.hasNext:()Z
        //    25: ifeq            328
        //    28: aload_2        
        //    29: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    34: checkcast       Lnet/minecraft/util/ResourceLocation;
        //    37: astore_3       
        //    38: aload_0        
        //    39: getfield        net/minecraft/client/resources/model/ModelBakery.models:Ljava/util/Map;
        //    42: aload_3        
        //    43: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //    48: checkcast       Lnet/minecraft/client/renderer/block/model/ModelBlock;
        //    51: astore          4
        //    53: aload           4
        //    55: ifnull          325
        //    58: aload_1        
        //    59: new             Lnet/minecraft/util/ResourceLocation;
        //    62: dup            
        //    63: aload           4
        //    65: ldc_w           "particle"
        //    68: invokevirtual   net/minecraft/client/renderer/block/model/ModelBlock.resolveTextureName:(Ljava/lang/String;)Ljava/lang/String;
        //    71: invokespecial   net/minecraft/util/ResourceLocation.<init>:(Ljava/lang/String;)V
        //    74: invokeinterface java/util/Set.add:(Ljava/lang/Object;)Z
        //    79: pop            
        //    80: aload_0        
        //    81: aload           4
        //    83: ifnonnull       212
        //    86: getstatic       net/minecraft/client/renderer/block/model/ItemModelGenerator.LAYERS:Ljava/util/List;
        //    89: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //    94: astore          5
        //    96: aload           5
        //    98: invokeinterface java/util/Iterator.hasNext:()Z
        //   103: ifeq            209
        //   106: aload           5
        //   108: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   113: checkcast       Ljava/lang/String;
        //   116: astore          6
        //   118: new             Lnet/minecraft/util/ResourceLocation;
        //   121: dup            
        //   122: aload           4
        //   124: aload           6
        //   126: invokevirtual   net/minecraft/client/renderer/block/model/ModelBlock.resolveTextureName:(Ljava/lang/String;)Ljava/lang/String;
        //   129: invokespecial   net/minecraft/util/ResourceLocation.<init>:(Ljava/lang/String;)V
        //   132: astore          7
        //   134: aload           4
        //   136: invokevirtual   net/minecraft/client/renderer/block/model/ModelBlock.getRootModel:()Lnet/minecraft/client/renderer/block/model/ModelBlock;
        //   139: getstatic       net/minecraft/client/resources/model/ModelBakery.MODEL_COMPASS:Lnet/minecraft/client/renderer/block/model/ModelBlock;
        //   142: if_acmpne       167
        //   145: getstatic       net/minecraft/client/renderer/texture/TextureMap.LOCATION_MISSING_TEXTURE:Lnet/minecraft/util/ResourceLocation;
        //   148: aload           7
        //   150: invokevirtual   net/minecraft/util/ResourceLocation.equals:(Ljava/lang/Object;)Z
        //   153: ifne            167
        //   156: aload           7
        //   158: invokevirtual   net/minecraft/util/ResourceLocation.toString:()Ljava/lang/String;
        //   161: invokestatic    net/minecraft/client/renderer/texture/TextureAtlasSprite.setLocationNameCompass:(Ljava/lang/String;)V
        //   164: goto            197
        //   167: aload           4
        //   169: invokevirtual   net/minecraft/client/renderer/block/model/ModelBlock.getRootModel:()Lnet/minecraft/client/renderer/block/model/ModelBlock;
        //   172: getstatic       net/minecraft/client/resources/model/ModelBakery.MODEL_CLOCK:Lnet/minecraft/client/renderer/block/model/ModelBlock;
        //   175: if_acmpne       197
        //   178: getstatic       net/minecraft/client/renderer/texture/TextureMap.LOCATION_MISSING_TEXTURE:Lnet/minecraft/util/ResourceLocation;
        //   181: aload           7
        //   183: invokevirtual   net/minecraft/util/ResourceLocation.equals:(Ljava/lang/Object;)Z
        //   186: ifne            197
        //   189: aload           7
        //   191: invokevirtual   net/minecraft/util/ResourceLocation.toString:()Ljava/lang/String;
        //   194: invokestatic    net/minecraft/client/renderer/texture/TextureAtlasSprite.setLocationNameClock:(Ljava/lang/String;)V
        //   197: aload_1        
        //   198: aload           7
        //   200: invokeinterface java/util/Set.add:(Ljava/lang/Object;)Z
        //   205: pop            
        //   206: goto            96
        //   209: goto            19
        //   212: aload_0        
        //   213: aload           4
        //   215: ifnonnull       325
        //   218: aload           4
        //   220: invokevirtual   net/minecraft/client/renderer/block/model/ModelBlock.getElements:()Ljava/util/List;
        //   223: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   228: astore          5
        //   230: aload           5
        //   232: invokeinterface java/util/Iterator.hasNext:()Z
        //   237: ifeq            325
        //   240: aload           5
        //   242: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   247: checkcast       Lnet/minecraft/client/renderer/block/model/BlockPart;
        //   250: astore          6
        //   252: aload           6
        //   254: getfield        net/minecraft/client/renderer/block/model/BlockPart.mapFaces:Ljava/util/Map;
        //   257: invokeinterface java/util/Map.values:()Ljava/util/Collection;
        //   262: invokeinterface java/util/Collection.iterator:()Ljava/util/Iterator;
        //   267: astore          7
        //   269: aload           7
        //   271: invokeinterface java/util/Iterator.hasNext:()Z
        //   276: ifeq            322
        //   279: aload           7
        //   281: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   286: checkcast       Lnet/minecraft/client/renderer/block/model/BlockPartFace;
        //   289: astore          8
        //   291: new             Lnet/minecraft/util/ResourceLocation;
        //   294: dup            
        //   295: aload           4
        //   297: aload           8
        //   299: getfield        net/minecraft/client/renderer/block/model/BlockPartFace.texture:Ljava/lang/String;
        //   302: invokevirtual   net/minecraft/client/renderer/block/model/ModelBlock.resolveTextureName:(Ljava/lang/String;)Ljava/lang/String;
        //   305: invokespecial   net/minecraft/util/ResourceLocation.<init>:(Ljava/lang/String;)V
        //   308: astore          9
        //   310: aload_1        
        //   311: aload           9
        //   313: invokeinterface java/util/Set.add:(Ljava/lang/Object;)Z
        //   318: pop            
        //   319: goto            269
        //   322: goto            230
        //   325: goto            19
        //   328: aload_1        
        //   329: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0325 (coming from #0215).
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
    
    private List getParentPath(final ResourceLocation resourceLocation) {
        final ArrayList arrayList = Lists.newArrayList((Object[])new ResourceLocation[] { resourceLocation });
        ResourceLocation parentLocation = resourceLocation;
        while ((parentLocation = this.getParentLocation(parentLocation)) != null) {
            arrayList.add(0, parentLocation);
        }
        return arrayList;
    }
    
    private void loadModels() {
        final ArrayDeque arrayDeque = Queues.newArrayDeque();
        final HashSet hashSet = Sets.newHashSet();
        for (final ResourceLocation resourceLocation : this.models.keySet()) {
            hashSet.add(resourceLocation);
            final ResourceLocation parentLocation = this.models.get(resourceLocation).getParentLocation();
            if (parentLocation != null) {
                arrayDeque.add(parentLocation);
            }
        }
        while (!arrayDeque.isEmpty()) {
            final ResourceLocation resourceLocation2 = arrayDeque.pop();
            if (this.models.get(resourceLocation2) != null) {
                continue;
            }
            final ModelBlock loadModel = this.loadModel(resourceLocation2);
            this.models.put(resourceLocation2, loadModel);
            final ResourceLocation parentLocation2 = loadModel.getParentLocation();
            if (parentLocation2 != null && !hashSet.contains(parentLocation2)) {
                arrayDeque.add(parentLocation2);
            }
            hashSet.add(resourceLocation2);
        }
    }
}
