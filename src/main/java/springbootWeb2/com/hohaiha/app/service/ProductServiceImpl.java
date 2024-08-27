package springbootWeb2.com.hohaiha.app.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import springbootWeb2.com.hohaiha.app.dto.request.ProductRequest;
import springbootWeb2.com.hohaiha.app.dto.response.ProductResponse;
import springbootWeb2.com.hohaiha.app.entity.Category;
import springbootWeb2.com.hohaiha.app.entity.Product;
import springbootWeb2.com.hohaiha.app.exception.AppException;
import springbootWeb2.com.hohaiha.app.exception.ErrorCode;
import springbootWeb2.com.hohaiha.app.mapper.ProductMapper;
import springbootWeb2.com.hohaiha.app.repository.CategoryRepository;
import springbootWeb2.com.hohaiha.app.repository.ProductRepository;

@Component
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ProductResponse createProduct(ProductRequest request) {
		Product product = productMapper.toProduct(request);

		Category category = categoryRepository.findById(request.getCategory().trim())
				.orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
		product.setCategory(category);

		try {
			productRepository.save(product);
		} catch (DataIntegrityViolationException exception) {
			throw new AppException(ErrorCode.PRODUCT_EXISTED);
		}
		return productMapper.toProductResponse(product);
	}

	public Page<ProductResponse> getProducts(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return productRepository.findAll(pageable).map(productMapper::toProductResponse);
	}

	public ProductResponse getProduct(String id) {
		return productMapper.toProductResponse(
				productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED)));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void deleteProduct(String id) {
		productRepository.deleteById(id);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ProductResponse updateProduct(String id, ProductRequest request) {
		Product oldProduct = productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
		
		Product product = productMapper.toProductFromUpdate(oldProduct,request);
		
		Category category = categoryRepository.findById(request.getCategory())
				.orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
		product.setCategory(category);

		try {
			productRepository.save(product);
		} catch (DataIntegrityViolationException exception) {
			throw new AppException(ErrorCode.PRODUCT_EXISTED);
		}
		return productMapper.toProductResponse(product);
	}
	
	public Page<ProductResponse> searchAndFilter(String name, int page, int size){
		Pageable pageable = PageRequest.of(page, size);
		return productRepository.searchAndFilter(name, pageable).map(productMapper::toProductResponse);
	}

}
