// 登录所用相关 JS代码

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




