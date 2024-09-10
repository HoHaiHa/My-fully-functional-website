package springbootWeb2.com.hohaiha.app.mapper;

import org.springframework.stereotype.Component;

import springbootWeb2.com.hohaiha.app.dto.request.OrdersRequest;
import springbootWeb2.com.hohaiha.app.dto.response.OrdersResponse;
import springbootWeb2.com.hohaiha.app.entity.Orders;

@Component
public class OrdersMapperImpl implements OrdersMapper{
	public Orders toOrders(OrdersRequest request) {
		return Orders.builder()
				.totalQuantity(request.getTotalQuantity())
				.discount(request.getDiscount())
				.creationDate(request.getCreationDate())
				.totalPrice(request.getTotalPrice())
				.Tax(request.getTax())
				.finalTotalPrice(request.getFinalTotalPrice())
				.city(request.getCity())
				.district(request.getDistrict())
				.ward(request.getWard())
				.numberAndStreet(request.getNumberAndStreet())
				.shippingCode(request.getShippingCode())
				.notes(request.getNotes())
				.paymentMethod(request.getPaymentMethod())
				.status(request.getStatus())
				.build();
	}
	
	public OrdersResponse toOrdersResponse(Orders orders) {
		return OrdersResponse.builder()
				.id(orders.getId())
				.user(orders.getUser())
				.totalQuantity(orders.getTotalQuantity())
				.discount(orders.getDiscount())
				.creationDate(orders.getCreationDate())
				.totalPrice(orders.getTotalPrice())
				.Tax(orders.getTax())
				.finalTotalPrice(orders.getFinalTotalPrice())
				.city(orders.getCity())
				.district(orders.getDistrict())
				.ward(orders.getWard())
				.numberAndStreet(orders.getNumberAndStreet())
				.shippingCode(orders.getShippingCode())
				.notes(orders.getNotes())
				.paymentMethod(orders.getPaymentMethod())
				.status(orders.getStatus())
				.item(orders.getItem())
				.build();
	}
}
