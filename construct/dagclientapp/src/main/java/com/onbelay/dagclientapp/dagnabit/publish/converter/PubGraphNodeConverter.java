package com.onbelay.dagclientapp.dagnabit.publish.converter;

import com.onbelay.dagclient.floatindex.snapshot.FloatIndexSnapshot;
import com.onbelay.dagclientapp.dagnabit.publish.snapshot.PubGraphNodeSnapshot;

import java.util.List;
import java.util.stream.Collectors;

public class PubGraphNodeConverter {

    public PubGraphNodeSnapshot convert(FloatIndexSnapshot floatIndexSnapshot) {
        PubGraphNodeSnapshot snapshot = new PubGraphNodeSnapshot();
        snapshot.getDetail().setName(floatIndexSnapshot.getDetail().getName());
        snapshot.getDetail().setCategory("floatIndex");
        snapshot.getDetail().setExternalReferenceId(floatIndexSnapshot.getEntityId().getId());
        return snapshot;
    }

    public List<PubGraphNodeSnapshot> convert (List<FloatIndexSnapshot> floatIndexSnapshots) {
        return floatIndexSnapshots
                .stream()
                .map( c-> convert(c))
                .collect(Collectors.toUnmodifiableList());
    }

}
