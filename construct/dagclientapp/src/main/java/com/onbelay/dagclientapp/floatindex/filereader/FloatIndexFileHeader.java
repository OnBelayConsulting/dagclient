package com.onbelay.dagclientapp.floatindex.filereader;

public abstract class FloatIndexFileHeader {
    public static final String HEADER_NAME = "Name";
    public static final String HEADER_TYPE = "Type";
    public static final String HEADER_DESCRIPTION = "Description";
    public static final String HEADER_BENCHES_TO = "BenchesTo";

    protected static String[] header = {
            HEADER_NAME,
            HEADER_TYPE,
            HEADER_DESCRIPTION,
            HEADER_BENCHES_TO};
}
