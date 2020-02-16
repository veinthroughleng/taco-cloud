package veinthrough.tacocloud.data;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Flux;
import veinthrough.tacocloud.model.Order;
import veinthrough.tacocloud.model.UserUDT;

import java.util.UUID;

public interface OrderRepository
        extends ReactiveCassandraRepository<Order, UUID> {
    Flux<Order> findByUserOrderByPlacedAt(UserUDT user);
}
