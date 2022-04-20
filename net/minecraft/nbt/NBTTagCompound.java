package net.minecraft.nbt;

import java.util.concurrent.*;
import net.minecraft.crash.*;
import com.google.common.collect.*;
import java.io.*;
import java.util.*;

public class NBTTagCompound extends NBTBase
{
    private Map tagMap;
    
    @Override
    void read(final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        nbtSizeTracker.read(384L);
        if (n > 512) {
            throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
        }
        this.tagMap.clear();
        byte type;
        while ((type = readType(dataInput, nbtSizeTracker)) != 0) {
            final String key = readKey(dataInput, nbtSizeTracker);
            nbtSizeTracker.read(224 + 16 * key.length());
            if (this.tagMap.put(key, readNBT(type, key, dataInput, n + 1, nbtSizeTracker)) != null) {
                nbtSizeTracker.read(288L);
            }
        }
    }
    
    @Override
    public byte getId() {
        return 10;
    }
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o) && this.tagMap.entrySet().equals(((NBTTagCompound)o).tagMap.entrySet());
    }
    
    public void setString(final String s, final String s2) {
        this.tagMap.put(s, new NBTTagString(s2));
    }
    
    private static String readKey(final DataInput dataInput, final NBTSizeTracker nbtSizeTracker) throws IOException {
        return dataInput.readUTF();
    }
    
    public void setByteArray(final String s, final byte[] array) {
        this.tagMap.put(s, new NBTTagByteArray(array));
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ this.tagMap.hashCode();
    }
    
    private static byte readType(final DataInput dataInput, final NBTSizeTracker nbtSizeTracker) throws IOException {
        return dataInput.readByte();
    }
    
    public NBTBase getTag(final String s) {
        return this.tagMap.get(s);
    }
    
    @Override
    public NBTBase copy() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        for (final String s : this.tagMap.keySet()) {
            nbtTagCompound.setTag(s, ((NBTBase)this.tagMap.get(s)).copy());
        }
        return nbtTagCompound;
    }
    
    public float getFloat(final String s) {
        return (s == 99) ? 0.0f : this.tagMap.get(s).getFloat();
    }
    
    public boolean hasKey(final String s) {
        return this.tagMap.containsKey(s);
    }
    
    public void removeTag(final String s) {
        this.tagMap.remove(s);
    }
    
    public short getShort(final String s) {
        return (short)((s == 99) ? 0 : this.tagMap.get(s).getShort());
    }
    
    private CrashReport createCrashReport(final String s, final int n, final ClassCastException ex) {
        final CrashReport crashReport = CrashReport.makeCrashReport(ex, "Reading NBT data");
        final CrashReportCategory categoryDepth = crashReport.makeCategoryDepth("Corrupt NBT tag", 1);
        categoryDepth.addCrashSectionCallable("Tag type found", new Callable(this, s) {
            final NBTTagCompound this$0;
            final String val$key;
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            @Override
            public String call() throws Exception {
                return NBTBase.NBT_TYPES[NBTTagCompound.access$000(this.this$0).get(this.val$key).getId()];
            }
        });
        categoryDepth.addCrashSectionCallable("Tag type expected", new Callable(this, n) {
            final int val$expectedType;
            final NBTTagCompound this$0;
            
            @Override
            public String call() throws Exception {
                return NBTBase.NBT_TYPES[this.val$expectedType];
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
        });
        categoryDepth.addCrashSection("Tag name", s);
        return crashReport;
    }
    
    public void setFloat(final String s, final float n) {
        this.tagMap.put(s, new NBTTagFloat(n));
    }
    
    public NBTTagCompound() {
        this.tagMap = Maps.newHashMap();
    }
    
    public void setShort(final String s, final short n) {
        this.tagMap.put(s, new NBTTagShort(n));
    }
    
    public byte getTagId(final String s) {
        final NBTBase nbtBase = this.tagMap.get(s);
        return (byte)((nbtBase != null) ? nbtBase.getId() : 0);
    }
    
    @Override
    void write(final DataOutput dataOutput) throws IOException {
        for (final String s : this.tagMap.keySet()) {
            writeEntry(s, (NBTBase)this.tagMap.get(s), dataOutput);
        }
        dataOutput.writeByte(0);
    }
    
    public long getLong(final String s) {
        return (s == 99) ? 0L : this.tagMap.get(s).getLong();
    }
    
    public NBTTagList getTagList(final String s, final int n) {
        if (this.getTagId(s) != 9) {
            return new NBTTagList();
        }
        final NBTTagList list = this.tagMap.get(s);
        return (list.tagCount() > 0 && list.getTagType() != n) ? new NBTTagList() : list;
    }
    
    public void setBoolean(final String s, final boolean b) {
        this.setByte(s, (byte)(b ? 1 : 0));
    }
    
    private static void writeEntry(final String s, final NBTBase nbtBase, final DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(nbtBase.getId());
        if (nbtBase.getId() != 0) {
            dataOutput.writeUTF(s);
            nbtBase.write(dataOutput);
        }
    }
    
    public int[] getIntArray(final String s) {
        return (s == 11) ? new int[0] : this.tagMap.get(s).getIntArray();
    }
    
    public byte[] getByteArray(final String s) {
        return (s == 7) ? new byte[0] : this.tagMap.get(s).getByteArray();
    }
    
    public void setDouble(final String s, final double n) {
        this.tagMap.put(s, new NBTTagDouble(n));
    }
    
    public double getDouble(final String s) {
        return (s == 99) ? 0.0 : this.tagMap.get(s).getDouble();
    }
    
    public void setIntArray(final String s, final int[] array) {
        this.tagMap.put(s, new NBTTagIntArray(array));
    }
    
    static NBTBase readNBT(final byte b, final String s, final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        final NBTBase newByType = NBTBase.createNewByType(b);
        newByType.read(dataInput, n, nbtSizeTracker);
        return newByType;
    }
    
    public String getString(final String s) {
        return (s == 8) ? "" : this.tagMap.get(s).getString();
    }
    
    public byte getByte(final String s) {
        return (byte)((s == 99) ? 0 : this.tagMap.get(s).getByte());
    }
    
    public void setByte(final String s, final byte b) {
        this.tagMap.put(s, new NBTTagByte(b));
    }
    
    public void setTag(final String s, final NBTBase nbtBase) {
        this.tagMap.put(s, nbtBase);
    }
    
    public int getInteger(final String s) {
        return (s == 99) ? 0 : this.tagMap.get(s).getInt();
    }
    
    public void setInteger(final String s, final int n) {
        this.tagMap.put(s, new NBTTagInt(n));
    }
    
    @Override
    public boolean hasNoTags() {
        return this.tagMap.isEmpty();
    }
    
    public void merge(final NBTTagCompound p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/nbt/NBTTagCompound.tagMap:Ljava/util/Map;
        //     4: invokeinterface java/util/Map.keySet:()Ljava/util/Set;
        //     9: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
        //    14: astore_2       
        //    15: aload_2        
        //    16: invokeinterface java/util/Iterator.hasNext:()Z
        //    21: ifeq            112
        //    24: aload_2        
        //    25: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    30: checkcast       Ljava/lang/String;
        //    33: astore_3       
        //    34: aload_1        
        //    35: getfield        net/minecraft/nbt/NBTTagCompound.tagMap:Ljava/util/Map;
        //    38: aload_3        
        //    39: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //    44: checkcast       Lnet/minecraft/nbt/NBTBase;
        //    47: astore          4
        //    49: aload           4
        //    51: invokevirtual   net/minecraft/nbt/NBTBase.getId:()B
        //    54: bipush          10
        //    56: if_icmpne       99
        //    59: aload_0        
        //    60: aload_3        
        //    61: bipush          10
        //    63: if_icmpne       86
        //    66: aload_0        
        //    67: aload_3        
        //    68: invokevirtual   net/minecraft/nbt/NBTTagCompound.getCompoundTag:(Ljava/lang/String;)Lnet/minecraft/nbt/NBTTagCompound;
        //    71: astore          5
        //    73: aload           5
        //    75: aload           4
        //    77: checkcast       Lnet/minecraft/nbt/NBTTagCompound;
        //    80: invokevirtual   net/minecraft/nbt/NBTTagCompound.merge:(Lnet/minecraft/nbt/NBTTagCompound;)V
        //    83: goto            15
        //    86: aload_0        
        //    87: aload_3        
        //    88: aload           4
        //    90: invokevirtual   net/minecraft/nbt/NBTBase.copy:()Lnet/minecraft/nbt/NBTBase;
        //    93: invokevirtual   net/minecraft/nbt/NBTTagCompound.setTag:(Ljava/lang/String;Lnet/minecraft/nbt/NBTBase;)V
        //    96: goto            15
        //    99: aload_0        
        //   100: aload_3        
        //   101: aload           4
        //   103: invokevirtual   net/minecraft/nbt/NBTBase.copy:()Lnet/minecraft/nbt/NBTBase;
        //   106: invokevirtual   net/minecraft/nbt/NBTTagCompound.setTag:(Ljava/lang/String;Lnet/minecraft/nbt/NBTBase;)V
        //   109: goto            15
        //   112: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0015 (coming from #0096).
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
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        for (final Map.Entry<String, V> entry : this.tagMap.entrySet()) {
            if (sb.length() != 1) {
                sb.append(',');
            }
            sb.append(entry.getKey()).append(':').append(entry.getValue());
        }
        return sb.append('}').toString();
    }
    
    public boolean getBoolean(final String s) {
        return this.getByte(s) != 0;
    }
    
    public NBTTagCompound getCompoundTag(final String s) {
        return (s == 10) ? new NBTTagCompound() : this.tagMap.get(s);
    }
    
    public Set getKeySet() {
        return this.tagMap.keySet();
    }
    
    public void setLong(final String s, final long n) {
        this.tagMap.put(s, new NBTTagLong(n));
    }
    
    static Map access$000(final NBTTagCompound nbtTagCompound) {
        return nbtTagCompound.tagMap;
    }
}
