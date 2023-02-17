package com.onbelay.dagclientapp.dagnabit.restclient;

import com.onbelay.dagclientapp.common.snapshot.WebResult;
import com.onbelay.dagclientapp.dagnabit.snapshot.DagModelSnapshot;

import java.util.List;

public interface CreateDagModelRestClient {

    WebResult saveDagModels(List<DagModelSnapshot> snapshots);

}
