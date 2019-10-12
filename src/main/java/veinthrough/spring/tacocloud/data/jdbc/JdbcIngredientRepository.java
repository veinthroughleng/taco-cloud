package veinthrough.spring.tacocloud.data.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import veinthrough.spring.tacocloud.data.AbstractIngredientRepository;
import veinthrough.spring.tacocloud.data.model.Ingredient;

import static veinthrough.spring.tacocloud.data.model.Ingredient.INGREDIENT_TYPE;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcIngredientRepository extends AbstractIngredientRepository {
    private JdbcOperations jdbc;

    @Autowired
    public JdbcIngredientRepository(JdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Iterable<Ingredient> getAll() {
        return jdbc.query("select id, name, type from Ingredient",
                (row, rowNum) -> mapRowToIngredient(row));
    }

    @Override
    public Ingredient getOneById(String id) {
        return jdbc.queryForObject("select id, name, type from Ingredient where id=?",
                (row, rowNum) -> mapRowToIngredient(row), id);
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbc.update("insert into Ingredient (id, name, type) values (?, ?, ?)",
                ingredient.getId(), ingredient.getName(), ingredient.getType().toString());
        return ingredient;
    }

    private Ingredient mapRowToIngredient(ResultSet row) throws SQLException {
        return Ingredient.builder()
                .id(row.getString("id"))
                .name(row.getString("name"))
                .type(INGREDIENT_TYPE.valueOf(row.getString("type")))
                .build();
    }
}
