package veinthrough.taco.data;

import org.springframework.data.repository.PagingAndSortingRepository;
import veinthrough.taco.model.Taco;

public interface TacoRepository
        extends PagingAndSortingRepository<Taco, Long> {
}
