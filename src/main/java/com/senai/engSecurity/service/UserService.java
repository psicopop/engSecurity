package com.senai.engSecurity.service;

import com.senai.engSecurity.model.User;
import com.senai.engSecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findByUsernameAndPassword(User user) {
        user.setUsername("admin");
        System.out.println("Buscando usuário: " + user.getUsername());
        User foundUser = userRepository.findByUsername(user.getUsername()).orElse(null);
        
        if (foundUser == null) {
            System.out.println("Usuário não encontrado no banco.");
            return null;
        }
        
        System.out.println("Usuário encontrado: " + foundUser.getUsername());
        System.out.println("Comparando senhas...");
        if (passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            System.out.println("Senhas coincidem.");
            return foundUser;
        }
        System.out.println("Senha incorreta.");
        return null;
    }
    

    
}
