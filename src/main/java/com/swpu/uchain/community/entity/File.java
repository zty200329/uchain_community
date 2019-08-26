package com.swpu.uchain.community.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class File implements Serializable {
    private Integer id;

    private String fileName;

    private Integer fileTypeId;

    private String fileUrl;

    private static final long serialVersionUID = 1L;
}
