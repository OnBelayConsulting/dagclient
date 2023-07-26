package com.onbelay.dagclientapp.dagnabit.serviceimpl;

import com.onbelay.dagclientapp.common.snapshot.WebResult;
import com.onbelay.dagclientapp.dagnabit.restclient.CreateDagModelRestClient;
import com.onbelay.dagclientapp.dagnabit.restclient.GetDagModelsRestClient;
import com.onbelay.dagclientapp.dagnabit.service.DagnabitService;
import com.onbelay.dagclientapp.dagnabit.snapshot.DagModelCollection;
import com.onbelay.dagclientapp.dagnabit.snapshot.DagModelSnapshot;
import com.onbelay.dagclientapp.dagnabit.snapshot.ModelResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DagnabitServiceBean implements DagnabitService {

    @Autowired
    private CreateDagModelRestClient createDagModelRestClient;

    @Autowired
    private GetDagModelsRestClient getDagModelsRestClient;

    @Override
    public ModelResult saveModel(DagModelSnapshot snapshot) {
        return createDagModelRestClient.saveDagModel(snapshot);
    }

    @Override
    public DagModelCollection findModels(
            String query,
            long start,
            int limit) {

        return getDagModelsRestClient.getDagModels(
                query,
                start,
                limit);
    }
}
