package veinthrough.taco.model.href;

import lombok.Data;
import veinthrough.taco.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Data
// only attributes, no id/placedAt
public class OrderHref {
    private String user;
    private List<String> tacos;

    // attributes
    private String name;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String ccNumber;
    private String ccExpiration;
    private String ccCVV;

    public OrderHref(OrderMix mix) {
        this.name = mix.getName();
        this.street = mix.getStreet();
        this.city = mix.getCity();
        this.state = mix.getState();
        this.zip = mix.getZip();
        this.ccNumber = mix.getCcNumber();
        this.ccExpiration = mix.getCcExpiration();
        this.ccCVV = mix.getCcCVV();

        // build href for user and tacos
        setUser(mix.getUser().getHref());
        setTacos(
                mix.getTacos().stream()
                        .map(TacoMix::getHref)
                        .collect(Collectors.toList()));
    }
}
