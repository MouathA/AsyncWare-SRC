package net.minecraft.nbt;

import java.util.zip.*;
import java.io.*;

public class CompressedStreamTools
{
    public static NBTTagCompound read(final DataInputStream dataInputStream) throws IOException {
        return read(dataInputStream, NBTSizeTracker.INFINITE);
    }
    
    public static void write(final NBTTagCompound nbtTagCompound, final DataOutput dataOutput) throws IOException {
        writeTag(nbtTagCompound, dataOutput);
    }
    
    public static NBTTagCompound read(final File file) throws IOException {
        if (!file.exists()) {
            return null;
        }
        final DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
        final NBTTagCompound read = read(dataInputStream, NBTSizeTracker.INFINITE);
        dataInputStream.close();
        return read;
    }
    
    private static void writeTag(final NBTBase nbtBase, final DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(nbtBase.getId());
        if (nbtBase.getId() != 0) {
            dataOutput.writeUTF("");
            nbtBase.write(dataOutput);
        }
    }
    
    public static NBTTagCompound read(final DataInput dataInput, final NBTSizeTracker nbtSizeTracker) throws IOException {
        final NBTBase func_152455_a = func_152455_a(dataInput, 0, nbtSizeTracker);
        if (func_152455_a instanceof NBTTagCompound) {
            return (NBTTagCompound)func_152455_a;
        }
        throw new IOException("Root tag must be a named compound tag");
    }
    
    public static void writeCompressed(final NBTTagCompound nbtTagCompound, final OutputStream outputStream) throws IOException {
        final DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(outputStream)));
        write(nbtTagCompound, dataOutputStream);
        dataOutputStream.close();
    }
    
    public static NBTTagCompound readCompressed(final InputStream inputStream) throws IOException {
        final DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new GZIPInputStream(inputStream)));
        final NBTTagCompound read = read(dataInputStream, NBTSizeTracker.INFINITE);
        dataInputStream.close();
        return read;
    }
    
    public static void safeWrite(final NBTTagCompound nbtTagCompound, final File file) throws IOException {
        final File file2 = new File(file.getAbsolutePath() + "_tmp");
        if (file2.exists()) {
            file2.delete();
        }
        write(nbtTagCompound, file2);
        if (file.exists()) {
            file.delete();
        }
        if (file.exists()) {
            throw new IOException("Failed to delete " + file);
        }
        file2.renameTo(file);
    }
    
    public static void write(final NBTTagCompound nbtTagCompound, final File file) throws IOException {
        final DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file));
        write(nbtTagCompound, dataOutputStream);
        dataOutputStream.close();
    }
    
    private static NBTBase func_152455_a(final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        final byte byte1 = dataInput.readByte();
        if (byte1 == 0) {
            return new NBTTagEnd();
        }
        dataInput.readUTF();
        final NBTBase newByType = NBTBase.createNewByType(byte1);
        newByType.read(dataInput, n, nbtSizeTracker);
        return newByType;
    }
}
