$(document).ready(function(){
    SessionCheck();
    SearchSetting();
    logoSetting();
    var keyword=KeyWordCheck();
    getItem(keyword);
})
function show_explanation() {
    var con1 = document.getElementById("details_explanation");
    var con2 = document.getElementById("details_buy");
    var con3 = document.getElementById("details_evaluation");
    var con4 = document.getElementById("details_QnA");
    var btn = document.getElementById("details_explanation_btn");
    if(con1.style.display == "none"){
        con1.style.display = "block";
        con2.style.display = "none";
        con3.style.display = "none";
        con4.style.display = "none";
        btn.style.backgroundColor = "#212425";
        btn.style.color = "#fff";
    }
}

function show_buy() {
    var con1 = document.getElementById("details_explanation");
    var con2 = document.getElementById("details_buy");
    var con3 = document.getElementById("details_evaluation");
    var con4 = document.getElementById("details_QnA");
    if(con2.style.display == "none"){
        con1.style.display = "none";
        con2.style.display = "block";
        con3.style.display = "none";
        con4.style.display = "none";
    }
}

function show_evaluation() {
    var con1 = document.getElementById("details_explanation");
    var con2 = document.getElementById("details_buy");
    var con3 = document.getElementById("details_evaluation");
    var con4 = document.getElementById("details_QnA");
    if(con3.style.display == "none"){
        con1.style.display = "none";
        con2.style.display = "none";
        con3.style.display = "block";
        con4.style.display = "none";
    }
}

function show_QnA() {
    var con1 = document.getElementById("details_explanation");
    var con2 = document.getElementById("details_buy");
    var con3 = document.getElementById("details_evaluation");
    var con4 = document.getElementById("details_QnA");
    if(con4.style.display == "none"){
        con1.style.display = "none";
        con2.style.display = "none";
        con3.style.display = "none";
        con4.style.display = "block";
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