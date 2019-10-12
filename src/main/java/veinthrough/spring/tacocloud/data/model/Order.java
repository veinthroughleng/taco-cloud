package veinthrough.spring.tacocloud.data.model;

import com.google.common.collect.ImmutableMap;
import lombok.Data;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class Order {
    private long id;

    private List<Taco> tacos = new ArrayList<>();

    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Street is required.")
    private String street;

    @NotBlank(message = "City is required.")
    private String city;

    @NotBlank(message = "State is required.")
    private String state;

    @NotBlank(message = "Zip code is required.")
    private String zip;

    //@CreditCardNumber(message="Not a valid credit card number")
    @Digits(integer = 10, fraction = 0, message = "Credit card number should be 10 digits.")
    private String ccNumber;

    //"^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$"
    @Pattern(regexp = "^(0[1-9]|1[0-2])([/])([1-9][0-9])$",
            message = "Must be formatted MM/YY.")
    private String ccExpiration;

    @Digits(integer = 3, fraction = 0, message = "Credit card CVV should be 3 digits.")
    private String ccCVV;

    private Date placedAt;

    public void addDesign(Taco taco) {
        tacos.add(taco);
    }

    public Map<String, Object> detailsMap() {
        return ImmutableMap.<String, Object>builder()
                .put("id", getId())
                .put("deliveryName", getName())
                .put("deliveryStreet", getStreet())
                .put("deliveryCity", getCity())
                .put("deliveryState", getState())
                .put("deliveryZip", getZip())
                .put("ccNumber", getCcNumber())
                .put("ccExpiration", getCcExpiration())
                .put("ccCVV", getCcCVV())
                .put("placedAt", getPlacedAt())
                .build();
    }
}
