package com.nahwu.kafkajavabenchmark.entity;

import lombok.Data;

@Data
public class TestRequest {
    private String payload;

    private int insertionSize;

    private String kafkaTopicName;

    private long consumerLogInterval;
}
