package org.bupt.aiop.common.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.bupt.aiop.notice.KafkaProperties;

import java.util.Properties;

/**
 * kafka生产者
 *
 * @author zlren
 * @date 2017-12-09
 */
public class MessageProducer extends Thread {

    private Producer<Integer, String> producer;

    private Properties props;

    public MessageProducer() {

        props = new Properties();

        // bootstrap.servers是新版的api
        props.put("bootstrap.servers", KafkaProperties.BROKER_LIST);

        // ack方式，all，会等所有的commit，最慢的方式
        props.put("acks", "1");

        // 生产者放入partition的负载策略类
        props.put("partitioner.class", KafkaProperties.PATITIONER_CLASS);

        // 键序列化类
        props.put("key.serializer", KafkaProperties.INTEGER_SERIALIZER);

        // 值序列化类
        props.put("value.serializer", KafkaProperties.STRING_SERIALIZER);

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
    public void send(String topic, Integer msgNumber, String msgContent) {
        producer.send(new ProducerRecord<>(topic, msgNumber, msgContent));
        System.out.println("producer thread = " + Thread.currentThread().getId() + " sent msg: msgNumber = " + msgNumber + " msgContent = " + msgContent);
    }
}
