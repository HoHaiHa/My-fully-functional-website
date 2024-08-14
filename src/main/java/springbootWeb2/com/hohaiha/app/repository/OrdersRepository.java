package springbootWeb2.com.hohaiha.app.repository;

import java.sql.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import springbootWeb2.com.hohaiha.app.dto.response.OrdersResponse;
import springbootWeb2.com.hohaiha.app.entity.Orders;

public interface OrdersRepository extends JpaRepository<Orders, String> {

	Page<Orders> findByStatus(String status, Pageable pageable);

	@Query("SELECT o FROM Orders o WHERE CONCAT(o.user.phone) LIKE %:phone%")
	Page<Orders> findOrdersByUserPhoneContaining(@Param("phone")String phone,Pageable pageable);

	Optional<OrdersResponse> findByShippingCode(String shippingCode);
	
	 @Query("SELECT o FROM Orders o WHERE o.creationDate BETWEEN :startDate AND :endDate")
	 Page<Orders> findOrdersByCreationDateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate,Pageable pageable);
	
}
