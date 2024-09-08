package springbootWeb2.com.hohaiha.app.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import springbootWeb2.com.hohaiha.app.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String>{
	@Query("SELECT p FROM Product p WHERE (:name IS NULL OR p.name like %:name%) AND p.creationDate BETWEEN :startDay AND :endDay AND (:category IS NULL OR p.category.name like %:category%)")
	Page<Product> searchAndFilter(Pageable pageable,
			@Param("name") String name, 
	        @Param("startDay") LocalDate startDay, 
	        @Param("endDay") LocalDate endDay, 
	        @Param("category") String category
			);
}
