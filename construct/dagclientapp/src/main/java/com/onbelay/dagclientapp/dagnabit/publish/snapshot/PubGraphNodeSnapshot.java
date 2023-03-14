package com.onbelay.dagclientapp.dagnabit.publish.snapshot;


import com.onbelay.core.entity.snapshot.AbstractSnapshot;

public class PubGraphNodeSnapshot extends AbstractSnapshot {

    private PubGraphNodeDetail detail = new PubGraphNodeDetail();

    public PubGraphNodeDetail getDetail() {
        return detail;
    }

    public void setDetail(PubGraphNodeDetail detail) {
        this.detail = detail;
    }
}
