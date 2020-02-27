package veinthrough.taco.data;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.PagingAndSortingRepository;
import veinthrough.taco.model.Taco;

@Profile("jpa-rest")
public interface TacoRepository
        extends PagingAndSortingRepository<Taco, Long> {
}
