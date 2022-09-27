package com.thinkbigthings.springrecords.dto;

import javax.validation.constraints.Size;

// This is one possible Builder
// Good news it's an easy one-liner per method, no separate Builder class, immutable by default, no .build() at the end
// Bad news is it's a lot of boilerplate so could be error prone, and creates new object per builder method call
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

