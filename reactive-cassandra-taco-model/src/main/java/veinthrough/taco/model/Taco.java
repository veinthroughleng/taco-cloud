package veinthrough.taco.model;

import com.datastax.driver.core.utils.UUIDs;
import lombok.*;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@RestResource(rel = "tacos", path = "tacos")
@Table("tacos")
public class Taco implements UDTed<TacoUDT>{
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private UUID id = UUIDs.timeBased();

    @NotNull
    @Size(min = 1, message = "You must choose at least 1 ingredient.")
    @Column("ingredients")
    private List<IngredientUDT> ingredients;

    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long.")
    private String name;

    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED,
            ordering = Ordering.DESCENDING)
    private Date createdAt = new Date();

    @Builder
    public Taco(@NotNull @Size(min = 1, message = "You must choose at least 1 ingredient.")
                        List<IngredientUDT> ingredients,
                @NotNull @Size(min = 5, message = "Name must be at least 5 characters long.")
                        String name) {
        this.ingredients = ingredients;
        this.name = name;
    }

    @Override
    public TacoUDT toUDT() {
        return new TacoUDT(name, ingredients);
    }
}
