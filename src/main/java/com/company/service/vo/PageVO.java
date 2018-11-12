package com.company.service.vo;

import java.io.Serializable;

public class PageVO implements Serializable {
    private Integer pstart;
    private Integer size;

    @Override
    public String toString() {
        return "PageVO{" +
                "pstart=" + pstart +
                ", size=" + size +
                '}';
    }

    public Integer getPstart() {
        return pstart;
    }

    public void setPstart(Integer pstart) {
        this.pstart = pstart;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public PageVO(Integer pstart, Integer size) {
        this.pstart = pstart;
        this.size = size;
    }

    public PageVO() {
    }
}
