package com.thinkbigthings.springrecords;

import com.thinkbigthings.springrecords.dto.RegistrationRequest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
public class UserController {

//    private final UserService userService;


    // if there's only one constructor, can omit Autowired and Inject
    public UserController(ConfigurationRecord env) {
        System.out.println(env.number());
    }

    @RequestMapping(value="/registration", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void createUserRegistration(@Valid @RequestBody RegistrationRequest newUser) {

//        userService.saveNewUser(newUser);
    }

/*

    @RequestMapping(value="/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Page<UserSummary> getUsers(@PageableDefault(page = 0, size = 10, sort = {"registrationTime"}, direction=Sort.Direction.DESC) Pageable page) {

        return userService.getUserSummaries(page);
    }

    @RequestMapping(value="/user/{username}/personalInfo", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public User updateUser(@RequestBody PersonalInfo userData, @PathVariable String username) {

        return userService.updateUser(username, userData);
    }

    @RequestMapping(value="/user/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public User getUser(@PathVariable String username) {

        return userService.getUser(username);
    }
    */
}
