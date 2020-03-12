package veinthrough.taco.model;

import lombok.*;
import org.springframework.context.annotation.Profile;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Profile("jpa-rest")
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor
@Builder
@Table(name = "Taco_Order")
@RestResource(rel="orders", path="orders")
public class Order {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // non-final, used in convert href to prototype

    @ManyToOne(targetEntity=User.class)
    private User user;

    // attributes
    private String name;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String ccNumber;
    private String ccExpiration;
    private String ccCVV;

    private Date placedAt;

    @ManyToMany(targetEntity = Taco.class)
    private List<Taco> tacos = new ArrayList<>();

    public Order(String name, String street, String city, String state, String zip) {
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    @PrePersist
    void placedAt() {
        this.placedAt = new Date();
    }

    public void addDesign(Taco taco) {
        if (tacos == null) tacos = new ArrayList<>();
        tacos.add(taco);
    }
}
