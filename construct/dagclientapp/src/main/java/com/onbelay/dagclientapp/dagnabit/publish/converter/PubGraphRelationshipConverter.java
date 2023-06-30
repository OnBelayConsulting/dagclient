package com.onbelay.dagclientapp.dagnabit.publish.converter;

import com.onbelay.dagclient.floatindex.snapshot.FloatIndexSnapshot;
import com.onbelay.dagclientapp.dagnabit.publish.snapshot.PubGraphRelationshipSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PubGraphRelationshipConverter {

    public PubGraphRelationshipSnapshot convert(FloatIndexSnapshot floatIndexSnapshot) {
        if (floatIndexSnapshot.getBenchesToFloatIndexId() == null)
            return null;

        PubGraphRelationshipSnapshot snapshot = new PubGraphRelationshipSnapshot();
        snapshot.setFromNodeName(floatIndexSnapshot.getDetail().getName());
        snapshot.setToNodeName(floatIndexSnapshot.getBenchesToFloatIndexId().getCode());
        snapshot.getDetail().setType("benches");
        return snapshot;
    }
    
    public List<PubGraphRelationshipSnapshot> convert (List<FloatIndexSnapshot> floatIndexSnapshots) {
        ArrayList<PubGraphRelationshipSnapshot> snapshots = new ArrayList<>();
        for (FloatIndexSnapshot floatIndexSnapshot : floatIndexSnapshots) {
            PubGraphRelationshipSnapshot snapshot = convert(floatIndexSnapshot);
            if (snapshot != null)
                snapshots.add(snapshot);
        }
        return snapshots;
    }

}
