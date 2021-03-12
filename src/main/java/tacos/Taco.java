package tacos;

import java.util.Date;
import java.util.List;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Data
public class Taco {

	private Long id;
	private Date createdAt;

	//name의 속성값이 없거나 null인지 확인
	@NotNull
	@Size(min = 5,message = "Name Must be at least 5 characters long")
	private String name;
	
	//최소한 하나의 식자재 항목을 선택했는지 확인
	@Size(min = 1,message = "You must choose at least 1 ingredient")
	private List<Ingredient> ingredients;
}
