package springbootWeb2.com.hohaiha.app.controller.apiController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import springbootWeb2.com.hohaiha.app.dto.response.ApiResponse;
import springbootWeb2.com.hohaiha.app.entity.Cart;
import springbootWeb2.com.hohaiha.app.entity.Product;
import springbootWeb2.com.hohaiha.app.service.CartService;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {
	@Autowired
	private CartService cartService;
	
	@GetMapping("/{id}")
	ApiResponse<Cart> getCart(@PathVariable String id){
		return ApiResponse.<Cart>builder()
				.result(cartService.getCart(id))
				.build();
	}
	
	@PostMapping("/{cartId}")
	ApiResponse<Cart> addItem(@PathVariable String cartId, @RequestParam String productId){
		return ApiResponse.<Cart>builder()
				.result(cartService.addItem(cartId, productId))
				.build();
	}
	
	@DeleteMapping("/{cartId}")
	ApiResponse<Cart> removeItem(@PathVariable String cartId, @RequestParam String itemId){
		return ApiResponse.<Cart>builder()
				.result(cartService.removeItem(cartId, itemId))
				.build();
	}
	
	@PutMapping("/{cartId}")
	ApiResponse<Cart> updateQuantityItem(@PathVariable String cartId, @RequestParam String itemId,@RequestParam int quantity){
		return ApiResponse.<Cart>builder()
				.result(cartService.updateQuantityItem(cartId, itemId,quantity))
				.build();
	}
	
}
