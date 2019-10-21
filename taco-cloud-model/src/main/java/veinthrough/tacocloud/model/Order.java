package veinthrough.tacocloud.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Table(name = "Taco_Order")
public class Order{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private User user;

    @ManyToMany(targetEntity = Taco.class)
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

    public Order(@NotBlank(message = "Name is required.") String name,
                 @NotBlank(message = "Street is required.") String street,
                 @NotBlank(message = "City is required.") String city,
                 @NotBlank(message = "State is required.") String state,
                 @NotBlank(message = "Zip code is required.") String zip) {
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public void addDesign(Taco taco) {
        tacos.add(taco);
    }

    @PrePersist
    void placedAt() {
        this.placedAt = new Date();
    }
}
