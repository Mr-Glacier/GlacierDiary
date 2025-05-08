// GlacierDiary 系统后台JS
// 跳转新增文章
/**
 * 跳转到新增文章页面
 */
async function goToAddArticle() {
    try {
        const response = await fetchWithAuth('/api/admin/article/draft/add', {
            method: 'GET',
        });

        if (!response) {
            console.error('未能获取有效的响应');
            return;
        }

        // 检查业务逻辑状态码
        if (response.code === 200 && response.data) {
            // 打开新窗口跳转到编辑页面
            window.open(`/admin/article/edit?id=${response.data}&type=draft`, '_blank');
        } else {
            console.error('服务器返回了无效的数据:', response);
        }
    } catch (error) {
        console.error('跳转到新增文章页面时出错:', error.message || error);
    }
}

async function logout() {
    try {
        const response = await fetchWithAuth('/api/system/oauth/logout', {
            method: 'GET',
        });
        if (!response) {
            console.error('未能获取有效的响应');
            return;
        }
        if (response.code === 200) {
            localStorage.removeItem('GlacierDiaryToken');
            // 跳转到系统登录页面
            window.location.replace('/login');
        } else {
            console.error('服务器返回了无效的数据:', response);
        }
    } catch (error) {
        console.error('退出登录时出错:', error.message || error);
    }
}
