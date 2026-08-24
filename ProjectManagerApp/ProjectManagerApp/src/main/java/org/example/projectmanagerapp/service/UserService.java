package org.example.projectmanagerapp.service;
import org.example.projectmanagerapp.entity.User;
import org.example.projectmanagerapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow();
    }

    public void deleteUserById (Long id){
        userRepository.deleteById(id);
    }

    public User updateUser(Long id, User updateUser){
        User existingUser = getUserById(id);

        existingUser.setUsername(updateUser.getUsername());

        return  userRepository.save(existingUser);
    }
}
