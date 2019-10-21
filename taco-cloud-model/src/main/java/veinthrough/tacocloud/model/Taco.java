package veinthrough.tacocloud.model;

import lombok.*;
import org.springframework.data.rest.core.annotation.RestResource;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@RestResource(rel="tacos", path="tacos")
public class Taco{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @NotNull
    @Size(min = 1, message = "You must choose at least 1 ingredient.")
    @ManyToMany(targetEntity=Ingredient.class)
    private List<Ingredient> ingredients;

    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long.")
    private String name;

    private Date createdAt;

    @Builder
    public Taco(@NotNull @Size(min = 1, message = "You must choose at least 1 ingredient.")
                        List<Ingredient> ingredients,
                @NotNull @Size(min = 5, message = "Name must be at least 5 characters long.")
                        String name) {
        this.ingredients = ingredients;
        this.name = name;
    }

    @PrePersist
    void createdAt() {
        this.createdAt = new Date();
    }
}
