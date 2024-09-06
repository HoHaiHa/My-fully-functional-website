package springbootWeb2.com.hohaiha.app.service;

import java.sql.Date;
import java.util.List;

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
	public Page<ProductResponse> searchAndFilter(int page, int size,String name, Date startDay,Date endDay,String category,String sortBy,String derection);
}
