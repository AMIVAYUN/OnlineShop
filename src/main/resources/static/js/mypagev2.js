const csrfToken = $('meta[name="_csrf"]').attr('content');
$(document).ready(function(){
    buttonSetting();
    chkIE();
    SessionCheck();
    SearchSetting();
    logoSetting();
    $('input').attr('autocomplete','off');

    getOrderedItem(0,3);
})

function buttonSetting(){
    $("#a_list li a").on("click",function(){
        displayComponent($("#a_list li a").index(this));

    })
}
function displayComponent(index){
    for(var i=0; i<$(".section").length;i++){

        if(i==index){
            $(".section").eq(i).css("display","block");
            listSetting(i);
        }else{
            $(".section").eq(i).css("display","none");
        }

    }


}
function listSetting( idx ){
    switch( idx ){
        case 0:
            getOrderedItem(0,3);
            break;
        case 1:
            getPersonalInfo();
            break;
    }
}
function Cleaning(bodytag){
    bodytag.empty();

}
function chkIE(){
    if (window.navigator.userAgent.match(/MSIE|Internet Explorer|Trident/i)) {
        window.open("microsoft-edge:" + window.location.href);
        window.location = 'https://support.microsoft.com/ko-kr/office/%ec%97%b0%ea%b2%b0%ed%95%98%eb%a0%a4%eb%8a%94-%ec%9b%b9-%ec%82%ac%ec%9d%b4%ed%8a%b8%ea%b0%80-internet-explorer%ec%97%90%ec%84%9c-%ec%9e%91%eb%8f%99%ed%95%98%ec%a7%80-%ec%95%8a%ec%8a%b5%eb%8b%88%eb%8b%a4-8f5fc675-cd47-414c-9535-12821ddfc554?ui=ko-kr&rs=ko-kr&ad=kr';
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
                document.getElementById("address").value = '';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('postcode').value = data.zonecode;
            document.getElementById("address").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("detailAddress").focus();
        }
    }).open();
}

async function getOrderedItem(offset,limit) {
    Cleaning($("#show_pay_list"));
    var baseurl=window.location;
    await fetch(baseurl .protocol +"//"+baseurl .host+"/api/v1/orders/paid/byuser?offset="+offset+"&limit="+limit).then( response => response.json()).then(
        res =>{
            $.each(res, function (idx){
                var innerhtml='<li>\n' +
                    '                        <div class="component" >\n' +
                    '                            <h2>'+res[idx].orderDate.substr(0,10)+'</h2>\n' +
                    '                            <div class="status"><h2>'+getOrderStatusKR(res[idx].status)+'</h2></div>\n' +
                    '                            <div class="order">\n' +
                    '                                <div class="title">\n' +
                    '                                    <div class="order_main"><img class="item_img" src='+res[idx].imgSrc+'><h3>'+res[idx].order_Title+'</h3></div>\n' +
                    '                                    <div class="totalPrice">\n' +
                    '                                        '+res[idx].totalPrice+'\n' +
                    '                                    </div>\n' +
                    '                                </div>\n' +
                    '\n' +
                    '\n' +
                    '                            </div>\n' +
                    '                        </div>\n' +
                    '                        <div>\n' +
                    '                            <div class="component_btn_list">\n' +
                    '                                <button class="show_delivery" id='+res[idx].transport_doc+'>배송 조회</button>\n' +
                    '                                <button class="cancel_order" onclick="cancelOrder(this)" id='+res[idx].paymentKey+'>주문 취소</button>\n' +
                    '                            </div>\n' +
                    '                        </div>\n' +
                    '\n' +
                    '                    </li>'
                $("#show_pay_list").append(innerhtml)
            })

        }
    )


}
function cancelOrder(vari){
    if(confirm("환불 하시겠습니까?")){
        $("#paymentKey").val($(vari).attr("id"));
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
function getOrderStatusKR(status){
    switch( status ){
        case "CANCEL":
            return "취소됨";
        case "READY":
            return "배송 준비중";
        case "DELIVERY":
            return "배송 중";
        case "COMPLETE":
            return "완료 됨";
    }
}
function pageUp(){
    var value=parseInt($("#page").val());
    if($(".component").length == 3){
        $("#page").val(value+1)
        pageChange(value+1);
    }else{
        alert("다음 페이지가 존재하지 않습니다.")
    }
}
function pageDown(){
    var value=parseInt($("#page").val());
    if(value==1){
        alert("첫번째 페이지 입니다.")
    }else{
        $("#page").val(value-1);
        pageChange(value-1);
    }
}
function pageChange(value){

    var offset=0+((value-1)*3);
    var limit=offset+3;
    getOrderedItem(offset,limit);
}
async function getPersonalInfo(){
    var url="/api/v1/members/single/local/aboutMe";
    const userinfoRes = await fetch(url, {method:"get"}).then((response) => response.json());

    $("#personal_id").text(userinfoRes.username);
    $("#personal_name").text(userinfoRes.name);
    $("#cg_tel").val(userinfoRes.phone_num);
    $("#personal_email").text(userinfoRes.email);
    $("#postcode").val(userinfoRes.address.postcode);
    $("#address").val(userinfoRes.address.address);
    $("#detailAddress").val(userinfoRes.address.detailAddress);
    $("#extraAddress").val(userinfoRes.address.ref_address);
}

function checkValidPassword() {
    var pass=$("#pwd").val();
    var passchk=$("#pwd_chk").val();
    if(pass != passchk){
        alert("비밀번호와 확인 칸의 값이 다릅니다.");

        return false;
    }
    if ($("#pwd").val() == "") {
        alert("비밀 번호의 공백을 없애주세요")
        return false;
    }

    const pw = $("#pwd").val();
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

async function changePwd(){
    if(checkValidPassword()){
        let obj={
            str:$("#pwd").val()
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
                location.reload();
            }
        )
    }
}

function chkValidAddr(){
    if($("#postcode").val() &&$("#address").val() && $("#detailAddress").val() && $("#extraAddress").val()){
        return true;
    }else{
        return false;
    }
}
async function changeAddr(){
    if(chkValidAddr()){
        let obj={
            postcode:$("#postcode").val(),
            address:$("#address").val(),
            detailAddress:$("#detailAddress").val(),
            ref_address:$("#extraAddress").val()
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
                location.reload();
            }
        )
    }
}

function chkValidPhone(){
    var phonenum=$("#cg_tel").val();
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
async function changeNum(){
    if(chkValidPhone()){
        let obj={
            str:$("#cg_tel").val()
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
                location.reload();
            }
        )
    }
}