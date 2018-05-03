package org.bupt.aiop.handler.email;

import com.alibaba.fastjson.JSONObject;
import org.bupt.aiop.common.kafka.AbstractMsgHandler;
import org.bupt.aiop.common.util.EmailSender;

/**
 * 发送邮件-单人
 */
public class SendEmailToSingleHandler implements AbstractMsgHandler {

	@Override
	public void onMessage(JSONObject params) {
		try {
			EmailSender.sendToSingle((String) params.get("fromName"),
					(String) params.get("to"),
					(String) params.get("subject"),
					(String) params.get("content"),
					(String) params.get("footer"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
