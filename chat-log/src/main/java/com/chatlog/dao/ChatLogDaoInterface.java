package com.chatlog.dao;

import java.util.List;

import com.chatlog.entity.MessageEntity;

public interface ChatLogDaoInterface {
	
	public List<MessageEntity> getUserMessages(String userId, int page, int limit);
	public Integer saveMessage(String user, String msg, Long timestamp, Boolean isSent);
	public boolean deleteAllMessage(String user);
	public boolean deleteMessage(String user, Integer msgId);
	public boolean validMessage(String userId, int msgId);
}
