package springbootWeb2.com.hohaiha.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import springbootWeb2.com.hohaiha.app.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

	Page<User> findAll(Pageable pageable);
	
	@Query("SELECT u FROM User u WHERE CAST(u.phone AS string) LIKE CONCAT('%', :keyword, '%')")
	List<User> findByPhone(@Param("keyword") String keyword);
	
	@Query("SELECT u FROM User u WHERE u.name LIKE CONCAT('%', :keyword, '%')")
	List<User> findByName(@Param("keyword") String keyword);
	
	@Query("SELECT u FROM User u WHERE u.email LIKE CONCAT('%', :keyword, '%')")
	List<User> findByEmail(@Param("keyword") String keyword);
}
