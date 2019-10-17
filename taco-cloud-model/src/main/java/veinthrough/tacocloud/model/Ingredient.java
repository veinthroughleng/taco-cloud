package veinthrough.tacocloud.model;

import lombok.*;
import veinthrough.utils.Identifiable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
//JPA requires that entities have a no-arguments  constructor
public class Ingredient implements Identifiable<String> {
    @Id
    private final String id;
    private final String name;
    private final INGREDIENT_TYPE type;

    public String getTypeString(boolean lowerCase) {
        if (lowerCase) {
            return getType().toString().toLowerCase();
        }
        return getType().toString();
    }

    @Override
    public String getIdentifier() {
        return getId();
    }

    public enum INGREDIENT_TYPE {
            WRAP,
            PROTEIN,
            VEGGIES,
            CHEESE,
            SAUCE
        }
    }
