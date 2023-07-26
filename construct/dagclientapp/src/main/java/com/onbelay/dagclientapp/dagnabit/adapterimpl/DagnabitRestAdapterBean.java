package com.onbelay.dagclientapp.dagnabit.adapterimpl;

import com.onbelay.core.controller.BaseRestAdapterBean;
import com.onbelay.core.entity.snapshot.TransactionResult;
import com.onbelay.dagclientapp.dagnabit.adapter.DagnabitRestAdapter;
import com.onbelay.dagclientapp.dagnabit.service.DagnabitService;
import com.onbelay.dagclientapp.dagnabit.snapshot.DagModelCollection;
import com.onbelay.dagclientapp.dagnabit.snapshot.DagModelSnapshot;
import com.onbelay.dagclientapp.dagnabit.snapshot.ModelResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DagnabitRestAdapterBean extends BaseRestAdapterBean implements DagnabitRestAdapter {

    @Autowired
    private DagnabitService dagnabitService;

    @Override
    public DagModelCollection findModels(
            int start,
            int limit,
            String query) {

        initializeSession();

        return dagnabitService.findModels(
                query,
                start,
                limit);
    }

    @Override
    public TransactionResult saveModel(DagModelSnapshot snapshot) {
        initializeSession();

        ModelResult result = dagnabitService.saveModel(snapshot);
        return new TransactionResult();
    }
}
