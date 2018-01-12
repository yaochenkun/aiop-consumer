package org.bupt.aiop.notice.handler;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.bupt.aiop.common.kafka.AbstractMsgHandler;

/**
 * 消费topic_send_sms
 */
public class SendSmsMsgHandler implements AbstractMsgHandler {

	@Override
	public void onMessage(ConsumerRecord<Integer, String> record) {
		//handle
	}
}
