package springbootWeb2.com.hohaiha.app.mapper;

import org.springframework.stereotype.Component;

import springbootWeb2.com.hohaiha.app.dto.request.CategoryRequest;
import springbootWeb2.com.hohaiha.app.dto.response.CategoryResponse;
import springbootWeb2.com.hohaiha.app.entity.Category;

@Component
public interface CategoryMapper {
	public Category toCategory(CategoryRequest request);
	
	public CategoryResponse toCategoryResponse (Category category);

}
