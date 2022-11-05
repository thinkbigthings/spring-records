package com.thinkbigthings.springrecords;

import com.thinkbigthings.springrecords.config.IntegrationTest;
import com.thinkbigthings.springrecords.user.UserController;
import com.thinkbigthings.springrecords.user.UserDao;
import com.thinkbigthings.springrecords.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import static com.thinkbigthings.springrecords.data.TestData.randomUser;
import static org.junit.jupiter.api.Assertions.*;


class UserControllerSpringBootTest extends IntegrationTest {

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDao userDao;

    @Test
    public void testControllerArgsAndReturns() {

        // enter here!
        var newUser = randomUser();
        var createdUser = userController.createUser(newUser);

        assertEquals(newUser, createdUser);
    }

    @Test
    public void testUserSummary() {

        // create test users
        userController.createUser(randomUser());

        // come on in!
        // data comes out of the database already mapped - no extra mapping step
        var users = userController.getUsers(PageRequest.of(0, 10));

        assertTrue(users.getTotalElements() >= 1);
    }

    @Test
    public void testRepoVsDao() {

        var createdUser = userController.createUser(randomUser());

        var daoUser = userDao.getUserDto(createdUser.username());
        var repoUser = userRepository.findRecord(createdUser.username());

        assertEquals(daoUser, repoUser);
    }

}