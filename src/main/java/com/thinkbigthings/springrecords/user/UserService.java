package com.thinkbigthings.springrecords.user;

import com.thinkbigthings.springrecords.dto.AddressRecord;
import com.thinkbigthings.springrecords.dto.PersonalInfo;
import com.thinkbigthings.springrecords.dto.RegistrationRequest;
import com.thinkbigthings.springrecords.dto.UserSummary;
import com.thinkbigthings.springrecords.entity.Address;
import com.thinkbigthings.springrecords.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.net.URLEncoder;
import java.time.Instant;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.joining;

@Service
public class UserService {

    private static Logger LOG = LoggerFactory.getLogger(UserService.class);

    private UserMapper toUserRecord = new UserMapper();

    private UserRepository userRepo;
    private UserDao userDao;

    public UserService(UserRepository repo, UserDao dao) {
        this.userRepo = repo;
        this.userDao = dao;
    }

    @Transactional
    public com.thinkbigthings.springrecords.dto.User updateUser(String username, PersonalInfo userData) {

        var user = userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("no user found for " + username));

        user.setEmail(userData.email());
        user.setDisplayName(userData.displayName());

        List<Address> newAddressEntities = userData.addresses().stream()
                .map(this::fromRecord)
                .toList();

        user.getAddresses().clear();
        user.getAddresses().addAll(newAddressEntities);
        newAddressEntities.forEach(a -> a.setUser(user));

        try {
            return toUserRecord.apply(userRepo.save(user));
        }
        catch(ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User can't be saved: " + e.getMessage());
        }
    }

    @Transactional
    public com.thinkbigthings.springrecords.dto.User saveNewUser(RegistrationRequest registration) {

        String username = registration.username();

        try {
            if( ! URLEncoder.encode(username, UTF_8).equals(username)) {
                throw new IllegalArgumentException("Username must be url-safe");
            }

            if(userRepo.existsByUsername(username)) {
                throw new IllegalArgumentException("Username already exists " + registration.username());
            }

            return toUserRecord.apply(userRepo.save(fromRegistration(registration)));
        }
        catch(ConstraintViolationException e) {
            String constraintMessage = "User can't be saved: " + e.getMessage();
            String list = e.getConstraintViolations().stream().map(v -> v.toString()).collect(joining(", "));
            constraintMessage += " " + list;
            LOG.error(constraintMessage, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, constraintMessage);
        }
        catch(IllegalArgumentException e) {
            String constraintMessage = "User can't be saved: " + e.getMessage();
            LOG.error(constraintMessage, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, constraintMessage);
        }
    }

    @Transactional(readOnly = true)
    public Page<UserSummary> getUserSummaries(Pageable page) {

        return userRepo.loadSummaries(page);
    }

    @Transactional(readOnly = true)
    public com.thinkbigthings.springrecords.dto.User getUser(String username) {

        // toUserRecord mapper is good for returning when an entity state transition was necessary
        // but for read only can return dto directly

        // both of these work

//        return userDao.getUserDto(username)
//                .orElseThrow(() -> new EntityNotFoundException("no user found for " + username));

        return userRepo.findByUsername(username)
                .map(toUserRecord)
                .orElseThrow(() -> new EntityNotFoundException("no user found for " + username));
    }

    public User fromRegistration(RegistrationRequest registration) {

        var user = new User(registration.username(), registration.username());

        user.setDisplayName(registration.username());
        user.setEmail(registration.email());
        user.setRegistrationTime(Instant.now());
        user.setEnabled(true);

        return user;
    }

    public Address fromRecord(AddressRecord addressData) {

        var address = new Address();

        address.setLine1(addressData.line1());
        address.setCity(addressData.city());
        address.setState(addressData.state());
        address.setZip(addressData.zip());

        return address;
    }

}
