var sum=0;
$(document).ready(function (){
    SessionCheck();
    SearchSetting();
    logoSetting();
    mypageSetting();
    delBtnSetting();
    purchaseSetting();

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
        })



        var context=baseurl.toString();
        var len=context.substr((baseurl .protocol +"//"+baseurl .host+"/payments/purchase/").length);
        //결제창 아이템 처리
        if(len.indexOf("bycart")!=-1){
            sessionStorage.setItem("type","cart");
            var suburl="/api/v1/shopcarts/items/all?username="+res1.iswho;
            const res=await fetch(suburl,{method:"get"}).then(response => response.json());
            await $.each(res, function(idx){
                var innerhtml='<tr class="component">\n' +
                    '                    <td class="mer_img"><img src=/'+res[idx].imgSrc+'></td>\n' +
                    '                    <td class="name" id='+res[idx].item_id+'>'+res[idx].item_name+'</td>\n' +
                    '                    <td class="count"><input type="number" onchange="dynamictotal()" min="1" max="'+res[idx].stockQuantity+'" value='+res[idx].count+'></td>\n' +
                    '                    <td class="price">'+res[idx].price+'</td>\n' +
                    '                    <td class="del"><i id="delete" class="fa-solid fa-trash-can"></i></td></tr>';
                $("#shoppinglist").append(innerhtml);

                sum+=res[idx].count*res[idx].price;
            })

        }else{
            sessionStorage.setItem("type","single");
            var suburl2="/api/v2/items/single/"+len.substring(len.indexOf("/")+1);
            const res2=await fetch(suburl2,{method:"get"}).then(response => response.json());
            var innerhtml='<tr class="component">\n' +
                '                    <td class="mer_img"><img src=/'+res2.imgSrc+'></td>\n' +
                '                    <td class="name" id='+res2.item_ID+'>'+res2.name+'</td>\n' +
                '                    <td class="count"><input type="number" onchange="dynamictotal()" min="1" max='+res2.stockQuantity+'></td>\n' +
                '                    <td class="price">'+res2.price+'</td>\n' +
                '                    <td class="del"><i id="delete" class="fa-solid fa-trash-can"></i></td></tr>';
            $("#shoppinglist").append(innerhtml);
            var count=getcountVal();
            $(".count input").val(count);
            sum+=count*res2.price;
        }
        delBtnSetting();

    }else{
        $("#scart").click(function (){
            alert("로그인이 필요한 서비스 입니다.")
        })
    }
    $("#totalprice").text(sum +"원");


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
function checkTerms(){
    if($("#info-passAssign").is(":checked")&&$("#ElectricTradeAssign").is(":checked")&&$("#DBCommercialAssgin").is(":checked")){
        return true;
    }else{
        alert("약관에 동의해주세요");
        return false;
    }
}
function dynamictotal(){


    var newsum=0;
    var count=0;
    var counts=[]
    $(".component .count input").each(function (){
        var count=$(this).val();
        counts.push(count);
    });



    $(".price").each(function(){
        var price=$(this).text();
        var a=parseInt(price);
        var b=parseInt(counts[count++]);
        newsum+=(a*b);

    })
    if(newsum==0){
        alert("결제하실 상품이 존재하지 않습니다.");
        location.assign("/");
    }
    $("#totalprice").text(newsum +"원");

}
function delBtnSetting(){
    $(".del i").click(function (){
        $(this).closest("tr").remove();
        dynamictotal();
    })
}
function getcountVal(){
    return localStorage.getItem("count");
}
async function getuserInfo(){
    const condition=await fetch("/api/v1/members/is-oauth",{method:"get"}).then(response => response.json())
    if(condition){
        alert("Oauth 로그인(대행 로그인)은 이용하실 수 없는 서비스 입니다.");
        $("#sameUserinfo").prop("checked",false);
        return;
    }
    if($("#sameUserinfo").is(":checked")){
        //const res=await fetch("/api/v1/members/is-who",{method:"get"}).then(response => response.text());
        var userurl="/api/v1/members/single/local/aboutMe"
        const res1= await fetch(userurl,{method:"get",headers:{'Content-type':'application/json'}}).then(response => response.json());
        $("#user_info_name").val(res1.name);
        $("#user_info_tel").val(res1.phone_num);
        $("#user_info_email").val(res1.email);
    }else{
        $("#user_info_name").val("");
        $("#user_info_tel").val("");
        $("#user_info_email").val("");
    }


}
async function getuserAddress(){
    const condition=await fetch("/api/v1/members/is-oauth",{method:"get"}).then(response => response.json())
    if(condition){
        alert("Oauth 로그인(대행 로그인)은 이용하실 수 없는 서비스 입니다.");
        $("#sameUseraddress").prop("checked",false);
        return;
    }
    if($("#sameUseraddress").is(":checked")){
        //const res=await fetch("/api/v1/members/is-who",{method:"get"}).then(response => response.text());
        var userurl="/api/v1/members/single/local/aboutMe"
        const res1= await fetch(userurl,{method:"get",headers:{'Content-type':'application/json'}}).then(response => response.json());
        $("#postcode").val(res1.address.postcode);
        $("#address").val(res1.address.address);
        $("#detailAddress").val(res1.address.detailAddress);
        $("#extraAddress").val(res1.address.ref_address);
    }else{
        $("#postcode").val("");
        $("#address").val("");
        $("#detailAddress").val("");
        $("#extraAddress").val("");
    }

}
function purchaseSetting(){
    $("#main_purchase").on("click","div",function(){
        if(checkTerms()&&checkValidName()&&checkvalidEmail()&&checkAddress()&&checkValidPhone()){
            var itemlist=getIteminCode();
            data={
                "name":$("#user_info_name").val(),
                "phone_num":$("#user_info_tel").val(),
                "email":$("#user_info_email").val(),
                "totalprice":$("#totalprice").text(),
                "postcode":$("#postcode").val(),
                "address":$("#address").val(),
                "detailAddress":$("#detailAddress").val(),
                "extraAddress":$("#extraAddress").val(),
                "type":sessionStorage.getItem("type"),
                "item":itemlist
            }
            makeOrder(data);

        }else{
            alert("필수 입력사항을 전부 입력해주세요.");
        }
    })
}

function checkvalidEmail(){
    if ($("#user_info_email").val() == "") {
        $("#user_info_email").attr("placeholder","필수 정보입니다.");
        $("#user_info_email").css("border-color","red");
        //form.email.focus();
        return false;
    }

    const exptext = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;

    // "ㅁ@ㅁ.ㅁ" 이메일 형식 검사.
    if (exptext.test($("#user_info_email").val()) === false) {
        $("#user_info_email").val(null);
        $("#user_info_email").attr("placeholder","유효하지 않은 이메일입니다");
        $("#user_info_email").css("border-color","red");
        //form.email.select();
        return false;
    }

    $("#emailvalue").attr("placeholder","이메일을 입력해주세요");
    $("#emailvalue").css("border-color","black");
    return true;
}

function checkValidName() {
    $("#user_info_name").css("color","black");
    $("#user_info_name").css("border","1px solid black");
    if ($("#user_info_name").val() == "") {
        $("#user_info_name").attr("placeholder","필수 정보 입니다.");
        $("#user_info_name").css("border","3px solid red");
        $("#user_info_name").css("color","red");
        return false;
    }

    return true;
}
function checkValidPhone() {
    if ( ($("#user_info_tel").val() == "")) {
        alert("전화번호를 다시 기입해주세요")
        //form.phone.focus();
        return false;
    }

    return true;
}
function checkAddress(){

    if($("#postcode").val()&&$("#address").val()&&$("#detailAddress").val()&&$("#extraAddress").val()){
        return true;
    }
    else{

        return false;
    }
}
function getIteminCode(){
    var item=[];
    $("#shoppinglist .component").each(function(){
        var individualItem={
            "item_id":$(this).find(".name").attr('id'),
            "count":$(this).find(".count input").val()
        }

        item.push(individualItem);
        console.log(individualItem.item_id+"갯수:"+individualItem.count)
    })

    return item;

}

async function makeOrder(data){
    const result=await fetch("/api/v1/orders/create"
    ,{
        method:"post",headers:{'Content-Type':'application/json'},
            body:JSON.stringify(data)
        }).then( response => response.text())

    if(result==-1){
        alert("에러가 발생하였습니다. 다시 시도해주세요");
        location.reload();
    }else{

        data.orderId=result;
        $("#orderId").val(result);
        var id=data.item[0].item_id;
        var str="#"+id;
        var firstName=$(str).text();
        var orderName=firstName+" 및 "+$(".component").length+"건";
        $("#orderName").val(orderName);
        $("#customerName").val(data.name);
        openTosspay();


    }
    function openTosspay(){
        window.open("/payments/credit/bytoss",'토스페이 결제',"width=800,height=600");
    }
}

