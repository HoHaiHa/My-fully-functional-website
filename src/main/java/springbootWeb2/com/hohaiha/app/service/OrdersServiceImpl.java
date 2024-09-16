package springbootWeb2.com.hohaiha.app.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import springbootWeb2.com.hohaiha.app.dto.request.OrdersRequest;
import springbootWeb2.com.hohaiha.app.dto.response.OrdersResponse;
import springbootWeb2.com.hohaiha.app.entity.Item;
import springbootWeb2.com.hohaiha.app.entity.User;
import springbootWeb2.com.hohaiha.app.entity.Orders;
import springbootWeb2.com.hohaiha.app.exception.AppException;
import springbootWeb2.com.hohaiha.app.exception.ErrorCode;
import springbootWeb2.com.hohaiha.app.mapper.OrdersMapper;
import springbootWeb2.com.hohaiha.app.repository.ItemRepository;
import springbootWeb2.com.hohaiha.app.repository.OrdersRepository;
import springbootWeb2.com.hohaiha.app.repository.UserRepository;

@Component
public class OrdersServiceImpl implements OrdersService {
	@Autowired
	private OrdersRepository ordersRepository;
	@Autowired
	private OrdersMapper ordersMapper;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public OrdersResponse createOrders(OrdersRequest request) {
		Orders orders = ordersMapper.toOrders(request);

		User user = userRepository.findById(request.getUserId())
				.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
		orders.setUser(user);

		List<String> itemsRequest = request.getItem();
		List<Item> itemsResult = new ArrayList<Item>();

		for (String item : itemsRequest) {
			Item itemResult = itemRepository.findById(item)
					.orElseThrow(() -> new AppException(ErrorCode.ITEM_NOT_EXISTED));
			itemsResult.add(itemResult);
		}

		orders.setItem(itemsResult);

		ordersRepository.save(orders);

		return ordersMapper.toOrdersResponse(orders);
	}

	@Override
	public OrdersResponse updateOrders(String id, OrdersRequest request) {
		Orders orders = ordersRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ORDERS_NOT_EXISTED));

		orders = ordersMapper.toOrders(request);

		User user = userRepository.findById(request.getUserId())
				.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
		orders.setUser(user);

		List<String> itemsRequest = request.getItem();

		List<Item> itemsResult = new ArrayList<Item>();

		for (String item : itemsRequest) {
			Item itemResult = itemRepository.findById(item)
					.orElseThrow(() -> new AppException(ErrorCode.ITEM_NOT_EXISTED));
			itemsResult.add(itemResult);
		}

		orders.setItem(itemsResult);

		ordersRepository.save(orders);

		return ordersMapper.toOrdersResponse(orders);
	}

	@Override
	public void deleteOrders(String id) {
		ordersRepository.existsById(id);
	}

	@Override
	public Page<OrdersResponse> getOrders(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return ordersRepository.findAll(pageable).map(ordersMapper::toOrdersResponse);
	}

	@Override
	public OrdersResponse getOrder(String id) {
		return ordersMapper.toOrdersResponse(
				ordersRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ORDERS_NOT_EXISTED)));
	}

	@Override
	public Page<OrdersResponse> filterOrders(String phone, LocalDate startDay, LocalDate endDay, String status,
			int page, int size, String sortBy, String direction) {

		Sort.Direction directionSort = Sort.Direction.fromString(direction);
		Sort sortOrder = Sort.by(directionSort, sortBy);

		Pageable pageable = PageRequest.of(page, size, sortOrder);
		return ordersRepository.filterOrders(pageable, phone, startDay, endDay, status)
				.map(ordersMapper::toOrdersResponse);
	}

	@Override
	public OrdersResponse getOrderByShippingCode(String code) {
		return ordersMapper.toOrdersResponse(ordersRepository.findByShippingCode(code)
				.orElseThrow(() -> new AppException(ErrorCode.ORDERS_NOT_EXISTED)));
	}

	@Override
	public OrdersResponse updateShippingCode(String orderId, String shippingCode) {
		Orders orders = ordersRepository.findById(orderId)
				.orElseThrow(() -> new AppException(ErrorCode.ORDERS_NOT_EXISTED));
		orders.setShippingCode(shippingCode);
		ordersRepository.save(orders);
		return ordersMapper.toOrdersResponse(orders);
	}

	@Override
	public OrdersResponse updateStatus(String id, String status) {
		Orders orders = ordersRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ORDERS_NOT_EXISTED));
		orders.setStatus(status);
		ordersRepository.save(orders);
		return ordersMapper.toOrdersResponse(orders);
	}

	@Override
	public OrdersResponse updateStaff(String id, String staffId) {
		Orders orders = ordersRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ORDERS_NOT_EXISTED));
		orders.setStaffId(staffId);
		ordersRepository.save(orders);
		return ordersMapper.toOrdersResponse(orders);
	}

}
