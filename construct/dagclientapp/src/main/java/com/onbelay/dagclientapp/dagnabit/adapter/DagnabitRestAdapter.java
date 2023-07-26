package com.onbelay.dagclientapp.dagnabit.adapter;

import com.onbelay.core.entity.snapshot.TransactionResult;
import com.onbelay.dagclientapp.dagnabit.snapshot.DagModelCollection;
import com.onbelay.dagclientapp.dagnabit.snapshot.DagModelSnapshot;

public interface DagnabitRestAdapter {

    public DagModelCollection findModels(
            int start,
            int limit,
            String query);

    public TransactionResult saveModel(DagModelSnapshot snapshot);

}
