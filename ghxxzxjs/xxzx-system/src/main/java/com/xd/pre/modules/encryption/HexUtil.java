package com.xd.pre.modules.encryption;//

import java.util.regex.Pattern;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

public class HexUtil {
    public static final Pattern HEX = Pattern.compile("^[a-f0-9]+$", 2);

    public HexUtil() {
    }

    public static byte[] hexToByte(String hexString) throws IllegalArgumentException {
        return ByteUtils.fromHexString(hexString);
    }

    public static String byteToHex(byte[] b) {
        return ByteUtils.toHexString(b);
    }

    public static boolean isHex(String value) {
        return HEX.matcher(value).matches();
    }
}
