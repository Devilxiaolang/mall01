package com.company.service.vo;

import java.io.Serializable;
import java.util.Date;

public class HiredateVO implements Serializable {
    private Date startDate;
    private Date endDate;

    @Override
    public String toString() {
        return "HiredateVO{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public HiredateVO(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public HiredateVO() {
    }
}
