var title_btn1_state = true;
var title_btn2_state = false;

$(document).ready(function(){
    SessionCheck();
    SearchSetting();
    logoSetting();
    nameCheckMypage();
    mypageSetting();
    $('input').attr('autocomplete','off');

    $('#mypage_title_container').hover(function(){
        $('#mypage_userinfo_btn').css('color', '#fff');
        $(this).css('backgroundColor', '#212425');
        $(this).css('border-bottom', '5px solid #212425');
        $(this).css('cursor', 'pointer');
    }, function(){
        $('#mypage_userinfo_btn').css('color', 'black');
        $(this).css('backgroundColor', '#fff');
        $(this).css('border-bottom', '5px solid #fff');
        $(this).css('cursor', 'default');
    })
    $('#mypage_title_container2').hover(function(){
        $('#mypage_order_btn').css('color', '#fff');
        $(this).css('backgroundColor', '#212425');
        $(this).css('border-bottom', '5px solid #212425');
        $(this).css('cursor', 'pointer');
    }, function(){
        $('#mypage_order_btn').css('color', 'black');
        $(this).css('backgroundColor', '#fff');
        $(this).css('border-bottom', '5px solid #fff');
        $(this).css('cursor', 'default');
    })
})

function show_userinfo(){
    var con1 = document.getElementById("mypage_userinfo");
    var con2 = document.getElementById("mypage_order");
    var btn1 = document.getElementById("mypage_title_container");
    var btn2 = document.getElementById("mypage_title_container2");
    if (title_btn1_state == false){
        con1.style.display = "block";
        btn1.style.zIndex = 2;
        con2.style.display = "none";
        btn2.style.zIndex = 0;
        title_btn1_state = true; title_btn2_state = false;
    }
}
function show_order(){
    var con1 = document.getElementById("mypage_userinfo");
    var con2 = document.getElementById("mypage_order");
    var btn1 = document.getElementById("mypage_title_container");
    var btn2 = document.getElementById("mypage_title_container2");
    if (title_btn2_state == false){
        con1.style.display = "none";
        btn1.style.zIndex = 0;
        con2.style.display = "block";
        btn2.style.zIndex = 2;
        title_btn1_state = false; title_btn2_state = true;
    }
}

async function SessionCheck(){
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
    }
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

function logoSetting(){
    $("#logo").click(function(){
        var baseurl=window.location;
        console.log(baseurl .protocol +"//"+baseurl .host);
        window.location.assign(baseurl .protocol +"//"+baseurl .host);
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
                document.getElementById("members_ref_address").value = extraAddr;

            } else {
                document.getElementById("members_ref_address").value = '';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('members_postcode').value = data.zonecode;
            document.getElementById("members_address").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("members_detailAddress").focus();
        }
    }).open();
}

async function nameCheckMypage(){
    var mypageUrl="/api/v1/members/is-who";
    const usernameRes = await fetch(mypageUrl, {method:"GET"}).then((response) => response.text());
    var url="/api/v1/members/single/local/aboutMe";
    const userinfoRes = await fetch(url, {method:"get"}).then((response) => response.json());
    fetch(url).then((response) => console.log(response));
    $("#members_username").val(usernameRes);
    $("#members_name").val(userinfoRes.name);
    $("#members_phone_num").val(userinfoRes.phone_num);
    $("#members_email").text(userinfoRes.email);
    $("#members_postcode").val(userinfoRes.address.postcode);
    $("#members_address").val(userinfoRes.address.address);
    $("#members_detailAddress").val(userinfoRes.address.detailAddress);
    $("#members_ref_address").val(userinfoRes.address.ref_address);
}

function checkValidPassword() {
    $("#members_password").css("color","black");
    $("#members_password_outer").css("border","1px solid black");
    if ($("#members_password").val() == "") {
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

function purposeSetting(){

    $("#purpose").click(function (){
        const emailproof=sessionStorage.getItem("email");

        if(checkValidName()&&checkValidPassword()&&checkValidPhone()&&checkValidUsername()&&emailproof&&checkAddress()&&checkTerms()){

            JoinbyLocal();
        }

    })
}

async function JoinbyLocal(){
    var baseurl=window.location;
    url=baseurl .protocol +"//"+baseurl .host+"/join-proc";
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
    await fetch(url,{method:"post",headers:{'Content-Type': 'application/json'},body:JSON.stringify(obj)}).then(response => {
        if(response.status==200){
            alert("회원가입이 완료되었습니다.");
            location.assign("/");
        }else{
            alert("오류가 발생하였습니다. 잠시후에 다시 시도해주세요");
        }
    })
}

function mypageSetting(){
    var baseurl=window.location;

    $("#comp_mypage").click(function (){
    if($("#login-navi").text()=="로그인"){
            alert("로그인이 필요한 서비스 입니다.")
    }else{
        var url=baseurl .protocol +"//"+baseurl .host+"/mypage"
        location.assign(url);
        }
    })

    $("#mypage").click(function (){
        if($("#login-navi").text()=="로그인"){
            alert("로그인이 필요한 서비스 입니다.")
        }else{
            var url=baseurl .protocol +"//"+baseurl .host+"/mypage"
            location.assign(url);
        }
    })
}