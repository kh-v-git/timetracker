package com.tracker.user;

import java.util.List;

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
        return userRepository.GetUserByID(id);
    }

    public boolean deleteUserByID(int id) {
        return userRepository.deleteUserByID(id);
    }

    public boolean updateUserByID(int id, User user) {
        return userRepository.updateUserById(id, user);
    }
}
