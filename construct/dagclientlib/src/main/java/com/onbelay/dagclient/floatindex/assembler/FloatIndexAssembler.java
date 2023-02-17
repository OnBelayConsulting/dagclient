package com.onbelay.dagclient.floatindex.assembler;

import com.onbelay.core.entity.assembler.EntityAssembler;
import com.onbelay.dagclient.floatindex.model.FloatIndex;
import com.onbelay.dagclient.floatindex.snapshot.FloatIndexSnapshot;

import java.util.List;
import java.util.stream.Collectors;

public class FloatIndexAssembler extends EntityAssembler {

    public FloatIndexSnapshot assemble(FloatIndex floatIndex) {
        FloatIndexSnapshot snapshot = new FloatIndexSnapshot();
        setEntityAttributes(floatIndex, snapshot);

        if (floatIndex.getBenchesToIndex() != null)
            snapshot.setBenchesToFloatIndexId(floatIndex.getBenchesToIndex().generateSlot());

        snapshot.getDetail().shallowCopyFrom(floatIndex.getDetail());
        return snapshot;
    }

    public List<FloatIndexSnapshot> assemble(List<FloatIndex> nodes) {
        return nodes
                .stream()
                .map(c-> assemble(c))
                .collect(Collectors.toUnmodifiableList());
    }

}
