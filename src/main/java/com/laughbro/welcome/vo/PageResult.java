package com.laughbro.welcome.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PageResult extends Result {
    private long totalPage;

    public PageResult(int code, String msg, Object data, long totalPage) {
        super(code, msg, data);
        this.totalPage = totalPage;
    }

    public static PageResult success(Object data, long total) {
        return new PageResult(200, "success", data, total);
    }

    public static PageResult fail(int code, String msg, Object data, int total) {
        return new PageResult(code, msg, data, total);
    }
}
