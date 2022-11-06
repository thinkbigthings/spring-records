package com.thinkbigthings.springrecords.user;

import com.thinkbigthings.springrecords.config.ConfigurationRecord;
import com.thinkbigthings.springrecords.dto.UserRecord;
import com.thinkbigthings.springrecords.dto.UserSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

    private UserService userService;
    private ConfigurationRecord config;

    @Autowired
    public UserController(UserService service, ConfigurationRecord env) {
        userService = service;
        config = env;
    }


    @RequestMapping(value="/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserRecord createUser(@Valid @RequestBody UserRecord newUser) {

        // control nullability at the boundaries and prevent NPE

        return userService.createUser(newUser);
    }


    @RequestMapping(value="/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Page<UserSummary> getUsers(Pageable page) {

        var boundedPage = withMaxSize(page);
        var summaries = userService.getUserSummaries(boundedPage);

        return summaries;
    }


    private Pageable withMaxSize(Pageable page) {
        return page.getPageSize() > config.page().maxSize()
                ? PageRequest.of(page.getPageSize(), config.page().maxSize(), page.getSort())
                : page;
    }
}
