package tacos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Data;

@Data
public class Order {

	private Long id;
	private Date placedAt;
	
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
	
	//대금 지불에 관한 필드는 유효한 번호인지까지 확인
	@CreditCardNumber
	private String ccNumber;
	
	@Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",message = "Must be formatted MM/YY")
	private String ccExpiration;
	
	//cvv 번호 3자리 숫자인지
	@Digits(integer=3, fraction=0, message="Invalid CVV")
	private String ccCVV;
	
	private List<Taco>tacos = new ArrayList<Taco>();
	
	public void addDesign(Taco design) {
		this.tacos.add(design);
	}
}
