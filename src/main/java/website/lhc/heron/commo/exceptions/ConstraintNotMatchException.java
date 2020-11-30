package website.lhc.heron.commo.exceptions;

/**
 * @ProjectName: heron
 * @Package: webiste.lhc.heron.commo.exceptions
 * @ClassName: ConstraintNotMatchException
 * @Author: lhc
 * @Description: TODO
 * @Date: 2020/8/16 下午 02:50
 */
public class ConstraintNotMatchException extends RuntimeException {
    private static final long serialVersionUID = -5918966224688106394L;

    public ConstraintNotMatchException() {
    }

    public ConstraintNotMatchException(String message) {
        super(message);
    }
}
