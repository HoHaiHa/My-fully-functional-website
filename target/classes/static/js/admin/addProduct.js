import baseUrl from "../config.js";

$(document).ready(() => {
	const token = localStorage.getItem('token');
	if (token === null) {
		throw new Error('login error');
	}

	//xử lý hiển thị danh mục
	let categoryApi = `${baseUrl}/categories`;

	let categoryOptions = {
		method: 'GET',
		headers: {
			'Authorization': `Bearer ${token}`,
			'Content-Type': 'application/json',
		},
	};

	getCategory(rendeCategory);

	function getCategory(callback) {
		fetch(categoryApi, categoryOptions)
			.then(response => {
				if (!response.ok) throw new alert('get category response was not ok')
				else return response.json()
			})
			.then(callback)
			.catch(error => {
				console.error('There was a problem with the fetch operation:', error);
			});
	}

	function rendeCategory(categories) {
		const listCateforyResult = categories.result;
		$('#input-category').html("")

		listCateforyResult.forEach((category) => {
			$("#input-category").append(`<option>${category.name}</option>`);
		})
	}

	//xử lý hiển thị ảnh khi chọn
	$('#input-img').change(function(event) {
		const file = event.target.files[0];
		if (file) {
			// Lấy ra đường dẫn của file ảnh
			const imageUrl = URL.createObjectURL(file);

			// Sử dụng đường dẫn ảnh làm nền cho thẻ div với ID là "abc"
			$('#output-img').css({
				'background-image': `url(${imageUrl})`,
				'background-size': 'cover',
				'background-repeat': 'no-repeat'
			});
		}

	})

	//xử nút huỷ	
	$('#btn_cancel').click(() => {
		let userConfirm = confirm('xác nhận huỷ?')
		if (userConfirm) window.history.back()
	})

	//xử lý nút lưu
	//hàm lưu ảnh 
	async function saveImage() {
		try {
			const fileInput = document.getElementById('input-img');
			const formData = new FormData();
			formData.append('file', fileInput.files[0]);

			let addApi = `${baseUrl}/images`;

			let addOptions = {
				method: 'POST',
				headers: {
					'Authorization': `Bearer ${token}`,
				},
				body: formData
			};

			const response = await fetch(addApi, addOptions);

			if (!response.ok) {
				console.log('Upload image response not ok');
				throw new Error('Upload failed');
			}

			const data = await response.json();

			// Trả về result từ response JSON
			return data;

		} catch (error) {
			console.error('Error:', error);
			throw error; // Ném lỗi để hàm gọi có thể xử lý
		}
	}


	async function getData() {
		try {
			// Chờ saveImage() hoàn thành và lấy kết quả trả về
			let img = await saveImage();

			var data = {
				name: $('#input-name').val(),
				img: img.result.url, // Sử dụng img.result để lưu đường dẫn ảnh
				price: $('#input-price').val(),
				descriptions: $('#input-descriptions').val(),
				material: $('#input-material').val(),
				quantity: $('#input-quantity').val(),
				feature: $('#input-feature').val(),
				size: $('#input-size').val(),
				brand: $('#input-brand').val(),
				category: $('#input-category').val().trim(),
				color: $('#input-color').val(),
				creationDate: $('#input-creationDate').val(),
				hot: $('#input-hot').val(),
			}

			return data;
		} catch (error) {
			console.error('Error getting data:', error);
			throw error; // Ném lỗi để hàm gọi có thể xử lý
		}
	}


	// Định nghĩa hàm saveProduct là async và trả về một Promise
	async function saveProduct() {
		let saveApi = `${baseUrl}/products`;

		let data = await getData(); // Đợi getData hoàn thành và lấy kết quả

		let saveOptions = {
			method: 'POST',
			headers: {
				'Authorization': `Bearer ${token}`,
				'Content-Type': 'application/json',
			},
			body: JSON.stringify(data) // Chuyển đổi dữ liệu thành JSON
		};

		try {
			let response = await fetch(saveApi, saveOptions);
			if (!response.ok) {
				alert('response not ok');
				throw new Error('save product response was not ok.');
			}
			let data = await response.json();
			return data; // Trả về dữ liệu để có thể xử lý tiếp
		} catch (error) {
			console.error('Error:', error);
			throw error; // Ném lỗi để hàm gọi có thể xử lý
		}
	}

	// Xử lý sự kiện click
	$('#btn_save').click(async () => {
		let userConfirm = confirm('Lưu sản phẩm');
		if (userConfirm) {
			try {
				await saveProduct(); // Đảm bảo saveProduct trả về một Promise
				let userConfirm2 = confirm('Lưu sản phẩm thành công! tiếp tục thêm sản phẩm?');
				if (!userConfirm2) {
					window.location.href = `${baseUrl}/admin/products`;
				}
			} catch (error) {
				console.error('Error:', error);
				// Không thực hiện confirm2 nếu có lỗi
			}
		}
	});



})