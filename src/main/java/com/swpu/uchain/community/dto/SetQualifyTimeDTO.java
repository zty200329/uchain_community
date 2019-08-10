package com.swpu.uchain.community.dto;

import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @author；lzh
 * @Date:2019/8/919:11 Descirption:
 */
@Data
public class SetQualifyTimeDTO {

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 用户一周所需打卡时间
     */
    private Integer qualifyTime;

    public SetQualifyTimeDTO(String userName, Integer qualifyTime){
        this.userName = userName;
        this.qualifyTime = qualifyTime;
    }
}
