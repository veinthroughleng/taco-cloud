package veinthrough.taco.service.rest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.LinkBuilder;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import veinthrough.taco.data.OrderRepository;
import veinthrough.taco.model.Order;
import veinthrough.taco.property.TacoProps;
import veinthrough.taco.utils.MethodLog;

import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static veinthrough.taco.utils.Constants.*;

@Slf4j
@RepositoryRestController
public class OrdersController {
    private OrderRepository orderRepo;
    private TacoProps tacoProps;
    private EntityLinks entityLinks;

    @Autowired
    public OrdersController(OrderRepository orderRepo,
                            TacoProps tacoProps,
                            EntityLinks entityLinks) {
        this.entityLinks = entityLinks;
        log.debug(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "tacoProps", tacoProps.toString()));
        this.orderRepo = orderRepo;
        this.tacoProps = tacoProps;
    }

    @GetMapping(path = PATH_ORDERS + PATH_USER + "/{id}", produces = HAL_JSON)
    public ResponseEntity<Resources<Resource<Order>>> OrdersOfUser(@PathVariable Long id) {
        PageRequest page = PageRequest.of(
                0, tacoProps.getPageSizes().get("order"), Sort.by("placedAt").descending());

        Resources<Resource<Order>> orderResources = new Resources<>(
                orderRepo.findByUserIdOrderByPlacedAtDesc(id, page)
                        .getContent().stream()
                        .map(order -> {
                            Resource<Order> resource = new Resource<>(order);
                            //    "self": {
                            //        "href": "http://localhost:8080/orders/{id}"
                            resource.add(
                                    entityLinks.linkForSingleResource(
                                            Order.class, order.getId())
                                            .withSelfRel());
                            //    "order": {
                            //        "href": "http://localhost:8080/orders/{id}"
                            resource.add(
                                    entityLinks.linkForSingleResource(
                                            Order.class, order.getId())
                                            .withRel("order"));
                            //    "tacos": {
                            //        "href": "http://localhost:8080/orders/{id}/tacos"
                            resource.add(
                                    entityLinks.linkForSingleResource(
                                            Order.class, order.getId())
                                            .slash("tacos")
                                            .withRel("tacos"));
                            //    "user": {
                            //        "href": "http://localhost:8080/orders/{id}/user"
                            resource.add(
                                    entityLinks.linkForSingleResource(
                                            Order.class, order.getId())
                                            .slash("user")
                                            .withRel("user"));
                            return resource;
                        })
                        .collect(Collectors.toList()));

        log.debug(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "user", id.toString(),
                "order resources by user", orderResources.toString()));

        // "_links": {
        //    "user": {
        //        "href": "http://localhost:8080/orders/user/{id}"
        //    }
        LinkBuilder linkBuilder = linkTo(methodOn(OrdersController.class).OrdersOfUser(id));
        orderResources.add(linkBuilder.withSelfRel());
        orderResources.add(linkBuilder.withRel("orders of user"));

        return new ResponseEntity<>(orderResources, HttpStatus.OK);
    }
}
