package veinthrough.spring.tacocloud.data;

import org.springframework.data.repository.CrudRepository;
import veinthrough.spring.tacocloud.data.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
