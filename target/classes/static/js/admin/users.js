import baseUrl from "../config.js";


document.addEventListener('DOMContentLoaded', () => {
	const token = localStorage.getItem('token');
	if (token === null) {
		throw new Error('login error');
	}

	const listUsers = document.querySelector('#list-users');
	const firstPageBtn = document.querySelector('#page_first');
	const previousPageBtn = document.querySelector('#page_previous');
	const nextPageBtn = document.querySelector('#page_next');
	const lastPageBtn = document.querySelector('#page_last');

	//thêm đường dẫn vào nút thêm user
	const addUserBtn = document.querySelector('#btn_addUser')
	addUserBtn.setAttribute('href', `${baseUrl}/admin/adduser`)

	let page = 0;
	let userApi = `${baseUrl}/users?page=${page}`;
	let resultFetch = '';
	let userId;

	const options = {
		method: 'GET',
		headers: {
			'Authorization': `Bearer ${token}`,
			'Content-Type': 'application/json',
		},
	};

	function start() {
		getUsers(renderUsers, getFetchResult);
	}

	start();

	function getUsers(callback1, callback2) {
		fetch(userApi, options)
			.then(response => {
				if (!response.ok) {
					throw new Error('Network response was not ok');
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

	function renderUsers(users) {
		const listUsersResult = users.result;

		listUsers.innerHTML = `		
		<tr>
			<th scope="col">STT</th>
			<th scope="col">Tên đăng nhập</th>
			<th scope="col">Tên</th>
			<th scope="col">Số điện thoại</th>
			<th scope="col">Email</th>
		</tr>`

		let index = users.page.number * users.page.size;

		listUsersResult.forEach((user, i) => {
			const row = document.createElement('tr');
			row.setAttribute('data-user-id', user.id);
			row.classList.add('user_info');
			row.setAttribute('style', "cursor:pointer");
			row.innerHTML = `
                <th scope="row" style="padding-left: 10px;">${index + i + 1}</th>
                <td style="padding-left: 10px;">${user.username}</td>
                <td style="padding-left: 10px;">${user.name}</td>
                <td style="padding-left: 10px;">${user.phone}</td>
                <td style="padding-left: 10px;">${user.email}</td>
            `;
			listUsers.appendChild(row);
		});
	}

	//xử lý nút chuyển trang
	//láy giá trị trả về

	function getFetchResult(users) {
		resultFetch = JSON.stringify(users);
	}

	firstPageBtn.addEventListener('click', () => {
		page = 0;
		userApi = `${baseUrl}/users?page=${page}`;
		start();
	});

	previousPageBtn.addEventListener('click', () => {
		const currentPage = JSON.parse(resultFetch)?.page?.number;
		if (currentPage <= 0) return; // Kiểm tra trang đầu tiên
		page = currentPage - 1;
		userApi = `${baseUrl}/users?page=${page}`;
		start();
	});

	nextPageBtn.addEventListener('click', () => {
		const currentPage = JSON.parse(resultFetch)?.page?.number;
		const totalPages = JSON.parse(resultFetch)?.page?.totalPages;
		if (currentPage >= totalPages - 1) return; // Kiểm tra trang cuối cùng
		page = currentPage + 1;
		userApi = `${baseUrl}/users?page=${page}`;
		start();
	});

	lastPageBtn.addEventListener('click', () => {
		page = JSON.parse(resultFetch)?.page?.totalPages - 1;
		userApi = `${baseUrl}/users?page=${page}`;
		start();
	});

	//xử lý chi tiết người dùng

	listUsers.addEventListener('click', function(event) {
		const target = event.target.closest('.user_info');
		if (target) {
			userId = target.getAttribute('data-user-id');
			window.location.href = `${baseUrl}/admin/detailuser?userId=${userId}`;
		} else {
			alert('có lỗi trong quá trình lấy thông tin')
		}
	});

	//xử lý ô tìm kiếm

	$("#input-search").keyup(() => {
		
		var searchKeyword = $("#input-search").val().trim()
		
		var searchApi = `${baseUrl}/users/search/${searchKeyword}`
		
		var searchOptions = {
			method: 'GET',
			headers: {
				'Authorization': `Bearer ${token}`,
				'Content-Type': 'application/json',
			},
		}

		searchUser(searchApi,searchOptions,renderSearchUsers);
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
	
	function renderSearchUsers(users) {
		const listUsersResult = users.result;

		listUsers.innerHTML = `		
		<tr>
			<th scope="col">STT</th>
			<th scope="col">Tên đăng nhập</th>
			<th scope="col">Tên</th>
			<th scope="col">Số điện thoại</th>
			<th scope="col">Email</th>
		</tr>`


		listUsersResult.forEach((user, i) => {
			const row = document.createElement('tr');
			row.setAttribute('data-user-id', user.id);
			row.classList.add('user_info');
			row.setAttribute('style', "cursor:pointer");
			row.innerHTML = `
                <th scope="row" style="padding-left: 10px;">${  i + 1}</th>
                <td style="padding-left: 10px;">${user.username}</td>
                <td style="padding-left: 10px;">${user.name}</td>
                <td style="padding-left: 10px;">${user.phone}</td>
                <td style="padding-left: 10px;">${user.email}</td>
            `;
			listUsers.appendChild(row);
		});
	}

});




