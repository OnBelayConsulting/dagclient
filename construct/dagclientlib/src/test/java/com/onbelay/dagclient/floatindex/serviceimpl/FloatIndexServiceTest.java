package com.onbelay.dagclient.floatindex.serviceimpl;

import com.onbelay.core.entity.enums.EntityState;
import com.onbelay.core.entity.snapshot.EntityId;
import com.onbelay.core.entity.snapshot.TransactionResult;
import com.onbelay.core.query.enums.ExpressionOperator;
import com.onbelay.core.query.snapshot.DefinedQuery;
import com.onbelay.core.query.snapshot.DefinedWhereExpression;
import com.onbelay.dagclient.common.DagnabitSpringTestCase;
import com.onbelay.dagclient.floatindex.model.FloatIndex;
import com.onbelay.dagclient.floatindex.model.FloatIndexFixture;
import com.onbelay.dagclient.floatindex.repository.FloatIndexRepository;
import com.onbelay.dagclient.floatindex.service.FloatIndexService;
import com.onbelay.dagclient.floatindex.snapshot.FloatIndexSnapshot;
import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class FloatIndexServiceTest extends DagnabitSpringTestCase {

    @Autowired
    private FloatIndexService floatIndexService;

    @Autowired
    private FloatIndexRepository floatIndexRepository;

    private FloatIndex firstIndex;
    private FloatIndex secondIndex;

    @Override
    public void setUp() {
        super.setUp();

        firstIndex = FloatIndexFixture.createSavedFloatIndex(
                "HarryIndex",
                "elements");
        secondIndex = FloatIndexFixture.createSavedFloatIndexWithBench(
                "secondIndex",
                "metals",
                firstIndex);
        flush();
    }

    @Test
    public void findIndexByName() {

        DefinedQuery definedQuery = new DefinedQuery("FloatIndex");
        definedQuery.getWhereClause().addExpression(
                new DefinedWhereExpression(
                        "name",
                        ExpressionOperator.EQUALS,
                        "HarryIndex"));

        List<FloatIndexSnapshot> floatIndexs = floatIndexService.findByDefinedQuery(definedQuery);
        TestCase.assertEquals(1, floatIndexs.size());
        FloatIndexSnapshot floatIndex = floatIndexs.get(0);
        TestCase.assertEquals("elements", floatIndex.getDetail().getType());
    }

    @Test
    public void findIndexByType() {

        DefinedQuery definedQuery = new DefinedQuery("FloatIndex");
        definedQuery.getWhereClause().addExpression(
                new DefinedWhereExpression(
                        "type",
                        ExpressionOperator.EQUALS,
                        "elements"));

        List<FloatIndexSnapshot> floatIndexs = floatIndexService.findByDefinedQuery(definedQuery);
        TestCase.assertEquals(1, floatIndexs.size());
        FloatIndexSnapshot floatIndex = floatIndexs.get(0);
        TestCase.assertEquals("HarryIndex", floatIndex.getDetail().getName());
    }

    @Test
    public void findIndexByIndexId() {

        DefinedQuery definedQuery = new DefinedQuery("FloatIndex");
        definedQuery.getWhereClause().addExpression(
                new DefinedWhereExpression(
                        "id",
                        ExpressionOperator.EQUALS,
                        firstIndex.getId()));

        List<FloatIndexSnapshot> floatIndexs = floatIndexService.findByDefinedQuery(definedQuery);
        TestCase.assertEquals(1, floatIndexs.size());
        FloatIndexSnapshot floatIndex = floatIndexs.get(0);
        TestCase.assertEquals("HarryIndex", floatIndex.getDetail().getName());
    }


    @Test
    public void createIndices() {

        ArrayList<FloatIndexSnapshot> snapshots = new ArrayList<>();

        FloatIndexSnapshot snapshot = new FloatIndexSnapshot();
        snapshot.getDetail().setName("MyMyIndex");
        snapshot.getDetail().setType("MyType");
        snapshot.getDetail().setDescription("desc");
        snapshots.add(snapshot);

        TransactionResult result = floatIndexService.save(snapshots);
        flush();

        FloatIndexSnapshot snapshotFound = floatIndexService.findByName("MyMyIndex");
        TestCase.assertNotNull(snapshotFound);
        TestCase.assertEquals("MyType", snapshotFound.getDetail().getType());
        TestCase.assertEquals("desc", snapshotFound.getDetail().getDescription());
    }

    @Test
    public void createBenchedIndex() {

        ArrayList<FloatIndexSnapshot> snapshots = new ArrayList<>();

        FloatIndexSnapshot snapshot = new FloatIndexSnapshot();
        snapshot.getDetail().setName("MyMyIndex");
        snapshot.getDetail().setType("MyType");
        snapshot.getDetail().setDescription("desc");
        snapshot.setBenchesToFloatIndexId(firstIndex.generateEntityId());
        snapshots.add(snapshot);

        TransactionResult result = floatIndexService.save(snapshots);
        flush();

        FloatIndexSnapshot snapshotFound = floatIndexService.findByName("MyMyIndex");
        TestCase.assertNotNull(snapshotFound);
        TestCase.assertEquals("HarryIndex", snapshotFound.getBenchesToFloatIndexId().getCode());
        TestCase.assertNotNull(snapshotFound.getBenchesToFloatIndexId());
        assertEquals(firstIndex.getId(), snapshotFound.getBenchesToFloatIndexId().getId());
    }


    @Test
    public void removeBenchedIndex() {

        ArrayList<FloatIndexSnapshot> snapshots = new ArrayList<>();

        FloatIndexSnapshot snapshot = new FloatIndexSnapshot();
        snapshot.setEntityState(EntityState.MODIFIED);
        snapshot.setEntityId(secondIndex.generateEntityId());

        snapshot.setBenchesToFloatIndexId(EntityId.makeNullEntityId());

        snapshots.add(snapshot);

        TransactionResult result = floatIndexService.save(snapshots);
        flush();

        FloatIndex secondIndex = floatIndexRepository.findByName("secondIndex");
        assertNull(secondIndex.getBenchesToIndex());

    }



}
