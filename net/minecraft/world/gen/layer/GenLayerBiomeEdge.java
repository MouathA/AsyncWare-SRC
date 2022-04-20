package net.minecraft.world.gen.layer;

public class GenLayerBiomeEdge extends GenLayer
{
    @Override
    public int[] getInts(final int p0, final int p1, final int p2, final int p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/world/gen/layer/GenLayerBiomeEdge.parent:Lnet/minecraft/world/gen/layer/GenLayer;
        //     4: iload_1        
        //     5: iconst_1       
        //     6: isub           
        //     7: iload_2        
        //     8: iconst_1       
        //     9: isub           
        //    10: iload_3        
        //    11: iconst_2       
        //    12: iadd           
        //    13: iload           4
        //    15: iconst_2       
        //    16: iadd           
        //    17: invokevirtual   net/minecraft/world/gen/layer/GenLayer.getInts:(IIII)[I
        //    20: astore          5
        //    22: iload_3        
        //    23: iload           4
        //    25: imul           
        //    26: invokestatic    net/minecraft/world/gen/layer/IntCache.getIntCache:(I)[I
        //    29: astore          6
        //    31: iconst_0       
        //    32: iload           4
        //    34: if_icmpge       603
        //    37: iconst_0       
        //    38: iload_3        
        //    39: if_icmpge       597
        //    42: aload_0        
        //    43: iconst_0       
        //    44: iload_1        
        //    45: iadd           
        //    46: i2l            
        //    47: iconst_0       
        //    48: iload_2        
        //    49: iadd           
        //    50: i2l            
        //    51: invokevirtual   net/minecraft/world/gen/layer/GenLayerBiomeEdge.initChunkSeed:(JJ)V
        //    54: aload           5
        //    56: iconst_1       
        //    57: iconst_1       
        //    58: iload_3        
        //    59: iconst_2       
        //    60: iadd           
        //    61: imul           
        //    62: iadd           
        //    63: iaload         
        //    64: istore          9
        //    66: aload_0        
        //    67: aload           5
        //    69: aload           6
        //    71: iconst_0       
        //    72: iconst_0       
        //    73: iload_3        
        //    74: iload           9
        //    76: getstatic       net/minecraft/world/biome/BiomeGenBase.extremeHills:Lnet/minecraft/world/biome/BiomeGenBase;
        //    79: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //    82: getstatic       net/minecraft/world/biome/BiomeGenBase.extremeHillsEdge:Lnet/minecraft/world/biome/BiomeGenBase;
        //    85: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //    88: ifne            591
        //    91: aload_0        
        //    92: aload           5
        //    94: aload           6
        //    96: iconst_0       
        //    97: iconst_0       
        //    98: iload_3        
        //    99: iload           9
        //   101: getstatic       net/minecraft/world/biome/BiomeGenBase.mesaPlateau_F:Lnet/minecraft/world/biome/BiomeGenBase;
        //   104: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   107: getstatic       net/minecraft/world/biome/BiomeGenBase.mesa:Lnet/minecraft/world/biome/BiomeGenBase;
        //   110: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   113: if_icmpeq       591
        //   116: aload_0        
        //   117: aload           5
        //   119: aload           6
        //   121: iconst_0       
        //   122: iconst_0       
        //   123: iload_3        
        //   124: iload           9
        //   126: getstatic       net/minecraft/world/biome/BiomeGenBase.mesaPlateau:Lnet/minecraft/world/biome/BiomeGenBase;
        //   129: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   132: getstatic       net/minecraft/world/biome/BiomeGenBase.mesa:Lnet/minecraft/world/biome/BiomeGenBase;
        //   135: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   138: if_icmpeq       591
        //   141: aload_0        
        //   142: aload           5
        //   144: aload           6
        //   146: iconst_0       
        //   147: iconst_0       
        //   148: iload_3        
        //   149: iload           9
        //   151: getstatic       net/minecraft/world/biome/BiomeGenBase.megaTaiga:Lnet/minecraft/world/biome/BiomeGenBase;
        //   154: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   157: getstatic       net/minecraft/world/biome/BiomeGenBase.taiga:Lnet/minecraft/world/biome/BiomeGenBase;
        //   160: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   163: if_icmpeq       591
        //   166: iload           9
        //   168: getstatic       net/minecraft/world/biome/BiomeGenBase.desert:Lnet/minecraft/world/biome/BiomeGenBase;
        //   171: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   174: if_icmpne       299
        //   177: aload           5
        //   179: iconst_1       
        //   180: iconst_0       
        //   181: iload_3        
        //   182: iconst_2       
        //   183: iadd           
        //   184: imul           
        //   185: iadd           
        //   186: iaload         
        //   187: istore          10
        //   189: aload           5
        //   191: iconst_2       
        //   192: iconst_1       
        //   193: iload_3        
        //   194: iconst_2       
        //   195: iadd           
        //   196: imul           
        //   197: iadd           
        //   198: iaload         
        //   199: istore          11
        //   201: aload           5
        //   203: iconst_0       
        //   204: iconst_1       
        //   205: iload_3        
        //   206: iconst_2       
        //   207: iadd           
        //   208: imul           
        //   209: iadd           
        //   210: iaload         
        //   211: istore          12
        //   213: aload           5
        //   215: iconst_1       
        //   216: iconst_2       
        //   217: iload_3        
        //   218: iconst_2       
        //   219: iadd           
        //   220: imul           
        //   221: iadd           
        //   222: iaload         
        //   223: istore          13
        //   225: iload           10
        //   227: getstatic       net/minecraft/world/biome/BiomeGenBase.icePlains:Lnet/minecraft/world/biome/BiomeGenBase;
        //   230: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   233: if_icmpeq       282
        //   236: iload           11
        //   238: getstatic       net/minecraft/world/biome/BiomeGenBase.icePlains:Lnet/minecraft/world/biome/BiomeGenBase;
        //   241: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   244: if_icmpeq       282
        //   247: iload           12
        //   249: getstatic       net/minecraft/world/biome/BiomeGenBase.icePlains:Lnet/minecraft/world/biome/BiomeGenBase;
        //   252: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   255: if_icmpeq       282
        //   258: iload           13
        //   260: getstatic       net/minecraft/world/biome/BiomeGenBase.icePlains:Lnet/minecraft/world/biome/BiomeGenBase;
        //   263: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   266: if_icmpeq       282
        //   269: aload           6
        //   271: iconst_0       
        //   272: iconst_0       
        //   273: iload_3        
        //   274: imul           
        //   275: iadd           
        //   276: iload           9
        //   278: iastore        
        //   279: goto            591
        //   282: aload           6
        //   284: iconst_0       
        //   285: iconst_0       
        //   286: iload_3        
        //   287: imul           
        //   288: iadd           
        //   289: getstatic       net/minecraft/world/biome/BiomeGenBase.extremeHillsPlus:Lnet/minecraft/world/biome/BiomeGenBase;
        //   292: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   295: iastore        
        //   296: goto            591
        //   299: iload           9
        //   301: getstatic       net/minecraft/world/biome/BiomeGenBase.swampland:Lnet/minecraft/world/biome/BiomeGenBase;
        //   304: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   307: if_icmpne       581
        //   310: aload           5
        //   312: iconst_1       
        //   313: iconst_0       
        //   314: iload_3        
        //   315: iconst_2       
        //   316: iadd           
        //   317: imul           
        //   318: iadd           
        //   319: iaload         
        //   320: istore          10
        //   322: aload           5
        //   324: iconst_2       
        //   325: iconst_1       
        //   326: iload_3        
        //   327: iconst_2       
        //   328: iadd           
        //   329: imul           
        //   330: iadd           
        //   331: iaload         
        //   332: istore          11
        //   334: aload           5
        //   336: iconst_0       
        //   337: iconst_1       
        //   338: iload_3        
        //   339: iconst_2       
        //   340: iadd           
        //   341: imul           
        //   342: iadd           
        //   343: iaload         
        //   344: istore          12
        //   346: aload           5
        //   348: iconst_1       
        //   349: iconst_2       
        //   350: iload_3        
        //   351: iconst_2       
        //   352: iadd           
        //   353: imul           
        //   354: iadd           
        //   355: iaload         
        //   356: istore          13
        //   358: iload           10
        //   360: getstatic       net/minecraft/world/biome/BiomeGenBase.desert:Lnet/minecraft/world/biome/BiomeGenBase;
        //   363: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   366: if_icmpeq       564
        //   369: iload           11
        //   371: getstatic       net/minecraft/world/biome/BiomeGenBase.desert:Lnet/minecraft/world/biome/BiomeGenBase;
        //   374: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   377: if_icmpeq       564
        //   380: iload           12
        //   382: getstatic       net/minecraft/world/biome/BiomeGenBase.desert:Lnet/minecraft/world/biome/BiomeGenBase;
        //   385: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   388: if_icmpeq       564
        //   391: iload           13
        //   393: getstatic       net/minecraft/world/biome/BiomeGenBase.desert:Lnet/minecraft/world/biome/BiomeGenBase;
        //   396: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   399: if_icmpeq       564
        //   402: iload           10
        //   404: getstatic       net/minecraft/world/biome/BiomeGenBase.coldTaiga:Lnet/minecraft/world/biome/BiomeGenBase;
        //   407: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   410: if_icmpeq       564
        //   413: iload           11
        //   415: getstatic       net/minecraft/world/biome/BiomeGenBase.coldTaiga:Lnet/minecraft/world/biome/BiomeGenBase;
        //   418: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   421: if_icmpeq       564
        //   424: iload           12
        //   426: getstatic       net/minecraft/world/biome/BiomeGenBase.coldTaiga:Lnet/minecraft/world/biome/BiomeGenBase;
        //   429: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   432: if_icmpeq       564
        //   435: iload           13
        //   437: getstatic       net/minecraft/world/biome/BiomeGenBase.coldTaiga:Lnet/minecraft/world/biome/BiomeGenBase;
        //   440: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   443: if_icmpeq       564
        //   446: iload           10
        //   448: getstatic       net/minecraft/world/biome/BiomeGenBase.icePlains:Lnet/minecraft/world/biome/BiomeGenBase;
        //   451: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   454: if_icmpeq       564
        //   457: iload           11
        //   459: getstatic       net/minecraft/world/biome/BiomeGenBase.icePlains:Lnet/minecraft/world/biome/BiomeGenBase;
        //   462: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   465: if_icmpeq       564
        //   468: iload           12
        //   470: getstatic       net/minecraft/world/biome/BiomeGenBase.icePlains:Lnet/minecraft/world/biome/BiomeGenBase;
        //   473: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   476: if_icmpeq       564
        //   479: iload           13
        //   481: getstatic       net/minecraft/world/biome/BiomeGenBase.icePlains:Lnet/minecraft/world/biome/BiomeGenBase;
        //   484: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   487: if_icmpeq       564
        //   490: iload           10
        //   492: getstatic       net/minecraft/world/biome/BiomeGenBase.jungle:Lnet/minecraft/world/biome/BiomeGenBase;
        //   495: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   498: if_icmpeq       547
        //   501: iload           13
        //   503: getstatic       net/minecraft/world/biome/BiomeGenBase.jungle:Lnet/minecraft/world/biome/BiomeGenBase;
        //   506: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   509: if_icmpeq       547
        //   512: iload           11
        //   514: getstatic       net/minecraft/world/biome/BiomeGenBase.jungle:Lnet/minecraft/world/biome/BiomeGenBase;
        //   517: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   520: if_icmpeq       547
        //   523: iload           12
        //   525: getstatic       net/minecraft/world/biome/BiomeGenBase.jungle:Lnet/minecraft/world/biome/BiomeGenBase;
        //   528: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   531: if_icmpeq       547
        //   534: aload           6
        //   536: iconst_0       
        //   537: iconst_0       
        //   538: iload_3        
        //   539: imul           
        //   540: iadd           
        //   541: iload           9
        //   543: iastore        
        //   544: goto            591
        //   547: aload           6
        //   549: iconst_0       
        //   550: iconst_0       
        //   551: iload_3        
        //   552: imul           
        //   553: iadd           
        //   554: getstatic       net/minecraft/world/biome/BiomeGenBase.jungleEdge:Lnet/minecraft/world/biome/BiomeGenBase;
        //   557: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   560: iastore        
        //   561: goto            591
        //   564: aload           6
        //   566: iconst_0       
        //   567: iconst_0       
        //   568: iload_3        
        //   569: imul           
        //   570: iadd           
        //   571: getstatic       net/minecraft/world/biome/BiomeGenBase.plains:Lnet/minecraft/world/biome/BiomeGenBase;
        //   574: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   577: iastore        
        //   578: goto            591
        //   581: aload           6
        //   583: iconst_0       
        //   584: iconst_0       
        //   585: iload_3        
        //   586: imul           
        //   587: iadd           
        //   588: iload           9
        //   590: iastore        
        //   591: iinc            8, 1
        //   594: goto            37
        //   597: iinc            7, 1
        //   600: goto            31
        //   603: aload           6
        //   605: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0037 (coming from #0594).
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
    
    public GenLayerBiomeEdge(final long n, final GenLayer parent) {
        super(n);
        this.parent = parent;
    }
}
