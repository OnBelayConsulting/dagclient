package com.onbelay.dagclientapp.dagnabit.serviceimpl;

import com.onbelay.dagclientapp.common.snapshot.WebResult;
import com.onbelay.dagclientapp.dagnabit.restclient.CreateDagModelRestClient;
import com.onbelay.dagclientapp.dagnabit.restclient.GetDagModelsRestClient;
import com.onbelay.dagclientapp.dagnabit.serviceimpl.service.DagnabitService;
import com.onbelay.dagclientapp.dagnabit.snapshot.DagModelCollection;
import com.onbelay.dagclientapp.dagnabit.snapshot.DagModelSnapshot;
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
    public WebResult createDagModel(String modelName) {
        DagModelSnapshot snapshot = new DagModelSnapshot();
        snapshot.setName(modelName);
        return createDagModelRestClient.saveDagModels(List.of(snapshot));
    }

    @Override
    public List<DagModelSnapshot> getDagModels(
            String query,
            long start,
            int limit) {

        DagModelCollection collection = getDagModelsRestClient.getDagModels(
                query,
                start,
                limit);
        return collection.getSnapshots();
    }
}
