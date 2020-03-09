package veinthrough.taco.model;

import lombok.*;
import org.springframework.context.annotation.Profile;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Profile("jpa-rest")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RestResource(rel="tacos", path="tacos")
public class Taco{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id; // non-final, used in convert href to prototype

    // attributes
    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long.")
    private String name;
    private Date createdAt;

    @NotNull
    @Size(min = 1, message = "You must choose at least 1 ingredient.")
    @ManyToMany(targetEntity=Ingredient.class)
    private List<Ingredient> ingredients;

    @PrePersist
    void createdAt() {
        this.createdAt = new Date();
    }

    public Taco(Long id, String name, List<Ingredient> ingredients) {
        setName(name);
        setId(id);
        this.ingredients = ingredients;
    }

    public Taco(String name, List<Ingredient> ingredients) {
        setName(name);
        setId(id);
        this.ingredients = ingredients;
    }
}
