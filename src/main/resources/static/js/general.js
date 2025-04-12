function displayMessage(message, type) {
    // 获取 toast 元素
    const toast = document.getElementById('messageToast');
    // 设置消息内容
    toast.textContent = message;
    // 添加对应的类型类名
    if (type === 'error') {
        toast.classList.add('error');
        toast.classList.remove('success');
        toast.classList.remove('loading');
    } else if (type === 'loading') {
        toast.classList.add('loading')
        toast.classList.remove('success');
        toast.classList.remove('error');
    } else if (type === 'success') {
        toast.classList.add('success');
        toast.classList.remove('error');
        toast.classList.remove('loading');
    }
    toast.style.display = 'block';
    // 自动隐藏 toast（例如 3 秒后）
    setTimeout(() => {
        toast.style.display = 'none';
    }, 5000);
}

// 示例：处理错误信息时调用
function handleError(errorMessage) {
    displayMessage(errorMessage, 'error');
}

// 示例：处理成功信息时调用
function handleSuccess(successMessage) {
    displayMessage(successMessage, 'success');
}
// 短暂加载信息
function handleLoading(LoadingMessage,statue) {
    const toast = document.getElementById('messageToast');
    toast.textContent = LoadingMessage;
    if (statue) {
        toast.classList.add('loading');
        toast.classList.remove('success');
        toast.classList.remove('error');
    }else {
        toast.classList.remove('loading');
    }
}