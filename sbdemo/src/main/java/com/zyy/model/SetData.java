package com.zyy.model;

public class SetData {
    private String id;

    private Integer timeslot;

    private Double temthreshold;

    private int settingTem;

    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSettingTem() {
        return settingTem;
    }

    public void setSettingTem(int settingTem) {
        this.settingTem = settingTem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(Integer timeslot) {
        this.timeslot = timeslot;
    }

    public Double getTemthreshold() {
        return temthreshold;
    }

    public void setTemthreshold(Double temthreshold) {
        this.temthreshold = temthreshold;
    }
}