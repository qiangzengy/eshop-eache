package com.qiangzengy.eshop.cache.kafka;



import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

/**
 * kafka消费者
 */
@Slf4j
public class KafkaConcusme implements Runnable{


    //private final ConsumerConnector consumer;
    private final String topic;
    private final KafkaConsumer<String,String> consumer;

    //
//
    public KafkaConcusme(String topic) {
//        this.consumer =  Consumer.createJavaConsumerConnector(createConsumerConfig());
        this.consumer = new KafkaConsumer<>(initConfig());
        this.topic = topic;
    }
//
//    @Override
//    public void run() {
//        log.info("==========topic:{}",topic);
//        Map<String, Integer> topicCountMap = new HashMap<>();
//        topicCountMap.put(topic, 1);
//        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
//        List<KafkaStream<byte[], byte[]>> streams =consumerMap.get(topic);
//        for (final KafkaStream<byte[], byte[]> stream : streams) {
//            new Thread(new KafkaMessageProcessor(stream)).start();
//        }
//    }
//
//
//    private static ConsumerConfig createConsumerConfig() {
//        Properties props = new Properties();
//        props.put("zookeeper.connect", "192.168.1.51:2181,192.168.1.52:2181,192.168.1.53:2181");
//        props.put("group.id", "eshop-cache-group");
//        props.put("zookeeper.session.timeout.ms", "400");
//        props.put("zookeeper.sync.time.ms", "200");
//        props.put("auto.commit.interval.ms", "1000");
//        return new ConsumerConfig(props);
//    }

    private static Properties initConfig(){
        Properties properties = new Properties();
        properties.put("bootstrap.servers","192.168.1.51:9092,192.168.1.52:9092,192.168.1.53:9092");
        properties.put("group.id","0");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return properties;
    }


    @Override
    public void run() {
        log.info("==========topic:{}",topic);
        consumer.subscribe(Collections.singleton(topic));
            ConsumerRecords<String, String> records = consumer.poll(1000);
            for (ConsumerRecord<String, String> record : records) {
                log.info("数据：{}",record);
            }
    }
}
