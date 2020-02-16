package veinthrough.tacocloud.model;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Data
@UserDefinedType("user")
public class UserUDT implements UDT<User>{

    private final String username;
    private final String fullname;
    private final String phoneNumber;
}
