package springbootWeb2.com.hohaiha.app.dto.response;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import springbootWeb2.com.hohaiha.app.entity.Category;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
	private String id;
	private String name;
	private String img;
	private long price;
	private String descriptions;
	private String material;
	private int quantity;
	private int discount;
	private String feature;
	private String size;
	private String brand;
	private Date creationDate;
	
	private Category category;
	private String color;
	
	private boolean hot;
}
