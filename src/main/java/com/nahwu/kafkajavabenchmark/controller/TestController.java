package com.nahwu.kafkajavabenchmark.controller;

import com.nahwu.kafkajavabenchmark.entity.TestRequest;
import com.nahwu.kafkajavabenchmark.service.TestServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private TestServiceImpl testService;

    @PostMapping("/v1/test/api/length")
    @Operation(summary = "Test API and reply with the request length")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "API working",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class))})})
    public ResponseEntity<?> testApiLength(
            @RequestBody TestRequest testRequest) {
        return new ResponseEntity<>(testRequest.getPayload().length(), HttpStatus.OK);
    }

    @PostMapping("/v1/test/kafka/test")
    @Operation(summary = "Test Kafka")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "API working",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class))})})
    public ResponseEntity<?> testKafka() {
        //Iterable<TestDbObject> books = testService.insertAndSelectObjects();
        //logger.info(books.iterator().toString());

        return new ResponseEntity<>("Got a response!", HttpStatus.OK);
    }

    @PostMapping("/v1/test/kafka/mass-insert")
    @Operation(summary = "Test Kafka - Mass insertion of data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "API working",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class))})})
    public ResponseEntity<?> writeLotsOfDataToKafka(
            @RequestBody TestRequest testRequest) {
        testService.writeLotsOfData(testRequest.getInsertionSize(), testRequest.getKafkaTopicName());
        return new ResponseEntity<>("Written!", HttpStatus.OK);
    }

    @PostMapping("/v1/test/kafka/read-a-lot")
    @Operation(summary = "Test Kafka - Mass insertion of data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "API working",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class))})})
    public ResponseEntity<?> readLotsOfDataFromKafka(@RequestBody TestRequest testRequest) {
        testService.startReadingLotsOfData(testRequest.getKafkaTopicName(), testRequest.getConsumerLogInterval());
        return new ResponseEntity<>("Started to read messages from Kafka", HttpStatus.OK);
    }
}
