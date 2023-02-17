package com.onbelay.dagclientapp.floatindex.adapterimpl;

import com.onbelay.core.entity.snapshot.TransactionResult;
import com.onbelay.core.query.parsing.DefinedQueryBuilder;
import com.onbelay.core.query.snapshot.QuerySelectedPage;
import com.onbelay.dagclient.floatindex.service.FloatIndexService;
import com.onbelay.dagclient.floatindex.snapshot.FloatIndexSnapshot;
import com.onbelay.dagclientapp.floatindex.adapter.FloatIndexRestAdapter;
import com.onbelay.dagclientapp.floatindex.filereader.FloatIndexFileReader;
import com.onbelay.dagclientapp.floatindex.filewriter.FloatIndexFileWriter;
import com.onbelay.dagclientapp.floatindex.snapshot.FileResult;
import com.onbelay.dagclientapp.floatindex.snapshot.FloatIndexCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
public class FloatIndexRestAdapterBean implements FloatIndexRestAdapter {

    @Autowired
    private FloatIndexService floatIndexService;

    @Override
    public TransactionResult save(FloatIndexSnapshot snapshot) {
        return floatIndexService.save(snapshot);
    }

    @Override
    public TransactionResult save(List<FloatIndexSnapshot> snapshots) {
        return floatIndexService.save(snapshots);
    }

    @Override
    public FloatIndexCollection find(
            int start,
            int limit,
            String query) {

        DefinedQueryBuilder queryBuilder = new DefinedQueryBuilder("FloatIndex", query);

        QuerySelectedPage page = floatIndexService.findIdsByDefinedQuery(queryBuilder.build());

        if (page.getIds().size() == 0 || start >= page.getIds().size()) {
            return new FloatIndexCollection(
                    start,
                    limit,
                    page.getIds().size());
        }

        int toIndex = start + limit;

        if (toIndex > page.getIds().size())
            toIndex =  page.getIds().size();
        int fromIndex = start;

        List<Integer> selected = page.getIds().subList(fromIndex, toIndex);
        QuerySelectedPage limitedPageSelection = new QuerySelectedPage(
                selected,
                page.getOrderByClause());

        List<FloatIndexSnapshot> snapshots = floatIndexService.findByIds(limitedPageSelection);

        return new FloatIndexCollection(
                start,
                limit,
                page.getIds().size(),
                snapshots);
    }

    @Override
    public TransactionResult uploadFile(
            String name,
            byte[] fileContents) {

        ByteArrayInputStream fileStream = new ByteArrayInputStream(fileContents);

        FloatIndexFileReader reader = new FloatIndexFileReader(fileStream);
        List<FloatIndexSnapshot> snapshots = reader.readFile();
        return floatIndexService.save(snapshots);
    }

    @Override
    public FileResult generateCSVFile(String query) {
        DefinedQueryBuilder queryBuilder = new DefinedQueryBuilder("FloatIndex", query);

        QuerySelectedPage page = floatIndexService.findIdsByDefinedQuery(queryBuilder.build());
        List<FloatIndexSnapshot> nodes = floatIndexService.findByIds(page);

        FloatIndexFileWriter writer = new FloatIndexFileWriter(nodes);

        return new FileResult("graphnodes.csv", writer.getContents());
    }
}
