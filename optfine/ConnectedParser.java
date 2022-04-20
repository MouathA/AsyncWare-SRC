package optfine;

import net.minecraft.block.*;
import net.minecraft.world.biome.*;
import net.minecraft.block.properties.*;
import java.util.*;
import net.minecraft.util.*;

public class ConnectedParser
{
    private String context;
    
    public MatchBlock[] parseMatchBlock(String trim) {
        if (trim == null) {
            return null;
        }
        trim = trim.trim();
        if (trim.length() <= 0) {
            return null;
        }
        final String[] tokenize = Config.tokenize(trim, ":");
        String s;
        if (tokenize.length > 1 && this < tokenize) {
            s = tokenize[0];
        }
        else {
            s = "minecraft";
        }
        final String s2 = tokenize[0];
        final String[] array = Arrays.copyOfRange(tokenize, 1, tokenize.length);
        final Block[] blockPart = this.parseBlockPart(s, s2);
        final MatchBlock[] array2 = new MatchBlock[blockPart.length];
        while (0 < blockPart.length) {
            final Block block = blockPart[0];
            array2[0] = new MatchBlock(Block.getIdFromBlock(block), this.parseBlockMetadatas(block, array));
            int n = 0;
            ++n;
        }
        return array2;
    }
    
    public ConnectedParser(final String context) {
        this.context = null;
        this.context = context;
    }
    
    public int parseInt(final String s) {
        if (s == null) {
            return -1;
        }
        final int int1 = Config.parseInt(s, -1);
        if (int1 < 0) {
            this.warn("Invalid number: " + s);
        }
        return int1;
    }
    
    public Block[] parseBlockPart(final String s, final String s2) {
        if (s2 == null) {
            final int[] intList = this.parseIntList(s2);
            if (intList == null) {
                return null;
            }
            final Block[] array = new Block[intList.length];
            while (0 < intList.length) {
                final int n = intList[0];
                final Block blockById = Block.getBlockById(n);
                if (blockById == null) {
                    this.warn("Block not found for id: " + n);
                    return null;
                }
                array[0] = blockById;
                int n2 = 0;
                ++n2;
            }
            return array;
        }
        else {
            final String string = s + ":" + s2;
            final Block blockFromName = Block.getBlockFromName(string);
            if (blockFromName == null) {
                this.warn("Block not found for name: " + string);
                return null;
            }
            return new Block[] { blockFromName };
        }
    }
    
    public boolean[] parseFaces(final String p0, final boolean[] p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnonnull       6
        //     4: aload_2        
        //     5: areturn        
        //     6: ldc             Lnet/minecraft/util/EnumFacing;.class
        //     8: invokestatic    java/util/EnumSet.allOf:(Ljava/lang/Class;)Ljava/util/EnumSet;
        //    11: astore_3       
        //    12: aload_1        
        //    13: ldc             " ,"
        //    15: invokestatic    optfine/Config.tokenize:(Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;
        //    18: astore          4
        //    20: iconst_0       
        //    21: aload           4
        //    23: arraylength    
        //    24: if_icmpge       128
        //    27: aload           4
        //    29: iconst_0       
        //    30: aaload         
        //    31: astore          6
        //    33: aload           6
        //    35: ldc             "sides"
        //    37: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    40: ifeq            78
        //    43: aload_3        
        //    44: getstatic       net/minecraft/util/EnumFacing.NORTH:Lnet/minecraft/util/EnumFacing;
        //    47: invokevirtual   java/util/EnumSet.add:(Ljava/lang/Object;)Z
        //    50: pop            
        //    51: aload_3        
        //    52: getstatic       net/minecraft/util/EnumFacing.SOUTH:Lnet/minecraft/util/EnumFacing;
        //    55: invokevirtual   java/util/EnumSet.add:(Ljava/lang/Object;)Z
        //    58: pop            
        //    59: aload_3        
        //    60: getstatic       net/minecraft/util/EnumFacing.WEST:Lnet/minecraft/util/EnumFacing;
        //    63: invokevirtual   java/util/EnumSet.add:(Ljava/lang/Object;)Z
        //    66: pop            
        //    67: aload_3        
        //    68: getstatic       net/minecraft/util/EnumFacing.EAST:Lnet/minecraft/util/EnumFacing;
        //    71: invokevirtual   java/util/EnumSet.add:(Ljava/lang/Object;)Z
        //    74: pop            
        //    75: goto            122
        //    78: aload           6
        //    80: ldc             "all"
        //    82: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    85: ifeq            102
        //    88: aload_3        
        //    89: getstatic       net/minecraft/util/EnumFacing.VALUES:[Lnet/minecraft/util/EnumFacing;
        //    92: invokestatic    java/util/Arrays.asList:([Ljava/lang/Object;)Ljava/util/List;
        //    95: invokevirtual   java/util/EnumSet.addAll:(Ljava/util/Collection;)Z
        //    98: pop            
        //    99: goto            122
        //   102: aload_0        
        //   103: aload           6
        //   105: invokevirtual   optfine/ConnectedParser.parseFace:(Ljava/lang/String;)Lnet/minecraft/util/EnumFacing;
        //   108: astore          7
        //   110: aload           7
        //   112: ifnull          122
        //   115: aload_3        
        //   116: aload           7
        //   118: invokevirtual   java/util/EnumSet.add:(Ljava/lang/Object;)Z
        //   121: pop            
        //   122: iinc            5, 1
        //   125: goto            20
        //   128: getstatic       net/minecraft/util/EnumFacing.VALUES:[Lnet/minecraft/util/EnumFacing;
        //   131: arraylength    
        //   132: newarray        Z
        //   134: astore          5
        //   136: iconst_0       
        //   137: aload           5
        //   139: arraylength    
        //   140: if_icmpge       162
        //   143: aload           5
        //   145: iconst_0       
        //   146: aload_3        
        //   147: getstatic       net/minecraft/util/EnumFacing.VALUES:[Lnet/minecraft/util/EnumFacing;
        //   150: iconst_0       
        //   151: aaload         
        //   152: invokevirtual   java/util/EnumSet.contains:(Ljava/lang/Object;)Z
        //   155: bastore        
        //   156: iinc            6, 1
        //   159: goto            136
        //   162: aload           5
        //   164: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public RangeListInt parseRangeListInt(final String s) {
        if (s == null) {
            return null;
        }
        final RangeListInt rangeListInt = new RangeListInt();
        final String[] tokenize = Config.tokenize(s, " ,");
        while (0 < tokenize.length) {
            final RangeInt rangeInt = this.parseRangeInt(tokenize[0]);
            if (rangeInt == null) {
                return null;
            }
            rangeListInt.addRange(rangeInt);
            int n = 0;
            ++n;
        }
        return rangeListInt;
    }
    
    public String parseName(final String s) {
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
    
    public int parseInt(final String s, final int n) {
        if (s == null) {
            return n;
        }
        final int int1 = Config.parseInt(s, -1);
        if (int1 < 0) {
            this.warn("Invalid number: " + s);
            return n;
        }
        return int1;
    }
    
    public BiomeGenBase[] parseBiomes(final String s) {
        if (s == null) {
            return null;
        }
        final String[] tokenize = Config.tokenize(s, " ");
        final ArrayList<BiomeGenBase> list = new ArrayList<BiomeGenBase>();
        while (0 < tokenize.length) {
            final String s2 = tokenize[0];
            final BiomeGenBase biome = this.findBiome(s2);
            if (biome == null) {
                this.warn("Biome not found: " + s2);
            }
            else {
                list.add(biome);
            }
            int n = 0;
            ++n;
        }
        return list.toArray(new BiomeGenBase[list.size()]);
    }
    
    public int[] parseBlockMetadatas(final Block p0, final String[] p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: arraylength    
        //     2: ifgt            7
        //     5: aconst_null    
        //     6: areturn        
        //     7: aload_2        
        //     8: iconst_0       
        //     9: aaload         
        //    10: astore_3       
        //    11: aload_0        
        //    12: aload_3        
        //    13: ifnonnull       26
        //    16: aload_0        
        //    17: aload_3        
        //    18: invokevirtual   optfine/ConnectedParser.parseIntList:(Ljava/lang/String;)[I
        //    21: astore          4
        //    23: aload           4
        //    25: areturn        
        //    26: aload_1        
        //    27: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //    30: astore          4
        //    32: aload           4
        //    34: invokeinterface net/minecraft/block/state/IBlockState.getPropertyNames:()Ljava/util/Collection;
        //    39: astore          5
        //    41: new             Ljava/util/HashMap;
        //    44: dup            
        //    45: invokespecial   java/util/HashMap.<init>:()V
        //    48: astore          6
        //    50: iconst_0       
        //    51: aload_2        
        //    52: arraylength    
        //    53: if_icmpge       317
        //    56: aload_2        
        //    57: iconst_0       
        //    58: aaload         
        //    59: astore          8
        //    61: aload           8
        //    63: invokevirtual   java/lang/String.length:()I
        //    66: ifle            311
        //    69: aload           8
        //    71: ldc             "="
        //    73: invokestatic    optfine/Config.tokenize:(Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;
        //    76: astore          9
        //    78: aload           9
        //    80: arraylength    
        //    81: iconst_2       
        //    82: if_icmpeq       111
        //    85: aload_0        
        //    86: new             Ljava/lang/StringBuilder;
        //    89: dup            
        //    90: invokespecial   java/lang/StringBuilder.<init>:()V
        //    93: ldc             "Invalid block property: "
        //    95: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    98: aload           8
        //   100: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   103: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   106: invokevirtual   optfine/ConnectedParser.warn:(Ljava/lang/String;)V
        //   109: aconst_null    
        //   110: areturn        
        //   111: aload           9
        //   113: iconst_0       
        //   114: aaload         
        //   115: astore          10
        //   117: aload           9
        //   119: iconst_1       
        //   120: aaload         
        //   121: astore          11
        //   123: aload           10
        //   125: aload           5
        //   127: invokestatic    optfine/ConnectedProperties.getProperty:(Ljava/lang/String;Ljava/util/Collection;)Lnet/minecraft/block/properties/IProperty;
        //   130: astore          12
        //   132: aload           12
        //   134: ifnonnull       172
        //   137: aload_0        
        //   138: new             Ljava/lang/StringBuilder;
        //   141: dup            
        //   142: invokespecial   java/lang/StringBuilder.<init>:()V
        //   145: ldc             "Property not found: "
        //   147: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   150: aload           10
        //   152: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   155: ldc             ", block: "
        //   157: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   160: aload_1        
        //   161: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   164: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   167: invokevirtual   optfine/ConnectedParser.warn:(Ljava/lang/String;)V
        //   170: aconst_null    
        //   171: areturn        
        //   172: aload           6
        //   174: aload           10
        //   176: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //   181: checkcast       Ljava/util/List;
        //   184: astore          13
        //   186: aload           13
        //   188: ifnonnull       212
        //   191: new             Ljava/util/ArrayList;
        //   194: dup            
        //   195: invokespecial   java/util/ArrayList.<init>:()V
        //   198: astore          13
        //   200: aload           6
        //   202: aload           12
        //   204: aload           13
        //   206: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   211: pop            
        //   212: aload           11
        //   214: ldc             ","
        //   216: invokestatic    optfine/Config.tokenize:(Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;
        //   219: astore          14
        //   221: iconst_0       
        //   222: aload           14
        //   224: arraylength    
        //   225: if_icmpge       311
        //   228: aload           14
        //   230: iconst_0       
        //   231: aaload         
        //   232: astore          16
        //   234: aload           12
        //   236: aload           16
        //   238: invokestatic    optfine/ConnectedParser.parsePropertyValue:(Lnet/minecraft/block/properties/IProperty;Ljava/lang/String;)Ljava/lang/Comparable;
        //   241: astore          17
        //   243: aload           17
        //   245: ifnonnull       295
        //   248: aload_0        
        //   249: new             Ljava/lang/StringBuilder;
        //   252: dup            
        //   253: invokespecial   java/lang/StringBuilder.<init>:()V
        //   256: ldc_w           "Property value not found: "
        //   259: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   262: aload           16
        //   264: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   267: ldc_w           ", property: "
        //   270: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   273: aload           10
        //   275: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   278: ldc             ", block: "
        //   280: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   283: aload_1        
        //   284: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   287: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   290: invokevirtual   optfine/ConnectedParser.warn:(Ljava/lang/String;)V
        //   293: aconst_null    
        //   294: areturn        
        //   295: aload           13
        //   297: aload           17
        //   299: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   304: pop            
        //   305: iinc            15, 1
        //   308: goto            221
        //   311: iinc            7, 1
        //   314: goto            50
        //   317: aload           6
        //   319: invokeinterface java/util/Map.isEmpty:()Z
        //   324: ifeq            329
        //   327: aconst_null    
        //   328: areturn        
        //   329: new             Ljava/util/ArrayList;
        //   332: dup            
        //   333: invokespecial   java/util/ArrayList.<init>:()V
        //   336: astore          7
        //   338: aload_1        
        //   339: iconst_0       
        //   340: invokevirtual   net/minecraft/block/Block.getStateFromMeta:(I)Lnet/minecraft/block/state/IBlockState;
        //   343: astore          9
        //   345: aload_0        
        //   346: aload           9
        //   348: aload           6
        //   350: ifeq            365
        //   353: aload           7
        //   355: iconst_0       
        //   356: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   359: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   364: pop            
        //   365: iinc            8, 1
        //   368: goto            338
        //   371: aload           7
        //   373: invokeinterface java/util/List.size:()I
        //   378: bipush          16
        //   380: if_icmpne       385
        //   383: aconst_null    
        //   384: areturn        
        //   385: aload           7
        //   387: invokeinterface java/util/List.size:()I
        //   392: newarray        I
        //   394: astore          8
        //   396: iconst_0       
        //   397: aload           8
        //   399: arraylength    
        //   400: if_icmpge       427
        //   403: aload           8
        //   405: iconst_0       
        //   406: aload           7
        //   408: iconst_0       
        //   409: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   414: checkcast       Ljava/lang/Integer;
        //   417: invokevirtual   java/lang/Integer.intValue:()I
        //   420: iastore        
        //   421: iinc            9, 1
        //   424: goto            396
        //   427: aload           8
        //   429: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0338 (coming from #0368).
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
    
    public void dbg(final String s) {
        Config.dbg("" + this.context + ": " + s);
    }
    
    public static Comparable parsePropertyValue(final IProperty property, final String s) {
        Comparable comparable = parseValue(s, property.getValueClass());
        if (comparable == null) {
            comparable = getPropertyValue(s, property.getAllowedValues());
        }
        return comparable;
    }
    
    public void warn(final String s) {
        Config.warn("" + this.context + ": " + s);
    }
    
    public BiomeGenBase findBiome(String lowerCase) {
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
    
    public int[] parseIntList(final String p0) {
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
        //    15: ldc             " ,"
        //    17: invokestatic    optfine/Config.tokenize:(Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;
        //    20: astore_3       
        //    21: iconst_0       
        //    22: aload_3        
        //    23: arraylength    
        //    24: if_icmpge       273
        //    27: aload_3        
        //    28: iconst_0       
        //    29: aaload         
        //    30: astore          5
        //    32: aload           5
        //    34: ldc_w           "-"
        //    37: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //    40: ifeq            205
        //    43: aload           5
        //    45: ldc_w           "-"
        //    48: invokestatic    optfine/Config.tokenize:(Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;
        //    51: astore          6
        //    53: aload           6
        //    55: arraylength    
        //    56: iconst_2       
        //    57: if_icmpeq       98
        //    60: aload_0        
        //    61: new             Ljava/lang/StringBuilder;
        //    64: dup            
        //    65: invokespecial   java/lang/StringBuilder.<init>:()V
        //    68: ldc_w           "Invalid interval: "
        //    71: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    74: aload           5
        //    76: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    79: ldc_w           ", when parsing: "
        //    82: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    85: aload_1        
        //    86: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    89: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    92: invokevirtual   optfine/ConnectedParser.warn:(Ljava/lang/String;)V
        //    95: goto            267
        //    98: aload           6
        //   100: iconst_0       
        //   101: aaload         
        //   102: iconst_m1      
        //   103: invokestatic    optfine/Config.parseInt:(Ljava/lang/String;I)I
        //   106: istore          7
        //   108: aload           6
        //   110: iconst_1       
        //   111: aaload         
        //   112: iconst_m1      
        //   113: invokestatic    optfine/Config.parseInt:(Ljava/lang/String;I)I
        //   116: istore          8
        //   118: iload           7
        //   120: iflt            167
        //   123: iload           8
        //   125: iflt            167
        //   128: iload           7
        //   130: iload           8
        //   132: if_icmpgt       167
        //   135: iload           7
        //   137: istore          9
        //   139: iload           9
        //   141: iload           8
        //   143: if_icmpgt       164
        //   146: aload_2        
        //   147: iload           9
        //   149: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   152: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   157: pop            
        //   158: iinc            9, 1
        //   161: goto            139
        //   164: goto            267
        //   167: aload_0        
        //   168: new             Ljava/lang/StringBuilder;
        //   171: dup            
        //   172: invokespecial   java/lang/StringBuilder.<init>:()V
        //   175: ldc_w           "Invalid interval: "
        //   178: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   181: aload           5
        //   183: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   186: ldc_w           ", when parsing: "
        //   189: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   192: aload_1        
        //   193: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   196: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   199: invokevirtual   optfine/ConnectedParser.warn:(Ljava/lang/String;)V
        //   202: goto            267
        //   205: aload           5
        //   207: iconst_m1      
        //   208: invokestatic    optfine/Config.parseInt:(Ljava/lang/String;I)I
        //   211: istore          6
        //   213: iload           6
        //   215: ifge            255
        //   218: aload_0        
        //   219: new             Ljava/lang/StringBuilder;
        //   222: dup            
        //   223: invokespecial   java/lang/StringBuilder.<init>:()V
        //   226: ldc             "Invalid number: "
        //   228: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   231: aload           5
        //   233: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   236: ldc_w           ", when parsing: "
        //   239: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   242: aload_1        
        //   243: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   246: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   249: invokevirtual   optfine/ConnectedParser.warn:(Ljava/lang/String;)V
        //   252: goto            267
        //   255: aload_2        
        //   256: iload           6
        //   258: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   261: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   266: pop            
        //   267: iinc            4, 1
        //   270: goto            21
        //   273: aload_2        
        //   274: invokeinterface java/util/List.size:()I
        //   279: newarray        I
        //   281: astore          4
        //   283: iconst_0       
        //   284: aload           4
        //   286: arraylength    
        //   287: if_icmpge       313
        //   290: aload           4
        //   292: iconst_0       
        //   293: aload_2        
        //   294: iconst_0       
        //   295: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   300: checkcast       Ljava/lang/Integer;
        //   303: invokevirtual   java/lang/Integer.intValue:()I
        //   306: iastore        
        //   307: iinc            5, 1
        //   310: goto            283
        //   313: aload           4
        //   315: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public String parseBasePath(final String s) {
        final int lastIndex = s.lastIndexOf(47);
        return (lastIndex < 0) ? "" : s.substring(0, lastIndex);
    }
    
    public MatchBlock[] parseMatchBlocks(final String s) {
        if (s == null) {
            return null;
        }
        final ArrayList list = new ArrayList();
        final String[] tokenize = Config.tokenize(s, " ");
        while (0 < tokenize.length) {
            final MatchBlock[] matchBlock = this.parseMatchBlock(tokenize[0]);
            if (matchBlock == null) {
                return null;
            }
            list.addAll(Arrays.asList(matchBlock));
            int n = 0;
            ++n;
        }
        return (MatchBlock[])list.toArray(new MatchBlock[list.size()]);
    }
    
    public static Comparable getPropertyValue(final String s, final Collection collection) {
        for (final Comparable next : collection) {
            if (String.valueOf(next).equals(s)) {
                return next;
            }
        }
        return null;
    }
    
    public EnumFacing parseFace(String lowerCase) {
        lowerCase = lowerCase.toLowerCase();
        if (lowerCase.equals("bottom") || lowerCase.equals("down")) {
            return EnumFacing.DOWN;
        }
        if (lowerCase.equals("top") || lowerCase.equals("up")) {
            return EnumFacing.UP;
        }
        if (lowerCase.equals("north")) {
            return EnumFacing.NORTH;
        }
        if (lowerCase.equals("south")) {
            return EnumFacing.SOUTH;
        }
        if (lowerCase.equals("east")) {
            return EnumFacing.EAST;
        }
        if (lowerCase.equals("west")) {
            return EnumFacing.WEST;
        }
        Config.warn("Unknown face: " + lowerCase);
        return null;
    }
    
    public static Comparable parseValue(final String s, final Class clazz) {
        return (Comparable)((clazz == String.class) ? s : ((clazz == Boolean.class) ? Boolean.valueOf(s) : ((Double)((clazz == Float.class) ? Float.valueOf(s) : ((clazz == Double.class) ? Double.valueOf(s) : ((double)((clazz == Integer.class) ? Integer.valueOf(s) : ((long)((clazz == Long.class) ? Long.valueOf(s) : null)))))))));
    }
    
    private RangeInt parseRangeInt(final String s) {
        if (s == null) {
            return null;
        }
        if (s.indexOf(45) >= 0) {
            final String[] tokenize = Config.tokenize(s, "-");
            if (tokenize.length != 2) {
                this.warn("Invalid range: " + s);
                return null;
            }
            final int int1 = Config.parseInt(tokenize[0], -1);
            final int int2 = Config.parseInt(tokenize[1], -1);
            if (int1 >= 0 && int2 >= 0) {
                return new RangeInt(int1, int2);
            }
            this.warn("Invalid range: " + s);
            return null;
        }
        else {
            final int int3 = Config.parseInt(s, -1);
            if (int3 < 0) {
                this.warn("Invalid integer: " + s);
                return null;
            }
            return new RangeInt(int3, int3);
        }
    }
}
