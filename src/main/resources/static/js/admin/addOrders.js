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
        <div class="col-11" id="product-item${indexOfItem}" style="min-height:70px;background-color:#e8e9eb;border-radius:14px;position:relative;margin:10px 0">
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
            <div class="add-item">
                +
            </div>
        </div>
    `);

		// Lắng nghe sự kiện và xử lý với id tương ứng
		listenId(`product-id${indexOfItem}`, `product-quantity${indexOfItem}`, `product-name${indexOfItem}`, `product-price${indexOfItem}`);

		// Tăng giá trị của indexOfItem cho lần thêm tiếp theo
		indexOfItem++;
	});



	//xử lý phần thông tin đơn hàng
	//hiển thị ngày hôm nay
	var today = new Date().toISOString().split('T')[0]; // Lấy ngày hiện tại và định dạng theo yyyy-mm-dd
	$('#inputCreationDate').val(today); // Đặt giá trị cho thẻ input

	//xử lý tổng số lượng sản phẩm và tổng giá trị sản phẩm 
	$(document).on('input', '.product-quantity', function() {
		var totalQuantity = parseInt($('#product-quantity').val())
		var totalPrice = parseInt($('#product-quantity').val()) * parseInt($('#product-price').text().slice(0, -4))
		if($('#product-price').text().slice(0, -4)==""){
			totalPrice=0
		}
		for (let i = 1; i < indexOfItem; i++) {
			totalQuantity = totalQuantity + parseInt($(`#product-quantity${i}`).val())
			
			if($(`#product-price${i}`).text().slice(0, -4)==""){
				totalPrice = totalPrice;
			}
			else{
				totalPrice = totalPrice + parseInt($(`#product-quantity${i}`).val()) * parseInt($(`#product-price${i}`).text().slice(0, -4))
			}
		}
		$('#inputTotalQuantity').text(totalQuantity)
		$('#inputTotalPrice').text(totalPrice + " vnđ")
		//gán 0 cho giá trị quantity để không lỗi ô tổng số lượng
		if ($(this).val().trim() === '') {
			$(this).val('0');
		}
	})
	//bắt cả sự kiện blur để tránh lỗi
	$(document).on('blur', '.product-quantity', function() {
		var totalQuantity = parseInt($('#product-quantity').val())
		var totalPrice = parseInt($('#product-quantity').val()) * parseInt($('#product-price').text().slice(0, -4))
		if($('#product-price').text().slice(0, -4)==""){
			totalPrice=0
		}
		for (let i = 1; i < indexOfItem; i++) {
			totalQuantity = totalQuantity + parseInt($(`#product-quantity${i}`).val())
			
			if($(`#product-price${i}`).text().slice(0, -4)==""){
				totalPrice = totalPrice;
			}
			else{
				totalPrice = totalPrice + parseInt($(`#product-quantity${i}`).val()) * parseInt($(`#product-price${i}`).text().slice(0, -4))
			}
		}
		$('#inputTotalQuantity').text(totalQuantity)
		$('#inputTotalPrice').text(totalPrice + " vnđ")
		//gán 0 cho giá trị quantity để không lỗi ô tổng số lượng
		if ($(this).val().trim() === '') {
			$(this).val('0');
		}
	})
	
	//hiển thị tổng giá trị sau thuế
	$('#input-tax').keyup(()=>{
		if($('#inputTotalPrice').text().slice(0, -4)==""){
			$('input-final-price').text('')
		}
		else{
			var finalPrice=$('#inputTotalPrice').text().slice(0, -4)+$('#inputTotalPrice').text().slice(0, -4)*($('#input-discount').val()/100)*($('#input-tax').val()/100)
			$('#input-final-price').text(finalPrice)
		}
	})

});
