package veinthrough.tacocloud.model;

import com.datastax.driver.core.utils.UUIDs;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Table("orders")
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Order{
    private static final long serialVersionUID = 1L;

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private UUID id = UUIDs.timeBased();

    @Column("user")
    private UserUDT user;

    @Column("tacos")
    private List<TacoUDT> tacos = new ArrayList<>();

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


    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED,
            ordering = Ordering.DESCENDING)
    private Date placedAt = new Date();

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

    public void addDesign(TacoUDT taco) {
        tacos.add(taco);
    }
}
