package org.bupt.aiop.common.kafka;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Map;
import java.util.Properties;

/**
 * kafka生产者
 */
public class MessageProducer extends Thread {

    private Producer<Integer, String> producer;

    private Properties props;

    private int msgNumber;

    private int maxPartitionNum = 100;

    public MessageProducer() {

        props = new Properties();

        // bootstrap.servers是新版的api
        props.put("bootstrap.servers", KafkaConsts.BROKER_ADDRESS_LIST);

        // ack方式，all，会等所有的commit，最慢的方式
        props.put("acks", "1");

        // 生产者放入partition的负载策略类
        props.put("partitioner.class", "org.bupt.aiop.common.kafka.RoundRobinPartitioner");

        // 键序列化类
        props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");

        // 值序列化类
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // // 失败是否重试，设置会有可能产生重复数据
        // props.put("retries", 0);
        //
        // // 对于每个partition的batch buffer大小
        // props.put("batch.size", 16384);
        //
        // // 等多久，如果buffer没满，比如设为1，即消息发送会多1ms的延迟，如果buffer没满
        // props.put("linger.ms", 1);
        //
        // // 整个producer可以用于buffer的内存大小
        // props.put("buffer.memory", 33554432);


        producer = new KafkaProducer<>(props);
    }

    /**
     * 发送消息
     */
    public void send(String topic, Map<String, Object> params) {

        msgNumber = (msgNumber + 1) % maxPartitionNum;
        String msgContent = JSON.toJSONString(params);

        // 发送
        producer.send(new ProducerRecord<>(topic, msgNumber, msgContent));

        System.out.println("producer thread = " + Thread.currentThread().getId() + " sent msg: msgNumber = " + msgNumber + " msgContent = " + msgContent);
    }
}
