package com.thinkbigthings.springrecords.dto;

import javax.validation.constraints.Size;

//@NotNull
//@Size(min = 3, message = "must be at least three characters")
public record RegistrationRequest(@Size(min=5) String username,
                                  @Size(min=5) String plainTextPassword,
                                  String email) { }

