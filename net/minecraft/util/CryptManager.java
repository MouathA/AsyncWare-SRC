package net.minecraft.util;

import org.apache.logging.log4j.*;
import java.security.spec.*;
import javax.crypto.*;
import java.security.*;
import javax.crypto.spec.*;

public class CryptManager
{
    private static final Logger LOGGER;
    
    public static KeyPair generateKeyPair() {
        final KeyPairGenerator instance = KeyPairGenerator.getInstance("RSA");
        instance.initialize(1024);
        return instance.generateKeyPair();
    }
    
    public static Cipher createNetCipherInstance(final int n, final Key key) {
        final Cipher instance = Cipher.getInstance("AES/CFB8/NoPadding");
        instance.init(n, key, new IvParameterSpec(key.getEncoded()));
        return instance;
    }
    
    public static byte[] getServerIdHash(final String s, final PublicKey publicKey, final SecretKey secretKey) {
        return digestOperation("SHA-1", new byte[][] { s.getBytes("ISO_8859_1"), secretKey.getEncoded(), publicKey.getEncoded() });
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
    
    public static PublicKey decodePublicKey(final byte[] array) {
        return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(array));
    }
    
    public static byte[] decryptData(final Key key, final byte[] array) {
        return cipherOperation(2, key, array);
    }
    
    public static byte[] encryptData(final Key key, final byte[] array) {
        return cipherOperation(1, key, array);
    }
    
    private static Cipher createTheCipherInstance(final int n, final String s, final Key key) {
        final Cipher instance = Cipher.getInstance(s);
        instance.init(n, key);
        return instance;
    }
    
    private static byte[] cipherOperation(final int n, final Key key, final byte[] array) {
        return createTheCipherInstance(n, key.getAlgorithm(), key).doFinal(array);
    }
    
    private static byte[] digestOperation(final String s, final byte[]... array) {
        final MessageDigest instance = MessageDigest.getInstance(s);
        while (0 < array.length) {
            instance.update(array[0]);
            int n = 0;
            ++n;
        }
        return instance.digest();
    }
    
    public static SecretKey createNewSharedKey() {
        final KeyGenerator instance = KeyGenerator.getInstance("AES");
        instance.init(128);
        return instance.generateKey();
    }
    
    public static SecretKey decryptSharedKey(final PrivateKey privateKey, final byte[] array) {
        return new SecretKeySpec(decryptData(privateKey, array), "AES");
    }
}
