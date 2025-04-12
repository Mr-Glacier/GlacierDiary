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
function handleLoading(LoadingMessage, statue) {
    const toast = document.getElementById('messageToast');
    toast.textContent = LoadingMessage;
    if (statue) {
        toast.classList.add('loading');
        toast.classList.remove('success');
        toast.classList.remove('error');
    } else {
        toast.classList.remove('loading');
    }
}

// ------ 验证码模块 START ---------
// 刷新验证码
async function refreshCaptcha() {
    const img = document.getElementById('captchaImage');
    try {
        // 发起 GET 请求
        const response = await fetch('/system/captcha/getCaptcha', {
            method: 'GET',
            headers: {
                'Accept': 'application/json', // 指定接收 JSON 数据
            },
        });
        const result = await response.json();
        // 处理成功结果
        if (result.code === 200) { // 假设 Result.success 返回的 code 是 200
            img.src = result.data.image.startsWith('data:image') ? result.data.image : `data:image/png;base64,${result.data.image}`;
            document.getElementById('captchaId').value = result.data.code;
        } else {
            handleError(result.message)
        }
    } catch (error) {
        handleError('获取验证码失败')
    }
}

// 验证码验证
async function validateCaptcha(captchaInput, captchaIdInput) {
    const requestBody = JSON.stringify({
        captcha: captchaInput,
        captchaId: captchaIdInput
    });
    try {
        const response = await fetch('/system/captcha/checkCaptcha', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: requestBody
        });
        // 处理响应
        const result = await response.json();
        if (result.code === 200) {
            return true;
        } else {
            handleError(result.message)
        }
    } catch (error) {
        handleError('验证码验证失败');
    }
}

// ------ 验证码模块 END ---------