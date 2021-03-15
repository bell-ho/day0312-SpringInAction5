package tacos;

import java.util.Date;
import java.util.List;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
public class Taco {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Date createdAt;

	
	@NotNull // name의 속성값이 없거나 null인지 확인
	@Size(min = 5, message = "Name Must be at least 5 characters long")
	private String name;

	
	@ManyToMany(targetEntity = Ingredient.class)
	@Size(min = 1, message = "You must choose at least 1 ingredient") // 최소한 하나의 식자재 항목을 선택했는지 확인
	private List<Ingredient> ingredients;

	@PrePersist	//객체가 저장되기전에 createAt 속성을 현재 일자와 시간으로 설정
	void createdAt() {
		this.createdAt = new Date();
	}
}
