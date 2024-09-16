import baseUrl from "../config.js";

$(document).ready(() => {
	const token = localStorage.getItem('token');
	const staff = localStorage.getItem('userId');
	if (token === null) {
		throw new Error('login error');
	}

	// phương thức tìm kiếm khách hàng theo số điện thoại 
	let hasUser = false;//kiểm tra xem có user đúng không

	var userId;
	function findUserByPhone(phone) {
		let getUserByPhoneApi = `${baseUrl}/users/phone/${phone}`;

		let getUserByPhoneOptions = {
			method: 'GET',
			headers: {
				'Authorization': `Bearer ${token}`,
				'Content-Type': 'application/json',
			},
		};

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
					hasUser = false;
					console.error('There was a problem with the fetch operation:', error);
				});
		}

		function renderUserName(user) {
			if (hasUser) {
				$('#user-name').val(user.result.name);
				userId = user.result.id
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
	function findProductById(productId, idProductQuantity, idProductName, idProductPrice) {
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
						$(`#${idProductName}`).text('');
						$(`#${idProductPrice}`).text('')
						$(`#${idProductQuantity}`).val(0).trigger('input')
						throw new Error('no product match with id');
					}
					hasProduct = true;
					return response.json();
				})
				.then(callback)
				.catch(error => {
					$(`#${idProductName}`).text('');
					$(`#${idProductPrice}`).text('')
					$(`#${idProductQuantity}`).val(0).trigger('input')
					console.error('There was a problem with the fetch operation:', error);
				});
		}

		function renderProduct(product) {
			if (hasProduct) {
				$(`#${idProductName}`).text(product.result.name);
				$(`#${idProductPrice}`).text(product.result.price + ' vnđ')
				$(`#${idProductQuantity}`).val(1).trigger('input')
			} else {
				$(`#${idProductName}`).text('');
				$(`#${idProductPrice}`).text('')
				$(`#${idProductQuantity}`).val(0).trigger('input')
			}
		}

		getProduct(renderProduct);
	}

	//xử lý khi nhập id vào ô id của suản phẩm
	function listenId(idProductId, idProductQuantity, idProductName, idProductPrice) {
		$(`#${idProductId}`).keyup(() => {
			findProductById($(`#${idProductId}`).val(), idProductQuantity, idProductName, idProductPrice)
		})
	}

	listenId('product-id', 'product-quantity', 'product-name', 'product-price')

	// Xử lý khi nhấn dấu cộng để thêm item
	var indexOfItem = 1;
	$(document).on('click', '.add-item', function() {
		$('#add-product-item').append(`
        <div class="col-11 product-item" id="product-item${indexOfItem}" style="min-height:70px;background-color:#e8e9eb;border-radius:14px;position:relative;margin:10px 0">
            <div>
                <span style="line-height:50px;margin:10px;margin-right:25px">Mã sản phẩm</span>
                <input id="product-id${indexOfItem}" class="col-4 product-id" style="border-radius:8px">
                <span style="line-height:70px;margin:10px;margin-left:100px">Số lượng</span>
                <input id="product-quantity${indexOfItem}" class="product-quantity" type="number" min="1"  style="max-width:50px ;border-radius:8px">    
            </div>
            <div class="d-flex flex-row">
                <span style="line-height:50px;margin:10px;margin-right:30px">Tên sản phẩm</span>
                <div id="product-name${indexOfItem}" class="col-5" style="line-height:70px"></div>
                <span style="line-height:50px;margin:10px;margin-right:25px">Giá sản phẩm</span>
                <div id="product-price${indexOfItem}" class="col-3" style="line-height:70px"></div>
            </div>
             <div class="remove-item">
                -
            </div>
            <div class="add-item">
                +
            </div>
        </div>
    `).hide().fadeIn(200);



		// Lắng nghe sự kiện và xử lý với id tương ứng
		listenId(`product-id${indexOfItem}`, `product-quantity${indexOfItem}`, `product-name${indexOfItem}`, `product-price${indexOfItem}`);

		// Tăng giá trị của indexOfItem cho lần thêm tiếp theo
		indexOfItem++;
	});

	//lắng nghe sự kiện remove Item
	$(document).on('click', '.product-item .remove-item', function() {
		$(this).parent().fadeOut(200, function() {
			$(this).remove(); // Xóa phần tử sau khi hiệu ứng mờ dần hoàn tất
		});
	});



	//xử lý phần thông tin đơn hàng
	//hiển thị ngày hôm nay
	var today = new Date().toISOString().split('T')[0]; // Lấy ngày hiện tại và định dạng theo yyyy-mm-dd
	$('#inputCreationDate').val(today); // Đặt giá trị cho thẻ input

	//xử lý tổng số lượng sản phẩm và tổng giá trị sản phẩm 
	$(document).on('input', '.product-quantity', function() {
		var totalQuantity = parseInt($('#product-quantity').val())
		var totalPrice = parseInt($('#product-quantity').val()) * parseInt($('#product-price').text().slice(0, -4))
		if ($('#product-price').text().slice(0, -4) == "") {
			totalPrice = 0
		}
		for (let i = 1; i < indexOfItem; i++) {
			totalQuantity = totalQuantity + parseInt($(`#product-quantity${i}`).val())

			if ($(`#product-price${i}`).text().slice(0, -4) == "") {
				totalPrice = totalPrice;
			}
			else {
				totalPrice = totalPrice + parseInt($(`#product-quantity${i}`).val()) * parseInt($(`#product-price${i}`).text().slice(0, -4))
			}
		}
		$('#inputTotalQuantity').text(totalQuantity)
		$('#inputTotalPrice').text(totalPrice + " vnđ")
		//gán 0 cho giá trị quantity để không lỗi ô tổng số lượng
		if ($(this).val().trim() === '') {
			$(this).val('0');
		}

		updateFinalTotalPrice()
	})
	//bắt cả sự kiện blur để tránh lỗi
	$(document).on('blur', '.product-quantity', function() {
		var totalQuantity = parseInt($('#product-quantity').val())
		var totalPrice = parseInt($('#product-quantity').val()) * parseInt($('#product-price').text().slice(0, -4))
		if ($('#product-price').text().slice(0, -4) == "") {
			totalPrice = 0
		}
		for (let i = 1; i < indexOfItem; i++) {
			totalQuantity = totalQuantity + parseInt($(`#product-quantity${i}`).val())

			if ($(`#product-price${i}`).text().slice(0, -4) == "") {
				totalPrice = totalPrice;
			}
			else {
				totalPrice = totalPrice + parseInt($(`#product-quantity${i}`).val()) * parseInt($(`#product-price${i}`).text().slice(0, -4))
			}
		}
		$('#inputTotalQuantity').text(totalQuantity)
		$('#inputTotalPrice').text(totalPrice + " vnđ")
		//gán 0 cho giá trị quantity để không lỗi ô tổng số lượng
		if ($(this).val().trim() === '') {
			$(this).val('0');
		}

		updateFinalTotalPrice()
	})

	//hiển thị tổng giá trị sau thuế
	$('#input-tax').keyup(() => {
		updateFinalTotalPrice()
	})
	$('#input-discount').keyup(() => {
		updateFinalTotalPrice()
	})

	function updateFinalTotalPrice() {
		var finalPrice = 0;
		if ($('#inputTotalPrice').text().slice(0, -4) == "") {
			$('input-final-price').text('')
		}
		else {
			var PriceAfterDisount = (parseInt($('#inputTotalPrice').text().slice(0, -4))
				- (parseInt($('#inputTotalPrice').text().slice(0, -4)) * ($('#input-discount').val() / 100)))

			finalPrice = PriceAfterDisount + (PriceAfterDisount * ($('#input-tax').val() / 100))
			$('#input-final-price').text(finalPrice + ' vnđ')
		}
	}

	//xử lý nút huỷ
	$('#btn-cancel').click(() => {
		window.history.back()
	})


	//xử lý nút lưu
	$('#btn-save').click(async () => {

		//kiểm tra chủ đơn hàng đã tồn tại chưa 
		await findUserByPhone($('#user-phone').val());
		if (!hasUser) {
			//nếu chưa tồn tại tạo người dùng mới
			userId = await addUser($('#user-name').val(), $('#user-phone').val())
		}
		//kiểm tra id sản phẩm
		var listProductId = [];
		var listProductQuantity = []
		//lấy danh sách id sản phẩm   
		$('.product-id').each(function() {
			listProductId.push($(this).val());
		});

		$('.product-quantity').each(function() {
			listProductQuantity.push($(this).val());
		});
		//check hợp lệ của sản phẩm
		const isValidProducts = await checkListProduct(listProductId);
		if (!isValidProducts) {
			alert('Mã sản phẩm chưa hợp lệ!');
			return false;
		}
		var userConfirm = confirm('Lưu đơn hàng!')
		if (!userConfirm) {
			return true
		}
		saveOrders(listProductId, listProductQuantity)
		window.history.back()

	});

	//kiểm trả tất cả sản phẩm hợp lệ
	async function checkListProduct(listProductId) {
		// Sử dụng Promise.all để đợi tất cả các lời gọi API hoàn thành
		return Promise.all(listProductId.map(productId => {
			let getProductApi = `${baseUrl}/products/${productId}`;

			let getProductOptions = {
				method: 'GET',
				headers: {
					'Authorization': `Bearer ${token}`,
					'Content-Type': 'application/json',
				},
			};

			// Thực hiện lời gọi fetch và xử lý kết quả
			return fetch(getProductApi, getProductOptions)
				.then(response => {
					if (!response.ok) {
						throw new Error(`Product ${productId} not found`);
					}
					return true; // Trả về true nếu lời gọi thành công
				})
				.catch(error => {
					console.error(`There was a problem with productId ${productId}:`, error);
					return false; // Trả về false nếu có lỗi xảy ra
				});
		}))
			.then(results => {
				// Kiểm tra kết quả của tất cả các lời gọi API
				return results.every(result => result === true);
			})
			.catch(error => {
				console.error('Error in checkListProduct:', error);
				return false; // Nếu có lỗi xảy ra với Promise.all
			});
	}

	$('#btn-save-print').click(() => {
		console.log(addUser('Trần Thị Thanh Hiền', '0377124152'))

	})

	//nếu người dùng chưa tồn tại thì tạo người dùng mới 
	async function addUser(name, phone) {
		let addUserApi = `${baseUrl}/users/admincreate`;

		let data = {
			username: phone,
			password: phone,
			name: name,
			dob: '',
			email: '',
			phone: phone,
			roles: ['USER']
		};

		let addUserOptions = {
			method: 'POST',
			headers: {
				'Authorization': `Bearer ${token}`,
				'Content-Type': 'application/json',
			},
			body: JSON.stringify(data)
		};

		return fetch(addUserApi, addUserOptions)  // Thêm return ở đây
			.then(response => {
				if (!response.ok) {
					return response.json().then(err => { throw new Error(err.message) });
				}
				return response.json();
			})
			.then(data => {
				return data.result.id;
			})
			.catch(error => {
				console.error('Lỗi:', error.message);
				alert(`Đã xảy ra lỗi thêm người dùng`);
			});
	}


	//lưu dữ liệu lên server
	async function saveOrders(listProductId, listProductQuantity) {
		//lấy userId
		await findUserByPhone($('#user-phone').val())

		let status = $('#input-status option:selected').data('status');

		let listItemId = await saveListItem(listProductId, listProductQuantity);

		let bodyData = {
			totalQuantity: $('#inputTotalQuantity').text(),
			discount: $('#input-discount').val(),
			creationDate: $('#inputCreationDate').val(),
			totalPrice: $('#inputTotalPrice').text().slice(0, -4),
			tax: $('#input-tax').val(),
			finalTotalPrice: $('#input-final-price').text().slice(0, -4),
			city: $('#input-city').val(),
			district: $('#input-district').val(),
			ward: $('#input-ward').val(),
			numberAndStreet: $('#input-numberAndStreet').val(),
			notes: $('#input-notes').val(),
			paymentMethod: $('#input-paymentMethod').val(),
			status: status,
			item: listItemId,
			userId: userId,
			staffId:staff,
		};

		console.log(JSON.stringify(bodyData))


		let saveOrdersApi = `${baseUrl}/orders`;

		let saveOrdersOptions = {
			method: 'POST',
			headers: {
				'Authorization': `Bearer ${token}`,
				'Content-Type': 'application/json',
			},
			body: JSON.stringify(bodyData) // Sử dụng JSON.stringify trực tiếp trên đối tượng data
		};

		fetch(saveOrdersApi, saveOrdersOptions)
			.then(response => {
				if (!response.ok) {
					return response.json().then(err => { throw new Error(err.message) });
				}
				return response.json();
			})
			.catch(error => {
				// Hiển thị thông báo lỗi cho người dùng
				console.error('Lỗi:', error.message);
				alert(`Đã xảy ra lỗi` + error);
				// Ghi lại lỗi nếu cần
			});
	}

	//lưu các product lên server và lấy về itemId
	async function saveListItem(listProductId, listProductQuantity) {
		var listItemId = [];

		// Lặp qua từng productId và quantity
		for (let i = 0; i < listProductId.length; i++) {
			try {
				// Gọi hàm saveItem và chờ kết quả trả về
				let itemId = await saveItem(listProductId[i], listProductQuantity[i]);
				// Thêm itemId vào mảng listItemId
				listItemId.push(itemId);
			} catch (error) {
				console.error(`Failed to save item for product ID ${listProductId[i]}: ${error}`);
			}
		}
		// Trả về mảng chứa các itemId
		return listItemId;
	}

	//lưu 1 product trả về 1 itemId
	async function saveItem(ProductId, ProductQuantity) {
		let saveItemApi = `${baseUrl}/items`;

		let data = {
			productId: ProductId,
			quantity: ProductQuantity
		};

		let saveItemOption = {
			method: 'POST',
			headers: {
				'Authorization': `Bearer ${token}`,
				'Content-Type': 'application/json',
			},
			body: JSON.stringify(data) // Chuyển đổi dữ liệu thành JSON
		};

		try {
			let response = await fetch(saveItemApi, saveItemOption);
			if (!response.ok) {
				throw new Error(`There was a problem with item fetch`);
			}
			let responseData = await response.json();
			// Lấy id từ đối tượng result
			return responseData.result.id;
		} catch (error) {
			console.error(`There was a problem with item fetch: ${error}`);
		}
	}

});

