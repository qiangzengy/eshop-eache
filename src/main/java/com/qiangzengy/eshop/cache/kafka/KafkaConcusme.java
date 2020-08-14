package com.qiangzengy.eshop.cache.kafka;



import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * kafka消费者
 */
public class KafkaConcusme implements Runnable{


    private final ConsumerConnector consumer;
    private final String topic;


    public KafkaConcusme(String topic) {
        consumer =  Consumer.createJavaConsumerConnector(createConsumerConfig());
        this.topic = topic;
    }

    @Override
    public void run() {

        Map<String, Integer> topicCountMap = new HashMap<>();
        topicCountMap.put(topic, 1);
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streams = (List<KafkaStream<byte[], byte[]>>) consumerMap.get(topic);

        for (final KafkaStream stream : streams) {
            new Thread(new KafkaMessageProcessor(stream)).start();
        }
    }


    private static ConsumerConfig createConsumerConfig() {
        Properties props = new Properties();
        props.put("zookeeper.connect", "192.168.31.187:2181,192.168.31.19:2181,192.168.31.227:2181");
        props.put("group.id", "eshop-cache-group");
        props.put("zookeeper.session.timeout.ms", "400");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");
        return new ConsumerConfig(props);
    }
}
