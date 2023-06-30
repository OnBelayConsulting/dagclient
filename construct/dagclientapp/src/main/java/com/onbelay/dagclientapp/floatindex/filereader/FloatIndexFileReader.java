package com.onbelay.dagclientapp.floatindex.filereader;

import com.onbelay.core.entity.snapshot.EntityId;
import com.onbelay.core.exception.OBRuntimeException;
import com.onbelay.dagclient.enums.TransactionErrorCode;
import com.onbelay.dagclient.floatindex.snapshot.FloatIndexSnapshot;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FloatIndexFileReader extends FloatIndexFileHeader {
    private static final Logger logger = LogManager.getLogger();
    private InputStream streamIn;

    public FloatIndexFileReader(InputStream streamIn) {
        this.streamIn = streamIn;
    }

    public List<FloatIndexSnapshot> readFile() {

        ArrayList<FloatIndexSnapshot> snapshots = new ArrayList<>();

        try (InputStreamReader reader = new InputStreamReader(streamIn)) {

            CSVParser parser = new CSVParser(
                    reader,
                    CSVFormat.EXCEL.builder()
                            .setHeader(header)
                            .setSkipHeaderRecord(true)
                            .build());

            Iterable<CSVRecord> records = parser;

            for (CSVRecord record : records) {
                FloatIndexSnapshot snapshot = new FloatIndexSnapshot();
                snapshot.getDetail().setName(record.get(HEADER_NAME));
                snapshot.getDetail().setType(record.get(HEADER_TYPE));
                snapshot.getDetail().setDescription(record.get(HEADER_DESCRIPTION));
                if (record.isSet(HEADER_BENCHES_TO))
                    snapshot.setBenchesToFloatIndexId(new EntityId(record.get(HEADER_BENCHES_TO)));

                snapshots.add(snapshot);
            }

            parser.close();

        } catch (IOException e) {
            logger.error("CSV file parsing read failed. ", e);
            throw new OBRuntimeException(TransactionErrorCode.FLOAT_INDEX_FILE_READ_FAILED.getCode());
        }

        return snapshots;
    }
}
