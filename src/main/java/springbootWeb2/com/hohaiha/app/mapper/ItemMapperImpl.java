package springbootWeb2.com.hohaiha.app.mapper;

import org.springframework.stereotype.Component;

import springbootWeb2.com.hohaiha.app.dto.request.ItemRequest;
import springbootWeb2.com.hohaiha.app.dto.response.ItemResponse;
import springbootWeb2.com.hohaiha.app.entity.Item;

@Component
public class ItemMapperImpl implements ItemMapper{

	@Override
	public ItemResponse toItemResponse(Item item) {
		return ItemResponse.builder()
				.id(item.getId())
				.product(item.getProduct())
				.quantity(item.getQuantity())
				.build();
	}

	@Override
	public Item toItem(ItemRequest request) {
		return Item.builder()
				.quantity(request.getQuantity())
				.build();
	}

}
