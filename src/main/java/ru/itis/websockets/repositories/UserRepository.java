package ru.itis.websockets.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.websockets.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
