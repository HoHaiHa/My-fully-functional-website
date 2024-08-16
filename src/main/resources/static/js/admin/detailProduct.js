import baseUrl from "../config.js";

document.addEventListener('DOMContentLoaded', () => {
	const token = localStorage.getItem('token')
	if (token === null) {
		throw new Error('login error');
	}

	//lấy productId từ đường dẫn
	const params = new URLSearchParams(window.location.search);
	const productId = params.get('productId');




	//lấy dữ liệu 
	const getProductApi = `${baseUrl}/products/${productId}`
	const getProductOptions = {
		method: 'GET',
		headers: {
			'Authorization': `Bearer ${token}`,
			'Content-Type': 'application/json',
		},
	}

	function start() {
		getProductInfo(renderInfo)
	}

	start()

	function getProductInfo(callback) {
		fetch(getProductApi, getProductOptions)
			.then(response => {
				if (!response.ok) {
					throw new Error(' response was not ok');
				}
				return response.json();
			})
			.then(callback)
			.catch(error => {
				console.error('Fetch error:', error);
				alert('Có lỗi: ' + error.message);
			});
	}

	function renderInfo(user) {
		$(document).ready(function() {
			if (user.result) {
				$('#output_id').html(user.result.id);
				$('#output_username').html(user.result.username);
				$('#output_name').html(user.result.name);
				$('#output_dob').html(user.result.dob);
				$('#output_email').html(user.result.email);
				$('#output_phone').html(user.result.phone);
				$('#output_role').html(user.result.roles.map(role => role.name).join(' '));
			} else {
				alert('Dữ liệu người dùng không tồn tại.');
			}
		});
	}

	//xử lý các nút

	$(document).ready(function() {
		//xoá
		$('#btn_delete').click(function(event) {
			const userConfirm = confirm('Bạn có chắc chắn xoá người dùng')
			if (userConfirm) {
				deleteUser()
				window.history.back()
			}
			else event.preventDefault();
		})

		//quay lại
		$('#btn_back').click(function() {
			window.history.back();
		});
		
		//
		$('#btn_update').click(()=>{
			window.location.href = `${baseUrl}/admin/updateuser?productId=${productId}`
		})

	});

	// logic xoá
	const delApi = `${baseUrl}/products/${productId}`
	const delOptions = {
		method: "DELETE",
		headers: {
			'Authorization': `Bearer ${token}`,
			'Content-Type': 'application/json',
		},
	}

	function deleteUser() {
		fetch(delApi, delOptions)
			.then(response => {
				if (!response.ok) throw new Error('error fetch api server');
				return response.json();
			})
			.then(data => {
				alert('xoá thành công')
			})
			.catch(error => {
				console.log(error)
			})
	}

})