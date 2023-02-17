package com.onbelay.dagclientapp.dagnabit.restclientimpl;

import com.onbelay.dagclientapp.common.snapshot.WebResult;
import com.onbelay.dagclientapp.dagnabit.restclient.CreateDagModelRestClient;
import com.onbelay.dagclientapp.dagnabit.snapshot.DagModelSnapshot;

import java.util.List;

public class CreateDagModelRestClientStub implements CreateDagModelRestClient {


    @Override
    public WebResult saveDagModels(List<DagModelSnapshot> snapshots) {
        return null;
    }
}
