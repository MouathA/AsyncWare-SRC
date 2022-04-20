package optfine;

import javax.imageio.*;
import java.io.*;
import java.awt.image.*;
import net.minecraft.client.particle.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.block.properties.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.entity.*;
import net.minecraft.client.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.block.*;
import net.minecraft.world.biome.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;

public class CustomColorizer
{
    private static Random random;
    private static int[] skyColors;
    private static int[][] blockPalettes;
    private static int[] swampGrassColors;
    private static final int TYPE_GRASS = 1;
    private static final int TYPE_FOLIAGE = 2;
    private static int[] foliageBirchColors;
    private static int[] stemColors;
    private static int[] waterColors;
    private static Vec3 fogColorEnd;
    private static boolean useDefaultColorMultiplier;
    private static int[] myceliumParticleColors;
    private static int[] fogColors;
    private static Vec3 fogColorNether;
    private static int[] lightMapsHeight;
    private static int[][] paletteColors;
    private static float[][] sunRgbs;
    private static float[][] torchRgbs;
    private static int[] swampFoliageColors;
    private static int[] foliageColors;
    private static Vec3 skyColorEnd;
    private static int[] foliagePineColors;
    private static int[] underwaterColors;
    private static int[] redstoneColors;
    private static float[][][] lightMapsColorsRgb;
    private static final int TYPE_NONE = 0;
    
    private static int getTextureHeight(final String s, final int n) {
        final InputStream resourceStream = Config.getResourceStream(new ResourceLocation(s));
        if (resourceStream == null) {
            return n;
        }
        final BufferedImage read = ImageIO.read(resourceStream);
        return (read == null) ? n : read.getHeight();
    }
    
    public static int getRedstoneColor(final int n) {
        return (CustomColorizer.redstoneColors == null) ? -1 : ((n >= 0 && n <= 15) ? (CustomColorizer.redstoneColors[n] & 0xFFFFFF) : -1);
    }
    
    public static void updateMyceliumFX(final EntityFX entityFX) {
        if (CustomColorizer.myceliumParticleColors != null) {
            final int n = CustomColorizer.myceliumParticleColors[CustomColorizer.random.nextInt(CustomColorizer.myceliumParticleColors.length)];
            entityFX.setRBGColorF((n >> 16 & 0xFF) / 255.0f, (n >> 8 & 0xFF) / 255.0f, (n & 0xFF) / 255.0f);
        }
    }
    
    private static float[][] toRgb(final int[] array) {
        final float[][] array2 = new float[array.length][3];
        while (0 < array.length) {
            final int n = array[0];
            final float n2 = (n >> 16 & 0xFF) / 255.0f;
            final float n3 = (n >> 8 & 0xFF) / 255.0f;
            final float n4 = (n & 0xFF) / 255.0f;
            final float[] array3 = array2[0];
            array3[0] = n2;
            array3[1] = n3;
            array3[2] = n4;
            int n5 = 0;
            ++n5;
        }
        return array2;
    }
    
    private static int averageColor(final int n, final int n2) {
        return ((n >> 16 & 0xFF) + (n2 >> 16 & 0xFF)) / 2 << 16 | ((n >> 8 & 0xFF) + (n2 >> 8 & 0xFF)) / 2 << 8 | ((n & 0xFF) + (n2 & 0xFF)) / 2;
    }
    
    public static int mixColors(final int n, final int n2, final float n3) {
        if (n3 <= 0.0f) {
            return n2;
        }
        if (n3 >= 1.0f) {
            return n;
        }
        final float n4 = 1.0f - n3;
        return (int)((n >> 16 & 0xFF) * n3 + (n2 >> 16 & 0xFF) * n4) << 16 | (int)((n >> 8 & 0xFF) * n3 + (n2 >> 8 & 0xFF) * n4) << 8 | (int)((n & 0xFF) * n3 + (n2 & 0xFF) * n4);
    }
    
    public static Vec3 getUnderwaterColor(final IBlockAccess blockAccess, final double n, final double n2, final double n3) {
        if (CustomColorizer.underwaterColors == null) {
            return null;
        }
        final int smoothColor = getSmoothColor(CustomColorizer.underwaterColors, blockAccess, n, n2, n3, 7, 1);
        return new Vec3((smoothColor >> 16 & 0xFF) / 255.0f, (smoothColor >> 8 & 0xFF) / 255.0f, (smoothColor & 0xFF) / 255.0f);
    }
    
    private static int[] getCustomColors(final String s, final String[] array, final int n) {
        while (0 < array.length) {
            final int[] customColors = getCustomColors(s + array[0], n);
            if (customColors != null) {
                return customColors;
            }
            int n2 = 0;
            ++n2;
        }
        return null;
    }
    
    public static Vec3 getSkyColorEnd(final Vec3 vec3) {
        return (CustomColorizer.skyColorEnd == null) ? vec3 : CustomColorizer.skyColorEnd;
    }
    
    public static Vec3 getSkyColor(final Vec3 vec3, final IBlockAccess blockAccess, final double n, final double n2, final double n3) {
        if (CustomColorizer.skyColors == null) {
            return vec3;
        }
        final int smoothColor = getSmoothColor(CustomColorizer.skyColors, blockAccess, n, n2, n3, 7, 1);
        return new Vec3((smoothColor >> 16 & 0xFF) / 255.0f * ((float)vec3.xCoord / 0.5f), (smoothColor >> 8 & 0xFF) / 255.0f * ((float)vec3.yCoord / 0.66275f), (smoothColor & 0xFF) / 255.0f * (float)vec3.zCoord);
    }
    
    private static void readColorProperties(final String s) {
        final InputStream resourceStream = Config.getResourceStream(new ResourceLocation(s));
        if (resourceStream == null) {
            return;
        }
        Config.log("Loading " + s);
        final Properties properties = new Properties();
        properties.load(resourceStream);
        CustomColorizer.lilyPadColor = readColor(properties, "lilypad");
        CustomColorizer.particleWaterColor = readColor(properties, new String[] { "particle.water", "drop.water" });
        CustomColorizer.particlePortalColor = readColor(properties, "particle.portal");
        CustomColorizer.fogColorNether = readColorVec3(properties, "fog.nether");
        CustomColorizer.fogColorEnd = readColorVec3(properties, "fog.end");
        CustomColorizer.skyColorEnd = readColorVec3(properties, "sky.end");
        readCustomPalettes(properties, s);
    }
    
    public static int getSmoothColor(final int[] array, final IBlockAccess blockAccess, final double n, final double n2, final double n3, final int n4, final int n5) {
        if (array == null) {
            return -1;
        }
        final int floor_double = MathHelper.floor_double(n);
        final int floor_double2 = MathHelper.floor_double(n2);
        final int floor_double3 = MathHelper.floor_double(n3);
        final int n6 = n4 * n5 / 2;
        final BlockPosM blockPosM = new BlockPosM(0, 0, 0);
        while (0 <= floor_double + n6) {
            while (0 <= floor_double3 + n6) {
                blockPosM.setXyz(0, floor_double2, 0);
                getCustomColor(array, blockAccess, blockPosM);
                int n7 = 0;
                ++n7;
            }
        }
        return 0;
    }
    
    private static int getRedstoneLevel(final IBlockState blockState, final int n) {
        if (!(blockState.getBlock() instanceof BlockRedstoneWire)) {
            return n;
        }
        final Comparable value = blockState.getValue(BlockRedstoneWire.POWER);
        if (!(value instanceof Integer)) {
            return n;
        }
        return (int)value;
    }
    
    static {
        CustomColorizer.grassColors = null;
        CustomColorizer.waterColors = null;
        CustomColorizer.foliageColors = null;
        CustomColorizer.foliagePineColors = null;
        CustomColorizer.foliageBirchColors = null;
        CustomColorizer.swampFoliageColors = null;
        CustomColorizer.swampGrassColors = null;
        CustomColorizer.blockPalettes = null;
        CustomColorizer.paletteColors = null;
        CustomColorizer.skyColors = null;
        CustomColorizer.fogColors = null;
        CustomColorizer.underwaterColors = null;
        CustomColorizer.lightMapsColorsRgb = null;
        CustomColorizer.lightMapsHeight = null;
        CustomColorizer.sunRgbs = new float[16][3];
        CustomColorizer.torchRgbs = new float[16][3];
        CustomColorizer.redstoneColors = null;
        CustomColorizer.stemColors = null;
        CustomColorizer.myceliumParticleColors = null;
        CustomColorizer.useDefaultColorMultiplier = true;
        CustomColorizer.fogColorNether = null;
        CustomColorizer.fogColorEnd = null;
        CustomColorizer.skyColorEnd = null;
        CustomColorizer.random = new Random();
    }
    
    public static void updatePortalFX(final EntityFX entityFX) {
    }
    
    public static Vec3 getWorldSkyColor(Vec3 vec3, final WorldClient worldClient, final Entity entity, final float n) {
        switch (worldClient.provider.getDimensionId()) {
            case 0: {
                vec3 = getSkyColor(vec3, Minecraft.getMinecraft().theWorld, entity.posX, entity.posY + 1.0, entity.posZ);
                break;
            }
            case 1: {
                vec3 = getSkyColorEnd(vec3);
                break;
            }
        }
        return vec3;
    }
    
    public static void updateWaterFX(final EntityFX entityFX, final IBlockAccess blockAccess, final double n, final double n2, final double n3) {
        if (CustomColorizer.waterColors != null) {
            final int fluidColor = getFluidColor(Blocks.water, blockAccess, new BlockPos(n, n2, n3));
            entityFX.setRBGColorF((fluidColor >> 16 & 0xFF) / 255.0f, (fluidColor >> 8 & 0xFF) / 255.0f, (fluidColor & 0xFF) / 255.0f);
        }
    }
    
    private static int[] getCustomColors(final String s, final int n) {
        final ResourceLocation resourceLocation = new ResourceLocation(s);
        if (Config.getResourceStream(resourceLocation) == null) {
            return null;
        }
        final int[] imageData = TextureUtil.readImageData(Config.getResourceManager(), resourceLocation);
        if (imageData == null) {
            return null;
        }
        if (n > 0 && imageData.length != n) {
            Config.log("Invalid custom colors length: " + imageData.length + ", path: " + s);
            return null;
        }
        Config.log("Loading custom colors: " + s);
        return imageData;
    }
    
    private static int readColor(final Properties properties, final String[] array) {
        while (0 < array.length) {
            final int color = readColor(properties, array[0]);
            if (color >= 0) {
                return color;
            }
            int n = 0;
            ++n;
        }
        return -1;
    }
    
    private static Vec3 readColorVec3(final Properties properties, final String s) {
        final int color = readColor(properties, s);
        if (color < 0) {
            return null;
        }
        return new Vec3((color >> 16 & 0xFF) / 255.0f, (color >> 8 & 0xFF) / 255.0f, (color & 0xFF) / 255.0f);
    }
    
    public static Vec3 getFogColorNether(final Vec3 vec3) {
        return (CustomColorizer.fogColorNether == null) ? vec3 : CustomColorizer.fogColorNether;
    }
    
    public static void update() {
        CustomColorizer.grassColors = null;
        CustomColorizer.waterColors = null;
        CustomColorizer.foliageColors = null;
        CustomColorizer.foliageBirchColors = null;
        CustomColorizer.foliagePineColors = null;
        CustomColorizer.swampGrassColors = null;
        CustomColorizer.swampFoliageColors = null;
        CustomColorizer.skyColors = null;
        CustomColorizer.fogColors = null;
        CustomColorizer.underwaterColors = null;
        CustomColorizer.redstoneColors = null;
        CustomColorizer.stemColors = null;
        CustomColorizer.myceliumParticleColors = null;
        CustomColorizer.lightMapsColorsRgb = null;
        CustomColorizer.lightMapsHeight = null;
        CustomColorizer.lilyPadColor = -1;
        CustomColorizer.particleWaterColor = -1;
        CustomColorizer.particlePortalColor = -1;
        CustomColorizer.fogColorNether = null;
        CustomColorizer.fogColorEnd = null;
        CustomColorizer.skyColorEnd = null;
        CustomColorizer.blockPalettes = null;
        CustomColorizer.paletteColors = null;
        CustomColorizer.useDefaultColorMultiplier = true;
        final String s = "mcpatcher/colormap/";
        CustomColorizer.grassColors = getCustomColors("textures/colormap/grass.png", 65536);
        CustomColorizer.foliageColors = getCustomColors("textures/colormap/foliage.png", 65536);
        CustomColorizer.waterColors = getCustomColors(s, new String[] { "water.png", "watercolorX.png" }, 65536);
        if (Config.isCustomColors()) {
            CustomColorizer.foliagePineColors = getCustomColors(s, new String[] { "pine.png", "pinecolor.png" }, 65536);
            CustomColorizer.foliageBirchColors = getCustomColors(s, new String[] { "birch.png", "birchcolor.png" }, 65536);
            CustomColorizer.swampGrassColors = getCustomColors(s, new String[] { "swampgrass.png", "swampgrasscolor.png" }, 65536);
            CustomColorizer.swampFoliageColors = getCustomColors(s, new String[] { "swampfoliage.png", "swampfoliagecolor.png" }, 65536);
            CustomColorizer.skyColors = getCustomColors(s, new String[] { "sky0.png", "skycolor0.png" }, 65536);
            CustomColorizer.fogColors = getCustomColors(s, new String[] { "fog0.png", "fogcolor0.png" }, 65536);
            CustomColorizer.underwaterColors = getCustomColors(s, new String[] { "underwater.png", "underwatercolor.png" }, 65536);
            CustomColorizer.redstoneColors = getCustomColors(s, new String[] { "redstone.png", "redstonecolor.png" }, 16);
            CustomColorizer.stemColors = getCustomColors(s, new String[] { "stem.png", "stemcolor.png" }, 8);
            CustomColorizer.myceliumParticleColors = getCustomColors(s, new String[] { "myceliumparticle.png", "myceliumparticlecolor.png" }, -1);
            final int[][] array = new int[3][];
            CustomColorizer.lightMapsColorsRgb = new float[3][][];
            CustomColorizer.lightMapsHeight = new int[3];
            while (0 < array.length) {
                final String string = "mcpatcher/lightmap/world" + -1 + ".png";
                array[0] = getCustomColors(string, -1);
                if (array[0] != null) {
                    CustomColorizer.lightMapsColorsRgb[0] = toRgb(array[0]);
                }
                CustomColorizer.lightMapsHeight[0] = getTextureHeight(string, 32);
                int n = 0;
                ++n;
            }
            readColorProperties("mcpatcher/color.properties");
        }
    }
    
    public static Vec3 getFogColorEnd(final Vec3 vec3) {
        return (CustomColorizer.fogColorEnd == null) ? vec3 : CustomColorizer.fogColorEnd;
    }
    
    public static int getColorMultiplier(final BakedQuad bakedQuad, final Block block, final IBlockAccess blockAccess, final BlockPos blockPos, final RenderEnv renderEnv) {
        if (CustomColorizer.useDefaultColorMultiplier) {
            return -1;
        }
        int[] array = null;
        int[] array2 = null;
        if (CustomColorizer.blockPalettes != null) {
            final int blockId = renderEnv.getBlockId();
            if (blockId >= 0 && blockId < 256) {
                final int[] array3 = CustomColorizer.blockPalettes[blockId];
                if (array3.length > 1) {
                    renderEnv.getMetadata();
                    final int n = array3[0];
                }
                else {
                    final int n2 = array3[0];
                }
                array = CustomColorizer.paletteColors[1];
            }
            if (array != null) {
                if (Config.isSmoothBiomes()) {
                    return getSmoothColorMultiplier(block, blockAccess, blockPos, array, array, 0, 0, renderEnv);
                }
                return getCustomColor(array, blockAccess, blockPos);
            }
        }
        if (!bakedQuad.hasTintIndex()) {
            return -1;
        }
        if (block == Blocks.waterlily) {
            return getLilypadColorMultiplier(blockAccess, blockPos);
        }
        if (block instanceof BlockStem) {
            return getStemColorMultiplier(block, blockAccess, blockPos, renderEnv);
        }
        final boolean swampColors = Config.isSwampColors();
        if (block != Blocks.grass && block != Blocks.tallgrass) {
            if (block == Blocks.leaves) {
                Config.isSmoothBiomes();
                renderEnv.getMetadata();
                array = CustomColorizer.foliageColors;
                if (swampColors) {
                    array2 = CustomColorizer.swampFoliageColors;
                }
                else {
                    array2 = array;
                }
            }
            else if (block == Blocks.vine) {
                Config.isSmoothBiomes();
                array = CustomColorizer.foliageColors;
                if (swampColors) {
                    array2 = CustomColorizer.swampFoliageColors;
                }
                else {
                    array2 = array;
                }
            }
        }
        else {
            Config.isSmoothBiomes();
            array = CustomColorizer.grassColors;
            if (swampColors) {
                array2 = CustomColorizer.swampGrassColors;
            }
            else {
                array2 = array;
            }
        }
        if (array2 != array && blockAccess.getBiomeGenForCoords(blockPos) == BiomeGenBase.swampland) {
            array = array2;
        }
        return (array != null) ? getCustomColor(array, blockAccess, blockPos) : -1;
    }
    
    public static int getFluidColor(final Block block, final IBlockAccess blockAccess, final BlockPos blockPos) {
        return (block.getMaterial() != Material.water) ? block.colorMultiplier(blockAccess, blockPos) : ((CustomColorizer.waterColors != null) ? (Config.isSmoothBiomes() ? getSmoothColor(CustomColorizer.waterColors, blockAccess, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 3, 1) : getCustomColor(CustomColorizer.waterColors, blockAccess, blockPos)) : (Config.isSwampColors() ? block.colorMultiplier(blockAccess, blockPos) : 16777215));
    }
    
    public static boolean updateLightmap(final World world, final float n, final int[] array, final boolean b) {
        if (world == null) {
            return false;
        }
        if (CustomColorizer.lightMapsColorsRgb == null) {
            return false;
        }
        if (!Config.isCustomColors()) {
            return false;
        }
        final int dimensionId = world.provider.getDimensionId();
        if (dimensionId < -1 || dimensionId > 1) {
            return false;
        }
        final int n2 = dimensionId + 1;
        final float[][] array2 = CustomColorizer.lightMapsColorsRgb[n2];
        if (array2 == null) {
            return false;
        }
        final int n3 = CustomColorizer.lightMapsHeight[n2];
        if (b && n3 < 64) {
            return false;
        }
        final int n4 = array2.length / n3;
        if (n4 < 16) {
            Config.warn("Invalid lightmap width: " + n4 + " for: /environment/lightmap" + dimensionId + ".png");
            CustomColorizer.lightMapsColorsRgb[n2] = null;
            return false;
        }
        if (b) {}
        float n5 = 1.1666666f * (world.getSunBrightness(1.0f) - 0.2f);
        if (world.getLastLightningBolt() > 0) {
            n5 = 1.0f;
        }
        final float n6 = Config.limitTo1(n5) * (n4 - 1);
        final float n7 = Config.limitTo1(n + 0.5f) * (n4 - 1);
        final float limitTo1 = Config.limitTo1(Config.getGameSettings().gammaSetting);
        final boolean b2 = limitTo1 > 1.0E-4f;
        getLightMapColumn(array2, n6, 0, n4, CustomColorizer.sunRgbs);
        getLightMapColumn(array2, n7, 0 + 16 * n4, n4, CustomColorizer.torchRgbs);
        final float[] array3 = new float[3];
        while (true) {
            float limitTo2 = Config.limitTo1(CustomColorizer.sunRgbs[0][0] + CustomColorizer.torchRgbs[0][0]);
            if (b2) {
                final float n8 = 1.0f - limitTo2;
                limitTo2 = limitTo1 * (1.0f - n8 * n8 * n8 * n8) + (1.0f - limitTo1) * limitTo2;
            }
            array3[0] = limitTo2;
            int n9 = 0;
            ++n9;
        }
    }
    
    private static int getCustomColor(final int[] array, final IBlockAccess blockAccess, final BlockPos blockPos) {
        final BiomeGenBase biomeGenForCoords = blockAccess.getBiomeGenForCoords(blockPos);
        final double n = MathHelper.clamp_float(biomeGenForCoords.getFloatTemperature(blockPos), 0.0f, 1.0f);
        return array[(int)((1.0 - MathHelper.clamp_float(biomeGenForCoords.getFloatRainfall(), 0.0f, 1.0f) * n) * 255.0) << 8 | (int)((1.0 - n) * 255.0)] & 0xFFFFFF;
    }
    
    public static Vec3 getWorldFogColor(Vec3 vec3, final WorldClient worldClient, final Entity entity, final float n) {
        switch (worldClient.provider.getDimensionId()) {
            case -1: {
                vec3 = getFogColorNether(vec3);
                break;
            }
            case 0: {
                vec3 = getFogColor(vec3, Minecraft.getMinecraft().theWorld, entity.posX, entity.posY + 1.0, entity.posZ);
                break;
            }
            case 1: {
                vec3 = getFogColorEnd(vec3);
                break;
            }
        }
        return vec3;
    }
    
    public static int getLilypadColorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return Blocks.waterlily.colorMultiplier(blockAccess, blockPos);
    }
    
    private static int getSmoothColorMultiplier(final Block block, final IBlockAccess blockAccess, final BlockPos blockPos, final int[] array, final int[] array2, final int n, final int n2, final RenderEnv renderEnv) {
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        final BlockPosM colorizerBlockPos = renderEnv.getColorizerBlockPos();
        int n3 = x - 1;
        while (0 <= x + 1) {
            int n4 = z - 1;
            while (0 <= z + 1) {
                colorizerBlockPos.setXyz(0, y, 0);
                int[] array3 = array;
                if (array2 != array && blockAccess.getBiomeGenForCoords(colorizerBlockPos) == BiomeGenBase.swampland) {
                    array3 = array2;
                }
                if (array3 == null) {
                    switch (n) {
                        case 1: {
                            blockAccess.getBiomeGenForCoords(colorizerBlockPos).getGrassColorAtPos(colorizerBlockPos);
                            break;
                        }
                        case 2: {
                            if ((n2 & 0x3) == 0x1) {
                                ColorizerFoliage.getFoliageColorPine();
                                break;
                            }
                            if ((n2 & 0x3) == 0x2) {
                                ColorizerFoliage.getFoliageColorBirch();
                                break;
                            }
                            blockAccess.getBiomeGenForCoords(colorizerBlockPos).getFoliageColorAtPos(colorizerBlockPos);
                            break;
                        }
                        default: {
                            block.colorMultiplier(blockAccess, colorizerBlockPos);
                            break;
                        }
                    }
                }
                else {
                    getCustomColor(array3, blockAccess, colorizerBlockPos);
                }
                ++n4;
            }
            ++n3;
        }
        return 0;
    }
    
    public static void updateUseDefaultColorMultiplier() {
        CustomColorizer.useDefaultColorMultiplier = (CustomColorizer.foliageBirchColors == null && CustomColorizer.foliagePineColors == null && CustomColorizer.swampGrassColors == null && CustomColorizer.swampFoliageColors == null && CustomColorizer.blockPalettes == null && Config.isSwampColors() && Config.isSmoothBiomes());
    }
    
    private static int readColor(final Properties properties, final String s) {
        final String property = properties.getProperty(s);
        if (property == null) {
            return -1;
        }
        final int n = Integer.parseInt(property, 16) & 0xFFFFFF;
        Config.log("Custom color: " + s + " = " + property);
        return n;
    }
    
    private static void getLightMapColumn(final float[][] array, final float n, final int n2, final int n3, final float[][] array2) {
        final int n4 = (int)Math.floor(n);
        final int n5 = (int)Math.ceil(n);
        if (n4 == n5) {
            final float[] array3 = array[n2 + 0 * n3 + n4];
            final float[] array4 = array2[0];
            while (true) {
                array4[0] = array3[0];
                int n6 = 0;
                ++n6;
            }
        }
        else {
            final float n7 = 1.0f - (n - n4);
            final float n8 = 1.0f - (n5 - n);
            final float[] array5 = array[n2 + 0 * n3 + n4];
            final float[] array6 = array[n2 + 0 * n3 + n5];
            final float[] array7 = array2[0];
            while (true) {
                array7[0] = array5[0] * n7 + array6[0] * n8;
                int n9 = 0;
                ++n9;
            }
        }
    }
    
    private static void readCustomPalettes(final Properties properties, final String s) {
        CustomColorizer.blockPalettes = new int[256][1];
        while (true) {
            CustomColorizer.blockPalettes[0][0] = -1;
            int n = 0;
            ++n;
        }
    }
    
    public static Vec3 getFogColor(final Vec3 vec3, final IBlockAccess blockAccess, final double n, final double n2, final double n3) {
        if (CustomColorizer.fogColors == null) {
            return vec3;
        }
        final int smoothColor = getSmoothColor(CustomColorizer.fogColors, blockAccess, n, n2, n3, 7, 1);
        return new Vec3((smoothColor >> 16 & 0xFF) / 255.0f * ((float)vec3.xCoord / 0.753f), (smoothColor >> 8 & 0xFF) / 255.0f * ((float)vec3.yCoord / 0.8471f), (smoothColor & 0xFF) / 255.0f * (float)vec3.zCoord);
    }
    
    public static void updateReddustFX(final EntityFX entityFX, final IBlockAccess blockAccess, final double n, final double n2, final double n3) {
        if (CustomColorizer.redstoneColors != null) {
            final int redstoneColor = getRedstoneColor(getRedstoneLevel(blockAccess.getBlockState(new BlockPos(n, n2, n3)), 15));
            if (redstoneColor != -1) {
                entityFX.setRBGColorF((redstoneColor >> 16 & 0xFF) / 255.0f, (redstoneColor >> 8 & 0xFF) / 255.0f, (redstoneColor & 0xFF) / 255.0f);
            }
        }
    }
    
    public static int getStemColorMultiplier(final Block block, final IBlockAccess blockAccess, final BlockPos blockPos, final RenderEnv renderEnv) {
        if (CustomColorizer.stemColors == null) {
            return block.colorMultiplier(blockAccess, blockPos);
        }
        renderEnv.getMetadata();
        if (0 >= CustomColorizer.stemColors.length) {
            final int n = CustomColorizer.stemColors.length - 1;
        }
        return CustomColorizer.stemColors[0];
    }
}
