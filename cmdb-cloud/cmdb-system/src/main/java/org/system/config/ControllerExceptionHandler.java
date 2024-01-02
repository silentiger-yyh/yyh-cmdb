package org.system.config;

import org.silentiger.api.CommonResult;
import org.silentiger.constant.CmdbConstant;
import org.silentiger.util.LoggerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ControllerExceptionHandler类
 * controller全局异常处理
 *
 * @Author silentiger@yyh
 * @Date 2024-01-02 10:42:28
 */

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CmdbConstant.LOGGER_NAME);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CommonResult<Object> exceptionHandler(Exception e) {
        logger.error(LoggerUtil.getFullExpMsg(e));
        return CommonResult.failed(e.getMessage());
    }
}
