package veinthrough.taco.data;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import veinthrough.taco.model.Taco;

import java.util.UUID;

public interface TacoRepository
        extends ReactiveCassandraRepository<Taco, UUID> {
}
