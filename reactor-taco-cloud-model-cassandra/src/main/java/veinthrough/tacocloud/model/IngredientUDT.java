package veinthrough.tacocloud.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import static veinthrough.tacocloud.model.Ingredient.INGREDIENT_TYPE;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@UserDefinedType("ingredient")
public
class IngredientUDT implements UDT<Ingredient>{
    private final String name;
    private final INGREDIENT_TYPE type;
}
