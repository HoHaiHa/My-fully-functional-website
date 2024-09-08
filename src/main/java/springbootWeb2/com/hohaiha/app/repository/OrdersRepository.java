package springbootWeb2.com.hohaiha.app.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import springbootWeb2.com.hohaiha.app.entity.Orders;

public interface OrdersRepository extends JpaRepository<Orders, String> {
	Optional<Orders> findByShippingCode(String shippingCode);

	@Query("SELECT o FROM Orders o WHERE (:phone IS NULL OR o.user.phone LIKE %:phone%) AND o.creationDate BETWEEN :startDay AND :endDay AND (:status IS NULL OR o.status LIKE %:status%)")
	Page<Orders> filterOrders(
	        Pageable pageable,
	        @Param("phone") String phone,
	        @Param("startDay") LocalDate startDay,
	        @Param("endDay") LocalDate endDay,
	        @Param("status") String status
	);

}
