package veinthrough.spring.tacocloud.data;

import org.springframework.data.repository.CrudRepository;
import veinthrough.spring.tacocloud.data.model.Taco;

public interface TacoRepository
        extends CrudRepository<Taco, Long> {
}
