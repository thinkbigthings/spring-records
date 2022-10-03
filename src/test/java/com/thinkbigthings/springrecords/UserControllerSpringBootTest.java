package com.thinkbigthings.springrecords;

import com.thinkbigthings.springrecords.dto.RegistrationRequest;
import com.thinkbigthings.springrecords.dto.User;
import com.thinkbigthings.springrecords.user.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.RequestEntity;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.net.URI;

import static com.thinkbigthings.springrecords.data.TestData.createRandomUserRegistration;
import static com.thinkbigthings.springrecords.data.TestData.randomPersonalInfo;
import static org.junit.jupiter.api.Assertions.*;


class UserControllerSpringBootTest extends IntegrationTest {

    private static String testUserName;
    private static URI testUserUrl;

    private static String baseUrl;
    private static URI users;

    private static String registrationUrl;

    @Autowired
    private UserService userService;

    @BeforeAll
    public static void createReadOnlyTestData(@LocalServerPort int randomServerPort) {

        baseUrl = "http://localhost:" + randomServerPort + "/";
        users = URI.create(baseUrl + "user");

        registrationUrl = baseUrl + "registration";

        RegistrationRequest testUserRegistration = createRandomUserRegistration();


        testUserName = testUserRegistration.username();
        testUserUrl = URI.create(users + "/" + testUserName);
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void controllerArgsPass() {

        RegistrationRequest registration = new RegistrationRequest("12345", "12345", "12345");

        var response = restTemplate.postForEntity(registrationUrl, registration, String.class);

        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void controllerArgsHaveValidAnnotation() {

        RegistrationRequest invalidRegistration = new RegistrationRequest("", "", "");

        var response = restTemplate.postForEntity(registrationUrl, invalidRegistration, String.class);

        assertTrue(response.getStatusCode().is4xxClientError());
    }


    @Test
    public void updateUserInfo() {

        RegistrationRequest registration = createRandomUserRegistration();
        var response = restTemplate.postForEntity(registrationUrl, registration, String.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());

        var userUrl = URI.create(users + "/" + registration.username());
        var userInfoUrl = URI.create(userUrl + "/personalInfo");
        var updatedInfo = randomPersonalInfo();
        var updateReq = RequestEntity.put(userInfoUrl).body(updatedInfo);
        var savedInfo = restTemplate.exchange(updateReq, User.class).getBody().personalInfo();
        assertEquals(updatedInfo, savedInfo);

        var savedUser = restTemplate.getForEntity(userUrl, User.class);
        assertEquals(savedUser.getBody().username(), registration.username());
    }

    @Test
    public void noSuchUser() {
        assertThrows(EntityNotFoundException.class, () -> userService.getUser("noSuchUser"));
    }

    @Test
    public void testListUsers() {

//        long numberUsers = userRepository.count();
//        Page<User> userPage = userRepository.findAll(PageRequest.of(0, 10));
//
//        assertEquals(10, userPage.getNumberOfElements());
//        assertTrue(userPage.getTotalElements() > 10);
//        assertTrue(numberUsers > 10);
    }

}