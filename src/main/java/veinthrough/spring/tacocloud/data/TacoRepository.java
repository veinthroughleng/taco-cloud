package veinthrough.spring.tacocloud.data;

import veinthrough.spring.tacocloud.data.model.Taco;

public interface TacoRepository {
    Taco save(Taco design);
}
