package com.onbelay.dagclientapp.dagnabit.snapshot;

import com.onbelay.core.entity.snapshot.AbstractSnapshotCollection;

import java.util.List;

public class DagModelCollection extends AbstractSnapshotCollection<DagModelSnapshot> {
    private static final String NAME = "GraphModel";
    private String filter;

    public DagModelCollection() {
    }

    public DagModelCollection(
            int start,
            int limit,
            int totalItems,
            List<DagModelSnapshot> items ) {

        super(
                "GraphModel",
                start,
                limit,
                totalItems,
                items);
    }


    public DagModelCollection(
            int start,
            int limit,
            int totalItems) {

        super(
                "GraphModel",
                start,
                limit,
                totalItems);
    }


    public DagModelCollection(String errorCode) {
        super("GraphModel", errorCode);
    }

    public DagModelCollection(
            String errorCode,
            List<String> parameters) {

        super(
                "GraphModel",
                errorCode,
                parameters);
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}
