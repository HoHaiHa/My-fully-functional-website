package springbootWeb2.com.hohaiha.app.dto.request;


import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {
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
	
	private String category;
	private String color;
	private Date creationDate;
	
	private boolean hot;
}
