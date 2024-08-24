import baseUrl from "../config.js";

document.addEventListener('DOMContentLoaded', () => {
	const token = localStorage.getItem('token')
	if (token === null) {
		throw new Error('login error');
	}

	//lấy productId từ đường dẫn
	const params = new URLSearchParams(window.location.search);
	const productId = params.get('productid');

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

	function renderInfo(product) {
		$(document).ready(function() {
			if (product.result) {
				$('#output-img').html(`<div style="
			        width: 160px;
			        height: 160px; /* Hoặc chiều cao mong muốn */
			        background-image: url(${product.result.img});
			        background-size: cover;
			        background-position: center;
			        background-repeat: no-repeat;
			    "></div>`);
				$('#output-id').html(product.result.id);
				$('#output-name').html(product.result.name);
				$('#output-price').html(product.result.price);
				$('#output-category').html(product.result.category.name);
				$('#output-description').html(product.result.description);
				$('#output-metaria').html(product.result.metaria);
				$('#output-quantity').html(product.result.quantity);
				$('#output-discount').html(product.result.discount);
				$('#output-feature').html(product.result.feature);
				$('#output-date').text(product.result.creationDate);
				$('#output-color').html(product.result.color);
				$('#output-hot').html(product.result.hot);

			} else {
				alert('Dữ liệu người dùng không tồn tại.');
			}
		});
	}

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
	$("#btn-delete").click(() => {
		let userConfirm = confirm('Xoá sản phẩm này')
		if (userConfirm) {
			deleteProduct(productId);
			window.history.back()
		}
	})

	//xử lý nút quay lại
	$("#btn-back").click(() => {
		window.history.back()
	})
		
	//nút sửa
	$("#btn-update").click(() => {
		window.location.href = `${baseUrl}/admin/updateProduct?productid=${productId}`
	})


})