package com.onbelay.dagclientapp.floatindex.filewriter;

import com.onbelay.core.entity.snapshot.EntityId;
import com.onbelay.dagclient.common.DagnabitSpringTestCase;
import com.onbelay.dagclient.floatindex.snapshot.FloatIndexSnapshot;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FloatIndexFileWriterTest extends DagnabitSpringTestCase {

    @Test
    public void writeNodes() throws IOException {

        List<FloatIndexSnapshot> nodes = new ArrayList<>();

        FloatIndexSnapshot snapshot = new FloatIndexSnapshot();
        snapshot.getDetail().setName("Fred");
        snapshot.getDetail().setType("Family");
        snapshot.getDetail().setDescription("Fred Index");
        nodes.add(snapshot);

        snapshot = new FloatIndexSnapshot();
        snapshot.getDetail().setName("Sammy");
        snapshot.getDetail().setType("Basis");
        snapshot.setBenchesToFloatIndexId(new EntityId("Fred"));
        nodes.add(snapshot);

        FloatIndexFileWriter writer = new FloatIndexFileWriter(nodes);
        byte[] contents = writer.getContents();

        ByteArrayInputStream inputStream = new ByteArrayInputStream(contents);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String headerLine = reader.readLine();
        String contentLine = reader.readLine();
        assertEquals("Name,Type,Description,BenchesTo", headerLine);

        assertEquals("Fred,Family,Fred Index,", contentLine);

    }

}
