package veinthrough.tacocloud.rest.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import veinthrough.tacocloud.data.OrderRepository;
import veinthrough.tacocloud.data.UserRepository;
import veinthrough.tacocloud.model.Order;
import veinthrough.tacocloud.model.User;
import veinthrough.tacocloud.property.PageSizeProps;
import veinthrough.utils.MethodLog;

import java.util.UUID;

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
    public Flux<Order> ordersOfUser(@PathVariable("userId") UUID userId) {
        return userRepo.findById(userId)
                .map(User::toUDT)
                .flatMapMany(orderRepo::findByUserOrderByPlacedAt)
                .take(pageSizeProps.getPageSizes().get("order"));
    }

    @GetMapping("/order/{id}")
    public Mono<Order> orderById(@PathVariable("id") UUID id) {
        return orderRepo.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Order> postOrder(@RequestBody Mono<Order> order) {
        log.info(MethodLog.inLog(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "order", order.toString()));
        return order.flatMap(orderRepo::save);
    }

    @PutMapping("/order/{id}")
    public Mono<Order> putOrder(@RequestBody Mono<Order> order, @PathVariable UUID id) {
        return order.map(order1 -> {
            order1.setId(id);
            return order1;
        }).flatMap(orderRepo::save);
    }

    @PatchMapping("/order/{id}")
    public Mono<Order> patchOrder(@PathVariable("id") UUID id,
                                  @RequestBody Mono<Order> patch) {
        return orderRepo.findById(id)
                .switchIfEmpty(patch.map(patchData -> {
                    patchData.setId(id);
                    return patchData;
                }))
                .flatMap(order ->
                        //will not handle {User user} and {List<Taco> tacos}
                        patch.map(patchData -> {
                            if (patchData.getName() != null) order.setName(patchData.getName());
                            if (patchData.getStreet() != null) order.setStreet(patchData.getStreet());
                            if (patchData.getCity() != null) order.setCity(patchData.getCity());
                            if (patchData.getState() != null) order.setState(patchData.getState());
                            if (patchData.getZip() != null) order.setZip(patchData.getZip());
                            if (patchData.getCcNumber() != null) order.setCcNumber(patchData.getCcNumber());
                            if (patchData.getCcExpiration() != null) order.setCcExpiration(patchData.getCcExpiration());
                            if (patchData.getCcCVV() != null) order.setCcCVV(patchData.getCcCVV());
                            return order;
                        }))
                .flatMap(orderRepo::save);
    }

    @DeleteMapping("/order/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteOrder(@PathVariable UUID id) {
        return orderRepo.deleteById(id);
    }
}
