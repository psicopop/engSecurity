package com.senai.engSecurity.controller;

import com.senai.engSecurity.model.User;
import com.senai.engSecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET, RequestMethod.POST}) // Aplica CORS para todos os métodos desse controlador
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User save(@RequestBody User user) {
        return userService.save(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        return userService.findByUsernameAndPassword(user);
    }

    @PostMapping("/testAuth")
public ResponseEntity<?> testAuthentication(@RequestBody User user) {
    User foundUser = userService.findByUsernameAndPassword(user);
    if (foundUser != null) {
        return ResponseEntity.ok("Usuário autenticado com sucesso!");
    } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado ou credenciais inválidas.");
    }
}
}
