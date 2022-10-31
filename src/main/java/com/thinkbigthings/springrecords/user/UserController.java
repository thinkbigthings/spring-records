package com.thinkbigthings.springrecords.user;

import com.thinkbigthings.springrecords.config.ConfigurationRecord;
import com.thinkbigthings.springrecords.dto.UserEditableInfo;
import com.thinkbigthings.springrecords.dto.RegistrationRequest;
import com.thinkbigthings.springrecords.dto.User;
import com.thinkbigthings.springrecords.dto.UserSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

    private static Logger LOG = LoggerFactory.getLogger(UserController.class);

    private UserService userService;
    private ConfigurationRecord config;

    @Autowired
    public UserController(UserService service, ConfigurationRecord env) {
        userService = service;
        config = env;

        LOG.info("Found configuration: " + config);
    }

    @RequestMapping(value="/registration", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@Valid @RequestBody RegistrationRequest newUser) {

        // control nullability at the boundaries and prevent NPE

        return  userService.saveNewUser(newUser);
    }

    @RequestMapping(value="/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Page<UserSummary> getUsers(@PageableDefault(page = 0, size = 10, sort = {"registrationTime"}, direction= Sort.Direction.DESC) Pageable page) {

        var summaries = userService.getUserSummaries(page, true);
        return summaries;
    }

    @RequestMapping(value="/user/{username}/personalInfo", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public User updateUser(@RequestBody UserEditableInfo userData, @PathVariable String username) {

        return userService.updateUser(username, userData);
    }

    @RequestMapping(value="/user/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public User getUser(@PathVariable String username) {

        User foundUser = userService.getUser(username);
        return foundUser;
    }

}
