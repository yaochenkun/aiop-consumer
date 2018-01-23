package org.bupt.aiop;

import org.bupt.aiop.common.kafka.AbstractMsgHandler;
import org.bupt.aiop.common.kafka.KafkaConsts;
import org.bupt.aiop.common.kafka.MessageConsumer;
import org.bupt.aiop.handler.email.SendEmailToMultiByCopyHandler;
import org.bupt.aiop.handler.email.SendEmailToMultiBySecretHandler;
import org.bupt.aiop.handler.email.SendEmailToSingleHandler;
import org.bupt.aiop.handler.sms.SendSmsToSingleHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 启动消费者进程
 */
public class ConsumersLaucher {

    private static final Logger logger = LoggerFactory.getLogger(ConsumersLaucher.class);

    private static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {

        /**
         * 开启邮件服务消费者
         */
        // 创建TOPIC_SEND_EMAIL_TO_SINGLE的消息消费者群
        startConsumerThreads(KafkaConsts.TOPIC_SEND_EMAIL_TO_SINGLE,
                             KafkaConsts.GROUPID_SEND_EMAIL_TO_SINGLE,
                             KafkaConsts.THREADNUM_SEND_EMAIL_TO_SINGLE,
                             new SendEmailToSingleHandler());

        // 创建TOPIC_SEND_EMAIL_TO_MULTI_BY_COPY的消息消费者群
        startConsumerThreads(KafkaConsts.TOPIC_SEND_EMAIL_TO_MULTI_BY_COPY,
                KafkaConsts.GROUPID_SEND_EMAIL_TO_MULTI_BY_COPY,
                KafkaConsts.THREADNUM_SEND_EMAIL_TO_MULTI_BY_COPY,
                new SendEmailToMultiByCopyHandler());

        // 创建TOPIC_SEND_EMAIL_TO_MULTI_BY_SECRET的消息消费者群
        startConsumerThreads(KafkaConsts.TOPIC_SEND_EMAIL_TO_MULTI_BY_SECRET,
                KafkaConsts.GROUPID_SEND_EMAIL_TO_MULTI_BY_SECRET,
                KafkaConsts.THREADNUM_SEND_EMAIL_TO_MULTI_BY_SECRET,
                new SendEmailToMultiBySecretHandler());

        /**
         * 开启短信服务消费者
         */
        // 创建TOPIC_SEND_SMS_TO_SINGLE的消息消费者群
        startConsumerThreads(KafkaConsts.TOPIC_SEND_SMS_TO_SINGLE,
                             KafkaConsts.GROUPID_SEND_SMS_TO_SINGLE,
                             KafkaConsts.THREADNUM_SEND_SMS_TO_SINGLE,
                             new SendSmsToSingleHandler());

        // 进程挂起不退出
        hangup();
        logger.info("kafka consumers(email and sms) have been successfully launched on PID = {}", getProcessID());
    }

    // 启动threadNum个消费者线程
    public static void startConsumerThreads(String topic, String groupId, Integer threadNum, AbstractMsgHandler handler) {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(threadNum);
        for (int i = 0; i < threadNum; i++) {
            MessageConsumer consumer = new MessageConsumer(topic, groupId, handler);
            fixedThreadPool.execute(consumer); // 启动消费者线程
        }
    }

    // 进程挂起
    public static void hangup() throws InterruptedException {
        while(true) {
            synchronized(lock) {  // 构建死锁消息
                lock.wait();
            }
        }
    }

    // 获取进程号
    public static final int getProcessID() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return Integer.valueOf(runtimeMXBean.getName().split("@")[0]).intValue();
    }

}
