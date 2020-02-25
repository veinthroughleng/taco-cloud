package veinthrough.taco.model.href;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Resource;
import veinthrough.taco.model.Order;
import veinthrough.taco.model.User;
import java.util.Date;
import java.util.List;

import static veinthrough.taco.model.href.Href.getIdFromHref;

@Data
@NoArgsConstructor
public class OrderMix implements Href {
    private Long id;
    private String href;
    private List<TacoMix> tacos;
    private Date placedAt;

    // attributes
    private User user;
    private String name;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String ccNumber;
    private String ccExpiration;
    private String ccCVV;



    public OrderMix(Resource<Order> resource, List<TacoMix> tacos) {
        this.user = resource.getContent().getUser();
        this.name = resource.getContent().getName();
        this.street = resource.getContent().getStreet();
        this.city = resource.getContent().getCity();
        this.state = resource.getContent().getState();
        this.zip = resource.getContent().getZip();
        this.ccNumber = resource.getContent().getCcNumber();
        this.ccExpiration = resource.getContent().getCcExpiration();
        this.ccCVV = resource.getContent().getCcCVV();
        this.placedAt = resource.getContent().getPlacedAt();

        // 1. set href
        setHref(resource.getId().getHref());

        // 2. set id
        if (resource.getContent().getId() != null) {
            setId(resource.getContent().getId());
        } else {
            setId(Long.valueOf(getIdFromHref(href)));
        }

        // 3. set tacos
        setTacos(tacos);
    }
}
