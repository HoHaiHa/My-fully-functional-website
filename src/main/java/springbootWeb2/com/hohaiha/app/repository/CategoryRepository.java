package springbootWeb2.com.hohaiha.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springbootWeb2.com.hohaiha.app.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String>{

}
