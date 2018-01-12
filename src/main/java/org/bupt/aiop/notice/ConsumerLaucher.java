package org.bupt.aiop.notice;

import org.bupt.aiop.common.kafka.AbstractMsgHandler;
import org.bupt.aiop.common.kafka.MessageConsumer;
import org.bupt.aiop.common.kafka.MessageProducer;
import org.bupt.aiop.notice.handler.SendEmailMsgHandler;
import org.bupt.aiop.notice.handler.SendSmsMsgHandler;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 客户端测试(后期删除)
 * @author zlren
 * @date 2017-12-09
 */
public class ConsumerLaucher {

    public static void main(String[] args) throws InterruptedException {

        // 创建TOPIC_SEND_EMAIl的消息消费者群
        startConsumerThreads(KafkaProperties.TOPIC_SEND_EMAIL,
                             KafkaProperties.GROUPID_SEND_EMAIL,
                             KafkaProperties.THREADNUM_SEND_EMAIL,
                             new SendEmailMsgHandler());

        // 创建TOPIC_SEND_SMS的消息消费者群
        startConsumerThreads(KafkaProperties.TOPIC_SEND_SMS,
                             KafkaProperties.GROUPID_SEND_SMS,
                             KafkaProperties.THREADNUM_SEND_SMS,
                             new SendSmsMsgHandler());

        Thread.sleep(1000);

        // 创建通用消息生产者
        MessageProducer messageProducer = new MessageProducer();

        //等待用户输入，模拟生产消息
        Scanner scanner = new Scanner(System.in);
        int msgNumber = 1;
        while (true) {
            String msgContent = scanner.nextLine();

            // 生产4条[发送邮件]消息
            messageProducer.send(KafkaProperties.TOPIC_SEND_EMAIL, msgNumber++, msgContent);
            messageProducer.send(KafkaProperties.TOPIC_SEND_EMAIL, msgNumber++, msgContent);
            messageProducer.send(KafkaProperties.TOPIC_SEND_EMAIL, msgNumber++, msgContent);
            messageProducer.send(KafkaProperties.TOPIC_SEND_EMAIL, msgNumber++, msgContent);

            // 生产4条[发送短信]消息
//            messageProducer.send(KafkaProperties.TOPIC_SEND_SMS, msgNumber++, msgContent);
//            messageProducer.send(KafkaProperties.TOPIC_SEND_SMS, msgNumber++, msgContent);
//            messageProducer.send(KafkaProperties.TOPIC_SEND_SMS, msgNumber++, msgContent);
//            messageProducer.send(KafkaProperties.TOPIC_SEND_SMS, msgNumber++, msgContent);
        }
    }

    // 启动threadNum个消费者线程
    public static void startConsumerThreads(String topic, String groupId, Integer threadNum, AbstractMsgHandler handler) {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(threadNum);
        for (int i = 0; i < threadNum; i++) {
            MessageConsumer consumer = new MessageConsumer(topic, groupId, handler);
            fixedThreadPool.execute(consumer); // 启动消费者线程
        }
    }

}
