package org.text.util.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.text.util.binding.v1.TextCreateRequest;
import org.text.util.binding.v1.TextDeleteRequest;
import org.text.util.binding.v1.TextResponse;
import org.text.util.documentation.Notes;
import org.text.util.domain.TextDomain;
import org.text.util.services.CreateTextService;
import org.text.util.services.DeleteTextService;
import org.text.util.services.ReadTextService;
import org.text.util.transformers.TextCreateRequestTransformer;
import org.text.util.transformers.TextDeleteRequestTransformer;
import org.text.util.transformers.TextReadRequestTransformer;
import org.text.util.transformers.TextResponseTransformer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/util/v1")
@Api(value = "Text Utils", description = "Text util apis to manage arbitrary text ", tags = { "Text Utils" })
@Slf4j
public class TextUtilController {

    @Autowired
    private TextCreateRequestTransformer textCreateRequestTransformer;
    
    @Autowired
    private TextReadRequestTransformer textReadRequestTransformer;

    @Autowired
    private TextDeleteRequestTransformer textDeleteRequestTransformer;

    @Autowired
    private TextResponseTransformer textResponseTransformer;

    @Autowired
    private CreateTextService createTextService;

    @Autowired
    private ReadTextService readTextService;

    @Autowired
    private DeleteTextService deleteTextService;

    /**
     * Rest controller method to save the arbitrary text in the request to the
     * data base.
     * 
     * @param textCreateRequest
     * @return {@link TextResponse}
     */
    @ApiOperation(value = "Create Text", notes = Notes.TEXT_CREATE)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = TextResponse.class),
            @ApiResponse(code = 400, message = "Validation error"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @RequestMapping(value = "/text", consumes = { MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    public ResponseEntity<TextResponse> createText(@RequestBody TextCreateRequest textCreateRequest) {
        log.debug("Received request to save text");
        TextDomain textDomain = textCreateRequestTransformer.transform(textCreateRequest);
        textDomain = createTextService.service(textDomain);
        TextResponse textResponse = textResponseTransformer.transform(textDomain);
        log.debug("Successfully completed request to save text");
        return new ResponseEntity<>(textResponse, HttpStatus.OK);
    }

    /**
     * Rest controller method to read the text from database
     * 
     * @param text
     * @return {@link TextResponse}
     */
    @ApiOperation(value = "Read Text", notes = Notes.TEXT_READ)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = TextResponse.class),
            @ApiResponse(code = 400, message = "Validation error"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @RequestMapping(value = "/text/textId/{textId}", produces = { MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET)
    public ResponseEntity<TextResponse> readText(@PathVariable("textId") String textId) {
        log.debug("Received request to read text");
        TextDomain textDomain = textReadRequestTransformer.transform(textId);
        textDomain = readTextService.service(textDomain);
        TextResponse textResponse = textResponseTransformer.transform(textDomain);
        log.debug("Successfully completed request to read text");
        return new ResponseEntity<>(textResponse, HttpStatus.OK);
    }

    /**
     * Rest controller method to read the text from database
     * 
     * @param textDeleteRequest
     * @return {@link TextResponse}
     */
    @ApiOperation(value = "Delete Text", notes = Notes.TEXT_DELETE)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = TextResponse.class),
            @ApiResponse(code = 400, message = "Validation error"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @RequestMapping(value = "/text", consumes = { MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.DELETE)
    public ResponseEntity<TextResponse> deleteText(@RequestBody TextDeleteRequest textDeleteRequest) {
        log.debug("Received request to delete text");
        TextDomain textDomain = textDeleteRequestTransformer.transform(textDeleteRequest);
        textDomain = deleteTextService.service(textDomain);
        TextResponse textResponse = textResponseTransformer.transform(textDomain);
        log.debug("Successfully deleted text");
        return new ResponseEntity<>(textResponse, HttpStatus.OK);
    }
    
}
