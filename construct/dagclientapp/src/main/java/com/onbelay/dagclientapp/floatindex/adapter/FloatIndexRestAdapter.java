package com.onbelay.dagclientapp.floatindex.adapter;

import com.onbelay.core.entity.snapshot.TransactionResult;
import com.onbelay.dagclient.floatindex.snapshot.FloatIndexSnapshot;
import com.onbelay.dagclientapp.floatindex.snapshot.FileResult;
import com.onbelay.dagclientapp.floatindex.snapshot.FloatIndexCollection;

import java.util.List;

public interface FloatIndexRestAdapter {

    TransactionResult save(FloatIndexSnapshot snapshot);

    TransactionResult save(List<FloatIndexSnapshot> snapshots);

    FloatIndexCollection find(
            int start,
            int limit,
            String query);

    TransactionResult uploadFile(
            String name,
            byte[] bytes);

    FileResult generateCSVFile(String query);
}
