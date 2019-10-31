package ru.itis.websockets.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.websockets.configs.Access;
import ru.itis.websockets.forms.UserForm;
import ru.itis.websockets.models.User;
import ru.itis.websockets.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User register(UserForm userForm) {
        User user = userRepository.save(User.builder().name(userForm.getName()).access(Access.ALLOWED).build());
        return user;
    }
}
