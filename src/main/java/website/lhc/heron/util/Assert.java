package website.lhc.heron.util;

import website.lhc.heron.commo.exceptions.ConstraintNotMatchException;

import java.util.Objects;

/**
 * @ProjectName: heron
 * @Package: website.lhc.heron.util
 * @ClassName: Assert
 * @Author: lhc
 * @Description: TODO
 * @Date: 2020/8/16 下午 12:35
 */
public class Assert extends org.springframework.util.Assert {
    public static void objectNotNull(Object o, String message) {
        if (Objects.isNull(o)) {
            throw new ConstraintNotMatchException(message);
        }
    }

    public static void stat(boolean b, String message) {
        if (b) {
            throw new ConstraintNotMatchException(message);
        }
    }
}
