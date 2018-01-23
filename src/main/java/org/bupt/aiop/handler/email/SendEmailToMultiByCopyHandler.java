package org.bupt.aiop.handler.email;

import com.alibaba.fastjson.JSONObject;
import org.bupt.aiop.common.kafka.AbstractMsgHandler;
import org.bupt.aiop.common.util.EmailSender;

import java.util.List;

/**
 * 发送邮件-多人抄送
 */
public class SendEmailToMultiByCopyHandler implements AbstractMsgHandler {

	@Override
	public void onMessage(JSONObject params) {
		try {
			EmailSender.sendToMultiByCopy((String) params.get("to"),
					(List<String>) params.get("multiTo"),
					(String) params.get("subject"),
					(String) params.get("content"),
					(String) params.get("footer"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
