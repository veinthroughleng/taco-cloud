package veinthrough.taco.service.rest.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInput {
    String username;
    String password;
}
