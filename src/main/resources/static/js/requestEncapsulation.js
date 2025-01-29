// requestEncapsulation.js
// 封装需要授权的请求

async function fetchWithAuth(url, options = {}) {
    // 从localStorage获取token
    const token = localStorage.getItem('authToken');

    // 设置默认请求头，如果已有headers则合并
    const headers = {
        'Content-Type': 'application/json',
        ...options.headers
    };

    if (token) {
        headers['Authorization'] = `Bearer ${token}`;
    }

    const modifiedOptions = {
        ...options,
        headers: headers
    };

    try {
        const response = await fetch(url, modifiedOptions);

        // 处理未授权情况
        if (response.status === 401) {
            console.warn('Token可能已过期或无效，请重新登录');
            // 清除无效token
            localStorage.removeItem('authToken');
            // 可以在这里重定向到登录页面或显示提示信息
            window.location.replace('/admin') ; // 重定向到登录页面
            return null; // 返回null或其他适当的值
        }

        if (response.status === 403) {
            console.warn('权限不足，请重新登录或联系管理员');
            window.location.replace('/client/home') ;
            return null;
        }


        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        return await response.json();
    } catch (error) {
        console.error('请求过程中发生错误:', error);
        displayError('网络错误，请稍后再试');
        return null; // 或者根据实际情况返回其他值
    }
}