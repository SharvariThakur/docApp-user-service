package com.doc.user_service.controller;

import com.doc.user_service.dto.UserDto;
import com.doc.user_service.entity.User;
import com.doc.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/info")
    public String getDetail()
    {
        return "Hello";
    }
@PostMapping("/register")
    public User register(@RequestBody UserDto userDto){
        return userService.register(userDto);
    }
@PostMapping("/login")
public Map<String,Object> login(@RequestBody Map<String,String> credentials)
{
    String username= credentials.get("userName");
    String password= credentials.get("password");


    return userService.login(username,password)
            .map(user -> Map.of("status","success","user",user))
            .orElse(Map.of("status","fail","message","Invalid Credentials"));
}
@GetMapping
public List<User> getAllUsers()
{
    return userService.getAllUsers();
}
@GetMapping("/{id}")
public User getUserById(@RequestBody Long id)
{
    return userService.getUserById(id).orElseThrow(()-> new RuntimeException("User not found"));
}
}
