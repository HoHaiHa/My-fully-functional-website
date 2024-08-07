import baseUrl from "../config.js";

document.addEventListener('DOMContentLoaded', () => {
	//lấy token từ localStorage
	const token = localStorage.getItem('token');
	if (token === null) {
		throw new Error('login error');
	}
	const password = document.querySelector("#input-password");
	const passwordAgain = document.querySelector("#input-passwordAgain");
	const email = document.querySelector("#input-email");

	const name = document.querySelector("#input-name");
	const dob = document.querySelector("#input-dob");
	const phone = document.querySelector("#input-phone");
	const listRoles = document.querySelector("#input_role");

	const btnSave = document.querySelector("#btn_save");
	const btnCancel = document.querySelector("#btn_cancel");

	const params = new URLSearchParams(window.location.search);
	const userId = params.get('userId');

	//thêm dữ liệu cho combobox role

	let roleApi = `${baseUrl}/roles`;

	let roleOptions = {
		method: 'GET',
		headers: {
			'Authorization': `Bearer ${token}`,
			'Content-Type': 'application/json',
		},
	};

	getRole(renderRole);

	function getRole(callback) {
		fetch(roleApi, roleOptions)
			.then(response => {
				if (!response.ok) throw new Error('Network response was not ok')
				else return response.json()
			})
			.then(callback)
			.catch(error => {
				console.error('There was a problem with the fetch operation:', error);
				alert('Đã có lỗi xảy ra trong quá trình lấy role.');
			});
	}

	function renderRole(roles) {
		const listRoleResult = roles.result;
		listRoles.innerHTML = ''

		listRoleResult.forEach((role) => {
			if (role.name !== "USER")
				listRoles.innerHTML += `<option>${role.name}</option>`
			else
				//để user làm giá trị mạc định cho thẻ select
				listRoles.innerHTML += `<option selected>${role.name}</option>`
		})
	}

	//lấy dữ liệu 

	//lấy dữ liệu 
	const getUserApi = `${baseUrl}/users/${userId}`
	const getUserOptions = {
		method: 'GET',
		headers: {
			'Authorization': `Bearer ${token}`,
			'Content-Type': 'application/json',
		},
	}


	$(document).ready(function() {
		getUserInfo(renderInfo);
	});


	function getUserInfo(callback) {
		fetch(getUserApi, getUserOptions)
			.then(response => {
				if (!response.ok) {
					throw new Error('Network response was not ok');
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
		if (user.result) {
			$('#input-username').text(user.result.username);
			$('#input-name').val(user.result.name);
			$('#input-dob').val(user.result.dob);
			$('#input-email').val(user.result.email);
			$('#input-phone').val(user.result.phone);
			
		} else {
			alert('Dữ liệu người dùng không tồn tại.');
		}

	}


	//thêm đường link cho nút huỷ
	btnCancel.setAttribute("href", `${baseUrl}/admin/getusers`);

	//validate
	password.addEventListener('blur', () => { testPasword() })
	passwordAgain.addEventListener('blur', () => { testPaswordAgain() })
	email.addEventListener('blur', () => { testEmail() })

	function testPasword() {
		var warn = document.querySelector("#input-passwordWarn")
		warn.innerHTML = "Mật khẩu ít nhất 6 ký tự";
		if (password.value.length < 6) {
			warn.classList.remove('nonWarn');
			warn.classList.add('warn');
			password.classList.add('is-invalid');
			return false;
		} else {
			warn.classList.remove('warn');
			warn.classList.add('nonWarn');
			password.classList.remove('is-invalid');
			return true;
		}
	}

	function testPaswordAgain() {
		var warn = document.querySelector("#input-passwordAgainWarn")
		warn.innerHTML = "Mật khẩu không trùng khớp";
		if (password.value.length !== passwordAgain.value.length) {
			warn.classList.remove('nonWarn');
			warn.classList.add('warn');
			passwordAgain.classList.add('is-invalid');
			return false;
		} else {
			warn.classList.remove('warn');
			warn.classList.add('nonWarn');
			passwordAgain.classList.remove('is-invalid');
			return true;
		}
	}

	function testEmail() {
		var warn = document.querySelector("#input-emailWarm")
		warn.innerHTML = "Email không hợp lệ";
		var emailRegex = /.+@.+\..+/;
		if (!emailRegex.test(email.value)) {
			warn.classList.remove('nonWarn');
			warn.classList.add('warn');
			email.classList.add('is-invalid');
			return false;
		} else {
			warn.classList.remove('warn');
			warn.classList.add('nonWarn');
			email.classList.remove('is-invalid');
			return true;
		}
	}



	//xử lý nút huỷ
	btnCancel.addEventListener('click', function(event) {
		const userConfirmed = confirm('Bạn có chắc chắn muốn huỷ chỉnh sửa');
		if (!userConfirmed) event.preventDefault()
	})

	//xử lý nút lưu
	btnSave.addEventListener('click', function() {
		//nếu tất cả validete hợp lệ
		testPasword()
		testPaswordAgain()
		testEmail()
		if (testPasword() && testPaswordAgain() && testEmail() ) {
			updaterUser()
			const userConfirmed = confirm('Lưu chỉnh sửa');
			if (userConfirmed) window.history.back();

		}
	})

	function updaterUser() {
		// Lấy giá trị từ các trường input
		let passwordValue = password.value;
		let nameValue = name.value;
		let dobValue = dob.value;
		let emailValue = email.value;
		let phoneValue = phone.value;
		let roleValue = listRoles.value;

		let updateApi = `${baseUrl}/users/admin/${userId}`;

		// Tạo dữ liệu JSON
		let data = {
			password: passwordValue, // Sửa chính tả từ "pasword" thành "password"
			name: nameValue,
			dob: dobValue,
			email: emailValue,
			phone: phoneValue,
			roles: [roleValue]
		};


		let updateOptions = {
			method: 'PUT',
			headers: {
				'Authorization': `Bearer ${token}`,
				'Content-Type': 'application/json',
			},
			body: JSON.stringify(data) // Sử dụng JSON.stringify trực tiếp trên đối tượng data
		};

		fetch(updateApi, updateOptions)
			.then(response => {
				if (!response.ok) {
					return response.json().then(err => { throw new Error(err.message) });
				}
				return response.json();
			})
			.catch(error => {
				// Hiển thị thông báo lỗi cho người dùng
				console.error('Lỗi:', error.message);
				alert(`Đã xảy ra lỗi: ${error.message}`);
				// Ghi lại lỗi nếu cần
			});
	}

})