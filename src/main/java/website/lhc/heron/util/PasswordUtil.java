package website.lhc.heron.util;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @ProjectName: heron
 * @Package: webiste.lhc.heron.util
 * @ClassName: PasswordUtil
 * @Author: lhc
 * @Description: TODO
 * @Date: 2020/8/16 下午 12:42
 */
public class PasswordUtil {

    private static final String ALGORITHM_NAME = "sha-1";

    private static final int HASH_ITERATIONS = 3;

    /**
     * 生成密码
     *
     * @param password
     * @param salt
     * @return
     */
    public static String generatorPassword(String password, String salt) {
        SimpleHash simpleHash = new SimpleHash(ALGORITHM_NAME, password, salt, HASH_ITERATIONS);
        return simpleHash.toString();
    }

    /**
     * 校验密码是否正确
     *
     * @param source 未加密的密码
     * @param salt   盐
     * @param target 已加密的密码
     * @return
     */
    public static boolean passwordMatch(String source, String salt, String target) {
        return generatorPassword(source, salt).equals(target);
    }

    public static void main(String[] args) {

        System.out.println(generatorPassword("123", "1c676d35-6f32-cd39-2cc1-c9cf8af2af48"));
        System.out.println(passwordMatch("123", "1c676d35-6f32-cd39-2cc1-c9cf8af2af48", "11911ec05cbf20af4329349fe4430e14cfe00fa7"));
    }
}
