package veinthrough.taco.rest.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import veinthrough.taco.data.OrderRepository;
import veinthrough.taco.data.UserRepository;
import veinthrough.taco.model.Order;
import veinthrough.taco.model.User;
import veinthrough.taco.property.TacoProps;
import veinthrough.taco.utils.MethodLog;

import static veinthrough.taco.utils.Constants.JSON;
import static veinthrough.taco.utils.Constants.PATH_ORDERS;

@RestController
@RequestMapping(path = PATH_ORDERS,
        produces = JSON,
        consumes = JSON)
@CrossOrigin(origins = "*")
@Slf4j
public class OrderController {
    private OrderRepository orderRepo;
    private TacoProps tacoProps;
    private UserRepository userRepo;

    @Autowired
    public OrderController(OrderRepository orderRepo, TacoProps tacoProps, UserRepository userRepo) {
        log.debug(MethodLog.log(
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                "pageSizeProps.order", tacoProps.toString()));
        this.orderRepo = orderRepo;
        this.tacoProps = tacoProps;
        this.userRepo = userRepo;
    }

    @GetMapping("/user/{userId}")
    public Iterable<Order> ordersOfUser(@PathVariable("userId") Long userId) {
        Pageable pageable = PageRequest.of(0, tacoProps.getPageSizes().get("order"));
        User user = userRepo.findById(userId).orElse(null);
        return user == null ? null :
                orderRepo.findByUserIdOrderByPlacedAtDesc(userId, pageable);
    }

    @GetMapping("/order/{id}")
    public Order orderById(@PathVariable("id") Long id) {
        return orderRepo.findById(id).orElse(null);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order postOrder(@RequestBody Order order) {
        log.info(MethodLog.log("postOrder",
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
