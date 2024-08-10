package springbootWeb2.com.hohaiha.app.mapper;

import springbootWeb2.com.hohaiha.app.dto.request.CategoryRequest;
import springbootWeb2.com.hohaiha.app.dto.response.CategoryResponse;
import springbootWeb2.com.hohaiha.app.entity.Category;

public class CategoryMapperImpl implements CategoryMapper{

	@Override
	public Category toCategory(CategoryRequest request) {
		return  Category.builder()
				.name(request.getName())
				.descriptions(request.getDescriptions())
				.build();
	}

	@Override
	public CategoryResponse toCategoryResponse(Category category) {
		return CategoryResponse.builder()
				.name(category.getName())
				.descriptions(category.getDescriptions())
				.build();
	}

}
