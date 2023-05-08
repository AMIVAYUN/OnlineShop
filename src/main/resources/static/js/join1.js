var count=0;
const csrfToken = $('meta[name="_csrf"]').attr('content');
$(document).ready(function(){

    //SessionCheck();
    SearchSetting();
    logoSetting();
    //mypageSetting();
    emailProveSetting();
    purposeSetting();
})

async function SessionCheck(){
    //shoplist 세팅 포함
    var baseurl=window.location;

    const res1=await fetch("/api/v1/members/session",{method:"GET"}).then(response => response.json());
    if(!res1.isauth){
        return false;
    }
    if(res1.iswhom !="[ROLE_ADMIN]"){
        $("#manager").remove();
    }
    if(res1.iswhom !="[ROLE_ANONYMOUS]"){
        $("#login-navi").text(res1.iswho + "님 안녕하세요");
        $("#login-navi").attr("href","#")
        $("#join-navi").text("로그아웃");
        $("#join-navi").attr("href","/logout");
        $("#scart").click(function (){
            window.location.assign(baseurl .protocol +"//"+baseurl .host+"/shopping-list");
        });
        $("#comp_mypage").click(function (){
            var url=baseurl .protocol +"//"+baseurl .host+"/mypage"
            location.assign(url);
        });
        $("#mypage").click(function (){
            var url=baseurl .protocol +"//"+baseurl .host+"/mypage"
            location.assign(url);
        });

    }else{
        $("#scart").click(function (){
            alert("로그인이 필요한 서비스 입니다.")
        });

        $("#comp_mypage").click(function (){
            alert("로그인이 필요한 서비스 입니다.")
        });
        $("#mypage").click(function (){
            alert("로그인이 필요한 서비스 입니다.")
        });
    }

}

function logoSetting(){
    $("#logo").click(function(){
        var baseurl=window.location;
        console.log(baseurl .protocol +"//"+baseurl .host);
        window.location.assign(baseurl .protocol +"//"+baseurl .host);
    })

}

function SearchSetting(){
    $("#searchicon").on("click",function(){
        if($("#searchbar").find("input").val()){
            var url="/search/"+$("#searchbar").find("input").val();
            window.location.assign(url);
        }else{
            location.reload();
        }

    })
}


function mypageSetting(){
    var baseurl=window.location;



    $("#mypage").click(function (){
        if($("#login-navi").text()=="로그인"){
            alert("로그인이 필요한 서비스 입니다.")
        }else{
            var url=baseurl .protocol +"//"+baseurl .host+"/mypage"
            location.assign(url);
        }
    })
}

function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            sessionStorage.setItem("juso",true);
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                document.getElementById("extraAddress").value = extraAddr;

            } else {
                document.getElementById("extraAddress").value = '';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('postcode').value = data.zonecode;
            document.getElementById("address").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("detailAddress").focus();
        }
    }).open();
}
function getCookie(name) {
    if (!document.cookie) {
        return null;
    }

    const xsrfCookies = document.cookie.split(';')
        .map(c => c.trim())
        .filter(c => c.startsWith(name + '='));

    if (xsrfCookies.length === 0) {
        return null;
    }
    return decodeURIComponent(xsrfCookies[0].split('=')[1]);
}
function emailProveSetting(){
    $("#email-identify").click(async function page1(){
        if(checkvalidEmail()){
            $("#code").attr("disabled",false);
            var baseurl=window.location;
            var url=baseurl .protocol +"//"+baseurl .host+"/api/v2/mails/confirm?email="+$("#emailvalue").val();
            await fetch(url).then(res => {
                if( res.status == 200 ){
                    alert("이메일이 전송되었습니다.");
                    $("#email-identify").text("코드 인증");
                    $("#emailvalue").attr("disabled",true);
                    $("#email-identify").off("click")
                    $("#email-identify").on("click",page2);
                }else{
                    alert("이메일 전송에 실패하였습니다. 개발팀에 문의해주세요.");
                    $("#code").attr("disabled",true);

                }
            });


        }
    })

}
async function page2(){
    let obj={
        "email":$("#emailvalue").val(),
        "key":$("#code").val()
    }
    const res=await fetch("/api/v2/mails/prove.do",{method:"post",headers:{'Content-Type': 'application/json','X-CSRF-TOKEN': csrfToken},body:JSON.stringify(obj)}).then(res => res.json())
    if(count>4){
        alert("이메일 인증한도를 넘으셨습니다. 처음부터 다시 시도해주세요");
        location.reload();
    }
    if(res.result){

        alert("이메일 인증에 성공하셨습니다.");
        $("#code").attr("disabled",true);
        sessionStorage.setItem("email",true);
    }else{
        alert("이메일 인증에 실패하셨습니다. 코드를 다시 입력해주세요");
        count+=1;
    }
}
function checkvalidEmail(){
    if ($("#emailvalue").val() == "") {
        $("#emailvalue").attr("placeholder","필수 정보입니다.");
        $("#emailvalue").css("border-color","red");
        //form.email.focus();
        return false;
    }

    const exptext = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;

    // "ㅁ@ㅁ.ㅁ" 이메일 형식 검사.
    if (exptext.test($("#emailvalue").val()) === false) {
        $("#emailvalue").val(null);
        $("#emailvalue").attr("placeholder","유효하지 않은 이메일입니다");
        $("#emailvalue").css("border-color","red");
        //form.email.select();
        return false;
    }

    $("#emailvalue").attr("placeholder","이메일을 입력해주세요");
    $("#emailvalue").css("border-color","black");
    return true;
}
function purposeSetting(){

    $("#purpose").click(function (){
        const emailproof=sessionStorage.getItem("email");

        if(checkValidName()&&checkValidPassword()&&checkValidPhone()&&checkValidUsername()&&emailproof&&checkAddress()&&checkTerms()){

            JoinbyLocal();
        }

    })
}
function checkTerms(){
    if($("#info-passAssign").is(":checked")&&$("#ElectricTradeAssign").is(":checked")&&$("#DBCommercialAssgin").is(":checked")){
        return true;
    }else{
        alert("약관에 동의해주세요");
        return false;
    }
}
function checkValidUsername() {
    $("#pid").css("color","black");
    $("#pid").css("border","1px solid black");
    if ($("#pid").val() == "") {
        $("#pid").attr("placeholder","필수 정보 입니다.");
        $("#pid").css("border","3px solid red");
        $("#pid").css("color","red");
        return false;
    }

    return true;
}

function checkValidPassword() {
    $("#ppwd").css("color","black");
    $("#ppwd").css("border","1px solid black");
    if ($("#ppwd").val() == "") {
        $("#ppwd").attr("placeholder","필수 정보 입니다.");
        $("#ppwd").css("border","3px solid red");
        $("#ppwd").css("color","red");
        return false;
    }

    const pw = $("#ppwd").val();
    // String.prototype.search() :: 검색된 문자열 중에 첫 번째로 매치되는 것의 인덱스를 반환한다. 찾지 못하면 -1 을 반환한다.
    // number

    if (pw.length < 6) {
        // 최소 6문자.
        $("#ppwd").val(null);
        $("#ppwd").attr("placeholder","비밀번호는 6글자 이상이어야 합니다.");
        $("#ppwd").css("border","3px solid red");
        $("#ppwd").css("color","red");
        return false;
    } else if (pw.search(/\s/) != -1) {
        // 공백 제거.
        $("#ppwd").val(null);
        $("#ppwd").attr("placeholder","공백(스페이스 바)없이 입력해주세요");
        $("#ppwd").css("border","3px solid red");
        $("#ppwd").css("color","red");
        return false;
    }

    return true;
}



function checkValidName() {
    $("#pname").css("color","black");
    $("#pname").css("border","1px solid black");
    if ($("#pname").val() == "") {
        $("#pname").attr("placeholder","필수 정보 입니다.");
        $("#pname").css("border","3px solid red");
        $("#pname").css("color","red");
        return false;
    }

    return true;
}

function checkValidPhone() {
    if ( ($("#phone1").val() == "") ||($("#phone2").val() == "")||($("#phone3").val() == "")) {
        alert("전화번호를 다시 기입해주세요")
        //form.phone.focus();
        return false;
    }

    return true;
}

function checkAddress(){
    const juso=sessionStorage.getItem("juso")
    if(juso&&($("#detailAddress").val()!=null)){
        return true;
    }
    else{
        alert("주소를 검색해주세요");
        return false;
    }
}
async function checkDup(){
    if($("#pid").val()){
        var baseurl=window.location;
        var url=baseurl .protocol +"//"+baseurl .host+"/api/v1/checkdup?user_id="+$("#pid").val();
        const result=await fetch(url,{method:"get"}).then(response => response.text());
        if(result=="true"){
            alert("중복된 아이디 입니다. 다시 입력해주세요");
        }else{
            alert("중복확인이 완료되었습니다.");
            $("#pid").attr('disabled',true);
        }
    }else{
        alert("아이디를 입력해주세요")
    }


}
async function JoinbyLocal(){
    if($("#pid").attr("disabled")){
        var baseurl=window.location;
        var url=baseurl .protocol +"//"+baseurl .host+"/join-proc";
        let obj={
            "username":$("#pid").val(),
            "password":$("#ppwd").val(),
            "email":$("#emailvalue").val(),
            "name":$("#pname").val(),
            "phone_num":$("#phone1").val()+$("#phone2").val()+$("#phone3").val(),
            "address":{
                "postcode":$("#postcode").val(),
                "address":$("#address").val(),
                "detailAddress":$("#detailAddress").val(),
                "ref_address":$("#extraAddress").val()
            }

        }
        await fetch(url,{method:"post",headers:{'Content-Type': 'application/json','X-CSRF-TOKEN': csrfToken},body:JSON.stringify(obj)}).then(response => {
            if(response.status==200){
                alert("회원가입이 완료되었습니다.");
                location.assign("/");
            }else{
                console.log(response.status);
                alert("오류가 발생하였습니다. 잠시후에 다시 시도해주세요");
            }
        })
    }else{
        alert("중복 확인을 진행해주세요");
    }

}

