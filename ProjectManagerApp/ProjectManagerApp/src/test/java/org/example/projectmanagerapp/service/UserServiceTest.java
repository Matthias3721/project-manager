package org.example.projectmanagerapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import org.example.projectmanagerapp.repository.UserRepository;
import org.example.projectmanagerapp.entity.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


import static org.mockito.Mockito.verify;

public class UserServiceTest {

    UserRepository userRepository = Mockito.mock(UserRepository.class);

    UserService userService = new UserService(userRepository);


    @Test
    public void shouldReturnAllUsers() {
        User user1 = new User();
        user1.setUsername("Mateusz");

        User user2 = new User();
        user2.setUsername("Jan");

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<User> users = userService.getUsers();
        assertEquals(2, users.size());
        verify(userRepository).findAll();
    }

    @Test
    public void getUserById(){
        User user = new User();
        user.setUsername("Mateusz");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);
        assertEquals("Mateusz", result.getUsername());
        verify(userRepository).findById(1L);

    }

    @Test
    public void createUser(){
        User user = new User();
        user.setUsername("Mateusz");


        when(userRepository.save(user)).thenReturn(user);

        User result = userService.createUser(user);
        assertEquals("Mateusz", result.getUsername());
        verify(userRepository).save(user);

    }

    @Test
    public void deleteUserById(){

        userService.deleteUserById(1L);
        verify(userRepository).deleteById(1L);

    }

    @Test
    public void updateUser(){
        User existingUser = new User();
        existingUser.setUsername("Jan");

        User updatedUser = new User();
        updatedUser.setUsername("Mateusz");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        User result = userService.updateUser(1L,updatedUser);
        assertEquals("Mateusz", result.getUsername());

        verify(userRepository).findById(1L);
        verify(userRepository).save(existingUser);
    }

    @Test
    public void shouldThrowWhenUserNotFound(){
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,() -> userService.getUserById(99L));
    }
}