package springbootWeb2.com.hohaiha.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import springbootWeb2.com.hohaiha.app.entity.Item;
import springbootWeb2.com.hohaiha.app.entity.Product;
import springbootWeb2.com.hohaiha.app.exception.AppException;
import springbootWeb2.com.hohaiha.app.exception.ErrorCode;
import springbootWeb2.com.hohaiha.app.repository.ItemRepository;
import springbootWeb2.com.hohaiha.app.repository.ProductRepository;

@Component
public class ItemServiceImpl implements ItemService{
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private ProductRepository productRepository;
	
	public Item getItem(String id) {
		return itemRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.ITEM_NOT_EXISTED));
	}
	
	public Item createItem(long quantity,String productId) {
		Item item = new Item();
		
		Product product = productRepository.findById(productId).orElseThrow(()-> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
		
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
	
	public Item updateQuantityItem(String id, int quantity) {
		Item currentItem = itemRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.ITEM_NOT_EXISTED));
		
		currentItem.setQuantity(quantity);
		
		itemRepository.save(currentItem);
		
		return currentItem;
	}

}
