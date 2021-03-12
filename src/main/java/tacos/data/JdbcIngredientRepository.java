package tacos.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import tacos.Ingredient;

//외부I/O 처리 : @Repository (퍼시스턴스 레이어, DB나 파일같은 외부 I/O 작업을 처리함)
@Repository
public class JdbcIngredientRepository implements IngredientRepository {

	private JdbcTemplate jdbc;

	@Autowired
	public JdbcIngredientRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public Iterable<Ingredient> findAll() {
		return jdbc.query("select id,name,type from Ingredient", this::mapRowToIngredient);
	}

	@Override
	public Ingredient findById(String id) {
		// TODO Auto-generated method stub
		return jdbc.queryForObject("select id, name, type, from Ingredient where Ingredient id=?",
				this::mapRowToIngredient, id);
	}

	private Ingredient mapRowToIngredient(ResultSet rs, int rowNum) throws SQLException {
		return new Ingredient(rs.getString("id"), rs.getString("name"), Ingredient.Type.valueOf(rs.getString("type")));
	}

	@Override
	public Ingredient save(Ingredient ingredient) {
		jdbc.update("insert into Ingredient (id,name,type) values(?,?,?)", 
				ingredient.getId(), 
				ingredient.getName(),
				ingredient.getType().toString());
		return ingredient;
	}
}
