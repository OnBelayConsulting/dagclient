package com.onbelay.dagclient.floatindex.repository;

import com.onbelay.core.query.enums.ExpressionOperator;
import com.onbelay.core.query.snapshot.DefinedQuery;
import com.onbelay.core.query.snapshot.DefinedWhereExpression;
import com.onbelay.core.query.snapshot.QuerySelectedPage;
import com.onbelay.dagclient.common.DagnabitSpringTestCase;
import com.onbelay.dagclient.floatindex.model.FloatIndexFixture;
import com.onbelay.dagclient.floatindex.model.FloatIndex;
import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FloatIndexRepositoryTest extends DagnabitSpringTestCase {

    @Autowired
    private FloatIndexRepository floatIndexRepository;

    private FloatIndex fromFloatIndex;
    private FloatIndex toFloatIndex;


    public void setUp() {
        super.setUp();
        fromFloatIndex = FloatIndexFixture.createSavedFloatIndex("myIndex");
        toFloatIndex = FloatIndexFixture.createSavedFloatIndex("yourIndex");
        flush();
    }


    @Test
    public void FindByName() {

        FloatIndex floatIndex = floatIndexRepository.findByName("myIndex");
        TestCase.assertNotNull(floatIndex);
    }


    @Test
    public void FindByQuery() {

        DefinedQuery definedQuery = new DefinedQuery("FloatIndex");
        definedQuery.getWhereClause().addExpression(
                new DefinedWhereExpression("name", ExpressionOperator.EQUALS, "myIndex")
        );

        List<Integer> ids = floatIndexRepository.findFloatIndexIds(definedQuery);
        List<FloatIndex> indices = floatIndexRepository.fetchByIds(new QuerySelectedPage(ids));
        TestCase.assertEquals(1, indices.size());
    }


}
