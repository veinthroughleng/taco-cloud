package veinthrough.taco.data;

import org.springframework.data.repository.CrudRepository;
import veinthrough.taco.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
