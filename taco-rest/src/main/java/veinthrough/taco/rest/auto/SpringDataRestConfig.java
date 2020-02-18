package veinthrough.taco.rest.auto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.*;
import org.springframework.hateoas.config.EnableEntityLinks;
import veinthrough.taco.model.Taco;

@Configuration
@EnableEntityLinks
public class SpringDataRestConfig {

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

    // since the IDs are already contained within the self links in the response,
    // you don't need to expose them as properties of the object itself.
    // extends RepositoryRestConfigurerAdapter
    /*
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Ingredient.class);
    }
    */
}
