package org.bupt.aiop.common.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

/**
 * 邮件发送器
 */
public class EmailSender {

	private static final String smtpAuth = "true"; // 是否开启认证
	private static final String smtpHost = "smtp.163.com"; // 发件人邮箱所在的SMTP服务
	private static final String username = "ken19931108@163.com"; // 发件人邮箱账号
	private static final String password = "89085128kk"; // 发件人邮箱密码
	private static final Properties props = new Properties();
	private static final Authenticator authenticator = new Authenticator() {
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}
	};
	static {
		props.put("mail.smtp.auth", smtpAuth);
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.user", username);
		props.put("mail.password", password);
	}

	/**
	 * 发送邮件到单个邮箱
	 * @param to 收件人邮箱地址
	 * @param subject 邮件标题
	 * @param content 邮件内容
	 * @param footer 邮件落款
	 * @throws MessagingException
	 */
	public static void sendToSingle(String to, String subject, String content, String footer) throws MessagingException{

		// 使用环境属性和授权信息，创建邮件会话
		Session mailSession = Session.getInstance(props, authenticator);

		// 创建邮件消息
		MimeMessage message = new MimeMessage(mailSession);

		// 设置发件人
		message.setFrom(new InternetAddress(username));

		// 设置收件人
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

		// 设置邮件标题
		message.setSubject(subject);

		// 设置邮件的内容体
		message.setContent("<span>" + content + "</span><br><br><br><strong>" + footer + "</strong>", "text/html;charset=UTF-8");

		// 发送邮件
		Transport.send(message);
	}

	/**
	 * 抄送邮件到多个邮箱
	 * @param to 收件人邮箱地址
	 * @param multiTo 抄送收件人邮箱地址组
	 * @param subject 邮件标题
	 * @param content 邮件内容
	 * @param footer 邮件落款
	 * @throws MessagingException
	 */
	public static void sendToMultiByCopy(String to, List<String> multiTo, String subject, String content, String footer) throws MessagingException{

		// 使用环境属性和授权信息，创建邮件会话
		Session mailSession = Session.getInstance(props, authenticator);

		// 创建邮件消息
		MimeMessage message = new MimeMessage(mailSession);

		// 设置发件人
		message.setFrom(new InternetAddress(username));

		// 设置收件人
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

		// 设置抄送的所有收件人
		Address[] addresses = new Address[multiTo.size()];
		for (int i = 0; i < multiTo.size(); i++) {
			addresses[i] = new InternetAddress(multiTo.get(i));
		}
		message.setRecipients(Message.RecipientType.CC, addresses);

		// 设置邮件标题
		message.setSubject(subject);

		// 设置邮件的内容体
		message.setContent("<span>" + content + "</span><br><br><br><strong>" + footer + "</strong>", "text/html;charset=UTF-8");

		// 发送邮件
		Transport.send(message);
	}

	/**
	 * 暗送邮件到多个邮箱
	 * @param to 收件人邮箱地址
	 * @param multiTo 暗送收件人邮箱地址组
	 * @param subject 邮件标题
	 * @param content 邮件内容
	 * @param footer 邮件落款
	 * @throws MessagingException
	 */
	public static void sendToMultiBySecret(String to, List<String> multiTo, String subject, String content, String footer) throws MessagingException{

		// 使用环境属性和授权信息，创建邮件会话
		Session mailSession = Session.getInstance(props, authenticator);

		// 创建邮件消息
		MimeMessage message = new MimeMessage(mailSession);

		// 设置发件人
		message.setFrom(new InternetAddress(username));

		// 设置收件人
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

		// 设置抄送的所有收件人
		Address[] addresses = new Address[multiTo.size()];
		for (int i = 0; i < multiTo.size(); i++) {
			addresses[i] = new InternetAddress(multiTo.get(i));
		}
		message.setRecipients(Message.RecipientType.BCC, addresses);

		// 设置邮件标题
		message.setSubject(subject);

		// 设置邮件的内容体
		message.setContent("<span>" + content + "</span><br><br><br><strong>" + footer + "</strong>", "text/html;charset=UTF-8");

		// 发送邮件
		Transport.send(message);
	}

}
