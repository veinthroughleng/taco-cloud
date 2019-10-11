package veinthrough.spring.tacocloud.data;

import veinthrough.spring.tacocloud.data.model.Order;

public interface OrderRepository {
    Order save(Order order);
}
