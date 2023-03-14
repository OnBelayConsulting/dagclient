package com.onbelay.dagclientapp.dagnabit.publish.publisher;

import com.onbelay.dagclient.floatindex.snapshot.FloatIndexSnapshot;
import com.onbelay.dagclientapp.dagnabit.publish.snapshot.PubGraphRelationshipSnapshot;

import java.util.List;

public interface GraphRelationshipPublisher {
    
    public void publish(FloatIndexSnapshot snapshot);

    public void publish(List<FloatIndexSnapshot> snapshots);
    
    
}
