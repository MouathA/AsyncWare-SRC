package optfine;

import net.minecraft.block.state.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.*;
import java.io.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.block.*;
import net.minecraft.world.biome.*;
import java.util.zip.*;
import java.util.*;

public class ConnectedTextures
{
    private static final int Z_POS_SOUTH = 3;
    private static final int Y_AXIS = 0;
    private static final int Y_NEG_DOWN = 0;
    private static boolean multipass;
    private static Map[] spriteQuadMaps;
    public static final IBlockState AIR_DEFAULT_STATE;
    private static TextureAtlasSprite emptySprite;
    private static ConnectedProperties[][] tileProperties;
    private static final int X_NEG_WEST = 4;
    private static final int Z_NEG_NORTH = 2;
    private static final int Z_AXIS = 1;
    private static final int X_POS_EAST = 5;
    private static final int X_AXIS = 2;
    private static final int Y_POS_UP = 1;
    private static ConnectedProperties[][] blockProperties;
    
    public static int getPaneTextureIndex(final boolean b, final boolean b2, final boolean b3, final boolean b4) {
        return (b2 && b) ? (b3 ? (b4 ? 34 : 50) : (b4 ? 18 : 2)) : ((b2 && !b) ? (b3 ? (b4 ? 35 : 51) : (b4 ? 19 : 3)) : ((!b2 && b) ? (b3 ? (b4 ? 33 : 49) : (b4 ? 17 : 1)) : (b3 ? (b4 ? 32 : 48) : (b4 ? 16 : 0))));
    }
    
    private static BakedQuad makeSpriteQuad(final BakedQuad bakedQuad, final TextureAtlasSprite textureAtlasSprite) {
        final int[] array = bakedQuad.getVertexData().clone();
        final TextureAtlasSprite sprite = bakedQuad.getSprite();
        while (true) {
            fixVertex(array, 0, sprite, textureAtlasSprite);
            int n = 0;
            ++n;
        }
    }
    
    static {
        ConnectedTextures.spriteQuadMaps = null;
        ConnectedTextures.blockProperties = null;
        ConnectedTextures.tileProperties = null;
        ConnectedTextures.multipass = false;
        ConnectedTextures.propSuffixes = new String[] { "", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
        ConnectedTextures.ctmIndexes = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 0, 0, 0, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 0, 0, 0, 0, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 0, 0, 0, 0, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 0, 0, 0, 0, 0 };
        AIR_DEFAULT_STATE = Blocks.air.getDefaultState();
        ConnectedTextures.emptySprite = null;
    }
    
    private static List makePropertyList(final ConnectedProperties[][] array) {
        final ArrayList<ArrayList> list = new ArrayList<ArrayList>();
        if (array != null) {
            while (0 < array.length) {
                final ConnectedProperties[] array2 = array[0];
                ArrayList list2 = null;
                if (array2 != null) {
                    list2 = new ArrayList(Arrays.asList(array2));
                }
                list.add(list2);
                int n = 0;
                ++n;
            }
        }
        return list;
    }
    
    private static int getWoodAxis(final int n, final int n2) {
        switch ((n2 & 0xC) >> 2) {
            case 1: {
                return 2;
            }
            case 2: {
                return 1;
            }
            default: {
                return 0;
            }
        }
    }
    
    public static TextureAtlasSprite getCtmTexture(final ConnectedProperties connectedProperties, final int n, final TextureAtlasSprite textureAtlasSprite) {
        if (connectedProperties.method != 1) {
            return textureAtlasSprite;
        }
        if (n >= 0 && n < ConnectedTextures.ctmIndexes.length) {
            final int n2 = ConnectedTextures.ctmIndexes[n];
            final TextureAtlasSprite[] tileIcons = connectedProperties.tileIcons;
            return (n2 >= 0 && n2 < tileIcons.length) ? tileIcons[n2] : textureAtlasSprite;
        }
        return textureAtlasSprite;
    }
    
    private static TextureAtlasSprite getConnectedTextureTop(final ConnectedProperties connectedProperties, final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final int n, final int n2, final TextureAtlasSprite textureAtlasSprite, final int n3) {
        switch (n) {
            case 0: {
                if (n2 == 1 || n2 == 0) {
                    return null;
                }
                isNeighbour(connectedProperties, blockAccess, blockState, blockPos.up(), n2, textureAtlasSprite, n3);
                break;
            }
            case 1: {
                if (n2 == 3 || n2 == 2) {
                    return null;
                }
                isNeighbour(connectedProperties, blockAccess, blockState, blockPos.south(), n2, textureAtlasSprite, n3);
                break;
            }
            case 2: {
                if (n2 == 5 || n2 == 4) {
                    return null;
                }
                isNeighbour(connectedProperties, blockAccess, blockState, blockPos.east(), n2, textureAtlasSprite, n3);
                break;
            }
        }
        return null;
    }
    
    public static void updateIcons(final TextureMap textureMap, final IResourcePack resourcePack) {
        final String[] collectFiles = collectFiles(resourcePack, "mcpatcher/ctm/", ".properties");
        Arrays.sort(collectFiles);
        final List propertyList = makePropertyList(ConnectedTextures.tileProperties);
        final List propertyList2 = makePropertyList(ConnectedTextures.blockProperties);
        while (0 < collectFiles.length) {
            final String s = collectFiles[0];
            Config.dbg("ConnectedTextures: " + s);
            final InputStream inputStream = resourcePack.getInputStream(new ResourceLocation(s));
            if (inputStream == null) {
                Config.warn("ConnectedTextures file not found: " + s);
            }
            else {
                final Properties properties = new Properties();
                properties.load(inputStream);
                final ConnectedProperties connectedProperties = new ConnectedProperties(properties, s);
                if (connectedProperties.isValid(s)) {
                    connectedProperties.updateIcons(textureMap);
                    addToTileList(connectedProperties, propertyList);
                    addToBlockList(connectedProperties, propertyList2);
                }
            }
            int n = 0;
            ++n;
        }
        ConnectedTextures.blockProperties = propertyListToArray(propertyList2);
        ConnectedTextures.tileProperties = propertyListToArray(propertyList);
        ConnectedTextures.multipass = detectMultipass();
        Config.dbg("Multipass connected textures: " + ConnectedTextures.multipass);
    }
    
    private static String[] collectFiles(final IResourcePack resourcePack, final String s, final String s2) {
        if (resourcePack instanceof DefaultResourcePack) {
            return collectFilesDefault(resourcePack);
        }
        if (!(resourcePack instanceof AbstractResourcePack)) {
            return new String[0];
        }
        final File resourcePackFile = ResourceUtils.getResourcePackFile((AbstractResourcePack)resourcePack);
        return (resourcePackFile == null) ? new String[0] : (resourcePackFile.isDirectory() ? collectFilesFolder(resourcePackFile, "", s, s2) : (resourcePackFile.isFile() ? collectFilesZIP(resourcePackFile, s, s2) : new String[0]));
    }
    
    private static void addToTileList(final ConnectedProperties connectedProperties, final List list) {
        if (connectedProperties.matchTileIcons != null) {
            while (0 < connectedProperties.matchTileIcons.length) {
                final TextureAtlasSprite textureAtlasSprite = connectedProperties.matchTileIcons[0];
                if (!(textureAtlasSprite instanceof TextureAtlasSprite)) {
                    Config.warn("TextureAtlasSprite is not TextureAtlasSprite: " + textureAtlasSprite + ", name: " + textureAtlasSprite.getIconName());
                }
                else {
                    final int indexInMap = textureAtlasSprite.getIndexInMap();
                    if (indexInMap < 0) {
                        Config.warn("Invalid tile ID: " + indexInMap + ", icon: " + textureAtlasSprite.getIconName());
                    }
                    else {
                        addToList(connectedProperties, list, indexInMap);
                    }
                }
                int n = 0;
                ++n;
            }
        }
    }
    
    private static void fixVertex(final int[] array, final int n, final TextureAtlasSprite textureAtlasSprite, final TextureAtlasSprite textureAtlasSprite2) {
        final int n2 = 7 * n;
        final float intBitsToFloat = Float.intBitsToFloat(array[n2 + 4]);
        final float intBitsToFloat2 = Float.intBitsToFloat(array[n2 + 4 + 1]);
        final double spriteU16 = textureAtlasSprite.getSpriteU16(intBitsToFloat);
        final double spriteV16 = textureAtlasSprite.getSpriteV16(intBitsToFloat2);
        array[n2 + 4] = Float.floatToRawIntBits(textureAtlasSprite2.getInterpolatedU(spriteU16));
        array[n2 + 4 + 1] = Float.floatToRawIntBits(textureAtlasSprite2.getInterpolatedV(spriteV16));
    }
    
    private static ConnectedProperties[][] propertyListToArray(final List list) {
        final ConnectedProperties[][] array = new ConnectedProperties[list.size()][];
        while (0 < list.size()) {
            final List list2 = list.get(0);
            if (list2 != null) {
                array[0] = (ConnectedProperties[])list2.toArray(new ConnectedProperties[list2.size()]);
            }
            int n = 0;
            ++n;
        }
        return array;
    }
    
    public static synchronized BakedQuad getConnectedTexture(final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final BakedQuad bakedQuad, final RenderEnv renderEnv) {
        final TextureAtlasSprite sprite = bakedQuad.getSprite();
        if (sprite == null) {
            return bakedQuad;
        }
        final Block block = blockState.getBlock();
        final EnumFacing face = bakedQuad.getFace();
        if (block instanceof BlockPane && sprite.getIconName().startsWith("minecraft:blocks/glass_pane_top") && blockAccess.getBlockState(blockPos.offset(bakedQuad.getFace())) == blockState) {
            return getQuad(ConnectedTextures.emptySprite, block, blockState, bakedQuad);
        }
        final TextureAtlasSprite connectedTextureMultiPass = getConnectedTextureMultiPass(blockAccess, blockState, blockPos, face, sprite, renderEnv);
        return (connectedTextureMultiPass == sprite) ? bakedQuad : getQuad(connectedTextureMultiPass, block, blockState, bakedQuad);
    }
    
    public static void updateIcons(final TextureMap textureMap) {
        ConnectedTextures.blockProperties = null;
        ConnectedTextures.tileProperties = null;
        if (Config.isConnectedTextures()) {
            final IResourcePack[] resourcePacks = Config.getResourcePacks();
            for (int i = resourcePacks.length - 1; i >= 0; --i) {
                updateIcons(textureMap, resourcePacks[i]);
            }
            updateIcons(textureMap, Config.getDefaultResourcePack());
            ConnectedTextures.emptySprite = textureMap.registerSprite(new ResourceLocation("mcpatcher/ctm/default/empty"));
            ConnectedTextures.spriteQuadMaps = new Map[textureMap.getCountRegisteredSprites() + 1];
        }
    }
    
    private static TextureAtlasSprite getNeighbourIcon(final IBlockAccess blockAccess, final BlockPos blockPos, IBlockState actualState, final int n) {
        actualState = actualState.getBlock().getActualState(actualState, blockAccess, blockPos);
        final IBakedModel modelForState = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(actualState);
        if (modelForState == null) {
            return null;
        }
        final EnumFacing facing = getFacing(n);
        final List faceQuads = modelForState.getFaceQuads(facing);
        if (faceQuads.size() > 0) {
            return faceQuads.get(0).getSprite();
        }
        final List generalQuads = modelForState.getGeneralQuads();
        while (0 < generalQuads.size()) {
            final BakedQuad bakedQuad = generalQuads.get(0);
            if (bakedQuad.getFace() == facing) {
                return bakedQuad.getSprite();
            }
            int n2 = 0;
            ++n2;
        }
        return null;
    }
    
    public static TextureAtlasSprite getConnectedTextureSingle(final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final EnumFacing enumFacing, final TextureAtlasSprite textureAtlasSprite, final boolean b, final RenderEnv renderEnv) {
        blockState.getBlock();
        int n = 0;
        if (ConnectedTextures.tileProperties != null) {
            final int indexInMap = textureAtlasSprite.getIndexInMap();
            if (indexInMap >= 0 && indexInMap < ConnectedTextures.tileProperties.length) {
                final ConnectedProperties[] array = ConnectedTextures.tileProperties[indexInMap];
                if (array != null) {
                    final int metadata = renderEnv.getMetadata();
                    final int side = getSide(enumFacing);
                    while (0 < array.length) {
                        final ConnectedProperties connectedProperties = array[0];
                        if (connectedProperties != null && connectedProperties.matchesBlock(renderEnv.getBlockId())) {
                            final TextureAtlasSprite connectedTexture = getConnectedTexture(connectedProperties, blockAccess, blockState, blockPos, side, textureAtlasSprite, metadata, renderEnv);
                            if (connectedTexture != null) {
                                return connectedTexture;
                            }
                        }
                        ++n;
                    }
                }
            }
        }
        if (ConnectedTextures.blockProperties != null && b) {
            final int blockId = renderEnv.getBlockId();
            if (blockId >= 0 && blockId < ConnectedTextures.blockProperties.length) {
                final ConnectedProperties[] array2 = ConnectedTextures.blockProperties[blockId];
                if (array2 != null) {
                    final int metadata2 = renderEnv.getMetadata();
                    final int side2 = getSide(enumFacing);
                    while (0 < array2.length) {
                        final ConnectedProperties connectedProperties2 = array2[0];
                        if (connectedProperties2 != null && connectedProperties2.matchesIcon(textureAtlasSprite)) {
                            final TextureAtlasSprite connectedTexture2 = getConnectedTexture(connectedProperties2, blockAccess, blockState, blockPos, side2, textureAtlasSprite, metadata2, renderEnv);
                            if (connectedTexture2 != null) {
                                return connectedTexture2;
                            }
                        }
                        ++n;
                    }
                }
            }
        }
        return textureAtlasSprite;
    }
    
    private static void addToBlockList(final ConnectedProperties connectedProperties, final List list) {
        if (connectedProperties.matchBlocks != null) {
            while (0 < connectedProperties.matchBlocks.length) {
                final int n = connectedProperties.matchBlocks[0];
                if (n < 0) {
                    Config.warn("Invalid block ID: " + n);
                }
                else {
                    addToList(connectedProperties, list, n);
                }
                int n2 = 0;
                ++n2;
            }
        }
    }
    
    private static TextureAtlasSprite getConnectedTextureCtm(final ConnectedProperties p0, final IBlockAccess p1, final IBlockState p2, final BlockPos p3, final int p4, final TextureAtlasSprite p5, final int p6, final RenderEnv p7) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     2: invokevirtual   optfine/RenderEnv.getBorderFlags:()[Z
        //     5: astore          8
        //     7: iload           4
        //     9: tableswitch {
        //                0: 48
        //                1: 131
        //                2: 214
        //                3: 297
        //                4: 380
        //                5: 463
        //          default: 543
        //        }
        //    48: aload           8
        //    50: iconst_0       
        //    51: aload_0        
        //    52: aload_1        
        //    53: aload_2        
        //    54: aload_3        
        //    55: invokevirtual   net/minecraft/util/BlockPos.west:()Lnet/minecraft/util/BlockPos;
        //    58: iload           4
        //    60: aload           5
        //    62: iload           6
        //    64: invokestatic    optfine/ConnectedTextures.isNeighbour:(Loptfine/ConnectedProperties;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockPos;ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;I)Z
        //    67: bastore        
        //    68: aload           8
        //    70: iconst_1       
        //    71: aload_0        
        //    72: aload_1        
        //    73: aload_2        
        //    74: aload_3        
        //    75: invokevirtual   net/minecraft/util/BlockPos.east:()Lnet/minecraft/util/BlockPos;
        //    78: iload           4
        //    80: aload           5
        //    82: iload           6
        //    84: invokestatic    optfine/ConnectedTextures.isNeighbour:(Loptfine/ConnectedProperties;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockPos;ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;I)Z
        //    87: bastore        
        //    88: aload           8
        //    90: iconst_2       
        //    91: aload_0        
        //    92: aload_1        
        //    93: aload_2        
        //    94: aload_3        
        //    95: invokevirtual   net/minecraft/util/BlockPos.north:()Lnet/minecraft/util/BlockPos;
        //    98: iload           4
        //   100: aload           5
        //   102: iload           6
        //   104: invokestatic    optfine/ConnectedTextures.isNeighbour:(Loptfine/ConnectedProperties;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockPos;ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;I)Z
        //   107: bastore        
        //   108: aload           8
        //   110: iconst_3       
        //   111: aload_0        
        //   112: aload_1        
        //   113: aload_2        
        //   114: aload_3        
        //   115: invokevirtual   net/minecraft/util/BlockPos.south:()Lnet/minecraft/util/BlockPos;
        //   118: iload           4
        //   120: aload           5
        //   122: iload           6
        //   124: invokestatic    optfine/ConnectedTextures.isNeighbour:(Loptfine/ConnectedProperties;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockPos;ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;I)Z
        //   127: bastore        
        //   128: goto            543
        //   131: aload           8
        //   133: iconst_0       
        //   134: aload_0        
        //   135: aload_1        
        //   136: aload_2        
        //   137: aload_3        
        //   138: invokevirtual   net/minecraft/util/BlockPos.west:()Lnet/minecraft/util/BlockPos;
        //   141: iload           4
        //   143: aload           5
        //   145: iload           6
        //   147: invokestatic    optfine/ConnectedTextures.isNeighbour:(Loptfine/ConnectedProperties;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockPos;ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;I)Z
        //   150: bastore        
        //   151: aload           8
        //   153: iconst_1       
        //   154: aload_0        
        //   155: aload_1        
        //   156: aload_2        
        //   157: aload_3        
        //   158: invokevirtual   net/minecraft/util/BlockPos.east:()Lnet/minecraft/util/BlockPos;
        //   161: iload           4
        //   163: aload           5
        //   165: iload           6
        //   167: invokestatic    optfine/ConnectedTextures.isNeighbour:(Loptfine/ConnectedProperties;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockPos;ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;I)Z
        //   170: bastore        
        //   171: aload           8
        //   173: iconst_2       
        //   174: aload_0        
        //   175: aload_1        
        //   176: aload_2        
        //   177: aload_3        
        //   178: invokevirtual   net/minecraft/util/BlockPos.south:()Lnet/minecraft/util/BlockPos;
        //   181: iload           4
        //   183: aload           5
        //   185: iload           6
        //   187: invokestatic    optfine/ConnectedTextures.isNeighbour:(Loptfine/ConnectedProperties;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockPos;ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;I)Z
        //   190: bastore        
        //   191: aload           8
        //   193: iconst_3       
        //   194: aload_0        
        //   195: aload_1        
        //   196: aload_2        
        //   197: aload_3        
        //   198: invokevirtual   net/minecraft/util/BlockPos.north:()Lnet/minecraft/util/BlockPos;
        //   201: iload           4
        //   203: aload           5
        //   205: iload           6
        //   207: invokestatic    optfine/ConnectedTextures.isNeighbour:(Loptfine/ConnectedProperties;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockPos;ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;I)Z
        //   210: bastore        
        //   211: goto            543
        //   214: aload           8
        //   216: iconst_0       
        //   217: aload_0        
        //   218: aload_1        
        //   219: aload_2        
        //   220: aload_3        
        //   221: invokevirtual   net/minecraft/util/BlockPos.east:()Lnet/minecraft/util/BlockPos;
        //   224: iload           4
        //   226: aload           5
        //   228: iload           6
        //   230: invokestatic    optfine/ConnectedTextures.isNeighbour:(Loptfine/ConnectedProperties;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockPos;ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;I)Z
        //   233: bastore        
        //   234: aload           8
        //   236: iconst_1       
        //   237: aload_0        
        //   238: aload_1        
        //   239: aload_2        
        //   240: aload_3        
        //   241: invokevirtual   net/minecraft/util/BlockPos.west:()Lnet/minecraft/util/BlockPos;
        //   244: iload           4
        //   246: aload           5
        //   248: iload           6
        //   250: invokestatic    optfine/ConnectedTextures.isNeighbour:(Loptfine/ConnectedProperties;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockPos;ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;I)Z
        //   253: bastore        
        //   254: aload           8
        //   256: iconst_2       
        //   257: aload_0        
        //   258: aload_1        
        //   259: aload_2        
        //   260: aload_3        
        //   261: invokevirtual   net/minecraft/util/BlockPos.down:()Lnet/minecraft/util/BlockPos;
        //   264: iload           4
        //   266: aload           5
        //   268: iload           6
        //   270: invokestatic    optfine/ConnectedTextures.isNeighbour:(Loptfine/ConnectedProperties;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockPos;ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;I)Z
        //   273: bastore        
        //   274: aload           8
        //   276: iconst_3       
        //   277: aload_0        
        //   278: aload_1        
        //   279: aload_2        
        //   280: aload_3        
        //   281: invokevirtual   net/minecraft/util/BlockPos.up:()Lnet/minecraft/util/BlockPos;
        //   284: iload           4
        //   286: aload           5
        //   288: iload           6
        //   290: invokestatic    optfine/ConnectedTextures.isNeighbour:(Loptfine/ConnectedProperties;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockPos;ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;I)Z
        //   293: bastore        
        //   294: goto            543
        //   297: aload           8
        //   299: iconst_0       
        //   300: aload_0        
        //   301: aload_1        
        //   302: aload_2        
        //   303: aload_3        
        //   304: invokevirtual   net/minecraft/util/BlockPos.west:()Lnet/minecraft/util/BlockPos;
        //   307: iload           4
        //   309: aload           5
        //   311: iload           6
        //   313: invokestatic    optfine/ConnectedTextures.isNeighbour:(Loptfine/ConnectedProperties;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockPos;ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;I)Z
        //   316: bastore        
        //   317: aload           8
        //   319: iconst_1       
        //   320: aload_0        
        //   321: aload_1        
        //   322: aload_2        
        //   323: aload_3        
        //   324: invokevirtual   net/minecraft/util/BlockPos.east:()Lnet/minecraft/util/BlockPos;
        //   327: iload           4
        //   329: aload           5
        //   331: iload           6
        //   333: invokestatic    optfine/ConnectedTextures.isNeighbour:(Loptfine/ConnectedProperties;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockPos;ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;I)Z
        //   336: bastore        
        //   337: aload           8
        //   339: iconst_2       
        //   340: aload_0        
        //   341: aload_1        
        //   342: aload_2        
        //   343: aload_3        
        //   344: invokevirtual   net/minecraft/util/BlockPos.down:()Lnet/minecraft/util/BlockPos;
        //   347: iload           4
        //   349: aload           5
        //   351: iload           6
        //   353: invokestatic    optfine/ConnectedTextures.isNeighbour:(Loptfine/ConnectedProperties;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockPos;ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;I)Z
        //   356: bastore        
        //   357: aload           8
        //   359: iconst_3       
        //   360: aload_0        
        //   361: aload_1        
        //   362: aload_2        
        //   363: aload_3        
        //   364: invokevirtual   net/minecraft/util/BlockPos.up:()Lnet/minecraft/util/BlockPos;
        //   367: iload           4
        //   369: aload           5
        //   371: iload           6
        //   373: invokestatic    optfine/ConnectedTextures.isNeighbour:(Loptfine/ConnectedProperties;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockPos;ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;I)Z
        //   376: bastore        
        //   377: goto            543
        //   380: aload           8
        //   382: iconst_0       
        //   383: aload_0        
        //   384: aload_1        
        //   385: aload_2        
        //   386: aload_3        
        //   387: invokevirtual   net/minecraft/util/BlockPos.north:()Lnet/minecraft/util/BlockPos;
        //   390: iload           4
        //   392: aload           5
        //   394: iload           6
        //   396: invokestatic    optfine/ConnectedTextures.isNeighbour:(Loptfine/ConnectedProperties;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockPos;ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;I)Z
        //   399: bastore        
        //   400: aload           8
        //   402: iconst_1       
        //   403: aload_0        
        //   404: aload_1        
        //   405: aload_2        
        //   406: aload_3        
        //   407: invokevirtual   net/minecraft/util/BlockPos.south:()Lnet/minecraft/util/BlockPos;
        //   410: iload           4
        //   412: aload           5
        //   414: iload           6
        //   416: invokestatic    optfine/ConnectedTextures.isNeighbour:(Loptfine/ConnectedProperties;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockPos;ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;I)Z
        //   419: bastore        
        //   420: aload           8
        //   422: iconst_2       
        //   423: aload_0        
        //   424: aload_1        
        //   425: aload_2        
        //   426: aload_3        
        //   427: invokevirtual   net/minecraft/util/BlockPos.down:()Lnet/minecraft/util/BlockPos;
        //   430: iload           4
        //   432: aload           5
        //   434: iload           6
        //   436: invokestatic    optfine/ConnectedTextures.isNeighbour:(Loptfine/ConnectedProperties;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockPos;ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;I)Z
        //   439: bastore        
        //   440: aload           8
        //   442: iconst_3       
        //   443: aload_0        
        //   444: aload_1        
        //   445: aload_2        
        //   446: aload_3        
        //   447: invokevirtual   net/minecraft/util/BlockPos.up:()Lnet/minecraft/util/BlockPos;
        //   450: iload           4
        //   452: aload           5
        //   454: iload           6
        //   456: invokestatic    optfine/ConnectedTextures.isNeighbour:(Loptfine/ConnectedProperties;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockPos;ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;I)Z
        //   459: bastore        
        //   460: goto            543
        //   463: aload           8
        //   465: iconst_0       
        //   466: aload_0        
        //   467: aload_1        
        //   468: aload_2        
        //   469: aload_3        
        //   470: invokevirtual   net/minecraft/util/BlockPos.south:()Lnet/minecraft/util/BlockPos;
        //   473: iload           4
        //   475: aload           5
        //   477: iload           6
        //   479: invokestatic    optfine/ConnectedTextures.isNeighbour:(Loptfine/ConnectedProperties;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockPos;ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;I)Z
        //   482: bastore        
        //   483: aload           8
        //   485: iconst_1       
        //   486: aload_0        
        //   487: aload_1        
        //   488: aload_2        
        //   489: aload_3        
        //   490: invokevirtual   net/minecraft/util/BlockPos.north:()Lnet/minecraft/util/BlockPos;
        //   493: iload           4
        //   495: aload           5
        //   497: iload           6
        //   499: invokestatic    optfine/ConnectedTextures.isNeighbour:(Loptfine/ConnectedProperties;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockPos;ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;I)Z
        //   502: bastore        
        //   503: aload           8
        //   505: iconst_2       
        //   506: aload_0        
        //   507: aload_1        
        //   508: aload_2        
        //   509: aload_3        
        //   510: invokevirtual   net/minecraft/util/BlockPos.down:()Lnet/minecraft/util/BlockPos;
        //   513: iload           4
        //   515: aload           5
        //   517: iload           6
        //   519: invokestatic    optfine/ConnectedTextures.isNeighbour:(Loptfine/ConnectedProperties;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockPos;ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;I)Z
        //   522: bastore        
        //   523: aload           8
        //   525: iconst_3       
        //   526: aload_0        
        //   527: aload_1        
        //   528: aload_2        
        //   529: aload_3        
        //   530: invokevirtual   net/minecraft/util/BlockPos.up:()Lnet/minecraft/util/BlockPos;
        //   533: iload           4
        //   535: aload           5
        //   537: iload           6
        //   539: invokestatic    optfine/ConnectedTextures.isNeighbour:(Loptfine/ConnectedProperties;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockPos;ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;I)Z
        //   542: bastore        
        //   543: aload           8
        //   545: iconst_0       
        //   546: baload         
        //   547: aload           8
        //   549: iconst_1       
        //   550: baload         
        //   551: ifne            558
        //   554: iconst_1       
        //   555: goto            559
        //   558: iconst_0       
        //   559: iand           
        //   560: aload           8
        //   562: iconst_2       
        //   563: baload         
        //   564: ifne            571
        //   567: iconst_1       
        //   568: goto            572
        //   571: iconst_0       
        //   572: iand           
        //   573: aload           8
        //   575: iconst_3       
        //   576: baload         
        //   577: ifne            584
        //   580: iconst_1       
        //   581: goto            585
        //   584: iconst_0       
        //   585: iand           
        //   586: ifeq            592
        //   589: goto            1150
        //   592: aload           8
        //   594: iconst_0       
        //   595: baload         
        //   596: ifne            603
        //   599: iconst_1       
        //   600: goto            604
        //   603: iconst_0       
        //   604: aload           8
        //   606: iconst_1       
        //   607: baload         
        //   608: iand           
        //   609: aload           8
        //   611: iconst_2       
        //   612: baload         
        //   613: ifne            620
        //   616: iconst_1       
        //   617: goto            621
        //   620: iconst_0       
        //   621: iand           
        //   622: aload           8
        //   624: iconst_3       
        //   625: baload         
        //   626: ifne            633
        //   629: iconst_1       
        //   630: goto            634
        //   633: iconst_0       
        //   634: iand           
        //   635: ifeq            641
        //   638: goto            1150
        //   641: aload           8
        //   643: iconst_0       
        //   644: baload         
        //   645: ifne            652
        //   648: iconst_1       
        //   649: goto            653
        //   652: iconst_0       
        //   653: aload           8
        //   655: iconst_1       
        //   656: baload         
        //   657: ifne            664
        //   660: iconst_1       
        //   661: goto            665
        //   664: iconst_0       
        //   665: iand           
        //   666: aload           8
        //   668: iconst_2       
        //   669: baload         
        //   670: iand           
        //   671: aload           8
        //   673: iconst_3       
        //   674: baload         
        //   675: ifne            682
        //   678: iconst_1       
        //   679: goto            683
        //   682: iconst_0       
        //   683: iand           
        //   684: ifeq            690
        //   687: goto            1150
        //   690: aload           8
        //   692: iconst_0       
        //   693: baload         
        //   694: ifne            701
        //   697: iconst_1       
        //   698: goto            702
        //   701: iconst_0       
        //   702: aload           8
        //   704: iconst_1       
        //   705: baload         
        //   706: ifne            713
        //   709: iconst_1       
        //   710: goto            714
        //   713: iconst_0       
        //   714: iand           
        //   715: aload           8
        //   717: iconst_2       
        //   718: baload         
        //   719: ifne            726
        //   722: iconst_1       
        //   723: goto            727
        //   726: iconst_0       
        //   727: iand           
        //   728: aload           8
        //   730: iconst_3       
        //   731: baload         
        //   732: iand           
        //   733: ifeq            739
        //   736: goto            1150
        //   739: aload           8
        //   741: iconst_0       
        //   742: baload         
        //   743: aload           8
        //   745: iconst_1       
        //   746: baload         
        //   747: iand           
        //   748: aload           8
        //   750: iconst_2       
        //   751: baload         
        //   752: ifne            759
        //   755: iconst_1       
        //   756: goto            760
        //   759: iconst_0       
        //   760: iand           
        //   761: aload           8
        //   763: iconst_3       
        //   764: baload         
        //   765: ifne            772
        //   768: iconst_1       
        //   769: goto            773
        //   772: iconst_0       
        //   773: iand           
        //   774: ifeq            780
        //   777: goto            1150
        //   780: aload           8
        //   782: iconst_0       
        //   783: baload         
        //   784: ifne            791
        //   787: iconst_1       
        //   788: goto            792
        //   791: iconst_0       
        //   792: aload           8
        //   794: iconst_1       
        //   795: baload         
        //   796: ifne            803
        //   799: iconst_1       
        //   800: goto            804
        //   803: iconst_0       
        //   804: iand           
        //   805: aload           8
        //   807: iconst_2       
        //   808: baload         
        //   809: iand           
        //   810: aload           8
        //   812: iconst_3       
        //   813: baload         
        //   814: iand           
        //   815: ifeq            821
        //   818: goto            1150
        //   821: aload           8
        //   823: iconst_0       
        //   824: baload         
        //   825: aload           8
        //   827: iconst_1       
        //   828: baload         
        //   829: ifne            836
        //   832: iconst_1       
        //   833: goto            837
        //   836: iconst_0       
        //   837: iand           
        //   838: aload           8
        //   840: iconst_2       
        //   841: baload         
        //   842: iand           
        //   843: aload           8
        //   845: iconst_3       
        //   846: baload         
        //   847: ifne            854
        //   850: iconst_1       
        //   851: goto            855
        //   854: iconst_0       
        //   855: iand           
        //   856: ifeq            862
        //   859: goto            1150
        //   862: aload           8
        //   864: iconst_0       
        //   865: baload         
        //   866: aload           8
        //   868: iconst_1       
        //   869: baload         
        //   870: ifne            877
        //   873: iconst_1       
        //   874: goto            878
        //   877: iconst_0       
        //   878: iand           
        //   879: aload           8
        //   881: iconst_2       
        //   882: baload         
        //   883: ifne            890
        //   886: iconst_1       
        //   887: goto            891
        //   890: iconst_0       
        //   891: iand           
        //   892: aload           8
        //   894: iconst_3       
        //   895: baload         
        //   896: iand           
        //   897: ifeq            903
        //   900: goto            1150
        //   903: aload           8
        //   905: iconst_0       
        //   906: baload         
        //   907: ifne            914
        //   910: iconst_1       
        //   911: goto            915
        //   914: iconst_0       
        //   915: aload           8
        //   917: iconst_1       
        //   918: baload         
        //   919: iand           
        //   920: aload           8
        //   922: iconst_2       
        //   923: baload         
        //   924: iand           
        //   925: aload           8
        //   927: iconst_3       
        //   928: baload         
        //   929: ifne            936
        //   932: iconst_1       
        //   933: goto            937
        //   936: iconst_0       
        //   937: iand           
        //   938: ifeq            944
        //   941: goto            1150
        //   944: aload           8
        //   946: iconst_0       
        //   947: baload         
        //   948: ifne            955
        //   951: iconst_1       
        //   952: goto            956
        //   955: iconst_0       
        //   956: aload           8
        //   958: iconst_1       
        //   959: baload         
        //   960: iand           
        //   961: aload           8
        //   963: iconst_2       
        //   964: baload         
        //   965: ifne            972
        //   968: iconst_1       
        //   969: goto            973
        //   972: iconst_0       
        //   973: iand           
        //   974: aload           8
        //   976: iconst_3       
        //   977: baload         
        //   978: iand           
        //   979: ifeq            985
        //   982: goto            1150
        //   985: aload           8
        //   987: iconst_0       
        //   988: baload         
        //   989: ifne            996
        //   992: iconst_1       
        //   993: goto            997
        //   996: iconst_0       
        //   997: aload           8
        //   999: iconst_1       
        //  1000: baload         
        //  1001: iand           
        //  1002: aload           8
        //  1004: iconst_2       
        //  1005: baload         
        //  1006: iand           
        //  1007: aload           8
        //  1009: iconst_3       
        //  1010: baload         
        //  1011: iand           
        //  1012: ifeq            1018
        //  1015: goto            1150
        //  1018: aload           8
        //  1020: iconst_0       
        //  1021: baload         
        //  1022: aload           8
        //  1024: iconst_1       
        //  1025: baload         
        //  1026: ifne            1033
        //  1029: iconst_1       
        //  1030: goto            1034
        //  1033: iconst_0       
        //  1034: iand           
        //  1035: aload           8
        //  1037: iconst_2       
        //  1038: baload         
        //  1039: iand           
        //  1040: aload           8
        //  1042: iconst_3       
        //  1043: baload         
        //  1044: iand           
        //  1045: ifeq            1051
        //  1048: goto            1150
        //  1051: aload           8
        //  1053: iconst_0       
        //  1054: baload         
        //  1055: aload           8
        //  1057: iconst_1       
        //  1058: baload         
        //  1059: iand           
        //  1060: aload           8
        //  1062: iconst_2       
        //  1063: baload         
        //  1064: ifne            1071
        //  1067: iconst_1       
        //  1068: goto            1072
        //  1071: iconst_0       
        //  1072: iand           
        //  1073: aload           8
        //  1075: iconst_3       
        //  1076: baload         
        //  1077: iand           
        //  1078: ifeq            1084
        //  1081: goto            1150
        //  1084: aload           8
        //  1086: iconst_0       
        //  1087: baload         
        //  1088: aload           8
        //  1090: iconst_1       
        //  1091: baload         
        //  1092: iand           
        //  1093: aload           8
        //  1095: iconst_2       
        //  1096: baload         
        //  1097: iand           
        //  1098: aload           8
        //  1100: iconst_3       
        //  1101: baload         
        //  1102: ifne            1109
        //  1105: iconst_1       
        //  1106: goto            1110
        //  1109: iconst_0       
        //  1110: iand           
        //  1111: ifeq            1117
        //  1114: goto            1150
        //  1117: aload           8
        //  1119: iconst_0       
        //  1120: baload         
        //  1121: aload           8
        //  1123: iconst_1       
        //  1124: baload         
        //  1125: iand           
        //  1126: aload           8
        //  1128: iconst_2       
        //  1129: baload         
        //  1130: iand           
        //  1131: aload           8
        //  1133: iconst_3       
        //  1134: baload         
        //  1135: iand           
        //  1136: ifeq            1139
        //  1139: goto            1150
        //  1142: aload_0        
        //  1143: getfield        optfine/ConnectedProperties.tileIcons:[Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;
        //  1146: bipush          45
        //  1148: aaload         
        //  1149: areturn        
        //  1150: invokestatic    optfine/Config.isConnectedTexturesFancy:()Z
        //  1153: ifne            1164
        //  1156: aload_0        
        //  1157: getfield        optfine/ConnectedProperties.tileIcons:[Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;
        //  1160: bipush          45
        //  1162: aaload         
        //  1163: areturn        
        //  1164: iload           4
        //  1166: tableswitch {
        //                0: 1204
        //                1: 1319
        //                2: 1434
        //                3: 1549
        //                4: 1664
        //                5: 1779
        //          default: 1891
        //        }
        //  1204: aload           8
        //  1206: iconst_0       
        //  1207: aload_0        
        //  1208: aload_1        
        //  1209: aload_2        
        //  1210: aload_3        
        //  1211: invokevirtual   net/minecraft/util/BlockPos.east:()Lnet/minecraft/util/BlockPos;
        //  1214: invokevirtual   net/minecraft/util/BlockPos.north:()Lnet/minecraft/util/BlockPos;
        //  1217: iload           4
        //  1219: aload           5
        //  1221: iload           6
        //  1223: if_acmpne       1230
        //  1226: iconst_1       
        //  1227: goto            1231
        //  1230: iconst_0       
        //  1231: bastore        
        //  1232: aload           8
        //  1234: iconst_1       
        //  1235: aload_0        
        //  1236: aload_1        
        //  1237: aload_2        
        //  1238: aload_3        
        //  1239: invokevirtual   net/minecraft/util/BlockPos.west:()Lnet/minecraft/util/BlockPos;
        //  1242: invokevirtual   net/minecraft/util/BlockPos.north:()Lnet/minecraft/util/BlockPos;
        //  1245: iload           4
        //  1247: aload           5
        //  1249: iload           6
        //  1251: if_acmpne       1258
        //  1254: iconst_1       
        //  1255: goto            1259
        //  1258: iconst_0       
        //  1259: bastore        
        //  1260: aload           8
        //  1262: iconst_2       
        //  1263: aload_0        
        //  1264: aload_1        
        //  1265: aload_2        
        //  1266: aload_3        
        //  1267: invokevirtual   net/minecraft/util/BlockPos.east:()Lnet/minecraft/util/BlockPos;
        //  1270: invokevirtual   net/minecraft/util/BlockPos.south:()Lnet/minecraft/util/BlockPos;
        //  1273: iload           4
        //  1275: aload           5
        //  1277: iload           6
        //  1279: if_acmpne       1286
        //  1282: iconst_1       
        //  1283: goto            1287
        //  1286: iconst_0       
        //  1287: bastore        
        //  1288: aload           8
        //  1290: iconst_3       
        //  1291: aload_0        
        //  1292: aload_1        
        //  1293: aload_2        
        //  1294: aload_3        
        //  1295: invokevirtual   net/minecraft/util/BlockPos.west:()Lnet/minecraft/util/BlockPos;
        //  1298: invokevirtual   net/minecraft/util/BlockPos.south:()Lnet/minecraft/util/BlockPos;
        //  1301: iload           4
        //  1303: aload           5
        //  1305: iload           6
        //  1307: if_acmpne       1314
        //  1310: iconst_1       
        //  1311: goto            1315
        //  1314: iconst_0       
        //  1315: bastore        
        //  1316: goto            1904
        //  1319: aload           8
        //  1321: iconst_0       
        //  1322: aload_0        
        //  1323: aload_1        
        //  1324: aload_2        
        //  1325: aload_3        
        //  1326: invokevirtual   net/minecraft/util/BlockPos.east:()Lnet/minecraft/util/BlockPos;
        //  1329: invokevirtual   net/minecraft/util/BlockPos.south:()Lnet/minecraft/util/BlockPos;
        //  1332: iload           4
        //  1334: aload           5
        //  1336: iload           6
        //  1338: if_acmpne       1345
        //  1341: iconst_1       
        //  1342: goto            1346
        //  1345: iconst_0       
        //  1346: bastore        
        //  1347: aload           8
        //  1349: iconst_1       
        //  1350: aload_0        
        //  1351: aload_1        
        //  1352: aload_2        
        //  1353: aload_3        
        //  1354: invokevirtual   net/minecraft/util/BlockPos.west:()Lnet/minecraft/util/BlockPos;
        //  1357: invokevirtual   net/minecraft/util/BlockPos.south:()Lnet/minecraft/util/BlockPos;
        //  1360: iload           4
        //  1362: aload           5
        //  1364: iload           6
        //  1366: if_acmpne       1373
        //  1369: iconst_1       
        //  1370: goto            1374
        //  1373: iconst_0       
        //  1374: bastore        
        //  1375: aload           8
        //  1377: iconst_2       
        //  1378: aload_0        
        //  1379: aload_1        
        //  1380: aload_2        
        //  1381: aload_3        
        //  1382: invokevirtual   net/minecraft/util/BlockPos.east:()Lnet/minecraft/util/BlockPos;
        //  1385: invokevirtual   net/minecraft/util/BlockPos.north:()Lnet/minecraft/util/BlockPos;
        //  1388: iload           4
        //  1390: aload           5
        //  1392: iload           6
        //  1394: if_acmpne       1401
        //  1397: iconst_1       
        //  1398: goto            1402
        //  1401: iconst_0       
        //  1402: bastore        
        //  1403: aload           8
        //  1405: iconst_3       
        //  1406: aload_0        
        //  1407: aload_1        
        //  1408: aload_2        
        //  1409: aload_3        
        //  1410: invokevirtual   net/minecraft/util/BlockPos.west:()Lnet/minecraft/util/BlockPos;
        //  1413: invokevirtual   net/minecraft/util/BlockPos.north:()Lnet/minecraft/util/BlockPos;
        //  1416: iload           4
        //  1418: aload           5
        //  1420: iload           6
        //  1422: if_acmpne       1429
        //  1425: iconst_1       
        //  1426: goto            1430
        //  1429: iconst_0       
        //  1430: bastore        
        //  1431: goto            1904
        //  1434: aload           8
        //  1436: iconst_0       
        //  1437: aload_0        
        //  1438: aload_1        
        //  1439: aload_2        
        //  1440: aload_3        
        //  1441: invokevirtual   net/minecraft/util/BlockPos.west:()Lnet/minecraft/util/BlockPos;
        //  1444: invokevirtual   net/minecraft/util/BlockPos.down:()Lnet/minecraft/util/BlockPos;
        //  1447: iload           4
        //  1449: aload           5
        //  1451: iload           6
        //  1453: if_acmpne       1460
        //  1456: iconst_1       
        //  1457: goto            1461
        //  1460: iconst_0       
        //  1461: bastore        
        //  1462: aload           8
        //  1464: iconst_1       
        //  1465: aload_0        
        //  1466: aload_1        
        //  1467: aload_2        
        //  1468: aload_3        
        //  1469: invokevirtual   net/minecraft/util/BlockPos.east:()Lnet/minecraft/util/BlockPos;
        //  1472: invokevirtual   net/minecraft/util/BlockPos.down:()Lnet/minecraft/util/BlockPos;
        //  1475: iload           4
        //  1477: aload           5
        //  1479: iload           6
        //  1481: if_acmpne       1488
        //  1484: iconst_1       
        //  1485: goto            1489
        //  1488: iconst_0       
        //  1489: bastore        
        //  1490: aload           8
        //  1492: iconst_2       
        //  1493: aload_0        
        //  1494: aload_1        
        //  1495: aload_2        
        //  1496: aload_3        
        //  1497: invokevirtual   net/minecraft/util/BlockPos.west:()Lnet/minecraft/util/BlockPos;
        //  1500: invokevirtual   net/minecraft/util/BlockPos.up:()Lnet/minecraft/util/BlockPos;
        //  1503: iload           4
        //  1505: aload           5
        //  1507: iload           6
        //  1509: if_acmpne       1516
        //  1512: iconst_1       
        //  1513: goto            1517
        //  1516: iconst_0       
        //  1517: bastore        
        //  1518: aload           8
        //  1520: iconst_3       
        //  1521: aload_0        
        //  1522: aload_1        
        //  1523: aload_2        
        //  1524: aload_3        
        //  1525: invokevirtual   net/minecraft/util/BlockPos.east:()Lnet/minecraft/util/BlockPos;
        //  1528: invokevirtual   net/minecraft/util/BlockPos.up:()Lnet/minecraft/util/BlockPos;
        //  1531: iload           4
        //  1533: aload           5
        //  1535: iload           6
        //  1537: if_acmpne       1544
        //  1540: iconst_1       
        //  1541: goto            1545
        //  1544: iconst_0       
        //  1545: bastore        
        //  1546: goto            1904
        //  1549: aload           8
        //  1551: iconst_0       
        //  1552: aload_0        
        //  1553: aload_1        
        //  1554: aload_2        
        //  1555: aload_3        
        //  1556: invokevirtual   net/minecraft/util/BlockPos.east:()Lnet/minecraft/util/BlockPos;
        //  1559: invokevirtual   net/minecraft/util/BlockPos.down:()Lnet/minecraft/util/BlockPos;
        //  1562: iload           4
        //  1564: aload           5
        //  1566: iload           6
        //  1568: if_acmpne       1575
        //  1571: iconst_1       
        //  1572: goto            1576
        //  1575: iconst_0       
        //  1576: bastore        
        //  1577: aload           8
        //  1579: iconst_1       
        //  1580: aload_0        
        //  1581: aload_1        
        //  1582: aload_2        
        //  1583: aload_3        
        //  1584: invokevirtual   net/minecraft/util/BlockPos.west:()Lnet/minecraft/util/BlockPos;
        //  1587: invokevirtual   net/minecraft/util/BlockPos.down:()Lnet/minecraft/util/BlockPos;
        //  1590: iload           4
        //  1592: aload           5
        //  1594: iload           6
        //  1596: if_acmpne       1603
        //  1599: iconst_1       
        //  1600: goto            1604
        //  1603: iconst_0       
        //  1604: bastore        
        //  1605: aload           8
        //  1607: iconst_2       
        //  1608: aload_0        
        //  1609: aload_1        
        //  1610: aload_2        
        //  1611: aload_3        
        //  1612: invokevirtual   net/minecraft/util/BlockPos.east:()Lnet/minecraft/util/BlockPos;
        //  1615: invokevirtual   net/minecraft/util/BlockPos.up:()Lnet/minecraft/util/BlockPos;
        //  1618: iload           4
        //  1620: aload           5
        //  1622: iload           6
        //  1624: if_acmpne       1631
        //  1627: iconst_1       
        //  1628: goto            1632
        //  1631: iconst_0       
        //  1632: bastore        
        //  1633: aload           8
        //  1635: iconst_3       
        //  1636: aload_0        
        //  1637: aload_1        
        //  1638: aload_2        
        //  1639: aload_3        
        //  1640: invokevirtual   net/minecraft/util/BlockPos.west:()Lnet/minecraft/util/BlockPos;
        //  1643: invokevirtual   net/minecraft/util/BlockPos.up:()Lnet/minecraft/util/BlockPos;
        //  1646: iload           4
        //  1648: aload           5
        //  1650: iload           6
        //  1652: if_acmpne       1659
        //  1655: iconst_1       
        //  1656: goto            1660
        //  1659: iconst_0       
        //  1660: bastore        
        //  1661: goto            1904
        //  1664: aload           8
        //  1666: iconst_0       
        //  1667: aload_0        
        //  1668: aload_1        
        //  1669: aload_2        
        //  1670: aload_3        
        //  1671: invokevirtual   net/minecraft/util/BlockPos.down:()Lnet/minecraft/util/BlockPos;
        //  1674: invokevirtual   net/minecraft/util/BlockPos.south:()Lnet/minecraft/util/BlockPos;
        //  1677: iload           4
        //  1679: aload           5
        //  1681: iload           6
        //  1683: if_acmpne       1690
        //  1686: iconst_1       
        //  1687: goto            1691
        //  1690: iconst_0       
        //  1691: bastore        
        //  1692: aload           8
        //  1694: iconst_1       
        //  1695: aload_0        
        //  1696: aload_1        
        //  1697: aload_2        
        //  1698: aload_3        
        //  1699: invokevirtual   net/minecraft/util/BlockPos.down:()Lnet/minecraft/util/BlockPos;
        //  1702: invokevirtual   net/minecraft/util/BlockPos.north:()Lnet/minecraft/util/BlockPos;
        //  1705: iload           4
        //  1707: aload           5
        //  1709: iload           6
        //  1711: if_acmpne       1718
        //  1714: iconst_1       
        //  1715: goto            1719
        //  1718: iconst_0       
        //  1719: bastore        
        //  1720: aload           8
        //  1722: iconst_2       
        //  1723: aload_0        
        //  1724: aload_1        
        //  1725: aload_2        
        //  1726: aload_3        
        //  1727: invokevirtual   net/minecraft/util/BlockPos.up:()Lnet/minecraft/util/BlockPos;
        //  1730: invokevirtual   net/minecraft/util/BlockPos.south:()Lnet/minecraft/util/BlockPos;
        //  1733: iload           4
        //  1735: aload           5
        //  1737: iload           6
        //  1739: if_acmpne       1746
        //  1742: iconst_1       
        //  1743: goto            1747
        //  1746: iconst_0       
        //  1747: bastore        
        //  1748: aload           8
        //  1750: iconst_3       
        //  1751: aload_0        
        //  1752: aload_1        
        //  1753: aload_2        
        //  1754: aload_3        
        //  1755: invokevirtual   net/minecraft/util/BlockPos.up:()Lnet/minecraft/util/BlockPos;
        //  1758: invokevirtual   net/minecraft/util/BlockPos.north:()Lnet/minecraft/util/BlockPos;
        //  1761: iload           4
        //  1763: aload           5
        //  1765: iload           6
        //  1767: if_acmpne       1774
        //  1770: iconst_1       
        //  1771: goto            1775
        //  1774: iconst_0       
        //  1775: bastore        
        //  1776: goto            1904
        //  1779: aload           8
        //  1781: iconst_0       
        //  1782: aload_0        
        //  1783: aload_1        
        //  1784: aload_2        
        //  1785: aload_3        
        //  1786: invokevirtual   net/minecraft/util/BlockPos.down:()Lnet/minecraft/util/BlockPos;
        //  1789: invokevirtual   net/minecraft/util/BlockPos.north:()Lnet/minecraft/util/BlockPos;
        //  1792: iload           4
        //  1794: aload           5
        //  1796: iload           6
        //  1798: if_acmpne       1805
        //  1801: iconst_1       
        //  1802: goto            1806
        //  1805: iconst_0       
        //  1806: bastore        
        //  1807: aload           8
        //  1809: iconst_1       
        //  1810: aload_0        
        //  1811: aload_1        
        //  1812: aload_2        
        //  1813: aload_3        
        //  1814: invokevirtual   net/minecraft/util/BlockPos.down:()Lnet/minecraft/util/BlockPos;
        //  1817: invokevirtual   net/minecraft/util/BlockPos.south:()Lnet/minecraft/util/BlockPos;
        //  1820: iload           4
        //  1822: aload           5
        //  1824: iload           6
        //  1826: if_acmpne       1833
        //  1829: iconst_1       
        //  1830: goto            1834
        //  1833: iconst_0       
        //  1834: bastore        
        //  1835: aload           8
        //  1837: iconst_2       
        //  1838: aload_0        
        //  1839: aload_1        
        //  1840: aload_2        
        //  1841: aload_3        
        //  1842: invokevirtual   net/minecraft/util/BlockPos.up:()Lnet/minecraft/util/BlockPos;
        //  1845: invokevirtual   net/minecraft/util/BlockPos.north:()Lnet/minecraft/util/BlockPos;
        //  1848: iload           4
        //  1850: aload           5
        //  1852: iload           6
        //  1854: if_acmpne       1861
        //  1857: iconst_1       
        //  1858: goto            1862
        //  1861: iconst_0       
        //  1862: bastore        
        //  1863: aload           8
        //  1865: iconst_3       
        //  1866: aload_0        
        //  1867: aload_1        
        //  1868: aload_2        
        //  1869: aload_3        
        //  1870: invokevirtual   net/minecraft/util/BlockPos.up:()Lnet/minecraft/util/BlockPos;
        //  1873: invokevirtual   net/minecraft/util/BlockPos.south:()Lnet/minecraft/util/BlockPos;
        //  1876: iload           4
        //  1878: aload           5
        //  1880: iload           6
        //  1882: if_acmpne       1889
        //  1885: iconst_1       
        //  1886: goto            1890
        //  1889: iconst_0       
        //  1890: bastore        
        //  1891: goto            1917
        //  1894: aload           8
        //  1896: iconst_0       
        //  1897: baload         
        //  1898: ifeq            1904
        //  1901: goto            2690
        //  1904: goto            1930
        //  1907: aload           8
        //  1909: iconst_1       
        //  1910: baload         
        //  1911: ifeq            1917
        //  1914: goto            2690
        //  1917: goto            1943
        //  1920: aload           8
        //  1922: iconst_2       
        //  1923: baload         
        //  1924: ifeq            1930
        //  1927: goto            2690
        //  1930: goto            1963
        //  1933: aload           8
        //  1935: iconst_3       
        //  1936: baload         
        //  1937: ifeq            1943
        //  1940: goto            2690
        //  1943: goto            1983
        //  1946: aload           8
        //  1948: iconst_0       
        //  1949: baload         
        //  1950: ifeq            1963
        //  1953: aload           8
        //  1955: iconst_1       
        //  1956: baload         
        //  1957: ifeq            1963
        //  1960: goto            2690
        //  1963: goto            2003
        //  1966: aload           8
        //  1968: iconst_0       
        //  1969: baload         
        //  1970: ifeq            1983
        //  1973: aload           8
        //  1975: iconst_2       
        //  1976: baload         
        //  1977: ifeq            1983
        //  1980: goto            2690
        //  1983: goto            2023
        //  1986: aload           8
        //  1988: iconst_3       
        //  1989: baload         
        //  1990: ifeq            2003
        //  1993: aload           8
        //  1995: iconst_1       
        //  1996: baload         
        //  1997: ifeq            2003
        //  2000: goto            2690
        //  2003: goto            2043
        //  2006: aload           8
        //  2008: iconst_3       
        //  2009: baload         
        //  2010: ifeq            2023
        //  2013: aload           8
        //  2015: iconst_2       
        //  2016: baload         
        //  2017: ifeq            2023
        //  2020: goto            2690
        //  2023: goto            2063
        //  2026: aload           8
        //  2028: iconst_0       
        //  2029: baload         
        //  2030: ifne            2043
        //  2033: aload           8
        //  2035: iconst_1       
        //  2036: baload         
        //  2037: ifeq            2043
        //  2040: goto            2690
        //  2043: goto            2083
        //  2046: aload           8
        //  2048: iconst_0       
        //  2049: baload         
        //  2050: ifeq            2063
        //  2053: aload           8
        //  2055: iconst_2       
        //  2056: baload         
        //  2057: ifne            2063
        //  2060: goto            2690
        //  2063: goto            2103
        //  2066: aload           8
        //  2068: iconst_3       
        //  2069: baload         
        //  2070: ifne            2083
        //  2073: aload           8
        //  2075: iconst_1       
        //  2076: baload         
        //  2077: ifeq            2083
        //  2080: goto            2690
        //  2083: goto            2123
        //  2086: aload           8
        //  2088: iconst_3       
        //  2089: baload         
        //  2090: ifeq            2103
        //  2093: aload           8
        //  2095: iconst_2       
        //  2096: baload         
        //  2097: ifne            2103
        //  2100: goto            2690
        //  2103: goto            2143
        //  2106: aload           8
        //  2108: iconst_0       
        //  2109: baload         
        //  2110: ifeq            2123
        //  2113: aload           8
        //  2115: iconst_1       
        //  2116: baload         
        //  2117: ifne            2123
        //  2120: goto            2690
        //  2123: goto            2163
        //  2126: aload           8
        //  2128: iconst_0       
        //  2129: baload         
        //  2130: ifne            2143
        //  2133: aload           8
        //  2135: iconst_2       
        //  2136: baload         
        //  2137: ifeq            2143
        //  2140: goto            2690
        //  2143: goto            2183
        //  2146: aload           8
        //  2148: iconst_3       
        //  2149: baload         
        //  2150: ifeq            2163
        //  2153: aload           8
        //  2155: iconst_1       
        //  2156: baload         
        //  2157: ifne            2163
        //  2160: goto            2690
        //  2163: goto            2217
        //  2166: aload           8
        //  2168: iconst_3       
        //  2169: baload         
        //  2170: ifne            2183
        //  2173: aload           8
        //  2175: iconst_2       
        //  2176: baload         
        //  2177: ifeq            2183
        //  2180: goto            2690
        //  2183: goto            2251
        //  2186: aload           8
        //  2188: iconst_0       
        //  2189: baload         
        //  2190: ifeq            2217
        //  2193: aload           8
        //  2195: iconst_1       
        //  2196: baload         
        //  2197: ifeq            2217
        //  2200: aload           8
        //  2202: iconst_2       
        //  2203: baload         
        //  2204: ifeq            2217
        //  2207: aload           8
        //  2209: iconst_3       
        //  2210: baload         
        //  2211: ifeq            2217
        //  2214: goto            2690
        //  2217: goto            2285
        //  2220: aload           8
        //  2222: iconst_0       
        //  2223: baload         
        //  2224: ifne            2251
        //  2227: aload           8
        //  2229: iconst_1       
        //  2230: baload         
        //  2231: ifeq            2251
        //  2234: aload           8
        //  2236: iconst_2       
        //  2237: baload         
        //  2238: ifeq            2251
        //  2241: aload           8
        //  2243: iconst_3       
        //  2244: baload         
        //  2245: ifeq            2251
        //  2248: goto            2690
        //  2251: goto            2319
        //  2254: aload           8
        //  2256: iconst_0       
        //  2257: baload         
        //  2258: ifeq            2285
        //  2261: aload           8
        //  2263: iconst_1       
        //  2264: baload         
        //  2265: ifne            2285
        //  2268: aload           8
        //  2270: iconst_2       
        //  2271: baload         
        //  2272: ifeq            2285
        //  2275: aload           8
        //  2277: iconst_3       
        //  2278: baload         
        //  2279: ifeq            2285
        //  2282: goto            2690
        //  2285: goto            2353
        //  2288: aload           8
        //  2290: iconst_0       
        //  2291: baload         
        //  2292: ifeq            2319
        //  2295: aload           8
        //  2297: iconst_1       
        //  2298: baload         
        //  2299: ifeq            2319
        //  2302: aload           8
        //  2304: iconst_2       
        //  2305: baload         
        //  2306: ifne            2319
        //  2309: aload           8
        //  2311: iconst_3       
        //  2312: baload         
        //  2313: ifeq            2319
        //  2316: goto            2690
        //  2319: goto            2387
        //  2322: aload           8
        //  2324: iconst_0       
        //  2325: baload         
        //  2326: ifeq            2353
        //  2329: aload           8
        //  2331: iconst_1       
        //  2332: baload         
        //  2333: ifeq            2353
        //  2336: aload           8
        //  2338: iconst_2       
        //  2339: baload         
        //  2340: ifeq            2353
        //  2343: aload           8
        //  2345: iconst_3       
        //  2346: baload         
        //  2347: ifne            2353
        //  2350: goto            2690
        //  2353: goto            2421
        //  2356: aload           8
        //  2358: iconst_0       
        //  2359: baload         
        //  2360: ifeq            2387
        //  2363: aload           8
        //  2365: iconst_1       
        //  2366: baload         
        //  2367: ifeq            2387
        //  2370: aload           8
        //  2372: iconst_2       
        //  2373: baload         
        //  2374: ifne            2387
        //  2377: aload           8
        //  2379: iconst_3       
        //  2380: baload         
        //  2381: ifne            2387
        //  2384: goto            2690
        //  2387: goto            2455
        //  2390: aload           8
        //  2392: iconst_0       
        //  2393: baload         
        //  2394: ifne            2421
        //  2397: aload           8
        //  2399: iconst_1       
        //  2400: baload         
        //  2401: ifne            2421
        //  2404: aload           8
        //  2406: iconst_2       
        //  2407: baload         
        //  2408: ifeq            2421
        //  2411: aload           8
        //  2413: iconst_3       
        //  2414: baload         
        //  2415: ifeq            2421
        //  2418: goto            2690
        //  2421: goto            2489
        //  2424: aload           8
        //  2426: iconst_0       
        //  2427: baload         
        //  2428: ifne            2455
        //  2431: aload           8
        //  2433: iconst_1       
        //  2434: baload         
        //  2435: ifeq            2455
        //  2438: aload           8
        //  2440: iconst_2       
        //  2441: baload         
        //  2442: ifne            2455
        //  2445: aload           8
        //  2447: iconst_3       
        //  2448: baload         
        //  2449: ifeq            2455
        //  2452: goto            2690
        //  2455: goto            2523
        //  2458: aload           8
        //  2460: iconst_0       
        //  2461: baload         
        //  2462: ifeq            2489
        //  2465: aload           8
        //  2467: iconst_1       
        //  2468: baload         
        //  2469: ifne            2489
        //  2472: aload           8
        //  2474: iconst_2       
        //  2475: baload         
        //  2476: ifeq            2489
        //  2479: aload           8
        //  2481: iconst_3       
        //  2482: baload         
        //  2483: ifne            2489
        //  2486: goto            2690
        //  2489: goto            2557
        //  2492: aload           8
        //  2494: iconst_0       
        //  2495: baload         
        //  2496: ifeq            2523
        //  2499: aload           8
        //  2501: iconst_1       
        //  2502: baload         
        //  2503: ifne            2523
        //  2506: aload           8
        //  2508: iconst_2       
        //  2509: baload         
        //  2510: ifne            2523
        //  2513: aload           8
        //  2515: iconst_3       
        //  2516: baload         
        //  2517: ifeq            2523
        //  2520: goto            2690
        //  2523: goto            2591
        //  2526: aload           8
        //  2528: iconst_0       
        //  2529: baload         
        //  2530: ifne            2557
        //  2533: aload           8
        //  2535: iconst_1       
        //  2536: baload         
        //  2537: ifeq            2557
        //  2540: aload           8
        //  2542: iconst_2       
        //  2543: baload         
        //  2544: ifeq            2557
        //  2547: aload           8
        //  2549: iconst_3       
        //  2550: baload         
        //  2551: ifne            2557
        //  2554: goto            2690
        //  2557: goto            2625
        //  2560: aload           8
        //  2562: iconst_0       
        //  2563: baload         
        //  2564: ifeq            2591
        //  2567: aload           8
        //  2569: iconst_1       
        //  2570: baload         
        //  2571: ifne            2591
        //  2574: aload           8
        //  2576: iconst_2       
        //  2577: baload         
        //  2578: ifne            2591
        //  2581: aload           8
        //  2583: iconst_3       
        //  2584: baload         
        //  2585: ifne            2591
        //  2588: goto            2690
        //  2591: goto            2659
        //  2594: aload           8
        //  2596: iconst_0       
        //  2597: baload         
        //  2598: ifne            2625
        //  2601: aload           8
        //  2603: iconst_1       
        //  2604: baload         
        //  2605: ifeq            2625
        //  2608: aload           8
        //  2610: iconst_2       
        //  2611: baload         
        //  2612: ifne            2625
        //  2615: aload           8
        //  2617: iconst_3       
        //  2618: baload         
        //  2619: ifne            2625
        //  2622: goto            2690
        //  2625: goto            2690
        //  2628: aload           8
        //  2630: iconst_0       
        //  2631: baload         
        //  2632: ifne            2659
        //  2635: aload           8
        //  2637: iconst_1       
        //  2638: baload         
        //  2639: ifne            2659
        //  2642: aload           8
        //  2644: iconst_2       
        //  2645: baload         
        //  2646: ifeq            2659
        //  2649: aload           8
        //  2651: iconst_3       
        //  2652: baload         
        //  2653: ifne            2659
        //  2656: goto            2690
        //  2659: goto            2690
        //  2662: aload           8
        //  2664: iconst_0       
        //  2665: baload         
        //  2666: ifne            2690
        //  2669: aload           8
        //  2671: iconst_1       
        //  2672: baload         
        //  2673: ifne            2690
        //  2676: aload           8
        //  2678: iconst_2       
        //  2679: baload         
        //  2680: ifne            2690
        //  2683: aload           8
        //  2685: iconst_3       
        //  2686: baload         
        //  2687: ifeq            2690
        //  2690: aload_0        
        //  2691: getfield        optfine/ConnectedProperties.tileIcons:[Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;
        //  2694: bipush          45
        //  2696: aaload         
        //  2697: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #1891 (coming from #1890).
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
    
    private static void addToList(final ConnectedProperties connectedProperties, final List list, final int i) {
        while (i >= list.size()) {
            list.add(null);
        }
        List<ConnectedProperties> list2 = list.get(i);
        if (list2 == null) {
            list2 = new ArrayList<ConnectedProperties>();
            list.set(i, list2);
        }
        list2.add(connectedProperties);
    }
    
    private static TextureAtlasSprite getConnectedTextureFixed(final ConnectedProperties connectedProperties) {
        return connectedProperties.tileIcons[0];
    }
    
    private static boolean detectMultipass() {
        final ArrayList list = new ArrayList();
        int n = 0;
        while (0 < ConnectedTextures.tileProperties.length) {
            final ConnectedProperties[] array = ConnectedTextures.tileProperties[0];
            if (array != null) {
                list.addAll(Arrays.asList(array));
            }
            ++n;
        }
        while (0 < ConnectedTextures.blockProperties.length) {
            final ConnectedProperties[] array2 = ConnectedTextures.blockProperties[0];
            if (array2 != null) {
                list.addAll(Arrays.asList(array2));
            }
            ++n;
        }
        final ConnectedProperties[] array3 = (ConnectedProperties[])list.toArray(new ConnectedProperties[list.size()]);
        final HashSet set = new HashSet();
        final HashSet set2 = new HashSet();
        while (0 < array3.length) {
            final ConnectedProperties connectedProperties = array3[0];
            if (connectedProperties.matchTileIcons != null) {
                set.addAll(Arrays.asList(connectedProperties.matchTileIcons));
            }
            if (connectedProperties.tileIcons != null) {
                set2.addAll(Arrays.asList(connectedProperties.tileIcons));
            }
            int n2 = 0;
            ++n2;
        }
        set.retainAll(set2);
        return !set.isEmpty();
    }
    
    private static TextureAtlasSprite getConnectedTextureVertical(final ConnectedProperties connectedProperties, final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final int n, final int n2, final TextureAtlasSprite textureAtlasSprite, final int n3) {
        switch (n) {
            case 0: {
                if (n2 == 1 || n2 == 0) {
                    return null;
                }
                isNeighbour(connectedProperties, blockAccess, blockState, blockPos.down(), n2, textureAtlasSprite, n3);
                isNeighbour(connectedProperties, blockAccess, blockState, blockPos.up(), n2, textureAtlasSprite, n3);
                break;
            }
            case 1: {
                if (n2 == 3 || n2 == 2) {
                    return null;
                }
                isNeighbour(connectedProperties, blockAccess, blockState, blockPos.south(), n2, textureAtlasSprite, n3);
                isNeighbour(connectedProperties, blockAccess, blockState, blockPos.north(), n2, textureAtlasSprite, n3);
                break;
            }
            case 2: {
                if (n2 == 5 || n2 == 4) {
                    return null;
                }
                isNeighbour(connectedProperties, blockAccess, blockState, blockPos.west(), n2, textureAtlasSprite, n3);
                isNeighbour(connectedProperties, blockAccess, blockState, blockPos.east(), n2, textureAtlasSprite, n3);
                break;
            }
        }
        return connectedProperties.tileIcons[3];
    }
    
    private static TextureAtlasSprite getConnectedTexture(final ConnectedProperties connectedProperties, final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final int n, final TextureAtlasSprite textureAtlasSprite, final int n2, final RenderEnv renderEnv) {
        final int y = blockPos.getY();
        if (y < connectedProperties.minHeight || y > connectedProperties.maxHeight) {
            return null;
        }
        if (connectedProperties.biomes != null) {
            final BiomeGenBase biomeGenForCoords = blockAccess.getBiomeGenForCoords(blockPos);
            while (0 < connectedProperties.biomes.length) {
                if (biomeGenForCoords == connectedProperties.biomes[0]) {
                    break;
                }
                int n3 = 0;
                ++n3;
            }
        }
        final Block block = blockState.getBlock();
        if (block instanceof BlockRotatedPillar) {
            getWoodAxis(n, n2);
        }
        if (block instanceof BlockQuartz) {
            getQuartzAxis(n, n2);
        }
        if (n >= 0 && connectedProperties.faces != 63 && (1 << n & connectedProperties.faces) == 0x0) {
            return null;
        }
        if (connectedProperties.metadatas != null) {
            final int[] metadatas = connectedProperties.metadatas;
            while (0 < metadatas.length) {
                if (metadatas[0] == 2) {
                    break;
                }
                int n4 = 0;
                ++n4;
            }
        }
        switch (connectedProperties.method) {
            case 1: {
                return getConnectedTextureCtm(connectedProperties, blockAccess, blockState, blockPos, n, textureAtlasSprite, n2, renderEnv);
            }
            case 2: {
                return getConnectedTextureHorizontal(connectedProperties, blockAccess, blockState, blockPos, 0, n, textureAtlasSprite, n2);
            }
            case 3: {
                return getConnectedTextureTop(connectedProperties, blockAccess, blockState, blockPos, 0, n, textureAtlasSprite, n2);
            }
            case 4: {
                return getConnectedTextureRandom(connectedProperties, blockPos, n);
            }
            case 5: {
                return getConnectedTextureRepeat(connectedProperties, blockPos, n);
            }
            case 6: {
                return getConnectedTextureVertical(connectedProperties, blockAccess, blockState, blockPos, 0, n, textureAtlasSprite, n2);
            }
            case 7: {
                return getConnectedTextureFixed(connectedProperties);
            }
            case 8: {
                return getConnectedTextureHorizontalVertical(connectedProperties, blockAccess, blockState, blockPos, 0, n, textureAtlasSprite, n2);
            }
            case 9: {
                return getConnectedTextureVerticalHorizontal(connectedProperties, blockAccess, blockState, blockPos, 0, n, textureAtlasSprite, n2);
            }
            default: {
                return null;
            }
        }
    }
    
    private static void updateIconEmpty(final TextureMap textureMap) {
    }
    
    private static EnumFacing getFacing(final int n) {
        switch (n) {
            case 0: {
                return EnumFacing.DOWN;
            }
            case 1: {
                return EnumFacing.UP;
            }
            case 2: {
                return EnumFacing.NORTH;
            }
            case 3: {
                return EnumFacing.SOUTH;
            }
            case 4: {
                return EnumFacing.WEST;
            }
            case 5: {
                return EnumFacing.EAST;
            }
            default: {
                return EnumFacing.UP;
            }
        }
    }
    
    private static TextureAtlasSprite getConnectedTextureHorizontalVertical(final ConnectedProperties connectedProperties, final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final int n, final int n2, final TextureAtlasSprite textureAtlasSprite, final int n3) {
        final TextureAtlasSprite[] tileIcons = connectedProperties.tileIcons;
        final TextureAtlasSprite connectedTextureHorizontal = getConnectedTextureHorizontal(connectedProperties, blockAccess, blockState, blockPos, n, n2, textureAtlasSprite, n3);
        if (connectedTextureHorizontal != null && connectedTextureHorizontal != textureAtlasSprite && connectedTextureHorizontal != tileIcons[3]) {
            return connectedTextureHorizontal;
        }
        final TextureAtlasSprite connectedTextureVertical = getConnectedTextureVertical(connectedProperties, blockAccess, blockState, blockPos, n, n2, textureAtlasSprite, n3);
        return (connectedTextureVertical == tileIcons[0]) ? tileIcons[4] : ((connectedTextureVertical == tileIcons[1]) ? tileIcons[5] : ((connectedTextureVertical == tileIcons[2]) ? tileIcons[6] : connectedTextureVertical));
    }
    
    private static String[] collectFilesDefault(final IResourcePack resourcePack) {
        final ArrayList<String> list = new ArrayList<String>();
        final String[] defaultCtmPaths = getDefaultCtmPaths();
        while (0 < defaultCtmPaths.length) {
            final String s = defaultCtmPaths[0];
            if (resourcePack.resourceExists(new ResourceLocation(s))) {
                list.add(s);
            }
            int n = 0;
            ++n;
        }
        return list.toArray(new String[list.size()]);
    }
    
    private static int getQuartzAxis(final int n, final int n2) {
        switch (n2) {
            case 3: {
                return 2;
            }
            case 4: {
                return 1;
            }
            default: {
                return 0;
            }
        }
    }
    
    private static TextureAtlasSprite getConnectedTextureRepeat(final ConnectedProperties connectedProperties, final BlockPos blockPos, final int n) {
        if (connectedProperties.tileIcons.length == 1) {
            return connectedProperties.tileIcons[0];
        }
        blockPos.getX();
        blockPos.getY();
        blockPos.getZ();
        switch (n) {
            case 0: {}
            case 1: {}
            case 2: {}
            case 3: {}
            case 4: {}
        }
        final int n2 = 0 % connectedProperties.width;
        final int n3 = 0 % connectedProperties.height;
        return connectedProperties.tileIcons[0 * connectedProperties.width + 0];
    }
    
    private static TextureAtlasSprite getConnectedTextureMultiPass(final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final EnumFacing enumFacing, final TextureAtlasSprite textureAtlasSprite, final RenderEnv renderEnv) {
        final TextureAtlasSprite connectedTextureSingle = getConnectedTextureSingle(blockAccess, blockState, blockPos, enumFacing, textureAtlasSprite, true, renderEnv);
        if (!ConnectedTextures.multipass) {
            return connectedTextureSingle;
        }
        if (connectedTextureSingle == textureAtlasSprite) {
            return connectedTextureSingle;
        }
        TextureAtlasSprite textureAtlasSprite2 = connectedTextureSingle;
        while (true) {
            final TextureAtlasSprite connectedTextureSingle2 = getConnectedTextureSingle(blockAccess, blockState, blockPos, enumFacing, textureAtlasSprite2, false, renderEnv);
            if (connectedTextureSingle2 == textureAtlasSprite2) {
                break;
            }
            textureAtlasSprite2 = connectedTextureSingle2;
            int n = 0;
            ++n;
        }
        return textureAtlasSprite2;
    }
    
    public static int getReversePaneTextureIndex(final int n) {
        final int n2 = n % 16;
        return (n2 == 1) ? (n + 2) : ((n2 == 3) ? (n - 2) : n);
    }
    
    private static String[] collectFilesZIP(final File file, final String s, final String s2) {
        final ArrayList<String> list = new ArrayList<String>();
        final String s3 = "assets/minecraft/";
        final ZipFile zipFile = new ZipFile(file);
        final Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            final String name = ((ZipEntry)entries.nextElement()).getName();
            if (name.startsWith(s3)) {
                final String substring = name.substring(s3.length());
                if (!substring.startsWith(s) || !substring.endsWith(s2)) {
                    continue;
                }
                list.add(substring);
            }
        }
        zipFile.close();
        return list.toArray(new String[list.size()]);
    }
    
    private static TextureAtlasSprite getConnectedTextureRandom(final ConnectedProperties connectedProperties, final BlockPos blockPos, final int n) {
        if (connectedProperties.tileIcons.length == 1) {
            return connectedProperties.tileIcons[0];
        }
        final int n2 = Config.getRandom(blockPos, n / connectedProperties.symmetry * connectedProperties.symmetry) & Integer.MAX_VALUE;
        if (connectedProperties.weights == null) {
            final int n3 = n2 % connectedProperties.tileIcons.length;
        }
        else {
            final int n4 = n2 % connectedProperties.sumAllWeights;
            final int[] sumWeights = connectedProperties.sumWeights;
            while (0 < sumWeights.length) {
                if (n4 < sumWeights[0]) {
                    break;
                }
                int n5 = 0;
                ++n5;
            }
        }
        return connectedProperties.tileIcons[0];
    }
    
    private static String[] getDefaultCtmPaths() {
        final ArrayList<String> list = new ArrayList<String>();
        final String s = "mcpatcher/ctm/default/";
        if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/glass.png"))) {
            list.add(s + "glass.properties");
            list.add(s + "glasspane.properties");
        }
        if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/bookshelf.png"))) {
            list.add(s + "bookshelf.properties");
        }
        if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/sandstone_normal.png"))) {
            list.add(s + "sandstone.properties");
        }
        final String[] array = { "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "silver", "cyan", "purple", "blue", "brown", "green", "red", "black" };
        while (0 < array.length) {
            final String s2 = array[0];
            if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/glass_" + s2 + ".png"))) {
                list.add(s + 0 + "_glass_" + s2 + "/glass_" + s2 + ".properties");
                list.add(s + 0 + "_glass_" + s2 + "/glass_pane_" + s2 + ".properties");
            }
            int n = 0;
            ++n;
        }
        return list.toArray(new String[list.size()]);
    }
    
    private static BakedQuad getQuad(final TextureAtlasSprite textureAtlasSprite, final Block block, final IBlockState blockState, final BakedQuad bakedQuad) {
        if (ConnectedTextures.spriteQuadMaps == null) {
            return bakedQuad;
        }
        final int indexInMap = textureAtlasSprite.getIndexInMap();
        if (indexInMap >= 0 && indexInMap < ConnectedTextures.spriteQuadMaps.length) {
            Map<Object, BakedQuad> map = (Map<Object, BakedQuad>)ConnectedTextures.spriteQuadMaps[indexInMap];
            if (map == null) {
                map = (Map<Object, BakedQuad>)new IdentityHashMap<BakedQuad, BakedQuad>(1);
                ConnectedTextures.spriteQuadMaps[indexInMap] = map;
            }
            BakedQuad spriteQuad = map.get(bakedQuad);
            if (spriteQuad == null) {
                spriteQuad = makeSpriteQuad(bakedQuad, textureAtlasSprite);
                map.put(bakedQuad, spriteQuad);
            }
            return spriteQuad;
        }
        return bakedQuad;
    }
    
    private static String[] collectFilesFolder(final File file, final String s, final String s2, final String s3) {
        final ArrayList<String> list = new ArrayList<String>();
        final String s4 = "assets/minecraft/";
        final File[] listFiles = file.listFiles();
        if (listFiles == null) {
            return new String[0];
        }
        while (0 < listFiles.length) {
            final File file2 = listFiles[0];
            if (file2.isFile()) {
                final String string = s + file2.getName();
                if (string.startsWith(s4)) {
                    final String substring = string.substring(s4.length());
                    if (substring.startsWith(s2) && substring.endsWith(s3)) {
                        list.add(substring);
                    }
                }
            }
            else if (file2.isDirectory()) {
                final String[] collectFilesFolder = collectFilesFolder(file2, s + file2.getName() + "/", s2, s3);
                while (0 < collectFilesFolder.length) {
                    list.add(collectFilesFolder[0]);
                    int n = 0;
                    ++n;
                }
            }
            int n2 = 0;
            ++n2;
        }
        return list.toArray(new String[list.size()]);
    }
    
    private static int fixSideByAxis(final int n, final int n2) {
        switch (n2) {
            case 0: {
                return n;
            }
            case 1: {
                switch (n) {
                    case 0: {
                        return 2;
                    }
                    case 1: {
                        return 3;
                    }
                    case 2: {
                        return 1;
                    }
                    case 3: {
                        return 0;
                    }
                    default: {
                        return n;
                    }
                }
                break;
            }
            case 2: {
                switch (n) {
                    case 0: {
                        return 4;
                    }
                    case 1: {
                        return 5;
                    }
                    default: {
                        return n;
                    }
                    case 4: {
                        return 1;
                    }
                    case 5: {
                        return 0;
                    }
                }
                break;
            }
            default: {
                return n;
            }
        }
    }
    
    private static TextureAtlasSprite getConnectedTextureHorizontal(final ConnectedProperties connectedProperties, final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final int n, final int n2, final TextureAtlasSprite textureAtlasSprite, final int n3) {
        Label_0626: {
            switch (n) {
                case 0: {
                    switch (n2) {
                        case 0:
                        case 1: {
                            return null;
                        }
                        case 2: {
                            isNeighbour(connectedProperties, blockAccess, blockState, blockPos.east(), n2, textureAtlasSprite, n3);
                            isNeighbour(connectedProperties, blockAccess, blockState, blockPos.west(), n2, textureAtlasSprite, n3);
                            break;
                        }
                        case 3: {
                            isNeighbour(connectedProperties, blockAccess, blockState, blockPos.west(), n2, textureAtlasSprite, n3);
                            isNeighbour(connectedProperties, blockAccess, blockState, blockPos.east(), n2, textureAtlasSprite, n3);
                            break;
                        }
                        case 4: {
                            isNeighbour(connectedProperties, blockAccess, blockState, blockPos.north(), n2, textureAtlasSprite, n3);
                            isNeighbour(connectedProperties, blockAccess, blockState, blockPos.south(), n2, textureAtlasSprite, n3);
                            break;
                        }
                        case 5: {
                            isNeighbour(connectedProperties, blockAccess, blockState, blockPos.south(), n2, textureAtlasSprite, n3);
                            isNeighbour(connectedProperties, blockAccess, blockState, blockPos.north(), n2, textureAtlasSprite, n3);
                            break;
                        }
                    }
                    break;
                }
                case 1: {
                    switch (n2) {
                        case 0: {
                            isNeighbour(connectedProperties, blockAccess, blockState, blockPos.west(), n2, textureAtlasSprite, n3);
                            isNeighbour(connectedProperties, blockAccess, blockState, blockPos.east(), n2, textureAtlasSprite, n3);
                            break;
                        }
                        case 1: {
                            isNeighbour(connectedProperties, blockAccess, blockState, blockPos.west(), n2, textureAtlasSprite, n3);
                            isNeighbour(connectedProperties, blockAccess, blockState, blockPos.east(), n2, textureAtlasSprite, n3);
                            break;
                        }
                        case 2:
                        case 3: {
                            return null;
                        }
                        case 4: {
                            isNeighbour(connectedProperties, blockAccess, blockState, blockPos.down(), n2, textureAtlasSprite, n3);
                            isNeighbour(connectedProperties, blockAccess, blockState, blockPos.up(), n2, textureAtlasSprite, n3);
                            break;
                        }
                        case 5: {
                            isNeighbour(connectedProperties, blockAccess, blockState, blockPos.up(), n2, textureAtlasSprite, n3);
                            isNeighbour(connectedProperties, blockAccess, blockState, blockPos.down(), n2, textureAtlasSprite, n3);
                            break;
                        }
                    }
                    break;
                }
                case 2: {
                    switch (n2) {
                        case 0: {
                            isNeighbour(connectedProperties, blockAccess, blockState, blockPos.north(), n2, textureAtlasSprite, n3);
                            isNeighbour(connectedProperties, blockAccess, blockState, blockPos.south(), n2, textureAtlasSprite, n3);
                            break Label_0626;
                        }
                        case 1: {
                            isNeighbour(connectedProperties, blockAccess, blockState, blockPos.north(), n2, textureAtlasSprite, n3);
                            isNeighbour(connectedProperties, blockAccess, blockState, blockPos.south(), n2, textureAtlasSprite, n3);
                            break Label_0626;
                        }
                        case 2: {
                            isNeighbour(connectedProperties, blockAccess, blockState, blockPos.down(), n2, textureAtlasSprite, n3);
                            isNeighbour(connectedProperties, blockAccess, blockState, blockPos.up(), n2, textureAtlasSprite, n3);
                            break Label_0626;
                        }
                        case 3: {
                            isNeighbour(connectedProperties, blockAccess, blockState, blockPos.up(), n2, textureAtlasSprite, n3);
                            isNeighbour(connectedProperties, blockAccess, blockState, blockPos.down(), n2, textureAtlasSprite, n3);
                            break Label_0626;
                        }
                        case 4:
                        case 5: {
                            return null;
                        }
                        default: {
                            break Label_0626;
                        }
                    }
                    break;
                }
            }
        }
        return connectedProperties.tileIcons[3];
    }
    
    public static int getSide(final EnumFacing enumFacing) {
        if (enumFacing == null) {
            return -1;
        }
        switch (ConnectedTextures$1.$SwitchMap$net$minecraft$util$EnumFacing[enumFacing.ordinal()]) {
            case 1: {
                return 0;
            }
            case 2: {
                return 1;
            }
            case 3: {
                return 5;
            }
            case 4: {
                return 4;
            }
            case 5: {
                return 2;
            }
            case 6: {
                return 3;
            }
            default: {
                return -1;
            }
        }
    }
    
    private static TextureAtlasSprite getConnectedTextureVerticalHorizontal(final ConnectedProperties connectedProperties, final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final int n, final int n2, final TextureAtlasSprite textureAtlasSprite, final int n3) {
        final TextureAtlasSprite[] tileIcons = connectedProperties.tileIcons;
        final TextureAtlasSprite connectedTextureVertical = getConnectedTextureVertical(connectedProperties, blockAccess, blockState, blockPos, n, n2, textureAtlasSprite, n3);
        if (connectedTextureVertical != null && connectedTextureVertical != textureAtlasSprite && connectedTextureVertical != tileIcons[3]) {
            return connectedTextureVertical;
        }
        final TextureAtlasSprite connectedTextureHorizontal = getConnectedTextureHorizontal(connectedProperties, blockAccess, blockState, blockPos, n, n2, textureAtlasSprite, n3);
        return (connectedTextureHorizontal == tileIcons[0]) ? tileIcons[4] : ((connectedTextureHorizontal == tileIcons[1]) ? tileIcons[5] : ((connectedTextureHorizontal == tileIcons[2]) ? tileIcons[6] : connectedTextureHorizontal));
    }
}
