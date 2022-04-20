package net.minecraft.stats;

import java.util.*;
import net.minecraft.entity.player.*;
import com.google.common.collect.*;
import net.minecraft.util.*;

public class StatFileWriter
{
    protected final Map statsData;
    
    public void unlockAchievement(final EntityPlayer entityPlayer, final StatBase statBase, final int integerValue) {
        TupleIntJsonSerializable tupleIntJsonSerializable = this.statsData.get(statBase);
        if (tupleIntJsonSerializable == null) {
            tupleIntJsonSerializable = new TupleIntJsonSerializable();
            this.statsData.put(statBase, tupleIntJsonSerializable);
        }
        tupleIntJsonSerializable.setIntegerValue(integerValue);
    }
    
    public StatFileWriter() {
        this.statsData = Maps.newConcurrentMap();
    }
    
    public IJsonSerializable func_150870_b(final StatBase statBase) {
        final TupleIntJsonSerializable tupleIntJsonSerializable = this.statsData.get(statBase);
        return (tupleIntJsonSerializable != null) ? tupleIntJsonSerializable.getJsonSerializableValue() : null;
    }
    
    public int func_150874_c(final Achievement p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1        
        //     2: ifle            7
        //     5: iconst_0       
        //     6: ireturn        
        //     7: aload_1        
        //     8: getfield        net/minecraft/stats/Achievement.parentAchievement:Lnet/minecraft/stats/Achievement;
        //    11: astore_3       
        //    12: aload_3        
        //    13: ifnull          32
        //    16: aload_0        
        //    17: aload_3        
        //    18: ifle            32
        //    21: aload_3        
        //    22: getfield        net/minecraft/stats/Achievement.parentAchievement:Lnet/minecraft/stats/Achievement;
        //    25: astore_3       
        //    26: iinc            2, 1
        //    29: goto            12
        //    32: iconst_0       
        //    33: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0032 (coming from #0018).
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
    
    public int readStat(final StatBase statBase) {
        final TupleIntJsonSerializable tupleIntJsonSerializable = this.statsData.get(statBase);
        return (tupleIntJsonSerializable == null) ? 0 : tupleIntJsonSerializable.getIntegerValue();
    }
    
    public IJsonSerializable func_150872_a(final StatBase statBase, final IJsonSerializable jsonSerializableValue) {
        TupleIntJsonSerializable tupleIntJsonSerializable = this.statsData.get(statBase);
        if (tupleIntJsonSerializable == null) {
            tupleIntJsonSerializable = new TupleIntJsonSerializable();
            this.statsData.put(statBase, tupleIntJsonSerializable);
        }
        tupleIntJsonSerializable.setJsonSerializableValue(jsonSerializableValue);
        return jsonSerializableValue;
    }
    
    public void increaseStat(final EntityPlayer p0, final StatBase p1, final int p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   net/minecraft/stats/StatBase.isAchievement:()Z
        //     4: ifeq            15
        //     7: aload_0        
        //     8: aload_2        
        //     9: checkcast       Lnet/minecraft/stats/Achievement;
        //    12: ifnull          28
        //    15: aload_0        
        //    16: aload_1        
        //    17: aload_2        
        //    18: aload_0        
        //    19: aload_2        
        //    20: invokevirtual   net/minecraft/stats/StatFileWriter.readStat:(Lnet/minecraft/stats/StatBase;)I
        //    23: iload_3        
        //    24: iadd           
        //    25: invokevirtual   net/minecraft/stats/StatFileWriter.unlockAchievement:(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/stats/StatBase;I)V
        //    28: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0015 (coming from #0012).
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
