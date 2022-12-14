package com.thinkbigthings.springrecords.user;

import com.thinkbigthings.springrecords.dto.UserAddress;
import com.thinkbigthings.springrecords.dto.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Component
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private record IntermediateUserRecord(Long id, String username, String email) {}


    // records work great with RowMapper
    // also with ResultSetExtractor, RowCallbackHandler...

    private RowMapper<UserAddress> addressRowMapper = new RowMapper<>() {
        @Override public UserAddress mapRow(ResultSet rs, int rowNum) throws SQLException {
            String street = rs.getString(3);
            String city = rs.getString(4);
            String state = rs.getString(5);
            String zip = rs.getString(6);
            return new UserAddress(street, city, state,zip);
        }
    };

    private RowMapper<IntermediateUserRecord> userRowMapper = new RowMapper<>() {
        @Override public IntermediateUserRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long id = rs.getLong(1);
            String name = rs.getString(2);
            String email = rs.getString(3);
            return new IntermediateUserRecord(id, name, email);
        }
    };

    public Optional<UserRecord> getUserDto(String username) {

        try {
            String userSql = "SELECT * FROM app_user u WHERE u.username=?";
            IntermediateUserRecord user = jdbcTemplate.queryForObject(userSql, userRowMapper, username);

            String addressSql = "SELECT * from address a WHERE a.user_id=?";
            List<UserAddress> addresses = jdbcTemplate.query(addressSql, addressRowMapper, user.id());

            return Optional.of(new UserRecord(user.username(), user.email(), new HashSet<>(addresses)));
        }
        catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

}
