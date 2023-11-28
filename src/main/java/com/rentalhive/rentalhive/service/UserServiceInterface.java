package com.rentalhive.rentalhive.service;

import com.rentalhive.rentalhive.model.User;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {

    List<User> getAllUsers();

    Optional<User> getUserById(int id);

    User addUser(User user);

    User updateUser(User user);

    void deleteUser(int id);
}
