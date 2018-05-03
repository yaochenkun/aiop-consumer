package org.bupt.aiop.common.kafka;

/**
 * Kafka配置常量类
 */
public class KafkaConsts {

    /**
     * 发送邮件
     */
    // 单发邮件
    public static final String TOPIC_SEND_EMAIL_TO_SINGLE = "topic_send_email_to_single";
    public static final String GROUPID_SEND_EMAIL_TO_SINGLE = "groupid_send_email_to_single";
    public static final Integer THREADNUM_SEND_EMAIL_TO_SINGLE = 3;

    // 抄送邮件
    public static final String TOPIC_SEND_EMAIL_TO_MULTI_BY_COPY = "topic_send_email_to_multi_by_copy";
    public static final String GROUPID_SEND_EMAIL_TO_MULTI_BY_COPY = "groupid_send_email_to_multi_by_copy";
    public static final Integer THREADNUM_SEND_EMAIL_TO_MULTI_BY_COPY = 3;

    // 暗送邮件
    public static final String TOPIC_SEND_EMAIL_TO_MULTI_BY_SECRET = "topic_send_email_to_multi_by_secret";
    public static final String GROUPID_SEND_EMAIL_TO_MULTI_BY_SECRET = "groupid_send_email_to_multi_by_secret";
    public static final Integer THREADNUM_SEND_EMAIL_TO_MULTI_BY_SECRET = 3;

    /**
     * 发送短信（若需发送多个手机号，则发送多个该消息）
     */
    public static final String TOPIC_SEND_SMS_TO_SINGLE = "topic_send_sms_to_single";
    public static final String GROUPID_SEND_SMS_TO_SINGLE = "groupid_send_sms_to_single";
    public static final Integer THREADNUM_SEND_SMS_TO_SINGLE = 3;

    // Kafka环境地址配置
    public static final String BROKER_ADDRESS_LIST = "10.109.246.35:9092";
}
