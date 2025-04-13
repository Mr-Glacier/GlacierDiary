/**
 * @fileOverview OAuth request object.
 */

/**
 * 带认证的通用请求函数
 * @param {string} url - 请求的URL
 * @param {Object} options - 请求选项
 * @returns {Promise<any>} - 返回解析后的数据或null
 */
async function fetchWithAuth(url, options = {}) {
    try {
        // 从localStorage获取token
        const token = localStorage.getItem('GlacierDiaryToken');

        // 设置默认请求头，合并用户提供的headers
        const headers = {
            'Content-Type': 'application/json',
            ...options.headers,
        };

        // 如果存在token，则添加到请求头中
        if (token) {
            headers['Authorization'] = `Bearer ${token}`;
        }

        // 合并最终的请求选项
        const modifiedOptions = {
            ...options,
            headers: headers,
        };

        // 发起请求
        const response = await fetch(url, modifiedOptions);

        // 检查响应状态
        if (!response.ok) {
            if (response.status === 401) {
                console.warn('Token可能已过期或无效，请重新登录');
                localStorage.removeItem('GlacierDiaryToken'); // 清除无效token
                window.location.replace('/login'); // 重定向到登录页面
            } else {
                console.error(`请求失败，状态码: ${response.status}`);
            }
            return null;
        }

        // 解析JSON响应
        const data = await response.json();

        // 检查业务逻辑状态码（假设后端返回code字段）
        if (data.code !== 200) {
            console.error(`业务逻辑错误: ${data.message || '未知错误'}`);
            return null;
        }

        return data;
    } catch (error) {
        console.error('请求过程中发生错误:', error.message || error);
        handleError(error); // 调用全局错误处理函数
        return null;
    }
}