package com.swpu.uchain.community.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class TimeInfo implements Serializable {
    private String timeId;

    private String userId;

    private String timeDate;

    private String timeIn;

    private String timeOut;

    private String timeState;

    private String timeValid;

}