package com.thinkbigthings.springrecords;

import com.thinkbigthings.springrecords.config.IntegrationTest;
import com.thinkbigthings.springrecords.dto.UserRecord;
import com.thinkbigthings.springrecords.user.UserController;
import com.thinkbigthings.springrecords.user.UserDao;
import com.thinkbigthings.springrecords.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import static com.thinkbigthings.springrecords.data.TestData.randomUser;
import static org.junit.jupiter.api.Assertions.*;


class UserControllerSpringBootTest extends IntegrationTest {

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDao userDao;


    private UserRecord newUser;


    @BeforeEach
    public void setup() {
        newUser = randomUser();
        userController.createUser(newUser);
    }


    @Test
    public void testUserSummary() {

        // data comes out of the database already mapped - no extra mapping step
        var users = userController.getUsers(PageRequest.of(0, 10));

        assertTrue(users.getTotalElements() >= 1);
    }


    @Test
    public void testRepoVsDao() {

        var daoUser = userDao.getUserDto(newUser.username());
        var repoUser = userRepository.findRecord(newUser.username());

        assertEquals(daoUser, repoUser);
    }

}