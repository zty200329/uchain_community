package com.swpu.uchain.community.exception;

import com.swpu.uchain.community.enums.ResultEnum;
import lombok.Getter;

/**
 * @author zty
 * @date: 2019/8/12 17:08
 * 描述：
 */
@Getter
public class FileException extends RuntimeException{
    private Integer code;

    public FileException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());

        this.code = resultEnum.getCode();
    }

    public FileException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
