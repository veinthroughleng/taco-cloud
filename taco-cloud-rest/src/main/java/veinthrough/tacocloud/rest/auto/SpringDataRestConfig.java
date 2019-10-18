package veinthrough.tacocloud.rest.auto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.*;
import veinthrough.tacocloud.model.Taco;

@Configuration
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

        /*
        //???
        //veinthrough.tacocloud.rest.resource.TacoResource
        //cannot be cast to org.springframework.hateoas.PagedResources
        return (PagedResources<Resource<Taco>> resource) -> {
            resource.add(
                    links.linkFor(Taco.class)
                            .slash("recent")
                            .withRel("recents"));
            return resource;
        };
         */
    }
}
