package com.swpu.uchain.community.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.File;

/**
 * @author zty
 * @date: 2019/8/15 14:03
 * 描述：
 */
@Data
public class ProBriefForm {
    /**
     * 项目名
     */
    @ApiModelProperty("项目名")
    @NotEmpty(message = "项目名不为空")
    private String proName;

    /**
     * 类型
     */
    @NotNull(message = "项目类型 文件类型必填")
    private Integer proTypeId;

    @NotEmpty(message = "学号必填")
    private String userId;

    @NotEmpty(message = "完成时间")
    private String uploadTime;

    private String proShow;

    private String content;

    private MultipartFile file[];
}
