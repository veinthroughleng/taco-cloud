package veinthrough.tacocloud.data;

import org.springframework.data.repository.CrudRepository;
import veinthrough.tacocloud.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
