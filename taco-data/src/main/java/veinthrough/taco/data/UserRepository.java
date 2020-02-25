package veinthrough.taco.data;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import veinthrough.taco.model.User;

@Profile({"rest", "security"})
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
