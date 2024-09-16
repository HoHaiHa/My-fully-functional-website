package springbootWeb2.com.hohaiha.app.mapper;

import org.springframework.stereotype.Component;

import springbootWeb2.com.hohaiha.app.dto.request.ItemRequest;
import springbootWeb2.com.hohaiha.app.dto.response.ItemResponse;
import springbootWeb2.com.hohaiha.app.entity.Item;

@Component
public interface ItemMapper {
	public ItemResponse toItemResponse(Item item);
	public Item toItem(ItemRequest request);
}
