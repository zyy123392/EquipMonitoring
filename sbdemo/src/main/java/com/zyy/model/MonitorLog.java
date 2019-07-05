package com.zyy.model;

public class MonitorLog {
    private String id;

    private String time;

    private Double tem;

    private int settingtem;

    private String status;

    private String operation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    public Double getTem() {
        return tem;
    }

    public void setTem(Double tem) {
        this.tem = tem;
    }

    public int getSettingtem() {
        return settingtem;
    }

    public void setSettingtem(int settingtem) {
        this.settingtem = settingtem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation == null ? null : operation.trim();
    }
}