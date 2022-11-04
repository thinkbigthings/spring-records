package com.thinkbigthings.springrecords.user;

import com.thinkbigthings.springrecords.config.ConfigurationRecord;
import com.thinkbigthings.springrecords.dto.UserInfo;
import com.thinkbigthings.springrecords.dto.CreateUser;
import com.thinkbigthings.springrecords.dto.User;
import com.thinkbigthings.springrecords.dto.UserSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    }

    @RequestMapping(value="/registration", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@Valid @RequestBody CreateUser newUser) {

        // control nullability at the boundaries and prevent NPE

        return  userService.saveNewUser(newUser);
    }


    @RequestMapping(value="/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Page<UserSummary> getUsers(@PageableDefault(page = 0, size = 10, sort = {"registrationTime"}, direction= Sort.Direction.DESC) Pageable page) {

        var boundedPage = withMaxSize(page);
        var summaries = userService.getUserSummaries(boundedPage);

        return summaries;
    }


    @RequestMapping(value="/user/{username}/personalInfo", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public User updateUser(@RequestBody UserInfo userData, @PathVariable String username) {

        return userService.updateUser(username, userData);
    }


    private Pageable withMaxSize(Pageable page) {
        return page.getPageSize() > config.page().maxSize()
                ? PageRequest.of(page.getPageSize(), config.page().maxSize(), page.getSort())
                : page;
    }
}
