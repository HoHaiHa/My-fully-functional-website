import baseUrl from "../config.js";

$(document).ready(() => {
	const token = localStorage.getItem('token');
	if (token === null) {
		throw new Error('login error');
	}

	// phương thức tìm kiếm khách hàng theo số điện thoại 
	function findUserByPhone(phone) {
		let getUserByPhoneApi = `${baseUrl}/users/phone/${phone}`;

		let getUserByPhoneOptions = {
			method: 'GET',
			headers: {
				'Authorization': `Bearer ${token}`,
				'Content-Type': 'application/json',
			},
		};

		let hasUser;

		function getUserByPhone(callback) {
			fetch(getUserByPhoneApi, getUserByPhoneOptions)
				.then(response => {
					if (!response.ok) {
						hasUser = false;
						$('#user-name').val('');
						throw new Error('no user match with phone');
					}
					hasUser = true;
					return response.json();
				})
				.then(callback)
				.catch(error => {
					$('#user-name').val('');
					console.error('There was a problem with the fetch operation:', error);
				});
		}

		function renderUserName(user) {
			if (hasUser) {
				$('#user-name').val(user.result.name);
			} else {
				$('#user-name').val(''); // Đặt nội dung thành trắng
			}
		}

		getUserByPhone(renderUserName);
	}

	$('#user-phone').keyup(() => {
		findUserByPhone($('#user-phone').val());
	});

	//xử lý phần sản phẩm
	function findProductById(productId) {
		let getProductApi = `${baseUrl}/products/${productId}`;

		let getProductOptions = {
			method: 'GET',
			headers: {
				'Authorization': `Bearer ${token}`,
				'Content-Type': 'application/json',
			},
		};

		let hasProduct;

		function getProduct(callback) {
			fetch(getProductApi, getProductOptions)
				.then(response => {
					if (!response.ok) {
						hasProduct = false;
						$('#product-name').text('');
						$('#product-price').text('')
						$('#product-quanity').val('')
						throw new Error('no product match with id');
					}
					hasProduct = true;
					return response.json();
				})
				.then(callback)
				.catch(error => {
					$('#product-name').text('');
					$('#product-price').text('')
					$('#product-quanity').val('')
					console.error('There was a problem with the fetch operation:', error);
				});
		}

		function renderProduct(product) {
			if (hasProduct) {
				$('#product-name').text(product.result.name);
				$('#product-price').text(product.result.price + ' vnđ' )
				$('#product-quanity').val(1)
			} else {
				$('#product-name').text('');
				$('#product-price').text('')
				$('#product-quanity').val('')
			}
		}

		getProduct(renderProduct);
	}
	
	$('#product-id').keyup(()=>{
		findProductById($('#product-id').val())
	})


});
