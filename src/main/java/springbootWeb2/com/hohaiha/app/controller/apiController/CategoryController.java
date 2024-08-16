 package springbootWeb2.com.hohaiha.app.controller.apiController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import springbootWeb2.com.hohaiha.app.dto.request.CategoryRequest;
import springbootWeb2.com.hohaiha.app.dto.response.ApiResponse;
import springbootWeb2.com.hohaiha.app.dto.response.CategoryResponse;
import springbootWeb2.com.hohaiha.app.service.CategoryService;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

		@Autowired
	    private CategoryService categoryService;

	    @PostMapping
	    ApiResponse<CategoryResponse> create(@RequestBody CategoryRequest request) {
	        return ApiResponse.<CategoryResponse>builder()
	                .result(categoryService.create(request))
	                .build();
	    }

	    @GetMapping
	    ApiResponse<List<CategoryResponse>> getAll() {
	        return ApiResponse.<List<CategoryResponse>>builder()
	                .result(categoryService.getAll())
	                .build();
	    }
	    
	    @GetMapping("/{name}")
	    ApiResponse<CategoryResponse> getCategory(@PathVariable String name) {
	        return ApiResponse.<CategoryResponse>builder()
	                .result(categoryService.getCategory(name))
	                .build();
	    }

	    @DeleteMapping("/{category}")
	    ApiResponse<Void> delete(@PathVariable String category) {
	        categoryService.delete(category);
	        return ApiResponse.<Void>builder().build();
	    }
	

}
