import baseUrl from "../config.js";


document.addEventListener('DOMContentLoaded', () => {

	//lấy token từ localStorage
	const token = localStorage.getItem('token');
	if (token === null) {
		throw new Error('login error');
	}

	const getApi = `${baseUrl}/roles`

	const getOptions = {
		method: 'GET',
		headers: {
			'Authorization': `Bearer ${token}`,
			'Content-Type': 'application/json',
		},
	};

	function start() {
		getRoles(renderRoles)
	}

	start()

	function getRoles(callback) {
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

	function renderRoles(roles) {
		const listRolesResult = roles.result;

		$('#list-roles').html(`		
    <tr>
        <th scope="col">Tên vai trò</th>
        <th scope="col">Mô tả</th>
    </tr>`);

		listRolesResult.forEach((role) => {
			const row = document.createElement('tr');
			row.setAttribute('style', "cursor:pointer");
			row.innerHTML = `
            <td >${role.name}</td>
            <td >${role.description}</td>
            <td ><button type="button" class="btn btn-outline-danger btn-delete"  data-role="${role.name}">Xoá role</button></td>
            <td> &emsp;</td>
        `;
			document.getElementById('list-roles').appendChild(row);
		});
	}

	//xử lý thêm 
	function addRole(data) {
		const addApi = `${baseUrl}/roles`

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
			})
			.catch(error => {
				console.log(error)
			})
	}

	$('#btn-save').click(() => {
		var data = {
			"name": $('#input-name').val(),
			"description": $('#input-desctiption').val(),
			"permissions": []
		};
		addRole(data)
		start()
		$('#layer-addRole').fadeOut(100);
		location.reload();
	})
	//xử lý xoá
	function deleteRole(roleName) {
		const deleteApi = `${baseUrl}/roles/${roleName}`

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
			})
			.catch(error => {
				console.log(error)
			})
	}

	// Sử dụng sự kiện ủy quyền
	document.addEventListener('click', function(event) {
		if (event.target.classList.contains('btn-delete')) {
			var userConfirm = confirm('Xoá role này?');
			if (userConfirm) {
				var roleName = event.target.getAttribute('data-role');
				deleteRole(roleName);
				location.reload()
			}
		}
	});


	//ẩn hiện form add role
	$('#btn-addRole').click(() => {
		$('#layer-addRole').fadeIn(100);
		$('#input-name').val('')
		$('#input-desctiption').val('')
	});

	$('.x , .dark-layer-cover').click(() => {
		var userConfirm = confirm('Xác nhận huỷ?')
		if (userConfirm) $('#layer-addRole').fadeOut(100); // Ẩn thẻ div khi nhấn vào x
	});
});




