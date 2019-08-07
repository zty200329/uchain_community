package com.swpu.uchain.community.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class User implements Serializable {
    private Integer id;

    private String stuId;

    private String trueName;

    private String password;

    private Integer role;

    private String userDesc;

    private Integer userPicId;

    private Integer unqualifyTimes;

    private Integer groupId;

}