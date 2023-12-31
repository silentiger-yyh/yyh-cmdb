package org.system.config;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * LoggerUtil类
 * 自定义LoggerUtil
 *
 * @Author silentiger@yyh
 * @Date 2023-12-31 10:42:19
 */

@Component
public class LoggerUtil {
    @Autowired
    @Qualifier(value = "logger-info")
    private Logger loggerInfo;
    @Autowired
    @Qualifier(value = "logger-error")
    private Logger loggerError;

    public void info(String msg) {
        loggerInfo.info(msg);
    }

    public void error(String msg) {
        loggerError.error(msg);
    }
}
