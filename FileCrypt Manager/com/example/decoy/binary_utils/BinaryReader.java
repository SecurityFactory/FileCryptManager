package com.example.decoy.binary_utils;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BinaryReader extends FilterInputStream {
    public BinaryReader(InputStream inputStream) {
        super(inputStream);
    }

    public int readInt32() throws IOException {
        return ByteBuffer.wrap(readBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    public byte readInt8() throws IOException {
        return ByteBuffer.wrap(readBytes(1)).order(ByteOrder.LITTLE_ENDIAN).get();
    }

    public long readUInt32() throws IOException {
        return ((long) readInt32()) & 4294967295L;
    }

    public short readInt16() throws IOException {
        return ByteBuffer.wrap(readBytes(2)).order(ByteOrder.LITTLE_ENDIAN).getShort();
    }

    public int readUInt16() throws IOException {
        return readInt16() & 65535;
    }

    public String readString() throws IOException {
        return new String(readBytes(getStringLength()));
    }

    public boolean readBoolean() throws IOException {
        return readBytes(1)[0] != 0;
    }

    public float readSingle() throws IOException {
        return ByteBuffer.wrap(readBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getFloat();
    }

    private int getStringLength() throws IOException {
        boolean z = true;
        int i = 0;
        int i2 = 0;
        while (z) {
            byte read = (byte) read();
            i |= (read & Byte.MAX_VALUE) << i2;
            i2 += 7;
            if ((read & 128) == 0) {
                z = false;
            }
        }
        return i;
    }

    public byte[] readBytes(int i) throws IOException {
        byte[] bArr = new byte[i];
        read(bArr);
        return bArr;
    }
}