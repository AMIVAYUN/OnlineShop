var btn1_state = true;
var btn2_state = false;
var btn3_state = false;
var btn4_state = false;

$(document).ready(function(){
    SessionCheck();

    SearchSetting();
    logoSetting();
    var keyword=KeyWordCheck();
    getItem(keyword);
    detailBtnSet();
    mypageSetting();
    shopCartSetting(keyword);

})

function detailBtnSet(){
    var btn = document.getElementById("details_explanation_btn");
    btn.style.backgroundColor = "rgb(225,225,225)";
}

function show_explanation() {
    var con1 = document.getElementById("details_explanation");
    var con2 = document.getElementById("details_buy");
    var con3 = document.getElementById("details_exchange");
    var con4 = document.getElementById("details_QnA");
    var btn1 = document.getElementById("details_explanation_btn");
    var btn2 = document.getElementById("details_buy_btn");
    var btn3 = document.getElementById("details_exchange_btn");
    var btn4 = document.getElementById("details_QnA_btn");
    if(con1.style.display == "none"){
        con1.style.display = "block";
        con2.style.display = "none";
        con3.style.display = "none";
        con4.style.display = "none";
        btn1.style.backgroundColor = "rgb(225,225,225)";
        btn2.style.backgroundColor = "rgb(245,245,245)";
        btn3.style.backgroundColor = "rgb(245,245,245)";
        btn4.style.backgroundColor = "rgb(245,245,245)";
        btn1_state = true;
        btn2_state = false;
        btn3_state = false;
        btn4_state = false;
    }
}

function show_buy() {
    var con1 = document.getElementById("details_explanation");
    var con2 = document.getElementById("details_buy");
    var con3 = document.getElementById("details_exchange");
    var con4 = document.getElementById("details_QnA");
    var btn = document.getElementById("details_buy_btn");
    var btn1 = document.getElementById("details_explanation_btn");
    var btn2 = document.getElementById("details_buy_btn");
    var btn3 = document.getElementById("details_exchange_btn");
    var btn4 = document.getElementById("details_QnA_btn");
    if(con2.style.display == "none"){
        con1.style.display = "none";
        con2.style.display = "block";
        con3.style.display = "none";
        con4.style.display = "none";
        btn1.style.backgroundColor = "rgb(245,245,245)";
        btn2.style.backgroundColor = "rgb(225,225,225)";
        btn3.style.backgroundColor = "rgb(245,245,245)";
        btn4.style.backgroundColor = "rgb(245,245,245)";
        btn1_state = false; btn2_state = true; btn3_state = false; btn4_state = false;
    }
}

function show_exchange() {
    var con1 = document.getElementById("details_explanation");
    var con2 = document.getElementById("details_buy");
    var con3 = document.getElementById("details_exchange");
    var con4 = document.getElementById("details_QnA");
    var btn1 = document.getElementById("details_explanation_btn");
    var btn2 = document.getElementById("details_buy_btn");
    var btn3 = document.getElementById("details_exchange_btn");
    var btn4 = document.getElementById("details_QnA_btn");
    if(con3.style.display == "none"){
        con1.style.display = "none";
        con2.style.display = "none";
        con3.style.display = "block";
        con4.style.display = "none";
        btn1.style.backgroundColor = "rgb(245,245,245)";
        btn2.style.backgroundColor = "rgb(245,245,245)";
        btn3.style.backgroundColor = "rgb(225,225,225)";
        btn4.style.backgroundColor = "rgb(245,245,245)";
        btn1_state = false;
        btn2_state = false;
        btn3_state = true;
        btn4_state = false;
    }
}

function show_QnA() {
    var con1 = document.getElementById("details_explanation");
    var con2 = document.getElementById("details_buy");
    var con3 = document.getElementById("details_exchange");
    var con4 = document.getElementById("details_QnA");
    var btn1 = document.getElementById("details_explanation_btn");
    var btn2 = document.getElementById("details_buy_btn");
    var btn3 = document.getElementById("details_exchange_btn");
    var btn4 = document.getElementById("details_QnA_btn");
    if(con4.style.display == "none"){
        con1.style.display = "none";
        con2.style.display = "none";
        con3.style.display = "none";
        con4.style.display = "block";
        btn1.style.backgroundColor = "rgb(245,245,245)";
        btn2.style.backgroundColor = "rgb(245,245,245)";
        btn3.style.backgroundColor = "rgb(245,245,245)";
        btn4.style.backgroundColor = "rgb(225,225,225)";
        btn1_state = false;
        btn2_state = false;
        btn3_state = false;
        btn4_state = true;
    }
}

$(document).ready(function(){
    $('#details_explanation_btn').hover(function(){
        $(this).css('color', '#fff');
        $(this).css('backgroundColor', '#212425');
        }, function(){
        $(this).css('color', 'black');
        if(btn1_state == false){
            $(this).css('backgroundColor', 'rgb(245,245,245)');
        } else {
            $(this).css('backgroundColor', 'rgb(225,225,225)');
        }
    })
    $('#details_buy_btn').hover(function(){
        $(this).css('color', '#fff');
        $(this).css('backgroundColor', '#212425');
        }, function(){
        $(this).css('color', 'black');
        if(btn2_state == false){
            $(this).css('backgroundColor', 'rgb(245,245,245)');
        } else {
            $(this).css('backgroundColor', 'rgb(225,225,225)');
        }
    })
    $('#details_exchange_btn').hover(function(){
        $(this).css('color', '#fff');
        $(this).css('backgroundColor', '#212425');
        }, function(){
        $(this).css('color', 'black');
        if(btn3_state == false){
            $(this).css('backgroundColor', 'rgb(245,245,245)');
        } else {
            $(this).css('backgroundColor', 'rgb(225,225,225)');
        }
    })
    $('#details_QnA_btn').hover(function(){
        $(this).css('color', '#fff');
        $(this).css('backgroundColor', '#212425');
        }, function(){
        $(this).css('color', 'black');
        if(btn4_state == false){
            $(this).css('backgroundColor', 'rgb(245,245,245)');
        } else {
            $(this).css('backgroundColor', 'rgb(225,225,225)');
        }
    })
})

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

async function getItem(keyword){
    var url="/api/v2/items/single/"+keyword;
    const res=await fetch(url, {method: "get"}).then(response => response.json());
    $("#product_image").attr("src","/"+res.imgSrc);
    $("#product_name").text(res.name);
    $("#product_price").text(res.price);
    $("#product_madeIn").text(res.madeIn);
    $("#product_manufacturedCompany").text(res.manufacturedCompany);
    $("#product_stockQuantity").text(res.stockQuantity);
    $("#product_weight").text(res.weight);
    $("#product_description").text(res.description);
}

function KeyWordCheck(){
    var baseurl=window.location;
    var locate=baseurl .protocol +"//"+baseurl .host+"/products/"
    var str=baseurl.toString();
    var Keyword=str.substr(locate.length,str.length);
    return Keyword;
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
async function shopCartSetting(keyword){
    $("#btn_cart").click(async function(){
        const res=await fetch("/api/v1/members/is-who",{method:"get"}).then(response => response.text());
        if(res==='anonymousUser'){
            alert("로그인이 필요한 서비스 입니다.");
        }else{
            let obj={
                "username":res,
                "item_id":keyword,
                "count":$("#itemcount").val()

            }
            const res1= await fetch("/api/v1/shopcarts/single/append.do",{method:"post",headers:{'Content-Type': 'application/json'},body:JSON.stringify(obj)}).then(response => response.json());
            if(res1){
                alert("장바구니에 담겼습니다.");
            }else{
                alert("이미 담으신 상품입니다.");
            }
        }
    })


}


