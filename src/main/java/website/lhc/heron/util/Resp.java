package website.lhc.heron.util;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

/**
 * @ProjectName: heron
 * @Package: webiste.lhc.heron.util
 * @ClassName: Resp
 * @Author: lhc
 * @Description: TODO
 * @Date: 2020/8/16 下午 02:36
 */
public class Resp extends HashMap<String, Object> {
    private static final long serialVersionUID = 4941398730825868814L;

    public Resp() {
        put("code", 1);
        put("message", "SUCCESS");
    }

    public static Resp ok() {
        return new Resp();
    }

    public static Resp ok(String message) {
        Resp resp = new Resp();
        resp.put("message", message);
        return resp;
    }

    public static Resp ok(Object data) {
        Resp resp = new Resp();
        resp.put("data", data);
        return resp;
    }

    public static Resp error(int code, String message) {
        Resp resp = new Resp();
        resp.put("code", code);
        resp.put("message", message);
        return resp;
    }

    public static Resp error() {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "ERROR");
    }

    public static Resp error(String message) {
        return error(0, message);
    }

    @Override
    public Object put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
