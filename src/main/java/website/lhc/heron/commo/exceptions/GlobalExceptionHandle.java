package website.lhc.heron.commo.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import website.lhc.heron.util.Resp;

/**
 * @ProjectName: heron
 * @Package: webiste.lhc.heron.commo.exceptions
 * @ClassName: GlobalExceptionHandle
 * @Author: lhc
 * @Description: TODO
 * @Date: 2020/8/16 下午 02:54
 */
@ControllerAdvice
public class GlobalExceptionHandle {
    @ResponseBody
    @ExceptionHandler(ConstraintNotMatchException.class)
    public Resp constraintNotMatchException(ConstraintNotMatchException e) {
        return Resp.error(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(HeronException.class)
    public Resp heronException(HeronException e) {
        return Resp.error(e.getMessage());
    }
}
