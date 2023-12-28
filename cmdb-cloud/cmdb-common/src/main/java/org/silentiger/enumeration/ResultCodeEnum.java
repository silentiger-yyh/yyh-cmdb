package org.silentiger.enumeration;

/**
 * 请求返回结果码
 *
 * @Author silentiger@yyh
 * @Date 2023-12-20 19:02:25
 */

public enum ResultCodeEnum {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限");

    private final long code;
    private final String message;

    ResultCodeEnum(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}