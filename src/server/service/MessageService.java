package server.service;

import model.Message;
import server.dao.MessageDAO;

import java.util.List;

public class MessageService {
    private static MessageService messageService;

    public static MessageService getInstance() {
        if (messageService == null) {
            messageService = new MessageService();
        }
        return messageService;
    }

    public List<Message> getRoomMessages(int roomId) {
        return MessageDAO.getInstance().getRoomMessages(roomId);
    }

    public Message addMessage(Message message) {
        int id = MessageDAO.getInstance().insertMessage(message);
        return getMessage(id);
    }

    public Message getMessage(int messageId) {
        return MessageDAO.getInstance().getMessage(messageId);
    }
}
