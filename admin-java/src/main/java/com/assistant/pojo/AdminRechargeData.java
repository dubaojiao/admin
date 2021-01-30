package com.assistant.pojo;

import java.io.Serializable;

public class AdminRechargeData implements Serializable {
    private Integer uid;
    private Integer amount;
    // 1 加 2 减
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
