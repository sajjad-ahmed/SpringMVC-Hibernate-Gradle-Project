package red.niloy.blog.util;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * @author sajjad.ahmed
 * @since 10/17/19.
 */
public class Encryption {
    public static String encrypt(String source) {
        String md5 = null;
        try {
            MessageDigest mdEnc = MessageDigest.getInstance("MD5");
            mdEnc.update(source.getBytes(), 0, source.length());
            md5 = new BigInteger(1, mdEnc.digest()).toString(16);
        } catch (Exception ex) {
            return null;
        }
        return md5;
    }
}