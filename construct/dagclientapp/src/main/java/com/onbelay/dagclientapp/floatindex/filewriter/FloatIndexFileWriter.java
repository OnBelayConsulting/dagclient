package com.onbelay.dagclientapp.floatindex.filewriter;

import com.onbelay.core.exception.OBRuntimeException;
import com.onbelay.dagclient.enums.TransactionErrorCode;
import com.onbelay.dagclient.floatindex.snapshot.FloatIndexSnapshot;
import com.onbelay.dagclientapp.floatindex.filereader.FloatIndexFileHeader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class FloatIndexFileWriter extends FloatIndexFileHeader {
    private static final Logger logger = LogManager.getLogger();

    private List<FloatIndexSnapshot> floatIndices = new ArrayList<>();

    public FloatIndexFileWriter(List<FloatIndexSnapshot> indices) {
        this.floatIndices = indices;
    }

    public List<FloatIndexSnapshot> getFloatIndices() {
        return floatIndices;
    }

    public byte[] getContents() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out)) {

            try (CSVPrinter printer = new CSVPrinter(
                    outputStreamWriter,
                    CSVFormat.EXCEL.builder().setHeader(header).build()) ) {

                for (FloatIndexSnapshot snapshot : floatIndices) {
                    printer.printRecord(
                            snapshot.getDetail().getName(),
                            snapshot.getDetail().getType(),
                            snapshot.getDetail().getDescription(),
                            snapshot.getBenchesToFloatIndexId() != null ? snapshot.getBenchesToFloatIndexId().getCode() : "");
                }
            } catch (IOException e) {
                logger.error("Writing node csv file failed with: ", e);
                throw new OBRuntimeException(TransactionErrorCode.FLOAT_INDEX_FILE_WRITE_FAILED.getCode());
            }
        } catch (IOException e) {
            logger.error("Writing node csv file failed with: ", e);
            throw new OBRuntimeException(TransactionErrorCode.FLOAT_INDEX_FILE_WRITE_FAILED.getCode());
        }

        return out.toByteArray();
    }
}
