package springbootWeb2.com.hohaiha.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import springbootWeb2.com.hohaiha.app.entity.Cart;
import springbootWeb2.com.hohaiha.app.entity.Item;
import springbootWeb2.com.hohaiha.app.exception.AppException;
import springbootWeb2.com.hohaiha.app.exception.ErrorCode;
import springbootWeb2.com.hohaiha.app.repository.CartRepository;
import springbootWeb2.com.hohaiha.app.repository.ItemRepository;

@Component
public class CartServiceImpl implements CartService{
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemRepository itemRepository;
	
	public Cart getCart(String id){
		return cartRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CART_NOT_EXISTED));
	}
	
	public Cart addItem(String CartId, String productId) {
		Item item = itemService.createItem(1, productId);
		
		Cart cart = cartRepository.findById(CartId).orElseThrow(() -> new AppException(ErrorCode.CART_NOT_EXISTED));
		
		List<Item> items = cart.getItem();
		
		items.add(item);
		
		cart.setItem(items);
		
		cartRepository.save(cart);
		
		return cart;
	}
	
	public Cart removeItem(String CartId, String ItemId) {
		Item item = itemRepository.findById(ItemId).orElseThrow(() -> new AppException(ErrorCode.ITEM_NOT_EXISTED));
		
		Cart cart = cartRepository.findById(CartId).orElseThrow(() -> new AppException(ErrorCode.CART_NOT_EXISTED));
		
		List<Item> items = cart.getItem();
		
		items.remove(item);
		
		cart.setItem(items);
		
		cartRepository.save(cart);
		
		itemService.deleteItem(ItemId);
		
		return cart;
	}
	
	public Cart updateQuantityItem(String CartId, String ItemId, int quantity) {
		Cart cart = cartRepository.findById(CartId).orElseThrow(() -> new AppException(ErrorCode.CART_NOT_EXISTED));
		
		itemService.updateQuantityItem(ItemId, quantity);
		
		return cart;
	}
	
}
