package com.doc.user_service.service;

import com.doc.user_service.dto.UserDto;
import com.doc.user_service.entity.User;
import com.doc.user_service.repositorty.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepo;

    public User register(UserDto userDto)
    {
        User user=new User();
        user.setFullName(userDto.getFullName());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setRole(userDto.getRole());
        return userRepo.save(user);
    }

    public Optional<User> login(String username, String password) {
        Optional<User> user = userRepo.findByUsername(username).filter(e-> e.getPassword().equals(password));
        System.out.println("Found user: " + user);
        return user;
    }

    public List<User> getAllUsers()
    {
        return userRepo.findAll();
    }

    public Optional<User> getUserById(Long id){
        return userRepo.findById(id);
    }


}
