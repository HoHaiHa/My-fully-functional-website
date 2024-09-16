import baseUrl from "../config.js";


document.addEventListener('DOMContentLoaded', () => {
	const loginBtn = document.querySelector('#btn_login');



	loginBtn.addEventListener('click', async () => {
		const username = 'admin';
		const password = 'admin';
		var token = await login(username, password);
		var userId = localStorage.getItem('userId');
		console.log(token);
		console.log(userId);
	});

	async function login(name, password) {
		await getToken(name, password, saveTokenWithExpiry);
		return localStorage.getItem('token');
	}

	function saveTokenWithExpiry(token, userId) {
		localStorage.setItem('token', token);
		localStorage.setItem('userId', userId);
		// Đặt thời gian xóa token
		setTimeout(() => {
			removeToken();
		}, 7200000); // 2 giờ
	}

	function removeToken() {
		localStorage.removeItem('token');
		localStorage.removeItem('userId');
	}

	async function getToken(username, password, callback) {
		const tokenApi = `${baseUrl}/auth/token`;

		const options = {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json', // Định dạng nội dung
			},
			body: JSON.stringify({
				// Dữ liệu bạn muốn gửi
				username: username,
				password: password,
			}),
		};

		try {
			const response = await fetch(tokenApi, options);
			if (!response.ok) {
				throw new Error('Network response was not ok');
			}
			const data = await response.json();
			if (data.code === 1000 && data.result && data.result.token) {
				callback(data.result.token,data.result.userId);
			} else {
				console.error('Invalid response format:', data);
			}
		} catch (error) {
			console.error('There was a problem with the fetch operation:', error);
		}
	}

});
