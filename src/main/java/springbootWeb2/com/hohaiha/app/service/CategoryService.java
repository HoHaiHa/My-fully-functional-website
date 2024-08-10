package springbootWeb2.com.hohaiha.app.service;

import java.util.List;

import springbootWeb2.com.hohaiha.app.dto.request.CategoryRequest;
import springbootWeb2.com.hohaiha.app.dto.response.CategoryResponse;


public interface CategoryService {
	public CategoryResponse create(CategoryRequest request);

	public List<CategoryResponse> getAll();

	public void delete(String Category);

}
