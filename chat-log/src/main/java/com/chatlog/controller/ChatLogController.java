package com.chatlog.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.chatlog.model.Message;
import com.chatlog.service.ChatLogServiceInterface;

@RestController
@RequestMapping("ofBusiness")
public class ChatLogController {

	@Autowired
	ChatLogServiceInterface chatLogService;

	@GetMapping(value = "/chatlogs/{user}")
	public ResponseEntity<Object> getUserMessage(@PathVariable String user,
			@RequestParam("page") Optional<Integer> page, @RequestParam("limit") Optional<Integer> limit) {
		int pageNumber = 1;
		int limitPerPage = 10;
		if (page.isPresent())
			pageNumber = page.get();
		if (limit.isPresent())
			limitPerPage = limit.get();
		try {
			List<Message> messages = chatLogService.getUserMessage(user, pageNumber, limitPerPage);
			if (messages.size() > 0)
				return new ResponseEntity<>(messages, HttpStatus.OK);
			else
				return new ResponseEntity<>("No record found", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Something went wrong", HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	@PostMapping(value = "/chatlogs/{userId}")
	public ResponseEntity<Object> saveUserChatLogs(@PathVariable String userId, @RequestBody Optional<Message> msg,
			@RequestParam(value = "message", required = false) String message,
			@RequestParam(value = "timestamp", required = false) Long timestamp,
			@RequestParam(value = "isSent", required = false) String isSent) {
		try {
			if (msg.isPresent()) {
				Message m = msg.get();
				return new ResponseEntity<>(
						(chatLogService.saveMessage(userId, m.getMsg(), m.getTimestamp(), m.getIsSent())),
						HttpStatus.OK);
			}
			return new ResponseEntity<>(
					(chatLogService.saveMessage(userId, message, timestamp, Boolean.parseBoolean(isSent))),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Something went wrong", HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	@DeleteMapping(value = { "/chatlogs/{user}", "/chatlogs/{user}/{msgId}" })
	public ResponseEntity<Object> deleteUserChatLogs(@PathVariable String user,
			@PathVariable(value = "msgId", required = false) String msgId) {
		try {

			if (msgId != null) {
				if ((chatLogService.deleteMessageById(user, Integer.parseInt(msgId))))
					return new ResponseEntity<>("Deleted Successfully!!", HttpStatus.OK);
				else
					return new ResponseEntity<>("No record found!!", HttpStatus.BAD_REQUEST);
			} else {
				if (chatLogService.deleteMessage(user))
					return new ResponseEntity<>("Deleted Successfully!!", HttpStatus.OK);
				else
					return new ResponseEntity<>("No record found!!", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Something went wrong", HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
}
