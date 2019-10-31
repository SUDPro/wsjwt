package ru.itis.websockets.controllers;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.websockets.forms.MessageDto;
import ru.itis.websockets.services.MessageService;

import java.util.List;

@Controller
public class MessageController {

    @Autowired
    MessageService messageService;

    @SneakyThrows
    @CrossOrigin
    @PreAuthorize("permitAll()")
    @GetMapping("/get-all-messages")
    public ResponseEntity<List<MessageDto>> getAllMessages(){
        return ResponseEntity.ok(messageService.getAllMessages());
    }
}
