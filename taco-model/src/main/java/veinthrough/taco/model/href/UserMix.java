package veinthrough.taco.model.href;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Resource;
import veinthrough.taco.model.User;

import static veinthrough.taco.model.href.Href.getIdFromHref;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class UserMix extends User implements Href {
    private String href;

    public UserMix(Resource<User> resource) {
        this(resource.getContent());
        setHref(resource.getId().getHref());
        // id may be empty if didn't expose id
        if (getId() == null) {
            setId(Long.valueOf(getIdFromHref(href)));
        }
    }

    private UserMix(User user) {
        super(user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getFullname(),
                user.getStreet(),
                user.getCity(),
                user.getState(),
                user.getZip(),
                user.getPhoneNumber(),
                user.isAccountNonExpired(),
                user.isAccountNonLocked(),
                user.isCredentialsNonExpired(),
                user.isEnabled(),
                user.getRoles());
    }

    public static UserMix userInfo(UserMix origin) {
        UserMix user = new UserMix(origin);
        // delete password for security purpose
        user.setPassword("[PROTECTED]");
        user.setHref("[PROTECTED]");
        return user;
    }
}
