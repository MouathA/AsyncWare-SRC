package optfine;

import java.util.*;

public class CompactArrayList
{
    private int initialCapacity;
    private int countValid;
    private ArrayList list;
    private float loadFactor;
    
    public void add(final int n, final Object o) {
        if (o != null) {
            ++this.countValid;
        }
        this.list.add(n, o);
    }
    
    public void compact() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        optfine/CompactArrayList.countValid:I
        //     4: ifgt            24
        //     7: aload_0        
        //     8: getfield        optfine/CompactArrayList.list:Ljava/util/ArrayList;
        //    11: invokevirtual   java/util/ArrayList.size:()I
        //    14: ifgt            24
        //    17: aload_0        
        //    18: invokevirtual   optfine/CompactArrayList.clear:()V
        //    21: goto            138
        //    24: aload_0        
        //    25: getfield        optfine/CompactArrayList.list:Ljava/util/ArrayList;
        //    28: invokevirtual   java/util/ArrayList.size:()I
        //    31: aload_0        
        //    32: getfield        optfine/CompactArrayList.initialCapacity:I
        //    35: if_icmple       138
        //    38: aload_0        
        //    39: getfield        optfine/CompactArrayList.countValid:I
        //    42: i2f            
        //    43: fconst_1       
        //    44: fmul           
        //    45: aload_0        
        //    46: getfield        optfine/CompactArrayList.list:Ljava/util/ArrayList;
        //    49: invokevirtual   java/util/ArrayList.size:()I
        //    52: i2f            
        //    53: fdiv           
        //    54: fstore_1       
        //    55: fload_1        
        //    56: aload_0        
        //    57: getfield        optfine/CompactArrayList.loadFactor:F
        //    60: fcmpg          
        //    61: ifgt            138
        //    64: iconst_0       
        //    65: aload_0        
        //    66: getfield        optfine/CompactArrayList.list:Ljava/util/ArrayList;
        //    69: invokevirtual   java/util/ArrayList.size:()I
        //    72: if_icmpge       113
        //    75: aload_0        
        //    76: getfield        optfine/CompactArrayList.list:Ljava/util/ArrayList;
        //    79: iconst_0       
        //    80: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //    83: astore          4
        //    85: aload           4
        //    87: ifnull          107
        //    90: goto            104
        //    93: aload_0        
        //    94: getfield        optfine/CompactArrayList.list:Ljava/util/ArrayList;
        //    97: iconst_0       
        //    98: aload           4
        //   100: invokevirtual   java/util/ArrayList.set:(ILjava/lang/Object;)Ljava/lang/Object;
        //   103: pop            
        //   104: iinc            2, 1
        //   107: iinc            3, 1
        //   110: goto            64
        //   113: aload_0        
        //   114: getfield        optfine/CompactArrayList.list:Ljava/util/ArrayList;
        //   117: invokevirtual   java/util/ArrayList.size:()I
        //   120: iconst_1       
        //   121: isub           
        //   122: istore_3       
        //   123: aload_0        
        //   124: getfield        optfine/CompactArrayList.list:Ljava/util/ArrayList;
        //   127: iconst_0       
        //   128: invokevirtual   java/util/ArrayList.remove:(I)Ljava/lang/Object;
        //   131: pop            
        //   132: iinc            3, -1
        //   135: goto            123
        //   138: return         
        // 
        // The error that occurred was:
        // 
        // java.util.ConcurrentModificationException
        //     at java.util.ArrayList$Itr.checkForComodification(Unknown Source)
        //     at java.util.ArrayList$Itr.next(Unknown Source)
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2863)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
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
    
    public boolean add(final Object o) {
        if (o != null) {
            ++this.countValid;
        }
        return this.list.add(o);
    }
    
    public CompactArrayList() {
        this(10, 0.75f);
    }
    
    public void clear() {
        this.list.clear();
        this.countValid = 0;
    }
    
    public Object set(final int n, final Object o) {
        final Object set = this.list.set(n, o);
        if (o != set) {
            if (set == null) {
                ++this.countValid;
            }
            if (o == null) {
                --this.countValid;
            }
        }
        return set;
    }
    
    public int size() {
        return this.list.size();
    }
    
    public boolean isEmpty() {
        return this.list.isEmpty();
    }
    
    public CompactArrayList(final int initialCapacity, final float loadFactor) {
        this.list = null;
        this.initialCapacity = 0;
        this.loadFactor = 1.0f;
        this.countValid = 0;
        this.list = new ArrayList(initialCapacity);
        this.initialCapacity = initialCapacity;
        this.loadFactor = loadFactor;
    }
    
    public Object remove(final int n) {
        final Object remove = this.list.remove(n);
        if (remove != null) {
            --this.countValid;
        }
        return remove;
    }
    
    public boolean contains(final Object o) {
        return this.list.contains(o);
    }
    
    public CompactArrayList(final int n) {
        this(n, 0.75f);
    }
    
    public int getCountValid() {
        return this.countValid;
    }
    
    public Object get(final int n) {
        return this.list.get(n);
    }
}
