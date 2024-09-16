import baseUrl from "../config.js";


document.addEventListener('DOMContentLoaded', () => {
	const token = localStorage.getItem('token');
	if (token === null) {
		throw new Error('login error');
	}

	const listOrders = document.querySelector('#list-orders');
	const firstPageBtn = document.querySelector('#page_first');
	const previousPageBtn = document.querySelector('#page_previous');
	const nextPageBtn = document.querySelector('#page_next');
	const lastPageBtn = document.querySelector('#page_last');

	//thêm đường dẫn vào nút thêm 
	const addUserBtn = document.querySelector('#btn_add')
	addUserBtn.setAttribute('href', `${baseUrl}/admin/addOrders`)

	//xử lý hiển thị đơn hàng
	var resultFetch;
	let page = 0;
	let ordersApi = `${baseUrl}/orders?page=${page}`;
	const orderOptions = {
		method: 'GET',
		headers: {
			'Authorization': `Bearer ${token}`,
			'Content-Type': 'application/json',
		},
	};

	function start() {
		getOrders(renderOrders, getFetchResult);
	}

	start();

	function getOrders(callback1, callback2) {
		fetch(ordersApi, orderOptions)
			.then(response => {
				if (!response.ok) {
					throw new Error(' response was not ok');
				}
				return response.json();
			})
			.then(data => {
				callback2(data);
				callback1(data);
			})
			.catch(error => {
				console.error('There was a problem with the fetch operation:', error);
				alert('Đã có lỗi xảy ra trong quá trình lấy dữ liệu.');
			});
	}

	function renderOrders(orders) {
		const listOrdersResult = orders.result.content;

		listOrders.innerHTML = `		
		<tr>
			<th scope="col">STT</th>
			<th scope="col">Khách hàng</th>
			<th scope="col">Tổng số lượng</th>
			<th scope="col">Tổng giá trị</th>
			<th scope="col">Trạng thái</th>
		</tr>`

		let index = orders.result.number * orders.result.size;

		listOrdersResult.forEach((order, i) => {
			const row = document.createElement('tr');
			row.setAttribute('data-order-id', order.id);
			row.setAttribute('id', 'orders-info');
			row.setAttribute('style', "cursor:pointer");
			row.innerHTML = `
                <th scope="row" style="padding-left: 10px;">${index + i + 1}</th>
                <td style="padding-left: 10px;">${order.user.phone}</td>
                <td style="padding-left: 10px;">${order.totalQuantity}</td>
                <td style="padding-left: 10px;">${order.finalTotalPrice}</td>
                <td style="padding-left: 10px;">${order.status}</td>
            `;
			listOrders.appendChild(row);

		});
	}


	function getFetchResult(orders) {
		resultFetch = JSON.stringify(orders);
	}




	//xử lý nút chuyển trang
	//láy giá trị trả về


	function getSearchResult(products) {
		searchResult = JSON.stringify(products);
	}
	
	var searchApi;

	let phone = $("#input-search").val().trim()
	let startDay = $("#input-startDay").val()
	let endDay = $("#input-endDay").val()

	let statusOption = $("#select-category").find('option:selected')
	let status = statusOption.data('status')

	var selectedOption = $('#select-sort').find('option:selected');
	var sortBy = selectedOption.data('sortby');
	var direction = selectedOption.data('direction');

	firstPageBtn.addEventListener('click', () => {
		/*if ($("#input-search").val().trim() == "" ) {
			page = 0;
			userApi = `${baseUrl}/products?page=${page}`;
			start();
		}
		else {*/
		page = 0;
		searchApi = `${baseUrl}/orders/filter?phone=${phone}&startDay=${startDay}&endDay=${endDay}&status=${status}&sortBy=${sortBy}&direction=${direction}`;
		search();
		//}

	});

	previousPageBtn.addEventListener('click', () => {
		/*if ($("#input-search").val().trim() == "") {
			const currentPage = JSON.parse(resultFetch)?.result?.number;
			if (currentPage <= 0) return; // Kiểm tra trang đầu tiên
			page = currentPage - 1;
			userApi = `${baseUrl}/products?page=${page}`;
			start();
		}
		else {*/
		const currentPage = JSON.parse(searchResult)?.result?.number;
		if (currentPage <= 0) return; // Kiểm tra trang đầu tiên
		page = currentPage - 1;
		searchApi = `${baseUrl}/orders/filter?phone=${phone}&startDay=${startDay}&endDay=${endDay}&status=${status}&sortBy=${sortBy}&direction=${direction}`;
		search();
		//}
	});

	nextPageBtn.addEventListener('click', () => {
		/*if ($("#input-search").val().trim() == "") {
			const currentPage = JSON.parse(resultFetch)?.result?.number;
			const totalPages = JSON.parse(resultFetch)?.page?.totalPages;
			if (currentPage >= totalPages - 1) return; // Kiểm tra trang cuối cùng
			page = currentPage + 1;
			userApi = `${baseUrl}/products?page=${page}`;
			start();
		}
		else {*/
		const currentPage = JSON.parse(searchResult)?.result?.number;
		const totalPages = JSON.parse(searchResult)?.page?.totalPages;
		if (currentPage >= totalPages - 1) return; // Kiểm tra trang cuối cùng
		page = currentPage + 1;
		searchApi =`${baseUrl}/orders/filter?phone=${phone}&startDay=${startDay}&endDay=${endDay}&status=${status}&sortBy=${sortBy}&direction=${direction}`
		search();
		//}

	});

	lastPageBtn.addEventListener('click', () => {
		/*if ($("#input-search").val().trim() == "") {
			page = JSON.parse(resultFetch)?.result?.totalPages - 1;
			userApi = `${baseUrl}/products?page=${page}`;
			start();
		}
		else {*/
		page = JSON.parse(searchResult)?.result?.totalPages - 1;
		searchApi=`${baseUrl}/orders/filter?phone=${phone}&startDay=${startDay}&endDay=${endDay}&status=${status}&sortBy=${sortBy}&direction=${direction}`
		search();
		//}

	});

	/*//xử lý nút xoá
	function deleteOrders(orderId) {
		let deleteApi = `${baseUrl}/orders/${ordersId}`

		let deleteOptions = {
			method: 'DELETE',
			headers: {
				'Authorization': `Bearer ${token}`,
				'Content-Type': 'application/json'
			}

		}

		fetch(deleteApi, deleteOptions)
			.then(response => {
				if (!response) alert('delete fetch response was not ok')
			})
			.catch(error => {
				consol.log(error)
			})

	}

	$('#list-orders').on('click', '.orders_info', function(event) {
		// Xử lý khi nhấn nút xóa
		if ($(event.target).is('.btn-delete')) {
			let userConfirm = confirm('Xoá sản phẩm này')
			if (userConfirm) {
				const productId = $(this).data('product-id');
				deleteProduct(productId);
				$(this).fadeOut();
			}
		}
		// Xử lý khi nhấn vào sản phẩm để xem chi tiết
		else {
			const productId = $(this).data('product-id');
			window.location.href = `${baseUrl}/admin/detailProduct?productid=${productId}`
		}
	});

*/
	//xử lý ô tìm kiếm

	$("#input-search").keyup(search)
	$("#input-startDay").change(search)
	$("#input-endDay").change(search)
	$("#select-sort").change(search)
	$("#select-status").change(search)
	var searchResult;
	
	

	function search() {

		let phone = $("#input-search").val().trim()
		let startDay = $("#input-startDay").val()
		let endDay = $("#input-endDay").val()

		let statusOption = $("#select-status").find('option:selected')
		let status = statusOption.data('status')

		var selectedOption = $('#select-sort').find('option:selected');
		var sortBy = selectedOption.data('sortby');
		var direction = selectedOption.data('direction');

		var searchApi = `${baseUrl}/orders/filter?phone=${phone}&startDay=${startDay}&endDay=${endDay}&status=${status}&sortBy=${sortBy}&direction=${direction}`

		console.log(searchApi)

		var searchOptions = {
			method: 'GET',
			headers: {
				'Authorization': `Bearer ${token}`,
				'Content-Type': 'application/json',
			},
		}

		searchOrders(searchApi, searchOptions, renderOrders, getSearchResult);
	}

	function searchOrders(api, options, callback1, callback2) {
		fetch(api, options)
			.then(response => {
				if (!response.ok) throw new Error('fetch error')
				else return response.json()
			})
			.then(callback1)
			.then(callback2)
			.catch(error => {
				alert('có lỗi: ' + error)
			})
	}

	function renderOrders(orders) {
		const listOrdersResult = orders.result.content;

		listOrders.innerHTML = `		
		<tr>
			<th scope="col">STT</th>
			<th scope="col">Khách hàng</th>
			<th scope="col">Tổng số lượng</th>
			<th scope="col">Tổng giá trị</th>
			<th scope="col">Trạng thái</th>
		</tr>`

		let index = orders.result.number * orders.result.size;

		listOrdersResult.forEach((order, i) => {
			const row = document.createElement('tr');
			row.setAttribute('data-order-id', order.id);
			row.setAttribute('id', 'orders-info');
			row.setAttribute('style', "cursor:pointer");
			row.innerHTML = `
                <th scope="row" style="padding-left: 10px;">${index + i + 1}</th>
                <td style="padding-left: 10px;">${order.user.phone}</td>
                <td style="padding-left: 10px;">${order.totalQuantity}</td>
                <td style="padding-left: 10px;">${order.finalTotalPrice}</td>
                <td style="padding-left: 10px;">${order.status}</td>
            `;
			listOrders.appendChild(row);

		});
	}

	//xử lý chi tiết orders
	$(document).on('click','#orders-info',function(){
		let orderId = $(this).data('order-id')
		window.location.href =`${baseUrl}/admin/detailOrder?orderId=${orderId}`
	})

});




