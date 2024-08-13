package springbootWeb2.com.hohaiha.app.service;


import org.springframework.beans.factory.annotation.Autowired;

import springbootWeb2.com.hohaiha.app.repository.CartRepository;

public class CartServiceImpl implements CartService{
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private ItemService itemService;
	
	public List<Item> getItem(){
		List<Item> = cartRepository.findAllItem();
	}

}
