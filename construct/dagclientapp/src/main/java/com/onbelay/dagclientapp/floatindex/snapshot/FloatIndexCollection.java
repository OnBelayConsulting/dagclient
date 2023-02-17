package com.onbelay.dagclientapp.floatindex.snapshot;

import com.onbelay.core.entity.snapshot.AbstractSnapshotCollection;
import com.onbelay.dagclient.floatindex.snapshot.FloatIndexSnapshot;

import java.util.List;

public class FloatIndexCollection extends AbstractSnapshotCollection<FloatIndexSnapshot> {


    public FloatIndexCollection() {
        super("FloatIndex");
    }

    public FloatIndexCollection(
            int start,
            int limit,
            int totalItems,
            List<FloatIndexSnapshot> items) {

        super(
                "FloatIndex",
                start,
                limit,
                totalItems,
                items);
    }

    public FloatIndexCollection(
            int start,
            int limit,
            int totalItems) {

        super(
                "FloatIndex",
                start,
                limit,
                totalItems);
    }

    public FloatIndexCollection(String errorCode) {
        super(
                "FloatIndex",
                errorCode);
    }

    public FloatIndexCollection(
            String errorCode,
            List<String> parameters) {

        super(
                "FloatIndex",
                errorCode,
                parameters);
    }
}
