package veinthrough.tacocloud.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import veinthrough.tacocloud.model.Order;
import veinthrough.tacocloud.model.User;

import java.util.List;

public interface OrderRepository
        extends CrudRepository<Order, Long> {
    List<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}
