package com.thinkbigthings.springrecords.user;


import com.thinkbigthings.springrecords.dto.UserAddress;
import com.thinkbigthings.springrecords.dto.UserRecord;
import com.thinkbigthings.springrecords.dto.UserSummary;
import com.thinkbigthings.springrecords.entity.UserEntity;
import com.thinkbigthings.springrecords.mapper.UserEntityToRecord;
import com.thinkbigthings.springrecords.mapper.UserRecordToEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.time.Instant;
import java.util.Collection;
import java.util.Set;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toList;


@Service
public class UserService {

    private UserEntityToRecord toUserRecord = new UserEntityToRecord();
    private UserRecordToEntity toUserEntity = new UserRecordToEntity();

    private UserRepository userRepo;

    public UserService(UserRepository repo) {
        this.userRepo = repo;
    }

    public boolean validateAddressApiCall(UserAddress address) throws SocketTimeoutException, ConnectException {

        // this would be a call to a third party service like USPS address validation API

        return true;
    }


    @Transactional
    public UserRecord createUser(UserRecord user) {

        var entity = toUserEntity.apply(user);
        var saved = userRepo.save(entity);
        return toUserRecord.apply(saved);
    }


    @Transactional(readOnly = true)
    public Page<UserSummary> getUserSummaries(Pageable page) {

        return userRepo.loadSummaries(page);
    }

}
