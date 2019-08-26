package com.swpu.uchain.community.entity;

import java.io.Serializable;

public class ProBrief implements Serializable {
    private Integer id;

    private String proName;

    private String proUserId;

    private String proUploadTime;

    private String proFileUrl;

    private String proShow;

    private Integer proTypeId;

    private String proDesc;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName == null ? null : proName.trim();
    }

    public String getProUserId() {
        return proUserId;
    }

    public void setProUserId(String proUserId) {
        this.proUserId = proUserId == null ? null : proUserId.trim();
    }

    public String getProUploadTime() {
        return proUploadTime;
    }

    public void setProUploadTime(String proUploadTime) {
        this.proUploadTime = proUploadTime;
    }

    public String getProFileUrl() {
        return proFileUrl;
    }

    public void setProFileUrl(String proFileUrl) {
        this.proFileUrl = proFileUrl == null ? null : proFileUrl.trim();
    }

    public String getProShow() {
        return proShow;
    }

    public void setProShow(String proShow) {
        this.proShow = proShow == null ? null : proShow.trim();
    }

    public Integer getProTypeId() {
        return proTypeId;
    }

    public void setProTypeId(Integer proTypeId) {
        this.proTypeId = proTypeId;
    }

    public String getProDesc() {
        return proDesc;
    }

    public void setProDesc(String proDesc) {
        this.proDesc = proDesc == null ? null : proDesc.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", proName=").append(proName);
        sb.append(", proUserId=").append(proUserId);
        sb.append(", proUploadTime=").append(proUploadTime);
        sb.append(", proFileUrl=").append(proFileUrl);
        sb.append(", proShow=").append(proShow);
        sb.append(", proTypeId=").append(proTypeId);
        sb.append(", proDesc=").append(proDesc);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}