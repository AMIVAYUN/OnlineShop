function login(){
    const form = document.login_form;
    const chkUsername = checkValidUsername(form);
    const chkPw = checkValidPassword(form);

    if(chkUsername){
        document.getElementById('alert_username').innerText = "";

    }
}