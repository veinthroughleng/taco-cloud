package veinthrough.tacocloud.model;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.List;

@Data
@UserDefinedType("taco")
class TacoUDT implements UDT<Taco>{
    private final String name;
    private final List<IngredientUDT> ingredients;
}
