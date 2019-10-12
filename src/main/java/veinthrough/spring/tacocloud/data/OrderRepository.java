package veinthrough.spring.tacocloud.data;

import org.springframework.data.repository.CrudRepository;
import veinthrough.spring.tacocloud.data.model.Order;

public interface OrderRepository
        extends CrudRepository<Order, Long> {
}
