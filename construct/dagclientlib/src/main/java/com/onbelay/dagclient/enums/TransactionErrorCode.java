/*
 Copyright 2019, OnBelay Consulting Ltd.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.  
*/
package com.onbelay.dagclient.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum TransactionErrorCode {

    SUCCESS                                       ("0", "Success: transaction was successful"),
    MISSING_FLOAT_INDEX                           ("DC-E0001", "FloatIndex not found."),
    MISSING_FLOAT_INDEX_NAME                      ("DC-E0003", "Missing FloatIndex name (Mandatory"),
    INVALID_BENCHES_FLOAT_INDEX_REF               ("DC-E0004", "Invalid BenchesTo FloatIndex reference"),

    FLOAT_INDEX_FILE_READ_FAILED                  ("DC-E1000", "Float Index file read failed."),
    FLOAT_INDEX_FILE_WRITE_FAILED                 ("DC-E1000", "Float Index file write failed."),

    WEB_CALL_TO_DAGNABIT_FAILED                   ("DC-E5000", "Web call to Dagnabit service failed.");

    private String code;
    private String description;

    private static final Map<String, TransactionErrorCode> lookup
            = new HashMap<String, TransactionErrorCode>();

    static {
        for (TransactionErrorCode c : EnumSet.allOf(TransactionErrorCode.class))
            lookup.put(c.code, c);
    }


    private TransactionErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String toString() {
        return code + ":" + description;
    }

    public String getCode() {
        return code;
    }

    public static List<String> getTransactionCodes() {
        ArrayList<String> list = new ArrayList<String>();
        for (TransactionErrorCode c : EnumSet.allOf(TransactionErrorCode.class))
            list.add(c.getCode() + " : " + c.getDescription());
        return list;
    }

    public String getDescription() {
        return description;
    }

    public static TransactionErrorCode lookUp(String code) {
        return lookup.get(code);
    }
}
