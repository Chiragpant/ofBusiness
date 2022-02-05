package com.chatlog.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatlog.entity.MessageEntity;

@SuppressWarnings("unchecked")
@Repository
public class ChatLogDao implements ChatLogDaoInterface {

	@Autowired
	SessionFactory sessionFactory;

	public List<MessageEntity> getUserMessages(String userId, int page, int limit) {
		Session session = sessionFactory.getCurrentSession();
		try {

			String hql = "FROM MessageEntity " + "WHERE userId = :user order by timestamp desc";
			Query query = session.createQuery(hql);
			query.setParameter("user", userId);
			List<MessageEntity> result = (List<MessageEntity>) query.setFirstResult((page - 1) * limit)
					.setMaxResults(limit).getResultList();

			return result;

		} catch (Exception e) {
			throw e;
		}
	}

	public Integer saveMessage(String user, String msg, Long timestamp, Boolean isSent) {
		Session session = sessionFactory.getCurrentSession();
		try {
			int sent = isSent == true ? 1 : 0;
			MessageEntity m = new MessageEntity(user, msg, timestamp, sent);
			Integer id = (Integer) session.save(m);

			return id;
		} catch (Exception e) {
			throw e;
		}
	}

	public boolean deleteAllMessage(String user) {
		Session session = sessionFactory.getCurrentSession();
		try {
			String hql = "DELETE FROM MessageEntity " + "WHERE userId = :user";
			Query query = session.createQuery(hql);
			query.setParameter("user", user);
			int result = query.executeUpdate();
			if (result > 0)
				return true;
			return false;
		} catch (Exception e) {
			throw e;
		}
	}

	public boolean deleteMessage(String user, Integer msgId) {

		Session session = sessionFactory.getCurrentSession();
		try {
			String hql = "DELETE FROM MessageEntity " + "WHERE userId = :user AND msgId= :msgId";
			Query query = session.createQuery(hql);
			query.setParameter("user", user).setParameter("msgId", msgId);
			System.out.println("here2");
			int result = query.executeUpdate();
			if (result > 0)
				return true;
			return false;
		} catch (Exception e) {
			throw e;
		}
	}

	public boolean validMessage(String userId, int msgId) {
		Session session = sessionFactory.getCurrentSession();
		try {

			String hql = "FROM MessageEntity " + "WHERE userId = :user AND msgId = :msgId";
			Query query = session.createQuery(hql);
			query.setParameter("user", userId);
			query.setParameter("msgId", msgId);
			List<MessageEntity> message = (List<MessageEntity>) query.getResultList();
			System.out.println(message.size());
			if (message.size() < 1)
				return false;
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

}
