package com.onbelay.dagclientapp.dagnabit.controller;

import com.onbelay.core.controller.BaseRestController;
import com.onbelay.core.entity.snapshot.TransactionResult;
import com.onbelay.core.exception.OBRuntimeException;
import com.onbelay.dagclient.floatindex.snapshot.FloatIndexSnapshot;
import com.onbelay.dagclientapp.dagnabit.adapter.DagnabitRestAdapter;
import com.onbelay.dagclientapp.dagnabit.snapshot.DagModelCollection;
import com.onbelay.dagclientapp.dagnabit.snapshot.DagModelSnapshot;
import com.onbelay.dagclientapp.floatindex.adapter.FloatIndexRestAdapter;
import com.onbelay.dagclientapp.floatindex.snapshot.FileResult;
import com.onbelay.dagclientapp.floatindex.snapshot.FloatIndexCollection;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@Tag(name="Dagnabit", description = "APIs to manage Dag Models.")
@RequestMapping("/api/models")
public class DagnabitRestController extends BaseRestController {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private DagnabitRestAdapter dagnabitRestAdapter;

    @Operation(summary = "Save a new model", description = "Create a model.",
            tags = {"model"})
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Operation Successful"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "406", description = "Not Acceptable"),
    })
    @RequestMapping(
            method = RequestMethod.POST,
            produces = "application/json",
            consumes = "application/json")
    public ResponseEntity<TransactionResult> saveModel(
            @RequestBody DagModelSnapshot snapshot,
            BindingResult bindingResult)  {

        if (bindingResult.getErrorCount() > 0) {
            logger.error("Errors on create/update FloatIndex POST");
            for (ObjectError error : bindingResult.getAllErrors()) {
                logger.error(error.toString());
            }

            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        TransactionResult result;
        try {
            result = dagnabitRestAdapter.saveModel(snapshot);
        } catch (OBRuntimeException p) {
            result = new TransactionResult(p.getErrorCode(), p.getParms());
            result.setErrorMessage(errorMessageService.getErrorMessage(p.getErrorCode()));
        } catch (RuntimeException bre) {
            result = new TransactionResult(bre.getMessage());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        return processResponse(result);
    }

    @Operation(summary = "Find zero or more models", description = "Find using an optional query",
            tags = {"model"})
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Operation Successful"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "406", description = "Not Acceptable"),
    })
    @RequestMapping(
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<DagModelCollection> findModels(
            @RequestHeader Map<String, String> headersIn,
            @RequestParam(value = "start", defaultValue = "0") int start,
            @RequestParam(value = "limit", defaultValue = "100") int limit,
            @RequestParam(value = "query", required = false) String query) {

        DagModelCollection collection;
        try {
            collection = dagnabitRestAdapter.findModels(
                    start,
                    limit,
                    query);
        } catch (OBRuntimeException e) {
            collection = new DagModelCollection(e.getErrorCode(), e.getParms());
            collection.setErrorMessage(errorMessageService.getErrorMessage(e.getErrorCode()));
        } catch (RuntimeException e) {
            collection = new DagModelCollection(e.getMessage());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        if (collection.isSuccessful()) {
            return new ResponseEntity<>(collection, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(collection, headers, HttpStatus.BAD_REQUEST);
        }
    }

}
