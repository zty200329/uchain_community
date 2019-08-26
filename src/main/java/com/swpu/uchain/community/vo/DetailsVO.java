package com.swpu.uchain.community.vo;

import lombok.Data;

/**
 * @author zty
 * @date: 2019/8/14 14:09
 * 描述：
 */
@Data
public class DetailsVO {
    /**
     * 项目名
     */
    private String proName;

    /**
     * 上传者学号
     */
    private String proUserId;

    /**
     * 更新时间
     */
    private String proUploadTime;

    /**
     * 项目简介 返回html
     */
    private String proDesc;

}
