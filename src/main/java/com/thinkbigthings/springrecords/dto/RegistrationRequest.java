package com.thinkbigthings.springrecords.dto;

import javax.validation.constraints.Size;

public record RegistrationRequest(@Size(min=5) String username,
                                  @Size(min=5) String plainTextPassword,
                                  String email)
{
    public RegistrationRequest withUsername(String newName) {
        return new RegistrationRequest(newName, plainTextPassword, email);
    }

    public RegistrationRequest withPassword(String newPassword) {
        return new RegistrationRequest(username, newPassword, email);
    }

    public RegistrationRequest withEmail(String newEmail) {
        return new RegistrationRequest(username, plainTextPassword, newEmail);
    }
}

