package com.onbelay.dagclientapp.dagnabit.publish.converter;

import com.onbelay.dagclient.floatindex.snapshot.FloatIndexSnapshot;
import com.onbelay.dagclientapp.dagnabit.publish.snapshot.PubGraphRelationshipSnapshot;

import java.util.List;
import java.util.stream.Collectors;

public class PubGraphRelationshipConverter {

    public PubGraphRelationshipSnapshot convert(FloatIndexSnapshot floatIndexSnapshot) {
        PubGraphRelationshipSnapshot snapshot = new PubGraphRelationshipSnapshot();
        snapshot.setFromNodeName(floatIndexSnapshot.getDetail().getName());
        snapshot.setToNodeName(floatIndexSnapshot.getBenchesToFloatIndexName());
        snapshot.getDetail().setType("benches");
        return snapshot;
    }
    
    public List<PubGraphRelationshipSnapshot> convert (List<FloatIndexSnapshot> floatIndexSnapshots) {
        return floatIndexSnapshots
                .stream()
                .map( c-> convert(c))
                .collect(Collectors.toUnmodifiableList());
    }

}
