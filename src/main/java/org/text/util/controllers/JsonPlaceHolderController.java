package org.text.util.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.text.util.services.JsonPlaceHolderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/util/v1")
@Api(value = "Json Place Holder", description = "api to fetch the json place holder data ", tags = { "Text Utils" })
@Slf4j
public class JsonPlaceHolderController {

    @Autowired
    private JsonPlaceHolderService jsonPlaceHolderService;

    /**
     * Rest controller method to fetch the json place holder data
     * 
     * @param void
     * @return {@link JsonPlaceholders}
     */
    @ApiOperation(value = "Fetch Json Place Holder Data")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = String.class),
            @ApiResponse(code = 400, message = "Validation error"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @RequestMapping(value = "/jsonplaceholder", produces = { MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET)
    public ResponseEntity<String> fetchjsonPlaceHolderData() {
        log.debug("Received request to fetch json place holder data");
        String jsonString = jsonPlaceHolderService.service("");
        log.debug("Successfully completed request to fetch json place holder data");
        return new ResponseEntity<>(jsonString, HttpStatus.OK);
    }

}
