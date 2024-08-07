import baseUrl from "../config.js";


document.addEventListener('DOMContentLoaded', () => {
    const loginBtn = document.querySelector('#btn_login');
	
    const username = 'admin';
    const password = 'admin';

    loginBtn.addEventListener('click', async () => {
        var token = await login(username, password);
        console.log(token); // Hoặc xử lý token như bạn muốn
    });

    async function login(name, password) {
        await getToken(name, password, saveTokenWithExpiry);
        return localStorage.getItem('token');
    }

    function saveTokenWithExpiry(token) {
        localStorage.setItem('token', token);
        // Đặt thời gian xóa token
        setTimeout(() => {
            removeToken();
        }, 7200000); // 2 giờ
    }

    function removeToken() {
        localStorage.removeItem('token');
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
                callback(data.result.token);
            } else {
                console.error('Invalid response format:', data);
            }
        } catch (error) {
            console.error('There was a problem with the fetch operation:', error);
        }
     }
    
});
