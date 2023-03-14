package com.onbelay.dagclient.floatindex.service;

import com.onbelay.core.entity.snapshot.EntityId;
import com.onbelay.core.entity.snapshot.TransactionResult;
import com.onbelay.core.query.snapshot.DefinedQuery;
import com.onbelay.core.query.snapshot.QuerySelectedPage;
import com.onbelay.dagclient.floatindex.snapshot.FloatIndexSnapshot;

import java.util.List;

public interface FloatIndexService {
    public static final String NAME = "graphNodeService";

    FloatIndexSnapshot load(EntityId entityId);

    List<FloatIndexSnapshot> load(List<EntityId> ids);

    List<FloatIndexSnapshot> findByDefinedQuery(DefinedQuery definedQuery);

    FloatIndexSnapshot findByName(String floatIndexName);

    QuerySelectedPage findIdsByDefinedQuery(DefinedQuery definedQuery);

    List<FloatIndexSnapshot> findByIds(QuerySelectedPage page);

    TransactionResult save(FloatIndexSnapshot snapshot);

    TransactionResult save(List<FloatIndexSnapshot> snapshots);
}
