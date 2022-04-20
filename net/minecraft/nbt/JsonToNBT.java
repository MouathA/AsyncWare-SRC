package net.minecraft.nbt;

import java.util.regex.*;
import org.apache.logging.log4j.*;
import com.google.common.base.*;
import java.util.*;
import com.google.common.collect.*;

public class JsonToNBT
{
    private static final Logger logger;
    private static final Pattern field_179273_b;
    
    static Any func_150316_a(final String s, String s2) throws NBTException {
        s2 = s2.trim();
        if (s2.startsWith("{")) {
            s2 = s2.substring(1, s2.length() - 1);
            final Compound compound = new Compound(s);
            if (s2.length() > 0) {
                final String func_150314_a = func_150314_a(s2, true);
                if (func_150314_a.length() > 0) {
                    compound.field_150491_b.add(func_179270_a(func_150314_a, true));
                }
                if (s2.length() >= func_150314_a.length() + 1) {
                    s2.charAt(func_150314_a.length());
                    throw new NBTException("Unexpected token '" + '\u0001' + "' at: " + s2.substring(func_150314_a.length()));
                }
            }
            return compound;
        }
        if (s2.startsWith("[") && !JsonToNBT.field_179273_b.matcher(s2).matches()) {
            s2 = s2.substring(1, s2.length() - 1);
            final List list = new List(s);
            if (s2.length() > 0) {
                final String func_150314_a2 = func_150314_a(s2, false);
                if (func_150314_a2.length() > 0) {
                    list.field_150492_b.add(func_179270_a(func_150314_a2, true));
                }
                if (s2.length() >= func_150314_a2.length() + 1) {
                    s2.charAt(func_150314_a2.length());
                    throw new NBTException("Unexpected token '" + '\u0001' + "' at: " + s2.substring(func_150314_a2.length()));
                }
            }
            return list;
        }
        return new Primitive(s, s2);
    }
    
    public static NBTTagCompound getTagFromJson(String trim) throws NBTException {
        trim = trim.trim();
        if (!trim.startsWith("{")) {
            throw new NBTException("Invalid tag encountered, expected '{' as first char.");
        }
        if (func_150310_b(trim) != 1) {
            throw new NBTException("Encountered multiple top tags, only one expected");
        }
        return (NBTTagCompound)func_150316_a("tag", trim).parse();
    }
    
    private static Any func_179270_a(final String s, final boolean b) throws NBTException {
        return func_179272_a(func_150313_b(s, b), func_150311_c(s, b));
    }
    
    private static String func_150314_a(final String s, final boolean b) throws NBTException {
        func_150312_a(s, ':');
        func_150312_a(s, ',');
        if (b) {
            throw new NBTException("Unable to locate name/value separator for string: " + s);
        }
        return func_179269_a(s, -1);
    }
    
    static {
        logger = LogManager.getLogger();
        field_179273_b = Pattern.compile("\\[[-+\\d|,\\s]+\\]");
    }
    
    private static String func_150311_c(String trim, final boolean b) throws NBTException {
        if (b) {
            trim = trim.trim();
            if (trim.startsWith("{") || trim.startsWith("[")) {
                return trim;
            }
        }
        final int func_150312_a = func_150312_a(trim, ':');
        if (func_150312_a != -1) {
            return trim.substring(func_150312_a + 1).trim();
        }
        if (b) {
            return trim;
        }
        throw new NBTException("Unable to locate name/value separator for string: " + trim);
    }
    
    private static String func_179269_a(final String p0, final int p1) throws NBTException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: invokespecial   java/util/Stack.<init>:()V
        //     7: astore_2       
        //     8: iload_1        
        //     9: iconst_1       
        //    10: iadd           
        //    11: istore_3       
        //    12: iload_3        
        //    13: aload_0        
        //    14: invokevirtual   java/lang/String.length:()I
        //    17: if_icmpge       268
        //    20: aload_0        
        //    21: iload_3        
        //    22: invokevirtual   java/lang/String.charAt:(I)C
        //    25: istore          8
        //    27: iload           8
        //    29: bipush          34
        //    31: if_icmpne       85
        //    34: aload_0        
        //    35: iload_3        
        //    36: ifle            66
        //    39: new             Lnet/minecraft/nbt/NBTException;
        //    42: dup            
        //    43: new             Ljava/lang/StringBuilder;
        //    46: dup            
        //    47: invokespecial   java/lang/StringBuilder.<init>:()V
        //    50: ldc             "Illegal use of \\\": "
        //    52: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    55: aload_0        
        //    56: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    59: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    62: invokespecial   net/minecraft/nbt/NBTException.<init>:(Ljava/lang/String;)V
        //    65: athrow         
        //    66: iconst_1       
        //    67: goto            71
        //    70: iconst_0       
        //    71: istore          4
        //    73: goto            79
        //    76: goto            79
        //    79: iload_3        
        //    80: istore          7
        //    82: goto            242
        //    85: iload           8
        //    87: bipush          123
        //    89: if_icmpeq       232
        //    92: iload           8
        //    94: bipush          91
        //    96: if_icmpeq       232
        //    99: iload           8
        //   101: bipush          125
        //   103: if_icmpne       155
        //   106: aload_2        
        //   107: invokevirtual   java/util/Stack.isEmpty:()Z
        //   110: ifne            128
        //   113: aload_2        
        //   114: invokevirtual   java/util/Stack.pop:()Ljava/lang/Object;
        //   117: checkcast       Ljava/lang/Character;
        //   120: invokevirtual   java/lang/Character.charValue:()C
        //   123: bipush          123
        //   125: if_icmpeq       155
        //   128: new             Lnet/minecraft/nbt/NBTException;
        //   131: dup            
        //   132: new             Ljava/lang/StringBuilder;
        //   135: dup            
        //   136: invokespecial   java/lang/StringBuilder.<init>:()V
        //   139: ldc             "Unbalanced curly brackets {}: "
        //   141: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   144: aload_0        
        //   145: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   148: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   151: invokespecial   net/minecraft/nbt/NBTException.<init>:(Ljava/lang/String;)V
        //   154: athrow         
        //   155: iload           8
        //   157: bipush          93
        //   159: if_icmpne       211
        //   162: aload_2        
        //   163: invokevirtual   java/util/Stack.isEmpty:()Z
        //   166: ifne            184
        //   169: aload_2        
        //   170: invokevirtual   java/util/Stack.pop:()Ljava/lang/Object;
        //   173: checkcast       Ljava/lang/Character;
        //   176: invokevirtual   java/lang/Character.charValue:()C
        //   179: bipush          91
        //   181: if_icmpeq       211
        //   184: new             Lnet/minecraft/nbt/NBTException;
        //   187: dup            
        //   188: new             Ljava/lang/StringBuilder;
        //   191: dup            
        //   192: invokespecial   java/lang/StringBuilder.<init>:()V
        //   195: ldc             "Unbalanced square brackets []: "
        //   197: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   200: aload_0        
        //   201: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   204: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   207: invokespecial   net/minecraft/nbt/NBTException.<init>:(Ljava/lang/String;)V
        //   210: athrow         
        //   211: iload           8
        //   213: bipush          44
        //   215: if_icmpne       242
        //   218: aload_2        
        //   219: invokevirtual   java/util/Stack.isEmpty:()Z
        //   222: ifeq            242
        //   225: aload_0        
        //   226: iconst_0       
        //   227: iload_3        
        //   228: invokevirtual   java/lang/String.substring:(II)Ljava/lang/String;
        //   231: areturn        
        //   232: aload_2        
        //   233: iload           8
        //   235: invokestatic    java/lang/Character.valueOf:(C)Ljava/lang/Character;
        //   238: invokevirtual   java/util/Stack.push:(Ljava/lang/Object;)Ljava/lang/Object;
        //   241: pop            
        //   242: iload           8
        //   244: invokestatic    java/lang/Character.isWhitespace:(C)Z
        //   247: ifne            262
        //   250: iconst_0       
        //   251: iload_3        
        //   252: if_icmpeq       262
        //   255: aload_0        
        //   256: iconst_0       
        //   257: iconst_1       
        //   258: invokevirtual   java/lang/String.substring:(II)Ljava/lang/String;
        //   261: areturn        
        //   262: iinc            3, 1
        //   265: goto            12
        //   268: aload_0        
        //   269: iconst_0       
        //   270: iload_3        
        //   271: invokevirtual   java/lang/String.substring:(II)Ljava/lang/String;
        //   274: areturn        
        //    Exceptions:
        //  throws net.minecraft.nbt.NBTException
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0242 (coming from #0082).
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
    
    static Any func_179272_a(final String... array) throws NBTException {
        return func_150316_a(array[0], array[1]);
    }
    
    private static int func_150312_a(final String p0, final char p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_0        
        //     2: invokevirtual   java/lang/String.length:()I
        //     5: if_icmpge       68
        //     8: aload_0        
        //     9: iconst_0       
        //    10: invokevirtual   java/lang/String.charAt:(I)C
        //    13: istore          4
        //    15: iload           4
        //    17: bipush          34
        //    19: if_icmpne       38
        //    22: aload_0        
        //    23: goto            62
        //    26: goto            33
        //    29: iconst_1       
        //    30: goto            34
        //    33: iconst_0       
        //    34: istore_3       
        //    35: goto            62
        //    38: iload           4
        //    40: iload_1        
        //    41: if_icmpne       46
        //    44: iconst_0       
        //    45: ireturn        
        //    46: iload           4
        //    48: bipush          123
        //    50: if_icmpeq       60
        //    53: iload           4
        //    55: bipush          91
        //    57: if_icmpne       62
        //    60: iconst_m1      
        //    61: ireturn        
        //    62: iinc            2, 1
        //    65: goto            0
        //    68: iconst_m1      
        //    69: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0062 (coming from #0023).
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
    
    static int func_150310_b(final String p0) throws NBTException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: invokespecial   java/util/Stack.<init>:()V
        //     7: astore_3       
        //     8: iconst_0       
        //     9: aload_0        
        //    10: invokevirtual   java/lang/String.length:()I
        //    13: if_icmpge       222
        //    16: aload_0        
        //    17: iconst_0       
        //    18: invokevirtual   java/lang/String.charAt:(I)C
        //    21: istore          5
        //    23: iload           5
        //    25: bipush          34
        //    27: if_icmpne       70
        //    30: aload_0        
        //    31: goto            61
        //    34: new             Lnet/minecraft/nbt/NBTException;
        //    37: dup            
        //    38: new             Ljava/lang/StringBuilder;
        //    41: dup            
        //    42: invokespecial   java/lang/StringBuilder.<init>:()V
        //    45: ldc             "Illegal use of \\\": "
        //    47: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    50: aload_0        
        //    51: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    54: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    57: invokespecial   net/minecraft/nbt/NBTException.<init>:(Ljava/lang/String;)V
        //    60: athrow         
        //    61: iconst_1       
        //    62: goto            66
        //    65: iconst_0       
        //    66: istore_2       
        //    67: goto            216
        //    70: iload           5
        //    72: bipush          123
        //    74: if_icmpeq       196
        //    77: iload           5
        //    79: bipush          91
        //    81: if_icmpeq       196
        //    84: iload           5
        //    86: bipush          125
        //    88: if_icmpne       140
        //    91: aload_3        
        //    92: invokevirtual   java/util/Stack.isEmpty:()Z
        //    95: ifne            113
        //    98: aload_3        
        //    99: invokevirtual   java/util/Stack.pop:()Ljava/lang/Object;
        //   102: checkcast       Ljava/lang/Character;
        //   105: invokevirtual   java/lang/Character.charValue:()C
        //   108: bipush          123
        //   110: if_icmpeq       140
        //   113: new             Lnet/minecraft/nbt/NBTException;
        //   116: dup            
        //   117: new             Ljava/lang/StringBuilder;
        //   120: dup            
        //   121: invokespecial   java/lang/StringBuilder.<init>:()V
        //   124: ldc             "Unbalanced curly brackets {}: "
        //   126: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   129: aload_0        
        //   130: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   133: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   136: invokespecial   net/minecraft/nbt/NBTException.<init>:(Ljava/lang/String;)V
        //   139: athrow         
        //   140: iload           5
        //   142: bipush          93
        //   144: if_icmpne       216
        //   147: aload_3        
        //   148: invokevirtual   java/util/Stack.isEmpty:()Z
        //   151: ifne            169
        //   154: aload_3        
        //   155: invokevirtual   java/util/Stack.pop:()Ljava/lang/Object;
        //   158: checkcast       Ljava/lang/Character;
        //   161: invokevirtual   java/lang/Character.charValue:()C
        //   164: bipush          91
        //   166: if_icmpeq       216
        //   169: new             Lnet/minecraft/nbt/NBTException;
        //   172: dup            
        //   173: new             Ljava/lang/StringBuilder;
        //   176: dup            
        //   177: invokespecial   java/lang/StringBuilder.<init>:()V
        //   180: ldc             "Unbalanced square brackets []: "
        //   182: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   185: aload_0        
        //   186: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   189: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   192: invokespecial   net/minecraft/nbt/NBTException.<init>:(Ljava/lang/String;)V
        //   195: athrow         
        //   196: aload_3        
        //   197: invokevirtual   java/util/Stack.isEmpty:()Z
        //   200: ifeq            206
        //   203: iinc            1, 1
        //   206: aload_3        
        //   207: iload           5
        //   209: invokestatic    java/lang/Character.valueOf:(C)Ljava/lang/Character;
        //   212: invokevirtual   java/util/Stack.push:(Ljava/lang/Object;)Ljava/lang/Object;
        //   215: pop            
        //   216: iinc            4, 1
        //   219: goto            8
        //   222: goto            252
        //   225: new             Lnet/minecraft/nbt/NBTException;
        //   228: dup            
        //   229: new             Ljava/lang/StringBuilder;
        //   232: dup            
        //   233: invokespecial   java/lang/StringBuilder.<init>:()V
        //   236: ldc             "Unbalanced quotation: "
        //   238: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   241: aload_0        
        //   242: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   245: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   248: invokespecial   net/minecraft/nbt/NBTException.<init>:(Ljava/lang/String;)V
        //   251: athrow         
        //   252: aload_3        
        //   253: invokevirtual   java/util/Stack.isEmpty:()Z
        //   256: ifne            286
        //   259: new             Lnet/minecraft/nbt/NBTException;
        //   262: dup            
        //   263: new             Ljava/lang/StringBuilder;
        //   266: dup            
        //   267: invokespecial   java/lang/StringBuilder.<init>:()V
        //   270: ldc             "Unbalanced brackets: "
        //   272: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   275: aload_0        
        //   276: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   279: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   282: invokespecial   net/minecraft/nbt/NBTException.<init>:(Ljava/lang/String;)V
        //   285: athrow         
        //   286: goto            296
        //   289: aload_0        
        //   290: invokevirtual   java/lang/String.isEmpty:()Z
        //   293: ifne            296
        //   296: iconst_1       
        //   297: ireturn        
        //    Exceptions:
        //  throws net.minecraft.nbt.NBTException
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0216 (coming from #0067).
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
    
    private static String func_150313_b(String trim, final boolean b) throws NBTException {
        if (b) {
            trim = trim.trim();
            if (trim.startsWith("{") || trim.startsWith("[")) {
                return "";
            }
        }
        final int func_150312_a = func_150312_a(trim, ':');
        if (func_150312_a != -1) {
            return trim.substring(0, func_150312_a).trim();
        }
        if (b) {
            return "";
        }
        throw new NBTException("Unable to locate name/value separator for string: " + trim);
    }
    
    static class Primitive extends Any
    {
        private static final Pattern DOUBLE_UNTYPED;
        private static final Pattern FLOAT;
        private static final Pattern BYTE;
        private static final Splitter SPLITTER;
        private static final Pattern SHORT;
        private static final Pattern DOUBLE;
        private static final Pattern INTEGER;
        protected String jsonValue;
        private static final Pattern LONG;
        
        public Primitive(final String json, final String jsonValue) {
            this.json = json;
            this.jsonValue = jsonValue;
        }
        
        @Override
        public NBTBase parse() throws NBTException {
            if (Primitive.DOUBLE.matcher(this.jsonValue).matches()) {
                return new NBTTagDouble(Double.parseDouble(this.jsonValue.substring(0, this.jsonValue.length() - 1)));
            }
            if (Primitive.FLOAT.matcher(this.jsonValue).matches()) {
                return new NBTTagFloat(Float.parseFloat(this.jsonValue.substring(0, this.jsonValue.length() - 1)));
            }
            if (Primitive.BYTE.matcher(this.jsonValue).matches()) {
                return new NBTTagByte(Byte.parseByte(this.jsonValue.substring(0, this.jsonValue.length() - 1)));
            }
            if (Primitive.LONG.matcher(this.jsonValue).matches()) {
                return new NBTTagLong(Long.parseLong(this.jsonValue.substring(0, this.jsonValue.length() - 1)));
            }
            if (Primitive.SHORT.matcher(this.jsonValue).matches()) {
                return new NBTTagShort(Short.parseShort(this.jsonValue.substring(0, this.jsonValue.length() - 1)));
            }
            if (Primitive.INTEGER.matcher(this.jsonValue).matches()) {
                return new NBTTagInt(Integer.parseInt(this.jsonValue));
            }
            if (Primitive.DOUBLE_UNTYPED.matcher(this.jsonValue).matches()) {
                return new NBTTagDouble(Double.parseDouble(this.jsonValue));
            }
            if (this.jsonValue.equalsIgnoreCase("true") || this.jsonValue.equalsIgnoreCase("false")) {
                return new NBTTagByte((byte)(Boolean.parseBoolean(this.jsonValue) ? 1 : 0));
            }
            if (this.jsonValue.startsWith("[") && this.jsonValue.endsWith("]")) {
                final String[] array = (String[])Iterables.toArray(Primitive.SPLITTER.split((CharSequence)this.jsonValue.substring(1, this.jsonValue.length() - 1)), (Class)String.class);
                final int[] array2 = new int[array.length];
                while (0 < array.length) {
                    array2[0] = Integer.parseInt(array[0].trim());
                    int n = 0;
                    ++n;
                }
                return new NBTTagIntArray(array2);
            }
            if (this.jsonValue.startsWith("\"") && this.jsonValue.endsWith("\"")) {
                this.jsonValue = this.jsonValue.substring(1, this.jsonValue.length() - 1);
            }
            this.jsonValue = this.jsonValue.replaceAll("\\\\\"", "\"");
            final StringBuilder sb = new StringBuilder();
            while (0 < this.jsonValue.length()) {
                int n2 = 0;
                if (0 < this.jsonValue.length() - 1 && this.jsonValue.charAt(0) == '\\' && this.jsonValue.charAt(1) == '\\') {
                    sb.append('\\');
                    ++n2;
                }
                else {
                    sb.append(this.jsonValue.charAt(0));
                }
                ++n2;
            }
            return new NBTTagString(sb.toString());
        }
        
        static {
            DOUBLE = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+[d|D]");
            FLOAT = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+[f|F]");
            BYTE = Pattern.compile("[-+]?[0-9]+[b|B]");
            LONG = Pattern.compile("[-+]?[0-9]+[l|L]");
            SHORT = Pattern.compile("[-+]?[0-9]+[s|S]");
            INTEGER = Pattern.compile("[-+]?[0-9]+");
            DOUBLE_UNTYPED = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+");
            SPLITTER = Splitter.on(',').omitEmptyStrings();
        }
    }
    
    abstract static class Any
    {
        protected String json;
        
        public abstract NBTBase parse() throws NBTException;
    }
    
    static class List extends Any
    {
        protected java.util.List field_150492_b;
        
        @Override
        public NBTBase parse() throws NBTException {
            final NBTTagList list = new NBTTagList();
            final Iterator<Any> iterator = this.field_150492_b.iterator();
            while (iterator.hasNext()) {
                list.appendTag(iterator.next().parse());
            }
            return list;
        }
        
        public List(final String json) {
            this.field_150492_b = Lists.newArrayList();
            this.json = json;
        }
    }
    
    static class Compound extends Any
    {
        protected java.util.List field_150491_b;
        
        @Override
        public NBTBase parse() throws NBTException {
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            for (final Any any : this.field_150491_b) {
                nbtTagCompound.setTag(any.json, any.parse());
            }
            return nbtTagCompound;
        }
        
        public Compound(final String json) {
            this.field_150491_b = Lists.newArrayList();
            this.json = json;
        }
    }
}
