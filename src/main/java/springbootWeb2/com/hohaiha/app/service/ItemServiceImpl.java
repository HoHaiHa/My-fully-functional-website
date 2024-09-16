package springbootWeb2.com.hohaiha.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import springbootWeb2.com.hohaiha.app.dto.request.ItemRequest;
import springbootWeb2.com.hohaiha.app.dto.response.ItemResponse;
import springbootWeb2.com.hohaiha.app.entity.Item;
import springbootWeb2.com.hohaiha.app.entity.Product;
import springbootWeb2.com.hohaiha.app.exception.AppException;
import springbootWeb2.com.hohaiha.app.exception.ErrorCode;
import springbootWeb2.com.hohaiha.app.mapper.ItemMapper;
import springbootWeb2.com.hohaiha.app.repository.ItemRepository;
import springbootWeb2.com.hohaiha.app.repository.ProductRepository;

@Component
public class ItemServiceImpl implements ItemService{
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ItemMapper itemMapper;
	
	public List<ItemResponse> getItems(){
		return itemRepository.findAll().stream().map(itemMapper::toItemResponse).toList();
	}
	
	public ItemResponse getItem(String id) {
		return itemMapper.toItemResponse(itemRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.ITEM_NOT_EXISTED)));
	}
	
	public ItemResponse createItem(ItemRequest request) {
		Item item = itemMapper.toItem(request);
		
		Product product = productRepository.findById(request.getProductId()).orElseThrow(()-> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
		
		item.setProduct(product);
		item.setQuantity(request.getQuantity());
		
		itemRepository.save(item);
		
		return itemMapper.toItemResponse(item);
	}
	
	public void deleteItem(String id) {
		itemRepository.deleteById(id);
	}
	

	
	public ItemResponse updateItem(String id, int Quanity) {
		Item currentItem = itemRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.ITEM_NOT_EXISTED));
		
		currentItem.setQuantity(Quanity);
		
		itemRepository.save(currentItem);
		
		return itemMapper.toItemResponse(currentItem);
	}

	
}
