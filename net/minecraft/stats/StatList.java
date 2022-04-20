package net.minecraft.stats;

import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import com.google.common.collect.*;

public class StatList
{
    public static StatBase treasureFishedStat;
    public static StatBase field_181725_I;
    public static StatBase dropStat;
    public static StatBase field_181735_S;
    public static StatBase timesTradedWithVillagerStat;
    public static StatBase field_181733_Q;
    public static StatBase field_181726_J;
    public static StatBase field_181741_Y;
    public static StatBase field_181727_K;
    public static StatBase distanceByHorseStat;
    public static StatBase field_181728_L;
    public static final StatBase[] objectCraftStats;
    public static StatBase timesTalkedToVillagerStat;
    public static StatBase distanceWalkedStat;
    public static StatBase field_181739_W;
    public static StatBase distanceFlownStat;
    public static List itemStats;
    public static final StatBase[] objectUseStats;
    public static final StatBase[] mineBlockStatArray;
    public static StatBase minutesPlayedStat;
    protected static Map oneShotStats;
    public static StatBase distanceByPigStat;
    public static StatBase mobKillsStat;
    public static StatBase distanceByBoatStat;
    public static StatBase damageDealtStat;
    public static StatBase distanceCrouchedStat;
    public static StatBase animalsBredStat;
    public static StatBase distanceDoveStat;
    public static StatBase field_181724_H;
    public static List allStats;
    public static StatBase distanceSprintedStat;
    public static StatBase field_181729_M;
    public static StatBase jumpStat;
    public static StatBase field_181740_X;
    public static StatBase field_181734_R;
    public static StatBase distanceClimbedStat;
    public static StatBase playerKillsStat;
    public static StatBase junkFishedStat;
    public static StatBase distanceByMinecartStat;
    public static StatBase distanceSwumStat;
    public static StatBase field_181736_T;
    public static final StatBase[] objectBreakStats;
    public static StatBase leaveGameStat;
    public static StatBase field_181737_U;
    public static StatBase timeSinceDeathStat;
    public static StatBase field_181723_aa;
    public static StatBase distanceFallenStat;
    public static List generalStats;
    public static StatBase field_181742_Z;
    public static StatBase field_181732_P;
    public static StatBase fishCaughtStat;
    public static StatBase deathsStat;
    public static List objectMineStats;
    public static StatBase field_181738_V;
    public static StatBase field_181731_O;
    public static StatBase damageTakenStat;
    public static StatBase field_181730_N;
    
    private static void mergeStatBases(final StatBase[] array, final Block block, final Block block2) {
        final int idFromBlock = Block.getIdFromBlock(block);
        final int idFromBlock2 = Block.getIdFromBlock(block2);
        if (array[idFromBlock] != null && array[idFromBlock2] == null) {
            array[idFromBlock2] = array[idFromBlock];
        }
        else {
            StatList.allStats.remove(array[idFromBlock]);
            StatList.objectMineStats.remove(array[idFromBlock]);
            StatList.generalStats.remove(array[idFromBlock]);
            array[idFromBlock] = array[idFromBlock2];
        }
    }
    
    public static StatBase getStatKillEntity(final EntityList.EntityEggInfo entityEggInfo) {
        final String stringFromID = EntityList.getStringFromID(entityEggInfo.spawnedID);
        return (stringFromID == null) ? null : new StatBase("stat.killEntity." + stringFromID, new ChatComponentTranslation("stat.entityKill", new Object[] { new ChatComponentTranslation("entity." + stringFromID + ".name", new Object[0]) })).registerStat();
    }
    
    private static void initStats() {
        for (final Item item : Item.itemRegistry) {
            if (item != null) {
                final int idFromItem = Item.getIdFromItem(item);
                final String func_180204_a = func_180204_a(item);
                if (func_180204_a == null) {
                    continue;
                }
                StatList.objectUseStats[idFromItem] = new StatCrafting("stat.useItem.", func_180204_a, new ChatComponentTranslation("stat.useItem", new Object[] { new ItemStack(item).getChatComponent() }), item).registerStat();
                if (item instanceof ItemBlock) {
                    continue;
                }
                StatList.itemStats.add(StatList.objectUseStats[idFromItem]);
            }
        }
        replaceAllSimilarBlocks(StatList.objectUseStats);
    }
    
    private static void initMiningStats() {
        for (final Block block : Block.blockRegistry) {
            final Item itemFromBlock = Item.getItemFromBlock(block);
            if (itemFromBlock != null) {
                final int idFromBlock = Block.getIdFromBlock(block);
                final String func_180204_a = func_180204_a(itemFromBlock);
                if (func_180204_a == null || !block.getEnableStats()) {
                    continue;
                }
                StatList.mineBlockStatArray[idFromBlock] = new StatCrafting("stat.mineBlock.", func_180204_a, new ChatComponentTranslation("stat.mineBlock", new Object[] { new ItemStack(block).getChatComponent() }), itemFromBlock).registerStat();
                StatList.objectMineStats.add(StatList.mineBlockStatArray[idFromBlock]);
            }
        }
        replaceAllSimilarBlocks(StatList.mineBlockStatArray);
    }
    
    private static void initCraftableStats() {
        final HashSet hashSet = Sets.newHashSet();
        for (final IRecipe recipe : CraftingManager.getInstance().getRecipeList()) {
            if (recipe.getRecipeOutput() != null) {
                hashSet.add(recipe.getRecipeOutput().getItem());
            }
        }
        final Iterator<ItemStack> iterator2 = FurnaceRecipes.instance().getSmeltingList().values().iterator();
        while (iterator2.hasNext()) {
            hashSet.add(iterator2.next().getItem());
        }
        for (final Item item : hashSet) {
            if (item != null) {
                final int idFromItem = Item.getIdFromItem(item);
                final String func_180204_a = func_180204_a(item);
                if (func_180204_a == null) {
                    continue;
                }
                StatList.objectCraftStats[idFromItem] = new StatCrafting("stat.craftItem.", func_180204_a, new ChatComponentTranslation("stat.craftItem", new Object[] { new ItemStack(item).getChatComponent() }), item).registerStat();
            }
        }
        replaceAllSimilarBlocks(StatList.objectCraftStats);
    }
    
    public static StatBase getOneShotStat(final String s) {
        return StatList.oneShotStats.get(s);
    }
    
    private static String func_180204_a(final Item item) {
        final ResourceLocation resourceLocation = (ResourceLocation)Item.itemRegistry.getNameForObject(item);
        return (resourceLocation != null) ? resourceLocation.toString().replace(':', '.') : null;
    }
    
    public static void init() {
    }
    
    private static void initItemDepleteStats() {
        for (final Item item : Item.itemRegistry) {
            if (item != null) {
                final int idFromItem = Item.getIdFromItem(item);
                final String func_180204_a = func_180204_a(item);
                if (func_180204_a == null || !item.isDamageable()) {
                    continue;
                }
                StatList.objectBreakStats[idFromItem] = new StatCrafting("stat.breakItem.", func_180204_a, new ChatComponentTranslation("stat.breakItem", new Object[] { new ItemStack(item).getChatComponent() }), item).registerStat();
            }
        }
        replaceAllSimilarBlocks(StatList.objectBreakStats);
    }
    
    private static void replaceAllSimilarBlocks(final StatBase[] array) {
        mergeStatBases(array, Blocks.water, Blocks.flowing_water);
        mergeStatBases(array, Blocks.lava, Blocks.flowing_lava);
        mergeStatBases(array, Blocks.lit_pumpkin, Blocks.pumpkin);
        mergeStatBases(array, Blocks.lit_furnace, Blocks.furnace);
        mergeStatBases(array, Blocks.lit_redstone_ore, Blocks.redstone_ore);
        mergeStatBases(array, Blocks.powered_repeater, Blocks.unpowered_repeater);
        mergeStatBases(array, Blocks.powered_comparator, Blocks.unpowered_comparator);
        mergeStatBases(array, Blocks.redstone_torch, Blocks.unlit_redstone_torch);
        mergeStatBases(array, Blocks.lit_redstone_lamp, Blocks.redstone_lamp);
        mergeStatBases(array, Blocks.double_stone_slab, Blocks.stone_slab);
        mergeStatBases(array, Blocks.double_wooden_slab, Blocks.wooden_slab);
        mergeStatBases(array, Blocks.double_stone_slab2, Blocks.stone_slab2);
        mergeStatBases(array, Blocks.grass, Blocks.dirt);
        mergeStatBases(array, Blocks.farmland, Blocks.dirt);
    }
    
    static {
        StatList.oneShotStats = Maps.newHashMap();
        StatList.allStats = Lists.newArrayList();
        StatList.generalStats = Lists.newArrayList();
        StatList.itemStats = Lists.newArrayList();
        StatList.objectMineStats = Lists.newArrayList();
        StatList.leaveGameStat = new StatBasic("stat.leaveGame", new ChatComponentTranslation("stat.leaveGame", new Object[0])).initIndependentStat().registerStat();
        StatList.minutesPlayedStat = new StatBasic("stat.playOneMinute", new ChatComponentTranslation("stat.playOneMinute", new Object[0]), StatBase.timeStatType).initIndependentStat().registerStat();
        StatList.timeSinceDeathStat = new StatBasic("stat.timeSinceDeath", new ChatComponentTranslation("stat.timeSinceDeath", new Object[0]), StatBase.timeStatType).initIndependentStat().registerStat();
        StatList.distanceWalkedStat = new StatBasic("stat.walkOneCm", new ChatComponentTranslation("stat.walkOneCm", new Object[0]), StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceCrouchedStat = new StatBasic("stat.crouchOneCm", new ChatComponentTranslation("stat.crouchOneCm", new Object[0]), StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceSprintedStat = new StatBasic("stat.sprintOneCm", new ChatComponentTranslation("stat.sprintOneCm", new Object[0]), StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceSwumStat = new StatBasic("stat.swimOneCm", new ChatComponentTranslation("stat.swimOneCm", new Object[0]), StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceFallenStat = new StatBasic("stat.fallOneCm", new ChatComponentTranslation("stat.fallOneCm", new Object[0]), StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceClimbedStat = new StatBasic("stat.climbOneCm", new ChatComponentTranslation("stat.climbOneCm", new Object[0]), StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceFlownStat = new StatBasic("stat.flyOneCm", new ChatComponentTranslation("stat.flyOneCm", new Object[0]), StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceDoveStat = new StatBasic("stat.diveOneCm", new ChatComponentTranslation("stat.diveOneCm", new Object[0]), StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceByMinecartStat = new StatBasic("stat.minecartOneCm", new ChatComponentTranslation("stat.minecartOneCm", new Object[0]), StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceByBoatStat = new StatBasic("stat.boatOneCm", new ChatComponentTranslation("stat.boatOneCm", new Object[0]), StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceByPigStat = new StatBasic("stat.pigOneCm", new ChatComponentTranslation("stat.pigOneCm", new Object[0]), StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceByHorseStat = new StatBasic("stat.horseOneCm", new ChatComponentTranslation("stat.horseOneCm", new Object[0]), StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.jumpStat = new StatBasic("stat.jump", new ChatComponentTranslation("stat.jump", new Object[0])).initIndependentStat().registerStat();
        StatList.dropStat = new StatBasic("stat.drop", new ChatComponentTranslation("stat.drop", new Object[0])).initIndependentStat().registerStat();
        StatList.damageDealtStat = new StatBasic("stat.damageDealt", new ChatComponentTranslation("stat.damageDealt", new Object[0]), StatBase.field_111202_k).registerStat();
        StatList.damageTakenStat = new StatBasic("stat.damageTaken", new ChatComponentTranslation("stat.damageTaken", new Object[0]), StatBase.field_111202_k).registerStat();
        StatList.deathsStat = new StatBasic("stat.deaths", new ChatComponentTranslation("stat.deaths", new Object[0])).registerStat();
        StatList.mobKillsStat = new StatBasic("stat.mobKills", new ChatComponentTranslation("stat.mobKills", new Object[0])).registerStat();
        StatList.animalsBredStat = new StatBasic("stat.animalsBred", new ChatComponentTranslation("stat.animalsBred", new Object[0])).registerStat();
        StatList.playerKillsStat = new StatBasic("stat.playerKills", new ChatComponentTranslation("stat.playerKills", new Object[0])).registerStat();
        StatList.fishCaughtStat = new StatBasic("stat.fishCaught", new ChatComponentTranslation("stat.fishCaught", new Object[0])).registerStat();
        StatList.junkFishedStat = new StatBasic("stat.junkFished", new ChatComponentTranslation("stat.junkFished", new Object[0])).registerStat();
        StatList.treasureFishedStat = new StatBasic("stat.treasureFished", new ChatComponentTranslation("stat.treasureFished", new Object[0])).registerStat();
        StatList.timesTalkedToVillagerStat = new StatBasic("stat.talkedToVillager", new ChatComponentTranslation("stat.talkedToVillager", new Object[0])).registerStat();
        StatList.timesTradedWithVillagerStat = new StatBasic("stat.tradedWithVillager", new ChatComponentTranslation("stat.tradedWithVillager", new Object[0])).registerStat();
        StatList.field_181724_H = new StatBasic("stat.cakeSlicesEaten", new ChatComponentTranslation("stat.cakeSlicesEaten", new Object[0])).registerStat();
        StatList.field_181725_I = new StatBasic("stat.cauldronFilled", new ChatComponentTranslation("stat.cauldronFilled", new Object[0])).registerStat();
        StatList.field_181726_J = new StatBasic("stat.cauldronUsed", new ChatComponentTranslation("stat.cauldronUsed", new Object[0])).registerStat();
        StatList.field_181727_K = new StatBasic("stat.armorCleaned", new ChatComponentTranslation("stat.armorCleaned", new Object[0])).registerStat();
        StatList.field_181728_L = new StatBasic("stat.bannerCleaned", new ChatComponentTranslation("stat.bannerCleaned", new Object[0])).registerStat();
        StatList.field_181729_M = new StatBasic("stat.brewingstandInteraction", new ChatComponentTranslation("stat.brewingstandInteraction", new Object[0])).registerStat();
        StatList.field_181730_N = new StatBasic("stat.beaconInteraction", new ChatComponentTranslation("stat.beaconInteraction", new Object[0])).registerStat();
        StatList.field_181731_O = new StatBasic("stat.dropperInspected", new ChatComponentTranslation("stat.dropperInspected", new Object[0])).registerStat();
        StatList.field_181732_P = new StatBasic("stat.hopperInspected", new ChatComponentTranslation("stat.hopperInspected", new Object[0])).registerStat();
        StatList.field_181733_Q = new StatBasic("stat.dispenserInspected", new ChatComponentTranslation("stat.dispenserInspected", new Object[0])).registerStat();
        StatList.field_181734_R = new StatBasic("stat.noteblockPlayed", new ChatComponentTranslation("stat.noteblockPlayed", new Object[0])).registerStat();
        StatList.field_181735_S = new StatBasic("stat.noteblockTuned", new ChatComponentTranslation("stat.noteblockTuned", new Object[0])).registerStat();
        StatList.field_181736_T = new StatBasic("stat.flowerPotted", new ChatComponentTranslation("stat.flowerPotted", new Object[0])).registerStat();
        StatList.field_181737_U = new StatBasic("stat.trappedChestTriggered", new ChatComponentTranslation("stat.trappedChestTriggered", new Object[0])).registerStat();
        StatList.field_181738_V = new StatBasic("stat.enderchestOpened", new ChatComponentTranslation("stat.enderchestOpened", new Object[0])).registerStat();
        StatList.field_181739_W = new StatBasic("stat.itemEnchanted", new ChatComponentTranslation("stat.itemEnchanted", new Object[0])).registerStat();
        StatList.field_181740_X = new StatBasic("stat.recordPlayed", new ChatComponentTranslation("stat.recordPlayed", new Object[0])).registerStat();
        StatList.field_181741_Y = new StatBasic("stat.furnaceInteraction", new ChatComponentTranslation("stat.furnaceInteraction", new Object[0])).registerStat();
        StatList.field_181742_Z = new StatBasic("stat.craftingTableInteraction", new ChatComponentTranslation("stat.workbenchInteraction", new Object[0])).registerStat();
        StatList.field_181723_aa = new StatBasic("stat.chestOpened", new ChatComponentTranslation("stat.chestOpened", new Object[0])).registerStat();
        mineBlockStatArray = new StatBase[4096];
        objectCraftStats = new StatBase[32000];
        objectUseStats = new StatBase[32000];
        objectBreakStats = new StatBase[32000];
    }
    
    public static StatBase getStatEntityKilledBy(final EntityList.EntityEggInfo entityEggInfo) {
        final String stringFromID = EntityList.getStringFromID(entityEggInfo.spawnedID);
        return (stringFromID == null) ? null : new StatBase("stat.entityKilledBy." + stringFromID, new ChatComponentTranslation("stat.entityKilledBy", new Object[] { new ChatComponentTranslation("entity." + stringFromID + ".name", new Object[0]) })).registerStat();
    }
}
