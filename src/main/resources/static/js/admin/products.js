import baseUrl from "../config.js";


document.addEventListener('DOMContentLoaded', () => {
	const token = localStorage.getItem('token');
	if (token === null) {
		throw new Error('login error');
	}

	const listProducts = document.querySelector('#list-products');
	const firstPageBtn = document.querySelector('#page_first');
	const previousPageBtn = document.querySelector('#page_previous');
	const nextPageBtn = document.querySelector('#page_next');
	const lastPageBtn = document.querySelector('#page_last');

	//thêm đường dẫn vào nút thêm 
	const addUserBtn = document.querySelector('#btn_add')
	addUserBtn.setAttribute('href', `${baseUrl}/admin/addProduct`)

	let page = 0;
	let userApi = `${baseUrl}/products?page=${page}`;
	let resultFetch = '';
	let productId;

	const options = {
		method: 'GET',
		headers: {
			'Authorization': `Bearer ${token}`,
			'Content-Type': 'application/json',
		},
	};

	function start() {
		getProducts(renderProducts, getFetchResult);
	}

	start();

	function getProducts(callback1, callback2) {
		fetch(userApi, options)
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

	function renderProducts(products) {
		const listProductsResult = products.result.content;

		listProducts.innerHTML = `		
		<tr>
			<th scope="col">STT</th>
			<th scope="col">Ảnh</th>
			<th scope="col">Tên</th>
			<th scope="col">Giá</th>
			<th scope="col">Hãng</th>
			<th scope="col">Số lượng</th>
		</tr>`

		let index = products.result.number * products.result.size;

		listProductsResult.forEach((product, i) => {
			const row = document.createElement('tr');
			row.setAttribute('data-product-id', product.id);
			row.classList.add('product_info');
			row.setAttribute('style', "cursor:pointer");
			row.innerHTML = `
                <th scope="row" style="padding-left: 10px;">${index + i + 1}</th>
                <td style="padding-left: 10px;">
				<div style="
			        width: 100px;
			        height: 100px; /* Hoặc chiều cao mong muốn */
			        background-image: url(${baseUrl}${product.img});
			        background-size: cover;
			        background-position: center;
			        background-repeat: no-repeat;
			    "></div>
				</td>
                <td style="padding-left: 10px;">${product.name}</td>
                <td style="padding-left: 10px;">${product.price}</td>
                <td style="padding-left: 10px;">${product.brand}</td>
                <td style="padding-left: 10px;">${product.quantity}</td>
            `;
			listProducts.appendChild(row);
		});
	}

	//xử lý nút chuyển trang
	//láy giá trị trả về

	function getFetchResult(products) {
		resultFetch = JSON.stringify(products);
	}

	firstPageBtn.addEventListener('click', () => {
		page = 0;
		userApi = `${baseUrl}/products?page=${page}`;
		start();
	});

	previousPageBtn.addEventListener('click', () => {
		const currentPage = JSON.parse(resultFetch)?.result?.number;
		if (currentPage <= 0) return; // Kiểm tra trang đầu tiên
		page = currentPage - 1;
		userApi = `${baseUrl}/products?page=${page}`;
		start();
	});

	nextPageBtn.addEventListener('click', () => {
		const currentPage = JSON.parse(resultFetch)?.result?.number;
		const totalPages = JSON.parse(resultFetch)?.page?.totalPages;
		if (currentPage >= totalPages - 1) return; // Kiểm tra trang cuối cùng
		page = currentPage + 1;
		userApi = `${baseUrl}/products?page=${page}`;
		start();
	});

	lastPageBtn.addEventListener('click', () => {
		page = JSON.parse(resultFetch)?.result?.totalPages - 1;
		userApi = `${baseUrl}/products?page=${page}`;
		start();
	});

	//xử lý chi tiết sản phẩm

	listProducts.addEventListener('click', function(event) {
		const target = event.target.closest('.product_info');
		if (target) {
			productId = target.getAttribute('data-product-id');
			window.location.href = `${baseUrl}/admin/detailProduct?productId=${productId}`;
		} else {
			alert('có lỗi trong quá trình lấy thông tin')
		}
	});

	//xử lý ô tìm kiếm
	/*
		$("#input-search").keyup(() => {
			
			var searchKeyword = $("#input-search").val().trim()
			
			var searchApi = `${baseUrl}/products/search/${searchKeyword}`
			
			var searchOptions = {
				method: 'GET',
				headers: {
					'Authorization': `Bearer ${token}`,
					'Content-Type': 'application/json',
				},
			}
	
			searchUser(searchApi,searchOptions,renderSearchproducts);
		})
	
		function searchUser(api, options, callback) {
			fetch(api, options)
				.then(response => {
					if (!response.ok) throw new Error('fetch error')
					else return response.json()
				})
				.then(callback)
				.catch(error => {
					alert('có lỗi: ' + error)
				})
		}
		
		function renderSearchproducts(products) {
			const listProductsResult = products.result;
	
			listProducts.innerHTML = `		
			<tr>
				<th scope="col">STT</th>
				<th scope="col">Tên đăng nhập</th>
				<th scope="col">Tên</th>
				<th scope="col">Số điện thoại</th>
				<th scope="col">Email</th>
			</tr>`
	
	
			listProductsResult.forEach((user, i) => {
				const row = document.createElement('tr');
				row.setAttribute('data-product-id', user.id);
				row.classList.add('user_info');
				row.setAttribute('style', "cursor:pointer");
				row.innerHTML = `
					<th scope="row" style="padding-left: 10px;">${  i + 1}</th>
					<td style="padding-left: 10px;">${user.username}</td>
					<td style="padding-left: 10px;">${user.name}</td>
					<td style="padding-left: 10px;">${user.phone}</td>
					<td style="padding-left: 10px;">${user.email}</td>
				`;
				listProducts.appendChild(row);
			});
		}
	*/
});




