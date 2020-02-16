package veinthrough.tacocloud.model;

import com.datastax.driver.core.utils.UUIDs;
import com.google.common.collect.Lists;
import lombok.*;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Collection;
import java.util.UUID;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@RequiredArgsConstructor
@Table("users")
public class User implements UDTed<UserUDT>, UserDetails {
    private static final long serialVersionUID = 1L;

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private UUID id = UUIDs.timeBased();

    private final String username;
    @NonNull private String password;
    private final String fullname;
    private final String street;
    private final String city;
    private final String state;
    private final String zip;
    private final String phoneNumber;
    private final String email;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;

    @Override
    public UserUDT toUDT() {
        return new UserUDT(username, fullname, phoneNumber);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Lists.newArrayList(
                new SimpleGrantedAuthority("ROLE_USER"));
    }

    public void encryptPassword(PasswordEncoder passwordEncoder) {
        password = passwordEncoder.encode(password);
    }
}
