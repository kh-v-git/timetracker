package com.tracker.user;


import java.util.List;

public interface UserRepository {
    List<User> findUsersList(String searchName);

    boolean setNewUser(User user);

    User GetUserByID(int id);

    boolean deleteUserByID(int id);

    Boolean updateUserById(int id, User user);
}
