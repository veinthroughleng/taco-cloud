package veinthrough.tacocloud.model;

import lombok.Data;
import veinthrough.utils.Identifiable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Taco implements Identifiable<Long> {
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

    @PrePersist
    void createdAt() {
        this.createdAt = new Date();
    }

    @Override
    public Long getIdentifier() {
        return getId();
    }
}
