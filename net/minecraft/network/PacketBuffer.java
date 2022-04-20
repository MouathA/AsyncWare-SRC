package net.minecraft.network;

import net.minecraft.item.*;
import java.nio.*;
import net.minecraft.util.*;
import java.nio.charset.*;
import com.google.common.base.*;
import java.util.*;
import io.netty.util.*;
import java.nio.channels.*;
import io.netty.handler.codec.*;
import io.netty.buffer.*;
import net.minecraft.nbt.*;
import java.io.*;

public class PacketBuffer extends ByteBuf
{
    private final ByteBuf buf;
    
    public ByteBuf duplicate() {
        return this.buf.duplicate();
    }
    
    public ByteBuf capacity(final int n) {
        return this.buf.capacity(n);
    }
    
    public int forEachByte(final int n, final int n2, final ByteBufProcessor byteBufProcessor) {
        return this.buf.forEachByte(n, n2, byteBufProcessor);
    }
    
    public boolean isWritable() {
        return this.buf.isWritable();
    }
    
    public char getChar(final int n) {
        return this.buf.getChar(n);
    }
    
    public long readVarLong() {
        long n = 0L;
        byte byte1;
        do {
            byte1 = this.readByte();
            final long n2 = n;
            final long n3 = byte1 & 0x7F;
            final int n4 = 0;
            int n5 = 0;
            ++n5;
            n = (n2 | n3 << n4 * 7);
        } while ((byte1 & 0x80) == 0x80);
        return n;
    }
    
    public int indexOf(final int n, final int n2, final byte b) {
        return this.buf.indexOf(n, n2, b);
    }
    
    public int maxWritableBytes() {
        return this.buf.maxWritableBytes();
    }
    
    public void writeItemStackToBuffer(final ItemStack itemStack) {
        if (itemStack == null) {
            this.writeShort(-1);
        }
        else {
            this.writeShort(Item.getIdFromItem(itemStack.getItem()));
            this.writeByte(itemStack.stackSize);
            this.writeShort(itemStack.getMetadata());
            NBTTagCompound tagCompound = null;
            if (itemStack.getItem().isDamageable() || itemStack.getItem().getShareTag()) {
                tagCompound = itemStack.getTagCompound();
            }
            this.writeNBTTagCompoundToBuffer(tagCompound);
        }
    }
    
    public ByteBuf readBytes(final ByteBuf byteBuf, final int n, final int n2) {
        return this.buf.readBytes(byteBuf, n, n2);
    }
    
    public boolean release(final int n) {
        return this.buf.release(n);
    }
    
    public ByteBuf resetWriterIndex() {
        return this.buf.resetWriterIndex();
    }
    
    public int hashCode() {
        return this.buf.hashCode();
    }
    
    public int readMedium() {
        return this.buf.readMedium();
    }
    
    public ByteBuf setDouble(final int n, final double n2) {
        return this.buf.setDouble(n, n2);
    }
    
    public short readUnsignedByte() {
        return this.buf.readUnsignedByte();
    }
    
    public int readerIndex() {
        return this.buf.readerIndex();
    }
    
    public ByteBuf writeLong(final long n) {
        return this.buf.writeLong(n);
    }
    
    public boolean isReadable() {
        return this.buf.isReadable();
    }
    
    public Enum readEnumValue(final Class clazz) {
        return ((Enum[])clazz.getEnumConstants())[this.readVarIntFromBuffer()];
    }
    
    public ByteOrder order() {
        return this.buf.order();
    }
    
    public boolean getBoolean(final int n) {
        return this.buf.getBoolean(n);
    }
    
    public int setBytes(final int n, final InputStream inputStream, final int n2) throws IOException {
        return this.buf.setBytes(n, inputStream, n2);
    }
    
    public ByteBuf getBytes(final int n, final ByteBuffer byteBuffer) {
        return this.buf.getBytes(n, byteBuffer);
    }
    
    public ByteBuffer nioBuffer(final int n, final int n2) {
        return this.buf.nioBuffer(n, n2);
    }
    
    public ByteBuffer nioBuffer() {
        return this.buf.nioBuffer();
    }
    
    public void writeVarLong(long n) {
        while ((n & 0xFFFFFFFFFFFFFF80L) != 0x0L) {
            this.writeByte((int)(n & 0x7FL) | 0x80);
            n >>>= 7;
        }
        this.writeByte((int)n);
    }
    
    public ByteBuf getBytes(final int n, final ByteBuf byteBuf, final int n2) {
        return this.buf.getBytes(n, byteBuf, n2);
    }
    
    public ByteBuf getBytes(final int n, final byte[] array) {
        return this.buf.getBytes(n, array);
    }
    
    public ByteBuf setIndex(final int n, final int n2) {
        return this.buf.setIndex(n, n2);
    }
    
    public ByteBuf writeZero(final int n) {
        return this.buf.writeZero(n);
    }
    
    public ByteBuf writeDouble(final double n) {
        return this.buf.writeDouble(n);
    }
    
    public int bytesBefore(final int n, final int n2, final byte b) {
        return this.buf.bytesBefore(n, n2, b);
    }
    
    public int getInt(final int n) {
        return this.buf.getInt(n);
    }
    
    public ByteBuf writeShort(final int n) {
        return this.buf.writeShort(n);
    }
    
    public ByteBuf discardSomeReadBytes() {
        return this.buf.discardSomeReadBytes();
    }
    
    public ByteBuf setShort(final int n, final int n2) {
        return this.buf.setShort(n, n2);
    }
    
    public int readUnsignedShort() {
        return this.buf.readUnsignedShort();
    }
    
    public void writeChatComponent(final IChatComponent chatComponent) throws IOException {
        this.writeString(IChatComponent.Serializer.componentToJson(chatComponent));
    }
    
    public short readShort() {
        return this.buf.readShort();
    }
    
    public ByteBuf readerIndex(final int n) {
        return this.buf.readerIndex(n);
    }
    
    public ByteBuf readSlice(final int n) {
        return this.buf.readSlice(n);
    }
    
    public ByteBuf writerIndex(final int n) {
        return this.buf.writerIndex(n);
    }
    
    public ByteBuf writeBytes(final ByteBuf byteBuf) {
        return this.buf.writeBytes(byteBuf);
    }
    
    public float getFloat(final int n) {
        return this.buf.getFloat(n);
    }
    
    public ByteBuf writeBytes(final ByteBuf byteBuf, final int n) {
        return this.buf.writeBytes(byteBuf, n);
    }
    
    public ByteBufAllocator alloc() {
        return this.buf.alloc();
    }
    
    public ByteBuf setBytes(final int n, final byte[] array) {
        return this.buf.setBytes(n, array);
    }
    
    public ByteBuf readBytes(final byte[] array, final int n, final int n2) {
        return this.buf.readBytes(array, n, n2);
    }
    
    public int capacity() {
        return this.buf.capacity();
    }
    
    public void writeBlockPos(final BlockPos blockPos) {
        this.writeLong(blockPos.toLong());
    }
    
    public ByteBuf setBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
        return this.buf.setBytes(n, byteBuf, n2, n3);
    }
    
    public ByteBuf readBytes(final ByteBuf byteBuf, final int n) {
        return this.buf.readBytes(byteBuf, n);
    }
    
    public ByteBuf markWriterIndex() {
        return this.buf.markWriterIndex();
    }
    
    public int getMedium(final int n) {
        return this.buf.getMedium(n);
    }
    
    public int getUnsignedMedium(final int n) {
        return this.buf.getUnsignedMedium(n);
    }
    
    public String toString(final Charset charset) {
        return this.buf.toString(charset);
    }
    
    public short getShort(final int n) {
        return this.buf.getShort(n);
    }
    
    public ByteBuffer[] nioBuffers(final int n, final int n2) {
        return this.buf.nioBuffers(n, n2);
    }
    
    public ByteBuf writeChar(final int n) {
        return this.buf.writeChar(n);
    }
    
    public ByteBuf setBoolean(final int n, final boolean b) {
        return this.buf.setBoolean(n, b);
    }
    
    public long readUnsignedInt() {
        return this.buf.readUnsignedInt();
    }
    
    public PacketBuffer writeString(final String s) {
        final byte[] bytes = s.getBytes(Charsets.UTF_8);
        if (bytes.length > 32767) {
            throw new EncoderException("String too big (was " + s.length() + " bytes encoded, max " + 32767 + ")");
        }
        this.writeVarIntToBuffer(bytes.length);
        this.writeBytes(bytes);
        return this;
    }
    
    public void writeUuid(final UUID uuid) {
        this.writeLong(uuid.getMostSignificantBits());
        this.writeLong(uuid.getLeastSignificantBits());
    }
    
    public ByteBuf setMedium(final int n, final int n2) {
        return this.buf.setMedium(n, n2);
    }
    
    public int bytesBefore(final byte b) {
        return this.buf.bytesBefore(b);
    }
    
    public int readBytes(final GatheringByteChannel gatheringByteChannel, final int n) throws IOException {
        return this.buf.readBytes(gatheringByteChannel, n);
    }
    
    public byte[] array() {
        return this.buf.array();
    }
    
    public ByteBuf setBytes(final int n, final ByteBuf byteBuf) {
        return this.buf.setBytes(n, byteBuf);
    }
    
    public boolean equals(final Object o) {
        return this.buf.equals(o);
    }
    
    public ByteBuf slice(final int n, final int n2) {
        return this.buf.slice(n, n2);
    }
    
    public int forEachByteDesc(final ByteBufProcessor byteBufProcessor) {
        return this.buf.forEachByteDesc(byteBufProcessor);
    }
    
    public boolean readBoolean() {
        return this.buf.readBoolean();
    }
    
    public ByteBuf setChar(final int n, final int n2) {
        return this.buf.setChar(n, n2);
    }
    
    public int readableBytes() {
        return this.buf.readableBytes();
    }
    
    public ByteBuf unwrap() {
        return this.buf.unwrap();
    }
    
    public ByteBuf clear() {
        return this.buf.clear();
    }
    
    public float readFloat() {
        return this.buf.readFloat();
    }
    
    public long memoryAddress() {
        return this.buf.memoryAddress();
    }
    
    public double getDouble(final int n) {
        return this.buf.getDouble(n);
    }
    
    public long readLong() {
        return this.buf.readLong();
    }
    
    public ByteBuf ensureWritable(final int n) {
        return this.buf.ensureWritable(n);
    }
    
    public int ensureWritable(final int n, final boolean b) {
        return this.buf.ensureWritable(n, b);
    }
    
    public ByteBuf writeByte(final int n) {
        return this.buf.writeByte(n);
    }
    
    public void writeNBTTagCompoundToBuffer(final NBTTagCompound nbtTagCompound) {
        if (nbtTagCompound == null) {
            this.writeByte(0);
        }
        else {
            CompressedStreamTools.write(nbtTagCompound, (DataOutput)new ByteBufOutputStream((ByteBuf)this));
        }
    }
    
    public ByteBuf discardReadBytes() {
        return this.buf.discardReadBytes();
    }
    
    public long getUnsignedInt(final int n) {
        return this.buf.getUnsignedInt(n);
    }
    
    public ByteBuf copy(final int n, final int n2) {
        return this.buf.copy(n, n2);
    }
    
    public ByteBuf writeMedium(final int n) {
        return this.buf.writeMedium(n);
    }
    
    public void writeVarIntToBuffer(int n) {
        while ((n & 0xFFFFFF80) != 0x0) {
            this.writeByte((n & 0x7F) | 0x80);
            n >>>= 7;
        }
        this.writeByte(n);
    }
    
    public ByteBuf writeBytes(final byte[] array, final int n, final int n2) {
        return this.buf.writeBytes(array, n, n2);
    }
    
    public ByteBuf resetReaderIndex() {
        return this.buf.resetReaderIndex();
    }
    
    public int compareTo(final Object o) {
        return this.compareTo((ByteBuf)o);
    }
    
    public ByteBuf order(final ByteOrder byteOrder) {
        return this.buf.order(byteOrder);
    }
    
    public ByteBuf setBytes(final int n, final ByteBuffer byteBuffer) {
        return this.buf.setBytes(n, byteBuffer);
    }
    
    public ByteBuffer[] nioBuffers() {
        return this.buf.nioBuffers();
    }
    
    public int readInt() {
        return this.buf.readInt();
    }
    
    public void writeByteArray(final byte[] array) {
        this.writeVarIntToBuffer(array.length);
        this.writeBytes(array);
    }
    
    public int bytesBefore(final int n, final byte b) {
        return this.buf.bytesBefore(n, b);
    }
    
    public int nioBufferCount() {
        return this.buf.nioBufferCount();
    }
    
    public boolean isReadable(final int n) {
        return this.buf.isReadable(n);
    }
    
    public double readDouble() {
        return this.buf.readDouble();
    }
    
    public ByteBuf readBytes(final OutputStream outputStream, final int n) throws IOException {
        return this.buf.readBytes(outputStream, n);
    }
    
    public ByteBuf writeBoolean(final boolean b) {
        return this.buf.writeBoolean(b);
    }
    
    public ByteBuf retain() {
        return this.buf.retain();
    }
    
    public ReferenceCounted retain(final int n) {
        return (ReferenceCounted)this.retain(n);
    }
    
    public ByteBuf readBytes(final int n) {
        return this.buf.readBytes(n);
    }
    
    public ByteBuf markReaderIndex() {
        return this.buf.markReaderIndex();
    }
    
    public ByteBuf setByte(final int n, final int n2) {
        return this.buf.setByte(n, n2);
    }
    
    public ByteBuf setInt(final int n, final int n2) {
        return this.buf.setInt(n, n2);
    }
    
    public ByteBuf slice() {
        return this.buf.slice();
    }
    
    public ByteBuf getBytes(final int n, final ByteBuf byteBuf) {
        return this.buf.getBytes(n, byteBuf);
    }
    
    public ByteBuf getBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
        return this.buf.getBytes(n, byteBuf, n2, n3);
    }
    
    public String toString(final int n, final int n2, final Charset charset) {
        return this.buf.toString(n, n2, charset);
    }
    
    public short getUnsignedByte(final int n) {
        return this.buf.getUnsignedByte(n);
    }
    
    public ByteBuf setFloat(final int n, final float n2) {
        return this.buf.setFloat(n, n2);
    }
    
    public ByteBuf writeBytes(final ByteBuf byteBuf, final int n, final int n2) {
        return this.buf.writeBytes(byteBuf, n, n2);
    }
    
    public BlockPos readBlockPos() {
        return BlockPos.fromLong(this.readLong());
    }
    
    public int getBytes(final int n, final GatheringByteChannel gatheringByteChannel, final int n2) throws IOException {
        return this.buf.getBytes(n, gatheringByteChannel, n2);
    }
    
    public ByteBuf writeBytes(final ByteBuffer byteBuffer) {
        return this.buf.writeBytes(byteBuffer);
    }
    
    public ByteBuf getBytes(final int n, final OutputStream outputStream, final int n2) throws IOException {
        return this.buf.getBytes(n, outputStream, n2);
    }
    
    public boolean hasMemoryAddress() {
        return this.buf.hasMemoryAddress();
    }
    
    public ByteBuf skipBytes(final int n) {
        return this.buf.skipBytes(n);
    }
    
    public ByteBuf setLong(final int n, final long n2) {
        return this.buf.setLong(n, n2);
    }
    
    public int forEachByte(final ByteBufProcessor byteBufProcessor) {
        return this.buf.forEachByte(byteBufProcessor);
    }
    
    public boolean release() {
        return this.buf.release();
    }
    
    public boolean isWritable(final int n) {
        return this.buf.isWritable(n);
    }
    
    public byte getByte(final int n) {
        return this.buf.getByte(n);
    }
    
    public ByteBuf readBytes(final byte[] array) {
        return this.buf.readBytes(array);
    }
    
    public int forEachByteDesc(final int n, final int n2, final ByteBufProcessor byteBufProcessor) {
        return this.buf.forEachByteDesc(n, n2, byteBufProcessor);
    }
    
    public int writerIndex() {
        return this.buf.writerIndex();
    }
    
    public ByteBuf readBytes(final ByteBuf byteBuf) {
        return this.buf.readBytes(byteBuf);
    }
    
    public long getLong(final int n) {
        return this.buf.getLong(n);
    }
    
    public int writeBytes(final ScatteringByteChannel scatteringByteChannel, final int n) throws IOException {
        return this.buf.writeBytes(scatteringByteChannel, n);
    }
    
    public static int getVarIntSize(final int n) {
        while ((n & 0xFFFFFF80) != 0x0) {
            int n2 = 0;
            ++n2;
        }
        return 1;
    }
    
    public void writeEnumValue(final Enum enum1) {
        this.writeVarIntToBuffer(enum1.ordinal());
    }
    
    public char readChar() {
        return this.buf.readChar();
    }
    
    public ByteBuf writeBytes(final byte[] array) {
        return this.buf.writeBytes(array);
    }
    
    public byte[] readByteArray() {
        final byte[] array = new byte[this.readVarIntFromBuffer()];
        this.readBytes(array);
        return array;
    }
    
    public byte readByte() {
        return this.buf.readByte();
    }
    
    public int readVarIntFromBuffer() {
        byte byte1;
        do {
            byte1 = this.readByte();
            int n = 0;
            ++n;
        } while ((byte1 & 0x80) == 0x80);
        return 0;
    }
    
    public ByteBuf writeFloat(final float n) {
        return this.buf.writeFloat(n);
    }
    
    public int getUnsignedShort(final int n) {
        return this.buf.getUnsignedShort(n);
    }
    
    public ByteBuf setBytes(final int n, final ByteBuf byteBuf, final int n2) {
        return this.buf.setBytes(n, byteBuf, n2);
    }
    
    public String readStringFromBuffer(final int n) {
        final int varIntFromBuffer = this.readVarIntFromBuffer();
        if (varIntFromBuffer > n * 4) {
            throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + varIntFromBuffer + " > " + n * 4 + ")");
        }
        if (varIntFromBuffer < 0) {
            throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
        }
        final String s = new String(this.readBytes(varIntFromBuffer).array(), Charsets.UTF_8);
        if (s.length() > n) {
            throw new DecoderException("The received string length is longer than maximum allowed (" + varIntFromBuffer + " > " + n + ")");
        }
        return s;
    }
    
    public UUID readUuid() {
        return new UUID(this.readLong(), this.readLong());
    }
    
    public boolean hasArray() {
        return this.buf.hasArray();
    }
    
    public int setBytes(final int n, final ScatteringByteChannel scatteringByteChannel, final int n2) throws IOException {
        return this.buf.setBytes(n, scatteringByteChannel, n2);
    }
    
    public int compareTo(final ByteBuf byteBuf) {
        return this.buf.compareTo(byteBuf);
    }
    
    public ByteBuf setBytes(final int n, final byte[] array, final int n2, final int n3) {
        return this.buf.setBytes(n, array, n2, n3);
    }
    
    public ByteBuf retain(final int n) {
        return this.buf.retain(n);
    }
    
    public ByteBuf writeInt(final int n) {
        return this.buf.writeInt(n);
    }
    
    public int writeBytes(final InputStream inputStream, final int n) throws IOException {
        return this.buf.writeBytes(inputStream, n);
    }
    
    public int maxCapacity() {
        return this.buf.maxCapacity();
    }
    
    public ByteBuffer internalNioBuffer(final int n, final int n2) {
        return this.buf.internalNioBuffer(n, n2);
    }
    
    public ReferenceCounted retain() {
        return (ReferenceCounted)this.retain();
    }
    
    public PacketBuffer(final ByteBuf buf) {
        this.buf = buf;
    }
    
    public ByteBuf getBytes(final int n, final byte[] array, final int n2, final int n3) {
        return this.buf.getBytes(n, array, n2, n3);
    }
    
    public ItemStack readItemStackFromBuffer() throws IOException {
        ItemStack itemStack = null;
        final short short1 = this.readShort();
        if (short1 >= 0) {
            itemStack = new ItemStack(Item.getItemById(short1), this.readByte(), this.readShort());
            itemStack.setTagCompound(this.readNBTTagCompoundFromBuffer());
        }
        return itemStack;
    }
    
    public int writableBytes() {
        return this.buf.writableBytes();
    }
    
    public ByteBuf readBytes(final ByteBuffer byteBuffer) {
        return this.buf.readBytes(byteBuffer);
    }
    
    public ByteBuf setZero(final int n, final int n2) {
        return this.buf.setZero(n, n2);
    }
    
    public NBTTagCompound readNBTTagCompoundFromBuffer() throws IOException {
        final int readerIndex = this.readerIndex();
        if (this.readByte() == 0) {
            return null;
        }
        this.readerIndex(readerIndex);
        return CompressedStreamTools.read((DataInput)new ByteBufInputStream((ByteBuf)this), new NBTSizeTracker(2097152L));
    }
    
    public String toString() {
        return this.buf.toString();
    }
    
    public ByteBuf copy() {
        return this.buf.copy();
    }
    
    public int readUnsignedMedium() {
        return this.buf.readUnsignedMedium();
    }
    
    public IChatComponent readChatComponent() throws IOException {
        return IChatComponent.Serializer.jsonToComponent(this.readStringFromBuffer(32767));
    }
    
    public int refCnt() {
        return this.buf.refCnt();
    }
    
    public boolean isDirect() {
        return this.buf.isDirect();
    }
    
    public int arrayOffset() {
        return this.buf.arrayOffset();
    }
}
