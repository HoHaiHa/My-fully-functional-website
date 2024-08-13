package springbootWeb2.com.hohaiha.app.service;

import org.springframework.stereotype.Service;

import springbootWeb2.com.hohaiha.app.entity.Item;
import springbootWeb2.com.hohaiha.app.entity.Product;
@Service
public interface ItemService {
	public Item getItem(String id);
	public Item createItem(long quantity,String productId);
	public void deleteItem(String id);
	public Item updateItem(String id, Item item) ;
	public Item updateQuantityItem(String id, int quantity);
}
