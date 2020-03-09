package veinthrough.taco.service.rest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import veinthrough.taco.data.UserRepository;
import veinthrough.taco.model.User;
import veinthrough.taco.utils.MethodLog;

import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static veinthrough.taco.utils.Constants.*;

@Slf4j
@RepositoryRestController
public class UsersController {
    private UserRepository userRepo;
    private EntityLinks entityLinks;

    @Autowired
    public UsersController(UserRepository userRepo, EntityLinks entityLinks) {
        this.userRepo = userRepo;
        this.entityLinks = entityLinks;
    }

    @GetMapping(path = PATH_USERS + PATH_USERNAME + "/{name}", produces = HAL_JSON)
    public ResponseEntity<Resource<User>> UserByName(@PathVariable String name) {
        Optional<User> optionalUser = userRepo.findByUsername(name);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Resource<User> resource = new Resource<>(user);
            //    "self": {
            //        "href": "http://localhost:8080/users/{id}"
            resource.add(
                    entityLinks.linkForSingleResource(
                            User.class, user.getId())
                            .withSelfRel());
            //    "user by name": {
            //        "href": "http://localhost:8080/users/name/{name}"
            resource.add(
                    linkTo(methodOn(
                            UsersController.class).UserByName(name))
                            .withRel("user by name"));

            log.debug(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                    "username", name,
                    "user by name", resource.toString()));

            return new ResponseEntity<>(resource, HttpStatus.OK);
        } else {
            return null;
        }
    }
}
