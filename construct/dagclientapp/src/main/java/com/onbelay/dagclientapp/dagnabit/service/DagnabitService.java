package com.onbelay.dagclientapp.dagnabit.service;

import com.onbelay.dagclientapp.common.snapshot.WebResult;
import com.onbelay.dagclientapp.dagnabit.snapshot.DagModelSnapshot;

import java.util.List;

public interface DagnabitService {

    WebResult createDagModel(String modelName);

    List<DagModelSnapshot> getDagModels(
            String query,
            long start,
            int limit);

}
