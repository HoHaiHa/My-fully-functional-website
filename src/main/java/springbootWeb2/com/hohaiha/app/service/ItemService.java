package springbootWeb2.com.hohaiha.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import springbootWeb2.com.hohaiha.app.dto.request.ItemRequest;
import springbootWeb2.com.hohaiha.app.dto.response.ItemResponse;
import springbootWeb2.com.hohaiha.app.entity.Item;
@Service
public interface ItemService {
	public ItemResponse getItem(String id);
	public ItemResponse createItem(ItemRequest request);
	public void deleteItem(String id);
	public ItemResponse updateItem(String id, int Quanity) ;
	public List<ItemResponse> getItems();
}
