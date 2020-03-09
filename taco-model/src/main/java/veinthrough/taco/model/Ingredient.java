package veinthrough.taco.model;

import lombok.*;
import org.springframework.context.annotation.Profile;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
@Entity
@Profile("jpa-rest")
@NoArgsConstructor
//JPA requires that entities have a no-arguments  constructor
public class Ingredient{
    @Id
    private String id; // non-final, used in convert href to prototype
    private String name;
    private INGREDIENT_TYPE type;

    public String getTypeString(boolean lowerCase) {
        if (lowerCase) {
            return getType().toString().toLowerCase();
        }
        return getType().toString();
    }

    public enum INGREDIENT_TYPE {
            WRAP,
            PROTEIN,
            VEGGIES,
            CHEESE,
            SAUCE
        }
    }
