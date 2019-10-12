package veinthrough.spring.tacocloud.data.jdbc;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;
import veinthrough.spring.tacocloud.data.OrderRepository;
import veinthrough.spring.tacocloud.data.model.Order;
import veinthrough.spring.tacocloud.data.model.Taco;

import java.util.Date;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    //use JdbcInsertOperations instead of JdbcOperations;
    //private JdbcOperations jdbc;
    private SimpleJdbcInsertOperations orderInserter;
    private SimpleJdbcInsertOperations orderTacoInserter;

    @Autowired
    public JdbcOrderRepository(JdbcTemplate jdbc) {
        this.orderInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Taco_Order")
                .usingGeneratedKeyColumns("id");

        this.orderTacoInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Taco_Order_Tacos");
    }

    @Override
    public Order save(Order order) {
        order.setPlacedAt(new Date());
        long orderId = saveOrderDetails(order);
        order.setId(orderId);
        for (Taco taco : order.getTacos()) {
            saveTacoToOrder(orderId, taco);
        }
        return order;
    }

    private long saveOrderDetails(Order order) {
        return orderInserter.executeAndReturnKey(order.detailsMap()).longValue();
    }

    private void saveTacoToOrder(long orderId, Taco taco) {
        orderTacoInserter.execute(
                ImmutableMap.<String, Object>builder()
                        .put("tacoOrder", orderId)
                        .put("taco", taco.getId())
                        .build());
    }
}
