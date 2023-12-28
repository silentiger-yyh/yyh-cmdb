package org.silentiger.util;

import java.util.Date;

/**
 * 日期工具类
 * Created by macro on 2020/3/3.
 */
public class DateUtil {
    public static long getTimeNow() {
        return new Date().getTime();
    }
}
