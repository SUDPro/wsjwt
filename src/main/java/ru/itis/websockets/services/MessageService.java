package ru.itis.websockets.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.websockets.forms.MessageDto;
import ru.itis.websockets.models.Message;
import ru.itis.websockets.repositories.MessageRepository;
import ru.itis.websockets.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    public void save(MessageDto messageDto) {
        Message message = Message.builder()
                .text(messageDto.getText())
                .sender(userRepository.getOne(messageDto.getFromId()))
                .build();
        messageRepository.save(message);
    }

    public List<MessageDto> getAllMessages() {
        List<MessageDto> messageDtos = messageRepository.findAll().stream().map(message -> MessageDto.builder()
                .fromId(message.getSender().getId())
                .from(message.getSender().getName())
                .text(message.getText())
                .build()).collect(Collectors.toList());
        return messageDtos;
    }
}
