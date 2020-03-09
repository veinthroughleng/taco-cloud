package veinthrough.taco.data;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import veinthrough.taco.model.User;

import java.util.Optional;

@Profile({"jpa-rest", "security"})
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
