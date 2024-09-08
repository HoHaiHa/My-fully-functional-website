package springbootWeb2.com.hohaiha.app.service;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import springbootWeb2.com.hohaiha.app.dto.request.ProductRequest;
import springbootWeb2.com.hohaiha.app.dto.response.ProductResponse;

@Service
public interface ProductService {
	public ProductResponse createProduct(ProductRequest request) ;
	
	public Page<ProductResponse> getProducts(int page, int size);
	public ProductResponse getProduct(String id);
	public void deleteProduct(String id);
	public ProductResponse updateProduct(String id,ProductRequest request);
	public Page<ProductResponse> searchAndFilter(int page, int size,String name, LocalDate startDay,LocalDate endDay,String category,String sortBy,String direction);
}
