var title_btn1_state = true;
var title_btn2_state = false;
const csrfToken = $('meta[name="_csrf"]').attr('content');

$(document).ready(function(){
    chkIE();
    SessionCheck();
    SearchSetting();
    logoSetting();
    nameCheckMypage();
    //mypageSetting();
    getOrderedItem();
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
function chkIE(){
    if (window.navigator.userAgent.match(/MSIE|Internet Explorer|Trident/i)) {
        window.open("microsoft-edge:" + window.location.href);
        window.location = 'https://support.microsoft.com/ko-kr/office/%ec%97%b0%ea%b2%b0%ed%95%98%eb%a0%a4%eb%8a%94-%ec%9b%b9-%ec%82%ac%ec%9d%b4%ed%8a%b8%ea%b0%80-internet-explorer%ec%97%90%ec%84%9c-%ec%9e%91%eb%8f%99%ed%95%98%ec%a7%80-%ec%95%8a%ec%8a%b5%eb%8b%88%eb%8b%a4-8f5fc675-cd47-414c-9535-12821ddfc554?ui=ko-kr&rs=ko-kr&ad=kr';
    }
}
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
        var baseurl = window.location;
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

async function getOrderedItem(){
    var url="/api/v1/payments/all?offset=0&&limit=30";
    const res=await fetch(url,{method:"get"}).then(response => response.json());
    var sum = 0;
    await $.each(res, function(idx1){
        var innerhtmlSum='';
        $.each(res[idx1].itemDtoList, function(idx2){
            var priceComma = res[idx1].itemDtoList[idx2].price;
            var totalComma = res[idx1].itemDtoList[idx2].count * res[idx1].itemDtoList[idx2].price;
            if(res[idx1].type=="ACTIVATE"){
                sum = sum + totalComma;

            }
            totalComma = totalComma.toLocaleString();
            priceComma = priceComma.toLocaleString();

            var innerhtml='<tr class="component" id='+res[idx1].paymentKey+'>\n' +
            '                    <td class="mer_img"><img src='+res[idx1].itemDtoList[idx2].imgSrc+'></td>\n' +
            '                    <td class="name" id='+res[idx1].itemDtoList[idx2].orderitem_id+'>'+res[idx1].itemDtoList[idx2].itemname+'</td>\n' +
                '                <td class="date">'+    res[idx1].approvedAt+'</td>'+
            '                    <td class="count">'+res[idx1].itemDtoList[idx2].count+'개</td>\n' +
            '                    <td class="price">'+priceComma+'</td>\n' +
            '                    <td class="total">'+totalComma+'원</td></tr>\n'
            innerhtmlSum = innerhtmlSum +'\n'+ innerhtml;
        })
        innerhtmlSum = innerhtmlSum + '\n' +'<tr style="text-align: center;font-family: SUIT-Medium" id='+res[idx1].paymentKey+'>\n' + checkStatus(res[idx1].type)

        $("#shoppinglist").append(innerhtmlSum);
    })
    sum = sum.toLocaleString();
    $("#totalpricevalue").text(sum);
}
function checkStatus(status){
    if(status=="CANCELED"){

        return '<td className="confirm" colSpan="2" style=" text-align: center;border-bottom: 3px solid #212425; padding-bottom:10px;">\n' +
            '            환불처리 된 주문입니다.\n' +
            '        </td>' +
            '<td class="cancel" colspan="3" style="border-bottom: 3px solid #212425;text-align: center;font-size: 13px; padding-bottom:10px;">문의사항은 010-3141-5278로 문의해주세요</td></tr>\n'

    }else if (status=="COMPLETE"){
        return '<td className="confirm" colSpan="2" style=" text-align: center;border-bottom: 3px solid #212425; padding-bottom:10px;">\n' +
            '            완료된 거래입니다.\n' +
            '        </td>' +
            '<td class="cancel" colspan="3" style="border-bottom: 3px solid #212425;text-align: center; padding-bottom:10px;">이용해주셔서 감사합니다</td></tr>\n'
    }else if (status=="DELIVERY"){
        return  '<td className="confirm" colSpan="2" style=" text-align: center;border-bottom: 3px solid #212425; padding-bottom:10px;">\n' +
            '            배송중인 거래입니다..\n' +
            '        </td>' +
            '<td class="cancel" colspan="3" style="border-bottom: 3px solid #212425;text-align: center; padding-bottom:10px;">환불 문의사항은 010-6348-5278로 연락주세요.</td></tr>\n'

    }
    else{
        return '     <td class="confirm" colspan="2" style="border-bottom: 3px solid #212425; padding-bottom:10px;"><button class="confirm_btn" onclick="checkGet(this)">인수 확인</button></td>\n' +
            '     <td class="cancel" colspan="3" style="border-bottom: 3px solid #212425; padding-bottom:10px;"><button class="cancel_btn"onclick="cancelOrder(this)">결제취소</button></td></tr>\n'
    }
}
async function checkGet(vari){
    if(confirm("물품을 받으셨습니까?")){
        var baseurl=window.location;
        var url=baseurl .protocol +"//"+baseurl .host+ "/payments/doCompleteTrade/"+$(vari).closest("tr").attr("id")
        const res=await fetch(url,{method:"post",headers:{'X-CSRF-TOKEN': csrfToken}})
        if(res.status==200){
            alert("인수 확인이 완료되었습니다. 이용해주셔서 감사합니다.")
            location.reload()
        }else if(res.status==400){
            alert("권한이 없습니다.")
            location.reload()
        }else{
            alert("내부 서버에 에러가 발생하였습니다.");
            location.reload()
        }
    }


}
function cancelOrder(vari){
    if(confirm("환불 하시겠습니까?")){
        $("#paymentKey").val($(vari).closest("tr").attr("id"));
        modalOpen();
    }

}
function modalClose(){
    var modal = document.getElementById('modal');
    modal.style.display = 'none';
}
function modalOpen(){
    var modal = document.getElementById('modal');
    modal.style.display = 'block';
}
async function dopayCancel(){
    if($("#cancelReason").val()){
        var baseurl=window.location;
        var url=baseurl .protocol +"//"+baseurl .host+"/payments/doCancel/"+$("#paymentKey").val();
        let obj={
            cancelReason:$("#cancelReason").val()
        }
        const res=await fetch(url,{method:"post",headers:{'Content-Type':'application/json','X-CSRF-TOKEN': csrfToken},body:JSON.stringify(obj)});
        if(res.status==200){
            alert("결제 취소가 완료되었습니다.");
            location.reload();
        }else{
            alert("서버 내부에 문제가 있습니다. 잠시 후 다시 시도해주세요.");
            location.reload();
        }

    }else{
        alert("환불 사유를 적어주세요.");
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
                document.getElementById("extraAddress").value = extraAddr;

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
    fetch(url).then((response) => console.log(response.status));
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
    await fetch(url,{method:"post",headers:{'Content-Type': 'application/json','X-CSRF-TOKEN': csrfToken},body:JSON.stringify(obj)}).then(response => {
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
function checkValidPassword() {
    var pass=$("#members_password").val();
    var passchk=$("#members_password_chk").val();
    if(pass != passchk){
        alert("비밀번호와 확인 칸의 값이 다릅니다.");

        return false;
    }
    if ($("#members_password").val() == "") {
        alert("비밀 번호의 공백을 없애주세요")
        return false;
    }

    const pw = $("#members_password").val();
    // String.prototype.search() :: 검색된 문자열 중에 첫 번째로 매치되는 것의 인덱스를 반환한다. 찾지 못하면 -1 을 반환한다.
    // number

    if (pw.length < 6) {
        // 최소 6문자.
        alert("비밀번호는 여섯 글자 이상 이어야 합니다.")
        return false;
    } else if (pw.search(/\s/) != -1) {
        // 공백 제거.
        alert("비밀번호에 공백은 제거해주세요")
        return false;
    }

    return true;
}
function chkValidAddr(){
    if($("#members_address").val() &&$("#members_postcode").val() && $("#members_detailAddress").val() && $("#members_ref_address").val()){
        return true;
    }else{
        return false;
    }
}
function chkValidPhone(){
    var phonenum=$("#members_phone_num").val();
    var isnum = /^\d+$/.test(phonenum);
    if(isnum){
        if(phonenum.length>9 && phonenum.length<13){
            return true;
        }else{
            alert("유효한 길이에 맞게 전화번호를 작성해주세요 ex)01012345678");
        }
    }else{
        alert("번호에는 숫자만 넣어주세요");
        return false;
    }

}
async function changePwd(){
    if(checkValidPassword()){
        let obj={
            str:$("#members_password").val()
        }
        var baseurl=window.location;
        var url=baseurl .protocol +"//"+baseurl .host+"/api/v1/members/updatePwd";
        await fetch(url,{method:"post",headers:{'Content-Type':'application/json','X-CSRF-TOKEN': csrfToken},body:JSON.stringify(obj)}).then(
            response => {
                if(response.status==400){
                    alert("잘못된 접근입니다");
                }
                else if(response.status=200){
                    alert("변경에 성공하였습니다.");
                }else{
                    alert("내부 서버에 장애가 있습니다. 잠시 후 다시 시도해주세요");
                }
            }
        )
    }
}

async function changeAddr(){
    if(chkValidAddr()){
        let obj={
            postcode:$("#members_address").val(),
            address:$("#members_address").val(),
            detailAddress:$("#members_detailAddress").val(),
            ref_address:$("#members_ref_address").val()
        }
        var baseurl=window.location;
        var url=baseurl .protocol +"//"+baseurl .host+"/api/v1/members/updateAddress";
        await fetch(url,{method:"post",headers:{'Content-Type':'application/json','X-CSRF-TOKEN': csrfToken},body:JSON.stringify(obj)}).then(
            response => {
                if(response.status==400){
                    alert("잘못된 접근입니다");
                }
                else if(response.status=200){
                    alert("변경에 성공하였습니다.");
                }else{
                    alert("내부 서버에 장애가 있습니다. 잠시 후 다시 시도해주세요");
                }
            }
        )
    }
}
async function changeNum(){
    if(chkValidPhone()){
        let obj={
            str:$("#members_phone_num").val()
        }
        var baseurl=window.location;
        var url=baseurl .protocol +"//"+baseurl .host+"/api/v1/members/updatePhonenum";
        await fetch(url,{method:"post",headers:{'Content-Type':'application/json','X-CSRF-TOKEN': csrfToken},body:JSON.stringify(obj)}).then(
            response => {
                if(response.status==400){
                    alert("잘못된 접근입니다");
                }
                else if(response.status=200){
                    alert("변경에 성공하였습니다.");
                }else{
                    alert("내부 서버에 장애가 있습니다. 잠시 후 다시 시도해주세요");
                }
            }
        )
    }
}