package com.onbelay.dagclientapp.dagnabit.restclient;

import com.onbelay.dagclientapp.dagnabit.snapshot.DagModelCollection;

public interface GetDagModelsRestClient {

    public DagModelCollection getDagModels(
            String query,
            long start,
            int limit);

    }
