package springbootWeb2.com.hohaiha.app.controller.apiController;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import springbootWeb2.com.hohaiha.app.dto.request.OrdersRequest;
import springbootWeb2.com.hohaiha.app.dto.response.ApiResponse;
import springbootWeb2.com.hohaiha.app.dto.response.OrdersResponse;
import springbootWeb2.com.hohaiha.app.service.OrdersService;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {
	@Autowired
	private OrdersService ordersService;
	
	@PostMapping
	ApiResponse<OrdersResponse> createOrders(@RequestBody OrdersRequest request){
		return ApiResponse.<OrdersResponse>builder()
				.result(ordersService.createOrders(request))
				.build();
	}
	
	@GetMapping
	ApiResponse<Page<OrdersResponse>> getOrders(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size){
		return ApiResponse.<Page<OrdersResponse>>builder()
				.result(ordersService.getOrders(page, size))
				.build();
	}
	
	@PutMapping("/{id}")
	ApiResponse<OrdersResponse> updateOrders(@PathVariable String id,@RequestBody OrdersRequest request){
		return ApiResponse.<OrdersResponse>builder()
				.result(ordersService.updateOrders(id,request))
				.build();
	}
	
	@DeleteMapping("/{id}")
	ApiResponse deleteOrders(@PathVariable String id){
		ordersService.deleteOrders(id);
		return new ApiResponse();
	}
	
	@GetMapping("/{id}")
	ApiResponse<OrdersResponse> getOrder(@PathVariable String id){
		return ApiResponse.<OrdersResponse>builder()
				.result(ordersService.getOrder(id))
				.build();
	}
	
	@GetMapping("/searchphone/{phone}")
	ApiResponse<Page<OrdersResponse>> searchOrdersPhone(@PathVariable String phone,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size){
		return ApiResponse.<Page<OrdersResponse>>builder()
				.result(ordersService.searchOrdersPhone(phone,page, size))
				.build();
	}
	
	@GetMapping("/searchdate")
	ApiResponse<Page<OrdersResponse>> searchOrdersCreationDate(@RequestParam Date startDate,@RequestParam Date endDate,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size){
		return ApiResponse.<Page<OrdersResponse>>builder()
				.result(ordersService.searchOrdersCreationDate(startDate, endDate,page, size))
				.build();
	}
	
	@GetMapping("/searchstatus/{status}")
	ApiResponse<Page<OrdersResponse>> searchOrdersStatus(@PathVariable String status,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size){
		return ApiResponse.<Page<OrdersResponse>>builder()
				.result(ordersService.searchOrdersStatus(status,page, size))
				.build();
	}
	
	@GetMapping("/searchcode/{code}")
	ApiResponse<OrdersResponse> getOrderByShippingCode(@PathVariable String code){
		return ApiResponse.<OrdersResponse>builder()
				.result(ordersService.getOrderByShippingCode(code))
				.build();
	}
	
	
}
