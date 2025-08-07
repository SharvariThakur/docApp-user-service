package com.doc.user_service.controller;

import com.doc.user_service.dto.LoginRequest;
import com.doc.user_service.dto.UserDto;
import com.doc.user_service.entity.User;
import com.doc.user_service.service.UserService;
import com.doc.user_service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    JwtUtil jwtUtil;

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
public Map<String,Object> login(@RequestBody LoginRequest credentials)
{
    Map<String,Object> responseMap=new HashMap<>();
    String username= credentials.getUsername();
    String password= credentials.getPassword();
    System.out.println("Login attempt: " + username + " / " + password);

    Optional<User> userOpt=userService.login(username,password);
    if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
        String token =jwtUtil.generateToken(username);
        responseMap.put("status","success");
        responseMap.put("token",token);

        return responseMap;
    } else {
        responseMap.put("status","fail");
        responseMap.put(HttpStatus.UNAUTHORIZED.toString(),"Invalid Credentials");
        return responseMap;
    }


}
@GetMapping
public List<User> getAllUsers()
{
    return userService.getAllUsers();
}
@GetMapping("/{id}")
public User getUserById(@PathVariable Long id)
{
    return userService.getUserById(id).orElseThrow(()-> new RuntimeException("User not found"));
}
}
