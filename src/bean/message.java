package bean;

import java.util.Date;

public class message {
	
	//发送的消息字符串
	private String msg;
	//发送者
	private String sender;
	//接收者
	private String sendTo;
	//发送时间
	private  Date sendTime;
	//bean方法
	//图片标志
	private boolean isImg;
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getSendTo() {
		return sendTo;
	}
	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public boolean isImg() {
		return isImg;
	}
	public void setImg(boolean isImg) {
		this.isImg = isImg;
	}
}
