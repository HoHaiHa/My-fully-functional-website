package springbootWeb2.com.hohaiha.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import springbootWeb2.com.hohaiha.app.dto.request.CategoryRequest;
import springbootWeb2.com.hohaiha.app.dto.response.CategoryResponse;

@Service
public interface CategoryService {
	public CategoryResponse create(CategoryRequest request);
	
	public CategoryResponse getCategory(String name);

	public List<CategoryResponse> getAll();

	public void delete(String Category);

}
