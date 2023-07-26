package com.onbelay.dagclientapp.dagnabit.restclient;

import com.onbelay.dagclientapp.common.snapshot.WebResult;
import com.onbelay.dagclientapp.dagnabit.snapshot.DagModelSnapshot;
import com.onbelay.dagclientapp.dagnabit.snapshot.ModelResult;

import java.util.List;

public interface CreateDagModelRestClient {

    ModelResult saveDagModel(DagModelSnapshot snapshot);

}
