package com.onbelay.dagclient.floatindex.snapshot;


import com.onbelay.core.entity.snapshot.AbstractSnapshot;
import com.onbelay.core.entity.snapshot.EntitySlot;

public class FloatIndexSnapshot extends AbstractSnapshot {

    private EntitySlot benchesToFloatIndexId;
    private String benchesToFloatIndexName;

    private FloatIndexDetail detail = new FloatIndexDetail();

    public FloatIndexDetail getDetail() {
        return detail;
    }

    public void setDetail(FloatIndexDetail detail) {
        this.detail = detail;
    }

    public EntitySlot getBenchesToFloatIndexId() {
        return benchesToFloatIndexId;
    }

    public void setBenchesToFloatIndexId(EntitySlot benchesToFloatIndexId) {
        this.benchesToFloatIndexId = benchesToFloatIndexId;
    }

    public String getBenchesToFloatIndexName() {
        return benchesToFloatIndexName;
    }

    public void setBenchesToFloatIndexName(String benchesToFloatIndexName) {
        this.benchesToFloatIndexName = benchesToFloatIndexName;
    }
}
