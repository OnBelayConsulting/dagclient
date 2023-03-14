package com.onbelay.dagclientapp.dagnabit.publish.publisherimpl;

import com.onbelay.dagclient.floatindex.snapshot.FloatIndexSnapshot;
import com.onbelay.dagclientapp.dagnabit.publish.converter.PubGraphRelationshipConverter;
import com.onbelay.dagclientapp.dagnabit.publish.publisher.GraphRelationshipPublisher;
import com.onbelay.dagclientapp.dagnabit.publish.snapshot.PubGraphRelationshipSnapshot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;

import java.util.ArrayList;
import java.util.List;

public class GraphRelationshipPublisherBean implements GraphRelationshipPublisher {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private StreamBridge streamBridge;

    @Value( "${dagnbit.graphrelationship.queue.name:dagnabit.graphrelationship.save}")
    private String queueName;

    @Override
    public void publish(FloatIndexSnapshot snapshotIn) {
        ArrayList<PubGraphRelationshipSnapshot> snapshots = new ArrayList<>();

        PubGraphRelationshipConverter converter = new PubGraphRelationshipConverter();
        snapshots.add(
                converter.convert(snapshotIn));

        streamBridge.send(queueName, snapshots);
    }



    @Override
    public void publish(List<FloatIndexSnapshot> snapshotsIn) {

        PubGraphRelationshipConverter converter = new PubGraphRelationshipConverter();
        List<PubGraphRelationshipSnapshot> snapshots =    converter.convert(snapshotsIn);
        streamBridge.send(queueName, snapshots);
    }

}
