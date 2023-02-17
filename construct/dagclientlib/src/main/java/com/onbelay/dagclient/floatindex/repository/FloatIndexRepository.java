package com.onbelay.dagclient.floatindex.repository;

import com.onbelay.core.entity.snapshot.EntityId;
import com.onbelay.core.query.snapshot.DefinedQuery;
import com.onbelay.core.query.snapshot.QuerySelectedPage;
import com.onbelay.dagclient.floatindex.model.FloatIndex;

import java.util.List;

public interface FloatIndexRepository {
    public static final String NAME = "floatIndexRepository";

    public FloatIndex findByName(String name);

    public FloatIndex load(EntityId entityId);

    public List<Integer> findFloatIndexIds(DefinedQuery definedQuery);

    public List<FloatIndex> fetchByIds(QuerySelectedPage selectedPage);



}
