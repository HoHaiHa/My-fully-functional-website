package springbootWeb2.com.hohaiha.app.controller.apiController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import springbootWeb2.com.hohaiha.app.dto.request.ProductRequest;
import springbootWeb2.com.hohaiha.app.dto.response.ApiResponse;
import springbootWeb2.com.hohaiha.app.dto.response.ProductResponse;
import springbootWeb2.com.hohaiha.app.service.ProductService;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@PostMapping
	ApiResponse<ProductResponse> createProduct(@RequestBody ProductRequest request) {
		return ApiResponse.<ProductResponse>builder()
				.result(productService.createProduct(request))
				.build();
	}
	@GetMapping
	ApiResponse<Page<ProductResponse>> getProducts(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size) {
		return ApiResponse.<Page<ProductResponse>>builder()
				.result(productService.getProducts(page,size))
				.build();
	}
	
	@GetMapping("/{id}")
	ApiResponse<ProductResponse> getProduct(@PathVariable String id) {
		return ApiResponse.<ProductResponse>builder()
				.result(productService.getProduct(id))
				.build();
	}
	
	@DeleteMapping("/{id}")
	ApiResponse<String> deleteProduct(@PathVariable String id) {
		productService.deleteProduct(id);
		return ApiResponse.<String>builder().result("User has been deleted").build();
				
	}
	
	@PutMapping("/{id}")
	ApiResponse<ProductResponse> updateProduct(@PathVariable String id, @RequestBody ProductRequest request) {
		return ApiResponse.<ProductResponse>builder()
				.result(productService.updateProduct(id,request))
				.build();
	}
	
	@GetMapping("/search")
	ApiResponse<Page<ProductResponse>> findByName(@RequestParam String name,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size) {
		return ApiResponse.<Page<ProductResponse>>builder()
				.result(productService.searchAndFilter(name,page,size))
				.build();
	}
	
	
}
