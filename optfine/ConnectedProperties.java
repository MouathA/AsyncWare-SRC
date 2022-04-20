package optfine;

import net.minecraft.world.biome.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import java.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;
import net.minecraft.client.*;

public class ConnectedProperties
{
    public int width;
    public String name;
    public static final int FACE_BOTTOM = 1;
    public static final int CONNECT_UNKNOWN = 128;
    public TextureAtlasSprite[] tileIcons;
    public static final int FACE_UNKNOWN = 128;
    public String basePath;
    public TextureAtlasSprite[] matchTileIcons;
    public static final int CONNECT_BLOCK = 1;
    public int[] sumWeights;
    public static final int FACE_TOP = 2;
    public static final int METHOD_RANDOM = 4;
    public static final int SYMMETRY_NONE = 1;
    public static final int CONNECT_MATERIAL = 3;
    public static final int CONNECT_NONE = 0;
    public static final int METHOD_VERTICAL_HORIZONTAL = 9;
    public boolean innerSeams;
    public static final int METHOD_NONE = 0;
    public int[] matchBlocks;
    public String[] matchTiles;
    public static final int METHOD_HORIZONTAL = 2;
    public static final int FACE_EAST = 32;
    public int symmetry;
    public int sumAllWeights;
    public int[] weights;
    public BiomeGenBase[] biomes;
    public static final int METHOD_CTM = 1;
    public int method;
    public int maxHeight;
    public int[] metadatas;
    public static final int SYMMETRY_ALL = 6;
    public int faces;
    public static final int FACE_NORTH = 4;
    public static final int FACE_ALL = 63;
    public static final int FACE_SOUTH = 8;
    public static final int FACE_WEST = 16;
    public String[] tiles;
    public static final int METHOD_REPEAT = 5;
    public int height;
    public static final int METHOD_VERTICAL = 6;
    public static final int SYMMETRY_OPPOSITE = 2;
    public static final int METHOD_HORIZONTAL_VERTICAL = 8;
    public static final int SYMMETRY_UNKNOWN = 128;
    public int minHeight;
    public static final int METHOD_TOP = 3;
    public static final int CONNECT_TILE = 2;
    public static final int METHOD_FIXED = 7;
    public static final int FACE_SIDES = 60;
    public int renderPass;
    public int connect;
    
    private static int parseInt(final String s) {
        if (s == null) {
            return -1;
        }
        final int int1 = Config.parseInt(s, -1);
        if (int1 < 0) {
            Config.warn("Invalid number: " + s);
        }
        return int1;
    }
    
    private boolean isValidFixed(final String s) {
        if (this.tiles == null) {
            Config.warn("Tiles not defined: " + s);
            return false;
        }
        if (this.tiles.length != 1) {
            Config.warn("Number of tiles should be 1 for method: fixed.");
            return false;
        }
        return true;
    }
    
    private static int parseSymmetry(final String s) {
        if (s == null) {
            return 1;
        }
        if (s.equals("opposite")) {
            return 2;
        }
        if (s.equals("all")) {
            return 6;
        }
        Config.warn("Unknown symmetry: " + s);
        return 1;
    }
    
    private String[] parseMatchTiles(final String s) {
        if (s == null) {
            return null;
        }
        final String[] tokenize = Config.tokenize(s, " ");
        while (0 < tokenize.length) {
            String substring = tokenize[0];
            if (substring.endsWith(".png")) {
                substring = substring.substring(0, substring.length() - 4);
            }
            tokenize[0] = TextureUtils.fixResourcePath(substring, this.basePath);
            int n = 0;
            ++n;
        }
        return tokenize;
    }
    
    private IBlockState parseBlockState(final String s) {
        if (s == null) {
            return null;
        }
        final String[] tokenize = Config.tokenize(s, ":");
        if (tokenize.length < 2) {
            return null;
        }
        final String string = tokenize[0] + ":" + tokenize[1];
        final Block blockFromName = Block.getBlockFromName(string);
        if (blockFromName == null) {
            return null;
        }
        IBlockState blockState = null;
        while (2 < tokenize.length) {
            final String s2 = tokenize[2];
            if (s2.length() >= 1) {
                if (Character.isDigit(s2.charAt(0))) {
                    if (s2.indexOf(45) < 0 && s2.indexOf(44) < 0) {
                        if (Config.parseInt(s2, -1) >= 0) {}
                    }
                }
                else {
                    final String[] tokenize2 = Config.tokenize(s2, "=");
                    if (tokenize2.length >= 2) {
                        final String s3 = tokenize2[0];
                        final String s4 = tokenize2[1];
                        if (s4.indexOf(44) < 0) {
                            if (blockState == null) {
                                blockState = blockFromName.getDefaultState();
                            }
                            final IProperty property = getProperty(s3, blockState.getPropertyNames());
                            if (property == null) {
                                final String s5 = "\"";
                                Config.warn("Block " + s5 + string + s5 + " has no property " + s5 + s3 + s5);
                            }
                            else {
                                Comparable comparable = ConnectedParser.parseValue(s4, property.getValueClass());
                                if (comparable == null) {
                                    comparable = ConnectedParser.getPropertyValue(s4, property.getAllowedValues());
                                }
                                if (comparable == null) {
                                    Config.warn("Invalid value: " + s4 + ", for property: " + property);
                                }
                                else if (!(comparable instanceof Comparable)) {
                                    Config.warn("Value is not Comparable: " + s4 + ", for property: " + property);
                                }
                                else {
                                    blockState = blockState.withProperty(property, comparable);
                                }
                            }
                        }
                    }
                }
            }
            int n = 0;
            ++n;
        }
        if (blockState == null) {
            return null;
        }
        return blockState;
    }
    
    public boolean isValid(final String s) {
        if (this.name == null || this.name.length() <= 0) {
            Config.warn("No name found: " + s);
            return false;
        }
        if (this.basePath == null) {
            Config.warn("No base path found: " + s);
            return false;
        }
        if (this.matchBlocks == null) {
            this.matchBlocks = this.detectMatchBlocks();
        }
        if (this.matchTiles == null && this.matchBlocks == null) {
            this.matchTiles = this.detectMatchTiles();
        }
        if (this.matchBlocks == null && this.matchTiles == null) {
            Config.warn("No matchBlocks or matchTiles specified: " + s);
            return false;
        }
        if (this.method == 0) {
            Config.warn("No method: " + s);
            return false;
        }
        if (this.tiles == null || this.tiles.length <= 0) {
            Config.warn("No tiles specified: " + s);
            return false;
        }
        if (this.connect == 0) {
            this.connect = this.detectConnect();
        }
        if (this.connect == 128) {
            Config.warn("Invalid connect in: " + s);
            return false;
        }
        if (this.renderPass > 0) {
            Config.warn("Render pass not supported: " + this.renderPass);
            return false;
        }
        if ((this.faces & 0x80) != 0x0) {
            Config.warn("Invalid faces in: " + s);
            return false;
        }
        if ((this.symmetry & 0x80) != 0x0) {
            Config.warn("Invalid symmetry in: " + s);
            return false;
        }
        switch (this.method) {
            case 1: {
                return this.isValidCtm(s);
            }
            case 2: {
                return this.isValidHorizontal(s);
            }
            case 3: {
                return this.isValidTop(s);
            }
            case 4: {
                return this.isValidRandom(s);
            }
            case 5: {
                return this.isValidRepeat(s);
            }
            case 6: {
                return this.isValidVertical(s);
            }
            case 7: {
                return this.isValidFixed(s);
            }
            case 8: {
                return this.isValidHorizontalVertical(s);
            }
            case 9: {
                return this.isValidVerticalHorizontal(s);
            }
            default: {
                Config.warn("Unknown method: " + s);
                return false;
            }
        }
    }
    
    private boolean isValidTop(final String s) {
        if (this.tiles == null) {
            this.tiles = this.parseTileNames("66");
        }
        if (this.tiles.length != 1) {
            Config.warn("Invalid tiles, must be exactly 1: " + s);
            return false;
        }
        return true;
    }
    
    private boolean isValidCtm(final String s) {
        if (this.tiles == null) {
            this.tiles = this.parseTileNames("0-11 16-27 32-43 48-58");
        }
        if (this.tiles.length < 47) {
            Config.warn("Invalid tiles, must be at least 47: " + s);
            return false;
        }
        return true;
    }
    
    private static int parseConnect(final String s) {
        if (s == null) {
            return 0;
        }
        if (s.equals("block")) {
            return 1;
        }
        if (s.equals("tile")) {
            return 2;
        }
        if (s.equals("material")) {
            return 3;
        }
        Config.warn("Unknown connect: " + s);
        return 128;
    }
    
    private boolean isValidVerticalHorizontal(final String s) {
        if (this.tiles == null) {
            Config.warn("No tiles defined for vertical+horizontal: " + s);
            return false;
        }
        if (this.tiles.length != 7) {
            Config.warn("Invalid tiles, must be exactly 7: " + s);
            return false;
        }
        return true;
    }
    
    public boolean matchesIcon(final TextureAtlasSprite textureAtlasSprite) {
        if (this.matchTileIcons != null && this.matchTileIcons.length > 0) {
            while (0 < this.matchTileIcons.length) {
                if (this.matchTileIcons[0] == textureAtlasSprite) {
                    return true;
                }
                int n = 0;
                ++n;
            }
            return false;
        }
        return true;
    }
    
    public ConnectedProperties(final Properties properties, final String s) {
        this.name = null;
        this.basePath = null;
        this.matchBlocks = null;
        this.matchTiles = null;
        this.method = 0;
        this.tiles = null;
        this.connect = 0;
        this.faces = 63;
        this.metadatas = null;
        this.biomes = null;
        this.minHeight = 0;
        this.maxHeight = 1024;
        this.renderPass = 0;
        this.innerSeams = false;
        this.width = 0;
        this.height = 0;
        this.weights = null;
        this.symmetry = 1;
        this.sumWeights = null;
        this.sumAllWeights = 1;
        this.matchTileIcons = null;
        this.tileIcons = null;
        this.name = parseName(s);
        this.basePath = parseBasePath(s);
        final String property = properties.getProperty("matchBlocks");
        final IBlockState blockState = this.parseBlockState(property);
        if (blockState != null) {
            this.matchBlocks = new int[] { Block.getIdFromBlock(blockState.getBlock()) };
            this.metadatas = new int[] { blockState.getBlock().getMetaFromState(blockState) };
        }
        if (this.matchBlocks == null) {
            this.matchBlocks = parseBlockIds(property);
        }
        if (this.metadatas == null) {
            this.metadatas = parseInts(properties.getProperty("metadata"));
        }
        this.matchTiles = this.parseMatchTiles(properties.getProperty("matchTiles"));
        this.method = parseMethod(properties.getProperty("method"));
        this.tiles = this.parseTileNames(properties.getProperty("tiles"));
        this.connect = parseConnect(properties.getProperty("connect"));
        this.faces = parseFaces(properties.getProperty("faces"));
        this.biomes = parseBiomes(properties.getProperty("biomes"));
        this.minHeight = parseInt(properties.getProperty("minHeight"), -1);
        this.maxHeight = parseInt(properties.getProperty("maxHeight"), 1024);
        this.renderPass = parseInt(properties.getProperty("renderPass"));
        this.innerSeams = parseBoolean(properties.getProperty("innerSeams"));
        this.width = parseInt(properties.getProperty("width"));
        this.height = parseInt(properties.getProperty("height"));
        this.weights = parseInts(properties.getProperty("weights"));
        this.symmetry = parseSymmetry(properties.getProperty("symmetry"));
    }
    
    private static BiomeGenBase[] parseBiomes(final String s) {
        if (s == null) {
            return null;
        }
        final String[] tokenize = Config.tokenize(s, " ");
        final ArrayList<BiomeGenBase> list = new ArrayList<BiomeGenBase>();
        while (0 < tokenize.length) {
            final String s2 = tokenize[0];
            final BiomeGenBase biome = findBiome(s2);
            if (biome == null) {
                Config.warn("Biome not found: " + s2);
            }
            else {
                list.add(biome);
            }
            int n = 0;
            ++n;
        }
        return list.toArray(new BiomeGenBase[list.size()]);
    }
    
    private static int parseBlockId(final String s) {
        final int int1 = Config.parseInt(s, -1);
        if (int1 >= 0) {
            return int1;
        }
        final Block blockFromName = Block.getBlockFromName(s);
        return (blockFromName != null) ? Block.getIdFromBlock(blockFromName) : -1;
    }
    
    private static String parseBasePath(final String s) {
        final int lastIndex = s.lastIndexOf(47);
        return (lastIndex < 0) ? "" : s.substring(0, lastIndex);
    }
    
    private static int[] parseBlockIds(final String p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnonnull       6
        //     4: aconst_null    
        //     5: areturn        
        //     6: new             Ljava/util/ArrayList;
        //     9: dup            
        //    10: invokespecial   java/util/ArrayList.<init>:()V
        //    13: astore_1       
        //    14: aload_0        
        //    15: ldc_w           " ,"
        //    18: invokestatic    optfine/Config.tokenize:(Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;
        //    21: astore_2       
        //    22: iconst_0       
        //    23: aload_2        
        //    24: arraylength    
        //    25: if_icmpge       269
        //    28: aload_2        
        //    29: iconst_0       
        //    30: aaload         
        //    31: astore          4
        //    33: aload           4
        //    35: ldc_w           "-"
        //    38: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //    41: ifeq            202
        //    44: aload           4
        //    46: ldc_w           "-"
        //    49: invokestatic    optfine/Config.tokenize:(Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;
        //    52: astore          5
        //    54: aload           5
        //    56: arraylength    
        //    57: iconst_2       
        //    58: if_icmpeq       98
        //    61: new             Ljava/lang/StringBuilder;
        //    64: dup            
        //    65: invokespecial   java/lang/StringBuilder.<init>:()V
        //    68: ldc_w           "Invalid interval: "
        //    71: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    74: aload           4
        //    76: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    79: ldc_w           ", when parsing: "
        //    82: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    85: aload_0        
        //    86: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    89: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    92: invokestatic    optfine/Config.warn:(Ljava/lang/String;)V
        //    95: goto            263
        //    98: aload           5
        //   100: iconst_0       
        //   101: aaload         
        //   102: invokestatic    optfine/ConnectedProperties.parseBlockId:(Ljava/lang/String;)I
        //   105: istore          6
        //   107: aload           5
        //   109: iconst_1       
        //   110: aaload         
        //   111: invokestatic    optfine/ConnectedProperties.parseBlockId:(Ljava/lang/String;)I
        //   114: istore          7
        //   116: iload           6
        //   118: iflt            165
        //   121: iload           7
        //   123: iflt            165
        //   126: iload           6
        //   128: iload           7
        //   130: if_icmpgt       165
        //   133: iload           6
        //   135: istore          8
        //   137: iload           8
        //   139: iload           7
        //   141: if_icmpgt       162
        //   144: aload_1        
        //   145: iload           8
        //   147: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   150: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   155: pop            
        //   156: iinc            8, 1
        //   159: goto            137
        //   162: goto            263
        //   165: new             Ljava/lang/StringBuilder;
        //   168: dup            
        //   169: invokespecial   java/lang/StringBuilder.<init>:()V
        //   172: ldc_w           "Invalid interval: "
        //   175: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   178: aload           4
        //   180: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   183: ldc_w           ", when parsing: "
        //   186: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   189: aload_0        
        //   190: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   193: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   196: invokestatic    optfine/Config.warn:(Ljava/lang/String;)V
        //   199: goto            263
        //   202: aload           4
        //   204: invokestatic    optfine/ConnectedProperties.parseBlockId:(Ljava/lang/String;)I
        //   207: istore          5
        //   209: iload           5
        //   211: ifge            251
        //   214: new             Ljava/lang/StringBuilder;
        //   217: dup            
        //   218: invokespecial   java/lang/StringBuilder.<init>:()V
        //   221: ldc_w           "Invalid block ID: "
        //   224: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   227: aload           4
        //   229: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   232: ldc_w           ", when parsing: "
        //   235: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   238: aload_0        
        //   239: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   242: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   245: invokestatic    optfine/Config.warn:(Ljava/lang/String;)V
        //   248: goto            263
        //   251: aload_1        
        //   252: iload           5
        //   254: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   257: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   262: pop            
        //   263: iinc            3, 1
        //   266: goto            22
        //   269: aload_1        
        //   270: invokeinterface java/util/List.size:()I
        //   275: newarray        I
        //   277: astore_3       
        //   278: iconst_0       
        //   279: aload_3        
        //   280: arraylength    
        //   281: if_icmpge       306
        //   284: aload_3        
        //   285: iconst_0       
        //   286: aload_1        
        //   287: iconst_0       
        //   288: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   293: checkcast       Ljava/lang/Integer;
        //   296: invokevirtual   java/lang/Integer.intValue:()I
        //   299: iastore        
        //   300: iinc            4, 1
        //   303: goto            278
        //   306: aload_3        
        //   307: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static IProperty getProperty(final String s, final Collection collection) {
        for (final IProperty next : collection) {
            if (s.equals(next.getName())) {
                return next;
            }
        }
        return null;
    }
    
    private int[] detectMatchBlocks() {
        if (!this.name.startsWith("block")) {
            return null;
        }
        while (5 < this.name.length()) {
            final char char1 = this.name.charAt(5);
            if (char1 < '0') {
                break;
            }
            if (char1 > '9') {
                break;
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    private static String parseName(final String s) {
        String s2 = s;
        final int lastIndex = s.lastIndexOf(47);
        if (lastIndex >= 0) {
            s2 = s.substring(lastIndex + 1);
        }
        final int lastIndex2 = s2.lastIndexOf(46);
        if (lastIndex2 >= 0) {
            s2 = s2.substring(0, lastIndex2);
        }
        return s2;
    }
    
    private String[] parseTileNames(final String p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnonnull       6
        //     4: aconst_null    
        //     5: areturn        
        //     6: new             Ljava/util/ArrayList;
        //     9: dup            
        //    10: invokespecial   java/util/ArrayList.<init>:()V
        //    13: astore_2       
        //    14: aload_1        
        //    15: ldc_w           " ,"
        //    18: invokestatic    optfine/Config.tokenize:(Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;
        //    21: astore_3       
        //    22: iconst_0       
        //    23: aload_3        
        //    24: arraylength    
        //    25: if_icmpge       182
        //    28: aload_3        
        //    29: iconst_0       
        //    30: aaload         
        //    31: astore          5
        //    33: aload           5
        //    35: ldc_w           "-"
        //    38: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //    41: ifeq            167
        //    44: aload           5
        //    46: ldc_w           "-"
        //    49: invokestatic    optfine/Config.tokenize:(Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;
        //    52: astore          6
        //    54: aload           6
        //    56: arraylength    
        //    57: iconst_2       
        //    58: if_icmpne       167
        //    61: aload           6
        //    63: iconst_0       
        //    64: aaload         
        //    65: iconst_m1      
        //    66: invokestatic    optfine/Config.parseInt:(Ljava/lang/String;I)I
        //    69: istore          7
        //    71: aload           6
        //    73: iconst_1       
        //    74: aaload         
        //    75: iconst_m1      
        //    76: invokestatic    optfine/Config.parseInt:(Ljava/lang/String;I)I
        //    79: istore          8
        //    81: iload           7
        //    83: iflt            167
        //    86: iload           8
        //    88: iflt            167
        //    91: iload           7
        //    93: iload           8
        //    95: if_icmple       135
        //    98: new             Ljava/lang/StringBuilder;
        //   101: dup            
        //   102: invokespecial   java/lang/StringBuilder.<init>:()V
        //   105: ldc_w           "Invalid interval: "
        //   108: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   111: aload           5
        //   113: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   116: ldc_w           ", when parsing: "
        //   119: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   122: aload_1        
        //   123: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   126: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   129: invokestatic    optfine/Config.warn:(Ljava/lang/String;)V
        //   132: goto            176
        //   135: iload           7
        //   137: istore          9
        //   139: iload           9
        //   141: iload           8
        //   143: if_icmple       149
        //   146: goto            176
        //   149: aload_2        
        //   150: iload           9
        //   152: invokestatic    java/lang/String.valueOf:(I)Ljava/lang/String;
        //   155: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   160: pop            
        //   161: iinc            9, 1
        //   164: goto            139
        //   167: aload_2        
        //   168: aload           5
        //   170: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   175: pop            
        //   176: iinc            4, 1
        //   179: goto            22
        //   182: aload_2        
        //   183: aload_2        
        //   184: invokeinterface java/util/List.size:()I
        //   189: anewarray       Ljava/lang/String;
        //   192: invokeinterface java/util/List.toArray:([Ljava/lang/Object;)[Ljava/lang/Object;
        //   197: checkcast       [Ljava/lang/String;
        //   200: checkcast       [Ljava/lang/String;
        //   203: checkcast       [Ljava/lang/String;
        //   206: astore          4
        //   208: iconst_0       
        //   209: aload           4
        //   211: arraylength    
        //   212: if_icmpge       379
        //   215: aload           4
        //   217: iconst_0       
        //   218: aaload         
        //   219: astore          6
        //   221: aload           6
        //   223: aload_0        
        //   224: getfield        optfine/ConnectedProperties.basePath:Ljava/lang/String;
        //   227: invokestatic    optfine/TextureUtils.fixResourcePath:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //   230: astore          6
        //   232: aload           6
        //   234: aload_0        
        //   235: getfield        optfine/ConnectedProperties.basePath:Ljava/lang/String;
        //   238: invokevirtual   java/lang/String.startsWith:(Ljava/lang/String;)Z
        //   241: ifne            296
        //   244: aload           6
        //   246: ldc_w           "textures/"
        //   249: invokevirtual   java/lang/String.startsWith:(Ljava/lang/String;)Z
        //   252: ifne            296
        //   255: aload           6
        //   257: ldc_w           "mcpatcher/"
        //   260: invokevirtual   java/lang/String.startsWith:(Ljava/lang/String;)Z
        //   263: ifne            296
        //   266: new             Ljava/lang/StringBuilder;
        //   269: dup            
        //   270: invokespecial   java/lang/StringBuilder.<init>:()V
        //   273: aload_0        
        //   274: getfield        optfine/ConnectedProperties.basePath:Ljava/lang/String;
        //   277: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   280: ldc_w           "/"
        //   283: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   286: aload           6
        //   288: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   291: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   294: astore          6
        //   296: aload           6
        //   298: ldc             ".png"
        //   300: invokevirtual   java/lang/String.endsWith:(Ljava/lang/String;)Z
        //   303: ifeq            321
        //   306: aload           6
        //   308: iconst_0       
        //   309: aload           6
        //   311: invokevirtual   java/lang/String.length:()I
        //   314: iconst_4       
        //   315: isub           
        //   316: invokevirtual   java/lang/String.substring:(II)Ljava/lang/String;
        //   319: astore          6
        //   321: ldc_w           "textures/blocks/"
        //   324: astore          7
        //   326: aload           6
        //   328: aload           7
        //   330: invokevirtual   java/lang/String.startsWith:(Ljava/lang/String;)Z
        //   333: ifeq            348
        //   336: aload           6
        //   338: aload           7
        //   340: invokevirtual   java/lang/String.length:()I
        //   343: invokevirtual   java/lang/String.substring:(I)Ljava/lang/String;
        //   346: astore          6
        //   348: aload           6
        //   350: ldc_w           "/"
        //   353: invokevirtual   java/lang/String.startsWith:(Ljava/lang/String;)Z
        //   356: ifeq            367
        //   359: aload           6
        //   361: iconst_1       
        //   362: invokevirtual   java/lang/String.substring:(I)Ljava/lang/String;
        //   365: astore          6
        //   367: aload           4
        //   369: iconst_0       
        //   370: aload           6
        //   372: aastore        
        //   373: iinc            5, 1
        //   376: goto            208
        //   379: aload           4
        //   381: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private static TextureAtlasSprite[] registerIcons(final String[] array, final TextureMap textureMap) {
        if (array == null) {
            return null;
        }
        final ArrayList<TextureAtlasSprite> list = new ArrayList<TextureAtlasSprite>();
        while (0 < array.length) {
            final ResourceLocation resourceLocation = new ResourceLocation(array[0]);
            final String resourceDomain = resourceLocation.getResourceDomain();
            String s = resourceLocation.getResourcePath();
            if (!s.contains("/")) {
                s = "textures/blocks/" + s;
            }
            final String string = s + ".png";
            if (!Config.hasResource(new ResourceLocation(resourceDomain, string))) {
                Config.warn("File not found: " + string);
            }
            final String s2 = "textures/";
            String substring = s;
            if (s.startsWith(s2)) {
                substring = s.substring(s2.length());
            }
            list.add(textureMap.registerSprite(new ResourceLocation(resourceDomain, substring)));
            int n = 0;
            ++n;
        }
        return list.toArray(new TextureAtlasSprite[list.size()]);
    }
    
    private boolean isValidRepeat(final String s) {
        if (this.tiles == null) {
            Config.warn("Tiles not defined: " + s);
            return false;
        }
        if (this.width <= 0 || this.width > 16) {
            Config.warn("Invalid width: " + s);
            return false;
        }
        if (this.height <= 0 || this.height > 16) {
            Config.warn("Invalid height: " + s);
            return false;
        }
        if (this.tiles.length != this.width * this.height) {
            Config.warn("Number of tiles does not equal width x height: " + s);
            return false;
        }
        return true;
    }
    
    private static int parseFace(String lowerCase) {
        lowerCase = lowerCase.toLowerCase();
        if (lowerCase.equals("bottom") || lowerCase.equals("down")) {
            return 1;
        }
        if (lowerCase.equals("top") || lowerCase.equals("up")) {
            return 2;
        }
        if (lowerCase.equals("north")) {
            return 4;
        }
        if (lowerCase.equals("south")) {
            return 8;
        }
        if (lowerCase.equals("east")) {
            return 32;
        }
        if (lowerCase.equals("west")) {
            return 16;
        }
        if (lowerCase.equals("sides")) {
            return 60;
        }
        if (lowerCase.equals("all")) {
            return 63;
        }
        Config.warn("Unknown face: " + lowerCase);
        return 128;
    }
    
    private int detectConnect() {
        return (this.matchBlocks != null) ? 1 : ((this.matchTiles != null) ? 2 : 128);
    }
    
    private String[] detectMatchTiles() {
        return (String[])((getIcon(this.name) == null) ? null : new String[] { this.name });
    }
    
    private boolean isValidVertical(final String s) {
        if (this.tiles == null) {
            Config.warn("No tiles defined for vertical: " + s);
            return false;
        }
        if (this.tiles.length != 4) {
            Config.warn("Invalid tiles, must be exactly 4: " + s);
            return false;
        }
        return true;
    }
    
    private static int[] parseInts(final String p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnonnull       6
        //     4: aconst_null    
        //     5: areturn        
        //     6: new             Ljava/util/ArrayList;
        //     9: dup            
        //    10: invokespecial   java/util/ArrayList.<init>:()V
        //    13: astore_1       
        //    14: aload_0        
        //    15: ldc_w           " ,"
        //    18: invokestatic    optfine/Config.tokenize:(Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;
        //    21: astore_2       
        //    22: iconst_0       
        //    23: aload_2        
        //    24: arraylength    
        //    25: if_icmpge       271
        //    28: aload_2        
        //    29: iconst_0       
        //    30: aaload         
        //    31: astore          4
        //    33: aload           4
        //    35: ldc_w           "-"
        //    38: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //    41: ifeq            204
        //    44: aload           4
        //    46: ldc_w           "-"
        //    49: invokestatic    optfine/Config.tokenize:(Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;
        //    52: astore          5
        //    54: aload           5
        //    56: arraylength    
        //    57: iconst_2       
        //    58: if_icmpeq       98
        //    61: new             Ljava/lang/StringBuilder;
        //    64: dup            
        //    65: invokespecial   java/lang/StringBuilder.<init>:()V
        //    68: ldc_w           "Invalid interval: "
        //    71: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    74: aload           4
        //    76: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    79: ldc_w           ", when parsing: "
        //    82: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    85: aload_0        
        //    86: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    89: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    92: invokestatic    optfine/Config.warn:(Ljava/lang/String;)V
        //    95: goto            265
        //    98: aload           5
        //   100: iconst_0       
        //   101: aaload         
        //   102: iconst_m1      
        //   103: invokestatic    optfine/Config.parseInt:(Ljava/lang/String;I)I
        //   106: istore          6
        //   108: aload           5
        //   110: iconst_1       
        //   111: aaload         
        //   112: iconst_m1      
        //   113: invokestatic    optfine/Config.parseInt:(Ljava/lang/String;I)I
        //   116: istore          7
        //   118: iload           6
        //   120: iflt            167
        //   123: iload           7
        //   125: iflt            167
        //   128: iload           6
        //   130: iload           7
        //   132: if_icmpgt       167
        //   135: iload           6
        //   137: istore          8
        //   139: iload           8
        //   141: iload           7
        //   143: if_icmpgt       164
        //   146: aload_1        
        //   147: iload           8
        //   149: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   152: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   157: pop            
        //   158: iinc            8, 1
        //   161: goto            139
        //   164: goto            265
        //   167: new             Ljava/lang/StringBuilder;
        //   170: dup            
        //   171: invokespecial   java/lang/StringBuilder.<init>:()V
        //   174: ldc_w           "Invalid interval: "
        //   177: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   180: aload           4
        //   182: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   185: ldc_w           ", when parsing: "
        //   188: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   191: aload_0        
        //   192: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   195: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   198: invokestatic    optfine/Config.warn:(Ljava/lang/String;)V
        //   201: goto            265
        //   204: aload           4
        //   206: iconst_m1      
        //   207: invokestatic    optfine/Config.parseInt:(Ljava/lang/String;I)I
        //   210: istore          5
        //   212: iload           5
        //   214: ifge            253
        //   217: new             Ljava/lang/StringBuilder;
        //   220: dup            
        //   221: invokespecial   java/lang/StringBuilder.<init>:()V
        //   224: ldc             "Invalid number: "
        //   226: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   229: aload           4
        //   231: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   234: ldc_w           ", when parsing: "
        //   237: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   240: aload_0        
        //   241: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   244: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   247: invokestatic    optfine/Config.warn:(Ljava/lang/String;)V
        //   250: goto            265
        //   253: aload_1        
        //   254: iload           5
        //   256: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   259: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   264: pop            
        //   265: iinc            3, 1
        //   268: goto            22
        //   271: aload_1        
        //   272: invokeinterface java/util/List.size:()I
        //   277: newarray        I
        //   279: astore_3       
        //   280: iconst_0       
        //   281: aload_3        
        //   282: arraylength    
        //   283: if_icmpge       308
        //   286: aload_3        
        //   287: iconst_0       
        //   288: aload_1        
        //   289: iconst_0       
        //   290: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   295: checkcast       Ljava/lang/Integer;
        //   298: invokevirtual   java/lang/Integer.intValue:()I
        //   301: iastore        
        //   302: iinc            4, 1
        //   305: goto            280
        //   308: aload_3        
        //   309: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private static TextureAtlasSprite getIcon(final String s) {
        final TextureMap textureMapBlocks = Minecraft.getMinecraft().getTextureMapBlocks();
        final TextureAtlasSprite spriteSafe = textureMapBlocks.getSpriteSafe(s);
        if (spriteSafe != null) {
            return spriteSafe;
        }
        return textureMapBlocks.getSpriteSafe("blocks/" + s);
    }
    
    private static boolean parseBoolean(final String s) {
        return s != null && s.toLowerCase().equals("true");
    }
    
    private boolean isValidHorizontal(final String s) {
        if (this.tiles == null) {
            this.tiles = this.parseTileNames("12-15");
        }
        if (this.tiles.length != 4) {
            Config.warn("Invalid tiles, must be exactly 4: " + s);
            return false;
        }
        return true;
    }
    
    private boolean isValidRandom(final String s) {
        if (this.tiles != null && this.tiles.length > 0) {
            if (this.weights != null) {
                if (this.weights.length > this.tiles.length) {
                    Config.warn("More weights defined than tiles, trimming weights: " + s);
                    final int[] weights = new int[this.tiles.length];
                    System.arraycopy(this.weights, 0, weights, 0, weights.length);
                    this.weights = weights;
                }
                int average = 0;
                if (this.weights.length < this.tiles.length) {
                    Config.warn("Less weights defined than tiles, expanding weights: " + s);
                    final int[] weights2 = new int[this.tiles.length];
                    System.arraycopy(this.weights, 0, weights2, 0, this.weights.length);
                    average = ConnectedUtils.getAverage(this.weights);
                    for (int i = this.weights.length; i < weights2.length; ++i) {
                        weights2[i] = 0;
                    }
                    this.weights = weights2;
                }
                this.sumWeights = new int[this.weights.length];
                while (0 < this.weights.length) {
                    final int n = 0 + this.weights[0];
                    this.sumWeights[0] = 0;
                    ++average;
                }
                this.sumAllWeights = 0;
                if (this.sumAllWeights <= 0) {
                    Config.warn("Invalid sum of all weights: " + 0);
                    this.sumAllWeights = 1;
                }
            }
            return true;
        }
        Config.warn("Tiles not defined: " + s);
        return false;
    }
    
    private static int parseInt(final String s, final int n) {
        if (s == null) {
            return n;
        }
        final int int1 = Config.parseInt(s, -1);
        if (int1 < 0) {
            Config.warn("Invalid number: " + s);
            return n;
        }
        return int1;
    }
    
    public void updateIcons(final TextureMap textureMap) {
        if (this.matchTiles != null) {
            this.matchTileIcons = registerIcons(this.matchTiles, textureMap);
        }
        if (this.tiles != null) {
            this.tileIcons = registerIcons(this.tiles, textureMap);
        }
    }
    
    private static int parseFaces(final String s) {
        if (s == null) {
            return 63;
        }
        final String[] tokenize = Config.tokenize(s, " ,");
        while (0 < tokenize.length) {
            final int n = 0x0 | parseFace(tokenize[0]);
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    @Override
    public String toString() {
        return "CTM name: " + this.name + ", basePath: " + this.basePath + ", matchBlocks: " + Config.arrayToString(this.matchBlocks) + ", matchTiles: " + Config.arrayToString(this.matchTiles);
    }
    
    public boolean matchesBlock(final int n) {
        if (this.matchBlocks != null && this.matchBlocks.length > 0) {
            while (0 < this.matchBlocks.length) {
                if (this.matchBlocks[0] == n) {
                    return true;
                }
                int n2 = 0;
                ++n2;
            }
            return false;
        }
        return true;
    }
    
    private static int parseMethod(final String s) {
        if (s == null) {
            return 1;
        }
        if (s.equals("ctm") || s.equals("glass")) {
            return 1;
        }
        if (s.equals("horizontal") || s.equals("bookshelf")) {
            return 2;
        }
        if (s.equals("vertical")) {
            return 6;
        }
        if (s.equals("top")) {
            return 3;
        }
        if (s.equals("random")) {
            return 4;
        }
        if (s.equals("repeat")) {
            return 5;
        }
        if (s.equals("fixed")) {
            return 7;
        }
        if (s.equals("horizontal+vertical") || s.equals("h+v")) {
            return 8;
        }
        if (!s.equals("vertical+horizontal") && !s.equals("v+h")) {
            Config.warn("Unknown method: " + s);
            return 0;
        }
        return 9;
    }
    
    private static BiomeGenBase findBiome(String lowerCase) {
        lowerCase = lowerCase.toLowerCase();
        final BiomeGenBase[] biomeGenArray = BiomeGenBase.getBiomeGenArray();
        while (0 < biomeGenArray.length) {
            final BiomeGenBase biomeGenBase = biomeGenArray[0];
            if (biomeGenBase != null && biomeGenBase.biomeName.replace(" ", "").toLowerCase().equals(lowerCase)) {
                return biomeGenBase;
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    private boolean isValidHorizontalVertical(final String s) {
        if (this.tiles == null) {
            Config.warn("No tiles defined for horizontal+vertical: " + s);
            return false;
        }
        if (this.tiles.length != 7) {
            Config.warn("Invalid tiles, must be exactly 7: " + s);
            return false;
        }
        return true;
    }
}
