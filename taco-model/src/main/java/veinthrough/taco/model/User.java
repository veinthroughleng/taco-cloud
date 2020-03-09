package veinthrough.taco.model;

import com.google.common.collect.Lists;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {
    private static final long serialVersionUID = 1L;
    public static final List<String> DEFAULT_ROLES = Lists.newArrayList("ROLE_USER");

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // non-final, used in convert href to prototype

    // attributes
    private String username;
    @NonNull
    private String password;
    private String fullname;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phoneNumber;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;
    @Column
    @ElementCollection(targetClass=String.class)
    private List<String> roles = DEFAULT_ROLES;

    public User(String username, String password, String fullname,
                String street, String city, String state,
                String zip, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phoneNumber;
    }

    public void encryptPassword(PasswordEncoder passwordEncoder) {
        password = passwordEncoder.encode(password);
    }

    @Override
    public List<JacksonGrantedAuthority> getAuthorities() {
        return this.roles.stream()
            .map(JacksonGrantedAuthority::new)
            .collect(Collectors.toList());
    }
}
