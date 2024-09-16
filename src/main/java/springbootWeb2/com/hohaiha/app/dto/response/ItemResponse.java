package springbootWeb2.com.hohaiha.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import springbootWeb2.com.hohaiha.app.entity.Product;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemResponse {
	private String id;
	private Product product;
	private long quantity;
}
