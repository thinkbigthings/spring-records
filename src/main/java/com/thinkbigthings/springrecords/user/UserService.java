package com.thinkbigthings.springrecords.user;


import com.thinkbigthings.springrecords.dto.UserRecord;
import com.thinkbigthings.springrecords.dto.UserSummary;
import com.thinkbigthings.springrecords.mapper.UserEntityToRecord;
import com.thinkbigthings.springrecords.mapper.UserRecordToEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

    private UserEntityToRecord toUserRecord = new UserEntityToRecord();
    private UserRecordToEntity toUserEntity = new UserRecordToEntity();

    private UserRepository userRepo;
    private UserDao userDao;

    public UserService(UserRepository repo, UserDao dao) {
        this.userRepo = repo;
        this.userDao = dao;
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
