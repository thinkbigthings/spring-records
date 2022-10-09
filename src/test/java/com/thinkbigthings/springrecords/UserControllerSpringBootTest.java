package com.thinkbigthings.springrecords;

import com.thinkbigthings.springrecords.config.IntegrationTest;
import com.thinkbigthings.springrecords.user.UserController;
import com.thinkbigthings.springrecords.user.UserDao;
import com.thinkbigthings.springrecords.user.UserRepository;
import com.thinkbigthings.springrecords.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static com.thinkbigthings.springrecords.data.TestData.createRandomUserRegistration;
import static com.thinkbigthings.springrecords.data.TestData.randomPersonalInfo;
import static org.junit.jupiter.api.Assertions.*;


class UserControllerSpringBootTest extends IntegrationTest {

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @Test
    public void testUserSummary() {

        for(int i=0; i < 10; i++) {
            userController.createUser(createRandomUserRegistration());
        }

        Pageable firstPage = PageRequest.of(0, 10);
        var users = userController.getUsers(firstPage);

        assertTrue(users.getTotalElements() >= 10);
    }

    @Test
    public void testUserRecord() {

        var newUser = userController.createUser(createRandomUserRegistration());

        var userResponse = userController.updateUser(randomPersonalInfo(), newUser.username());

        var updatedUser = userController.getUser(newUser.username());

        assertEquals(userResponse, updatedUser);
    }

    @Test
    public void testRepoVsDao() {

        var user = userController.createUser(createRandomUserRegistration());
        var updateUser = userController.updateUser(randomPersonalInfo(), user.username());

        var daoUser = userDao.getUserDto(user.username());
        var repoUser = userRepository.findRecord(user.username());

        assertEquals(daoUser, repoUser);
    }

}