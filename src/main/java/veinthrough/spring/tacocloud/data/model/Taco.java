package veinthrough.spring.tacocloud.data.model;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class Taco {
    @Size(min=1, message="You must choose at least 1 ingredient.")
    private List<String> ingredients;

    @Size(min=5, message="Name must be at least 5 characters long.")
    private String name;
}
