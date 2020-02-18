package veinthrough.taco.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import veinthrough.taco.model.Order;
import veinthrough.taco.model.User;

import java.util.List;

public interface OrderRepository
        extends CrudRepository<Order, Long> {
    List<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}
