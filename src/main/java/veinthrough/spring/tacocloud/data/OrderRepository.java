package veinthrough.spring.tacocloud.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import veinthrough.spring.tacocloud.data.model.Order;
import veinthrough.spring.tacocloud.data.model.User;
import java.util.List;

public interface OrderRepository
        extends CrudRepository<Order, Long> {
    List<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}
