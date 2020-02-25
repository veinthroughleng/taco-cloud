package veinthrough.taco.data;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.PagingAndSortingRepository;
import veinthrough.taco.model.Taco;

@Profile("rest")
public interface TacoRepository
        extends PagingAndSortingRepository<Taco, Long> {
}
