package com.nahwu.kafkajavabenchmark.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

@Service
public class TestServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);

    @Value("${service.kafka.address}")
    private String kafkaEndpoint;
    @Value("${service.kafka.port}")
    private String kafkaPort;

    private double getRandomNumberUsingNextDouble(double min, double max) {
        Random random = new Random();
        return random.nextDouble(max - min) + min;
    }

    private String getRandomStringFromList(ArrayList<String> list) {
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }

    public long startReadingLotsOfData(String kafkaTopic, long consumerLogInterval) {
        Properties props2 = new Properties();
        props2.put("bootstrap.servers", kafkaEndpoint + ":" + kafkaPort);
        props2.put("group.id", "ha7");
        props2.put("enable.auto.commit", "false");
        props2.put("auto.offset.reset", "earliest");
        //props2.put("fetch.message.max.bytes", "10000000");
        props2.put("max.partition.fetch.bytes", "10000000");
        //props.put("enable.auto.commit", "true");
        //props.put("auto.commit.interval.ms", "1000");
        props2.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props2.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        /***********
         // SECURITY
         ************/
        props2.put("ssl.endpoint.identification.algorithm", " ");
        /*
        props2.put("ssl.truststore.location", "kafka.truststore.jks");
        props2.put("ssl.truststore.password", "vmware");
        */

        // SECURITY - SSL ONLY
        //props2.put("security.protocol", "SSL");

        // SECURITY - SASL + SSL. Username/password over TLS

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props2);
        consumer.subscribe(Arrays.asList(kafkaTopic));
        logger.info("__Kafka Consumer Loop starting");
        long currentIndex = 1;
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5));
            for (ConsumerRecord<String, String> record : records) {
                if (currentIndex % consumerLogInterval == 0 || currentIndex == 1234567) {
                    logger.info("Offset: " + record.offset() + " Key: " + record.key() + " Value: " + record.value());
                }
                currentIndex++;
            }
        }
    }

    public void writeLotsOfData(int insertionSize, String kafkaTopicName) {
        //--------------------------------------------------
        // [OPTIONAL] Code Segment For Producing to a Kafka Topic
        //--------------------------------------------------
        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaEndpoint + ":" + kafkaPort);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        ///////////////
        // SECURITY
        ///////////////
        props.put("ssl.endpoint.identification.algorithm", " ");
        /*
        props.put("ssl.truststore.location", "kafka.truststore.jks");
        props.put("ssl.truststore.password", "vmware");
        */

        // SECURITY - SSL ONLY
        //props.put("security.protocol", "SSL");

        // SECURITY - SASL + SSL. Username/password over TLS
        /*
        props.put("security.protocol", "SASL_SSL");
        props.put("sasl.mechanism", "PLAIN");
        props.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"$ConnectionString\" password=\"Endpoint=sb://your-kafka-url/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=v3INFPCQ8QOdsCR72RCCOzgRGFsBs6/7TTPj1n0KPgM=\";\n ");
        */

        logger.info("__Kafka Producer starting");
        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 1; i <= insertionSize; i++) {
            producer.send(new ProducerRecord<>(kafkaTopicName, Integer.toString(i),
                    "{\"id\":\"" + i + "_WOW" + "\"," +
                            " \"payload\": \"c2RmYXNmdnNkZmR2ZmRzMjRmcjNc2c2RmYXNmdnNkZmR2ZmRzMjRmcjNc2RmYXNmdnNkZmR2ZmRzMjRmcjNc2RmYXNmdnNkZmR2ZmRzMjRmcjNc2RmYXNmdnNkZmR2ZmRzMjRmcjNc2RmYXNmdnNkZmR2ZmRzM0MzR0MzR0NHQzdHZ3NXQ1dHd0MzR0c2RmYXNmdnNkZmR2ZmRzMjRmcjN0MzR0MzR0NHQzdHZ3NXQ1dHd0MzR0c2RmYXNmdnNkZmR2ZmRzMjRmcjN0MzR0MzR0NHQzdHZ3NXQ1dHd0MzR0c2RmYXNmdnNkZmR2ZmRzMjRmcjN0MzR0MzR0NHQzdHZ3NXQ1dHd0MzR0c2RmYXNmdnNkZmR2ZmRzMjRmcjN0MzR0MzR0NHQzdHZ3NXQ1dHd0MzR0c2RmYXNmdnNkZmR2ZmRzMjRmcjN0MzR0MzR0NHQzdHZ3NXQ1dHd0MzR0c2RmYXNmdnNkZmR2ZmRzMjRmcjN0MzR0MzR0NHQzdHZ3NXQ1dHd0MzR0c2RmYXNmdnNkZmR2ZmRzMjRmcjN0MzR0MzR0NHQzdHZ3NXQ1dHd0MzR0c2RmYXNmdnNkZmR2ZmRzMjRmcjN0MzR0MzR0NHQzdHZ3NXQ1dHd0MzR0c2RmYXNmdnNkZmR2ZmRzMjRmcjN0MzR0MzR0NHQzdHZ3NXQ1dHd0MzR0c2RmYXNmdnNkZmR2ZmRzMjRmcjN0MzR0MzR0NHQzdHZ3NXQ1dHd0MzR0c2RmYXNmdnNkZmR2ZmRzMjRmcjN0MzR0MzR0NHQzdHZ3NXQ1dHd0MzR0c2RmYXNmdnNkZmR2ZmRzMjRmcjN0MzR0MzR0NHQzdHZ3NXQ1dHd0MzR0c2RmYXNmdnNkZmR2ZmRzMjRmcjN0MzR0MzR0NHQzdHZ3NXQ1dHd0MzR0c2RmYXNmdnNkZmR2ZmRzMjRmcjN0MzR0MzR0NHQzdHZ3NXQ1dHd0MzR0c2RmYXNmdnNkZmR2ZmRzMjRmcjN0MzR0MzR0NHQzdHZ3NXQ1dHd0MzR0\"" +
                            "}"));
            //logger.info("Produced msg #" + i);
        }
        logger.info("__Kafka Producer closing");
        producer.close();
    }
}
