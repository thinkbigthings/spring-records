package com.thinkbigthings.springrecords;

import com.thinkbigthings.springrecords.dto.RegistrationRequest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

//    private final UserService userService;


    // if there's only one constructor, can omit Autowired and Inject
//    public UserController(ConfigurationRecord env) {
//        System.out.println(env.number());
//    }

    @RequestMapping(value="/registration", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String createUserRegistration(@Valid @RequestBody RegistrationRequest newUser) {

        return "got it!";
//        userService.saveNewUser(newUser);
    }

}
