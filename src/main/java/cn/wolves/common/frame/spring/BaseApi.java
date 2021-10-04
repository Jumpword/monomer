package cn.wolves.common.frame.spring;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.wolves.common.exception.BusinessException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * @author Jumping
 */
@Slf4j
public abstract class BaseApi {

    @Autowired(required = false)
    TableInfoConfiguration tableInfoConfiguration;

    @InitBinder
    protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
        binder.registerCustomEditor(Date.class, new WolvesDateEditor());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> exceptionHandler(Exception exception) {
        exception.printStackTrace();
        StringBuilder msg = new StringBuilder(200);
        String errorCode = "300";
        if (exception instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException validException = (MethodArgumentNotValidException) exception;
            BindingResult result = validException.getBindingResult();
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError error : errors) {
                msg.append(error.getObjectName());
                msg.append(":\t");
                msg.append(error.getDefaultMessage());
                msg.append("\n");
            }
            log.error(msg.toString(), exception);
        } else if (exception instanceof ConstraintViolationException) {
            ConstraintViolationException validatorExceptions = (ConstraintViolationException) exception;
            Set<ConstraintViolation<?>> contraints = validatorExceptions.getConstraintViolations();
            for (ConstraintViolation<?> contraint : contraints) {
                msg.append(contraint.getMessageTemplate());
                msg.append("\n");
            }
            errorCode = "3002";
        } else if (exception instanceof BusinessException || exception.getCause() instanceof BusinessException) {
            BusinessException businessException = (BusinessException) (exception.getCause());
            if (businessException != null) {
                msg.append(businessException.getMessage());
                errorCode = businessException.getErrorCode();
            } else if (exception instanceof BusinessException) {
                msg.append(exception.getMessage());
                errorCode = ((BusinessException) exception).getErrorCode();
            }
        } else if (exception instanceof BindException) {
            BindException bindException = (BindException) exception;
            bindException.getAllErrors().forEach((error) -> {
                msg.append(error.getDefaultMessage());
            });
        } else {
            Throwable root = ExceptionUtil.getRootCause(exception);
            String ms = ExceptionUtil.getRootCauseMessage(exception);
            if (root instanceof SQLException) {
                ms = handlerSqlMsg((SQLException) root);
            }
            msg.append("系统异常：").append(ms);
        }
        log.error("【系统异常】-", exception);
        return ApiResponseBuilder.wrap(Integer.parseInt(Optional.ofNullable(errorCode).orElse("300")), msg.toString());
    }

    /**
     * 处理sql异常信息
     *
     * @param root
     * @return
     */
    protected String handlerSqlMsg(SQLException root) {
        int sqlExceptionCode = 12899;
        String ms = root.getMessage();
        if (root.getErrorCode() == sqlExceptionCode && tableInfoConfiguration != null) {
            int s = ms.indexOf("\"");
            int e = ms.lastIndexOf("\"");
            s = ms.indexOf(".");
            String tableKey = ms.substring(s + 1, e + 1);
            ms = ms.substring(e + 1);
            s = ms.indexOf(":");
            e = ms.indexOf(",");
            String actual = ms.substring(s, e);
            ms = ms.substring(e + 1);
            s = ms.indexOf(":");
            String max = ms.substring(s);
            String tableMsg = Optional.ofNullable(tableInfoConfiguration.getTableFieldMsg(tableKey)).orElse(tableKey);
            ms = String.format("%s录入超长(录入长度为%s,限制长度为%s", tableMsg, actual, max);
        }
        return ms;

    }

}
