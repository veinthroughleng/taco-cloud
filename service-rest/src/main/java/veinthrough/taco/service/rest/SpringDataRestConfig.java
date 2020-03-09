package veinthrough.taco.service.rest;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.config.EnableEntityLinks;
import veinthrough.taco.model.Ingredient;
import veinthrough.taco.model.Order;
import veinthrough.taco.model.Taco;

@Configuration
@EnableEntityLinks
@Profile("jpa-rest")
public class SpringDataRestConfig extends RepositoryRestConfigurerAdapter {
    @SuppressWarnings("Convert2Lambda")
    @Bean
    public ResourceProcessor<PagedResources<Resource<Taco>>>
    tacoProcessor(EntityLinks links) {

        return new ResourceProcessor<PagedResources<Resource<Taco>>>() {
            @Override
            public PagedResources<Resource<Taco>> process(
                    PagedResources<Resource<Taco>> resource) {
                resource.add(
                        links.linkFor(Taco.class)
                                .slash("recent")
                                .withRel("recents"));

                return resource;
            }
        };
    }

    @SuppressWarnings("Convert2Lambda")
    @Bean
    public ResourceProcessor<PagedResources<Resource<Order>>>
    orderProcessor(EntityLinks links) {

        return new ResourceProcessor<PagedResources<Resource<Order>>>() {
            @SneakyThrows
            @Override
            public PagedResources<Resource<Order>> process(
                    PagedResources<Resource<Order>> resource) {
                resource.add(
                        links.linkFor(Order.class)
                                .slash("user/USER_ID")
                                .withRel("orders of user"));

                return resource;
            }
        };
    }

    // since the IDs are already contained within the self links in the response,
    // you don't need to expose them as properties of the object itself.
    // extends RepositoryRestConfigurerAdapter
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Ingredient.class)
                .exposeIdsFor(Taco.class)
                .exposeIdsFor(Order.class);
    }
}
