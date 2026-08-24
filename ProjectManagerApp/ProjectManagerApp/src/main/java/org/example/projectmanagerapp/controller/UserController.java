package org.example.projectmanagerapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.projectmanagerapp.entity.User;
import org.example.projectmanagerapp.service.UserService;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Operations related to users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @Operation(summary = "Get user by ID")
    @GetMapping("/{id}")
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @Operation(summary = "Create user")
    @PostMapping
    public User createUser(
            @RequestBody User user){
        return userService.createUser(user);
    }
    @Operation(summary = "Get all users")
    @GetMapping
    public User getUserById(
            @Parameter(description = "ID of the user")
            @PathVariable Long id){
        return userService.getUserById(id);
    }

    @Operation(summary = "Delete user")
    @DeleteMapping("/{id}")
    public void deleteUserById (
            @Parameter(description = "ID of the user")
            @PathVariable Long id){
        userService.deleteUserById(id);
    }

    @Operation(summary = "Update user")
    @PutMapping("/{id}")
    public User updateUser(
            @Parameter(description = "ID of the user")
            @PathVariable Long id,
            @RequestBody User updateUser){
        return  userService.updateUser(id, updateUser);
    }

}