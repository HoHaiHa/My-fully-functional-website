import baseUrl from "../config.js";

document.addEventListener('DOMContentLoaded', () => {
	//lấy token từ localStorage
	const token = localStorage.getItem('token');
	if (token === null) {
		throw new Error('login error');
	}
	const username = document.querySelector("#input-username");
	const password = document.querySelector("#input-password");
	const passwordAgain = document.querySelector("#input-passwordAgain");
	const email = document.querySelector("#input-email");

	const name = document.querySelector("#input-name");
	const dob = document.querySelector("#input-dob");
	const phone = document.querySelector("#input-phone");
	const listRoles = document.querySelector("#input_role");

	const btnSave = document.querySelector("#btn_save");
	const btnCancel = document.querySelector("#btn_cancel");

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

	//thêm đường link cho nút huỷ
	btnCancel.setAttribute("href", `${baseUrl}/admin/getusers`);

	//validate
	username.addEventListener('blur', () => { testUsername() });
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

	function testUsername() {
		var warn = document.querySelector("#input-usernameWarn");
		warn.innerHTML = "Tên đăng ít nhất 4 ký tự";
		if (username.value.length < 4) {
			warn.classList.remove('nonWarn');
			warn.classList.add('warn');
			username.classList.add('is-invalid');
			return false;
		} else {
			warn.classList.remove('warn');
			warn.classList.add('nonWarn');
			username.classList.remove('is-invalid');
			return true;
		}
	}

	//xử lý nút huỷ
	btnCancel.addEventListener('click', function(event) {
		const userConfirmed = confirm('Bạn có chắc chắn muốn huỷ thêm người dùng không?');
		if (!userConfirmed) event.preventDefault()
	})

	//xử lý nút lưu
	btnSave.addEventListener('click', function() {
		//nếu tất cả validete hợp lệ
		testPasword()
		testPaswordAgain()
		testEmail()
		testUsername()
		if (testPasword() && testPaswordAgain() && testEmail() && testUsername()) {
			console.log(listRoles.value)
			addUser()
			const userConfirmed = confirm('Có tiếp tục thêm người');
			if(!userConfirmed) window.history.back();

		}
	})

	function addUser() {
		// Lấy giá trị từ các trường input
		let usernameValue = username.value;
		let passwordValue = password.value;
		let nameValue = name.value;
		let dobValue = dob.value;
		let emailValue = email.value;
		let phoneValue = phone.value;
		let roleValue = listRoles.value;
		
		

		let addUserApi = `${baseUrl}/users/admincreate`;

		// Tạo dữ liệu JSON
		let data = {
			username: usernameValue,
			password: passwordValue, // Sửa chính tả từ "pasword" thành "password"
			name: nameValue,
			dob: dobValue,
			email: emailValue,
			phone: phoneValue,
			roles: [roleValue]
		};


		let addUserOptions = {
			method: 'POST',
			headers: {
				'Authorization': `Bearer ${token}`,
				'Content-Type': 'application/json',
			},
			body: JSON.stringify(data) // Sử dụng JSON.stringify trực tiếp trên đối tượng data
		};

		fetch(addUserApi, addUserOptions)
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