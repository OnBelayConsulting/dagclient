package com.onbelay.dagclient.floatindex.snapshot;

import com.onbelay.core.exception.OBValidationException;
import com.onbelay.dagclient.enums.TransactionErrorCode;
import jakarta.persistence.Column;


public class FloatIndexDetail {

    private String name;
    private String type;
    private String description;

    public void applyDefaults() {
    }

    public void shallowCopyFrom(FloatIndexDetail copy) {

        if (copy.name != null)
            this.name = copy.name;

        if (copy.type != null)
            this.type = copy.type;

        if (copy.description != null)
            this.description = copy.description;

    }

    public void validate() {
        if (this.name == null)
            throw new OBValidationException(TransactionErrorCode.MISSING_FLOAT_INDEX_NAME.getCode());
    }

    @Column(name = "INDEX_NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "INDEX_DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "INDEX_TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
