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
        super(resource.getContent().getId(),
                resource.getContent().getUsername(),
                resource.getContent().getPassword(),
                resource.getContent().getFullname(),
                resource.getContent().getStreet(),
                resource.getContent().getCity(),
                resource.getContent().getState(),
                resource.getContent().getZip(),
                resource.getContent().getPhoneNumber(),
                resource.getContent().isAccountNonExpired(),
                resource.getContent().isAccountNonLocked(),
                resource.getContent().isCredentialsNonExpired(),
                resource.getContent().isEnabled(),
                resource.getContent().getRoles());
        setHref(resource.getId().getHref());
        // id may be empty if didn't expose id
        if (getId() == null) {
            setId(Long.valueOf(getIdFromHref(href)));
        }
    }
}
