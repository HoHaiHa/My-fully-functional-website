package springbootWeb2.com.hohaiha.app.service;

import org.springframework.beans.factory.annotation.Autowired;

import springbootWeb2.com.hohaiha.app.entity.Item;
import springbootWeb2.com.hohaiha.app.entity.Product;
import springbootWeb2.com.hohaiha.app.exception.AppException;
import springbootWeb2.com.hohaiha.app.exception.ErrorCode;
import springbootWeb2.com.hohaiha.app.repository.ItemRepository;

public class ItemServiceImpl implements ItemService{
	@Autowired
	private ItemRepository itemRepository;
	
	public Item getItem(String id) {
		return itemRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.ITEM_NOT_EXISTED));
	}
	
	public Item createItem(long quantity,Product product) {
		Item item = new Item();
		
		item.setProduct(product);
		item.setQuantity(quantity);
		
		itemRepository.save(item);
		
		return item;
	}
	
	public void deleteItem(String id) {
		itemRepository.deleteById(id);
	}
	
	public Item updateItem(String id, Item item) {
		Item currentItem = itemRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.ITEM_NOT_EXISTED));
		
		currentItem.setProduct(item.getProduct());
		currentItem.setQuantity(item.getQuantity());
		
		itemRepository.save(currentItem);
		
		return currentItem;
	}
}
