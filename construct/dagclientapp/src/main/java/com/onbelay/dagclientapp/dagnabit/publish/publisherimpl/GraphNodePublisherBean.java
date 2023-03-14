package com.onbelay.dagclientapp.dagnabit.publish.publisherimpl;

import com.onbelay.dagclient.floatindex.snapshot.FloatIndexSnapshot;
import com.onbelay.dagclientapp.dagnabit.publish.converter.PubGraphNodeConverter;
import com.onbelay.dagclientapp.dagnabit.publish.publisher.GraphNodePublisher;
import com.onbelay.dagclientapp.dagnabit.publish.snapshot.PubGraphNodeSnapshot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;

import java.util.ArrayList;
import java.util.List;

public class GraphNodePublisherBean implements GraphNodePublisher {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private StreamBridge streamBridge;

    @Value( "${dagnbit.graphnode.queue.name:dagnabit.graphnode.save}")
    private String queueName;

    @Override
    public void publish(FloatIndexSnapshot snapshotIn) {
        ArrayList<PubGraphNodeSnapshot> snapshots = new ArrayList<>();

        PubGraphNodeConverter converter = new PubGraphNodeConverter();
        snapshots.add(
                converter.convert(snapshotIn));

        streamBridge.send(queueName, snapshots);
    }



    @Override
    public void publish(List<FloatIndexSnapshot> snapshotsIn) {

        PubGraphNodeConverter converter = new PubGraphNodeConverter();
        List<PubGraphNodeSnapshot> snapshots =    converter.convert(snapshotsIn);
        streamBridge.send(queueName, snapshots);
    }

}
