package com.tracker.impl.user.user;


import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findUsersList(String searchName);

    boolean setNewUser(User user);

    User getUserByID(int id);

    boolean deleteUserByID(int id);

    Boolean updateUser(User user);

    Optional<User> authUser(String email);
}
