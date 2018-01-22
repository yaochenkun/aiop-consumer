package org.bupt.aiop.notice;

import org.bupt.aiop.common.kafka.AbstractMsgHandler;
import org.bupt.aiop.common.kafka.MessageConsumer;
import org.bupt.aiop.notice.handler.email.SendEmailToMultiByCopyHandler;
import org.bupt.aiop.notice.handler.email.SendEmailToMultiBySecretHandler;
import org.bupt.aiop.notice.handler.email.SendEmailToSingleHandler;
import org.bupt.aiop.notice.handler.sms.SendSmsToSingleHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 客户端测试(后期删除)
 */
public class ConsumerLaucher {

    private static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {

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

        // 创建TOPIC_SEND_SMS_TO_SINGLE的消息消费者群
        startConsumerThreads(KafkaConsts.TOPIC_SEND_SMS_TO_SINGLE,
                             KafkaConsts.GROUPID_SEND_SMS_TO_SINGLE,
                             KafkaConsts.THREADNUM_SEND_SMS_TO_SINGLE,
                             new SendSmsToSingleHandler());

        // 进程挂起不退出
        hangup();

//        // 创建通用消息生产者
//        MessageProducer messageProducer = new MessageProducer();
//
//        //等待用户输入，模拟生产消息
//        Scanner scanner = new Scanner(System.in);
//        int msgNumber = 1;
//        while (true) {
//            String msgContent = scanner.nextLine();
//
//            // 生产4条[发送邮件]消息
//            messageProducer.send(KafkaConsts.TOPIC_SEND_EMAIL, msgNumber++, msgContent);
//            messageProducer.send(KafkaConsts.TOPIC_SEND_EMAIL, msgNumber++, msgContent);
//            messageProducer.send(KafkaConsts.TOPIC_SEND_EMAIL, msgNumber++, msgContent);
//            messageProducer.send(KafkaConsts.TOPIC_SEND_EMAIL, msgNumber++, msgContent);
//
//            // 生产4条[发送短信]消息
////            messageProducer.send(KafkaConsts.TOPIC_SEND_SMS, msgNumber++, msgContent);
////            messageProducer.send(KafkaConsts.TOPIC_SEND_SMS, msgNumber++, msgContent);
////            messageProducer.send(KafkaConsts.TOPIC_SEND_SMS, msgNumber++, msgContent);
////            messageProducer.send(KafkaConsts.TOPIC_SEND_SMS, msgNumber++, msgContent);
//        }
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
            synchronized(lock) {
                lock.wait();
            }
        }
    }

}
