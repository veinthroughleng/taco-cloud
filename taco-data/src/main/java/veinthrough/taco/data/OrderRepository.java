package veinthrough.taco.data;

import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import veinthrough.taco.model.Order;

@Profile("jpa-rest")
public interface OrderRepository
        extends PagingAndSortingRepository<Order, Long> {
//    Page<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
    Page<Order> findByUserIdOrderByPlacedAtDesc(Long userId, Pageable pageable);
}
