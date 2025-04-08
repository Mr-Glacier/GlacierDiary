// 登录所用相关 JS代码

function displayMessage(message, type) {
    // 获取 toast 元素
    const toast = document.getElementById('toast');
    // 设置消息内容
    toast.textContent = message;
    // 添加对应的类型类名
    if (type === 'error') {
        toast.classList.add('error');
        toast.classList.remove('success');
    } else if (type === 'success') {
        toast.classList.add('success');
        toast.classList.remove('error');
    }
    toast.style.display = 'block';
    // 自动隐藏 toast（例如 3 秒后）
    setTimeout(() => {
        toast.style.display = 'none';
    }, 3000);
}

// 示例：处理错误信息时调用
function handleError(errorMessage) {
    displayMessage(errorMessage, 'error');
}

// 示例：处理成功信息时调用
function handleSuccess(successMessage) {
    displayMessage(successMessage, 'success');
}


// Function 1 : 刷新验证码
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
        // 检查响应状态码
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
        // 处理成功结果
        if (data.code === 200) { // 假设 Result.success 返回的 code 是 200
            img.src = data.data.image.startsWith('data:image') ? data.data.image : `data:image/png;base64,${data.data.image}`;
            document.getElementById('captchaId').value = data.data.code;
        } else {
            handleError('获取验证码失败')
        }
    } catch (error) {
        handleError('获取验证码失败')
    }
}

// Function 2 : 登录校验(校验用户名、密码、验证码不为空)
async function validateForm() {
    console.log('调用校验方法 1')
    // 获取表单元素
    const account = document.getElementsByName('account')[0];
    const userPassword = document.getElementsByName('userPassword')[0];
    const captcha = document.getElementById('captcha');
    const captchaId = document.getElementById('captchaId').value;
    // 清空之前的错误信息
    const errorMessageDiv = document.getElementById('errorMessage');
    errorMessageDiv.innerHTML = '';
    hideErrorMessage(errorMessageDiv);

    // 检查用户名是否为空
    if (account.value === '') {
        handleError('用户名不可为空')
        account.focus();
        return false;
    }

    // 检查密码是否为空
    if (userPassword.value === '') {
        handleError('密码不可为空')
        userPassword.focus();
        return false;
    }

    // 检查验证码是否为空
    if (captcha.value === '') {
        handleError('验证码不可为空')
        captcha.focus();
        await refreshCaptcha();
        return false;
    }
    // 如果所有字段都已填写，并且验证码有效，则允许提交表单
    return true;
}


//  Function 3 : 验证码校验
async function validateCaptcha(captchaInput, captchaIdInput) {

    console.log('调用校验方法 2')

    const errorMessageDiv = document.getElementById('errorMessage');
    errorMessageDiv.innerHTML = '';
    hideErrorMessage(errorMessageDiv);

    // 创建请求体
    const requestBody = JSON.stringify({
        captcha: captchaInput,
        captchaId: captchaIdInput
    });

    try {
        const response = await fetch('/api/captcha/checkCaptcha', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: requestBody
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        // 尝试解析JSON并捕获任何潜在的解析错误
        const data = await response.json();
        if (data && data.code === "200" && data.data.valid) {
            console.log("验证码有效");
            return true;
        } else {
            displayError('验证码无效，请重试', errorMessageDiv);
            return false;
        }
    } catch (error) {
        // 网络错误或服务器错误时的操作
        displayError('网络错误，请稍后再试', errorMessageDiv);
        console.error('There has been a problem with your fetch operation:', error);
        return false;
    }
}



