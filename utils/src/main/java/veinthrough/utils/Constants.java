package veinthrough.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    public static final String JSON = "application/json";
    public static final String HAL_JSON = "application/hal+json";

    public static final String PATH_DESIGN = "design";
    public static final String PATH_ORDER = "orders";
    public static final String PATH_INGREDIENT = "ingredients";
}
