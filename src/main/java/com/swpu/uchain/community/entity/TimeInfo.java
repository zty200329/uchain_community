package com.swpu.uchain.community.entity;

import java.io.Serializable;

public class TimeInfo implements Serializable {
    private String timeId;

    private String userId;

    private String timeDate;

    private String timeIn;

    private String timeOut;

    private String timeState;

    private String timeValid;

    private static final long serialVersionUID = 1L;

    public String getTimeId() {
        return timeId;
    }

    public void setTimeId(String timeId) {
        this.timeId = timeId == null ? null : timeId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(String timeDate) {
        this.timeDate = timeDate == null ? null : timeDate.trim();
    }

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn == null ? null : timeIn.trim();
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut == null ? null : timeOut.trim();
    }

    public String getTimeState() {
        return timeState;
    }

    public void setTimeState(String timeState) {
        this.timeState = timeState == null ? null : timeState.trim();
    }

    public String getTimeValid() {
        return timeValid;
    }

    public void setTimeValid(String timeValid) {
        this.timeValid = timeValid == null ? null : timeValid.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", timeId=").append(timeId);
        sb.append(", userId=").append(userId);
        sb.append(", timeDate=").append(timeDate);
        sb.append(", timeIn=").append(timeIn);
        sb.append(", timeOut=").append(timeOut);
        sb.append(", timeState=").append(timeState);
        sb.append(", timeValid=").append(timeValid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}