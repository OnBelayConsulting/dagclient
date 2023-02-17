package com.onbelay.dagclient.floatindex.serviceimpl;

import com.onbelay.core.entity.enums.EntityState;
import com.onbelay.core.entity.snapshot.EntityId;
import com.onbelay.core.entity.snapshot.TransactionResult;
import com.onbelay.core.exception.OBRuntimeException;
import com.onbelay.core.query.snapshot.DefinedQuery;
import com.onbelay.core.query.snapshot.QuerySelectedPage;
import com.onbelay.dagclient.enums.TransactionErrorCode;
import com.onbelay.dagclient.floatindex.assembler.FloatIndexAssembler;
import com.onbelay.dagclient.floatindex.model.FloatIndex;
import com.onbelay.dagclient.floatindex.repository.FloatIndexRepository;
import com.onbelay.dagclient.floatindex.service.FloatIndexService;
import com.onbelay.dagclient.floatindex.snapshot.FloatIndexSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FloatIndexServiceBean implements FloatIndexService {

    @Autowired
    private FloatIndexRepository floatIndexRepository;

    @Override
    public TransactionResult save(FloatIndexSnapshot snapshot) {

        if (snapshot.getEntityState() == EntityState.NEW) {
            FloatIndex floatIndex = new FloatIndex();
            floatIndex.createWith(snapshot);
            return new TransactionResult(floatIndex.getEntityId());
        } else if (snapshot.getEntityState() == EntityState.MODIFIED || snapshot.getEntityState() == EntityState.DELETE)  {
            FloatIndex floatIndex = floatIndexRepository.load(snapshot.getEntityId());

            if (floatIndex == null)
                throw new OBRuntimeException(TransactionErrorCode.MISSING_FLOAT_INDEX.getCode());
            floatIndex.updateWith(snapshot);
            return new TransactionResult(floatIndex.getId());
        } else {
            return new TransactionResult();
        }
    }

    @Override
    public TransactionResult save(List<FloatIndexSnapshot> snapshots) {
        ArrayList<EntityId> ids = new ArrayList<>();
        for (FloatIndexSnapshot snapshot : snapshots) {
            TransactionResult child = save(snapshot);
            if (child.getEntityId() != null)
                ids.add(child.getEntityId());
        }
        return new TransactionResult(ids);
    }

    @Override
    public FloatIndexSnapshot load(EntityId entityId) {
        FloatIndex node = floatIndexRepository.load(entityId);
        if (node == null)
            throw new OBRuntimeException(TransactionErrorCode.MISSING_FLOAT_INDEX.getCode());

        FloatIndexAssembler assembler = new FloatIndexAssembler();
        return assembler.assemble(node);
    }

    @Override
    public List<FloatIndexSnapshot> findByDefinedQuery(DefinedQuery definedQuery) {
        List<Integer> ids = floatIndexRepository.findFloatIndexIds(definedQuery);
        FloatIndexAssembler assembler = new FloatIndexAssembler();
        return assembler.assemble(
                floatIndexRepository.fetchByIds(
                        new QuerySelectedPage(
                                ids,
                                definedQuery.getOrderByClause())));
    }

    @Override
    public FloatIndexSnapshot findByName(String floatIndexName) {
        FloatIndex floatIndex =  floatIndexRepository.findByName(floatIndexName);
        if (floatIndex == null)
            return null;
        FloatIndexAssembler assembler = new FloatIndexAssembler();
        return assembler.assemble(floatIndex);
    }

    @Override
    public QuerySelectedPage findIdsByDefinedQuery(DefinedQuery definedQuery) {
        List<Integer> ids = floatIndexRepository.findFloatIndexIds(definedQuery);
        return new QuerySelectedPage(ids, definedQuery.getOrderByClause());
    }

    @Override
    public List<FloatIndexSnapshot> findByIds(QuerySelectedPage page) {
        FloatIndexAssembler assembler = new FloatIndexAssembler();
        return assembler.assemble(
                floatIndexRepository.fetchByIds(page));
    }
}
