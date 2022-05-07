package com.test.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult {
    private int code;
    private String msg;
    private Object Data;

    public static CommonResult ok() {
        return new CommonResult(200, "", null);
    }

    public static CommonResult ok(Object data) {
        return new CommonResult(200, "", data);
    }

    public static CommonResult fail(int code, String msg, Object data) {
        return new CommonResult(code, msg, data);
    }

    public static CommonResult fail(String msg) {
        return new CommonResult(500, msg, null);
    }
}