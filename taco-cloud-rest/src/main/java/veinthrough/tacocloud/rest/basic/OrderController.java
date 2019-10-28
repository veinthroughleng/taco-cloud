package veinthrough.tacocloud.rest.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import veinthrough.tacocloud.data.OrderRepository;
import veinthrough.tacocloud.data.UserRepository;
import veinthrough.tacocloud.model.Order;
import veinthrough.tacocloud.model.User;
import veinthrough.tacocloud.property.PageSizeProps;
import veinthrough.utils.MethodLog;

import static veinthrough.utils.Constants.JSON;
import static veinthrough.utils.Constants.PATH_ORDER;

@RestController
@RequestMapping(path = PATH_ORDER,
        produces = JSON,
        consumes = JSON)
@CrossOrigin(origins = "*")
@Slf4j
public class OrderController {
    private OrderRepository orderRepo;
    private PageSizeProps pageSizeProps;
    private UserRepository userRepo;

    @Autowired
    public OrderController(OrderRepository orderRepo, PageSizeProps pageSizeProps, UserRepository userRepo) {
        //[DEBUG]
        log.info(MethodLog.inLog("OrderController constructor",
                "pageSizeProps.order", pageSizeProps.toString()));
        this.orderRepo = orderRepo;
        this.pageSizeProps = pageSizeProps;
        this.userRepo = userRepo;
    }

    @GetMapping("/user/{userId}")
    public Iterable<Order> ordersOfUser(@PathVariable("userId") Long userId) {
        Pageable pageable = PageRequest.of(0, pageSizeProps.getPageSizes().get("order"));
        User user = userRepo.findById(userId).orElse(null);
        return user == null ? null :
                orderRepo.findByUserOrderByPlacedAtDesc(user, pageable);
    }

    @GetMapping("/order/{id}")
    public Order orderById(@PathVariable("id") Long id) {
        //[DEBUG]
        log.info(MethodLog.inLog("orderById"));
        return orderRepo.findById(id).orElse(null);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order postOrder(@RequestBody Order order) {
        log.info(MethodLog.inLog("postOrder",
                "order", order.toString()));
        return orderRepo.save(order);
    }

    @PutMapping("/order/{id}")
    public Order putOrder(@PathVariable("id") Long id,
                          @RequestBody Order order) {
        order.setId(id);
        return orderRepo.save(order);
    }

    @PatchMapping("/order/{id}")
    public Order patchOrder(@PathVariable("id") Long id,
                            @RequestBody Order patch) {
        Order order = orderRepo.findById(id).orElseGet(
                () -> {
                    patch.setId(id);
                    return patch;}
        );

        //will not handle {User user} and {List<Taco> tacos}
        if (patch.getName() != null) order.setName(patch.getName());
        if (patch.getStreet() != null) order.setStreet(patch.getStreet());
        if (patch.getCity() != null) order.setCity(patch.getCity());
        if (patch.getState() != null) order.setState(patch.getState());
        if (patch.getZip() != null) order.setZip(patch.getZip());
        if (patch.getCcNumber() != null) order.setCcNumber(patch.getCcNumber());
        if (patch.getCcExpiration() != null) order.setCcExpiration(patch.getCcExpiration());
        if (patch.getCcCVV() != null) order.setCcCVV(patch.getCcCVV());

        return orderRepo.save(order);
    }

    @DeleteMapping("/order/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Long id) {
        orderRepo.deleteById(id);
    }
}
