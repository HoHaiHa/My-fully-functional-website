package springbootWeb2.com.hohaiha.app.controller.apiController;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
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
		return ApiResponse.<ProductResponse>builder().result(productService.createProduct(request)).build();
	}

	@GetMapping
	ApiResponse<Page<ProductResponse>> getProducts(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return ApiResponse.<Page<ProductResponse>>builder().result(productService.getProducts(page, size)).build();
	}

	@GetMapping("/{id}")
	ApiResponse<ProductResponse> getProduct(@PathVariable String id) {
		return ApiResponse.<ProductResponse>builder().result(productService.getProduct(id)).build();
	}

	@DeleteMapping("/{id}")
	ApiResponse<String> deleteProduct(@PathVariable String id) {
		productService.deleteProduct(id);
		return ApiResponse.<String>builder().result("User has been deleted").build();

	}

	@PutMapping("/{id}")
	ApiResponse<ProductResponse> updateProduct(@PathVariable String id, @RequestBody ProductRequest request) {
		return ApiResponse.<ProductResponse>builder().result(productService.updateProduct(id, request)).build();
	}

	@GetMapping("/filter")
	ApiResponse<Page<ProductResponse>> filterProduct(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "") String name,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDay,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDay,
			@RequestParam(required = false) String category,
			@RequestParam(defaultValue = "creationDate") String sortBy,
			@RequestParam(defaultValue = "DESC") String direction

	) {
		if (startDay == null) {
	        // Gán giá trị mặc định cho startDay (ngày 01/01/1990)
	        startDay = LocalDate.of(1990, 1, 1);
	    }
	    if (endDay == null) {
	        // Gán giá trị mặc định cho endDay là ngày hiện tại
	        endDay = LocalDate.now();
	    }

		
		return ApiResponse.<Page<ProductResponse>>builder()
				.result(productService.searchAndFilter(page, size, name, startDay, endDay, category, sortBy, direction))
				.build();
	}

}
