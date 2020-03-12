package veinthrough.taco.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
    public class Constants {
        public static final String JSON = "application/json";
        public static final String HAL_JSON = "application/hal+json";

        public static final String PATH_BASIC_REST = "/rest";
        public static final String PATH_DESIGN = "/design";
        public static final String PATH_ORDERS = "/orders";
        public static final String PATH_INGREDIENTS = "/ingredients";
        public static final String PATH_TACOS = "/tacos";
        public static final String PATH_USERS = "/users";
        public static final String PATH_USER = "/user";
        public static final String PATH_USER_INFO = "/userInfo";
        public static final String PATH_USERNAME = "/name";
        public static final String PATH_RECENT = "/recent";

    public static final String DEFAULT_DIRECT_EXCHANGE = "amq.direct";

    public static final String[] URL_LOGIN = {
            "/login"
    };

    public static final String[] URL_H2_CONSOLE = {
            "/h2-console",
            "/h2-console/**"
    };

    public static final String[] URL_ACTUATOR = {
            "/actuator",
            "/actuator/**"
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

    public static final String[] URL_DATA_TACOS = {
            "/rest/tacos",
            "/rest/tacos/**"
    };

    public static final String[] URL_DATA_ORDERS = {
            "/rest/orders",
            "/rest/orders/**"
    };

    public static final String[] URL_DATA_USERS = {
            "/rest/users",
            "/rest/users/**"
    };
}
