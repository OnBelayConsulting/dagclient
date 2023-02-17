package com.onbelay.dagclientapp.dagnabit.restclientimpl;

import com.onbelay.dagclientapp.dagnabit.restclient.GetDagModelsRestClient;
import com.onbelay.dagclientapp.dagnabit.snapshot.DagModelCollection;

public class GetDagModelsRestClientStub implements GetDagModelsRestClient {
    @Override
    public DagModelCollection getDagModels(String query, long start, int limit) {
        return new DagModelCollection();
    }
}
