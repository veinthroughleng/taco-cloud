package veinthrough.taco.data;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import veinthrough.taco.model.User;

import java.util.UUID;

public interface UserRepository extends ReactiveCrudRepository<User, UUID> {
    @AllowFiltering
    Mono<User> findByUsername(String username);
}