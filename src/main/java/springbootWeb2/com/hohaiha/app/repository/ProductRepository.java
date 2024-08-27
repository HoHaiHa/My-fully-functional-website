package springbootWeb2.com.hohaiha.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import springbootWeb2.com.hohaiha.app.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String>{
	Page<Product> searchAndFilter(String name, Pageable pageable);
}
