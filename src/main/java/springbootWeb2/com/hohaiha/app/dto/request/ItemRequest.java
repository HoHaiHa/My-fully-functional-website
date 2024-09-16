package springbootWeb2.com.hohaiha.app.dto.request;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import springbootWeb2.com.hohaiha.app.entity.Product;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemRequest {
	private String productId;
	private long quantity;
}
