package com.onbelay.dagclientapp.dagnabit.publish.snapshot;


import com.onbelay.core.entity.snapshot.AbstractSnapshot;

public class PubGraphRelationshipSnapshot extends AbstractSnapshot {

    private String fromNodeName;
    private String toNodeName;

    private PubGraphRelationshipDetail detail = new PubGraphRelationshipDetail();


    public String getFromNodeName() {
        return fromNodeName;
    }

    public void setFromNodeName(String fromNodeName) {
        this.fromNodeName = fromNodeName;
    }

    public String getToNodeName() {
        return toNodeName;
    }

    public void setToNodeName(String toNodeName) {
        this.toNodeName = toNodeName;
    }

    public PubGraphRelationshipDetail getDetail() {
        return detail;
    }

    public void setDetail(PubGraphRelationshipDetail detail) {
        this.detail = detail;
    }
}
