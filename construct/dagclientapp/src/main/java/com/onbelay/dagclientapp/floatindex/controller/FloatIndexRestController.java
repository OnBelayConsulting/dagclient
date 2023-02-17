package com.onbelay.dagclientapp.floatindex.controller;

import com.onbelay.core.entity.snapshot.TransactionResult;
import com.onbelay.core.errorhandling.ErrorMessageService;
import com.onbelay.core.exception.OBRuntimeException;
import com.onbelay.dagclient.floatindex.snapshot.FloatIndexSnapshot;
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
@Tag(name="FloatIndex", description = "APIs to manage persisted FloatIndices.")
@RequestMapping("/api/floatIndices")public class FloatIndexRestController {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private FloatIndexRestAdapter floatIndexRestAdapter;
    
    @Autowired
    private ErrorMessageService errorMessageService;

    @Operation(summary = "Save a new FloatIndex", description = "Create a persisted FloatIndex by unique name.",
            tags = {"node"})
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Operation Successful"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "406", description = "Not Acceptable"),
    })
    @RequestMapping(
            method = RequestMethod.POST,
            produces = "application/json",
            consumes = "application/json")
    public ResponseEntity<TransactionResult> saveFloatIndex(
            @RequestBody FloatIndexSnapshot snapshot,
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
            result = floatIndexRestAdapter.save(snapshot);
        } catch (OBRuntimeException p) {
            result = new TransactionResult(p.getErrorCode(), p.getParms());
            result.setErrorMessage(errorMessageService.getErrorMessage(p.getErrorCode()));
        } catch (RuntimeException bre) {
            result = new TransactionResult(bre.getMessage());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        if (result.isSuccessful()) {
            return new ResponseEntity(result, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }

    }


    @Operation(summary = "Save multiple FloatIndexs", description = "Create multiple persisted FloatIndexs by unique names.",
            tags = {"model"})
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Operation Successful"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "406", description = "Not Acceptable"),
    })
    @RequestMapping(
            method = RequestMethod.PUT,
            produces = "application/json",
            consumes = "application/json")
    public ResponseEntity<TransactionResult> saveFloatIndexs(
            @RequestBody List<FloatIndexSnapshot> snapshots,
            BindingResult bindingResult)  {

        if (bindingResult.getErrorCount() > 0) {
            logger.error("Errors on create/update FloatIndex PUT");
            for (ObjectError error : bindingResult.getAllErrors()) {
                logger.error(error.toString());
            }

            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        TransactionResult result;
        try {
            result = floatIndexRestAdapter.save(snapshots);
        } catch (OBRuntimeException p) {
            result = new TransactionResult(p.getErrorCode(), p.getParms());
            result.setErrorMessage(errorMessageService.getErrorMessage(p.getErrorCode()));
        } catch (RuntimeException bre) {
            result = new TransactionResult(bre.getMessage());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        if (result.isSuccessful()) {
            return new ResponseEntity(result, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }

    }

    @Operation(summary = "Find zero or more FloatIndexs", description = "Find using an optional query - zero or more existing graph nodes",
            tags = {"node"})
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Operation Successful"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "406", description = "Not Acceptable"),
    })
    @RequestMapping(
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<FloatIndexCollection> findFloatIndexs(
            @RequestHeader Map<String, String> headersIn,
            @RequestParam(value = "start", defaultValue = "0") int start,
            @RequestParam(value = "limit", defaultValue = "100") int limit,
            @RequestParam(value = "query", required = false) String query) {

        FloatIndexCollection collection;
        try {
            collection = floatIndexRestAdapter.find(
                    start,
                    limit,
                    query);
        } catch (OBRuntimeException e) {
            collection = new FloatIndexCollection(e.getErrorCode(), e.getParms());
            collection.setErrorMessage(errorMessageService.getErrorMessage(e.getErrorCode()));
        } catch (RuntimeException e) {
            collection = new FloatIndexCollection(e.getMessage());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        if (collection.isSuccessful()) {
            return new ResponseEntity<>(collection, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(collection, headers, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Upload a csv FloatIndexs file", description = "Upload a file in CSV format describing graph nodes to add.",
            tags = {"node"})
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Operation Successful"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "406", description = "Not Acceptable"),
    })
    @RequestMapping(
            value = "/file",
            produces = "application/json",
            method = RequestMethod.POST
    )
    public ResponseEntity<TransactionResult> uploadFile(
            @RequestHeader Map<String, String> headersIn,
            @RequestParam("name") String name,
            @RequestParam("file") MultipartFile file) {

        TransactionResult result;

        try {
            result = floatIndexRestAdapter.uploadFile(
                    name,
                    file.getBytes());
        } catch (OBRuntimeException e) {
            result = new TransactionResult(e.getErrorCode(), e.getParms());
            result.setErrorMessage(errorMessageService.getErrorMessage(e.getErrorCode()));
        } catch (RuntimeException e) {
            result = new TransactionResult(e.getMessage());
        } catch (IOException e) {
            logger.error("File upload failed", e);
            result = new TransactionResult("Invalid File");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        if (result.isSuccessful()) {
            return new ResponseEntity<>(result, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, headers, HttpStatus.BAD_REQUEST);
        }


    }

    @Operation(summary = "Download a csv FloatIndexs file", description = "Download a file in CSV format with an optional query to filter..",
            tags = {"node"})
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Operation Successful"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "406", description = "Not Acceptable"),
    })
    @RequestMapping(method = RequestMethod.GET, produces ="application/text")
    public HttpEntity<byte[]> generateCSVFile(
            @RequestHeader Map<String, String> headersIn,
            @RequestParam(value = "query", defaultValue = "WHERE") String query) {

        FileResult fileResult;


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);


        try {
            fileResult = floatIndexRestAdapter.generateCSVFile(query);
            if (fileResult.wasSuccessful()) {
                headers.set("Content-Disposition", "attachment; fileName=" + fileResult.getFileName());
                return new HttpEntity<byte[]>(fileResult.getContents(),  headers);
            } else {
                return new HttpEntity<byte[]>(null, headers);
            }
        } catch (OBRuntimeException p) {
            return new HttpEntity<byte[]>(null, headers);
        } catch (RuntimeException e) {
            return new HttpEntity<byte[]>(null, headers);
        }

    }

}
