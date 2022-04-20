package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.*;

public class GenLayerShore extends GenLayer
{
    public GenLayerShore(final long n, final GenLayer parent) {
        super(n);
        this.parent = parent;
    }
    
    private void func_151632_a(final int[] array, final int[] array2, final int n, final int n2, final int n3, final int n4, final int n5) {
        if (GenLayer.isBiomeOceanic(n4)) {
            array2[n + n2 * n3] = n4;
        }
        else {
            final int n6 = array[n + 1 + (n2 + 1 - 1) * (n3 + 2)];
            final int n7 = array[n + 1 + 1 + (n2 + 1) * (n3 + 2)];
            final int n8 = array[n + 1 - 1 + (n2 + 1) * (n3 + 2)];
            final int n9 = array[n + 1 + (n2 + 1 + 1) * (n3 + 2)];
            if (!GenLayer.isBiomeOceanic(n6) && !GenLayer.isBiomeOceanic(n7) && !GenLayer.isBiomeOceanic(n8) && !GenLayer.isBiomeOceanic(n9)) {
                array2[n + n2 * n3] = n4;
            }
            else {
                array2[n + n2 * n3] = n5;
            }
        }
    }
    
    private boolean func_151633_d(final int n) {
        return BiomeGenBase.getBiome(n) instanceof BiomeGenMesa;
    }
    
    @Override
    public int[] getInts(final int p0, final int p1, final int p2, final int p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/world/gen/layer/GenLayerShore.parent:Lnet/minecraft/world/gen/layer/GenLayer;
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
        //    34: if_icmpge       819
        //    37: iconst_0       
        //    38: iload_3        
        //    39: if_icmpge       813
        //    42: aload_0        
        //    43: iconst_0       
        //    44: iload_1        
        //    45: iadd           
        //    46: i2l            
        //    47: iconst_0       
        //    48: iload_2        
        //    49: iadd           
        //    50: i2l            
        //    51: invokevirtual   net/minecraft/world/gen/layer/GenLayerShore.initChunkSeed:(JJ)V
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
        //    66: iload           9
        //    68: invokestatic    net/minecraft/world/biome/BiomeGenBase.getBiome:(I)Lnet/minecraft/world/biome/BiomeGenBase;
        //    71: astore          10
        //    73: iload           9
        //    75: getstatic       net/minecraft/world/biome/BiomeGenBase.mushroomIsland:Lnet/minecraft/world/biome/BiomeGenBase;
        //    78: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //    81: if_icmpne       206
        //    84: aload           5
        //    86: iconst_1       
        //    87: iconst_0       
        //    88: iload_3        
        //    89: iconst_2       
        //    90: iadd           
        //    91: imul           
        //    92: iadd           
        //    93: iaload         
        //    94: istore          11
        //    96: aload           5
        //    98: iconst_2       
        //    99: iconst_1       
        //   100: iload_3        
        //   101: iconst_2       
        //   102: iadd           
        //   103: imul           
        //   104: iadd           
        //   105: iaload         
        //   106: istore          12
        //   108: aload           5
        //   110: iconst_0       
        //   111: iconst_1       
        //   112: iload_3        
        //   113: iconst_2       
        //   114: iadd           
        //   115: imul           
        //   116: iadd           
        //   117: iaload         
        //   118: istore          13
        //   120: aload           5
        //   122: iconst_1       
        //   123: iconst_2       
        //   124: iload_3        
        //   125: iconst_2       
        //   126: iadd           
        //   127: imul           
        //   128: iadd           
        //   129: iaload         
        //   130: istore          14
        //   132: iload           11
        //   134: getstatic       net/minecraft/world/biome/BiomeGenBase.ocean:Lnet/minecraft/world/biome/BiomeGenBase;
        //   137: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   140: if_icmpeq       189
        //   143: iload           12
        //   145: getstatic       net/minecraft/world/biome/BiomeGenBase.ocean:Lnet/minecraft/world/biome/BiomeGenBase;
        //   148: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   151: if_icmpeq       189
        //   154: iload           13
        //   156: getstatic       net/minecraft/world/biome/BiomeGenBase.ocean:Lnet/minecraft/world/biome/BiomeGenBase;
        //   159: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   162: if_icmpeq       189
        //   165: iload           14
        //   167: getstatic       net/minecraft/world/biome/BiomeGenBase.ocean:Lnet/minecraft/world/biome/BiomeGenBase;
        //   170: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   173: if_icmpeq       189
        //   176: aload           6
        //   178: iconst_0       
        //   179: iconst_0       
        //   180: iload_3        
        //   181: imul           
        //   182: iadd           
        //   183: iload           9
        //   185: iastore        
        //   186: goto            807
        //   189: aload           6
        //   191: iconst_0       
        //   192: iconst_0       
        //   193: iload_3        
        //   194: imul           
        //   195: iadd           
        //   196: getstatic       net/minecraft/world/biome/BiomeGenBase.mushroomIslandShore:Lnet/minecraft/world/biome/BiomeGenBase;
        //   199: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   202: iastore        
        //   203: goto            807
        //   206: aload           10
        //   208: ifnull          372
        //   211: aload           10
        //   213: invokevirtual   net/minecraft/world/biome/BiomeGenBase.getBiomeClass:()Ljava/lang/Class;
        //   216: ldc             Lnet/minecraft/world/biome/BiomeGenJungle;.class
        //   218: if_acmpne       372
        //   221: aload           5
        //   223: iconst_1       
        //   224: iconst_0       
        //   225: iload_3        
        //   226: iconst_2       
        //   227: iadd           
        //   228: imul           
        //   229: iadd           
        //   230: iaload         
        //   231: istore          11
        //   233: aload           5
        //   235: iconst_2       
        //   236: iconst_1       
        //   237: iload_3        
        //   238: iconst_2       
        //   239: iadd           
        //   240: imul           
        //   241: iadd           
        //   242: iaload         
        //   243: istore          12
        //   245: aload           5
        //   247: iconst_0       
        //   248: iconst_1       
        //   249: iload_3        
        //   250: iconst_2       
        //   251: iadd           
        //   252: imul           
        //   253: iadd           
        //   254: iaload         
        //   255: istore          13
        //   257: aload           5
        //   259: iconst_1       
        //   260: iconst_2       
        //   261: iload_3        
        //   262: iconst_2       
        //   263: iadd           
        //   264: imul           
        //   265: iadd           
        //   266: iaload         
        //   267: istore          14
        //   269: aload_0        
        //   270: iload           11
        //   272: ifnull          355
        //   275: aload_0        
        //   276: iload           12
        //   278: ifnull          355
        //   281: aload_0        
        //   282: iload           13
        //   284: ifnull          355
        //   287: aload_0        
        //   288: iload           14
        //   290: ifnull          355
        //   293: iload           11
        //   295: invokestatic    net/minecraft/world/gen/layer/GenLayerShore.isBiomeOceanic:(I)Z
        //   298: ifne            338
        //   301: iload           12
        //   303: invokestatic    net/minecraft/world/gen/layer/GenLayerShore.isBiomeOceanic:(I)Z
        //   306: ifne            338
        //   309: iload           13
        //   311: invokestatic    net/minecraft/world/gen/layer/GenLayerShore.isBiomeOceanic:(I)Z
        //   314: ifne            338
        //   317: iload           14
        //   319: invokestatic    net/minecraft/world/gen/layer/GenLayerShore.isBiomeOceanic:(I)Z
        //   322: ifne            338
        //   325: aload           6
        //   327: iconst_0       
        //   328: iconst_0       
        //   329: iload_3        
        //   330: imul           
        //   331: iadd           
        //   332: iload           9
        //   334: iastore        
        //   335: goto            807
        //   338: aload           6
        //   340: iconst_0       
        //   341: iconst_0       
        //   342: iload_3        
        //   343: imul           
        //   344: iadd           
        //   345: getstatic       net/minecraft/world/biome/BiomeGenBase.beach:Lnet/minecraft/world/biome/BiomeGenBase;
        //   348: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   351: iastore        
        //   352: goto            807
        //   355: aload           6
        //   357: iconst_0       
        //   358: iconst_0       
        //   359: iload_3        
        //   360: imul           
        //   361: iadd           
        //   362: getstatic       net/minecraft/world/biome/BiomeGenBase.jungleEdge:Lnet/minecraft/world/biome/BiomeGenBase;
        //   365: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   368: iastore        
        //   369: goto            807
        //   372: iload           9
        //   374: getstatic       net/minecraft/world/biome/BiomeGenBase.extremeHills:Lnet/minecraft/world/biome/BiomeGenBase;
        //   377: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   380: if_icmpeq       788
        //   383: iload           9
        //   385: getstatic       net/minecraft/world/biome/BiomeGenBase.extremeHillsPlus:Lnet/minecraft/world/biome/BiomeGenBase;
        //   388: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   391: if_icmpeq       788
        //   394: iload           9
        //   396: getstatic       net/minecraft/world/biome/BiomeGenBase.extremeHillsEdge:Lnet/minecraft/world/biome/BiomeGenBase;
        //   399: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   402: if_icmpeq       788
        //   405: aload           10
        //   407: ifnull          440
        //   410: aload           10
        //   412: invokevirtual   net/minecraft/world/biome/BiomeGenBase.isSnowyBiome:()Z
        //   415: ifeq            440
        //   418: aload_0        
        //   419: aload           5
        //   421: aload           6
        //   423: iconst_0       
        //   424: iconst_0       
        //   425: iload_3        
        //   426: iload           9
        //   428: getstatic       net/minecraft/world/biome/BiomeGenBase.coldBeach:Lnet/minecraft/world/biome/BiomeGenBase;
        //   431: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   434: invokespecial   net/minecraft/world/gen/layer/GenLayerShore.func_151632_a:([I[IIIIII)V
        //   437: goto            807
        //   440: iload           9
        //   442: getstatic       net/minecraft/world/biome/BiomeGenBase.mesa:Lnet/minecraft/world/biome/BiomeGenBase;
        //   445: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   448: if_icmpeq       629
        //   451: iload           9
        //   453: getstatic       net/minecraft/world/biome/BiomeGenBase.mesaPlateau_F:Lnet/minecraft/world/biome/BiomeGenBase;
        //   456: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   459: if_icmpeq       629
        //   462: iload           9
        //   464: getstatic       net/minecraft/world/biome/BiomeGenBase.ocean:Lnet/minecraft/world/biome/BiomeGenBase;
        //   467: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   470: if_icmpeq       616
        //   473: iload           9
        //   475: getstatic       net/minecraft/world/biome/BiomeGenBase.deepOcean:Lnet/minecraft/world/biome/BiomeGenBase;
        //   478: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   481: if_icmpeq       616
        //   484: iload           9
        //   486: getstatic       net/minecraft/world/biome/BiomeGenBase.river:Lnet/minecraft/world/biome/BiomeGenBase;
        //   489: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   492: if_icmpeq       616
        //   495: iload           9
        //   497: getstatic       net/minecraft/world/biome/BiomeGenBase.swampland:Lnet/minecraft/world/biome/BiomeGenBase;
        //   500: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   503: if_icmpeq       616
        //   506: aload           5
        //   508: iconst_1       
        //   509: iconst_0       
        //   510: iload_3        
        //   511: iconst_2       
        //   512: iadd           
        //   513: imul           
        //   514: iadd           
        //   515: iaload         
        //   516: istore          11
        //   518: aload           5
        //   520: iconst_2       
        //   521: iconst_1       
        //   522: iload_3        
        //   523: iconst_2       
        //   524: iadd           
        //   525: imul           
        //   526: iadd           
        //   527: iaload         
        //   528: istore          12
        //   530: aload           5
        //   532: iconst_0       
        //   533: iconst_1       
        //   534: iload_3        
        //   535: iconst_2       
        //   536: iadd           
        //   537: imul           
        //   538: iadd           
        //   539: iaload         
        //   540: istore          13
        //   542: aload           5
        //   544: iconst_1       
        //   545: iconst_2       
        //   546: iload_3        
        //   547: iconst_2       
        //   548: iadd           
        //   549: imul           
        //   550: iadd           
        //   551: iaload         
        //   552: istore          14
        //   554: iload           11
        //   556: invokestatic    net/minecraft/world/gen/layer/GenLayerShore.isBiomeOceanic:(I)Z
        //   559: ifne            599
        //   562: iload           12
        //   564: invokestatic    net/minecraft/world/gen/layer/GenLayerShore.isBiomeOceanic:(I)Z
        //   567: ifne            599
        //   570: iload           13
        //   572: invokestatic    net/minecraft/world/gen/layer/GenLayerShore.isBiomeOceanic:(I)Z
        //   575: ifne            599
        //   578: iload           14
        //   580: invokestatic    net/minecraft/world/gen/layer/GenLayerShore.isBiomeOceanic:(I)Z
        //   583: ifne            599
        //   586: aload           6
        //   588: iconst_0       
        //   589: iconst_0       
        //   590: iload_3        
        //   591: imul           
        //   592: iadd           
        //   593: iload           9
        //   595: iastore        
        //   596: goto            807
        //   599: aload           6
        //   601: iconst_0       
        //   602: iconst_0       
        //   603: iload_3        
        //   604: imul           
        //   605: iadd           
        //   606: getstatic       net/minecraft/world/biome/BiomeGenBase.beach:Lnet/minecraft/world/biome/BiomeGenBase;
        //   609: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   612: iastore        
        //   613: goto            807
        //   616: aload           6
        //   618: iconst_0       
        //   619: iconst_0       
        //   620: iload_3        
        //   621: imul           
        //   622: iadd           
        //   623: iload           9
        //   625: iastore        
        //   626: goto            807
        //   629: aload           5
        //   631: iconst_1       
        //   632: iconst_0       
        //   633: iload_3        
        //   634: iconst_2       
        //   635: iadd           
        //   636: imul           
        //   637: iadd           
        //   638: iaload         
        //   639: istore          11
        //   641: aload           5
        //   643: iconst_2       
        //   644: iconst_1       
        //   645: iload_3        
        //   646: iconst_2       
        //   647: iadd           
        //   648: imul           
        //   649: iadd           
        //   650: iaload         
        //   651: istore          12
        //   653: aload           5
        //   655: iconst_0       
        //   656: iconst_1       
        //   657: iload_3        
        //   658: iconst_2       
        //   659: iadd           
        //   660: imul           
        //   661: iadd           
        //   662: iaload         
        //   663: istore          13
        //   665: aload           5
        //   667: iconst_1       
        //   668: iconst_2       
        //   669: iload_3        
        //   670: iconst_2       
        //   671: iadd           
        //   672: imul           
        //   673: iadd           
        //   674: iaload         
        //   675: istore          14
        //   677: iload           11
        //   679: invokestatic    net/minecraft/world/gen/layer/GenLayerShore.isBiomeOceanic:(I)Z
        //   682: ifne            775
        //   685: iload           12
        //   687: invokestatic    net/minecraft/world/gen/layer/GenLayerShore.isBiomeOceanic:(I)Z
        //   690: ifne            775
        //   693: iload           13
        //   695: invokestatic    net/minecraft/world/gen/layer/GenLayerShore.isBiomeOceanic:(I)Z
        //   698: ifne            775
        //   701: iload           14
        //   703: invokestatic    net/minecraft/world/gen/layer/GenLayerShore.isBiomeOceanic:(I)Z
        //   706: ifne            775
        //   709: aload_0        
        //   710: iload           11
        //   712: invokespecial   net/minecraft/world/gen/layer/GenLayerShore.func_151633_d:(I)Z
        //   715: ifeq            758
        //   718: aload_0        
        //   719: iload           12
        //   721: invokespecial   net/minecraft/world/gen/layer/GenLayerShore.func_151633_d:(I)Z
        //   724: ifeq            758
        //   727: aload_0        
        //   728: iload           13
        //   730: invokespecial   net/minecraft/world/gen/layer/GenLayerShore.func_151633_d:(I)Z
        //   733: ifeq            758
        //   736: aload_0        
        //   737: iload           14
        //   739: invokespecial   net/minecraft/world/gen/layer/GenLayerShore.func_151633_d:(I)Z
        //   742: ifeq            758
        //   745: aload           6
        //   747: iconst_0       
        //   748: iconst_0       
        //   749: iload_3        
        //   750: imul           
        //   751: iadd           
        //   752: iload           9
        //   754: iastore        
        //   755: goto            807
        //   758: aload           6
        //   760: iconst_0       
        //   761: iconst_0       
        //   762: iload_3        
        //   763: imul           
        //   764: iadd           
        //   765: getstatic       net/minecraft/world/biome/BiomeGenBase.desert:Lnet/minecraft/world/biome/BiomeGenBase;
        //   768: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   771: iastore        
        //   772: goto            807
        //   775: aload           6
        //   777: iconst_0       
        //   778: iconst_0       
        //   779: iload_3        
        //   780: imul           
        //   781: iadd           
        //   782: iload           9
        //   784: iastore        
        //   785: goto            807
        //   788: aload_0        
        //   789: aload           5
        //   791: aload           6
        //   793: iconst_0       
        //   794: iconst_0       
        //   795: iload_3        
        //   796: iload           9
        //   798: getstatic       net/minecraft/world/biome/BiomeGenBase.stoneBeach:Lnet/minecraft/world/biome/BiomeGenBase;
        //   801: getfield        net/minecraft/world/biome/BiomeGenBase.biomeID:I
        //   804: invokespecial   net/minecraft/world/gen/layer/GenLayerShore.func_151632_a:([I[IIIIII)V
        //   807: iinc            8, 1
        //   810: goto            37
        //   813: iinc            7, 1
        //   816: goto            31
        //   819: aload           6
        //   821: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0807 (coming from #0369).
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
}
