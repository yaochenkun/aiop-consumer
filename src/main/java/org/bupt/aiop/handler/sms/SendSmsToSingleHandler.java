package org.bupt.aiop.handler.sms;

import com.alibaba.fastjson.JSONObject;
import org.bupt.aiop.common.kafka.AbstractMsgHandler;
import org.bupt.aiop.common.util.SmsSender;

/**
 * 发送短信-单人
 */
public class SendSmsToSingleHandler implements AbstractMsgHandler {

	@Override
	public void onMessage(JSONObject params) {
		try {
			SmsSender.send((String) params.get("to"),
					(String) params.get("content"),
					(String) params.get("footer"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
