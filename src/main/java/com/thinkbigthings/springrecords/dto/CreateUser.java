package com.thinkbigthings.springrecords.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record CreateUser(@NotNull @Size(min=5) String username, @NotNull @Size(min=5) String plainTextPassword, @Email String email) {}

