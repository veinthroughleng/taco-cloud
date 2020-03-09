package veinthrough.taco.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;


@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class JacksonGrantedAuthority implements GrantedAuthority {
    private static final long serialVersionUID = 1001L;
    private final String role;

    JacksonGrantedAuthority(String role) {
        Assert.hasText(role, "A granted authority textual representation is required");
        this.role = role;
    }

    public String getAuthority() {
        return this.role;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else {
            return obj instanceof JacksonGrantedAuthority &&
                    this.role.equals(((JacksonGrantedAuthority) obj).role);
        }
    }

    public int hashCode() {
        return this.role.hashCode();
    }

    public String toString() {
        return this.role;
    }
}