package ru.itis.websockets.controllers;



import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.websockets.forms.UserForm;
import ru.itis.websockets.models.User;
import ru.itis.websockets.services.UserService;

@RestController
public class RegisterController {

    @Autowired
    UserService userService;

    private String secretKey = "tepaIepaspringjpapropertieshibernatejdbclobnoncontextualcreationjavasosibibucontextualcreationjavasosibibu";

    @PostMapping("/register")
    @CrossOrigin
    @PreAuthorize("permitAll()")
    public ResponseEntity<String> register(@RequestBody UserForm userForm) {
        User user = userService.register(userForm);
        String token = Jwts.builder()
                .claim("name", user.getName())
                .claim("access", user.getAccess())
                .claim("id", user.getId())
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
        return ResponseEntity.ok(token);
    }
}
