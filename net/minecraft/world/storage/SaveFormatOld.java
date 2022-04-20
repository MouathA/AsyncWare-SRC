package net.minecraft.world.storage;

import org.apache.logging.log4j.*;
import java.io.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.client.*;

public class SaveFormatOld implements ISaveFormat
{
    private static final Logger logger;
    protected final File savesDirectory;
    
    static {
        logger = LogManager.getLogger();
    }
    
    public SaveFormatOld(final File savesDirectory) {
        if (!savesDirectory.exists()) {
            savesDirectory.mkdirs();
        }
        this.savesDirectory = savesDirectory;
    }
    
    @Override
    public WorldInfo getWorldInfo(final String s) {
        final File file = new File(this.savesDirectory, s);
        if (!file.exists()) {
            return null;
        }
        final File file2 = new File(file, "level.dat");
        if (file2.exists()) {
            return new WorldInfo(CompressedStreamTools.readCompressed(new FileInputStream(file2)).getCompoundTag("Data"));
        }
        final File file3 = new File(file, "level.dat_old");
        if (file3.exists()) {
            return new WorldInfo(CompressedStreamTools.readCompressed(new FileInputStream(file3)).getCompoundTag("Data"));
        }
        return null;
    }
    
    @Override
    public void renameWorld(final String s, final String s2) {
        final File file = new File(this.savesDirectory, s);
        if (file.exists()) {
            final File file2 = new File(file, "level.dat");
            if (file2.exists()) {
                final NBTTagCompound compressed = CompressedStreamTools.readCompressed(new FileInputStream(file2));
                compressed.getCompoundTag("Data").setString("LevelName", s2);
                CompressedStreamTools.writeCompressed(compressed, new FileOutputStream(file2));
            }
        }
    }
    
    @Override
    public ISaveHandler getSaveLoader(final String s, final boolean b) {
        return new SaveHandler(this.savesDirectory, s, b);
    }
    
    @Override
    public boolean convertMapFormat(final String s, final IProgressUpdate progressUpdate) {
        return false;
    }
    
    @Override
    public boolean canLoadWorld(final String s) {
        return new File(this.savesDirectory, s).isDirectory();
    }
    
    @Override
    public String getName() {
        return "Old Format";
    }
    
    @Override
    public boolean deleteWorldDirectory(final String p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: aload_0        
        //     5: getfield        net/minecraft/world/storage/SaveFormatOld.savesDirectory:Ljava/io/File;
        //     8: aload_1        
        //     9: invokespecial   java/io/File.<init>:(Ljava/io/File;Ljava/lang/String;)V
        //    12: astore_2       
        //    13: aload_2        
        //    14: invokevirtual   java/io/File.exists:()Z
        //    17: ifne            22
        //    20: iconst_1       
        //    21: ireturn        
        //    22: getstatic       net/minecraft/world/storage/SaveFormatOld.logger:Lorg/apache/logging/log4j/Logger;
        //    25: new             Ljava/lang/StringBuilder;
        //    28: dup            
        //    29: invokespecial   java/lang/StringBuilder.<init>:()V
        //    32: ldc             "Deleting level "
        //    34: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    37: aload_1        
        //    38: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    41: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    44: invokeinterface org/apache/logging/log4j/Logger.info:(Ljava/lang/String;)V
        //    49: getstatic       net/minecraft/world/storage/SaveFormatOld.logger:Lorg/apache/logging/log4j/Logger;
        //    52: new             Ljava/lang/StringBuilder;
        //    55: dup            
        //    56: invokespecial   java/lang/StringBuilder.<init>:()V
        //    59: ldc             "Attempt "
        //    61: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    64: iconst_1       
        //    65: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //    68: ldc             "..."
        //    70: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    73: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    76: invokeinterface org/apache/logging/log4j/Logger.info:(Ljava/lang/String;)V
        //    81: aload_2        
        //    82: invokevirtual   java/io/File.listFiles:()[Ljava/io/File;
        //    85: if_icmpge       91
        //    88: goto            118
        //    91: getstatic       net/minecraft/world/storage/SaveFormatOld.logger:Lorg/apache/logging/log4j/Logger;
        //    94: ldc             "Unsuccessful in deleting contents."
        //    96: invokeinterface org/apache/logging/log4j/Logger.warn:(Ljava/lang/String;)V
        //   101: ldc2_w          500
        //   104: invokestatic    java/lang/Thread.sleep:(J)V
        //   107: goto            112
        //   110: astore          4
        //   112: iinc            3, 1
        //   115: goto            49
        //   118: aload_2        
        //   119: invokevirtual   java/io/File.delete:()Z
        //   122: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public boolean isOldMapFormat(final String s) {
        return false;
    }
    
    @Override
    public void flushCache() {
    }
    
    @Override
    public List getSaveList() throws AnvilConverterException {
        final ArrayList arrayList = Lists.newArrayList();
        while (true) {
            final String string = "World" + 1;
            final WorldInfo worldInfo = this.getWorldInfo(string);
            if (worldInfo != null) {
                arrayList.add(new SaveFormatComparator(string, "", worldInfo.getLastTimePlayed(), worldInfo.getSizeOnDisk(), worldInfo.getGameType(), false, worldInfo.isHardcoreModeEnabled(), worldInfo.areCommandsAllowed()));
            }
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public boolean func_154335_d(final String s) {
        final File file = new File(this.savesDirectory, s);
        if (file.exists()) {
            return false;
        }
        file.mkdir();
        file.delete();
        return true;
    }
    
    @Override
    public boolean func_154334_a(final String s) {
        return false;
    }
}
