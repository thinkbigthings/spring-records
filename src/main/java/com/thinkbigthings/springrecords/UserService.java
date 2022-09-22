package com.thinkbigthings.springrecords;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;


@Service
public class UserService {

    private static Logger LOG = LoggerFactory.getLogger(UserService.class);

/*
    private UserMapper toUserRecord = new UserMapper();

    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.userRepo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void updatePassword(String username, String newPassword) {

        var user = userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("no user found for " + username));

        user.setPassword(passwordEncoder.encode(newPassword));

        userRepo.save(user);
    }

    @Transactional
    public org.thinkbigthings.zdd.dto.User updateUser(String username, PersonalInfo userData) {

        var user = userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("no user found for " + username));

        user.setEmail(userData.email());
        user.setDisplayName(userData.displayName());
        user.setPhoneNumber(userData.phoneNumber());
        user.setHeightCm(userData.heightCm());

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
    public void saveNewUser(RegistrationRequest registration) {

        String username = registration.username();

        try {
            if( ! URLEncoder.encode(username, UTF_8).equals(username)) {
                throw new IllegalArgumentException("Username must be url-safe");
            }

            if(userRepo.existsByUsername(username)) {
                throw new IllegalArgumentException("Username already exists " + registration.username());
            }

            userRepo.persist(fromRegistration(registration));
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
    public org.thinkbigthings.zdd.dto.User getUser(String username) {

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
        user.setPassword(passwordEncoder.encode(registration.plainTextPassword()));
        user.getRoles().add(Role.USER);

        user.setSearchConfig(new SearchConfig());
        user.getSearchConfig().setUser(user);

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
*/
}
