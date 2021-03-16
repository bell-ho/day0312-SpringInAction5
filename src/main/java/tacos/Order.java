package tacos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Data;

@Data
@Entity
/*
 * @Entity 어노테이션은 데이타베이스의 테이블과 일대일로 매칭되는 객체 단위이며 Entity 객체의 인스턴스 하나가 테이블에서 하나의
 * 레코드 값을 의미합니다. 그래서 객체의 인스턴스를 구분하기 위한 유일한 키값을 가지는데 이것은 테이블 상의 Primary Key 와 같은
 * 의미를 가지며 @Id 어노테이션으로 표기 됩니다.
 */
@Table(name = "Taco_Order") //@Table : Order개체가 데이터베이스 Taco_Order테이블에 저장되어야 한다는 것을 나타낸다
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Date placedAt;
	
	//한 건의 주문이 한 명의 사용자에 속한다는 것, 한명의 사용자는 여러 주문을 가질 수 없다.
	@ManyToOne	
	private User user;
	
	// 배달 주소에 관한 속성(street,city,state,zip)들의 경우 사용자가 입력을 하지않은 필드가 있는지 확인만 하면 되므로
	// 이때는 자바빈 유효성 검사 API의 @Notblank 어노테이션을 사용

	@NotBlank(message = "이름 입력")
	private String deliveryName;

	@NotBlank(message = "주소 입력")
	private String deliveryStreet;

	@NotBlank(message = "도시 입력")
	private String deliveryCity;

	@NotBlank(message = "주 입력")
	private String deliveryState;

	@NotBlank(message = "zip 입력")
	private String deliveryZip;

	// 대금 지불에 관한 필드는 유효한 번호인지까지 확인
	@CreditCardNumber
	private String ccNumber;

	@Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$", message = "Must be formatted MM/YY")
	private String ccExpiration;

	// cvv 번호 3자리 숫자인지
	@Digits(integer = 3, fraction = 0, message = "Invalid CVV")
	private String ccCVV;

	@ManyToMany(targetEntity = Taco.class)
	private List<Taco> tacos = new ArrayList<Taco>();

	public void addDesign(Taco design) {
		this.tacos.add(design);
	}

	@PrePersist
	void placedAt() {
		this.placedAt = new Date();
	}
}
