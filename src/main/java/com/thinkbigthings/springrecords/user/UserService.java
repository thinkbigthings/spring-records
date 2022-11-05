package com.thinkbigthings.springrecords.user;


import com.thinkbigthings.springrecords.dto.User;
import com.thinkbigthings.springrecords.dto.UserSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.ConstraintViolationException;


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
    public com.thinkbigthings.springrecords.dto.User createUser(User user) {

        try {
            var entity = toUserEntity.apply(user);
            var saved = userRepo.save(entity);
            return toUserRecord.apply(saved);
        }
        catch(ConstraintViolationException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User can't be saved: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Page<UserSummary> getUserSummaries(Pageable page) {

        return userRepo.loadSummaries(page);
    }

}
