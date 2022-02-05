package com.chatlog.service;

import java.util.List;

import com.chatlog.model.Message;

public interface ChatLogServiceInterface {
	
	public Integer saveMessage(String user, String msg, Long timestamp, Boolean isSent) throws Exception;
	public List<Message> getUserMessage(String user, int page, int limit) throws Exception;
	public boolean deleteMessage(String user) throws Exception;
	public boolean deleteMessageById(String user, Integer msgId) throws Exception;
	

}
