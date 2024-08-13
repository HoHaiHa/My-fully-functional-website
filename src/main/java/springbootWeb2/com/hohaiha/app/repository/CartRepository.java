package springbootWeb2.com.hohaiha.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import springbootWeb2.com.hohaiha.app.entity.Cart;

public interface CartRepository  extends JpaRepository<Cart,String>{

}
