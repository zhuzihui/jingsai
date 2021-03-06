package com.xd.pre.modules.encryption;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.SecureRandom;
import java.security.Security;

/**
 * SM4加密
 * @auther:zlk
 * @date:2021-3-5
 * @description:
 *
 */
public final class Sm4Util {
    private static final String ALGORITHM = "SM4";
    private static final String AES_ALGORITHM_NAME_CBC_PADDING = "SM4/CBC/PKCS7Padding";
    private static final String AES_ALGORITHM_NAME_ECB_PADDING = "SM4/ECB/PKCS7Padding";
    private static final int KEY_SIZE = 128;
    private static final int KEY_OR_IV_BYTE_LENGTH = 16;

    public static final String sm4key = "636f6d2e686e75702e6f736d702e7373";
    public static final String sm4iv = "6f6e2e63727970746f2e736d2e736d34";


    static {

        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
        if (Security.getProvider("BC") == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    public static byte[] generateKey() throws Exception {
        return generateKey(KEY_SIZE);
    }

    public static byte[] generateKey(int keySize) throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM, "BC");
        kg.init(keySize, new SecureRandom());
        return kg.generateKey().getEncoded();
    }

    public static SecretKeySpec getSecretKey(byte[] keyByte) {
        return new SecretKeySpec(keyByte, ALGORITHM);
    }

    public static String encryptCBC(byte[] keyByte, byte[] ivByte, String source) throws Exception {
        byte[] sourceBytes = source.getBytes(StandardCharsets.UTF_8);
        byte[] bytes = encryptCBC(keyByte, ivByte, sourceBytes);
        return HexUtil.byteToHex(bytes);
    }

    public static byte[] encryptCBC(byte[] keyByte, byte[] ivByte, byte[] sourceByte) throws Exception {
        return doCBCFinal(AES_ALGORITHM_NAME_CBC_PADDING, 1, keyByte, ivByte, sourceByte);
    }

    public static String decryptCBC(byte[] keyByte, byte[] ivByte, String hexCipherString) throws Exception {
        byte[] cipherByte = HexUtil.hexToByte(hexCipherString);
        byte[] bytes = decryptCBC(keyByte, ivByte, cipherByte);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static byte[] decryptCBC(byte[] keyByte, byte[] ivByte, byte[] cipherByte)  throws Exception{
        return doCBCFinal(AES_ALGORITHM_NAME_CBC_PADDING, 2, keyByte, ivByte, cipherByte);
    }

    public static String encryptECB(byte[] keyByte, String source) throws Exception {
        byte[] sourceByte = source.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedData = encryptECB(keyByte, sourceByte);
        return HexUtil.byteToHex(encryptedData);
    }

    public static byte[] encryptECB(byte[] keyByte, byte[] sourceByte)  throws Exception{
        return doECBFinal(AES_ALGORITHM_NAME_ECB_PADDING, 1, keyByte, sourceByte);
    }

    public static String decryptECB(byte[] keyByte, String hexCipherString)  throws Exception{
        byte[] cipherByte = HexUtil.hexToByte(hexCipherString);
        byte[] decoded = doECBFinal(AES_ALGORITHM_NAME_ECB_PADDING, 2, keyByte, cipherByte);
        return new String(decoded, StandardCharsets.UTF_8);
    }

    public static byte[] decryptECB(byte[] keyByte, byte[] cipherByte) throws Exception {
        return doECBFinal(AES_ALGORITHM_NAME_ECB_PADDING, 2, keyByte, cipherByte);
    }

    public static byte[] doCBCFinal(String cipherAlgorithm, int mode, byte[] keyByte, byte[] ivByte, byte[] executeByte) throws Exception {
        Cipher cipher = CipherUtil.getCipher(cipherAlgorithm, "BC");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivByte);
        checkKeyLength(keyByte);
        checkIvLength(ivByte);

        try {
            cipher.init(mode, getSecretKey(keyByte), ivParameterSpec);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException var9) {
            throw new Exception("算法参数异常!");
        }

        try {
            return cipher.doFinal(executeByte);
        } catch (IllegalBlockSizeException | BadPaddingException var8) {
            throw new Exception("算法处理的数据异常!");
        }
    }

    public static byte[] doECBFinal(String cipherAlgorithm, int mode, byte[] keyByte, byte[] executeByte)  throws Exception{
        Cipher cipher = CipherUtil.getCipher(cipherAlgorithm, "BC");
        checkKeyLength(keyByte);

        try {
            cipher.init(mode, getSecretKey(keyByte));
        } catch (InvalidKeyException var7) {
            throw new Exception("算法参数异常!");
        }

        try {
            return cipher.doFinal(executeByte);
        } catch (IllegalBlockSizeException | BadPaddingException var6) {
            throw new Exception("算法处理的数据异常!");
        }
    }

    private static void checkKeyLength(byte[] keyByte)  throws Exception{
        if (keyByte.length != KEY_OR_IV_BYTE_LENGTH) {
            throw new Exception("SM4 算法的 Key 长度不对，必须是 16 byte（128 bit）!");
        }
    }

    private static void checkIvLength(byte[] ivByte)  throws Exception{
        if (ivByte.length != KEY_OR_IV_BYTE_LENGTH) {
            throw new Exception("SM4 算法的 iv 长度不对，必须是 16 byte（128 bit）!");
        }
    }

    public String getSm4key() {
        return sm4key;
    }

    public String getSm4iv() {
        return sm4iv;
    }
}
