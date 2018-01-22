package org.bupt.aiop.common.kafka;


import com.alibaba.fastjson.JSONObject;

/**
 * Kafka消费者处理逻辑
 */
public interface AbstractMsgHandler {

	/**
	 * 处理消息的行为定义
	 * @param params 消息实体
	 */
	void onMessage(JSONObject params);
}
