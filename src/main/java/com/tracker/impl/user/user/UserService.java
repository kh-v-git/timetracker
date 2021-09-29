package com.tracker.impl.user.user;

import java.util.List;
import java.util.Optional;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> searchUsers(String searchText) {
        return userRepository.findUsersList(searchText);
    }

    public boolean createNewUser(User user) {
        return userRepository.setNewUser(user);
    }

    public User getUserByID(int id) {
        return userRepository.getUserByID(id);
    }

    public boolean deleteUserByID(int id) {
        return userRepository.deleteUserByID(id);
    }

    public boolean updateUserByID(User user) {
        return userRepository.updateUser(user);
    }

    public Optional<User> authUser(String email) {
        return userRepository.authUser(email);
    }
}
