package veinthrough.tacocloud.data;

import org.springframework.data.repository.CrudRepository;
import veinthrough.tacocloud.model.Taco;

public interface TacoRepository
        extends CrudRepository<Taco, Long> {
}
