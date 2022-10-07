package com.thinkbigthings.springrecords.dto;

import javax.validation.constraints.Size;

public record RegistrationRequest(@Size(min=5) String username, @Size(min=5) String plainTextPassword, String email) {}

