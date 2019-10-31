package ru.itis.websockets.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.websockets.models.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
