package veinthrough.tacocloud.data;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import veinthrough.tacocloud.model.Taco;

import java.util.UUID;

public interface TacoRepository
        extends ReactiveCassandraRepository<Taco, UUID> {
}
