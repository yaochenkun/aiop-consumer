package org.bupt.aiop;

import org.bupt.aiop.common.kafka.MessageProducer;
import org.bupt.aiop.common.kafka.KafkaConsts;

import javax.mail.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProducerTest {
	public static void main(String[] args) throws MessagingException {

		// 创建通用消息生产者
        MessageProducer messageProducer = new MessageProducer();

        // 构造参数
        Map<String, Object> params = new HashMap<>();
        List<String> multiTo = new ArrayList<>();
		multiTo.add("ken19931108@sina.com");
		params.put("to", "yaochenkun@gmail.com");
		params.put("multiTo", multiTo);
		params.put("subject", "今天开心吗");
		params.put("content", "我是姚陈堃~~~~~");
		params.put("footer", "by chenkun");

		// 生产消息
		messageProducer.send(KafkaConsts.TOPIC_SEND_EMAIL_TO_MULTI_BY_COPY, params);
	}
}
