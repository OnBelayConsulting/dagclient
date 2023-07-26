package com.onbelay.dagclientapp.dagnabit.service;

import com.onbelay.dagclientapp.common.snapshot.WebResult;
import com.onbelay.dagclientapp.dagnabit.snapshot.DagModelCollection;
import com.onbelay.dagclientapp.dagnabit.snapshot.DagModelSnapshot;
import com.onbelay.dagclientapp.dagnabit.snapshot.ModelResult;

import java.util.List;

public interface DagnabitService {

    ModelResult saveModel(DagModelSnapshot snapshot);

    DagModelCollection findModels(
            String query,
            long start,
            int limit);

}
