package veinthrough.taco.model;

import lombok.*;

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
@AllArgsConstructor
@Table(name = "Taco_Order")
public class Order {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // non-final, used in convert href to prototype

    @ManyToMany(targetEntity = Taco.class)
    private List<Taco> tacos = new ArrayList<>();

    // attributes
    private Date placedAt;
    @ManyToOne
    private User user;
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
    @Digits(integer = 10, fraction = 0, message = "Credit card number should be 10 digits.")
    private String ccNumber;
    @Pattern(regexp = "^(0[1-9]|1[0-2])([/])([1-9][0-9])$",
            message = "Must be formatted MM/YY.")
    private String ccExpiration;
    @Digits(integer = 3, fraction = 0, message = "Credit card CVV should be 3 digits.")
    private String ccCVV;

    @PrePersist
    void placedAt() {
        this.placedAt = new Date();
    }

    @Builder
    public Order(Long id, User user, String name,
                 String street, String city, String state, String zip,
                 String ccNumber, String ccExpiration, String ccCVV,
                 Date placedAt, List<Taco> tacos) {
        this.user = user;
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.ccNumber = ccNumber;
        this.ccExpiration = ccExpiration;
        this.ccCVV = ccCVV;
        setPlacedAt(placedAt);
        setId(id);
        setTacos(tacos);
    }

    public void addDesign(Taco taco) {
        if (tacos == null) tacos = new ArrayList<>();
        tacos.add(taco);
    }
}
