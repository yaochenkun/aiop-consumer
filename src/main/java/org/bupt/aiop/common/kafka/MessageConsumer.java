package org.bupt.aiop.common.kafka;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.Collections;
import java.util.Properties;

/**
 * Kafka消费者
 */
public class MessageConsumer extends Thread{

    private String topic;

    private String groupId;

    private Consumer<Integer, String> consumer;

    private AbstractMsgHandler handler;

    private Properties configs;

    public MessageConsumer(String topic, String groupId, AbstractMsgHandler handler) {

        this.topic = topic;
        this.groupId = groupId;
        this.handler = handler;

        configs = new Properties();
        configs.put("bootstrap.servers", KafkaConsts.BROKER_ADDRESS_LIST);
        configs.put("group.id", this.groupId);
        configs.put("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
        configs.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        consumer = new KafkaConsumer<>(configs);
        consumer.subscribe(Collections.singletonList(this.topic));
    }

    @Override
    public void run() {
        while (true) {
            ConsumerRecords<Integer, String> records = consumer.poll(1000);

            // 遍历所有分区，寻找是否有topic数据生产出来
            for (TopicPartition partition : records.partitions()) {
                // 遍历当前分区下的数据
                for (ConsumerRecord<Integer, String> record : records.records(partition)) {
                    if (record.value() == null || record.key() == null) {
                        consumer.commitSync();
                    } else {
                        // 处理消息
                        System.out.println("consumer[thread = "+ Thread.currentThread().getName() + ", topic = " + this.topic + ", groupId = " + this.groupId + "] is handling msg[offset = " + record.offset() + ", msgNumber = " + record.key() + ", msgContent = " + record.value() + ", partition = " + record.partition() + "]");
                        handler.onMessage(JSON.parseObject(record.value()));
                    }
                }
            }
        }
    }
}
