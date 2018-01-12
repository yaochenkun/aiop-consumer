package org.bupt.aiop.notice;

/**
 * @author zlren
 * @date 2017-12-09
 */
public class KafkaProperties {

    public static final String TOPIC_SEND_EMAIL = "topic_send_email";
    public static final String GROUPID_SEND_EMAIL = "groupid_send_email";
    public static final Integer THREADNUM_SEND_EMAIL = 3;

    public static final String TOPIC_SEND_SMS = "topic_send_sms";
    public static final String GROUPID_SEND_SMS = "groupid_send_sms";
    public static final Integer THREADNUM_SEND_SMS = 3;

    // 环境配置
    public static final String PATITIONER_CLASS = "org.bupt.aiop.common.kafka.RoundRobinPartitioner";
    public static final String INTEGER_SERIALIZER = "org.apache.kafka.common.serialization.IntegerSerializer";
    public static final String STRING_SERIALIZER = "org.apache.kafka.common.serialization.StringSerializer";
    public static final String INTEGER_DESERIALIZER = "org.apache.kafka.common.serialization.IntegerDeserializer";
    public static final String STRING_DESERIALIZER = "org.apache.kafka.common.serialization.StringDeserializer";

    public static final String BROKER_LIST = "10.109.246.68:9092";
}
