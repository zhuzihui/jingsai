package com.xd.pre.modules.encryption;//

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Signature;

/**
 * SM4加密
 * @auther:zlk
 * @date:2021-3-5
 * @description:
 *
 */
public final class CipherUtil {

    public CipherUtil() {
    }

    public static KeyFactory getRsaKeyFactory() throws Exception {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory;
        } catch (NoSuchAlgorithmException var1) {
            throw new Exception("无法找到 RSA 算法");
        }
    }

    public static Cipher getCipher(String cipherAlgorithm) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            return cipher;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException var2) {
            throw new Exception("无法找到 " + cipherAlgorithm + " 算法", var2);
        }
    }

    public static Cipher getCipher(String cipherAlgorithm, String provider) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance(cipherAlgorithm, provider);
            return cipher;
        } catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException var3) {
            throw new Exception("无法找到 " + cipherAlgorithm + " 算法, 或 provider: " + provider);
        }
    }

    public static Signature getSignature(String signAlgorithms) throws Exception  {
        Signature signature = null;

        try {
            signature = Signature.getInstance(signAlgorithms);
            return signature;
        } catch (NoSuchAlgorithmException var3) {
            throw new Exception("没有找到 " + signAlgorithms + "算法");
        }
    }

    public static Signature getSignature(String signAlgorithms, String provider)  throws Exception {
        Signature signature = null;

        try {
            signature = Signature.getInstance(signAlgorithms, provider);
            return signature;
        } catch (NoSuchProviderException | NoSuchAlgorithmException var4) {
            throw new Exception("没有找到 " + signAlgorithms + "算法, 或 provider: " + provider);
        }
    }
}
