package springbootWeb2.com.hohaiha.app.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import springbootWeb2.com.hohaiha.app.dto.request.CategoryRequest;
import springbootWeb2.com.hohaiha.app.dto.response.CategoryResponse;

public class CategoryServiceImpl implements CategoryService{

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public CategoryResponse create(CategoryRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<CategoryResponse> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void delete(String Category) {
		// TODO Auto-generated method stub
		
	}

}
