package com.onbelay.dagclientapp.floatindex.filereader;

import com.onbelay.dagclient.common.DagnabitSpringTestCase;
import com.onbelay.dagclient.floatindex.snapshot.FloatIndexSnapshot;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class FloatIndexFileReaderTest extends DagnabitSpringTestCase  {

    @Test
    public void readFloatIndices() throws IOException {

        List<FloatIndexSnapshot> nodes;
        try (InputStream stream = FloatIndexFileReaderTest.class.getResourceAsStream("/floatindex_file_example.csv")) {
            FloatIndexFileReader reader = new FloatIndexFileReader(stream);
            nodes = reader.readFile();
        }
        assertEquals(3, nodes.size());
        FloatIndexSnapshot first = nodes.get(0);
        assertEquals("FirstIndex", first.getDetail().getName());
        assertEquals("Hub", first.getDetail().getType());
        assertNull(first.getBenchesToFloatIndexName());

        FloatIndexSnapshot third = nodes.get(2);
        assertEquals("ThirdBasis", third.getDetail().getName());
        assertEquals("Basis", third.getDetail().getType());
        assertEquals("FirstIndex", third.getBenchesToFloatIndexName());

    }

}
