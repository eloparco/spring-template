package com.example.demo.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
  @NotNull(message = "Username name cannot be null")
  @Size(min = 2, message = "Username must not be less than 2 characters")
  private String username;

  @NotNull
  private String password;
}
