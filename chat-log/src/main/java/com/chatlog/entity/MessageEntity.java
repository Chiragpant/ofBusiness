package com.chatlog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "message")
public class MessageEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer msgId;
	@Column
	private String userId;
	@Column
	private String message;
	@Column
	private Long timestamp;
	@Column
	private Integer isSent;
	
	
	
	
	public MessageEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MessageEntity( String userId, String message, Long timestamp, Integer isSent) {
		super();
		
		this.userId = userId;
		this.message = message;
		this.timestamp = timestamp;
		this.isSent=isSent;
	}
	public Integer getMsgId() {
		return msgId;
	}
	public void setMsgId(Integer msgId) {
		this.msgId = msgId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public Integer getIsSent() {
		return isSent;
	}
	public void setIsSent(Integer isSent) {
		this.isSent = isSent;
	}
	
	

}
