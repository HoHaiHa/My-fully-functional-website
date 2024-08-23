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
			        background-image: url(${product.img});
			        background-size: cover;
			        background-position: center;
			        background-repeat: no-repeat;
			    "></div>
				</td>
                <td style="padding-left: 10px;">${product.name}</td>
                <td style="padding-left: 10px;">${product.price}</td>
                <td style="padding-left: 10px;">${product.brand}</td>
                <td style="padding-left: 10px;">${product.quantity}</td>
                <td style="padding-left: 10px;"><button type="button" class="btn btn-outline-danger btn-delete"  data-product-id	="${product.id}">Xoá sản phẩm</button></td>
            `;
			listProducts.appendChild(row);
		});
	}




	//xử lý ô tìm kiếm

	$("#input-search").keyup(search)
	var searchResult;

	function search() {

		var name = $("#input-search").val().trim()

		var searchApi = `${baseUrl}/products/search?name=${name}`

		var searchOptions = {
			method: 'GET',
			headers: {
				'Authorization': `Bearer ${token}`,
				'Content-Type': 'application/json',
			},
		}

		searchUser(searchApi, searchOptions, renderSearchproducts, getSearchResult);
	}

	function searchUser(api, options, callback1, callback2) {
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

	function renderSearchproducts(products) {
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
			        background-image: url(${product.img});
			        background-size: cover;
			        background-position: center;
			        background-repeat: no-repeat;
			    "></div>
				</td>
                <td style="padding-left: 10px;">${product.name}</td>
                <td style="padding-left: 10px;">${product.price}</td>
                <td style="padding-left: 10px;">${product.brand}</td>
                <td style="padding-left: 10px;">${product.quantity}</td>
                <td style="padding-left: 10px;"><button type="button" class="btn btn-outline-danger btn-delete"  data-product-id="${product.id}">Xoá sản phẩm</button></td>
            `;
			listProducts.appendChild(row);
		});
	}

	//xử lý nút chuyển trang
	//láy giá trị trả về

	function getFetchResult(products) {
		resultFetch = JSON.stringify(products);
	}

	function getSearchResult(products) {
		searchResult = JSON.stringify(products);
	}

	let name = $("#input-search").val().trim()

	firstPageBtn.addEventListener('click', () => {
		if ($("#input-search").val().trim() == "") {
			page = 0;
			userApi = `${baseUrl}/products?page=${page}`;
			start();
		}
		else {
			page = 0;
			userApi = `${baseUrl}/products/search?name=${name}`;
			search();
		}

	});

	previousPageBtn.addEventListener('click', () => {
		if ($("#input-search").val().trim() == "") {
			const currentPage = JSON.parse(resultFetch)?.result?.number;
			if (currentPage <= 0) return; // Kiểm tra trang đầu tiên
			page = currentPage - 1;
			userApi = `${baseUrl}/products?page=${page}`;
			start();
		}
		else {
			const currentPage = JSON.parse(searchResult)?.result?.number;
			if (currentPage <= 0) return; // Kiểm tra trang đầu tiên
			page = currentPage - 1;
			userApi = `${baseUrl}/products/search?name=${name}&page=${page}`;
			search();
		}
	});

	nextPageBtn.addEventListener('click', () => {
		if ($("#input-search").val().trim() == "") {
			const currentPage = JSON.parse(resultFetch)?.result?.number;
			const totalPages = JSON.parse(resultFetch)?.page?.totalPages;
			if (currentPage >= totalPages - 1) return; // Kiểm tra trang cuối cùng
			page = currentPage + 1;
			userApi = `${baseUrl}/products?page=${page}`;
			start();
		}
		else {
			const currentPage = JSON.parse(searchResult)?.result?.number;
			const totalPages = JSON.parse(searchResult)?.page?.totalPages;
			if (currentPage >= totalPages - 1) return; // Kiểm tra trang cuối cùng
			page = currentPage + 1;
			userApi = `${baseUrl}/products/search?name=${name}&page=${page}`;
			search();
		}

	});

	lastPageBtn.addEventListener('click', () => {
		if ($("#input-search").val().trim() == "") {
			page = JSON.parse(resultFetch)?.result?.totalPages - 1;
			userApi = `${baseUrl}/products?page=${page}`;
			start();
		}
		else {
			page = JSON.parse(searchResult)?.result?.totalPages - 1;
			userApi = `${baseUrl}/products/search?name=${name}&page=${page}`;
			search();
		}

	});

	//xử lý nút xoá
	function deleteProduct(productId) {
		let deleteApi = `${baseUrl}/products/${productId}`

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

	$('#list-products').on('click', '.product_info', function(event) {
		// Xử lý khi nhấn nút xóa
		if ($(event.target).is('.btn-delete')) {
			let userConfirm = confirm('Xoá sản phẩm này')
			if(userConfirm){
				const productId = $(this).data('product-id');
				deleteProduct(productId);
				$(this).fadeOut();
			}
		}
		// Xử lý khi nhấn vào sản phẩm để xem chi tiết
		else {
			const productId = $(this).data('product-id');
			window.location.href=`${baseUrl}/admin/detailProduct?productid=${productId}`
		}
	});





});




