package com.thinkbigthings.springrecords.user;

import com.thinkbigthings.springrecords.dto.AddressRecord;
import com.thinkbigthings.springrecords.dto.PersonalInfo;
import com.thinkbigthings.springrecords.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Component
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private record IntermediateUserRecord(Long id, String username, Instant registrationTime, String email, String display) {}

    private RowMapper<AddressRecord> addressRowMapper = new RowMapper<>() {
        @Override public AddressRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
            String street = rs.getString(3);
            String city = rs.getString(4);
            String state = rs.getString(5);
            String zip = rs.getString(6);
            return new AddressRecord(street, city, state,zip);
        }
    };

    private RowMapper<IntermediateUserRecord> userRowMapper = new RowMapper<>() {
        @Override public IntermediateUserRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long id = rs.getLong(1);
            String name = rs.getString(2);
            String email = rs.getString(3);
            String display = rs.getString(4);
            Timestamp reg = rs.getTimestamp(6);
            return new IntermediateUserRecord(id, name, reg.toInstant(), email, display);
        }
    };

    public Optional<User> getUserDto(String username) {

        try {
            String userSql = "SELECT * FROM app_user u WHERE u.username=?";
            IntermediateUserRecord user = jdbcTemplate.queryForObject(userSql, userRowMapper, username);

            String addressSql = "SELECT * from address a WHERE a.user_id=?";
            List<AddressRecord> addresses = jdbcTemplate.query(addressSql, addressRowMapper, user.id());

            return Optional.of(new User(user.username(), user.registrationTime().toString(),
                    new PersonalInfo(user.email(), user.display(), new HashSet<>(addresses))));
        }
        catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

}
