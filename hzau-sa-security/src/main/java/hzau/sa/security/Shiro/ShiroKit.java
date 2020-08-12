package hzau.sa.security.Shiro;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class ShiroKit {
    public final static String HASH_ALGORITHM_NAME = "MD5";
    public final static String SALT_SOURCE = "BGISALT";

    /**
     * 循环次数
     */
    public final static int HASH_ITERATIONS = 4;

    /**
     * shiro密码加密工具类
     *
     * @param credentials 密码
     * @return
     */
    public static String md5(String credentials) {
        ByteSource salt = new Md5Hash(SALT_SOURCE);
        return new SimpleHash(HASH_ALGORITHM_NAME, credentials, salt, HASH_ITERATIONS).toString();
    }

    /**
     * shiro密码加密工具类
     *
     * @param credentials 密码
     * @param saltSource 密码盐
     * @return
     */
    public static String md5(String credentials, String saltSource) {
        ByteSource salt = new Md5Hash(saltSource);
        return new SimpleHash(HASH_ALGORITHM_NAME, credentials, salt, HASH_ITERATIONS).toString();
    }
}
