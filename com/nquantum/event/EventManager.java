package com.nquantum.event;

import java.util.*;
import java.lang.reflect.*;

public class EventManager
{
    private Map REGISTRY_MAP;
    
    public void register(final Object p0, final Class p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   java/lang/Object.getClass:()Ljava/lang/Class;
        //     4: invokevirtual   java/lang/Class.getDeclaredMethods:()[Ljava/lang/reflect/Method;
        //     7: astore_3       
        //     8: aload_3        
        //     9: arraylength    
        //    10: istore          4
        //    12: iconst_0       
        //    13: iload           4
        //    15: if_icmpge       43
        //    18: aload_3        
        //    19: iconst_0       
        //    20: aaload         
        //    21: astore          6
        //    23: aload_0        
        //    24: aload           6
        //    26: aload_2        
        //    27: ifne            37
        //    30: aload_0        
        //    31: aload           6
        //    33: aload_1        
        //    34: invokespecial   com/nquantum/event/EventManager.register:(Ljava/lang/reflect/Method;Ljava/lang/Object;)V
        //    37: iinc            5, 1
        //    40: goto            12
        //    43: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0012 (coming from #0040).
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
    
    private void sortListValue(final Class clazz) {
        final ArrayHelper arrayHelper = new ArrayHelper();
        final byte[] value_ARRAY = Priority.VALUE_ARRAY;
        while (0 < value_ARRAY.length) {
            final byte b = value_ARRAY[0];
            for (final Data data : this.REGISTRY_MAP.get(clazz)) {
                if (data.priority == b) {
                    arrayHelper.add(data);
                }
            }
            int n = 0;
            ++n;
        }
        this.REGISTRY_MAP.put(clazz, arrayHelper);
    }
    
    public void shutdown() {
        this.REGISTRY_MAP.clear();
    }
    
    public ArrayHelper get(final Class clazz) {
        return this.REGISTRY_MAP.get(clazz);
    }
    
    public EventManager() {
        this.REGISTRY_MAP = new HashMap();
    }
    
    public void register(final Object o) {
        final Method[] declaredMethods = o.getClass().getDeclaredMethods();
        while (0 < declaredMethods.length) {
            final Method method = declaredMethods[0];
            if (this == method) {
                this.register(method, o);
            }
            int n = 0;
            ++n;
        }
    }
    
    public void removeEnty(final Class clazz) {
        final Iterator<Map.Entry<Class<?>, V>> iterator = this.REGISTRY_MAP.entrySet().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getKey().equals(clazz)) {
                iterator.remove();
                break;
            }
        }
    }
    
    public void cleanMap(final boolean b) {
        final Iterator<Map.Entry<K, ArrayHelper>> iterator = this.REGISTRY_MAP.entrySet().iterator();
        while (iterator.hasNext()) {
            if (!b || iterator.next().getValue().isEmpty()) {
                iterator.remove();
            }
        }
    }
    
    public void unregister(final Object o, final Class clazz) {
        if (this.REGISTRY_MAP.containsKey(clazz)) {
            for (final Data data : this.REGISTRY_MAP.get(clazz)) {
                if (data.source.equals(o)) {
                    this.REGISTRY_MAP.get(clazz).remove(data);
                }
            }
            this.cleanMap(true);
        }
    }
    
    public void unregister(final Object o) {
        for (final ArrayHelper arrayHelper : this.REGISTRY_MAP.values()) {
            for (final Data data : arrayHelper) {
                if (data.source.equals(o)) {
                    arrayHelper.remove(data);
                }
            }
        }
        this.cleanMap(true);
    }
    
    private void register(final Method method, final Object o) {
        final Class<?> clazz = method.getParameterTypes()[0];
        final Data data = new Data(o, method, method.getAnnotation(Punjabi.class).value());
        if (!data.target.isAccessible()) {
            data.target.setAccessible(true);
        }
        if (this.REGISTRY_MAP.containsKey(clazz)) {
            if (!this.REGISTRY_MAP.get(clazz).contains((Object)data)) {
                this.REGISTRY_MAP.get(clazz).add(data);
                this.sortListValue(clazz);
            }
        }
        else {
            this.REGISTRY_MAP.put(clazz, new ArrayHelper(this, data) {
                final EventManager this$0;
                final Data val$methodData;
                
                {
                    this.add(this.val$methodData);
                }
            });
        }
    }
}
