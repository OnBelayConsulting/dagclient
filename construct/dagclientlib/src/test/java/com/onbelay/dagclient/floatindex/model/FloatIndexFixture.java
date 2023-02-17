package com.onbelay.dagclient.floatindex.model;

import com.onbelay.dagclient.floatindex.snapshot.FloatIndexSnapshot;

public class FloatIndexFixture {

    private FloatIndexFixture() { }

    public static FloatIndex createSavedFloatIndex(String name) {
        FloatIndex floatIndex = new FloatIndex();
        floatIndex.createWith(name, name);
        return floatIndex;
    }

    public static FloatIndex createSavedFloatIndex(
            String name,
            String type) {

        FloatIndexSnapshot snapshot = new FloatIndexSnapshot();
        snapshot.getDetail().setName(name);
        snapshot.getDetail().setType(type);

        FloatIndex floatIndex = new FloatIndex();
        floatIndex.createWith(snapshot);
        return floatIndex;
    }

    public static FloatIndex createSavedFloatIndexWithBench(
            String name,
            String type,
            FloatIndex benchesToIndex) {

        FloatIndexSnapshot snapshot = new FloatIndexSnapshot();
        snapshot.getDetail().setName(name);
        snapshot.getDetail().setType(type);
        snapshot.setBenchesToFloatIndexId(benchesToIndex.generateSlot());

        FloatIndex floatIndex = new FloatIndex();
        floatIndex.createWith(snapshot);
        return floatIndex;
    }

}
