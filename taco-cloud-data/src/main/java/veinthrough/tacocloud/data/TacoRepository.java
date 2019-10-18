package veinthrough.tacocloud.data;

import org.springframework.data.repository.PagingAndSortingRepository;
import veinthrough.tacocloud.model.Taco;

public interface TacoRepository
        extends PagingAndSortingRepository<Taco, Long> {
}
