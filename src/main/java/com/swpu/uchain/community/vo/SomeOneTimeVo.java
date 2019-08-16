package com.swpu.uchain.community.vo;

import lombok.Data;

import java.util.List;

/**
 * @ClassName SomeOneTimeVO
 * @Description TODO
 * @Author LZH
 */
@Data
public class SomeOneTimeVo {
    /**
     * 用户名
     */
    private String userName;

    /**
     * 单条签到记录
     */
    private List<SingleRecordVo>  singleRecordVoList;
}
