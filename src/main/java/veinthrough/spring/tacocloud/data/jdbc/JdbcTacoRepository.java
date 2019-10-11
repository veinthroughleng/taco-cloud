package veinthrough.spring.tacocloud.data.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import veinthrough.spring.tacocloud.data.TacoRepository;
import veinthrough.spring.tacocloud.data.model.Ingredient;
import veinthrough.spring.tacocloud.data.model.Taco;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

@Repository
public class JdbcTacoRepository implements TacoRepository {
    private JdbcOperations jdbc;

    @Autowired
    public JdbcTacoRepository(JdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Taco save(Taco taco) {
        long tacoId = saveTaco(taco);
        taco.setId(tacoId);
        for (Ingredient ingredient : taco.getIngredients()) {
            saveIngredientToTaco(tacoId, ingredient);
        }
        return taco;
    }

    private long saveTaco(Taco taco) {
        taco.setCreatedAt(new Date());
        PreparedStatementCreatorFactory pscFactory = new PreparedStatementCreatorFactory(
                "insert into Taco (name, createdAt) values (?, ?)",
                Types.VARCHAR, Types.TIMESTAMP);

        PreparedStatementCreator psc = pscFactory.newPreparedStatementCreator(
                Arrays.asList(taco.getName(),
                        new Timestamp(taco.getCreatedAt().getTime()))
        );

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(psc, keyHolder);
        //SOMETIMES, Method invocation 'longValue' may produce 'NullPointerException'
        //with h2 version 1.4.196?
        return keyHolder.getKey().longValue();
    }

    private void saveIngredientToTaco(long tacoId, Ingredient ingredient) {
        jdbc.update("insert into Taco_Ingredients (taco, ingredient) values (?,?)",
                tacoId, ingredient.getId());
    }
}
