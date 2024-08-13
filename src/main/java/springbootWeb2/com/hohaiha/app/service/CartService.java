package springbootWeb2.com.hohaiha.app.service;

import org.springframework.stereotype.Service;

import springbootWeb2.com.hohaiha.app.entity.Cart;
import springbootWeb2.com.hohaiha.app.entity.Product;

@Service
public interface CartService {

	public Cart getCart(String id);
	public Cart addItem(String CartId, String productId);
	public Cart removeItem(String CartId, String ItemId);
	public Cart updateQuantityItem(String CartId, String ItemId, int quantity) ;
}
