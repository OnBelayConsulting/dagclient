package com.onbelay.dagclient.floatindex.repositoryimpl;

import com.onbelay.core.entity.repository.BaseRepository;
import com.onbelay.core.entity.snapshot.EntityId;
import com.onbelay.core.enums.CoreTransactionErrorCode;
import com.onbelay.core.exception.OBRuntimeException;
import com.onbelay.core.query.snapshot.DefinedQuery;
import com.onbelay.core.query.snapshot.QuerySelectedPage;
import com.onbelay.dagclient.floatindex.repository.FloatIndexRepository;
import com.onbelay.dagclient.floatindex.model.FloatIndexColumnDefinitions;
import com.onbelay.dagclient.floatindex.model.FloatIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository(value= FloatIndexRepository.NAME)
@Transactional
public class FloatIndexRepositoryBean extends BaseRepository<FloatIndex> implements FloatIndexRepository {
    public static final String FIND_INDEX_BY_NAME = "FloatIndex.FIND_INDEX_BY_NAME";
    public static final String FIND_INDICES_BY_IDS = "FloatIndex.FIND_INDICES_BY_IDS";

    @Autowired
    private FloatIndexColumnDefinitions floatIndexColumnDefinitions;

    public FloatIndex load(EntityId entityId) {
        if (entityId.isNull())
            return null;

        if (entityId.isInvalid())
            throw new OBRuntimeException(CoreTransactionErrorCode.ENTITY_DELETE_FAIL.getCode());

        if (entityId.isSet())
            return find(FloatIndex.class, entityId.getId());
        else if (entityId.getCode() != null)
            return findByName(entityId.getCode());
        else
            return null;
    }

    @Override
    public List<FloatIndex> load(List<EntityId> entityIds) {
        List<Integer> ids = entityIds
                .stream()
                .map( c-> c.getId())
                .collect(Collectors.toUnmodifiableList());

        return fetchByIds(new QuerySelectedPage(ids));
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
