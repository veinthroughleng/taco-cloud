package veinthrough.taco.rest.auto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.hateoas.*;
import org.springframework.hateoas.config.EnableEntityLinks;
import veinthrough.taco.model.Ingredient;
import veinthrough.taco.model.Taco;

@Configuration
@EnableEntityLinks
public class SpringDataRestConfig {
    /*
    // since the IDs are already contained within the self links in the response,
    // you don't need to expose them as properties of the object itself.
    // extends RepositoryRestConfigurerAdapter
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Ingredient.class);
    }
    */

    @SuppressWarnings("Convert2Lambda")
    @Bean
    public ResourceProcessor<PagedResources<Resource<Taco>>>
    tacoProcessor(EntityLinks links) {

        return new ResourceProcessor<PagedResources<Resource<Taco>>>() {
            @Override
            public PagedResources<Resource<Taco>> process(
                    PagedResources<Resource<Taco>> resource) {

                //"_links": {
                //    "recents": {
                //        "href": "http://localhost:8080/tacos/recent"
                //    }
                resource.add(
                        links.linkFor(Taco.class)
                                .slash("recent")
                                .withRel("recents"));
                return resource;
            }
        };
    }
}
