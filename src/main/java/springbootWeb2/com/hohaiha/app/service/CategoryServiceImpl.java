package springbootWeb2.com.hohaiha.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import springbootWeb2.com.hohaiha.app.dto.request.CategoryRequest;
import springbootWeb2.com.hohaiha.app.dto.response.CategoryResponse;
import springbootWeb2.com.hohaiha.app.entity.Category;
import springbootWeb2.com.hohaiha.app.mapper.CategoryMapper;
import springbootWeb2.com.hohaiha.app.repository.CategoryRepository;

@Component
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private CategoryMapper categoryMapper;
	

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public CategoryResponse create(CategoryRequest request) {
		Category category = categoryMapper.toCategory(request);
		category = categoryRepository.save(category);
		return categoryMapper.toCategoryResponse(category);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<CategoryResponse> getAll() {
		var categories= categoryRepository.findAll();
		return categories.stream().map(category -> categoryMapper.toCategoryResponse(category)).toList();
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void delete(String Category) {
		categoryRepository.deleteById(Category);
	}

}
