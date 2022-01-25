package com.eindproject.YogaShare.users;

import com.eindproject.YogaShare.YogaShareApplication;
import com.eindproject.YogaShare.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ContextConfiguration(classes = {YogaShareApplication.class})
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Mock
    User user;

    @Test
    public void getAllUsersTest() {
        List<User> testUserList = new ArrayList<>();
        User user1 = new User();
        User user2 = new User();
        user1.setId(1L);
        user2.setId(2L);
        user1.setUsername("test1");
        user2.setUsername("test2");
        testUserList.add(user1);
        testUserList.add(user2);

        Mockito
                .when(userRepository.findAll())
                .thenReturn(testUserList);
        userService.getAllUsers();
        verify(userRepository, times(1)).findAll();

        assertThat(testUserList.size()).isEqualTo(2);
        assertThat(testUserList.get(0)).isEqualTo(user1);
        assertThat(testUserList.get(1)).isEqualTo(user2);
    }

    @Test
    public void getUserByUsernameTest() {
        User user = new User();
        user.setUsername("fenn");

        Mockito
                .when(userRepository.findByUsername("fenn"))
                .thenReturn(Optional.of(user));

        String username = "fenn";
        String expected = "fenn";

        User found = userService.getUser(username);

        verify(userRepository).findByUsername(expected);
        assertEquals(expected, found.getUsername());
    }

    @Test
    public void getUserExceptionTest() {
        assertThrows(UserNotFoundException.class, () -> userService.getUser(null));
    }

    @Test
    void createUser() {
    }

    @Test
    void updateUsername() {
    }

    @Test
    public void deleteUserTest() {
        user = new User();
        user.setId(1L);

        Mockito
                .when(userRepository.existsById(1L))
                .thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(user.getId());
    }


}