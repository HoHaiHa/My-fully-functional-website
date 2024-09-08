package springbootWeb2.com.hohaiha.app.service;


import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import springbootWeb2.com.hohaiha.app.dto.request.OrdersRequest;
import springbootWeb2.com.hohaiha.app.dto.response.OrdersResponse;

@Service 
public interface OrdersService {
	public OrdersResponse createOrders(OrdersRequest request);
	public OrdersResponse updateOrders(String id,OrdersRequest request );
	public OrdersResponse setShippingCode(String orderId, String shippingCode);
	public void deleteOrders(String id);
	public Page<OrdersResponse> getOrders(int page, int size);
	public OrdersResponse getOrder(String id);
	public OrdersResponse getOrderByShippingCode(String code);
	public Page<OrdersResponse> filterOrders(String phone, LocalDate startDay, LocalDate endDay, String status,
			int page, int size, String sortBy, String direction) ;
}
