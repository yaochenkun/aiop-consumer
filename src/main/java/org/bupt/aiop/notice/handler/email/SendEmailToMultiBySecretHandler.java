package org.bupt.aiop.notice.handler.email;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.bupt.aiop.common.kafka.AbstractMsgHandler;
import org.bupt.aiop.common.util.EmailSender;

import java.util.List;

/**
 * 发送邮件-多人暗送
 */
public class SendEmailToMultiBySecretHandler implements AbstractMsgHandler {

	@Override
	public void onMessage(JSONObject params) {
		try {
			EmailSender.sendToMultiBySecret((String) params.get("to"),
					(List<String>) params.get("multiTo"),
					(String) params.get("subject"),
					(String) params.get("content"),
					(String) params.get("footer"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
