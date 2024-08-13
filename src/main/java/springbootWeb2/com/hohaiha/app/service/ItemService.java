package springbootWeb2.com.hohaiha.app.service;

import springbootWeb2.com.hohaiha.app.entity.Item;
import springbootWeb2.com.hohaiha.app.entity.Product;

public interface ItemService {
	public Item getItem(String id);
	public Item createItem(long quantity,Product product);
	public void deleteItem(String id);
	public Item updateItem(String id, Item item) ;
}
