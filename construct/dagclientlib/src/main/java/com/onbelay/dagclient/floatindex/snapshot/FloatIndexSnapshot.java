package com.onbelay.dagclient.floatindex.snapshot;


import com.onbelay.core.entity.snapshot.AbstractSnapshot;
import com.onbelay.core.entity.snapshot.EntityId;

public class FloatIndexSnapshot extends AbstractSnapshot {

    private EntityId benchesToFloatIndexId;
    private String benchesToFloatIndexName;

    private FloatIndexDetail detail = new FloatIndexDetail();

    public FloatIndexDetail getDetail() {
        return detail;
    }

    public void setDetail(FloatIndexDetail detail) {
        this.detail = detail;
    }

    public EntityId getBenchesToFloatIndexId() {
        return benchesToFloatIndexId;
    }

    public void setBenchesToFloatIndexId(EntityId benchesToFloatIndexId) {
        this.benchesToFloatIndexId = benchesToFloatIndexId;
    }

    public String getBenchesToFloatIndexName() {
        return benchesToFloatIndexName;
    }

    public void setBenchesToFloatIndexName(String benchesToFloatIndexName) {
        this.benchesToFloatIndexName = benchesToFloatIndexName;
    }
}
