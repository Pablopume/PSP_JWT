package dao;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

/**
 *
 * @author oscar
 */
public class Utils {


    public static String randomBytes()
    {
        SecureRandom sr = new SecureRandom();
        byte[] bits = new byte[32];
        sr.nextBytes(bits);
        return Base64.getUrlEncoder().encodeToString(bits);
    }


}