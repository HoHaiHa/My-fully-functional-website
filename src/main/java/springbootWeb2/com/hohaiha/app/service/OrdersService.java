package springbootWeb2.com.hohaiha.app.service;

import java.sql.Date;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import springbootWeb2.com.hohaiha.app.dto.request.OrdersRequest;
import springbootWeb2.com.hohaiha.app.dto.response.OrdersResponse;

@Service 
public interface OrdersService {
	public OrdersResponse createOrders(OrdersRequest request);
	public OrdersResponse updateOrders(String id,OrdersRequest request );
	public void deleteOrders(String id);
	public Page<OrdersResponse> getOrders(int page, int size);
	public OrdersResponse getOrder(String id);
	public Page<OrdersResponse> searchOrdersStatus(String status,int page, int size);
	public Page<OrdersResponse> searchOrdersPhone(String phone, int page, int size);
	public Page<OrdersResponse> searchOrdersCreationDate(Date startDate, Date endDate, int page, int size);
	public OrdersResponse getOrderByShippingCode(String code);
}
