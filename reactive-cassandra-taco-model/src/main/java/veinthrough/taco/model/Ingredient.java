package veinthrough.taco.model;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Table("ingredients")
//JPA requires that entities have a no-arguments  constructor
public class Ingredient implements UDTed<IngredientUDT> {
    @PrimaryKey
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
    public IngredientUDT toUDT() {
        return new IngredientUDT(name, type);
    }

    public enum INGREDIENT_TYPE {
        WRAP,
        PROTEIN,
        VEGGIES,
        CHEESE,
        SAUCE
    }
}
