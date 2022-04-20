package com.nquantum.util.raytrace;

import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import java.util.*;

public class NodeProcessor
{
    HashMap hashOpenNodes;
    public ArrayList path;
    private static Minecraft mc;
    int nextID;
    public ArrayList triedPaths;
    HashMap hashClosedNodes;
    long totalTime;
    ArrayList openNodes;
    
    public Node createNode(final BlockPos p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: aload_1        
        //     5: invokevirtual   net/minecraft/util/BlockPos.down:()Lnet/minecraft/util/BlockPos;
        //     8: invokestatic    com/nquantum/util/raytrace/NodeProcessor.getBlock:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/Block;
        //    11: ifne            35
        //    14: aload_1        
        //    15: invokestatic    com/nquantum/util/raytrace/NodeProcessor.getBlock:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/Block;
        //    18: if_acmpeq       35
        //    21: aload_1        
        //    22: invokevirtual   net/minecraft/util/BlockPos.up:()Lnet/minecraft/util/BlockPos;
        //    25: invokestatic    com/nquantum/util/raytrace/NodeProcessor.getBlock:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/Block;
        //    28: if_acmpeq       35
        //    31: iconst_1       
        //    32: goto            36
        //    35: iconst_0       
        //    36: aload_1        
        //    37: invokespecial   com/nquantum/util/raytrace/Node.<init>:(ZLnet/minecraft/util/BlockPos;)V
        //    40: aload_1        
        //    41: invokevirtual   net/minecraft/util/BlockPos.hashCode:()I
        //    44: invokevirtual   com/nquantum/util/raytrace/Node.setId:(I)Lcom/nquantum/util/raytrace/Node;
        //    47: astore_2       
        //    48: aload_2        
        //    49: aload_2        
        //    50: invokestatic    com/nquantum/util/raytrace/NodeProcessor.isVclipableDown:(Lcom/nquantum/util/raytrace/Node;)Z
        //    53: invokevirtual   com/nquantum/util/raytrace/Node.setVclipableDown:(Z)V
        //    56: aload_2        
        //    57: aload_2        
        //    58: invokestatic    com/nquantum/util/raytrace/NodeProcessor.isVclipableUp:(Lcom/nquantum/util/raytrace/Node;)Z
        //    61: invokevirtual   com/nquantum/util/raytrace/Node.setVclipableUp:(Z)V
        //    64: aload_2        
        //    65: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0035 (coming from #0018).
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
    
    public NodeProcessor() {
        this.path = new ArrayList();
        this.triedPaths = new ArrayList();
        this.openNodes = new ArrayList();
        this.hashOpenNodes = new HashMap();
        this.hashClosedNodes = new HashMap();
        this.totalTime = 0L;
        this.nextID = 0;
    }
    
    public void closeNode(final Node node) {
        this.openNodes.remove(node);
        this.hashOpenNodes.remove(node.id);
        this.hashClosedNodes.put(node.id, node);
    }
    
    private boolean isNodeOpen(final Node node) {
        return this.hashOpenNodes.get(node.id) != null;
    }
    
    public static Block getBlock(final BlockPos blockPos) {
        return NodeProcessor.mc.theWorld.getBlockState(blockPos).getBlock();
    }
    
    private ArrayList getNeighbors(final Node p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: invokespecial   java/util/ArrayList.<init>:()V
        //     7: astore_2       
        //     8: aload_1        
        //     9: invokevirtual   com/nquantum/util/raytrace/Node.getBlockpos:()Lnet/minecraft/util/BlockPos;
        //    12: astore_3       
        //    13: aload_1        
        //    14: invokevirtual   com/nquantum/util/raytrace/Node.isVclipableDown:()Z
        //    17: ifeq            33
        //    20: aload_2        
        //    21: aload_0        
        //    22: aload_1        
        //    23: invokevirtual   com/nquantum/util/raytrace/Node.getVclipPosDown:()Lnet/minecraft/util/BlockPos;
        //    26: invokevirtual   com/nquantum/util/raytrace/NodeProcessor.createNode:(Lnet/minecraft/util/BlockPos;)Lcom/nquantum/util/raytrace/Node;
        //    29: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //    32: pop            
        //    33: aload_1        
        //    34: invokevirtual   com/nquantum/util/raytrace/Node.isVclipableUp:()Z
        //    37: ifeq            53
        //    40: aload_2        
        //    41: aload_0        
        //    42: aload_1        
        //    43: invokevirtual   com/nquantum/util/raytrace/Node.getVclipPosUp:()Lnet/minecraft/util/BlockPos;
        //    46: invokevirtual   com/nquantum/util/raytrace/NodeProcessor.createNode:(Lnet/minecraft/util/BlockPos;)Lcom/nquantum/util/raytrace/Node;
        //    49: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //    52: pop            
        //    53: aload_3        
        //    54: iconst_1       
        //    55: iconst_1       
        //    56: iconst_1       
        //    57: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //    60: aload_3        
        //    61: iconst_m1      
        //    62: iconst_m1      
        //    63: iconst_m1      
        //    64: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //    67: invokestatic    net/minecraft/util/BlockPos.getAllInBox:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/BlockPos;)Ljava/lang/Iterable;
        //    70: invokeinterface java/lang/Iterable.iterator:()Ljava/util/Iterator;
        //    75: astore          4
        //    77: aload           4
        //    79: invokeinterface java/util/Iterator.hasNext:()Z
        //    84: ifeq            317
        //    87: aload           4
        //    89: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    94: checkcast       Lnet/minecraft/util/BlockPos;
        //    97: astore          5
        //    99: aload           5
        //   101: aload_3        
        //   102: invokevirtual   net/minecraft/util/BlockPos.equals:(Ljava/lang/Object;)Z
        //   105: ifeq            111
        //   108: goto            77
        //   111: aload           5
        //   113: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //   116: aload_3        
        //   117: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //   120: if_icmple       138
        //   123: aload           5
        //   125: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //   128: aload_3        
        //   129: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //   132: if_icmple       138
        //   135: goto            77
        //   138: aload           5
        //   140: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //   143: aload_3        
        //   144: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //   147: if_icmpge       165
        //   150: aload           5
        //   152: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //   155: aload_3        
        //   156: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //   159: if_icmpge       165
        //   162: goto            77
        //   165: aload           5
        //   167: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //   170: aload_3        
        //   171: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //   174: if_icmpge       192
        //   177: aload           5
        //   179: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //   182: aload_3        
        //   183: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //   186: if_icmple       192
        //   189: goto            77
        //   192: aload           5
        //   194: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //   197: aload_3        
        //   198: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //   201: if_icmple       219
        //   204: aload           5
        //   206: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //   209: aload_3        
        //   210: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //   213: if_icmpge       219
        //   216: goto            77
        //   219: aload           5
        //   221: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   224: aload_3        
        //   225: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   228: if_icmpge       257
        //   231: getstatic       com/nquantum/util/raytrace/NodeProcessor.mc:Lnet/minecraft/client/Minecraft;
        //   234: getfield        net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   237: aload           5
        //   239: iconst_2       
        //   240: invokevirtual   net/minecraft/util/BlockPos.up:(I)Lnet/minecraft/util/BlockPos;
        //   243: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   246: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   251: if_acmpeq       257
        //   254: goto            77
        //   257: aload           5
        //   259: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   262: aload_3        
        //   263: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   266: if_icmple       294
        //   269: getstatic       com/nquantum/util/raytrace/NodeProcessor.mc:Lnet/minecraft/client/Minecraft;
        //   272: getfield        net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   275: aload_3        
        //   276: iconst_2       
        //   277: invokevirtual   net/minecraft/util/BlockPos.up:(I)Lnet/minecraft/util/BlockPos;
        //   280: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   283: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   288: if_acmpeq       294
        //   291: goto            77
        //   294: aload_0        
        //   295: aload           5
        //   297: invokevirtual   com/nquantum/util/raytrace/NodeProcessor.createNode:(Lnet/minecraft/util/BlockPos;)Lcom/nquantum/util/raytrace/Node;
        //   300: astore          6
        //   302: aload           6
        //   304: ifnull          314
        //   307: aload_2        
        //   308: aload           6
        //   310: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   313: pop            
        //   314: goto            77
        //   317: aload_2        
        //   318: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void getPath(final BlockPos p0, final BlockPos p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: lconst_0       
        //     2: putfield        com/nquantum/util/raytrace/NodeProcessor.totalTime:J
        //     5: aload_0        
        //     6: getfield        com/nquantum/util/raytrace/NodeProcessor.hashOpenNodes:Ljava/util/HashMap;
        //     9: invokevirtual   java/util/HashMap.clear:()V
        //    12: aload_0        
        //    13: getfield        com/nquantum/util/raytrace/NodeProcessor.hashClosedNodes:Ljava/util/HashMap;
        //    16: invokevirtual   java/util/HashMap.clear:()V
        //    19: aload_0        
        //    20: getfield        com/nquantum/util/raytrace/NodeProcessor.openNodes:Ljava/util/ArrayList;
        //    23: invokevirtual   java/util/ArrayList.clear:()V
        //    26: aload_0        
        //    27: aload_1        
        //    28: invokevirtual   com/nquantum/util/raytrace/NodeProcessor.createNode:(Lnet/minecraft/util/BlockPos;)Lcom/nquantum/util/raytrace/Node;
        //    31: astore_3       
        //    32: aload_0        
        //    33: aload_2        
        //    34: invokevirtual   com/nquantum/util/raytrace/NodeProcessor.createNode:(Lnet/minecraft/util/BlockPos;)Lcom/nquantum/util/raytrace/Node;
        //    37: astore          4
        //    39: aload_0        
        //    40: aload_3        
        //    41: invokevirtual   com/nquantum/util/raytrace/NodeProcessor.openNode:(Lcom/nquantum/util/raytrace/Node;)V
        //    44: invokestatic    java/lang/System.nanoTime:()J
        //    47: lstore          5
        //    49: aload_0        
        //    50: getfield        com/nquantum/util/raytrace/NodeProcessor.hashOpenNodes:Ljava/util/HashMap;
        //    53: invokevirtual   java/util/HashMap.values:()Ljava/util/Collection;
        //    56: invokeinterface java/util/Collection.size:()I
        //    61: ifle            421
        //    64: aload_0        
        //    65: getfield        com/nquantum/util/raytrace/NodeProcessor.openNodes:Ljava/util/ArrayList;
        //    68: iconst_0       
        //    69: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //    72: checkcast       Lcom/nquantum/util/raytrace/Node;
        //    75: astore          8
        //    77: goto            86
        //    80: aload_0        
        //    81: aconst_null    
        //    82: putfield        com/nquantum/util/raytrace/NodeProcessor.path:Ljava/util/ArrayList;
        //    85: return         
        //    86: aload           8
        //    88: getfield        com/nquantum/util/raytrace/Node.fCost:D
        //    91: dstore          9
        //    93: iconst_1       
        //    94: aload_0        
        //    95: getfield        com/nquantum/util/raytrace/NodeProcessor.openNodes:Ljava/util/ArrayList;
        //    98: invokevirtual   java/util/ArrayList.size:()I
        //   101: if_icmpge       171
        //   104: aload_0        
        //   105: getfield        com/nquantum/util/raytrace/NodeProcessor.openNodes:Ljava/util/ArrayList;
        //   108: iconst_1       
        //   109: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //   112: checkcast       Lcom/nquantum/util/raytrace/Node;
        //   115: astore          12
        //   117: aload           12
        //   119: getfield        com/nquantum/util/raytrace/Node.fCost:D
        //   122: dstore          13
        //   124: dload           13
        //   126: dload           9
        //   128: dcmpg          
        //   129: iflt            157
        //   132: dload           13
        //   134: aload           8
        //   136: getfield        com/nquantum/util/raytrace/Node.fCost:D
        //   139: dcmpl          
        //   140: ifne            165
        //   143: aload           12
        //   145: getfield        com/nquantum/util/raytrace/Node.hCost:D
        //   148: aload           8
        //   150: getfield        com/nquantum/util/raytrace/Node.hCost:D
        //   153: dcmpg          
        //   154: ifge            165
        //   157: dload           13
        //   159: dstore          9
        //   161: aload           12
        //   163: astore          8
        //   165: iinc            11, 1
        //   168: goto            93
        //   171: aload_0        
        //   172: aload           8
        //   174: invokevirtual   com/nquantum/util/raytrace/NodeProcessor.closeNode:(Lcom/nquantum/util/raytrace/Node;)V
        //   177: aload_0        
        //   178: getfield        com/nquantum/util/raytrace/NodeProcessor.triedPaths:Ljava/util/ArrayList;
        //   181: aload           8
        //   183: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   186: pop            
        //   187: aload           8
        //   189: invokevirtual   com/nquantum/util/raytrace/Node.getBlockpos:()Lnet/minecraft/util/BlockPos;
        //   192: aload           4
        //   194: invokevirtual   com/nquantum/util/raytrace/Node.getBlockpos:()Lnet/minecraft/util/BlockPos;
        //   197: invokevirtual   net/minecraft/util/BlockPos.equals:(Ljava/lang/Object;)Z
        //   200: ifeq            218
        //   203: aload           4
        //   205: aload           8
        //   207: putfield        com/nquantum/util/raytrace/Node.parent:Lcom/nquantum/util/raytrace/Node;
        //   210: aload_0        
        //   211: aload_3        
        //   212: aload           4
        //   214: invokespecial   com/nquantum/util/raytrace/NodeProcessor.retracePath:(Lcom/nquantum/util/raytrace/Node;Lcom/nquantum/util/raytrace/Node;)V
        //   217: return         
        //   218: aload_0        
        //   219: aload           8
        //   221: invokespecial   com/nquantum/util/raytrace/NodeProcessor.getNeighbors:(Lcom/nquantum/util/raytrace/Node;)Ljava/util/ArrayList;
        //   224: invokevirtual   java/util/ArrayList.iterator:()Ljava/util/Iterator;
        //   227: astore          11
        //   229: aload           11
        //   231: invokeinterface java/util/Iterator.hasNext:()Z
        //   236: ifeq            415
        //   239: aload           11
        //   241: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   246: checkcast       Lcom/nquantum/util/raytrace/Node;
        //   249: astore          12
        //   251: aload           12
        //   253: invokevirtual   com/nquantum/util/raytrace/Node.isWalkable:()Z
        //   256: ifne            281
        //   259: aload           12
        //   261: invokevirtual   com/nquantum/util/raytrace/Node.isVclipableDown:()Z
        //   264: dup            
        //   265: istore          13
        //   267: ifne            281
        //   270: aload           12
        //   272: invokevirtual   com/nquantum/util/raytrace/Node.isVclipableUp:()Z
        //   275: dup            
        //   276: istore          14
        //   278: ifeq            229
        //   281: aload_0        
        //   282: aload           12
        //   284: ifnull          290
        //   287: goto            229
        //   290: goto            324
        //   293: aload_0        
        //   294: aload           12
        //   296: invokevirtual   com/nquantum/util/raytrace/Node.getBlockpos:()Lnet/minecraft/util/BlockPos;
        //   299: invokevirtual   net/minecraft/util/BlockPos.up:()Lnet/minecraft/util/BlockPos;
        //   302: invokevirtual   com/nquantum/util/raytrace/NodeProcessor.createNode:(Lnet/minecraft/util/BlockPos;)Lcom/nquantum/util/raytrace/Node;
        //   305: astore          12
        //   307: goto            324
        //   310: aload_0        
        //   311: aload           12
        //   313: invokevirtual   com/nquantum/util/raytrace/Node.getBlockpos:()Lnet/minecraft/util/BlockPos;
        //   316: invokevirtual   net/minecraft/util/BlockPos.down:()Lnet/minecraft/util/BlockPos;
        //   319: invokevirtual   com/nquantum/util/raytrace/NodeProcessor.createNode:(Lnet/minecraft/util/BlockPos;)Lcom/nquantum/util/raytrace/Node;
        //   322: astore          12
        //   324: aload           8
        //   326: getfield        com/nquantum/util/raytrace/Node.gCost:D
        //   329: aload           8
        //   331: invokevirtual   com/nquantum/util/raytrace/Node.getBlockpos:()Lnet/minecraft/util/BlockPos;
        //   334: aload           4
        //   336: invokevirtual   com/nquantum/util/raytrace/Node.getBlockpos:()Lnet/minecraft/util/BlockPos;
        //   339: invokestatic    com/nquantum/util/raytrace/Node.distance:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/BlockPos;)D
        //   342: dadd           
        //   343: dstore          15
        //   345: aload_0        
        //   346: aload           12
        //   348: invokespecial   com/nquantum/util/raytrace/NodeProcessor.isNodeOpen:(Lcom/nquantum/util/raytrace/Node;)Z
        //   351: istore          17
        //   353: dload           15
        //   355: aload           12
        //   357: getfield        com/nquantum/util/raytrace/Node.gCost:D
        //   360: dcmpg          
        //   361: iflt            369
        //   364: iload           17
        //   366: ifne            412
        //   369: aload           12
        //   371: dload           15
        //   373: putfield        com/nquantum/util/raytrace/Node.gCost:D
        //   376: aload           12
        //   378: aload           12
        //   380: invokevirtual   com/nquantum/util/raytrace/Node.getBlockpos:()Lnet/minecraft/util/BlockPos;
        //   383: aload           4
        //   385: invokevirtual   com/nquantum/util/raytrace/Node.getBlockpos:()Lnet/minecraft/util/BlockPos;
        //   388: invokestatic    com/nquantum/util/raytrace/Node.distance:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/BlockPos;)D
        //   391: putfield        com/nquantum/util/raytrace/Node.hCost:D
        //   394: aload           12
        //   396: aload           8
        //   398: putfield        com/nquantum/util/raytrace/Node.parent:Lcom/nquantum/util/raytrace/Node;
        //   401: iload           17
        //   403: ifne            412
        //   406: aload_0        
        //   407: aload           12
        //   409: invokevirtual   com/nquantum/util/raytrace/NodeProcessor.openNode:(Lcom/nquantum/util/raytrace/Node;)V
        //   412: goto            229
        //   415: iinc            7, 1
        //   418: goto            49
        //   421: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0229 (coming from #0412).
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
    
    public static boolean isVclipableDown(final Node p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   com/nquantum/util/raytrace/Node.isWalkable:()Z
        //     4: ifne            9
        //     7: iconst_0       
        //     8: ireturn        
        //     9: aload_0        
        //    10: invokevirtual   com/nquantum/util/raytrace/Node.getBlockpos:()Lnet/minecraft/util/BlockPos;
        //    13: astore_1       
        //    14: aload_1        
        //    15: invokevirtual   net/minecraft/util/BlockPos.down:()Lnet/minecraft/util/BlockPos;
        //    18: invokestatic    com/nquantum/util/raytrace/NodeProcessor.getBlock:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/Block;
        //    21: if_acmpeq       26
        //    24: iconst_0       
        //    25: ireturn        
        //    26: new             Lnet/minecraft/util/BlockPos;
        //    29: dup            
        //    30: aload_1        
        //    31: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //    34: aload_1        
        //    35: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //    38: iconst_1       
        //    39: isub           
        //    40: aload_1        
        //    41: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //    44: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //    47: astore_3       
        //    48: aload_3        
        //    49: invokestatic    com/nquantum/util/raytrace/NodeProcessor.getBlock:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/Block;
        //    52: if_acmpeq       72
        //    55: aload_3        
        //    56: invokevirtual   net/minecraft/util/BlockPos.up:()Lnet/minecraft/util/BlockPos;
        //    59: invokestatic    com/nquantum/util/raytrace/NodeProcessor.getBlock:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/Block;
        //    62: if_acmpeq       72
        //    65: aload_0        
        //    66: aload_3        
        //    67: invokevirtual   com/nquantum/util/raytrace/Node.setVclipPosDown:(Lnet/minecraft/util/BlockPos;)V
        //    70: iconst_1       
        //    71: ireturn        
        //    72: iinc            2, 1
        //    75: goto            26
        //    78: iconst_0       
        //    79: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void openNode(final Node node) {
        this.hashOpenNodes.put(node.id, node);
        this.openNodes.add(node);
    }
    
    public static boolean isWalkable(final Block block) {
        return block.getMaterial() != Material.air && (block instanceof BlockLilyPad || block.getMaterial() == Material.vine || block instanceof BlockLadder || block.isFullBlock());
    }
    
    static {
        NodeProcessor.mc = Minecraft.getMinecraft();
    }
    
    private void retracePath(final Node node, final Node node2) {
        final ArrayList<Node> path = new ArrayList<Node>();
        for (Node parent = node2; !parent.equals(node); parent = parent.parent) {
            path.add(parent);
        }
        path.add(node);
        Collections.reverse(path);
        this.path = path;
    }
    
    public static boolean isVclipableUp(final Node p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   com/nquantum/util/raytrace/Node.isWalkable:()Z
        //     4: ifne            9
        //     7: iconst_0       
        //     8: ireturn        
        //     9: aload_0        
        //    10: invokevirtual   com/nquantum/util/raytrace/Node.getBlockpos:()Lnet/minecraft/util/BlockPos;
        //    13: astore_1       
        //    14: aload_1        
        //    15: invokevirtual   net/minecraft/util/BlockPos.up:()Lnet/minecraft/util/BlockPos;
        //    18: invokestatic    com/nquantum/util/raytrace/NodeProcessor.getBlock:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/Block;
        //    21: if_acmpeq       26
        //    24: iconst_0       
        //    25: ireturn        
        //    26: new             Lnet/minecraft/util/BlockPos;
        //    29: dup            
        //    30: aload_1        
        //    31: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //    34: aload_1        
        //    35: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //    38: iconst_1       
        //    39: iadd           
        //    40: aload_1        
        //    41: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //    44: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //    47: astore_3       
        //    48: aload_3        
        //    49: invokestatic    com/nquantum/util/raytrace/NodeProcessor.getBlock:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/Block;
        //    52: if_acmpeq       72
        //    55: aload_3        
        //    56: invokevirtual   net/minecraft/util/BlockPos.up:()Lnet/minecraft/util/BlockPos;
        //    59: invokestatic    com/nquantum/util/raytrace/NodeProcessor.getBlock:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/Block;
        //    62: if_acmpeq       72
        //    65: aload_0        
        //    66: aload_3        
        //    67: invokevirtual   com/nquantum/util/raytrace/Node.setVclipPosUp:(Lnet/minecraft/util/BlockPos;)V
        //    70: iconst_1       
        //    71: ireturn        
        //    72: iinc            2, 1
        //    75: goto            26
        //    78: iconst_0       
        //    79: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
