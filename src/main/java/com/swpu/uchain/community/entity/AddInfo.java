package com.swpu.uchain.community.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class AddInfo implements Serializable {
    private Integer addId;

    private String userId;

    private String addTime;

    private String addDate;

    private String addExcuse;

    private Integer addState;
}