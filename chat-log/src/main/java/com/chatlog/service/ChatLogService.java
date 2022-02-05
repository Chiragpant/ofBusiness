package com.chatlog.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chatlog.dao.ChatLogDaoInterface;
import com.chatlog.entity.MessageEntity;
import com.chatlog.model.Message;

@Service
public class ChatLogService implements ChatLogServiceInterface {

	@Autowired
	ChatLogDaoInterface chatLogDao;

	@Transactional
	public Integer saveMessage(String user, String msg, Long timestamp, Boolean isSent) throws Exception {
		try {
			return chatLogDao.saveMessage(user, msg, timestamp, isSent);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Transactional
	public List<Message> getUserMessage(String user, int page, int limit) throws Exception {
		try {
			List<MessageEntity> entity = chatLogDao.getUserMessages(user, page, limit);
			List<Message> result = new ArrayList<Message>();
			for (int i = 0; i < entity.size(); i++) {
				Message message = new Message();
				message.setMsg(entity.get(i).getMessage());
				message.setTimestamp(entity.get(i).getTimestamp());
				message.setUserId(entity.get(i).getUserId());
				message.setIsSent(entity.get(i).getIsSent() == 1 ? true : false);
				message.setMsgId(entity.get(i).getMsgId());
				result.add(message);
			}
			return result;
		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional
	public boolean deleteMessage(String user) throws Exception {
		try {

			return chatLogDao.deleteAllMessage(user);
		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional
	public boolean deleteMessageById(String user, Integer msgId) throws Exception {
		try {
			if (chatLogDao.validMessage(user, msgId))
				return chatLogDao.deleteMessage(user, msgId);
			else
				return false;
		} catch (Exception e) {
			throw e;
		}
	}

}
