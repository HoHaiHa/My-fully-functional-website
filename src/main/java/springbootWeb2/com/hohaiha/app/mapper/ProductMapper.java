package springbootWeb2.com.hohaiha.app.mapper;

import org.springframework.stereotype.Component;

import springbootWeb2.com.hohaiha.app.dto.request.ProductRequest;
import springbootWeb2.com.hohaiha.app.dto.response.ProductResponse;
import springbootWeb2.com.hohaiha.app.entity.Product;

@Component
public interface ProductMapper {
	public Product toProduct(ProductRequest request);
	
	public ProductResponse toProductResponse(Product product);
}
