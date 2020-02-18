package veinthrough.taco.data;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Flux;
import veinthrough.taco.model.Order;
import veinthrough.taco.model.UserUDT;

import java.util.UUID;

public interface OrderRepository
        extends ReactiveCassandraRepository<Order, UUID> {
    Flux<Order> findByUserOrderByPlacedAt(UserUDT user);
}
