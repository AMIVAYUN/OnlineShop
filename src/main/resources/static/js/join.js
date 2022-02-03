function join() {
    const form = document.join_form;
    const chkID = checkValidID(form);
    const chkPw = checkValidPassword(form);
    const chkEmail = checkValidEmail(form);
    const chkName = checkValidName(form);
    const chkPhone = checkValidPhone(form);

    if (chkID) {
        document.getElementById('alert_ID').innerText = "";
        form.ID.style.border = '2px solid';
        form.ID.style.borderColor = '#00D000';
    } else {
        form.ID.style.border = '2px solid';
        form.ID.style.borderColor = '#FF0000';
        document.getElementById('alert_ID').style.color = '#FF0000';
    }

    if (chkPw) {
        document.getElementById('alert_password').innerText = "";
        form.password.style.border = '2px solid';
        form.password.style.borderColor = '#00D000';
    } else {
        form.password.style.border = '2px solid';
        form.password.style.borderColor = '#FF0000';
        document.getElementById('alert_password').style.color = '#FF0000';
    }

    if (chkEmail) {
        document.getElementById('alert_email').innerText = "";
        form.email.style.border = '2px solid';
        form.email.style.borderColor = '#00D000';
    } else {
        form.email.style.border = '2px solid';
        form.email.style.borderColor = '#FF0000';
        document.getElementById('alert_email').style.color = '#FF0000';
    }

    if (chkName) {
        document.getElementById('alert_name').innerText = "";
        form.name.style.border = '2px solid';
        form.name.style.borderColor = '#00D000';
    } else {
        form.name.style.border = '2px solid';
        form.name.style.borderColor = '#FF0000';
        document.getElementById('alert_name').style.color = '#FF0000';
    }

    if (chkPhone) {
        document.getElementById('alert_phone').innerText = "";
        form.phone.style.border = '2px solid';
        form.phone.style.borderColor = '#00D000';
    } else {
        form.phone.style.border = '2px solid';
        form.phone.style.borderColor = '#FF0000';
        document.getElementById('alert_phone').style.color = '#FF0000';
    }
}

function checkValidID(form) {
    if (form.ID.value == "") {
        document.getElementById('alert_ID').innerText = "필수 정보입니다.";
        //form.ID.focus();
        return false;
    }

    return true;
}

function checkValidPassword(form) {
    if (form.password.value == "") {
        document.getElementById('alert_password').innerText = "필수 정보입니다.";
        form.password.focus();
        return false;
    }

    const pw = form.password.value;
    // String.prototype.search() :: 검색된 문자열 중에 첫 번째로 매치되는 것의 인덱스를 반환한다. 찾지 못하면 -1 을 반환한다.
    // number

    if (pw.length < 6) {
        // 최소 6문자.
        document.getElementById('alert_password').innerText = "비밀번호는 6글자 이상이어야 합니다.";
        return false;
    } else if (pw.search(/\s/) != -1) {
        // 공백 제거.
        document.getElementById('alert_password').innerText = "공백 없이 입력해 주세요";
        return false;
    }

    return true;
}

function checkValidEmail(form) {
    if (form.email.value == "") {
        document.getElementById('alert_email').innerText = "필수 정보입니다.";
        //form.email.focus();
        return false;
    }

    const exptext = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;

    // "ㅁ@ㅁ.ㅁ" 이메일 형식 검사.
    if (exptext.test(form.email.value) === false) {
        document.getElementById('alert_email').innerText = "유효하지 않은 이메일입니다.";
        //form.email.select();
        return false;
    }

    return true;
}

function checkValidName(form) {
    if (form.name.value == "") {
        document.getElementById('alert_name').innerText = "필수 정보입니다.";
        //form.name.focus();
        return false;
    }

    return true;
}

function checkValidPhone(form) {
    if (form.phone.value == "") {
        document.getElementById('alert_phone').innerText = "전화번호를 입력하세요.";
        //form.phone.focus();
        return false;
    }

    return true;
}