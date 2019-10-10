package veinthrough.spring.tacocloud.data.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Ingredient {
    @Getter private final String id;
    @Getter private final String name;
    @Getter private final INGREDIENT_TYPE type;

    public enum INGREDIENT_TYPE {
        WRAP,
        PROTEIN,
        VEGGIES,
        CHEESE,
        SAUCE
    }
}
