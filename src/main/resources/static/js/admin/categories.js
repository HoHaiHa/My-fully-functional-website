import baseUrl from "../config.js";


document.addEventListener('DOMContentLoaded', () => {

	//lấy token từ localStorage
	const token = localStorage.getItem('token');
	if (token === null) {
		throw new Error('login error');
	}

	const getApi = `${baseUrl}/categories`

	const getOptions = {
		method: 'GET',
		headers: {
			'Authorization': `Bearer ${token}`,
			'Content-Type': 'application/json',
		},
	};

	function start() {
		getCategories(renderCategories)
	}

	start()

	function getCategories(callback) {
		fetch(getApi, getOptions)
			.then(response => {
				if (!response.ok) throw new Error("error fetch response not ok")
				return response.json()
					.then(callback)
					.catch(error => {
						console.log('error fetch execute data' + error)
					})
			})
	}

	function renderCategories(categories) {
		const listCategoriesResult = categories.result;

		$('#list-categories').html(`		
    <tr>
        <th scope="col">Tên danh mục</th>
        <th scope="col">Mô tả</th>
    </tr>`);

		listCategoriesResult.forEach((category) => {
			const row = document.createElement('tr');
			row.setAttribute('style', "cursor:pointer");
			row.innerHTML = `
            <td >${category.name}</td>
            <td >${category.descriptions}</td>
            <td ><button type="button" class="btn btn-outline-danger btn-delete"  data-categories="${category.name}">Xoá danh mục</button></td>
            <td> &emsp;</td>
        `;
			document.getElementById('list-categories').appendChild(row);
		});
	}

	//xử lý thêm 
	function addRole(data) {
		const addApi = `${baseUrl}/categories`

		const addOptions = {
			method: "POST",
			headers: {
				'Authorization': `Bearer ${token}`,
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(data)
		}

		fetch(addApi, addOptions)
			.then(response => {
				if (!response.ok) throw new Error('error fetch response not OK')
				return response.json()
			})
			.catch(error => {
				console.log(error)
			})
	}

	$('#btn-save').click(() => {
		var data = {
			"name": $('#input-name').val().trim(),
			"descriptions": $('#input-desctiption').val(),
			"permissions": []
		};
		addRole(data)
		start()
		$('#layer-add').fadeOut(100);
		location.reload();
	})
	//xử lý xoá
	function deleteCategoy(categoryName) {
		const deleteApi = `${baseUrl}/categories/${categoryName}`

		const deleteOptions = {
			method: "DELETE",
			headers: {
				'Authorization': `Bearer ${token}`,
				'Content-Type': 'application/json'
			},
		}

		fetch(deleteApi, deleteOptions)
			.then(response => {
				if (!response.ok) throw new Error('error fetch response not OK')
				return response.json()
			})
			.catch(error => {
				console.log(error)
			})
	}

	// Sử dụng sự kiện ủy quyền nút xoá
	document.addEventListener('click', function(event) {
		if (event.target.classList.contains('btn-delete')) {
			var userConfirm = confirm('Xoá danh mục này?');
			if (userConfirm) {
				
				var categoryName = event.target.getAttribute('data-categories');
				console.log(categoryName)
				deleteCategoy(categoryName);
				location.reload()
			}
		}
	});


	//ẩn hiện form add role
	$('#btn-add').click(() => {
		$('#layer-add').fadeIn(100);
		$('#input-name').val('')
		$('#input-desctiption').val('')
	});

	$('.x , .dark-layer-cover').click(() => {
		var userConfirm = confirm('Xác nhận huỷ?')
		if (userConfirm) $('#layer-add').fadeOut(100); // Ẩn thẻ div khi nhấn vào x
	});
});




