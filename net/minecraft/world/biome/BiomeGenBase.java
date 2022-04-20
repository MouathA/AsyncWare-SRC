package net.minecraft.world.biome;

import net.minecraft.block.state.*;
import net.minecraft.world.gen.*;
import org.apache.logging.log4j.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.world.gen.feature.*;
import com.google.common.collect.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.util.*;

public abstract class BiomeGenBase
{
    public static final BiomeGenBase plains;
    protected WorldGenTrees worldGeneratorTrees;
    public static final Map BIOME_ID_MAP;
    public int waterColorMultiplier;
    public static final BiomeGenBase river;
    protected List spawnableCaveCreatureList;
    public static final BiomeGenBase mesaPlateau_F;
    private static final BiomeGenBase[] biomeList;
    public IBlockState topBlock;
    public float maxHeight;
    public static final BiomeGenBase mushroomIslandShore;
    public static final BiomeGenBase forestHills;
    public static final BiomeGenBase swampland;
    public final int biomeID;
    protected static final Height height_ShallowWaters;
    public static final BiomeGenBase extremeHillsPlus;
    protected static final Height height_Default;
    protected static final NoiseGeneratorPerlin GRASS_COLOR_NOISE;
    public static final BiomeGenBase forest;
    public static final BiomeGenBase jungleEdge;
    public static final BiomeGenBase megaTaigaHills;
    public static final BiomeGenBase extremeHills;
    protected static final Height height_Oceans;
    public static final BiomeGenBase coldBeach;
    public static final BiomeGenBase mesaPlateau;
    public static final BiomeGenBase icePlains;
    public IBlockState fillerBlock;
    public static final BiomeGenBase hell;
    public static final BiomeGenBase taigaHills;
    public static final BiomeGenBase birchForest;
    public static final Set explorationBiomesList;
    public BiomeDecorator theBiomeDecorator;
    protected static final Height height_MidHills;
    public static final BiomeGenBase stoneBeach;
    protected List spawnableWaterCreatureList;
    public static final BiomeGenBase sky;
    public static final BiomeGenBase savanna;
    protected static final Height height_LowHills;
    protected static final Height height_RockyWaters;
    public int fillerBlockMetadata;
    protected static final Height height_Shores;
    public static final BiomeGenBase iceMountains;
    protected static final Height height_LowPlains;
    public static final BiomeGenBase ocean;
    protected static final Height height_LowIslands;
    public static final BiomeGenBase field_180279_ad;
    public float rainfall;
    public static final BiomeGenBase birchForestHills;
    public static final BiomeGenBase jungleHills;
    public int field_150609_ah;
    public static final BiomeGenBase frozenRiver;
    protected boolean enableRain;
    public static final BiomeGenBase deepOcean;
    public static final BiomeGenBase mesa;
    public static final BiomeGenBase extremeHillsEdge;
    public static final BiomeGenBase mushroomIsland;
    protected static final Height height_MidPlains;
    public static final BiomeGenBase desert;
    public int color;
    protected static final Height height_PartiallySubmerged;
    public static final BiomeGenBase savannaPlateau;
    public static final BiomeGenBase jungle;
    protected static final Height height_HighPlateaus;
    protected List spawnableMonsterList;
    public static final BiomeGenBase coldTaigaHills;
    public static final BiomeGenBase desertHills;
    protected static final WorldGenDoublePlant DOUBLE_PLANT_GENERATOR;
    protected List spawnableCreatureList;
    protected boolean enableSnow;
    public static final BiomeGenBase taiga;
    protected WorldGenSwamp worldGeneratorSwamp;
    private static final Logger logger;
    protected WorldGenBigTree worldGeneratorBigTree;
    public float minHeight;
    public float temperature;
    public static final BiomeGenBase megaTaiga;
    public String biomeName;
    protected static final Height height_DeepOceans;
    public static final BiomeGenBase roofedForest;
    public static final BiomeGenBase coldTaiga;
    protected static final NoiseGeneratorPerlin temperatureNoise;
    public static final BiomeGenBase beach;
    public static final BiomeGenBase frozenOcean;
    
    public boolean getEnableSnow() {
        return this.isSnowyBiome();
    }
    
    protected BiomeGenBase setColor(final int n) {
        this.func_150557_a(n, false);
        return this;
    }
    
    public WorldGenerator getRandomWorldGenForGrass(final Random random) {
        return new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
    }
    
    protected BiomeGenBase setFillerBlockMetadata(final int fillerBlockMetadata) {
        this.fillerBlockMetadata = fillerBlockMetadata;
        return this;
    }
    
    protected BiomeGenBase func_150563_c(final int field_150609_ah) {
        this.field_150609_ah = field_150609_ah;
        return this;
    }
    
    public static BiomeGenBase getBiome(final int n) {
        return getBiomeFromBiomeList(n, null);
    }
    
    public int getFoliageColorAtPos(final BlockPos blockPos) {
        return ColorizerFoliage.getFoliageColor(MathHelper.clamp_float(this.getFloatTemperature(blockPos), 0.0f, 1.0f), MathHelper.clamp_float(this.getFloatRainfall(), 0.0f, 1.0f));
    }
    
    protected BiomeGenBase createMutatedBiome(final int n) {
        return new BiomeGenMutated(n, this);
    }
    
    public boolean isHighHumidity() {
        return this.rainfall > 0.85f;
    }
    
    static {
        logger = LogManager.getLogger();
        height_Default = new Height(0.1f, 0.2f);
        height_ShallowWaters = new Height(-0.5f, 0.0f);
        height_Oceans = new Height(-1.0f, 0.1f);
        height_DeepOceans = new Height(-1.8f, 0.1f);
        height_LowPlains = new Height(0.125f, 0.05f);
        height_MidPlains = new Height(0.2f, 0.2f);
        height_LowHills = new Height(0.45f, 0.3f);
        height_HighPlateaus = new Height(1.5f, 0.025f);
        height_MidHills = new Height(1.0f, 0.5f);
        height_Shores = new Height(0.0f, 0.025f);
        height_RockyWaters = new Height(0.1f, 0.8f);
        height_LowIslands = new Height(0.2f, 0.3f);
        height_PartiallySubmerged = new Height(-0.2f, 0.1f);
        biomeList = new BiomeGenBase[256];
        explorationBiomesList = Sets.newHashSet();
        BIOME_ID_MAP = Maps.newHashMap();
        ocean = new BiomeGenOcean(0).setColor(112).setBiomeName("Ocean").setHeight(BiomeGenBase.height_Oceans);
        plains = new BiomeGenPlains(1).setColor(9286496).setBiomeName("Plains");
        desert = new BiomeGenDesert(2).setColor(16421912).setBiomeName("Desert").setDisableRain().setTemperatureRainfall(2.0f, 0.0f).setHeight(BiomeGenBase.height_LowPlains);
        extremeHills = new BiomeGenHills(3, false).setColor(6316128).setBiomeName("Extreme Hills").setHeight(BiomeGenBase.height_MidHills).setTemperatureRainfall(0.2f, 0.3f);
        forest = new BiomeGenForest(4, 0).setColor(353825).setBiomeName("Forest");
        taiga = new BiomeGenTaiga(5, 0).setColor(747097).setBiomeName("Taiga").setFillerBlockMetadata(5159473).setTemperatureRainfall(0.25f, 0.8f).setHeight(BiomeGenBase.height_MidPlains);
        swampland = new BiomeGenSwamp(6).setColor(522674).setBiomeName("Swampland").setFillerBlockMetadata(9154376).setHeight(BiomeGenBase.height_PartiallySubmerged).setTemperatureRainfall(0.8f, 0.9f);
        river = new BiomeGenRiver(7).setColor(255).setBiomeName("River").setHeight(BiomeGenBase.height_ShallowWaters);
        hell = new BiomeGenHell(8).setColor(16711680).setBiomeName("Hell").setDisableRain().setTemperatureRainfall(2.0f, 0.0f);
        sky = new BiomeGenEnd(9).setColor(8421631).setBiomeName("The End").setDisableRain();
        frozenOcean = new BiomeGenOcean(10).setColor(9474208).setBiomeName("FrozenOcean").setEnableSnow().setHeight(BiomeGenBase.height_Oceans).setTemperatureRainfall(0.0f, 0.5f);
        frozenRiver = new BiomeGenRiver(11).setColor(10526975).setBiomeName("FrozenRiver").setEnableSnow().setHeight(BiomeGenBase.height_ShallowWaters).setTemperatureRainfall(0.0f, 0.5f);
        icePlains = new BiomeGenSnow(12, false).setColor(16777215).setBiomeName("Ice Plains").setEnableSnow().setTemperatureRainfall(0.0f, 0.5f).setHeight(BiomeGenBase.height_LowPlains);
        iceMountains = new BiomeGenSnow(13, false).setColor(10526880).setBiomeName("Ice Mountains").setEnableSnow().setHeight(BiomeGenBase.height_LowHills).setTemperatureRainfall(0.0f, 0.5f);
        mushroomIsland = new BiomeGenMushroomIsland(14).setColor(16711935).setBiomeName("MushroomIsland").setTemperatureRainfall(0.9f, 1.0f).setHeight(BiomeGenBase.height_LowIslands);
        mushroomIslandShore = new BiomeGenMushroomIsland(15).setColor(10486015).setBiomeName("MushroomIslandShore").setTemperatureRainfall(0.9f, 1.0f).setHeight(BiomeGenBase.height_Shores);
        beach = new BiomeGenBeach(16).setColor(16440917).setBiomeName("Beach").setTemperatureRainfall(0.8f, 0.4f).setHeight(BiomeGenBase.height_Shores);
        desertHills = new BiomeGenDesert(17).setColor(13786898).setBiomeName("DesertHills").setDisableRain().setTemperatureRainfall(2.0f, 0.0f).setHeight(BiomeGenBase.height_LowHills);
        forestHills = new BiomeGenForest(18, 0).setColor(2250012).setBiomeName("ForestHills").setHeight(BiomeGenBase.height_LowHills);
        taigaHills = new BiomeGenTaiga(19, 0).setColor(1456435).setBiomeName("TaigaHills").setFillerBlockMetadata(5159473).setTemperatureRainfall(0.25f, 0.8f).setHeight(BiomeGenBase.height_LowHills);
        extremeHillsEdge = new BiomeGenHills(20, true).setColor(7501978).setBiomeName("Extreme Hills Edge").setHeight(BiomeGenBase.height_MidHills.attenuate()).setTemperatureRainfall(0.2f, 0.3f);
        jungle = new BiomeGenJungle(21, false).setColor(5470985).setBiomeName("Jungle").setFillerBlockMetadata(5470985).setTemperatureRainfall(0.95f, 0.9f);
        jungleHills = new BiomeGenJungle(22, false).setColor(2900485).setBiomeName("JungleHills").setFillerBlockMetadata(5470985).setTemperatureRainfall(0.95f, 0.9f).setHeight(BiomeGenBase.height_LowHills);
        jungleEdge = new BiomeGenJungle(23, true).setColor(6458135).setBiomeName("JungleEdge").setFillerBlockMetadata(5470985).setTemperatureRainfall(0.95f, 0.8f);
        deepOcean = new BiomeGenOcean(24).setColor(48).setBiomeName("Deep Ocean").setHeight(BiomeGenBase.height_DeepOceans);
        stoneBeach = new BiomeGenStoneBeach(25).setColor(10658436).setBiomeName("Stone Beach").setTemperatureRainfall(0.2f, 0.3f).setHeight(BiomeGenBase.height_RockyWaters);
        coldBeach = new BiomeGenBeach(26).setColor(16445632).setBiomeName("Cold Beach").setTemperatureRainfall(0.05f, 0.3f).setHeight(BiomeGenBase.height_Shores).setEnableSnow();
        birchForest = new BiomeGenForest(27, 2).setBiomeName("Birch Forest").setColor(3175492);
        birchForestHills = new BiomeGenForest(28, 2).setBiomeName("Birch Forest Hills").setColor(2055986).setHeight(BiomeGenBase.height_LowHills);
        roofedForest = new BiomeGenForest(29, 3).setColor(4215066).setBiomeName("Roofed Forest");
        coldTaiga = new BiomeGenTaiga(30, 0).setColor(3233098).setBiomeName("Cold Taiga").setFillerBlockMetadata(5159473).setEnableSnow().setTemperatureRainfall(-0.5f, 0.4f).setHeight(BiomeGenBase.height_MidPlains).func_150563_c(16777215);
        coldTaigaHills = new BiomeGenTaiga(31, 0).setColor(2375478).setBiomeName("Cold Taiga Hills").setFillerBlockMetadata(5159473).setEnableSnow().setTemperatureRainfall(-0.5f, 0.4f).setHeight(BiomeGenBase.height_LowHills).func_150563_c(16777215);
        megaTaiga = new BiomeGenTaiga(32, 1).setColor(5858897).setBiomeName("Mega Taiga").setFillerBlockMetadata(5159473).setTemperatureRainfall(0.3f, 0.8f).setHeight(BiomeGenBase.height_MidPlains);
        megaTaigaHills = new BiomeGenTaiga(33, 1).setColor(4542270).setBiomeName("Mega Taiga Hills").setFillerBlockMetadata(5159473).setTemperatureRainfall(0.3f, 0.8f).setHeight(BiomeGenBase.height_LowHills);
        extremeHillsPlus = new BiomeGenHills(34, true).setColor(5271632).setBiomeName("Extreme Hills+").setHeight(BiomeGenBase.height_MidHills).setTemperatureRainfall(0.2f, 0.3f);
        savanna = new BiomeGenSavanna(35).setColor(12431967).setBiomeName("Savanna").setTemperatureRainfall(1.2f, 0.0f).setDisableRain().setHeight(BiomeGenBase.height_LowPlains);
        savannaPlateau = new BiomeGenSavanna(36).setColor(10984804).setBiomeName("Savanna Plateau").setTemperatureRainfall(1.0f, 0.0f).setDisableRain().setHeight(BiomeGenBase.height_HighPlateaus);
        mesa = new BiomeGenMesa(37, false, false).setColor(14238997).setBiomeName("Mesa");
        mesaPlateau_F = new BiomeGenMesa(38, false, true).setColor(11573093).setBiomeName("Mesa Plateau F").setHeight(BiomeGenBase.height_HighPlateaus);
        mesaPlateau = new BiomeGenMesa(39, false, false).setColor(13274213).setBiomeName("Mesa Plateau").setHeight(BiomeGenBase.height_HighPlateaus);
        field_180279_ad = BiomeGenBase.ocean;
        BiomeGenBase.plains.createMutation();
        BiomeGenBase.desert.createMutation();
        BiomeGenBase.forest.createMutation();
        BiomeGenBase.taiga.createMutation();
        BiomeGenBase.swampland.createMutation();
        BiomeGenBase.icePlains.createMutation();
        BiomeGenBase.jungle.createMutation();
        BiomeGenBase.jungleEdge.createMutation();
        BiomeGenBase.coldTaiga.createMutation();
        BiomeGenBase.savanna.createMutation();
        BiomeGenBase.savannaPlateau.createMutation();
        BiomeGenBase.mesa.createMutation();
        BiomeGenBase.mesaPlateau_F.createMutation();
        BiomeGenBase.mesaPlateau.createMutation();
        BiomeGenBase.birchForest.createMutation();
        BiomeGenBase.birchForestHills.createMutation();
        BiomeGenBase.roofedForest.createMutation();
        BiomeGenBase.megaTaiga.createMutation();
        BiomeGenBase.extremeHills.createMutation();
        BiomeGenBase.extremeHillsPlus.createMutation();
        BiomeGenBase.megaTaiga.createMutatedBiome(BiomeGenBase.megaTaigaHills.biomeID + 128).setBiomeName("Redwood Taiga Hills M");
        final BiomeGenBase[] biomeList2 = BiomeGenBase.biomeList;
        while (0 < biomeList2.length) {
            final BiomeGenBase biomeGenBase = biomeList2[0];
            if (biomeGenBase != null) {
                if (BiomeGenBase.BIOME_ID_MAP.containsKey(biomeGenBase.biomeName)) {
                    throw new Error("Biome \"" + biomeGenBase.biomeName + "\" is defined as both ID " + ((BiomeGenBase)BiomeGenBase.BIOME_ID_MAP.get(biomeGenBase.biomeName)).biomeID + " and " + biomeGenBase.biomeID);
                }
                BiomeGenBase.BIOME_ID_MAP.put(biomeGenBase.biomeName, biomeGenBase);
                if (biomeGenBase.biomeID < 128) {
                    BiomeGenBase.explorationBiomesList.add(biomeGenBase);
                }
            }
            int n = 0;
            ++n;
        }
        BiomeGenBase.explorationBiomesList.remove(BiomeGenBase.hell);
        BiomeGenBase.explorationBiomesList.remove(BiomeGenBase.sky);
        BiomeGenBase.explorationBiomesList.remove(BiomeGenBase.frozenOcean);
        BiomeGenBase.explorationBiomesList.remove(BiomeGenBase.extremeHillsEdge);
        temperatureNoise = new NoiseGeneratorPerlin(new Random(1234L), 1);
        GRASS_COLOR_NOISE = new NoiseGeneratorPerlin(new Random(2345L), 1);
        DOUBLE_PLANT_GENERATOR = new WorldGenDoublePlant();
    }
    
    protected BiomeGenBase func_150557_a(final int n, final boolean b) {
        this.color = n;
        if (b) {
            this.field_150609_ah = (n & 0xFEFEFE) >> 1;
        }
        else {
            this.field_150609_ah = n;
        }
        return this;
    }
    
    public boolean isSnowyBiome() {
        return this.enableSnow;
    }
    
    protected BiomeGenBase setBiomeName(final String biomeName) {
        this.biomeName = biomeName;
        return this;
    }
    
    protected BiomeGenBase setTemperatureRainfall(final float temperature, final float rainfall) {
        if (temperature > 0.1f && temperature < 0.2f) {
            throw new IllegalArgumentException("Please avoid temperatures in the range 0.1 - 0.2 because of snow");
        }
        this.temperature = temperature;
        this.rainfall = rainfall;
        return this;
    }
    
    public void genTerrainBlocks(final World world, final Random random, final ChunkPrimer chunkPrimer, final int n, final int n2, final double n3) {
        this.generateBiomeTerrain(world, random, chunkPrimer, n, n2, n3);
    }
    
    protected BiomeGenBase createMutation() {
        return this.createMutatedBiome(this.biomeID + 128);
    }
    
    public int getGrassColorAtPos(final BlockPos blockPos) {
        return ColorizerGrass.getGrassColor(MathHelper.clamp_float(this.getFloatTemperature(blockPos), 0.0f, 1.0f), MathHelper.clamp_float(this.getFloatRainfall(), 0.0f, 1.0f));
    }
    
    public boolean isEqualTo(final BiomeGenBase biomeGenBase) {
        return biomeGenBase == this || (biomeGenBase != null && this.getBiomeClass() == biomeGenBase.getBiomeClass());
    }
    
    public boolean canSpawnLightningBolt() {
        return !this.isSnowyBiome() && this.enableRain;
    }
    
    public final float getFloatRainfall() {
        return this.rainfall;
    }
    
    public final void generateBiomeTerrain(final World world, final Random random, final ChunkPrimer chunkPrimer, final int n, final int n2, final double n3) {
        final int func_181545_F = world.func_181545_F();
        IBlockState blockState = this.topBlock;
        IBlockState blockState2 = this.fillerBlock;
        final int n4 = (int)(n3 / 3.0 + 3.0 + random.nextDouble() * 0.25);
        final int n5 = n & 0xF;
        final int n6 = n2 & 0xF;
        final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        while (true) {
            if (255 <= random.nextInt(5)) {
                chunkPrimer.setBlockState(n6, 255, n5, Blocks.bedrock.getDefaultState());
            }
            else {
                final IBlockState blockState3 = chunkPrimer.getBlockState(n6, 255, n5);
                if (blockState3.getBlock().getMaterial() != Material.air) {
                    if (blockState3.getBlock() == Blocks.stone) {
                        if (n4 <= 0) {
                            blockState = null;
                            blockState2 = Blocks.stone.getDefaultState();
                        }
                        else if (255 >= func_181545_F - 4 && 255 <= func_181545_F + 1) {
                            blockState = this.topBlock;
                            blockState2 = this.fillerBlock;
                        }
                        if (255 < func_181545_F && (blockState == null || blockState.getBlock().getMaterial() == Material.air)) {
                            if (this.getFloatTemperature(mutableBlockPos.func_181079_c(n, 255, n2)) < 0.15f) {
                                blockState = Blocks.ice.getDefaultState();
                            }
                            else {
                                blockState = Blocks.water.getDefaultState();
                            }
                        }
                        if (255 >= func_181545_F - 1) {
                            chunkPrimer.setBlockState(n6, 255, n5, blockState);
                        }
                        else if (255 < func_181545_F - 7 - n4) {
                            blockState = null;
                            blockState2 = Blocks.stone.getDefaultState();
                            chunkPrimer.setBlockState(n6, 255, n5, Blocks.gravel.getDefaultState());
                        }
                        else {
                            chunkPrimer.setBlockState(n6, 255, n5, blockState2);
                        }
                    }
                }
            }
            int n7 = 0;
            --n7;
        }
    }
    
    public final float getFloatTemperature(final BlockPos blockPos) {
        if (blockPos.getY() > 64) {
            return this.temperature - ((float)(BiomeGenBase.temperatureNoise.func_151601_a(blockPos.getX() * 1.0 / 8.0, blockPos.getZ() * 1.0 / 8.0) * 4.0) + blockPos.getY() - 64.0f) * 0.05f / 30.0f;
        }
        return this.temperature;
    }
    
    protected BiomeGenBase setDisableRain() {
        this.enableRain = false;
        return this;
    }
    
    public static BiomeGenBase[] getBiomeGenArray() {
        return BiomeGenBase.biomeList;
    }
    
    public TempCategory getTempCategory() {
        return (this.temperature < 0.2) ? TempCategory.COLD : ((this.temperature < 1.0) ? TempCategory.MEDIUM : TempCategory.WARM);
    }
    
    public WorldGenAbstractTree genBigTreeChance(final Random random) {
        return (random.nextInt(10) == 0) ? this.worldGeneratorBigTree : this.worldGeneratorTrees;
    }
    
    protected BiomeGenBase(final int biomeID) {
        this.topBlock = Blocks.grass.getDefaultState();
        this.fillerBlock = Blocks.dirt.getDefaultState();
        this.fillerBlockMetadata = 5169201;
        this.minHeight = BiomeGenBase.height_Default.rootHeight;
        this.maxHeight = BiomeGenBase.height_Default.variation;
        this.temperature = 0.5f;
        this.rainfall = 0.5f;
        this.waterColorMultiplier = 16777215;
        this.spawnableMonsterList = Lists.newArrayList();
        this.spawnableCreatureList = Lists.newArrayList();
        this.spawnableWaterCreatureList = Lists.newArrayList();
        this.spawnableCaveCreatureList = Lists.newArrayList();
        this.enableRain = true;
        this.worldGeneratorTrees = new WorldGenTrees(false);
        this.worldGeneratorBigTree = new WorldGenBigTree(false);
        this.worldGeneratorSwamp = new WorldGenSwamp();
        this.biomeID = biomeID;
        BiomeGenBase.biomeList[biomeID] = this;
        this.theBiomeDecorator = this.createBiomeDecorator();
        this.spawnableCreatureList.add(new SpawnListEntry(EntitySheep.class, 12, 4, 4));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityRabbit.class, 10, 3, 3));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityPig.class, 10, 4, 4));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityChicken.class, 10, 4, 4));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityCow.class, 8, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySpider.class, 100, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 100, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 100, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityCreeper.class, 100, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySlime.class, 100, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityEnderman.class, 10, 1, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityWitch.class, 5, 1, 1));
        this.spawnableWaterCreatureList.add(new SpawnListEntry(EntitySquid.class, 10, 4, 4));
        this.spawnableCaveCreatureList.add(new SpawnListEntry(EntityBat.class, 10, 8, 8));
    }
    
    protected BiomeGenBase setEnableSnow() {
        this.enableSnow = true;
        return this;
    }
    
    public BlockFlower.EnumFlowerType pickRandomFlower(final Random random, final BlockPos blockPos) {
        return (random.nextInt(3) > 0) ? BlockFlower.EnumFlowerType.DANDELION : BlockFlower.EnumFlowerType.POPPY;
    }
    
    protected final BiomeGenBase setHeight(final Height height) {
        this.minHeight = height.rootHeight;
        this.maxHeight = height.variation;
        return this;
    }
    
    public List getSpawnableList(final EnumCreatureType enumCreatureType) {
        switch (BiomeGenBase$1.$SwitchMap$net$minecraft$entity$EnumCreatureType[enumCreatureType.ordinal()]) {
            case 1: {
                return this.spawnableMonsterList;
            }
            case 2: {
                return this.spawnableCreatureList;
            }
            case 3: {
                return this.spawnableWaterCreatureList;
            }
            case 4: {
                return this.spawnableCaveCreatureList;
            }
            default: {
                return Collections.emptyList();
            }
        }
    }
    
    public float getSpawningChance() {
        return 0.1f;
    }
    
    public final int getIntRainfall() {
        return (int)(this.rainfall * 65536.0f);
    }
    
    public void decorate(final World world, final Random random, final BlockPos blockPos) {
        this.theBiomeDecorator.decorate(world, random, this, blockPos);
    }
    
    protected BiomeDecorator createBiomeDecorator() {
        return new BiomeDecorator();
    }
    
    public int getSkyColorByTemp(float clamp_float) {
        clamp_float /= 3.0f;
        clamp_float = MathHelper.clamp_float(clamp_float, -1.0f, 1.0f);
        return MathHelper.func_181758_c(0.62222224f - clamp_float * 0.05f, 0.5f + clamp_float * 0.1f, 1.0f);
    }
    
    public static BiomeGenBase getBiomeFromBiomeList(final int n, final BiomeGenBase biomeGenBase) {
        if (n >= 0 && n <= BiomeGenBase.biomeList.length) {
            final BiomeGenBase biomeGenBase2 = BiomeGenBase.biomeList[n];
            return (biomeGenBase2 == null) ? biomeGenBase : biomeGenBase2;
        }
        BiomeGenBase.logger.warn("Biome ID is out of bounds: " + n + ", defaulting to 0 (Ocean)");
        return BiomeGenBase.ocean;
    }
    
    public Class getBiomeClass() {
        return this.getClass();
    }
    
    public static class Height
    {
        public float variation;
        public float rootHeight;
        
        public Height(final float rootHeight, final float variation) {
            this.rootHeight = rootHeight;
            this.variation = variation;
        }
        
        public Height attenuate() {
            return new Height(this.rootHeight * 0.8f, this.variation * 0.6f);
        }
    }
    
    public enum TempCategory
    {
        private static final TempCategory[] $VALUES;
        
        COLD("COLD", 1), 
        WARM("WARM", 3), 
        MEDIUM("MEDIUM", 2), 
        OCEAN("OCEAN", 0);
        
        private TempCategory(final String s, final int n) {
        }
        
        static {
            $VALUES = new TempCategory[] { TempCategory.OCEAN, TempCategory.COLD, TempCategory.MEDIUM, TempCategory.WARM };
        }
    }
    
    public static class SpawnListEntry extends WeightedRandom.Item
    {
        public int minGroupCount;
        public Class entityClass;
        public int maxGroupCount;
        
        public SpawnListEntry(final Class entityClass, final int n, final int minGroupCount, final int maxGroupCount) {
            super(n);
            this.entityClass = entityClass;
            this.minGroupCount = minGroupCount;
            this.maxGroupCount = maxGroupCount;
        }
        
        @Override
        public String toString() {
            return this.entityClass.getSimpleName() + "*(" + this.minGroupCount + "-" + this.maxGroupCount + "):" + this.itemWeight;
        }
    }
}
