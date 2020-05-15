package com.example.decoy;

import java.util.Random;

public class encDec {
    public static byte[] EncodeData(byte[] bArr) {
        byte[] bArr2 = new byte[(bArr.length + 32)];
        byte[] bArr3 = new byte[32];
        new Random().nextBytes(bArr3);
        System.arraycopy(bArr3, 0, bArr2, 0, 32);
        System.arraycopy(bArr, 0, bArr2, 32, bArr.length);
        for (int i = 0; i < bArr.length; i++) {
            int i2 = i + 32;
            bArr2[i2] = (byte) (bArr2[i2] ^ bArr2[i % 32]);
        }
        return bArr2;
    }

    public static byte[] DecodeData(byte[] bArr) {
        byte[] bArr2 = new byte[(bArr.length - 32)];
        System.arraycopy(bArr, 32, bArr2, 0, bArr2.length);
        for (int i = 0; i < bArr2.length; i++) {
            bArr2[i] = (byte) (bArr2[i] ^ bArr[i % 32]);
        }
        return bArr2;
    }
}