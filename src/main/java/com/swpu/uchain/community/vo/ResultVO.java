package com.swpu.uchain.community.vo;

import lombok.Data;

/**
 * @author hobo
 * @description
 */
@Data
public class ResultVO<T> {

    private Integer code;

    private String msg;

    private T data;
}
