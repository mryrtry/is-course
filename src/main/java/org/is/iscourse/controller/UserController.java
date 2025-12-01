package org.is.iscourse.controller;

import org.is.iscourse.model.dto.UserDto;
import org.is.iscourse.model.entity.User;
import org.is.iscourse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        try {
            User user = userService.registerUser(userDto);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/hi")
    public ResponseEntity<?> Hi() {
        
        return new ResponseEntity<>("Hello World!", HttpStatus.OK);
        
    }
}
