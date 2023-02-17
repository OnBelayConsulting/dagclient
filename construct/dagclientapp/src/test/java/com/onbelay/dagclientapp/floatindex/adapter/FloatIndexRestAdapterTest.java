package com.onbelay.dagclientapp.floatindex.adapter;

import com.onbelay.core.entity.snapshot.TransactionResult;
import com.onbelay.dagclient.common.DagnabitSpringTestCase;
import com.onbelay.dagclient.floatindex.model.FloatIndex;
import com.onbelay.dagclient.floatindex.model.FloatIndexFixture;
import com.onbelay.dagclient.floatindex.repository.FloatIndexRepository;
import com.onbelay.dagclient.floatindex.snapshot.FloatIndexSnapshot;
import com.onbelay.dagclientapp.floatindex.snapshot.FloatIndexCollection;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;

@WithMockUser
public class FloatIndexRestAdapterTest extends DagnabitSpringTestCase {

    @Autowired
    private FloatIndexRestAdapter floatIndexRestAdapter;

    @Autowired
    private FloatIndexRepository floatIndexRepository;

    private FloatIndex firstFloatIndex;

    @Override
    public void setUp() {
        super.setUp();

        firstFloatIndex = FloatIndexFixture.createSavedFloatIndex("HarryFloatIndex");
        flush();
    }

    @Test
    public void createFloatIndices() {
        ArrayList<FloatIndexSnapshot> snapshots = new ArrayList<>();

        FloatIndexSnapshot snapshot = new FloatIndexSnapshot();
        snapshot.getDetail().setName("MyMyFloatIndex");
        snapshot.getDetail().setType("MyType");
        snapshots.add(snapshot);

        TransactionResult result = floatIndexRestAdapter.save(snapshots);
        assertEquals(1, result.getEntityIds().size());
        FloatIndex saved = floatIndexRepository.findByName("MyMyFloatIndex");
        assertNotNull(saved);
    }

    @Test
    public void findFloatIndices() {
        FloatIndexCollection collection = floatIndexRestAdapter.find(0, 100, "WHERE name like 'H%'");
        assertEquals(1, collection.getSnapshots().size());
    }
}
