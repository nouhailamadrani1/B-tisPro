package com.rentalhive.rentalhive.service;

import com.rentalhive.rentalhive.model.User;

import com.rentalhive.rentalhive.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getAllUsers() {

        List<User> mockUsers = Arrays.asList(
                new User(1, "User1", false),
                new User(2, "User2", true)
        );
        Mockito.when(userRepository.findAll()).thenReturn(mockUsers);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        assertEquals("User1", result.get(0).getName());
        assertEquals("User2", result.get(1).getName());
    }

    @Test
    void getUserById() {

        User mockUser = new User(1, "User1", false);
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));


        Optional<User> result = userService.getUserById(1);


        assertTrue(result.isPresent());
        assertEquals("User1", result.get().getName());
    }

    @Test
    void addUser() {

        User newUser = new User(null, "NewUser", true);
        Mockito.when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(1);
            return savedUser;
        });

        User result = userService.addUser(newUser);

        assertNotNull(result.getId());
        assertEquals("NewUser", result.getName());
        assertTrue(result.isAdmin());
    }
    @Test
    void addUserByNameNull() {

        User newUser = new User(null, "", true);
        Mockito.when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(1);
            return savedUser;
        });

        User result = userService.addUser(newUser);

        assertNotNull(result.getId());
        assertEquals("", result.getName());
        assertTrue(result.isAdmin());
    }


    @Test
    void updateUser() {

        User existingUser = new User(1, "ExistingUser", false);
        User updatedUser = new User(1, "UpdatedUser", true);
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));
        Mockito.when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(updatedUser);

        assertEquals("UpdatedUser", result.getName());
        assertTrue(result.isAdmin());
    }
    @Test
    void deleteUser() {

        User existingUser = new User(1, "UserToDelete", false);
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));

        userService.deleteUser(1);

        Mockito.verify(userRepository, Mockito.times(1)).deleteById(eq(1));
    }
}
