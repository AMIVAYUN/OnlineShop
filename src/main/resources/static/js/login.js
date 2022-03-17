
function openOauth2(){
    location.assign("/oauth2/authorization/google");
}

function login(){
    const form = document.login_form;
    const chkUsername = checkValidUsername(form);
    const chkPw = checkValidPassword(form);
}


