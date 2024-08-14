package springbootWeb2.com.hohaiha.app.mapper;

import org.springframework.stereotype.Component;

import springbootWeb2.com.hohaiha.app.dto.request.OrdersRequest;
import springbootWeb2.com.hohaiha.app.dto.response.OrdersResponse;
import springbootWeb2.com.hohaiha.app.entity.Orders;

@Component
public interface OrdersMapper {
	public Orders toOrders(OrdersRequest request);
	public OrdersResponse toOrdersResponse(Orders orders);
}
