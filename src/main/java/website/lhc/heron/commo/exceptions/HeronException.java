package website.lhc.heron.commo.exceptions;

/**
 * heron服务异常
 */
public class HeronException extends RuntimeException {

    public HeronException(String message) {
        super(message);
    }

    public HeronException(String message, Throwable cause) {
        super(message, cause);
    }
}
