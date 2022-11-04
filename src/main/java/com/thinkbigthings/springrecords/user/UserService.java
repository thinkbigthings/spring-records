package com.thinkbigthings.springrecords.user;

import com.thinkbigthings.springrecords.dto.UserAddress;
import com.thinkbigthings.springrecords.dto.UserInfo;
import com.thinkbigthings.springrecords.dto.CreateUser;
import com.thinkbigthings.springrecords.dto.UserSummary;
import com.thinkbigthings.springrecords.entity.Address;
import com.thinkbigthings.springrecords.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.time.Instant;
import java.util.List;

@Service
public class UserService {

    private UserMapper toUserRecord = new UserMapper();

    private UserRepository userRepo;
    private UserDao userDao;

    public UserService(UserRepository repo, UserDao dao) {
        this.userRepo = repo;
        this.userDao = dao;
    }

    @Transactional
    public com.thinkbigthings.springrecords.dto.User updateUser(String username, UserInfo userData) {

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
    public com.thinkbigthings.springrecords.dto.User saveNewUser(CreateUser registration) {

        try {
            return toUserRecord.apply(userRepo.save(fromRegistration(registration)));
        }
        catch(ConstraintViolationException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User can't be saved: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Page<UserSummary> getUserSummaries(Pageable page) {

        return userRepo.loadSummaries(page);
    }

    public User fromRegistration(CreateUser registration) {

        var user = new User(registration.username(), registration.username());

        user.setDisplayName(registration.username());
        user.setEmail(registration.email());
        user.setRegistrationTime(Instant.now());

        return user;
    }

    public Address fromRecord(UserAddress addressData) {

        var address = new Address();

        address.setLine1(addressData.line1());
        address.setCity(addressData.city());
        address.setState(addressData.state());
        address.setZip(addressData.zip());

        return address;
    }

}
