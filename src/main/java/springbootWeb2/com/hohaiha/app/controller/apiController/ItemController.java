package springbootWeb2.com.hohaiha.app.controller.apiController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import springbootWeb2.com.hohaiha.app.dto.request.ItemRequest;
import springbootWeb2.com.hohaiha.app.dto.response.ApiResponse;
import springbootWeb2.com.hohaiha.app.dto.response.ItemResponse;
import springbootWeb2.com.hohaiha.app.service.ItemService;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@GetMapping
	ApiResponse<List<ItemResponse>> getItems(){
		return ApiResponse.<List<ItemResponse>>builder()
				.result(itemService.getItems())
				.build();
	}
	
	@GetMapping("/{id}")
	ApiResponse<ItemResponse> getItem(@PathVariable String id){
		return ApiResponse.<ItemResponse>builder()
				.result(itemService.getItem(id))
				.build();
	}
	
	@PostMapping
	ApiResponse<ItemResponse> createItem(@RequestBody ItemRequest request){
		return ApiResponse.<ItemResponse>builder()
				.result(itemService.createItem(request))
				.build();
	}
	@DeleteMapping("/{id}")
	ApiResponse deleteItem(@PathVariable String id) {
		return new ApiResponse();
	}
	
	@PatchMapping("/{id}")
	ApiResponse<ItemResponse> updateItem(@PathVariable String id,@RequestParam(defaultValue = "1") String quantity){
		return ApiResponse.<ItemResponse>builder()
				.result(itemService.updateItem(id, Integer.parseInt(quantity)))
				.build();
	}
}
