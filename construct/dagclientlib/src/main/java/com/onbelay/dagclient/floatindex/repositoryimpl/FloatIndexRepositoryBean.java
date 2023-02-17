package com.onbelay.dagclient.floatindex.repositoryimpl;

import com.onbelay.core.entity.repository.BaseRepository;
import com.onbelay.core.entity.snapshot.EntityId;
import com.onbelay.core.query.snapshot.DefinedQuery;
import com.onbelay.core.query.snapshot.QuerySelectedPage;
import com.onbelay.dagclient.floatindex.repository.FloatIndexRepository;
import com.onbelay.dagclient.floatindex.model.FloatIndexColumnDefinitions;
import com.onbelay.dagclient.floatindex.model.FloatIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository(value= FloatIndexRepository.NAME)
@Transactional
public class FloatIndexRepositoryBean extends BaseRepository<FloatIndex> implements FloatIndexRepository {
    public static final String FIND_INDEX_BY_NAME = "FloatIndex.FIND_INDEX_BY_NAME";

    @Autowired
    private FloatIndexColumnDefinitions floatIndexColumnDefinitions;

    public FloatIndex load(EntityId entityId) {
        if (entityId.isSet())
            return find(FloatIndex.class, entityId.getId());
        else
            return null;
    }

    @Override
    public FloatIndex findByName(String name) {
        return executeSingleResultQuery(
                FIND_INDEX_BY_NAME,
                "name",
                name);
    }

    @Override
    public List<Integer> findFloatIndexIds(DefinedQuery definedQuery) {
        return executeDefinedQueryForIds(
                floatIndexColumnDefinitions,
                definedQuery);
    }

    @Override
    public List<FloatIndex> fetchByIds(QuerySelectedPage selectedPage) {
        return fetchEntitiesById(
                floatIndexColumnDefinitions,
                "FloatIndex",
                selectedPage);
    }


}
