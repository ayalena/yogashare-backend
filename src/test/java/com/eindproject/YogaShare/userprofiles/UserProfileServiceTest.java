package com.eindproject.YogaShare.userprofiles;

import com.eindproject.YogaShare.YogaShareApplication;
import com.eindproject.YogaShare.exceptions.UserNotFoundException;
import com.eindproject.YogaShare.users.User;
import com.eindproject.YogaShare.users.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ContextConfiguration(classes = {YogaShareApplication.class})
class UserProfileServiceTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserProfileService userProfileService;

    @Mock
    UserProfile userProfile;

    @Mock
    User user;

    @Test
    void getAllProfiles() {
    }

    @Test
    public void saveUserProfile() {
        user = new User();
        user.setId(1L);

        userProfile = new UserProfile();
        userProfile.setId(1L);
        userProfile.setUser(user);

        Mockito
                .when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        Mockito
                .when(userProfileRepository.findById(1L))
                .thenReturn(Optional.of(userProfile));

        userProfileService.saveUserProfile(userProfile, user.getId());

        verify(userProfileRepository, times(1)).save(userProfile);
    }

    @Test
    public void saveUserProfileException() {
        assertThrows(UserNotFoundException.class, () -> userProfileService.saveUserProfile(null, null));
    }

    @Test
    public void updateUserProfile() {
        userProfile = new UserProfile();
        userProfile.setId(1L);
        userProfile.setFirstName("Testy");
        userProfile.setLastName("Testerson");
        userProfile.setAge(33);
        userProfile.setAddress("Test Road 33");
        userProfile.setPostalCode("3333AA");
        userProfile.setCountry("TestLand");

        Mockito
                .when(userProfileRepository.findById(1L))
                .thenReturn(Optional.of(userProfile));

        userProfile.setFirstName("Testo");
        userProfile.setLastName("Testania");
        userProfile.setAge(34);
        userProfile.setAddress("Test Avenue 34");
        userProfile.setPostalCode("3333BB");
        userProfile.setCountry("Testopia");

        userProfileService.updateUserProfile(1L, userProfile);

        verify(userProfileRepository).save(userProfile);
        assertThat(userProfile.getFirstName()).isEqualTo("Testo");
        assertThat(userProfile.getLastName()).isEqualTo("Testania");
        assertThat(userProfile.getAge()).isEqualTo(34);
        assertThat(userProfile.getAddress()).isEqualTo("Test Avenue 34");
        assertThat(userProfile.getPostalCode()).isEqualTo("3333BB");
        assertThat(userProfile.getCountry()).isEqualTo("Testopia");
    }
}