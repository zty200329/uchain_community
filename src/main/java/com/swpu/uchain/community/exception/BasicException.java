package com.swpu.uchain.community.exception;

import lombok.Data;

/**
 * @author；lzh
 * @Date:2019/7/1710:23 Descirption:
 */
@Data
public class BasicException extends RuntimeException {

    private Integer code;

    public BasicException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }
}
