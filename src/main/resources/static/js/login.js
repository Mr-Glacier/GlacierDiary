// 登录所用相关 JS代码

// Function 1 : 刷新验证码
function refreshCaptcha() {
    const img = document.getElementById('captchaImage');
    // 添加时间戳来模拟刷新效果
    img.src = '/static/images/captcha-placeholder.png?' + new Date().getTime();
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
        displayError('用户名不可为空', errorMessageDiv);
        account.focus();
        return false;
    }

    // 检查密码是否为空
    if (userPassword.value === '') {
        displayError('密码不可为空', errorMessageDiv);
        userPassword.focus();
        return false;
    }

    // 检查验证码是否为空
    if (captcha.value === '') {
        displayError('验证码不可为空', errorMessageDiv);
        captcha.focus();
        refreshCaptcha();
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



