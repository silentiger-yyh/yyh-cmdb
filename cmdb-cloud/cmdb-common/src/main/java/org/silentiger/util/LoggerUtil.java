package org.silentiger.util;

/**
 * LoggerUtil类
 * 获取异常日志的Trace
 *
 * @Author silentiger@yyh
 * @Date 2024-01-02 11:34:33
 */

public class LoggerUtil {
    public static String getFullExpMsg(Exception e) {
        StringBuilder sb = new StringBuilder(e.getMessage()).append("\n").append(":");
        for (StackTraceElement element : e.getStackTrace()) {
            sb.append("\t").append(element.toString()).append("\n");
        }
        return sb.toString();
    }
}
