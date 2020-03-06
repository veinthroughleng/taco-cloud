package veinthrough.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    public static final String JSON = "application/json";
    public static final String HAL_JSON = "application/hal+json";

    public static final String PATH_BASIC_REST = "/rest";
    public static final String PATH_DESIGN = "/design";
    public static final String PATH_ORDER = "/orders";
    public static final String PATH_INGREDIENT = "/ingredients";
    public static final String PATH_TACO = "/tacos";
    public static final String PATH_RECENT = "/recent";

    public static final String DEFAULT_DIRECT_EXCHANGE = "amq.direct";

    public static final Long DEFAULT_TOKEN_EXPIRATION_MINUTES = 60L;

    public static final String[] URL_LOGIN = {
            "/login",
            "/signin"
    };

    public static final String[] URL_H2_CONSOLE = {
            "/h2-console",
            "/h2-console/**"
    };

    public static final String[] URL_SWAGGER = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };

    public static final String[] URL_DATA_INGREDIENTS = {
            "/rest/ingredients",
            "/rest/ingredients/**"
    };

    public static final String[] URL_DATA_OTHERS = {
            "/rest/tacos",
            "/rest/tacos/**",
            "/rest/orders",
            "/rest/orders/**",
            "/rest/users",
            "/rest/users/**",

    };
}
